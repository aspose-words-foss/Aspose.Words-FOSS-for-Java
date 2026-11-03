// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/11/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Possible title positions.
/// Position of title is not stored in the chart XML, 
/// value is calculated depending on the element the title applied to. 
/// </summary>
/*enum*/ final class TitlePosition
{
    private TitlePosition(){}
    
    // The following four positions defines default title position,
    
    /// <summary>
    /// Title is horizontally centered and positioned at top edge.
    /// </summary>
    public static final int TOP = 0;
    /// <summary>
    /// Title is horizontally centered and positioned at bottom edge.
    /// </summary>
    public static final int BOTTOM = 1;
    /// <summary>
    /// Title is vertically centered and positioned at left edge.
    /// </summary>
    public static final int LEFT = 2;
    /// <summary>
    /// Title is vertically centered and positioned at right edge.
    /// </summary>
    public static final int RIGHT = 3;

    // The following four positions are used to define default position of display units labels,
    // which behaves the same as title except of default positions.

    /// <summary>
    /// Title (Display unit label) is horizontal and positioned in top right corner.
    /// </summary>
    public static final int TOP_RIGHT = 4;
    /// <summary>
    /// Title (Display unit label) is horizontal and positioned in bottom right corner.
    /// </summary>
    public static final int BOTTOM_RIGHT = 5;
    /// <summary>
    /// Title (Display unit label) is vertical and positioned in left top corner.
    /// </summary>
    public static final int LEFT_TOP = 6;
    /// <summary>
    /// Title (Display unit label) is vertical and positioned in right top corner.
    /// </summary>
    public static final int RIGHT_TOP = 7;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TitlePosition value.
    /// </summary>
    public static String getName(/*TitlePosition*/int titlePosition)
    {
    	switch (titlePosition)
    	{
    		case TOP: return "TOP";
    		case BOTTOM: return "BOTTOM";
    		case LEFT: return "LEFT";
    		case RIGHT: return "RIGHT";
    		case TOP_RIGHT: return "TOP_RIGHT";
    		case BOTTOM_RIGHT: return "BOTTOM_RIGHT";
    		case LEFT_TOP: return "LEFT_TOP";
    		case RIGHT_TOP: return "RIGHT_TOP";
    		default: return "Unknown TitlePosition value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TitlePosition value.
    /// </summary>
    public static String toString(/*TitlePosition*/int titlePosition)
    {
    	switch (titlePosition)
    	{
    		case TOP: return "Top";
    		case BOTTOM: return "Bottom";
    		case LEFT: return "Left";
    		case RIGHT: return "Right";
    		case TOP_RIGHT: return "TopRight";
    		case BOTTOM_RIGHT: return "BottomRight";
    		case LEFT_TOP: return "LeftTop";
    		case RIGHT_TOP: return "RightTop";
    		default: return "Unknown TitlePosition value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TitlePosition name.
    /// </summary>
    public static /*TitlePosition*/int fromName(String titlePositionName)
    {
    	if ("TOP".equals(titlePositionName)) return TOP;
    	if ("BOTTOM".equals(titlePositionName)) return BOTTOM;
    	if ("LEFT".equals(titlePositionName)) return LEFT;
    	if ("RIGHT".equals(titlePositionName)) return RIGHT;
    	if ("TOP_RIGHT".equals(titlePositionName)) return TOP_RIGHT;
    	if ("BOTTOM_RIGHT".equals(titlePositionName)) return BOTTOM_RIGHT;
    	if ("LEFT_TOP".equals(titlePositionName)) return LEFT_TOP;
    	if ("RIGHT_TOP".equals(titlePositionName)) return RIGHT_TOP;
    	throw new IllegalArgumentException("Unknown TitlePosition name.");
    }
    
    /// <summary>
    /// Returns array of TitlePosition values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TOP,
    		BOTTOM,
    		LEFT,
    		RIGHT,
    		TOP_RIGHT,
    		BOTTOM_RIGHT,
    		LEFT_TOP,
    		RIGHT_TOP,
    	};
    }

    public static final int length = 8;
}

