// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/03/2017 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msUInt;
import com.aspose.ms.System.msByte;
import com.aspose.ms.System.msUShort;


@Test
public class TestMsUInt
{
    @Test
    public void testUnaryOperators()
    {
        /*uint*/int uintValue = (int) 4294967285L;
        /*uint*/int uintValue2 = (int) 10L;
        msAssert.isTrue(4294967285L == +(uintValue & 0xFFFFFFFFL));
        msAssert.isTrue(-4294967285L == -(uintValue & 0xFFFFFFFFL));
        msAssert.isTrue(4294967285L == (uintValue++ & 0xFFFFFFFFL));
        msAssert.isTrue(4294967287L == ((++uintValue) & 0xFFFFFFFFL));
        msAssert.isTrue(4294967287L == (uintValue-- & 0xFFFFFFFFL));
        msAssert.isTrue(4294967285L == ((--uintValue) & 0xFFFFFFFFL));
        msAssert.isTrue(10 == (~uintValue & 0xFFFFFFFFL));
        msAssert.isTrue(4294967285L == (~uintValue2 & 0xFFFFFFFFL));
    }

    @Test
    public void testUnaryBitwiseOperator()
    {
        /*uint*/int maxValue = msUInt.MAX_VALUE;
        /*uint*/int minValue = msUInt.MIN_VALUE;

        msAssert.areEqual(0, (~maxValue & 0xFFFFFFFFL));
        msAssert.areEqual((int) 4294967295L, (~minValue & 0xFFFFFFFFL));

        msAssert.areEqual((int) 4294967295L, ~((maxValue & 0xFFFFFFFFL) + 1));
        msAssert.areEqual(1, ~((maxValue & 0xFFFFFFFFL) - 1));

        msAssert.areEqual((int) 4294967294L, ~((minValue & 0xFFFFFFFFL) + 1));
        msAssert.areEqual(0, ~((minValue & 0xFFFFFFFFL) - 1));

        msAssert.areEqual((int) 4294967294L, (~uints[0] & 0xFFFFFFFFL));
        msAssert.areEqual((int) 4294967293L, (~uints[1] & 0xFFFFFFFFL));
        msAssert.areEqual((int) 4294967292L, (~uints[2] & 0xFFFFFFFFL));
        msAssert.areEqual((int) 4294967245L, (~uints[3] & 0xFFFFFFFFL));
    }

    @Test
    public void testBinaryBitwiseOperator()
    {
        /*uint*/int uintValue = (int) 4294967285L;
        /*uint*/int uintValue2 = (int) 11L;

        msAssert.areEqual(252645125, (uintValue & 0xFFFFFFFFL) & 0x0F0F0F0F);
        msAssert.areEqual(1, (uintValue & 0xFFFFFFFFL) & (uintValue2 & 0xFFFFFFFFL));

        msAssert.areEqual((int) 4294967295L, (uintValue & 0xFFFFFFFFL) | 0x0F0F0F0F);
        msAssert.areEqual((int) 4294967295L, (uintValue & 0xFFFFFFFFL) | (uintValue2 & 0xFFFFFFFFL));

        msAssert.areEqual((int) 4042322170L, (uintValue & 0xFFFFFFFFL) ^ 0x0F0F0F0F);
        msAssert.areEqual((int) 4294967294L, (uintValue & 0xFFFFFFFFL) ^ (uintValue2 & 0xFFFFFFFFL));

        msAssert.areEqual(3, uintValue >>> 30);
        msAssert.areEqual((int) 3221225472L, ((uintValue2 << 30) & 0xFFFFFFFFL));
    }

    @Test
    public void testIncrementOperators()
    {
        /*uint*/int uintValue = uints[4];
        uintValue++;
        msAssert.areEqual(0, uintValue);
        uintValue--;
        msAssert.areEqual((int) 4294967295L, uintValue);
        ++uintValue;
        msAssert.areEqual(0, uintValue);
        --uintValue;
        msAssert.areEqual((int) 4294967295L, uintValue);
        uintValue = (/*uint*/int)ints[4];
        uintValue++;
        msAssert.areEqual((int) 2147483648L, uintValue);
        uintValue--;
        msAssert.areEqual(2147483647, uintValue);
        ++uintValue;
        msAssert.areEqual((int) 2147483648L, uintValue);
        --uintValue;
        msAssert.areEqual(2147483647, uintValue);
    }

    @Test
    public void testShiftOperators()
    {
        /*uint*/int uintValue = uints[4];
        msAssert.areEqual((int) 4294967280L, ((uintValue << 4) & 0xFFFFFFFFL));
        msAssert.areEqual((int) 4294967040L, ((uintValue << 8) & 0xFFFFFFFFL));
        msAssert.areEqual((int) 4294901760L, ((uintValue << 16) & 0xFFFFFFFFL));
        msAssert.areEqual((int) 4278190080L, ((uintValue << 24) & 0xFFFFFFFFL));
        msAssert.areEqual((int) 4294967295L, ((uintValue << 32) & 0xFFFFFFFFL));
        msAssert.areEqual((int) 4294967040L, ((uintValue << 40) & 0xFFFFFFFFL));
        msAssert.areEqual((int) 4294901760L, ((uintValue << 48) & 0xFFFFFFFFL));
        msAssert.areEqual((int) 4278190080L, ((uintValue << 56) & 0xFFFFFFFFL));
        msAssert.areEqual(268435455, uintValue >>> 4);

        msAssert.areEqual((int) 4294967292L, ((uintValue << sbytes[1]) & 0xFFFFFFFFL));
        msAssert.areEqual((int) 4294967292L, ((uintValue << (bytes[1] & 0xFF)) & 0xFFFFFFFFL));
        msAssert.areEqual((int) 4294967292L, ((uintValue << shorts[1]) & 0xFFFFFFFFL));
        msAssert.areEqual((int) 4294967292L, ((uintValue << (ushorts[1] & 0xFFFF)) & 0xFFFFFFFFL));
        msAssert.areEqual((int) 4294967292L, ((uintValue << ints[1]) & 0xFFFFFFFFL));

        msAssert.areEqual(1073741823, uintValue >>> sbytes[1]);
        msAssert.areEqual(1073741823, uintValue >>> (bytes[1] & 0xFF));
        msAssert.areEqual(1073741823, uintValue >>> shorts[1]);
        msAssert.areEqual(1073741823, uintValue >>> (ushorts[1] & 0xFFFF));
        msAssert.areEqual(1073741823, uintValue >>> ints[1]);
    }

    @Test
    public void testAssignment()
    {
        /*uint*/int uintValue = (/*uint*/int)sbytes[2];
        msAssert.areEqual("3", msUInt.toString(uintValue));
        uintValue = (bytes[2] & 0xFF);
        msAssert.areEqual("3", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)shorts[2];
        msAssert.areEqual("3", msUInt.toString(uintValue));
        uintValue = (ushorts[2] & 0xFFFF);
        msAssert.areEqual("3", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)ints[2];
        msAssert.areEqual("3", msUInt.toString(uintValue));
        uintValue = uints[2];
        msAssert.areEqual("3", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)longs[2];
        msAssert.areEqual("3", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)floats[2];
        msAssert.areEqual("3", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)doubles[2];
        msAssert.areEqual("3", msUInt.toString(uintValue));
    }

    @Test
    public void testSByteCompoundAssignment()
    {
        //arithmetic addition
        /*uint*/int uintValue = (/*uint*/int)(sbytes[4] / 2);
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) + (((/*uint*/int)sbytes[1]) & 0xFFFFFFFFL));
        msAssert.areEqual("65", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) - (((/*uint*/int)sbytes[2]) & 0xFFFFFFFFL));
        msAssert.areEqual("62", msUInt.toString(uintValue));
        //arithmetic scaling
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) * (((/*uint*/int)sbytes[1]) & 0xFFFFFFFFL));
        msAssert.areEqual("124", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) % (((/*uint*/int)sbytes[3]) & 0xFFFFFFFFL));
        msAssert.areEqual("24", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) / (((/*uint*/int)sbytes[1]) & 0xFFFFFFFFL));
        msAssert.areEqual("12", msUInt.toString(uintValue));
        //bitwise
        uintValue &= (/*uint*/int)sbytes[1];
        msAssert.areEqual("0", msUInt.toString(uintValue));
        uintValue |= (/*uint*/int)sbytes[1];
        msAssert.areEqual("2", msUInt.toString(uintValue));
        uintValue ^= (/*uint*/int)sbytes[3];
        msAssert.areEqual("48", msUInt.toString(uintValue));
        //bitwise shift
        uintValue = (/*uint*/int)(uintValue >>> sbytes[1]);
        msAssert.areEqual("12", msUInt.toString(uintValue));
        uintValue <<= sbytes[1];
        msAssert.areEqual("48", msUInt.toString(uintValue));
    }

    @Test
    public void testByteCompoundAssignment()
    {
        //arithmetic addition
        /*uint*/int uintValue = (/*uint*/int)((bytes[4] & 0xFF) / 2);
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) + (bytes[1] & 0xFF));
        msAssert.areEqual("129", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) - (bytes[2] & 0xFF));
        msAssert.areEqual("126", msUInt.toString(uintValue));
        //arithmetic scaling
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) * (bytes[1] & 0xFF));
        msAssert.areEqual("252", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) % (bytes[3] & 0xFF));
        msAssert.areEqual("2", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) / (bytes[1] & 0xFF));
        msAssert.areEqual("1", msUInt.toString(uintValue));
        //bitwise
        uintValue &= ((bytes[1]) & 0xFF);
        msAssert.areEqual("0", msUInt.toString(uintValue));
        uintValue |= ((bytes[1]) & 0xFF);
        msAssert.areEqual("2", msUInt.toString(uintValue));
        uintValue ^= ((bytes[3]) & 0xFF);
        msAssert.areEqual("48", msUInt.toString(uintValue));
        //bitwise shift
        uintValue = (/*uint*/int)(uintValue >>> (bytes[1] & 0xFF));
        msAssert.areEqual("12", msUInt.toString(uintValue));
        uintValue <<= ((bytes[1]) & 0xFF);
        msAssert.areEqual("48", msUInt.toString(uintValue));
    }

    @Test
    public void testShortCompoundAssignment()
    {
        //arithmetic addition
        /*uint*/int uintValue = (/*uint*/int)(shorts[4] / 2);
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) + (((/*uint*/int)shorts[1]) & 0xFFFFFFFFL));
        msAssert.areEqual("16385", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) - (((/*uint*/int)shorts[2]) & 0xFFFFFFFFL));
        msAssert.areEqual("16382", msUInt.toString(uintValue));
        //arithmetic scaling
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) * (((/*uint*/int)shorts[1]) & 0xFFFFFFFFL));
        msAssert.areEqual("32764", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) % (((/*uint*/int)shorts[3]) & 0xFFFFFFFFL));
        msAssert.areEqual("14", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) / (((/*uint*/int)shorts[1]) & 0xFFFFFFFFL));
        msAssert.areEqual("7", msUInt.toString(uintValue));
        //bitwise
        uintValue &= (/*uint*/int)shorts[1];
        msAssert.areEqual("2", msUInt.toString(uintValue));
        uintValue |= (/*uint*/int)shorts[1];
        msAssert.areEqual("2", msUInt.toString(uintValue));
        uintValue ^= (/*uint*/int)shorts[3];
        msAssert.areEqual("48", msUInt.toString(uintValue));
        //bitwise shift
        uintValue = (/*uint*/int)(uintValue >>> shorts[1]);
        msAssert.areEqual("12", msUInt.toString(uintValue));
        uintValue <<= shorts[1];
        msAssert.areEqual("48", msUInt.toString(uintValue));
    }

    @Test
    public void testUShortCompoundAssignment()
    {
        //arithmetic addition
        /*uint*/int uintValue = (/*uint*/int)((ushorts[4] & 0xFFFF) / 2);
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) + (ushorts[1] & 0xFFFF));
        msAssert.areEqual("32769", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) - (ushorts[2] & 0xFFFF));
        msAssert.areEqual("32766", msUInt.toString(uintValue));
        //arithmetic scaling
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) * (ushorts[1] & 0xFFFF));
        msAssert.areEqual("65532", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) % (ushorts[3] & 0xFFFF));
        msAssert.areEqual("32", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) / (ushorts[1] & 0xFFFF));
        msAssert.areEqual("16", msUInt.toString(uintValue));
        //bitwise
        uintValue &= ushorts[1];
        msAssert.areEqual("0", msUInt.toString(uintValue));
        uintValue |= ushorts[1];
        msAssert.areEqual("2", msUInt.toString(uintValue));
        uintValue ^= ushorts[3];
        msAssert.areEqual("48", msUInt.toString(uintValue));
        //bitwise shift
        uintValue = (/*uint*/int)(uintValue >>> (ushorts[1] & 0xFFFF));
        msAssert.areEqual("12", msUInt.toString(uintValue));
        uintValue <<= ushorts[1];
        msAssert.areEqual("48", msUInt.toString(uintValue));
    }

    @Test
    public void testIntCompoundAssignment()
    {
        //arithmetic addition
        /*uint*/int uintValue = (/*uint*/int)(ints[4] / 2);
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) + (((/*uint*/int)ints[1]) & 0xFFFFFFFFL));
        msAssert.areEqual("1073741825", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) - (((/*uint*/int)ints[2]) & 0xFFFFFFFFL));
        msAssert.areEqual("1073741822", msUInt.toString(uintValue));
        //arithmetic scaling
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) * (((/*uint*/int)ints[1]) & 0xFFFFFFFFL));
        msAssert.areEqual("2147483644", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) % (((/*uint*/int)ints[3]) & 0xFFFFFFFFL));
        msAssert.areEqual("44", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) / (((/*uint*/int)ints[1]) & 0xFFFFFFFFL));
        msAssert.areEqual("22", msUInt.toString(uintValue));
        //bitwise
        uintValue &= (/*uint*/int)ints[1];
        msAssert.areEqual("2", msUInt.toString(uintValue));
        uintValue |= (/*uint*/int)ints[1];
        msAssert.areEqual("2", msUInt.toString(uintValue));
        uintValue ^= (/*uint*/int)ints[3];
        msAssert.areEqual("48", msUInt.toString(uintValue));
        //bitwise shift
        uintValue = (/*uint*/int)(uintValue >>> ints[1]);
        msAssert.areEqual("12", msUInt.toString(uintValue));
        uintValue <<= ints[1];
        msAssert.areEqual("48", msUInt.toString(uintValue));
    }

    @Test
    public void testUIntCompoundAssignment()
    {
        //arithmetic addition
        /*uint*/int uintValue = (/*uint*/int)(((uints[4] & 0xFFFFFFFFL) / 2));
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) + (uints[1] & 0xFFFFFFFFL));
        msAssert.areEqual("2147483649", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) - (uints[2] & 0xFFFFFFFFL));
        msAssert.areEqual("2147483646", msUInt.toString(uintValue));
        //arithmetic scaling
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) * (uints[1] & 0xFFFFFFFFL));
        msAssert.areEqual("4294967292", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) % (uints[3] & 0xFFFFFFFFL));
        msAssert.areEqual("42", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) / (uints[1] & 0xFFFFFFFFL));
        msAssert.areEqual("21", msUInt.toString(uintValue));
        //bitwise
        uintValue &= uints[1];
        msAssert.areEqual("0", msUInt.toString(uintValue));
        uintValue |= uints[1];
        msAssert.areEqual("2", msUInt.toString(uintValue));
        uintValue ^= uints[3];
        msAssert.areEqual("48", msUInt.toString(uintValue));
        //bitwise shift
        uintValue = (/*uint*/int)(uintValue >>> (int)(uints[1] & 0xFFFFFFFFL));
        msAssert.areEqual("12", msUInt.toString(uintValue));
        uintValue <<= (int)(uints[1] & 0xFFFFFFFFL);
        msAssert.areEqual("48", msUInt.toString(uintValue));
    }

    @Test
    public void testLongCompoundAssignment()
    {
        //arithmetic addition
        /*uint*/int uintValue = (/*uint*/int)(longs[4] / 2);
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) + (((/*uint*/int)longs[1]) & 0xFFFFFFFFL));
        msAssert.areEqual("1", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) - (((/*uint*/int)longs[2]) & 0xFFFFFFFFL));
        msAssert.areEqual("4294967294", msUInt.toString(uintValue));
        //arithmetic scaling
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) * (((/*uint*/int)longs[1]) & 0xFFFFFFFFL));
        msAssert.areEqual("4294967292", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) % (((/*uint*/int)longs[3]) & 0xFFFFFFFFL));
        msAssert.areEqual("42", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) / (((/*uint*/int)longs[1]) & 0xFFFFFFFFL));
        msAssert.areEqual("21", msUInt.toString(uintValue));
        //bitwise
        uintValue &= (/*uint*/int)longs[1];
        msAssert.areEqual("0", msUInt.toString(uintValue));
        uintValue |= (/*uint*/int)longs[1];
        msAssert.areEqual("2", msUInt.toString(uintValue));
        uintValue ^= (/*uint*/int)longs[3];
        msAssert.areEqual("48", msUInt.toString(uintValue));
        //bitwise shift
        uintValue = (/*uint*/int)(uintValue >>> (int)longs[1]);
        msAssert.areEqual("12", msUInt.toString(uintValue));
        uintValue <<= (int)longs[1];
        msAssert.areEqual("48", msUInt.toString(uintValue));
    }

    @Test
    public void testFloatCompoundAssignment()
    {
        //arithmetic addition
        /*uint*/int uintValue = (/*uint*/int)(Byte.MAX_VALUE);
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) + (((/*uint*/int)floats[1]) & 0xFFFFFFFFL));
        msAssert.areEqual("129", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) - (((/*uint*/int)floats[2]) & 0xFFFFFFFFL));
        msAssert.areEqual("126", msUInt.toString(uintValue));
        //arithmetic scaling
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) * (((/*uint*/int)floats[1]) & 0xFFFFFFFFL));
        msAssert.areEqual("252", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) % (((/*uint*/int)floats[3]) & 0xFFFFFFFFL));
        msAssert.areEqual("2", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) / (((/*uint*/int)floats[1]) & 0xFFFFFFFFL));
        msAssert.areEqual("1", msUInt.toString(uintValue));
        //bitwise
        uintValue &= (/*uint*/int)floats[1];
        msAssert.areEqual("0", msUInt.toString(uintValue));
        uintValue |= (/*uint*/int)floats[1];
        msAssert.areEqual("2", msUInt.toString(uintValue));
        uintValue ^= (/*uint*/int)floats[3];
        msAssert.areEqual("48", msUInt.toString(uintValue));
        //bitwise shift
        uintValue = (/*uint*/int)(uintValue >>> (int)floats[1]);
        msAssert.areEqual("12", msUInt.toString(uintValue));
        uintValue <<= (int)floats[1];
        msAssert.areEqual("48", msUInt.toString(uintValue));
    }

    @Test
    public void testDoubleCompoundAssignment()
    {
        //arithmetic addition
        /*uint*/int uintValue = (/*uint*/int)(Byte.MAX_VALUE);
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) + (((/*uint*/int)doubles[1]) & 0xFFFFFFFFL));
        msAssert.areEqual("129", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) - (((/*uint*/int)doubles[2]) & 0xFFFFFFFFL));
        msAssert.areEqual("126", msUInt.toString(uintValue));
        //arithmetic scaling
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) * (((/*uint*/int)doubles[1]) & 0xFFFFFFFFL));
        msAssert.areEqual("252", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) % (((/*uint*/int)doubles[3]) & 0xFFFFFFFFL));
        msAssert.areEqual("2", msUInt.toString(uintValue));
        uintValue = (/*uint*/int)((uintValue & 0xFFFFFFFFL) / (((/*uint*/int)doubles[1]) & 0xFFFFFFFFL));
        msAssert.areEqual("1", msUInt.toString(uintValue));
        //bitwise
        uintValue &= (/*uint*/int)doubles[1];
        msAssert.areEqual("0", msUInt.toString(uintValue));
        uintValue |= (/*uint*/int)doubles[1];
        msAssert.areEqual("2", msUInt.toString(uintValue));
        uintValue ^= (/*uint*/int)doubles[3];
        msAssert.areEqual("48", msUInt.toString(uintValue));
        //bitwise shift
        uintValue = (/*uint*/int)(uintValue >>> (int)doubles[1]);
        msAssert.areEqual("12", msUInt.toString(uintValue));
        uintValue <<= (int)doubles[1];
        msAssert.areEqual("48", msUInt.toString(uintValue));
    }

    private /*final*/ byte[] sbytes = { 1, 2, 3, 50, Byte.MAX_VALUE };
    private /*final*/ byte[] bytes = { (byte) 1, (byte) 2, (byte) 3, (byte) 50, msByte.MAX_VALUE };
    private /*final*/ short[] shorts = { 1, 2, 3, 50, Short.MAX_VALUE };
    private /*final*/ /*ushort*/short[] ushorts = { (short) 1, (short) 2, (short) 3, (short) 50, msUShort.MAX_VALUE };
    private /*final*/ int[] ints = { 1, 2, 3, 50, Integer.MAX_VALUE };
    private /*final*/ /*uint*/int[] uints = { 1, 2, 3, 50, msUInt.MAX_VALUE, msUInt.MIN_VALUE };
    private /*final*/ long[] longs = { 1L, 2L, 3L, 50, Long.MAX_VALUE };
    private /*final*/ float[] floats = { 1.0F, 2.0F, 3.0F, 50f };
    private /*final*/ double[] doubles = { 1.0, 2.0, 3.0, 50.0 };
}

