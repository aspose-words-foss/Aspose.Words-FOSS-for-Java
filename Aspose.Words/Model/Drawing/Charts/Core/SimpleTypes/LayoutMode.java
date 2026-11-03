// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This enum specifies the possible ways to store a chart element's position.
/// Corresponds ST_LayoutMode simple type (5.7.3.20).
/// </summary>
/*enum*/ final class LayoutMode
{
    private LayoutMode(){}
    
    /// <summary>
    /// Specifies that the Width or Height shall be interpreted as the Right or Bottom of the chart element.
    /// </summary>
    public static final int EDGE = 0;
    /// <summary>
    /// Specifies that the Width or Height shall be interpreted as the Width or Height of the chart element.
    /// </summary>
    public static final int FACTOR = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer LayoutMode value.
    /// </summary>
    public static String getName(/*LayoutMode*/int layoutMode)
    {
    	switch (layoutMode)
    	{
    		case EDGE: return "EDGE";
    		case FACTOR: return "FACTOR";
    		default: return "Unknown LayoutMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer LayoutMode value.
    /// </summary>
    public static String toString(/*LayoutMode*/int layoutMode)
    {
    	switch (layoutMode)
    	{
    		case EDGE: return "Edge";
    		case FACTOR: return "Factor";
    		default: return "Unknown LayoutMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by LayoutMode name.
    /// </summary>
    public static /*LayoutMode*/int fromName(String layoutModeName)
    {
    	if ("EDGE".equals(layoutModeName)) return EDGE;
    	if ("FACTOR".equals(layoutModeName)) return FACTOR;
    	throw new IllegalArgumentException("Unknown LayoutMode name.");
    }
    
    /// <summary>
    /// Returns array of LayoutMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		EDGE,
    		FACTOR,
    	};
    }

    public static final int length = 2;
}

