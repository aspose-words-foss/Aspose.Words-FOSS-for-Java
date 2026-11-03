// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/04/2019 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Drawing.Rectangle;


/// <summary>
/// Helps to read 'glyf' table.
/// </summary>
class GlyfTableReader
{
    public GlyfTableReader(BigEndianBinaryReader reader, SfntTableRecordEntry locaEntry, SfntTableRecordEntry glyfEntry,
        boolean isLocaShort) throws Exception
    {
        mReader = reader;
        mGlyfEntry = glyfEntry;
        mIsLocaShort = isLocaShort;

        reader.getBaseStream().setPosition((locaEntry.Offset & 0xFFFFFFFFL));
        mLocaTable = TTLocaTable.read(mReader, locaEntry.Length, isLocaShort);
    }

    public void locateGlyph(int glyphIndex) throws Exception
    {
        Debug.assertTrue(glyphIndex < getGlyphCount());
        mReader.getBaseStream().setPosition((mGlyfEntry.Offset & 0xFFFFFFFFL) + mLocaTable.getGlyphLocations().get(glyphIndex));
    }

    public Rectangle readGlyphBox(int glyphIndex) throws Exception
    {
        Debug.assertTrue(glyphIndex < getGlyphCount());
        int glyphLength = getGlyphLength(glyphIndex);
        if (glyphLength < 10)
        {
            Debug.assertTrue(glyphLength == 0);
            return Rectangle.Empty;
        }
        locateGlyph(glyphIndex);
        mReader.readInt16(); // numberOfContours
        int xMin = mReader.readInt16();
        int yMin = mReader.readInt16();
        int xMax = mReader.readInt16();
        int yMax = mReader.readInt16();
        return Rectangle.fromLTRB(xMin, yMin, xMax, yMax);
    }

    public int getGlyphLength(int glyphIndex)
    {
        Debug.assertTrue(glyphIndex < getGlyphCount());
        return mLocaTable.getGlyphLocations().get(glyphIndex + 1) - mLocaTable.getGlyphLocations().get(glyphIndex);
    }

    public byte[] getGlyphBytes(int glyphIndex) throws Exception
    {
        int length = getGlyphLength(glyphIndex);

        if (length == 0)
            return new byte[0];

        locateGlyph(glyphIndex);
        return mReader.readBytes(length);
    }

    public int getGlyphCount() { return mLocaTable.getGlyphLocations().getCount() - 1; }

    public boolean isLocaShort() { return mIsLocaShort; }

    private /*final*/ BigEndianBinaryReader mReader;
    private /*final*/ SfntTableRecordEntry mGlyfEntry;
    private /*final*/ boolean mIsLocaShort;
    private /*final*/ TTLocaTable mLocaTable;
}

