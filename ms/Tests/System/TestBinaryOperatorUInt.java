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
public class TestBinaryOperatorUInt
{
    @Test
    public void testSByteBinaryOperator()
    {
        //arithmetic addition
        /*uint*/int uintValue = (/*uint*/int)(sbytes[4] / 2);
        msAssert.areEqual(65, (uintValue & 0xFFFFFFFFL) + sbytes[1]);
        msAssert.areEqual(60, (uintValue & 0xFFFFFFFFL) - sbytes[2]);
        //arithmetic scaling
        msAssert.areEqual(126, (uintValue & 0xFFFFFFFFL) * sbytes[1]);
        msAssert.areEqual(13, (uintValue & 0xFFFFFFFFL) % sbytes[3]);
        msAssert.areEqual(31, (uintValue & 0xFFFFFFFFL) / sbytes[1]);
        //bitwise
        msAssert.areEqual(2, (uintValue & 0xFFFFFFFFL) & sbytes[1]);
        msAssert.areEqual(63, (uintValue & 0xFFFFFFFFL) | sbytes[1]);
        msAssert.areEqual(13, (uintValue & 0xFFFFFFFFL) ^ sbytes[3]);
        //bitwise shift
        msAssert.areEqual(15, uintValue >>> sbytes[1]);
        msAssert.areEqual(252, ((uintValue << sbytes[1]) & 0xFFFFFFFFL));
    }

    @Test
    public void testByteBinaryOperator()
    {
        //arithmetic addition
        /*uint*/int uintValue = (/*uint*/int)((bytes[4] & 0xFF) / 2);
        msAssert.areEqual(129, (uintValue & 0xFFFFFFFFL) + (bytes[1] & 0xFF));
        msAssert.areEqual(124, (uintValue & 0xFFFFFFFFL) - (bytes[2] & 0xFF));
        //arithmetic scaling
        msAssert.areEqual(254, (uintValue & 0xFFFFFFFFL) * (bytes[1] & 0xFF));
        msAssert.areEqual(27, (uintValue & 0xFFFFFFFFL) % (bytes[3] & 0xFF));
        msAssert.areEqual(63, (uintValue & 0xFFFFFFFFL) / (bytes[1] & 0xFF));
        //bitwise
        msAssert.areEqual(2, (uintValue & 0xFFFFFFFFL) & (bytes[1] & 0xFF));
        msAssert.areEqual(127, (uintValue & 0xFFFFFFFFL) | (bytes[1] & 0xFF));
        msAssert.areEqual(77, (uintValue & 0xFFFFFFFFL) ^ (bytes[3] & 0xFF));
        //bitwise shift
        msAssert.areEqual(31, uintValue >>> (bytes[1] & 0xFF));
        msAssert.areEqual(508, ((uintValue << (bytes[1] & 0xFF)) & 0xFFFFFFFFL));
    }

    @Test
    public void testShortBinaryOperator()
    {
        //arithmetic addition
        /*uint*/int uintValue = (/*uint*/int)(shorts[4] / 2);
        msAssert.areEqual(16385, (uintValue & 0xFFFFFFFFL) + shorts[1]);
        msAssert.areEqual(16380, (uintValue & 0xFFFFFFFFL) - shorts[2]);
        //arithmetic scaling
        msAssert.areEqual(32766, (uintValue & 0xFFFFFFFFL) * shorts[1]);
        msAssert.areEqual(33, (uintValue & 0xFFFFFFFFL) % shorts[3]);
        msAssert.areEqual(8191, (uintValue & 0xFFFFFFFFL) / shorts[1]);
        //bitwise
        msAssert.areEqual(2, (uintValue & 0xFFFFFFFFL) & shorts[1]);
        msAssert.areEqual(16383, (uintValue & 0xFFFFFFFFL) | shorts[1]);
        msAssert.areEqual(16333, (uintValue & 0xFFFFFFFFL) ^ shorts[3]);
        //bitwise shift
        msAssert.areEqual(4095, uintValue >>> shorts[1]);
        msAssert.areEqual(65532, ((uintValue << shorts[1]) & 0xFFFFFFFFL));
    }

    @Test
    public void testUShortBinaryOperator()
    {
        //arithmetic addition
        /*uint*/int uintValue = (/*uint*/int)((ushorts[4] & 0xFFFF) / 2);
        msAssert.areEqual(32769, (uintValue & 0xFFFFFFFFL) + (ushorts[1] & 0xFFFF));
        msAssert.areEqual(32764, (uintValue & 0xFFFFFFFFL) - (ushorts[2] & 0xFFFF));
        //arithmetic scaling
        msAssert.areEqual(65534, (uintValue & 0xFFFFFFFFL) * (ushorts[1] & 0xFFFF));
        msAssert.areEqual(17, (uintValue & 0xFFFFFFFFL) % (ushorts[3] & 0xFFFF));
        msAssert.areEqual(16383, (uintValue & 0xFFFFFFFFL) / (ushorts[1] & 0xFFFF));
        //bitwise
        msAssert.areEqual(2, (uintValue & 0xFFFFFFFFL) & (ushorts[1] & 0xFFFF));
        msAssert.areEqual(32767, (uintValue & 0xFFFFFFFFL) | (ushorts[1] & 0xFFFF));
        msAssert.areEqual(32717, (uintValue & 0xFFFFFFFFL) ^ (ushorts[3] & 0xFFFF));
        //bitwise shift
        msAssert.areEqual(8191, uintValue >>> (ushorts[1] & 0xFFFF));
        msAssert.areEqual(131068, ((uintValue << (ushorts[1] & 0xFFFF)) & 0xFFFFFFFFL));
    }

    @Test
    public void testIntBinaryOperator()
    {
        //arithmetic addition
        /*uint*/int uintValue = (/*uint*/int)(ints[4] / 2);
        msAssert.areEqual(1073741825, (uintValue & 0xFFFFFFFFL) + ints[1]);
        msAssert.areEqual(1073741820, (uintValue & 0xFFFFFFFFL) - ints[2]);
        //arithmetic scaling
        msAssert.areEqual(2147483646, (uintValue & 0xFFFFFFFFL) * ints[1]);
        msAssert.areEqual(23, (uintValue & 0xFFFFFFFFL) % ints[3]);
        msAssert.areEqual(536870911, (uintValue & 0xFFFFFFFFL) / ints[1]);
        //bitwise
        msAssert.areEqual(2, (uintValue & 0xFFFFFFFFL) & ints[1]);
        msAssert.areEqual(1073741823, (uintValue & 0xFFFFFFFFL) | ints[1]);
        msAssert.areEqual(1073741773, (uintValue & 0xFFFFFFFFL) ^ ints[3]);
        //bitwise shift
        msAssert.areEqual(268435455, uintValue >>> ints[1]);
        msAssert.areEqual((int) 4294967292L, ((uintValue << ints[1]) & 0xFFFFFFFFL));
    }

    @Test
    public void testUIntBinaryOperator()
    {
        //arithmetic addition
        /*uint*/int uintValue = (/*uint*/int)((uints[4] & 0xFFFFFFFFL) / 2);
        msAssert.areEqual((int) 2147483649L, (uintValue & 0xFFFFFFFFL) + (uints[1] & 0xFFFFFFFFL));
        msAssert.areEqual(2147483644, (uintValue & 0xFFFFFFFFL) - (uints[2] & 0xFFFFFFFFL));
        //arithmetic scaling
        msAssert.areEqual((int) 4294967294L, (uintValue & 0xFFFFFFFFL) * (uints[1] & 0xFFFFFFFFL));
        msAssert.areEqual(47, (uintValue & 0xFFFFFFFFL) % (uints[3] & 0xFFFFFFFFL));
        msAssert.areEqual(1073741823, (uintValue & 0xFFFFFFFFL) / (uints[1] & 0xFFFFFFFFL));
        //bitwise
        msAssert.areEqual(2, (uintValue & 0xFFFFFFFFL) & (uints[1] & 0xFFFFFFFFL));
        msAssert.areEqual(2147483647, (uintValue & 0xFFFFFFFFL) | (uints[1] & 0xFFFFFFFFL));
        msAssert.areEqual(2147483597, (uintValue & 0xFFFFFFFFL) ^ (uints[3] & 0xFFFFFFFFL));
    }

    @Test
    public void testLongBinaryOperator()
    {
        //arithmetic addition
        /*uint*/int uintValue = (/*uint*/int)(longs[4] / 2);
        msAssert.areEqual(4294967297L, (uintValue & 0xFFFFFFFFL) + longs[1]);
        msAssert.areEqual(4294967292L, (uintValue & 0xFFFFFFFFL) - longs[2]);
        //arithmetic scaling
        msAssert.areEqual(8589934590L, (uintValue & 0xFFFFFFFFL) * longs[1]);
        msAssert.areEqual(45, (uintValue & 0xFFFFFFFFL) % longs[3]);
        msAssert.areEqual(2147483647, (uintValue & 0xFFFFFFFFL) / longs[1]);
        //bitwise
        msAssert.areEqual(2, (uintValue & 0xFFFFFFFFL) & longs[1]);
        msAssert.areEqual(4294967295L, (uintValue & 0xFFFFFFFFL) | longs[1]);
        msAssert.areEqual(4294967245L, (uintValue & 0xFFFFFFFFL) ^ longs[3]);
    }

    @Test
    public void testFloatBinaryOperator()
    {
        //arithmetic addition
        /*uint*/int uintValue = (/*uint*/int)(Short.MAX_VALUE);
        msAssert.areEqual(32769.0f, (uintValue & 0xFFFFFFFFL) + floats[1]);
        msAssert.areEqual(32764.0f, (uintValue & 0xFFFFFFFFL) - floats[2]);
        //arithmetic scaling
        msAssert.areEqual(65534.0f, (uintValue & 0xFFFFFFFFL) * floats[1]);
        msAssert.areEqual(17.0f, (uintValue & 0xFFFFFFFFL) % floats[3]);
        msAssert.areEqual(16383.5f, (uintValue & 0xFFFFFFFFL) / floats[1]);
    }

    @Test
    public void testDoubleBinaryOperator()
    {
        //arithmetic addition
        /*uint*/int uintValue = (/*uint*/int)(Short.MAX_VALUE);
        msAssert.areEqual(32769.0d, (uintValue & 0xFFFFFFFFL) + doubles[1]);
        msAssert.areEqual(32764.0d, (uintValue & 0xFFFFFFFFL) - doubles[2]);
        //arithmetic scaling
        msAssert.areEqual(65534.0d, (uintValue & 0xFFFFFFFFL) * doubles[1]);
        msAssert.areEqual(17.0d, (uintValue & 0xFFFFFFFFL) % doubles[3]);
        msAssert.areEqual(16383.5d, (uintValue & 0xFFFFFFFFL) / doubles[1]);
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


