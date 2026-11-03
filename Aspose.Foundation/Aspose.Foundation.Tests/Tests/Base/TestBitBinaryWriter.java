// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/03/2016 by Ilya Navrotskiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.Convert;
import com.aspose.ms.System.IO.MemoryStream;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.BitConverter;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests bit binary writer.
/// </summary>
@Test
public class TestBitBinaryWriter
{
    /// <summary>
    /// Tests writing of arbitrary number of bits.
    /// </summary>
    // 1 bit.
    @Test (dataProvider = "testWriteArbitraryBitsDataProvider")
    // 5 bits.
    // 8 bits.
    // 9 bits.
    // 16 bits.
    public void testWriteArbitraryBits(boolean[] data, byte[] expectedResultWithMsbOrdering, String message) throws Exception
    {
        verifyResultByteArray(data, expectedResultWithMsbOrdering, message);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testWriteArbitraryBitsDataProvider")
	public static Object[][] testWriteArbitraryBitsDataProvider() throws Exception
	{
		return new Object[][]
		{
			{new boolean[] { false },  new byte[] { (byte) 0x00 },  "1 bit: 0x00."},
			{new boolean[] { true },  new byte[] { (byte) 0x80 },  "1 bit: 0x80."},
			{new boolean[] { false, true, false, true, false },  new byte[] { (byte) 0x50 },  "5 bits: 0x50."},
			{new boolean[] { true, false, true, false, true },  new byte[] { (byte) 0xA8 },  "5 bits: 0xA8."},
			{new boolean[] { false, false, true, true, false, true, false, true },  new byte[] { (byte) 0x35 },  "8 bits: 0x35."},
			{new boolean[] { true, true, false, false, true, false, true, false },  new byte[] { (byte) 0xCA },  "8 bits: 0xCA."},
			{new boolean[] { true, true, true, true, false, false, true, false, true },  new byte[] { (byte) 0xF2, (byte) 0x80 },  "9 bits: 0xF2, 0x80."},
			{new boolean[] { true, true, false, false, true, false, true, false, false },  new byte[] { (byte) 0xCA, (byte) 0x00 },  "9 bits: 0xCA, 0x00."},
			{new boolean[] { true, true, true, true, false, false, true, false,
        false, false, true, true, false, false, false, true },  new byte[] { (byte) 0xF2, (byte) 0x31 },  "16 bits: 0xF2, 0x31."},
			{new boolean[] { false, true, true, true, false, false, true, true,
        true, false, true, true, false, false, false, false },  new byte[] { (byte) 0x73, (byte) 0xB0 },  "16 bits: 0x73, 0xB0."},
		};
	}

    /// <summary>
    /// Tests writing of various bit values. 
    /// </summary>
    @Test (dataProvider = "testWriteValueDataProvider")
    public void testWriteValue(String value) throws Exception
    {
        verifyWriteValue(value);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testWriteValueDataProvider")
	public static Object[][] testWriteValueDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"0"},
			{"1"},
			{"001"},
			{"000"},
			{"000000001"},
			{"000000000"},
			{"000011001"},
			{"101010101"},
			{"1111111101010101"},
			{"11111111010101011111111111"},
			{"001111111101010101111111111100"},
			{"1011111111010101011111111111001"},
		};
	}

    /// <summary>
    /// Verifies writing value from binary string.
    /// </summary>
    private static void verifyWriteValue(String bitsValue) throws Exception
    {
        int expectedValue = Convert.toInt32(bitsValue, 2);
        verifyWriteValue(expectedValue, bitsValue.length());
    }

    /// <summary>
    /// Verifies writing of arbitrary number of bits.
    /// </summary>
    private static void verifyWriteValue(int expectedValue, int bitsCount) throws Exception
    {
        MemoryStream stream = new MemoryStream();
        try /*JAVA: was using*/
        {
            // Msb ordering.
            BitBinaryWriter writer = new BitBinaryWriter(stream, true);
            writer.writeValue(expectedValue, bitsCount);
            writer.flush();

            stream.setPosition(0);
            /*uint*/int value = readValue(stream, bitsCount, true);
            msAssert.areEqual(value, expectedValue);

            // Lsb ordering.
            stream.setPosition(0);
            writer = new BitBinaryWriter(stream, false);
            writer.writeValue(expectedValue, bitsCount);
            writer.flush();

            stream.setPosition(0);
            value = readValue(stream, bitsCount, false);
            msAssert.areEqual(value, expectedValue);
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Reads original value from the stream.
    /// </summary>
    private static /*uint*/int readValue(Stream stream, int bitsCount, boolean isMsb) throws Exception
    {
        int bytesCount = (bitsCount - 1) / 8 + 1;
        byte[] valueBytes = new byte[4];
        stream.read(valueBytes, 4 - bytesCount, bytesCount);

        if (!isMsb)
            for (int i = 0; i < bytesCount; i++)
                valueBytes[3 - i] = BitUtil.reverseBits(valueBytes[3 - i]);

        /*uint*/int value = BitConverter.toUInt32(valueBytes, 0);
        value = BitUtil.swapUInt32(value);

        int shiftCount = (8 - (bitsCount % 8)) & 0x7;
        value = (/*uint*/int)(value >>> shiftCount);

        return value;
    }

    /// <summary>
    /// Verifies writing of bool array using both MSB and LSB ordering.
    /// </summary>
    private static void verifyResultByteArray(boolean[] data, byte[] expectedResultWithMsbOrdering, String message) throws Exception
    {
        // Verify MSB byte ordering.
        verifyMsbResultByteArray(data, expectedResultWithMsbOrdering, message);
        // Verify LSB byte ordering.
        verifyLsbResultByteArray(data, expectedResultWithMsbOrdering, message);
    }

    /// <summary>
    /// Verifies writing of bool array using MSB order.
    /// </summary>
    private static void verifyMsbResultByteArray(boolean[] data, byte[] expectedResultWithMsbOrdering, String message) throws Exception
    {
        MemoryStream stream = new MemoryStream();
        try /*JAVA: was using*/
        {
            BitBinaryWriter msbWriter = new BitBinaryWriter(stream, true);
            for (boolean bit : data)
                msbWriter.writeBit(bit);
            msbWriter.flush();

            msAssert.areEqual(expectedResultWithMsbOrdering, stream.toArray(), message);
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Verifies writing of bool array using LSB order.
    /// </summary>
    private static void verifyLsbResultByteArray(boolean[] data, byte[] expectedResultWithMsbOrdering, String message) throws Exception
    {
        MemoryStream stream = new MemoryStream();
        try /*JAVA: was using*/
        {
            BitBinaryWriter msbWriter = new BitBinaryWriter(stream, false);
            for (boolean bit : data)
                msbWriter.writeBit(bit);
            msbWriter.flush();

            byte[] expectedResultWithLsbOrdering = getLsbArray(expectedResultWithMsbOrdering);
            msAssert.areEqual(expectedResultWithLsbOrdering, stream.toArray(), message);
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Gets LSB array from the specified MSB array.
    /// </summary>
    private static byte[] getLsbArray(byte[] msbArray)
    {
        byte[] lsbArray = new byte[msbArray.length];

        for (int i = 0; i < msbArray.length; i++)
            lsbArray[i] = BitUtil.reverseBits(msbArray[i]);

        return lsbArray;
    }
}
