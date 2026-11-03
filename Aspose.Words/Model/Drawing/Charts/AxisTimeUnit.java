// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the unit of time for axes.
/// </summary>
public /*enum*/ final class AxisTimeUnit
{
    private AxisTimeUnit(){}
    
    // Corresponds ST_TimeUnit simple type (5.7.3.50).

    /// <summary>
    /// Specifies that unit was not set explicitly and default value should be used.
    /// </summary>
    public static final int AUTOMATIC = 0;

    /// <summary>
    /// Specifies that the chart data shall be shown in days.
    /// </summary>
    public static final int DAYS = 1;

    /// <summary>
    /// Specifies that the chart data shall be shown in months.
    /// </summary>
    public static final int MONTHS = 2;

    /// <summary>
    /// Specifies that the chart data shall be shown in years.
    /// </summary>
    public static final int YEARS = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer AxisTimeUnit value.
    /// </summary>
    public static String getName(/*AxisTimeUnit*/int axisTimeUnit)
    {
    	switch (axisTimeUnit)
    	{
    		case AUTOMATIC: return "AUTOMATIC";
    		case DAYS: return "DAYS";
    		case MONTHS: return "MONTHS";
    		case YEARS: return "YEARS";
    		default: return "Unknown AxisTimeUnit value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer AxisTimeUnit value.
    /// </summary>
    public static String toString(/*AxisTimeUnit*/int axisTimeUnit)
    {
    	switch (axisTimeUnit)
    	{
    		case AUTOMATIC: return "Automatic";
    		case DAYS: return "Days";
    		case MONTHS: return "Months";
    		case YEARS: return "Years";
    		default: return "Unknown AxisTimeUnit value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by AxisTimeUnit name.
    /// </summary>
    public static /*AxisTimeUnit*/int fromName(String axisTimeUnitName)
    {
    	if ("AUTOMATIC".equals(axisTimeUnitName)) return AUTOMATIC;
    	if ("DAYS".equals(axisTimeUnitName)) return DAYS;
    	if ("MONTHS".equals(axisTimeUnitName)) return MONTHS;
    	if ("YEARS".equals(axisTimeUnitName)) return YEARS;
    	throw new IllegalArgumentException("Unknown AxisTimeUnit name.");
    }
    
    /// <summary>
    /// Returns array of AxisTimeUnit values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		AUTOMATIC,
    		DAYS,
    		MONTHS,
    		YEARS,
    	};
    }

    public static final int length = 4;
}

