// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/05/2015 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;


/// <summary>
/// Specifies how a series of points are to be interpreted to construct a path.
/// 
/// 2.2.53 MSOPATHINFO in the DOC SPEC
/// 2.2.54 MSOPATHESCAPEINFO in the DOC SPEC
/// </summary>
class PathInfo
{
    PathInfo()
    {
    	this(com.aspose.words.PathType.UNKNOWN, 0);
	
    }

    PathInfo(/*PathType*/int pathType, int segmentCount)
    {
        mPathType = pathType;
        mSegmentCount = segmentCount;
    }

    /// <summary>
    /// Specifies how this part of a path is to be drawn.
    /// </summary>
    /*PathType*/int getPathType() { return mPathType; }

    /// <summary>
    /// An unsigned integer that specifies the number of segments to process.
    /// For escape path types this is the number of points (not number of segments).
    /// </summary>
    int getSegmentCount() { return mSegmentCount; }

    public /*override*/ String toString()
    {
        return msString.format("PathType: {0}, Count: {1}", getPathType(), getSegmentCount());
    }

    /// <summary>
    /// Gets the total number of points this path info uses.
    /// </summary>
    int getPointCount()
    {
        return getPointsAlways() + getPointsPerSegment() * getSegmentCount();
    }

    /// <summary>
    /// Gets the number of points this path info uses regardless of the segment count value.
    /// </summary>
    int getPointsAlways()
    {
        switch (getPathType())
        {
            case PathType.MOVE_TO:
                // This command uses 1 point, but the segment count is 0.
                return 1;
            default:
                return 0;
        }
    }

    /// <summary>
    /// Gets the number of points this path info uses per segment.
    /// </summary>
    int getPointsPerSegment()
    {
        switch (getPathType())
        {
            case PathType.LINE_TO:
                return 1;
            case PathType.CURVE_TO:
                return 3;
            case PathType.MOVE_TO:
            case PathType.CLOSE:
            case PathType.END:
                return 0;
            case PathType.ANGLE_ELLIPSE_TO:
            case PathType.ANGLE_ELLIPSE:
            case PathType.ARC_TO:
            case PathType.ARC:
            case PathType.CLOCKWISE_ARC_TO:
            case PathType.CLOCKWISE_ARC:
            case PathType.ELLIPTICAL_QUADRANT_X:
            case PathType.ELLIPTICAL_QUADRANT_Y:
            case PathType.QUADRATIC_BEZIER:
                // Although some of these commands require 3 or 4 points, they always have segment count 
                // equal to the number of points because for escape commands it is the point count.
                return 1;
            case PathType.NO_FILL:
            case PathType.NO_LINE:
            case PathType.ESCAPE_AUTO_LINE:
            case PathType.ESCAPE_AUTO_CURVE:
            case PathType.ESCAPE_CORNER_LINE:
            case PathType.ESCAPE_CORNER_CURVE:
            case PathType.ESCAPE_SMOOTH_LINE:
            case PathType.ESCAPE_SMOOTH_CURVE:
            case PathType.ESCAPE_SYMMETRIC_LINE:
            case PathType.ESCAPE_SYMMETRIC_CURVE:
            case PathType.ESCAPE_FREE_FORM:
                return 0;
            case PathType.FILL_COLOR:
            case PathType.LINE_COLOR:
                // My guess.
                return 1;
            default:
                throw new IllegalStateException("Unrecognized command in geometry path.");
        }
    }

    private /*final*/ /*PathType*/int mPathType;
    private /*final*/ int mSegmentCount;
}

