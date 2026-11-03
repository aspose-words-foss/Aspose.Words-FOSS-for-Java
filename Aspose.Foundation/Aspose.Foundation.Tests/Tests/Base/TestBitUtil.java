// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/08/2013 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.annotations.DataProvider;


@Test
public class TestBitUtil
{
    @Test (dataProvider = "testSwapInt64DataProvider")
    public void testSwapInt64(long expectedValue, long value)
    {
        msAssert.areEqual(expectedValue, BitUtil.swapInt64(value));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSwapInt64DataProvider")
	public static Object[][] testSwapInt64DataProvider()
	{
		return new Object[][]
		{
			{(long)0x0807060504030201L,  (long)0x0102030405060708L},
			{(long)-9007199254740992000L,  (long)0x0000000000000083},
			{(long)0x0000000000000083,  (long)-9007199254740992000L},
		};
	}

    @Test (dataProvider = "testSwapUInt32DataProvider")
    public void testSwapUInt32(/*uint*/int expectedValue, /*uint*/int value)
    {
        msAssert.areEqual(expectedValue, BitUtil.swapUInt32(value));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSwapUInt32DataProvider")
	public static Object[][] testSwapUInt32DataProvider()
	{
		return new Object[][]
		{
			{(/*uint*/int)0x04030201,  (/*uint*/int)0x01020304},
			{(/*uint*/int)0xf4030201L,  (/*uint*/int)0x010203f4},
			{(/*uint*/int)0x040302f1,  (/*uint*/int)0xf1020304L},
		};
	}

    @Test (dataProvider = "testSwapInt32DataProvider")
    public void testSwapInt32(int expectedValue, int value)
    {
        msAssert.areEqual(expectedValue, BitUtil.swapInt32(value));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSwapInt32DataProvider")
	public static Object[][] testSwapInt32DataProvider()
	{
		return new Object[][]
		{
			{(int)0x04030201,  (int)0x01020304},
			{(int)-2097152000,  (int)0x00000083},
			{(int)0x00000083,  (int)-2097152000},
		};
	}

    @Test (dataProvider = "testSwapUInt16DataProvider")
    public void testSwapUInt16(/*ushort*/short expectedValue, /*ushort*/short value)
    {
        msAssert.areEqual(expectedValue, BitUtil.swapUInt16(value));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSwapUInt16DataProvider")
	public static Object[][] testSwapUInt16DataProvider()
	{
		return new Object[][]
		{
			{(/*ushort*/short)0x0201,  (/*ushort*/short)0x0102},
			{(/*ushort*/short)0xf201,  (/*ushort*/short)0x01f2},
			{(/*ushort*/short)0x02f1,  (/*ushort*/short)0xf102},
		};
	}

    @Test (dataProvider = "testSwapInt16DataProvider")
    public void testSwapInt16(short expectedValue, short value)
    {
        msAssert.areEqual(expectedValue, BitUtil.swapInt16(value));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSwapInt16DataProvider")
	public static Object[][] testSwapInt16DataProvider()
	{
		return new Object[][]
		{
			{(short)0x0201,  (short)0x0102},
			{(short)-32000,  (short)0x0083},
			{(short)0x0083,  (short)-32000},
		};
	}

    @Test (dataProvider = "testCountBitsInt32DataProvider")
    public void testCountBitsInt32(int expectedCount, int value)
    {
        msAssert.areEqual(expectedCount, BitUtil.countBitsInt32(value));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testCountBitsInt32DataProvider")
	public static Object[][] testCountBitsInt32DataProvider()
	{
		return new Object[][]
		{
			{4,  0x0000000f},
			{11,  0x1050e20f},
			{32,  /*unchecked*/((int)(int) 0xffffffffL)},
			{0,  0x0000000000000000},
		};
	}

    @Test (dataProvider = "testCountBitsInt64DataProvider")
    public void testCountBitsInt64(int expectedCount, long value)
    {
        msAssert.areEqual(expectedCount, BitUtil.countBitsInt64(value));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testCountBitsInt64DataProvider")
	public static Object[][] testCountBitsInt64DataProvider()
	{
		return new Object[][]
		{
			{44,  /*unchecked*/((long)0xf0f1fff23fec07ffL)},
			{3,  0x0010000080000200L},
		};
	}

    @Test
    public void testReverseBits()
    {
        byte b = (byte) 0xC1;
        byte result = BitUtil.reverseBits(b);
        msAssert.areEqual(0x83, result);
    }

    /// <summary>
    /// Checks number of bits in various values.
    /// </summary>
    @Test (dataProvider = "testBitsUsedDataProvider")
    public void testBitsUsed(int expectedCount, int value)
    {
        msAssert.areEqual(expectedCount, BitUtil.bitsUsed(value));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testBitsUsedDataProvider")
	public static Object[][] testBitsUsedDataProvider()
	{
		return new Object[][]
		{
			{1,  0x0},
			{1,  0x1},
			{2,  0x2},
			{3,  0x4},
			{4,  0x8},
			{5,  0x10},
			{6,  0x20},
			{7,  0x40},
			{8,  0x80},
			{10,  0x200},
			{12,  0x800},
			{14,  0x2000},
			{16,  0x8000},
			{24,  0x800000},
			{29,  0x10000000},
			{31,  0x40000000},
			{31,  0x7fffffff},
			{32,  /*unchecked*/((int)(int) 0xffffffffL)},
			{32,  -500},
		};
	}

    @Test (dataProvider = "testTruncateBitsDataProvider")   // 1010101 -> 0000000
    public void testTruncateBits(int value, int position, int expectedResult)
    {
        msAssert.areEqual(expectedResult, BitUtil.truncateBits(value, position));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testTruncateBitsDataProvider")
	public static Object[][] testTruncateBitsDataProvider()
	{
		return new Object[][]
		{
			{85,  0,  0},
			{85,  1,  1},
			{85,  3,  5},
			{85,  5,  21},
			{85,  7,  85},
			{63,  0,  0},
			{63,  1,  1},
			{63,  3,  7},
			{63,  5,  31},
			{63,  7,  63},
			{9,  0,  0},
			{9,  1,  1},
			{9,  3,  1},
			{9,  5,  9},
			{9,  7,  9},
		};
	}
}

