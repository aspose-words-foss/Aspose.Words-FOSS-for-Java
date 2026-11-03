// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies orientation of text on a page, in a table cell or a text frame.
/// </summary>
public /*enum*/ final class TextOrientation
{
    private TextOrientation(){}
    
    /// <summary>
    /// Text is arranged horizontally (lr-tb).
    /// </summary>
    public static final int HORIZONTAL = 0;

    /// <summary>
    /// Text is rotated 90 degrees to the right to appear from top to bottom (tb-rl).
    /// </summary>
    public static final int DOWNWARD = 1;

    /// <summary>
    /// Text is rotated 90 degrees to the left to appear from bottom to top (bt-lr).
    /// </summary>
    public static final int UPWARD = 3;

    /// <summary>
    /// Text is arranged horizontally, but Far East characters are rotated 90 degrees to the left (lr-tb-v).
    /// </summary>
    public static final int HORIZONTAL_ROTATED_FAR_EAST = 4;

    /// <summary>
    /// Far East characters appear vertical, other text is rotated 90 degrees
    /// to the right to appear from top to bottom (tb-rl-v).
    /// </summary>
    public static final int VERTICAL_FAR_EAST = 5;

    /// <summary>
    /// Far East characters appear vertical, other text is rotated 90 degrees
    /// to the right to appear from top to bottom vertically, then left to right horizontally  (tb-lr-v).
    /// </summary>
    /// <dev> IN: Set this value to 7 because MS Word uses this value in DOC format. </dev>
    public static final int VERTICAL_ROTATED_FAR_EAST = 7;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TextOrientation value.
    /// </summary>
    public static String getName(/*TextOrientation*/int textOrientation)
    {
    	switch (textOrientation)
    	{
    		case HORIZONTAL: return "HORIZONTAL";
    		case DOWNWARD: return "DOWNWARD";
    		case UPWARD: return "UPWARD";
    		case HORIZONTAL_ROTATED_FAR_EAST: return "HORIZONTAL_ROTATED_FAR_EAST";
    		case VERTICAL_FAR_EAST: return "VERTICAL_FAR_EAST";
    		case VERTICAL_ROTATED_FAR_EAST: return "VERTICAL_ROTATED_FAR_EAST";
    		default: return "Unknown TextOrientation value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TextOrientation value.
    /// </summary>
    public static String toString(/*TextOrientation*/int textOrientation)
    {
    	switch (textOrientation)
    	{
    		case HORIZONTAL: return "Horizontal";
    		case DOWNWARD: return "Downward";
    		case UPWARD: return "Upward";
    		case HORIZONTAL_ROTATED_FAR_EAST: return "HorizontalRotatedFarEast";
    		case VERTICAL_FAR_EAST: return "VerticalFarEast";
    		case VERTICAL_ROTATED_FAR_EAST: return "VerticalRotatedFarEast";
    		default: return "Unknown TextOrientation value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TextOrientation name.
    /// </summary>
    public static /*TextOrientation*/int fromName(String textOrientationName)
    {
    	if ("HORIZONTAL".equals(textOrientationName)) return HORIZONTAL;
    	if ("DOWNWARD".equals(textOrientationName)) return DOWNWARD;
    	if ("UPWARD".equals(textOrientationName)) return UPWARD;
    	if ("HORIZONTAL_ROTATED_FAR_EAST".equals(textOrientationName)) return HORIZONTAL_ROTATED_FAR_EAST;
    	if ("VERTICAL_FAR_EAST".equals(textOrientationName)) return VERTICAL_FAR_EAST;
    	if ("VERTICAL_ROTATED_FAR_EAST".equals(textOrientationName)) return VERTICAL_ROTATED_FAR_EAST;
    	throw new IllegalArgumentException("Unknown TextOrientation name.");
    }
    
    /// <summary>
    /// Returns array of TextOrientation values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		HORIZONTAL,
    		DOWNWARD,
    		UPWARD,
    		HORIZONTAL_ROTATED_FAR_EAST,
    		VERTICAL_FAR_EAST,
    		VERTICAL_ROTATED_FAR_EAST,
    	};
    }

    public static final int length = 6;
}

