// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/12/2018 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the alignment of the picture of the control.
/// </summary>
/// <dev>
/// [MS-OFORMS], 2.5.63 PictureAlignment
/// </dev>
/*enum*/ final class PictureAlignment
{
    private PictureAlignment(){}
    
    public static final int TOP_LEFT = 0;
    public static final int TOP_RIGHT = 1;
    public static final int CENTER = 2;
    public static final int BOTTOM_LEFT = 3;
    public static final int BOTTOM_RIGHT = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer PictureAlignment value.
    /// </summary>
    public static String getName(/*PictureAlignment*/int pictureAlignment)
    {
    	switch (pictureAlignment)
    	{
    		case TOP_LEFT: return "TOP_LEFT";
    		case TOP_RIGHT: return "TOP_RIGHT";
    		case CENTER: return "CENTER";
    		case BOTTOM_LEFT: return "BOTTOM_LEFT";
    		case BOTTOM_RIGHT: return "BOTTOM_RIGHT";
    		default: return "Unknown PictureAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer PictureAlignment value.
    /// </summary>
    public static String toString(/*PictureAlignment*/int pictureAlignment)
    {
    	switch (pictureAlignment)
    	{
    		case TOP_LEFT: return "TopLeft";
    		case TOP_RIGHT: return "TopRight";
    		case CENTER: return "Center";
    		case BOTTOM_LEFT: return "BottomLeft";
    		case BOTTOM_RIGHT: return "BottomRight";
    		default: return "Unknown PictureAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by PictureAlignment name.
    /// </summary>
    public static /*PictureAlignment*/int fromName(String pictureAlignmentName)
    {
    	if ("TOP_LEFT".equals(pictureAlignmentName)) return TOP_LEFT;
    	if ("TOP_RIGHT".equals(pictureAlignmentName)) return TOP_RIGHT;
    	if ("CENTER".equals(pictureAlignmentName)) return CENTER;
    	if ("BOTTOM_LEFT".equals(pictureAlignmentName)) return BOTTOM_LEFT;
    	if ("BOTTOM_RIGHT".equals(pictureAlignmentName)) return BOTTOM_RIGHT;
    	throw new IllegalArgumentException("Unknown PictureAlignment name.");
    }
    
    /// <summary>
    /// Returns array of PictureAlignment values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TOP_LEFT,
    		TOP_RIGHT,
    		CENTER,
    		BOTTOM_LEFT,
    		BOTTOM_RIGHT,
    	};
    }

    public static final int length = 5;
}

