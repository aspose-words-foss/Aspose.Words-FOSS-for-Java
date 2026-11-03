// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.Stream;
import java.util.Map;


/// <summary>
/// Responsible for building 'sfnt' files.
/// </summary>
/// <remarks>
/// See http://www.microsoft.com/typography/otspec/otff.htm for more info about 'sfnt' format.
/// 'sfnt' format is used in TrueType and OpenType fonts and also in MTX compression.
/// </remarks>
class SfntBuilder implements IFontBuilder
{
    /// <summary>
    /// Ctor.
    /// </summary>
    public SfntBuilder(int sfntVersion)
    {
    	this();
	
        mSfntVersion = sfntVersion;
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    public SfntBuilder()
    {
        mTables = new SortedStringListGeneric<byte[]>(true);
    }

    public int getSfntVersion() { return mSfntVersion; }
    public void setSfntVersion(int value) { mSfntVersion = value; }

    /// <summary>
    /// Add table to file.
    /// </summary>
    public void addTable(String tag, byte[] tableData)
    {
        mTables.add(tag, tableData);
    }

    /// <summary>
    /// Writes result 'sfnt' file to byte array.
    /// </summary>
    public byte[] writeFileToByteArray() throws Exception
    {
        MemoryStream stream = new MemoryStream();
        try /*JAVA: was using*/
        {
            writeFileToStream(stream);
            return stream.toArray();
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Writes result 'sfnt' file to stream.
    /// </summary>
    public void writeFileToStream(Stream stream) throws Exception
    {
        BigEndianBinaryWriter writer = new BigEndianBinaryWriter(stream);

        SfntOffsetTable offsetTable = new SfntOffsetTable();
        offsetTable.setVersion(mSfntVersion);
        offsetTable.setNumTables((/*ushort*/short)mTables.getCount());
        offsetTable.write(writer);

        /*uint*/int currentTableOffset = (/*uint*/int)(stream.getPosition() + SfntTableRecordEntry.STRUCTURE_SIZE * mTables.getCount());
        long currentTableRecordOffset = stream.getPosition();

        // Specification says: Entries in the Table Record must be sorted in ascending order by tag.
        // To achieve that we will enumerate through SortedStringList.
        // Note: There is a recommendation that 'head' table data (not table record entry) should be placed first
        // in the file or at least before the 'OS/2' table. Ignore for now.
        for (Map.Entry<String, byte[]> pair : (Iterable<Map.Entry<String, byte[]>>) mTables)
        {
            stream.setPosition(currentTableRecordOffset);
            byte[] tableData = pair.getValue();
            SfntTableRecordEntry tableRecordEntry = new SfntTableRecordEntry();
            tableRecordEntry.Tag = pair.getKey();
            tableRecordEntry.Length = (/*uint*/int)tableData.length;
            tableRecordEntry.Offset = currentTableOffset;
            tableRecordEntry.Checksum = SfntTableRecordEntry.calculateChecksum(tableData);
            tableRecordEntry.write(writer);
            currentTableRecordOffset = stream.getPosition();

            stream.setPosition((currentTableOffset & 0xFFFFFFFFL));
            writer.writeBytes(tableData, 0, tableData.length);
            padTable(writer);
            currentTableOffset = (/*uint*/int)stream.getPosition();
        }
    }

    /// <summary>
    /// Pads the previously written table.
    /// </summary>
    /// <remarks>
    /// Specification says:
    /// A font is not considered structurally proper without the correct padding. All tables must begin on
    /// four byte boundaries, and any remaining space between tables is padded with zeros. The length of all tables
    /// should be recorded in the table record with their actual length (not their padded length).
    /// </remarks>
    private static void padTable(BigEndianBinaryWriter writer) throws Exception
    {
        StreamUtil.seekToNextPage(writer.getBaseStream(), 4);
    }

    private int mSfntVersion = SfntOffsetTable.SFNT_VERSION_1;
    private /*final*/ SortedStringListGeneric<byte[]> mTables;
}

