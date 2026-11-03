// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies vertical justification of text on each page.
/// </summary>
/// <seealso cref="PageSetup"/>
/// <seealso cref="PageSetup.VerticalAlignment"/>
public /*enum*/ final class PageVerticalAlignment
{
    private PageVerticalAlignment(){}
    
    /// <summary>
    /// Text is aligned at the bottom of the page.
    /// </summary>
    public static final int BOTTOM = 3;
    /// <summary>
    /// Text is aligned in the middle of the page.
    /// </summary>
    public static final int CENTER = 1;
    /// <summary>
    /// Text is spread to fill the page.
    /// </summary>
    public static final int JUSTIFY = 2;
    /// <summary>
    /// Text is aligned at the top of the page.
    /// </summary>
    public static final int TOP = 0;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer PageVerticalAlignment value.
    /// </summary>
    public static String getName(/*PageVerticalAlignment*/int pageVerticalAlignment)
    {
    	switch (pageVerticalAlignment)
    	{
    		case BOTTOM: return "BOTTOM";
    		case CENTER: return "CENTER";
    		case JUSTIFY: return "JUSTIFY";
    		case TOP: return "TOP";
    		default: return "Unknown PageVerticalAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer PageVerticalAlignment value.
    /// </summary>
    public static String toString(/*PageVerticalAlignment*/int pageVerticalAlignment)
    {
    	switch (pageVerticalAlignment)
    	{
    		case BOTTOM: return "Bottom";
    		case CENTER: return "Center";
    		case JUSTIFY: return "Justify";
    		case TOP: return "Top";
    		default: return "Unknown PageVerticalAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by PageVerticalAlignment name.
    /// </summary>
    public static /*PageVerticalAlignment*/int fromName(String pageVerticalAlignmentName)
    {
    	if ("BOTTOM".equals(pageVerticalAlignmentName)) return BOTTOM;
    	if ("CENTER".equals(pageVerticalAlignmentName)) return CENTER;
    	if ("JUSTIFY".equals(pageVerticalAlignmentName)) return JUSTIFY;
    	if ("TOP".equals(pageVerticalAlignmentName)) return TOP;
    	throw new IllegalArgumentException("Unknown PageVerticalAlignment name.");
    }
    
    /// <summary>
    /// Returns array of PageVerticalAlignment values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BOTTOM,
    		CENTER,
    		JUSTIFY,
    		TOP,
    	};
    }

    public static final int length = 4;
}

