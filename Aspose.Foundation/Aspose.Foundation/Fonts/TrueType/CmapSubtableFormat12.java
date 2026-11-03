// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2012 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents 'cmap' format 12 subtable.
/// </summary>
class CmapSubtableFormat12 extends CmapSubtable
{
    /// <summary>
    /// Ctor.
    /// </summary>
    public CmapSubtableFormat12(int platformId, int encodingId, SortedIntegerListGeneric<Integer> charMap, int language)
    {
    	super(platformId, encodingId, language, charMap);
	
    }

    /// <summary>
    /// Reads subtable from binary reader.
    /// </summary>
    public static CmapSubtableFormat12 read(BigEndianBinaryReader reader, CmapEncodingRecord encodingRecord) throws Exception
    {
        reader.getBaseStream().setPosition(encodingRecord.getPosition());
        int format = (reader.readUInt16() & 0xFFFF);
        Debug.assertTrue(format == Cmap.CMAP_FORMAT_12);
        reader.readUInt16(); // reserved
        reader.readInt32();  // length
        int language = reader.readInt32();
        int nGroups = reader.readInt32();

        SortedIntegerListGeneric<Integer> charMap = new SortedIntegerListGeneric<Integer>();
        for (int i = 0; i < nGroups; i++)
        {
            int startCharCode = reader.readInt32();
            int endCharCode = reader.readInt32();
            int startGlyphId = reader.readInt32();

            if (startCharCode > endCharCode || startCharCode < 0 || startGlyphId < 0)
                throw new IllegalStateException("Invalid Format12 'cmap' table entry.");

            for (int charCode = startCharCode; charCode <= endCharCode; charCode++)
                charMap.set(charCode, startGlyphId++);
        }

        // Explicitly add mapping for missing glyph because it is not required to be in subtable.
        charMap.set(0xFFFF, 0);

        return new CmapSubtableFormat12(encodingRecord.getPlatformId(), encodingRecord.getEncodingId(), charMap, language);
    }

    /// <summary>
    /// Writes subtable to binary writer.
    /// </summary>
    public /*override*/ void write(BigEndianBinaryWriter writer) throws Exception
    {
        writer.writeUInt16(Cmap.CMAP_FORMAT_12); // format
        writer.writeUInt16(0);  // reserved
        writer.writeInt32(16 + 12 * getCharMap().getCount());
        writer.writeInt32(getLanguage());
        writer.writeInt32(getCharMap().getCount());

        for (int i = 0; i < getCharMap().getCount(); i++)
        {
            int charCode = getCharMap().getKey(i);
            int glyphId = getCharMap().getByIndex(i);

            writer.writeInt32(charCode); // startCharCode
            writer.writeInt32(charCode); // endCharCode
            writer.writeInt32(glyphId); // startGlyphId
        }
    }
}

