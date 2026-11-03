// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This enum specifies the possible ways to place a picture on a data point, series, wall, or floor.
/// </summary>
/*enum*/ final class AxisOrientation
{
    private AxisOrientation(){}
    
    // Corresponds ST_Orientation simple type (5.7.3.30).

    /// <summary>
    /// Specifies that the values on the axis shall be reversed so they go from maximum to minimum.
    /// </summary>
    public static final int MAX_MIN = 0;

    /// <summary>
    /// Specifies that the axis values shall be in the usual order, minimum to maximum.
    /// </summary>
    public static final int MIN_MAX = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer AxisOrientation value.
    /// </summary>
    public static String getName(/*AxisOrientation*/int axisOrientation)
    {
    	switch (axisOrientation)
    	{
    		case MAX_MIN: return "MAX_MIN";
    		case MIN_MAX: return "MIN_MAX";
    		default: return "Unknown AxisOrientation value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer AxisOrientation value.
    /// </summary>
    public static String toString(/*AxisOrientation*/int axisOrientation)
    {
    	switch (axisOrientation)
    	{
    		case MAX_MIN: return "MaxMin";
    		case MIN_MAX: return "MinMax";
    		default: return "Unknown AxisOrientation value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by AxisOrientation name.
    /// </summary>
    public static /*AxisOrientation*/int fromName(String axisOrientationName)
    {
    	if ("MAX_MIN".equals(axisOrientationName)) return MAX_MIN;
    	if ("MIN_MAX".equals(axisOrientationName)) return MIN_MAX;
    	throw new IllegalArgumentException("Unknown AxisOrientation name.");
    }
    
    /// <summary>
    /// Returns array of AxisOrientation values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		MAX_MIN,
    		MIN_MAX,
    	};
    }

    public static final int length = 2;
}

