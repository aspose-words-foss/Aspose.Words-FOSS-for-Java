// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/01/2017 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;
import com.aspose.NullableBool;
import com.aspose.MathUtil;
import com.aspose.StringUtil;
import com.aspose.TTFont;
import com.aspose.ms.System.Drawing.FontStyle;
import com.aspose.FormatterPal;
import com.aspose.LocaleConverter;
import com.aspose.Language;
import com.aspose.ms.System.msString;


/// <summary>
/// Applies CSS formatting to characters. Uses same formatting rules as MS Word.
/// </summary>
class FontFormatterWordRules extends FontFormatter
{
    FontFormatterWordRules(
        DocumentFontProvider fontProvider,
        boolean isLoadingHtmlAltChunk)
    {
    	super(false);
	
        mFontProvider = fontProvider;
        mIsLoadingHtmlAltChunk = isLoadingHtmlAltChunk;
    }

    protected /*override*/ void applySpecialFormatting(
        Font font,
        CssDeclarationCollection declarations,
        CssTextDecoration textDecoration,
        boolean isInsideHtmlParagraph) throws Exception
    {
        applyDeclarations(font, declarations, isInsideHtmlParagraph, false, true);
        applyPropagatedTextDecoration(font, textDecoration);
    }

    protected /*override*/ void applySpecialFormatting(
        Font font,
        /*StyleType*/int styleType,
        CssDeclarationCollection declarations) throws Exception
    {
        // In MS Word, 'text-decoration: none' is applied to characters styles only.
        boolean applyNoneTextDecoration = styleType == StyleType.CHARACTER;

        // For some reason, MS Word applies character category hint to styles. We mimic this behavior when loading altChunks.
        applyDeclarations(font, declarations, true, mIsLoadingHtmlAltChunk, applyNoneTextDecoration);
    }

    private void applyDeclarations(
        Font font,
        CssDeclarationCollection declarations,
        boolean isInsideHtmlParagraph,
        boolean applyCharacterCategoryHint,
        boolean applyNoneTextDecoration) throws Exception
    {
        applyFontSize(font, declarations);
        if (mIsLoadingHtmlAltChunk)
        {
            applyFontFamily(font, declarations, isInsideHtmlParagraph, applyCharacterCategoryHint);
        }
        else
        {
            applyMsoFontFamily(font, declarations, applyCharacterCategoryHint);
            applyMsoLanguage(font, declarations);
        }
        applyBorder(font, declarations);
        applyBackgroundColor(font, declarations);
        applyTextDecoration(font, declarations, applyNoneTextDecoration);
    }

    private static void applyTextDecoration(
        Font font,
        CssDeclarationCollection declarations,
        boolean applyNoneTextDecoration)
    {
        Debug.assertTrue(declarations != null);

        CssDeclaration textDecorationDeclaration = declarations.get("text-decoration");
        if (textDecorationDeclaration == null)
        {
            return;
        }

        CssTextDecorationPropertyValue textDeclarationValue =
            ms.as(textDecorationDeclaration.getValue(), CssTextDecorationPropertyValue.class);
        if (textDeclarationValue == null)
        {
            return;
        }

        if (textDeclarationValue.isNone())
        {
            if (applyNoneTextDecoration)
            {
                font.setStrikeThrough(false);
                font.setUnderline(Underline.NONE);
                font.setDoubleStrikeThrough(false);
                font.setTextEffect(TextEffect.NONE);
            }
        }
        else
        {
            if (textDeclarationValue.isLineThrough())
            {
                font.setStrikeThrough(true);
            }
            if (textDeclarationValue.isUnderline())
            {
                font.setUnderline(Underline.SINGLE);
            }
        }
    }

    private static void applyPropagatedTextDecoration(Font font, CssTextDecoration textDecoration)
    {
        if (textDecoration.getUnderline() != NullableBool.NOT_DEFINED)
        {
            font.setUnderline((textDecoration.getUnderline() == NullableBool.TRUE)
                ? Underline.SINGLE
                : Underline.NONE);
        }

        if (textDecoration.getStrikeThrough() != NullableBool.NOT_DEFINED)
        {
            font.setStrikeThrough(textDecoration.getStrikeThrough() == NullableBool.TRUE);
        }
    }

    private static void applyFontSize(Font font, CssDeclarationCollection declarations)
    {
        CssDeclaration fontSizeDeclaration = declarations.get("font-size");
        if (fontSizeDeclaration == null)
        {
            return;
        }

        double fontSize = CssUtil.lengthToPoint(fontSizeDeclaration.getValue());
        if (MathUtil.isMinValue(fontSize))
        {
            return;
        }

        font.setSize(fontSize);
        font.setSizeBi(fontSize);
    }

    private void applyFontFamily(
        Font font,
        CssDeclarationCollection declarations,
        boolean isInsideHtmlParagraph,
        boolean applyCharacterCategoryHint) throws Exception
    {
        // Mimics observable behavior of MS Word for text imported from elements other than <p>.
        if (!isInsideHtmlParagraph)
        {
            font.setNameFarEast("Times New Roman");
        }

        // We don't support @font-face rules when loading HTML with MS Word rules.
        String fontName = declarations.getFontName(null, mFontProvider);
        if (StringUtil.hasChars(fontName))
        {
            font.setNameAscii(fontName);
            font.setNameOther(fontName);

            // WORDSNET-21707 MS Word sets complex script language if the corresponding font family supports it.
            TTFont ttFont = mFontProvider.getTTFont(fontName, FontStyle.REGULAR);
            if ((ttFont != null) && ttFont.getUnicodeRanges().isComplexScriptSupported())
            {
                font.setNameBi(fontName);
            }

            if (applyCharacterCategoryHint)
            {
                font.getParent().setRunAttr(FontAttr.CHARACTER_CATEGORY_HINT, CharacterCategory.ASCII);
            }
        }
    }

    private void applyMsoFontFamily(
        Font font,
        CssDeclarationCollection declarations,
        boolean applyCharacterCategoryHint) throws Exception
    {
        String msoAsciiFontFamily = declarations.getIdentifier("mso-ascii-font-family");
        if (StringUtil.hasChars(msoAsciiFontFamily))
        {
            font.setNameAscii(msoAsciiFontFamily);
        }

        String msoFarEastFontFamily = declarations.getIdentifier("mso-fareast-font-family");
        if (StringUtil.hasChars(msoFarEastFontFamily))
        {
            font.setNameFarEast(msoFarEastFontFamily);
        }

        String msoHAnsiFontFamily = declarations.getIdentifier("mso-hansi-font-family");
        if (StringUtil.hasChars(msoHAnsiFontFamily))
        {
            font.setNameOther(msoHAnsiFontFamily);
        }

        String msoBidiFontFamily = declarations.getIdentifier("mso-bidi-font-family");
        if (StringUtil.hasChars(msoBidiFontFamily))
        {
            font.setNameBi(msoBidiFontFamily);
        }

        String fontName = declarations.getFontName(null, mFontProvider);
        if (StringUtil.hasChars(fontName))
        {
            if (!StringUtil.hasChars(msoAsciiFontFamily))
                font.setNameAscii(fontName);

            if (!StringUtil.hasChars(msoHAnsiFontFamily))
                font.setNameOther(fontName);

            // WORDSNET-21707 MS Word sets complex script language if the corresponding font family supports it.
            TTFont ttFont = mFontProvider.getTTFont(fontName, FontStyle.REGULAR);
            if ((ttFont != null) &&
                ttFont.getUnicodeRanges().isComplexScriptSupported() &&
                !StringUtil.hasChars(msoBidiFontFamily))
            {
                font.setNameBi(fontName);
            }
            if (applyCharacterCategoryHint)
            {
                font.getParent().setRunAttr(FontAttr.CHARACTER_CATEGORY_HINT, CharacterCategory.ASCII);
            }
        }

        // Applying theme fonts. Theme fonts may override the font families previously set.
        applyThemeFont(font, declarations, "mso-ascii-theme-font", FontAttr.NAME_ASCII);
        applyThemeFont(font, declarations, "mso-hansi-theme-font", FontAttr.NAME_OTHER);
        applyThemeFont(font, declarations, "mso-fareast-theme-font", FontAttr.NAME_FAR_EAST);
        applyThemeFont(font, declarations, "mso-bidi-theme-font", FontAttr.NAME_BI);
    }

    private static void applyThemeFont(
        Font font,
        CssDeclarationCollection declarations,
        String propertyName,
        int fontAttr)
    {
        String themeFontIdentifier = declarations.getIdentifier(propertyName);
        if (!StringUtil.hasChars(themeFontIdentifier))
            return;

        /*ThemeFontCore*/int themeFontCore = parseMsoThemeFontIdentifier(themeFontIdentifier);
        if (themeFontCore == ThemeFontCore.NONE)
            return;

        font.getParent().setRunAttr(fontAttr, ComplexFontName.fromTheme(themeFontCore));
    }

    private static void applyBorder(Font font, CssDeclarationCollection declarations)
    {
        // Only top border seems to be taken into account by MS Word.
        CssBorder topBorder = CssBorder.createBorder(declarations, BorderType.TOP, false);
        if (topBorder.isUndefined())
        {
            return;
        }

        topBorder.toModelBorder(font.getBorder());
    }

    private static void applyBackgroundColor(Font font, CssDeclarationCollection declarations)
    {
        CssBackgroundColor backgroundColor = new CssBackgroundColor(declarations);
        backgroundColor.toShading(font.getParent());
    }

    /// <summary>
    /// Applies locales according to the MSO properties.
    /// </summary>
    private static void applyMsoLanguage(Font font, CssDeclarationCollection declarations)
    {
        CssDeclaration ansiLanguageDeclaration = declarations.get("mso-ansi-language");
        if ((ansiLanguageDeclaration != null) && (ansiLanguageDeclaration.getValue().getCount() == 1))
            font.setLocaleId(parseMsoLocaleId(ansiLanguageDeclaration.getValue().getFirstValue()));

        CssDeclaration fareastLanguageDeclaration = declarations.get("mso-fareast-language");
        if ((fareastLanguageDeclaration != null) && (fareastLanguageDeclaration.getValue().getCount() == 1))
            font.setLocaleIdFarEast(parseMsoLocaleId(fareastLanguageDeclaration.getValue().getFirstValue()));

        CssDeclaration bidiLanguageDeclaration = declarations.get("mso-bidi-language");
        if ((bidiLanguageDeclaration != null) && (bidiLanguageDeclaration.getValue().getCount() == 1))
            font.setLocaleIdBi(parseMsoLocaleId(bidiLanguageDeclaration.getValue().getFirstValue()));
    }

    /// <summary>
    /// Resolves the language tag or hexadecimal value to a locale ID.
    /// </summary>
    /// <returns>
    /// If the language tag is unknown, this method returns <see cref="Language.InvariantCulture"/>.
    /// </returns>
    static int parseMsoLocaleId(CssValue cssValue)
    {
        // The language tag may be in a hexadecimal form (for example `mso-ansi-language:#0471`).
        if (cssValue.getValueType() == CssValueType.HASH)
        {
            String hash = ((CssHashValue)cssValue).getText();
            if (hash.length() == 4)
            {
                int localeId = FormatterPal.tryParseHex(hash);
                // Note that the condition also covers the case where parsing fails and returns int.MinValue.
                if ((localeId >= 0) && (localeId <= 0xFFFF))
                {
                    return localeId;
                }
            }
        }
        else if (cssValue.getValueType() == CssValueType.IDENTIFIER)
        {
            // MS Word has some limitations and slightly different behavior during resolving locale ID.
            // Currently, we use the old dictionary from the built-in locale converter.
            return LocaleConverter.wmlTagToLocale(((CssIdentifierValue)cssValue).getValue());
        }

        return (int)Language.INVARIANT_CULTURE;
    }

    private static /*ThemeFontCore*/int parseMsoThemeFontIdentifier(String themeFont)
    {
        // For values 'major-latin' and 'minor-latin' MS Word sets w:asciiTheme="majorHAnsi" and w:asciiTheme="minorHAnsi"
        // respectively. We follow this behavior.
        String themeFontLower = msString.toLowerInvariant(themeFont);
        switch (gStringSwitchMap.of(themeFontLower))
        {
            case /*"minor-latin"*/0:
                return ThemeFontCore.MINOR_H_ANSI;
            case /*"minor-bidi"*/1:
                return ThemeFontCore.MINOR_BIDI;
            case /*"minor-fareast"*/2:
                return ThemeFontCore.MINOR_EAST_ASIA;
            case /*"major-latin"*/3:
                return ThemeFontCore.MAJOR_H_ANSI;
            case /*"major-bidi"*/4:
                return ThemeFontCore.MAJOR_BIDI;
            case /*"major-fareast"*/5:
                return ThemeFontCore.MAJOR_EAST_ASIA;
            default:
                return ThemeFontCore.NONE;
        }
    }

    private /*final*/ DocumentFontProvider mFontProvider;

    /// <summary>
    /// Indicates whether this class is used to format content loaded from a HTML altChunk.
    /// Note that MS Word's behavior is a little different in this case.
    /// </summary>
    private /*final*/ boolean mIsLoadingHtmlAltChunk;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"minor-latin",
		"minor-bidi",
		"minor-fareast",
		"major-latin",
		"major-bidi",
		"major-fareast"
	);

}

