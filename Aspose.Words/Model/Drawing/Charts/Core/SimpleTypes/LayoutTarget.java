// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This enum specifies the possible ways to layout the plot area.
/// Corresponds ST_LayoutTarget simple type (5.7.3.21).
/// </summary>
/*enum*/ final class LayoutTarget
{
    private LayoutTarget(){}
    
    /// <summary>
    /// Specifies that the plot area size shall determine the size of the plot area, 
    /// not including the tick marks and axis labels.
    /// </summary>
    public static final int INNER = 0;
    /// <summary>
    /// Specifies that the plot area size shall determine the size of the plot area, 
    /// the tick marks, and the axis labels.
    /// </summary>
    public static final int OUTER = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer LayoutTarget value.
    /// </summary>
    public static String getName(/*LayoutTarget*/int layoutTarget)
    {
    	switch (layoutTarget)
    	{
    		case INNER: return "INNER";
    		case OUTER: return "OUTER";
    		default: return "Unknown LayoutTarget value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer LayoutTarget value.
    /// </summary>
    public static String toString(/*LayoutTarget*/int layoutTarget)
    {
    	switch (layoutTarget)
    	{
    		case INNER: return "Inner";
    		case OUTER: return "Outer";
    		default: return "Unknown LayoutTarget value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by LayoutTarget name.
    /// </summary>
    public static /*LayoutTarget*/int fromName(String layoutTargetName)
    {
    	if ("INNER".equals(layoutTargetName)) return INNER;
    	if ("OUTER".equals(layoutTargetName)) return OUTER;
    	throw new IllegalArgumentException("Unknown LayoutTarget name.");
    }
    
    /// <summary>
    /// Returns array of LayoutTarget values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		INNER,
    		OUTER,
    	};
    }

    public static final int length = 2;
}

