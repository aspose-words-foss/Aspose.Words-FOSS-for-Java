// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This attribute can be set on a character that is a line break.
/// </summary>
/*enum*/ final class LineBreakClear
{
    private LineBreakClear(){}
    
    public static final int NONE    = 0;
    public static final int LEFT    = 1;
    public static final int RIGHT   = 2;
    public static final int ALL     = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer LineBreakClear value.
    /// </summary>
    public static String getName(/*LineBreakClear*/int lineBreakClear)
    {
    	switch (lineBreakClear)
    	{
    		case NONE: return "NONE";
    		case LEFT: return "LEFT";
    		case RIGHT: return "RIGHT";
    		case ALL: return "ALL";
    		default: return "Unknown LineBreakClear value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer LineBreakClear value.
    /// </summary>
    public static String toString(/*LineBreakClear*/int lineBreakClear)
    {
    	switch (lineBreakClear)
    	{
    		case NONE: return "None";
    		case LEFT: return "Left";
    		case RIGHT: return "Right";
    		case ALL: return "All";
    		default: return "Unknown LineBreakClear value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by LineBreakClear name.
    /// </summary>
    public static /*LineBreakClear*/int fromName(String lineBreakClearName)
    {
    	if ("NONE".equals(lineBreakClearName)) return NONE;
    	if ("LEFT".equals(lineBreakClearName)) return LEFT;
    	if ("RIGHT".equals(lineBreakClearName)) return RIGHT;
    	if ("ALL".equals(lineBreakClearName)) return ALL;
    	throw new IllegalArgumentException("Unknown LineBreakClear name.");
    }
    
    /// <summary>
    /// Returns array of LineBreakClear values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		LEFT,
    		RIGHT,
    		ALL,
    	};
    }

    public static final int length = 4;
}

