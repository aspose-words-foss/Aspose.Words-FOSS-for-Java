// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/05/2006 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// hmtx - Horizontal Metrics
///
/// Contains widths of the glyphs and their left side bearings.
/// </summary>
class HorizontalMetrics extends TTTable
{
    HorizontalMetrics(HorizontalMetric[] widthPairs, short[] leftSideBearings)
    {
        setWidthPairs(widthPairs);
        setLeftSideBearings(leftSideBearings);
    }

    /// <summary>
    /// Paired advance width and left side bearing values for each glyph in 1000th of the font size.
    /// Index is the glyph index.
    /// Last entry applies to all subsequent glyphs.
    /// If the font is monospaced, only one entry will be in this array.
    /// </summary>
    HorizontalMetric[] getWidthPairs() { return mWidthPairs; }; private void setWidthPairs(HorizontalMetric[] value) { mWidthPairs = value; };

    private HorizontalMetric[] mWidthPairs;
    /// <summary>
    /// Here the advanceWidth is assumed to be the same as the advanceWidth for the last entry above.
    /// The number of entries in this array is derived from numGlyphs (from 'maxp' table)
    /// minus numberOfHMetrics. This generally is used with a run of monospaced glyphs
    /// (e.g., Kanji fonts or Courier fonts). Only one run is allowed and it must be at the end.
    /// This allows a monospaced font to vary the left side bearing values for each glyph.
    /// </summary>
    short[] getLeftSideBearings() { return mLeftSideBearings; }; private void setLeftSideBearings(short[] value) { mLeftSideBearings = value; };

    private short[] mLeftSideBearings;

    /// <summary>
    /// Ctor. Reads the table.
    /// </summary>
    static HorizontalMetrics read(BigEndianBinaryReader reader, int hMetricCount, int glyphCount) throws Exception
    {
        HorizontalMetric[] widthPairs = new HorizontalMetric[hMetricCount];
        for (int i = 0; i < widthPairs.length; i++)
            widthPairs[i] = new HorizontalMetric(reader);

        // Read array of left side bearings at the end of the table.
        int bearingsCount = glyphCount - hMetricCount;
        short[] leftSideBearings = new short[bearingsCount];
        for (int i = 0; i < leftSideBearings.length; i++)
            leftSideBearings[i] = reader.readInt16();
        return new HorizontalMetrics(widthPairs, leftSideBearings);
    }

    /// <summary>
    /// Gets width for the specified glyph index.
    /// </summary>
    HorizontalMetric getHMetric(int glyphIndex)
    {
        //There could be fewer glyph widths than glyphs,
        //in this case use the last glyph width according to the spec.
        if (glyphIndex < getWidthPairs().length)
        {
            return getWidthPairs()[glyphIndex];
        }

        HorizontalMetric lastMetric = getWidthPairs()[getWidthPairs().length - 1];
        short leftSideBearing = getLeftSideBearings()[glyphIndex - getWidthPairs().length];
        return new HorizontalMetric(lastMetric.getAdvanceWidth(), leftSideBearing);
    }

    void updateMetrics(HorizontalMetric[] metrics)
    {
        Debug.assertTrue(metrics.length == getWidthPairs().length + getLeftSideBearings().length);
        setWidthPairs(metrics);
        setLeftSideBearings(new short[0]);
    }

    void updateLeftSideBearing(int glyphIndex, short leftSideBearing)
    {
        if (glyphIndex < getWidthPairs().length)
            getWidthPairs()[glyphIndex] = new HorizontalMetric(getWidthPairs()[glyphIndex].getAdvanceWidth(), leftSideBearing);
        else if (glyphIndex < getWidthPairs().length + getLeftSideBearings().length)
            getLeftSideBearings()[glyphIndex - getWidthPairs().length] = leftSideBearing;
        else
            Debug.fail("Invalid glyph index");
    }

    /*override*/ void write(BigEndianBinaryWriter writer) throws Exception
    {
        for (HorizontalMetric widthPair : getWidthPairs())
            widthPair.write(writer);

        for (short leftSideBearing : getLeftSideBearings())
            writer.writeUInt16(leftSideBearing);
    }

    public HorizontalMetrics deepClone()
    {
        return new HorizontalMetrics(getWidthPairs(), getLeftSideBearings());
    }
}

