// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/02/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how text is wrapped around a shape or picture.
/// </summary>
/// <seealso cref="ShapeBase.WrapType"/>
public /*enum*/ final class WrapType
{
    private WrapType(){}
    
    /// <summary>
    /// No text wrapping around the shape. The shape is placed behind or in front of text.
    /// </summary>
    public static final int NONE = 3;
    /// <summary>
    /// The shape remains on the same layer as text and treated as a character.
    /// </summary>
    public static final int INLINE = 0;
    /// <summary>
    /// The text stops at the top of the shape and restarts on the line below the shape.
    /// </summary>
    public static final int TOP_BOTTOM = 1;
    /// <summary>
    /// Wraps text around all sides of the square bounding box of the shape.
    /// </summary>
    public static final int SQUARE = 2;
    /// <summary>
    /// Wraps tightly around the edges of the shape, instead of wrapping around the bounding box.
    /// </summary>
    public static final int TIGHT = 4;
    /// <summary>
    /// Same as Tight, but wraps inside any parts of the shape that are open.
    /// </summary>
    public static final int THROUGH = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer WrapType value.
    /// </summary>
    public static String getName(/*WrapType*/int wrapType)
    {
    	switch (wrapType)
    	{
    		case NONE: return "NONE";
    		case INLINE: return "INLINE";
    		case TOP_BOTTOM: return "TOP_BOTTOM";
    		case SQUARE: return "SQUARE";
    		case TIGHT: return "TIGHT";
    		case THROUGH: return "THROUGH";
    		default: return "Unknown WrapType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer WrapType value.
    /// </summary>
    public static String toString(/*WrapType*/int wrapType)
    {
    	switch (wrapType)
    	{
    		case NONE: return "None";
    		case INLINE: return "Inline";
    		case TOP_BOTTOM: return "TopBottom";
    		case SQUARE: return "Square";
    		case TIGHT: return "Tight";
    		case THROUGH: return "Through";
    		default: return "Unknown WrapType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by WrapType name.
    /// </summary>
    public static /*WrapType*/int fromName(String wrapTypeName)
    {
    	if ("NONE".equals(wrapTypeName)) return NONE;
    	if ("INLINE".equals(wrapTypeName)) return INLINE;
    	if ("TOP_BOTTOM".equals(wrapTypeName)) return TOP_BOTTOM;
    	if ("SQUARE".equals(wrapTypeName)) return SQUARE;
    	if ("TIGHT".equals(wrapTypeName)) return TIGHT;
    	if ("THROUGH".equals(wrapTypeName)) return THROUGH;
    	throw new IllegalArgumentException("Unknown WrapType name.");
    }
    
    /// <summary>
    /// Returns array of WrapType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		INLINE,
    		TOP_BOTTOM,
    		SQUARE,
    		TIGHT,
    		THROUGH,
    	};
    }

    public static final int length = 6;
}

