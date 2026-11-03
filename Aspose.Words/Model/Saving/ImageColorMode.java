// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/16/2010 by Victor Kazmiruk

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the color mode for the generated images of document pages.
/// </summary>
public /*enum*/ final class ImageColorMode
{
    private ImageColorMode(){}
    
    /// <summary>
    /// The pages of the document will be rendered as color images.
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    /// The pages of the document will be rendered as grayscale images.
    /// </summary>
    public static final int GRAYSCALE = 1;
    /// <summary>
    /// The pages of the document will be rendered as black and white images.
    /// </summary>
    public static final int BLACK_AND_WHITE = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ImageColorMode value.
    /// </summary>
    public static String getName(/*ImageColorMode*/int imageColorMode)
    {
    	switch (imageColorMode)
    	{
    		case NONE: return "NONE";
    		case GRAYSCALE: return "GRAYSCALE";
    		case BLACK_AND_WHITE: return "BLACK_AND_WHITE";
    		default: return "Unknown ImageColorMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ImageColorMode value.
    /// </summary>
    public static String toString(/*ImageColorMode*/int imageColorMode)
    {
    	switch (imageColorMode)
    	{
    		case NONE: return "None";
    		case GRAYSCALE: return "Grayscale";
    		case BLACK_AND_WHITE: return "BlackAndWhite";
    		default: return "Unknown ImageColorMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ImageColorMode name.
    /// </summary>
    public static /*ImageColorMode*/int fromName(String imageColorModeName)
    {
    	if ("NONE".equals(imageColorModeName)) return NONE;
    	if ("GRAYSCALE".equals(imageColorModeName)) return GRAYSCALE;
    	if ("BLACK_AND_WHITE".equals(imageColorModeName)) return BLACK_AND_WHITE;
    	throw new IllegalArgumentException("Unknown ImageColorMode name.");
    }
    
    /// <summary>
    /// Returns array of ImageColorMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		GRAYSCALE,
    		BLACK_AND_WHITE,
    	};
    }

    public static final int length = 3;
}

