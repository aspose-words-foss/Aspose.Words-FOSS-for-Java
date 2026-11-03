// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/08/2021 by Alexey Minenkov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


/// <summary>
/// This class is a helper for <see cref="NumberConverterCore"/>,
/// its methods convert a number into ThaiArabic numbering style.
/// </summary>
/*static*/ class ThaiArabicNumber
{
	/* Simulation of static class by using private constructor */
	private ThaiArabicNumber()
	{}

    static String toThaiArabic(long value)
    {
        ArrayList<Integer> groups = GroupSplitter.splitToGroups(value, 1);
        StringBuilder sb = new StringBuilder();

        for (int rank = groups.size() - 1; rank >= 0; rank--)
        {
            int groupValue = groups.get(rank);
            sb.append(DIGITS.charAt(groupValue));
        }

        return sb.toString();
    }

    private static final String DIGITS = "๐๑๒๓๔๕๖๗๘๙";
}

