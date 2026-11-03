// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the display units for an axis.
/// </summary>
/// <dev>
/// Corresponds to the ST_BuiltInUnit simple type (5.7.3.6).
/// </dev>
public /*enum*/ final class AxisBuiltInUnit
{
    private AxisBuiltInUnit(){}
    
    /// <summary>
    /// Specifies the values on the chart shall displayed as is.
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    /// Specifies the values on the chart shall be divided by a user-defined divisor. This value is not supported
    /// by the new chart types of MS Office 2016.
    /// </summary>
    public static final int CUSTOM = 1;
    /// <summary>
    /// Specifies the values on the chart shall be divided by 1,000,000,000.
    /// </summary>
    public static final int BILLIONS = 2;
    /// <summary>
    /// Specifies the values on the chart shall be divided by 100,000,000.
    /// </summary>
    public static final int HUNDRED_MILLIONS = 3;
    /// <summary>
    /// Specifies the values on the chart shall be divided by 100.
    /// </summary>
    public static final int HUNDREDS = 4;
    /// <summary>
    /// Specifies the values on the chart shall be divided by 100,000.
    /// </summary>
    public static final int HUNDRED_THOUSANDS = 5;
    /// <summary>
    /// Specifies the values on the chart shall be divided by 1,000,000.
    /// </summary>
    public static final int MILLIONS = 6;
    /// <summary>
    /// Specifies the values on the chart shall be divided by 10,000,000.
    /// </summary>
    public static final int TEN_MILLIONS = 7;
    /// <summary>
    /// Specifies the values on the chart shall be divided by 10,000.
    /// </summary>
    public static final int TEN_THOUSANDS = 8;
    /// <summary>
    /// Specifies the values on the chart shall be divided by 1,000.
    /// </summary>
    public static final int THOUSANDS = 9;
    /// <summary>
    /// Specifies the values on the chart shall be divided by 1,000,000,000,0000.
    /// </summary>
    public static final int TRILLIONS = 10;
    /// <summary>
    /// Specifies the values on the chart shall be divided by 0.01. This value is supported only by the new chart
    /// types of MS Office 2016.
    /// </summary>
    public static final int PERCENTAGE = 11;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer AxisBuiltInUnit value.
    /// </summary>
    public static String getName(/*AxisBuiltInUnit*/int axisBuiltInUnit)
    {
    	switch (axisBuiltInUnit)
    	{
    		case NONE: return "NONE";
    		case CUSTOM: return "CUSTOM";
    		case BILLIONS: return "BILLIONS";
    		case HUNDRED_MILLIONS: return "HUNDRED_MILLIONS";
    		case HUNDREDS: return "HUNDREDS";
    		case HUNDRED_THOUSANDS: return "HUNDRED_THOUSANDS";
    		case MILLIONS: return "MILLIONS";
    		case TEN_MILLIONS: return "TEN_MILLIONS";
    		case TEN_THOUSANDS: return "TEN_THOUSANDS";
    		case THOUSANDS: return "THOUSANDS";
    		case TRILLIONS: return "TRILLIONS";
    		case PERCENTAGE: return "PERCENTAGE";
    		default: return "Unknown AxisBuiltInUnit value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer AxisBuiltInUnit value.
    /// </summary>
    public static String toString(/*AxisBuiltInUnit*/int axisBuiltInUnit)
    {
    	switch (axisBuiltInUnit)
    	{
    		case NONE: return "None";
    		case CUSTOM: return "Custom";
    		case BILLIONS: return "Billions";
    		case HUNDRED_MILLIONS: return "HundredMillions";
    		case HUNDREDS: return "Hundreds";
    		case HUNDRED_THOUSANDS: return "HundredThousands";
    		case MILLIONS: return "Millions";
    		case TEN_MILLIONS: return "TenMillions";
    		case TEN_THOUSANDS: return "TenThousands";
    		case THOUSANDS: return "Thousands";
    		case TRILLIONS: return "Trillions";
    		case PERCENTAGE: return "Percentage";
    		default: return "Unknown AxisBuiltInUnit value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by AxisBuiltInUnit name.
    /// </summary>
    public static /*AxisBuiltInUnit*/int fromName(String axisBuiltInUnitName)
    {
    	if ("NONE".equals(axisBuiltInUnitName)) return NONE;
    	if ("CUSTOM".equals(axisBuiltInUnitName)) return CUSTOM;
    	if ("BILLIONS".equals(axisBuiltInUnitName)) return BILLIONS;
    	if ("HUNDRED_MILLIONS".equals(axisBuiltInUnitName)) return HUNDRED_MILLIONS;
    	if ("HUNDREDS".equals(axisBuiltInUnitName)) return HUNDREDS;
    	if ("HUNDRED_THOUSANDS".equals(axisBuiltInUnitName)) return HUNDRED_THOUSANDS;
    	if ("MILLIONS".equals(axisBuiltInUnitName)) return MILLIONS;
    	if ("TEN_MILLIONS".equals(axisBuiltInUnitName)) return TEN_MILLIONS;
    	if ("TEN_THOUSANDS".equals(axisBuiltInUnitName)) return TEN_THOUSANDS;
    	if ("THOUSANDS".equals(axisBuiltInUnitName)) return THOUSANDS;
    	if ("TRILLIONS".equals(axisBuiltInUnitName)) return TRILLIONS;
    	if ("PERCENTAGE".equals(axisBuiltInUnitName)) return PERCENTAGE;
    	throw new IllegalArgumentException("Unknown AxisBuiltInUnit name.");
    }
    
    /// <summary>
    /// Returns array of AxisBuiltInUnit values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		CUSTOM,
    		BILLIONS,
    		HUNDRED_MILLIONS,
    		HUNDREDS,
    		HUNDRED_THOUSANDS,
    		MILLIONS,
    		TEN_MILLIONS,
    		TEN_THOUSANDS,
    		THOUSANDS,
    		TRILLIONS,
    		PERCENTAGE,
    	};
    }

    public static final int length = 12;
}

