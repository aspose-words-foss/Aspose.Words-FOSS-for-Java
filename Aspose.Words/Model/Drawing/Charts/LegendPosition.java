// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the possible positions for a chart legend.
/// </summary>
public /*enum*/ final class LegendPosition
{
    private LegendPosition(){}
    
    // Corresponds ST_LegendPos simple type (5.7.3.24).

    /// <summary>
    /// No legend will be shown for the chart.
    /// </summary>
    public static final int NONE = 0;

    /// <summary>
    /// Specifies that the legend shall be drawn at the bottom of the chart.
    /// </summary>
    public static final int BOTTOM = 1;

    /// <summary>
    /// Specifies that the legend shall be drawn at the left of the chart.
    /// </summary>
    public static final int LEFT = 2;

    /// <summary>
    /// Specifies that the legend shall be drawn at the right of the chart.
    /// </summary>
    public static final int RIGHT = 3;

    /// <summary>
    /// Specifies that the legend shall be drawn at the top of the chart.
    /// </summary>
    public static final int TOP = 4;

    /// <summary>
    /// Specifies that the legend shall be drawn at the top right of the chart.
    /// </summary>
    public static final int TOP_RIGHT = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer LegendPosition value.
    /// </summary>
    public static String getName(/*LegendPosition*/int legendPosition)
    {
    	switch (legendPosition)
    	{
    		case NONE: return "NONE";
    		case BOTTOM: return "BOTTOM";
    		case LEFT: return "LEFT";
    		case RIGHT: return "RIGHT";
    		case TOP: return "TOP";
    		case TOP_RIGHT: return "TOP_RIGHT";
    		default: return "Unknown LegendPosition value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer LegendPosition value.
    /// </summary>
    public static String toString(/*LegendPosition*/int legendPosition)
    {
    	switch (legendPosition)
    	{
    		case NONE: return "None";
    		case BOTTOM: return "Bottom";
    		case LEFT: return "Left";
    		case RIGHT: return "Right";
    		case TOP: return "Top";
    		case TOP_RIGHT: return "TopRight";
    		default: return "Unknown LegendPosition value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by LegendPosition name.
    /// </summary>
    public static /*LegendPosition*/int fromName(String legendPositionName)
    {
    	if ("NONE".equals(legendPositionName)) return NONE;
    	if ("BOTTOM".equals(legendPositionName)) return BOTTOM;
    	if ("LEFT".equals(legendPositionName)) return LEFT;
    	if ("RIGHT".equals(legendPositionName)) return RIGHT;
    	if ("TOP".equals(legendPositionName)) return TOP;
    	if ("TOP_RIGHT".equals(legendPositionName)) return TOP_RIGHT;
    	throw new IllegalArgumentException("Unknown LegendPosition name.");
    }
    
    /// <summary>
    /// Returns array of LegendPosition values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		BOTTOM,
    		LEFT,
    		RIGHT,
    		TOP,
    		TOP_RIGHT,
    	};
    }

    public static final int length = 6;
}

