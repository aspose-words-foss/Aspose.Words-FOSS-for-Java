// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/01/2017 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ArrayUtil;
import com.aspose.Debug;
import com.aspose.MathUtil;
import com.aspose.DrColor;
import com.aspose.NullableBool;
import com.aspose.NullableBoolUtil;


/// <summary>
/// Applies CSS formatting to characters.
/// </summary>
abstract class FontFormatter
{
    protected FontFormatter(boolean applyOverridableStyles)
    {
        mApplyOverridableStyles = applyOverridableStyles;
    }

    void format(Font font, CssStyleTracker cssStyleTracker) throws Exception
    {
        CssDeclarationCollection declarations = cssStyleTracker.getElementDeclarations();
        CssTextDecoration textDecoration = cssStyleTracker.getTextDecoration();

        // WORDSNET-27579 If an element maps to a built-in character style and we're in the "use builder formatting" mode,
        // default user-agent HTML formatting must not be applied directly, because otherwise it will override formatting
        // applied by character styles.
        if (!mApplyOverridableStyles)
        {
            // Remove user-agent default property values from elements that map to built-in character styles.
            // This prevents this user-agent defaults from overwriting formatting specified by styles, which is what
            // we want in the "use builder formatting" HTML insertion mode.
            String[] overridableProperties = getOverridableProperties(font.getStyle(), cssStyleTracker);
            declarations = declarations.withoutUserAgentDeclarations(overridableProperties);

            // This removes underline from hyperlinks.
            if (ArrayUtil.contains(overridableProperties, "text-decoration"))
            {
                textDecoration = textDecoration.withoutUnderline();
            }
        }

        applyDeclarations(font, declarations);

        applyBorder(font, cssStyleTracker.getInlineBorder());
        applyVerticalAlign(font, cssStyleTracker.getSubSupScript());
        applyBackgroundColor(font, new CssBackgroundColor(cssStyleTracker.getBackground().getFontBackgroundColor()));

        // Apply 'lang' attribute values.
        font.setLocaleId(cssStyleTracker.getLocaleId());
        font.setLocaleIdBi(cssStyleTracker.getLocaleIdBi());
        font.setLocaleIdFarEast(cssStyleTracker.getLocaleIdFarEast());

        HtmlElementInfo parentBlock = cssStyleTracker.getCurrentElementInfo().ParentBlockElement;
        boolean isInsideHtmlParagraph = (parentBlock != null) && ("p".equals(parentBlock.Element.getElementName()));

        applySpecialFormatting(font, declarations, textDecoration, isInsideHtmlParagraph);
    }

    /// <summary>
    /// Gets properties that map to default formatting of built-in character styles.
    /// </summary>
    private static String[] getOverridableProperties(Style fontStyle, CssStyleTracker styleTracker)
    {
        // MS Word propagates character styles to inner inline elements (for example, "<em><span>Text</span><em>")
        // so we have to look up the HTML tree for the element that applies the character style.
        String elementName = styleTracker.findNearestInlineOverridableParentElement();

        switch (gStringSwitchMap.of(elementName))
        {
            case /*"a"*/0:
                if (fontStyle.getStyleIdentifier() == StyleIdentifier.HYPERLINK)
                {
                    // "Hyperlink" text is blue and underlined by default.
                    return new String[] { "color", "text-decoration" };
                }
                break;
            case /*"strong"*/1:
                if (fontStyle.getStyleIdentifier() == StyleIdentifier.STRONG)
                {
                    // "Strong" text is bold by default.
                    return new String[] { "font-weight" };
                }
                break;
            case /*"em"*/2:
                if (fontStyle.getStyleIdentifier() == StyleIdentifier.EMPHASIS)
                {
                    // "Emphasis" text is italic by default.
                    return new String[] { "font-style" };
                }
                break;
            default:
                break;
        }
        return ArrayUtil.EMPTY_STRING_ARRAY;
    }

    void format(Font font, /*StyleType*/int styleType, CssDeclarationCollection declarations) throws Exception
    {
        applyDeclarations(font, declarations);

        applyBorder(font, CssBorder.createBoxBorder(declarations));
        applyVerticalAlign(font, new CssVerticalAlign(declarations));
        applyBackgroundColor(font, new CssBackgroundColor(declarations));

        applySpecialFormatting(font, styleType, declarations);
    }

    protected abstract void applySpecialFormatting(
        Font font,
        CssDeclarationCollection declarations,
        CssTextDecoration textDecoration,
        boolean isInsideHtmlParagraph) throws Exception;

    protected abstract void applySpecialFormatting(Font font, /*StyleType*/int styleType, CssDeclarationCollection declarations) throws Exception;

    private static void applyDeclarations(Font font, CssDeclarationCollection declarations)
    {
        applyColor(font, declarations);
        applyDisplay(font, declarations);
        applyFontStyle(font, declarations);
        applyFontVariant(font, declarations);
        applyFontWeight(font, declarations);
        applyLetterSpacing(font, declarations);
        applyTextTransform(font, declarations);
        applyVisibility(font, declarations);
    }

    private static void applyLetterSpacing(Font font, CssDeclarationCollection declarations)
    {
        CssDeclaration declaration = declarations.get("letter-spacing");
        if (declaration == null)
            return;

        Debug.assertTrue(declaration.getValue().getCount() == 1);
        CssValue cssValue = declaration.getValue().getFirstValue();

        // Default is 0. Can be negative.
        double spacing = cssValue.equals(CssValue.Normal) ? 0.0 : CssUtil.lengthToPoint(cssValue);
        if (!MathUtil.isMinValue(spacing))
            font.setSpacing(spacing);
    }

    private static void applyBorder(Font font, CssBorder border)
    {
        if (!border.isUndefined())
        {
            border.toModelBorder(font.getBorder());
        }
    }

    private static void applyColor(Font font, CssDeclarationCollection declarations)
    {
        DrColor color = declarations.getColor("color");
        if (color != null)
        {
            font.setColorInternal(color);
        }
    }

    private static void applyDisplay(Font font, CssDeclarationCollection declarations)
    {
        CssDeclaration displayDeclaration = declarations.get("display");
        if (displayDeclaration == null)
        {
            return;
        }

        Debug.assertTrue(displayDeclaration.getValue().getCount() == 1);
        CssValue cssValue = displayDeclaration.getValue().getFirstValue();

        // According to the specification, if 'display:none' is declared for an element, all the child elements should be hidden,
        // no matter what 'display' property value they have. This behavior cannot be implemented using the IFontApplicable mechanism,
        // it's implemented in DocumentStyleResolver class (see ElementDisplayState property for details).
        // But this function is used when we create document styles based on CSS rules declared in a HTML document.
        if (cssValue.equals(CssValue.None))
        {
            font.setHidden(true);
        }
    }

    private static void applyFontVariant(Font font, CssDeclarationCollection declarations)
    {
        CssDeclaration declaration = declarations.get("font-variant");
        if (declaration == null)
        {
            return;
        }

        Debug.assertTrue(declaration.getValue().getCount() == 1);
        CssValue cssValue = declaration.getValue().getFirstValue();

        font.setSmallCaps(cssValue.equals(CssValue.SmallCaps));
    }

    private static void applyFontStyle(Font font, CssDeclarationCollection declarations)
    {
        /*NullableBool*/byte isItalic = CssUtil.isItalicFont(declarations, "font-style");
        if (NullableBoolUtil.hasValue(isItalic))
        {
            font.setItalic(isItalic == NullableBool.TRUE);
            font.setItalicBi(isItalic == NullableBool.TRUE);
        }
    }

    private static void applyFontWeight(Font font, CssDeclarationCollection declarations)
    {
        /*NullableBool*/byte isBold = CssUtil.isBoldFont(declarations, "font-weight");
        if (NullableBoolUtil.hasValue(isBold))
        {
            font.setBold(isBold == NullableBool.TRUE);
            font.setBoldBi(isBold == NullableBool.TRUE);
        }
    }

    private static void applyTextTransform(Font font, CssDeclarationCollection declarations)
    {
        CssDeclaration declaration = declarations.get("text-transform");
        if (declaration == null)
        {
            return;
        }

        Debug.assertTrue(declaration.getValue().getCount() == 1);
        CssValue cssValue = declaration.getValue().getFirstValue();

        font.setAllCaps(cssValue.equals(CssValue.UpperCase));
    }

    private static void applyVerticalAlign(Font font, CssVerticalAlign verticalAlign)
    {
        if (verticalAlign.getVerticalAlignValue() == null)
        {
            return;
        }

        Debug.assertTrue(verticalAlign.getVerticalAlignValue().getCount() == 1);
        CssValue cssValue = verticalAlign.getVerticalAlignValue().getFirstValue();
        switch (cssValue.getValueType())
        {
            case CssValueType.IDENTIFIER:
            {
                if (cssValue.equals(CssValue.Sub))
                    font.setVerticalAlignment(RunVerticalAlignment.SUBSCRIPT);
                else if (cssValue.equals(CssValue.Super))
                    font.setVerticalAlignment(RunVerticalAlignment.SUPERSCRIPT);
                else if (cssValue.equals(CssValue.Baseline))
                    font.setVerticalAlignment(RunVerticalAlignment.BASELINE);
                break;
            }
            default:
            {
                double length = CssUtil.lengthToPoint(cssValue);
                if (!MathUtil.isMinValue(length))
                    font.setPosition(length);
                break;
            }
        }
    }

    private static void applyVisibility(Font font, CssDeclarationCollection declarations)
    {
        CssDeclaration declaration = declarations.get("vertical-align");
        if (declaration == null)
        {
            return;
        }

        Debug.assertTrue(declaration.getValue().getCount() == 1);
        CssValue cssValue = declaration.getValue().getFirstValue();

        if (cssValue.equals(CssValue.Hidden) || cssValue.equals(CssValue.Collapse))
        {
            font.setHidden(true);
        }
    }

    private static void applyBackgroundColor(Font font, CssBackgroundColor backgroundColor)
    {
        backgroundColor.toShading(font.getParent());
    }

    private /*final*/ boolean mApplyOverridableStyles;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"a",
		"strong",
		"em"
	);

}

