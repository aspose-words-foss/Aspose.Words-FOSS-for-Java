// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/09/2010 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the scrollbar behavior for a frame.
/// </summary>
/// <remarks>Values of members correspond to [MS-DOC] 2.9.122 IScrollType.</remarks>
/*enum*/ final class FrameScrollType
{
    private FrameScrollType(){}
    
    /// <summary>
    /// A scrollbar appears only if it is needed. 
    /// </summary>
    public static final int AUTO = 0x00;
    
    /// <summary>
    /// A scrollbar always appears on the frame.
    /// </summary>
    public static final int YES = 0x01;
    
    /// <summary>
    /// The frame never has a scrollbar.
    /// </summary>
    public static final int NO = 0x02;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FrameScrollType value.
    /// </summary>
    public static String getName(/*FrameScrollType*/int frameScrollType)
    {
    	switch (frameScrollType)
    	{
    		case AUTO: return "AUTO";
    		case YES: return "YES";
    		case NO: return "NO";
    		default: return "Unknown FrameScrollType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FrameScrollType value.
    /// </summary>
    public static String toString(/*FrameScrollType*/int frameScrollType)
    {
    	switch (frameScrollType)
    	{
    		case AUTO: return "Auto";
    		case YES: return "Yes";
    		case NO: return "No";
    		default: return "Unknown FrameScrollType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FrameScrollType name.
    /// </summary>
    public static /*FrameScrollType*/int fromName(String frameScrollTypeName)
    {
    	if ("AUTO".equals(frameScrollTypeName)) return AUTO;
    	if ("YES".equals(frameScrollTypeName)) return YES;
    	if ("NO".equals(frameScrollTypeName)) return NO;
    	throw new IllegalArgumentException("Unknown FrameScrollType name.");
    }
    
    /// <summary>
    /// Returns array of FrameScrollType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		AUTO,
    		YES,
    		NO,
    	};
    }

    public static final int length = 3;
}

