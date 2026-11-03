// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents Device Record in hdmx table in CTF file.
/// </summary>
class CtfHdmxDeviceRecord
{
    /// <summary>
    /// Reads record from binary reader.
    /// </summary>
    public static CtfHdmxDeviceRecord read(BigEndianBinaryReader reader) throws Exception
    {
        CtfHdmxDeviceRecord record = new CtfHdmxDeviceRecord();
        record.PixelSize = reader.readByte();
        record.MaxWidth = reader.readByte();
        return record;
    }

    /// <summary>
    /// Writes record to binary reader.
    /// </summary>
    public void write(BigEndianBinaryWriter writer) throws Exception
    {
        writer.writeByte(PixelSize);
        writer.writeByte(MaxWidth);
    }

    public byte PixelSize;

    public byte MaxWidth;
}

