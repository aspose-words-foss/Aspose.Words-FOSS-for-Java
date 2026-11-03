// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/02/2020 by Vasiliy Stepchenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.msString;


/// <summary>
/// This class is a helper for <see cref="NumberConverterCore"/>,
/// its method converts a number into Greek numbering style.
/// </summary>
/// <remarks>Algorithm is based on Word's behaviour and https://en.wikipedia.org/wiki/Greek_numerals page.</remarks>
/*static*/ class GreekNumber
{
	/* Simulation of static class by using private constructor */
	private GreekNumber()
	{}

    /// <summary>
    /// Returns Greek counting system number.
    /// </summary>
    /// <param name="value">number to convert.</param>
    /// <returns>String representation of the given number.</returns>
    static String toGreek(long value)
    {
        if (value < 1)
            return "";

        //Replicate Word's behaviour. After 9999 Word starts numeration from scratch.
        while (value > 9999)
            value -= 9999;

        ArrayList<Integer> groups = GroupSplitter.splitToGroups(value, 1);
        StringBuilder sb = new StringBuilder();

        for (int exponent = groups.size() - 1; exponent >= 0; exponent--)
        {
            int groupValue = groups.get(exponent);
            if (groupValue > 0)
            {
                msStringBuilder.append(sb, getDigit(groupValue, exponent));
            }
        }

        return sb.toString();
    }

    /// <summary>
    /// Returns string representation of the number.
    /// The number is specified by two parameters: digit value and exponent. In other words number = value*10^exponent.
    /// </summary>
    /// <param name="value">Digit value of the number.</param>
    /// <param name="exponent">Exponent of the number.</param>
    /// <returns>String representation of the number.</returns>
    private static String getDigit(int value, int exponent)
    {
        switch (exponent)
        {
            case 3:
                return GREEK_THOUSAND_SIGN + getDigit(value, 0);
            case 2:
                return Character.toString(HUNDREDS.charAt(value - 1));
            case 1:
                return Character.toString(TENS.charAt(value - 1));
            case 0:
                return (value == 6) ? GREEK_SIX : Character.toString(DIGITS.charAt(value - 1));
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + msString.format("Unexpected exponent: '{0}'", exponent));
        }
    }

    private static final String DIGITS = "αβγδε?ζηθ";
    private static final String TENS = "ικλμνξοπϟ";
    private static final String HUNDREDS = "ρστυφχψωϡ";
    private static final String GREEK_THOUSAND_SIGN = ",";
    private static final String GREEK_SIX = "στ";
}

