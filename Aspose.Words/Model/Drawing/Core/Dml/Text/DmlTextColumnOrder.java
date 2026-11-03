// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Order of columns in text body.
/// </summary>
/*enum*/ final class DmlTextColumnOrder
{
    private DmlTextColumnOrder(){}
    
    public static final int LEFT_TO_RIGHT = 0;
    public static final int RIGHT_TO_LEFT = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlTextColumnOrder value.
    /// </summary>
    public static String getName(/*DmlTextColumnOrder*/int dmlTextColumnOrder)
    {
    	switch (dmlTextColumnOrder)
    	{
    		case LEFT_TO_RIGHT: return "LEFT_TO_RIGHT";
    		case RIGHT_TO_LEFT: return "RIGHT_TO_LEFT";
    		default: return "Unknown DmlTextColumnOrder value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlTextColumnOrder value.
    /// </summary>
    public static String toString(/*DmlTextColumnOrder*/int dmlTextColumnOrder)
    {
    	switch (dmlTextColumnOrder)
    	{
    		case LEFT_TO_RIGHT: return "LeftToRight";
    		case RIGHT_TO_LEFT: return "RightToLeft";
    		default: return "Unknown DmlTextColumnOrder value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlTextColumnOrder name.
    /// </summary>
    public static /*DmlTextColumnOrder*/int fromName(String dmlTextColumnOrderName)
    {
    	if ("LEFT_TO_RIGHT".equals(dmlTextColumnOrderName)) return LEFT_TO_RIGHT;
    	if ("RIGHT_TO_LEFT".equals(dmlTextColumnOrderName)) return RIGHT_TO_LEFT;
    	throw new IllegalArgumentException("Unknown DmlTextColumnOrder name.");
    }
    
    /// <summary>
    /// Returns array of DmlTextColumnOrder values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		LEFT_TO_RIGHT,
    		RIGHT_TO_LEFT,
    	};
    }

    public static final int length = 2;
}
