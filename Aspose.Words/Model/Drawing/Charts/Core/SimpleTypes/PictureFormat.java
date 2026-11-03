// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This enum specifies the possible ways to place a picture on a data point, series, wall, or floor.
/// Corresponds ST_PictureFormat simple type (5.7.3.35).
/// </summary>
/*enum*/ final class PictureFormat
{
    private PictureFormat(){}
    
    /// <summary>
    /// Specifies that the picture shall be stacked.
    /// </summary>
    public static final int STACK = 0;
    /// <summary>
    /// Specifies that the picture shall be stacked after being scaled so that it's height is one Picture Stack Unit. 
    /// Does not apply to walls or floor.
    /// </summary>
    public static final int STACK_SCALE = 1;
    /// <summary>
    /// Specifies that the picture shall be anisotropic stretched to fill the data point, series, wall or floor.
    /// </summary>
    public static final int STRETCH = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer PictureFormat value.
    /// </summary>
    public static String getName(/*PictureFormat*/int pictureFormat)
    {
    	switch (pictureFormat)
    	{
    		case STACK: return "STACK";
    		case STACK_SCALE: return "STACK_SCALE";
    		case STRETCH: return "STRETCH";
    		default: return "Unknown PictureFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer PictureFormat value.
    /// </summary>
    public static String toString(/*PictureFormat*/int pictureFormat)
    {
    	switch (pictureFormat)
    	{
    		case STACK: return "Stack";
    		case STACK_SCALE: return "StackScale";
    		case STRETCH: return "Stretch";
    		default: return "Unknown PictureFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by PictureFormat name.
    /// </summary>
    public static /*PictureFormat*/int fromName(String pictureFormatName)
    {
    	if ("STACK".equals(pictureFormatName)) return STACK;
    	if ("STACK_SCALE".equals(pictureFormatName)) return STACK_SCALE;
    	if ("STRETCH".equals(pictureFormatName)) return STRETCH;
    	throw new IllegalArgumentException("Unknown PictureFormat name.");
    }
    
    /// <summary>
    /// Returns array of PictureFormat values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		STACK,
    		STACK_SCALE,
    		STRETCH,
    	};
    }

    public static final int length = 3;
}

