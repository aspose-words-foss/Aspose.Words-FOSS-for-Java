// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2006 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies a number style for a number-to-text conversion.
/// </summary>
/// <dev>
/// This is a copycat of Aspose.Words.NumberStyle. Should keep members of these enums in sync.
/// </dev>
public /*enum*/ final class NumberStyleCore
{
    private NumberStyleCore(){}
    
    /// <summary>
    /// No bullet or number.
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    /// Arabic numbering (1, 2, 3, ...)
    /// </summary>
    public static final int ARABIC = 1;
    /// <summary>
    /// Upper case Roman (I, II, III, ...)
    /// </summary>
    public static final int UPPERCASE_ROMAN = 2;
    /// <summary>
    /// Lower case Roman (i, ii, iii, ...)
    /// </summary>
    public static final int LOWERCASE_ROMAN = 3;
    /// <summary>
    /// Upper case Letter (A, B, C, ...)
    /// </summary>
    public static final int UPPERCASE_LETTER = 4;
    /// <summary>
    /// Lower case letter (a, b, c, ...)
    /// </summary>
    public static final int LOWERCASE_LETTER = 5;
    /// <summary>
    /// Ordinal (1st, 2nd, 3rd, ...)
    /// </summary>
    public static final int ORDINAL = 6;
    /// <summary>
    /// Numbered (One, Two, Three, ...)
    /// </summary>
    public static final int NUMBER = 7;
    /// <summary>
    /// Ordinal (text) (First, Second, Third, ...)
    /// </summary>
    public static final int ORDINAL_TEXT = 8;
    /// <summary>
    /// Hexadecimal: 8, 9, A, B, C, D, E, F, 10, 11, 12
    /// </summary>
    public static final int HEX = 9;
    /// <summary>
    /// Chicago Manual of Style: *, †, †
    /// </summary>
    public static final int CHICAGO_MANUAL = 10;
    /// <summary>
    /// Ideograph-digital
    /// </summary>
    public static final int KANJI = 11;
    /// <summary>
    /// Japanese counting
    /// </summary>
    public static final int KANJI_DIGIT = 12;
    /// <summary>
    /// Aiueo
    /// </summary>
    public static final int AIUEO_HALF_WIDTH = 13;
    /// <summary>
    /// Iroha
    /// </summary>
    public static final int IROHA_HALF_WIDTH = 14;
    /// <summary>
    /// Full-width Arabic: 1, 2, 3, 4
    /// </summary>
    public static final int ARABIC_FULL_WIDTH = 15;
    /// <summary>
    /// Half-width Arabic: 1, 2, 3, 4
    /// </summary>
    public static final int ARABIC_HALF_WIDTH = 16;
    /// <summary>
    /// Japanese legal
    /// </summary>
    public static final int KANJI_TRADITIONAL = 17;
    /// <summary>
    /// Japanese digital ten thousand
    /// </summary>
    public static final int KANJI_TRADITIONAL_2 = 18;
    /// <summary>
    /// Enclosed circles
    /// </summary>
    public static final int NUMBER_IN_CIRCLE = 19;
    /// <summary>
    /// Decimal full width: 1, 2, 3, 4
    /// </summary>
    public static final int DECIMAL_FULL_WIDTH = 20;
    /// <summary>
    /// Aiueo full width
    /// </summary>
    public static final int AIUEO = 21;
    /// <summary>
    /// Iroha full width
    /// </summary>
    public static final int IROHA = 22;
    /// <summary>
    /// Leading Zero (01, 02,..., 09, 10, 11,..., 99, 100, 101,...)
    /// </summary>
    public static final int LEADING_ZERO = 23;
    /// <summary>
    /// Leading Zero (001, 002,..., 009, 010, 011,..., 099, 100, 101,...)
    /// </summary>
    public static final int LEADING_ZERO_2 = 24;
    /// <summary>
    /// Leading Zero (0001, 0002,..., 0009, 0010, 0011,..., 0099, 0100, 0101,..., 0999, 1000, 1001,...)
    /// </summary>
    public static final int LEADING_ZERO_3 = 25;
    /// <summary>
    /// Leading Zero (00001, 00002,..., 00009, 00010, 00011,..., 00099, 00100, 00101,..., 00999, 01000, 01001,..., 09999, 10000, 10001,...)
    /// </summary>
    public static final int LEADING_ZERO_4 = 26;
    /// <summary>
    /// Bullet (check the character code in the text)
    /// </summary>
    public static final int BULLET = 27;
    /// <summary>
    /// Korean Ganada
    /// </summary>
    public static final int GANADA = 28;
    /// <summary>
    /// Korea Chosung
    /// </summary>
    public static final int CHOSUNG = 29;
    /// <summary>
    /// Enclosed full stop
    /// </summary>
    public static final int GB_1 = 30;
    /// <summary>
    /// Enclosed parenthesis
    /// </summary>
    public static final int GB_2 = 31;
    /// <summary>
    /// Enclosed circle Chinese
    /// </summary>
    public static final int GB_3 = 32;
    /// <summary>
    /// Ideograph enclosed circle
    /// </summary>
    public static final int GB_4 = 33;
    /// <summary>
    /// Ideograph traditional
    /// </summary>
    public static final int ZODIAC_1 = 34;
    /// <summary>
    /// Ideograph Zodiac
    /// </summary>
    public static final int ZODIAC_2 = 35;
    /// <summary>
    /// Ideograph Zodiac traditional
    /// </summary>
    public static final int ZODIAC_3 = 36;
    /// <summary>
    /// Taiwanese counting
    /// </summary>
    public static final int TRAD_CHIN_NUM_1 = 37;
    /// <summary>
    /// Ideograph legal traditional
    /// </summary>
    public static final int TRAD_CHIN_NUM_2 = 38;
    /// <summary>
    /// Taiwanese counting thousand
    /// </summary>
    public static final int TRAD_CHIN_NUM_3 = 39;
    /// <summary>
    /// Taiwanese digital
    /// </summary>
    public static final int TRAD_CHIN_NUM_4 = 40;
    /// <summary>
    /// Chinese counting
    /// </summary>
    public static final int SIMP_CHIN_NUM_1 = 41;
    /// <summary>
    /// Chinese legal simplified
    /// </summary>
    public static final int SIMP_CHIN_NUM_2 = 42;
    /// <summary>
    /// Chinese counting thousand
    /// </summary>
    public static final int SIMP_CHIN_NUM_3 = 43;
    /// <summary>
    /// Chinese (not implemented)
    /// </summary>
    public static final int SIMP_CHIN_NUM_4 = 44;
    /// <summary>
    /// Korean digital
    /// </summary>
    public static final int HANJA_READ = 45;
    /// <summary>
    /// Korean counting
    /// </summary>
    public static final int HANJA_READ_DIGIT = 46;
    /// <summary>
    /// Korea legal
    /// </summary>
    public static final int HANGUL = 47;
    /// <summary>
    /// Korea digital2
    /// </summary>
    public static final int HANJA = 48;
    /// <summary>
    /// Hebrew-1
    /// </summary>
    public static final int HEBREW_1 = 49;
    /// <summary>
    /// Arabic alpha
    /// </summary>
    public static final int ARABIC_1 = 50;
    /// <summary>
    /// Hebrew-2
    /// </summary>
    public static final int HEBREW_2 = 51;
    /// <summary>
    /// Arabic abjad
    /// </summary>
    public static final int ARABIC_2 = 52;
    /// <summary>
    /// Hindi vowels
    /// </summary>
    public static final int HINDI_LETTER_1 = 53;
    /// <summary>
    /// Hindi consonants
    /// </summary>
    public static final int HINDI_LETTER_2 = 54;
    /// <summary>
    /// Hindi numbers
    /// </summary>
    public static final int HINDI_ARABIC = 55;
    /// <summary>
    /// Hindi descriptive (cardinals)
    /// </summary>
    public static final int HINDI_CARDINAL_TEXT = 56;
    /// <summary>
    /// Thai letters
    /// </summary>
    public static final int THAI_LETTER = 57;
    /// <summary>
    /// Thai numbers
    /// </summary>
    public static final int THAI_ARABIC = 58;
    /// <summary>
    /// Thai descriptive (cardinals)
    /// </summary>
    public static final int THAI_CARDINAL_TEXT = 59;
    /// <summary>
    /// Vietnamese descriptive (cardinals)
    /// </summary>
    public static final int VIET_CARDINAL_TEXT = 60;
    /// <summary>
    /// Page number format: - 1 -, - 2 -, - 3 -, - 4 -
    /// </summary>
    public static final int NUMBER_IN_DASH = 61;
    /// <summary>
    /// Lowercase Russian alphabet
    /// </summary>
    public static final int LOWERCASE_RUSSIAN = 62;
    /// <summary>
    /// Uppercase Russian alphabet
    /// </summary>
    public static final int UPPERCASE_RUSSIAN = 63;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer NumberStyleCore value.
    /// </summary>
    public static String getName(/*NumberStyleCore*/int numberStyleCore)
    {
    	switch (numberStyleCore)
    	{
    		case NONE: return "NONE";
    		case ARABIC: return "ARABIC";
    		case UPPERCASE_ROMAN: return "UPPERCASE_ROMAN";
    		case LOWERCASE_ROMAN: return "LOWERCASE_ROMAN";
    		case UPPERCASE_LETTER: return "UPPERCASE_LETTER";
    		case LOWERCASE_LETTER: return "LOWERCASE_LETTER";
    		case ORDINAL: return "ORDINAL";
    		case NUMBER: return "NUMBER";
    		case ORDINAL_TEXT: return "ORDINAL_TEXT";
    		case HEX: return "HEX";
    		case CHICAGO_MANUAL: return "CHICAGO_MANUAL";
    		case KANJI: return "KANJI";
    		case KANJI_DIGIT: return "KANJI_DIGIT";
    		case AIUEO_HALF_WIDTH: return "AIUEO_HALF_WIDTH";
    		case IROHA_HALF_WIDTH: return "IROHA_HALF_WIDTH";
    		case ARABIC_FULL_WIDTH: return "ARABIC_FULL_WIDTH";
    		case ARABIC_HALF_WIDTH: return "ARABIC_HALF_WIDTH";
    		case KANJI_TRADITIONAL: return "KANJI_TRADITIONAL";
    		case KANJI_TRADITIONAL_2: return "KANJI_TRADITIONAL_2";
    		case NUMBER_IN_CIRCLE: return "NUMBER_IN_CIRCLE";
    		case DECIMAL_FULL_WIDTH: return "DECIMAL_FULL_WIDTH";
    		case AIUEO: return "AIUEO";
    		case IROHA: return "IROHA";
    		case LEADING_ZERO: return "LEADING_ZERO";
    		case LEADING_ZERO_2: return "LEADING_ZERO_2";
    		case LEADING_ZERO_3: return "LEADING_ZERO_3";
    		case LEADING_ZERO_4: return "LEADING_ZERO_4";
    		case BULLET: return "BULLET";
    		case GANADA: return "GANADA";
    		case CHOSUNG: return "CHOSUNG";
    		case GB_1: return "GB_1";
    		case GB_2: return "GB_2";
    		case GB_3: return "GB_3";
    		case GB_4: return "GB_4";
    		case ZODIAC_1: return "ZODIAC_1";
    		case ZODIAC_2: return "ZODIAC_2";
    		case ZODIAC_3: return "ZODIAC_3";
    		case TRAD_CHIN_NUM_1: return "TRAD_CHIN_NUM_1";
    		case TRAD_CHIN_NUM_2: return "TRAD_CHIN_NUM_2";
    		case TRAD_CHIN_NUM_3: return "TRAD_CHIN_NUM_3";
    		case TRAD_CHIN_NUM_4: return "TRAD_CHIN_NUM_4";
    		case SIMP_CHIN_NUM_1: return "SIMP_CHIN_NUM_1";
    		case SIMP_CHIN_NUM_2: return "SIMP_CHIN_NUM_2";
    		case SIMP_CHIN_NUM_3: return "SIMP_CHIN_NUM_3";
    		case SIMP_CHIN_NUM_4: return "SIMP_CHIN_NUM_4";
    		case HANJA_READ: return "HANJA_READ";
    		case HANJA_READ_DIGIT: return "HANJA_READ_DIGIT";
    		case HANGUL: return "HANGUL";
    		case HANJA: return "HANJA";
    		case HEBREW_1: return "HEBREW_1";
    		case ARABIC_1: return "ARABIC_1";
    		case HEBREW_2: return "HEBREW_2";
    		case ARABIC_2: return "ARABIC_2";
    		case HINDI_LETTER_1: return "HINDI_LETTER_1";
    		case HINDI_LETTER_2: return "HINDI_LETTER_2";
    		case HINDI_ARABIC: return "HINDI_ARABIC";
    		case HINDI_CARDINAL_TEXT: return "HINDI_CARDINAL_TEXT";
    		case THAI_LETTER: return "THAI_LETTER";
    		case THAI_ARABIC: return "THAI_ARABIC";
    		case THAI_CARDINAL_TEXT: return "THAI_CARDINAL_TEXT";
    		case VIET_CARDINAL_TEXT: return "VIET_CARDINAL_TEXT";
    		case NUMBER_IN_DASH: return "NUMBER_IN_DASH";
    		case LOWERCASE_RUSSIAN: return "LOWERCASE_RUSSIAN";
    		case UPPERCASE_RUSSIAN: return "UPPERCASE_RUSSIAN";
    		default: return "Unknown NumberStyleCore value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer NumberStyleCore value.
    /// </summary>
    public static String toString(/*NumberStyleCore*/int numberStyleCore)
    {
    	switch (numberStyleCore)
    	{
    		case NONE: return "None";
    		case ARABIC: return "Arabic";
    		case UPPERCASE_ROMAN: return "UppercaseRoman";
    		case LOWERCASE_ROMAN: return "LowercaseRoman";
    		case UPPERCASE_LETTER: return "UppercaseLetter";
    		case LOWERCASE_LETTER: return "LowercaseLetter";
    		case ORDINAL: return "Ordinal";
    		case NUMBER: return "Number";
    		case ORDINAL_TEXT: return "OrdinalText";
    		case HEX: return "Hex";
    		case CHICAGO_MANUAL: return "ChicagoManual";
    		case KANJI: return "Kanji";
    		case KANJI_DIGIT: return "KanjiDigit";
    		case AIUEO_HALF_WIDTH: return "AiueoHalfWidth";
    		case IROHA_HALF_WIDTH: return "IrohaHalfWidth";
    		case ARABIC_FULL_WIDTH: return "ArabicFullWidth";
    		case ARABIC_HALF_WIDTH: return "ArabicHalfWidth";
    		case KANJI_TRADITIONAL: return "KanjiTraditional";
    		case KANJI_TRADITIONAL_2: return "KanjiTraditional2";
    		case NUMBER_IN_CIRCLE: return "NumberInCircle";
    		case DECIMAL_FULL_WIDTH: return "DecimalFullWidth";
    		case AIUEO: return "Aiueo";
    		case IROHA: return "Iroha";
    		case LEADING_ZERO: return "LeadingZero";
    		case LEADING_ZERO_2: return "LeadingZero2";
    		case LEADING_ZERO_3: return "LeadingZero3";
    		case LEADING_ZERO_4: return "LeadingZero4";
    		case BULLET: return "Bullet";
    		case GANADA: return "Ganada";
    		case CHOSUNG: return "Chosung";
    		case GB_1: return "GB1";
    		case GB_2: return "GB2";
    		case GB_3: return "GB3";
    		case GB_4: return "GB4";
    		case ZODIAC_1: return "Zodiac1";
    		case ZODIAC_2: return "Zodiac2";
    		case ZODIAC_3: return "Zodiac3";
    		case TRAD_CHIN_NUM_1: return "TradChinNum1";
    		case TRAD_CHIN_NUM_2: return "TradChinNum2";
    		case TRAD_CHIN_NUM_3: return "TradChinNum3";
    		case TRAD_CHIN_NUM_4: return "TradChinNum4";
    		case SIMP_CHIN_NUM_1: return "SimpChinNum1";
    		case SIMP_CHIN_NUM_2: return "SimpChinNum2";
    		case SIMP_CHIN_NUM_3: return "SimpChinNum3";
    		case SIMP_CHIN_NUM_4: return "SimpChinNum4";
    		case HANJA_READ: return "HanjaRead";
    		case HANJA_READ_DIGIT: return "HanjaReadDigit";
    		case HANGUL: return "Hangul";
    		case HANJA: return "Hanja";
    		case HEBREW_1: return "Hebrew1";
    		case ARABIC_1: return "Arabic1";
    		case HEBREW_2: return "Hebrew2";
    		case ARABIC_2: return "Arabic2";
    		case HINDI_LETTER_1: return "HindiLetter1";
    		case HINDI_LETTER_2: return "HindiLetter2";
    		case HINDI_ARABIC: return "HindiArabic";
    		case HINDI_CARDINAL_TEXT: return "HindiCardinalText";
    		case THAI_LETTER: return "ThaiLetter";
    		case THAI_ARABIC: return "ThaiArabic";
    		case THAI_CARDINAL_TEXT: return "ThaiCardinalText";
    		case VIET_CARDINAL_TEXT: return "VietCardinalText";
    		case NUMBER_IN_DASH: return "NumberInDash";
    		case LOWERCASE_RUSSIAN: return "LowercaseRussian";
    		case UPPERCASE_RUSSIAN: return "UppercaseRussian";
    		default: return "Unknown NumberStyleCore value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by NumberStyleCore name.
    /// </summary>
    public static /*NumberStyleCore*/int fromName(String numberStyleCoreName)
    {
    	if ("NONE".equals(numberStyleCoreName)) return NONE;
    	if ("ARABIC".equals(numberStyleCoreName)) return ARABIC;
    	if ("UPPERCASE_ROMAN".equals(numberStyleCoreName)) return UPPERCASE_ROMAN;
    	if ("LOWERCASE_ROMAN".equals(numberStyleCoreName)) return LOWERCASE_ROMAN;
    	if ("UPPERCASE_LETTER".equals(numberStyleCoreName)) return UPPERCASE_LETTER;
    	if ("LOWERCASE_LETTER".equals(numberStyleCoreName)) return LOWERCASE_LETTER;
    	if ("ORDINAL".equals(numberStyleCoreName)) return ORDINAL;
    	if ("NUMBER".equals(numberStyleCoreName)) return NUMBER;
    	if ("ORDINAL_TEXT".equals(numberStyleCoreName)) return ORDINAL_TEXT;
    	if ("HEX".equals(numberStyleCoreName)) return HEX;
    	if ("CHICAGO_MANUAL".equals(numberStyleCoreName)) return CHICAGO_MANUAL;
    	if ("KANJI".equals(numberStyleCoreName)) return KANJI;
    	if ("KANJI_DIGIT".equals(numberStyleCoreName)) return KANJI_DIGIT;
    	if ("AIUEO_HALF_WIDTH".equals(numberStyleCoreName)) return AIUEO_HALF_WIDTH;
    	if ("IROHA_HALF_WIDTH".equals(numberStyleCoreName)) return IROHA_HALF_WIDTH;
    	if ("ARABIC_FULL_WIDTH".equals(numberStyleCoreName)) return ARABIC_FULL_WIDTH;
    	if ("ARABIC_HALF_WIDTH".equals(numberStyleCoreName)) return ARABIC_HALF_WIDTH;
    	if ("KANJI_TRADITIONAL".equals(numberStyleCoreName)) return KANJI_TRADITIONAL;
    	if ("KANJI_TRADITIONAL_2".equals(numberStyleCoreName)) return KANJI_TRADITIONAL_2;
    	if ("NUMBER_IN_CIRCLE".equals(numberStyleCoreName)) return NUMBER_IN_CIRCLE;
    	if ("DECIMAL_FULL_WIDTH".equals(numberStyleCoreName)) return DECIMAL_FULL_WIDTH;
    	if ("AIUEO".equals(numberStyleCoreName)) return AIUEO;
    	if ("IROHA".equals(numberStyleCoreName)) return IROHA;
    	if ("LEADING_ZERO".equals(numberStyleCoreName)) return LEADING_ZERO;
    	if ("LEADING_ZERO_2".equals(numberStyleCoreName)) return LEADING_ZERO_2;
    	if ("LEADING_ZERO_3".equals(numberStyleCoreName)) return LEADING_ZERO_3;
    	if ("LEADING_ZERO_4".equals(numberStyleCoreName)) return LEADING_ZERO_4;
    	if ("BULLET".equals(numberStyleCoreName)) return BULLET;
    	if ("GANADA".equals(numberStyleCoreName)) return GANADA;
    	if ("CHOSUNG".equals(numberStyleCoreName)) return CHOSUNG;
    	if ("GB_1".equals(numberStyleCoreName)) return GB_1;
    	if ("GB_2".equals(numberStyleCoreName)) return GB_2;
    	if ("GB_3".equals(numberStyleCoreName)) return GB_3;
    	if ("GB_4".equals(numberStyleCoreName)) return GB_4;
    	if ("ZODIAC_1".equals(numberStyleCoreName)) return ZODIAC_1;
    	if ("ZODIAC_2".equals(numberStyleCoreName)) return ZODIAC_2;
    	if ("ZODIAC_3".equals(numberStyleCoreName)) return ZODIAC_3;
    	if ("TRAD_CHIN_NUM_1".equals(numberStyleCoreName)) return TRAD_CHIN_NUM_1;
    	if ("TRAD_CHIN_NUM_2".equals(numberStyleCoreName)) return TRAD_CHIN_NUM_2;
    	if ("TRAD_CHIN_NUM_3".equals(numberStyleCoreName)) return TRAD_CHIN_NUM_3;
    	if ("TRAD_CHIN_NUM_4".equals(numberStyleCoreName)) return TRAD_CHIN_NUM_4;
    	if ("SIMP_CHIN_NUM_1".equals(numberStyleCoreName)) return SIMP_CHIN_NUM_1;
    	if ("SIMP_CHIN_NUM_2".equals(numberStyleCoreName)) return SIMP_CHIN_NUM_2;
    	if ("SIMP_CHIN_NUM_3".equals(numberStyleCoreName)) return SIMP_CHIN_NUM_3;
    	if ("SIMP_CHIN_NUM_4".equals(numberStyleCoreName)) return SIMP_CHIN_NUM_4;
    	if ("HANJA_READ".equals(numberStyleCoreName)) return HANJA_READ;
    	if ("HANJA_READ_DIGIT".equals(numberStyleCoreName)) return HANJA_READ_DIGIT;
    	if ("HANGUL".equals(numberStyleCoreName)) return HANGUL;
    	if ("HANJA".equals(numberStyleCoreName)) return HANJA;
    	if ("HEBREW_1".equals(numberStyleCoreName)) return HEBREW_1;
    	if ("ARABIC_1".equals(numberStyleCoreName)) return ARABIC_1;
    	if ("HEBREW_2".equals(numberStyleCoreName)) return HEBREW_2;
    	if ("ARABIC_2".equals(numberStyleCoreName)) return ARABIC_2;
    	if ("HINDI_LETTER_1".equals(numberStyleCoreName)) return HINDI_LETTER_1;
    	if ("HINDI_LETTER_2".equals(numberStyleCoreName)) return HINDI_LETTER_2;
    	if ("HINDI_ARABIC".equals(numberStyleCoreName)) return HINDI_ARABIC;
    	if ("HINDI_CARDINAL_TEXT".equals(numberStyleCoreName)) return HINDI_CARDINAL_TEXT;
    	if ("THAI_LETTER".equals(numberStyleCoreName)) return THAI_LETTER;
    	if ("THAI_ARABIC".equals(numberStyleCoreName)) return THAI_ARABIC;
    	if ("THAI_CARDINAL_TEXT".equals(numberStyleCoreName)) return THAI_CARDINAL_TEXT;
    	if ("VIET_CARDINAL_TEXT".equals(numberStyleCoreName)) return VIET_CARDINAL_TEXT;
    	if ("NUMBER_IN_DASH".equals(numberStyleCoreName)) return NUMBER_IN_DASH;
    	if ("LOWERCASE_RUSSIAN".equals(numberStyleCoreName)) return LOWERCASE_RUSSIAN;
    	if ("UPPERCASE_RUSSIAN".equals(numberStyleCoreName)) return UPPERCASE_RUSSIAN;
    	throw new IllegalArgumentException("Unknown NumberStyleCore name.");
    }
    
    /// <summary>
    /// Returns array of NumberStyleCore values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		ARABIC,
    		UPPERCASE_ROMAN,
    		LOWERCASE_ROMAN,
    		UPPERCASE_LETTER,
    		LOWERCASE_LETTER,
    		ORDINAL,
    		NUMBER,
    		ORDINAL_TEXT,
    		HEX,
    		CHICAGO_MANUAL,
    		KANJI,
    		KANJI_DIGIT,
    		AIUEO_HALF_WIDTH,
    		IROHA_HALF_WIDTH,
    		ARABIC_FULL_WIDTH,
    		ARABIC_HALF_WIDTH,
    		KANJI_TRADITIONAL,
    		KANJI_TRADITIONAL_2,
    		NUMBER_IN_CIRCLE,
    		DECIMAL_FULL_WIDTH,
    		AIUEO,
    		IROHA,
    		LEADING_ZERO,
    		LEADING_ZERO_2,
    		LEADING_ZERO_3,
    		LEADING_ZERO_4,
    		BULLET,
    		GANADA,
    		CHOSUNG,
    		GB_1,
    		GB_2,
    		GB_3,
    		GB_4,
    		ZODIAC_1,
    		ZODIAC_2,
    		ZODIAC_3,
    		TRAD_CHIN_NUM_1,
    		TRAD_CHIN_NUM_2,
    		TRAD_CHIN_NUM_3,
    		TRAD_CHIN_NUM_4,
    		SIMP_CHIN_NUM_1,
    		SIMP_CHIN_NUM_2,
    		SIMP_CHIN_NUM_3,
    		SIMP_CHIN_NUM_4,
    		HANJA_READ,
    		HANJA_READ_DIGIT,
    		HANGUL,
    		HANJA,
    		HEBREW_1,
    		ARABIC_1,
    		HEBREW_2,
    		ARABIC_2,
    		HINDI_LETTER_1,
    		HINDI_LETTER_2,
    		HINDI_ARABIC,
    		HINDI_CARDINAL_TEXT,
    		THAI_LETTER,
    		THAI_ARABIC,
    		THAI_CARDINAL_TEXT,
    		VIET_CARDINAL_TEXT,
    		NUMBER_IN_DASH,
    		LOWERCASE_RUSSIAN,
    		UPPERCASE_RUSSIAN,
    	};
    }

    public static final int length = 64;
}

