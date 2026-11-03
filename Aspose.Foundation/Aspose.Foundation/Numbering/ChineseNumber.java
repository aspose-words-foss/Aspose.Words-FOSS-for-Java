// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/11/2013 by Ilya Navrotskiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// This class is a helper for <see cref="NumberConverterCore"/>,
/// its methods convert a number into several Chinese numbering styles.
/// </summary>
/// <remarks>See algorithms description in ISO29500 p1, 17.18.59 ST_NumberFormat (Numbering Format).</remarks>
/*static*/ class ChineseNumber
{
	/* Simulation of static class by using private constructor */
	private ChineseNumber()
	{}

    /// <summary>
    /// Returns Chinese Counting System number.
    /// </summary>
    static String toChineseCountingSystem(long value, /*NumberStyleCore*/int numberStyle)
    {
        Debug.assertTrue(value >= 0);

        switch (numberStyle)
        {
            case NumberStyleCore.TRAD_CHIN_NUM_2:
            case NumberStyleCore.TRAD_CHIN_NUM_3:
            case NumberStyleCore.SIMP_CHIN_NUM_2:
            case NumberStyleCore.SIMP_CHIN_NUM_3:
                return toChineseCountingThousand(value, numberStyle);
            case NumberStyleCore.SIMP_CHIN_NUM_1:
            case NumberStyleCore.TRAD_CHIN_NUM_1:
                return toChineseCounting(value, numberStyle);
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "numberStyle");
        }
    }

    /// <summary>
    /// Returns Chinese Counting Thousand number.
    /// </summary>
    private static String toChineseCountingThousand(long value, /*NumberStyleCore*/int numberStyle)
    {
        if (value == 0)
            return "";

        ArrayList<Integer> digits = GroupSplitter.splitToGroups(value, 1);
        StringBuilder stringBuilder = new StringBuilder();

        boolean isPrevZero = false;
        for (int digitPos = digits.size() - 1; digitPos >= 0; digitPos--)
        {
            int digit = digits.get(digitPos);

            // Don`t write any for zero, but writes special symbol later on first non-zero digit
            if (digit == 0)
            {
                isPrevZero = true;
                continue;
            }

            // Write special symbol, if previous digits was any number of consecutive zeros.
            if (isPrevZero)
            {
                stringBuilder.append(zeroToSymbol(numberStyle));
                isPrevZero = false;
            }

            // Write down the symbol, depending on current digit position in number.
            msStringBuilder.append(stringBuilder, digitToSymbolInThousandCountingSystem(digit, digitPos, value, numberStyle));

        }

        return stringBuilder.toString();
    }

    /// <summary>
    /// Returns Chinese Counting number.
    /// </summary>
    private static String toChineseCounting(long value, /*NumberStyleCore*/int numberStyle)
    {
        if (value == 0)
            return Character.toString(zeroToSymbol(numberStyle));

        // Single digit. Simply convert it and return.
        if (value < 10)
            return Character.toString(digitToSymbol((int)value, numberStyle));

        // 'Ten' has special symbol, process it separately.
        if (value == 10)
            return Character.toString(CHINESE_COUNTING_TEN);

        ArrayList<Integer> digits = GroupSplitter.splitToGroups(value, 1);
        StringBuilder stringBuilder = new StringBuilder();

        if (value < 20)
        {
            // If (11 <= value <= 19), then we should start from the 'Chinese Ten'
            // and then append last digit.
            stringBuilder.append(CHINESE_COUNTING_TEN);
            stringBuilder.append(digitToSymbol(digits.get(0), numberStyle));
        }
        else if (value < 100)
        {
            // If (20 <= value <= 99), then we should append number of tens,
            // then append 'Chinese Ten' and then append last digit, if it is not zero.
            stringBuilder.append(digitToSymbol(digits.get(1), numberStyle));
            stringBuilder.append(CHINESE_COUNTING_TEN);
            if (digits.get(0) != 0)
                stringBuilder.append(digitToSymbol(digits.get(0), numberStyle));
        }
        else
        {
            // For all other values we should take each digit,
            // convert it to the corresponding Chinese symbol and append to the resulting string.
            for (int digitPos = digits.size() - 1; digitPos >= 0; digitPos--)
                stringBuilder.append(digitToSymbol(digits.get(digitPos), numberStyle));
        }

        return stringBuilder.toString();
    }

    /// <summary>
    /// Returns symbol, for digit in number at specified position for thousand counting system.
    /// <param name="digit">Digit at position in original number.</param>
    /// <param name="digitPos">Position in original number</param>
    /// <param name="originalValue">Original number</param>
    /// <param name="numberStyle">Style of the counting system for which to convert.</param>
    /// </summary>
    private static String digitToSymbolInThousandCountingSystem(int digit, int digitPos, long originalValue, /*NumberStyleCore*/int numberStyle)
    {
        StringBuilder stringBuilder = new StringBuilder();

        //  In Thousand Counting Systems for numbers less then 20, we don`t write number of tens (symbol 'one')
        if (((numberStyle != NumberStyleCore.TRAD_CHIN_NUM_3) && (numberStyle != NumberStyleCore.SIMP_CHIN_NUM_3)) || (originalValue >= 20) || (digitPos != 1))
            stringBuilder.append(digitToSymbol(digit, numberStyle));

        // Length of the groups (-1) for Counting System
        final int GROUP_LENGTH = 4;

        // Write down the symbol, depending on the current digit position in the group and numbering style.
        int powPos = digitPos % GROUP_LENGTH;
        if (powPos != 0)
            stringBuilder.append(powToSymbol(powPos, numberStyle));

        // Write down symbol 'ten thousand', if value >= 100000.
        msStringBuilder.append(stringBuilder, tenThousandToSymbol(numberStyle), digitPos / GROUP_LENGTH);

        return stringBuilder.toString();
    }

    /// <summary>
    /// Returns symbol, which mean digit 1-9 for the specified counting system.
    /// <param name="digit">Digit to convert.</param>
    /// <param name="numberStyle">Style of the counting system for which to convert.</param>
    /// </summary>
    private static char digitToSymbol(int digit, /*NumberStyleCore*/int numberStyle)
    {
        if (digit == 0)
            return zeroToSymbol(numberStyle);

        char symbol;
        switch (numberStyle)
        {
            case NumberStyleCore.TRAD_CHIN_NUM_2:
                symbol = G_IDEOGRAPH_LEGAL_TRADITIONAL_DIGITS[0][--digit];
                break;
            case NumberStyleCore.SIMP_CHIN_NUM_2:
                symbol = G_IDEOGRAPH_LEGAL_SIMPLIFIED_DIGITS[0][--digit];
                break;
            case NumberStyleCore.TRAD_CHIN_NUM_3:
            case NumberStyleCore.SIMP_CHIN_NUM_3:
            case NumberStyleCore.TRAD_CHIN_NUM_1:
            case NumberStyleCore.SIMP_CHIN_NUM_1:
                symbol = G_COUNTING_THOUSAND_DIGITS[0][--digit];
                break;
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "numberStyle");
        }

        return symbol;
    }

    /// <summary>
    /// Returns symbol, which mean some power of ten for the specified counting system (10, 100, 1000, 10000).
    /// <param name="power">Power of ten.</param>
    /// <param name="numberStyle">Style of the counting system for which to convert.</param>
    /// </summary>
    private static char powToSymbol(int power, /*NumberStyleCore*/int numberStyle)
    {
        if (power == 4)
            return tenThousandToSymbol(numberStyle);

        char powerSymbol;
        switch (numberStyle)
        {
            case NumberStyleCore.TRAD_CHIN_NUM_2:
                powerSymbol = G_IDEOGRAPH_LEGAL_TRADITIONAL_DIGITS[1][--power];
                break;
            case NumberStyleCore.SIMP_CHIN_NUM_2:
                powerSymbol = G_IDEOGRAPH_LEGAL_SIMPLIFIED_DIGITS[1][--power];
                break;
            case NumberStyleCore.TRAD_CHIN_NUM_3:
            case NumberStyleCore.SIMP_CHIN_NUM_3:
                powerSymbol = G_COUNTING_THOUSAND_DIGITS[1][--power];
                break;
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "numberStyle");
        }

        return powerSymbol;
    }

    /// <summary>
    /// Returns symbol, which mean '10 000' for specified counting system.
    /// <param name="numberStyle">Style of the counting system for which to convert.</param>
    /// </summary>
    private static char tenThousandToSymbol(/*NumberStyleCore*/int numberStyle)
    {
        char tenThousand;
        switch (numberStyle)
        {
            // Ideograph Legal Traditional 'ten thosand' symbol is same as in Taiwanese Counting Thousand System
            case NumberStyleCore.TRAD_CHIN_NUM_2:
            case NumberStyleCore.TRAD_CHIN_NUM_3:
                tenThousand = TAIWANESE_COUNTING_THOUSAND_TEN_THOUSAND;
                break;
            case NumberStyleCore.SIMP_CHIN_NUM_2:
            case NumberStyleCore.SIMP_CHIN_NUM_3:
                tenThousand = CHINESE_COUNTING_THOUSAND_TEN_THOUSAND;
                break;
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "numberStyle");
        }
        return tenThousand;
    }

    /// <summary>
    /// Returns symbol, which mean any sequence of zeroes for the specified counting system.
    /// <param name="numberStyle">Style of the counting system for which to convert.</param>
    /// </summary>
    private static char zeroToSymbol(/*NumberStyleCore*/int numberStyle)
    {
        char zero;
        switch (numberStyle)
        {
            // Ideograph Legal Traditional 'zero' symbol is the same as in
            // Taiwanese Counting Thousand System and Chinese Legal Simplified Format
            case NumberStyleCore.TRAD_CHIN_NUM_2:
            case NumberStyleCore.TRAD_CHIN_NUM_3:
            case NumberStyleCore.SIMP_CHIN_NUM_2:
                zero = TAIWANESE_COUNTING_THOUSAND_ZERO;
                break;
            case NumberStyleCore.SIMP_CHIN_NUM_3:
                zero = CHINESE_COUNTING_THOUSAND_ZERO;
                break;
            case NumberStyleCore.TRAD_CHIN_NUM_1:
            case NumberStyleCore.SIMP_CHIN_NUM_1:
                zero = CHINESE_COUNTING_ZERO;
                break;
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "numberStyle");
        }
        return zero;
    }

    /// <summary>
    /// This is only specifical part of Ideograph Legal Traditional Counting System
    /// These characters mean:
    /// 1, 2, 3, 4, 5, 6, 7, 8, 9;
    /// 10, 100, 1000;
    /// </summary>
    private static /*final*/ char[][] G_IDEOGRAPH_LEGAL_TRADITIONAL_DIGITS =
    {
        new char[] { '壹', '貳', '參', '肆', '伍', '陸', '柒', '捌', '玖' },
        new char[] { '拾', '佰', '仟' }
    };

    /// <summary>
    /// This is only specifical part of Ideograph Chinese Legal Simplified Counting System
    /// These characters mean:
    /// 1, 2, 3, 4, 5, 6, 7, 8, 9;
    /// 10, 100, 1000;
    /// </summary>
    private static /*final*/ char[][] G_IDEOGRAPH_LEGAL_SIMPLIFIED_DIGITS =
    {
        new char[] { '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' },
        new char[] { '拾', '佰', '仟' }
    };

    /// <summary>
    /// This is only common part of different Counting Thousand Systems
    /// These characters mean:
    /// 1, 2, 3, 4, 5, 6, 7, 8, 9;
    /// 10, 100, 1000;
    /// </summary>
    private static /*final*/ char[][] G_COUNTING_THOUSAND_DIGITS =
    {
        new char[] { '一', '二', '三', '四', '五', '六', '七', '八', '九' },
        new char[] { '十', '百', '千' }
    };

    private static final char TAIWANESE_COUNTING_THOUSAND_TEN_THOUSAND = '萬';
    private static final char CHINESE_COUNTING_THOUSAND_TEN_THOUSAND = '万';

    private static final char TAIWANESE_COUNTING_THOUSAND_ZERO = '零';
    // The standard states for the chineseCountingThousand enumeration value, if the sequence number is between 10,000 and 100,000
    // and no groups are formed, and the number is not a mulitple of 1,000, the symbol U+96F6 (零) should be written.
    // Word does not write out the symbol 零, but writes symbol '〇' instead.
    private static final char CHINESE_COUNTING_THOUSAND_ZERO = '〇';

    private static final char CHINESE_COUNTING_ZERO = '○';
    private static final char CHINESE_COUNTING_TEN = '十';
}

