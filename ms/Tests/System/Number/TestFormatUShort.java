// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/02/2017 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.System.msUShort;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestFormatUShort
{
    @Test
    public void testBasic()
    {
        /*ushort*/short ushortValue = (short) 0x1F;
        msAssert.areEqual("31", msUShort.toString(ushortValue));
        msAssert.areEqual("31", msUShort.toString(ushortValue, "D2"));
        msAssert.areEqual("31", msUShort.toString(ushortValue, "d2"));
        msAssert.areEqual("31", msUShort.toString(ushortValue, "D1"));
        msAssert.areEqual("31", msUShort.toString(ushortValue, "d1"));
        msAssert.areEqual("1F", msUShort.toString(ushortValue, "X"));
        msAssert.areEqual("1f", msUShort.toString(ushortValue, "x"));
        msAssert.areEqual("1F", msUShort.toString(ushortValue, "X1"));
        msAssert.areEqual("1f", msUShort.toString(ushortValue, "x1"));
        ushortValue = (short) 0x99F1;
        msAssert.areEqual("39409", msUShort.toString(ushortValue));
        msAssert.areEqual("39409", msUShort.toString(ushortValue, "D2"));
        msAssert.areEqual("39409", msUShort.toString(ushortValue, "d2"));
        msAssert.areEqual("39409", msUShort.toString(ushortValue, "D1"));
        msAssert.areEqual("39409", msUShort.toString(ushortValue, "d1"));
        msAssert.areEqual("99F1", msUShort.toString(ushortValue, "X"));
        msAssert.areEqual("99f1", msUShort.toString(ushortValue, "x"));
        msAssert.areEqual("99F1", msUShort.toString(ushortValue, "X1"));
        msAssert.areEqual("99f1", msUShort.toString(ushortValue, "x1"));
    }

    @Test
    public void testZeroPad()
    {
        /*ushort*/short ushortValue = (short) 31;
        msAssert.areEqual("00031", msUShort.toString(ushortValue, "D5"));
        msAssert.areEqual("00031", msUShort.toString(ushortValue, "d5"));

        msAssert.areEqual("0001F", msUShort.toString(ushortValue, "X5"));
        msAssert.areEqual("0001f", msUShort.toString(ushortValue, "x5"));
    }

    @Test
    public void testUShortArray()
    {
        msAssert.areEqual("99", msUShort.toString(ushortArray[3]));
        msAssert.areEqual("65534", msUShort.toString(ushortArray[4], "d5"));

        msAssert.areEqual("09", msUShort.toString(ushortArray[2], "X2"));
        msAssert.areEqual("fffe", msUShort.toString(ushortArray[4], "x"));
    }

    @Test
    public void testBorder()
    {
        msAssert.areEqual("65535", msUShort.toString(ushortArray[0]));
        msAssert.areEqual("0", msUShort.toString(ushortArray[1]));
        msAssert.areEqual("9", msUShort.toString(ushortArray[2]));
        msAssert.areEqual("99", msUShort.toString(ushortArray[3]));
        msAssert.areEqual("65534", msUShort.toString(ushortArray[4]));
        msAssert.areEqual("65525", msUShort.toString(ushortArray[5]));
        msAssert.areEqual("65435", msUShort.toString(ushortArray[6]));
        msAssert.areEqual("0", msUShort.toString(ushortArray[7]));
        msAssert.areEqual("1", msUShort.toString(ushortArray[8]));
        msAssert.areEqual("10", msUShort.toString(ushortArray[9]));
        msAssert.areEqual("100", msUShort.toString(ushortArray[10]));
        msAssert.areEqual("65535", msUShort.toString(ushortArray[11]));
        msAssert.areEqual("65526", msUShort.toString(ushortArray[12]));
        msAssert.areEqual("65436", msUShort.toString(ushortArray[13]));
    }

    @Test
    public void testMax()
    {
        msAssert.areEqual("65535", msUShort.toString(maxValue));
        msAssert.areEqual("65535", msUShort.toString(maxValue, "D"));
        msAssert.areEqual("65535", msUShort.toString(maxValue, "d"));

        msAssert.areEqual("65535", msUShort.toString(maxValue, "D1"));
        msAssert.areEqual("65535", msUShort.toString(maxValue, "d2"));
        msAssert.areEqual("65535", msUShort.toString(maxValue, "D5"));

        msAssert.areEqual("FFFF", msUShort.toString(maxValue, "X"));
        msAssert.areEqual("ffff", msUShort.toString(maxValue, "x"));
    }

    @Test
    public void testMin()
    {
        msAssert.areEqual("0", msUShort.toString(minValue));
        msAssert.areEqual("0", msUShort.toString(minValue, "D"));
        msAssert.areEqual("0", msUShort.toString(minValue, "d"));
        msAssert.areEqual("0", msUShort.toString(minValue, "G"));
        msAssert.areEqual("0", msUShort.toString(minValue, "g"));

        msAssert.areEqual("0", msUShort.toString(minValue, "D1"));
        msAssert.areEqual("00", msUShort.toString(minValue, "d2"));
        msAssert.areEqual("00000", msUShort.toString(minValue, "D5"));

        msAssert.areEqual("0", msUShort.toString(minValue, "X"));
        msAssert.areEqual("0", msUShort.toString(minValue, "x"));
    }

    private static /*ushort*/short maxValue = msUShort.MAX_VALUE;
    private static /*ushort*/short minValue = msUShort.MIN_VALUE;

    private static /*ushort*/short[] ushortArray =
    {
        maxValue,
        (/*ushort*/short)((maxValue & 0xFFFF) + 1),
        (/*ushort*/short)((maxValue & 0xFFFF) + 10),
        (/*ushort*/short)((maxValue & 0xFFFF) + 100),
        (/*ushort*/short)((maxValue & 0xFFFF) - 1),
        (/*ushort*/short)((maxValue & 0xFFFF) - 10),
        (/*ushort*/short)((maxValue & 0xFFFF) - 100),
        minValue,
        (/*ushort*/short)((minValue & 0xFFFF) + 1),
        (/*ushort*/short)((minValue & 0xFFFF) + 10),
        (/*ushort*/short)((minValue & 0xFFFF) + 100),
        (/*ushort*/short)((minValue & 0xFFFF) - 1),
        (/*ushort*/short)((minValue & 0xFFFF) - 10),
        (/*ushort*/short)((minValue & 0xFFFF) - 100)
    };
}
