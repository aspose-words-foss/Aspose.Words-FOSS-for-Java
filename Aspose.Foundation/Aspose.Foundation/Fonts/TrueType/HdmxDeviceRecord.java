// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents Device Record in hdmx table in OpenType font.
/// </summary>
class HdmxDeviceRecord
{
    /// <summary>
    /// Reads record from binary reader.
    /// </summary>
    static HdmxDeviceRecord read(BigEndianBinaryReader reader, int numGlyphs, int deviceRecordSize) throws Exception
    {
        if (deviceRecordSize < getActualRecordSize(numGlyphs))
            throw new IllegalStateException("Wrong hdmx device record size.");

        HdmxDeviceRecord record = new HdmxDeviceRecord();

        record.PixelSize = reader.readByte();
        record.MaxWidth = reader.readByte();
        record.Widths = reader.readBytes(numGlyphs);

        reader.getBaseStream().setPosition(reader.getBaseStream().getPosition() + (deviceRecordSize - getActualRecordSize(numGlyphs)));

        return record;
    }

    /// <summary>
    /// Writes record with its widths to writer.
    /// </summary>
    void write(BigEndianBinaryWriter writer, int deviceRecordSize) throws Exception
    {
        if (deviceRecordSize < getActualRecordSize(Widths.length))
            throw new IllegalStateException("Wrong hdmx device record size.");

        write(writer);
        writer.writeBytes(Widths);

        // Pad record to deviceRecordSize.
        writer.getBaseStream().setPosition(writer.getBaseStream().getPosition() + (deviceRecordSize - getActualRecordSize()));
    }

    /// <summary>
    /// Writes 'pixelSize' and 'maxWidth' of this record to writer.
    /// </summary>
    void write(BigEndianBinaryWriter writer) throws Exception
    {
        writer.writeByte(PixelSize);
        writer.writeByte(MaxWidth);
    }

    /// <summary>
    /// Returns actual size of device record data.
    /// </summary>
    static int getActualRecordSize(int numGlyphs)
    {
        return numGlyphs + 2;
    }

    /// <summary>
    /// Returns actual size of device record data.
    /// </summary>
    private int getActualRecordSize()
    {
        return getActualRecordSize(Widths.length);
    }

    byte PixelSize;
    byte MaxWidth;
    byte[] Widths;
}

