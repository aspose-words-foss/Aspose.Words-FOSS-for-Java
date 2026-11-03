// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies page orientation.
/// </summary>
public /*enum*/ final class Orientation
{
    private Orientation(){}
    
    /// <summary>
    /// Portrait page orientation (narrow and tall).
    /// </summary>
    public static final int PORTRAIT = 1;
    /// <summary>
    /// Landscape page orientation (wide and short).
    /// </summary>
    public static final int LANDSCAPE = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer Orientation value.
    /// </summary>
    public static String getName(/*Orientation*/int orientation)
    {
    	switch (orientation)
    	{
    		case PORTRAIT: return "PORTRAIT";
    		case LANDSCAPE: return "LANDSCAPE";
    		default: return "Unknown Orientation value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer Orientation value.
    /// </summary>
    public static String toString(/*Orientation*/int orientation)
    {
    	switch (orientation)
    	{
    		case PORTRAIT: return "Portrait";
    		case LANDSCAPE: return "Landscape";
    		default: return "Unknown Orientation value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by Orientation name.
    /// </summary>
    public static /*Orientation*/int fromName(String orientationName)
    {
    	if ("PORTRAIT".equals(orientationName)) return PORTRAIT;
    	if ("LANDSCAPE".equals(orientationName)) return LANDSCAPE;
    	throw new IllegalArgumentException("Unknown Orientation name.");
    }
    
    /// <summary>
    /// Returns array of Orientation values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		PORTRAIT,
    		LANDSCAPE,
    	};
    }

    public static final int length = 2;
}

