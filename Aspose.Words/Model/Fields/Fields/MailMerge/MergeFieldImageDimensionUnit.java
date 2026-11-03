// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/10/2012 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies an unit of an image dimension (i.e. the width or the height) used across a mail merge process.
/// </summary>
public /*enum*/ final class MergeFieldImageDimensionUnit
{
    private MergeFieldImageDimensionUnit(){}
    
    /// <summary>
    /// The point (i.e. 1/72 inch).
    /// </summary>
    public static final int POINT = 0;

    /// <summary>
    /// The percent of the original image dimension value.
    /// </summary>
    public static final int PERCENT = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MergeFieldImageDimensionUnit value.
    /// </summary>
    public static String getName(/*MergeFieldImageDimensionUnit*/int mergeFieldImageDimensionUnit)
    {
    	switch (mergeFieldImageDimensionUnit)
    	{
    		case POINT: return "POINT";
    		case PERCENT: return "PERCENT";
    		default: return "Unknown MergeFieldImageDimensionUnit value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MergeFieldImageDimensionUnit value.
    /// </summary>
    public static String toString(/*MergeFieldImageDimensionUnit*/int mergeFieldImageDimensionUnit)
    {
    	switch (mergeFieldImageDimensionUnit)
    	{
    		case POINT: return "Point";
    		case PERCENT: return "Percent";
    		default: return "Unknown MergeFieldImageDimensionUnit value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MergeFieldImageDimensionUnit name.
    /// </summary>
    public static /*MergeFieldImageDimensionUnit*/int fromName(String mergeFieldImageDimensionUnitName)
    {
    	if ("POINT".equals(mergeFieldImageDimensionUnitName)) return POINT;
    	if ("PERCENT".equals(mergeFieldImageDimensionUnitName)) return PERCENT;
    	throw new IllegalArgumentException("Unknown MergeFieldImageDimensionUnit name.");
    }
    
    /// <summary>
    /// Returns array of MergeFieldImageDimensionUnit values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		POINT,
    		PERCENT,
    	};
    }

    public static final int length = 2;
}

