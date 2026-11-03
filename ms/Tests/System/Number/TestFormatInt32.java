// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/05/2017 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msNumber;


@Test
public class TestFormatInt32
{
    @Test
    public void testBasic()
    {
        int intValue = 0x1FFFFFFF;
        msAssert.areEqual("536870911", Integer.toString(intValue));
        msAssert.areEqual("536870911", msNumber.format(intValue, "D2"));
        msAssert.areEqual("536870911", msNumber.format(intValue, "d2"));
        msAssert.areEqual("536870911", msNumber.format(intValue, "D1"));
        msAssert.areEqual("536870911", msNumber.format(intValue, "d1"));
        msAssert.areEqual("1FFFFFFF", msNumber.format(intValue, "X"));
        msAssert.areEqual("1fffffff", msNumber.format(intValue, "x"));
        msAssert.areEqual("1FFFFFFF", msNumber.format(intValue, "X1"));
        msAssert.areEqual("1fffffff", msNumber.format(intValue, "x1"));
        msAssert.areEqual("536870911", msNumber.format(intValue, "G"));
        msAssert.areEqual("536870911", msNumber.format(intValue, "g"));
    }

    @Test
    public void testMinusSign()
    {
        int intValue = -3156876;
        msAssert.areEqual("-3156876", msNumber.format(intValue, "D2"));
        msAssert.areEqual("-3156876", msNumber.format(intValue, "d2"));
        msAssert.areEqual("-3156876", msNumber.format(intValue, "D1"));
        msAssert.areEqual("-3156876", msNumber.format(intValue, "d1"));
        msAssert.areEqual("-3156876", msNumber.format(intValue, "D"));
        msAssert.areEqual("-3156876", msNumber.format(intValue, "d"));
        msAssert.areEqual("-3156876", msNumber.format(intValue, "G"));
        msAssert.areEqual("-3156876", msNumber.format(intValue, "g"));
    }

    @Test
    public void testZeroPad()
    {
        int intValue = 3156876;
        int intValue1 = -3156876;
        msAssert.areEqual("3156876", msNumber.format(intValue, "D7"));
        msAssert.areEqual("3156876", msNumber.format(intValue, "d7"));
        msAssert.areEqual("-3156876", msNumber.format(intValue1, "d7"));

        msAssert.areEqual("0003156876", msNumber.format(intValue, "D10"));
        msAssert.areEqual("0003156876", msNumber.format(intValue, "d10"));
        msAssert.areEqual("-0003156876", msNumber.format(intValue1, "d10"));

        msAssert.areEqual("302B8C", msNumber.format(intValue, "X5"));
        msAssert.areEqual("302b8c", msNumber.format(intValue, "x5"));
        msAssert.areEqual("ffcfd474", msNumber.format(intValue1, "x5"));
    }

    @Test
    public void testintArray()
    {
        msAssert.areEqual("-2147483549", Integer.toString(intArray[3]));
        msAssert.areEqual("2147483646", msNumber.format(intArray[4], "d7"));

        msAssert.areEqual("80000009", msNumber.format(intArray[2], "X2"));
        msAssert.areEqual("7ffffffe", msNumber.format(intArray[4], "x"));
    }

    @Test
    public void testBorder()
    {
        msAssert.areEqual("2147483647", Integer.toString(intArray[0]));
        msAssert.areEqual("-2147483648", Integer.toString(intArray[1]));
        msAssert.areEqual("-2147483639", Integer.toString(intArray[2]));
        msAssert.areEqual("-2147483549", Integer.toString(intArray[3]));
        msAssert.areEqual("2147483646", Integer.toString(intArray[4]));
        msAssert.areEqual("2147483637", Integer.toString(intArray[5]));
        msAssert.areEqual("2147483547", Integer.toString(intArray[6]));
        msAssert.areEqual("-2147483648", Integer.toString(intArray[7]));
        msAssert.areEqual("-2147483647", Integer.toString(intArray[8]));
        msAssert.areEqual("-2147483638", Integer.toString(intArray[9]));
        msAssert.areEqual("-2147483548", Integer.toString(intArray[10]));
        msAssert.areEqual("2147483647", Integer.toString(intArray[11]));
        msAssert.areEqual("2147483638", Integer.toString(intArray[12]));
        msAssert.areEqual("2147483548", Integer.toString(intArray[13]));
    }

    @Test
    public void testMax()
    {
        msAssert.areEqual("2147483647", Integer.toString(maxValue));
        msAssert.areEqual("2147483647", msNumber.format(maxValue, "D"));
        msAssert.areEqual("2147483647", msNumber.format(maxValue, "d"));
        msAssert.areEqual("2147483647", msNumber.format(maxValue, "G"));
        msAssert.areEqual("2147483647", msNumber.format(maxValue, "g"));

        msAssert.areEqual("2147483647", msNumber.format(maxValue, "D1"));
        msAssert.areEqual("2147483647", msNumber.format(maxValue, "d2"));
        msAssert.areEqual("002147483647", msNumber.format(maxValue, "D12"));

        msAssert.areEqual("7FFFFFFF", msNumber.format(maxValue, "X"));
        msAssert.areEqual("7fffffff", msNumber.format(maxValue, "x"));
    }

    @Test
    public void testMin()
    {
        msAssert.areEqual("-2147483648", Integer.toString(minValue));
        msAssert.areEqual("-2147483648", msNumber.format(minValue, "D"));
        msAssert.areEqual("-2147483648", msNumber.format(minValue, "d"));
        msAssert.areEqual("-2147483648", msNumber.format(minValue, "G"));
        msAssert.areEqual("-2147483648", msNumber.format(minValue, "g"));

        msAssert.areEqual("-2147483648", msNumber.format(minValue, "D1"));
        msAssert.areEqual("-2147483648", msNumber.format(minValue, "d2"));
        msAssert.areEqual("-002147483648", msNumber.format(minValue, "D12"));

        msAssert.areEqual("80000000", msNumber.format(minValue, "X"));
        msAssert.areEqual("80000000", msNumber.format(minValue, "x"));
    }

    private static int maxValue = Integer.MAX_VALUE;
    private static int minValue = Integer.MIN_VALUE;

    private static int[] intArray =
    {
        maxValue,
        maxValue + 1,
        maxValue + 10,
        maxValue + 100,
        maxValue - 1,
        maxValue - 10,
        maxValue - 100,
        minValue,
        minValue + 1,
        minValue + 10,
        minValue + 100,
        minValue - 1,
        minValue - 10,
        minValue - 100
    };
}

