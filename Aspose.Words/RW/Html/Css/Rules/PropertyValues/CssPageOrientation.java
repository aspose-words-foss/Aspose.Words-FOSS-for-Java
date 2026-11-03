// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/06/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies page orientation. 
/// </summary>
/*enum*/ final class CssPageOrientation
{
    private CssPageOrientation(){}
    
    /// <summary>
    /// If a page size is not specified, the size of the page sheet is chosen by the UA.
    /// </summary>
    public static final int NOT_SPECIFIED = 0;
    /// <summary>
    /// Specifies that the page's content be printed in landscape orientation. The longer sides of the page box are horizontal.
    /// </summary>
    public static final int LANDSCAPE = 1;
    /// <summary>
    /// Specifies that the page's content be printed in portrait orientation. The shorter sides of the page box are horizontal. 
    /// </summary>
    public static final int PORTRAIT = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CssPageOrientation value.
    /// </summary>
    public static String getName(/*CssPageOrientation*/int cssPageOrientation)
    {
    	switch (cssPageOrientation)
    	{
    		case NOT_SPECIFIED: return "NOT_SPECIFIED";
    		case LANDSCAPE: return "LANDSCAPE";
    		case PORTRAIT: return "PORTRAIT";
    		default: return "Unknown CssPageOrientation value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CssPageOrientation value.
    /// </summary>
    public static String toString(/*CssPageOrientation*/int cssPageOrientation)
    {
    	switch (cssPageOrientation)
    	{
    		case NOT_SPECIFIED: return "NotSpecified";
    		case LANDSCAPE: return "Landscape";
    		case PORTRAIT: return "Portrait";
    		default: return "Unknown CssPageOrientation value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CssPageOrientation name.
    /// </summary>
    public static /*CssPageOrientation*/int fromName(String cssPageOrientationName)
    {
    	if ("NOT_SPECIFIED".equals(cssPageOrientationName)) return NOT_SPECIFIED;
    	if ("LANDSCAPE".equals(cssPageOrientationName)) return LANDSCAPE;
    	if ("PORTRAIT".equals(cssPageOrientationName)) return PORTRAIT;
    	throw new IllegalArgumentException("Unknown CssPageOrientation name.");
    }
    
    /// <summary>
    /// Returns array of CssPageOrientation values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NOT_SPECIFIED,
    		LANDSCAPE,
    		PORTRAIT,
    	};
    }

    public static final int length = 3;
}

