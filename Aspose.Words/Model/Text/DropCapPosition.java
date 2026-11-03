// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the position for a drop cap text.
/// </summary>
public /*enum*/ final class DropCapPosition
{
    private DropCapPosition(){}
    
    /// <summary>
    /// The paragraph does not have a drop cap.
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    /// The drop cap is positioned inside the text margin on the anchor paragraph.
    /// </summary>
    public static final int NORMAL = 1;
    /// <summary>
    /// The drop cap is positioned outside the text margin on the anchor paragraph.
    /// </summary>
    public static final int MARGIN = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DropCapPosition value.
    /// </summary>
    public static String getName(/*DropCapPosition*/int dropCapPosition)
    {
    	switch (dropCapPosition)
    	{
    		case NONE: return "NONE";
    		case NORMAL: return "NORMAL";
    		case MARGIN: return "MARGIN";
    		default: return "Unknown DropCapPosition value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DropCapPosition value.
    /// </summary>
    public static String toString(/*DropCapPosition*/int dropCapPosition)
    {
    	switch (dropCapPosition)
    	{
    		case NONE: return "None";
    		case NORMAL: return "Normal";
    		case MARGIN: return "Margin";
    		default: return "Unknown DropCapPosition value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DropCapPosition name.
    /// </summary>
    public static /*DropCapPosition*/int fromName(String dropCapPositionName)
    {
    	if ("NONE".equals(dropCapPositionName)) return NONE;
    	if ("NORMAL".equals(dropCapPositionName)) return NORMAL;
    	if ("MARGIN".equals(dropCapPositionName)) return MARGIN;
    	throw new IllegalArgumentException("Unknown DropCapPosition name.");
    }
    
    /// <summary>
    /// Returns array of DropCapPosition values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		NORMAL,
    		MARGIN,
    	};
    }

    public static final int length = 3;
}

