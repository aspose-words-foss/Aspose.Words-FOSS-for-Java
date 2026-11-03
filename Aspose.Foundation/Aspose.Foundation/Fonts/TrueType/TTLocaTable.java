// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msUShort;


/// <summary>
/// Represents 'loca' table in OpenType file.
/// </summary>
class TTLocaTable extends TTTable
{
    /// <summary>
    /// Ctor.
    /// </summary>
    public TTLocaTable()
    {
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    public TTLocaTable(boolean isLocaShort)
    {
        mIsLocaShort = isLocaShort;
    }

    /// <summary>
    /// Reads loca table from binary reader.
    /// </summary>
    public static TTLocaTable read(BigEndianBinaryReader reader, /*uint*/int tableLenght, boolean isLocaShort) throws Exception
    {
        TTLocaTable loca = new TTLocaTable(isLocaShort);

        if (isLocaShort)
        {
            int count = (int)((tableLenght & 0xFFFFFFFFL) / 2);
            for (int i = 0; i < count; i++)
                loca.getGlyphLocations().add((reader.readUInt16() & 0xFFFF) * 2);
        }
        else
        {
            int count = (int)((tableLenght & 0xFFFFFFFFL) / 4);
            for (int i = 0; i < count; i++)
                loca.getGlyphLocations().add(reader.readInt32());
        }

        return loca;
    }

    /// <summary>
    /// Writes loca table to binary writer.
    /// </summary>
    /*override*/ void write(BigEndianBinaryWriter writer) throws Exception
    {
        for (int i = 0; i < getGlyphLocations().getCount(); i++)
        {
            if (isLocaShort())
                writer.writeInt16(getGlyphLocations().get(i) / 2);
            else
                writer.writeInt32(getGlyphLocations().get(i));
        }
    }

    /// <summary>
    /// Chooses loca table format according to glyphs offset values.
    /// </summary>
    public void chooseFormat()
    {
        int maxOffset = 0;
        for (int i = 0; i < getGlyphLocations().getCount(); i++)
        {
            if (getGlyphLocations().get(i) % 2 != 0)
            {
                // In short loca only even offsets could be defined.
                isLocaShort(false);
                return;
            }
            maxOffset = Math.max(maxOffset, getGlyphLocations().get(i));
        }

        isLocaShort((maxOffset <= MAX_SHORT_LOCA_OFFSET));
    }

    /// <summary>
    /// True if short glyph offsets are used.
    /// </summary>
    public boolean isLocaShort() { return mIsLocaShort; }
    public void isLocaShort(boolean value) { mIsLocaShort = value; }

    /// <summary>
    /// Relative offsets of glyph records in glyph table.
    /// </summary>
    public IntList getGlyphLocations() { return mGlyphLocations; }

    private /*final*/ IntList mGlyphLocations = IntList.createAllocated();
    private boolean mIsLocaShort;

    private static final int MAX_SHORT_LOCA_OFFSET = (msUShort.MAX_VALUE & 0xFFFF) * 2;
}

