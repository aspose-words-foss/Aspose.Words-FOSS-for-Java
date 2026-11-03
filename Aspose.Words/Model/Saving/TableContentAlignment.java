// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/05/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Allows to specify the alignment of the content of the table to be used when exporting into Markdown format.
/// </summary>
public /*enum*/ final class TableContentAlignment
{
    private TableContentAlignment(){}
    
    /// <summary>
    /// The alignment will be taken from the first paragraph in corresponding table column.
    /// </summary>
    public static final int AUTO = 0;
    /// <summary>
    /// The content of tables will be aligned to the Left.
    /// </summary>
    public static final int LEFT = 1;
    /// <summary>
    /// The content of tables will be aligned to the Center.
    /// </summary>
    public static final int CENTER = 2;
    /// <summary>
    /// The content of tables will be aligned to the Right.
    /// </summary>
    public static final int RIGHT = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TableContentAlignment value.
    /// </summary>
    public static String getName(/*TableContentAlignment*/int tableContentAlignment)
    {
    	switch (tableContentAlignment)
    	{
    		case AUTO: return "AUTO";
    		case LEFT: return "LEFT";
    		case CENTER: return "CENTER";
    		case RIGHT: return "RIGHT";
    		default: return "Unknown TableContentAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TableContentAlignment value.
    /// </summary>
    public static String toString(/*TableContentAlignment*/int tableContentAlignment)
    {
    	switch (tableContentAlignment)
    	{
    		case AUTO: return "Auto";
    		case LEFT: return "Left";
    		case CENTER: return "Center";
    		case RIGHT: return "Right";
    		default: return "Unknown TableContentAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TableContentAlignment name.
    /// </summary>
    public static /*TableContentAlignment*/int fromName(String tableContentAlignmentName)
    {
    	if ("AUTO".equals(tableContentAlignmentName)) return AUTO;
    	if ("LEFT".equals(tableContentAlignmentName)) return LEFT;
    	if ("CENTER".equals(tableContentAlignmentName)) return CENTER;
    	if ("RIGHT".equals(tableContentAlignmentName)) return RIGHT;
    	throw new IllegalArgumentException("Unknown TableContentAlignment name.");
    }
    
    /// <summary>
    /// Returns array of TableContentAlignment values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		AUTO,
    		LEFT,
    		CENTER,
    		RIGHT,
    	};
    }

    public static final int length = 4;
}

