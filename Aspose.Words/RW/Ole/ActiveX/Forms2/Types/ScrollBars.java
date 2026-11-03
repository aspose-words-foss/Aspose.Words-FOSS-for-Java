// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies whether the control has vertical scroll bars, horizontal scroll bars, both, or neither.
/// </summary>
/// <dev>
/// [MS-OFORMS] 2.5.74 ScrollBars (MorphData).
/// </dev>
/*enum*/ final class ScrollBars
{
    private ScrollBars(){}
    
    public static final int NONE = 0;
    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;
    public static final int BOTH = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ScrollBars value.
    /// </summary>
    public static String getName(/*ScrollBars*/int scrollBars)
    {
    	switch (scrollBars)
    	{
    		case NONE: return "NONE";
    		case HORIZONTAL: return "HORIZONTAL";
    		case VERTICAL: return "VERTICAL";
    		case BOTH: return "BOTH";
    		default: return "Unknown ScrollBars value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ScrollBars value.
    /// </summary>
    public static String toString(/*ScrollBars*/int scrollBars)
    {
    	switch (scrollBars)
    	{
    		case NONE: return "None";
    		case HORIZONTAL: return "Horizontal";
    		case VERTICAL: return "Vertical";
    		case BOTH: return "Both";
    		default: return "Unknown ScrollBars value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ScrollBars name.
    /// </summary>
    public static /*ScrollBars*/int fromName(String scrollBarsName)
    {
    	if ("NONE".equals(scrollBarsName)) return NONE;
    	if ("HORIZONTAL".equals(scrollBarsName)) return HORIZONTAL;
    	if ("VERTICAL".equals(scrollBarsName)) return VERTICAL;
    	if ("BOTH".equals(scrollBarsName)) return BOTH;
    	throw new IllegalArgumentException("Unknown ScrollBars name.");
    }
    
    /// <summary>
    /// Returns array of ScrollBars values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		HORIZONTAL,
    		VERTICAL,
    		BOTH,
    	};
    }

    public static final int length = 4;
}

