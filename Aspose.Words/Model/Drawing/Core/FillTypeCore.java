// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/07/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Defines the type of fill.
/// </summary>
/// <seealso cref="Fill.FillType"/>
/*enum*/ final class FillTypeCore
{
    private FillTypeCore(){}
    
    /// <summary>
    /// Fill with a solid color.
    /// </summary>
    public static final int SOLID = 0;
    /// <summary>
    /// Fill with a pattern (bitmap).
    /// </summary>
    public static final int PATTERN = 1;
    /// <summary>
    /// A texture (pattern with its own color map).
    /// </summary>
    public static final int TEXTURE = 2;
    /// <summary>
    /// Fill with a picture that is centered in the shape.
    /// </summary>
    public static final int PICTURE = 3;
    /// <summary>
    /// Shade from start to end points.
    /// </summary>
    public static final int SHADE = 4;
    /// <summary>
    /// Shade from bounding rectangle to end point.
    /// </summary>
    public static final int SHADE_CENTER = 5;
    /// <summary>
    /// Shade from shape outline to end point.
    /// </summary>
    public static final int SHADE_SHAPE = 6;
    /// <summary>
    /// Similar to Shade, but the fillAngle is additionally scaled by the aspect ratio of
    /// the shape. If shape is square, it is the same as Shade.
    /// </summary>
    public static final int SHADE_SCALE = 7;
    /// <summary>
    /// Special type - shade to title ---  for PP.
    /// </summary>
    public static final int SHADE_TITLE = 8;
    /// <summary>
    /// Use the background fill color/pattern.
    /// </summary>
    public static final int BACKGROUND = 9;
    /// <summary>
    /// Similar to Shade, but the fillAngle is additionally NOT scaled by the aspect ratio of
    /// the shape. If shape is square, it is the same as Shade.
    /// </summary>
    public static final int SHADE_UNSCALE = 10;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FillTypeCore value.
    /// </summary>
    public static String getName(/*FillTypeCore*/int fillTypeCore)
    {
    	switch (fillTypeCore)
    	{
    		case SOLID: return "SOLID";
    		case PATTERN: return "PATTERN";
    		case TEXTURE: return "TEXTURE";
    		case PICTURE: return "PICTURE";
    		case SHADE: return "SHADE";
    		case SHADE_CENTER: return "SHADE_CENTER";
    		case SHADE_SHAPE: return "SHADE_SHAPE";
    		case SHADE_SCALE: return "SHADE_SCALE";
    		case SHADE_TITLE: return "SHADE_TITLE";
    		case BACKGROUND: return "BACKGROUND";
    		case SHADE_UNSCALE: return "SHADE_UNSCALE";
    		default: return "Unknown FillTypeCore value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FillTypeCore value.
    /// </summary>
    public static String toString(/*FillTypeCore*/int fillTypeCore)
    {
    	switch (fillTypeCore)
    	{
    		case SOLID: return "Solid";
    		case PATTERN: return "Pattern";
    		case TEXTURE: return "Texture";
    		case PICTURE: return "Picture";
    		case SHADE: return "Shade";
    		case SHADE_CENTER: return "ShadeCenter";
    		case SHADE_SHAPE: return "ShadeShape";
    		case SHADE_SCALE: return "ShadeScale";
    		case SHADE_TITLE: return "ShadeTitle";
    		case BACKGROUND: return "Background";
    		case SHADE_UNSCALE: return "ShadeUnscale";
    		default: return "Unknown FillTypeCore value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FillTypeCore name.
    /// </summary>
    public static /*FillTypeCore*/int fromName(String fillTypeCoreName)
    {
    	if ("SOLID".equals(fillTypeCoreName)) return SOLID;
    	if ("PATTERN".equals(fillTypeCoreName)) return PATTERN;
    	if ("TEXTURE".equals(fillTypeCoreName)) return TEXTURE;
    	if ("PICTURE".equals(fillTypeCoreName)) return PICTURE;
    	if ("SHADE".equals(fillTypeCoreName)) return SHADE;
    	if ("SHADE_CENTER".equals(fillTypeCoreName)) return SHADE_CENTER;
    	if ("SHADE_SHAPE".equals(fillTypeCoreName)) return SHADE_SHAPE;
    	if ("SHADE_SCALE".equals(fillTypeCoreName)) return SHADE_SCALE;
    	if ("SHADE_TITLE".equals(fillTypeCoreName)) return SHADE_TITLE;
    	if ("BACKGROUND".equals(fillTypeCoreName)) return BACKGROUND;
    	if ("SHADE_UNSCALE".equals(fillTypeCoreName)) return SHADE_UNSCALE;
    	throw new IllegalArgumentException("Unknown FillTypeCore name.");
    }
    
    /// <summary>
    /// Returns array of FillTypeCore values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		SOLID,
    		PATTERN,
    		TEXTURE,
    		PICTURE,
    		SHADE,
    		SHADE_CENTER,
    		SHADE_SHAPE,
    		SHADE_SCALE,
    		SHADE_TITLE,
    		BACKGROUND,
    		SHADE_UNSCALE,
    	};
    }

    public static final int length = 11;
}

