// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/02/2010 by Alexey Noskov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Class represent one point from set of points, which are used to draw glyph.
/// </summary>
public class TTGlyphPoint
{
    /// <summary>
    /// Creates new instance.
    /// </summary>
    /// <param name="absoluteX">Absolute X coordinate of the point.</param>
    /// <param name="absoluteY">Absolute Y coordinate of the point.</param>
    /// <param name="isOnCurve">Flag indicated whether point is on-curve or off-curve.</param>
    /// <param name="isEndOfContour">Flag indicates whether point is end of contour.</param>
    TTGlyphPoint(int absoluteX, int absoluteY, boolean isOnCurve, boolean isEndOfContour)
    {
        setX(absoluteX);
        setY(absoluteY);
        mIsOnCurve = isOnCurve;
        mIsEndOfContour = isEndOfContour;
    }

    /// <summary>
    /// Translates point to the specified offsets.
    /// Only absolute coordinates are changed deltas remain the same.
    /// </summary>
    void translate(int dx, int dy)
    {
        setX(getX() + dx);
        setY(getY() + dy);
    }

    /// <summary>
    /// Returns absolute X coordinate of the point.
    /// </summary>
    public int getX() { return mX; }; private void setX(int value) { mX = value; };

    private int mX;

    /// <summary>
    /// Returns absolute Y coordinate of the point.
    /// </summary>
    public int getY() { return mY; }; private void setY(int value) { mY = value; };

    private int mY;

    /// <summary>
    /// Flag indicates whether this is on-curve point or off-curve.
    /// </summary>
    public boolean isOnCurve() { return mIsOnCurve; };

    private  boolean mIsOnCurve;

    /// <summary>
    /// Flag indicates whether this is point is last point of contour.
    /// </summary>
    public boolean isEndOfContour() { return mIsEndOfContour; };

    private  boolean mIsEndOfContour;

    /// <summary>
    /// Set this property if point is used as reference point upon converting glyph to path.
    /// </summary>
    public boolean isRefPoint() { return mIsRefPoint; }; public void isRefPoint(boolean value) { mIsRefPoint = value; };

    private boolean mIsRefPoint;
}

