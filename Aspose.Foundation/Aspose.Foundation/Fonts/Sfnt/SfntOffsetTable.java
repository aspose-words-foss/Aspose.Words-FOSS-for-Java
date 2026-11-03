// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msMath;


/// <summary>
/// Represents an 'Offset Table' inside the 'sfnt' file.
/// </summary>
class SfntOffsetTable
{
    /// <summary>
    /// Reads 'Offset Table' from binary reader.
    /// </summary>
    public static SfntOffsetTable read(BigEndianBinaryReader reader) throws Exception
    {
        SfntOffsetTable result = new SfntOffsetTable();
        result.setVersion(reader.readInt32());
        result.setNumTables(reader.readUInt16());
        result.setSearchRange(reader.readUInt16());
        result.setEntrySelector(reader.readUInt16());
        result.setRangeShift(reader.readUInt16());
        return result;
    }

    /// <summary>
    /// Writes table to binary writer.
    /// </summary>
    public void write(BigEndianBinaryWriter writer) throws Exception
    {
        writer.writeInt32(getVersion());
        writer.writeUInt16((getNumTables() & 0xFFFF));

        int entrySelector = (int)Math.floor(msMath.log(getNumTables(), 2.0));
        int searchRange = (int)Math.pow(2.0, entrySelector) * 16;
        int rangeShift = (getNumTables() & 0xFFFF) * 16 - searchRange;

        writer.writeUInt16(searchRange);
        writer.writeUInt16(entrySelector);
        writer.writeUInt16(rangeShift);
    }

    /// <summary>
    /// Version.
    /// </summary>
    public int getVersion() { return mVersion; }; public void setVersion(int value) { mVersion = value; };

    private int mVersion;

    /// <summary>
    /// Number of tables in file.
    /// </summary>
    public /*ushort*/short getNumTables() { return mNumTables; }; public void setNumTables(/*ushort*/short value) { mNumTables = value; };

    private /*ushort*/short mNumTables;

    /// <summary>
    /// True when 'Offset Table' is valid.
    /// </summary>
    public boolean isValid() { return ((getVersion() == SFNT_VERSION_1 || getVersion() == SFNT_VERSION_OTTO) && (getNumTables() & 0xFFFF) > 0); }

    /*ushort*/short getEntrySelector() { return mEntrySelector; }; private void setEntrySelector(/*ushort*/short value) { mEntrySelector = value; };

    private /*ushort*/short mEntrySelector;
    /*ushort*/short getSearchRange() { return mSearchRange; }; private void setSearchRange(/*ushort*/short value) { mSearchRange = value; };

    private /*ushort*/short mSearchRange;
    /*ushort*/short getRangeShift() { return mRangeShift; }; private void setRangeShift(/*ushort*/short value) { mRangeShift = value; };

    private /*ushort*/short mRangeShift;


    public static final int SFNT_VERSION_1 = 0x00010000;
    public static final int SFNT_VERSION_OTTO = 0x4f54544f;
}

