// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.msString;


/// <summary>
/// Responsible for reading 'sfnt' files.
/// </summary>
/// <remarks>
/// See http://www.microsoft.com/typography/otspec/otff.htm for more info about 'sfnt' format.
/// 'sfnt' format is used in TrueType and OpenType fonts and also in MTX compression.
/// </remarks>
class SfntReader
{
    /// <summary>
    /// Ctor.
    /// </summary>
    public SfntReader(BigEndianBinaryReader reader)
    {
        mReader = reader;
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    public SfntReader(Stream stream)
    {
        mReader = new BigEndianBinaryReader(stream);
    }

    /// <summary>
    /// Reads 'sfnt' header from the current position in stream.
    /// </summary>
    public void readHeader() throws Exception
    {
        if (!tryReadHeader())
            throw new IllegalStateException("The sfnt file is not valid.");
    }

    /// <summary>
    /// Tries to read 'sfnt' header from the current position in stream.
    /// Returns true if header is valid and false otherwise.
    /// </summary>
    public boolean tryReadHeader() throws Exception
    {
        mOffsetTable = SfntOffsetTable.read(mReader);
        if (!getOffsetTable().isValid())
            return false;

        mTableRecordEntries = new SortedStringListGeneric<SfntTableRecordEntry>();
        for (int i = 0; i < (getOffsetTable().getNumTables() & 0xFFFF); i++)
        {
            SfntTableRecordEntry tableRecordEntry = SfntTableRecordEntry.read(mReader);
            getTableRecordEntries().add(tableRecordEntry.Tag, tableRecordEntry);
        }

        return true;
    }

    /// <summary>
    /// Sets the position of the reader stream to the beginning of the specified table.
    /// </summary>
    public void seekToTable(String tableTag) throws Exception
    {
        SfntTableRecordEntry tableRecordEntry = DictionaryExtension.getValueOrNull(mTableRecordEntries, tableTag);
        if (tableRecordEntry == null)
            throw new IllegalStateException(msString.format("Cannot find table '{0}' in the font file.", tableTag));

        mReader.getBaseStream().setPosition((tableRecordEntry.Offset & 0xFFFFFFFFL));
    }

    /// <summary>
    /// Reads the specified table into an array of bytes.
    /// </summary>
    public byte[] readTable(String tableTag) throws Exception
    {
        seekToTable(tableTag);

        SfntTableRecordEntry tableRecordEntry = DictionaryExtension.getValueOrNull(mTableRecordEntries, tableTag);
        Debug.assertTrue(tableRecordEntry != null);
        return mReader.readBytes((int)(tableRecordEntry.Length & 0xFFFFFFFFL));
    }

    /// <summary>
    /// Binary reader.
    /// </summary>
    public BigEndianBinaryReader getBinaryReader() { return mReader; }

    /// <summary>
    /// Base stream.
    /// </summary>
    public Stream getBaseStream() { return mReader.getBaseStream(); }

    /// <summary>
    /// Offset Table.
    /// </summary>
    public SfntOffsetTable getOffsetTable() { return mOffsetTable; }

    /// <summary>
    /// List of entries in Table Record.
    /// Key - table tag.
    /// Value - SfntTableRecordEntry instance.
    /// </summary>
    public SortedStringListGeneric<SfntTableRecordEntry> getTableRecordEntries() { return mTableRecordEntries; }

    private /*final*/ BigEndianBinaryReader mReader;
    private SfntOffsetTable mOffsetTable;
    private SortedStringListGeneric<SfntTableRecordEntry> mTableRecordEntries;
}

