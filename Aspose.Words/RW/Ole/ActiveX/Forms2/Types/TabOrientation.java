// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/01/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the position of the tabs of a form, relative to the control.
/// </summary>
/// <dev>
/// [MS-OFORMS] 2.5.85 TabOrientation.
/// </dev>
/*enum*/ final class TabOrientation
{
    private TabOrientation(){}
    
    public static final int TOP = 0;
    public static final int BOTTOM = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TabOrientation value.
    /// </summary>
    public static String getName(/*TabOrientation*/int tabOrientation)
    {
    	switch (tabOrientation)
    	{
    		case TOP: return "TOP";
    		case BOTTOM: return "BOTTOM";
    		case LEFT: return "LEFT";
    		case RIGHT: return "RIGHT";
    		default: return "Unknown TabOrientation value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TabOrientation value.
    /// </summary>
    public static String toString(/*TabOrientation*/int tabOrientation)
    {
    	switch (tabOrientation)
    	{
    		case TOP: return "Top";
    		case BOTTOM: return "Bottom";
    		case LEFT: return "Left";
    		case RIGHT: return "Right";
    		default: return "Unknown TabOrientation value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TabOrientation name.
    /// </summary>
    public static /*TabOrientation*/int fromName(String tabOrientationName)
    {
    	if ("TOP".equals(tabOrientationName)) return TOP;
    	if ("BOTTOM".equals(tabOrientationName)) return BOTTOM;
    	if ("LEFT".equals(tabOrientationName)) return LEFT;
    	if ("RIGHT".equals(tabOrientationName)) return RIGHT;
    	throw new IllegalArgumentException("Unknown TabOrientation name.");
    }
    
    /// <summary>
    /// Returns array of TabOrientation values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TOP,
    		BOTTOM,
    		LEFT,
    		RIGHT,
    	};
    }

    public static final int length = 4;
}

