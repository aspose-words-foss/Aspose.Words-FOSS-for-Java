// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2011 by Konstantin Kornilov
package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.IO.MemoryStream;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestBitBinaryReader
{
    @Test
    public void testReadBit() throws Exception
    {
        verifyResultBitArray(
            new byte[] { (byte) 0x35 },
            new boolean[] { false, false, true, true, false, true, false, true });

        verifyResultBitArray(
            new byte[] { (byte) 0xF2, (byte) 0x01 },
            new boolean[]
                {
                    true, true, true, true, false, false, true, false,
                    false, false, false, false, false, false, false, true
                });
    }

    private void verifyResultBitArray(byte[] data, boolean[] expectedResultWithMsbOrdering) throws Exception
    {
        MemoryStream stream = new MemoryStream(data);
        try /*JAVA: was using*/
        {
            // Verify MSB ordering.
            BitBinaryReader msbReader = new BitBinaryReader(stream, true);
            for (int i = 0; i < expectedResultWithMsbOrdering.length; i++)
            {
                msAssert.areEqual(expectedResultWithMsbOrdering[i], msbReader.readBit());
            }

            // Verify LSB ordering.
            stream.setPosition(0);
            BitBinaryReader lsbReader = new BitBinaryReader(stream, false);
            msAssert.areEqual(0, expectedResultWithMsbOrdering.length % 8);
            for (int i = 0; i < expectedResultWithMsbOrdering.length / 8; i++)
                for (int j = 0; j < 8; j++)
                {
                    msAssert.areEqual(expectedResultWithMsbOrdering[8 * i + 8 - j - 1], lsbReader.readBit());
                }
        }
        finally { if (stream != null) stream.close(); }
    }

    @Test
    public void testReadValueMsb() throws Exception
    {
        byte[] data = new byte[] { (byte) 0x04, (byte) 0x03, (byte) 0x02, (byte) 0x01 };
        MemoryStream stream = new MemoryStream(data);
        try /*JAVA: was using*/
        {
            BitBinaryReader msbReader = new BitBinaryReader(stream, true);
            msAssert.areEqual(0x0, msbReader.readUnsignedValue(4));
            msAssert.areEqual(0x40, msbReader.readUnsignedValue(8));
            msAssert.areEqual(0x30201, msbReader.readUnsignedValue(20));
        }
        finally { if (stream != null) stream.close(); }
    }

    @Test (expectedExceptions = UnsupportedOperationException.class)
    public void testReadValueLsb() throws Exception
    {
        byte[] data = new byte[] { (byte) 0x04, (byte) 0x03, (byte) 0x02, (byte) 0x01 };
        MemoryStream stream = new MemoryStream(data);
        try /*JAVA: was using*/
        {
            BitBinaryReader lsbReader = new BitBinaryReader(stream, false);
            lsbReader.readUnsignedValue(4);
            lsbReader.readUnsignedValue(8);
            lsbReader.readUnsignedValue(20);
        }
        finally { if (stream != null) stream.close(); }
    }
}
