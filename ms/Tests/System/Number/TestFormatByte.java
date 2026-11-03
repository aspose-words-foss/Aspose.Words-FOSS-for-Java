// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/02/2017 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.System.msByte;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestFormatByte
{
    @Test
    public void testBasic()
    {
        byte byteValue = (byte) 0x1F;
        msAssert.areEqual("31", msByte.toString(byteValue));
        msAssert.areEqual("31", msByte.toString(byteValue, "D2"));
        msAssert.areEqual("31", msByte.toString(byteValue, "d2"));
        msAssert.areEqual("31", msByte.toString(byteValue, "D1"));
        msAssert.areEqual("31", msByte.toString(byteValue, "d1"));
        msAssert.areEqual("1F", msByte.toString(byteValue, "X"));
        msAssert.areEqual("1f", msByte.toString(byteValue, "x"));
        msAssert.areEqual("1F", msByte.toString(byteValue, "X1"));
        msAssert.areEqual("1f", msByte.toString(byteValue, "x1"));
        msAssert.areEqual("31", msByte.toString(byteValue, "G2"));
        msAssert.areEqual("31", msByte.toString(byteValue, "g2"));
        byteValue = (byte) 0xF1;
        msAssert.areEqual("241", msByte.toString(byteValue));
        msAssert.areEqual("241", msByte.toString(byteValue, "D2"));
        msAssert.areEqual("241", msByte.toString(byteValue, "d2"));
        msAssert.areEqual("241", msByte.toString(byteValue, "D1"));
        msAssert.areEqual("241", msByte.toString(byteValue, "d1"));
        msAssert.areEqual("F1", msByte.toString(byteValue, "X"));
        msAssert.areEqual("f1", msByte.toString(byteValue, "x"));
        msAssert.areEqual("F1", msByte.toString(byteValue, "X1"));
        msAssert.areEqual("f1", msByte.toString(byteValue, "x1"));
        msAssert.areEqual("241", msByte.toString(byteValue, "G3"));
        msAssert.areEqual("241", msByte.toString(byteValue, "g3"));
    }

    @Test
    public void testZeroPad()
    {
        byte byteValue = (byte) 31;
        msAssert.areEqual("00031", msByte.toString(byteValue, "D5"));
        msAssert.areEqual("00031", msByte.toString(byteValue, "d5"));

        msAssert.areEqual("0001F", msByte.toString(byteValue, "X5"));
        msAssert.areEqual("0001f", msByte.toString(byteValue, "x5"));
    }

    @Test
    public void testByteArray()
    {
        msAssert.areEqual("99", msByte.toString(byteArray[3]));
        msAssert.areEqual("00254", msByte.toString(byteArray[4], "d5"));

        msAssert.areEqual("09", msByte.toString(byteArray[2], "X2"));
        msAssert.areEqual("fe", msByte.toString(byteArray[4], "x"));
    }

    @Test
    public void testBorder()
    {
        msAssert.areEqual("255", msByte.toString(byteArray[0]));
        msAssert.areEqual("0", msByte.toString(byteArray[1]));
        msAssert.areEqual("9", msByte.toString(byteArray[2]));
        msAssert.areEqual("99", msByte.toString(byteArray[3]));
        msAssert.areEqual("254", msByte.toString(byteArray[4]));
        msAssert.areEqual("245", msByte.toString(byteArray[5]));
        msAssert.areEqual("155", msByte.toString(byteArray[6]));
        msAssert.areEqual("0", msByte.toString(byteArray[7]));
        msAssert.areEqual("1", msByte.toString(byteArray[8]));
        msAssert.areEqual("10", msByte.toString(byteArray[9]));
        msAssert.areEqual("100", msByte.toString(byteArray[10]));
        msAssert.areEqual("255", msByte.toString(byteArray[11]));
        msAssert.areEqual("246", msByte.toString(byteArray[12]));
        msAssert.areEqual("156", msByte.toString(byteArray[13]));
    }

    @Test
    public void testMax()
    {
        msAssert.areEqual("255", msByte.toString(maxValue));
        msAssert.areEqual("255", msByte.toString(maxValue, "D"));
        msAssert.areEqual("255", msByte.toString(maxValue, "d"));
        msAssert.areEqual("255", msByte.toString(maxValue, "G"));
        msAssert.areEqual("255", msByte.toString(maxValue, "g"));

        msAssert.areEqual("255", msByte.toString(maxValue, "D1"));
        msAssert.areEqual("255", msByte.toString(maxValue, "d2"));
        msAssert.areEqual("00255", msByte.toString(maxValue, "D5"));

        msAssert.areEqual("FF", msByte.toString(maxValue, "X"));
        msAssert.areEqual("ff", msByte.toString(maxValue, "x"));
    }

    @Test
    public void testMin()
    {
        msAssert.areEqual("0", msByte.toString(minValue));
        msAssert.areEqual("0", msByte.toString(minValue, "D"));
        msAssert.areEqual("0", msByte.toString(minValue, "d"));
        msAssert.areEqual("0", msByte.toString(minValue, "G"));
        msAssert.areEqual("0", msByte.toString(minValue, "g"));

        msAssert.areEqual("0", msByte.toString(minValue, "D1"));
        msAssert.areEqual("00", msByte.toString(minValue, "d2"));
        msAssert.areEqual("00000", msByte.toString(minValue, "D5"));

        msAssert.areEqual("0", msByte.toString(minValue, "X"));
        msAssert.areEqual("0", msByte.toString(minValue, "x"));
    }

    private static byte maxValue = msByte.MAX_VALUE;
    private static byte minValue = msByte.MIN_VALUE;

    private static byte[] byteArray =
    {
        maxValue,
        (byte)((maxValue & 0xFF) + 1),
        (byte)((maxValue & 0xFF) + 10),
        (byte)((maxValue & 0xFF) + 100),
        (byte)((maxValue & 0xFF) - 1),
        (byte)((maxValue & 0xFF) - 10),
        (byte)((maxValue & 0xFF) - 100),
        minValue,
        (byte)((minValue & 0xFF) + 1),
        (byte)((minValue & 0xFF) + 10),
        (byte)((minValue & 0xFF) + 100),
        (byte)((minValue & 0xFF) - 1),
        (byte)((minValue & 0xFF) - 10),
        (byte)((minValue & 0xFF) - 100)
    };
}
