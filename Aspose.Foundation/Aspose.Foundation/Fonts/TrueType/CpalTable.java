// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/11/2021 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Handles 'CPAL' OpenType table.
/// </summary>
class CpalTable
{
    public CpalTable(BigEndianBinaryReader reader) throws Exception
    {
        long tableStartPosition = reader.getBaseStream().getPosition();
        reader.readUInt16(); // version
        int numPaletteEntries = (reader.readUInt16() & 0xFFFF);
        reader.readUInt16(); // numPalettes
        reader.readUInt16(); // numColorRecords
        int colorRecordsArrayOffset = reader.readInt32();

        // For now read only first palette.
        int palette0ColorRecordIndex = (reader.readUInt16() & 0xFFFF);
        reader.getBaseStream().setPosition(tableStartPosition + colorRecordsArrayOffset + palette0ColorRecordIndex * 4);
        mPaletteColors = new DrColor[numPaletteEntries];
        for (int i = 0; i < numPaletteEntries; i++)
        {
            byte b = reader.readByte();
            byte g = reader.readByte();
            byte r = reader.readByte();
            byte a = reader.readByte();
            getPaletteColors()[i] = new DrColor((a & 0xFF), (r & 0xFF), (g & 0xFF), (b & 0xFF));
        }
    }

    public DrColor[] getPaletteColors() { return mPaletteColors; };

    private DrColor[] mPaletteColors;
}

