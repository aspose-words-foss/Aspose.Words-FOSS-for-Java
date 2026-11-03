// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents 'hdmx' table of OpenType font.
/// </summary>
class HdmxTable extends TTTable
{
    /// <summary>
    /// Read hdmx table from binary reader.
    /// </summary>
    static HdmxTable read(BigEndianBinaryReader reader, int numGlyphs) throws Exception
    {
        HdmxTable hdmx = new HdmxTable();

        hdmx.NumGlyphs = numGlyphs;
        hdmx.Version = reader.readUInt16();
        int numRecords = reader.readInt16();
        hdmx.DeviceRecordSize = reader.readInt32();

        if (numRecords < 0 || hdmx.DeviceRecordSize < HdmxDeviceRecord.getActualRecordSize(numGlyphs))
            throw new IllegalStateException("Unsupported hdmx table format.");

        hdmx.DeviceRecords = new HdmxDeviceRecord[numRecords];
        for (int i = 0; i < numRecords; i++)
            hdmx.DeviceRecords[i] = HdmxDeviceRecord.read(reader, numGlyphs, hdmx.DeviceRecordSize);

        return hdmx;
    }

    /// <summary>
    /// Writes table to binary writer.
    /// </summary>
    /*override*/ void write(BigEndianBinaryWriter writer) throws Exception
    {
        writeHeader(writer);

        for (int i = 0; i < DeviceRecords.length; i++)
            DeviceRecords[i].write(writer, DeviceRecordSize);
    }

    /// <summary>
    /// Writes table header to binary writer.
    /// </summary>
    void writeHeader(BigEndianBinaryWriter writer) throws Exception
    {
        writer.writeUInt16((Version & 0xFFFF));
        writer.writeInt16(DeviceRecords.length);
        writer.writeInt32(DeviceRecordSize);
    }

    /// <summary>
    /// Sets <see cref="DeviceRecordSize"/> to optimal value.
    /// </summary>
    void setOptimalRecordSize()
    {
        // Spec says that records should be long aligned so increase actualRecordSize to multiply 4.
        DeviceRecordSize = MathUtil.roundUp(HdmxDeviceRecord.getActualRecordSize(NumGlyphs), 4);
    }

    int NumGlyphs;
    /*ushort*/short Version;
    int DeviceRecordSize;
    HdmxDeviceRecord[] DeviceRecords;
}

