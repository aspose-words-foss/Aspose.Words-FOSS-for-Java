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
public class TestBinaryExpressionByte
{
    @Test
    public void testSByteBinaryExpression()
    {
        //equality expression
        for (int i = 0; i < 4; i++)
        {
            msAssert.isTrue((bytes[i] & 0xFF) == sbytes[i]);
            msAssert.isTrue(sbytes[i] == (bytes[i] & 0xFF));
        }
        msAssert.isTrue((bytes[4] & 0xFF) != sbytes[4]);
        msAssert.isTrue(sbytes[4] != (bytes[4] & 0xFF));
        byte byteValue = (byte)sbytes[4];
        msAssert.isTrue((byteValue & 0xFF) == sbytes[4]);
        msAssert.isTrue(sbytes[4] == (byteValue & 0xFF));
        //compare expression
        msAssert.isTrue((byteValue & 0xFF) > sbytes[0]);
        msAssert.isTrue((byteValue & 0xFF) >= sbytes[1]);
        msAssert.isFalse((byteValue & 0xFF) < sbytes[2]);
        msAssert.isTrue((byteValue & 0xFF) <= sbytes[4]);
        msAssert.isFalse(sbytes[0] > (byteValue & 0xFF));
        msAssert.isFalse(sbytes[1] >= (byteValue & 0xFF));
        msAssert.isTrue(sbytes[2] < (byteValue & 0xFF));
        msAssert.isTrue(sbytes[4] <= (byteValue & 0xFF));
    }

    @Test
    public void testByteBinaryExpression()
    {
        //equality expression
        byte byteValue = (byte)sbytes[4];
        msAssert.isTrue(byteValue != bytes[4]);
        msAssert.isTrue(bytes[4] != byteValue);
        msAssert.isTrue(byteValue == 127);
        byteValue = (byte)((byteValue & 0xFF) + 2);
        msAssert.isTrue((byteValue & 0xFF) == 129);
        for (int i = 0; i < 5; i++)
        {
            byteValue = bytes[i];
            msAssert.isTrue(byteValue == bytes[i]);
        }
        //compare expression
        msAssert.isTrue((byteValue & 0xFF) > (bytes[0] & 0xFF));
        msAssert.isTrue((byteValue & 0xFF) >= (bytes[1] & 0xFF));
        msAssert.isFalse((byteValue & 0xFF) < (bytes[2] & 0xFF));
        msAssert.isTrue((byteValue & 0xFF) <= (bytes[4] & 0xFF));
        msAssert.isFalse((bytes[0] & 0xFF) > (byteValue & 0xFF));
        msAssert.isFalse((bytes[1] & 0xFF) >= (byteValue & 0xFF));
        msAssert.isTrue((bytes[2] & 0xFF) < (byteValue & 0xFF));
        msAssert.isTrue((bytes[4] & 0xFF) <= (byteValue & 0xFF));
    }

    @Test
    public void testShortBinaryExpression()
    {
        //equality expression
        for (int i = 0; i < 4; i++)
        {
            msAssert.isTrue((bytes[i] & 0xFF) == shorts[i]);
            msAssert.isTrue(shorts[i] == (bytes[i] & 0xFF));
        }
        msAssert.isTrue((bytes[4] & 0xFF) != shorts[4]);
        msAssert.isTrue(shorts[4] != (bytes[4] & 0xFF));
        short shortValue = (short)(bytes[4] & 0xFF);
        msAssert.isTrue((bytes[4] & 0xFF) == shortValue);
        msAssert.isTrue(shortValue == (bytes[4] & 0xFF));
        //compare expression
        msAssert.isTrue(shortValue > (bytes[0] & 0xFF));
        msAssert.isTrue(shortValue >= (bytes[1] & 0xFF));
        msAssert.isFalse(shortValue < (bytes[2] & 0xFF));
        msAssert.isTrue(shortValue <= (bytes[4] & 0xFF));
        msAssert.isFalse((bytes[0] & 0xFF) > shortValue);
        msAssert.isFalse((bytes[1] & 0xFF) >= shortValue);
        msAssert.isTrue((bytes[2] & 0xFF) < shortValue);
        msAssert.isTrue((bytes[4] & 0xFF) <= shortValue);
    }

    @Test
    public void testUShortBinaryExpression()
    {
        //equality expression
        for (int i = 0; i < 4; i++)
        {
            msAssert.isTrue((bytes[i] & 0xFF) == (ushorts[i] & 0xFFFF));
            msAssert.isTrue((ushorts[i] & 0xFFFF) == (bytes[i] & 0xFF));
        }
        msAssert.isTrue((bytes[4] & 0xFF) != (ushorts[4] & 0xFFFF));
        msAssert.isTrue((ushorts[4] & 0xFFFF) != (bytes[4] & 0xFF));
        /*ushort*/short ushortValue = (/*ushort*/short)(bytes[4] & 0xFF);
        msAssert.isTrue((bytes[4] & 0xFF) == (ushortValue & 0xFFFF));
        msAssert.isTrue((ushortValue & 0xFFFF) == (bytes[4] & 0xFF));
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
    public void testIntBinaryExpression()
    {
        //equality expression
        for (int i = 0; i < 4; i++)
        {
            msAssert.isTrue((bytes[i] & 0xFF) == ints[i]);
            msAssert.isTrue(ints[i] == (bytes[i] & 0xFF));
        }
        msAssert.isTrue((bytes[4] & 0xFF) != ints[4]);
        msAssert.isTrue(ints[4] != (bytes[4] & 0xFF));
        int intValue = (int)(bytes[4] & 0xFF);
        msAssert.isTrue((bytes[4] & 0xFF) == intValue);
        msAssert.isTrue(intValue == (bytes[4] & 0xFF));
        //compare expression
        msAssert.isTrue(intValue > (bytes[0] & 0xFF));
        msAssert.isTrue(intValue >= (bytes[1] & 0xFF));
        msAssert.isFalse(intValue < (bytes[2] & 0xFF));
        msAssert.isTrue(intValue <= (bytes[4] & 0xFF));
        msAssert.isFalse((bytes[0] & 0xFF) > intValue);
        msAssert.isFalse((bytes[1] & 0xFF) >= intValue);
        msAssert.isTrue((bytes[2] & 0xFF) < intValue);
        msAssert.isTrue((bytes[4] & 0xFF) <= intValue);
    }

    @Test
    public void testUIntBinaryExpression()
    {
        //equality expression
        for (int i = 0; i < 4; i++)
        {
            msAssert.isTrue((bytes[i] & 0xFF) == (uints[i] & 0xFFFFFFFFL));
            msAssert.isTrue((uints[i] & 0xFFFFFFFFL) == (bytes[i] & 0xFF));
        }
        msAssert.isTrue((bytes[4] & 0xFF) != (uints[4] & 0xFFFFFFFFL));
        msAssert.isTrue((uints[4] & 0xFFFFFFFFL) != (bytes[4] & 0xFF));
        /*uint*/int uintValue = (/*uint*/int)(bytes[4] & 0xFF);
        msAssert.isTrue((bytes[4] & 0xFF) == (uintValue & 0xFFFFFFFFL));
        msAssert.isTrue((uintValue & 0xFFFFFFFFL) == (bytes[4] & 0xFF));
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
    public void testLongBinaryExpression()
    {
        //equality expression
        for (int i = 0; i < 4; i++)
        {
            msAssert.isTrue((bytes[i] & 0xFF) == longs[i]);
            msAssert.isTrue(longs[i] == (bytes[i] & 0xFF));
        }
        msAssert.isTrue((bytes[4] & 0xFF) != longs[4]);
        msAssert.isTrue(longs[4] != (bytes[4] & 0xFF));
        long longValue = (long)(bytes[4] & 0xFF);
        msAssert.isTrue((bytes[4] & 0xFF) == longValue);
        msAssert.isTrue(longValue == (bytes[4] & 0xFF));
        //compare expression
        msAssert.isTrue(longValue > (bytes[0] & 0xFF));
        msAssert.isTrue(longValue >= (bytes[1] & 0xFF));
        msAssert.isFalse(longValue < (bytes[2] & 0xFF));
        msAssert.isTrue(longValue <= (bytes[4] & 0xFF));
        msAssert.isFalse((bytes[0] & 0xFF) > longValue);
        msAssert.isFalse((bytes[1] & 0xFF) >= longValue);
        msAssert.isTrue((bytes[2] & 0xFF) < longValue);
        msAssert.isTrue((bytes[4] & 0xFF) <= longValue);
    }

    @Test
    public void testFloatBinaryExpression()
    {
        //equality expression
        for (int i = 0; i < 4; i++)
        {
            msAssert.isTrue((bytes[i] & 0xFF) == floats[i]);
            msAssert.isTrue(floats[i] == (bytes[i] & 0xFF));
        }
        //compare expression
        float floatValue = (bytes[4] & 0xFF);
        msAssert.isTrue(floatValue > (bytes[0] & 0xFF));
        msAssert.isTrue(floatValue >= (bytes[1] & 0xFF));
        msAssert.isFalse(floatValue < (bytes[2] & 0xFF));
        msAssert.isTrue(floatValue <= (bytes[4] & 0xFF));
        msAssert.isFalse((bytes[0] & 0xFF) > floatValue);
        msAssert.isFalse((bytes[1] & 0xFF) >= floatValue);
        msAssert.isTrue((bytes[2] & 0xFF) < floatValue);
        msAssert.isTrue((bytes[4] & 0xFF) <= floatValue);
    }

    @Test
    public void testDoubleBinaryExpression()
    {
        //equality expression
        for (int i = 0; i < 4; i++)
        {
            msAssert.isTrue((bytes[i] & 0xFF) == doubles[i]);
            msAssert.isTrue(doubles[i] == (bytes[i] & 0xFF));
        }
        //compare expression
        double doubleValue = (bytes[4] & 0xFF);
        msAssert.isTrue(doubleValue > (bytes[0] & 0xFF));
        msAssert.isTrue(doubleValue >= (bytes[1] & 0xFF));
        msAssert.isFalse(doubleValue < (bytes[2] & 0xFF));
        msAssert.isTrue(doubleValue <= (bytes[4] & 0xFF));
        msAssert.isFalse((bytes[0] & 0xFF) > doubleValue);
        msAssert.isFalse((bytes[1] & 0xFF) >= doubleValue);
        msAssert.isTrue((bytes[2] & 0xFF) < doubleValue);
        msAssert.isTrue((bytes[4] & 0xFF) <= doubleValue);
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

