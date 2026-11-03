// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This enum specifies the possible directions for a bar chart.
/// Corresponds ST_BarDir simple type (5.7.3.3).
/// </summary>
/*enum*/ final class BarDirection
{
    private BarDirection(){}
    
    public static final int BAR = 0;
    public static final int COLUMN = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer BarDirection value.
    /// </summary>
    public static String getName(/*BarDirection*/int barDirection)
    {
    	switch (barDirection)
    	{
    		case BAR: return "BAR";
    		case COLUMN: return "COLUMN";
    		default: return "Unknown BarDirection value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer BarDirection value.
    /// </summary>
    public static String toString(/*BarDirection*/int barDirection)
    {
    	switch (barDirection)
    	{
    		case BAR: return "Bar";
    		case COLUMN: return "Column";
    		default: return "Unknown BarDirection value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by BarDirection name.
    /// </summary>
    public static /*BarDirection*/int fromName(String barDirectionName)
    {
    	if ("BAR".equals(barDirectionName)) return BAR;
    	if ("COLUMN".equals(barDirectionName)) return COLUMN;
    	throw new IllegalArgumentException("Unknown BarDirection name.");
    }
    
    /// <summary>
    /// Returns array of BarDirection values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BAR,
    		COLUMN,
    	};
    }

    public static final int length = 2;
}

