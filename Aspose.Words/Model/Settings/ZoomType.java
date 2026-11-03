// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Possible values for how large or small the document appears on the screen in Microsoft Word.
/// </summary>
/// <seealso cref="ViewOptions"/>
/// <seealso cref="ViewOptions.ZoomType"/>
public /*enum*/ final class ZoomType
{
    private ZoomType(){}
    
    /// <summary>
    /// Zoom percentage is set explicitly. It is not recalculated automatically when control size changes.
    /// </summary>
    public static final int CUSTOM = 0;

    /// <summary>
    /// Indicates to use the explicit zoom percentage. Same as <see cref="Custom"/>.
    /// </summary>
    public static final int NONE = CUSTOM;

    /// <summary>
    /// Zoom percentage is automatically recalculated to fit one full page.
    /// </summary>
    public static final int FULL_PAGE = 1;

    /// <summary>
    /// Zoom percentage is automatically recalculated to fit page width.
    /// </summary>
    public static final int PAGE_WIDTH = 2;

    /// <summary>
    /// Zoom percentage is automatically recalculated to fit text.
    /// </summary>
    public static final int TEXT_FIT = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ZoomType value.
    /// </summary>
    public static String getName(/*ZoomType*/int zoomType)
    {
    	switch (zoomType)
    	{
    		case CUSTOM:
    		/*case NONE:*/
    			return "CUSTOM" + " | " + "NONE";
    		case FULL_PAGE: return "FULL_PAGE";
    		case PAGE_WIDTH: return "PAGE_WIDTH";
    		case TEXT_FIT: return "TEXT_FIT";
    		default: return "Unknown ZoomType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ZoomType value.
    /// </summary>
    public static String toString(/*ZoomType*/int zoomType)
    {
    	switch (zoomType)
    	{
    		case CUSTOM:
    		/*case NONE:*/
    			return "Custom" + " | " + "None";
    		case FULL_PAGE: return "FullPage";
    		case PAGE_WIDTH: return "PageWidth";
    		case TEXT_FIT: return "TextFit";
    		default: return "Unknown ZoomType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ZoomType name.
    /// </summary>
    public static /*ZoomType*/int fromName(String zoomTypeName)
    {
    	if ("CUSTOM".equals(zoomTypeName)) return CUSTOM;
    	if ("NONE".equals(zoomTypeName)) return NONE;
    	if ("FULL_PAGE".equals(zoomTypeName)) return FULL_PAGE;
    	if ("PAGE_WIDTH".equals(zoomTypeName)) return PAGE_WIDTH;
    	if ("TEXT_FIT".equals(zoomTypeName)) return TEXT_FIT;
    	throw new IllegalArgumentException("Unknown ZoomType name.");
    }
    
    /// <summary>
    /// Returns array of ZoomType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		CUSTOM,
    		NONE,
    		FULL_PAGE,
    		PAGE_WIDTH,
    		TEXT_FIT,
    	};
    }

    public static final int length = 5;
}

