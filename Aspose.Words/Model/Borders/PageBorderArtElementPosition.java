// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/07/2012 by Dmitry Bormashov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/*enum*/ final class PageBorderArtElementPosition
{
    private PageBorderArtElementPosition(){}
    
    public static final int FIRST = 0;
    public static final int MIDDLE = 1;
    public static final int LAST = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer PageBorderArtElementPosition value.
    /// </summary>
    public static String getName(/*PageBorderArtElementPosition*/int pageBorderArtElementPosition)
    {
    	switch (pageBorderArtElementPosition)
    	{
    		case FIRST: return "FIRST";
    		case MIDDLE: return "MIDDLE";
    		case LAST: return "LAST";
    		default: return "Unknown PageBorderArtElementPosition value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer PageBorderArtElementPosition value.
    /// </summary>
    public static String toString(/*PageBorderArtElementPosition*/int pageBorderArtElementPosition)
    {
    	switch (pageBorderArtElementPosition)
    	{
    		case FIRST: return "First";
    		case MIDDLE: return "Middle";
    		case LAST: return "Last";
    		default: return "Unknown PageBorderArtElementPosition value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by PageBorderArtElementPosition name.
    /// </summary>
    public static /*PageBorderArtElementPosition*/int fromName(String pageBorderArtElementPositionName)
    {
    	if ("FIRST".equals(pageBorderArtElementPositionName)) return FIRST;
    	if ("MIDDLE".equals(pageBorderArtElementPositionName)) return MIDDLE;
    	if ("LAST".equals(pageBorderArtElementPositionName)) return LAST;
    	throw new IllegalArgumentException("Unknown PageBorderArtElementPosition name.");
    }
    
    /// <summary>
    /// Returns array of PageBorderArtElementPosition values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		FIRST,
    		MIDDLE,
    		LAST,
    	};
    }

    public static final int length = 3;
}

