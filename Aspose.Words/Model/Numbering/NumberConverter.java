// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/03/2012 by Andrey Soldatov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.NumberStyleCore;
import com.aspose.Language;
import com.aspose.NumberConverterCore;
import com.aspose.StringUtil;
import com.aspose.ms.System.Threading.CurrentThread;
import com.aspose.ms.System.msString;


/// <summary>
/// Converts a number into a specific style.
/// </summary>
/// <dev>
/// This class is a facade for <see cref="NumberConverterCore"/> accepting <see cref="NumberStyle"/> instead of
/// <see cref="NumberStyleCore"/>.
/// </dev>
/*static*/ class NumberConverter
{
	/* Simulation of static class by using private constructor */
	private NumberConverter()
	{}

    /// <summary>
    /// Converts an int value into a string using the specified number style.
    /// </summary>
    /// <param name="value">The value to convert.</param>
    /// <param name="numberStyle">The number style to use.</param>
    /// <param name="isFirstLetterUppercase">Whether to make the first letter uppercase for textual formats:
    /// <see cref="NumberStyle.Number"/> and <see cref="NumberStyle.OrdinalText"/>.</param>
    static String numberToString(int value, /*NumberStyle*/int numberStyle, boolean isFirstLetterUppercase)
    {
        return numberToString(value, numberStyle, "", isFirstLetterUppercase);
    }

    /// <summary>
    /// Converts an int value into a string using the specified number style.
    /// </summary>
    /// <param name="value">The value to convert.</param>
    /// <param name="numberStyle">The number style to use.</param>
    /// <param name="customNumberStyle">Custom number style string. Meaningful if <paramref name="numberStyle"/> is <see cref="NumberStyle.Custom"/></param>
    /// <param name="isFirstLetterUppercase">Whether to make the first letter uppercase for textual formats:
    /// <see cref="NumberStyle.Number"/> and <see cref="NumberStyle.OrdinalText"/>.</param>
    static String numberToString(int value, /*NumberStyle*/int numberStyle, String customNumberStyle, boolean isFirstLetterUppercase)
    {
        /*NumberStyleCore*/int numberStyleCore = toNumberStyleCore(numberStyle, customNumberStyle);

        // By default, language will be English.
        /*Language*/int language = getLanguage(customNumberStyle);

        return NumberConverterCore.numberToString(value, numberStyleCore, isFirstLetterUppercase, language);
    }

    /// <summary>
    /// Converts an int value into a string using the specified number style and the locale identifier (localeId).
    /// </summary>
    static String numberToLocalizedString(long value, /*NumberStyle*/int numberStyle, String customNumberStyle, boolean isFirstLetterUppercase, int localeId)
    {
        /*NumberStyleCore*/int numberStyleCore = toNumberStyleCore(numberStyle, customNumberStyle);

        // For a moment language is used for all 'letter' number styles,
        // such as NumberStyleCore.UppercaseLetter, NumberStyleCore.LowercaseLetter and so on.
        // So, when custom style is defined with some of the letter styles,
        // then language should be obtained from the custom style string itself.
        if ((numberStyleCore == NumberStyleCore.LOWERCASE_LETTER) || (numberStyleCore == NumberStyleCore.UPPERCASE_LETTER))
        {
            // WORDSNET-23954 Determine language by custom style only when the custom style is defined.
            // Also, Word replaces all locales except of SwedishSweden (and this is quite strange) with EnglishUs locale
            // when retrieves localized string for list label. Probably, there are some other locales for which it does
            // the same, but I found only Swedish for a moment.
            if (StringUtil.hasChars(customNumberStyle))
            {
                localeId = (int)getLanguage(customNumberStyle);
            }
            else if ((/*Language*/int)localeId != Language.SWEDISH_SWEDEN)
            {
                localeId = (int)Language.ENGLISH_US;
            }
        }

        return NumberConverterCore.numberToLocalizedString(value, numberStyleCore, isFirstLetterUppercase, localeId);
    }

    /// <summary>
    /// Converts an int value into a string using the specified number style and the current locale.
    /// </summary>
    static String numberToLocalizedString(long value, /*NumberStyle*/int numberStyle, boolean isFirstLetterUppercase)
    {
        int localeId = CurrentThread.getCurrentCulture().getLCID();
        return numberToLocalizedString(value, numberStyle, isFirstLetterUppercase, localeId);
    }

    /// <summary>
    /// Converts an int value into a string using the specified number style and the locale identifier (localeId).
    /// </summary>
    static String numberToLocalizedString(long value, /*NumberStyle*/int numberStyle, boolean isFirstLetterUppercase, int localeId)
    {
        return numberToLocalizedString(value, numberStyle, "", isFirstLetterUppercase, localeId);
    }

    /// <summary>
    /// Parses the specified sequence of characters to its numeric representation. 'A' means 0, 'B' means 1,
    /// ... , 'Z' means 25, 'AA' means 26, 'AB' means 27 and so forth. The characters are case insensitive.
    /// If was passed invalid column name returns negative value as error indication
    /// </summary>
    static int parseExcelColumnName(String text)
    {
        return NumberConverterCore.parseExcelColumnName(text);
    }

    /// <summary>
    /// Converts the specified value to its alphabetic representation. 0 means 'A', 1 means 'B', ...,
    /// 25 means 'Z', 26 means 'AA', 27 means 'AB' and so forth.
    /// If was passed invalid column index returns a null.
    /// </summary>
    static String toExcelColumnName(int value)
    {
        return NumberConverterCore.toExcelColumnName(value);
    }

    /// <summary>
    /// Converts <see cref="NumberStyle"/> to <see cref="NumberStyleCore"/>.
    /// </summary>
    /// <param name="numberStyle">Number style to convert.</param>
    /// <param name="customNumberStyle">Custom number style string. Meaningful if <paramref name="numberStyle"/> is <see cref="NumberStyle.Custom"/></param>
    private static /*NumberStyleCore*/int toNumberStyleCore(/*NumberStyle*/int numberStyle, String customNumberStyle)
    {
        switch (numberStyle)
        {
            case NumberStyle.NONE:
                return NumberStyleCore.NONE;
            case NumberStyle.ARABIC:
                return NumberStyleCore.ARABIC;
            case NumberStyle.UPPERCASE_ROMAN:
                return NumberStyleCore.UPPERCASE_ROMAN;
            case NumberStyle.LOWERCASE_ROMAN:
                return NumberStyleCore.LOWERCASE_ROMAN;
            case NumberStyle.UPPERCASE_LETTER:
                return NumberStyleCore.UPPERCASE_LETTER;
            case NumberStyle.LOWERCASE_LETTER:
                return NumberStyleCore.LOWERCASE_LETTER;
            case NumberStyle.ORDINAL:
                return NumberStyleCore.ORDINAL;
            case NumberStyle.NUMBER:
                return NumberStyleCore.NUMBER;
            case NumberStyle.ORDINAL_TEXT:
                return NumberStyleCore.ORDINAL_TEXT;
            case NumberStyle.HEX:
                return NumberStyleCore.HEX;
            case NumberStyle.CHICAGO_MANUAL:
                return NumberStyleCore.CHICAGO_MANUAL;
            case NumberStyle.KANJI:
                return NumberStyleCore.KANJI;
            case NumberStyle.KANJI_DIGIT:
                return NumberStyleCore.KANJI_DIGIT;
            case NumberStyle.AIUEO_HALF_WIDTH:
                return NumberStyleCore.AIUEO_HALF_WIDTH;
            case NumberStyle.IROHA_HALF_WIDTH:
                return NumberStyleCore.IROHA_HALF_WIDTH;
            case NumberStyle.ARABIC_FULL_WIDTH:
                return NumberStyleCore.ARABIC_FULL_WIDTH;
            case NumberStyle.ARABIC_HALF_WIDTH:
                return NumberStyleCore.ARABIC_HALF_WIDTH;
            case NumberStyle.KANJI_TRADITIONAL:
                return NumberStyleCore.KANJI_TRADITIONAL;
            case NumberStyle.KANJI_TRADITIONAL_2:
                return NumberStyleCore.KANJI_TRADITIONAL_2;
            case NumberStyle.NUMBER_IN_CIRCLE:
                return NumberStyleCore.NUMBER_IN_CIRCLE;
            case NumberStyle.DECIMAL_FULL_WIDTH:
                return NumberStyleCore.DECIMAL_FULL_WIDTH;
            case NumberStyle.AIUEO:
                return NumberStyleCore.AIUEO;
            case NumberStyle.IROHA:
                return NumberStyleCore.IROHA;
            case NumberStyle.LEADING_ZERO:
                return NumberStyleCore.LEADING_ZERO;
            case NumberStyle.BULLET:
                return NumberStyleCore.BULLET;
            case NumberStyle.GANADA:
                return NumberStyleCore.GANADA;
            case NumberStyle.CHOSUNG:
                return NumberStyleCore.CHOSUNG;
            case NumberStyle.GB_1:
                return NumberStyleCore.GB_1;
            case NumberStyle.GB_2:
                return NumberStyleCore.GB_2;
            case NumberStyle.GB_3:
                return NumberStyleCore.GB_3;
            case NumberStyle.GB_4:
                return NumberStyleCore.GB_4;
            case NumberStyle.ZODIAC_1:
                return NumberStyleCore.ZODIAC_1;
            case NumberStyle.ZODIAC_2:
                return NumberStyleCore.ZODIAC_2;
            case NumberStyle.ZODIAC_3:
                return NumberStyleCore.ZODIAC_3;
            case NumberStyle.TRAD_CHIN_NUM_1:
                return NumberStyleCore.TRAD_CHIN_NUM_1;
            case NumberStyle.TRAD_CHIN_NUM_2:
                return NumberStyleCore.TRAD_CHIN_NUM_2;
            case NumberStyle.TRAD_CHIN_NUM_3:
                return NumberStyleCore.TRAD_CHIN_NUM_3;
            case NumberStyle.TRAD_CHIN_NUM_4:
                return NumberStyleCore.TRAD_CHIN_NUM_4;
            case NumberStyle.SIMP_CHIN_NUM_1:
                return NumberStyleCore.SIMP_CHIN_NUM_1;
            case NumberStyle.SIMP_CHIN_NUM_2:
                return NumberStyleCore.SIMP_CHIN_NUM_2;
            case NumberStyle.SIMP_CHIN_NUM_3:
                return NumberStyleCore.SIMP_CHIN_NUM_3;
            case NumberStyle.SIMP_CHIN_NUM_4:
                return NumberStyleCore.SIMP_CHIN_NUM_4;
            case NumberStyle.HANJA_READ:
                return NumberStyleCore.HANJA_READ;
            case NumberStyle.HANJA_READ_DIGIT:
                return NumberStyleCore.HANJA_READ_DIGIT;
            case NumberStyle.HANGUL:
                return NumberStyleCore.HANGUL;
            case NumberStyle.HANJA:
                return NumberStyleCore.HANJA;
            case NumberStyle.HEBREW_1:
                return NumberStyleCore.HEBREW_1;
            case NumberStyle.ARABIC_1:
                return NumberStyleCore.ARABIC_1;
            case NumberStyle.HEBREW_2:
                return NumberStyleCore.HEBREW_2;
            case NumberStyle.ARABIC_2:
                return NumberStyleCore.ARABIC_2;
            case NumberStyle.HINDI_LETTER_1:
                return NumberStyleCore.HINDI_LETTER_1;
            case NumberStyle.HINDI_LETTER_2:
                return NumberStyleCore.HINDI_LETTER_2;
            case NumberStyle.HINDI_ARABIC:
                return NumberStyleCore.HINDI_ARABIC;
            case NumberStyle.HINDI_CARDINAL_TEXT:
                return NumberStyleCore.HINDI_CARDINAL_TEXT;
            case NumberStyle.THAI_LETTER:
                return NumberStyleCore.THAI_LETTER;
            case NumberStyle.THAI_ARABIC:
                return NumberStyleCore.THAI_ARABIC;
            case NumberStyle.THAI_CARDINAL_TEXT:
                return NumberStyleCore.THAI_CARDINAL_TEXT;
            case NumberStyle.VIET_CARDINAL_TEXT:
                return NumberStyleCore.VIET_CARDINAL_TEXT;
            case NumberStyle.NUMBER_IN_DASH:
                return NumberStyleCore.NUMBER_IN_DASH;
            case NumberStyle.LOWERCASE_RUSSIAN:
                return NumberStyleCore.LOWERCASE_RUSSIAN;
            case NumberStyle.UPPERCASE_RUSSIAN:
                return NumberStyleCore.UPPERCASE_RUSSIAN;
            case NumberStyle.CUSTOM:
                return toNumberStyleCore(customNumberStyle);
            default:
                throw new IllegalArgumentException(msString.format("Unexpected number style: '{0}'", numberStyle));
        }
    }

    /// <summary>
    /// Returns corresponding <see cref="NumberStyleCore"/> by the specified <paramref name="customNumberStyle"/> string.
    /// </summary>
    /// <remarks>
    /// In MS Word we can define various custom number formats, such as:
    /// "1, 2, 3, ...",
    /// "I, II, III, ...",
    /// "i, ii, iii, ...",
    /// "A, B, C, ..."
    /// "a, b, c, ...",
    /// "1st, 2nd, 3rd, ..." and so on.
    /// But seems MS Word writes them all as general number formats: 'decimal', 'upperRoman', 'lowerRoman' and so on.
    /// Even "01, 02, 03, ..." custom style it writes just as 'decimalZero'.
    /// Only "001, 002, 003, ...", "0001, 0002, 0003, ...",  "00001, 00002, 00003, ..."
    /// or locale-specific (for example Turkish lower letters = "a, ç, ĝ, ...")
    /// number formats MS Word writes as 'custom'.
    /// </remarks>
    private static /*NumberStyleCore*/int toNumberStyleCore(String customNumberStyle)
    {
        if (!StringUtil.hasChars(customNumberStyle))
            return NumberStyleCore.NONE;

        char firstLetter = customNumberStyle.charAt(0);
        if (Character.isLowerCase(firstLetter))
            return NumberStyleCore.LOWERCASE_LETTER;
        if (Character.isUpperCase(firstLetter))
            return NumberStyleCore.UPPERCASE_LETTER;

        switch (gStringSwitchMap.of(customNumberStyle))
        {
            case /*"001, 002, 003, ..."*/0:
                return NumberStyleCore.LEADING_ZERO_2;
            case /*"0001, 0002, 0003, ..."*/1:
                return NumberStyleCore.LEADING_ZERO_3;
            case /*"00001, 00002, 00003, ..."*/2:
                return NumberStyleCore.LEADING_ZERO_4;
            default:
            {
                throw new IllegalArgumentException(
                    msString.format("Unexpected custom number format style: '{0}'", customNumberStyle));
            }
        }
    }

    /// <summary>
    /// Returns corresponding <see cref="Language"/> by the <paramref name="customNumberStyle"/> string.
    /// </summary>
    private static /*Language*/int getLanguage(String customNumberStyle)
    {
        switch (gStringSwitchMap.of(customNumberStyle))
        {
            case /*"A, Ç, Ĝ, ..."*/3:
            case /*"a, ç, ĝ, ..."*/4:
                return Language.TURKISH_TURKEY;
            case /*"Α, Β, Γ, ..."*/5:
            case /*"α, β, γ, ..."*/6:
                return Language.GREEK_GREECE;
            default:
                return Language.ENGLISH_US;
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"001, 002, 003, ...",
		"0001, 0002, 0003, ...",
		"00001, 00002, 00003, ...",
		"A, Ç, Ĝ, ...",
		"a, ç, ĝ, ...",
		"Α, Β, Γ, ...",
		"α, β, γ, ..."
	);

}

