// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/12/2018 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the horizontal justification of the text used by the control.
/// </summary>
/// <dev>
/// [MS-OFORMS], 2.5.60 ParagraphAlign.
/// </dev>
/*enum*/ final class ParagraphAlign
{
    private ParagraphAlign(){}
    
    public static final int LEFT = 0x01;
    public static final int RIGHT = 0x02;
    public static final int CENTER = 0x03;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ParagraphAlign value.
    /// </summary>
    public static String getName(/*ParagraphAlign*/int paragraphAlign)
    {
    	switch (paragraphAlign)
    	{
    		case LEFT: return "LEFT";
    		case RIGHT: return "RIGHT";
    		case CENTER: return "CENTER";
    		default: return "Unknown ParagraphAlign value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ParagraphAlign value.
    /// </summary>
    public static String toString(/*ParagraphAlign*/int paragraphAlign)
    {
    	switch (paragraphAlign)
    	{
    		case LEFT: return "Left";
    		case RIGHT: return "Right";
    		case CENTER: return "Center";
    		default: return "Unknown ParagraphAlign value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ParagraphAlign name.
    /// </summary>
    public static /*ParagraphAlign*/int fromName(String paragraphAlignName)
    {
    	if ("LEFT".equals(paragraphAlignName)) return LEFT;
    	if ("RIGHT".equals(paragraphAlignName)) return RIGHT;
    	if ("CENTER".equals(paragraphAlignName)) return CENTER;
    	throw new IllegalArgumentException("Unknown ParagraphAlign name.");
    }
    
    /// <summary>
    /// Returns array of ParagraphAlign values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		LEFT,
    		RIGHT,
    		CENTER,
    	};
    }

    public static final int length = 3;
}

