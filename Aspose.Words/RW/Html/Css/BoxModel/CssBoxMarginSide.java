// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/10/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// The side to which a CSS box margin belongs.
/// </summary>
/*enum*/ final class CssBoxMarginSide
{
    private CssBoxMarginSide(){}
    
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int TOP = 2;
    public static final int BOTTOM = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CssBoxMarginSide value.
    /// </summary>
    public static String getName(/*CssBoxMarginSide*/int cssBoxMarginSide)
    {
    	switch (cssBoxMarginSide)
    	{
    		case LEFT: return "LEFT";
    		case RIGHT: return "RIGHT";
    		case TOP: return "TOP";
    		case BOTTOM: return "BOTTOM";
    		default: return "Unknown CssBoxMarginSide value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CssBoxMarginSide value.
    /// </summary>
    public static String toString(/*CssBoxMarginSide*/int cssBoxMarginSide)
    {
    	switch (cssBoxMarginSide)
    	{
    		case LEFT: return "Left";
    		case RIGHT: return "Right";
    		case TOP: return "Top";
    		case BOTTOM: return "Bottom";
    		default: return "Unknown CssBoxMarginSide value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CssBoxMarginSide name.
    /// </summary>
    public static /*CssBoxMarginSide*/int fromName(String cssBoxMarginSideName)
    {
    	if ("LEFT".equals(cssBoxMarginSideName)) return LEFT;
    	if ("RIGHT".equals(cssBoxMarginSideName)) return RIGHT;
    	if ("TOP".equals(cssBoxMarginSideName)) return TOP;
    	if ("BOTTOM".equals(cssBoxMarginSideName)) return BOTTOM;
    	throw new IllegalArgumentException("Unknown CssBoxMarginSide name.");
    }
    
    /// <summary>
    /// Returns array of CssBoxMarginSide values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		LEFT,
    		RIGHT,
    		TOP,
    		BOTTOM,
    	};
    }

    public static final int length = 4;
}

