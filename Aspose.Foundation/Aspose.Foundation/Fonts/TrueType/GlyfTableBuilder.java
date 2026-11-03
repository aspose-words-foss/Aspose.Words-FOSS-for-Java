// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/04/2019 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.MemoryStream;


/// <summary>
/// Helps to build 'glyf' and 'loca' tables.
/// </summary>
/// <remarks>
/// Individual glyphs can be written either by <see cref="WriteGlyph"/> method or by calling <see cref="StartGlyph"/>
/// method and then writing the glyph data directly to the <see cref="GlyfStream"/>/<see cref="GlyfWriter"/>.
/// At the end to the table you should call <see cref="EndTable"/> method.
/// To get the 'loca' output you should set the <see cref="TTLocaTable.IsLocaShort"/> field on the <see cref="LocaTable"/>
/// and call <see cref="BuildLocaStream"/> method.
/// Output is in the <see cref="GlyfStream"/> and <see cref="LocaStream"/>.
/// </remarks>
class GlyfTableBuilder
{
    public GlyfTableBuilder(boolean padToLocaShort) throws Exception
    {
        mPadToLocaShort = padToLocaShort;
        mGlyfStream = new MemoryStream();
        mGlyfWriter = new BigEndianBinaryWriter(getGlyfStream());
        mLocaTable = new TTLocaTable();
        mLocaStream = new MemoryStream();
    }

    public void writeEmptyGlyph() throws Exception
    {
        startGlyph();
    }

    public void writeGlyph(byte[] glyphData) throws Exception
    {
        startGlyph();
        getGlyfStream().write(glyphData, 0, glyphData.length);
        if (mPadToLocaShort && getGlyfStream().getLength() % 2 == 1)
            getGlyfStream().writeByte((byte) 0);
    }

    public void startGlyph() throws Exception
    {
        getLocaTable().getGlyphLocations().add((int)getGlyfStream().getPosition());
    }

    public void endTable() throws Exception
    {
        getLocaTable().getGlyphLocations().add((int)getGlyfStream().getPosition());
    }

    public void buildLocaStream() throws Exception
    {
        BigEndianBinaryWriter writer = new BigEndianBinaryWriter(getLocaStream());
        getLocaTable().write(writer);
    }

    public MemoryStream getGlyfStream() { return mGlyfStream; };

    private MemoryStream mGlyfStream;

    public BigEndianBinaryWriter getGlyfWriter() { return mGlyfWriter; };

    private BigEndianBinaryWriter mGlyfWriter;

    public TTLocaTable getLocaTable() { return mLocaTable; };

    private TTLocaTable mLocaTable;

    public MemoryStream getLocaStream() { return mLocaStream; };

    private MemoryStream mLocaStream;

    private /*final*/ boolean mPadToLocaShort;
}

