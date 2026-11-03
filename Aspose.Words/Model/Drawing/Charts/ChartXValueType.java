// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/08/2022 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Allows to specify type of an X value of a chart series.
/// </summary>
public /*enum*/ final class ChartXValueType
{
    private ChartXValueType(){}
    
    /// <summary>
    /// Specifies that an X value is a string category.
    /// </summary>
    public static final int STRING = 0;

    /// <summary>
    /// Specifies that an X value is a double-precision floating-point number.
    /// </summary>
    public static final int DOUBLE = 1;

    /// <summary>
    /// Specifies that an X value is a date and time of day.
    /// </summary>
    /// <dev>
    /// Values of this type are locale-specific in Word 2016 charts: correct datetime format must be specified to
    /// correctly display value/axis labels. See TestChartDataApi tests.
    /// And even if allowed, this value type may be not useful/correct for some chart types of Word 2016.
    /// </dev>
    public static final int DATE_TIME = 2;

    /// <summary>
    /// Specifies that an X value is a time of day.
    /// </summary>
    /// <dev>
    /// Values of this type are locale-specific in Word 2016 charts: correct time format must be specified to
    /// correctly display value/axis labels. See TestChartDataApi tests.
    /// And even if allowed, this value type may be not useful/correct for some chart types of Word 2016.
    /// </dev>
    public static final int TIME = 3;

    /// <summary>
    /// Specifies that an X value is a multilevel value.
    /// </summary>
    /// <seealso cref="ChartMultilevelValue"/>
    public static final int MULTILEVEL = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ChartXValueType value.
    /// </summary>
    public static String getName(/*ChartXValueType*/int chartXValueType)
    {
    	switch (chartXValueType)
    	{
    		case STRING: return "STRING";
    		case DOUBLE: return "DOUBLE";
    		case DATE_TIME: return "DATE_TIME";
    		case TIME: return "TIME";
    		case MULTILEVEL: return "MULTILEVEL";
    		default: return "Unknown ChartXValueType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ChartXValueType value.
    /// </summary>
    public static String toString(/*ChartXValueType*/int chartXValueType)
    {
    	switch (chartXValueType)
    	{
    		case STRING: return "String";
    		case DOUBLE: return "Double";
    		case DATE_TIME: return "DateTime";
    		case TIME: return "Time";
    		case MULTILEVEL: return "Multilevel";
    		default: return "Unknown ChartXValueType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ChartXValueType name.
    /// </summary>
    public static /*ChartXValueType*/int fromName(String chartXValueTypeName)
    {
    	if ("STRING".equals(chartXValueTypeName)) return STRING;
    	if ("DOUBLE".equals(chartXValueTypeName)) return DOUBLE;
    	if ("DATE_TIME".equals(chartXValueTypeName)) return DATE_TIME;
    	if ("TIME".equals(chartXValueTypeName)) return TIME;
    	if ("MULTILEVEL".equals(chartXValueTypeName)) return MULTILEVEL;
    	throw new IllegalArgumentException("Unknown ChartXValueType name.");
    }
    
    /// <summary>
    /// Returns array of ChartXValueType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		STRING,
    		DOUBLE,
    		DATE_TIME,
    		TIME,
    		MULTILEVEL,
    	};
    }

    public static final int length = 5;
}

