// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/05/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// A simple type that specifies how to position two rectangles relative to each other.
/// </summary>
/*enum*/ final class DmlRectangleAlignment
{
    private DmlRectangleAlignment(){}
    
    public static final int TOP_LEFT = 0;
    public static final int TOP = 1;
    public static final int TOP_RIGHT = 2;
    public static final int LEFT = 3;
    public static final int CENTER = 4;
    public static final int RIGHT = 5;
    public static final int BOTTOM_LEFT = 6;
    public static final int BOTTOM = 7;
    public static final int BOTTOM_RIGHT = 8;
    public static final int NONE = 9;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlRectangleAlignment value.
    /// </summary>
    public static String getName(/*DmlRectangleAlignment*/int dmlRectangleAlignment)
    {
    	switch (dmlRectangleAlignment)
    	{
    		case TOP_LEFT: return "TOP_LEFT";
    		case TOP: return "TOP";
    		case TOP_RIGHT: return "TOP_RIGHT";
    		case LEFT: return "LEFT";
    		case CENTER: return "CENTER";
    		case RIGHT: return "RIGHT";
    		case BOTTOM_LEFT: return "BOTTOM_LEFT";
    		case BOTTOM: return "BOTTOM";
    		case BOTTOM_RIGHT: return "BOTTOM_RIGHT";
    		case NONE: return "NONE";
    		default: return "Unknown DmlRectangleAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlRectangleAlignment value.
    /// </summary>
    public static String toString(/*DmlRectangleAlignment*/int dmlRectangleAlignment)
    {
    	switch (dmlRectangleAlignment)
    	{
    		case TOP_LEFT: return "TopLeft";
    		case TOP: return "Top";
    		case TOP_RIGHT: return "TopRight";
    		case LEFT: return "Left";
    		case CENTER: return "Center";
    		case RIGHT: return "Right";
    		case BOTTOM_LEFT: return "BottomLeft";
    		case BOTTOM: return "Bottom";
    		case BOTTOM_RIGHT: return "BottomRight";
    		case NONE: return "None";
    		default: return "Unknown DmlRectangleAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlRectangleAlignment name.
    /// </summary>
    public static /*DmlRectangleAlignment*/int fromName(String dmlRectangleAlignmentName)
    {
    	if ("TOP_LEFT".equals(dmlRectangleAlignmentName)) return TOP_LEFT;
    	if ("TOP".equals(dmlRectangleAlignmentName)) return TOP;
    	if ("TOP_RIGHT".equals(dmlRectangleAlignmentName)) return TOP_RIGHT;
    	if ("LEFT".equals(dmlRectangleAlignmentName)) return LEFT;
    	if ("CENTER".equals(dmlRectangleAlignmentName)) return CENTER;
    	if ("RIGHT".equals(dmlRectangleAlignmentName)) return RIGHT;
    	if ("BOTTOM_LEFT".equals(dmlRectangleAlignmentName)) return BOTTOM_LEFT;
    	if ("BOTTOM".equals(dmlRectangleAlignmentName)) return BOTTOM;
    	if ("BOTTOM_RIGHT".equals(dmlRectangleAlignmentName)) return BOTTOM_RIGHT;
    	if ("NONE".equals(dmlRectangleAlignmentName)) return NONE;
    	throw new IllegalArgumentException("Unknown DmlRectangleAlignment name.");
    }
    
    /// <summary>
    /// Returns array of DmlRectangleAlignment values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TOP_LEFT,
    		TOP,
    		TOP_RIGHT,
    		LEFT,
    		CENTER,
    		RIGHT,
    		BOTTOM_LEFT,
    		BOTTOM,
    		BOTTOM_RIGHT,
    		NONE,
    	};
    }

    public static final int length = 10;
}
