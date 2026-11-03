// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/02/2015 by Denis Shvydkiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Entry in the vertical metrics array.
/// http://www.microsoft.com/typography/otspec/vmtx.htm
/// </summary>
/*struct*/ final class VerticalMetric
{
	public VerticalMetric(){}
	
    // kvk: Specification says that AdvanceHeight is USHORT but MS Word and FontLab Studio reads this value as SHORT.
    short AdvanceHeight;
    short TopSideBearing;

    VerticalMetric(short advanceHeight, short topSideBearing)
    {
        AdvanceHeight = advanceHeight;
        TopSideBearing = topSideBearing;
    }

    VerticalMetric(BigEndianBinaryReader reader) throws Exception
    {
        AdvanceHeight = reader.readInt16();
        TopSideBearing = reader.readInt16();
    }

    void write(BigEndianBinaryWriter writer) throws Exception
    {
        writer.writeInt16(AdvanceHeight);
        writer.writeInt16(TopSideBearing);
    }
}

