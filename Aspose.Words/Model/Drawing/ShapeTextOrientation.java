// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies orientation of text in shapes.
/// </summary>
/// <dev>
/// Type of vertical text (20.1.10.82 ST_TextVerticalType (Vertical Text Types)).
/// Enum elements have the same names as their corresponding <see cref="TextOrientation"/> elements.
/// </dev>
public /*enum*/ final class ShapeTextOrientation
{
    private ShapeTextOrientation(){}
    
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
    public static final int UPWARD = 2;

    /// <summary>
    /// Far East characters appear vertical, other text is rotated 90 degrees
    /// to the right to appear from top to bottom (tb-rl-v).
    /// </summary>
    public static final int VERTICAL_FAR_EAST = 3;

    /// <summary>
    /// Far East characters appear vertical, other text is rotated 90 degrees
    /// to the right to appear from top to bottom vertically, then left to right horizontally  (tb-lr-v).
    /// </summary>
    public static final int VERTICAL_ROTATED_FAR_EAST = 4;

    /// <summary>
    /// Text is vertical, with one letter on top of the other.
    /// </summary>
    public static final int WORD_ART_VERTICAL = 5;

    /// <summary>
    /// Text is vertical, with one letter on top of the other, then right to left horizontally.
    /// </summary>
    public static final int WORD_ART_VERTICAL_RIGHT_TO_LEFT = 6;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ShapeTextOrientation value.
    /// </summary>
    public static String getName(/*ShapeTextOrientation*/int shapeTextOrientation)
    {
    	switch (shapeTextOrientation)
    	{
    		case HORIZONTAL: return "HORIZONTAL";
    		case DOWNWARD: return "DOWNWARD";
    		case UPWARD: return "UPWARD";
    		case VERTICAL_FAR_EAST: return "VERTICAL_FAR_EAST";
    		case VERTICAL_ROTATED_FAR_EAST: return "VERTICAL_ROTATED_FAR_EAST";
    		case WORD_ART_VERTICAL: return "WORD_ART_VERTICAL";
    		case WORD_ART_VERTICAL_RIGHT_TO_LEFT: return "WORD_ART_VERTICAL_RIGHT_TO_LEFT";
    		default: return "Unknown ShapeTextOrientation value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ShapeTextOrientation value.
    /// </summary>
    public static String toString(/*ShapeTextOrientation*/int shapeTextOrientation)
    {
    	switch (shapeTextOrientation)
    	{
    		case HORIZONTAL: return "Horizontal";
    		case DOWNWARD: return "Downward";
    		case UPWARD: return "Upward";
    		case VERTICAL_FAR_EAST: return "VerticalFarEast";
    		case VERTICAL_ROTATED_FAR_EAST: return "VerticalRotatedFarEast";
    		case WORD_ART_VERTICAL: return "WordArtVertical";
    		case WORD_ART_VERTICAL_RIGHT_TO_LEFT: return "WordArtVerticalRightToLeft";
    		default: return "Unknown ShapeTextOrientation value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ShapeTextOrientation name.
    /// </summary>
    public static /*ShapeTextOrientation*/int fromName(String shapeTextOrientationName)
    {
    	if ("HORIZONTAL".equals(shapeTextOrientationName)) return HORIZONTAL;
    	if ("DOWNWARD".equals(shapeTextOrientationName)) return DOWNWARD;
    	if ("UPWARD".equals(shapeTextOrientationName)) return UPWARD;
    	if ("VERTICAL_FAR_EAST".equals(shapeTextOrientationName)) return VERTICAL_FAR_EAST;
    	if ("VERTICAL_ROTATED_FAR_EAST".equals(shapeTextOrientationName)) return VERTICAL_ROTATED_FAR_EAST;
    	if ("WORD_ART_VERTICAL".equals(shapeTextOrientationName)) return WORD_ART_VERTICAL;
    	if ("WORD_ART_VERTICAL_RIGHT_TO_LEFT".equals(shapeTextOrientationName)) return WORD_ART_VERTICAL_RIGHT_TO_LEFT;
    	throw new IllegalArgumentException("Unknown ShapeTextOrientation name.");
    }
    
    /// <summary>
    /// Returns array of ShapeTextOrientation values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		HORIZONTAL,
    		DOWNWARD,
    		UPWARD,
    		VERTICAL_FAR_EAST,
    		VERTICAL_ROTATED_FAR_EAST,
    		WORD_ART_VERTICAL,
    		WORD_ART_VERTICAL_RIGHT_TO_LEFT,
    	};
    }

    public static final int length = 7;
}

