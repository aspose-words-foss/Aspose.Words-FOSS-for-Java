// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/09/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies alignment for an inline table.
/// </summary>
public /*enum*/ final class TableAlignment
{
    private TableAlignment(){}
    
    /// <summary>
    /// The table is aligned to the left.
    /// </summary>
    public static final int LEFT = 0;
    /// <summary>
    /// The table is centered.
    /// </summary>
    public static final int CENTER = 1;
    /// <summary>
    /// The table is aligned to the right.
    /// </summary>
    public static final int RIGHT = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TableAlignment value.
    /// </summary>
    public static String getName(/*TableAlignment*/int tableAlignment)
    {
    	switch (tableAlignment)
    	{
    		case LEFT: return "LEFT";
    		case CENTER: return "CENTER";
    		case RIGHT: return "RIGHT";
    		default: return "Unknown TableAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TableAlignment value.
    /// </summary>
    public static String toString(/*TableAlignment*/int tableAlignment)
    {
    	switch (tableAlignment)
    	{
    		case LEFT: return "Left";
    		case CENTER: return "Center";
    		case RIGHT: return "Right";
    		default: return "Unknown TableAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TableAlignment name.
    /// </summary>
    public static /*TableAlignment*/int fromName(String tableAlignmentName)
    {
    	if ("LEFT".equals(tableAlignmentName)) return LEFT;
    	if ("CENTER".equals(tableAlignmentName)) return CENTER;
    	if ("RIGHT".equals(tableAlignmentName)) return RIGHT;
    	throw new IllegalArgumentException("Unknown TableAlignment name.");
    }
    
    /// <summary>
    /// Returns array of TableAlignment values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		LEFT,
    		CENTER,
    		RIGHT,
    	};
    }

    public static final int length = 3;
}

