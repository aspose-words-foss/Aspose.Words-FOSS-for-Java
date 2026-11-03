// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/03/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This enum specifies side positions of title or legend.
/// Corresponds to the 2.24.4.19 ST_SidePos simple type [MS-ODRAWXML].
/// </summary>
/*enum*/ final class SidePosition
{
    private SidePosition(){}
    
    /// <summary>
    /// The title or legend is on the left side.
    /// </summary>
    public static final int LEFT = 0;
    /// <summary>
    /// The title or legend is on the top.
    /// </summary>
    public static final int TOP = 1;
    /// <summary>
    /// The title or legend is on the right side.
    /// </summary>
    public static final int RIGHT = 2;
    /// <summary>
    /// The title or legend is on the bottom.
    /// </summary>
    public static final int BOTTOM = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer SidePosition value.
    /// </summary>
    public static String getName(/*SidePosition*/int sidePosition)
    {
    	switch (sidePosition)
    	{
    		case LEFT: return "LEFT";
    		case TOP: return "TOP";
    		case RIGHT: return "RIGHT";
    		case BOTTOM: return "BOTTOM";
    		default: return "Unknown SidePosition value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer SidePosition value.
    /// </summary>
    public static String toString(/*SidePosition*/int sidePosition)
    {
    	switch (sidePosition)
    	{
    		case LEFT: return "Left";
    		case TOP: return "Top";
    		case RIGHT: return "Right";
    		case BOTTOM: return "Bottom";
    		default: return "Unknown SidePosition value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by SidePosition name.
    /// </summary>
    public static /*SidePosition*/int fromName(String sidePositionName)
    {
    	if ("LEFT".equals(sidePositionName)) return LEFT;
    	if ("TOP".equals(sidePositionName)) return TOP;
    	if ("RIGHT".equals(sidePositionName)) return RIGHT;
    	if ("BOTTOM".equals(sidePositionName)) return BOTTOM;
    	throw new IllegalArgumentException("Unknown SidePosition name.");
    }
    
    /// <summary>
    /// Returns array of SidePosition values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		LEFT,
    		TOP,
    		RIGHT,
    		BOTTOM,
    	};
    }

    public static final int length = 4;
}

