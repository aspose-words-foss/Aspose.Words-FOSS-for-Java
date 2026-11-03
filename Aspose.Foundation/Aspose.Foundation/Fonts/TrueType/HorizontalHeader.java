// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/09/2005 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// hhea - Horizontal Header
///
/// This table contains information for horizontal layout.
/// </summary>
class HorizontalHeader extends TTTable
{
    /// <summary>
    /// 0x00010000 for version 1.0.
    /// </summary>
    /*uint*/int Version;
    /// <summary>
    /// Typographic ascent. Apple specific.
    /// </summary>
    short Ascender;
    /// <summary>
    /// Typographic descent. Apple specific.
    /// </summary>
    short Descender;
    /// <summary>
    /// Typographic line gap. Apple specific.
    /// </summary>
    short LineGap;
    /// <summary>
    /// Maximum advance width value in 'hmtx' table.
    /// </summary>
    /*ushort*/short AdvanceWidthMax;
    /// <summary>
    /// Minimum left sidebearing value in 'hmtx' table.
    /// </summary>
    short MinLeftSideBearing;
    /// <summary>
    /// Minimum right sidebearing value; calculated as Min(aw - lsb - (xMax - xMin)).
    /// </summary>
    short MinRightSideBearing;
    /// <summary>
    /// Max(lsb + (xMax - xMin)).
    /// </summary>
    short XMaxExtent;
    /// <summary>
    /// Used to calculate the slope of the cursor (rise/run); 1 for vertical.
    /// </summary>
    short CaretSlopeRise;
    /// <summary>
    /// 0 for vertical.
    /// </summary>
    short CaretSlopeRun;
    /// <summary>
    /// The amount by which a slanted highlight on a glyph needs to be shifted to produce the best appearance. Set to 0 for non-slanted fonts.
    /// </summary>
    short CaretOffset;
    /// <summary>
    /// reserved
    /// </summary>
    short r1;
    /// <summary>
    /// reserved
    /// </summary>
    short r2;
    /// <summary>
    /// reserved
    /// </summary>
    short r3;
    /// <summary>
    /// reserved
    /// </summary>
    short r4;
    /// <summary>
    /// 0 for current format.
    /// </summary>
    short MetricDataFormat;
    /// <summary>
    /// Number of hMetric entries in 'hmtx' table.
    /// </summary>
    /*ushort*/short NumberOfHMetrics;

    HorizontalHeader(BigEndianBinaryReader reader) throws Exception
    {
        Version = reader.readUInt32();
        // WORDSNET-Test27393 Version 2 is not defined in OTF spec but in this case there is legacy Taiwan font
        // with version 2 which is handled well by MW and Windows.
        if (Version != 0x00010000 && Version != 0x00020000)
            throw new UnsupportedOperationException("Unsupported horizontal header version.");

        Ascender = reader.readInt16();
        Descender = reader.readInt16();
        LineGap = reader.readInt16();
        AdvanceWidthMax = reader.readUInt16();

        MinLeftSideBearing = reader.readInt16();
        MinRightSideBearing = reader.readInt16();
        XMaxExtent = reader.readInt16();

        CaretSlopeRise = reader.readInt16();
        CaretSlopeRun = reader.readInt16();
        CaretOffset = reader.readInt16();

        r1 = reader.readInt16();
        r2 = reader.readInt16();
        r3 = reader.readInt16();
        r4 = reader.readInt16();

        MetricDataFormat = reader.readInt16();
        NumberOfHMetrics = reader.readUInt16();
    }

    /// <summary>
    /// Writes the table.
    /// </summary>
    /*override*/ void write(BigEndianBinaryWriter writer) throws Exception
    {
        writer.writeUInt32(Version);

        writer.writeInt16(Ascender);
        writer.writeInt16(Descender);
        writer.writeInt16(LineGap);
        writer.writeInt16((AdvanceWidthMax & 0xFFFF));

        writer.writeInt16(MinLeftSideBearing);
        writer.writeInt16(MinRightSideBearing);
        writer.writeInt16(XMaxExtent);

        writer.writeInt16(CaretSlopeRise);
        writer.writeInt16(CaretSlopeRun);
        writer.writeInt16(CaretOffset);

        writer.writeInt16(r1);
        writer.writeInt16(r2);
        writer.writeInt16(r3);
        writer.writeInt16(r4);

        writer.writeInt16(MetricDataFormat);
        writer.writeInt16((NumberOfHMetrics & 0xFFFF));
    }
}

