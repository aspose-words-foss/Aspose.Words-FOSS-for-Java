// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/09/2010 by Alexey Morozov
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the units of a frame divider position.
/// </summary>
/// <remarks>
/// Values correspond to [MS-DOC] 2.9.99 FssdUnits.
/// </remarks>
/*enum*/ final class FrameDividerPositionType
{
    private FrameDividerPositionType(){}
    
    /// <summary>
    /// No units are specified.
    /// </summary>
    public static final int NONE = 0x00;
    
    /// <summary>
    /// The value is in pixels.
    /// </summary>
    public static final int PIXEL = 0x01;
    
    /// <summary>
    /// The value is a percentage of the size of the parent frame.
    /// </summary>
    public static final int PERCENTAGE = 0x02;
    
    /// <summary>
    /// The value is a relative position.
    /// </summary>
    public static final int RELATIVE = 0x03;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FrameDividerPositionType value.
    /// </summary>
    public static String getName(/*FrameDividerPositionType*/int frameDividerPositionType)
    {
    	switch (frameDividerPositionType)
    	{
    		case NONE: return "NONE";
    		case PIXEL: return "PIXEL";
    		case PERCENTAGE: return "PERCENTAGE";
    		case RELATIVE: return "RELATIVE";
    		default: return "Unknown FrameDividerPositionType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FrameDividerPositionType value.
    /// </summary>
    public static String toString(/*FrameDividerPositionType*/int frameDividerPositionType)
    {
    	switch (frameDividerPositionType)
    	{
    		case NONE: return "None";
    		case PIXEL: return "Pixel";
    		case PERCENTAGE: return "Percentage";
    		case RELATIVE: return "Relative";
    		default: return "Unknown FrameDividerPositionType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FrameDividerPositionType name.
    /// </summary>
    public static /*FrameDividerPositionType*/int fromName(String frameDividerPositionTypeName)
    {
    	if ("NONE".equals(frameDividerPositionTypeName)) return NONE;
    	if ("PIXEL".equals(frameDividerPositionTypeName)) return PIXEL;
    	if ("PERCENTAGE".equals(frameDividerPositionTypeName)) return PERCENTAGE;
    	if ("RELATIVE".equals(frameDividerPositionTypeName)) return RELATIVE;
    	throw new IllegalArgumentException("Unknown FrameDividerPositionType name.");
    }
    
    /// <summary>
    /// Returns array of FrameDividerPositionType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		PIXEL,
    		PERCENTAGE,
    		RELATIVE,
    	};
    }

    public static final int length = 4;
}

