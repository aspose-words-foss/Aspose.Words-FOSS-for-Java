// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/29/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Enum of <see cref="ChartMarker"/> properties.
/// </summary>
/*enum*/ final class DmlChartMarkerAttr
{
    private DmlChartMarkerAttr(){}
    
    public static final int SYMBOL = 0;
    public static final int SIZE = 1;
    public static final int SP_PR = 2;
    public static final int EXTENSIONS = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlChartMarkerAttr value.
    /// </summary>
    public static String getName(/*DmlChartMarkerAttr*/int dmlChartMarkerAttr)
    {
    	switch (dmlChartMarkerAttr)
    	{
    		case SYMBOL: return "SYMBOL";
    		case SIZE: return "SIZE";
    		case SP_PR: return "SP_PR";
    		case EXTENSIONS: return "EXTENSIONS";
    		default: return "Unknown DmlChartMarkerAttr value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlChartMarkerAttr value.
    /// </summary>
    public static String toString(/*DmlChartMarkerAttr*/int dmlChartMarkerAttr)
    {
    	switch (dmlChartMarkerAttr)
    	{
    		case SYMBOL: return "Symbol";
    		case SIZE: return "Size";
    		case SP_PR: return "SpPr";
    		case EXTENSIONS: return "Extensions";
    		default: return "Unknown DmlChartMarkerAttr value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlChartMarkerAttr name.
    /// </summary>
    public static /*DmlChartMarkerAttr*/int fromName(String dmlChartMarkerAttrName)
    {
    	if ("SYMBOL".equals(dmlChartMarkerAttrName)) return SYMBOL;
    	if ("SIZE".equals(dmlChartMarkerAttrName)) return SIZE;
    	if ("SP_PR".equals(dmlChartMarkerAttrName)) return SP_PR;
    	if ("EXTENSIONS".equals(dmlChartMarkerAttrName)) return EXTENSIONS;
    	throw new IllegalArgumentException("Unknown DmlChartMarkerAttr name.");
    }
    
    /// <summary>
    /// Returns array of DmlChartMarkerAttr values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		SYMBOL,
    		SIZE,
    		SP_PR,
    		EXTENSIONS,
    	};
    }

    public static final int length = 4;
}

