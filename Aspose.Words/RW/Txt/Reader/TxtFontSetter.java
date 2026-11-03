// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/12/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.ms.System.msString;

/// <summary>
/// Sets fonts to runs in a manner like MS Word does on loading document from a text file.
/// Font is changed from default (Courier New) for some East Asian languages to make text more readable.
/// </summary>
/// <remarks>
/// Result is not exactly identical since sometimes MS Word sets font in a bit strange way: for two nearest
/// chars in a same Unicode block it often assigns different fonts.
/// </remarks>
class TxtFontSetter extends DocumentVisitor
{
    /// <summary>
    /// Sets font to the passed run depending on language of its text. If necessary,
    /// the run is split to several ones.
    /// </summary>
    public /*override*/ /*VisitorAction*/int visitRun(Run run)
    {
        String text = run.getText();
        if (!StringUtil.hasChars(text))
            return VisitorAction.CONTINUE;

        String fontName = getCharFont(text.charAt(0));
        int textStart = 0;
        for (int i = 1; i < text.length(); i++)
        {
            String currentFontName = getCharFont(text.charAt(i));
            if (!msString.equals(fontName, currentFontName))
            {
                splitRun(run, i - textStart, fontName);

                fontName = currentFontName;
                textStart = i;
            }
        }

        run.getFont().setName(fontName);

        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Gets preferred font for the specified char.
    /// </summary>
    static String getCharFont(char ch)
    {
        final String DEFAULT_CHINESE_FONT = "Microsoft JhengHei";
        final String DEFAULT_KOREAN_FONT = "Malgun Gothic";
        final String OTHER_ASIAN_FONT = "MS Gothic";
        final String YA_HEI_FONT = "Microsoft YaHei";
        final String YI_BAITI_FONT = "Microsoft Yi Baiti";

        // Skip all checks if European language.
        if (ch < 0x1100)
            return DEFAULT_FONT;

        if (((ch >= 0x4E00) && (ch <= 0x9FFF)) || //CJK Unified Ideographs. Word uses Microsoft JhengHei and MS Gothic
            ((ch >= 0x2E80) && (ch <= 0x2FEF)) || //CJK Radicals Supplement, Kangxi Radicals
            ((ch >= 0x3100) && (ch <= 0x312F)) || //Bopomofo
            ((ch >= 0x3190) && (ch <= 0x31EF)) || //Kanbun, Bopomofo Extended, CJK Strokes,
            ((ch >= 0xFE30) && (ch <= 0xFE4F)))   //CJK Compatibility Forms
            return DEFAULT_CHINESE_FONT;

        if (((ch >= 0x3000) && (ch <= 0x30FF)) || //CJK Symbols and Punctuation, Hiragana, Katakana
            ((ch >= 0x31F0) && (ch <= 0x31FF)) || //Katakana Phonetic Extensions
            ((ch >= 0x3220) && (ch <= 0x325F)) || //Enclosed CJK Letters and Months
            ((ch >= 0x3280) && (ch <= 0x32FF)) || //Enclosed CJK Letters and Months
            ((ch >= 0x3300) && (ch <= 0x33FF)) || //CJK Compatibility
            ((ch >= 0xFA10) && (ch <= 0xFAFF)))   //CJK Compatibility Ideographs
            return OTHER_ASIAN_FONT;

        // Hangul
        if (((ch >= 0x1100) && (ch <= 0x11FF)) || //Hangul Jamo
            ((ch >= 0x3130) && (ch <= 0x318F)) || //Hangul Compatibility Jamo
            ((ch >= 0xA960) && (ch <= 0xA97F)) || //Hangul Jamo Extended-A
            ((ch >= 0xAC00) && (ch <= 0xD7FF)))   //Hangul Syllables, Hangul Jamo Extended-B
            return DEFAULT_KOREAN_FONT;

        // CJK
        if (((ch >= 0x3200) && (ch <= 0x321F)) || //Enclosed CJK Letters and Months
            ((ch >= 0x3260) && (ch <= 0x327F)) || //Enclosed CJK Letters and Months
            ((ch >= 0x3400) && (ch <= 0x4DBF)) || //CJK Unified Ideographs Extension A
            ((ch >= 0xF900) && (ch <= 0xFA0F)))   //CJK Compatibility Ideographs
            return DEFAULT_KOREAN_FONT;

        if ((ch >= 0x2FF0) && (ch <= 0x2FFF))     //Ideographic Description Characters
            return YA_HEI_FONT;

        if ((ch >= 0xA000) && (ch <= 0xA4CF))     //Yi Syllables, Yi Radicals
            return YI_BAITI_FONT;

        return DEFAULT_FONT;
    }

    /// <summary>
    /// Splits the specified base run.
    /// </summary>
    /// <param name="baseRun">A run to split.</param>
    /// <param name="position">Position in text of the run, at which to split.</param>
    /// <param name="fontName">Font name for the left run.</param>
    private static void splitRun(Run baseRun, int position, String fontName)
    {
        Run run = new Run(baseRun.getDocument(), baseRun.getText().substring((0), (0) + (position)), AttrCollectionExtension.deepClone(baseRun.getRunPr()));
        run.getFont().setName(fontName);
        baseRun.insertPrevious(run);

        baseRun.setText(baseRun.getText().substring(position));
    }

    static final String DEFAULT_FONT = "Courier New";
}

