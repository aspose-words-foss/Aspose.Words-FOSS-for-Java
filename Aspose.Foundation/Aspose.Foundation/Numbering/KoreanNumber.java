// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/11/2024 by Ilya Navrotskiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// This class is a helper for <see cref="NumberConverterCore"/>,
/// its methods convert a number into several Korean numbering styles.
/// </summary>
/*static*/ class KoreanNumber
{
	/* Simulation of static class by using private constructor */
	private KoreanNumber()
	{}

    /// <summary>
    /// Converts a specified long value to Ganada numbering string.
    /// </summary>
    static String toGanada(long value)
    {
        // Fallback to arabic 0 for illegal values.
        if (value < 1)
            return "0";

        // The spec says that for values greater than 14, the text displayed in Ganada shall be
        // repeatedly subtract the size of the set(14) from the value until the result is equal
        // to or less than the size of the set. The remainder determines which character to use
        // from the set above, and that sequence of character is repeated the number of times the
        // size of the set was subtracted from the original value.
        // However, the Word does not repeat the digit, but just displays the single remainder digit.
        int index = (int)((value - 1) % 14);
        return G_GANADA_DIGITS[index];
    }

    /// <summary>
    /// Converts a specified long value to KoreanDigital2 numbering string.
    /// </summary>
    static String toKoreanDigital2(long value)
    {
        // There is a special symbol (零) for zero number in Word. However, note that
        // zero digit within other numbers Word writes using another symbol (零).
        final String ZERO = "零";
        if (value < 1)
            return ZERO;

        ArrayList<Integer> digits = GroupSplitter.splitToGroups(value, 1);

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = digits.size() - 1; i >= 0; i--)
            msStringBuilder.append(stringBuilder, G_KOREAN_DIGITAL_2[digits.get(i)]);

        return stringBuilder.toString();
    }

    /// <summary>
    /// Converts a specified long value to Chosung numbering string.
    /// </summary>
    static String toChosung(long value)
    {
        // Fallback to arabic 0 for illegal values.
        if (value < 1)
            return "0";

        // The spec says that for values greater than 14, the text displayed in Chosung shall be
        // repeatedly subtract the size of the set(14) from the value until the result is equal
        // to or less than the size of the set. The remainder determines which character to use
        // from the set above, and that sequence of character is repeated the number of times the
        // size of the set was subtracted from the original value.
        // However, the Word does not repeat the digit, but just displays the single remainder digit.
        int index = (int)((value - 1) % 14);
        return G_CHOSUNG_DIGITS[index];
    }

    /// <summary>
    /// Converts a specified long value to Hangul (koreanLegal) numbering string.
    /// </summary>
    static String toHangul(long value)
    {
        // Fallback to arabic 0 for illegal values less than 1.
        if (value < 1)
            return "0";

        // Return an empty string for illegal values greater or equal to 10,000,000.
        if (value >= 10000000)
            return "";

        if (value < 10)
            return G_KOREAN_LEGAL_DIGITS[(int)value - 1];

        StringBuilder sb = new StringBuilder();
        ArrayList<Integer> digits;
        if (value < 100)
        {
            digits = GroupSplitter.splitToGroups(value, 1);
            // Here are values [10; 99], so tens are always non-zero.
            msStringBuilder.append(sb, G_KOREAN_LEGAL_TENS[digits.get(1) - 1]);
            if (digits.get(0) > 0)
                msStringBuilder.append(sb, G_KOREAN_LEGAL_DIGITS[digits.get(0) - 1]);

            return sb.toString();
        }

        // If we here, then value is between 100 and 9,999,999.
        // Word in this case splits number onto two parts (groups): <= 10,000 and > 10,000.
        final int GROUP_LENGTH = 5;
        // There can be up to 2 groups.
        ArrayList<Integer> groups = GroupSplitter.splitToGroups(value, GROUP_LENGTH);
        for (int groupIndex = groups.size() - 1; groupIndex >= 0; groupIndex--)
        {
            // Split to individual digits within a group.
            digits = GroupSplitter.splitToGroups(groups.get(groupIndex), 1);

            // Word adds '만' (10,000) after first group (numbers > 10,000), if second group (< 10000) is incomplete.
            if ((groups.size() > 1) && (groupIndex == 0) && (digits.size() < GROUP_LENGTH))
                sb.append(KOREAN_LEGAL_TEN_THOUSANDS);

            // Process digits within split group.
            for (int i = digits.size() - 1; i >= 0; i--)
            {
                int digit = digits.get(i);

                // There is no zero digit in Korean Legal.
                if (digit == 0)
                    continue;

                // Word does not write digit 1 (일) in some cases.
                if (digit != 1)
                {
                    msStringBuilder.append(sb, G_KOREAN_LEGAL_DIGITS_ABOVE_HUNDRED[digit - 1]);
                }
                // Word does not write 1 (일) at very start of number.
                else if (sb.length() > 0)
                {
                    if (groupIndex == 0)
                    {
                        // For the second (lower) split group Word writes 1 (일) only for ones and thousands.
                        if (i == 0 || i == 3)
                            msStringBuilder.append(sb, G_KOREAN_LEGAL_DIGITS_ABOVE_HUNDRED[digit - 1]);
                    }
                    else
                    {
                        // For the first (upper) split group Word writes 1 (일) if it is not at very start of number
                        // that we have checked already above.
                        msStringBuilder.append(sb, G_KOREAN_LEGAL_DIGITS_ABOVE_HUNDRED[digit - 1]);
                    }
                }

                // Append degree of ten (one of the 10, 100, 1000 or 10000).
                // The value depends on digit position. And also it depends
                // on a group index: for upper group it is shifted one degree up.
                if ((i - 1) + groupIndex >= 0)
                    msStringBuilder.append(sb, G_KOREAN_LEGAL_TENS_DEGREE[(i - 1) + groupIndex]);
            }
        }

        return sb.toString();
    }

    /// <summary>
    /// Specifies the sequence of digits [1 - 14] for the Korean Ganada format.
    /// </summary>
    private static /*final*/ String[] G_GANADA_DIGITS =
    {
        "가", "나", "다", "라", "마", "바", "사", "아", "자", "차", "카", "타", "파", "하"
    };

    /// <summary>
    /// Specifies the sequence of digits [0 - 9] for the KoreanDigital2 format.
    /// </summary>
    private static /*final*/ String[] G_KOREAN_DIGITAL_2 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };

    /// <summary>
    /// Specifies the sequence of digits [1 - 14] for the Korean Chosung format.
    /// </summary>
    private static /*final*/ String[] G_CHOSUNG_DIGITS =
    {
        "ㄱ", "ㄴ", "ㄷ", "ㄹ", "ㅁ", "ㅂ", "ㅅ", "ㅇ", "ㅈ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"
    };

    /// <summary>
    /// Specifies the sequence of digits [1 - 9] for the KoreanLegal (Hangul) format.
    /// </summary>
    private static /*final*/ String[] G_KOREAN_LEGAL_DIGITS = { "하나", "둘", "셋", "넷", "다섯", "여섯", "일곱", "여덟", "아홉" };

    /// <summary>
    /// Specifies the sequence of digits [10 - 90] for the KoreanLegal (Hangul) format.
    /// </summary>
    private static /*final*/ String[] G_KOREAN_LEGAL_TENS = { "열", "스물", "서른", "마흔", "쉰", "예순", "일흔", "여든", "아흔" };

    /// <summary>
    /// Specifies the sequence of digits [1 - 9] above 100 for the KoreanLegal (Hangul) format.
    /// </summary>
    private static /*final*/ String[] G_KOREAN_LEGAL_DIGITS_ABOVE_HUNDRED = { "일", "이", "삼", "사", "오", "육", "칠", "팔", "구" };

    /// <summary>
    /// Specifies the sequence of digits [10, 100, 1000, 10000] for the KoreanLegal (Hangul) format.
    /// </summary>
    private static /*final*/ String[] G_KOREAN_LEGAL_TENS_DEGREE = { "십", "백", "천", "만" };

    /// <summary>
    /// Specifies KoreanLegal (Hangul) 10,000.
    /// </summary>
    private static final char KOREAN_LEGAL_TEN_THOUSANDS = '만';
}

