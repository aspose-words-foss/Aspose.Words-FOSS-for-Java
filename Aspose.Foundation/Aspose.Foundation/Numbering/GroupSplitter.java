// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/06/2016 by Ivan Lyagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Utility for splitting numbers into groups of digits.
/// </summary>
/*static*/ class GroupSplitter
{
	/* Simulation of static class by using private constructor */
	private GroupSplitter()
	{}

    /// <summary>
    /// <para>Returns list of integers where each element consists of several digits of a given integer.</para>
    /// <para>The lowest digit has index of 0 in the list.</para>
    /// <para>For example, SplitToGroups(12345, 3) gives list: {345, 12}.</para>
    /// </summary>
    public static ArrayList<Integer> splitToGroups(long value, int digitsInGroup)
    {
        Debug.assertTrue(value >= 0);
        ArrayList<Integer> digitsList = new ArrayList<Integer>(10);
        int divisor = divisorByDigitNumber(digitsInGroup);

        do
        {
            msArrayList.add(digitsList, (int)(value % divisor));
            value /= divisor;
        }
        while (value > 0);

        return digitsList;
    }

    private static int divisorByDigitNumber(int value)
    {
        switch (value)
        {
            case 1:
                return 10;
            case 3:
                return 1000;
            case 4:
                return 10000;
            case 5:
                return 100000;
            default:
                Debug.fail("For now, other values aren't used. Add them if needed.");
                return 0;
        }
    }
}

