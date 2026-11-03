// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/02/2017 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Word 97 colors. 
/// Also highlight colors of all Word versions are limited to this set of 16 colors.
/// </summary>
/*enum*/ final class Word97Color
{
    private Word97Color(){}
    
    public static final int AUTO = 0;
    public static final int BLACK = 1;
    public static final int BLUE = 2;
    public static final int CYAN = 3;
    public static final int GREEN = 4;
    public static final int MAGENTA = 5;
    public static final int RED = 6;
    public static final int YELLOW = 7;
    public static final int WHITE = 8;
    public static final int DARK_BLUE = 9;
    public static final int DARK_CYAN = 10;
    public static final int DARK_GREEN = 11;
    public static final int DARK_MAGENTA = 12;
    public static final int DARK_RED = 13;
    public static final int DARK_YELLOW = 14;
    public static final int DARK_GRAY = 15;
    public static final int LIGHT_GRAY = 16;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer Word97Color value.
    /// </summary>
    public static String getName(/*Word97Color*/int word97Color)
    {
    	switch (word97Color)
    	{
    		case AUTO: return "AUTO";
    		case BLACK: return "BLACK";
    		case BLUE: return "BLUE";
    		case CYAN: return "CYAN";
    		case GREEN: return "GREEN";
    		case MAGENTA: return "MAGENTA";
    		case RED: return "RED";
    		case YELLOW: return "YELLOW";
    		case WHITE: return "WHITE";
    		case DARK_BLUE: return "DARK_BLUE";
    		case DARK_CYAN: return "DARK_CYAN";
    		case DARK_GREEN: return "DARK_GREEN";
    		case DARK_MAGENTA: return "DARK_MAGENTA";
    		case DARK_RED: return "DARK_RED";
    		case DARK_YELLOW: return "DARK_YELLOW";
    		case DARK_GRAY: return "DARK_GRAY";
    		case LIGHT_GRAY: return "LIGHT_GRAY";
    		default: return "Unknown Word97Color value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer Word97Color value.
    /// </summary>
    public static String toString(/*Word97Color*/int word97Color)
    {
    	switch (word97Color)
    	{
    		case AUTO: return "Auto";
    		case BLACK: return "Black";
    		case BLUE: return "Blue";
    		case CYAN: return "Cyan";
    		case GREEN: return "Green";
    		case MAGENTA: return "Magenta";
    		case RED: return "Red";
    		case YELLOW: return "Yellow";
    		case WHITE: return "White";
    		case DARK_BLUE: return "DarkBlue";
    		case DARK_CYAN: return "DarkCyan";
    		case DARK_GREEN: return "DarkGreen";
    		case DARK_MAGENTA: return "DarkMagenta";
    		case DARK_RED: return "DarkRed";
    		case DARK_YELLOW: return "DarkYellow";
    		case DARK_GRAY: return "DarkGray";
    		case LIGHT_GRAY: return "LightGray";
    		default: return "Unknown Word97Color value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by Word97Color name.
    /// </summary>
    public static /*Word97Color*/int fromName(String word97ColorName)
    {
    	if ("AUTO".equals(word97ColorName)) return AUTO;
    	if ("BLACK".equals(word97ColorName)) return BLACK;
    	if ("BLUE".equals(word97ColorName)) return BLUE;
    	if ("CYAN".equals(word97ColorName)) return CYAN;
    	if ("GREEN".equals(word97ColorName)) return GREEN;
    	if ("MAGENTA".equals(word97ColorName)) return MAGENTA;
    	if ("RED".equals(word97ColorName)) return RED;
    	if ("YELLOW".equals(word97ColorName)) return YELLOW;
    	if ("WHITE".equals(word97ColorName)) return WHITE;
    	if ("DARK_BLUE".equals(word97ColorName)) return DARK_BLUE;
    	if ("DARK_CYAN".equals(word97ColorName)) return DARK_CYAN;
    	if ("DARK_GREEN".equals(word97ColorName)) return DARK_GREEN;
    	if ("DARK_MAGENTA".equals(word97ColorName)) return DARK_MAGENTA;
    	if ("DARK_RED".equals(word97ColorName)) return DARK_RED;
    	if ("DARK_YELLOW".equals(word97ColorName)) return DARK_YELLOW;
    	if ("DARK_GRAY".equals(word97ColorName)) return DARK_GRAY;
    	if ("LIGHT_GRAY".equals(word97ColorName)) return LIGHT_GRAY;
    	throw new IllegalArgumentException("Unknown Word97Color name.");
    }
    
    /// <summary>
    /// Returns array of Word97Color values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		AUTO,
    		BLACK,
    		BLUE,
    		CYAN,
    		GREEN,
    		MAGENTA,
    		RED,
    		YELLOW,
    		WHITE,
    		DARK_BLUE,
    		DARK_CYAN,
    		DARK_GREEN,
    		DARK_MAGENTA,
    		DARK_RED,
    		DARK_YELLOW,
    		DARK_GRAY,
    		LIGHT_GRAY,
    	};
    }

    public static final int length = 17;
}

