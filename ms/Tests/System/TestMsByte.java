// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/03/2017 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msByte;
import com.aspose.ms.System.msUShort;
import com.aspose.ms.System.msUInt;


@Test
public class TestMsByte
{
    @Test
    public void testUnaryOperators()
    {
        byte byteValue = (byte)245;
        byte byteValue2 = (byte)10;
        msAssert.areEqual(245, +(byteValue & 0xFF));
        msAssert.areEqual(-245, -(byteValue & 0xFF));
        msAssert.areEqual(245, (byteValue++) & 0xFF);
        msAssert.areEqual(247, (++byteValue) & 0xFF);
        msAssert.areEqual(247, (byteValue--) & 0xFF);
        msAssert.areEqual(245, (--byteValue) & 0xFF);
        msAssert.areEqual(-246, ~(byteValue & 0xFF));
        msAssert.areEqual(-11, ~(byteValue2 & 0xFF));
    }

    @Test
    public void testUnaryBitwiseOperator()
    {
        byte maxValue = msByte.MAX_VALUE;
        byte minValue = msByte.MIN_VALUE;

        msAssert.areEqual(-256, ~(maxValue & 0xFF));
        msAssert.areEqual(-1, ~(minValue & 0xFF));

        msAssert.areEqual(-257, ~((maxValue & 0xFF) + 1));
        msAssert.areEqual(-255, ~((maxValue & 0xFF) - 1));

        msAssert.areEqual(-2, ~((minValue & 0xFF) + 1));
        msAssert.areEqual(0, ~((minValue & 0xFF) - 1));

        msAssert.areEqual(-2, ~(bytes[0] & 0xFF));
        msAssert.areEqual(-3, ~(bytes[1] & 0xFF));
        msAssert.areEqual(-4, ~(bytes[2] & 0xFF));
        msAssert.areEqual(-51, ~(bytes[3] & 0xFF));
    }

    @Test
    public void testBinaryBitwiseOperator()
    {
        byte byteValue = (byte)245;
        byte byteValue2 = (byte)11;

        msAssert.areEqual(5, (byteValue & 0xFF) & 0x0F);
        msAssert.areEqual(1, (byteValue & 0xFF) & (byteValue2 & 0xFF));

        msAssert.areEqual(255, (byteValue & 0xFF) | 0x0F);
        msAssert.areEqual(255, (byteValue & 0xFF) | (byteValue2 & 0xFF));

        msAssert.areEqual(250, (byteValue & 0xFF) ^ 0x0F);
        msAssert.areEqual(254, (byteValue & 0xFF) ^ (byteValue2 & 0xFF));

        msAssert.areEqual(122, (byteValue & 0xFF) >>> 1);
        msAssert.areEqual(22, (byteValue2 & 0xFF) << 1);
    }

    @Test
    public void testIncrementOperators()
    {
        byte byteValue = bytes[4];
        byteValue++;
        msAssert.areEqual(0, byteValue);
        byteValue--;
        msAssert.areEqual(255, byteValue);
        ++byteValue;
        msAssert.areEqual(0, byteValue);
        --byteValue;
        msAssert.areEqual(255, byteValue);
        byteValue = (byte)sbytes[4];
        byteValue++;
        msAssert.areEqual(128, byteValue);
        byteValue--;
        msAssert.areEqual(127, byteValue);
        ++byteValue;
        msAssert.areEqual(128, byteValue);
        --byteValue;
        msAssert.areEqual(127, byteValue);
    }

    @Test
    public void testShiftOperators()
    {
        byte byteValue = bytes[4];
        msAssert.areEqual(4080, (byteValue & 0xFF) << 4);
        msAssert.areEqual(65280, (byteValue & 0xFF) << 8);
        msAssert.areEqual(16711680, (byteValue & 0xFF) << 16);
        msAssert.areEqual(-16777216, (byteValue & 0xFF) << 24);
        msAssert.areEqual(255, (byteValue & 0xFF) << 32);
        msAssert.areEqual(65280, (byteValue & 0xFF) << 40);
        msAssert.areEqual(16711680, (byteValue & 0xFF) << 48);
        msAssert.areEqual(-16777216, (byteValue & 0xFF) << 56);
        msAssert.areEqual(15, (byteValue & 0xFF) >>> 4);

        msAssert.areEqual(1020, (byteValue & 0xFF) << sbytes[1]);
        msAssert.areEqual(1020, (byteValue & 0xFF) << (bytes[1] & 0xFF));
        msAssert.areEqual(1020, (byteValue & 0xFF) << shorts[1]);
        msAssert.areEqual(1020, (byteValue & 0xFF) << (ushorts[1] & 0xFFFF));
        msAssert.areEqual(1020, (byteValue & 0xFF) << ints[1]);

        msAssert.areEqual(63, (byteValue & 0xFF) >>> sbytes[1]);
        msAssert.areEqual(63, (byteValue & 0xFF) >>> (bytes[1] & 0xFF));
        msAssert.areEqual(63, (byteValue & 0xFF) >>> shorts[1]);
        msAssert.areEqual(63, (byteValue & 0xFF) >>> (ushorts[1] & 0xFFFF));
        msAssert.areEqual(63, (byteValue & 0xFF) >>> ints[1]);
    }

    @Test
    public void testAssignment()
    {
        byte byteValue = (byte)sbytes[2];
        msAssert.areEqual("3", msByte.toString(byteValue));
        byteValue = bytes[2];
        msAssert.areEqual("3", msByte.toString(byteValue));
        byteValue = (byte)shorts[2];
        msAssert.areEqual("3", msByte.toString(byteValue));
        byteValue = (byte)(ushorts[2] & 0xFFFF);
        msAssert.areEqual("3", msByte.toString(byteValue));
        byteValue = (byte)ints[2];
        msAssert.areEqual("3", msByte.toString(byteValue));
        byteValue = (byte)(uints[2] & 0xFFFFFFFFL);
        msAssert.areEqual("3", msByte.toString(byteValue));
        byteValue = (byte)longs[2];
        msAssert.areEqual("3", msByte.toString(byteValue));
        byteValue = (byte)floats[2];
        msAssert.areEqual("3", msByte.toString(byteValue));
        byteValue = (byte)doubles[2];
        msAssert.areEqual("3", msByte.toString(byteValue));
    }

    @Test
    public void testSByteCompoundAssignment()
    {
        //arithmetic addition
        byte byteValue = (byte)(sbytes[4] / 2);
        byteValue = (byte)((byteValue & 0xFF) + (((byte)sbytes[1]) & 0xFF));
        msAssert.areEqual("65", msByte.toString(byteValue));
        byteValue = (byte)((byteValue & 0xFF) - (((byte)sbytes[2]) & 0xFF));
        msAssert.areEqual("62", msByte.toString(byteValue));
        //arithmetic scaling
        byteValue = (byte)((byteValue & 0xFF) * (((byte)sbytes[1]) & 0xFF));
        msAssert.areEqual("124", msByte.toString(byteValue));
        byteValue = (byte)((byteValue & 0xFF) % (((byte)sbytes[3]) & 0xFF));
        msAssert.areEqual("24", msByte.toString(byteValue));
        byteValue = (byte)((byteValue & 0xFF) / (((byte)sbytes[1]) & 0xFF));
        msAssert.areEqual("12", msByte.toString(byteValue));
        //bitwise
        byteValue &= (byte)sbytes[1];
        msAssert.areEqual("0", msByte.toString(byteValue));
        byteValue |= (byte)sbytes[1];
        msAssert.areEqual("2", msByte.toString(byteValue));
        byteValue ^= (byte)sbytes[3];
        msAssert.areEqual("48", msByte.toString(byteValue));
        //bitwise shift
        byteValue = (byte)(byteValue >>> (((byte)sbytes[1]) & 0xFF));
        msAssert.areEqual("12", msByte.toString(byteValue));
        byteValue <<= (byte)sbytes[1];
        msAssert.areEqual("48", msByte.toString(byteValue));
    }

    @Test
    public void testByteCompoundAssignment()
    {
        //arithmetic addition
        byte byteValue = (byte)((bytes[4] & 0xFF) / 2);
        byteValue = (byte)((byteValue & 0xFF) + (bytes[1] & 0xFF));
        msAssert.areEqual("129", msByte.toString(byteValue));
        byteValue = (byte)((byteValue & 0xFF) - (bytes[2] & 0xFF));
        msAssert.areEqual("126", msByte.toString(byteValue));
        //arithmetic scaling
        byteValue = (byte)((byteValue & 0xFF) * (bytes[1] & 0xFF));
        msAssert.areEqual("252", msByte.toString(byteValue));
        byteValue = (byte)((byteValue & 0xFF) % (bytes[3] & 0xFF));
        msAssert.areEqual("2", msByte.toString(byteValue));
        byteValue = (byte)((byteValue & 0xFF) / (bytes[1] & 0xFF));
        msAssert.areEqual("1", msByte.toString(byteValue));
        //bitwise
        byteValue &= bytes[1];
        msAssert.areEqual("0", msByte.toString(byteValue));
        byteValue |= bytes[1];
        msAssert.areEqual("2", msByte.toString(byteValue));
        byteValue ^= bytes[3];
        msAssert.areEqual("48", msByte.toString(byteValue));
        //bitwise shift
        byteValue = (byte)(byteValue >>> (bytes[1] & 0xFF));
        msAssert.areEqual("12", msByte.toString(byteValue));
        byteValue <<= bytes[1];
        msAssert.areEqual("48", msByte.toString(byteValue));
    }

    @Test
    public void testShortCompoundAssignment()
    {
        //arithmetic addition
        byte byteValue = (byte)(shorts[4] / 2);
        byteValue = (byte)((byteValue & 0xFF) + (((byte)shorts[1]) & 0xFF));
        msAssert.areEqual("1", msByte.toString(byteValue));
        byteValue = (byte)((byteValue & 0xFF) - (((byte)shorts[2]) & 0xFF));
        msAssert.areEqual("254", msByte.toString(byteValue));
        //arithmetic scaling
        byteValue = (byte)((byteValue & 0xFF) * (((byte)shorts[1]) & 0xFF));
        msAssert.areEqual("252", msByte.toString(byteValue));
        byteValue = (byte)((byteValue & 0xFF) % (((byte)shorts[3]) & 0xFF));
        msAssert.areEqual("2", msByte.toString(byteValue));
        byteValue = (byte)((byteValue & 0xFF) / (((byte)shorts[1]) & 0xFF));
        msAssert.areEqual("1", msByte.toString(byteValue));
        //bitwise
        byteValue &= (byte)shorts[1];
        msAssert.areEqual("0", msByte.toString(byteValue));
        byteValue |= (byte)shorts[1];
        msAssert.areEqual("2", msByte.toString(byteValue));
        byteValue ^= (byte)shorts[3];
        msAssert.areEqual("48", msByte.toString(byteValue));
        //bitwise shift
        byteValue = (byte)(byteValue >>> shorts[1]);
        msAssert.areEqual("12", msByte.toString(byteValue));
        byteValue <<= shorts[1];
        msAssert.areEqual("48", msByte.toString(byteValue));
    }

    @Test
    public void testUShortCompoundAssignment()
    {
        //arithmetic addition
        byte byteValue = (byte)((ushorts[4] & 0xFFFF) / 2);
        byteValue = (byte)((byteValue & 0xFF) + (((byte)(ushorts[1] & 0xFFFF)) & 0xFF));
        msAssert.areEqual("1", msByte.toString(byteValue));
        byteValue = (byte)((byteValue & 0xFF) - (((byte)(ushorts[2] & 0xFFFF)) & 0xFF));
        msAssert.areEqual("254", msByte.toString(byteValue));
        //arithmetic scaling
        byteValue = (byte)((byteValue & 0xFF) * (((byte)(ushorts[1] & 0xFFFF)) & 0xFF));
        msAssert.areEqual("252", msByte.toString(byteValue));
        byteValue = (byte)((byteValue & 0xFF) % (((byte)(ushorts[3] & 0xFFFF)) & 0xFF));
        msAssert.areEqual("2", msByte.toString(byteValue));
        byteValue = (byte)((byteValue & 0xFF) / (((byte)(ushorts[1] & 0xFFFF)) & 0xFF));
        msAssert.areEqual("1", msByte.toString(byteValue));
        //bitwise
        byteValue &= (byte)(ushorts[1] & 0xFFFF);
        msAssert.areEqual("0", msByte.toString(byteValue));
        byteValue |= (byte)(ushorts[1] & 0xFFFF);
        msAssert.areEqual("2", msByte.toString(byteValue));
        byteValue ^= (byte)(ushorts[3] & 0xFFFF);
        msAssert.areEqual("48", msByte.toString(byteValue));
        //bitwise shift
        byteValue = (byte)(byteValue >>> (ushorts[1] & 0xFFFF));
        msAssert.areEqual("12", msByte.toString(byteValue));
        byteValue <<= ushorts[1];
        msAssert.areEqual("48", msByte.toString(byteValue));
    }

    @Test
    public void testIntCompoundAssignment()
    {
        //arithmetic addition
        byte byteValue = (byte)(ints[4] / 2);
        byteValue = (byte)((byteValue & 0xFF) + (((byte)ints[1]) & 0xFF));
        msAssert.areEqual("1", msByte.toString(byteValue));
        byteValue = (byte)((byteValue & 0xFF) - (((byte)ints[2]) & 0xFF));
        msAssert.areEqual("254", msByte.toString(byteValue));
        //arithmetic scaling
        byteValue = (byte)((byteValue & 0xFF) * (((byte)ints[1]) & 0xFF));
        msAssert.areEqual("252", msByte.toString(byteValue));
        byteValue = (byte)((byteValue & 0xFF) % (((byte)ints[3]) & 0xFF));
        msAssert.areEqual("2", msByte.toString(byteValue));
        byteValue = (byte)((byteValue & 0xFF) / (((byte)ints[1]) & 0xFF));
        msAssert.areEqual("1", msByte.toString(byteValue));
        //bitwise
        byteValue &= (byte)ints[1];
        msAssert.areEqual("0", msByte.toString(byteValue));
        byteValue |= (byte)ints[1];
        msAssert.areEqual("2", msByte.toString(byteValue));
        byteValue ^= (byte)ints[3];
        msAssert.areEqual("48", msByte.toString(byteValue));
        //bitwise shift
        byteValue = (byte)(byteValue >>> ints[1]);
        msAssert.areEqual("12", msByte.toString(byteValue));
        byteValue <<= ints[1];
        msAssert.areEqual("48", msByte.toString(byteValue));
    }

    @Test
    public void testUIntCompoundAssignment()
    {
        //arithmetic addition
        byte byteValue = (byte)((uints[4] & 0xFFFFFFFFL) / 2);
        byteValue = (byte)((byteValue & 0xFF) + (((byte)(uints[1] & 0xFFFFFFFFL)) & 0xFF));
        msAssert.areEqual("1", msByte.toString(byteValue));
        byteValue = (byte)((byteValue & 0xFF) - (((byte)(uints[2] & 0xFFFFFFFFL)) & 0xFF));
        msAssert.areEqual("254", msByte.toString(byteValue));
        //arithmetic scaling
        byteValue = (byte)((byteValue & 0xFF) * (((byte)(uints[1] & 0xFFFFFFFFL)) & 0xFF));
        msAssert.areEqual("252", msByte.toString(byteValue));
        byteValue = (byte)((byteValue & 0xFF) % (((byte)(uints[3] & 0xFFFFFFFFL)) & 0xFF));
        msAssert.areEqual("2", msByte.toString(byteValue));
        byteValue = (byte)((byteValue & 0xFF) / (((byte)(uints[1] & 0xFFFFFFFFL)) & 0xFF));
        msAssert.areEqual("1", msByte.toString(byteValue));
        //bitwise
        byteValue &= (byte)(uints[1] & 0xFFFFFFFFL);
        msAssert.areEqual("0", msByte.toString(byteValue));
        byteValue |= (byte)(uints[1] & 0xFFFFFFFFL);
        msAssert.areEqual("2", msByte.toString(byteValue));
        byteValue ^= (byte)(uints[3] & 0xFFFFFFFFL);
        msAssert.areEqual("48", msByte.toString(byteValue));
        //bitwise shift
        byteValue = (byte)(byteValue >>> (int)(uints[1] & 0xFFFFFFFFL));
        msAssert.areEqual("12", msByte.toString(byteValue));
        byteValue <<= (int)(uints[1] & 0xFFFFFFFFL);
        msAssert.areEqual("48", msByte.toString(byteValue));
    }

    @Test
    public void testLongCompoundAssignment()
    {
        //arithmetic addition
        byte byteValue = (byte)(longs[4] / 2);
        byteValue = (byte)((byteValue & 0xFF) + (((byte)longs[1]) & 0xFF));
        msAssert.areEqual("1", msByte.toString(byteValue));
        byteValue = (byte)((byteValue & 0xFF) - (((byte)longs[2]) & 0xFF));
        msAssert.areEqual("254", msByte.toString(byteValue));
        //arithmetic scaling
        byteValue = (byte)((byteValue & 0xFF) * (((byte)longs[1]) & 0xFF));
        msAssert.areEqual("252", msByte.toString(byteValue));
        byteValue = (byte)((byteValue & 0xFF) % (((byte)longs[3]) & 0xFF));
        msAssert.areEqual("2", msByte.toString(byteValue));
        byteValue = (byte)((byteValue & 0xFF) / (((byte)longs[1]) & 0xFF));
        msAssert.areEqual("1", msByte.toString(byteValue));
        //bitwise
        byteValue &= (byte)longs[1];
        msAssert.areEqual("0", msByte.toString(byteValue));
        byteValue |= (byte)longs[1];
        msAssert.areEqual("2", msByte.toString(byteValue));
        byteValue ^= (byte)longs[3];
        msAssert.areEqual("48", msByte.toString(byteValue));
        //bitwise shift
        byteValue = (byte)(byteValue >>> (int)longs[1]);
        msAssert.areEqual("12", msByte.toString(byteValue));
        byteValue <<= (int)longs[1];
        msAssert.areEqual("48", msByte.toString(byteValue));
    }

    @Test
    public void testFloatCompoundAssignment()
    {
        //arithmetic addition
        byte byteValue = (byte)(Byte.MAX_VALUE);
        byteValue = (byte)((byteValue & 0xFF) + (((byte)floats[1]) & 0xFF));
        msAssert.areEqual("129", msByte.toString(byteValue));
        byteValue = (byte)((byteValue & 0xFF) - (((byte)floats[2]) & 0xFF));
        msAssert.areEqual("126", msByte.toString(byteValue));
        //arithmetic scaling
        byteValue = (byte)((byteValue & 0xFF) * (((byte)floats[1]) & 0xFF));
        msAssert.areEqual("252", msByte.toString(byteValue));
        byteValue = (byte)((byteValue & 0xFF) % (((byte)floats[3]) & 0xFF));
        msAssert.areEqual("2", msByte.toString(byteValue));
        byteValue = (byte)((byteValue & 0xFF) / (((byte)floats[1]) & 0xFF));
        msAssert.areEqual("1", msByte.toString(byteValue));
        //bitwise
        byteValue &= (byte)floats[1];
        msAssert.areEqual("0", msByte.toString(byteValue));
        byteValue |= (byte)floats[1];
        msAssert.areEqual("2", msByte.toString(byteValue));
        byteValue ^= (byte)floats[3];
        msAssert.areEqual("48", msByte.toString(byteValue));
        //bitwise shift
        byteValue = (byte)(byteValue >>> (int)floats[1]);
        msAssert.areEqual("12", msByte.toString(byteValue));
        byteValue <<= (int)floats[1];
        msAssert.areEqual("48", msByte.toString(byteValue));
    }

    @Test
    public void testDoubleCompoundAssignment()
    {
        //arithmetic addition
        byte byteValue = (byte)(Byte.MAX_VALUE);
        byteValue = (byte)((byteValue & 0xFF) + (((byte)doubles[1]) & 0xFF));
        msAssert.areEqual("129", msByte.toString(byteValue));
        byteValue = (byte)((byteValue & 0xFF) - (((byte)doubles[2]) & 0xFF));
        msAssert.areEqual("126", msByte.toString(byteValue));
        //arithmetic scaling
        byteValue = (byte)((byteValue & 0xFF) * (((byte)doubles[1]) & 0xFF));
        msAssert.areEqual("252", msByte.toString(byteValue));
        byteValue = (byte)((byteValue & 0xFF) % (((byte)doubles[3]) & 0xFF));
        msAssert.areEqual("2", msByte.toString(byteValue));
        byteValue = (byte)((byteValue & 0xFF) / (((byte)doubles[1]) & 0xFF));
        msAssert.areEqual("1", msByte.toString(byteValue));
        //bitwise
        byteValue &= (byte)doubles[1];
        msAssert.areEqual("0", msByte.toString(byteValue));
        byteValue |= (byte)doubles[1];
        msAssert.areEqual("2", msByte.toString(byteValue));
        byteValue ^= (byte)doubles[3];
        msAssert.areEqual("48", msByte.toString(byteValue));
        //bitwise shift
        byteValue = (byte)(byteValue >>> (int)doubles[1]);
        msAssert.areEqual("12", msByte.toString(byteValue));
        byteValue <<= (int)doubles[1];
        msAssert.areEqual("48", msByte.toString(byteValue));
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

