// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/29/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Enum of <see cref="ChartDataPoint"/> properties.
/// </summary>
/*enum*/ final class DmlChartDataPointAttr
{
    private DmlChartDataPointAttr(){}
    
    public static final int INDEX = 0;
    public static final int EXPLOSION = 1;
    public static final int INVERT_IF_NEGATIVE = 2;
    public static final int BUBBLE_3_D = 3;
    public static final int MARKER = 4;
    public static final int SP_PR = 5;
    public static final int PICTURE_OPTIONS = 6;
    public static final int EXTENSIONS = 7;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlChartDataPointAttr value.
    /// </summary>
    public static String getName(/*DmlChartDataPointAttr*/int dmlChartDataPointAttr)
    {
    	switch (dmlChartDataPointAttr)
    	{
    		case INDEX: return "INDEX";
    		case EXPLOSION: return "EXPLOSION";
    		case INVERT_IF_NEGATIVE: return "INVERT_IF_NEGATIVE";
    		case BUBBLE_3_D: return "BUBBLE_3_D";
    		case MARKER: return "MARKER";
    		case SP_PR: return "SP_PR";
    		case PICTURE_OPTIONS: return "PICTURE_OPTIONS";
    		case EXTENSIONS: return "EXTENSIONS";
    		default: return "Unknown DmlChartDataPointAttr value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlChartDataPointAttr value.
    /// </summary>
    public static String toString(/*DmlChartDataPointAttr*/int dmlChartDataPointAttr)
    {
    	switch (dmlChartDataPointAttr)
    	{
    		case INDEX: return "Index";
    		case EXPLOSION: return "Explosion";
    		case INVERT_IF_NEGATIVE: return "InvertIfNegative";
    		case BUBBLE_3_D: return "Bubble3D";
    		case MARKER: return "Marker";
    		case SP_PR: return "SpPr";
    		case PICTURE_OPTIONS: return "PictureOptions";
    		case EXTENSIONS: return "Extensions";
    		default: return "Unknown DmlChartDataPointAttr value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlChartDataPointAttr name.
    /// </summary>
    public static /*DmlChartDataPointAttr*/int fromName(String dmlChartDataPointAttrName)
    {
    	if ("INDEX".equals(dmlChartDataPointAttrName)) return INDEX;
    	if ("EXPLOSION".equals(dmlChartDataPointAttrName)) return EXPLOSION;
    	if ("INVERT_IF_NEGATIVE".equals(dmlChartDataPointAttrName)) return INVERT_IF_NEGATIVE;
    	if ("BUBBLE_3_D".equals(dmlChartDataPointAttrName)) return BUBBLE_3_D;
    	if ("MARKER".equals(dmlChartDataPointAttrName)) return MARKER;
    	if ("SP_PR".equals(dmlChartDataPointAttrName)) return SP_PR;
    	if ("PICTURE_OPTIONS".equals(dmlChartDataPointAttrName)) return PICTURE_OPTIONS;
    	if ("EXTENSIONS".equals(dmlChartDataPointAttrName)) return EXTENSIONS;
    	throw new IllegalArgumentException("Unknown DmlChartDataPointAttr name.");
    }
    
    /// <summary>
    /// Returns array of DmlChartDataPointAttr values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		INDEX,
    		EXPLOSION,
    		INVERT_IF_NEGATIVE,
    		BUBBLE_3_D,
    		MARKER,
    		SP_PR,
    		PICTURE_OPTIONS,
    		EXTENSIONS,
    	};
    }

    public static final int length = 8;
}

