// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/12/2016 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import com.aspose.ms.System.msObject;


/// <summary>
/// Gets and formats appropriate model styles when CSS formatting is applied to the document model. to paragraphs.
/// Uses formatting rules that are different from what MS Word uses but produce better looking results
/// and are more confomant to the CSS and HTML specifications.
/// </summary>
class StyleFormatterHtmlRules extends StyleFormatter
{
    StyleFormatterHtmlRules(
        Document document,
        CssStyleTracker cssStyleTracker,
        int defaultParagraphIstd,
        int defaultFontIstd,
        FontFormatter fontFormatter,
        ParagraphFormatter paragraphFormatter)
    {
    	super(document, cssStyleTracker, paragraphFormatter, fontFormatter);
	
        mSelectorStyleCache = new HtmlSelectorStyleCache();

        mDefaultParagraphIstd = defaultParagraphIstd;
        mDefaultFontIstd = defaultFontIstd;
    }

    /// <summary>
    /// Computes default CSS styles for the given HTML document and applies this CSS to the predefined styles.
    /// Predefined styles are not created explicitly, just updated with CSS information.
    /// </summary>
    /*override*/ void updatePredefinedStyles() throws Exception
    {
        // Heading styles.
        // Note that there are 9 built-in heading styles in MS Word but only 6 heading elements in HTML (h1..h6).
        // However, HTML documents can define formating of headings 7..9 using the "-aw-style-name" property.
        Debug.assertTrue((int)StyleIdentifier.HEADING_1 == 1);
        Debug.assertTrue((int)StyleIdentifier.HEADING_9 == 9);
        for (/*StyleIdentifier*/int sti = StyleIdentifier.HEADING_1; sti <= StyleIdentifier.HEADING_9; sti++)
        {
            CssDeclarationCollection headingStyleDeclarations = CssStyleTracker.getPredefinedStyleDeclarations(sti);
            if (headingStyleDeclarations != null)
            {
                Style style = Document.getStyles().getBySti(sti, true);
                format(style, headingStyleDeclarations);

                // WORDSNET-9026 Use default font name for heading styles.
                // FOSS: font substitution was removed; the default substitution font name was "Times New Roman".
                if (headingStyleDeclarations.get("font-family") == null)
                    style.getFont().setName("Times New Roman");
                // WORDSNET-8693 By default, <h2> and <h5> elements use normal font, but 'Heading 2' and 'Heading 5' use italic font.
                if (headingStyleDeclarations.get("font-style") == null)
                    style.getFont().setItalic(false);
            }
        }

        // WORDSNET-12924 MS Word sets kerning to 18pt on the 'Heading 1' style and so do we. Otherwise text imported
        // from <h1> elements gets longer and may wrap inappropriately.
        // Note that we don't want to create the 'Heading 1' style here unless it's explicitly declared in the document's
        // stylesheet.
        Style heading1Style = Document.getStyles().getBySti(StyleIdentifier.HEADING_1, false);
        if (heading1Style != null)
        {
            heading1Style.getFont().setKerning(18.0);
        }

        // Normal style.
        updatePredefinedStyle(StyleIdentifier.NORMAL);

        // Supported character styles.
        for (/*StyleIdentifier*/int styleIdentifier : CssDocumentStyleNames.getCharacterStyleIdentifiers())
        {
            updatePredefinedStyle(styleIdentifier);
        }
    }

    /// <summary>
    /// Returns a style for the current HTML element. Can return null.
    /// </summary>
    /*override*/ Style getModelStyle(/*StyleType*/int styleType) throws Exception
    {
        mStyleWithoutDeclarations = null;

        CssDeclaration styleNameDeclaration = CssStyleTracker.getElementDeclarations().get(HtmlConstants.STYLE_NAME);
        if (styleNameDeclaration != null)
        {
            Style styleByStyleName = getStyleByStyleNameDeclaration(styleNameDeclaration, styleType);
            if (styleByStyleName != null)
            {
                return styleByStyleName;
            }
        }

        CssSelector preferableSelector = null;
        for (CssSelector selector : CssStyleTracker.getCurrentElementSelectors())
        {
            if ((preferableSelector == null) || (selector.getSpecificity().compareTo(preferableSelector.getSpecificity()) == 1))
                preferableSelector = selector;
        }
        if (preferableSelector != null)
        {
            return getStyleBySelector(preferableSelector, styleType);
        }

        String elementName = CssStyleTracker.getCurrentElement().getElementName();
        String classValue = CssStyleTracker.getCurrentElement().getAttributeValue("class", "");
        if ((("p".equals(elementName)) || ("span".equals(elementName)) || ("table".equals(elementName))) && (!msString.equals(classValue, "")))
        {
            Style style = getStyleByClassAttribute(classValue, styleType);
            mStyleWithoutDeclarations = style;
            return style;
        }

        String hrefAttributeValue = CssStyleTracker.getCurrentElement().getAttributeValue("href");
        return getStyleByElement(
            CssStyleTracker.getCurrentElement(),
            hrefAttributeValue,
            styleType);
    }

    /// <summary>
    /// Gets style which is specified as element's class but has no corresponding declarations;
    /// </summary>
    /*override*/ Style getStyleWithoutDeclarations() { return mStyleWithoutDeclarations; }

    private void updatePredefinedStyle(/*StyleIdentifier*/int styleIdentifier) throws Exception
    {
        CssDeclarationCollection declarations = CssStyleTracker.getPredefinedStyleDeclarations(styleIdentifier);
        if (declarations != null)
        {
            Style style = Document.getStyles().getBySti(styleIdentifier, true);
            format(style, declarations);
        }
    }

    private Style getStyleByStyleNameDeclaration(CssDeclaration styleNameDeclaration, /*StyleType*/int styleType)
    {
        /*StyleIdentifier*/int styleIdentifier = CssDocumentStyleNames.cssStyleNameToStyleIdentifier(styleNameDeclaration.getValue());
        // The type of the specified style must match the requested style type. It is possible, for example, to specify
        // a character style (say, "-aw-style-name:hyperlink") for a HTML paragraph, and we want to ignore such situations.
        // This also handles situations where the value of "-aw-style-name" has not been recognized and the style identifier
        // is nil.
        return CssDocumentStyleNames.validateStyleType(styleIdentifier, styleType)
            ? Document.getStyles().getBySti(styleIdentifier, true)
            : null;
    }

    private Style getStyleByElement(
        IHtmlElementProvider element,
        String hrefAttributeValue,
        /*StyleType*/int styleType)
    {
        /*StyleIdentifier*/int styleIdentifier = StyleIdentifier.NIL;
        if (styleType == StyleType.PARAGRAPH)
        {
            switch (gStringSwitchMap.of(msString.toLowerInvariant(element.getElementName())))
            {
                case /*"h1"*/0:
                    styleIdentifier = StyleIdentifier.HEADING_1;
                    break;
                case /*"h2"*/1:
                    styleIdentifier = StyleIdentifier.HEADING_2;
                    break;
                case /*"h3"*/2:
                    styleIdentifier = StyleIdentifier.HEADING_3;
                    break;
                case /*"h4"*/3:
                    styleIdentifier = StyleIdentifier.HEADING_4;
                    break;
                case /*"h5"*/4:
                    styleIdentifier = StyleIdentifier.HEADING_5;
                    break;
                case /*"h6"*/5:
                    styleIdentifier = StyleIdentifier.HEADING_6;
                    break;
                default:
                    // Ignore.
                    break;
            }
        }

        // WORDSNET-27579 Map <a>, <em>, and <strong> to built-in character styles.
        if (styleType == StyleType.CHARACTER)
        {
            switch (gStringSwitchMap.of(msString.toLowerInvariant(element.getElementName())))
            {
                case /*"a"*/6:
                    // In HTML, an <a> element is a hyperlink only if it has a non-empty "href" attribute.
                    if (StringUtil.hasChars(hrefAttributeValue))
                    {
                        styleIdentifier = StyleIdentifier.HYPERLINK;
                    }
                    break;
                case /*"em"*/7:
                    styleIdentifier = StyleIdentifier.EMPHASIS;
                    break;
                case /*"strong"*/8:
                    styleIdentifier = StyleIdentifier.STRONG;
                    break;
                default:
                    // Ignore.
                    break;
            }
        }

        // Return a built-in style. Create it if needed.
        if (styleIdentifier != StyleIdentifier.NIL)
        {
            return Document.getStyles().getBySti(styleIdentifier, true);
        }

        // In MS Word if inline element doesn't have its own style it inherits style from parent span element.
        if (styleType == StyleType.CHARACTER)
        {
            Style parentCharacterStyle = getParentCharacterStyle();
            if (parentCharacterStyle != null)
            {
                return parentCharacterStyle;
            }
        }

        // Return a default style. Create it if needed.
        int istd = getDefaultStyleIstd(styleType);
        return (istd != StyleIndex.NIL)
            ? Document.getStyles().getByIstd(istd, true)
            : null;
    }

    /// <summary>
    /// Gets document style of the specified type for the selector.
    /// </summary>
    /// <param name="selector">CSS selector.</param>
    /// <param name="styleType">Document style type.</param>
    /// <returns>
    /// New or existing document style; null, if style isn't defined or there are too many styles in the document.
    /// </returns>
    private Style getStyleBySelector(CssSelector selector, /*StyleType*/int styleType) throws Exception
    {
        Style style = mSelectorStyleCache.getStyle(selector, styleType);
        if (style == null)
        {
            style = getStyleBySelectorUncached(selector, styleType);
            mSelectorStyleCache.addStyle(selector, styleType, style);
        }

        // A value returned from cache may be a substituted null style.
        if (mSelectorStyleCache.isNullSubstitute(style))
        {
            style = null;
        }

        Debug.assertTrue((style == null) || (style.getDocument() == Document));
        return style;
    }

    /// <summary>
    /// Gets document style of the specified type for the selector.
    /// </summary>
    /// <param name="selector">CSS selector.</param>
    /// <param name="styleType">Document style type.</param>
    /// <returns>
    /// New or existing document style; null, if style isn't defined or there are too many styles in the document.
    /// </returns>
    private Style getStyleBySelectorUncached(CssSelector selector, /*StyleType*/int styleType) throws Exception
    {
        Style existingStyle = getPredefinedStyle(selector, styleType);
        if (existingStyle != null)
        {
            return existingStyle;
        }

        String preferredStyleName = null;

        CssDeclarationCollection selectorDeclarations = CssStyleTracker.getSelectorDeclarations(selector);
        if (selectorDeclarations != null)
        {
            CssDeclaration styleNameDeclaration = selectorDeclarations.get(HtmlConstants.STYLE_NAME);
            if (styleNameDeclaration != null)
            {
                preferredStyleName = selectorDeclarations.getString(HtmlConstants.STYLE_NAME);
            }
        }

        // WORDSNET-21412 If possible, we save names of character styles as class names of "span.class" selectors. Here
        // we try to restore such names.
        if (!StringUtil.hasChars(preferredStyleName) && (styleType == StyleType.CHARACTER))
        {
            preferredStyleName = parseSpanClassSelector(selector);
        }

        if (!StringUtil.hasChars(preferredStyleName))
        {
            preferredStyleName = selector.getPreferableStyleName();
        }

        // Try to find an existing style.
        existingStyle = Document.getStyles().get(preferredStyleName);
        if ((existingStyle != null) && (existingStyle.getType() != styleType))
        {
            // The type of the existing style doesn't match the requested type and the style cannot be reused.
            // We have to modify the name of imported style.
            preferredStyleName = msString.format("{0} {1}", preferredStyleName, getStyleTypeName(styleType));
            existingStyle = null;
        }

        if (selectorDeclarations == null)
        {
            int istd = getDefaultStyleIstd(styleType);
            return (istd != StyleIndex.NIL)
                ? Document.getStyles().getByIstd(istd, true)
                : null;
        }

        // In case the selector has no declarations we try to reuse an existing style as if there were no selector at all.
        if ((selectorDeclarations.getCount() == 0) && (existingStyle != null))
        {
            return existingStyle;
        }

        // We always create a new style first. We need a new style in order to compare its formatting to formatting of
        // styles imported before and decide whether we could reuse an existing style. If we decide to reuse, the new
        // style is removed.
        Style newStyle = createNewStyle(preferredStyleName, styleType);

        // Style creation will fail if there are too many styles in the document.
        if (newStyle == null)
        {
            return null;
        }

        newStyle.setBasedOnIstd(getBaseStyleIstd(newStyle, selector, selectorDeclarations));
        format(newStyle, selectorDeclarations);

        // Compare formatting of imported and existing styles and decide if we can reuse an existing style.
        // Search for a custom (not built-in) style that is equal to the newly created one.
        // Skip the new style that we've just created. Note that the new style is not necessarily last on the list.
        // It might be inserted into the middle of the list in place of a previously removed style, so we should
        // also search for reuse candidates past the new style.
        // Skip built-in styles. We're importing a new style from HTML, so it is user-defined and it cannot
        // be mapped to a built-in style.
        String existingStyleName = preferredStyleName;
        int i = 0;
        Style reusableStyle;
        do
        {
            reusableStyle = Document.getStyles().getByName(existingStyleName, false);

            // It is important that the style names generated here match unique style names that are generated when new
            // styles are created. In this way we try to limit the number of reuse candidates and check only styles that
            // are most likely created by previous HTML import sessions.
            existingStyleName = msString.format("{0}_{1}", preferredStyleName, i);
            i++;
        }
        while ((reusableStyle != null) &&
            (msObject.referenceEquals(reusableStyle, newStyle) || reusableStyle.getBuiltIn() || (!reusableStyle.equals(newStyle))));

        // An existing user-defined style can be reused.
        if (reusableStyle != null)
        {
            // So the new style is not needed.
            Document.getStyles().removeCore(newStyle);
            return reusableStyle;
        }

        // No reusable existing style is found, so we return the newly created style.
        return newStyle;
    }

    /// <summary>
    /// Parses a selector that looks like "span.class" and returns the class name. If cannot parse, returns <c>null</c>.
    /// </summary>
    private static String parseSpanClassSelector(CssSelector selector)
    {
        CssCompoundSelector compoundSelector = ms.as(selector, CssCompoundSelector.class);
        if (compoundSelector == null)
        {
            return null;
        }

        CssTypeSelector head = ms.as(compoundSelector.getHead(), CssTypeSelector.class);
        if ((head == null) || (!"span".equals(head.getElementName())))
        {
            return null;
        }

        CssClassSelector tail = ms.as(compoundSelector.getTail(), CssClassSelector.class);
        if (tail == null)
        {
            return null;
        }

        return tail.getClassName();
    }

    private Style getStyleByClassAttribute(String classAttributeValue, /*StyleType*/int styleType)
    {
        String styleName = classAttributeValue;

        // Try to find an existing style.
        Style style = Document.getStyles().get(styleName);
        if ((style != null) && (style.getType() != styleType))
        {
            styleName = msString.format("{0} {1}", styleName, getStyleTypeName(styleType));
            style = Document.getStyles().get(styleName);
        }
        if ((style != null) && (style.getType() != styleType))
        {
            style = null;
        }

        return (style != null)
            ? style
            : createNewStyle(styleName, styleType);
    }

    /// <summary>
    /// Creates a new style of the specified type.
    /// </summary>
    /// <returns>
    /// A new style or <c>null</c> if there are too many styles in the document already.
    /// </returns>
    private Style createNewStyle(String preferredName, /*StyleType*/int type)
    {
        // This method is not designed for creation of list styles.
        Debug.assertTrue(type != StyleType.LIST);

        if (mTooManyStyles)
        {
            return null;
        }

        int istd;
        try
        {
            istd = Document.getStyles().getNextFreeIstd();
        }
        catch (IllegalStateException e)
        {
            mTooManyStyles = true;
            return null;
        }

        String name = Document.getStyles().getUniqueStyleName(preferredName);
        Style style = Style.create(type, istd, StyleIdentifier.USER, name);
        Document.getStyles().add(style);
        style.setBasedOnIstd((type != StyleType.TABLE)
            ? getDefaultStyleIstd(type)
            : StyleIndex.TABLE_NORMAL);
        return style;
    }

    private static String getStyleTypeName(/*StyleType*/int styleType)
    {
        switch (styleType)
        {
            case StyleType.PARAGRAPH:
                return "Paragraph";
            case StyleType.CHARACTER:
                return "Character";
            case StyleType.TABLE:
                return "Table";
            default:
                Debug.assertTrue(false);
                return "";
        }
    }

    private Style getPredefinedStyle(CssSelector selector, /*StyleType*/int styleType)
    {
        if (styleType != StyleType.PARAGRAPH)
            return null;

        /*StyleIdentifier*/int sti;
        switch (gStringSwitchMap.of(selector.toCss()))
        {
            case /*"h1"*/0:
                sti = StyleIdentifier.HEADING_1;
                break;
            case /*"h2"*/1:
                sti = StyleIdentifier.HEADING_2;
                break;
            case /*"h3"*/2:
                sti = StyleIdentifier.HEADING_3;
                break;
            case /*"h4"*/3:
                sti = StyleIdentifier.HEADING_4;
                break;
            case /*"h5"*/4:
                sti = StyleIdentifier.HEADING_5;
                break;
            case /*"h6"*/5:
                sti = StyleIdentifier.HEADING_6;
                break;
            default:
                sti = StyleIdentifier.NIL;
                break;
        }

        return (sti != StyleIdentifier.NIL)
                   ? Document.getStyles().getBySti(sti, true)
                   : null;
    }

    /// <summary>
    /// Gets base style Istd for specified style and selector.
    /// </summary>
    /// <param name="style">Document style.</param>
    /// <param name="selector">CSS selector.</param>
    /// <param name="declarations">CSS declarations of the selector's rule.</param>
    /// <returns>Base style Istd.</returns>
    private int getBaseStyleIstd(
        Style style,
        CssSelector selector,
        CssDeclarationCollection declarations) throws Exception
    {
        if (style.getType() == StyleType.TABLE)
        {
            return StyleIndex.TABLE_NORMAL;
        }

        CssDeclaration parentStyleDeclaration = declarations.get(HtmlConstants.STYLE_PARENT);
        if (parentStyleDeclaration != null)
        {
            /*StyleIdentifier*/int parentStyleIdentifier = CssDocumentStyleNames.cssStyleNameToStyleIdentifier(parentStyleDeclaration.getValue());
            if (CssDocumentStyleNames.validateStyleType(parentStyleIdentifier, style.getType()))
            {
                Style baseStyle = style.getStyles().getBySti(parentStyleIdentifier, false);
                if (baseStyle != null)
                {
                    return baseStyle.getIstd();
                }
            }
        }

        if (selector instanceof CssCompoundSelector)
        {
            CssCompoundSelector compoundSelector = (CssCompoundSelector)selector;
            // Style of a selector like element1.class1 should be based on the element1 style.
            if ((compoundSelector.getHead() instanceof CssTypeSelector) && (compoundSelector.getTail() instanceof CssClassSelector))
            {
                Style baseStyle = getStyleBySelector(compoundSelector.getHead(), style.getType());
                if (baseStyle != null)
                {
                    return baseStyle.getIstd();
                }
            }
        }

        return getDefaultStyleIstd(style.getType());
    }

    private int getDefaultStyleIstd(/*StyleType*/int styleType)
    {
        int istd;
        switch (styleType)
        {
            case StyleType.PARAGRAPH:
                istd = mDefaultParagraphIstd;
                break;
            case StyleType.CHARACTER:
                istd = mDefaultFontIstd;
                break;
            case StyleType.TABLE:
                istd = StyleIndex.NIL;
                break;
            default:
                istd = StyleIndex.NIL;
                Debug.assertTrue(false);
                break;
        }
        return istd;
    }

    private void format(Style style, CssDeclarationCollection declarations) throws Exception
    {
        if ((style.getType() != StyleType.PARAGRAPH) && (style.getType() != StyleType.CHARACTER))
        {
            return;
        }
        if (style.getType() == StyleType.PARAGRAPH)
        {
            ParagraphFormatter.format(style.getParagraphFormat(), declarations);
        }
        FontFormatter.format(style.getFont(), style.getType(), declarations);

        addAliases(style, declarations);
    }

    private static void addAliases(Style style, CssDeclarationCollection declarations)
    {
        CssDeclaration styleAliasesDeclaration = declarations.get(HtmlConstants.STYLE_ALIASES);
        if (styleAliasesDeclaration == null)
        {
            return;
        }

        // Alias list is a sequence of string values.
        for (int i = 0; i < styleAliasesDeclaration.getValue().getCount(); i++)
        {
            CssStringValue stringValue = ms.as(styleAliasesDeclaration.getValue().get(i), CssStringValue.class);
            if (stringValue != null)
            {
                String alias = stringValue.getValue();

                // MS Word combines style name and all aliases into a single comma-separated string. As a result, aliases
                // cannot contain comma characters. It's possible to add such an alias to a style, but the alias
                // will not work correctly and will not be preserved upon re-saving to MS Word's formats.
                if (StringUtil.hasChars(alias) && (alias.indexOf(',') < 0))
                {
                    style.addAlias(stringValue.getValue());
                }
            }
        }
    }

    private /*final*/ HtmlSelectorStyleCache mSelectorStyleCache;

    private boolean mTooManyStyles;

    /// <summary>
    /// Index of the style that all paragraph styles imported from CSS will be based on.
    /// </summary>
    private /*final*/ int mDefaultParagraphIstd;

    /// <summary>
    /// Index of the style that all font styles imported from CSS will be based on.
    /// </summary>
    private /*final*/ int mDefaultFontIstd;

    private Style mStyleWithoutDeclarations;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"h1",
		"h2",
		"h3",
		"h4",
		"h5",
		"h6",
		"a",
		"em",
		"strong"
	);

}

