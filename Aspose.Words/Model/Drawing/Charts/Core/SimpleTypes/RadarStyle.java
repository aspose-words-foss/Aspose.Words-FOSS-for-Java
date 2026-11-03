// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This enum specifies the possible styles of radar chart.
/// Corresponds ST_RadarStyle simple style (5.7.3.37).
/// </summary>
/*enum*/ final class RadarStyle
{
    private RadarStyle(){}
    
    /// <summary>
    /// Specifies that the radar chart shall be filled and have lines but no markers.
    /// </summary>
    public static final int FILLED = 0;
    /// <summary>
    /// Specifies that the radar chart shall have lines and markers but no fill.
    /// </summary>
    public static final int MARKER = 1;
    /// <summary>
    /// Specifies that the radar chart shall have lines but no markers and no fill.
    /// </summary>
    public static final int STANDARD = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer RadarStyle value.
    /// </summary>
    public static String getName(/*RadarStyle*/int radarStyle)
    {
    	switch (radarStyle)
    	{
    		case FILLED: return "FILLED";
    		case MARKER: return "MARKER";
    		case STANDARD: return "STANDARD";
    		default: return "Unknown RadarStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer RadarStyle value.
    /// </summary>
    public static String toString(/*RadarStyle*/int radarStyle)
    {
    	switch (radarStyle)
    	{
    		case FILLED: return "Filled";
    		case MARKER: return "Marker";
    		case STANDARD: return "Standard";
    		default: return "Unknown RadarStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by RadarStyle name.
    /// </summary>
    public static /*RadarStyle*/int fromName(String radarStyleName)
    {
    	if ("FILLED".equals(radarStyleName)) return FILLED;
    	if ("MARKER".equals(radarStyleName)) return MARKER;
    	if ("STANDARD".equals(radarStyleName)) return STANDARD;
    	throw new IllegalArgumentException("Unknown RadarStyle name.");
    }
    
    /// <summary>
    /// Returns array of RadarStyle values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		FILLED,
    		MARKER,
    		STANDARD,
    	};
    }

    public static final int length = 3;
}

