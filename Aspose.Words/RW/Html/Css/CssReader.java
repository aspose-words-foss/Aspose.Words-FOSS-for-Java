// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/02/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;
import com.aspose.HashSetGeneric;
import java.util.ArrayList;
import com.aspose.StringToObjDictionary;
import com.aspose.StringUtil;
import com.aspose.UriUtil;
import com.aspose.ms.System.msString;


/// <summary>
/// Reads multiple CSS style sheets.
/// </summary>
class CssReader
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="baseUri">Base URI of the document. The base URI is used to resolve relative URI of external CSS documents.</param>
    /// <param name="htmlResourceLoader">HtmlResourceLoader used for loading resources.
    /// This is needed for @import rules when importing from container formats (MHTML).</param>
    /// <param name="documentMode">The mode in which the corresponding HTML document has been opened.</param>
    /// <param name="mimicMsWordBehavior">Indicates whether we should stick to MS Word's behavior when reading CSS.</param>
    CssReader(
        String baseUri,
        HtmlResourceLoader htmlResourceLoader,
        /*CssDocumentMode*/int documentMode,
        boolean mimicMsWordBehavior)
    {
        Debug.assertTrue(htmlResourceLoader != null);

        mHtmlResourceLoader = htmlResourceLoader;
        mStyleSheetsBeingProcessed = new HashSetGeneric<String>();
        mBaseUri = baseUri;
        if (mBaseUri == null)
            mBaseUri = "";
        mDocumentMode = documentMode;
        mNamespacePrefixResolver = new CssNamespacePrefixResolver();
        mMimicMsWordBehavior = mimicMsWordBehavior;
    }

    /// <summary>
    /// Reads CSS rules starting from this node.
    /// </summary>
    /// <param name="rootElement">Root element of a HTML document.</param>
    void readAllRules(IElementProvider rootElement) throws Exception
    {
        setStyleRules(new ArrayList<CssStyleRule>());
        setPageRules(new ArrayList<CssPageRule>());
        setFontFaceRules(new ArrayList<CssFontFaceRule>());
        setListRules(new ArrayList<CssListRule>());

        // WORDSNET-9318 Browsers read styles from "style" and "link" nodes
        // throughout the entire document before processing the document.
        collectElementStyleInformation(rootElement);

        // WORDSNET-27811 MS Word supports only a small subset of CSS selectors. Remove rules with unsupported selectors.
        if (mMimicMsWordBehavior)
        {
            removeRulesWithSelectorsNotSupportedByMsWord();
        }

        // WORDSNET-18682 Optimize loaded stylesheet by combining style rules with equal selectors. This also
        // removes duplicate style rules from the stylesheet. As a side effect, rules with more than one selector are
        // split into multiple rules all sharing same declarations: one rule per selector.
        // MS Word processes style rules individually so we cannot optimize them when loading MsoHtml.
        if (!mMimicMsWordBehavior)
        {
            optimizeStyleRules();
        }
    }

    private void removeRulesWithSelectorsNotSupportedByMsWord()
    {
        int i = 0;
        while (i < getStyleRules().size())
        {
            boolean ruleHasUnsupportedSelectors = false;
            for (CssSelector selector : getStyleRules().get(i).getSelectors())
            {
                MsoHtmlSelector msoHtmlSelector = MsoHtmlSelector.parse(selector.toCss());
                if (!msoHtmlSelector.isSupportedByMsWord())
                {
                    ruleHasUnsupportedSelectors = true;
                    break;
                }
            }
            if (ruleHasUnsupportedSelectors)
            {
                // Remove the whole rule if any of its selectors is not supported by MS Word.
                // Continue without incrementing the loop variable, because removal of a list item shifts indexes
                // of the list's tail elements.
                getStyleRules().remove(i);
            }
            else
            {
                ++i;
            }
        }
    }

    private void optimizeStyleRules()
    {
        // Contains optimized (combined) CSS rules indexed by their selectors. Note that after optimization each rule
        // has only one selector.
        StringToObjDictionary<CssStyleRule> selectorsToOptimizedRulesMap = new StringToObjDictionary<CssStyleRule>();

        boolean anythingOptimized = false;
        for (CssStyleRule rule : getStyleRules())
        {
            // Split rules by their selectors.
            for (CssSelector selector : rule.getSelectors())
            {
                // Since our CSS parser guarantees that in parsed CSS rules no CSS namespaces will share the same prefix,
                // it's safe to compare selectors from different rules just by their CSS text.
                String selectorCss = selector.toCss();

                CssStyleRule optimizedRule;
                CssStyleRule ruleWithSameSelector = selectorsToOptimizedRulesMap.get(selectorCss);
                if (ruleWithSameSelector != null)
                {
                    // Found a rule with the same selector. Optimize two rules by combining their declarations.
                    CssDeclarationCollectionBuilder combinedDeclarationsBuilder =
                        new CssDeclarationCollectionBuilder(ruleWithSameSelector.getDeclarations());
                    // Combine declarations paying attention to their importance.
                    combinedDeclarationsBuilder.addOrReplaceIfMoreOrEquallyImportant(rule.getDeclarations());

                    optimizedRule = new CssStyleRule(
                        ruleWithSameSelector.getSelectors(),
                        combinedDeclarationsBuilder.getDeclarations());

                    anythingOptimized = true;
                }
                else
                {
                    // No rules with this selector have been processed yet, so there is nothing to optimize.
                    // Remember the rule for further optimization.
                    // MEMORY: Try not to create new instances of selector lists.
                    CssSelector[] singleSelector = (rule.getSelectors().length == 1)
                        ? rule.getSelectors()
                        : new CssSelector[] { selector };
                    optimizedRule = new CssStyleRule(singleSelector, rule.getDeclarations());
                }

                // Update the optimized rule.
                selectorsToOptimizedRulesMap.set(selectorCss, optimizedRule);
            }
        }

        // Replace loaded rules with optimized version.
        if (anythingOptimized)
        {
            getStyleRules().clear();
            for (CssStyleRule styleRule : selectorsToOptimizedRulesMap.getValues())
            {
                getStyleRules().add(styleRule);
            }
        }
    }

    private void collectElementStyleInformation(IElementProvider element) throws Exception
    {
        // WORDSNET-20441 We used to do recursive tree traversal here but it caused stack overflow exceptions
        // on huge trees. Now we do non-recursive depth-first traversal instead.
        IElementProvider currentElement = element.getFirstChildElement();
        while (currentElement != null)
        {
            findStylesInNode(currentElement);

            IElementProvider firstChild = currentElement.getFirstChildElement();
            if (firstChild != null)
            {
                currentElement = firstChild;
                continue;
            }

            IElementProvider nextSibling = currentElement.getNextSiblingElement();
            if (nextSibling != null)
            {
                currentElement = nextSibling;
                continue;
            }

            IElementProvider parent = currentElement.getParentElement();
            currentElement = null;
            while ((parent != null) && (parent != element))
            {
                nextSibling = parent.getNextSiblingElement();
                if (nextSibling != null)
                {
                    currentElement = nextSibling;
                    break;
                }
                parent = parent.getParentElement();
            }
        }
    }

    /// <summary>
    /// Processes one embedded CSS style sheet read into string. At this stage nothing is populated
    /// to the document because we can have multiple style sheets. Styles should be
    /// consolidated and only after that imported to the model.
    /// </summary>
    /// <param name="styleSheet">The text with style sheet</param>
    private void processEmbeddedStyleSheet(String styleSheet) throws Exception
    {
        processStyleSheetCore(styleSheet, "");
    }

    /// <summary>
    /// Processes external CSS style sheet read into string. At this stage nothing is populated
    /// to the document because we can have multiple style sheets. Styles should be
    /// consolidated and only after that imported to the model.
    /// </summary>
    /// <param name="stylesheetUri">Original URI of this style sheet.</param>
    private void processExternalStyleSheet(String stylesheetUri) throws Exception
    {
        Debug.assertTrue(StringUtil.hasChars(stylesheetUri));

        // Use the absolute URI of the stylesheet to make sure we load each stylesheet only once.
        String absoluteUri = UriUtil.constructUnescapedAbsoluteUri(mBaseUri, stylesheetUri);
        if (!mStyleSheetsBeingProcessed.add(absoluteUri))
        {
            return;
        }

        String styleSheet = mHtmlResourceLoader.loadCss(mBaseUri, stylesheetUri);
        if (styleSheet != null)
        {
            processStyleSheetCore(styleSheet, stylesheetUri);
        }
    }

    /// <summary>
    /// Called on start boundary of any node when collecting style information.
    /// </summary>
    private void findStylesInNode(IElementProvider element) throws Exception
    {
        switch (gStringSwitchMap.of(element.getElementName()))
        {
            case /*"style"*/0:
                handleStyle(element);
                break;
            case /*"link"*/1:
                handleLink(element);
                break;
            case /*"base"*/2:
                // If found before a style sheet or link should affect them
                mBaseUri = HtmlUtil.validateUri(element.getAttributeValue("href", ""));
                break;
            default:
                // Styles in other elements are ignored.
                break;
        }
    }

    /// <summary>
    /// Reads embedded style information from CSS style sheet.
    /// </summary>
    private void handleStyle(IElementProvider element) throws Exception
    {
        // The HTML Specification says that:
        // Authors should not specify a type attribute on a style element. If the attribute is present, its value
        // must be an ASCII case-insensitive match for "text/css".
        // See https://html.spec.whatwg.org/multipage/obsolete.html#obsolete-but-conforming-features
        String typeValue = element.getAttributeValue("type"); // Will be null if "type" is not present.
        if (!StringUtil.hasChars(typeValue) ||
            StringUtil.equalsIgnoreCase(typeValue, "text/css"))
        {
            processEmbeddedStyleSheet(element.getInnerText());
        }
    }

    /// <summary>
    /// Reads external style information from CSS style sheet.
    /// Other kinds of links are currently not supported.
    /// </summary>
    private void handleLink(IElementProvider element) throws Exception
    {
        if (StringUtil.equalsIgnoreCase(element.getAttributeValue("type", "text/css"), "text/css") &&
            StringUtil.equalsIgnoreCase(element.getAttributeValue("rel", ""), "stylesheet"))
        {
            // WORDSNET-17788 Previously, we loaded CSS files with 'disabled' attribute.
            // Now we skip such files.
            boolean isDisabled = msString.equals(element.getAttributeValue("disabled"), "");
            if (isDisabled)
                return;

            String href = HtmlUtil.validateUri(element.getAttributeValue("href", ""));
            if (href.length() > 0)
            {
                String media = element.getAttributeValue("media", "");
                CssMediaQueryList mediaQueryList = CssParser.parseMediaQueryList(media);
                if (mediaQueryList.isSupported())
                {
                    // Resource might be inaccessible.
                    // At this moment just ignore the absence of stylesheet. Most common behavior
                    // is resilience in case when referenced file is inaccessible.
                    // Create the style sheet by either loading from URI or reusing already loaded bytes.
                    // We map binary from original URI since when we decode the resource we might not know the base URI.
                    // Base URI is constant so the relative URIs will be distinguishable.
                    // It's uncertain that the same style sheet will be loaded several times. So this mechanism is needed
                    // only to support HTML-based container formats such MHTML. Don't put loaded style sheets in the hash.
                    processExternalStyleSheet(href);
                }
            }
        }
    }

    /// <summary>
    /// Processes one CSS style sheet. At this stage nothing is populated to the document because we can have multiple
    /// style sheets. Styles should be consolidated and only after that imported to the model.
    /// </summary>
    /// <param name="styleSheet">The text with style sheet.</param>
    /// <param name="stylesheetUri">
    /// URI of this stylesheet to properly process @import rules. Can be empty but not null.
    /// </param>
    private void processStyleSheetCore(String styleSheet, String stylesheetUri) throws Exception
    {
        Debug.assertTrue(styleSheet != null);
        Debug.assertTrue(stylesheetUri != null);

        ArrayList<CssRule> rules = CssParser.parseStylesheet(
            styleSheet,
            mDocumentMode,
            stylesheetUri,
            mNamespacePrefixResolver);
        for (CssRule rule : rules)
        {
            processRule(rule);
        }
    }

    private void processRule(CssRule rule) throws Exception
    {

        switch (rule.getType())
        {
            case CssRuleType.IMPORT:
            {
                CssImportRule importRule = (CssImportRule)rule;
                if (importRule.getMediaQuery().isSupported())
                {
                    processExternalStyleSheet(importRule.getUrl());
                }
                break;
            }
            case CssRuleType.MEDIA:
            {
                processMediaRule((CssMediaRule)rule);
                break;
            }
            case CssRuleType.PAGE:
            {
                getPageRules().add((CssPageRule)rule);
                break;
            }
            case CssRuleType.STYLE:
            {
                getStyleRules().add((CssStyleRule)rule);
                break;
            }
            case CssRuleType.FONT_FACE:
            {
                getFontFaceRules().add((CssFontFaceRule)rule);
                break;
            }
            case CssRuleType.LIST:
            {
                getListRules().add((CssListRule)rule);
                break;
            }
            default:
            {
                Debug.assertTrue(false);
                break;
            }
        }
    }

    /// <summary>
    /// Processes rules nested in a @media rule.
    /// </summary>
    private void processMediaRule(CssMediaRule rule) throws Exception
    {
        if (!rule.getMediaQueryList().isSupported())
        {
            return;
        }

        for (CssRule nestedRule : rule.getRules())
        {
            processRule(nestedRule);
        }
    }

    /// <summary>
    /// CSS style rules read by <see cref="ReadAllRules" /> function.
    /// </summary>
    ArrayList<CssStyleRule> getStyleRules() { return mStyleRules; }; private void setStyleRules(ArrayList<CssStyleRule> value) { mStyleRules = value; };

    private ArrayList<CssStyleRule> mStyleRules;

    /// <summary>
    /// CSS @page rules read by <see cref="ReadAllRules" /> function.
    /// </summary>
    ArrayList<CssPageRule> getPageRules() { return mPageRules; }; private void setPageRules(ArrayList<CssPageRule> value) { mPageRules = value; };

    private ArrayList<CssPageRule> mPageRules;

    /// <summary>
    /// CSS @font-face rules read by <see cref="ReadAllRules" /> function.
    /// </summary>
    ArrayList<CssFontFaceRule> getFontFaceRules() { return mFontFaceRules; }; private void setFontFaceRules(ArrayList<CssFontFaceRule> value) { mFontFaceRules = value; };

    private ArrayList<CssFontFaceRule> mFontFaceRules;

    /// <summary>
    /// CSS @list rules read by <see cref="ReadAllRules" /> function.
    /// </summary>
    ArrayList<CssListRule> getListRules() { return mListRules; }; private void setListRules(ArrayList<CssListRule> value) { mListRules = value; };

    private ArrayList<CssListRule> mListRules;

    /// <summary>
    /// This hashset is needed to know what style sheets are already started processing. Prevents recursion and duplication.
    /// </summary>
    /// <remarks>
    /// Keys are absolute URIs of stylesheets.
    /// </remarks>
    private /*final*/ HashSetGeneric<String> mStyleSheetsBeingProcessed;

    /// <summary>
    /// Caches all loaded resources and provides access to them which is independent on whether a resource already loaded or not yet.
    /// </summary>
    private /*final*/ HtmlResourceLoader mHtmlResourceLoader;

    private /*final*/ /*CssDocumentMode*/int mDocumentMode;
    private /*final*/ CssNamespacePrefixResolver mNamespacePrefixResolver;

    /// <summary>
    /// Used to construct full path for external CSS documents that specify relative path.
    /// </summary>
    private String mBaseUri;

    /// <summary>
    /// Indicates whether we should stick to MS Word's behavior when reading CSS.
    /// </summary>
    private /*final*/ boolean mMimicMsWordBehavior;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"style",
		"link",
		"base"
	);

}

