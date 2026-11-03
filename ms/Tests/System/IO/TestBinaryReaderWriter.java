// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/07/2005 by Roman Korchagin
// 2016/06/20 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.Text.Encoding;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.BinaryWriter;


@Test
public class TestBinaryReaderWriter
{
    @Test
    public void testReadChars() throws Exception
    {
        byte[] unicodeBytes = { (byte) 84, (byte) 0, (byte) 101, (byte) 0, (byte) 115, (byte) 0, (byte) 116, (byte) 0, (byte) 32, (byte) 0, (byte) 83, (byte) 0, (byte) 116, (byte) 0, (byte) 114, (byte) 0, (byte) 105, (byte) 0, (byte) 110, (byte) 0, (byte) 103, (byte) 0, (byte) 32, (byte) 0, (byte) 34, (byte) 4, (byte) 53, (byte) 4, (byte) 65, (byte) 4, (byte) 66, (byte) 4, (byte) 62, (byte) 4, (byte) 50, (byte) 4, (byte) 48, (byte) 4, (byte) 79, (byte) 4, (byte) 32, (byte) 0, (byte) 65, (byte) 4, (byte) 66, (byte) 4, (byte) 64, (byte) 4, (byte) 62, (byte) 4, (byte) 71, (byte) 4, (byte) 58, (byte) 4, (byte) 48, (byte) 4, (byte) 32, (byte) 0, (byte) 49, (byte) 0, (byte) 50, (byte) 0, (byte) 51, (byte) 0, (byte) 32, (byte) 0, (byte) 47, (byte) 0, (byte) 110, (byte) 0, (byte) 46, (byte) 0 };
        String testStrUnicode = "Test String Тестовая строчка 123 /n.";
        String testStrAscii = "T\u0000e\u0000s\u0000t\u0000 \u0000";

        MemoryStream stream = new MemoryStream(unicodeBytes);
        BinaryReader reader = new BinaryReader(stream, Encoding.getUnicode());

        //reading in "main" (setted by ctor) encoding
        char[] chars = reader.readChars(11);
        char[] testChars = testStrUnicode.substring((0), (0) + (11)).toCharArray();
        msAssert.isTrue(ArrayUtil.isArrayEqual(chars, testChars));
        msAssert.areEqual(reader.getBaseStream().getPosition(), 11 * 2);

        //one-short reading in another encoding
        reader = new BinaryReader(stream, Encoding.getASCII());
        reader.getBaseStream().setPosition(0);
        chars = reader.readChars(10);
        testChars = testStrAscii.toCharArray();
        msAssert.isTrue(ArrayUtil.isArrayEqual(chars, testChars));
        msAssert.areEqual(reader.getBaseStream().getPosition(), 10);

        //again main encoding
        reader = new BinaryReader(stream, Encoding.getUnicode());
        reader.getBaseStream().setPosition(10);
        chars = reader.readChars(15);
        testChars = testStrUnicode.substring((5), (5) + (15)).toCharArray();
        msAssert.isTrue(ArrayUtil.isArrayEqual(chars, testChars));
    }

    @Test
    public void testBinaryWriter() throws Exception
    {
        Stream stream = new MemoryStream();
        BinaryWriter writer = new BinaryWriter(stream);

        writer.writeByte((byte)1);
        writer.writeByte((byte)0);

        writer.writeInt16((short)1234);
        writer.writeInt16((short)-1234);

        writer.writeInt32(123456);
        writer.writeInt32(-123456);

        writer.writeUInt16((/*ushort*/short)0xF123);
        writer.writeUInt32((/*uint*/int)0xF1234567L);

        //Check length ang go back to read the stuff
        msAssert.areEqual(20, stream.getLength());
        stream.setPosition(0);

        BinaryReader reader = new BinaryReader(stream);

        msAssert.areEqual(1, reader.readByte());
        msAssert.areEqual(0, (byte)reader.readByte());

        msAssert.areEqual(1234, reader.readInt16());
        msAssert.areEqual(-1234, reader.readInt16());

        msAssert.areEqual(123456, reader.readInt32());
        msAssert.areEqual(-123456, reader.readInt32());

        msAssert.areEqual((/*ushort*/short)0xF123, reader.readUInt16());
        msAssert.areEqual((/*uint*/int)0xF1234567L, reader.readUInt32());
    }
}

