// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/22/2020 by Artem Ptitsin.

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Defines layout of the watermark relative to the watermark center.
/// </summary>
public /*enum*/ final class WatermarkLayout
{
    private WatermarkLayout(){}
    
    /// <summary>
    /// Horizontal watermark layout. Corresponds to 0 degrees of rotation.
    /// </summary>
    public static final int HORIZONTAL = 0;

    /// <summary>
    /// Diagonal watermark layout. Corresponds to 315 degrees of rotation.
    /// </summary>
    public static final int DIAGONAL = 315;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer WatermarkLayout value.
    /// </summary>
    public static String getName(/*WatermarkLayout*/int watermarkLayout)
    {
    	switch (watermarkLayout)
    	{
    		case HORIZONTAL: return "HORIZONTAL";
    		case DIAGONAL: return "DIAGONAL";
    		default: return "Unknown WatermarkLayout value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer WatermarkLayout value.
    /// </summary>
    public static String toString(/*WatermarkLayout*/int watermarkLayout)
    {
    	switch (watermarkLayout)
    	{
    		case HORIZONTAL: return "Horizontal";
    		case DIAGONAL: return "Diagonal";
    		default: return "Unknown WatermarkLayout value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by WatermarkLayout name.
    /// </summary>
    public static /*WatermarkLayout*/int fromName(String watermarkLayoutName)
    {
    	if ("HORIZONTAL".equals(watermarkLayoutName)) return HORIZONTAL;
    	if ("DIAGONAL".equals(watermarkLayoutName)) return DIAGONAL;
    	throw new IllegalArgumentException("Unknown WatermarkLayout name.");
    }
    
    /// <summary>
    /// Returns array of WatermarkLayout values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		HORIZONTAL,
    		DIAGONAL,
    	};
    }

    public static final int length = 2;
}

