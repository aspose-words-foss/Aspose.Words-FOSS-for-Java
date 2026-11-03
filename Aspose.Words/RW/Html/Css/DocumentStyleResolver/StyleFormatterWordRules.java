// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/12/2016 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.StringToStringDictionary;
import com.aspose.FormatterPal;
import com.aspose.DrColor;


/// <summary>
/// Gets and formats appropriate model styles when CSS formatting is applied to the document model. to paragraphs.
/// Uses same formatting rules as MS Word.
/// </summary>
class StyleFormatterWordRules extends StyleFormatter
{
    StyleFormatterWordRules(
        Document document,
        CssStyleTracker cssStyleTracker,
        ParagraphFormatter paragraphFormatter,
        FontFormatter fontFormatter)
    {
    	super(document, cssStyleTracker, paragraphFormatter, fontFormatter);
	
        // Empty constructor.
    }

    /*override*/ Style getModelStyle(/*StyleType*/int styleType) throws Exception
    {
        switch (styleType)
        {
            case StyleType.PARAGRAPH:
                return getParagraphStyle();
            case StyleType.CHARACTER:
                return getCharacterStyle();
            default:
                return null;
        }
    }

    /*override*/ void updatePredefinedStyles() throws Exception
    {
        // Get default run properties from 'p' and 'span' selectors.
        for (CssStyleRule cssRule : CssStyleTracker.getDocumentStyleRules())
        {
            for (CssSelector selector : cssRule.getSelectors())
            {
                // For each of 'p' selectors.
                CssTypeSelector selectorAsType = ms.as(selector, CssTypeSelector.class);
                if (selectorAsType == null)
                {
                    continue;
                }

                // Get paragraph properties from declarations.
                if ("p".equals(selectorAsType.getElementName()))
                {
                    ParagraphFormatter.format(new ParagraphFormat(mParaPrFromParagraphSelector, Document.getStyles()),
                        cssRule.getDeclarations());
                }

                // Get run properties from declarations.
                RunPr runPr;
                /*StyleType*/int styleType;
                switch (gStringSwitchMap.of(selectorAsType.getElementName()))
                {
                    case /*"p"*/0:
                    {
                        runPr = mRunPrFromParagraphSelector;
                        styleType = StyleType.PARAGRAPH;
                        break;
                    }
                    case /*"span"*/1:
                    {
                        runPr = mRunPrFromSpanSelector;
                        styleType = StyleType.CHARACTER;
                        break;
                    }
                    default:
                    {
                        continue;
                    }
                }
                FontFormatter.format(new Font(runPr, Document), styleType, cssRule.getDeclarations());
            }
        }

        CssComputedDeclarationResolver resolver = new CssComputedDeclarationResolver(true);

        // Create styles from selectors.
        for (CssStyleRule cssRule : CssStyleTracker.getDocumentStyleRules())
        {
            // WORDSNET-24917 Resolves specified CSS declarations for computing during updating the predefined styles.
            CssDeclarationCollection computedRuleDeclarations = resolver.resolveToComputed(cssRule.getDeclarations());

            for (CssSelector selector : cssRule.getSelectors())
            {
                // Element name selectors "h1".."h6"
                CssTypeSelector selectorAsType = ms.as(selector, CssTypeSelector.class);
                if (selectorAsType != null)
                {
                    /*StyleIdentifier*/int styleIdentifier = elementNameToHeadingStyleIdentifier(selectorAsType.getElementName());
                    if (styleIdentifier != StyleIdentifier.NIL)
                    {
                        getAndFormatStyle(styleIdentifier, computedRuleDeclarations);
                    }
                    continue;
                }

                // Class name selectors like ".someclass"
                // MS Word discards CSS classes that have no declarations or are marked as "export-only".
                // Note that the "msochpdefault" style is formatted elsewhere.
                CssClassSelector selectorAsClass = ms.as(selector, CssClassSelector.class);
                if ((selectorAsClass != null) &&
                    (computedRuleDeclarations.getCount() > 0) &&
                    (!computedRuleDeclarations.containsIdentifier("mso-style-type", "export-only")) &&
                    !msString.equals(selectorAsClass.getClassName(), MSO_CHP_DEFAULT_CLASS_NAME, StringComparison.ORDINAL_IGNORE_CASE))
                {
                    getAndFormatStyle(selectorAsClass.getClassName(), StyleType.PARAGRAPH, computedRuleDeclarations);
                    continue;
                }

                // Element name + class name selectors like "p.someclass"
                CssCompoundSelector selectorAsTypeAndClass = ms.as(selector, CssCompoundSelector.class);
                if (selectorAsTypeAndClass != null)
                {
                    CssTypeSelector headAsType = ms.as(selectorAsTypeAndClass.getHead(), CssTypeSelector.class);
                    CssClassSelector tailAsClass = ms.as(selectorAsTypeAndClass.getTail(), CssClassSelector.class);
                    if ((headAsType != null) && (tailAsClass != null))
                    {
                        /*StyleType*/int styleType = ("span".equals(headAsType.getElementName()))
                            ? StyleType.CHARACTER
                            : StyleType.PARAGRAPH;
                        getAndFormatStyle(tailAsClass.getClassName(), styleType, computedRuleDeclarations);
                    }
                }
            }
        }
    }

    /*override*/ Style getStyleWithoutDeclarations() { return null; }

    protected Style getAndFormatStyle(
        /*StyleIdentifier*/int styleIdentifier,
        CssDeclarationCollection declarations) throws Exception
    {
        // If the style exists, the basic formatting has been applied to it already.
        Style style = Document.getStyles().getBySti(styleIdentifier, false);
        if (style == null)
        {
            // The style doesn't exist so we created it and set up its formatting.
            style = Document.getStyles().getBySti(styleIdentifier, true);
            applyBasicFormatting(style);
        }

        applyAdditionalFormatting(style, declarations);
        return style;
    }

    protected /*virtual*/ Style getAndFormatStyle(
        String preferredName,
        /*StyleType*/int styleType,
        CssDeclarationCollection declarations) throws Exception
    {
        Debug.assertTrue(StringUtil.hasChars(preferredName));
        Style style;
        // WORDSNET-24917 Updates the 'MsoNormal' style in accordance with the MS Word behavior.
        // WORDSNET-27053 'MsoNormal' is only applicable to paragraphs.
        if ((styleType == StyleType.PARAGRAPH) &&
            msString.equals(preferredName, MSO_NORMAL_CLASS_NAME, StringComparison.ORDINAL_IGNORE_CASE))
        {
            style = getAndFormatStyle(StyleIdentifier.NORMAL, declarations);
            style.getRunPr().remove(FontAttr.CHARACTER_CATEGORY_HINT);
            // MS Word changes the style properties only if it has any declarations.
            if (declarations.getCount() > 0)
            {
                ParaPr styleParaPr = style.getParaPr();
                if (declarations.get("margin-left") == null)
                {
                    if (!styleParaPr.contains(ParaAttr.SPACE_BEFORE))
                        styleParaPr.setSpaceBefore(100);
                    if (!styleParaPr.contains(ParaAttr.SPACE_BEFORE_AUTO))
                        styleParaPr.setSpaceBeforeAuto(true);
                }
                if (declarations.get("margin-right") == null)
                {
                    if (!styleParaPr.contains(ParaAttr.SPACE_AFTER))
                        styleParaPr.setSpaceAfter(100);
                    if (!styleParaPr.contains(ParaAttr.SPACE_AFTER_AUTO))
                        styleParaPr.setSpaceAfterAuto(true);
                }
            }
        }
        else
        {
            StringToStringDictionary createdStylesDictionary;
            switch (styleType)
            {
                case StyleType.PARAGRAPH:
                    createdStylesDictionary = mParagraphStylesCreatedByName;
                    break;
                case StyleType.CHARACTER:
                    createdStylesDictionary = mCharacterStylesCreatedByName;
                    break;
                default:
                    Debug.fail("Unexpected style type");
                    return null;
            }

            // MS Word converts CSS class names to lower case.
            preferredName = msString.toLowerInvariant(preferredName);

            // Try to reuse the style we've created earlier for the same preferred name.
            String usedName = createdStylesDictionary.get(preferredName);
            if (usedName == null)
            {
                // No style to reuse. We'll create a new one after we choose an unique name for it.
                usedName = preferredName;
                Style existingStyle = Document.getStyles().getByName(usedName, false);
                int i = 1;
                while ((existingStyle != null) && (existingStyle.getType() != styleType))
                {
                    usedName = preferredName + FormatterPal.intToStr(i);
                    i++;
                    existingStyle = Document.getStyles().getByName(usedName, false);
                }
            }

            style = Document.getStyles().getByName(usedName, false);
            if (style == null)
            {
                int istd = Document.getStyles().getNextFreeIstd();
                style = Style.create(styleType, istd, StyleIdentifier.USER, usedName);
                style.setBasedOnIstd((styleType == StyleType.CHARACTER)
                    ? StyleIndex.DEFAULT_PARAGRAPH_FONT
                    : StyleIndex.NORMAL);
                Document.getStyles().add(style);
                applyBasicFormatting(style);
                createdStylesDictionary.add(preferredName, usedName);
            }

            applyAdditionalFormatting(style, declarations);
        }

        return style;
    }

    protected /*virtual*/ void applyBasicFormatting(Style style)
    {
        Debug.assertTrue(style != null);
        // WORDSNET-24917 MS Word sets the following default values for the Normal Web style.
        if (style.getStyleIdentifier() == StyleIdentifier.NORMAL_WEB)
        {
            style.setSemiHidden(true);
            style.setUnhideWhenUsed(true);
            style.setPriority(99);
        }
        applyDefaultFormatting(style);
        applyFormattingFromElementSelectors(style);
    }

    private Style getParagraphStyle() throws Exception
    {
        IHtmlElementProvider element = CssStyleTracker.getCurrentElement();

        /*StyleIdentifier*/int headingStyleIdentifier = elementNameToHeadingStyleIdentifier(element.getElementName());
        if (headingStyleIdentifier != StyleIdentifier.NIL)
        {
            return getAndFormatStyle(headingStyleIdentifier, CssDeclarationCollection.EMPTY);
        }

        // In most cases, imported <p> elements are formatted using the "Normal (Web)" style.
        //
        // Implicit <p> elements don't exist in the source HTML, so MS Word doesn't apply 'Normal (Web)' to them.
        //
        // WORDSNET-21335 When importing an AltChunk, MS Word applies the "Normal (Web)" style to text that
        // is inside a paragraph element in HTML and the "Normal" style to text that is outside HTML paragraphs
        // (is inside the HTML body or a table cell). In order to mimic this behavior, we apply the "Normal" style
        // to empty paragraphs and, as a result, to out-of-paragraph text that follows them.
        // Note that CurrentElementHasChildren may return "false" here in case a <p> element contains nothing
        // but an ::after pseudo-element. However, since MS Word doesn't support pseudo-elements, it also considers
        // such HTML paragraphs as containing no children.
        if (("p".equals(element.getElementName()))
            && !element.isImplicit()
            && CssStyleTracker.getCurrentElementHasChildren())
        {
            String[] classes = element.getClasses();
            if (classes.length > 0)
            {
                String className = classes[0];

                // WORDSNET-17035 "MsoNormal" class name has special meaning for MS Word: it is an alias for the
                // "Normal" paragraph style. <p> elements with the "MsoNormal" class are formatted using the "Normal" style
                // (not the "Web" version).
                if (msString.equals(className, MSO_NORMAL_CLASS_NAME, StringComparison.ORDINAL_IGNORE_CASE))
                {
                    return getAndFormatStyle(StyleIdentifier.NORMAL, CssDeclarationCollection.EMPTY);
                }

                return getAndFormatStyle(className, StyleType.PARAGRAPH, CssDeclarationCollection.EMPTY);
            }

            return getAndFormatStyle(StyleIdentifier.NORMAL_WEB, CssDeclarationCollection.EMPTY);
        }

        return getAndFormatStyle(StyleIdentifier.NORMAL, CssDeclarationCollection.EMPTY);
    }

    private static /*StyleIdentifier*/int elementNameToHeadingStyleIdentifier(String elementName)
    {
        switch (gStringSwitchMap.of(elementName))
        {
            case /*"h1"*/2:
                return StyleIdentifier.HEADING_1;
            case /*"h2"*/3:
                return StyleIdentifier.HEADING_2;
            case /*"h3"*/4:
                return StyleIdentifier.HEADING_3;
            case /*"h4"*/5:
                return StyleIdentifier.HEADING_4;
            case /*"h5"*/6:
                return StyleIdentifier.HEADING_5;
            case /*"h6"*/7:
                return StyleIdentifier.HEADING_6;
            default:
                return StyleIdentifier.NIL;
        }
    }

    private static /*StyleIdentifier*/int elementNameToCharacterStyleIdentifier(String elementName)
    {
        switch (gStringSwitchMap.of(elementName))
        {
            case /*"a"*/8:
                return StyleIdentifier.HYPERLINK;
            case /*"em"*/9:
                return StyleIdentifier.EMPHASIS;
            case /*"strong"*/10:
                return StyleIdentifier.STRONG;
            default:
                return StyleIdentifier.NIL;
        }
    }

    private static void applyDefaultFormatting(Style style)
    {
        // Only paragraph styles have default formatting in MS Word.
        if (style.getType() != StyleType.PARAGRAPH)
        {
            return;
        }

        ParaPr defaultParaPr = new ParaPr();
        defaultParaPr.setAlignment(ParagraphAlignment.LEFT);
        defaultParaPr.setSpaceBefore(100);
        defaultParaPr.setSpaceBeforeAuto(true);
        defaultParaPr.setSpaceAfter(100);
        defaultParaPr.setSpaceAfterAuto(true);
        defaultParaPr.setLineSpacing(240);
        defaultParaPr.setLineSpacingRule(LineSpacingRule.MULTIPLE);
        defaultParaPr.setKeepWithNext(false);
        defaultParaPr.setKeepTogether(false);

        int defaultFontSize;
        switch (style.getStyleIdentifier())
        {
            case StyleIdentifier.HEADING_1:
                defaultFontSize = 48;
                break;
            case StyleIdentifier.HEADING_2:
                defaultFontSize = 36;
                break;
            case StyleIdentifier.HEADING_3:
                defaultFontSize = 27;
                break;
            case StyleIdentifier.HEADING_5:
                defaultFontSize = 20;
                break;
            case StyleIdentifier.HEADING_6:
                defaultFontSize = 15;
                break;
            default:
                defaultFontSize = 24;
                break;
        }

        RunPr defaultRunPr = new RunPr();
        defaultRunPr.setNameAscii("Times New Roman");
        defaultRunPr.setNameBi("Times New Roman");
        defaultRunPr.setNameOther("Times New Roman");
        defaultRunPr.setComplexNameFarEast(ComplexFontName.fromTheme(ThemeFontCore.MINOR_EAST_ASIA));
        defaultRunPr.setSize(defaultFontSize);
        defaultRunPr.setSizeBi(defaultFontSize);
        defaultRunPr.setColor(DrColor.EMPTY);

        if (style.getStyleIdentifier() == StyleIdentifier.HEADING_1)
        {
            defaultRunPr.setKerning(36);
        }
        if (style.isHeading())
        {
            defaultRunPr.setBold(AttrBoolEx.TRUE);
            defaultRunPr.setBoldBi(AttrBoolEx.TRUE);
            defaultRunPr.setItalic(AttrBoolEx.FALSE);
            defaultRunPr.setItalicBi(AttrBoolEx.FALSE);
        }

        applyFormattingToStyle(style, defaultParaPr, defaultRunPr);
    }

    private void applyFormattingFromElementSelectors(Style style)
    {
        // In MS Word, declarations imported from 'p' and 'span' selectors are not applied to heading styles.
        if (style.isHeading())
        {
            return;
        }

        ParaPr paraPr;
        RunPr runPr;
        switch (style.getType())
        {
            case StyleType.PARAGRAPH:
            {
                paraPr = mParaPrFromParagraphSelector;
                runPr = mRunPrFromParagraphSelector;
                break;
            }
            case StyleType.CHARACTER:
            {
                paraPr = null;
                runPr = mRunPrFromSpanSelector;
                break;
            }
            default:
            {
                return;
            }
        }
        applyFormattingToStyle(style, paraPr, runPr);
    }

    private void applyAdditionalFormatting(Style style, CssDeclarationCollection declarations) throws Exception
    {
        if (((style.getType() != StyleType.PARAGRAPH) && (style.getType() != StyleType.CHARACTER)) ||
            (declarations.getCount() == 0))
        {
            return;
        }

        if (style.getType() == StyleType.PARAGRAPH)
        {
            ParaPr paraPr = new ParaPr();
            ParagraphFormatter.format(new ParagraphFormat(paraPr, Document.getStyles()), declarations);
            applyFormattingToStyle(style, paraPr, null);
        }

        RunPr runPr = new RunPr();
        FontFormatter.format(Font.makeFont(runPr, Document), style.getType(), declarations);
        if (style.getType() == StyleType.CHARACTER)
        {
            // MS Word applies all formatting to character styles, even if it duplicates inherited formatting. The reason
            // is that inherited formatting for a character style also depends on the paragraph style at the place where
            // text is located. Consequently, inherited formatting for a character style is not final at the moment
            // the style is formatted.
            runPr.copyTo(style.getRunPr());
        }
        else
        {
            applyFormattingToStyle(style, null, runPr);
        }
    }

    private static void applyFormattingToStyle(Style style, ParaPr newParaPr, RunPr newRunPr)
    {
        // Paragraph formatting.
        if (newParaPr != null)
        {
            for (int i = 0; i < newParaPr.getCount(); i++)
            {
                int key = newParaPr.getKey(i);
                Object desiredValue = newParaPr.getByIndex(i);
                Debug.assertTrue(desiredValue != null);
                Object inheritedValue = ((IParaAttrSource)style).fetchInheritedParaAttr(key);
                if (desiredValue.equals(inheritedValue))
                {
                    // Don't add direct formatting. Reuse the inherited value.
                    style.getParaPr().remove(key);
                }
                else
                {
                    // Override the inherited value by direct formatting.
                    style.getParaPr().setAttr(key, desiredValue);
                }
            }
        }

        // Character formatting.
        if (newRunPr != null)
        {
            for (int i = 0; i < newRunPr.getCount(); i++)
            {
                int key = newRunPr.getKey(i);
                Object desiredValue = newRunPr.getByIndex(i);
                Debug.assertTrue(desiredValue != null);
                Object inheritedValue = style.getInheritedFontAttr(key, true);
                if (desiredValue.equals(inheritedValue))
                {
                    // Don't add direct formatting. Reuse the inherited value.
                    style.getRunPr().remove(key);
                }
                else
                {
                    // Override the inherited value by direct formatting.
                    style.getRunPr().setAttr(key, desiredValue);
                }
            }
        }
    }

    private Style getCharacterStyle() throws Exception
    {
        // In MS Word, there are built-in character styles for certain elements.
        /*StyleIdentifier*/int styleIdentifierByName =
            elementNameToCharacterStyleIdentifier(CssStyleTracker.getCurrentElement().getElementName());
        if (styleIdentifierByName != StyleIdentifier.NIL)
        {
            return getAndFormatStyle(styleIdentifierByName, CssDeclarationCollection.EMPTY);
        }

        // MS Word creates character styles from CSS class names only for "span" elements.
        if ("span".equals(CssStyleTracker.getCurrentElement().getElementName()))
        {
            String[] classes = CssStyleTracker.getCurrentElement().getClasses();
            if (classes.length > 0)
            {
                // MS Word doesn't support multiple class names like <span class="c1 c2">.
                String className = classes[0];
                Style styleFromClass = getAndFormatStyle(className, StyleType.CHARACTER, CssDeclarationCollection.EMPTY);

                // It's possible that the style specified by the class name is not a character style and cannot be applied.
                if (styleFromClass == null)
                {
                    return null;
                }

                // MS Word applies run properties from '.class' selectors to character styles created by class name.
                for (CssStyleRule cssRule : CssStyleTracker.getDocumentStyleRules())
                {
                    for (CssSelector selector : cssRule.getSelectors())
                    {
                        // Class name selectors like ".someclass"
                        CssClassSelector selectorAsClass = ms.as(selector, CssClassSelector.class);
                        if ((selectorAsClass != null) && (msString.equals(selectorAsClass.getClassName(), classes[0])))
                        {
                            applyAdditionalFormatting(styleFromClass, cssRule.getDeclarations());
                        }
                    }
                }

                return styleFromClass;
            }
        }

        // In MS Word, if inline element doesn't have its own style it inherits style from parent span element.
        Style parentCharacterStyle = getParentCharacterStyle();
        return (parentCharacterStyle != null)
            ? parentCharacterStyle
            : getAndFormatStyle(StyleIdentifier.DEFAULT_PARAGRAPH_FONT, CssDeclarationCollection.EMPTY);
    }

    private static final String MSO_NORMAL_CLASS_NAME = "msonormal";
    private static final String MSO_CHP_DEFAULT_CLASS_NAME = "msochpdefault";

    /// <summary>
    /// Paragraph properties imported from 'p' selectors. These properties are applied to all new paragraph styles.
    /// </summary>
    private /*final*/ ParaPr mParaPrFromParagraphSelector = new ParaPr();

    /// <summary>
    /// Run properties imported from 'p' selectors. These properties are applied to all new paragraph styles.
    /// </summary>
    private /*final*/ RunPr mRunPrFromParagraphSelector = new RunPr();

    /// <summary>
    /// Run properties imported from 'span' selectors. These properties are applied to all new character styles.
    /// </summary>
    private /*final*/ RunPr mRunPrFromSpanSelector = new RunPr();

    private /*final*/ StringToStringDictionary mParagraphStylesCreatedByName = new StringToStringDictionary();
    private /*final*/ StringToStringDictionary mCharacterStylesCreatedByName = new StringToStringDictionary();

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"p",
		"span",
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

