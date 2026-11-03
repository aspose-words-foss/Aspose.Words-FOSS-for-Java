// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/03/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This enum specifies the interval closed side.
/// Corresponds to the 2.24.4.11 ST_IntervalClosedSide simple type [MS-ODRAWXML].
/// </summary>
/*enum*/ final class IntervalClosedSide
{
    private IntervalClosedSide(){}
    
    /// <summary>
    /// The interval is closed on the left side.
    /// </summary>
    public static final int LEFT = 0;

    /// <summary>
    /// The interval is closed on the right side.
    /// </summary>
    public static final int RIGHT = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer IntervalClosedSide value.
    /// </summary>
    public static String getName(/*IntervalClosedSide*/int intervalClosedSide)
    {
    	switch (intervalClosedSide)
    	{
    		case LEFT: return "LEFT";
    		case RIGHT: return "RIGHT";
    		default: return "Unknown IntervalClosedSide value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer IntervalClosedSide value.
    /// </summary>
    public static String toString(/*IntervalClosedSide*/int intervalClosedSide)
    {
    	switch (intervalClosedSide)
    	{
    		case LEFT: return "Left";
    		case RIGHT: return "Right";
    		default: return "Unknown IntervalClosedSide value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by IntervalClosedSide name.
    /// </summary>
    public static /*IntervalClosedSide*/int fromName(String intervalClosedSideName)
    {
    	if ("LEFT".equals(intervalClosedSideName)) return LEFT;
    	if ("RIGHT".equals(intervalClosedSideName)) return RIGHT;
    	throw new IllegalArgumentException("Unknown IntervalClosedSide name.");
    }
    
    /// <summary>
    /// Returns array of IntervalClosedSide values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		LEFT,
    		RIGHT,
    	};
    }

    public static final int length = 2;
}

