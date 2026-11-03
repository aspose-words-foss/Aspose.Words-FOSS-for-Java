// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/08/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents the alignment for the specified horizontal rule. 
/// </summary>
public /*enum*/ final class HorizontalRuleAlignment
{
    private HorizontalRuleAlignment(){}
    
    /// <summary>
    /// Aligned to the left.
    /// </summary>
    public static final int LEFT = 0;

    /// <summary>
    /// Aligned to the center.
    /// </summary>
    public static final int CENTER = 1;

    /// <summary>
    /// Aligned to the right.
    /// </summary>
    public static final int RIGHT = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer HorizontalRuleAlignment value.
    /// </summary>
    public static String getName(/*HorizontalRuleAlignment*/int horizontalRuleAlignment)
    {
    	switch (horizontalRuleAlignment)
    	{
    		case LEFT: return "LEFT";
    		case CENTER: return "CENTER";
    		case RIGHT: return "RIGHT";
    		default: return "Unknown HorizontalRuleAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer HorizontalRuleAlignment value.
    /// </summary>
    public static String toString(/*HorizontalRuleAlignment*/int horizontalRuleAlignment)
    {
    	switch (horizontalRuleAlignment)
    	{
    		case LEFT: return "Left";
    		case CENTER: return "Center";
    		case RIGHT: return "Right";
    		default: return "Unknown HorizontalRuleAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by HorizontalRuleAlignment name.
    /// </summary>
    public static /*HorizontalRuleAlignment*/int fromName(String horizontalRuleAlignmentName)
    {
    	if ("LEFT".equals(horizontalRuleAlignmentName)) return LEFT;
    	if ("CENTER".equals(horizontalRuleAlignmentName)) return CENTER;
    	if ("RIGHT".equals(horizontalRuleAlignmentName)) return RIGHT;
    	throw new IllegalArgumentException("Unknown HorizontalRuleAlignment name.");
    }
    
    /// <summary>
    /// Returns array of HorizontalRuleAlignment values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		LEFT,
    		CENTER,
    		RIGHT,
    	};
    }

    public static final int length = 3;
}

