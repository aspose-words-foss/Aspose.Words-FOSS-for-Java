// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/03/2017 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msByte;
import com.aspose.ms.System.msUShort;
import com.aspose.ms.System.msUInt;



@Test
public class TestBinaryOperatorByte
{
    @Test
    public void testSByteBinaryOperator()
    {
        //arithmetic addition
        byte byteValue = (byte)(sbytes[4] / 2);
        msAssert.areEqual(65, (byteValue & 0xFF) + sbytes[1]);
        msAssert.areEqual(60, (byteValue & 0xFF) - sbytes[2]);
        //arithmetic scaling
        msAssert.areEqual(126, (byteValue & 0xFF) * sbytes[1]);
        msAssert.areEqual(13, (byteValue & 0xFF) % sbytes[3]);
        msAssert.areEqual(31, (byteValue & 0xFF) / sbytes[1]);
        //bitwise
        msAssert.areEqual(2, (byteValue & 0xFF) & sbytes[1]);
        msAssert.areEqual(63, (byteValue & 0xFF) | sbytes[1]);
        msAssert.areEqual(13, (byteValue & 0xFF) ^ sbytes[3]);
        //bitwise shift
        msAssert.areEqual(15, (byteValue & 0xFF) >>> sbytes[1]);
        msAssert.areEqual(252, (byteValue & 0xFF) << sbytes[1]);
    }

    @Test
    public void testByteBinaryOperator()
    {
        //arithmetic addition
        byte byteValue = (byte)((bytes[4] & 0xFF) / 2);
        msAssert.areEqual(129, (byteValue & 0xFF) + (bytes[1] & 0xFF));
        msAssert.areEqual(124, (byteValue & 0xFF) - (bytes[2] & 0xFF));
        //arithmetic scaling
        msAssert.areEqual(254, (byteValue & 0xFF) * (bytes[1] & 0xFF));
        msAssert.areEqual(27, (byteValue & 0xFF) % (bytes[3] & 0xFF));
        msAssert.areEqual(63, (byteValue & 0xFF) / (bytes[1] & 0xFF));
        //bitwise
        msAssert.areEqual(2, (byteValue & 0xFF) & (bytes[1] & 0xFF));
        msAssert.areEqual(127, (byteValue & 0xFF) | (bytes[1] & 0xFF));
        msAssert.areEqual(77, (byteValue & 0xFF) ^ (bytes[3] & 0xFF));
        //bitwise shift
        msAssert.areEqual(31, (byteValue & 0xFF) >>> (bytes[1] & 0xFF));
        msAssert.areEqual(508, (byteValue & 0xFF) << (bytes[1] & 0xFF));
    }

    @Test
    public void testShortBinaryOperator()
    {
        //arithmetic addition
        byte byteValue = (byte)(shorts[4] / 2);
        msAssert.areEqual(257, (byteValue & 0xFF) + shorts[1]);
        msAssert.areEqual(252, (byteValue & 0xFF) - shorts[2]);
        //arithmetic scaling
        msAssert.areEqual(510, (byteValue & 0xFF) * shorts[1]);
        msAssert.areEqual(5, (byteValue & 0xFF) % shorts[3]);
        msAssert.areEqual(127, (byteValue & 0xFF) / shorts[1]);
        //bitwise
        msAssert.areEqual(2, (byteValue & 0xFF) & shorts[1]);
        msAssert.areEqual(255, (byteValue & 0xFF) | shorts[1]);
        msAssert.areEqual(205, (byteValue & 0xFF) ^ shorts[3]);
        //bitwise shift
        msAssert.areEqual(63, (byteValue & 0xFF) >>> shorts[1]);
        msAssert.areEqual(1020, (byteValue & 0xFF) << shorts[1]);
    }

    @Test
    public void testUShortBinaryOperator()
    {
        //arithmetic addition
        byte byteValue = (byte)((ushorts[4] & 0xFFFF) / 2);
        msAssert.areEqual(257, (byteValue & 0xFF) + (ushorts[1] & 0xFFFF));
        msAssert.areEqual(252, (byteValue & 0xFF) - (ushorts[2] & 0xFFFF));
        //arithmetic scaling
        msAssert.areEqual(510, (byteValue & 0xFF) * (ushorts[1] & 0xFFFF));
        msAssert.areEqual(5, (byteValue & 0xFF) % (ushorts[3] & 0xFFFF));
        msAssert.areEqual(127, (byteValue & 0xFF) / (ushorts[1] & 0xFFFF));
        //bitwise
        msAssert.areEqual(2, (byteValue & 0xFF) & (ushorts[1] & 0xFFFF));
        msAssert.areEqual(255, (byteValue & 0xFF) | (ushorts[1] & 0xFFFF));
        msAssert.areEqual(205, (byteValue & 0xFF) ^ (ushorts[3] & 0xFFFF));
        //bitwise shift
        msAssert.areEqual(63, (byteValue & 0xFF) >>> (ushorts[1] & 0xFFFF));
        msAssert.areEqual(1020, (byteValue & 0xFF) << (ushorts[1] & 0xFFFF));
    }

    @Test
    public void testIntBinaryOperator()
    {
        //arithmetic addition
        byte byteValue = (byte)(ints[4] / 2);
        msAssert.areEqual(257, (byteValue & 0xFF) + ints[1]);
        msAssert.areEqual(252, (byteValue & 0xFF) - ints[2]);
        //arithmetic scaling
        msAssert.areEqual(510, (byteValue & 0xFF) * ints[1]);
        msAssert.areEqual(5, (byteValue & 0xFF) % ints[3]);
        msAssert.areEqual(127, (byteValue & 0xFF) / ints[1]);
        //bitwise
        msAssert.areEqual(2, (byteValue & 0xFF) & ints[1]);
        msAssert.areEqual(255, (byteValue & 0xFF) | ints[1]);
        msAssert.areEqual(205, (byteValue & 0xFF) ^ ints[3]);
        //bitwise shift
        msAssert.areEqual(63, (byteValue & 0xFF) >>> ints[1]);
        msAssert.areEqual(1020, (byteValue & 0xFF) << ints[1]);
    }

    @Test
    public void testUIntBinaryOperator()
    {
        //arithmetic addition
        byte byteValue = (byte)((uints[4] & 0xFFFFFFFFL) / 2);
        msAssert.areEqual(257, (byteValue & 0xFF) + (uints[1] & 0xFFFFFFFFL));
        msAssert.areEqual(252L, (byteValue & 0xFF) - (uints[2] & 0xFFFFFFFFL));
        //arithmetic scaling
        msAssert.areEqual(510, (byteValue & 0xFF) * (uints[1] & 0xFFFFFFFFL));
        msAssert.areEqual(5L, (byteValue & 0xFF) % (uints[3] & 0xFFFFFFFFL));
        msAssert.areEqual(127L, (byteValue & 0xFF) / (uints[1] & 0xFFFFFFFFL));
        //bitwise
        msAssert.areEqual(2, (byteValue & 0xFF) & (uints[1] & 0xFFFFFFFFL));
        msAssert.areEqual(255L, (byteValue & 0xFF) | (uints[1] & 0xFFFFFFFFL));
        msAssert.areEqual(205L, (byteValue & 0xFF) ^ (uints[3] & 0xFFFFFFFFL));
    }

    @Test
    public void testLongBinaryOperator()
    {
        //arithmetic addition
        byte byteValue = (byte)(longs[4] / 2);
        msAssert.areEqual(257, (byteValue & 0xFF) + longs[1]);
        msAssert.areEqual(252, (byteValue & 0xFF) - longs[2]);
        //arithmetic scaling
        msAssert.areEqual(510, (byteValue & 0xFF) * longs[1]);
        msAssert.areEqual(5, (byteValue & 0xFF) % longs[3]);
        msAssert.areEqual(127, (byteValue & 0xFF) / longs[1]);
        //bitwise
        msAssert.areEqual(2, (byteValue & 0xFF) & longs[1]);
        msAssert.areEqual(255, (byteValue & 0xFF) | longs[1]);
        msAssert.areEqual(205, (byteValue & 0xFF) ^ longs[3]);
    }

    @Test
    public void testFloatBinaryOperator()
    {
        //arithmetic addition
        byte byteValue = (byte)(Byte.MAX_VALUE);
        msAssert.areEqual(129.0f, (byteValue & 0xFF) + floats[1]);
        msAssert.areEqual(124.0f, (byteValue & 0xFF) - floats[2]);
        //arithmetic scaling
        msAssert.areEqual(254.0f, (byteValue & 0xFF) * floats[1]);
        msAssert.areEqual(27.0f, (byteValue & 0xFF) % floats[3]);
        msAssert.areEqual(63.5f, (byteValue & 0xFF) / floats[1]);
    }

    @Test
    public void testDoubleBinaryOperator()
    {
        //arithmetic addition
        byte byteValue = (byte)(Byte.MAX_VALUE);
        msAssert.areEqual(129.0d, (byteValue & 0xFF) + doubles[1]);
        msAssert.areEqual(124.0d, (byteValue & 0xFF) - doubles[2]);
        //arithmetic scaling
        msAssert.areEqual(254.0d, (byteValue & 0xFF) * doubles[1]);
        msAssert.areEqual(27.0d, (byteValue & 0xFF) % doubles[3]);
        msAssert.areEqual(63.5d, (byteValue & 0xFF) / doubles[1]);
    }

    private /*final*/ byte[] sbytes = { 1, 2, 3, 50, Byte.MAX_VALUE };
    private /*final*/ byte[] bytes = { (byte) 1, (byte) 2, (byte) 3, (byte) 50, msByte.MAX_VALUE };
    private /*final*/ short[] shorts = { 1, 2, 3, 50, Short.MAX_VALUE };
    private /*final*/ /*ushort*/short[] ushorts = { (short) 1, (short) 2, (short) 3, (short) 50, msUShort.MAX_VALUE };
    private /*final*/ int[] ints = { 1, 2, 3, 50, Integer.MAX_VALUE };
    private /*final*/ /*uint*/int[] uints = { 1, 2, 3, 50, msUInt.MAX_VALUE };
    private /*final*/ long[] longs = { 1L, 2L, 3L, 50, Long.MAX_VALUE };
    private /*final*/ float[] floats = { 1.0F, 2.0F, 3.0F, 50f };
    private /*final*/ double[] doubles = { 1.0, 2.0, 3.0, 50.0 };
}


