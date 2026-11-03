// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/05/2017 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msNumber;


@Test
public class TestFormatLong
{
    @Test
    public void testBasic()
    {
        long longValue = 0x1234567890ABCDEFL;
        msAssert.areEqual("1311768467294899695", Long.toString(longValue));
        msAssert.areEqual("1311768467294899695", msNumber.format(longValue, "D2"));
        msAssert.areEqual("1311768467294899695", msNumber.format(longValue, "d2"));
        msAssert.areEqual("1311768467294899695", msNumber.format(longValue, "D1"));
        msAssert.areEqual("1311768467294899695", msNumber.format(longValue, "d1"));
        msAssert.areEqual("1234567890ABCDEF", msNumber.format(longValue, "X"));
        msAssert.areEqual("1234567890abcdef", msNumber.format(longValue, "x"));
        msAssert.areEqual("1234567890ABCDEF", msNumber.format(longValue, "X1"));
        msAssert.areEqual("1234567890abcdef", msNumber.format(longValue, "x1"));
        msAssert.areEqual("1311768467294899695", msNumber.format(longValue, "G"));
        msAssert.areEqual("1311768467294899695", msNumber.format(longValue, "g"));
    }

    @Test
    public void testMinusSign()
    {
        long longValue = -31568343242576L;
        msAssert.areEqual("-31568343242576", msNumber.format(longValue, "D2"));
        msAssert.areEqual("-31568343242576", msNumber.format(longValue, "d2"));
        msAssert.areEqual("-31568343242576", msNumber.format(longValue, "D1"));
        msAssert.areEqual("-31568343242576", msNumber.format(longValue, "d1"));
        msAssert.areEqual("-31568343242576", msNumber.format(longValue, "D"));
        msAssert.areEqual("-31568343242576", msNumber.format(longValue, "d"));
        msAssert.areEqual("-31568343242576", msNumber.format(longValue, "G"));
        msAssert.areEqual("-31568343242576", msNumber.format(longValue, "g"));
    }

    @Test
    public void testZeroPad()
    {
        long longValue = 31568343242576L;
        long longValue1 = -31568343242576L;
        msAssert.areEqual("31568343242576", msNumber.format(longValue, "D7"));
        msAssert.areEqual("31568343242576", msNumber.format(longValue, "d7"));
        msAssert.areEqual("-31568343242576", msNumber.format(longValue1, "d7"));

        msAssert.areEqual("00031568343242576", msNumber.format(longValue, "D17"));
        msAssert.areEqual("00031568343242576", msNumber.format(longValue, "d17"));
        msAssert.areEqual("-00031568343242576", msNumber.format(longValue1, "d17"));

        msAssert.areEqual("1CB613E29750", msNumber.format(longValue, "X5"));
        msAssert.areEqual("1cb613e29750", msNumber.format(longValue, "x5"));
        msAssert.areEqual("ffffe349ec1d68b0", msNumber.format(longValue1, "x5"));
    }

    @Test
    public void testlongArray()
    {
        msAssert.areEqual("-9223372036854775709", Long.toString(longArray[3]));
        msAssert.areEqual("9223372036854775806", msNumber.format(longArray[4], "d7"));

        msAssert.areEqual("8000000000000009", msNumber.format(longArray[2], "X2"));
        msAssert.areEqual("7ffffffffffffffe", msNumber.format(longArray[4], "x"));
    }

    @Test
    public void testBorder()
    {
        msAssert.areEqual("9223372036854775807", Long.toString(longArray[0]));
        msAssert.areEqual("-9223372036854775808", Long.toString(longArray[1]));
        msAssert.areEqual("-9223372036854775799", Long.toString(longArray[2]));
        msAssert.areEqual("-9223372036854775709", Long.toString(longArray[3]));
        msAssert.areEqual("9223372036854775806", Long.toString(longArray[4]));
        msAssert.areEqual("9223372036854775797", Long.toString(longArray[5]));
        msAssert.areEqual("9223372036854775707", Long.toString(longArray[6]));
        msAssert.areEqual("-9223372036854775808", Long.toString(longArray[7]));
        msAssert.areEqual("-9223372036854775807", Long.toString(longArray[8]));
        msAssert.areEqual("-9223372036854775798", Long.toString(longArray[9]));
        msAssert.areEqual("-9223372036854775708", Long.toString(longArray[10]));
        msAssert.areEqual("9223372036854775807", Long.toString(longArray[11]));
        msAssert.areEqual("9223372036854775798", Long.toString(longArray[12]));
        msAssert.areEqual("9223372036854775708", Long.toString(longArray[13]));
    }

    @Test
    public void testMax()
    {
        msAssert.areEqual("9223372036854775807", Long.toString(maxValue));
        msAssert.areEqual("9223372036854775807", msNumber.format(maxValue, "D"));
        msAssert.areEqual("9223372036854775807", msNumber.format(maxValue, "d"));
        msAssert.areEqual("9223372036854775807", msNumber.format(maxValue, "G"));
        msAssert.areEqual("9223372036854775807", msNumber.format(maxValue, "g"));

        msAssert.areEqual("9223372036854775807", msNumber.format(maxValue, "D1"));
        msAssert.areEqual("9223372036854775807", msNumber.format(maxValue, "d2"));
        msAssert.areEqual("009223372036854775807", msNumber.format(maxValue, "D21"));

        msAssert.areEqual("7FFFFFFFFFFFFFFF", msNumber.format(maxValue, "X"));
        msAssert.areEqual("7fffffffffffffff", msNumber.format(maxValue, "x"));
    }

    @Test
    public void testMin()
    {
        msAssert.areEqual("-9223372036854775808", Long.toString(minValue));
        msAssert.areEqual("-9223372036854775808", msNumber.format(minValue, "D"));
        msAssert.areEqual("-9223372036854775808", msNumber.format(minValue, "d"));
        msAssert.areEqual("-9223372036854775808", msNumber.format(minValue, "G"));
        msAssert.areEqual("-9223372036854775808", msNumber.format(minValue, "g"));

        msAssert.areEqual("-9223372036854775808", msNumber.format(minValue, "D1"));
        msAssert.areEqual("-9223372036854775808", msNumber.format(minValue, "d2"));
        msAssert.areEqual("-009223372036854775808", msNumber.format(minValue, "D21"));

        msAssert.areEqual("8000000000000000", msNumber.format(minValue, "X"));
        msAssert.areEqual("8000000000000000", msNumber.format(minValue, "x"));
    }

    private static long maxValue = Long.MAX_VALUE;
    private static long minValue = Long.MIN_VALUE;

    private static long[] longArray =
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

