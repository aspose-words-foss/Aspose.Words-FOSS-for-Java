// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/09/2010 by Alexey Morozov
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies layout for child frames.
/// </summary>
/// <remarks>Values correspond to [MS-DOC] 2.9.56 DofrFsn tCols type.</remarks>
/*enum*/ final class FrameLayoutType
{
    private FrameLayoutType(){}
    
    /// <summary>
    /// Indicates that frame has no child frames.
    /// </summary>
    public static final int NONE = -1;

    /// <summary>
    /// Child frames should be arranged in rows.
    /// </summary>  
    public static final int VERTICAL = 0x00;
    
    /// <summary>
    /// Child frames should be arranged in columns.
    /// </summary>
    public static final int HORIZONTAL = 0x01;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FrameLayoutType value.
    /// </summary>
    public static String getName(/*FrameLayoutType*/int frameLayoutType)
    {
    	switch (frameLayoutType)
    	{
    		case NONE: return "NONE";
    		case VERTICAL: return "VERTICAL";
    		case HORIZONTAL: return "HORIZONTAL";
    		default: return "Unknown FrameLayoutType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FrameLayoutType value.
    /// </summary>
    public static String toString(/*FrameLayoutType*/int frameLayoutType)
    {
    	switch (frameLayoutType)
    	{
    		case NONE: return "None";
    		case VERTICAL: return "Vertical";
    		case HORIZONTAL: return "Horizontal";
    		default: return "Unknown FrameLayoutType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FrameLayoutType name.
    /// </summary>
    public static /*FrameLayoutType*/int fromName(String frameLayoutTypeName)
    {
    	if ("NONE".equals(frameLayoutTypeName)) return NONE;
    	if ("VERTICAL".equals(frameLayoutTypeName)) return VERTICAL;
    	if ("HORIZONTAL".equals(frameLayoutTypeName)) return HORIZONTAL;
    	throw new IllegalArgumentException("Unknown FrameLayoutType name.");
    }
    
    /// <summary>
    /// Returns array of FrameLayoutType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		VERTICAL,
    		HORIZONTAL,
    	};
    }

    public static final int length = 3;
}

