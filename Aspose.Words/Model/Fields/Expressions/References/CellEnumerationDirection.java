// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/01/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies a direction cell enumeration should follow.
/// </summary>
/*enum*/ final class CellEnumerationDirection
{
    private CellEnumerationDirection(){}
    
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CellEnumerationDirection value.
    /// </summary>
    public static String getName(/*CellEnumerationDirection*/int cellEnumerationDirection)
    {
    	switch (cellEnumerationDirection)
    	{
    		case UP: return "UP";
    		case DOWN: return "DOWN";
    		case LEFT: return "LEFT";
    		case RIGHT: return "RIGHT";
    		default: return "Unknown CellEnumerationDirection value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CellEnumerationDirection value.
    /// </summary>
    public static String toString(/*CellEnumerationDirection*/int cellEnumerationDirection)
    {
    	switch (cellEnumerationDirection)
    	{
    		case UP: return "Up";
    		case DOWN: return "Down";
    		case LEFT: return "Left";
    		case RIGHT: return "Right";
    		default: return "Unknown CellEnumerationDirection value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CellEnumerationDirection name.
    /// </summary>
    public static /*CellEnumerationDirection*/int fromName(String cellEnumerationDirectionName)
    {
    	if ("UP".equals(cellEnumerationDirectionName)) return UP;
    	if ("DOWN".equals(cellEnumerationDirectionName)) return DOWN;
    	if ("LEFT".equals(cellEnumerationDirectionName)) return LEFT;
    	if ("RIGHT".equals(cellEnumerationDirectionName)) return RIGHT;
    	throw new IllegalArgumentException("Unknown CellEnumerationDirection name.");
    }
    
    /// <summary>
    /// Returns array of CellEnumerationDirection values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		UP,
    		DOWN,
    		LEFT,
    		RIGHT,
    	};
    }

    public static final int length = 4;
}

