// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/02/2017 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.System.msUInt;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestFormatUInt
{
    @Test
    public void testBasic()
    {
        /*uint*/int uintValue = 0x1F;
        msAssert.areEqual("31", msUInt.toString(uintValue));
        msAssert.areEqual("31", msUInt.toString(uintValue, "D2"));
        msAssert.areEqual("31", msUInt.toString(uintValue, "d2"));
        msAssert.areEqual("31", msUInt.toString(uintValue, "D1"));
        msAssert.areEqual("31", msUInt.toString(uintValue, "d1"));
        msAssert.areEqual("1F", msUInt.toString(uintValue, "X"));
        msAssert.areEqual("1f", msUInt.toString(uintValue, "x"));
        msAssert.areEqual("1F", msUInt.toString(uintValue, "X1"));
        msAssert.areEqual("1f", msUInt.toString(uintValue, "x1"));
        uintValue = (int) 0x99FFFFFFL;
        msAssert.areEqual("2583691263", msUInt.toString(uintValue));
        msAssert.areEqual("2583691263", msUInt.toString(uintValue, "D2"));
        msAssert.areEqual("2583691263", msUInt.toString(uintValue, "d2"));
        msAssert.areEqual("2583691263", msUInt.toString(uintValue, "D1"));
        msAssert.areEqual("2583691263", msUInt.toString(uintValue, "d1"));
        msAssert.areEqual("99FFFFFF", msUInt.toString(uintValue, "X"));
        msAssert.areEqual("99ffffff", msUInt.toString(uintValue, "x"));
        msAssert.areEqual("99FFFFFF", msUInt.toString(uintValue, "X1"));
        msAssert.areEqual("99ffffff", msUInt.toString(uintValue, "x1"));
    }

    @Test
    public void testZeroPad()
    {
        /*uint*/int uintValue = 31;
        msAssert.areEqual("00031", msUInt.toString(uintValue, "D5"));
        msAssert.areEqual("00031", msUInt.toString(uintValue, "d5"));

        msAssert.areEqual("0001F", msUInt.toString(uintValue, "X5"));
        msAssert.areEqual("0001f", msUInt.toString(uintValue, "x5"));
    }

    @Test
    public void testUIntArray()
    {
        msAssert.areEqual("99", msUInt.toString(uintArray[3]));
        msAssert.areEqual("4294967294", msUInt.toString(uintArray[4], "d5"));

        msAssert.areEqual("09", msUInt.toString(uintArray[2], "X2"));
        msAssert.areEqual("fffffffe", msUInt.toString(uintArray[4], "x"));
    }

    @Test
    public void testBorder()
    {
        msAssert.areEqual("4294967295", msUInt.toString(uintArray[0]));
        msAssert.areEqual("0", msUInt.toString(uintArray[1]));
        msAssert.areEqual("9", msUInt.toString(uintArray[2]));
        msAssert.areEqual("99", msUInt.toString(uintArray[3]));
        msAssert.areEqual("4294967294", msUInt.toString(uintArray[4]));
        msAssert.areEqual("4294967285", msUInt.toString(uintArray[5]));
        msAssert.areEqual("4294967195", msUInt.toString(uintArray[6]));
        msAssert.areEqual("0", msUInt.toString(uintArray[7]));
        msAssert.areEqual("1", msUInt.toString(uintArray[8]));
        msAssert.areEqual("10", msUInt.toString(uintArray[9]));
        msAssert.areEqual("100", msUInt.toString(uintArray[10]));
        msAssert.areEqual("4294967295", msUInt.toString(uintArray[11]));
        msAssert.areEqual("4294967286", msUInt.toString(uintArray[12]));
        msAssert.areEqual("4294967196", msUInt.toString(uintArray[13]));
    }

    @Test
    public void testMax()
    {
        msAssert.areEqual("4294967295", msUInt.toString(maxValue));
        msAssert.areEqual("4294967295", msUInt.toString(maxValue, "D"));
        msAssert.areEqual("4294967295", msUInt.toString(maxValue, "d"));

        msAssert.areEqual("4294967295", msUInt.toString(maxValue, "D1"));
        msAssert.areEqual("4294967295", msUInt.toString(maxValue, "d2"));
        msAssert.areEqual("4294967295", msUInt.toString(maxValue, "D5"));

        msAssert.areEqual("FFFFFFFF", msUInt.toString(maxValue, "X"));
        msAssert.areEqual("ffffffff", msUInt.toString(maxValue, "x"));
    }

    @Test
    public void testMin()
    {
        msAssert.areEqual("0", msUInt.toString(minValue));
        msAssert.areEqual("0", msUInt.toString(minValue, "D"));
        msAssert.areEqual("0", msUInt.toString(minValue, "d"));
        msAssert.areEqual("0", msUInt.toString(minValue, "G"));
        msAssert.areEqual("0", msUInt.toString(minValue, "g"));

        msAssert.areEqual("0", msUInt.toString(minValue, "D1"));
        msAssert.areEqual("00", msUInt.toString(minValue, "d2"));
        msAssert.areEqual("00000", msUInt.toString(minValue, "D5"));

        msAssert.areEqual("0", msUInt.toString(minValue, "X"));
        msAssert.areEqual("0", msUInt.toString(minValue, "x"));
    }

    private static /*uint*/int maxValue = msUInt.MAX_VALUE;
    private static /*uint*/int minValue = msUInt.MIN_VALUE;

    private static /*uint*/int[] uintArray =
    {
        maxValue,
        (/*uint*/int)((maxValue & 0xFFFFFFFFL) + 1),
        (/*uint*/int)((maxValue & 0xFFFFFFFFL) + 10),
        (/*uint*/int)((maxValue & 0xFFFFFFFFL) + 100),
        (/*uint*/int)((maxValue & 0xFFFFFFFFL) - 1),
        (/*uint*/int)((maxValue & 0xFFFFFFFFL) - 10),
        (/*uint*/int)((maxValue & 0xFFFFFFFFL) - 100),
        minValue,
        (/*uint*/int)((minValue & 0xFFFFFFFFL) + 1),
        (/*uint*/int)((minValue & 0xFFFFFFFFL) + 10),
        (/*uint*/int)((minValue & 0xFFFFFFFFL) + 100),
        (/*uint*/int)((minValue & 0xFFFFFFFFL) - 1),
        (/*uint*/int)((minValue & 0xFFFFFFFFL) - 10),
        (/*uint*/int)((minValue & 0xFFFFFFFFL) - 100)
    };
}
