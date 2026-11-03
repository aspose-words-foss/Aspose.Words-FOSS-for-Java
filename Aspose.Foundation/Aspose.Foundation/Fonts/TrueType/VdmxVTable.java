// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents vTable in VDMX table in OpenType file.
/// </summary>
class VdmxVTable
{
    /// <summary>
    /// Writes vTable to binary writer.
    /// </summary>
    public void write(BigEndianBinaryWriter writer) throws Exception
    {
        writer.writeUInt16((YPelHeight & 0xFFFF));
        writer.writeInt16(YMax);
        writer.writeInt16(YMin);
    }

    /// <summary>
    /// Reads vTable from binary reader.
    /// </summary>
    public static VdmxVTable read(BigEndianBinaryReader reader) throws Exception
    {
        VdmxVTable vdmxVTable = new VdmxVTable();
        vdmxVTable.YPelHeight = reader.readUInt16();
        vdmxVTable.YMax = reader.readInt16();
        vdmxVTable.YMin = reader.readInt16();

        return vdmxVTable;
    }

    public /*ushort*/short YPelHeight;
    public short YMax;
    public short YMin;
}

