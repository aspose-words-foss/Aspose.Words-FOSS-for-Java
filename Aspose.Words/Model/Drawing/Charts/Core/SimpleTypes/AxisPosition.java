// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This enum specifies the possible positions for an axis.
/// Corresponds ST_AxPos (Axis Position) type (5.7.3.2).
/// </summary>
/*enum*/ final class AxisPosition
{
    private AxisPosition(){}
    
    public static final int BOTTOM = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int TOP = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer AxisPosition value.
    /// </summary>
    public static String getName(/*AxisPosition*/int axisPosition)
    {
    	switch (axisPosition)
    	{
    		case BOTTOM: return "BOTTOM";
    		case LEFT: return "LEFT";
    		case RIGHT: return "RIGHT";
    		case TOP: return "TOP";
    		default: return "Unknown AxisPosition value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer AxisPosition value.
    /// </summary>
    public static String toString(/*AxisPosition*/int axisPosition)
    {
    	switch (axisPosition)
    	{
    		case BOTTOM: return "Bottom";
    		case LEFT: return "Left";
    		case RIGHT: return "Right";
    		case TOP: return "Top";
    		default: return "Unknown AxisPosition value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by AxisPosition name.
    /// </summary>
    public static /*AxisPosition*/int fromName(String axisPositionName)
    {
    	if ("BOTTOM".equals(axisPositionName)) return BOTTOM;
    	if ("LEFT".equals(axisPositionName)) return LEFT;
    	if ("RIGHT".equals(axisPositionName)) return RIGHT;
    	if ("TOP".equals(axisPositionName)) return TOP;
    	throw new IllegalArgumentException("Unknown AxisPosition name.");
    }
    
    /// <summary>
    /// Returns array of AxisPosition values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BOTTOM,
    		LEFT,
    		RIGHT,
    		TOP,
    	};
    }

    public static final int length = 4;
}

