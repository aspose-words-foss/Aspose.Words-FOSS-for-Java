// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/05/2017 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msNumber;


@Test
public class TestFormatSByte
{
    @Test
    public void testBasic()
    {
        byte sbyteValue = 0x1F;
        msAssert.areEqual("31", Byte.toString(sbyteValue));
        msAssert.areEqual("31", msNumber.format(sbyteValue, "D2"));
        msAssert.areEqual("31", msNumber.format(sbyteValue, "d2"));
        msAssert.areEqual("31", msNumber.format(sbyteValue, "D1"));
        msAssert.areEqual("31", msNumber.format(sbyteValue, "d1"));
        msAssert.areEqual("1F", msNumber.format(sbyteValue, "X"));
        msAssert.areEqual("1f", msNumber.format(sbyteValue, "x"));
        msAssert.areEqual("1F", msNumber.format(sbyteValue, "X1"));
        msAssert.areEqual("1f", msNumber.format(sbyteValue, "x1"));
        msAssert.areEqual("31", msNumber.format(sbyteValue, "G"));
        msAssert.areEqual("31", msNumber.format(sbyteValue, "g"));
    }

    @Test
    public void testMinusSign()
    {
        byte sbyteValue = -31;
        msAssert.areEqual("-31", msNumber.format(sbyteValue, "D2"));
        msAssert.areEqual("-31", msNumber.format(sbyteValue, "d2"));
        msAssert.areEqual("-31", msNumber.format(sbyteValue, "D1"));
        msAssert.areEqual("-31", msNumber.format(sbyteValue, "d1"));
        msAssert.areEqual("-31", msNumber.format(sbyteValue, "D"));
        msAssert.areEqual("-31", msNumber.format(sbyteValue, "d"));
        msAssert.areEqual("-31", msNumber.format(sbyteValue, "G"));
        msAssert.areEqual("-31", msNumber.format(sbyteValue, "g"));
    }

    @Test
    public void testZeroPad()
    {
        byte sbyteValue = 31;
        byte sbyteValue1 = -31;
        msAssert.areEqual("00031", msNumber.format(sbyteValue, "D5"));
        msAssert.areEqual("00031", msNumber.format(sbyteValue, "d5"));
        msAssert.areEqual("-00031", msNumber.format(sbyteValue1, "d5"));

        msAssert.areEqual("0000000031", msNumber.format(sbyteValue, "D10"));
        msAssert.areEqual("0000000031", msNumber.format(sbyteValue, "d10"));
        msAssert.areEqual("-0000000031", msNumber.format(sbyteValue1, "d10"));

        msAssert.areEqual("0001F", msNumber.format(sbyteValue, "X5"));
        msAssert.areEqual("0001f", msNumber.format(sbyteValue, "x5"));
        msAssert.areEqual("000e1", msNumber.format(sbyteValue1, "x5"));
    }

    @Test
    public void testSByteArray()
    {
        msAssert.areEqual("-29", Byte.toString(sbyteArray[3]));
        msAssert.areEqual("00126", msNumber.format(sbyteArray[4], "d5"));

        msAssert.areEqual("89", msNumber.format(sbyteArray[2], "X2"));
        msAssert.areEqual("7e", msNumber.format(sbyteArray[4], "x"));
    }

    @Test
    public void testBorder()
    {
        msAssert.areEqual("127", Byte.toString(sbyteArray[0]));
        msAssert.areEqual("-128", Byte.toString(sbyteArray[1]));
        msAssert.areEqual("-119", Byte.toString(sbyteArray[2]));
        msAssert.areEqual("-29", Byte.toString(sbyteArray[3]));
        msAssert.areEqual("126", Byte.toString(sbyteArray[4]));
        msAssert.areEqual("117", Byte.toString(sbyteArray[5]));
        msAssert.areEqual("27", Byte.toString(sbyteArray[6]));
        msAssert.areEqual("-128", Byte.toString(sbyteArray[7]));
        msAssert.areEqual("-127", Byte.toString(sbyteArray[8]));
        msAssert.areEqual("-118", Byte.toString(sbyteArray[9]));
        msAssert.areEqual("-28", Byte.toString(sbyteArray[10]));
        msAssert.areEqual("127", Byte.toString(sbyteArray[11]));
        msAssert.areEqual("118", Byte.toString(sbyteArray[12]));
        msAssert.areEqual("28", Byte.toString(sbyteArray[13]));
    }

    @Test
    public void testMax()
    {
        msAssert.areEqual("127", Byte.toString(maxValue));
        msAssert.areEqual("127", msNumber.format(maxValue, "D"));
        msAssert.areEqual("127", msNumber.format(maxValue, "d"));
        msAssert.areEqual("127", msNumber.format(maxValue, "G"));
        msAssert.areEqual("127", msNumber.format(maxValue, "g"));

        msAssert.areEqual("127", msNumber.format(maxValue, "D1"));
        msAssert.areEqual("127", msNumber.format(maxValue, "d2"));
        msAssert.areEqual("00127", msNumber.format(maxValue, "D5"));

        msAssert.areEqual("7F", msNumber.format(maxValue, "X"));
        msAssert.areEqual("7f", msNumber.format(maxValue, "x"));
    }

    @Test
    public void testMin()
    {
        msAssert.areEqual("-128", Byte.toString(minValue));
        msAssert.areEqual("-128", msNumber.format(minValue, "D"));
        msAssert.areEqual("-128", msNumber.format(minValue, "d"));
        msAssert.areEqual("-128", msNumber.format(minValue, "G"));
        msAssert.areEqual("-128", msNumber.format(minValue, "g"));

        msAssert.areEqual("-128", msNumber.format(minValue, "D1"));
        msAssert.areEqual("-128", msNumber.format(minValue, "d2"));
        msAssert.areEqual("-00128", msNumber.format(minValue, "D5"));

        msAssert.areEqual("80", msNumber.format(minValue, "X"));
        msAssert.areEqual("80", msNumber.format(minValue, "x"));
    }

    private static byte maxValue = Byte.MAX_VALUE;
    private static byte minValue = Byte.MIN_VALUE;

    private static byte[] sbyteArray =
    {
        maxValue,
        (byte)(maxValue + 1),
        (byte)(maxValue + 10),
        (byte)(maxValue + 100),
        (byte)(maxValue - 1),
        (byte)(maxValue - 10),
        (byte)(maxValue - 100),
        minValue,
        (byte)(minValue + 1),
        (byte)(minValue + 10),
        (byte)(minValue + 100),
        (byte)(minValue - 1),
        (byte)(minValue - 10),
        (byte)(minValue - 100)
    };
}

