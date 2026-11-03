// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/11/2021 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Handles 'COLR' OpenType table.
/// </summary>
class ColrTable
{
    public ColrTable(BigEndianBinaryReader reader) throws Exception
    {
        long tableStartPosition = reader.getBaseStream().getPosition();
        reader.readUInt16(); // version
        int numGlyphRecords = reader.readInt16();
        int glyphRecordsOffset = reader.readInt32();
        int layerRecordsOffset = reader.readInt32();
        int numLayerRecords = (reader.readUInt16() & 0xFFFF);

        reader.getBaseStream().setPosition(tableStartPosition + glyphRecordsOffset);
        mGlyphRecords = new ColrBaseGlyphRecord[numGlyphRecords];
        for (int i = 0; i < numGlyphRecords; i++)
        {
            ColrBaseGlyphRecord record = new ColrBaseGlyphRecord();
            record.GlyphId = (reader.readUInt16() & 0xFFFF);
            record.FirstLayerIndex = (reader.readUInt16() & 0xFFFF);
            record.NumLayers = (reader.readUInt16() & 0xFFFF);
            getGlyphRecords()[i] = record;
        }

        reader.getBaseStream().setPosition(tableStartPosition + layerRecordsOffset);
        mLayerRecords = new ColrLayerRecord[numLayerRecords];
        for (int i = 0; i < numLayerRecords; i++)
        {
            ColrLayerRecord record = new ColrLayerRecord();
            record.GlyphId = (reader.readUInt16() & 0xFFFF);
            record.PaletteIndex = (reader.readUInt16() & 0xFFFF);
            getLayerRecords()[i] = record;
        }
    }

    public ColrBaseGlyphRecord[] getGlyphRecords() { return mGlyphRecords; };

    private ColrBaseGlyphRecord[] mGlyphRecords;
    public ColrLayerRecord[] getLayerRecords() { return mLayerRecords; };

    private ColrLayerRecord[] mLayerRecords;
}

/// <summary>
/// Represents "Base Glyph Record" in "COLR" table.
/// </summary>
class ColrBaseGlyphRecord
{
    public int GlyphId;
    public int FirstLayerIndex;
    public int NumLayers;
}

/// <summary>
/// Represents "Layer Record" in "COLR" table.
/// </summary>
class ColrLayerRecord
{
    public int GlyphId;
    public int PaletteIndex;
}

