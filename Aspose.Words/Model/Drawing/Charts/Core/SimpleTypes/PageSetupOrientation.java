// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This enum specifies the page orientation of the printed page(s) on which this chart shall appear.
/// Corresponds ST_PageSetupOrientation simple type (5.7.3.32).
/// </summary>
/*enum*/ final class PageSetupOrientation
{
    private PageSetupOrientation(){}
    
    /// <summary>
    /// Specifies that the page orientation shall be the default orientation of the system.
    /// </summary>
    public static final int DEFAULT = 0;
    /// <summary>
    /// Specifies that the printed page shall have landscape orientation.
    /// </summary>
    public static final int LANDSCAPE = 1;
    /// <summary>
    /// Specifies that the printed page shall have portrait orientation.
    /// </summary>
    public static final int PORTRAIT = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer PageSetupOrientation value.
    /// </summary>
    public static String getName(/*PageSetupOrientation*/int pageSetupOrientation)
    {
    	switch (pageSetupOrientation)
    	{
    		case DEFAULT: return "DEFAULT";
    		case LANDSCAPE: return "LANDSCAPE";
    		case PORTRAIT: return "PORTRAIT";
    		default: return "Unknown PageSetupOrientation value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer PageSetupOrientation value.
    /// </summary>
    public static String toString(/*PageSetupOrientation*/int pageSetupOrientation)
    {
    	switch (pageSetupOrientation)
    	{
    		case DEFAULT: return "Default";
    		case LANDSCAPE: return "Landscape";
    		case PORTRAIT: return "Portrait";
    		default: return "Unknown PageSetupOrientation value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by PageSetupOrientation name.
    /// </summary>
    public static /*PageSetupOrientation*/int fromName(String pageSetupOrientationName)
    {
    	if ("DEFAULT".equals(pageSetupOrientationName)) return DEFAULT;
    	if ("LANDSCAPE".equals(pageSetupOrientationName)) return LANDSCAPE;
    	if ("PORTRAIT".equals(pageSetupOrientationName)) return PORTRAIT;
    	throw new IllegalArgumentException("Unknown PageSetupOrientation name.");
    }
    
    /// <summary>
    /// Returns array of PageSetupOrientation values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DEFAULT,
    		LANDSCAPE,
    		PORTRAIT,
    	};
    }

    public static final int length = 3;
}

