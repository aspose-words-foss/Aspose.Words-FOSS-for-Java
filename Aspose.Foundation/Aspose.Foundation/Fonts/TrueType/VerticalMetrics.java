// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/02/2015 by Denis Shvydkiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// vmtx - Vertical Metrics.
/// http://www.microsoft.com/typography/otspec/vmtx.htm
///
/// Contains heights of the glyphs and their top side bearings.
/// </summary>
class VerticalMetrics extends TTTable
{
    /// <summary>
    /// Paired advance height and top side bearing values for each glyph in 1000th of the font size.
    /// Index is the glyph index.
    /// Last entry applies to all subsequent glyphs.
    /// If the font is monospaced, only one entry will be in this array.
    /// </summary>
    VerticalMetric[] HeightPairs;
    /// <summary>
    /// Here the advance height is assumed to be the same as the advance height for the last entry above.
    /// The number of entries in this array is derived from numGlyphs (from 'maxp' table)
    /// minus numberOfVMetrics. This generally is used with a run of monospaced glyphs
    /// (e.g., Kanji fonts or Courier fonts). Only one run is allowed and it must be at the end.
    /// This allows a monospaced font to vary the top side bearing values for each glyph.
    /// </summary>
    short[] TopSideBearings;

    /// <summary>
    /// Ctor. Reads the table.
    /// </summary>
    VerticalMetrics(BigEndianBinaryReader reader, int vMetricCount, int glyphCount) throws Exception
    {
        // WORDSNET-25754 Customer font has malformed vmtx table. Number of metrics is larger than glyph count.
        if (vMetricCount > glyphCount)
            vMetricCount = glyphCount;

        HeightPairs = new VerticalMetric[vMetricCount];
        for (int i = 0; i < HeightPairs.length; i++)
            HeightPairs[i] = new VerticalMetric(reader);

        // Read array of top side bearings at the end of the table.
        int bearingsCount = glyphCount - vMetricCount;
        TopSideBearings = new short[bearingsCount];
        for (int i = 0; i < TopSideBearings.length; i++)
            TopSideBearings[i] = reader.readInt16();
    }

    /// <summary>
    /// Gets height for the specified glyph index.
    /// </summary>
    VerticalMetric getVMetric(int glyphIndex)
    {
        //There could be fewer glyph heights than glyphs,
        //in this case use the last glyph height according to the spec.
        if (glyphIndex < HeightPairs.length)
        {
            return HeightPairs[glyphIndex];
        }
        else
        {
            VerticalMetric lastMetric = HeightPairs[HeightPairs.length - 1];
            short topSideBearing = TopSideBearings[glyphIndex - HeightPairs.length];
            return new VerticalMetric(lastMetric.AdvanceHeight, topSideBearing);
        }
    }

    /*override*/ void write(BigEndianBinaryWriter writer) throws Exception
    {
        for (VerticalMetric pair : HeightPairs)
            pair.write(writer);

        for (short sideBearing : TopSideBearings)
            writer.writeUInt16(sideBearing);
    }
}

