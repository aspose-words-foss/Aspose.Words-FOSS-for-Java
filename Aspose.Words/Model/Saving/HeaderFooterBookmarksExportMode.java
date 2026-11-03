// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/06/2018 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how bookmarks in headers/footers are exported.
/// </summary>
public /*enum*/ final class HeaderFooterBookmarksExportMode
{
    private HeaderFooterBookmarksExportMode(){}
    
    /// <summary>
    /// Bookmarks in headers/footers are not exported.
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    /// Only bookmark in first header/footer of the section is exported.
    /// </summary>
    public static final int FIRST = 1;
    /// <summary>
    /// Bookmarks in all headers/footers are exported.
    /// </summary>
    public static final int ALL = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer HeaderFooterBookmarksExportMode value.
    /// </summary>
    public static String getName(/*HeaderFooterBookmarksExportMode*/int headerFooterBookmarksExportMode)
    {
    	switch (headerFooterBookmarksExportMode)
    	{
    		case NONE: return "NONE";
    		case FIRST: return "FIRST";
    		case ALL: return "ALL";
    		default: return "Unknown HeaderFooterBookmarksExportMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer HeaderFooterBookmarksExportMode value.
    /// </summary>
    public static String toString(/*HeaderFooterBookmarksExportMode*/int headerFooterBookmarksExportMode)
    {
    	switch (headerFooterBookmarksExportMode)
    	{
    		case NONE: return "None";
    		case FIRST: return "First";
    		case ALL: return "All";
    		default: return "Unknown HeaderFooterBookmarksExportMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by HeaderFooterBookmarksExportMode name.
    /// </summary>
    public static /*HeaderFooterBookmarksExportMode*/int fromName(String headerFooterBookmarksExportModeName)
    {
    	if ("NONE".equals(headerFooterBookmarksExportModeName)) return NONE;
    	if ("FIRST".equals(headerFooterBookmarksExportModeName)) return FIRST;
    	if ("ALL".equals(headerFooterBookmarksExportModeName)) return ALL;
    	throw new IllegalArgumentException("Unknown HeaderFooterBookmarksExportMode name.");
    }
    
    /// <summary>
    /// Returns array of HeaderFooterBookmarksExportMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		FIRST,
    		ALL,
    	};
    }

    public static final int length = 3;
}

