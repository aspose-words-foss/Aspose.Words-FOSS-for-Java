// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/11/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/*enum*/ final class DmlChartUserShapeAnchorType
{
    private DmlChartUserShapeAnchorType(){}
    
    public static final int ABSOLUTE = 0;
    public static final int RELATIVE = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlChartUserShapeAnchorType value.
    /// </summary>
    public static String getName(/*DmlChartUserShapeAnchorType*/int dmlChartUserShapeAnchorType)
    {
    	switch (dmlChartUserShapeAnchorType)
    	{
    		case ABSOLUTE: return "ABSOLUTE";
    		case RELATIVE: return "RELATIVE";
    		default: return "Unknown DmlChartUserShapeAnchorType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlChartUserShapeAnchorType value.
    /// </summary>
    public static String toString(/*DmlChartUserShapeAnchorType*/int dmlChartUserShapeAnchorType)
    {
    	switch (dmlChartUserShapeAnchorType)
    	{
    		case ABSOLUTE: return "Absolute";
    		case RELATIVE: return "Relative";
    		default: return "Unknown DmlChartUserShapeAnchorType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlChartUserShapeAnchorType name.
    /// </summary>
    public static /*DmlChartUserShapeAnchorType*/int fromName(String dmlChartUserShapeAnchorTypeName)
    {
    	if ("ABSOLUTE".equals(dmlChartUserShapeAnchorTypeName)) return ABSOLUTE;
    	if ("RELATIVE".equals(dmlChartUserShapeAnchorTypeName)) return RELATIVE;
    	throw new IllegalArgumentException("Unknown DmlChartUserShapeAnchorType name.");
    }
    
    /// <summary>
    /// Returns array of DmlChartUserShapeAnchorType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ABSOLUTE,
    		RELATIVE,
    	};
    }

    public static final int length = 2;
}

