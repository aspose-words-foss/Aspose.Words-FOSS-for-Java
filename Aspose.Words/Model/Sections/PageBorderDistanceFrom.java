// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the positioning of the page border relative to the page margin.
/// </summary>
/// <seealso cref="PageSetup"/>
/// <seealso cref="PageSetup.BorderDistanceFrom"/>
public /*enum*/ final class PageBorderDistanceFrom
{
    private PageBorderDistanceFrom(){}
    
    /// <summary>
    /// Border position is measured from the page margin.
    /// </summary>
    public static final int TEXT = 0;
    /// <summary>
    /// Border position is measured from the page edge.
    /// </summary>
    public static final int PAGE_EDGE = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer PageBorderDistanceFrom value.
    /// </summary>
    public static String getName(/*PageBorderDistanceFrom*/int pageBorderDistanceFrom)
    {
    	switch (pageBorderDistanceFrom)
    	{
    		case TEXT: return "TEXT";
    		case PAGE_EDGE: return "PAGE_EDGE";
    		default: return "Unknown PageBorderDistanceFrom value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer PageBorderDistanceFrom value.
    /// </summary>
    public static String toString(/*PageBorderDistanceFrom*/int pageBorderDistanceFrom)
    {
    	switch (pageBorderDistanceFrom)
    	{
    		case TEXT: return "Text";
    		case PAGE_EDGE: return "PageEdge";
    		default: return "Unknown PageBorderDistanceFrom value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by PageBorderDistanceFrom name.
    /// </summary>
    public static /*PageBorderDistanceFrom*/int fromName(String pageBorderDistanceFromName)
    {
    	if ("TEXT".equals(pageBorderDistanceFromName)) return TEXT;
    	if ("PAGE_EDGE".equals(pageBorderDistanceFromName)) return PAGE_EDGE;
    	throw new IllegalArgumentException("Unknown PageBorderDistanceFrom name.");
    }
    
    /// <summary>
    /// Returns array of PageBorderDistanceFrom values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TEXT,
    		PAGE_EDGE,
    	};
    }

    public static final int length = 2;
}

