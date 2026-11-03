// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/06/2017 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.DictionaryExtension;
import com.aspose.CharCase;
import java.util.HashMap;


/// <summary>
/// Provides utility functions for <see cref="GeneralFormat"/> enum.
/// </summary>
/*static*/ class GeneralFormatUtil
{
	/* Simulation of static class by using private constructor */
	private GeneralFormatUtil()
	{}


    private static void addMapEntry(/*GeneralFormat*/int format, String formatString)
    {
        msDictionary.add(G_GENERAL_FORMAT_TO_STRING_MAP, format, formatString);
        if (!G_STRING_TO_GENERAL_FORMAT_MAP.containsKey(formatString.toUpperCase()))
            msDictionary.add(G_STRING_TO_GENERAL_FORMAT_MAP, formatString.toUpperCase(), format);
    }

    static String generalFormatToString(/*GeneralFormat*/int format)
    {
        return DictionaryExtension.getValueOrNull(G_GENERAL_FORMAT_TO_STRING_MAP, format);
    }

    static /*GeneralFormat*/int stringToGeneralFormat(String formatString)
    {
        // Special case for the alphabetic and Roman formats.
        switch (gStringSwitchMap.of(formatString.toLowerCase()))
        {
            case /*"alphabetic"*/0:
                return (Character.isLowerCase(formatString.charAt(0)))
                    ? GeneralFormat.LOWERCASE_ALPHABETIC
                    : GeneralFormat.UPPERCASE_ALPHABETIC;
            case /*"roman"*/1:
                return (Character.isLowerCase(formatString.charAt(0)))
                    ? GeneralFormat.LOWERCASE_ROMAN
                    : GeneralFormat.UPPERCASE_ROMAN;
            default:
                break;
        }

        return DictionaryExtension.getValueOrDefault(G_STRING_TO_GENERAL_FORMAT_MAP, formatString.toUpperCase(), GeneralFormat.NONE);
    }

    static boolean isNumericFormat(/*GeneralFormat*/int format)
    {
        return generalFormatToNumberStyle(format) != NumberStyle.NONE;
    }

    static /*NumberStyle*/int generalFormatToNumberStyle(/*GeneralFormat*/int format)
    {
        // ECMA $17.16.4.3.1 matches several number formats to multiple number styles. AW uses the first one in such cases.
        switch (format)
        {
            case GeneralFormat.AIUEO:
                return NumberStyle.AIUEO;
            case GeneralFormat.UPPERCASE_ALPHABETIC:
                return NumberStyle.UPPERCASE_LETTER;
            case GeneralFormat.LOWERCASE_ALPHABETIC:
                return NumberStyle.LOWERCASE_LETTER;
            case GeneralFormat.ARABIC:
                return NumberStyle.ARABIC;
            case GeneralFormat.ARABIC_ABJAD:
                return NumberStyle.ARABIC_2;
            case GeneralFormat.ARABIC_ALPHA:
                return NumberStyle.ARABIC_1;
            case GeneralFormat.ARABIC_DASH:
                return NumberStyle.NUMBER_IN_DASH;
            case GeneralFormat.BAHT_TEXT:
                // NOTE: the NumberStyle enum misses 'bahtText' value.
                return NumberStyle.ARABIC;
            case GeneralFormat.CARD_TEXT:
                return NumberStyle.NUMBER;
            case GeneralFormat.CHINESE_NUM_1:
                // ECMA $17.16.4.3.1: chineseCounting(zh-CN) or taiwaneseCounting (zn-TW)
                return NumberStyle.SIMP_CHIN_NUM_1;
            case GeneralFormat.CHINESE_NUM_2:
                // ECMA $17.16.4.3.1: chineseLegalSimplified (zh-CN) or ideographLegalTraditional (zh-TW)
                return NumberStyle.SIMP_CHIN_NUM_2;
            case GeneralFormat.CHINESE_NUM_3:
                // ECMA $17.16.4.3.1: chineseCountingThousand (zh-CN) or taiwaneseCountingThousand (zh-TW)
                return NumberStyle.SIMP_CHIN_NUM_3;
            case GeneralFormat.CHOSUNG:
                return NumberStyle.CHOSUNG;
            case GeneralFormat.CIRCLE_NUM:
                return NumberStyle.NUMBER_IN_CIRCLE;
            case GeneralFormat.DB_CHAR:
                return NumberStyle.ARABIC_FULL_WIDTH;
            case GeneralFormat.DB_NUM_1:
                // ECMA $17.16.4.3.1: ideographDigital (ja-JP) or koreanDigital (ko-KR)
                return NumberStyle.KANJI;
            case GeneralFormat.DB_NUM_2:
                // ECMA $17.16.4.3.1: japaneseCounting (ja-JP) or koreanCounting (ko-KR)
                return NumberStyle.KANJI_DIGIT;
            case GeneralFormat.DB_NUM_3:
                // ECMA $17.16.4.3.1: japaneseLegal(ja-JP) or koreanLegal (ko-KR)
                return NumberStyle.KANJI_TRADITIONAL;
            case GeneralFormat.DB_NUM_4:
                // ECMA $17.16.4.3.1: japaneseDigitalTenThousand (ja-JP) or koreanDigital2 (ko-KR) or taiwaneseDigital (zh-TW)
                return NumberStyle.KANJI_TRADITIONAL_2;
            case GeneralFormat.DOLLAR_TEXT:
                // NOTE: the NumberStyle enum misses 'dollarText' value.
                return NumberStyle.ARABIC;
            case GeneralFormat.GANADA:
                return NumberStyle.GANADA;
            case GeneralFormat.GB_1:
                return NumberStyle.GB_1;
            case GeneralFormat.GB_2:
                return NumberStyle.GB_2;
            case GeneralFormat.GB_3:
                return NumberStyle.GB_3;
            case GeneralFormat.GB_4:
                return NumberStyle.GB_4;
            case GeneralFormat.HEBREW_1:
                return NumberStyle.HEBREW_1;
            case GeneralFormat.HEBREW_2:
                return NumberStyle.HEBREW_2;
            case GeneralFormat.HEX:
                return NumberStyle.HEX;
            case GeneralFormat.HINDI_ARABIC:
                return NumberStyle.HINDI_ARABIC;
            case GeneralFormat.HINDI_CARD_TEXT:
                return NumberStyle.HINDI_CARDINAL_TEXT;
            case GeneralFormat.HINDI_LETTER_1:
                // NOTE: the NumberStyle enum misses 'hindiVowels' value.
                return NumberStyle.ARABIC;
            case GeneralFormat.HINDI_LETTER_2:
                return NumberStyle.HINDI_LETTER_2;
            case GeneralFormat.IROHA:
                return NumberStyle.IROHA;
            case GeneralFormat.KANJI_NUM_1:
                // ECMA $17.16.4.3.1: koreanDigital(ko-KR), ideographDigital (ja-JP), chineseCounting (zh-CN), or taiwaneseCounting (zh-TW)
                return NumberStyle.HANJA_READ;
            case GeneralFormat.KANJI_NUM_2:
                // ECMA $17.16.4.3.1: koreanCounting(ko-KR), chineseCountingThousand (ja-JP), chineseLegalSimplified (zh-CN), or ideographLegalTraditional (zh-TW)
                return NumberStyle.HANJA_READ_DIGIT;
            case GeneralFormat.KANJI_NUM_3:
                // ECMA $17.16.4.3.1: koreanLegal (koKR) or japaneseLegal (ja-JP) or chineseCountingThousand (zh-CN) or taiwaneseCountingThousand (zh-TW)
                return NumberStyle.HANGUL;
            case GeneralFormat.ORDINAL:
                return NumberStyle.ORDINAL;
            case GeneralFormat.ORD_TEXT:
                return NumberStyle.ORDINAL_TEXT;
            case GeneralFormat.UPPERCASE_ROMAN:
                return NumberStyle.UPPERCASE_ROMAN;
            case GeneralFormat.LOWERCASE_ROMAN:
                return NumberStyle.LOWERCASE_ROMAN;
            case GeneralFormat.SB_CHAR:
                return NumberStyle.ARABIC_HALF_WIDTH;
            case GeneralFormat.THAI_ARABIC:
                return NumberStyle.THAI_ARABIC;
            case GeneralFormat.THAI_CARD_TEXT:
                return NumberStyle.THAI_CARDINAL_TEXT;
            case GeneralFormat.THAI_LETTER:
                return NumberStyle.THAI_LETTER;
            case GeneralFormat.VIET_CARD_TEXT:
                return NumberStyle.VIET_CARDINAL_TEXT;
            case GeneralFormat.ZODIAC_1:
                return NumberStyle.ZODIAC_1;
            case GeneralFormat.ZODIAC_2:
                return NumberStyle.ZODIAC_2;
            case GeneralFormat.ZODIAC_3:
                return NumberStyle.ZODIAC_3;
            default:
                return NumberStyle.NONE;
        }
    }

    static /*CharCase*/int generalFormatToCharCase(/*GeneralFormat*/int format)
    {
        switch (format)
        {
            case GeneralFormat.CAPS:
                return CharCase.CAPS;
            case GeneralFormat.FIRST_CAP:
                return CharCase.FIRST_CAP;
            case GeneralFormat.LOWER:
                return CharCase.LOWER;
            case GeneralFormat.UPPER:
                return CharCase.UPPER;
            case GeneralFormat.DB_CHAR:
                return CharCase.DB_CHAR;
            default:
                return CharCase.DEFAULT;
        }
    }

    private static /*final*/ HashMap<Integer, String> G_GENERAL_FORMAT_TO_STRING_MAP = new HashMap<Integer, String>();
    private static /*final*/ HashMap<String, Integer> G_STRING_TO_GENERAL_FORMAT_MAP = new HashMap<String, Integer>();
    static/* GeneralFormatUtil()*/
    {
        addMapEntry(GeneralFormat.AIUEO, "AIUEO");
        addMapEntry(GeneralFormat.UPPERCASE_ALPHABETIC, "ALPHABETIC");
        addMapEntry(GeneralFormat.LOWERCASE_ALPHABETIC, "alphabetic");
        addMapEntry(GeneralFormat.ARABIC, "Arabic");
        addMapEntry(GeneralFormat.ARABIC_ABJAD, "ARABICABJAD");
        addMapEntry(GeneralFormat.ARABIC_ALPHA, "ARABICALPHA");
        addMapEntry(GeneralFormat.ARABIC_DASH, "ArabicDash");
        addMapEntry(GeneralFormat.BAHT_TEXT, "BAHTTEXT");
        addMapEntry(GeneralFormat.CARD_TEXT, "CardText");
        addMapEntry(GeneralFormat.CHINESE_NUM_1, "CHINESENUM1");
        addMapEntry(GeneralFormat.CHINESE_NUM_2, "CHINESENUM2");
        addMapEntry(GeneralFormat.CHINESE_NUM_3, "CHINESENUM3");
        addMapEntry(GeneralFormat.CHOSUNG, "CHOSUNG");
        addMapEntry(GeneralFormat.CIRCLE_NUM, "CIRCLENUM");
        addMapEntry(GeneralFormat.DB_CHAR, "DBCHAR");
        addMapEntry(GeneralFormat.DB_NUM_1, "DBNUM1");
        addMapEntry(GeneralFormat.DB_NUM_2, "DBNUM2");
        addMapEntry(GeneralFormat.DB_NUM_3, "DBNUM3");
        addMapEntry(GeneralFormat.DB_NUM_4, "DBNUM4");
        addMapEntry(GeneralFormat.DOLLAR_TEXT, "DollarText");
        addMapEntry(GeneralFormat.GANADA, "GANADA");
        addMapEntry(GeneralFormat.GB_1, "GB1");
        addMapEntry(GeneralFormat.GB_2, "GB2");
        addMapEntry(GeneralFormat.GB_3, "GB3");
        addMapEntry(GeneralFormat.GB_4, "GB4");
        addMapEntry(GeneralFormat.HEBREW_1, "HEBREW1");
        addMapEntry(GeneralFormat.HEBREW_2, "HEBREW2");
        addMapEntry(GeneralFormat.HEX, "Hex");
        addMapEntry(GeneralFormat.HINDI_ARABIC, "HINDIARABIC");
        addMapEntry(GeneralFormat.HINDI_CARD_TEXT, "HINDICARDTEXT");
        addMapEntry(GeneralFormat.HINDI_LETTER_1, "HINDILETTER1");
        addMapEntry(GeneralFormat.HINDI_LETTER_2, "HINDILETTER2");
        addMapEntry(GeneralFormat.IROHA, "IROHA");
        addMapEntry(GeneralFormat.KANJI_NUM_1, "KANJINUM1");
        addMapEntry(GeneralFormat.KANJI_NUM_2, "KANJINUM2");
        addMapEntry(GeneralFormat.KANJI_NUM_3, "KANJINUM3");
        addMapEntry(GeneralFormat.ORDINAL, "Ordinal");
        addMapEntry(GeneralFormat.ORD_TEXT, "OrdText");
        addMapEntry(GeneralFormat.UPPERCASE_ROMAN, "Roman");
        addMapEntry(GeneralFormat.LOWERCASE_ROMAN, "roman");
        addMapEntry(GeneralFormat.SB_CHAR, "SBCHAR");
        addMapEntry(GeneralFormat.THAI_ARABIC, "THAIARABIC");
        addMapEntry(GeneralFormat.THAI_CARD_TEXT, "THAICARDTEXT");
        addMapEntry(GeneralFormat.THAI_LETTER, "THAILETTER");
        addMapEntry(GeneralFormat.VIET_CARD_TEXT, "VIETCARDTEXT");
        addMapEntry(GeneralFormat.ZODIAC_1, "ZODIAC1");
        addMapEntry(GeneralFormat.ZODIAC_2, "ZODIAC2");
        addMapEntry(GeneralFormat.ZODIAC_3, "ZODIAC3");

        addMapEntry(GeneralFormat.CAPS, "Caps");
        addMapEntry(GeneralFormat.FIRST_CAP, "FirstCap");
        addMapEntry(GeneralFormat.LOWER, "Lower");
        addMapEntry(GeneralFormat.UPPER, "Upper");

        addMapEntry(GeneralFormat.CHAR_FORMAT, "CHARFORMAT");
        addMapEntry(GeneralFormat.MERGE_FORMAT, "MERGEFORMAT");
        addMapEntry(GeneralFormat.MERGE_FORMAT_INET, "MERGEFORMATINET");
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"alphabetic",
		"roman"
	);

}

