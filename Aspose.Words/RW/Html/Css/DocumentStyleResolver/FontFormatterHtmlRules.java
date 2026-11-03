// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/01/2017 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;
import com.aspose.StringUtil;
import com.aspose.NullableBool;


/// <summary>
/// Applies CSS formatting to characters. Uses formatting rules that are different from what MS Word uses but produce
/// better looking results and are more confomant to the CSS and HTML specifications.
/// </summary>
class FontFormatterHtmlRules extends FontFormatter
{
    FontFormatterHtmlRules(
        boolean applyUserAgentStyles,
        CssFontFaceProvider cssFontFaceProvider,
        DocumentFontProvider fontProvider)
    {
    	super(applyUserAgentStyles);
	
        mApplyUserAgentStyles = applyUserAgentStyles;
        mCssFontFaceProvider = cssFontFaceProvider;
        mFontProvider = fontProvider;
    }

    protected /*override*/ void applySpecialFormatting(
        Font font,
        CssDeclarationCollection declarations,
        CssTextDecoration textDecoration,
        boolean isInsideHtmlParagraph) throws Exception
    {
        // Default HTML font size is different from default AW font size, so we need to set it explicitly.
        // Note that we don't apply this to styles.
        if (mApplyUserAgentStyles)
        {
            font.setSize(12.0);
            font.setSizeBi(12.0);
        }

        applyFontSize(font, declarations);
        applyFontFamily(font, declarations);

        applyTextDecoration(font, textDecoration);
    }

    protected /*override*/ void applySpecialFormatting(Font font, /*StyleType*/int styleType, CssDeclarationCollection declarations) throws Exception
    {
        applyFontSize(font, declarations);
        applyFontFamily(font, declarations);

        // WORDSNET-9025 'text-decoration' is not translated to style formatting. 'text-decoration' corresponds
        // to font Underline and StrikeThrough properties in AW document model. However, 'text-decoration' always specifies
        // the full set of decorations applied to text. For example, 'text-decoration:underline' means not only that text
        // is to be underlined, but also that the text has no line through it (has no decoration other than 'underline').
        // As a result, it is impossible in CSS to combine text decorations that come from CSS classes and from inline
        // style but it is possible in AW model. To match CSS rules we have to prevent combination of text decorations
        // in AW model, that is why we do not translate 'text-decoration' to AW styles. Text decorations are always imported
        // as direct formatting, and it is the only source of them in the result AW model.
        //
        // We have to clear corresponding properties out of the style's font formatting, because we might be applying
        // formatting to a built-in style that has text decoration applied by default (for example, the 'Hyperlink' style).
        clearTextDecoration(font);
    }

    private void applyFontSize(Font font, CssDeclarationCollection declarations)
    {
        boolean applyFontSize = false;

        CssDeclaration fontSizeDeclaration = declarations.get("font-size");
        if (fontSizeDeclaration != null)
        {
            applyFontSize = true;
        }

        double fontSize = (fontSizeDeclaration != null)
            ? CssUtil.lengthToPoint(fontSizeDeclaration.getValue())
            : CssUtil.DEFAULT_FONT_SIZE;
        if (MathUtil.isMinValue(fontSize))
        {
            return;
        }

        // The 'vertical-align' CSS property is translated to Font.Subscript or Font.Superscript.
        // In MS Word, text with Font.Subscript or Font.Superscript property set is rendered in smaller font
        // than specified, but in HTML the 'vertical-align' property does not affect the font size.
        // To compensate the font size reduction in MS Word we increase the font size specified in HTML.
        // This behavior is optional, however. When <sub> or <sup> elements are processed with formatting specified
        // in document builder, the font size of text imported from these elements is not changed.
        if (mApplyUserAgentStyles)
        {
            CssDeclaration verticalAlignDeclaration = declarations.get("vertical-align");
            if ((verticalAlignDeclaration != null) &&
                (verticalAlignDeclaration.getValue().equals(CssValue.Sub) || verticalAlignDeclaration.getValue().equals(CssValue.Super)))
            {
                // In MS Word, text with Font.Subscript or Font.Superscript property set is rendered in 2/3
                // of its specified size. To compensate the font size reduction we increase the font size
                // specified in HTML by 3/2 = 1.5
                final double SUB_SUP_FONT_SCALE = 1.5;
                fontSize *= SUB_SUP_FONT_SCALE;
                applyFontSize = true;
            }
        }

        // We apply font size either if it is declared explicitly (there is a 'font-size' declaration in CSS style)
        // or if font is sub/superscript and should be scaled.
        if (applyFontSize)
        {
            font.setSize(fontSize);
            font.setSizeBi(fontSize);
        }
    }

    private void applyFontFamily(Font font, CssDeclarationCollection declarations) throws Exception
    {
        String originalFontName = declarations.getString(HtmlConstants.ORIGINAL_FONT_FAMILY);
        if (StringUtil.hasChars(originalFontName))
        {
            // The original font name might have been changed during export to HTML. Here we restore it.
            font.setName(originalFontName);
            return;
        }

        String fontName = declarations.getFontName(mCssFontFaceProvider, mFontProvider);
        if (StringUtil.hasChars(fontName))
        {
            font.setName(fontName);
        }
    }

    private static void applyTextDecoration(Font font, CssTextDecoration textDecoration)
    {
        if (textDecoration.getStrikeThrough() == NullableBool.TRUE)
        {
            font.setStrikeThrough(true);
        }
        if (textDecoration.getUnderline() == NullableBool.TRUE)
        {
            font.setUnderline(Underline.SINGLE);
            if (textDecoration.getUnderlineColor() != null)
            {
                font.setUnderlineColorInternal(textDecoration.getUnderlineColor());
            }
        }
    }

    private static void clearTextDecoration(Font font)
    {
        font.getParent().removeRunAttr(FontAttr.UNDERLINE);
        font.getParent().removeRunAttr(FontAttr.UNDERLINE_COLOR);
        font.getParent().removeRunAttr(FontAttr.STRIKE_THROUGH);
    }

    /// <summary>
    /// Controls whether certain default (user-agent) formatting is applied to characters.
    /// </summary>
    private /*final*/ boolean mApplyUserAgentStyles;

    private /*final*/ CssFontFaceProvider mCssFontFaceProvider;
    private /*final*/ DocumentFontProvider mFontProvider;
}

