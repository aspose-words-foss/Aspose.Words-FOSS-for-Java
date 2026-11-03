// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/03/2017 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msUShort;
import com.aspose.ms.System.msByte;
import com.aspose.ms.System.msUInt;


@Test
public class TestMsUShort
{
    @Test
    public void testUnaryOperators()
    {
        /*ushort*/short ushortValue = (/*ushort*/short)65525;
        /*ushort*/short ushortValue2 = (/*ushort*/short)10;
        msAssert.areEqual(65525, +(ushortValue & 0xFFFF));
        msAssert.areEqual(-65525, -(ushortValue & 0xFFFF));
        msAssert.areEqual(65525, (ushortValue++) & 0xFFFF);
        msAssert.areEqual(65527, (++ushortValue) & 0xFFFF);
        msAssert.areEqual(65527, (ushortValue--) & 0xFFFF);
        msAssert.areEqual(65525, (--ushortValue) & 0xFFFF);
        msAssert.areEqual(-65526, ~(ushortValue & 0xFFFF));
        msAssert.areEqual(-11, ~(ushortValue2 & 0xFFFF));
    }

    @Test
    public void testUnaryBitwiseOperator()
    {
        /*ushort*/short maxValue = msUShort.MAX_VALUE;
        /*ushort*/short minValue = msUShort.MIN_VALUE;

        msAssert.areEqual(-65536, ~(maxValue & 0xFFFF));
        msAssert.areEqual(-1, ~(minValue & 0xFFFF));

        msAssert.areEqual(-65537, ~((maxValue & 0xFFFF) + 1));
        msAssert.areEqual(-65535, ~((maxValue & 0xFFFF) - 1));

        msAssert.areEqual(-2, ~((minValue & 0xFFFF) + 1));
        msAssert.areEqual(0, ~((minValue & 0xFFFF) - 1));

        msAssert.areEqual(-2, ~(ushorts[0] & 0xFFFF));
        msAssert.areEqual(-3, ~(ushorts[1] & 0xFFFF));
        msAssert.areEqual(-4, ~(ushorts[2] & 0xFFFF));
        msAssert.areEqual(-51, ~(ushorts[3] & 0xFFFF));
    }

    @Test
    public void testBinaryBitwiseOperator()
    {
        /*ushort*/short ushortValue = (/*ushort*/short)65525;
        /*ushort*/short ushortValue2 = (/*ushort*/short)11;

        msAssert.areEqual(4085, (ushortValue & 0xFFFF) & 0x0FFF);
        msAssert.areEqual(1, (ushortValue & 0xFFFF) & (ushortValue2 & 0xFFFF));

        msAssert.areEqual(65535, (ushortValue & 0xFFFF) | 0x0FFF);
        msAssert.areEqual(65535, (ushortValue & 0xFFFF) | (ushortValue2 & 0xFFFF));

        msAssert.areEqual(61450, (ushortValue & 0xFFFF) ^ 0x0FFF);
        msAssert.areEqual(65534, (ushortValue & 0xFFFF) ^ (ushortValue2 & 0xFFFF));

        msAssert.areEqual(3, (ushortValue & 0xFFFF) >>> 14);
        msAssert.areEqual(45056, (ushortValue2 & 0xFFFF) << 12);
    }

    @Test
    public void testIncrementOperators()
    {
        /*ushort*/short ushortValue = ushorts[4];
        ushortValue++;
        msAssert.areEqual((/*ushort*/short)0, ushortValue);
        ushortValue--;
        msAssert.areEqual((/*ushort*/short)65535, ushortValue);
        ++ushortValue;
        msAssert.areEqual((/*ushort*/short)0, ushortValue);
        --ushortValue;
        msAssert.areEqual((/*ushort*/short)65535, ushortValue);
        ushortValue = (/*ushort*/short)shorts[4];
        ushortValue++;
        msAssert.areEqual((/*ushort*/short)32768, ushortValue);
        ushortValue--;
        msAssert.areEqual((/*ushort*/short)32767, ushortValue);
        ++ushortValue;
        msAssert.areEqual((/*ushort*/short)32768, ushortValue);
        --ushortValue;
        msAssert.areEqual((/*ushort*/short)32767, ushortValue);
    }

    @Test
    public void testShiftOperators()
    {
        /*ushort*/short ushortValue = ushorts[4];
        msAssert.areEqual(1048560, (ushortValue & 0xFFFF) << 4);
        msAssert.areEqual(16776960, (ushortValue & 0xFFFF) << 8);
        msAssert.areEqual(-65536, (ushortValue & 0xFFFF) << 16);
        msAssert.areEqual(-16777216, (ushortValue & 0xFFFF) << 24);
        msAssert.areEqual(65535, (ushortValue & 0xFFFF) << 32);
        msAssert.areEqual(16776960, (ushortValue & 0xFFFF) << 40);
        msAssert.areEqual(-65536, (ushortValue & 0xFFFF) << 48);
        msAssert.areEqual(-16777216, (ushortValue & 0xFFFF) << 56);
        msAssert.areEqual(4095, (ushortValue & 0xFFFF) >>> 4);

        msAssert.areEqual(262140, (ushortValue & 0xFFFF) << sbytes[1]);
        msAssert.areEqual(262140, (ushortValue & 0xFFFF) << (bytes[1] & 0xFF));
        msAssert.areEqual(262140, (ushortValue & 0xFFFF) << shorts[1]);
        msAssert.areEqual(262140, (ushortValue & 0xFFFF) << (ushorts[1] & 0xFFFF));
        msAssert.areEqual(262140, (ushortValue & 0xFFFF) << ints[1]);

        msAssert.areEqual(16383, (ushortValue & 0xFFFF) >>> sbytes[1]);
        msAssert.areEqual(16383, (ushortValue & 0xFFFF) >>> (bytes[1] & 0xFF));
        msAssert.areEqual(16383, (ushortValue & 0xFFFF) >>> shorts[1]);
        msAssert.areEqual(16383, (ushortValue & 0xFFFF) >>> (ushorts[1] & 0xFFFF));
        msAssert.areEqual(16383, (ushortValue & 0xFFFF) >>> ints[1]);
    }

    @Test
    public void testAssignment()
    {
        /*ushort*/short ushortValue = (/*ushort*/short)sbytes[2];
        msAssert.areEqual("3", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)(bytes[2] & 0xFF);
        msAssert.areEqual("3", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)shorts[2];
        msAssert.areEqual("3", msUShort.toString(ushortValue));
        ushortValue = ushorts[2];
        msAssert.areEqual("3", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)ints[2];
        msAssert.areEqual("3", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)(uints[2] & 0xFFFFFFFFL);
        msAssert.areEqual("3", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)longs[2];
        msAssert.areEqual("3", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)floats[2];
        msAssert.areEqual("3", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)doubles[2];
        msAssert.areEqual("3", msUShort.toString(ushortValue));
    }

    @Test
    public void testSByteCompoundAssignment()
    {
        //arithmetic addition
        /*ushort*/short ushortValue = (/*ushort*/short)(sbytes[4] / 2);
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) + (((/*ushort*/short)sbytes[1]) & 0xFFFF));
        msAssert.areEqual("65", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) - (((/*ushort*/short)sbytes[2]) & 0xFFFF));
        msAssert.areEqual("62", msUShort.toString(ushortValue));
        //arithmetic scaling
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) * (((/*ushort*/short)sbytes[1]) & 0xFFFF));
        msAssert.areEqual("124", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) % (((/*ushort*/short)sbytes[3]) & 0xFFFF));
        msAssert.areEqual("24", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) / (((/*ushort*/short)sbytes[1]) & 0xFFFF));
        msAssert.areEqual("12", msUShort.toString(ushortValue));
        //bitwise
        ushortValue &= (/*ushort*/short)sbytes[1];
        msAssert.areEqual("0", msUShort.toString(ushortValue));
        ushortValue |= (/*ushort*/short)sbytes[1];
        msAssert.areEqual("2", msUShort.toString(ushortValue));
        ushortValue ^= (/*ushort*/short)sbytes[3];
        msAssert.areEqual("48", msUShort.toString(ushortValue));
        //bitwise shift
        ushortValue = (/*ushort*/short)(ushortValue >>> (((/*ushort*/short)sbytes[1]) & 0xFFFF));
        msAssert.areEqual("12", msUShort.toString(ushortValue));
        ushortValue <<= (/*ushort*/short)sbytes[1];
        msAssert.areEqual("48", msUShort.toString(ushortValue));
    }

    @Test
    public void testByteCompoundAssignment()
    {
        //arithmetic addition
        /*ushort*/short ushortValue = (/*ushort*/short)((bytes[4] & 0xFF) / 2);
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) + (bytes[1] & 0xFF));
        msAssert.areEqual("129", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) - (bytes[2] & 0xFF));
        msAssert.areEqual("126", msUShort.toString(ushortValue));
        //arithmetic scaling
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) * (bytes[1] & 0xFF));
        msAssert.areEqual("252", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) % (bytes[3] & 0xFF));
        msAssert.areEqual("2", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) / (bytes[1] & 0xFF));
        msAssert.areEqual("1", msUShort.toString(ushortValue));
        //bitwise
        ushortValue &= ((bytes[1]) & 0xFF);
        msAssert.areEqual("0", msUShort.toString(ushortValue));
        ushortValue |= ((bytes[1]) & 0xFF);
        msAssert.areEqual("2", msUShort.toString(ushortValue));
        ushortValue ^= ((bytes[3]) & 0xFF);
        msAssert.areEqual("48", msUShort.toString(ushortValue));
        //bitwise shift
        ushortValue = (/*ushort*/short)(ushortValue >>> (bytes[1] & 0xFF));
        msAssert.areEqual("12", msUShort.toString(ushortValue));
        ushortValue <<= ((bytes[1]) & 0xFF);
        msAssert.areEqual("48", msUShort.toString(ushortValue));
    }

    @Test
    public void testShortCompoundAssignment()
    {
        //arithmetic addition
        /*ushort*/short ushortValue = (/*ushort*/short)(shorts[4] / 2);
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) + (((/*ushort*/short)shorts[1]) & 0xFFFF));
        msAssert.areEqual("16385", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) - (((/*ushort*/short)shorts[2]) & 0xFFFF));
        msAssert.areEqual("16382", msUShort.toString(ushortValue));
        //arithmetic scaling
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) * (((/*ushort*/short)shorts[1]) & 0xFFFF));
        msAssert.areEqual("32764", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) % (((/*ushort*/short)shorts[3]) & 0xFFFF));
        msAssert.areEqual("14", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) / (((/*ushort*/short)shorts[1]) & 0xFFFF));
        msAssert.areEqual("7", msUShort.toString(ushortValue));
        //bitwise
        ushortValue &= (/*ushort*/short)shorts[1];
        msAssert.areEqual("2", msUShort.toString(ushortValue));
        ushortValue |= (/*ushort*/short)shorts[1];
        msAssert.areEqual("2", msUShort.toString(ushortValue));
        ushortValue ^= (/*ushort*/short)shorts[3];
        msAssert.areEqual("48", msUShort.toString(ushortValue));
        //bitwise shift
        ushortValue = (/*ushort*/short)(ushortValue >>> shorts[1]);
        msAssert.areEqual("12", msUShort.toString(ushortValue));
        ushortValue <<= shorts[1];
        msAssert.areEqual("48", msUShort.toString(ushortValue));
    }

    @Test
    public void testUShortCompoundAssignment()
    {
        //arithmetic addition
        /*ushort*/short ushortValue = (/*ushort*/short)((ushorts[4] & 0xFFFF) / 2);
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) + (ushorts[1] & 0xFFFF));
        msAssert.areEqual("32769", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) - (ushorts[2] & 0xFFFF));
        msAssert.areEqual("32766", msUShort.toString(ushortValue));
        //arithmetic scaling
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) * (ushorts[1] & 0xFFFF));
        msAssert.areEqual("65532", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) % (ushorts[3] & 0xFFFF));
        msAssert.areEqual("32", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) / (ushorts[1] & 0xFFFF));
        msAssert.areEqual("16", msUShort.toString(ushortValue));
        //bitwise
        ushortValue &= ushorts[1];
        msAssert.areEqual("0", msUShort.toString(ushortValue));
        ushortValue |= ushorts[1];
        msAssert.areEqual("2", msUShort.toString(ushortValue));
        ushortValue ^= ushorts[3];
        msAssert.areEqual("48", msUShort.toString(ushortValue));
        //bitwise shift
        ushortValue = (/*ushort*/short)(ushortValue >>> (ushorts[1] & 0xFFFF));
        msAssert.areEqual("12", msUShort.toString(ushortValue));
        ushortValue <<= ushorts[1];
        msAssert.areEqual("48", msUShort.toString(ushortValue));
    }

    @Test
    public void testIntCompoundAssignment()
    {
        //arithmetic addition
        /*ushort*/short ushortValue = (/*ushort*/short)(ints[4] / 2);
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) + (((/*ushort*/short)ints[1]) & 0xFFFF));
        msAssert.areEqual("1", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) - (((/*ushort*/short)ints[2]) & 0xFFFF));
        msAssert.areEqual("65534", msUShort.toString(ushortValue));
        //arithmetic scaling
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) * (((/*ushort*/short)ints[1]) & 0xFFFF));
        msAssert.areEqual("65532", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) % (((/*ushort*/short)ints[3]) & 0xFFFF));
        msAssert.areEqual("32", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) / (((/*ushort*/short)ints[1]) & 0xFFFF));
        msAssert.areEqual("16", msUShort.toString(ushortValue));
        //bitwise
        ushortValue &= (/*ushort*/short)ints[1];
        msAssert.areEqual("0", msUShort.toString(ushortValue));
        ushortValue |= (/*ushort*/short)ints[1];
        msAssert.areEqual("2", msUShort.toString(ushortValue));
        ushortValue ^= (/*ushort*/short)ints[3];
        msAssert.areEqual("48", msUShort.toString(ushortValue));
        //bitwise shift
        ushortValue = (/*ushort*/short)(ushortValue >>> ints[1]);
        msAssert.areEqual("12", msUShort.toString(ushortValue));
        ushortValue <<= ints[1];
        msAssert.areEqual("48", msUShort.toString(ushortValue));
    }

    @Test
    public void testUIntCompoundAssignment()
    {
        //arithmetic addition
        /*ushort*/short ushortValue = (/*ushort*/short)((uints[4] & 0xFFFFFFFFL) / 2);
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) + (((/*ushort*/short)(uints[1] & 0xFFFFFFFFL)) & 0xFFFF));
        msAssert.areEqual("1", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) - (((/*ushort*/short)(uints[2] & 0xFFFFFFFFL)) & 0xFFFF));
        msAssert.areEqual("65534", msUShort.toString(ushortValue));
        //arithmetic scaling
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) * (((/*ushort*/short)(uints[1] & 0xFFFFFFFFL)) & 0xFFFF));
        msAssert.areEqual("65532", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) % (((/*ushort*/short)(uints[3] & 0xFFFFFFFFL)) & 0xFFFF));
        msAssert.areEqual("32", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) / (((/*ushort*/short)(uints[1] & 0xFFFFFFFFL)) & 0xFFFF));
        msAssert.areEqual("16", msUShort.toString(ushortValue));
        //bitwise
        ushortValue &= (/*ushort*/short)(uints[1] & 0xFFFFFFFFL);
        msAssert.areEqual("0", msUShort.toString(ushortValue));
        ushortValue |= (/*ushort*/short)(uints[1] & 0xFFFFFFFFL);
        msAssert.areEqual("2", msUShort.toString(ushortValue));
        ushortValue ^= (/*ushort*/short)(uints[3] & 0xFFFFFFFFL);
        msAssert.areEqual("48", msUShort.toString(ushortValue));
        //bitwise shift
        ushortValue = (/*ushort*/short)(ushortValue >>> (int)(uints[1] & 0xFFFFFFFFL));
        msAssert.areEqual("12", msUShort.toString(ushortValue));
        ushortValue <<= (int)(uints[1] & 0xFFFFFFFFL);
        msAssert.areEqual("48", msUShort.toString(ushortValue));
    }

    @Test
    public void testLongCompoundAssignment()
    {
        //arithmetic addition
        /*ushort*/short ushortValue = (/*ushort*/short)(longs[4] / 2);
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) + (((/*ushort*/short)longs[1]) & 0xFFFF));
        msAssert.areEqual("1", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) - (((/*ushort*/short)longs[2]) & 0xFFFF));
        msAssert.areEqual("65534", msUShort.toString(ushortValue));
        //arithmetic scaling
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) * (((/*ushort*/short)longs[1]) & 0xFFFF));
        msAssert.areEqual("65532", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) % (((/*ushort*/short)longs[3]) & 0xFFFF));
        msAssert.areEqual("32", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) / (((/*ushort*/short)longs[1]) & 0xFFFF));
        msAssert.areEqual("16", msUShort.toString(ushortValue));
        //bitwise
        ushortValue &= (/*ushort*/short)longs[1];
        msAssert.areEqual("0", msUShort.toString(ushortValue));
        ushortValue |= (/*ushort*/short)longs[1];
        msAssert.areEqual("2", msUShort.toString(ushortValue));
        ushortValue ^= (/*ushort*/short)longs[3];
        msAssert.areEqual("48", msUShort.toString(ushortValue));
        //bitwise shift
        ushortValue = (/*ushort*/short)(ushortValue >>> (int)longs[1]);
        msAssert.areEqual("12", msUShort.toString(ushortValue));
        ushortValue <<= (int)longs[1];
        msAssert.areEqual("48", msUShort.toString(ushortValue));
    }

    @Test
    public void testFloatCompoundAssignment()
    {
        //arithmetic addition
        /*ushort*/short ushortValue = (/*ushort*/short)(Byte.MAX_VALUE);
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) + (((/*ushort*/short)floats[1]) & 0xFFFF));
        msAssert.areEqual("129", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) - (((/*ushort*/short)floats[2]) & 0xFFFF));
        msAssert.areEqual("126", msUShort.toString(ushortValue));
        //arithmetic scaling
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) * (((/*ushort*/short)floats[1]) & 0xFFFF));
        msAssert.areEqual("252", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) % (((/*ushort*/short)floats[3]) & 0xFFFF));
        msAssert.areEqual("2", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) / (((/*ushort*/short)floats[1]) & 0xFFFF));
        msAssert.areEqual("1", msUShort.toString(ushortValue));
        //bitwise
        ushortValue &= (/*ushort*/short)floats[1];
        msAssert.areEqual("0", msUShort.toString(ushortValue));
        ushortValue |= (/*ushort*/short)floats[1];
        msAssert.areEqual("2", msUShort.toString(ushortValue));
        ushortValue ^= (/*ushort*/short)floats[3];
        msAssert.areEqual("48", msUShort.toString(ushortValue));
        //bitwise shift
        ushortValue = (/*ushort*/short)(ushortValue >>> (int)floats[1]);
        msAssert.areEqual("12", msUShort.toString(ushortValue));
        ushortValue <<= (int)floats[1];
        msAssert.areEqual("48", msUShort.toString(ushortValue));
    }

    @Test
    public void testDoubleCompoundAssignment()
    {
        //arithmetic addition
        /*ushort*/short ushortValue = (/*ushort*/short)(Byte.MAX_VALUE);
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) + (((/*ushort*/short)doubles[1]) & 0xFFFF));
        msAssert.areEqual("129", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) - (((/*ushort*/short)doubles[2]) & 0xFFFF));
        msAssert.areEqual("126", msUShort.toString(ushortValue));
        //arithmetic scaling
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) * (((/*ushort*/short)doubles[1]) & 0xFFFF));
        msAssert.areEqual("252", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) % (((/*ushort*/short)doubles[3]) & 0xFFFF));
        msAssert.areEqual("2", msUShort.toString(ushortValue));
        ushortValue = (/*ushort*/short)((ushortValue & 0xFFFF) / (((/*ushort*/short)doubles[1]) & 0xFFFF));
        msAssert.areEqual("1", msUShort.toString(ushortValue));
        //bitwise
        ushortValue &= (/*ushort*/short)doubles[1];
        msAssert.areEqual("0", msUShort.toString(ushortValue));
        ushortValue |= (/*ushort*/short)doubles[1];
        msAssert.areEqual("2", msUShort.toString(ushortValue));
        ushortValue ^= (/*ushort*/short)doubles[3];
        msAssert.areEqual("48", msUShort.toString(ushortValue));
        //bitwise shift
        ushortValue = (/*ushort*/short)(ushortValue >>> (int)doubles[1]);
        msAssert.areEqual("12", msUShort.toString(ushortValue));
        ushortValue <<= (int)doubles[1];
        msAssert.areEqual("48", msUShort.toString(ushortValue));
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

