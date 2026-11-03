// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
//06/09/05 by Roman Korchagin

package com.aspose.ms.java.IO;

import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.IO.Stream;

// Copied here as is from Foundation/IO. Used by ImageIO/tiff.

/// <summary>
/// Big-Endian byte order binary writer.
/// </summary>
public class BigEndianBinaryWriter
{
    public BigEndianBinaryWriter(Stream stream) throws Exception
    {
        mWriter = new BinaryWriter(stream);
    }

    public Stream getBaseStream() throws Exception { return mWriter.getBaseStream(); }

    public void writeInt32(int value) throws Exception
    {
        mWriter.writeInt32((int)BitUtil.swapInt32(value));
    }

    public void writeUInt32(/*uint*/int value) throws Exception
    {
        mWriter.writeInt32((int)(BitUtil.swapUInt32(value) & 0xFFFFFFFFL));
    }

    public void writeInt16(int value) throws Exception
    {
        mWriter.writeInt16((short)BitUtil.swapInt16((short)value));
    }

    public void writeUInt16(int value) throws Exception
    {
        mWriter.writeUInt16((/*ushort*/short)BitUtil.swapUInt16((/*ushort*/short)value));
    }

    public void writeUInt24(int value) throws Exception
    {
        byte b0 = (byte)((value & 0xFF0000) >> 16);
        byte b1 = (byte)((value & 0x00FF00) >> 8);
        byte b2 = (byte)(value & 0x0000FF);
        mWriter.writeByte(b0);
        mWriter.writeByte(b1);
        mWriter.writeByte(b2);
    }

    public void writeInt64(long value) throws Exception
    {
        mWriter.writeInt64((long)BitUtil.swapInt64(value));
    }

    public void writeByte(byte value) throws Exception
    {
        mWriter.writeByte((byte)value);
    }

    public void writeBytes(byte[] buffer, int index, int count) throws Exception
    {
        mWriter.writeBytes(buffer, index, count);
    }

    public void writeBytes(byte[] buffer) throws Exception
    {
        mWriter.writeBytes(buffer, 0, buffer.length);
    }

    public void flush() throws Exception
    {
        mWriter.flush();
    }

    private /*final*/ BinaryWriter mWriter;
}

