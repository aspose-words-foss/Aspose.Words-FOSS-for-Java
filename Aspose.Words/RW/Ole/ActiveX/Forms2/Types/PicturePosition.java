// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the location of the picture of a control relative to the caption of the control.
/// </summary>
/*enum*/ final class PicturePosition
{
    private PicturePosition(){}
    
    public static final int LEFT_TOP = 0x00020000;
    public static final int LEFT_CENTER = 0x00050003;
    public static final int LEFT_BOTTOM = 0x00080006;
    public static final int RIGHT_TOP = 0x00000002;
    public static final int RIGHT_CENTER = 0x00030005;
    public static final int RIGHT_BOTTOM = 0x00060008;
    public static final int ABOVE_LEFT = 0x00060000;
    public static final int ABOVE_CENTER = 0x00070001;
    public static final int ABOVE_RIGHT = 0x00080002;
    public static final int BELOW_LEFT = 0x00000006;
    public static final int BELOW_CENTER = 0x00010007;
    public static final int BELOW_RIGHT = 0x00020008;
    public static final int CENTER = 0x00040004;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer PicturePosition value.
    /// </summary>
    public static String getName(/*PicturePosition*/int picturePosition)
    {
    	switch (picturePosition)
    	{
    		case LEFT_TOP: return "LEFT_TOP";
    		case LEFT_CENTER: return "LEFT_CENTER";
    		case LEFT_BOTTOM: return "LEFT_BOTTOM";
    		case RIGHT_TOP: return "RIGHT_TOP";
    		case RIGHT_CENTER: return "RIGHT_CENTER";
    		case RIGHT_BOTTOM: return "RIGHT_BOTTOM";
    		case ABOVE_LEFT: return "ABOVE_LEFT";
    		case ABOVE_CENTER: return "ABOVE_CENTER";
    		case ABOVE_RIGHT: return "ABOVE_RIGHT";
    		case BELOW_LEFT: return "BELOW_LEFT";
    		case BELOW_CENTER: return "BELOW_CENTER";
    		case BELOW_RIGHT: return "BELOW_RIGHT";
    		case CENTER: return "CENTER";
    		default: return "Unknown PicturePosition value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer PicturePosition value.
    /// </summary>
    public static String toString(/*PicturePosition*/int picturePosition)
    {
    	switch (picturePosition)
    	{
    		case LEFT_TOP: return "LeftTop";
    		case LEFT_CENTER: return "LeftCenter";
    		case LEFT_BOTTOM: return "LeftBottom";
    		case RIGHT_TOP: return "RightTop";
    		case RIGHT_CENTER: return "RightCenter";
    		case RIGHT_BOTTOM: return "RightBottom";
    		case ABOVE_LEFT: return "AboveLeft";
    		case ABOVE_CENTER: return "AboveCenter";
    		case ABOVE_RIGHT: return "AboveRight";
    		case BELOW_LEFT: return "BelowLeft";
    		case BELOW_CENTER: return "BelowCenter";
    		case BELOW_RIGHT: return "BelowRight";
    		case CENTER: return "Center";
    		default: return "Unknown PicturePosition value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by PicturePosition name.
    /// </summary>
    public static /*PicturePosition*/int fromName(String picturePositionName)
    {
    	if ("LEFT_TOP".equals(picturePositionName)) return LEFT_TOP;
    	if ("LEFT_CENTER".equals(picturePositionName)) return LEFT_CENTER;
    	if ("LEFT_BOTTOM".equals(picturePositionName)) return LEFT_BOTTOM;
    	if ("RIGHT_TOP".equals(picturePositionName)) return RIGHT_TOP;
    	if ("RIGHT_CENTER".equals(picturePositionName)) return RIGHT_CENTER;
    	if ("RIGHT_BOTTOM".equals(picturePositionName)) return RIGHT_BOTTOM;
    	if ("ABOVE_LEFT".equals(picturePositionName)) return ABOVE_LEFT;
    	if ("ABOVE_CENTER".equals(picturePositionName)) return ABOVE_CENTER;
    	if ("ABOVE_RIGHT".equals(picturePositionName)) return ABOVE_RIGHT;
    	if ("BELOW_LEFT".equals(picturePositionName)) return BELOW_LEFT;
    	if ("BELOW_CENTER".equals(picturePositionName)) return BELOW_CENTER;
    	if ("BELOW_RIGHT".equals(picturePositionName)) return BELOW_RIGHT;
    	if ("CENTER".equals(picturePositionName)) return CENTER;
    	throw new IllegalArgumentException("Unknown PicturePosition name.");
    }
    
    /// <summary>
    /// Returns array of PicturePosition values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		LEFT_TOP,
    		LEFT_CENTER,
    		LEFT_BOTTOM,
    		RIGHT_TOP,
    		RIGHT_CENTER,
    		RIGHT_BOTTOM,
    		ABOVE_LEFT,
    		ABOVE_CENTER,
    		ABOVE_RIGHT,
    		BELOW_LEFT,
    		BELOW_CENTER,
    		BELOW_RIGHT,
    		CENTER,
    	};
    }

    public static final int length = 13;
}

