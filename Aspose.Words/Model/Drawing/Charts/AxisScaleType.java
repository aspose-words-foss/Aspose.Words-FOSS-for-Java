// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/08/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the possible scale types for an axis.
/// </summary>
public /*enum*/ final class AxisScaleType
{
    private AxisScaleType(){}
    
    /// <summary>
    /// Linear scaling.
    /// </summary>
    public static final int LINEAR = 0; 

    /// <summary>
    /// Logarithmic scaling.
    /// </summary>
    public static final int LOGARITHMIC = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer AxisScaleType value.
    /// </summary>
    public static String getName(/*AxisScaleType*/int axisScaleType)
    {
    	switch (axisScaleType)
    	{
    		case LINEAR: return "LINEAR";
    		case LOGARITHMIC: return "LOGARITHMIC";
    		default: return "Unknown AxisScaleType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer AxisScaleType value.
    /// </summary>
    public static String toString(/*AxisScaleType*/int axisScaleType)
    {
    	switch (axisScaleType)
    	{
    		case LINEAR: return "Linear";
    		case LOGARITHMIC: return "Logarithmic";
    		default: return "Unknown AxisScaleType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by AxisScaleType name.
    /// </summary>
    public static /*AxisScaleType*/int fromName(String axisScaleTypeName)
    {
    	if ("LINEAR".equals(axisScaleTypeName)) return LINEAR;
    	if ("LOGARITHMIC".equals(axisScaleTypeName)) return LOGARITHMIC;
    	throw new IllegalArgumentException("Unknown AxisScaleType name.");
    }
    
    /// <summary>
    /// Returns array of AxisScaleType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		LINEAR,
    		LOGARITHMIC,
    	};
    }

    public static final int length = 2;
}

