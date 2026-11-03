// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/09/2021 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the alignment for the tiling of the texture fill.
/// </summary>
/// <dev>
/// Public analogue of <see cref="Core.Dml.Fills.DmlRectangleAlignment"/>.
/// </dev>
public /*enum*/ final class TextureAlignment
{
    private TextureAlignment(){}
    
    /// <summary>
    /// Top left texture alignment.
    /// </summary>
    public static final int TOP_LEFT = 0;

    /// <summary>
    /// Top texture alignment.
    /// </summary>
    public static final int TOP = 1;

    /// <summary>
    /// Top right texture alignment.
    /// </summary>
    public static final int TOP_RIGHT = 2;

    /// <summary>
    /// Left texture alignment.
    /// </summary>
    public static final int LEFT = 3;

    /// <summary>
    /// Center texture alignment.
    /// </summary>
    public static final int CENTER = 4;

    /// <summary>
    /// Right texture alignment.
    /// </summary>
    public static final int RIGHT = 5;

    /// <summary>
    /// Bottom left texture alignment.
    /// </summary>
    public static final int BOTTOM_LEFT = 6;

    /// <summary>
    /// Bottom texture alignment.
    /// </summary>
    public static final int BOTTOM = 7;

    /// <summary>
    /// Bottom right texture alignment.
    /// </summary>
    public static final int BOTTOM_RIGHT = 8;

    /// <summary>
    /// None texture alignment.
    /// </summary>
    public static final int NONE = 9;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TextureAlignment value.
    /// </summary>
    public static String getName(/*TextureAlignment*/int textureAlignment)
    {
    	switch (textureAlignment)
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
    		default: return "Unknown TextureAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TextureAlignment value.
    /// </summary>
    public static String toString(/*TextureAlignment*/int textureAlignment)
    {
    	switch (textureAlignment)
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
    		default: return "Unknown TextureAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TextureAlignment name.
    /// </summary>
    public static /*TextureAlignment*/int fromName(String textureAlignmentName)
    {
    	if ("TOP_LEFT".equals(textureAlignmentName)) return TOP_LEFT;
    	if ("TOP".equals(textureAlignmentName)) return TOP;
    	if ("TOP_RIGHT".equals(textureAlignmentName)) return TOP_RIGHT;
    	if ("LEFT".equals(textureAlignmentName)) return LEFT;
    	if ("CENTER".equals(textureAlignmentName)) return CENTER;
    	if ("RIGHT".equals(textureAlignmentName)) return RIGHT;
    	if ("BOTTOM_LEFT".equals(textureAlignmentName)) return BOTTOM_LEFT;
    	if ("BOTTOM".equals(textureAlignmentName)) return BOTTOM;
    	if ("BOTTOM_RIGHT".equals(textureAlignmentName)) return BOTTOM_RIGHT;
    	if ("NONE".equals(textureAlignmentName)) return NONE;
    	throw new IllegalArgumentException("Unknown TextureAlignment name.");
    }
    
    /// <summary>
    /// Returns array of TextureAlignment values.
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

