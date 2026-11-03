// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This enum specifies the possible styles of scatter chart.
/// Corresponds ST_ScatterStyle simple type (5.7.3.40).
/// </summary>
/*enum*/ final class ScatterStyle
{
    private ScatterStyle(){}
    
    /// <summary>
    /// Specifies the points on the scatter chart shall be connected with straight lines but markers shall not be drawn.
    /// </summary>
    public static final int LINE = 0;
    /// <summary>
    /// Specifies the points on the scatter chart shall be connected with straight lines and markers shall be drawn.
    /// </summary>
    public static final int LINE_MARKER = 1;
    /// <summary>
    /// Specifies the points on the scatter chart shall not be connected with lines and markers shall be drawn.
    /// </summary>
    public static final int MARKER = 2;
    /// <summary>
    /// Specifies the points on the scatter chart shall not be connected with straight lines and markers shall not be drawn.
    /// </summary>
    public static final int NONE = 3;
    /// <summary>
    /// Specifies the points on the scatter chart shall be connected with smoothed lines and markers shall not be drawn.
    /// </summary>
    public static final int SMOOTH = 4;
    /// <summary>
    /// Specifies the points on the scatter chart shall be connected with smoothed lines and markers shall be drawn.
    /// </summary>
    public static final int SMOOTH_MARKER = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ScatterStyle value.
    /// </summary>
    public static String getName(/*ScatterStyle*/int scatterStyle)
    {
    	switch (scatterStyle)
    	{
    		case LINE: return "LINE";
    		case LINE_MARKER: return "LINE_MARKER";
    		case MARKER: return "MARKER";
    		case NONE: return "NONE";
    		case SMOOTH: return "SMOOTH";
    		case SMOOTH_MARKER: return "SMOOTH_MARKER";
    		default: return "Unknown ScatterStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ScatterStyle value.
    /// </summary>
    public static String toString(/*ScatterStyle*/int scatterStyle)
    {
    	switch (scatterStyle)
    	{
    		case LINE: return "Line";
    		case LINE_MARKER: return "LineMarker";
    		case MARKER: return "Marker";
    		case NONE: return "None";
    		case SMOOTH: return "Smooth";
    		case SMOOTH_MARKER: return "SmoothMarker";
    		default: return "Unknown ScatterStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ScatterStyle name.
    /// </summary>
    public static /*ScatterStyle*/int fromName(String scatterStyleName)
    {
    	if ("LINE".equals(scatterStyleName)) return LINE;
    	if ("LINE_MARKER".equals(scatterStyleName)) return LINE_MARKER;
    	if ("MARKER".equals(scatterStyleName)) return MARKER;
    	if ("NONE".equals(scatterStyleName)) return NONE;
    	if ("SMOOTH".equals(scatterStyleName)) return SMOOTH;
    	if ("SMOOTH_MARKER".equals(scatterStyleName)) return SMOOTH_MARKER;
    	throw new IllegalArgumentException("Unknown ScatterStyle name.");
    }
    
    /// <summary>
    /// Returns array of ScatterStyle values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		LINE,
    		LINE_MARKER,
    		MARKER,
    		NONE,
    		SMOOTH,
    		SMOOTH_MARKER,
    	};
    }

    public static final int length = 6;
}

