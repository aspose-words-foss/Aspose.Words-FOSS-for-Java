// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/08/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Drawing.msPoint;
import com.aspose.ms.System.msString;


/// <summary>
/// Specifies a two dimensional point of a shape geometry.
/// Coordinates can contain references to shape formulas.
/// 
/// 2.2.55 POINT in the DOC SPEC.
/// </summary>
class PathPoint
{
    PathPoint()
    {
    	this(msPoint.Empty);
	
    }

    /// <summary>
    /// Creates a path point with values that are not formulas.
    /// </summary>
    PathPoint(int x, int y)
    {
    	this(msPoint.ctor(x, y));
	
    }

    /// <summary>
    /// Creates a path point with values that are not formulas.
    /// </summary>
    PathPoint(/*Point*/long point)
    {
    	this(new PathValue(msPoint.getX(point), false), new PathValue(msPoint.getY(point), false));
	
    }

    /// <summary>
    /// Creates a path point with values that could be coordinates or formulas.
    /// </summary>
    PathPoint(PathValue x, PathValue y)
    {
        mX = x;
        mY = y;
    }

    /// <summary>
    /// The X value of the point.
    /// The coordinate system used for this value is dependent on the scenario in which it is used.
    /// </summary>
    PathValue getX() { return mX; }

    /// <summary>
    /// The Y value of the point.
    /// The coordinate system used for this value is dependent on the scenario in which it is used.
    /// </summary>
    PathValue getY() { return mY; }

    public /*override*/ String toString()
    {
        return msString.format("X:{0}, Y:{1}", getX().toString(), getY().toString());
    }

    private /*final*/ PathValue mX;
    private /*final*/ PathValue mY;
}

