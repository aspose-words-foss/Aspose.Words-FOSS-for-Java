// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This enum specifies the possible directions for error bars.
/// Corresponds ST_ErrDir simple type (5.7.3.13).
/// </summary>
/*enum*/ final class ErrorBarDirection
{
    private ErrorBarDirection(){}
    
    /// <summary>
    /// Specifies that error bars shall be shown in the x direction.
    /// </summary>
    public static final int X = 0;
    /// <summary>
    /// Specifies that error bars shall be shown in the y direction.
    /// </summary>
    public static final int Y = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ErrorBarDirection value.
    /// </summary>
    public static String getName(/*ErrorBarDirection*/int errorBarDirection)
    {
    	switch (errorBarDirection)
    	{
    		case X: return "X";
    		case Y: return "Y";
    		default: return "Unknown ErrorBarDirection value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ErrorBarDirection value.
    /// </summary>
    public static String toString(/*ErrorBarDirection*/int errorBarDirection)
    {
    	switch (errorBarDirection)
    	{
    		case X: return "X";
    		case Y: return "Y";
    		default: return "Unknown ErrorBarDirection value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ErrorBarDirection name.
    /// </summary>
    public static /*ErrorBarDirection*/int fromName(String errorBarDirectionName)
    {
    	if ("X".equals(errorBarDirectionName)) return X;
    	if ("Y".equals(errorBarDirectionName)) return Y;
    	throw new IllegalArgumentException("Unknown ErrorBarDirection name.");
    }
    
    /// <summary>
    /// Returns array of ErrorBarDirection values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		X,
    		Y,
    	};
    }

    public static final int length = 2;
}

