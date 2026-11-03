// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/05/2017 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msNumber;


@Test
public class TestFormatShort
{
    @Test
    public void testBasic()
    {
        short shortValue = 0x1FFF;
        msAssert.areEqual("8191", Short.toString(shortValue));
        msAssert.areEqual("8191", msNumber.format(shortValue, "D2"));
        msAssert.areEqual("8191", msNumber.format(shortValue, "d2"));
        msAssert.areEqual("8191", msNumber.format(shortValue, "D1"));
        msAssert.areEqual("8191", msNumber.format(shortValue, "d1"));
        msAssert.areEqual("1FFF", msNumber.format(shortValue, "X"));
        msAssert.areEqual("1fff", msNumber.format(shortValue, "x"));
        msAssert.areEqual("1FFF", msNumber.format(shortValue, "X1"));
        msAssert.areEqual("1fff", msNumber.format(shortValue, "x1"));
        msAssert.areEqual("8191", msNumber.format(shortValue, "G"));
        msAssert.areEqual("8191", msNumber.format(shortValue, "g"));
    }

    @Test
    public void testMinusSign()
    {
        short shortValue = -3156;
        msAssert.areEqual("-3156", msNumber.format(shortValue, "D2"));
        msAssert.areEqual("-3156", msNumber.format(shortValue, "d2"));
        msAssert.areEqual("-3156", msNumber.format(shortValue, "D1"));
        msAssert.areEqual("-3156", msNumber.format(shortValue, "d1"));
        msAssert.areEqual("-3156", msNumber.format(shortValue, "D"));
        msAssert.areEqual("-3156", msNumber.format(shortValue, "d"));
        msAssert.areEqual("-3156", msNumber.format(shortValue, "G"));
        msAssert.areEqual("-3156", msNumber.format(shortValue, "g"));
    }

    @Test
    public void testZeroPad()
    {
        short shortValue = 3156;
        short shortValue1 = -3156;
        msAssert.areEqual("03156", msNumber.format(shortValue, "D5"));
        msAssert.areEqual("03156", msNumber.format(shortValue, "d5"));
        msAssert.areEqual("-03156", msNumber.format(shortValue1, "d5"));

        msAssert.areEqual("0000003156", msNumber.format(shortValue, "D10"));
        msAssert.areEqual("0000003156", msNumber.format(shortValue, "d10"));
        msAssert.areEqual("-0000003156", msNumber.format(shortValue1, "d10"));

        msAssert.areEqual("00C54", msNumber.format(shortValue, "X5"));
        msAssert.areEqual("00c54", msNumber.format(shortValue, "x5"));
        msAssert.areEqual("0f3ac", msNumber.format(shortValue1, "x5"));
    }

    @Test
    public void testShortArray()
    {
        msAssert.areEqual("-32669", Short.toString(shortArray[3]));
        msAssert.areEqual("0032766", msNumber.format(shortArray[4], "d7"));

        msAssert.areEqual("8009", msNumber.format(shortArray[2], "X2"));
        msAssert.areEqual("7ffe", msNumber.format(shortArray[4], "x"));
    }

    @Test
    public void testBorder()
    {
        msAssert.areEqual("32767", Short.toString(shortArray[0]));
        msAssert.areEqual("-32768", Short.toString(shortArray[1]));
        msAssert.areEqual("-32759", Short.toString(shortArray[2]));
        msAssert.areEqual("-32669", Short.toString(shortArray[3]));
        msAssert.areEqual("32766", Short.toString(shortArray[4]));
        msAssert.areEqual("32757", Short.toString(shortArray[5]));
        msAssert.areEqual("32667", Short.toString(shortArray[6]));
        msAssert.areEqual("-32768", Short.toString(shortArray[7]));
        msAssert.areEqual("-32767", Short.toString(shortArray[8]));
        msAssert.areEqual("-32758", Short.toString(shortArray[9]));
        msAssert.areEqual("-32668", Short.toString(shortArray[10]));
        msAssert.areEqual("32767", Short.toString(shortArray[11]));
        msAssert.areEqual("32758", Short.toString(shortArray[12]));
        msAssert.areEqual("32668", Short.toString(shortArray[13]));
    }

    @Test
    public void testMax()
    {
        msAssert.areEqual("32767", Short.toString(maxValue));
        msAssert.areEqual("32767", msNumber.format(maxValue, "D"));
        msAssert.areEqual("32767", msNumber.format(maxValue, "d"));
        msAssert.areEqual("32767", msNumber.format(maxValue, "G"));
        msAssert.areEqual("32767", msNumber.format(maxValue, "g"));

        msAssert.areEqual("32767", msNumber.format(maxValue, "D1"));
        msAssert.areEqual("32767", msNumber.format(maxValue, "d2"));
        msAssert.areEqual("0032767", msNumber.format(maxValue, "D7"));

        msAssert.areEqual("7FFF", msNumber.format(maxValue, "X"));
        msAssert.areEqual("7fff", msNumber.format(maxValue, "x"));
    }

    @Test
    public void testMin()
    {
        msAssert.areEqual("-32768", Short.toString(minValue));
        msAssert.areEqual("-32768", msNumber.format(minValue, "D"));
        msAssert.areEqual("-32768", msNumber.format(minValue, "d"));
        msAssert.areEqual("-32768", msNumber.format(minValue, "G"));
        msAssert.areEqual("-32768", msNumber.format(minValue, "g"));

        msAssert.areEqual("-32768", msNumber.format(minValue, "D1"));
        msAssert.areEqual("-32768", msNumber.format(minValue, "d2"));
        msAssert.areEqual("-0032768", msNumber.format(minValue, "D7"));

        msAssert.areEqual("8000", msNumber.format(minValue, "X"));
        msAssert.areEqual("8000", msNumber.format(minValue, "x"));
    }

    private static short maxValue = Short.MAX_VALUE;
    private static short minValue = Short.MIN_VALUE;

    private static short[] shortArray =
    {
        maxValue,
        (short)(maxValue + 1),
        (short)(maxValue + 10),
        (short)(maxValue + 100),
        (short)(maxValue - 1),
        (short)(maxValue - 10),
        (short)(maxValue - 100),
        minValue,
        (short)(minValue + 1),
        (short)(minValue + 10),
        (short)(minValue + 100),
        (short)(minValue - 1),
        (short)(minValue - 10),
        (short)(minValue - 100)
    };
}

