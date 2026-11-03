// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/03/2012 by Andrey Soldatov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import java.util.ArrayList;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.Globalization.msCultureInfo;


/// <summary>
/// Converts a number to text using a specific style.
/// </summary>
public /*static*/ class NumberConverterCore
{
	/* Simulation of static class by using private constructor */
	private NumberConverterCore()
	{}

    /// <summary>
    /// Converts an int value into a string using the specified number style.
    /// </summary>
    /// <param name="value">The value to convert.</param>
    /// <param name="numberStyle">The number style to use.</param>
    /// <param name="isFirstLetterUppercase">Whether to make the first letter uppercase for textual formats:
    /// <see cref="NumberStyleCore.Number"/> and <see cref="NumberStyleCore.OrdinalText"/>.</param>
    /// <param name="language">The language to use in conversion.</param>
    public static String numberToString(long value, /*NumberStyleCore*/int numberStyle, boolean isFirstLetterUppercase, /*Language*/int language)
    {
        switch (numberStyle)
        {
            case NumberStyleCore.AIUEO:
                return JapaneseNumber.toAiueo(value, false);
            case NumberStyleCore.AIUEO_HALF_WIDTH:
                return JapaneseNumber.toAiueo(value, true);
            case NumberStyleCore.ARABIC:
                return toArabic(value);
            case NumberStyleCore.ARABIC_FULL_WIDTH:
                return toArabicFullWidth(value);
            case NumberStyleCore.BULLET:
                Debug.fail("Bullets must not be handled by NumberConverter.");
                return "";
            case NumberStyleCore.HEBREW_1:
                return HebrewNumber.toHebrew1(value);
            case NumberStyleCore.HEBREW_2:
                return HebrewNumber.toHebrew2(value);
            case NumberStyleCore.HEX:
                return toHex(value);
            case NumberStyleCore.IROHA:
                return JapaneseNumber.toIroha(value);
            case NumberStyleCore.KANJI:
                // Yes. ToJapaneseDigital corresponds to NumberStyle.Kanji, not NumberStyle.KanjiDigit.
                return JapaneseNumber.toJapaneseDigital(value, 0);
            case NumberStyleCore.KANJI_DIGIT:
                return JapaneseNumber.toJapaneseCounting(value);
            case NumberStyleCore.LEADING_ZERO:
                return FormatterPal.intToStrD2(value);
            case NumberStyleCore.LEADING_ZERO_2:
                return FormatterPal.intToStrD3(value);
            case NumberStyleCore.LEADING_ZERO_3:
                return FormatterPal.intToStrD4(value);
            case NumberStyleCore.LEADING_ZERO_4:
                return FormatterPal.intToStrD5(value);
            case NumberStyleCore.LOWERCASE_LETTER:
                return toLowercaseLetter(value, language);
            case NumberStyleCore.LOWERCASE_ROMAN:
                return toLowercaseRoman(value);
            case NumberStyleCore.LOWERCASE_RUSSIAN:
                return toLowercaseLetter(value, Language.RUSSIAN_RUSSIA);
            case NumberStyleCore.NUMBER:
                return TextualNumber.toFullString(value, true, isFirstLetterUppercase, language);
            case NumberStyleCore.NUMBER_IN_CIRCLE:
                return toNumberInCircle(value);
            case NumberStyleCore.NUMBER_IN_DASH:
                return toNumberInDash(value);
            case NumberStyleCore.ORDINAL:
                return OrdinalNumber.toOrdinalNumber(value);
            case NumberStyleCore.ORDINAL_TEXT:
                return TextualNumber.toFullString(value, false, isFirstLetterUppercase, language);
            case NumberStyleCore.UPPERCASE_LETTER:
                return toUppercaseLetter(value, language);
            case NumberStyleCore.UPPERCASE_ROMAN:
                return toUppercaseRoman(value);
            case NumberStyleCore.UPPERCASE_RUSSIAN:
                return toUppercaseLetter(value, Language.RUSSIAN_RUSSIA);
            case NumberStyleCore.CHICAGO_MANUAL:
                return toChicagoManualOfStyle(value);
            case NumberStyleCore.TRAD_CHIN_NUM_2:
            case NumberStyleCore.TRAD_CHIN_NUM_3:
            case NumberStyleCore.SIMP_CHIN_NUM_3:
            case NumberStyleCore.SIMP_CHIN_NUM_2:
            case NumberStyleCore.SIMP_CHIN_NUM_1:
            // WORDSNET-28954 Implemented taiwaneseCounting numbering.
            case NumberStyleCore.TRAD_CHIN_NUM_1:
                return ChineseNumber.toChineseCountingSystem(value, numberStyle);
            case NumberStyleCore.ARABIC_1:
            case NumberStyleCore.ARABIC_2:
                return toArabicAlphaOrAbjad(value, numberStyle);
            case NumberStyleCore.ZODIAC_1:
                return ZodiacNumber.toZodiac1(value);
            case NumberStyleCore.ZODIAC_2:
                return ZodiacNumber.toZodiac2(value);
            case NumberStyleCore.ZODIAC_3:
                return ZodiacNumber.toZodiac3(value);
            case NumberStyleCore.GB_1:
                return toDecimalEnclosedFullstop(value);
            case NumberStyleCore.GB_2:
                return toDecimalEnclosedParen(value);
            case NumberStyleCore.GB_3:
                return toDecimalEnclosedCircleChinese(value);
            case NumberStyleCore.GB_4:
                return toIdeographEnclosedCircle(value);
            case NumberStyleCore.NONE:
                return "";
            case NumberStyleCore.THAI_LETTER:
                // WORDSNET-16501 Process "ThaiLetter" number style.
                return toLowercaseLetter(value, Language.THAI);
            case NumberStyleCore.VIET_CARDINAL_TEXT:
                return VietnameseNumber.toVietnameseCardinal(value);
            case NumberStyleCore.THAI_ARABIC:
                return ThaiArabicNumber.toThaiArabic(value);
            // WORDSNET-26909 Implemented Korean Ganada format.
            case NumberStyleCore.GANADA:
                return KoreanNumber.toGanada(value);
            // WORDSNET-27187 Implemented KoreanDigital2 format.
            case NumberStyleCore.HANJA:
                return KoreanNumber.toKoreanDigital2(value);
            // WORDSNET-27573 Implemented Chosung format.
            case NumberStyleCore.CHOSUNG:
                return KoreanNumber.toChosung(value);
            // WORDSNET-27573 Implemented Korean Legal format.
            case NumberStyleCore.HANGUL:
                return KoreanNumber.toHangul(value);
            default:
                return toArabic(value);
        }
    }

    /// <summary>
    /// Converts an int value into a string using the specified number style and the locale identifier.
    /// </summary>
    public static String numberToLocalizedString(long value, /*NumberStyleCore*/int numberStyle, boolean isFirstLetterUppercase, int localeId)
    {
        switch (numberStyle)
        {
            case NumberStyleCore.ORDINAL:
                return OrdinalNumber.toLocalizedOrdinalNumber(value, localeId);
            default:
                return numberToString(value, numberStyle, isFirstLetterUppercase, (/*Language*/int)localeId);
        }
    }

    /// <summary>
    /// Parses the specified sequence of characters to its numeric representation. 'A' means 0, 'B' means 1,
    /// ... , 'Z' means 25, 'AA' means 26, 'AB' means 27 and so forth. The characters are case insensitive.
    /// If was passed invalid column name returns negative value as error indication
    /// </summary>
    public static int parseExcelColumnName(String text)
    {
        // WORDSNET-6456 Instead of throwing exception we return a negative value as error indication.
        // This is needed to mimic Word's behavior.
        if (!StringUtil.hasChars(text) || (text.length() > MAX_EXCEL_COLUMN_NAME_LENGTH))
            return INVALID_COLUMN_INDEX;

        long result = 0;

        for (int i = 0; i < text.length() - 1; i++)
        {
            result += StringUtil.letterToInt(text.charAt(i)) + 1;
            result *= ALPHABET_LENGTH;
        }

        result += StringUtil.letterToInt(text.charAt(text.length() - 1));

        if (result > Integer.MAX_VALUE)
            return INVALID_COLUMN_INDEX;

        return (int)result;
    }

    /// <summary>
    /// Converts the specified value to its alphabetic representation. 0 means 'A', 1 means 'B', ...,
    /// 25 means 'Z', 26 means 'AA', 27 means 'AB' and so forth.
    /// If was passed invalid column index returns a null.
    /// </summary>
    public static String toExcelColumnName(int value)
    {
        // WORDSNET-6456 Instead of throwing an exception, we return a null, and process it in the appropriate places.
        // This is needed to mimic Word's behavior.
        if (value < 0)
            return null;

        int pos = MAX_EXCEL_COLUMN_NAME_LENGTH;
        StringBuilder stringBuilder = new StringBuilder("FXSHRXX");

        while (value >= 0)
        {
            stringBuilder.setCharAt(--pos, (char)(value % ALPHABET_LENGTH + 'A'));
            value = value / ALPHABET_LENGTH - 1;
        }

        return stringBuilder.substring((pos), (pos) + (MAX_EXCEL_COLUMN_NAME_LENGTH - pos));
    }

    /// <summary>
    /// MS Words supports numbers 1-392 for <see cref="NumberStyleCore.Hebrew1"/>, <see cref="NumberStyleCore.Arabic1"/>
    /// and <see cref="NumberStyleCore.Arabic2"/> styles and cycles larger numbers from 1 again. And so we do.
    /// </summary>
    static int getCycledValue(long value)
    {
        return (int)((value - 1) % 392) + 1;
    }

    /// <summary>
    /// Returns an Arabic number in dash (- 1 -, - 2 -, - 3 -, ...).
    /// </summary>
    private static String toNumberInDash(long value)
    {
        return msString.format("- {0} -", value);
    }

    /// <summary>
    /// Returns an Arabic number (1, 2, 3...).
    /// </summary>
    private static String toArabic(long value)
    {
        return Long.toString(value);
    }

    /// <summary>
    /// Returns a hexadecimal number (1CD...).
    /// </summary>
    private static String toHex(long value)
    {
        return ((value >= Integer.MIN_VALUE) && (value <= Integer.MAX_VALUE))
            ? FormatterPal.intToStrX((int)value)
            : FormatterPal.int64ToStrX(value);
    }

    /// <summary>
    /// Converts to symbols defined in Chicago (Chicago Manual of Style), see ISO29500 17.18.59 ST_NumberFormat for @ details.
    /// </summary>
    private static String toChicagoManualOfStyle(long value)
    {
        return toAlphabetCore(value, "*†‡§");
    }

    /// <summary>
    /// Returns a lower case Roman (i, ii, iii, ...).
    /// </summary>
    private static String toLowercaseRoman(long value)
    {
        Debug.assertTrue((value >= 0) && (value <= 32767));

        ArrayList<Integer> thousands = GroupSplitter.splitToGroups(value, 3);
        StringBuilder stringBuilder = new StringBuilder();

        if (thousands.size() > 1)
            for (int i = 0; i < thousands.get(1); i++)
                stringBuilder.append(ROMAN_THOUSAND);

        ArrayList<Integer> digits = GroupSplitter.splitToGroups(thousands.get(0), 1);
        for (int i = digits.size() - 1; i >= 0; i--)
            msStringBuilder.append(stringBuilder, G_ROMAN_DIGITS[i][digits.get(i)]);

        return stringBuilder.toString();
    }

    /// <summary>
    /// Returns an upper case Roman (I, II, III, ...).
    /// </summary>
    private static String toUppercaseRoman(long value)
    {
        // We use the invariant version of ToUpper here, because this method must be locale-insensitive.
        // For example, we don't want 'i' to turn into 'İ' in the Turkish locale.
        return msString.toUpperInvariant(toLowercaseRoman(value));
    }

    private static String toArabicFullWidth(long value)
    {
        ArrayList<Integer> digits = GroupSplitter.splitToGroups(value, 1);
        StringBuilder stringBuilder = new StringBuilder();

        // The 'Fullwidth Digit Zero' char has Unicode code 0xFF10.
        for (int i = digits.size() - 1; i >= 0; i--)
            stringBuilder.append((char)('０' + digits.get(i)));

        return stringBuilder.toString();
    }

    /// <summary>
    /// Returns a number in circle (①, ②, ③, ..., ⑳, 21, ...).
    /// </summary>
    /// <remarks>
    /// MS Word does not allow 0 (zero) value for this numbering style but I don't see a reason
    /// why don't return "0" for the case. This probably will not happen for real documents.
    /// MS Word shows all numbers larger than 20 by Arabic style.
    /// </remarks>
    private static String toNumberInCircle(long value)
    {
        Debug.assertTrue(value >= 0);
        return toUnicodeCharacter(value, 0x2460, 0x2473);
    }

    /// <summary>
    /// Returns a number converted to the Arabic Alpha or the Arabic Abjad numeral system.
    /// </summary>
    private static String toArabicAlphaOrAbjad(long value, /*NumberStyleCore*/int style)
    {
        Debug.assertTrue(value >= 0);
        Debug.assertTrue(style == NumberStyleCore.ARABIC_1 || style == NumberStyleCore.ARABIC_2);

        if (value <= 0)
            return "";

        String digits = (style == NumberStyleCore.ARABIC_1 ? ARABIC_ALPHA : ARABIC_ABJAD);
        int cycledValue = getCycledValue(value) - 1;
        return msString.newString(digits.charAt(cycledValue % digits.length()), cycledValue / digits.length() + 1);
    }

    /// <summary>
    /// Returns a lower case or uppercase letter format.
    /// For now, used for Russian, English, Turkish, Thai alphabets and ChicagoManualOfStyle.
    ///  </summary>
    private static String toAlphabetCore(long value, String alphabet)
    {
        // WORDSNET-5451 Do not throw here but simply return an empty string.
        if (value < 1)
            return "";

        if (value > Integer.MAX_VALUE)
            return "";

        int intValue = (int)value;

        intValue--;

        int repeat = intValue / alphabet.length();
        int posInAlphabet = intValue - (repeat * alphabet.length());

        return msString.newString(alphabet.charAt(posInAlphabet), repeat + 1);
    }

    /// <summary>
    /// Returns a lower case letter using specified <paramref name="language"/>.
    /// </summary>
    private static String toLowercaseLetter(long value, /*Language*/int language)
    {
        switch (language)
        {
            case Language.GREEK_GREECE:
            {
                return GreekNumber.toGreek(value);
            }
            default:
            {
                // By default return repeated letters of a specified language.
                String alphabet = getAlphabetLetters(language);
                return toAlphabetCore(value, alphabet);
            }
        }
    }

    /// <summary>
    /// Returns a upper case letter using specified <paramref name="language"/>.
    /// </summary>
    private static String toUppercaseLetter(long value, /*Language*/int language)
    {
        return msString.toUpper(toLowercaseLetter(value, language), new msCultureInfo((int)language));
    }

    /// <summary>
    /// Returns a set of letters (alphabet) for the specified <paramref name="language"/> in a lower case.
    /// </summary>
    private static String getAlphabetLetters(/*Language*/int language)
    {
        switch (language)
        {
            case Language.RUSSIAN_RUSSIA:
                return "абвгдежзиклмнопрстуфхцчшщыэюя";
            case Language.TURKISH_TURKEY:
                return "abcçdefgğhıijklmnoöprsştuüvyz";
            case Language.THAI:
                // Thai letters, which used in the Word lists.
                // Thai letters range starts at 0x0E01 and ends at 0x0E2E.
                // However, MSW does not use these chars: 0x0E03, 0x0E05, 0x0E06, 0x0E24, 0x0E26.
                return "กขคงจฉชซฌญฎฏฐฑฒณดตถทธนบปผฝพฟภมยรลวศษสหฬอฮ";
            case Language.SWEDISH_SWEDEN:
                return "abcdefghijklmnopqrstuvwxyzåäö";
            default:
                // Return English by default.
                return "abcdefghijklmnopqrstuvwxyz";
        }
    }

    /// <summary>
    /// Returns decimal numbers followed by a period: ⒈, ⒉, ⒊, ...
    /// </summary>
    private static String toDecimalEnclosedFullstop(long value)
    {
        // The numbers 1-20 are represented with Unicode characters U+2488 – U+249B respectively.
        return toUnicodeCharacter(value, 0x2488, 0x249B);
    }

    /// <summary>
    /// Returns decimal numbers enclosed in parenthesis: ⑴, ⑵, ⑶, ...
    /// </summary>
    private static String toDecimalEnclosedParen(long value)
    {
        // The numbers 1-20 are represented with Unicode characters U+2474 – U+2487 respectively.
        return toUnicodeCharacter(value, 0x2474, 0x2487);
    }

    /// <summary>
    /// Returns decimal numbers enclosed in a circle: ①, ②, ③, ...
    /// </summary>
    private static String toDecimalEnclosedCircleChinese(long value)
    {
        // Identical to decimalEnclosedCircle.
        return toNumberInCircle(value);
    }

    /// <summary>
    /// Returns ideographs enclosed in a circle: ㈠, ㈡, ㈢, ...
    /// </summary>
    private static String toIdeographEnclosedCircle(long value)
    {
        // The numbers 1-10 are represented with Unicode characters U+3220 – U+3229 respectively.
        return toUnicodeCharacter(value, 0x3220, 0x3229);
    }

    /// <summary>
    /// Converts numbers 1 - N to Unicode characters U+<paramref name="minCharCode"/> – U+<paramref name="maxCharCode"/> respectively.
    /// For all other values, returns an Arabic number.
    /// </summary>
    private static String toUnicodeCharacter(long value, int minCharCode, int maxCharCode)
    {
        long valueCharCode = value - 1 + minCharCode;
        if (valueCharCode >= minCharCode && valueCharCode <= maxCharCode)
            return Character.toString(((char)valueCharCode));

        return toArabic(value);
    }

    private static /*final*/ String[][] G_ROMAN_DIGITS =
    {
        { "", "i", "ii", "iii", "iv", "v", "vi", "vii", "viii", "ix" },
        { "", "x", "xx", "xxx", "xl", "l", "lx", "lxx", "lxxx", "xc" },
        { "", "c", "cc", "ccc", "cd", "d", "dc", "dcc", "dccc", "cm" }
    };

    private static final char ROMAN_THOUSAND = 'm';

    /// <summary>
    /// List of letters that represents numerals in Arabic Alpha numbering style.
    /// </summary>
    private static final String ARABIC_ALPHA = "أبتثجحخدذرزسشصضطظعغفقكلمنهوي";
    /// <summary>
    /// List of letters that represents numerals in Arabic Abjad numbering style.
    /// </summary>
    private static final String ARABIC_ABJAD = "أبجدهوزحطيكلمنسعفصقرشتثخذضغظ";

    private static final int ALPHABET_LENGTH = 26;

    /// <summary>
    /// Length of int.MaxValue ~ "FXSHRXX".
    /// </summary>
    private static final int MAX_EXCEL_COLUMN_NAME_LENGTH = 7;

    private static final int INVALID_COLUMN_INDEX = -1;
}

