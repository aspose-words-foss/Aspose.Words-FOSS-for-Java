// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the type of border used by the control.
/// </summary>
/*enum*/ final class BorderStyle
{
    private BorderStyle(){}
    
    public static final int NONE = 0;
    public static final int SINGLE = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer BorderStyle value.
    /// </summary>
    public static String getName(/*BorderStyle*/int borderStyle)
    {
    	switch (borderStyle)
    	{
    		case NONE: return "NONE";
    		case SINGLE: return "SINGLE";
    		default: return "Unknown BorderStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer BorderStyle value.
    /// </summary>
    public static String toString(/*BorderStyle*/int borderStyle)
    {
    	switch (borderStyle)
    	{
    		case NONE: return "None";
    		case SINGLE: return "Single";
    		default: return "Unknown BorderStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by BorderStyle name.
    /// </summary>
    public static /*BorderStyle*/int fromName(String borderStyleName)
    {
    	if ("NONE".equals(borderStyleName)) return NONE;
    	if ("SINGLE".equals(borderStyleName)) return SINGLE;
    	throw new IllegalArgumentException("Unknown BorderStyle name.");
    }
    
    /// <summary>
    /// Returns array of BorderStyle values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		SINGLE,
    	};
    }

    public static final int length = 2;
}

