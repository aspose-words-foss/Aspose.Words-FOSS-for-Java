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
public class TestBinaryOperatorUShort
{
    @Test
    public void testSByteBinaryOperator()
    {
        //arithmetic addition
        /*ushort*/short ushortValue = (/*ushort*/short)(sbytes[4] / 2);
        msAssert.areEqual(65, (ushortValue & 0xFFFF) + sbytes[1]);
        msAssert.areEqual(60, (ushortValue & 0xFFFF) - sbytes[2]);
        //arithmetic scaling
        msAssert.areEqual(126, (ushortValue & 0xFFFF) * sbytes[1]);
        msAssert.areEqual(13, (ushortValue & 0xFFFF) % sbytes[3]);
        msAssert.areEqual(31, (ushortValue & 0xFFFF) / sbytes[1]);
        //bitwise
        msAssert.areEqual(2, (ushortValue & 0xFFFF) & sbytes[1]);
        msAssert.areEqual(63, (ushortValue & 0xFFFF) | sbytes[1]);
        msAssert.areEqual(13, (ushortValue & 0xFFFF) ^ sbytes[3]);
        //bitwise shift
        msAssert.areEqual(15, (ushortValue & 0xFFFF) >>> sbytes[1]);
        msAssert.areEqual(252, (ushortValue & 0xFFFF) << sbytes[1]);
    }

    @Test
    public void testByteBinaryOperator()
    {
        //arithmetic addition
        /*ushort*/short ushortValue = (/*ushort*/short)((bytes[4] & 0xFF) / 2);
        msAssert.areEqual(129, (ushortValue & 0xFFFF) + (bytes[1] & 0xFF));
        msAssert.areEqual(124, (ushortValue & 0xFFFF) - (bytes[2] & 0xFF));
        //arithmetic scaling
        msAssert.areEqual(254, (ushortValue & 0xFFFF) * (bytes[1] & 0xFF));
        msAssert.areEqual(27, (ushortValue & 0xFFFF) % (bytes[3] & 0xFF));
        msAssert.areEqual(63, (ushortValue & 0xFFFF) / (bytes[1] & 0xFF));
        //bitwise
        msAssert.areEqual(2, (ushortValue & 0xFFFF) & (bytes[1] & 0xFF));
        msAssert.areEqual(127, (ushortValue & 0xFFFF) | (bytes[1] & 0xFF));
        msAssert.areEqual(77, (ushortValue & 0xFFFF) ^ (bytes[3] & 0xFF));
        //bitwise shift
        msAssert.areEqual(31, (ushortValue & 0xFFFF) >>> (bytes[1] & 0xFF));
        msAssert.areEqual(508, (ushortValue & 0xFFFF) << (bytes[1] & 0xFF));
    }

    @Test
    public void testShortBinaryOperator()
    {
        //arithmetic addition
        /*ushort*/short ushortValue = (/*ushort*/short)(shorts[4] / 2);
        msAssert.areEqual(16385, (ushortValue & 0xFFFF) + shorts[1]);
        msAssert.areEqual(16380, (ushortValue & 0xFFFF) - shorts[2]);
        //arithmetic scaling
        msAssert.areEqual(32766, (ushortValue & 0xFFFF) * shorts[1]);
        msAssert.areEqual(33, (ushortValue & 0xFFFF) % shorts[3]);
        msAssert.areEqual(8191, (ushortValue & 0xFFFF) / shorts[1]);
        //bitwise
        msAssert.areEqual(2, (ushortValue & 0xFFFF) & shorts[1]);
        msAssert.areEqual(16383, (ushortValue & 0xFFFF) | shorts[1]);
        msAssert.areEqual(16333, (ushortValue & 0xFFFF) ^ shorts[3]);
        //bitwise shift
        msAssert.areEqual(4095, (ushortValue & 0xFFFF) >>> shorts[1]);
        msAssert.areEqual(65532, (ushortValue & 0xFFFF) << shorts[1]);
    }

    @Test
    public void testUShortBinaryOperator()
    {
        //arithmetic addition
        /*ushort*/short ushortValue = (/*ushort*/short)((ushorts[4] & 0xFFFF) / 2);
        msAssert.areEqual(32769, (ushortValue & 0xFFFF) + (ushorts[1] & 0xFFFF));
        msAssert.areEqual(32764, (ushortValue & 0xFFFF) - (ushorts[2] & 0xFFFF));
        //arithmetic scaling
        msAssert.areEqual(65534, (ushortValue & 0xFFFF) * (ushorts[1] & 0xFFFF));
        msAssert.areEqual(17, (ushortValue & 0xFFFF) % (ushorts[3] & 0xFFFF));
        msAssert.areEqual(16383, (ushortValue & 0xFFFF) / (ushorts[1] & 0xFFFF));
        //bitwise
        msAssert.areEqual(2, (ushortValue & 0xFFFF) & (ushorts[1] & 0xFFFF));
        msAssert.areEqual(32767, (ushortValue & 0xFFFF) | (ushorts[1] & 0xFFFF));
        msAssert.areEqual(32717, (ushortValue & 0xFFFF) ^ (ushorts[3] & 0xFFFF));
        //bitwise shift
        msAssert.areEqual(8191, (ushortValue & 0xFFFF) >>> (ushorts[1] & 0xFFFF));
        msAssert.areEqual(131068, (ushortValue & 0xFFFF) << (ushorts[1] & 0xFFFF));
    }

    @Test
    public void testIntBinaryOperator()
    {
        //arithmetic addition
        /*ushort*/short ushortValue = (/*ushort*/short)(ints[4] / 2);
        msAssert.areEqual(65537, (ushortValue & 0xFFFF) + ints[1]);
        msAssert.areEqual(65532, (ushortValue & 0xFFFF) - ints[2]);
        //arithmetic scaling
        msAssert.areEqual(131070, (ushortValue & 0xFFFF) * ints[1]);
        msAssert.areEqual(35, (ushortValue & 0xFFFF) % ints[3]);
        msAssert.areEqual(32767, (ushortValue & 0xFFFF) / ints[1]);
        //bitwise
        msAssert.areEqual(2, (ushortValue & 0xFFFF) & ints[1]);
        msAssert.areEqual(65535, (ushortValue & 0xFFFF) | ints[1]);
        msAssert.areEqual(65485, (ushortValue & 0xFFFF) ^ ints[3]);
        //bitwise shift
        msAssert.areEqual(16383, (ushortValue & 0xFFFF) >>> ints[1]);
        msAssert.areEqual(262140, (ushortValue & 0xFFFF) << ints[1]);
    }

    @Test
    public void testUIntBinaryOperator()
    {
        //arithmetic addition
        /*ushort*/short ushortValue = (/*ushort*/short)((uints[4] & 0xFFFFFFFFL) / 2);
        msAssert.areEqual(65537, (ushortValue & 0xFFFF) + (uints[1] & 0xFFFFFFFFL));
        msAssert.areEqual(65532, (ushortValue & 0xFFFF) - (uints[2] & 0xFFFFFFFFL));
        //arithmetic scaling
        msAssert.areEqual(131070, (ushortValue & 0xFFFF) * (uints[1] & 0xFFFFFFFFL));
        msAssert.areEqual(35, (ushortValue & 0xFFFF) % (uints[3] & 0xFFFFFFFFL));
        msAssert.areEqual(32767, (ushortValue & 0xFFFF) / (uints[1] & 0xFFFFFFFFL));
        //bitwise
        msAssert.areEqual(2, (ushortValue & 0xFFFF) & (uints[1] & 0xFFFFFFFFL));
        msAssert.areEqual(65535, (ushortValue & 0xFFFF) | (uints[1] & 0xFFFFFFFFL));
        msAssert.areEqual(65485, (ushortValue & 0xFFFF) ^ (uints[3] & 0xFFFFFFFFL));
    }

    @Test
    public void testLongBinaryOperator()
    {
        //arithmetic addition
        /*ushort*/short ushortValue = (/*ushort*/short)(longs[4] / 2);
        msAssert.areEqual(65537, (ushortValue & 0xFFFF) + longs[1]);
        msAssert.areEqual(65532, (ushortValue & 0xFFFF) - longs[2]);
        //arithmetic scaling
        msAssert.areEqual(131070, (ushortValue & 0xFFFF) * longs[1]);
        msAssert.areEqual(35, (ushortValue & 0xFFFF) % longs[3]);
        msAssert.areEqual(32767, (ushortValue & 0xFFFF) / longs[1]);
        //bitwise
        msAssert.areEqual(2, (ushortValue & 0xFFFF) & longs[1]);
        msAssert.areEqual(65535, (ushortValue & 0xFFFF) | longs[1]);
        msAssert.areEqual(65485, (ushortValue & 0xFFFF) ^ longs[3]);
    }

    @Test
    public void testFloatBinaryOperator()
    {
        //arithmetic addition
        /*ushort*/short ushortValue = (/*ushort*/short)(Short.MAX_VALUE);
        msAssert.areEqual(32769.0f, (ushortValue & 0xFFFF) + floats[1]);
        msAssert.areEqual(32764.0f, (ushortValue & 0xFFFF) - floats[2]);
        //arithmetic scaling
        msAssert.areEqual(65534.0f, (ushortValue & 0xFFFF) * floats[1]);
        msAssert.areEqual(17.0f, (ushortValue & 0xFFFF) % floats[3]);
        msAssert.areEqual(16383.5f, (ushortValue & 0xFFFF) / floats[1]);
    }

    @Test
    public void testDoubleBinaryOperator()
    {
        //arithmetic addition
        /*ushort*/short ushortValue = (/*ushort*/short)(Short.MAX_VALUE);
        msAssert.areEqual(32769.0d, (ushortValue & 0xFFFF) + doubles[1]);
        msAssert.areEqual(32764.0d, (ushortValue & 0xFFFF) - doubles[2]);
        //arithmetic scaling
        msAssert.areEqual(65534.0d, (ushortValue & 0xFFFF) * doubles[1]);
        msAssert.areEqual(17.0d, (ushortValue & 0xFFFF) % doubles[3]);
        msAssert.areEqual(16383.5d, (ushortValue & 0xFFFF) / doubles[1]);
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

