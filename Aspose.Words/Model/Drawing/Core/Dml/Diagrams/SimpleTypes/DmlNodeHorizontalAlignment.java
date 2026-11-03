// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.45 ST_NodeHorizontalAlignment (Node Horizontal Alignment)
/// </summary>
/*enum*/ final class DmlNodeHorizontalAlignment
{
    private DmlNodeHorizontalAlignment(){}
    
    public static final int CENTER = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlNodeHorizontalAlignment value.
    /// </summary>
    public static String getName(/*DmlNodeHorizontalAlignment*/int dmlNodeHorizontalAlignment)
    {
    	switch (dmlNodeHorizontalAlignment)
    	{
    		case CENTER: return "CENTER";
    		case LEFT: return "LEFT";
    		case RIGHT: return "RIGHT";
    		default: return "Unknown DmlNodeHorizontalAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlNodeHorizontalAlignment value.
    /// </summary>
    public static String toString(/*DmlNodeHorizontalAlignment*/int dmlNodeHorizontalAlignment)
    {
    	switch (dmlNodeHorizontalAlignment)
    	{
    		case CENTER: return "Center";
    		case LEFT: return "Left";
    		case RIGHT: return "Right";
    		default: return "Unknown DmlNodeHorizontalAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlNodeHorizontalAlignment name.
    /// </summary>
    public static /*DmlNodeHorizontalAlignment*/int fromName(String dmlNodeHorizontalAlignmentName)
    {
    	if ("CENTER".equals(dmlNodeHorizontalAlignmentName)) return CENTER;
    	if ("LEFT".equals(dmlNodeHorizontalAlignmentName)) return LEFT;
    	if ("RIGHT".equals(dmlNodeHorizontalAlignmentName)) return RIGHT;
    	throw new IllegalArgumentException("Unknown DmlNodeHorizontalAlignment name.");
    }
    
    /// <summary>
    /// Returns array of DmlNodeHorizontalAlignment values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		CENTER,
    		LEFT,
    		RIGHT,
    	};
    }

    public static final int length = 3;
}

