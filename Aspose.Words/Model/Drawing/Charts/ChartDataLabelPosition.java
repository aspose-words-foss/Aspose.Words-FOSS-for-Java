// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the position for a chart data label.
/// </summary>
/// <remarks>
/// Not all series types allow you to specify label positions. And those that do, do not support all values.
/// </remarks>
/// <dev>
/// Corresponds ST_DLblPos simple type (21.2.3.11 of ISO/IEC 29500).
/// </dev>
public /*enum*/ final class ChartDataLabelPosition
{
    private ChartDataLabelPosition(){}
    
    /// <summary>
    /// Specifies that a data label should be displayed centered on a data marker.
    /// </summary>
    public static final int CENTER = 0;
    /// <summary>
    /// Specifies that a data label should be displayed to the left of a data marker.
    /// </summary>
    public static final int LEFT = 1;
    /// <summary>
    /// Specifies that a data label should be displayed to the right of a data marker.
    /// </summary>
    public static final int RIGHT = 2;
    /// <summary>
    /// Specifies that a data label should be displayed above a data marker.
    /// </summary>
    public static final int ABOVE = 3;
    /// <summary>
    /// Specifies that a data label should be displayed below a data marker.
    /// </summary>
    public static final int BELOW = 4;
    /// <summary>
    /// Specifies that a data label should be displayed inside the base of a data marker.
    /// </summary>
    public static final int INSIDE_BASE = 5;
    /// <summary>
    /// Specifies that a data label should be displayed inside the end of a data marker.
    /// </summary>
    public static final int INSIDE_END = 6;
    /// <summary>
    /// Specifies that a data label should be displayed outside the end of a data marker.
    /// </summary>
    public static final int OUTSIDE_END = 7;
    /// <summary>
    /// Specifies that a data label should be displayed in the most appropriate position.
    /// </summary>
    public static final int BEST_FIT = 8;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ChartDataLabelPosition value.
    /// </summary>
    public static String getName(/*ChartDataLabelPosition*/int chartDataLabelPosition)
    {
    	switch (chartDataLabelPosition)
    	{
    		case CENTER: return "CENTER";
    		case LEFT: return "LEFT";
    		case RIGHT: return "RIGHT";
    		case ABOVE: return "ABOVE";
    		case BELOW: return "BELOW";
    		case INSIDE_BASE: return "INSIDE_BASE";
    		case INSIDE_END: return "INSIDE_END";
    		case OUTSIDE_END: return "OUTSIDE_END";
    		case BEST_FIT: return "BEST_FIT";
    		default: return "Unknown ChartDataLabelPosition value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ChartDataLabelPosition value.
    /// </summary>
    public static String toString(/*ChartDataLabelPosition*/int chartDataLabelPosition)
    {
    	switch (chartDataLabelPosition)
    	{
    		case CENTER: return "Center";
    		case LEFT: return "Left";
    		case RIGHT: return "Right";
    		case ABOVE: return "Above";
    		case BELOW: return "Below";
    		case INSIDE_BASE: return "InsideBase";
    		case INSIDE_END: return "InsideEnd";
    		case OUTSIDE_END: return "OutsideEnd";
    		case BEST_FIT: return "BestFit";
    		default: return "Unknown ChartDataLabelPosition value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ChartDataLabelPosition name.
    /// </summary>
    public static /*ChartDataLabelPosition*/int fromName(String chartDataLabelPositionName)
    {
    	if ("CENTER".equals(chartDataLabelPositionName)) return CENTER;
    	if ("LEFT".equals(chartDataLabelPositionName)) return LEFT;
    	if ("RIGHT".equals(chartDataLabelPositionName)) return RIGHT;
    	if ("ABOVE".equals(chartDataLabelPositionName)) return ABOVE;
    	if ("BELOW".equals(chartDataLabelPositionName)) return BELOW;
    	if ("INSIDE_BASE".equals(chartDataLabelPositionName)) return INSIDE_BASE;
    	if ("INSIDE_END".equals(chartDataLabelPositionName)) return INSIDE_END;
    	if ("OUTSIDE_END".equals(chartDataLabelPositionName)) return OUTSIDE_END;
    	if ("BEST_FIT".equals(chartDataLabelPositionName)) return BEST_FIT;
    	throw new IllegalArgumentException("Unknown ChartDataLabelPosition name.");
    }
    
    /// <summary>
    /// Returns array of ChartDataLabelPosition values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		CENTER,
    		LEFT,
    		RIGHT,
    		ABOVE,
    		BELOW,
    		INSIDE_BASE,
    		INSIDE_END,
    		OUTSIDE_END,
    		BEST_FIT,
    	};
    }

    public static final int length = 9;
}

