// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2015 by Dmitry Burov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how colors are rendered.
/// </summary>
public /*enum*/ final class ColorMode
{
    private ColorMode(){}
    
    /// <summary>
    /// Rendering with unmodified colors.
    /// </summary>
    public static final int NORMAL = 0;
    /// <summary>
    /// Rendering with colors in a range of gray shades from white to black.
    /// </summary>
    public static final int GRAYSCALE = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ColorMode value.
    /// </summary>
    public static String getName(/*ColorMode*/int colorMode)
    {
    	switch (colorMode)
    	{
    		case NORMAL: return "NORMAL";
    		case GRAYSCALE: return "GRAYSCALE";
    		default: return "Unknown ColorMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ColorMode value.
    /// </summary>
    public static String toString(/*ColorMode*/int colorMode)
    {
    	switch (colorMode)
    	{
    		case NORMAL: return "Normal";
    		case GRAYSCALE: return "Grayscale";
    		default: return "Unknown ColorMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ColorMode name.
    /// </summary>
    public static /*ColorMode*/int fromName(String colorModeName)
    {
    	if ("NORMAL".equals(colorModeName)) return NORMAL;
    	if ("GRAYSCALE".equals(colorModeName)) return GRAYSCALE;
    	throw new IllegalArgumentException("Unknown ColorMode name.");
    }
    
    /// <summary>
    /// Returns array of ColorMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NORMAL,
    		GRAYSCALE,
    	};
    }

    public static final int length = 2;
}

