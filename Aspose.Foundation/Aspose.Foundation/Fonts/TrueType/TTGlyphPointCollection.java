// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/02/2010 by Alexey Noskov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Class represent set of points, which are used to draw glyph.
/// </summary>
class TTGlyphPointCollection
{
    public TTGlyphPointCollection()
    {
        mPoints = new ArrayList<TTGlyphPoint>();
        setXMin(Integer.MAX_VALUE);
        setXMax(Integer.MIN_VALUE);
        setYMin(Integer.MAX_VALUE);
        setYMax(Integer.MIN_VALUE);
    }

    /// <summary>
    /// Retrieves a <see cref="TTGlyphPoint"/> at the given index.
    /// Returns null if index is out of range
    /// </summary>
    /// <param name="index">An index into the collection of points.</param>
    public TTGlyphPoint get(int index) { return (index >= 0 && index < mPoints.size()) ? mPoints.get(index) : null; }

    /// <summary>
    /// Adds a new point.
    /// </summary>
    /// <param name="x">Absolute X coordinate.</param>
    /// <param name="y">Absolute Y coordinate.</param>
    /// <param name="isOnCurve">Flag indicates whether point is on curve.</param>
    /// <param name="isEndOfContour">Flag indicates whether point is end of curve.</param>
    public void add(int x, int y, boolean isOnCurve, boolean isEndOfContour)
    {
        TTGlyphPoint point = new TTGlyphPoint(x, y, isOnCurve, isEndOfContour);
        // WORDSNET-25135 There should not be two end of contour points. If such case occurs just ignore the second end of contour point.
        if (getLastPoint() != null && getLastPoint().isEndOfContour() && point.isEndOfContour())
            return;

        msArrayList.add(mPoints, point);
        setXMin(Math.min(x, getXMin()));
        setXMax(Math.max(x, getXMax()));
        setYMin(Math.min(y, getYMin()));
        setYMax(Math.max(y, getYMax()));
    }

    public void translatePoints(int xOffset, int yOffset)
    {
        for (TTGlyphPoint point : mPoints)
            point.translate(xOffset, yOffset);
    }

    public int getCount() { return mPoints.size(); }

    private TTGlyphPoint getLastPoint() { return this.get(mPoints.size() - 1); }

    public int getXMin() { return mXMin; }; private void setXMin(int value) { mXMin = value; };

    private int mXMin;
    public int getYMin() { return mYMin; }; private void setYMin(int value) { mYMin = value; };

    private int mYMin;
    public int getXMax() { return mXMax; }; private void setXMax(int value) { mXMax = value; };

    private int mXMax;
    public int getYMax() { return mYMax; }; private void setYMax(int value) { mYMax = value; };

    private int mYMax;

    private /*final*/ ArrayList<TTGlyphPoint> mPoints;
}

