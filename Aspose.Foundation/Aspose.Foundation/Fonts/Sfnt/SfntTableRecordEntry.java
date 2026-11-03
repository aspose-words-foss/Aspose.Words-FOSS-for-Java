// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents a Table Record entry inside the 'sfnt' file.
/// </summary>
class SfntTableRecordEntry
{
    /// <summary>
    /// Reads Table Record entry from binary reader.
    /// </summary>
    public static SfntTableRecordEntry read(BigEndianBinaryReader reader) throws Exception
    {
        SfntTableRecordEntry result = new SfntTableRecordEntry();
        result.Tag = new String(reader.readByteChars(4));
        result.Checksum = reader.readInt32();
        result.Offset = reader.readUInt32();
        result.Length = reader.readUInt32();
        return result;
    }

    /// <summary>
    /// Writes Table Record entry to binary writer.
    /// </summary>
    /// <param name="writer"></param>
    public void write(BigEndianBinaryWriter writer) throws Exception
    {
        for (int i = 0; i < Tag.length(); i++)
            writer.writeByte((byte)Tag.charAt(i));

        writer.writeInt32(Checksum);
        writer.writeUInt32(Offset);
        writer.writeUInt32(Length);
    }

    /// <summary>
    /// Calculates checksum of a 'sfnt' table.
    /// </summary>
    public static int calculateChecksum(byte[] data)
    {
        return calculateChecksum(data, 0, data.length);
    }

    /// <summary>
    /// Calculates checksum of a 'sfnt' table.
    /// </summary>
    public static int calculateChecksum(byte[] data, int index, int count)
    {
        int sum = 0;

        int v0, v1, v2, v3;

        //Calculate the sum of the whole unsigned integers.
        int ptr = index;
        int wholeCount = count / 4;
        for (int i = 0; i < wholeCount; i++)
        {
            v3 = (data[ptr++] & 0xFF);
            v2 = (data[ptr++] & 0xFF);
            v1 = (data[ptr++] & 0xFF);
            v0 = (data[ptr++] & 0xFF);
            int value = v0 | (v1 << 8) | (v2 << 16) | (v3 << 24);
            sum += value;
        }

        //The stream length could be not a multiple of 4 bytes,
        //therefore up to 3 bytes might still be in the stream.
        v3 = (ptr < count) ? (int)(data[ptr++] & 0xFF) : 0;
        v2 = (ptr < count) ? (int)(data[ptr++] & 0xFF) : 0;
        v1 = (ptr < count) ? (int)(data[ptr] & 0xFF) : 0;
        v0 = 0;
        int lastValue = v0 | (v1 << 8) | (v2 << 16) | (v3 << 24);
        sum += lastValue;

        return sum;
    }

    /// <summary>
    /// 4-char table identifier.
    /// </summary>
    public String Tag;
    /// <summary>
    /// CheckSum for this table.
    /// </summary>
    public int Checksum;
    /// <summary>
    /// Offset from beginning of file.
    /// </summary>
    public /*uint*/int Offset;
    /// <summary>
    /// Length of this table.
    /// </summary>
    public /*uint*/int Length;

    /// <summary>
    /// Length of this structure on file.
    /// </summary>
    public static final int STRUCTURE_SIZE = 16;
}

