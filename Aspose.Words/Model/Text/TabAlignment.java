// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies the alignment/type of a tab stop.
/// </summary>
public /*enum*/ final class TabAlignment
{
    private TabAlignment(){}
    
    /// <summary>
    /// Left-aligns the text after the tab stop.
    /// </summary>
    public static final int LEFT = 0;
    /// <summary>
    /// Centers the text around the tab stop.
    /// </summary>
    public static final int CENTER = 1;
    /// <summary>
    /// Right-aligns the text at the tab stop.
    /// </summary>
    public static final int RIGHT = 2;
    /// <summary>
    /// Aligns the text at the decimal dot.
    /// </summary>
    public static final int DECIMAL = 3;
    /// <summary>
    /// Draws a vertical bar at the tab stop position.
    /// </summary>
    public static final int BAR = 4;
    /// <summary>
    /// The tab is a delimiter between the number/bullet and text in a list item.
    /// </summary>
    public static final int LIST = 6;
    /// <summary>
    /// Clears any tab stop in this position.
    /// </summary>
    public static final int CLEAR = 7;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TabAlignment value.
    /// </summary>
    public static String getName(/*TabAlignment*/int tabAlignment)
    {
    	switch (tabAlignment)
    	{
    		case LEFT: return "LEFT";
    		case CENTER: return "CENTER";
    		case RIGHT: return "RIGHT";
    		case DECIMAL: return "DECIMAL";
    		case BAR: return "BAR";
    		case LIST: return "LIST";
    		case CLEAR: return "CLEAR";
    		default: return "Unknown TabAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TabAlignment value.
    /// </summary>
    public static String toString(/*TabAlignment*/int tabAlignment)
    {
    	switch (tabAlignment)
    	{
    		case LEFT: return "Left";
    		case CENTER: return "Center";
    		case RIGHT: return "Right";
    		case DECIMAL: return "Decimal";
    		case BAR: return "Bar";
    		case LIST: return "List";
    		case CLEAR: return "Clear";
    		default: return "Unknown TabAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TabAlignment name.
    /// </summary>
    public static /*TabAlignment*/int fromName(String tabAlignmentName)
    {
    	if ("LEFT".equals(tabAlignmentName)) return LEFT;
    	if ("CENTER".equals(tabAlignmentName)) return CENTER;
    	if ("RIGHT".equals(tabAlignmentName)) return RIGHT;
    	if ("DECIMAL".equals(tabAlignmentName)) return DECIMAL;
    	if ("BAR".equals(tabAlignmentName)) return BAR;
    	if ("LIST".equals(tabAlignmentName)) return LIST;
    	if ("CLEAR".equals(tabAlignmentName)) return CLEAR;
    	throw new IllegalArgumentException("Unknown TabAlignment name.");
    }
    
    /// <summary>
    /// Returns array of TabAlignment values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		LEFT,
    		CENTER,
    		RIGHT,
    		DECIMAL,
    		BAR,
    		LIST,
    		CLEAR,
    	};
    }

    public static final int length = 7;
}

