// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/11/2024 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how the values ​​that specify the location of a data label - the <see cref="ChartDataLabel.Left"/> and
/// <see cref="ChartDataLabel.Top"/> properties - are interpreted.
/// </summary>
public /*enum*/ final class ChartDataLabelLocationMode
{
    private ChartDataLabelLocationMode(){}
    
    /// <summary>
    /// The location of a data label is specified by an offset from the position defined by its
    /// <see cref="ChartDataLabel.Position"/> property.
    /// </summary>
    public static final int OFFSET = 0;

    /// <summary>
    /// The location of a data label is specified using absolute coordinates, staring from the upper left corner
    /// of a chart.
    /// </summary>
    public static final int ABSOLUTE = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ChartDataLabelLocationMode value.
    /// </summary>
    public static String getName(/*ChartDataLabelLocationMode*/int chartDataLabelLocationMode)
    {
    	switch (chartDataLabelLocationMode)
    	{
    		case OFFSET: return "OFFSET";
    		case ABSOLUTE: return "ABSOLUTE";
    		default: return "Unknown ChartDataLabelLocationMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ChartDataLabelLocationMode value.
    /// </summary>
    public static String toString(/*ChartDataLabelLocationMode*/int chartDataLabelLocationMode)
    {
    	switch (chartDataLabelLocationMode)
    	{
    		case OFFSET: return "Offset";
    		case ABSOLUTE: return "Absolute";
    		default: return "Unknown ChartDataLabelLocationMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ChartDataLabelLocationMode name.
    /// </summary>
    public static /*ChartDataLabelLocationMode*/int fromName(String chartDataLabelLocationModeName)
    {
    	if ("OFFSET".equals(chartDataLabelLocationModeName)) return OFFSET;
    	if ("ABSOLUTE".equals(chartDataLabelLocationModeName)) return ABSOLUTE;
    	throw new IllegalArgumentException("Unknown ChartDataLabelLocationMode name.");
    }
    
    /// <summary>
    /// Returns array of ChartDataLabelLocationMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		OFFSET,
    		ABSOLUTE,
    	};
    }

    public static final int length = 2;
}

