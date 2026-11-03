// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/08/2022 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Allows to specify type of an Y value of a chart series.
/// </summary>
public /*enum*/ final class ChartYValueType
{
    private ChartYValueType(){}
    
    /// <summary>
    /// Specifies that an Y value is a double-precision floating-point number.
    /// </summary>
    public static final int DOUBLE = 0;

    /// <summary>
    /// Specifies that an Y value is a date and time of day.
    /// </summary>
    /// <dev>
    /// Values of this type are locale-specific in Word 2016 charts: correct datetime format must be specified to
    /// correctly display value/axis labels. See TestChartDataApi tests.
    /// And even if allowed, this value type may be not useful/correct for some chart types.
    /// </dev>
    public static final int DATE_TIME = 1;

    /// <summary>
    /// Specifies that an X value is a time of day.
    /// </summary>
    /// <dev>
    /// Values of this type are locale-specific in Word 2016 charts: correct time format must be specified to
    /// correctly display value/axis labels. See TestChartDataApi tests.
    /// And even if allowed, this value type may be not useful/correct for some chart types.
    /// </dev>
    public static final int TIME = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ChartYValueType value.
    /// </summary>
    public static String getName(/*ChartYValueType*/int chartYValueType)
    {
    	switch (chartYValueType)
    	{
    		case DOUBLE: return "DOUBLE";
    		case DATE_TIME: return "DATE_TIME";
    		case TIME: return "TIME";
    		default: return "Unknown ChartYValueType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ChartYValueType value.
    /// </summary>
    public static String toString(/*ChartYValueType*/int chartYValueType)
    {
    	switch (chartYValueType)
    	{
    		case DOUBLE: return "Double";
    		case DATE_TIME: return "DateTime";
    		case TIME: return "Time";
    		default: return "Unknown ChartYValueType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ChartYValueType name.
    /// </summary>
    public static /*ChartYValueType*/int fromName(String chartYValueTypeName)
    {
    	if ("DOUBLE".equals(chartYValueTypeName)) return DOUBLE;
    	if ("DATE_TIME".equals(chartYValueTypeName)) return DATE_TIME;
    	if ("TIME".equals(chartYValueTypeName)) return TIME;
    	throw new IllegalArgumentException("Unknown ChartYValueType name.");
    }
    
    /// <summary>
    /// Returns array of ChartYValueType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DOUBLE,
    		DATE_TIME,
    		TIME,
    	};
    }

    public static final int length = 3;
}

