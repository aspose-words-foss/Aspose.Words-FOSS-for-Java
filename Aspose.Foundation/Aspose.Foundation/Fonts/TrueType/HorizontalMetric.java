// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/05/2006 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Corresponds to longHorMetric in the TTF file.
/// </summary>
/*struct*/ final class HorizontalMetric
{
	public HorizontalMetric(){}
	
    HorizontalMetric(short advanceWidth, short leftSideBearing)
    {
        mAdvanceWidth = advanceWidth;
        mLeftSideBearing = leftSideBearing;
    }

    HorizontalMetric(BigEndianBinaryReader reader) throws Exception
    {
        // kvk: Specification says that AdvanceWidth is USHORT but MS Word and FontLab Studio reads this value as SHORT.
        mAdvanceWidth = reader.readInt16();
        mLeftSideBearing = reader.readInt16();
    }

    void write(BigEndianBinaryWriter writer) throws Exception
    {
        writer.writeInt16(getAdvanceWidth());
        writer.writeInt16(getLeftSideBearing());
    }

    public short getAdvanceWidth() { return mAdvanceWidth; };

    private  short mAdvanceWidth;
    public short getLeftSideBearing() { return mLeftSideBearing; };

    private  short mLeftSideBearing;
}

