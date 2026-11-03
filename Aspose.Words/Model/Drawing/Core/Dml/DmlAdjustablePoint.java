// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/10/2016 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Position coordinate.
/// This class is common for spec parts 20.1.9.17 pos (Shape Position Coordinate) 
/// and 20.1.9.20 pt (Shape Path Point).
/// </summary>
class DmlAdjustablePoint implements Cloneable
{
    /// <summary>
    /// Constructor.
    /// </summary>
    /// <param name="x">The x value. Can be a guide name or a number.</param>
    /// <param name="y">The y.  Can be a guide name or a number.</param>
    DmlAdjustablePoint(String x, String y)
    {
        setX(new DmlAdjustableCoordinate(x));
        setY(new DmlAdjustableCoordinate(y));
    }

    /// <summary>
    /// Constructor.
    /// </summary>
    /// <param name="x">The x.</param>
    /// <param name="y">The y.</param>
    DmlAdjustablePoint(double x, double y)
    {
        setX(new DmlAdjustableCoordinate(x));
        setY(new DmlAdjustableCoordinate(y));
    }

    /// <summary>
    /// Constructor.
    /// </summary>
    DmlAdjustablePoint()
    {
        setX(new DmlAdjustableCoordinate(0.0));
        setY(new DmlAdjustableCoordinate(0.0));
    }

    /// <summary>
    /// Clones this instance of <see cref="DmlAdjustablePoint"/>.
    /// </summary>
    DmlAdjustablePoint deepClone()
    {
        DmlAdjustablePoint lhs = (DmlAdjustablePoint)memberwiseClone();

        lhs.mX = mX.deepClone();
        lhs.mY = mY.deepClone();

        return lhs;
    }

    /// <summary>
    /// Gets or sets the X coordinate.
    /// </summary>
    DmlAdjustableCoordinate getX() { return mX; }
    void setX(DmlAdjustableCoordinate value) { mX = (value != null) ? value : new DmlAdjustableCoordinate(0.0); }

    /// <summary>
    /// Gets or sets the Y coordinate.
    /// </summary>
    DmlAdjustableCoordinate getY() { return mY; }
    void setY(DmlAdjustableCoordinate value) { mY = (value != null) ? value : new DmlAdjustableCoordinate(0.0); }

    private DmlAdjustableCoordinate mX;
    private DmlAdjustableCoordinate mY;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

