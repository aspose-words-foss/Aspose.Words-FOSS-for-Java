// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/11/2015 by Ilya Navrotskiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.msString;


/// <summary>
/// This class is a helper for <see cref="NumberConverterCore"/>,
/// its methods convert a number into several Hebrew numbering styles.
/// </summary>
/// <remarks>See algorithms description in ISO29500 p1, 17.18.59 ST_NumberFormat (Numbering Format).</remarks>
/*static*/ class HebrewNumber
{
	/* Simulation of static class by using private constructor */
	private HebrewNumber()
	{}

    /// <summary>
    /// Returns a Hebrew1 number.
    /// </summary>
    static String toHebrew1(long value)
    {
        if (value == 0)
            return "";

        int cycledValue = NumberConverterCore.getCycledValue(value);

        ArrayList<Integer> digits = GroupSplitter.splitToGroups(cycledValue, 1);
        StringBuilder stringBuilder = new StringBuilder();

        for (int digitPos = digits.size() - 1; digitPos >= 0; digitPos--)
        {
            // If the remainder is 15 or 16, we should replace them with the special strings and stop.
            String specialNumber = processHebrew1SpecialNumbers(digits, digitPos);
            if (specialNumber != null)
            {
                msStringBuilder.append(stringBuilder, specialNumber);
                return stringBuilder.toString();
            }

            int digit = digits.get(digitPos);
            if (digit != 0)
                stringBuilder.append(G_HEBREW_1_DIGITS[digitPos][digit - 1]);
        }

        return stringBuilder.toString();
    }

    /// <summary>
    /// Returns a Hebrew2 number.
    /// </summary>
    static String toHebrew2(long value)
    {
        Debug.assertTrue(value >= 0);

        int cycledValue = NumberConverterCore.getCycledValue(value);

        StringBuilder stringBuilder = new StringBuilder();

        int tavCount = cycledValue / HEBREW_2_DIVISOR;
        msStringBuilder.append(stringBuilder, msString.newString(TAV_CHAR, tavCount));

        int remainder = cycledValue % HEBREW_2_DIVISOR;
        if (remainder > 0)
            stringBuilder.append(HEBREW_2_NUMERALS.charAt(remainder - 1));

        return stringBuilder.toString();
    }

    /// <summary>
    /// Checks that the remaining digits from the specified position
    /// form number '15' or '16' and returns corresponding string.
    /// </summary>
    /// <remarks>
    /// In accordance with the specification, numbers '15' and '16' have special representation.
    /// </remarks>
    /// <param name="digits">List of digits where search for special numbers.</param>
    /// <param name="digitPos">Position in list of digits from which start to search.</param>
    /// <returns>String designation for special numbers or 'null' if not found.</returns>
    private static String processHebrew1SpecialNumbers(ArrayList<Integer> digits, int digitPos)
    {
        // Check that exactly two digits remained starting from the specified position.
        if (digitPos != 1)
            return null;

        // Check number is '15' or '16' and return corresponding string.
        int digit = digits.get(digitPos);
        if (digit == 1)
        {
            digit = digits.get(digitPos - 1);

            // Digits form number '15'.
            if (digit == 5)
                return "טו";

            // Digits form number '16'.
            if (digit == 6)
                return "טז";
        }

        return null;
    }

    /// <summary>
    /// These characters mean:
    /// 1, 2, 3, 4, 5, 6, 7, 8, 9;
    /// 10, 20, 30, 40, 50, 60, 70, 80, 90;
    /// 100, 200, 300.
    /// </summary>
    private static /*final*/ char[][] G_HEBREW_1_DIGITS =
    {
        new char[] { 'א', 'ב', 'ג', 'ד', 'ה', 'ו', 'ז', 'ח', 'ט' },
        new char[] { 'י', 'כ', 'ל', 'מ', 'נ', 'ס', 'ע', 'פ', 'צ' },
        new char[] { 'ק', 'ר', 'ש' }
    };

    /// <summary>
    /// This string represents Hebrew2 numbering alphabet.
    /// </summary>
    private static final String HEBREW_2_NUMERALS = "אבגדהוזחטיכלמנסעפצקרש";

    /// <summary>
    /// Length of Hebrew2 numbering alphabet + 1 (Hebrew2Numerals.Length + 1).
    /// </summary>
    private static final int HEBREW_2_DIVISOR = 22;

    /// <summary>
    /// Twenty-second and last letter in Hebrew2.
    /// </summary>
    private static final char TAV_CHAR = 'ת';
}

