// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/05/2025 by Dmitry Burov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies internal layout modes for rendering multiple pages into a single output.
/// </summary>
/*enum*/ final class MultiPageLayoutMode
{
    private MultiPageLayoutMode(){}
    
    /// <summary>
    /// A single page is rendered.
    /// If the specified page set contains multiple pages, only the first one is rendered.
    /// </summary>
    public static final int SINGLE_PAGE = 0;

    /// <summary>
    /// All pages from the specified page set are rendered vertically, one below another,
    /// within a single image or document.
    /// </summary>
    public static final int VERTICAL = 1;

    /// <summary>
    /// All pages from the specified page set are rendered horizontally, one beside another,
    /// within a single image or document.
    /// </summary>
    public static final int HORIZONTAL = 2;

    /// <summary>
    /// Pages are rendered in a grid layout with a fixed number of columns. When the column
    /// limit is reached, rendering continues in the next row.
    /// </summary>
    public static final int GRID = 3;

    /// <summary>
    /// Each page is rendered as a separate frame in a multi-frame TIFF image.
    /// This mode is applicable only to TIFF image formats.
    /// </summary>
    public static final int TIFF_FRAMES = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MultiPageLayoutMode value.
    /// </summary>
    public static String getName(/*MultiPageLayoutMode*/int multiPageLayoutMode)
    {
    	switch (multiPageLayoutMode)
    	{
    		case SINGLE_PAGE: return "SINGLE_PAGE";
    		case VERTICAL: return "VERTICAL";
    		case HORIZONTAL: return "HORIZONTAL";
    		case GRID: return "GRID";
    		case TIFF_FRAMES: return "TIFF_FRAMES";
    		default: return "Unknown MultiPageLayoutMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MultiPageLayoutMode value.
    /// </summary>
    public static String toString(/*MultiPageLayoutMode*/int multiPageLayoutMode)
    {
    	switch (multiPageLayoutMode)
    	{
    		case SINGLE_PAGE: return "SinglePage";
    		case VERTICAL: return "Vertical";
    		case HORIZONTAL: return "Horizontal";
    		case GRID: return "Grid";
    		case TIFF_FRAMES: return "TiffFrames";
    		default: return "Unknown MultiPageLayoutMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MultiPageLayoutMode name.
    /// </summary>
    public static /*MultiPageLayoutMode*/int fromName(String multiPageLayoutModeName)
    {
    	if ("SINGLE_PAGE".equals(multiPageLayoutModeName)) return SINGLE_PAGE;
    	if ("VERTICAL".equals(multiPageLayoutModeName)) return VERTICAL;
    	if ("HORIZONTAL".equals(multiPageLayoutModeName)) return HORIZONTAL;
    	if ("GRID".equals(multiPageLayoutModeName)) return GRID;
    	if ("TIFF_FRAMES".equals(multiPageLayoutModeName)) return TIFF_FRAMES;
    	throw new IllegalArgumentException("Unknown MultiPageLayoutMode name.");
    }
    
    /// <summary>
    /// Returns array of MultiPageLayoutMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		SINGLE_PAGE,
    		VERTICAL,
    		HORIZONTAL,
    		GRID,
    		TIFF_FRAMES,
    	};
    }

    public static final int length = 5;
}

