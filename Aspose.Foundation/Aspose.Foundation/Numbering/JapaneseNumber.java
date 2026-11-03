// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/03/2012 by Andrey Soldatov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// This class is a helper for <see cref="NumberConverterCore"/>,
/// its methods convert a number into several Japanese numbering styles.
/// Some of these methods are used independently.
/// </summary>
public /*static*/ class JapaneseNumber
{
	/* Simulation of static class by using private constructor */
	private JapaneseNumber()
	{}

    static String toAiueo(long value, boolean isHalfWidth)
    {
        String[] numbers = isHalfWidth ? G_AIUEO_HALF_WIDTH_NUMBERS : G_AIUEO_NUMBERS;
        return (value == 0) ? "0" : numbers[(int)(value % numbers.length)];
    }

    static String toIroha(long value)
    {
        return (value == 0) ? "0" : G_IROHA_NUMBERS[(int)(value % G_IROHA_NUMBERS.length)];
    }

    /// <summary>
    /// <para>Returns Japanese number ('一', '二', '三') written by digits.</para>
    /// <para>Each decimal digit is converted to one corresponding Japanese character.</para>
    /// <para>Returns "二二一" for 221 number, compare with <see cref="ToJapaneseCounting"/>.</para>
    /// <para>Zero characters are prepended before number to return at least <paramref name="minChars"/> characters.</para>
    /// </summary>
    public static String toJapaneseDigital(long value, int minChars)
    {
        ArrayList<Integer> digits = GroupSplitter.splitToGroups(value, 1);

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = minChars - digits.size(); i > 0; i--)
            msStringBuilder.append(stringBuilder, G_KANJI_DIGITS[0]);

        for (int i = digits.size() - 1; i >= 0; i--)
            msStringBuilder.append(stringBuilder, G_KANJI_DIGITS[digits.get(i)]);

        return stringBuilder.toString();
    }

    /// <summary>
    /// <para>Returns Japanese number ('一', '二', '三') written in words.</para>
    /// <para>Returns "二百二十一" for 221 number, compare with <see cref="ToJapaneseDigital"/>.</para>
    /// </summary>
    /// <remarks>http://en.wikipedia.org/wiki/Japanese_numerals</remarks>
    public static String toJapaneseCounting(long value)
    {
        if (value < 10)
            return G_KANJI_DIGITS[(int)value];

        StringBuilder stringBuilder = new StringBuilder();

        // There is a special treatment of the highest '1' digit if a value >= 10000
        boolean isHighestDigit = (value >= 10000);

        ArrayList<Integer> myriadGroups = GroupSplitter.splitToGroups(value, 4);

        for (int myriadPos = myriadGroups.size() - 1; myriadPos >= 0; myriadPos--)
        {
            ArrayList<Integer> digits = GroupSplitter.splitToGroups(myriadGroups.get(myriadPos), 1);

            for (int digitPos = digits.size() - 1; digitPos >= 0; digitPos--)
            {
                int digit = digits.get(digitPos);

                if (digit == 0)
                    continue;

                // Only digits larger than one are written for KanjiDigit numbering.
                // There are two exceptions when digit is written even if it is '1':
                // 1. The lowest digit.
                // 2. The highest digit, if Number >= 10000.
                if ((digit > 1) || (digitPos == 0) || isHighestDigit)
                    msStringBuilder.append(stringBuilder, G_KANJI_DIGITS[digit]);

                // Order character is written after digit or without digit character(if the digit is 1).
                msStringBuilder.append(stringBuilder, G_KANJI_ORDER_CHARS[digitPos]);

                isHighestDigit = false;
            }

            msStringBuilder.append(stringBuilder, G_KANJI_MYRIAD_CHARS[myriadPos]);
        }

        return stringBuilder.toString();
    }

    /// <summary>
    /// These characters mean: '46', '1', '2', ..., '45'.
    /// </summary>
    private static /*final*/ String[] G_AIUEO_NUMBERS =
    {
        "ン", "ア", "イ", "ウ", "エ", "オ", "カ", "キ", "ク", "ケ",
        "コ", "サ", "シ", "ス", "セ", "ソ", "タ", "チ", "ツ", "テ",
        "ト", "ナ", "ニ", "ヌ", "ネ", "ノ", "ハ", "ヒ", "フ", "ヘ",
        "ホ", "マ", "ミ", "ム", "メ", "モ", "ヤ", "ユ", "ヨ", "ラ",
        "リ", "ル", "レ", "ロ", "ワ", "ヲ"
    };

    /// <summary>
    /// These characters mean: '46', '1', '2', ..., '45'.
    /// </summary>
    private static /*final*/ String[] G_AIUEO_HALF_WIDTH_NUMBERS =
    {
        "ﾝ", "ｱ", "ｲ", "ｳ", "ｴ", "ｵ", "ｶ", "ｷ", "ｸ", "ｹ",
        "ｺ", "ｻ", "ｼ", "ｽ", "ｾ", "ｿ", "ﾀ", "ﾁ", "ﾂ", "ﾃ",
        "ﾄ", "ﾅ", "ﾆ", "ﾇ", "ﾈ", "ﾉ", "ﾊ", "ﾋ", "ﾌ", "ﾍ",
        "ﾎ", "ﾏ", "ﾐ", "ﾑ", "ﾒ", "ﾓ", "ﾔ", "ﾕ", "ﾖ", "ﾗ",
        "ﾘ", "ﾙ", "ﾚ", "ﾛ", "ﾜ", "ｦ"
    };

    /// <summary>
    /// These characters mean: '48', '1', '2', ..., '47'.
    /// </summary>
    private static /*final*/ String[] G_IROHA_NUMBERS =
    {
        "ン", "イ", "ロ", "ハ", "ニ", "ホ", "ヘ", "ト", "チ", "リ",
        "ヌ", "ル", "ヲ", "ワ", "カ", "ヨ", "タ", "レ", "ソ", "ツ",
        "ネ", "ナ", "ラ", "ム", "ウ", "ヰ", "ノ", "オ", "ク", "ヤ",
        "マ", "ケ", "フ", "コ", "エ", "テ", "ア", "サ", "キ", "ユ",
        "メ", "ミ", "シ", "ヱ", "ヒ", "モ", "セ", "ス"
    };

    /// <summary>
    /// These characters mean: '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'.
    /// </summary>
    private static /*final*/ String[] G_KANJI_DIGITS = { "〇", "一", "二", "三", "四", "五", "六", "七", "八", "九" };

    /// <summary>
    /// These characters mean: "ten", "hundred", "thousand". Empty string when nothing to add.
    /// </summary>
    private static /*final*/ String[] G_KANJI_ORDER_CHARS = { "", "十", "百", "千" };

    /// <summary>
    /// These characters mean: "ten thousand", "hundred million". Empty string when nothing to add.
    /// </summary>
    private static /*final*/ String[] G_KANJI_MYRIAD_CHARS = { "", "万", "億" };
}

