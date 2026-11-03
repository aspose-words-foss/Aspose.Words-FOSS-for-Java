// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/02/2018 by Dmitry Matveenko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents gutter position on the page.
/// </summary>
/*enum*/ final class PageSide
{
    private PageSide(){}
    
    public static final int LEFT = 0;
    public static final int TOP = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer PageSide value.
    /// </summary>
    public static String getName(/*PageSide*/int pageSide)
    {
    	switch (pageSide)
    	{
    		case LEFT: return "LEFT";
    		case TOP: return "TOP";
    		case RIGHT: return "RIGHT";
    		case BOTTOM: return "BOTTOM";
    		default: return "Unknown PageSide value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer PageSide value.
    /// </summary>
    public static String toString(/*PageSide*/int pageSide)
    {
    	switch (pageSide)
    	{
    		case LEFT: return "Left";
    		case TOP: return "Top";
    		case RIGHT: return "Right";
    		case BOTTOM: return "Bottom";
    		default: return "Unknown PageSide value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by PageSide name.
    /// </summary>
    public static /*PageSide*/int fromName(String pageSideName)
    {
    	if ("LEFT".equals(pageSideName)) return LEFT;
    	if ("TOP".equals(pageSideName)) return TOP;
    	if ("RIGHT".equals(pageSideName)) return RIGHT;
    	if ("BOTTOM".equals(pageSideName)) return BOTTOM;
    	throw new IllegalArgumentException("Unknown PageSide name.");
    }
    
    /// <summary>
    /// Returns array of PageSide values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		LEFT,
    		TOP,
    		RIGHT,
    		BOTTOM,
    	};
    }

    public static final int length = 4;
}

