// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/02/2011 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


public /*enum*/ final class ColorModel
{
    private ColorModel(){}
    
    public static final int RGB = 0;
    public static final int INDEXED = 1;
    public static final int GRAY_SCALE = 2;
    public static final int CMYK = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ColorModel value.
    /// </summary>
    public static String getName(/*ColorModel*/int colorModel)
    {
    	switch (colorModel)
    	{
    		case RGB: return "RGB";
    		case INDEXED: return "INDEXED";
    		case GRAY_SCALE: return "GRAY_SCALE";
    		case CMYK: return "CMYK";
    		default: return "Unknown ColorModel value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ColorModel value.
    /// </summary>
    public static String toString(/*ColorModel*/int colorModel)
    {
    	switch (colorModel)
    	{
    		case RGB: return "Rgb";
    		case INDEXED: return "Indexed";
    		case GRAY_SCALE: return "GrayScale";
    		case CMYK: return "Cmyk";
    		default: return "Unknown ColorModel value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ColorModel name.
    /// </summary>
    public static /*ColorModel*/int fromName(String colorModelName)
    {
    	if ("RGB".equals(colorModelName)) return RGB;
    	if ("INDEXED".equals(colorModelName)) return INDEXED;
    	if ("GRAY_SCALE".equals(colorModelName)) return GRAY_SCALE;
    	if ("CMYK".equals(colorModelName)) return CMYK;
    	throw new IllegalArgumentException("Unknown ColorModel name.");
    }
    
    /// <summary>
    /// Returns array of ColorModel values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		RGB,
    		INDEXED,
    		GRAY_SCALE,
    		CMYK,
    	};
    }

    public static final int length = 4;
}

