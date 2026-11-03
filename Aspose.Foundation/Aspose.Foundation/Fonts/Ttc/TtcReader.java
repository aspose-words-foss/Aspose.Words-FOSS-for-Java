// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2015 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Responsible for reading TTC font collections.
/// </summary>
/// <remarks>
/// See http://www.microsoft.com/typography/otspec/otff.htm for more info about TTC format.
/// </remarks>
public class TtcReader
{
    /// <summary>
    /// Ctor.
    /// </summary>
    public TtcReader(BigEndianBinaryReader reader)
    {
        mReader = reader;
    }

    /// <summary>
    /// Tries to read a TTC header. Returns true if header was read successfully.
    /// </summary>
    public boolean tryReadHeader() throws Exception
    {
        // According to TTC specification TTC header is always located at the beginning of the file.
        mReader.getBaseStream().setPosition(0);
        String ttcfTag = new String(mReader.readByteChars(4));
        if (!"ttcf".equals(ttcfTag))
            return false;

        int fontVersion = mReader.readInt32();
        if ((fontVersion != 0x00010000) && (fontVersion != 0x00020000))
            return false;

        mNumberOfFonts = mReader.readInt32();
        if (mNumberOfFonts <= 0)
            return false;

        setOffsetTables(new /*uint*/int[getNumberOfFonts()]);

        for (int i = 0; i < getNumberOfFonts(); i++)
            getOffsetTables()[i] = mReader.readUInt32();

        // TTC header version 2.0 has 3 extra fields.
        if (0x00020000 == fontVersion)
        {
            mReader.readUInt32();    // ULONG ulDsigTag.
            mReader.readUInt32();    // ULONG ulDsigTag.
            mReader.readUInt32();    // ULONG ulDsigOffset.
        }
        return true;
    }

    /// <summary>
    /// Sets stream position to the beginning of the specified font.
    /// </summary>
    public void seekToFont(int fontIndex) throws Exception
    {
        /*uint*/int offset = getOffsetTables()[fontIndex];
        mReader.getBaseStream().setPosition((offset & 0xFFFFFFFFL));
    }

    /// <summary>
    /// Number of fonts in collection.
    /// </summary>
    public int getNumberOfFonts() { return mNumberOfFonts; }

    /// <summary>
    /// Array of offsets to the OffsetTable for each font from the beginning of the file.
    /// </summary>
    public /*uint*/int[] getOffsetTables() { return mOffsetTables; }
    public void setOffsetTables(/*uint*/int[] value) { mOffsetTables = value; }

    private /*final*/ BigEndianBinaryReader mReader;
    private int mNumberOfFonts;
    private /*uint*/int[] mOffsetTables;
}

