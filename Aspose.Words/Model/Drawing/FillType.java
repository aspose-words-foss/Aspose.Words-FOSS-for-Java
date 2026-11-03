// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/12/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies fill type for a fillable object.
/// </summary>
public /*enum*/ final class FillType
{
    private FillType(){}
    
    /// <summary>
    /// Solid fill.
    /// </summary>
    public static final int SOLID = 1;

    /// <summary>
    /// Patterned fill.
    /// </summary>
    public static final int PATTERNED = 2;

    /// <summary>
    /// Gradient fill.
    /// </summary>
    public static final int GRADIENT = 3;

    /// <summary>
    /// Textured fill.
    /// </summary>
    public static final int TEXTURED = 4;

    /// <summary>
    /// Fill is the same as the background.
    /// </summary>
    public static final int BACKGROUND = 5;

    /// <summary>
    /// Picture fill.
    /// </summary>
    public static final int PICTURE = 6;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FillType value.
    /// </summary>
    public static String getName(/*FillType*/int fillType)
    {
    	switch (fillType)
    	{
    		case SOLID: return "SOLID";
    		case PATTERNED: return "PATTERNED";
    		case GRADIENT: return "GRADIENT";
    		case TEXTURED: return "TEXTURED";
    		case BACKGROUND: return "BACKGROUND";
    		case PICTURE: return "PICTURE";
    		default: return "Unknown FillType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FillType value.
    /// </summary>
    public static String toString(/*FillType*/int fillType)
    {
    	switch (fillType)
    	{
    		case SOLID: return "Solid";
    		case PATTERNED: return "Patterned";
    		case GRADIENT: return "Gradient";
    		case TEXTURED: return "Textured";
    		case BACKGROUND: return "Background";
    		case PICTURE: return "Picture";
    		default: return "Unknown FillType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FillType name.
    /// </summary>
    public static /*FillType*/int fromName(String fillTypeName)
    {
    	if ("SOLID".equals(fillTypeName)) return SOLID;
    	if ("PATTERNED".equals(fillTypeName)) return PATTERNED;
    	if ("GRADIENT".equals(fillTypeName)) return GRADIENT;
    	if ("TEXTURED".equals(fillTypeName)) return TEXTURED;
    	if ("BACKGROUND".equals(fillTypeName)) return BACKGROUND;
    	if ("PICTURE".equals(fillTypeName)) return PICTURE;
    	throw new IllegalArgumentException("Unknown FillType name.");
    }
    
    /// <summary>
    /// Returns array of FillType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		SOLID,
    		PATTERNED,
    		GRADIENT,
    		TEXTURED,
    		BACKGROUND,
    		PICTURE,
    	};
    }

    public static final int length = 6;
}

