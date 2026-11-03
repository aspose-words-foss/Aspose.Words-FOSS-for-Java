// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/09/2012 by Denis Shvydkiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.LocaleClassifier;
import com.aspose.Language;

/// <summary>
/// Resolves width of characters in Asian text. The character width is used while choosing the font for rendering.
/// Refer to Unicode Standard Annex #11 (http://unicode.org/reports/tr11/) for more information.
/// </summary>
class EastAsianWidthResolver
{
    /// <summary>
    /// Indicates whether the specified character has wide width.
    /// </summary>
    /// <remarks>
    /// Currently, only Latin-1 Supplement U+00A0 - U+00FF Unicode block is supported.
    /// Support for other Unicode blocks of characters will be added once WORDSNET-6877 is resolved.
    /// </remarks>
    /// <param name="c">The character to evaluate.</param>
    /// <param name="localeIdFarEast">The Asian locale Id specified for the character.</param>
    /// <returns>True if the character has wide width; otherwise, false.</returns>
    static boolean isWideCharacter(int c, int localeIdFarEast)
    {
        return (getEastAsianWidth(c, localeIdFarEast) == EastAsianWidth.WIDE);
    }

    //>>>>>>>> #region  Implementation
    /// <summary>
    /// Returns width of the specified character.
    /// </summary>
    /// <remarks>
    /// The character width information is specified 
    /// in the Unicode standard (http://www.unicode.org/Public/UNIDATA/EastAsianWidth.txt).
    /// However, for some characters MS Word resolves width differently 
    /// than specified in the Unicode standard.
    /// <see href="gCharacterEastAsianWidth"/> and <see href="gCharacterEastAsianWidthNonAsianLocale"/>
    /// contains resolved ambiguous widths.
    /// </remarks>
    private static /*EastAsianWidth*/int getEastAsianWidth(int c, int localeIdFarEast)
    {
        /*EastAsianWidth*/int width = EastAsianWidth.WIDE;

        // Latin-1 Supplement Unicode block U+00A0 - U+00FF.
        if (c >= 0x00A0 && c <= 0x00FF)
        {
            // Most of the characters in this block have narrow width.
            width = EastAsianWidth.NARROW;

            // Resolve width to wide for characters with ambiguous width.
            if (isWideEastAsianWidth(c))
                width = EastAsianWidth.WIDE;
            // However, some characters have narrow width when locale is not Chinese or Japanese.
            if (!LocaleClassifier.isChineseOrJapanese(localeIdFarEast)
                && localeIdFarEast != (int)Language.NO_PROOF
                && isNarrowEastAsianWidthNonAsianLocale(c))
                width = EastAsianWidth.NARROW;
        }

        return width;
    }

    private static boolean isWideEastAsianWidth(int c)
    {
        // Latin-1 Supplement Unicode block
        switch (c)
        {
            case '\u00A1':
            case '\u00A4':
            case '\u00A7':
            case '\u00A8':
            case '\u00AA':
            case '\u00AD':
            case '\u00AF':
            case '\u00B0':
            case '\u00B1':
            case '\u00B2':
            case '\u00B3':
            case '\u00B4':
            case '\u00B6':
            case '\u00B7':
            case '\u00B8':
            case '\u00B9':
            case '\u00BA':
            case '\u00BC':
            case '\u00BD':
            case '\u00BE':
            case '\u00BF':
            case '\u00D7':
            case '\u00E0':
            case '\u00E1':
            case '\u00E8':
            case '\u00E9':
            case '\u00EA':
            case '\u00EC':
            case '\u00ED':
            case '\u00F2':
            case '\u00F3':
            case '\u00F7':
            case '\u00F9':
            case '\u00FA':
            case '\u00FC':
                return true;
            default:
                return false;
        }
    }

    private static boolean isNarrowEastAsianWidthNonAsianLocale(int c)
    {
        // Latin-1 Supplement Unicode block
        switch (c)
        {
            case '\u00E0':
            case '\u00E1':
            case '\u00E8':
            case '\u00E9':
            case '\u00EA':
            case '\u00EC':
            case '\u00ED':
            case '\u00F2':
            case '\u00F3':
            case '\u00F9':
            case '\u00FA':
            case '\u00FC':
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Identifies width of characters in Asian text.
    /// </summary>
    private /*enum*/ final class EastAsianWidth
    {
        private EastAsianWidth(){}
        
        public static final int WIDE = 0;
        public static final int NARROW = 1;

        public static final int length = 2;
    }

    //<<<<<<<< #endregion 
}

