// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/03/2017 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msByte;
import com.aspose.ms.System.msUShort;
import com.aspose.ms.System.msUInt;


@Test
public class TestBinaryExpressionUShort
{
    @Test
    public void testSByteBinaryExpression()
    {
        //equality expression
        for (int i = 0; i < 4; i++)
        {
            msAssert.isTrue((ushorts[i] & 0xFFFF) == sbytes[i]);
            msAssert.isTrue(sbytes[i] == (ushorts[i] & 0xFFFF));
        }
        msAssert.isTrue((ushorts[4] & 0xFFFF) != sbytes[4]);
        msAssert.isTrue(sbytes[4] != (ushorts[4] & 0xFFFF));
        /*ushort*/short ushortValue = (/*ushort*/short)sbytes[4];
        msAssert.isTrue((ushortValue & 0xFFFF) == sbytes[4]);
        msAssert.isTrue(sbytes[4] == (ushortValue & 0xFFFF));
        //compare expression
        msAssert.isTrue((ushortValue & 0xFFFF) > sbytes[0]);
        msAssert.isTrue((ushortValue & 0xFFFF) >= sbytes[1]);
        msAssert.isFalse((ushortValue & 0xFFFF) < sbytes[2]);
        msAssert.isTrue((ushortValue & 0xFFFF) <= sbytes[4]);
        msAssert.isFalse(sbytes[0] > (ushortValue & 0xFFFF));
        msAssert.isFalse(sbytes[1] >= (ushortValue & 0xFFFF));
        msAssert.isTrue(sbytes[2] < (ushortValue & 0xFFFF));
        msAssert.isTrue(sbytes[4] <= (ushortValue & 0xFFFF));
    }

    @Test
    public void testByteBinaryExpression()
    {
        //equality expression
        for (int i = 0; i < 4; i++)
        {
            msAssert.isTrue((ushorts[i] & 0xFFFF) == (bytes[i] & 0xFF));
            msAssert.isTrue((bytes[i] & 0xFF) == (ushorts[i] & 0xFFFF));
        }
        msAssert.isTrue((ushorts[4] & 0xFFFF) != (bytes[4] & 0xFF));
        msAssert.isTrue((bytes[4] & 0xFF) != (ushorts[4] & 0xFFFF));
        /*ushort*/short ushortValue = (/*ushort*/short)(bytes[4] & 0xFF);
        msAssert.isTrue((ushortValue & 0xFFFF) == (bytes[4] & 0xFF));
        msAssert.isTrue((bytes[4] & 0xFF) == (ushortValue & 0xFFFF));
        //compare expression
        msAssert.isTrue((ushortValue & 0xFFFF) > (bytes[0] & 0xFF));
        msAssert.isTrue((ushortValue & 0xFFFF) >= (bytes[1] & 0xFF));
        msAssert.isFalse((ushortValue & 0xFFFF) < (bytes[2] & 0xFF));
        msAssert.isTrue((ushortValue & 0xFFFF) <= (bytes[4] & 0xFF));
        msAssert.isFalse((bytes[0] & 0xFF) > (ushortValue & 0xFFFF));
        msAssert.isFalse((bytes[1] & 0xFF) >= (ushortValue & 0xFFFF));
        msAssert.isTrue((bytes[2] & 0xFF) < (ushortValue & 0xFFFF));
        msAssert.isTrue((bytes[4] & 0xFF) <= (ushortValue & 0xFFFF));
    }

    @Test
    public void testShortBinaryExpression()
    {
        //equality expression
        for (int i = 0; i < 4; i++)
        {
            msAssert.isTrue((ushorts[i] & 0xFFFF) == shorts[i]);
            msAssert.isTrue(shorts[i] == (ushorts[i] & 0xFFFF));
        }
        msAssert.isTrue((ushorts[4] & 0xFFFF) != shorts[4]);
        msAssert.isTrue(shorts[4] != (ushorts[4] & 0xFFFF));
        /*ushort*/short ushortValue = (/*ushort*/short)shorts[4];
        msAssert.isTrue((ushortValue & 0xFFFF) == shorts[4]);
        msAssert.isTrue(shorts[4] == (ushortValue & 0xFFFF));
        //compare expression
        msAssert.isTrue((ushortValue & 0xFFFF) > shorts[0]);
        msAssert.isTrue((ushortValue & 0xFFFF) >= shorts[1]);
        msAssert.isFalse((ushortValue & 0xFFFF) < shorts[2]);
        msAssert.isTrue((ushortValue & 0xFFFF) <= shorts[4]);
        msAssert.isFalse(shorts[0] > (ushortValue & 0xFFFF));
        msAssert.isFalse(shorts[1] >= (ushortValue & 0xFFFF));
        msAssert.isTrue(shorts[2] < (ushortValue & 0xFFFF));
        msAssert.isTrue(shorts[4] <= (ushortValue & 0xFFFF));
    }

    @Test
    public void testUShortBinaryExpression()
    {
        //equality expression
        /*ushort*/short ushortValue = (/*ushort*/short)shorts[4];
        msAssert.isTrue(ushortValue != ushorts[4]);
        msAssert.isTrue(ushorts[4] != ushortValue);
        msAssert.isTrue(ushortValue == 32767);
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) + 2);
        msAssert.isTrue((ushortValue & 0xFFFF) == 32769);
        for (int i = 0; i < 5; i++)
        {
            ushortValue = ushorts[i];
            msAssert.isTrue(ushortValue == ushorts[i]);
        }
        //compare expression
        msAssert.isTrue((ushortValue & 0xFFFF) > (ushorts[0] & 0xFFFF));
        msAssert.isTrue((ushortValue & 0xFFFF) >= (ushorts[1] & 0xFFFF));
        msAssert.isFalse((ushortValue & 0xFFFF) < (ushorts[2] & 0xFFFF));
        msAssert.isTrue((ushortValue & 0xFFFF) <= (ushorts[4] & 0xFFFF));
        msAssert.isFalse((ushorts[0] & 0xFFFF) > (ushortValue & 0xFFFF));
        msAssert.isFalse((ushorts[1] & 0xFFFF) >= (ushortValue & 0xFFFF));
        msAssert.isTrue((ushorts[2] & 0xFFFF) < (ushortValue & 0xFFFF));
        msAssert.isTrue((ushorts[4] & 0xFFFF) <= (ushortValue & 0xFFFF));
    }

    @Test
    public void testIntBinaryExpression()
    {
        //equality expression
        for (int i = 0; i < 4; i++)
        {
            msAssert.isTrue((ushorts[i] & 0xFFFF) == ints[i]);
            msAssert.isTrue(ints[i] == (ushorts[i] & 0xFFFF));
        }
        msAssert.isTrue((ushorts[4] & 0xFFFF) != ints[4]);
        msAssert.isTrue(ints[4] != (ushorts[4] & 0xFFFF));
        int intValue = (ushorts[4] & 0xFFFF);
        msAssert.isTrue(intValue == (ushorts[4] & 0xFFFF));
        msAssert.isTrue((ushorts[4] & 0xFFFF) == intValue);
        //compare expression
        msAssert.isTrue(intValue > (ushorts[0] & 0xFFFF));
        msAssert.isTrue(intValue >= (ushorts[1] & 0xFFFF));
        msAssert.isFalse(intValue < (ushorts[2] & 0xFFFF));
        msAssert.isTrue(intValue <= (ushorts[4] & 0xFFFF));
        msAssert.isFalse((ushorts[0] & 0xFFFF) > intValue);
        msAssert.isFalse((ushorts[1] & 0xFFFF) >= intValue);
        msAssert.isTrue((ushorts[2] & 0xFFFF) < intValue);
        msAssert.isTrue((ushorts[4] & 0xFFFF) <= intValue);
    }

    @Test
    public void testUIntBinaryExpression()
    {
        //equality expression
        for (int i = 0; i < 4; i++)
        {
            msAssert.isTrue((ushorts[i] & 0xFFFF) == (uints[i] & 0xFFFFFFFFL));
            msAssert.isTrue((uints[i] & 0xFFFFFFFFL) == (ushorts[i] & 0xFFFF));
        }
        msAssert.isTrue((ushorts[4] & 0xFFFF) != (uints[4] & 0xFFFFFFFFL));
        msAssert.isTrue((uints[4] & 0xFFFFFFFFL) != (ushorts[4] & 0xFFFF));
        /*uint*/int uintValue = (ushorts[4] & 0xFFFF);
        msAssert.isTrue((uintValue & 0xFFFFFFFFL) == (ushorts[4] & 0xFFFF));
        msAssert.isTrue((ushorts[4] & 0xFFFF) == (uintValue & 0xFFFFFFFFL));
        //compare expression
        msAssert.isTrue((uintValue & 0xFFFFFFFFL) > (ushorts[0] & 0xFFFF));
        msAssert.isTrue((uintValue & 0xFFFFFFFFL) >= (ushorts[1] & 0xFFFF));
        msAssert.isFalse((uintValue & 0xFFFFFFFFL) < (ushorts[2] & 0xFFFF));
        msAssert.isTrue((uintValue & 0xFFFFFFFFL) <= (ushorts[4] & 0xFFFF));
        msAssert.isFalse((ushorts[0] & 0xFFFF) > (uintValue & 0xFFFFFFFFL));
        msAssert.isFalse((ushorts[1] & 0xFFFF) >= (uintValue & 0xFFFFFFFFL));
        msAssert.isTrue((ushorts[2] & 0xFFFF) < (uintValue & 0xFFFFFFFFL));
        msAssert.isTrue((ushorts[4] & 0xFFFF) <= (uintValue & 0xFFFFFFFFL));
    }

    @Test
    public void testLongBinaryExpression()
    {
        //equality expression
        for (int i = 0; i < 4; i++)
        {
            msAssert.isTrue((ushorts[i] & 0xFFFF) == longs[i]);
            msAssert.isTrue(longs[i] == (ushorts[i] & 0xFFFF));
        }
        msAssert.isTrue((ushorts[4] & 0xFFFF) != longs[4]);
        msAssert.isTrue(longs[4] != (ushorts[4] & 0xFFFF));
        long longValue = (ushorts[4] & 0xFFFF);
        msAssert.isTrue(longValue == (ushorts[4] & 0xFFFF));
        msAssert.isTrue((ushorts[4] & 0xFFFF) == longValue);
        //compare expression
        msAssert.isTrue(longValue > (ushorts[0] & 0xFFFF));
        msAssert.isTrue(longValue >= (ushorts[1] & 0xFFFF));
        msAssert.isFalse(longValue < (ushorts[2] & 0xFFFF));
        msAssert.isTrue(longValue <= (ushorts[4] & 0xFFFF));
        msAssert.isFalse((ushorts[0] & 0xFFFF) > longValue);
        msAssert.isFalse((ushorts[1] & 0xFFFF) >= longValue);
        msAssert.isTrue((ushorts[2] & 0xFFFF) < longValue);
        msAssert.isTrue((ushorts[4] & 0xFFFF) <= longValue);
    }

    @Test
    public void testFloatBinaryExpression()
    {
        //equality expression
        for (int i = 0; i < 4; i++)
        {
            msAssert.isTrue((ushorts[i] & 0xFFFF) == floats[i]);
            msAssert.isTrue(floats[i] == (ushorts[i] & 0xFFFF));
        }
        //compare expression
        float floatValue = (ushorts[4] & 0xFFFF);
        msAssert.isTrue(floatValue > (ushorts[0] & 0xFFFF));
        msAssert.isTrue(floatValue >= (ushorts[1] & 0xFFFF));
        msAssert.isFalse(floatValue < (ushorts[2] & 0xFFFF));
        msAssert.isTrue(floatValue <= (ushorts[4] & 0xFFFF));
        msAssert.isFalse((ushorts[0] & 0xFFFF) > floatValue);
        msAssert.isFalse((ushorts[1] & 0xFFFF) >= floatValue);
        msAssert.isTrue((ushorts[2] & 0xFFFF) < floatValue);
        msAssert.isTrue((ushorts[4] & 0xFFFF) <= floatValue);
    }

    @Test
    public void testDoubleBinaryExpression()
    {
        //equality expression
        for (int i = 0; i < 4; i++)
        {
            msAssert.isTrue((ushorts[i] & 0xFFFF) == doubles[i]);
            msAssert.isTrue(doubles[i] == (ushorts[i] & 0xFFFF));
        }
        //compare expression
        double doubleValue = (ushorts[4] & 0xFFFF);
        msAssert.isTrue(doubleValue > (ushorts[0] & 0xFFFF));
        msAssert.isTrue(doubleValue >= (ushorts[1] & 0xFFFF));
        msAssert.isFalse(doubleValue < (ushorts[2] & 0xFFFF));
        msAssert.isTrue(doubleValue <= (ushorts[4] & 0xFFFF));
        msAssert.isFalse((ushorts[0] & 0xFFFF) > doubleValue);
        msAssert.isFalse((ushorts[1] & 0xFFFF) >= doubleValue);
        msAssert.isTrue((ushorts[2] & 0xFFFF) < doubleValue);
        msAssert.isTrue((ushorts[4] & 0xFFFF) <= doubleValue);
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

