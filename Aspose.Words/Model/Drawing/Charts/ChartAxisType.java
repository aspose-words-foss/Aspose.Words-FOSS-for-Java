// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies type of chart axis.
/// </summary>
public /*enum*/ final class ChartAxisType
{
    private ChartAxisType(){}
    
    /// <summary>
    /// Category axis of a chart.
    /// </summary>
    public static final int CATEGORY = 0;

    /// <summary>
    /// Series axis of a chart.
    /// </summary>
    public static final int SERIES = 1;

    /// <summary>
    /// Value axis of a chart.
    /// </summary>
    public static final int VALUE = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ChartAxisType value.
    /// </summary>
    public static String getName(/*ChartAxisType*/int chartAxisType)
    {
    	switch (chartAxisType)
    	{
    		case CATEGORY: return "CATEGORY";
    		case SERIES: return "SERIES";
    		case VALUE: return "VALUE";
    		default: return "Unknown ChartAxisType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ChartAxisType value.
    /// </summary>
    public static String toString(/*ChartAxisType*/int chartAxisType)
    {
    	switch (chartAxisType)
    	{
    		case CATEGORY: return "Category";
    		case SERIES: return "Series";
    		case VALUE: return "Value";
    		default: return "Unknown ChartAxisType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ChartAxisType name.
    /// </summary>
    public static /*ChartAxisType*/int fromName(String chartAxisTypeName)
    {
    	if ("CATEGORY".equals(chartAxisTypeName)) return CATEGORY;
    	if ("SERIES".equals(chartAxisTypeName)) return SERIES;
    	if ("VALUE".equals(chartAxisTypeName)) return VALUE;
    	throw new IllegalArgumentException("Unknown ChartAxisType name.");
    }
    
    /// <summary>
    /// Returns array of ChartAxisType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		CATEGORY,
    		SERIES,
    		VALUE,
    	};
    }

    public static final int length = 3;
}

