// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents VDMX Group in VDMX table in OpenType file.
/// </summary>
class VdmxGroup
{
    /// <summary>
    /// Writes VDMX Group to binary writer.
    /// </summary>
    public void write(BigEndianBinaryWriter writer) throws Exception
    {
        writer.writeUInt16(VTables.length);
        writer.writeByte((byte)(VTables[0].YPelHeight & 0xFFFF));
        writer.writeByte((byte)(VTables[VTables.length - 1].YPelHeight & 0xFFFF));
        for (VdmxVTable vdmxVTable : VTables)
        {
            vdmxVTable.write(writer);
        }
    }

    /// <summary>
    /// Reads VDMX Group from binary reader.
    /// </summary>
    public static VdmxGroup read(BigEndianBinaryReader reader) throws Exception
    {
        VdmxGroup vdmxGroup = new VdmxGroup();
        /*ushort*/short vTablesCount = reader.readUInt16();
        vdmxGroup.VTables = new VdmxVTable[(vTablesCount & 0xFFFF)];

        // These values will be recalculated according to the specification below.
        vdmxGroup.mYMaxMultiplier = (reader.readByte() & 0xFF);
        vdmxGroup.mYMinMultiplier = (reader.readByte() & 0xFF);
        int yMaxRatio = 0;
        int yMinRatio = 0;
        for (int i = 0; i < (vTablesCount & 0xFFFF); i++)
        {
            vdmxGroup.VTables[i] = VdmxVTable.read(reader);
            yMaxRatio += ((vdmxGroup.VTables[i].YMax << 16) + (vdmxGroup.VTables[i].YPelHeight & 0xFFFF) / 2) / (vdmxGroup.VTables[i].YPelHeight & 0xFFFF);
            yMinRatio += ((vdmxGroup.VTables[i].YMin << 16) - (vdmxGroup.VTables[i].YPelHeight & 0xFFFF) / 2) / (vdmxGroup.VTables[i].YPelHeight & 0xFFFF);
        }

        yMaxRatio = (yMaxRatio + (vTablesCount & 0xFFFF) / 2) / (vTablesCount & 0xFFFF);
        yMinRatio = (yMinRatio - (vTablesCount & 0xFFFF) / 2) / (vTablesCount & 0xFFFF);

        // Convert to 21.11 format.
        vdmxGroup.mYMaxMultiplier = (yMaxRatio + 16) >> 5;
        vdmxGroup.mYMinMultiplier = (-yMinRatio + 16) >> 5;

        return vdmxGroup;
    }

    /// <summary>
    /// Used to predict actual yMax values.
    /// </summary>
    int getYMaxMultiplier() { return mYMaxMultiplier; }

    /// <summary>
    /// used to predict actual yMin values.
    /// </summary>
    int getYMinMultiplier() { return mYMinMultiplier; }

    public VdmxVTable[] VTables;
    private int mYMaxMultiplier;
    private int mYMinMultiplier;
}

