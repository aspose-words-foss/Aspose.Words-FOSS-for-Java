// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
//06/09/05 by Roman Korchagin

package com.aspose.ms.java.IO;

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.Stream;

// Copied here as is from Foundation/IO. Used by ImageIO/tiff.

/// <summary>
/// Big-Endian byte order binary reader.
/// </summary>
public class BigEndianBinaryReader
{
    public BigEndianBinaryReader(Stream stream)
    {
        mReader = new BinaryReader(stream);
    }

    
    public Stream getBaseStream() { return mReader.getBaseStream(); }

    public int readInt32() throws Exception
    {
        return BitUtil.swapInt32(mReader.readInt32());
    }

    public /*uint*/int readUInt32() throws Exception
    {
        return BitUtil.swapUInt32(mReader.readUInt32());
    }

    public short readInt16() throws Exception
    {
        return BitUtil.swapInt16(mReader.readInt16());
    }

    public /*ushort*/short readUInt16() throws Exception
    {
        return BitUtil.swapUInt16(mReader.readUInt16());
    }

    public long readInt64() throws Exception
    {
        return BitUtil.swapInt64(mReader.readInt64());
    }

    public int readUInt24() throws Exception
    {
        byte[] data = mReader.readBytes(3);
        int value = 0;
        int multiplier = 1;
        for (int i = 2; i >= 0; i--)
        {
            value += (data[i] & 0xFF)*multiplier;
            multiplier <<= 8;
        }
        return value;
    }

    public byte readByte() throws Exception
    {
        return mReader.readByte();
    }

    public byte readSByte() throws Exception
    {
        return mReader.readSByte();
    }

    public byte[] readBytes(int count) throws Exception
    {
        return mReader.readBytes(count);
    }

    public int read(byte[] buffer, int index, int count) throws Exception
    {
        return mReader.read(buffer, index, count);
    }

    public char[] readByteChars(int count) throws Exception
    {
        char[] chars = new char[count];
        
        for (int i = 0; i < chars.length; i++)
            chars[i] = (char)(mReader.readByte() & 0xFF);
        
        return chars;
    }

    private /*final*/ BinaryReader mReader;
}

