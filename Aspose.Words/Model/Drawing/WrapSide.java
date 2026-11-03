// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/02/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies what side(s) of the shape or picture the text wraps around.
/// </summary>
/// <seealso cref="ShapeBase.WrapSide"/>
public /*enum*/ final class WrapSide
{
    private WrapSide(){}
    
    /// <summary>
    /// The document text wraps on both sides of the shape.
    /// </summary>
    public static final int BOTH = 0;
    /// <summary>
    /// The document text wraps on the left side of the shape only. There is a text free area on the right of the shape.
    /// </summary>
    public static final int LEFT = 1;
    /// <summary>
    /// The document text wraps on the right side of the shape only. There is a text free area on the left side of the shape.
    /// </summary>
    public static final int RIGHT = 2;
    /// <summary>
    /// The document text wraps on the side of the shape that is farthest from the page margin, leaving text free area on the other side of the shape.
    /// </summary>
    public static final int LARGEST = 3;
    /// <summary>
    /// Default value is <see cref="Both"/>.
    /// </summary>
    public static final int DEFAULT = BOTH;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer WrapSide value.
    /// </summary>
    public static String getName(/*WrapSide*/int wrapSide)
    {
    	switch (wrapSide)
    	{
    		case BOTH:
    		/*case DEFAULT:*/
    			return "BOTH" + " | " + "DEFAULT";
    		case LEFT: return "LEFT";
    		case RIGHT: return "RIGHT";
    		case LARGEST: return "LARGEST";
    		default: return "Unknown WrapSide value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer WrapSide value.
    /// </summary>
    public static String toString(/*WrapSide*/int wrapSide)
    {
    	switch (wrapSide)
    	{
    		case BOTH:
    		/*case DEFAULT:*/
    			return "Both" + " | " + "Default";
    		case LEFT: return "Left";
    		case RIGHT: return "Right";
    		case LARGEST: return "Largest";
    		default: return "Unknown WrapSide value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by WrapSide name.
    /// </summary>
    public static /*WrapSide*/int fromName(String wrapSideName)
    {
    	if ("BOTH".equals(wrapSideName)) return BOTH;
    	if ("LEFT".equals(wrapSideName)) return LEFT;
    	if ("RIGHT".equals(wrapSideName)) return RIGHT;
    	if ("LARGEST".equals(wrapSideName)) return LARGEST;
    	if ("DEFAULT".equals(wrapSideName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown WrapSide name.");
    }
    
    /// <summary>
    /// Returns array of WrapSide values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BOTH,
    		LEFT,
    		RIGHT,
    		LARGEST,
    		DEFAULT,
    	};
    }

    public static final int length = 5;
}

