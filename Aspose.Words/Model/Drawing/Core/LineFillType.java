// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/05/2015 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how to "fill" the line contour.
/// </summary>
/// <seealso cref="Stroke.LineFillType"/>
/*enum*/ final class LineFillType
{
    private LineFillType(){}
    
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
    /// Center a picture in the shape.
    /// </summary>
    public static final int PICTURE = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer LineFillType value.
    /// </summary>
    public static String getName(/*LineFillType*/int lineFillType)
    {
    	switch (lineFillType)
    	{
    		case SOLID: return "SOLID";
    		case PATTERN: return "PATTERN";
    		case TEXTURE: return "TEXTURE";
    		case PICTURE: return "PICTURE";
    		default: return "Unknown LineFillType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer LineFillType value.
    /// </summary>
    public static String toString(/*LineFillType*/int lineFillType)
    {
    	switch (lineFillType)
    	{
    		case SOLID: return "Solid";
    		case PATTERN: return "Pattern";
    		case TEXTURE: return "Texture";
    		case PICTURE: return "Picture";
    		default: return "Unknown LineFillType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by LineFillType name.
    /// </summary>
    public static /*LineFillType*/int fromName(String lineFillTypeName)
    {
    	if ("SOLID".equals(lineFillTypeName)) return SOLID;
    	if ("PATTERN".equals(lineFillTypeName)) return PATTERN;
    	if ("TEXTURE".equals(lineFillTypeName)) return TEXTURE;
    	if ("PICTURE".equals(lineFillTypeName)) return PICTURE;
    	throw new IllegalArgumentException("Unknown LineFillType name.");
    }
    
    /// <summary>
    /// Returns array of LineFillType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		SOLID,
    		PATTERN,
    		TEXTURE,
    		PICTURE,
    	};
    }

    public static final int length = 4;
}

