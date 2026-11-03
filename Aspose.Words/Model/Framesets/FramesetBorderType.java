// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/09/2010 by Alexey Morozov
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies border type of entire frameset.
/// </summary>
/// <remarks>
/// Corresponds to "[MS-DOC] 2.9.60 DofrFsnSpbd" fNoBorder and f3DBorder flags.
/// </remarks>
/*enum*/ final class FramesetBorderType
{
    private FramesetBorderType(){}
    
    /// <summary>
    /// Entire frameset has no border.
    /// </summary>
    public static final int NONE = 0;

    /// <summary>
    /// Entire frameset has flat border.
    /// </summary>
    public static final int SIMPLE = 1;

    /// <summary>
    /// Entire frameset has 3D border.
    /// </summary>
    public static final int RAISED = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FramesetBorderType value.
    /// </summary>
    public static String getName(/*FramesetBorderType*/int framesetBorderType)
    {
    	switch (framesetBorderType)
    	{
    		case NONE: return "NONE";
    		case SIMPLE: return "SIMPLE";
    		case RAISED: return "RAISED";
    		default: return "Unknown FramesetBorderType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FramesetBorderType value.
    /// </summary>
    public static String toString(/*FramesetBorderType*/int framesetBorderType)
    {
    	switch (framesetBorderType)
    	{
    		case NONE: return "None";
    		case SIMPLE: return "Simple";
    		case RAISED: return "Raised";
    		default: return "Unknown FramesetBorderType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FramesetBorderType name.
    /// </summary>
    public static /*FramesetBorderType*/int fromName(String framesetBorderTypeName)
    {
    	if ("NONE".equals(framesetBorderTypeName)) return NONE;
    	if ("SIMPLE".equals(framesetBorderTypeName)) return SIMPLE;
    	if ("RAISED".equals(framesetBorderTypeName)) return RAISED;
    	throw new IllegalArgumentException("Unknown FramesetBorderType name.");
    }
    
    /// <summary>
    /// Returns array of FramesetBorderType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		SIMPLE,
    		RAISED,
    	};
    }

    public static final int length = 3;
}

