// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/05/2024 by Denis Panov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.annotations.Test;
import com.aspose.ms.System.Text.Encoding;
import org.testng.annotations.DataProvider;


public class TestCrc32
{
    @Test (dataProvider = "testCrcForPartDataProvider")
    public void testCrcForPart(int index, int count, /*uint*/int expected)
    {
        /*uint*/int result = Crc32.computeCRC(gBuffer, index, count);
        msAssert.areEqual(expected, result);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testCrcForPartDataProvider")
	public static Object[][] testCrcForPartDataProvider()
	{
		return new Object[][]
		{
			{3,  11,  (int) 0xA0D118A0L},
			{9,  5,  (int) 0xDEF4CFE9L},
			{70,  6,  (int) 0xF373B43BL},
		};
	}

    @Test
    public void testCrcByteArray()
    {
        /*uint*/int result = Crc32.computeCRC(gBuffer);
        msAssert.areEqual((int) 0xCBE25E99L, result);
    }

    @Test
    public void testCrcEmptyArray()
    {
        /*uint*/int result = Crc32.computeCRC(new byte[] { });
        msAssert.areEqual(0, result);
    }

    @Test
    public void testCrcString()
    {
        byte[] bytesOfString = Encoding.getASCII().getBytes("Test string");
        /*uint*/int result = Crc32.computeCRC(bytesOfString);
        msAssert.areEqual((int) 0x95DB9A92L, result);
    }

    static byte[] gBuffer = new byte[] {
        (byte) 0x49, (byte) 0x74, (byte) 0x20, (byte) 0x69, (byte) 0x73, (byte) 0x20, (byte) 0x61, (byte) 0x20, (byte) 0x6D, (byte) 0x69, (byte) 0x73, (byte) 0x74, (byte) 0x61, (byte) 0x6B, (byte) 0x65, (byte) 0x20, (byte) 0x74, (byte) 0x6F, (byte) 0x20,
        (byte) 0x74, (byte) 0x68, (byte) 0x69, (byte) 0x6E, (byte) 0x6B, (byte) 0x20, (byte) 0x79, (byte) 0x6F, (byte) 0x75, (byte) 0x20, (byte) 0x63, (byte) 0x61, (byte) 0x6E, (byte) 0x20, (byte) 0x73, (byte) 0x6F, (byte) 0x6C, (byte) 0x76, (byte) 0x65,
        (byte) 0x20, (byte) 0x61, (byte) 0x6E, (byte) 0x79, (byte) 0x20, (byte) 0x6D, (byte) 0x61, (byte) 0x6A, (byte) 0x6F, (byte) 0x72, (byte) 0x20, (byte) 0x70, (byte) 0x72, (byte) 0x6F, (byte) 0x62, (byte) 0x6C, (byte) 0x65, (byte) 0x6D, (byte) 0x73,
        (byte) 0x20, (byte) 0x6A, (byte) 0x75, (byte) 0x73, (byte) 0x74, (byte) 0x20, (byte) 0x77, (byte) 0x69, (byte) 0x74, (byte) 0x68, (byte) 0x20, (byte) 0x70, (byte) 0x6F, (byte) 0x74, (byte) 0x61, (byte) 0x74, (byte) 0x6F, (byte) 0x65, (byte) 0x73
    };
}

