// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


// Most of the members are taken from Microsoft Office 2003 Edition XML Schema References: Schema References | Word | Concepts | Using WordprocessingML (look in the end of article).
// Names are made similar to VBA ( referenced in http://msdn.microsoft.com/library/default.asp?url=/library/en-us/vbawd11/html/wohowConstants_HV01049731.asp ).
/// <summary>
/// Specifies the number style for a list, footnotes and endnotes, page numbers.
/// </summary>
/// <dev>
/// Should keep members of this enum in sync with <see cref="Aspose.Numbering.NumberStyleCore"/>.
/// </dev>
public /*enum*/ final class NumberStyle
{
    private NumberStyle(){}
    
    /// <summary>
    /// Arabic numbering (1, 2, 3, ...)
    /// </summary>
    public static final int ARABIC = 0;

    /// <summary>
    /// Upper case Roman (I, II, III, ...)
    /// </summary>
    public static final int UPPERCASE_ROMAN = 1;

    /// <summary>
    /// Lower case Roman (i, ii, iii, ...)
    /// </summary>
    public static final int LOWERCASE_ROMAN = 2;

    /// <summary>
    /// Upper case Letter (A, B, C, ...)
    /// </summary>
    public static final int UPPERCASE_LETTER = 3;

    /// <summary>
    /// Lower case letter (a, b, c, ...)
    /// </summary>
    public static final int LOWERCASE_LETTER = 4;

    /// <summary>
    /// Ordinal (1st, 2nd, 3rd, ...)
    /// </summary>
    public static final int ORDINAL = 5;

    /// <summary>
    /// Numbered (One, Two, Three, ...)
    /// </summary>
    public static final int NUMBER = 6;

    /// <summary>
    /// Ordinal (text) (First, Second, Third, ...)
    /// </summary>
    public static final int ORDINAL_TEXT = 7;

    /// <summary>
    /// Hexadecimal: 8, 9, A, B, C, D, E, F, 10, 11, 12
    /// </summary>
    public static final int HEX = 8;

    /// <summary>
    /// Chicago Manual of Style: *, †, †
    /// </summary>
    public static final int CHICAGO_MANUAL = 9;

    /// <summary>
    /// Ideograph-digital
    /// </summary>
    public static final int KANJI = 10;

    /// <summary>
    /// Japanese counting
    /// </summary>
    public static final int KANJI_DIGIT = 11;

    /// <summary>
    /// Aiueo
    /// </summary>
    public static final int AIUEO_HALF_WIDTH = 12;

    /// <summary>
    /// Iroha
    /// </summary>
    public static final int IROHA_HALF_WIDTH = 13;

    /// <summary>
    /// Full-width Arabic: 1, 2, 3, 4
    /// </summary>
    public static final int ARABIC_FULL_WIDTH = 14;

    /// <summary>
    /// Half-width Arabic: 1, 2, 3, 4
    /// </summary>
    public static final int ARABIC_HALF_WIDTH = 15;

    /// <summary>
    /// Japanese legal
    /// </summary>
    public static final int KANJI_TRADITIONAL = 16;

    /// <summary>
    /// Japanese digital ten thousand
    /// </summary>
    public static final int KANJI_TRADITIONAL_2 = 17;

    /// <summary>
    /// Enclosed circles
    /// </summary>
    public static final int NUMBER_IN_CIRCLE = 18;

    /// <summary>
    /// Decimal full width: 1, 2, 3, 4
    /// </summary>
    public static final int DECIMAL_FULL_WIDTH = 19;

    /// <summary>
    /// Aiueo full width
    /// </summary>
    public static final int AIUEO = 20;

    /// <summary>
    /// Iroha full width
    /// </summary>
    public static final int IROHA = 21;

    /// <summary>
    /// Leading Zero (01, 02,..., 09, 10, 11,..., 99, 100, 101,...)
    /// </summary>
    public static final int LEADING_ZERO = 22;

    /// <summary>
    /// Bullet (check the character code in the text)
    /// </summary>
    public static final int BULLET = 23;

    /// <summary>
    /// Korean Ganada
    /// </summary>
    public static final int GANADA = 24;

    /// <summary>
    /// Korea Chosung
    /// </summary>
    public static final int CHOSUNG = 25;

    /// <summary>
    /// Enclosed full stop
    /// </summary>
    public static final int GB_1 = 26;

    /// <summary>
    /// Enclosed parenthesis
    /// </summary>
    public static final int GB_2 = 27;

    /// <summary>
    /// Enclosed circle Chinese
    /// </summary>
    public static final int GB_3 = 28;

    /// <summary>
    /// Ideograph enclosed circle
    /// </summary>
    public static final int GB_4 = 29;

    /// <summary>
    /// Ideograph traditional
    /// </summary>
    public static final int ZODIAC_1 = 30;

    /// <summary>
    /// Ideograph Zodiac
    /// </summary>
    public static final int ZODIAC_2 = 31;

    /// <summary>
    /// Ideograph Zodiac traditional
    /// </summary>
    public static final int ZODIAC_3 = 32;

    /// <summary>
    /// Taiwanese counting
    /// </summary>
    public static final int TRAD_CHIN_NUM_1 = 33;

    /// <summary>
    /// Ideograph legal traditional
    /// </summary>
    public static final int TRAD_CHIN_NUM_2 = 34;

    /// <summary>
    /// Taiwanese counting thousand
    /// </summary>
    public static final int TRAD_CHIN_NUM_3 = 35;

    /// <summary>
    /// Taiwanese digital
    /// </summary>
    public static final int TRAD_CHIN_NUM_4 = 36;

    /// <summary>
    /// Chinese counting
    /// </summary>
    public static final int SIMP_CHIN_NUM_1 = 37;

    /// <summary>
    /// Chinese legal simplified
    /// </summary>
    public static final int SIMP_CHIN_NUM_2 = 38;

    /// <summary>
    /// Chinese counting thousand
    /// </summary>
    public static final int SIMP_CHIN_NUM_3 = 39;

    /// <summary>
    /// Chinese (not implemented)
    /// </summary>
    public static final int SIMP_CHIN_NUM_4 = 40;

    /// <summary>
    /// Korean digital
    /// </summary>
    public static final int HANJA_READ = 41;

    /// <summary>
    /// Korean counting
    /// </summary>
    public static final int HANJA_READ_DIGIT = 42;

    /// <summary>
    /// Korea legal
    /// </summary>
    public static final int HANGUL = 43;

    /// <summary>
    /// Korea digital2
    /// </summary>
    public static final int HANJA = 44;

    /// <summary>
    /// Hebrew-1
    /// </summary>
    public static final int HEBREW_1 = 45;

    /// <summary>
    /// Arabic alpha
    /// </summary>
    public static final int ARABIC_1 = 46;

    /// <summary>
    /// Hebrew-2
    /// </summary>
    public static final int HEBREW_2 = 47;

    /// <summary>
    /// Arabic abjad
    /// </summary>
    public static final int ARABIC_2 = 48;

    /// <summary>
    /// Hindi vowels
    /// </summary>
    public static final int HINDI_LETTER_1 = 49;

    /// <summary>
    /// Hindi consonants
    /// </summary>
    public static final int HINDI_LETTER_2 = 50;

    /// <summary>
    /// Hindi numbers
    /// </summary>
    public static final int HINDI_ARABIC = 51;

    /// <summary>
    /// Hindi descriptive (cardinals)
    /// </summary>
    public static final int HINDI_CARDINAL_TEXT = 52;

    /// <summary>
    /// Thai letters
    /// </summary>
    public static final int THAI_LETTER = 53;

    /// <summary>
    /// Thai numbers
    /// </summary>
    public static final int THAI_ARABIC = 54;

    /// <summary>
    /// Thai descriptive (cardinals)
    /// </summary>
    public static final int THAI_CARDINAL_TEXT = 55;

    /// <summary>
    /// Vietnamese descriptive (cardinals)
    /// </summary>
    public static final int VIET_CARDINAL_TEXT = 56;

    /// <summary>
    /// Page number format: - 1 -, - 2 -, - 3 -, - 4 -
    /// </summary>
    public static final int NUMBER_IN_DASH = 57;

    /// <summary>
    /// Lowercase Russian alphabet
    /// </summary>
    public static final int LOWERCASE_RUSSIAN = 58;

    /// <summary>
    /// Uppercase Russian alphabet
    /// </summary>
    public static final int UPPERCASE_RUSSIAN = 59;

    /// <summary>
    /// No bullet or number.
    /// </summary>
    public static final int NONE = 255;

    /// <summary>
    /// Custom number format. It is supported by DOCX format only.
    /// </summary>
    public static final int CUSTOM = 0xFF00;

    // The following NumberStyle members are additionally described in http://msdn.microsoft.com/library/default.asp?url=/library/en-us/vbawd11/html/wohowConstants_HV01049731.asp
    //   PictureBullet    249
    //   Legal            253
    //   LegalLZ        254
    // They have no analogs in WordML.

    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer NumberStyle value.
    /// </summary>
    public static String getName(/*NumberStyle*/int numberStyle)
    {
    	switch (numberStyle)
    	{
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
    		case NONE: return "NONE";
    		case CUSTOM: return "CUSTOM";
    		default: return "Unknown NumberStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer NumberStyle value.
    /// </summary>
    public static String toString(/*NumberStyle*/int numberStyle)
    {
    	switch (numberStyle)
    	{
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
    		case NONE: return "None";
    		case CUSTOM: return "Custom";
    		default: return "Unknown NumberStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by NumberStyle name.
    /// </summary>
    public static /*NumberStyle*/int fromName(String numberStyleName)
    {
    	if ("ARABIC".equals(numberStyleName)) return ARABIC;
    	if ("UPPERCASE_ROMAN".equals(numberStyleName)) return UPPERCASE_ROMAN;
    	if ("LOWERCASE_ROMAN".equals(numberStyleName)) return LOWERCASE_ROMAN;
    	if ("UPPERCASE_LETTER".equals(numberStyleName)) return UPPERCASE_LETTER;
    	if ("LOWERCASE_LETTER".equals(numberStyleName)) return LOWERCASE_LETTER;
    	if ("ORDINAL".equals(numberStyleName)) return ORDINAL;
    	if ("NUMBER".equals(numberStyleName)) return NUMBER;
    	if ("ORDINAL_TEXT".equals(numberStyleName)) return ORDINAL_TEXT;
    	if ("HEX".equals(numberStyleName)) return HEX;
    	if ("CHICAGO_MANUAL".equals(numberStyleName)) return CHICAGO_MANUAL;
    	if ("KANJI".equals(numberStyleName)) return KANJI;
    	if ("KANJI_DIGIT".equals(numberStyleName)) return KANJI_DIGIT;
    	if ("AIUEO_HALF_WIDTH".equals(numberStyleName)) return AIUEO_HALF_WIDTH;
    	if ("IROHA_HALF_WIDTH".equals(numberStyleName)) return IROHA_HALF_WIDTH;
    	if ("ARABIC_FULL_WIDTH".equals(numberStyleName)) return ARABIC_FULL_WIDTH;
    	if ("ARABIC_HALF_WIDTH".equals(numberStyleName)) return ARABIC_HALF_WIDTH;
    	if ("KANJI_TRADITIONAL".equals(numberStyleName)) return KANJI_TRADITIONAL;
    	if ("KANJI_TRADITIONAL_2".equals(numberStyleName)) return KANJI_TRADITIONAL_2;
    	if ("NUMBER_IN_CIRCLE".equals(numberStyleName)) return NUMBER_IN_CIRCLE;
    	if ("DECIMAL_FULL_WIDTH".equals(numberStyleName)) return DECIMAL_FULL_WIDTH;
    	if ("AIUEO".equals(numberStyleName)) return AIUEO;
    	if ("IROHA".equals(numberStyleName)) return IROHA;
    	if ("LEADING_ZERO".equals(numberStyleName)) return LEADING_ZERO;
    	if ("BULLET".equals(numberStyleName)) return BULLET;
    	if ("GANADA".equals(numberStyleName)) return GANADA;
    	if ("CHOSUNG".equals(numberStyleName)) return CHOSUNG;
    	if ("GB_1".equals(numberStyleName)) return GB_1;
    	if ("GB_2".equals(numberStyleName)) return GB_2;
    	if ("GB_3".equals(numberStyleName)) return GB_3;
    	if ("GB_4".equals(numberStyleName)) return GB_4;
    	if ("ZODIAC_1".equals(numberStyleName)) return ZODIAC_1;
    	if ("ZODIAC_2".equals(numberStyleName)) return ZODIAC_2;
    	if ("ZODIAC_3".equals(numberStyleName)) return ZODIAC_3;
    	if ("TRAD_CHIN_NUM_1".equals(numberStyleName)) return TRAD_CHIN_NUM_1;
    	if ("TRAD_CHIN_NUM_2".equals(numberStyleName)) return TRAD_CHIN_NUM_2;
    	if ("TRAD_CHIN_NUM_3".equals(numberStyleName)) return TRAD_CHIN_NUM_3;
    	if ("TRAD_CHIN_NUM_4".equals(numberStyleName)) return TRAD_CHIN_NUM_4;
    	if ("SIMP_CHIN_NUM_1".equals(numberStyleName)) return SIMP_CHIN_NUM_1;
    	if ("SIMP_CHIN_NUM_2".equals(numberStyleName)) return SIMP_CHIN_NUM_2;
    	if ("SIMP_CHIN_NUM_3".equals(numberStyleName)) return SIMP_CHIN_NUM_3;
    	if ("SIMP_CHIN_NUM_4".equals(numberStyleName)) return SIMP_CHIN_NUM_4;
    	if ("HANJA_READ".equals(numberStyleName)) return HANJA_READ;
    	if ("HANJA_READ_DIGIT".equals(numberStyleName)) return HANJA_READ_DIGIT;
    	if ("HANGUL".equals(numberStyleName)) return HANGUL;
    	if ("HANJA".equals(numberStyleName)) return HANJA;
    	if ("HEBREW_1".equals(numberStyleName)) return HEBREW_1;
    	if ("ARABIC_1".equals(numberStyleName)) return ARABIC_1;
    	if ("HEBREW_2".equals(numberStyleName)) return HEBREW_2;
    	if ("ARABIC_2".equals(numberStyleName)) return ARABIC_2;
    	if ("HINDI_LETTER_1".equals(numberStyleName)) return HINDI_LETTER_1;
    	if ("HINDI_LETTER_2".equals(numberStyleName)) return HINDI_LETTER_2;
    	if ("HINDI_ARABIC".equals(numberStyleName)) return HINDI_ARABIC;
    	if ("HINDI_CARDINAL_TEXT".equals(numberStyleName)) return HINDI_CARDINAL_TEXT;
    	if ("THAI_LETTER".equals(numberStyleName)) return THAI_LETTER;
    	if ("THAI_ARABIC".equals(numberStyleName)) return THAI_ARABIC;
    	if ("THAI_CARDINAL_TEXT".equals(numberStyleName)) return THAI_CARDINAL_TEXT;
    	if ("VIET_CARDINAL_TEXT".equals(numberStyleName)) return VIET_CARDINAL_TEXT;
    	if ("NUMBER_IN_DASH".equals(numberStyleName)) return NUMBER_IN_DASH;
    	if ("LOWERCASE_RUSSIAN".equals(numberStyleName)) return LOWERCASE_RUSSIAN;
    	if ("UPPERCASE_RUSSIAN".equals(numberStyleName)) return UPPERCASE_RUSSIAN;
    	if ("NONE".equals(numberStyleName)) return NONE;
    	if ("CUSTOM".equals(numberStyleName)) return CUSTOM;
    	throw new IllegalArgumentException("Unknown NumberStyle name.");
    }
    
    /// <summary>
    /// Returns array of NumberStyle values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
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
    		NONE,
    		CUSTOM,
    	};
    }

    public static final int length = 62;
}

