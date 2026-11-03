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
public class TestBinaryExpressionUInt
{
    @Test
    public void testSByteBinaryExpression()
    {
        //equality expression
        for (int i = 0; i < 4; i++)
        {
            msAssert.isTrue((uints[i] & 0xFFFFFFFFL) == sbytes[i]);
            msAssert.isTrue(sbytes[i] == (uints[i] & 0xFFFFFFFFL));
        }
        msAssert.isTrue((uints[4] & 0xFFFFFFFFL) != sbytes[4]);
        msAssert.isTrue(sbytes[4] != (uints[4] & 0xFFFFFFFFL));
        /*uint*/int uintValue = (/*uint*/int)sbytes[4];
        msAssert.isTrue((uintValue & 0xFFFFFFFFL) == sbytes[4]);
        msAssert.isTrue(sbytes[4] == (uintValue & 0xFFFFFFFFL));
        //compare expression
        msAssert.isTrue((uintValue & 0xFFFFFFFFL) > sbytes[0]);
        msAssert.isTrue((uintValue & 0xFFFFFFFFL) >= sbytes[1]);
        msAssert.isFalse((uintValue & 0xFFFFFFFFL) < sbytes[2]);
        msAssert.isTrue((uintValue & 0xFFFFFFFFL) <= sbytes[4]);
        msAssert.isFalse(sbytes[0] > (uintValue & 0xFFFFFFFFL));
        msAssert.isFalse(sbytes[1] >= (uintValue & 0xFFFFFFFFL));
        msAssert.isTrue(sbytes[2] < (uintValue & 0xFFFFFFFFL));
        msAssert.isTrue(sbytes[4] <= (uintValue & 0xFFFFFFFFL));
    }

    @Test
    public void testByteBinaryExpression()
    {
        //equality expression
        for (int i = 0; i < 4; i++)
        {
            msAssert.isTrue((uints[i] & 0xFFFFFFFFL) == (bytes[i] & 0xFF));
            msAssert.isTrue((bytes[i] & 0xFF) == (uints[i] & 0xFFFFFFFFL));
        }
        msAssert.isTrue((uints[4] & 0xFFFFFFFFL) != (bytes[4] & 0xFF));
        msAssert.isTrue((bytes[4] & 0xFF) != (uints[4] & 0xFFFFFFFFL));
        /*uint*/int uintValue = (/*uint*/int)(bytes[4] & 0xFF);
        msAssert.isTrue((uintValue & 0xFFFFFFFFL) == (bytes[4] & 0xFF));
        msAssert.isTrue((bytes[4] & 0xFF) == (uintValue & 0xFFFFFFFFL));
        //compare expression
        msAssert.isTrue((uintValue & 0xFFFFFFFFL) > (bytes[0] & 0xFF));
        msAssert.isTrue((uintValue & 0xFFFFFFFFL) >= (bytes[1] & 0xFF));
        msAssert.isFalse((uintValue & 0xFFFFFFFFL) < (bytes[2] & 0xFF));
        msAssert.isTrue((uintValue & 0xFFFFFFFFL) <= (bytes[4] & 0xFF));
        msAssert.isFalse((bytes[0] & 0xFF) > (uintValue & 0xFFFFFFFFL));
        msAssert.isFalse((bytes[1] & 0xFF) >= (uintValue & 0xFFFFFFFFL));
        msAssert.isTrue((bytes[2] & 0xFF) < (uintValue & 0xFFFFFFFFL));
        msAssert.isTrue((bytes[4] & 0xFF) <= (uintValue & 0xFFFFFFFFL));
    }

    @Test
    public void testShortBinaryExpression()
    {
        //equality expression
        for (int i = 0; i < 4; i++)
        {
            msAssert.isTrue((uints[i] & 0xFFFFFFFFL) == shorts[i]);
            msAssert.isTrue(shorts[i] == (uints[i] & 0xFFFFFFFFL));
        }
        msAssert.isTrue((uints[4] & 0xFFFFFFFFL) != shorts[4]);
        msAssert.isTrue(shorts[4] != (uints[4] & 0xFFFFFFFFL));
        /*uint*/int uintValue = (/*uint*/int)shorts[4];
        msAssert.isTrue((uintValue & 0xFFFFFFFFL) == shorts[4]);
        msAssert.isTrue(shorts[4] == (uintValue & 0xFFFFFFFFL));
        //compare expression
        msAssert.isTrue((uintValue & 0xFFFFFFFFL) > shorts[0]);
        msAssert.isTrue((uintValue & 0xFFFFFFFFL) >= shorts[1]);
        msAssert.isFalse((uintValue & 0xFFFFFFFFL) < shorts[2]);
        msAssert.isTrue((uintValue & 0xFFFFFFFFL) <= shorts[4]);
        msAssert.isFalse(shorts[0] > (uintValue & 0xFFFFFFFFL));
        msAssert.isFalse(shorts[1] >= (uintValue & 0xFFFFFFFFL));
        msAssert.isTrue(shorts[2] < (uintValue & 0xFFFFFFFFL));
        msAssert.isTrue(shorts[4] <= (uintValue & 0xFFFFFFFFL));
    }

    @Test
    public void testUShortBinaryExpression()
    {
        //equality expression
        for (int i = 0; i < 4; i++)
        {
            msAssert.isTrue((uints[i] & 0xFFFFFFFFL) == (ushorts[i] & 0xFFFF));
            msAssert.isTrue((ushorts[i] & 0xFFFF) == (uints[i] & 0xFFFFFFFFL));
        }
        msAssert.isTrue((uints[4] & 0xFFFFFFFFL) != (ushorts[4] & 0xFFFF));
        msAssert.isTrue((ushorts[4] & 0xFFFF) != (uints[4] & 0xFFFFFFFFL));
        /*uint*/int uintValue = (/*uint*/int)(ushorts[4] & 0xFFFF);
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
    public void testIntBinaryExpression()
    {
        //equality expression
        for (int i = 0; i < 4; i++)
        {
            msAssert.isTrue((uints[i] & 0xFFFFFFFFL) == ints[i]);
            msAssert.isTrue(ints[i] == (uints[i] & 0xFFFFFFFFL));
        }
        msAssert.isTrue((uints[4] & 0xFFFFFFFFL) != ints[4]);
        msAssert.isTrue(ints[4] != (uints[4] & 0xFFFFFFFFL));
        /*uint*/int uintValue = (/*uint*/int)ints[4];
        msAssert.isTrue((uintValue & 0xFFFFFFFFL) == ints[4]);
        msAssert.isTrue(ints[4] == (uintValue & 0xFFFFFFFFL));
        //compare expression
        msAssert.isTrue((uintValue & 0xFFFFFFFFL) > ints[0]);
        msAssert.isTrue((uintValue & 0xFFFFFFFFL) >= ints[1]);
        msAssert.isFalse((uintValue & 0xFFFFFFFFL) < ints[2]);
        msAssert.isTrue((uintValue & 0xFFFFFFFFL) <= ints[4]);
        msAssert.isFalse(ints[0] > (uintValue & 0xFFFFFFFFL));
        msAssert.isFalse(ints[1] >= (uintValue & 0xFFFFFFFFL));
        msAssert.isTrue(ints[2] < (uintValue & 0xFFFFFFFFL));
        msAssert.isTrue(ints[4] <= (uintValue & 0xFFFFFFFFL));
    }

    @Test
    public void testUIntBinaryExpression()
    {
        //equality expression
        /*uint*/int uintValue = (/*uint*/int)ints[4];
        msAssert.isTrue(uintValue != uints[4]);
        msAssert.isTrue(uints[4] != uintValue);
        msAssert.isTrue(uintValue == 2147483647);
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) + 2);
        msAssert.isTrue((uintValue & 0xFFFFFFFFL) == 2147483649L);
        for (int i = 0; i < 5; i++)
        {
            uintValue = uints[i];
            msAssert.isTrue(uintValue == uints[i]);
        }
        //compare expression
        msAssert.isTrue((uintValue & 0xFFFFFFFFL) > (uints[0] & 0xFFFFFFFFL));
        msAssert.isTrue((uintValue & 0xFFFFFFFFL) >= (uints[1] & 0xFFFFFFFFL));
        msAssert.isFalse((uintValue & 0xFFFFFFFFL) < (uints[2] & 0xFFFFFFFFL));
        msAssert.isTrue((uintValue & 0xFFFFFFFFL) <= (uints[4] & 0xFFFFFFFFL));
        msAssert.isFalse((uints[0] & 0xFFFFFFFFL) > (uintValue & 0xFFFFFFFFL));
        msAssert.isFalse((uints[1] & 0xFFFFFFFFL) >= (uintValue & 0xFFFFFFFFL));
        msAssert.isTrue((uints[2] & 0xFFFFFFFFL) < (uintValue & 0xFFFFFFFFL));
        msAssert.isTrue((uints[4] & 0xFFFFFFFFL) <= (uintValue & 0xFFFFFFFFL));
        //compare uint literal
        msAssert.isTrue((uintValue & 0xFFFFFFFFL) > 2147483649L);
        msAssert.isTrue((uintValue & 0xFFFFFFFFL) >= 2147483649L);
        uintValue = (/*uint*/int)ints[4];
        msAssert.isTrue((uintValue & 0xFFFFFFFFL) < 2147483649L);
        msAssert.isTrue((uintValue & 0xFFFFFFFFL) <= 2147483649L);
    }

    @Test
    public void testLongBinaryExpression()
    {
        //equality expression
        for (int i = 0; i < 4; i++)
        {
            msAssert.isTrue((uints[i] & 0xFFFFFFFFL) == longs[i]);
            msAssert.isTrue(longs[i] == (uints[i] & 0xFFFFFFFFL));
        }
        msAssert.isTrue((uints[4] & 0xFFFFFFFFL) != longs[4]);
        msAssert.isTrue(longs[4] != (uints[4] & 0xFFFFFFFFL));
        long longValue = (long)(uints[4] & 0xFFFFFFFFL);
        msAssert.isTrue(longValue == (uints[4] & 0xFFFFFFFFL));
        msAssert.isTrue((uints[4] & 0xFFFFFFFFL) == longValue);
        //compare expression
        msAssert.isTrue(longValue > (uints[0] & 0xFFFFFFFFL));
        msAssert.isTrue(longValue >= (uints[1] & 0xFFFFFFFFL));
        msAssert.isFalse(longValue < (uints[2] & 0xFFFFFFFFL));
        msAssert.isTrue(longValue <= (uints[4] & 0xFFFFFFFFL));
        msAssert.isFalse((uints[0] & 0xFFFFFFFFL) > longValue);
        msAssert.isFalse((uints[1] & 0xFFFFFFFFL) >= longValue);
        msAssert.isTrue((uints[2] & 0xFFFFFFFFL) < longValue);
        msAssert.isTrue((uints[4] & 0xFFFFFFFFL) <= longValue);
    }

    @Test
    public void testFloatBinaryExpression()
    {
        //equality expression
        for (int i = 0; i < 4; i++)
        {
            msAssert.isTrue((uints[i] & 0xFFFFFFFFL) == floats[i]);
            msAssert.isTrue(floats[i] == (uints[i] & 0xFFFFFFFFL));
        }
        //compare expression
        float floatValue = (uints[4] & 0xFFFFFFFFL);
        msAssert.isTrue(floatValue > (uints[0] & 0xFFFFFFFFL));
        msAssert.isTrue(floatValue >= (uints[1] & 0xFFFFFFFFL));
        msAssert.isFalse(floatValue < (uints[2] & 0xFFFFFFFFL));
        msAssert.isTrue(floatValue <= (uints[4] & 0xFFFFFFFFL));
        msAssert.isFalse((uints[0] & 0xFFFFFFFFL) > floatValue);
        msAssert.isFalse((uints[1] & 0xFFFFFFFFL) >= floatValue);
        msAssert.isTrue((uints[2] & 0xFFFFFFFFL) < floatValue);
        msAssert.isTrue((uints[4] & 0xFFFFFFFFL) <= floatValue);
    }

    @Test
    public void testDoubleBinaryExpression()
    {
        //equality expression
        for (int i = 0; i < 4; i++)
        {
            msAssert.isTrue((uints[i] & 0xFFFFFFFFL) == doubles[i]);
            msAssert.isTrue(doubles[i] == (uints[i] & 0xFFFFFFFFL));
        }
        //compare expression
        double doubleValue = (uints[4] & 0xFFFFFFFFL);
        msAssert.isTrue(doubleValue > (uints[0] & 0xFFFFFFFFL));
        msAssert.isTrue(doubleValue >= (uints[1] & 0xFFFFFFFFL));
        msAssert.isFalse(doubleValue < (uints[2] & 0xFFFFFFFFL));
        msAssert.isTrue(doubleValue <= (uints[4] & 0xFFFFFFFFL));
        msAssert.isFalse((uints[0] & 0xFFFFFFFFL) > doubleValue);
        msAssert.isFalse((uints[1] & 0xFFFFFFFFL) >= doubleValue);
        msAssert.isTrue((uints[2] & 0xFFFFFFFFL) < doubleValue);
        msAssert.isTrue((uints[4] & 0xFFFFFFFFL) <= doubleValue);
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

