// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/12/2016 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import java.util.ArrayList;
import com.aspose.Debug;
import com.aspose.ms.System.Collections.msStack;
import com.aspose.IntList;
import com.aspose.ms.System.msString;
import com.aspose.BidiLevelList;
import com.aspose.StringUtil;
import java.util.Collection;
import com.aspose.FormatterPal;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.MathUtil;
import com.aspose.BidiLevel;
import java.util.HashMap;
import com.aspose.ms.System.Collections.msDictionary;
import java.util.Map;
import com.aspose.IntToObjDictionary;
import com.aspose.words.ref.Ref;


/// <summary>
/// Keeps track of CSS formatting of HTML elements when an HTML tree is being traversed.
/// </summary>
class CssStyleTracker
{
    /// <summary>
    /// Initializes an instance of the class.
    /// </summary>
    /// <param name="rules">CSS rules imported from the HTML document.</param>
    /// <param name="pageRules">CSS @page rules imported from the HTML document.</param>
    /// <param name="htmlElementInlineCssStyle">Inline CSS style of the HTML element.</param>
    /// <param name="bodyElementInlineCssStyle">Inline CSS style of the BODY element.</param>
    /// <param name="cssResolver">Instance of <see cref="CssResolver"/>.</param>
    /// <param name="elementCategorizer">Instance of <see cref="HtmlElementCategorizer"/>.</param>
    /// <param name="attributeResolver">Instance of <see cref="HtmlAttributeResolver"/>.</param>
    /// <param name="defaultElementStyleResolver">Instance of <see cref="DefaultElementStyleResolver"/>.</param>
    /// <param name="computedDeclarationResolver">Instance of <see cref="CssComputedDeclarationResolver"/>.</param>
    /// <param name="boxModel">Css box model.</param>
    /// <param name="resolveFormattingAsMsWord">
    /// Indicates whether we should stick to MS Word's rules when resolving CSS formatting.
    /// </param>
    /// <param name="useHtmlBlocks">
    /// Indicates whether to mimic MS Word's behavior and use <see cref="HtmlBlock"/> nodes to preserve borders and margins
    /// of block-level elements like BODY, DIV, or BLOCKQUOTE.
    /// </param>
    CssStyleTracker(
        ArrayList<CssStyleRule> rules,
        ArrayList<CssPageRule> pageRules,
        String htmlElementInlineCssStyle,
        String bodyElementInlineCssStyle,
        CssResolver cssResolver,
        HtmlElementCategorizer elementCategorizer,
        HtmlAttributeResolver attributeResolver,
        DefaultElementStyleResolver defaultElementStyleResolver,
        CssComputedDeclarationResolver computedDeclarationResolver,
        CssBoxModel boxModel,
        boolean resolveFormattingAsMsWord,
        boolean useHtmlBlocks)
    {
        Debug.assertTrue(rules != null);
        Debug.assertTrue(pageRules != null);
        Debug.assertTrue(htmlElementInlineCssStyle != null);
        Debug.assertTrue(bodyElementInlineCssStyle != null);
        Debug.assertTrue(cssResolver != null);
        Debug.assertTrue(elementCategorizer != null);
        Debug.assertTrue(attributeResolver != null);
        Debug.assertTrue(defaultElementStyleResolver != null);
        Debug.assertTrue(computedDeclarationResolver != null);
        Debug.assertTrue(boxModel != null);

        mStyleRules = rules;
        mPageRules = pageRules;
        mHtmlElementInlineCssStyle = htmlElementInlineCssStyle;
        mBodyElementInlineCssStyle = bodyElementInlineCssStyle;
        mCssResolver = cssResolver;
        mElementCategorizer = elementCategorizer;
        mAttributeResolver = attributeResolver;
        mDefaultElementStyleResolver = defaultElementStyleResolver;
        mComputedDeclarationResolver = computedDeclarationResolver;
        setBoxModel(boxModel);

        mElements = new ArrayList<HtmlElementInfo>();
        mTextDecorationsStack = new msStack<CssTextDecoration>();
        mInlineBorderStack = new msStack<CssBorder>();
        mBackgroundResolver = new BackgroundResolver();
        mActiveBidiLevels = new IntList();
        mSupSubScriptResolver = new SupSubScriptResolver();
        mCounters = new CssCounters();

        mResolveFormattingAsMsWord = resolveFormattingAsMsWord;
        mUseHtmlBlocks = useHtmlBlocks;

        // SPEED - In order to speed up getting of CSS declarations by CSS selectors, we cache CSS selector values and
        // their corresponding CSS rule declarations.
        fillSelectorToComputedDeclarationsMap();

        calculatePredefinedStyleDeclarations();
    }

    CssDeclarationCollection getPredefinedStyleDeclarations(/*StyleIdentifier*/int styleIdentifier)
    {
        return mPredefinedStyleDeclarations.get((int)styleIdentifier);
    }

    /// <summary>
    /// Adds an element to the stack and calculates document style for this element.
    /// </summary>
    /// <param name="element">
    /// An HTML element to add.
    /// </param>
    /// <param name="updateCounters">
    /// Controls whether CSS counters must be updated after the element is added. Counters must be updated exactly once
    /// per each HTML element in a document tree.
    /// </param>
    /// <remarks>
    /// In order to correctly calculate CSS counters, HTML elements of a document tree must be processed in a depth-first 
    /// manner without returns (each element must be processed exactly once). However, the style resolver allows for
    /// reprocessing of HTML elements and we actively use this possibility, for example, to figure out structure of a table
    /// before processing its elements or to modify HTML tree and insert anonymous nodes into it. If we push an HTML
    /// element to the resolver more than once, we must use the <paramref name="updateCounters"/> flag to prevent repeated
    /// update of counters.
    /// </remarks>
    void pushElement(IHtmlElementProvider element, boolean updateCounters)
    {
        // Counters are never updated for implicit (anonymous) elements, because there is no way to apply custom CSS styles
        // to such elements.
        if (element.isImplicit())
        {
            Debug.assertTrue(!updateCounters);
        }

        mCssResolver.pushElement(element);
        CssDeclarationCollection declarations = mCssResolver.getDeclarations();

        if ("img".equals(element.getElementName()))
        {
            // WORDSNET-10651 Correct CSS style of an <img> inside an absolutely positioned <span>
            // to make the <img> absolutely positioned too.
            declarations = correctStyleOfImagesInsidePositionedSpans(element, declarations);

            // WORDSNET-21954 Apply floating to the image if it is the only child of one or more floating parent
            // containers.
            declarations = correctStyleOfImagesInsideFloatingParents(element, declarations);
        }

        RealElementInfo elementInfo = new RealElementInfo(element, declarations,
            mCssResolver.getDeclarations(HtmlElementPart.BEFORE),
            mCssResolver.getDeclarations(HtmlElementPart.AFTER));
        pushElementInternal(elementInfo);

        // Counters must be updated after the element is pushed.
        if (updateCounters)
        {
            updateCounters(declarations);
        }

        // This method pushes real HTML elements only, not pseudo-elements. It mustn't accidentally switch the resolver
        // to a pseudo-element.
        Debug.assertTrue(getCurrentPart() == HtmlElementPart.ELEMENT);
    }

    /// <summary>
    /// Removes the top element from the stack.
    /// </summary>
    void popElement()
    {
        // If we forgot to switch the resolver back to the real HTML element before popping it, let's do it now.
        Debug.assertTrue(getCurrentPart() == HtmlElementPart.ELEMENT);
        switchToPart(HtmlElementPart.ELEMENT, false);

        popElementInternal();
    }

    void pushImplicitDiv()
    {
        if (mIsOnImplicitElement)
        {
            Debug.fail("Unexpected state.");
            return;
        }

        // HtmlReader can create new paragraphs during processing of non-paragraph elements like <div>, <td> or <span>. 
        // We cannot apply CSS of such elements directly to model paragraphs, and we have to insert an implicit <p>
        // element in order to get correct CSS for paragraphs.
        if ((!getCurrentElement().isImplicit()) && (!isMappedToParagraphElement()))
        {
            mReturnToElement = null;
            mReturnToPart = HtmlElementPart.ELEMENT;

            if (isMappedToRunElement())
            {
                // We need to go back to the parent HTML element.
                // If we are processing a real HTML element then we go back by popping that element as usual.
                if (getCurrentPart() == HtmlElementPart.ELEMENT)
                {
                    mReturnToElement = getCurrentElement();
                    popElement();
                }
                // If we are processing a pseudo-element, we go back by switching back to the main part
                // of the current element.
                else
                {
                    mReturnToPart = getCurrentPart();
                    switchToPart(HtmlElementPart.ELEMENT, false);
                }
            }
            IHtmlElementProvider pElement = new HtmlElementNode("p", true);
            // Counters are never updated for implicit elements.
            pushElement(pElement, false);
            mIsOnImplicitElement = true;
        }
    }

    void pushImplicitSpan()
    {
        if (mIsOnImplicitElement)
        {
            Debug.fail("Unexpected state.");
            return;
        }

        // WORDSNET-8856 We insert implicit (anonymous) span elements for text inside HTML blocks.
        // Code like <p style="...">Text</p> is processed as <p style="..."><span>Text</span></p>
        if ((!getCurrentElement().isImplicit()) && (!isMappedToRunElement()))
        {
            IHtmlElementProvider spanElement = new HtmlElementNode("span", true);
            // Counters are never updated for implicit elements.
            pushElement(spanElement, false);
            mIsOnImplicitElement = true;
            mReturnToElement = null;
            mReturnToPart = HtmlElementPart.ELEMENT;
        }
    }

    void popImplicitElement()
    {
        if (!mIsOnImplicitElement)
        {
            return;
        }

        // Remove the implicit element we might have inserted and restore the original HTML tree structure.
        Debug.assertTrue(getCurrentElement().isImplicit());
        popElement();

        // If we were processing a real HTML element then we restore the tree by pushing that element as usual.
        if (mReturnToElement != null)
        {
            // This element has been processed already and we should block repeated update of counters.
            // Otherwise, counter values will be corrupted.
            pushElement(mReturnToElement, false);
        }
        // If we were processing a pseudo-element, we restore the tree by switching to the part we were at.
        else if (mReturnToPart != HtmlElementPart.ELEMENT)
        {
            // This element has been processed already and we should block repeated update of counters.
            // Otherwise, counter values will be corrupted.
            switchToPart(mReturnToPart, false);
        }

        mIsOnImplicitElement = false;
        mReturnToElement = null;
        mReturnToPart = HtmlElementPart.ELEMENT;
    }

    /// <summary>
    /// Gets CSS block flow direction.
    /// </summary>
    /*CssBlockFlowDirection*/int getBlockFlowDirection()
    {
        String writingMode = getElementDeclarations().getIdentifier("writing-mode");
        return (!msString.equals(writingMode, ""))
                   ? CssUtil.cssWritingModeToBlockFlowDirection(writingMode)
                   : CssBlockFlowDirection.HORIZONTAL_TB;
    }

    /// <summary>
    /// Indicates whether the block-level element currently being processed has RTL direction.
    /// </summary>
    /// <returns>
    /// <c>true</c> if the block-level element currently being processed has RTL direction. 
    /// <c>false</c> if the element has LTR direction.
    /// </returns>
    boolean isBlockRtl()
    {
        return getCurrentElementInfo().BlockLevelDirection == CssDirection.RTL;
    }

    /// <summary>
    /// Returns a copy of currently active bidi levels.
    /// </summary>
    BidiLevelList getActiveBidiLevels()
    {
        return new BidiLevelList(mActiveBidiLevels.toArray());
    }

    /// <summary>
    /// Indicates whether text of HTML element currently being processed is preformatted.
    /// </summary>
    /// <returns>
    /// <c>true</c> if HTML element is preformatted.
    /// <c>false</c> if HTML element is not preformatted.
    /// </returns>
    boolean isPreformatted()
    {
        CssDeclaration whiteSpaceDeclaration = getElementDeclarations().get("white-space");
        return (whiteSpaceDeclaration != null) && whiteSpaceDeclaration.getValue().equals(CssValue.Pre);
    }

    /// <summary>
    /// Indicates whether text of HTML element currently being processed is preformatted with line.
    /// </summary>
    /// <returns>
    /// <c>true</c> if HTML element is preformatted with line.
    /// <c>false</c> if HTML element is not preformatted with line.
    /// </returns>
    boolean isPreformattedWithLine()
    {
        CssDeclaration whiteSpaceDeclaration = getElementDeclarations().get("white-space");
        return (whiteSpaceDeclaration != null) && whiteSpaceDeclaration.getValue().equals(CssValue.PreLine);
    }

    /// <summary>
    /// Indicates whether text of HTML element currently being processed is preformatted with wrap.
    /// </summary>
    /// <returns>
    /// <c>true</c> if HTML element is preformatted with wrap.
    /// <c>false</c> if HTML element is not preformatted with wrap.
    /// </returns>
    boolean isPreformattedWithWrap()
    {
        CssDeclaration whiteSpaceDeclaration = getElementDeclarations().get("white-space");
        return (whiteSpaceDeclaration != null) && whiteSpaceDeclaration.getValue().equals(CssValue.PreWrap);
    }

    /// <summary>
    /// Determines whether the current element is a block-level element.
    /// </summary>
    boolean isBlockLevelElement()
    {
        return getCurrentElementInfo().IsBlockLevelElement;
    }

    /// <summary>
    /// Determines whether the parent of current element is a block-level element.
    /// </summary>
    boolean parentElementIsBlockLevel()
    {
        HtmlElementInfo parentElementInfo = getElementInfo(1);
        return (parentElementInfo != null) && parentElementInfo.IsBlockLevelElement;
    }

    /// <summary>
    /// Looks up the HTML tree and gets the nearest inline parent that maps to a built-in character style.
    /// </summary>
    String findNearestInlineOverridableParentElement()
    {
        for (int depth = 0; depth < mElements.size(); ++depth)
        {
            HtmlElementInfo elementInfo = getElementInfo(depth);

            // Stop if we meet a block-level parent.
            if ((elementInfo == null) || elementInfo.IsBlockLevelElement)
            {
                return null;
            }

            // Recognized elements are:
            //  <a> with non-empty "href" attribute (maps to the "Hyperlink" style);
            //  <strong> (maps to the "Strong" style);
            //  <em> (maps to the "Emphasis" style).
            IHtmlElementProvider element = elementInfo.Element;
            String name = element.getElementName();
            if (("a".equals(name)) && StringUtil.hasChars(element.getAttributeValue("href")))
            {
                return name;
            }
            else if (("strong".equals(name)) || ("em".equals(name)))
            {
                return name;
            }

            // Elements with "class" attributes will have custom character styles applied.
            // That's why we stop searching if we meet such an element.
            String[] classes = element.getClasses();
            if (classes.length > 0)
            {
                return null;
            }
        }
        return null;
    }

    /// <summary>
    /// Gets parent HTML element "display" property value.
    /// </summary>
    /*CssDisplayType*/int parentElementDisplayType()
    {
        HtmlElementInfo parentElementInfo = getElementInfo(1);
        return (parentElementInfo != null)
            ? parentElementInfo.DisplayType
            : CssDisplayType.NONE;
    }

    /// <summary>
    /// Switches the resolver to the specified part (pseudo-element) of the current real HTML element.
    /// </summary>
    /// <param name="part">
    /// Part of HTML element on which the switch will be done.
    /// </param>
    /// <param name="updateCounters">
    /// Controls whether CSS counters must be updated after switch to part. Counters must be updated only once.
    /// </param>
    /// <returns>
    /// A value indicating whether the resolver has been switched. In certain cases switching is impossible (for example,
    /// if the pseudo-element has no content and is thus invisible).
    /// </returns>
    /// <remarks>
    /// Technically, pseudo-elements are processed as normal child nodes of the current HTML element. However, their
    /// CSS declarations are resolved at the parent element's level, because pseudo-element selectors match the main HTML
    /// element. That is why we have to pre-resolve declarations of pseudo-elements and then use switching to process them.
    /// It is important that the resolver is switched in the correct order (i.e., ::before first and ::after last) and each
    /// part is processed no more than once, because pseudo-elements affect counters and violation of switch order will
    /// ruin numbering in the document.
    /// </remarks>
    boolean switchToPart(/*HtmlElementPart*/int part, boolean updateCounters)
    {
        // Pseudo-elements other than ::before and ::after are not supported yet and their influence on CSS counters
        // hasn't been investigated. That is the reason why they are not allowed in this method.
        Debug.assertTrue((part == HtmlElementPart.ELEMENT) ||
            (part == HtmlElementPart.BEFORE) ||
            (part == HtmlElementPart.AFTER));

        // Switch back from a pseudo-element to the main element if needed.
        if (getCurrentPart() != HtmlElementPart.ELEMENT)
        {
            popElementInternal();
        }

        // Remember the real HTML element that the requested pseudo-element belongs to.
        HtmlElementInfo realElementInfo = getCurrentElementInfo();

        // Switch from the main element to the requested pseudo-element.
        if (part != HtmlElementPart.ELEMENT)
        {
            CssDeclarationCollection pseudoElementDeclarations = getCurrentElementInfo().getDeclarations(part);

            CssDeclaration contentDeclaration = pseudoElementDeclarations.get("content");
            PseudoElementContent content = (contentDeclaration != null)
                ? PseudoElementContent.parse(contentDeclaration.getValue())
                : null;

            if (content != null)
            {
                // Create a pseudo-element node.
                IHtmlElementProvider pseudoElementNode = new HtmlElementNode("span", true);
                // Push the pseudo-element. The pseudo-element cannot have nested pseudo-elements.
                PseudoElementInfo elementInfo = new PseudoElementInfo(pseudoElementNode, pseudoElementDeclarations,
                    part, content);
                pushElementInternal(elementInfo);

                if (getElementDisplayState() == HtmlElementDisplayState.NONE)
                {
                    // The pseudo-element is invisible and we cannot switch the resolver to it.
                    popElementInternal();
                }
                else if (updateCounters)
                {
                    // The resolver has been switched to the requested pseudo-element. The pseudo-element is visible
                    // and it may affect counters.
                    updateCounters(pseudoElementDeclarations);

                    // The pseudo-element is considered a child of the corresponding real element.
                    realElementInfo.HasChildren = true;
                }
            }
        }

        return getCurrentPart() == part;
    }

    /// <summary>
    /// Gets declarations for a selector.
    /// </summary>
    /// <param name="selector">CSS selector.</param>
    /// <returns>The selector declarations; null, if selector not found.</returns>
    CssDeclarationCollection getSelectorDeclarations(CssSelector selector)
    {
        // SPEED - In order to speed up getting of CSS declarations by CSS selectors, we cache CSS selector values and
        // their corresponding CSS rule declarations.
        String selectorText = selector.toCss();

        if (mDeclarationsBySelector.containsKey(selectorText))
            return mDeclarationsBySelector.get(selectorText);
        else
            return null;
    }

    /// <summary>
    /// Gets textual representation of the current part's generated content.
    /// Only pseudo-elements can have generated content.
    /// </summary>
    /// <returns>
    /// A text with generated content of the current part or an empty string if the current part has no generated content.
    /// </returns>
    String getGeneratedContent()
    {
        PseudoElementContent content = getCurrentElementInfo().getGeneratedContent();
        if (content != null)
        {
            // The current element is a pseudo-element but we need to see attributes of the main element
            // (the parent of the pseudo-element) in order to generate pseudo-element's text.
            IHtmlElementProvider mainElement = getElementInfo(1).Element;
            return GeneratedContentTextBuilder.getText(content, mCounters, mainElement);
        }
        return "";
    }

    int getListLevelCount()
    {
        PseudoElementContent content = getCurrentElementInfo().getGeneratedContent();
        if (content != null)
        {
            // The current element is a pseudo-element but we need to see attributes of the main element
            // (the parent of the pseudo-element) in order to generate pseudo-element's text.
            IHtmlElementProvider mainElement = getElementInfo(1).Element;
            return GeneratedContentListLevelCounter.getListLevelCount(content, mCounters, mainElement);
        }

        return 0;
    }

    GeneratedContentListLabelInfo getListLabelInfo(int skipListLevelsCount, int currentListLevelNumber)
    {
        PseudoElementContent content = getCurrentElementInfo().getGeneratedContent();
        if (content != null)
        {
            // The current element is a pseudo-element but we need to see attributes of the main element
            // (the parent of the pseudo-element) in order to generate pseudo-element's text.
            IHtmlElementProvider mainElement = getElementInfo(1).Element;
            return GeneratedContentListLabelInfoCreator.getListLabelInfo(
                content,
                mCounters,
                mainElement,
                skipListLevelsCount,
                currentListLevelNumber);
        }

        return null;
    }

    /// <summary>
    /// Gets the current value of a counter. Corresponds to the 'counter' CSS function.
    /// </summary>
    int getCounterValue(String counterName)
    {
        return mCounters.getValue(counterName);
    }

    SizeD getEffectiveParentElementSize()
    {
        for (int i = mElements.size() - 2; i >= 0; i--)
        {
            HtmlElementInfo elementInfo = mElements.get(i);
            if (elementInfo.EffectiveSize != null)
                return elementInfo.EffectiveSize;
        }

        return null;
    }

    /*LineBreakClear*/int getLineBreakClear()
    {
        String clearPropertyValue = msString.toLowerInvariant(getElementDeclarations().getIdentifier("clear"));
        switch (gStringSwitchMap.of(clearPropertyValue))
        {
            case /*"left"*/0:
                return LineBreakClear.LEFT;
            case /*"right"*/1:
                return LineBreakClear.RIGHT;
            case /*"both"*/2:
                return LineBreakClear.ALL;
            default:
                // Ignore unknown values.
                return LineBreakClear.NONE;
        }
    }

    /// <summary>
    /// Gets CSS rules declared in the HTML document's stylesheet. />.
    /// </summary>
    Collection<CssStyleRule> getDocumentStyleRules() { return mStyleRules; }

    /// <summary>
    /// Gets CSS selectors that match for the current element.
    /// </summary>
    ArrayList<CssSelector> getCurrentElementSelectors() { return mCssResolver.getSelectors(); }

    boolean isEmpty() { return mElements.size() == 0; }

    /// <summary>
    /// Gets current HTML element CSS declarations. All returned declarations are guaranteed to be computed.
    /// </summary>
    CssDeclarationCollection getElementDeclarations() { return getCurrentElementInfo().getDeclarations(HtmlElementPart.ELEMENT); }

    /// <summary>
    /// Gets CSS declarations applied to the additional content inserted before the current HTML element
    /// (the ::before pseudo-element). All returned declarations are guaranteed to be computed.
    /// </summary>
    CssDeclarationCollection getBeforePseudoElementDeclarations() { return getCurrentElementInfo().getDeclarations(HtmlElementPart.BEFORE); }

    /// <summary>
    /// Gets an insertion revision for the current element. 
    /// Can be null, if the element doesn't have an insertion revision.
    /// </summary>
    EditRevision getElementInsertionRevision() { return mAttributeResolver.getInsertionRevision(); }

    /// <summary>
    /// Gets a deletion revision for the current element. 
    /// Can be null, if the element doesn't have a deletion revision.
    /// </summary>
    EditRevision getElementDeletionRevision() { return mAttributeResolver.getDeletionRevision(); }

    /// <summary>
    /// Gets current HTML element display state.
    /// </summary>
    /*HtmlElementDisplayState*/int getElementDisplayState() { return getCurrentElementInfo().DisplayState; }

    CssBoxModel getBoxModel() { return mBoxModel; }; private void setBoxModel(CssBoxModel value) { mBoxModel = value; };

    private CssBoxModel mBoxModel;

    /// <summary>
    /// Gets current HTML element.
    /// </summary>
    IHtmlElementProvider getCurrentElement() { return getCurrentElementInfo().Element; }

    /// <summary>
    /// Gets a value indicating whether the current HTML element has children (either real or pseudo-elements).
    /// </summary>
    /// <remarks>
    /// The value is valid only after the current HTML element is fully processed, including its ::before and ::after
    /// pseudo-elements.
    /// </remarks>
    boolean getCurrentElementHasChildren() { return getCurrentElementInfo().HasChildren; }

    /// <summary>
    /// Gets current HTML element info.
    /// </summary>
    HtmlElementInfo getCurrentElementInfo()
    {
        HtmlElementInfo result = getElementInfo(0);
        Debug.assertTrue(result != null);
        return result;
    }

    /// <summary>
    /// Gets current HTML element "display" property value.
    /// </summary>
    /*CssDisplayType*/int getElementDisplayType() { return getCurrentElementInfo().DisplayType; }

    CssTextDecoration getTextDecoration() { return mTextDecorationsStack.peek(); }

    /// <summary>
    /// Inline-level border of the current element.
    /// </summary>
    /// <remarks>
    /// Only box borders (having all four sides: top, right, bottom, and left) are supported on inline level.
    /// Inline-level borders are propagated from parents to children.
    /// </remarks>
    CssBorder getInlineBorder() { return mInlineBorderStack.peek(); }

    ElementBackgroundInfo getBackground() { return mBackgroundResolver.getCurrentElementBackground(); }

    CssVerticalAlign getSubSupScript() { return mSupSubScriptResolver.getCurrentElementInfo(); }

    int getLocaleId() { return mAttributeResolver.getLocaleId(); }

    int getLocaleIdBi() { return mAttributeResolver.getLocaleIdBi(); }

    int getLocaleIdFarEast() { return mAttributeResolver.getLocaleIdFarEast(); }

    private void calculatePredefinedStyleDeclarations()
    {
        // Check if formatting of any built-in styles is specified using rules with "-aw-style-name" declarations.
        for (CssStyleRule rule : mStyleRules)
        {
            CssDeclaration styleNameDeclaration = rule.getDeclarations().get(HtmlConstants.STYLE_NAME);
            if (styleNameDeclaration != null)
            {
                /*StyleIdentifier*/int styleIdentifier = CssDocumentStyleNames.cssStyleNameToStyleIdentifier(styleNameDeclaration.getValue());
                if (styleIdentifier != StyleIdentifier.NIL)
                {
                    mPredefinedStyleDeclarations.set((int)styleIdentifier, mComputedDeclarationResolver.resolveToComputed(rule.getDeclarations()));
                }
            }
        }

        // h1..h6 document styles.
        Debug.assertTrue((int)StyleIdentifier.HEADING_1 == 1);
        Debug.assertTrue((int)StyleIdentifier.HEADING_6 == 6);
        for (/*StyleIdentifier*/int sti = StyleIdentifier.HEADING_1; sti <= StyleIdentifier.HEADING_6; sti++)
        {
            // Skip built-in styles whose formatting is specified via rules with "-aw-style-name" declarations.
            if (mPredefinedStyleDeclarations.containsKey((int)sti))
                continue;

            CssDeclarationCollectionBuilder styleDeclarationsBuilder = new CssDeclarationCollectionBuilder();

            CssDeclarationCollection defaultDeclarations = getDefaultDeclarations(msString.format("h{0}", (int)sti));
            styleDeclarationsBuilder.addOrReplace(defaultDeclarations);

            String headingElementName = "h" + FormatterPal.intToStr((int)sti);
            CssDeclarationCollection selectorDeclarations = getSelectorDeclarations(CssSelector.element(headingElementName));
            if (selectorDeclarations != null)
            {
                styleDeclarationsBuilder.addOrReplace(selectorDeclarations);
            }

            mPredefinedStyleDeclarations.set((int)sti, styleDeclarationsBuilder.getDeclarations());
        }

        // If formatting of the "Normal" style is not specified via a rule with a "-aw-style-name" declaration.
        if (!mPredefinedStyleDeclarations.containsKey((int)StyleIdentifier.NORMAL))
        {
            // "html" and "body" should affect the "Normal" style even when the HTML document uses inline CSS. In order to make
            // this happen we fill the "style" attribute values of the mock elements with inline CSS taken from real elements.

            MockHtmlElementProvider elementHtml = MockHtmlElementProvider.create("html");
            elementHtml.addAttribute("style", mHtmlElementInlineCssStyle);
            mCssResolver.pushElement(elementHtml);

            MockHtmlElementProvider elementBody = MockHtmlElementProvider.create("body", elementHtml);
            elementBody.addAttribute("style", mBodyElementInlineCssStyle);
            mCssResolver.pushElement(elementBody);

            CssDeclarationCollection bodyDeclarations = mCssResolver.getDeclarations();

            CssDeclarationCollectionBuilder normalDeclarationsBuilder = new CssDeclarationCollectionBuilder(bodyDeclarations);
            normalDeclarationsBuilder.remove("background-color");

            mPredefinedStyleDeclarations.set((int)StyleIdentifier.NORMAL, normalDeclarationsBuilder.getDeclarations());

            mCssResolver.popElement();
            mCssResolver.popElement();
        }
    }

    private void pushElementInternal(HtmlElementInfo elementInfo)
    {
        IHtmlElementProvider element = elementInfo.Element;

        // It is a logical error to have an implicit element inside another implicit element.
        if (mElements.size() > 0)
        {
            Debug.assertTrue((!getCurrentElement().isImplicit()) || (!element.isImplicit()));
        }

        // Determine whether the the element contains other elements or text nodes.
        elementInfo.HasChildren = (element.getFirstChildElement() != null) || (element.getInnerText().length() > 0);

        // Common info computing.
        elementInfo.IsBlockLevelElement = mElementCategorizer.isBlockLevelElement(element, elementInfo.getDeclarations()) ||
            ("body".equals(element.getElementName()));
        elementInfo.DisplayType = mElementCategorizer.getDisplayType(elementInfo.getDeclarations());

        elementInfo.ParentBlockElement = getLastBlockElementInfo();
        // Direction computing.
        if (elementInfo.IsBlockLevelElement)
            elementInfo.BlockLevelDirection = CssUtil.getDirection(elementInfo.getDeclarations());
        else if (elementInfo.ParentBlockElement != null)
            elementInfo.BlockLevelDirection = CssUtil.getDirection(elementInfo.ParentBlockElement.getDeclarations());

        elementInfo.InlineLevelDirection = CssUtil.getDirection(elementInfo.getDeclarations());

        boolean createsHtmlBlock = mUseHtmlBlocks &&
            CssUtil.createsHtmlBlock(elementInfo.Element.getElementName(), elementInfo.getDeclarations(), mResolveFormattingAsMsWord);

        // Indents and spacings computing. 
        if (!element.isImplicit())
        {
            /*CssDisplayType*/int parentDisplayType = isEmpty()
                ? CssDisplayType.NONE
                : getCurrentElementInfo().DisplayType;
            getBoxModel().push(elementInfo.Element, elementInfo.getDeclarations(), elementInfo.DisplayType, parentDisplayType, createsHtmlBlock);
        }

        // Display state computing.
        /*HtmlElementDisplayState*/int displayState = (mElements.size() != 0)
            ? getElementDisplayState()
            : HtmlElementDisplayState.VISIBLE;

        if ((displayState != HtmlElementDisplayState.NONE) && (elementInfo.DisplayType == CssDisplayType.NONE))
        {
            displayState = HtmlElementDisplayState.NONE;
        }
        if (displayState != HtmlElementDisplayState.NONE)
        {
            CssDeclaration visibilityDeclaration = elementInfo.getDeclarations().get("visibility");
            if (visibilityDeclaration != null)
            {
                // The support for visibility:collapse is missing or partially incorrect in some modern browsers. We ignore it too.
                if (visibilityDeclaration.getValue().equals(CssValue.Hidden) ||
                    visibilityDeclaration.getValue().equals(CssValue.Collapse))
                {
                    displayState = HtmlElementDisplayState.HIDDEN;
                }
                else if (visibilityDeclaration.getValue().equals(CssValue.Visible))
                {
                    displayState = HtmlElementDisplayState.VISIBLE;
                }
            }
        }
        elementInfo.DisplayState = displayState;

        readElementSize(elementInfo);

        mAttributeResolver.pushElement(element, elementInfo.getDeclarations());

        msArrayList.add(mElements, elementInfo);

        recalculateActiveBidiLevels(getCurrentElementInfo(), true);

        // Text decoration computing.
        computeCurrentTextDecoration();

        computeInlineBorder(createsHtmlBlock);

        mBackgroundResolver.pushBackground(getCurrentElementInfo());

        mSupSubScriptResolver.push(elementInfo);

        // Create a new counter nesting level for the element we've just pushed.
        mCounters.increaseNesting();
    }

    private static void readElementSize(HtmlElementInfo elementInfo)
    {
        double width = elementInfo.getDeclarations().getLength("width");
        double minWidth = elementInfo.getDeclarations().getLength("min-width");
        double height = elementInfo.getDeclarations().getLength("height");
        double minHeight = elementInfo.getDeclarations().getLength("min-height");

        double effectiveWidth = Math.max(minWidth, width);
        double effectiveHeight = Math.max(minHeight, height);

        if (!MathUtil.isMinValue(effectiveWidth) && !MathUtil.isMinValue(effectiveHeight))
            elementInfo.EffectiveSize = new SizeD(effectiveWidth, effectiveHeight);
    }

    private void popElementInternal()
    {
        Debug.assertTrue(mElements.size() != 0);
        if (!getCurrentElement().isImplicit())
            getBoxModel().pop();

        recalculateActiveBidiLevels(getCurrentElementInfo(), false);

        // Pseudo-elements are not pushed to the CSS rosolver because their declarations are resolved together with
        // declarations of main elements. That is why pseudo-elements are skipped from this step.
        if (getCurrentPart() == HtmlElementPart.ELEMENT)
        {
            mCssResolver.popElement();
        }

        mElements.remove(mElements.size() - 1);
        mAttributeResolver.popElement();
        mTextDecorationsStack.pop();
        mInlineBorderStack.pop();
        mBackgroundResolver.popBackground();
        mSupSubScriptResolver.pop();
        mCounters.decreaseNesting();
    }

    /// <summary>
    /// Corrects CSS style of IMG elements that are inside absolutely positioned SPANs to make images absolutely positioned.
    /// </summary>
    /// <remarks>
    /// MS Word exports a floating image as an absolutely positioned span with an inline image inside. Aspose.Words does
    /// not support absolutely positioned spans but it do support absolutely positioned images. This method copies certain
    /// CSS properties from the span to the image to make the image absolutely positioned and suitable for HTML import.
    /// </remarks>
    private CssDeclarationCollection correctStyleOfImagesInsidePositionedSpans(
        IHtmlElementProvider element,
        CssDeclarationCollection declarations)
    {
        // The recognized constructions are:
        //
        //  <span style="position:absolute;..."><img src="..."></span>
        //
        // and
        //
        //  <span style="position:absolute;..."><a href="..."><img src="..."></a></span>
        //
        // Whitespace (but not meaningful text or other elements) is allowed between any of the elements.

        Debug.assertTrue("img".equals(element.getElementName()));

        CssDeclarationCollection result = declarations;
        HtmlElementInfo parentInfo = getElementInfo(0);

        // An optional anchor element.
        if ((parentInfo != null) &&
            ("a".equals(parentInfo.Element.getElementName())) &&
            parentInfo.Element.hasOnlyOneChildElementWithOptionalWhitespace())
        {
            parentInfo = getElementInfo(1);
        }

        // An absolutely positioned span element.
        if ((parentInfo != null) &&
            ("span".equals(parentInfo.Element.getElementName())) &&
            parentInfo.Element.hasOnlyOneChildElementWithOptionalWhitespace())
        {
            CssDeclaration positionDeclaration = parentInfo.getDeclarations().get("position");
            if ((positionDeclaration != null) && positionDeclaration.getValue().equals(CssValue.Absolute))
            {
                CssDeclarationCollectionBuilder correctedDeclarations = new CssDeclarationCollectionBuilder(declarations);
                correctedDeclarations.addIfNotNullAndNotExist(positionDeclaration);
                correctedDeclarations.addIfNotNullAndNotExist(parentInfo.getDeclarations().get("z-index"));
                correctedDeclarations.addIfNotNullAndNotExist(parentInfo.getDeclarations().get("margin-left"));
                correctedDeclarations.addIfNotNullAndNotExist(parentInfo.getDeclarations().get("margin-right"));
                correctedDeclarations.addIfNotNullAndNotExist(parentInfo.getDeclarations().get("margin-top"));
                correctedDeclarations.addIfNotNullAndNotExist(parentInfo.getDeclarations().get("left"));
                result = correctedDeclarations.getDeclarations();
            }
        }
        return result;
    }

    /// <summary>
    /// Determines whether the current element is directly mapped to a model run. 
    /// </summary>
    private boolean isMappedToRunElement()
    {
        return isMappedToParaOrRunElement(getCurrentElement()) && !isBlockLevelElement();
    }

    /// <summary>
    /// Determines whether the curent element is directly mapped to a model paragraph.
    /// </summary>
    private boolean isMappedToParagraphElement()
    {
        return isMappedToParaOrRunElement(getCurrentElement()) && isBlockLevelElement();
    }

    /// <summary>
    /// Determines whether the element is mapped to a model paragraph or run. 
    /// </summary>
    private static boolean isMappedToParaOrRunElement(IHtmlElementProvider element)
    {
        switch (gStringSwitchMap.of(element.getElementName()))
        {
            case /*"div"*/3:
            case /*"li"*/4:
            case /*"p"*/5:
            case /*"h1"*/6:
            case /*"h2"*/7:
            case /*"h3"*/8:
            case /*"h4"*/9:
            case /*"h5"*/10:
            case /*"h6"*/11:
            case /*"center"*/12:
            case /*"pre"*/13:
            case /*"blockquote"*/14:
            case /*"a"*/15:
            case /*"b"*/16:
            case /*"big"*/17:
            case /*"em"*/18:
            case /*"i"*/19:
            case /*"s"*/20:
            case /*"small"*/21:
            case /*"strike"*/22:
            case /*"strong"*/23:
            case /*"sub"*/24:
            case /*"sup"*/25:
            case /*"tt"*/26:
            case /*"u"*/27:
            case /*"span"*/28:
            case /*"font"*/29:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Gets last block-level element info stored in the element collection.
    /// </summary>
    /// <returns>Last block-level element info; null if there is no elements in the collections.</returns>
    private HtmlElementInfo getLastBlockElementInfo()
    {
        HtmlElementInfo blockElementInfo = null;
        if (mElements.size() > 0)
        {
            HtmlElementInfo lastElementInfo = mElements.get(mElements.size() - 1);
            blockElementInfo = lastElementInfo.IsBlockLevelElement
                ? lastElementInfo
                : lastElementInfo.ParentBlockElement;
        }

        return blockElementInfo;
    }

    /// <summary>
    /// Computes text decoration for the current element.
    /// </summary>
    /// <remarks>
    /// Implemented rules:
    /// 'text-decoration' property is not inherited but is propagated from ancestor to descendant elements.
    /// 'text-decoration' property on descendant elements cannot have any effect on the decoration of the ancestor.
    /// 'text-decoration' property is not propagated to floating elements.
    /// Other rules need to be investigated or are browser-specific and are not implemented yet.
    /// http://www.w3.org/TR/CSS21/text.html#propdef-text-decoration
    /// </remarks>
    private void computeCurrentTextDecoration()
    {
        CssTextDecoration textDecoration = ((mTextDecorationsStack.getCount() > 0) && (getCurrentElementInfo().getDeclarations().get("float") == null))
            ? mTextDecorationsStack.peek()
            : new CssTextDecoration();

        // In MS Word, 'text-decoration: none' clears all propagated decorations.
        textDecoration = textDecoration.apply(getCurrentElementInfo().getDeclarations(), mResolveFormattingAsMsWord);

        mTextDecorationsStack.push(textDecoration);
    }

    /// <summary>
    /// Computes and pushes the inline-level border of the current element.
    /// </summary>
    private void computeInlineBorder(boolean createsHtmlBlock)
    {
        // WORDSNET-21063 Discard the inline border for an element for which the HTML block was created.
        if (getCurrentElementInfo().IsBlockLevelElement || createsHtmlBlock)
        {
            mInlineBorderStack.push(CssBorder.getEmpty());
        }
        else
        {
            CssBorder inlineBorder = CssBorder.createBoxBorder(getCurrentElementInfo().getDeclarations());
            if (inlineBorder.isUndefined() && (mInlineBorderStack.getCount() > 0))
            {
                // In case no supported border is specified on this element, propagate the inline border from its parent.
                inlineBorder = mInlineBorderStack.peek();
            }
            mInlineBorderStack.push(inlineBorder);
        }
    }

    /// <summary>
    /// Gets HTML info of an element at the specified zero-based depth (0 - current element; 1 - its parent, and so on).
    /// </summary>
    /// <returns>
    /// HTML info of an element at the specified depth or <c>null</c>, if no element is found (depth is out of range).
    /// </returns>
    private HtmlElementInfo getElementInfo(int depth)
    {
        int index = mElements.size() - depth - 1;
        return ((index >= 0) && (index < mElements.size()))
            ? mElements.get(index)
            : null;
    }

    /// <summary>
    /// Recalculates opened bidi levels when an HTML element is added or removed.
    /// </summary>
    private void recalculateActiveBidiLevels(HtmlElementInfo elementInfo, boolean isAdded)
    {
        /*CssUnicodeBidi*/int unicodeBidi = CssUtil.getUnicodeBidi(elementInfo.getDeclarations(), elementInfo.IsBlockLevelElement);
        if (unicodeBidi == CssUnicodeBidi.EMBED)
        {
            if (isAdded)
            {
                if (elementInfo.InlineLevelDirection == CssDirection.RTL)
                {
                    mActiveBidiLevels.add((int)BidiLevel.EMBED_RTL);
                }
                else
                {
                    mActiveBidiLevels.add((int)BidiLevel.EMBED_LTR);
                }
            }
            else
            {
                Debug.assertTrue(mActiveBidiLevels.getCount() > 0);
                mActiveBidiLevels.removeAt(mActiveBidiLevels.getCount() - 1);
            }
        }
    }

    /// <summary>
    /// Modifies CSS counters according to the specified CSS declarations.
    /// </summary>
    private void updateCounters(CssDeclarationCollection elementDeclarations)
    {
        CssCounterModifications modifications = CssCounterModifications.parse(elementDeclarations);
        modifications.applyTo(mCounters);
    }

    private /*HtmlElementPart*/int getCurrentPart()
    {
        HtmlElementInfo info = getElementInfo(0);
        return (info != null)
            ? info.getPart()
            : HtmlElementPart.ELEMENT;
    }

    /// <summary>
    /// Returns CSS declarations of the all suitable page rules.
    /// </summary>
    CssDeclarationCollection getAllPageDeclarations()
    {
        if (isEmpty())
        {
            return CssDeclarationCollection.EMPTY;
        }

        String pageName = getElementDeclarations().getIdentifier("page");

        ArrayList<CssPageRule> suitableRules = new ArrayList<CssPageRule>();
        for (CssPageRule rule : mPageRules)
        {
            if (rule.getSelector().matches(pageName))
            {
                msArrayList.add(suitableRules, rule);
            }
        }
        if (suitableRules.size() == 0)
            return CssDeclarationCollection.EMPTY;

        if (!StringUtil.hasChars(pageName) || (suitableRules.size() == 1))
            return mComputedDeclarationResolver.resolveToComputed(suitableRules.get(0).getDeclarations());

        // Here we have several suitable rules. Some rules are common (applied to all pages) and others are specific and are
        // applied only to pages that have the specified name.
        // We should combine the CSS declarations from common and specific rules.
        CssDeclarationCollectionBuilder commonDeclarations = new CssDeclarationCollectionBuilder();
        CssDeclarationCollectionBuilder otherDeclarations = new CssDeclarationCollectionBuilder();
        for (CssPageRule rule : suitableRules)
        {
            if (rule.getSelector().hasPageNames())
            {
                otherDeclarations.addOrReplace(rule.getDeclarations());
            }
            else
            {
                commonDeclarations.addOrReplace(rule.getDeclarations());
            }
        }
        commonDeclarations.addOrReplace(otherDeclarations.getDeclarations());

        return mComputedDeclarationResolver.resolveToComputed(commonDeclarations.getDeclarations());
    }

    CssDeclaration parentTextAlign()
    {
        return (mElements.size() > 1)
            ? mElements.get(mElements.size() - 2).getDeclarations().get("text-align")
            : null;
    }

    private CssDeclarationCollection getDefaultDeclarations(String elementName)
    {
        IHtmlElementProvider element = new MockHtmlElementProvider(elementName);
        CssDeclarationCollection declarations = mDefaultElementStyleResolver.getDeclarations(element);
        return mComputedDeclarationResolver.resolveToComputed(declarations);
    }

    /// <summary>
    /// Creates dictionary to map CSS selector value to CSS rule declarations.
    /// </summary>
    private void fillSelectorToComputedDeclarationsMap()
    {
        // Collect declarations by selectors.
        HashMap<String, CssDeclarationCollectionBuilder> declarationBuilderBySelector =
            new HashMap<String, CssDeclarationCollectionBuilder>();
        for (CssStyleRule rule : mStyleRules)
        {
            for (CssSelector ruleSelector : rule.getSelectors())
            {
                String cssSelector = ruleSelector.toCss();
                CssDeclarationCollectionBuilder declarationBuilder = null;
                Ref<CssDeclarationCollectionBuilder> referenceToDeclarationBuilder = new Ref<CssDeclarationCollectionBuilder>(declarationBuilder);
                boolean outRefCondition0 = !msDictionary.tryGetValue(declarationBuilderBySelector, cssSelector, /*out*/ referenceToDeclarationBuilder);
                declarationBuilder = referenceToDeclarationBuilder.get();
                if (outRefCondition0)
                {
                    declarationBuilder = new CssDeclarationCollectionBuilder();
                    msDictionary.add(declarationBuilderBySelector, cssSelector, declarationBuilder);
                }
                // When combining declarations from multiple CSS rules, we must take their importance into account.
                declarationBuilder.addOrReplaceIfMoreOrEquallyImportant(rule.getDeclarations());
            }
        }

        // Compute and cache collected declarations.
        mDeclarationsBySelector.clear();
        for (Map.Entry<String, CssDeclarationCollectionBuilder> selectorAndBuilder : declarationBuilderBySelector.entrySet())
        {
            String selector = selectorAndBuilder.getKey();
            CssDeclarationCollectionBuilder declarationBuilder = selectorAndBuilder.getValue();

            CssDeclarationCollection declarations = declarationBuilder.getDeclarations();
            declarations = mComputedDeclarationResolver.resolveToComputed(declarations);

            msDictionary.add(mDeclarationsBySelector, selector, declarations);
        }
    }

    /// <summary>
    /// Applies floating to a non-floating image that is a single child of one or more floating parent containers.
    /// </summary>
    private CssDeclarationCollection correctStyleOfImagesInsideFloatingParents(
        IHtmlElementProvider element,
        CssDeclarationCollection declarations)
    {
        Debug.assertTrue("img".equals(element.getElementName()));

        CssDeclarationCollection result = declarations;
        if (declarations.get("float") == null)
        {
            CssDeclaration parentFloatDeclaration = getParentWithOnlyChildFloatDeclaration();
            if (parentFloatDeclaration != null)
            {
                CssDeclarationCollectionBuilder correctedDeclarations = new CssDeclarationCollectionBuilder(declarations);
                correctedDeclarations.add(parentFloatDeclaration);
                result = correctedDeclarations.getDeclarations();
            }
        }

        return result;
    }

    /// <summary>
    /// Returns the nearest ancestor's float CSS declaration.
    /// </summary>
    private CssDeclaration getParentWithOnlyChildFloatDeclaration()
    {
        // Discard `html` and `body` tags because they aren't affecting the floating.
        for (int i = mElements.size() - 1; i >= 2; i--)
        {
            HtmlElementInfo element = mElements.get(i);
            // The ancestor must have the only child because floating should apply on a single shape.
            if (element.Element.getChildElements().length > 1)
                return null;

            CssDeclaration floatDeclaration = element.getDeclarations().get("float");
            if (floatDeclaration != null)
                return floatDeclaration;
        }
        return null;
    }

    private /*final*/ ArrayList<CssStyleRule> mStyleRules;
    private /*final*/ ArrayList<CssPageRule> mPageRules;
    private /*final*/ String mHtmlElementInlineCssStyle;
    private /*final*/ String mBodyElementInlineCssStyle;
    private /*final*/ CssResolver mCssResolver;
    private /*final*/ HtmlElementCategorizer mElementCategorizer;
    private /*final*/ HtmlAttributeResolver mAttributeResolver;
    private /*final*/ DefaultElementStyleResolver mDefaultElementStyleResolver;
    private /*final*/ CssComputedDeclarationResolver mComputedDeclarationResolver;

    /// <summary>
    /// Currently opened parent HTML elements, from outermost to innermost. 
    /// Items are instances of <see cref="HtmlElementInfo"/>.
    /// </summary>
    private /*final*/ ArrayList<HtmlElementInfo> mElements;

    /// <summary>
    /// Text decoration of all currently opened parent HTML elements, from outermost to innermost.
    /// Items are instances of <see cref="CssTextDecoration"/>.
    /// </summary>
    private /*final*/ msStack<CssTextDecoration> mTextDecorationsStack;

    /// <summary>
    /// Inline-level borders of currently opened elements.
    /// </summary>
    private /*final*/ msStack<CssBorder> mInlineBorderStack;
    private /*final*/ BackgroundResolver mBackgroundResolver;

    /// <summary>
    /// Currently opened bidi levels. From outermost to innermost. Items are <see cref="BidiLevel"/> values.
    /// </summary>
    private /*final*/ IntList mActiveBidiLevels;

    private /*final*/ SupSubScriptResolver mSupSubScriptResolver;

    private /*final*/ CssCounters mCounters;

    /// <summary>
    /// CSS declarations that are applied to certain built-in styles.
    /// Key is <see cref="StyleIdentifier"/> casted to <c>int</c>. Value is <see cref="CssDeclarationCollection"/>;
    /// </summary>
    private /*final*/ IntToObjDictionary<CssDeclarationCollection> mPredefinedStyleDeclarations =
        new IntToObjDictionary<CssDeclarationCollection>();

    /// <summary>
    /// Indicates whether we are currently on he current implicit element (div or span).
    /// </summary>
    private boolean mIsOnImplicitElement;

    /// <summary>
    /// A real HTML element to return to after we finish processing an implicit element. 
    /// <c>null</c> if we shall return to a pseudo-element.
    /// </summary>
    private IHtmlElementProvider mReturnToElement;

    /// <summary>
    /// A pseudo-element to return to after we finish processing the current implicit element. 
    /// <see cref="HtmlElementPart.Element"/> if we shall return to a real element.
    /// </summary>
    private /*HtmlElementPart*/int mReturnToPart;

    /// <summary>
    /// Indicates whether we should stick to MS Word's rules when resolving CSS formatting.
    /// </summary>
    private /*final*/ boolean mResolveFormattingAsMsWord;

    /// <summary>
    /// Computed CSS declarations that are matched to certain CSS selector.
    /// </summary>
    /// <remarks>
    /// Key is CSS selector value. Value - collection of computed CSS declarations.
    /// </remarks>
    private /*final*/ HashMap<String, CssDeclarationCollection> mDeclarationsBySelector =
        new HashMap<String, CssDeclarationCollection>();

    /// <summary>
    /// Indicates whether to mimic MS Word's behavior and use <see cref="HtmlBlock"/> nodes to preserve borders and margins
    /// of block-level elements like BODY, DIV, or BLOCKQUOTE.
    /// </summary>
    private /*final*/ boolean mUseHtmlBlocks;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"left",
		"right",
		"both",
		"div",
		"li",
		"p",
		"h1",
		"h2",
		"h3",
		"h4",
		"h5",
		"h6",
		"center",
		"pre",
		"blockquote",
		"a",
		"b",
		"big",
		"em",
		"i",
		"s",
		"small",
		"strike",
		"strong",
		"sub",
		"sup",
		"tt",
		"u",
		"span",
		"font"
	);

}

