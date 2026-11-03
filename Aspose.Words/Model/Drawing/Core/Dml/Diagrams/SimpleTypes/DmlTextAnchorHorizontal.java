// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2015 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.59 ST_TextAnchorHorizontal (Text Anchor Horizontal)
/// </summary>
/*enum*/ final class DmlTextAnchorHorizontal
{
    private DmlTextAnchorHorizontal(){}
    
    public static final int CENTER = 0;
    public static final int NONE = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlTextAnchorHorizontal value.
    /// </summary>
    public static String getName(/*DmlTextAnchorHorizontal*/int dmlTextAnchorHorizontal)
    {
    	switch (dmlTextAnchorHorizontal)
    	{
    		case CENTER: return "CENTER";
    		case NONE: return "NONE";
    		default: return "Unknown DmlTextAnchorHorizontal value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlTextAnchorHorizontal value.
    /// </summary>
    public static String toString(/*DmlTextAnchorHorizontal*/int dmlTextAnchorHorizontal)
    {
    	switch (dmlTextAnchorHorizontal)
    	{
    		case CENTER: return "Center";
    		case NONE: return "None";
    		default: return "Unknown DmlTextAnchorHorizontal value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlTextAnchorHorizontal name.
    /// </summary>
    public static /*DmlTextAnchorHorizontal*/int fromName(String dmlTextAnchorHorizontalName)
    {
    	if ("CENTER".equals(dmlTextAnchorHorizontalName)) return CENTER;
    	if ("NONE".equals(dmlTextAnchorHorizontalName)) return NONE;
    	throw new IllegalArgumentException("Unknown DmlTextAnchorHorizontal name.");
    }
    
    /// <summary>
    /// Returns array of DmlTextAnchorHorizontal values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		CENTER,
    		NONE,
    	};
    }

    public static final int length = 2;
}

