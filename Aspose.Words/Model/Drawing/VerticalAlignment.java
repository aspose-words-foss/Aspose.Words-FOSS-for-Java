// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies vertical alignment of a floating shape, text frame or a floating table.
/// </summary>
/// <seealso cref="ShapeBase.VerticalAlignment"/>
/// <dev>
/// Do not renumber. The values are taken from the RTF specification.
/// </dev>
public /*enum*/ final class VerticalAlignment
{
    private VerticalAlignment(){}
    
    /// <summary>
    /// The object is explicitly positioned, usually using its <b>Top</b> property.
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    /// Specifies that the object shall be at the top of the vertical alignment base.
    /// </summary>
    public static final int TOP = 1;
    /// <summary>
    /// Specifies that the object shall be centered with respect to the vertical alignment base.
    /// </summary>
    public static final int CENTER = 2;
    /// <summary>
    /// Specifies that the object shall be at the bottom of the vertical alignment base.
    /// </summary>
    public static final int BOTTOM = 3;
    /// <summary>
    /// Specifies that the object shall be inside of the horizontal alignment base.
    /// </summary>
    public static final int INSIDE = 4;
    /// <summary>
    /// Specifies that the object shall be outside of the vertical alignment base.
    /// </summary>
    public static final int OUTSIDE = 5;
    /// <summary>
    /// Not documented. Seems to be a possible value for floating paragraphs and tables.
    /// </summary>
    public static final int INLINE = -1;

    /// <summary>
    /// Same as <see cref="None"/>.
    /// </summary>
    public static final int DEFAULT = NONE;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer VerticalAlignment value.
    /// </summary>
    public static String getName(/*VerticalAlignment*/int verticalAlignment)
    {
    	switch (verticalAlignment)
    	{
    		case NONE:
    		/*case DEFAULT:*/
    			return "NONE" + " | " + "DEFAULT";
    		case TOP: return "TOP";
    		case CENTER: return "CENTER";
    		case BOTTOM: return "BOTTOM";
    		case INSIDE: return "INSIDE";
    		case OUTSIDE: return "OUTSIDE";
    		case INLINE: return "INLINE";
    		default: return "Unknown VerticalAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer VerticalAlignment value.
    /// </summary>
    public static String toString(/*VerticalAlignment*/int verticalAlignment)
    {
    	switch (verticalAlignment)
    	{
    		case NONE:
    		/*case DEFAULT:*/
    			return "None" + " | " + "Default";
    		case TOP: return "Top";
    		case CENTER: return "Center";
    		case BOTTOM: return "Bottom";
    		case INSIDE: return "Inside";
    		case OUTSIDE: return "Outside";
    		case INLINE: return "Inline";
    		default: return "Unknown VerticalAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by VerticalAlignment name.
    /// </summary>
    public static /*VerticalAlignment*/int fromName(String verticalAlignmentName)
    {
    	if ("NONE".equals(verticalAlignmentName)) return NONE;
    	if ("TOP".equals(verticalAlignmentName)) return TOP;
    	if ("CENTER".equals(verticalAlignmentName)) return CENTER;
    	if ("BOTTOM".equals(verticalAlignmentName)) return BOTTOM;
    	if ("INSIDE".equals(verticalAlignmentName)) return INSIDE;
    	if ("OUTSIDE".equals(verticalAlignmentName)) return OUTSIDE;
    	if ("INLINE".equals(verticalAlignmentName)) return INLINE;
    	if ("DEFAULT".equals(verticalAlignmentName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown VerticalAlignment name.");
    }
    
    /// <summary>
    /// Returns array of VerticalAlignment values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		TOP,
    		CENTER,
    		BOTTOM,
    		INSIDE,
    		OUTSIDE,
    		INLINE,
    		DEFAULT,
    	};
    }

    public static final int length = 8;
}

