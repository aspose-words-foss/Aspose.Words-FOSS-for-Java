// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the possible crossing points for an axis.
/// </summary>
public /*enum*/ final class AxisCrosses
{
    private AxisCrosses(){}
    
    // Corresponds ST_Crosses simple type (5.7.3.8).
    
    /// <summary>
    /// The category axis crosses at the zero point of the value axis (if possible), or at the minimum value
    /// if the minimum is greater than zero, or at the maximum if the maximum is less than zero.
    /// </summary>
    public static final int AUTOMATIC = 0;

    /// <summary>
    /// A perpendicular axis crosses at the maximum value of the axis.
    /// </summary>
    public static final int MAXIMUM = 1;
    
    /// <summary>
    /// A perpendicular axis crosses at the minimum value of the axis.
    /// </summary>
    public static final int MINIMUM = 2;

    /// <summary>
    /// A perpendicular axis crosses at the specified value of the axis.
    /// </summary>
    public static final int CUSTOM = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer AxisCrosses value.
    /// </summary>
    public static String getName(/*AxisCrosses*/int axisCrosses)
    {
    	switch (axisCrosses)
    	{
    		case AUTOMATIC: return "AUTOMATIC";
    		case MAXIMUM: return "MAXIMUM";
    		case MINIMUM: return "MINIMUM";
    		case CUSTOM: return "CUSTOM";
    		default: return "Unknown AxisCrosses value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer AxisCrosses value.
    /// </summary>
    public static String toString(/*AxisCrosses*/int axisCrosses)
    {
    	switch (axisCrosses)
    	{
    		case AUTOMATIC: return "Automatic";
    		case MAXIMUM: return "Maximum";
    		case MINIMUM: return "Minimum";
    		case CUSTOM: return "Custom";
    		default: return "Unknown AxisCrosses value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by AxisCrosses name.
    /// </summary>
    public static /*AxisCrosses*/int fromName(String axisCrossesName)
    {
    	if ("AUTOMATIC".equals(axisCrossesName)) return AUTOMATIC;
    	if ("MAXIMUM".equals(axisCrossesName)) return MAXIMUM;
    	if ("MINIMUM".equals(axisCrossesName)) return MINIMUM;
    	if ("CUSTOM".equals(axisCrossesName)) return CUSTOM;
    	throw new IllegalArgumentException("Unknown AxisCrosses name.");
    }
    
    /// <summary>
    /// Returns array of AxisCrosses values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		AUTOMATIC,
    		MAXIMUM,
    		MINIMUM,
    		CUSTOM,
    	};
    }

    public static final int length = 4;
}

