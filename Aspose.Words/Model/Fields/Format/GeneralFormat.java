// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/12/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies a general format that is applied to a numeric, text, or any field result.
/// A field may have a combination of general formats.
/// </summary>
public /*enum*/ final class GeneralFormat
{
    private GeneralFormat(){}
    
    /// <summary>
    /// Used to specify a missing general format.
    /// </summary>
    public static final int NONE = 0;

    /// <summary>
    /// Numeric formatting. Formats a numeric result using hiragana characters in the traditional a-i-u-e-o order.
    /// </summary>
    public static final int AIUEO = 1;
    /// <summary>
    /// Numeric formatting. Formats a numeric result as one or more occurrences of an uppercase alphabetic Latin character.
    /// </summary>
    public static final int UPPERCASE_ALPHABETIC = 2;
    /// <summary>
    /// Numeric formatting. Formats a numeric result as one or more occurrences of an lowercase alphabetic Latin character.
    /// </summary>
    public static final int LOWERCASE_ALPHABETIC = 3;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using Arabic cardinal numerals.
    /// </summary>
    public static final int ARABIC = 4;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using ascending Abjad numerals.
    /// </summary>
    public static final int ARABIC_ABJAD = 5;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using characters in the Arabic alphabet.
    /// </summary>
    public static final int ARABIC_ALPHA = 6;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using Arabic cardinal numerals, with a prefix of "- " and a suffix of " -".
    /// </summary>
    public static final int ARABIC_DASH = 7;
    /// <summary>
    /// Numeric formatting. Formats a numeric result in the Thai counting system.
    /// </summary>
    public static final int BAHT_TEXT = 8;
    /// <summary>
    /// Numeric formatting. Cardinal text (One, Two, Three, ...).
    /// </summary>
    public static final int CARD_TEXT = 9;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using ascending numbers from the appropriate counting system.
    /// </summary>
    public static final int CHINESE_NUM_1 = 10;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using sequential numbers from the appropriate legal format.
    /// </summary>
    public static final int CHINESE_NUM_2 = 11;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using sequential numbers from the appropriate counting thousand system.
    /// </summary>
    public static final int CHINESE_NUM_3 = 12;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using sequential numbers from the Korean Chosung format.
    /// </summary>
    public static final int CHOSUNG = 13;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using decimal numbering enclosed in a circle, using the
    /// enclosed alphanumeric glyph character for numbers in the range 1–20.
    /// </summary>
    public static final int CIRCLE_NUM = 14;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using double-byte Arabic numbering.
    /// </summary>
    public static final int DB_CHAR = 15;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using sequential digital ideographs, using the appropriate character.
    /// </summary>
    public static final int DB_NUM_1 = 16;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using sequential numbers from the appropriate counting system.
    /// </summary>
    public static final int DB_NUM_2 = 17;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using sequential numbers from the appropriate legal counting system.
    /// </summary>
    public static final int DB_NUM_3 = 18;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using sequential numbers from the appropriate digital counting system.
    /// </summary>
    public static final int DB_NUM_4 = 19;
    /// <summary>
    /// Numeric formatting. Dollar text (One, Two, Three, ... + AND 55/100).
    /// </summary>
    public static final int DOLLAR_TEXT = 20;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using sequential numbers from the Korean Ganada format.
    /// </summary>
    public static final int GANADA = 21;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using decimal numbering followed by a period, using
    /// the enclosed alphanumeric glyph character.
    /// </summary>
    public static final int GB_1 = 22;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using decimal numbering enclosed in parenthesis,
    /// using the enclosed alphanumeric glyph character.
    /// </summary>
    public static final int GB_2 = 23;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using decimal numbering enclosed in a circle, using the
    /// enclosed alphanumeric glyph character.
    /// </summary>
    public static final int GB_3 = 24;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using decimal numbering enclosed in a circle, using the
    /// enclosed alphanumeric glyph character.
    /// </summary>
    public static final int GB_4 = 25;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using Hebrew numerals.
    /// </summary>
    public static final int HEBREW_1 = 26;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using the Hebrew alphabet.
    /// </summary>
    public static final int HEBREW_2 = 27;
    /// <summary>
    /// Numeric formatting. Formats the numeric result using uppercase hexadecimal digits.
    /// </summary>
    public static final int HEX = 28;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using Hindi numbers.
    /// </summary>
    public static final int HINDI_ARABIC = 29;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using sequential numbers from the Hindi counting system.
    /// </summary>
    public static final int HINDI_CARD_TEXT = 30;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using Hindi vowels.
    /// </summary>
    public static final int HINDI_LETTER_1 = 31;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using Hindi consonants.
    /// </summary>
    public static final int HINDI_LETTER_2 = 32;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using the Japanese iroha.
    /// </summary>
    public static final int IROHA = 33;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using a Japanese style using the appropriate counting system.
    /// </summary>
    public static final int KANJI_NUM_1 = 34;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using the appropriate counting system.
    /// </summary>
    public static final int KANJI_NUM_2 = 35;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using the appropriate counting system.
    /// </summary>
    public static final int KANJI_NUM_3 = 36;
    /// <summary>
    /// Numeric formatting. Ordinal (1st, 2nd, 3rd, ...).
    /// </summary>
    public static final int ORDINAL = 37;
    /// <summary>
    /// Numeric formatting. Ordinal text (First, Second, Third, ...).
    /// </summary>
    public static final int ORD_TEXT = 38;
    /// <summary>
    /// Numeric formatting. Uppercase Roman (I, II, III, ...).
    /// </summary>
    public static final int UPPERCASE_ROMAN = 39;
    /// <summary>
    /// Numeric formatting. Lowercase Roman (i, ii, iii, ...).
    /// </summary>
    public static final int LOWERCASE_ROMAN = 40;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using single-byte Arabic numbering.
    /// </summary>
    public static final int SB_CHAR = 41;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using Thai numbers.
    /// </summary>
    public static final int THAI_ARABIC = 42;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using sequential numbers from the Thai counting system.
    /// </summary>
    public static final int THAI_CARD_TEXT = 43;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using Thai letters.
    /// </summary>
    public static final int THAI_LETTER = 44;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using Vietnamese numerals.
    /// </summary>
    public static final int VIET_CARD_TEXT = 45;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using sequential numerical traditional ideographs.
    /// </summary>
    public static final int ZODIAC_1 = 46;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using sequential zodiac ideographs.
    /// </summary>
    public static final int ZODIAC_2 = 47;
    /// <summary>
    /// Numeric formatting. Formats a numeric result using sequential traditional zodiac ideographs.
    /// </summary>
    public static final int ZODIAC_3 = 48;

    /// <summary>
    /// Text formatting. Capitalizes the first letter of each word.
    /// </summary>
    public static final int CAPS = 49;
    /// <summary>
    /// Text formatting. Capitalizes the first letter of the first word.
    /// </summary>
    public static final int FIRST_CAP = 50;
    /// <summary>
    /// Text formatting. All letters are lowercase.
    /// </summary>
    public static final int LOWER = 51;
    /// <summary>
    /// Text formatting. All letters are uppercase.
    /// </summary>
    public static final int UPPER = 52;

    /// <summary>
    /// Field result formatting. The CHARFORMAT instruction.
    /// </summary>
    public static final int CHAR_FORMAT = 53;
    /// <summary>
    /// Field result formatting. The MERGEFORMAT instruction.
    /// </summary>
    public static final int MERGE_FORMAT = 54;
    /// <summary>
    /// Field result formatting. The MERGEFORMATINET instruction.
    /// </summary>
    public static final int MERGE_FORMAT_INET = 55;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer GeneralFormat value.
    /// </summary>
    public static String getName(/*GeneralFormat*/int generalFormat)
    {
    	switch (generalFormat)
    	{
    		case NONE: return "NONE";
    		case AIUEO: return "AIUEO";
    		case UPPERCASE_ALPHABETIC: return "UPPERCASE_ALPHABETIC";
    		case LOWERCASE_ALPHABETIC: return "LOWERCASE_ALPHABETIC";
    		case ARABIC: return "ARABIC";
    		case ARABIC_ABJAD: return "ARABIC_ABJAD";
    		case ARABIC_ALPHA: return "ARABIC_ALPHA";
    		case ARABIC_DASH: return "ARABIC_DASH";
    		case BAHT_TEXT: return "BAHT_TEXT";
    		case CARD_TEXT: return "CARD_TEXT";
    		case CHINESE_NUM_1: return "CHINESE_NUM_1";
    		case CHINESE_NUM_2: return "CHINESE_NUM_2";
    		case CHINESE_NUM_3: return "CHINESE_NUM_3";
    		case CHOSUNG: return "CHOSUNG";
    		case CIRCLE_NUM: return "CIRCLE_NUM";
    		case DB_CHAR: return "DB_CHAR";
    		case DB_NUM_1: return "DB_NUM_1";
    		case DB_NUM_2: return "DB_NUM_2";
    		case DB_NUM_3: return "DB_NUM_3";
    		case DB_NUM_4: return "DB_NUM_4";
    		case DOLLAR_TEXT: return "DOLLAR_TEXT";
    		case GANADA: return "GANADA";
    		case GB_1: return "GB_1";
    		case GB_2: return "GB_2";
    		case GB_3: return "GB_3";
    		case GB_4: return "GB_4";
    		case HEBREW_1: return "HEBREW_1";
    		case HEBREW_2: return "HEBREW_2";
    		case HEX: return "HEX";
    		case HINDI_ARABIC: return "HINDI_ARABIC";
    		case HINDI_CARD_TEXT: return "HINDI_CARD_TEXT";
    		case HINDI_LETTER_1: return "HINDI_LETTER_1";
    		case HINDI_LETTER_2: return "HINDI_LETTER_2";
    		case IROHA: return "IROHA";
    		case KANJI_NUM_1: return "KANJI_NUM_1";
    		case KANJI_NUM_2: return "KANJI_NUM_2";
    		case KANJI_NUM_3: return "KANJI_NUM_3";
    		case ORDINAL: return "ORDINAL";
    		case ORD_TEXT: return "ORD_TEXT";
    		case UPPERCASE_ROMAN: return "UPPERCASE_ROMAN";
    		case LOWERCASE_ROMAN: return "LOWERCASE_ROMAN";
    		case SB_CHAR: return "SB_CHAR";
    		case THAI_ARABIC: return "THAI_ARABIC";
    		case THAI_CARD_TEXT: return "THAI_CARD_TEXT";
    		case THAI_LETTER: return "THAI_LETTER";
    		case VIET_CARD_TEXT: return "VIET_CARD_TEXT";
    		case ZODIAC_1: return "ZODIAC_1";
    		case ZODIAC_2: return "ZODIAC_2";
    		case ZODIAC_3: return "ZODIAC_3";
    		case CAPS: return "CAPS";
    		case FIRST_CAP: return "FIRST_CAP";
    		case LOWER: return "LOWER";
    		case UPPER: return "UPPER";
    		case CHAR_FORMAT: return "CHAR_FORMAT";
    		case MERGE_FORMAT: return "MERGE_FORMAT";
    		case MERGE_FORMAT_INET: return "MERGE_FORMAT_INET";
    		default: return "Unknown GeneralFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer GeneralFormat value.
    /// </summary>
    public static String toString(/*GeneralFormat*/int generalFormat)
    {
    	switch (generalFormat)
    	{
    		case NONE: return "None";
    		case AIUEO: return "Aiueo";
    		case UPPERCASE_ALPHABETIC: return "UppercaseAlphabetic";
    		case LOWERCASE_ALPHABETIC: return "LowercaseAlphabetic";
    		case ARABIC: return "Arabic";
    		case ARABIC_ABJAD: return "ArabicAbjad";
    		case ARABIC_ALPHA: return "ArabicAlpha";
    		case ARABIC_DASH: return "ArabicDash";
    		case BAHT_TEXT: return "BahtText";
    		case CARD_TEXT: return "CardText";
    		case CHINESE_NUM_1: return "ChineseNum1";
    		case CHINESE_NUM_2: return "ChineseNum2";
    		case CHINESE_NUM_3: return "ChineseNum3";
    		case CHOSUNG: return "Chosung";
    		case CIRCLE_NUM: return "CircleNum";
    		case DB_CHAR: return "DBChar";
    		case DB_NUM_1: return "DBNum1";
    		case DB_NUM_2: return "DBNum2";
    		case DB_NUM_3: return "DBNum3";
    		case DB_NUM_4: return "DBNum4";
    		case DOLLAR_TEXT: return "DollarText";
    		case GANADA: return "Ganada";
    		case GB_1: return "GB1";
    		case GB_2: return "GB2";
    		case GB_3: return "GB3";
    		case GB_4: return "GB4";
    		case HEBREW_1: return "Hebrew1";
    		case HEBREW_2: return "Hebrew2";
    		case HEX: return "Hex";
    		case HINDI_ARABIC: return "HindiArabic";
    		case HINDI_CARD_TEXT: return "HindiCardText";
    		case HINDI_LETTER_1: return "HindiLetter1";
    		case HINDI_LETTER_2: return "HindiLetter2";
    		case IROHA: return "Iroha";
    		case KANJI_NUM_1: return "KanjiNum1";
    		case KANJI_NUM_2: return "KanjiNum2";
    		case KANJI_NUM_3: return "KanjiNum3";
    		case ORDINAL: return "Ordinal";
    		case ORD_TEXT: return "OrdText";
    		case UPPERCASE_ROMAN: return "UppercaseRoman";
    		case LOWERCASE_ROMAN: return "LowercaseRoman";
    		case SB_CHAR: return "SBChar";
    		case THAI_ARABIC: return "ThaiArabic";
    		case THAI_CARD_TEXT: return "ThaiCardText";
    		case THAI_LETTER: return "ThaiLetter";
    		case VIET_CARD_TEXT: return "VietCardText";
    		case ZODIAC_1: return "Zodiac1";
    		case ZODIAC_2: return "Zodiac2";
    		case ZODIAC_3: return "Zodiac3";
    		case CAPS: return "Caps";
    		case FIRST_CAP: return "FirstCap";
    		case LOWER: return "Lower";
    		case UPPER: return "Upper";
    		case CHAR_FORMAT: return "CharFormat";
    		case MERGE_FORMAT: return "MergeFormat";
    		case MERGE_FORMAT_INET: return "MergeFormatInet";
    		default: return "Unknown GeneralFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by GeneralFormat name.
    /// </summary>
    public static /*GeneralFormat*/int fromName(String generalFormatName)
    {
    	if ("NONE".equals(generalFormatName)) return NONE;
    	if ("AIUEO".equals(generalFormatName)) return AIUEO;
    	if ("UPPERCASE_ALPHABETIC".equals(generalFormatName)) return UPPERCASE_ALPHABETIC;
    	if ("LOWERCASE_ALPHABETIC".equals(generalFormatName)) return LOWERCASE_ALPHABETIC;
    	if ("ARABIC".equals(generalFormatName)) return ARABIC;
    	if ("ARABIC_ABJAD".equals(generalFormatName)) return ARABIC_ABJAD;
    	if ("ARABIC_ALPHA".equals(generalFormatName)) return ARABIC_ALPHA;
    	if ("ARABIC_DASH".equals(generalFormatName)) return ARABIC_DASH;
    	if ("BAHT_TEXT".equals(generalFormatName)) return BAHT_TEXT;
    	if ("CARD_TEXT".equals(generalFormatName)) return CARD_TEXT;
    	if ("CHINESE_NUM_1".equals(generalFormatName)) return CHINESE_NUM_1;
    	if ("CHINESE_NUM_2".equals(generalFormatName)) return CHINESE_NUM_2;
    	if ("CHINESE_NUM_3".equals(generalFormatName)) return CHINESE_NUM_3;
    	if ("CHOSUNG".equals(generalFormatName)) return CHOSUNG;
    	if ("CIRCLE_NUM".equals(generalFormatName)) return CIRCLE_NUM;
    	if ("DB_CHAR".equals(generalFormatName)) return DB_CHAR;
    	if ("DB_NUM_1".equals(generalFormatName)) return DB_NUM_1;
    	if ("DB_NUM_2".equals(generalFormatName)) return DB_NUM_2;
    	if ("DB_NUM_3".equals(generalFormatName)) return DB_NUM_3;
    	if ("DB_NUM_4".equals(generalFormatName)) return DB_NUM_4;
    	if ("DOLLAR_TEXT".equals(generalFormatName)) return DOLLAR_TEXT;
    	if ("GANADA".equals(generalFormatName)) return GANADA;
    	if ("GB_1".equals(generalFormatName)) return GB_1;
    	if ("GB_2".equals(generalFormatName)) return GB_2;
    	if ("GB_3".equals(generalFormatName)) return GB_3;
    	if ("GB_4".equals(generalFormatName)) return GB_4;
    	if ("HEBREW_1".equals(generalFormatName)) return HEBREW_1;
    	if ("HEBREW_2".equals(generalFormatName)) return HEBREW_2;
    	if ("HEX".equals(generalFormatName)) return HEX;
    	if ("HINDI_ARABIC".equals(generalFormatName)) return HINDI_ARABIC;
    	if ("HINDI_CARD_TEXT".equals(generalFormatName)) return HINDI_CARD_TEXT;
    	if ("HINDI_LETTER_1".equals(generalFormatName)) return HINDI_LETTER_1;
    	if ("HINDI_LETTER_2".equals(generalFormatName)) return HINDI_LETTER_2;
    	if ("IROHA".equals(generalFormatName)) return IROHA;
    	if ("KANJI_NUM_1".equals(generalFormatName)) return KANJI_NUM_1;
    	if ("KANJI_NUM_2".equals(generalFormatName)) return KANJI_NUM_2;
    	if ("KANJI_NUM_3".equals(generalFormatName)) return KANJI_NUM_3;
    	if ("ORDINAL".equals(generalFormatName)) return ORDINAL;
    	if ("ORD_TEXT".equals(generalFormatName)) return ORD_TEXT;
    	if ("UPPERCASE_ROMAN".equals(generalFormatName)) return UPPERCASE_ROMAN;
    	if ("LOWERCASE_ROMAN".equals(generalFormatName)) return LOWERCASE_ROMAN;
    	if ("SB_CHAR".equals(generalFormatName)) return SB_CHAR;
    	if ("THAI_ARABIC".equals(generalFormatName)) return THAI_ARABIC;
    	if ("THAI_CARD_TEXT".equals(generalFormatName)) return THAI_CARD_TEXT;
    	if ("THAI_LETTER".equals(generalFormatName)) return THAI_LETTER;
    	if ("VIET_CARD_TEXT".equals(generalFormatName)) return VIET_CARD_TEXT;
    	if ("ZODIAC_1".equals(generalFormatName)) return ZODIAC_1;
    	if ("ZODIAC_2".equals(generalFormatName)) return ZODIAC_2;
    	if ("ZODIAC_3".equals(generalFormatName)) return ZODIAC_3;
    	if ("CAPS".equals(generalFormatName)) return CAPS;
    	if ("FIRST_CAP".equals(generalFormatName)) return FIRST_CAP;
    	if ("LOWER".equals(generalFormatName)) return LOWER;
    	if ("UPPER".equals(generalFormatName)) return UPPER;
    	if ("CHAR_FORMAT".equals(generalFormatName)) return CHAR_FORMAT;
    	if ("MERGE_FORMAT".equals(generalFormatName)) return MERGE_FORMAT;
    	if ("MERGE_FORMAT_INET".equals(generalFormatName)) return MERGE_FORMAT_INET;
    	throw new IllegalArgumentException("Unknown GeneralFormat name.");
    }
    
    /// <summary>
    /// Returns array of GeneralFormat values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		AIUEO,
    		UPPERCASE_ALPHABETIC,
    		LOWERCASE_ALPHABETIC,
    		ARABIC,
    		ARABIC_ABJAD,
    		ARABIC_ALPHA,
    		ARABIC_DASH,
    		BAHT_TEXT,
    		CARD_TEXT,
    		CHINESE_NUM_1,
    		CHINESE_NUM_2,
    		CHINESE_NUM_3,
    		CHOSUNG,
    		CIRCLE_NUM,
    		DB_CHAR,
    		DB_NUM_1,
    		DB_NUM_2,
    		DB_NUM_3,
    		DB_NUM_4,
    		DOLLAR_TEXT,
    		GANADA,
    		GB_1,
    		GB_2,
    		GB_3,
    		GB_4,
    		HEBREW_1,
    		HEBREW_2,
    		HEX,
    		HINDI_ARABIC,
    		HINDI_CARD_TEXT,
    		HINDI_LETTER_1,
    		HINDI_LETTER_2,
    		IROHA,
    		KANJI_NUM_1,
    		KANJI_NUM_2,
    		KANJI_NUM_3,
    		ORDINAL,
    		ORD_TEXT,
    		UPPERCASE_ROMAN,
    		LOWERCASE_ROMAN,
    		SB_CHAR,
    		THAI_ARABIC,
    		THAI_CARD_TEXT,
    		THAI_LETTER,
    		VIET_CARD_TEXT,
    		ZODIAC_1,
    		ZODIAC_2,
    		ZODIAC_3,
    		CAPS,
    		FIRST_CAP,
    		LOWER,
    		UPPER,
    		CHAR_FORMAT,
    		MERGE_FORMAT,
    		MERGE_FORMAT_INET,
    	};
    }

    public static final int length = 56;
}

