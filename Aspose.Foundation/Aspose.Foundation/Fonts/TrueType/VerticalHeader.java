// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/02/2015 by Denis Shvydkiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// vhea - Vertical Header
///
/// This table contains information for vertical layout.
/// </summary>
class VerticalHeader extends TTTable
{
    /// <summary>
    /// version 1.0: 0x00010000.
    /// version 1.1: 0x00011000.
    /// </summary>
    /*uint*/int Version;
    /// <summary>
    /// version 1.0: Distance in FUnits from the centerline to the previous line’s descent.
    /// version 1.1: The vertical typographic ascender for this font.
    ///              It is the distance in FUnits from the ideographic em-box center baseline
    ///              for the vertical axis to the right of the ideographic em-box and is usually set to (head.unitsPerEm)/2.
    /// </summary>
    short Ascender;
    /// <summary>
    /// version 1.0: Distance in FUnits from the centerline to the next line’s ascent.
    /// version 1.1: The vertical typographic descender for this font.
    ///              It is the distance in FUnits from the ideographic em-box center baseline
    ///              for the horizontal axis to the left of the ideographic em-box and is usually set to (head.unitsPerEm)/2.
    /// </summary>
    short Descender;
    /// <summary>
    /// version 1.0: Reserved; set to 0
    /// version 1.1: The vertical typographic gap for this font.
    ///              An application can determine the recommended line spacing for single spaced vertical text
    ///              for an OpenType font by the following expression: ideo embox width + vhea.vertTypoLineGap.
    /// </summary>
    short LineGap;
    /// <summary>
    /// version 1.0 and 1.1: The maximum advance height measurement found in the font.
    /// </summary>
    /*ushort*/short AdvanceHeightMax;
    /// <summary>
    /// version 1.0 and 1.1: The minimum top sidebearing measurement found in the font.
    /// </summary>
    short MinTopSideBearing;
    /// <summary>
    /// version 1.0 and 1.1: The minimum bottom sidebearing measurement found in the font.
    /// </summary>
    short MinBottomSideBearing;
    /// <summary>
    /// version 1.0 and 1.1: YMaxExtent = MinTopSideBearing + (yMax - yMin).
    /// </summary>
    short YMaxExtent;
    /// <summary>
    /// version 1.0 and 1.1: The value of the caretSlopeRise field divided by the value of the caretSlopeRun Field determines the slope of the caret.
    /// A value of 0 for the rise and a value of 1 for the run specifies a horizontal caret.
    /// A value of 1 for the rise and a value of 0 for the run specifies a vertical caret.
    /// Intermediate values are desirable for fonts whose glyphs are oblique or italic.
    /// For a vertical font, a horizontal caret is best.
    /// </summary>
    short CaretSlopeRise;
    /// <summary>
    /// version 1.0 and 1.1: See the CaretSlopeRise field. Value = 1 for nonslanted vertical fonts.
    /// </summary>
    short CaretSlopeRun;
    /// <summary>
    /// version 1.0 and 1.1: The amount by which the highlight on a slanted glyph needs to be shifted away from the glyph in order to produce the best appearance.
    /// Set value equal to 0 for nonslanted fonts.
    /// </summary>
    short CaretOffset;
    /// <summary>
    /// version 1.0 and 1.1: reserved.
    /// </summary>
    short r1;
    /// <summary>
    /// version 1.0 and 1.1: reserved.
    /// </summary>
    short r2;
    /// <summary>
    /// version 1.0 and 1.1: reserved.
    /// </summary>
    short r3;
    /// <summary>
    /// version 1.0 and 1.1: reserved.
    /// </summary>
    short r4;
    /// <summary>
    /// version 1.0 and 1.1: 0 for current format.
    /// </summary>
    short MetricDataFormat;
    /// <summary>
    /// version 1.0 and 1.1: Number of advance heights in the vertical metrics table.
    /// </summary>
    /*ushort*/short NumberOfVMetrics;

    VerticalHeader(BigEndianBinaryReader reader) throws Exception
    {
        // WORDSNET-24139, WORDSNET-25217 Customer fonts have invalid 'vmtx' version value. MW seems to be OK with that.
        // Do not validate the version at all for now. But potentially the 'vmtx' table with incorrect version
        // could be ignored.
        Version = reader.readUInt32();
        Ascender = reader.readInt16();
        Descender = reader.readInt16();
        LineGap = reader.readInt16();
        AdvanceHeightMax = reader.readUInt16();

        MinTopSideBearing = reader.readInt16();
        MinBottomSideBearing = reader.readInt16();
        YMaxExtent = reader.readInt16();

        CaretSlopeRise = reader.readInt16();
        CaretSlopeRun = reader.readInt16();
        CaretOffset = reader.readInt16();

        // reserved
        r1 = reader.readInt16();
        r2 = reader.readInt16();
        r3 = reader.readInt16();
        r4 = reader.readInt16();

        MetricDataFormat = reader.readInt16();
        NumberOfVMetrics = reader.readUInt16();
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
        writer.writeInt16((AdvanceHeightMax & 0xFFFF));

        writer.writeInt16(MinTopSideBearing);
        writer.writeInt16(MinBottomSideBearing);
        writer.writeInt16(YMaxExtent);

        writer.writeInt16(CaretSlopeRise);
        writer.writeInt16(CaretSlopeRun);
        writer.writeInt16(CaretOffset);

        writer.writeInt16(r1);
        writer.writeInt16(r2);
        writer.writeInt16(r3);
        writer.writeInt16(r4);

        writer.writeInt16(MetricDataFormat);
        writer.writeInt16((NumberOfVMetrics & 0xFFFF));
    }
}

