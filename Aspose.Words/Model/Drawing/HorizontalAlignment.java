// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies horizontal alignment of a floating shape, text frame or floating table.
/// </summary>
/// <seealso cref="ShapeBase.HorizontalAlignment"/>
/// <dev>
/// Do not renumber. The values are taken from the RTF specification.
/// </dev>
public /*enum*/ final class HorizontalAlignment
{
    private HorizontalAlignment(){}
    
    /// <summary>
    /// The object is explicitly positioned, usually using its <b>Left</b> property.
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    /// Same as <see cref="None"/>.
    /// </summary>
    public static final int DEFAULT = NONE;
    /// <summary>
    /// Specifies that the object shall be left aligned to the horizontal alignment base.
    /// </summary>
    public static final int LEFT = 1;
    /// <summary>
    /// Specifies that the object shall be centered with respect to the horizontal alignment base.
    /// </summary>
    public static final int CENTER = 2;
    /// <summary>
    /// Specifies that the object shall be right aligned to the horizontal alignment base.
    /// </summary>
    public static final int RIGHT = 3;
    /// <summary>
    /// Specifies that the object shall be inside of the horizontal alignment base.
    /// </summary>
    public static final int INSIDE = 4;
    /// <summary>
    /// Specifies that the object shall be outside of the horizontal alignment base.
    /// </summary>
    public static final int OUTSIDE = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer HorizontalAlignment value.
    /// </summary>
    public static String getName(/*HorizontalAlignment*/int horizontalAlignment)
    {
    	switch (horizontalAlignment)
    	{
    		case NONE:
    		/*case DEFAULT:*/
    			return "NONE" + " | " + "DEFAULT";
    		case LEFT: return "LEFT";
    		case CENTER: return "CENTER";
    		case RIGHT: return "RIGHT";
    		case INSIDE: return "INSIDE";
    		case OUTSIDE: return "OUTSIDE";
    		default: return "Unknown HorizontalAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer HorizontalAlignment value.
    /// </summary>
    public static String toString(/*HorizontalAlignment*/int horizontalAlignment)
    {
    	switch (horizontalAlignment)
    	{
    		case NONE:
    		/*case DEFAULT:*/
    			return "None" + " | " + "Default";
    		case LEFT: return "Left";
    		case CENTER: return "Center";
    		case RIGHT: return "Right";
    		case INSIDE: return "Inside";
    		case OUTSIDE: return "Outside";
    		default: return "Unknown HorizontalAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by HorizontalAlignment name.
    /// </summary>
    public static /*HorizontalAlignment*/int fromName(String horizontalAlignmentName)
    {
    	if ("NONE".equals(horizontalAlignmentName)) return NONE;
    	if ("DEFAULT".equals(horizontalAlignmentName)) return DEFAULT;
    	if ("LEFT".equals(horizontalAlignmentName)) return LEFT;
    	if ("CENTER".equals(horizontalAlignmentName)) return CENTER;
    	if ("RIGHT".equals(horizontalAlignmentName)) return RIGHT;
    	if ("INSIDE".equals(horizontalAlignmentName)) return INSIDE;
    	if ("OUTSIDE".equals(horizontalAlignmentName)) return OUTSIDE;
    	throw new IllegalArgumentException("Unknown HorizontalAlignment name.");
    }
    
    /// <summary>
    /// Returns array of HorizontalAlignment values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		DEFAULT,
    		LEFT,
    		CENTER,
    		RIGHT,
    		INSIDE,
    		OUTSIDE,
    	};
    }

    public static final int length = 7;
}

