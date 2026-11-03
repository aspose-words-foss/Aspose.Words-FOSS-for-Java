// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/10/2017 by Maxim Glazyrin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Language;
import com.aspose.ms.System.msString;
import com.aspose.FontUnicodeRanges;
import com.aspose.TTFont;
import com.aspose.ms.System.Drawing.FontStyle;
import com.aspose.UnicodeUtil;
import java.util.ArrayList;


/// <summary>
/// Extracts runs with certain characters and ensures font/locale/complex script is set.
/// </summary>
/// <remarks>
/// AM. For unknown reason Word fails to detect characters from certain Unicode blocks without additional information. 
/// Sometimes it requires proper locale to be set, for others requires both complex script and proper font name. 
/// This class tries to mimic such behavior.
/// </remarks>
/*static*/ class ComplexScriptRunUpdater
{
	/* Simulation of static class by using private constructor */
	private ComplexScriptRunUpdater()
	{}

    /// <summary>
    /// Extracts runs with certain characters and applies required formatting.
    /// </summary>
    /// <param name="run">Run to process.</param>
    static void doProcess(Run run)
    {
        CharacterInfo lastInfo = null;

        int i = 0;
        while (i < run.getTextLength())
        {
            String text = run.getText();

            // WORDSNET-19668 Try to get information about the surrogate char, which is an emoji.
            CharacterInfo thisInfo = tryGetSurrogateCharacterInfo(run, i);
            boolean surrogate = thisInfo != null;

            if (!surrogate)
                thisInfo = getCharacterInfo(text.charAt(i));

            if (lastInfo != thisInfo)
            {
                Run newRun = run.splitBefore(i);
                i = 0;

                if (lastInfo != null)
                    forceRunAttributes(newRun, lastInfo);
            }

            // Increase because the surrogate char consists of two Unicode chars.
            if (surrogate)
                i++;
            i++;

            lastInfo = thisInfo;
        }

        if (lastInfo != null)
            forceRunAttributes(run, lastInfo);
    }

    /// <summary>
    /// Sets required attributes for run to be correctly detected by Word.
    /// </summary>
    private static void forceRunAttributes(Run run, CharacterInfo charInfo)
    {
        boolean isComplexScript = InlineHelper.getBool(run, FontAttr.COMPLEX_SCRIPT);

        if (charInfo.getSetComplexScript())
        {
            // WORDSNET-16910 Update size and formatting, when the "ComplexScript" attribute is not set.
            if (!isComplexScript)
                updateComplexScriptFormatting(run);

            run.getRunPr().setAttr(FontAttr.COMPLEX_SCRIPT, AttrBoolEx.TRUE);
        }

        if (charInfo.getLanguage() != Language.LANGUAGE_NOT_SET)
            run.getRunPr().setAttr(FontAttr.LOCALE_ID_BI, (int)charInfo.getLanguage());

        // Change font info in run
        if ((msString.equals(charInfo.getFontName(), run.getFont().getName())) || (run.getTextLength() == 0))
        {
            // If required font is already set or text is empty ignore run.
            return;
        }

        // First try to get information from FontInfo.Signature to know whether font has this character.
        char ch = run.getText().charAt(0);
        // WORDSNET-17046 Use appropriate font name depending on original value of the "ComplexScript" flag.
        String effectiveFontName = isComplexScript ? run.getFont().getNameBi() : run.getFont().getName();
        FontInfo info = run.getDocument().getFontInfos().get(effectiveFontName);

        boolean setFont;
        if ((info != null) && (!info.getUnicodeRanges().equals(FontUnicodeRanges.EMPTY)))
        {
            // Document has font info, check if font contains glyph, if doesn't contain, change font to default
            setFont = !info.getUnicodeRanges().isCharInFunctionalRange(ch);
        }
        else
        {
            // Document doesn't contain font info, may be user want to override standard font, for example set Arial Unicode MS
            // In this case we need to load font and check if it contains glyph.

            // This case is rare and should not affect performance in common case.
            TTFont curFont = run.getDocument().getFontProvider().getTTFont(effectiveFontName, com.aspose.ms.System.Drawing.FontStyle.REGULAR);

            // If unable to load selected font info or font doesn't contain glyph, change it to default for language.
            setFont = (curFont == null) || (!curFont.getGlyphs().containsCharCode(ch, true));
        }

        if (setFont)
            run.getFont().setName(charInfo.getFontName());
    }

    /// <summary>
    /// Updates formatting for complex script chars.
    /// </summary>
    private static void updateComplexScriptFormatting(Run run)
    {      
        int size = (/*int*/Integer)InlineHelper.fetchAttr(run, FontAttr.SIZE);
        int sizeBi = (/*int*/Integer)InlineHelper.fetchAttr(run, FontAttr.SIZE_BI);
        if (size != sizeBi)
            run.getRunPr().set(FontAttr.SIZE_BI, size);

        boolean isBold = InlineHelper.getBool(run, FontAttr.BOLD);
        if (isBold != InlineHelper.getBool(run, FontAttr.BOLD_BI))
            run.getRunPr().set(FontAttr.BOLD_BI, AttrBoolEx.fromBool(isBold));

        boolean isItalic = InlineHelper.getBool(run, FontAttr.ITALIC);
        if (isItalic != InlineHelper.getBool(run, FontAttr.ITALIC_BI))
            run.getRunPr().set(FontAttr.ITALIC_BI, AttrBoolEx.fromBool(isItalic));
    }

    /// <summary>
    /// Determine language info by character.
    /// </summary>
    private static CharacterInfo getCharacterInfo(char ch)
    {
        for (CharacterInfo charInfo : (Iterable<CharacterInfo>) G_OVERRIDE_LOCALES)
        {
            if ((charInfo.getFromCharCode() <= ch) && (ch <= charInfo.getToCharCode()))
                return charInfo;
        }

        return null;
    }

    /// <summary>
    /// Tries determine character info by surrogate symbol.
    /// </summary>
    /// <returns>Character info or null if the character isn't a surrogate symbol.</returns>
    private static CharacterInfo tryGetSurrogateCharacterInfo(Run run, int indexChar)
    {
        String text = run.getText();

        if ((indexChar + 1 < run.getTextLength()) && Character.isHighSurrogate(text.charAt(indexChar)) &&
            Character.isLowSurrogate(text.charAt(indexChar + 1)))
        {
            int charCode = UnicodeUtil.convertToUtf32(text.charAt(indexChar), text.charAt(indexChar + 1));
            return getSurrogateCharacterInfo(charCode);
        }

        return null;
    }

    /// <summary>
    /// Determine character info by surrogate symbol.
    /// </summary>
    private static CharacterInfo getSurrogateCharacterInfo(int charCode)
    {
        for (CharacterInfo charInfo : (Iterable<CharacterInfo>) G_OVERRIDE_EMOJIS)
        {
            if ((charInfo.getFromCharCode() <= charCode) && (charCode <= charInfo.getToCharCode()))
                return charInfo;
        }

        return null;
    }

    /// <summary>
    /// List of languages to override in document locale and fonts.
    /// </summary>
    private static /*final*/ ArrayList<ComplexScriptRunUpdater.CharacterInfo> G_OVERRIDE_LOCALES = getOverrideLocales();

    /// <summary>
    /// List of Unicode emoji code point ranges to override in document fonts.
    /// </summary>
    private static /*final*/ ArrayList<ComplexScriptRunUpdater.CharacterInfo> G_OVERRIDE_EMOJIS = getOverrideEmojes();

    private static ArrayList<ComplexScriptRunUpdater.CharacterInfo> getOverrideLocales()
    {
        ArrayList<ComplexScriptRunUpdater.CharacterInfo> returnInitializer = new ArrayList<ComplexScriptRunUpdater.CharacterInfo>();
        {
            // For these languages MS Word set locale, complex script and font
            returnInitializer.add(new CharacterInfo(0x0D80, 0x0DFF, Language.SINHALESE, "Iskoola Pota", true));
            returnInitializer.add(new CharacterInfo(0x0E00, 0x0E7F, Language.THAI_THAILAND, "Angsana New", true));
            returnInitializer.add(new CharacterInfo(0x1800, 0x18FF, Language.MONGOLIAN_MONGOLIAN, "Mongolian Baiti", true));

            // For these languages MS Word sets only font but not locale or complex script flag
            returnInitializer.add(new CharacterInfo(0x1200, 0x137F, Language.LANGUAGE_NOT_SET, "Nyala", false));
            returnInitializer.add(new CharacterInfo(0x16A0, 0x16FF, Language.LANGUAGE_NOT_SET, "Segoe UI Historic", false)); // Runic
            returnInitializer.add(new CharacterInfo(0x1780, 0x17FF, Language.LANGUAGE_NOT_SET, "DaunPenh", false));

            // WORDSNET-16856 It's a range with a single symbol. Mimic MSW for Mac. 
            returnInitializer.add(new CharacterInfo(0x2060, 0x2060, Language.LANGUAGE_NOT_SET, "Segoe UI Symbol", false));
        }
        return returnInitializer;
    }

    private static ArrayList<ComplexScriptRunUpdater.CharacterInfo> getOverrideEmojes()
    {
        ArrayList<ComplexScriptRunUpdater.CharacterInfo> returnInitializer = new ArrayList<ComplexScriptRunUpdater.CharacterInfo>();
        {
            // Emoticons.
            returnInitializer.add(new CharacterInfo(0x1F600, 0x1F64F, Language.LANGUAGE_NOT_SET, "Segoe UI Emoji", false));
        }
        return returnInitializer;
    }

    /// <summary>
    /// Stores information about document locale and fonts.
    /// </summary>
    private static class CharacterInfo
    {
        /// <summary>
        /// Constructor, initialize internal variables.
        /// </summary>
        /// <param name="fromCharCode">Start character of Unicode block.</param>
        /// <param name="toCharCode">End character of Unicode block.</param>
        /// <param name="language">Language should be forced for text. If equal to Language.LanguageNotSet then it is ignored.</param>
        /// <param name="fontName">Font name to set in document.</param>
        /// <param name="setComplexScript">Indicates that ComplexScript should be forced for text.</param>
        public  CharacterInfo(int fromCharCode, int toCharCode, /*Language*/int language, String fontName, boolean setComplexScript)
        {
            mFromCharCode = fromCharCode;
            mToCharCode = toCharCode;
            mLanguage = language;
            mFontName = fontName;
            mSetComplexScript = setComplexScript;
        }

        /// <summary>
        /// Start character of Unicode block.
        /// </summary>
        public int getFromCharCode() { return mFromCharCode; }

        /// <summary>
        /// End character of Unicode block.
        /// </summary>
        public int getToCharCode() { return mToCharCode; }

        /// <summary>
        /// Locale to set in document.
        /// </summary>
        public /*Language*/int getLanguage() { return mLanguage; }

        /// <summary>
        /// Font name to set in document.
        /// </summary>
        public String getFontName() { return mFontName; }

        /// <summary>
        /// Indicates that complex script should be set for flag.
        /// </summary>
        public boolean getSetComplexScript() { return mSetComplexScript; }
             
        private /*final*/ int mToCharCode;           
        private /*final*/ String mFontName;
        private /*final*/ int mFromCharCode;
        private /*final*/ /*Language*/int mLanguage;
        private /*final*/ boolean mSetComplexScript;
    }
}

