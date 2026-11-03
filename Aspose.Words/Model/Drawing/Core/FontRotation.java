// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/07/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies font rotation in a textbox. Have not seen in MS Word documents.
/// </summary>
/// <dev>
/// Do not renumber. The values are from the RTF spec.
/// </dev>
/*enum*/ final class FontRotation
{
    private FontRotation(){}
    
    /// <summary>
    /// 0 degrees rotation.
    /// </summary>
    public static final int RIGHT = 0;
    /// <summary>
    /// 90 degrees rotation.
    /// </summary>
    public static final int DOWN = 1;
    /// <summary>
    /// 180 degrees rotation.
    /// </summary>
    public static final int LEFT = 2;
    /// <summary>
    /// 270 degrees rotation.
    /// </summary>
    public static final int UP = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FontRotation value.
    /// </summary>
    public static String getName(/*FontRotation*/int fontRotation)
    {
    	switch (fontRotation)
    	{
    		case RIGHT: return "RIGHT";
    		case DOWN: return "DOWN";
    		case LEFT: return "LEFT";
    		case UP: return "UP";
    		default: return "Unknown FontRotation value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FontRotation value.
    /// </summary>
    public static String toString(/*FontRotation*/int fontRotation)
    {
    	switch (fontRotation)
    	{
    		case RIGHT: return "Right";
    		case DOWN: return "Down";
    		case LEFT: return "Left";
    		case UP: return "Up";
    		default: return "Unknown FontRotation value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FontRotation name.
    /// </summary>
    public static /*FontRotation*/int fromName(String fontRotationName)
    {
    	if ("RIGHT".equals(fontRotationName)) return RIGHT;
    	if ("DOWN".equals(fontRotationName)) return DOWN;
    	if ("LEFT".equals(fontRotationName)) return LEFT;
    	if ("UP".equals(fontRotationName)) return UP;
    	throw new IllegalArgumentException("Unknown FontRotation name.");
    }
    
    /// <summary>
    /// Returns array of FontRotation values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		RIGHT,
    		DOWN,
    		LEFT,
    		UP,
    	};
    }

    public static final int length = 4;
}

