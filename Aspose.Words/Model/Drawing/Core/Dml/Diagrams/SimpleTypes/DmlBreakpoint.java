// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2014 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.11 ST_Breakpoint (Breakpoint)
/// </summary>
/*enum*/ final class DmlBreakpoint
{
    private DmlBreakpoint(){}
    
    public static final int BALANCED = 0;
    public static final int END_OF_CANVAS = 1;
    public static final int FIXED = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlBreakpoint value.
    /// </summary>
    public static String getName(/*DmlBreakpoint*/int dmlBreakpoint)
    {
    	switch (dmlBreakpoint)
    	{
    		case BALANCED: return "BALANCED";
    		case END_OF_CANVAS: return "END_OF_CANVAS";
    		case FIXED: return "FIXED";
    		default: return "Unknown DmlBreakpoint value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlBreakpoint value.
    /// </summary>
    public static String toString(/*DmlBreakpoint*/int dmlBreakpoint)
    {
    	switch (dmlBreakpoint)
    	{
    		case BALANCED: return "Balanced";
    		case END_OF_CANVAS: return "EndOfCanvas";
    		case FIXED: return "Fixed";
    		default: return "Unknown DmlBreakpoint value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlBreakpoint name.
    /// </summary>
    public static /*DmlBreakpoint*/int fromName(String dmlBreakpointName)
    {
    	if ("BALANCED".equals(dmlBreakpointName)) return BALANCED;
    	if ("END_OF_CANVAS".equals(dmlBreakpointName)) return END_OF_CANVAS;
    	if ("FIXED".equals(dmlBreakpointName)) return FIXED;
    	throw new IllegalArgumentException("Unknown DmlBreakpoint name.");
    }
    
    /// <summary>
    /// Returns array of DmlBreakpoint values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BALANCED,
    		END_OF_CANVAS,
    		FIXED,
    	};
    }

    public static final int length = 3;
}

