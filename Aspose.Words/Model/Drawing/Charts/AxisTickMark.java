// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the possible positions for tick marks.
/// </summary>
public /*enum*/ final class AxisTickMark
{
    private AxisTickMark(){}
    
    // Corresponds ST_TickMark simple type (5.7.3.49).

    /// <summary>
    /// Specifies that the tick marks shall cross the axis.
    /// </summary>
    public static final int CROSS = 0;

    /// <summary>
    /// Specifies that the tick marks shall be inside the plot area.
    /// </summary>
    public static final int INSIDE = 1;

    /// <summary>
    /// Specifies that the tick marks shall be outside the plot area.
    /// </summary>
    public static final int OUTSIDE = 2;

    /// <summary>
    /// Specifies that there shall be no tick marks.
    /// </summary>
    public static final int NONE = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer AxisTickMark value.
    /// </summary>
    public static String getName(/*AxisTickMark*/int axisTickMark)
    {
    	switch (axisTickMark)
    	{
    		case CROSS: return "CROSS";
    		case INSIDE: return "INSIDE";
    		case OUTSIDE: return "OUTSIDE";
    		case NONE: return "NONE";
    		default: return "Unknown AxisTickMark value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer AxisTickMark value.
    /// </summary>
    public static String toString(/*AxisTickMark*/int axisTickMark)
    {
    	switch (axisTickMark)
    	{
    		case CROSS: return "Cross";
    		case INSIDE: return "Inside";
    		case OUTSIDE: return "Outside";
    		case NONE: return "None";
    		default: return "Unknown AxisTickMark value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by AxisTickMark name.
    /// </summary>
    public static /*AxisTickMark*/int fromName(String axisTickMarkName)
    {
    	if ("CROSS".equals(axisTickMarkName)) return CROSS;
    	if ("INSIDE".equals(axisTickMarkName)) return INSIDE;
    	if ("OUTSIDE".equals(axisTickMarkName)) return OUTSIDE;
    	if ("NONE".equals(axisTickMarkName)) return NONE;
    	throw new IllegalArgumentException("Unknown AxisTickMark name.");
    }
    
    /// <summary>
    /// Returns array of AxisTickMark values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		CROSS,
    		INSIDE,
    		OUTSIDE,
    		NONE,
    	};
    }

    public static final int length = 4;
}

