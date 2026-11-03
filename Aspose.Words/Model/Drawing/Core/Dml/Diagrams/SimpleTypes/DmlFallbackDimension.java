// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.29 ST_FallbackDimension (Fallback Dimension)
/// </summary>
/*enum*/ final class DmlFallbackDimension
{
    private DmlFallbackDimension(){}
    
    public static final int ONE_DIMENSION = 0;
    public static final int TWO_DIMENSIONS = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlFallbackDimension value.
    /// </summary>
    public static String getName(/*DmlFallbackDimension*/int dmlFallbackDimension)
    {
    	switch (dmlFallbackDimension)
    	{
    		case ONE_DIMENSION: return "ONE_DIMENSION";
    		case TWO_DIMENSIONS: return "TWO_DIMENSIONS";
    		default: return "Unknown DmlFallbackDimension value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlFallbackDimension value.
    /// </summary>
    public static String toString(/*DmlFallbackDimension*/int dmlFallbackDimension)
    {
    	switch (dmlFallbackDimension)
    	{
    		case ONE_DIMENSION: return "OneDimension";
    		case TWO_DIMENSIONS: return "TwoDimensions";
    		default: return "Unknown DmlFallbackDimension value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlFallbackDimension name.
    /// </summary>
    public static /*DmlFallbackDimension*/int fromName(String dmlFallbackDimensionName)
    {
    	if ("ONE_DIMENSION".equals(dmlFallbackDimensionName)) return ONE_DIMENSION;
    	if ("TWO_DIMENSIONS".equals(dmlFallbackDimensionName)) return TWO_DIMENSIONS;
    	throw new IllegalArgumentException("Unknown DmlFallbackDimension name.");
    }
    
    /// <summary>
    /// Returns array of DmlFallbackDimension values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ONE_DIMENSION,
    		TWO_DIMENSIONS,
    	};
    }

    public static final int length = 2;
}

