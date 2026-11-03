// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/03/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// The direction(s) in which to flip the gradient while tiling. 
/// </summary>
/*enum*/ final class DmlTileFlipMode
{
    private DmlTileFlipMode(){}
    
    public static final int NONE = 0;
    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;
    public static final int HORIZONTAL_AND_VERTICAL = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlTileFlipMode value.
    /// </summary>
    public static String getName(/*DmlTileFlipMode*/int dmlTileFlipMode)
    {
    	switch (dmlTileFlipMode)
    	{
    		case NONE: return "NONE";
    		case HORIZONTAL: return "HORIZONTAL";
    		case VERTICAL: return "VERTICAL";
    		case HORIZONTAL_AND_VERTICAL: return "HORIZONTAL_AND_VERTICAL";
    		default: return "Unknown DmlTileFlipMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlTileFlipMode value.
    /// </summary>
    public static String toString(/*DmlTileFlipMode*/int dmlTileFlipMode)
    {
    	switch (dmlTileFlipMode)
    	{
    		case NONE: return "None";
    		case HORIZONTAL: return "Horizontal";
    		case VERTICAL: return "Vertical";
    		case HORIZONTAL_AND_VERTICAL: return "HorizontalAndVertical";
    		default: return "Unknown DmlTileFlipMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlTileFlipMode name.
    /// </summary>
    public static /*DmlTileFlipMode*/int fromName(String dmlTileFlipModeName)
    {
    	if ("NONE".equals(dmlTileFlipModeName)) return NONE;
    	if ("HORIZONTAL".equals(dmlTileFlipModeName)) return HORIZONTAL;
    	if ("VERTICAL".equals(dmlTileFlipModeName)) return VERTICAL;
    	if ("HORIZONTAL_AND_VERTICAL".equals(dmlTileFlipModeName)) return HORIZONTAL_AND_VERTICAL;
    	throw new IllegalArgumentException("Unknown DmlTileFlipMode name.");
    }
    
    /// <summary>
    /// Returns array of DmlTileFlipMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		HORIZONTAL,
    		VERTICAL,
    		HORIZONTAL_AND_VERTICAL,
    	};
    }

    public static final int length = 4;
}
