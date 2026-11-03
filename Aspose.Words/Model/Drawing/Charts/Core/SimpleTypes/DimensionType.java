// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/02/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents values of the 2.24.4.12 ST_NumericDimensionType and 2.24.4.20 ST_StringDimensionType 
/// types [MS-ODAWXML].
/// </summary>
/*enum*/ final class DimensionType
{
    private DimensionType(){}
    
    /// <summary>
    /// The dimension is a value.
    /// </summary>
    public static final int VALUE = 0;
    /// <summary>
    /// The dimension is an x-coordinate.
    /// </summary>
    public static final int X = 1;
    /// <summary>
    /// The dimension is an y-coordinate.
    /// </summary>
    public static final int Y = 2;
    /// <summary>
    /// The dimension is a size.
    /// </summary>
    public static final int SIZE = 3;
    /// <summary>
    /// The dimension is a category.
    /// </summary>
    public static final int CATEGORY = 4;
    /// <summary>
    /// The dimension is a value that is used to generate a color in a map chart.
    /// </summary>
    public static final int COLOR_VALUE = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DimensionType value.
    /// </summary>
    public static String getName(/*DimensionType*/int dimensionType)
    {
    	switch (dimensionType)
    	{
    		case VALUE: return "VALUE";
    		case X: return "X";
    		case Y: return "Y";
    		case SIZE: return "SIZE";
    		case CATEGORY: return "CATEGORY";
    		case COLOR_VALUE: return "COLOR_VALUE";
    		default: return "Unknown DimensionType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DimensionType value.
    /// </summary>
    public static String toString(/*DimensionType*/int dimensionType)
    {
    	switch (dimensionType)
    	{
    		case VALUE: return "Value";
    		case X: return "X";
    		case Y: return "Y";
    		case SIZE: return "Size";
    		case CATEGORY: return "Category";
    		case COLOR_VALUE: return "ColorValue";
    		default: return "Unknown DimensionType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DimensionType name.
    /// </summary>
    public static /*DimensionType*/int fromName(String dimensionTypeName)
    {
    	if ("VALUE".equals(dimensionTypeName)) return VALUE;
    	if ("X".equals(dimensionTypeName)) return X;
    	if ("Y".equals(dimensionTypeName)) return Y;
    	if ("SIZE".equals(dimensionTypeName)) return SIZE;
    	if ("CATEGORY".equals(dimensionTypeName)) return CATEGORY;
    	if ("COLOR_VALUE".equals(dimensionTypeName)) return COLOR_VALUE;
    	throw new IllegalArgumentException("Unknown DimensionType name.");
    }
    
    /// <summary>
    /// Returns array of DimensionType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		VALUE,
    		X,
    		Y,
    		SIZE,
    		CATEGORY,
    		COLOR_VALUE,
    	};
    }

    public static final int length = 6;
}

