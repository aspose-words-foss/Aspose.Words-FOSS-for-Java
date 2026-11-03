// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/07/2019 by Edward Voronov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msString;


/// <summary>
/// This class is a helper for <see cref="NumberConverterCore"/>,
/// its method converts a number into Vietnamese numbering style.
/// </summary>
/*static*/ class VietnameseNumber
{
	/* Simulation of static class by using private constructor */
	private VietnameseNumber()
	{}

    static String toVietnameseCardinal(long value)
    {
        if (value < 0)
            return "";

        if (value == 0 || value > 1000)
            return NOTHING;

        if (value == 1000)
            return G_DIGITS[1] + SEPARATOR + THOUSANDS;

        ArrayList<Integer> digits = GroupSplitter.splitToGroups(value, 1);

        int ones = digits.get(0);
        int tens = digits.size() > 1 ? digits.get(1) : 0;
        int hundreds = digits.size() > 2 ? digits.get(2) : 0;

        ArrayList<String> builder = new ArrayList<String>();

        if (hundreds != 0)
        {
            msArrayList.add(builder, G_DIGITS[hundreds]);
            msArrayList.add(builder, HUNDREDS);
        }

        if (tens != 0)
        {
            if (tens == 1)
            {
                msArrayList.add(builder, TEN);
            }
            else
            {
                msArrayList.add(builder, G_DIGITS[tens]);
                msArrayList.add(builder, TENS);
            }
        }

        if (ones != 0)
        {
            if (hundreds == 1 && tens == 0)
                msArrayList.add(builder, ONES_PREFIX);

            if (ones == 1 && tens > 1)
                msArrayList.add(builder, ONE_OVERRIDE);
            else if (ones == 5 && tens != 0)
                msArrayList.add(builder, FIVE_OVERRIDE);
            else
                msArrayList.add(builder, G_DIGITS[ones]);
        }

        // alexnosk: String.Join(string, IEnumerable<string>) overload is available starting from .NET 4.0.
        return msString.join(SEPARATOR, msArrayList.toArray(builder, new String[0]));
    }

    private static /*final*/ String[] G_DIGITS =
    {
        null, "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín"
    };

    private static final String ONE_OVERRIDE = "mốt";
    private static final String FIVE_OVERRIDE = "lăm";

    private static final String NOTHING = "không";
    private static final String ONES_PREFIX = "lẻ";
    private static final String TEN = "mười";
    private static final String TENS = "mươi";
    private static final String HUNDREDS = "trăm";
    private static final String THOUSANDS = "ngàn";

    private static final String SEPARATOR = " ";
}

