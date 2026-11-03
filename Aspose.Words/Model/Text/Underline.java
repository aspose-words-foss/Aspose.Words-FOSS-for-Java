// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Indicates type of the underline applied to a font.
/// </summary>
public /*enum*/ final class Underline
{
    private Underline(){}
    
    /// <summary>
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    /// </summary>
    public static final int SINGLE = 1;
    /// <summary>
    /// </summary>
    public static final int WORDS = 2;
    /// <summary>
    /// </summary>
    public static final int DOUBLE = 3;
    /// <summary>
    /// </summary>
    public static final int DOTTED = 4;
    /// <summary>
    /// </summary>
    public static final int THICK = 6;
    /// <summary>
    /// </summary>
    public static final int DASH = 7;
    /// <summary>
    /// </summary>
    public static final int DASH_LONG = 39;
    /// <summary>
    /// </summary>
    public static final int DOT_DASH = 9;
    /// <summary>
    /// </summary>
    public static final int DOT_DOT_DASH = 10;
    /// <summary>
    /// </summary>
    public static final int WAVY = 11;
    /// <summary>
    /// </summary>
    public static final int DOTTED_HEAVY = 20;
    /// <summary>
    /// </summary>
    public static final int DASH_HEAVY = 23;
    /// <summary>
    /// </summary>
    public static final int DASH_LONG_HEAVY = 55;
    /// <summary>
    /// </summary>
    public static final int DOT_DASH_HEAVY = 25;
    /// <summary>
    /// </summary>
    public static final int DOT_DOT_DASH_HEAVY = 26;
    /// <summary>
    /// </summary>
    public static final int WAVY_HEAVY = 27;
    /// <summary>
    /// </summary>
    public static final int WAVY_DOUBLE = 43;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer Underline value.
    /// </summary>
    public static String getName(/*Underline*/int underline)
    {
    	switch (underline)
    	{
    		case NONE: return "NONE";
    		case SINGLE: return "SINGLE";
    		case WORDS: return "WORDS";
    		case DOUBLE: return "DOUBLE";
    		case DOTTED: return "DOTTED";
    		case THICK: return "THICK";
    		case DASH: return "DASH";
    		case DASH_LONG: return "DASH_LONG";
    		case DOT_DASH: return "DOT_DASH";
    		case DOT_DOT_DASH: return "DOT_DOT_DASH";
    		case WAVY: return "WAVY";
    		case DOTTED_HEAVY: return "DOTTED_HEAVY";
    		case DASH_HEAVY: return "DASH_HEAVY";
    		case DASH_LONG_HEAVY: return "DASH_LONG_HEAVY";
    		case DOT_DASH_HEAVY: return "DOT_DASH_HEAVY";
    		case DOT_DOT_DASH_HEAVY: return "DOT_DOT_DASH_HEAVY";
    		case WAVY_HEAVY: return "WAVY_HEAVY";
    		case WAVY_DOUBLE: return "WAVY_DOUBLE";
    		default: return "Unknown Underline value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer Underline value.
    /// </summary>
    public static String toString(/*Underline*/int underline)
    {
    	switch (underline)
    	{
    		case NONE: return "None";
    		case SINGLE: return "Single";
    		case WORDS: return "Words";
    		case DOUBLE: return "Double";
    		case DOTTED: return "Dotted";
    		case THICK: return "Thick";
    		case DASH: return "Dash";
    		case DASH_LONG: return "DashLong";
    		case DOT_DASH: return "DotDash";
    		case DOT_DOT_DASH: return "DotDotDash";
    		case WAVY: return "Wavy";
    		case DOTTED_HEAVY: return "DottedHeavy";
    		case DASH_HEAVY: return "DashHeavy";
    		case DASH_LONG_HEAVY: return "DashLongHeavy";
    		case DOT_DASH_HEAVY: return "DotDashHeavy";
    		case DOT_DOT_DASH_HEAVY: return "DotDotDashHeavy";
    		case WAVY_HEAVY: return "WavyHeavy";
    		case WAVY_DOUBLE: return "WavyDouble";
    		default: return "Unknown Underline value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by Underline name.
    /// </summary>
    public static /*Underline*/int fromName(String underlineName)
    {
    	if ("NONE".equals(underlineName)) return NONE;
    	if ("SINGLE".equals(underlineName)) return SINGLE;
    	if ("WORDS".equals(underlineName)) return WORDS;
    	if ("DOUBLE".equals(underlineName)) return DOUBLE;
    	if ("DOTTED".equals(underlineName)) return DOTTED;
    	if ("THICK".equals(underlineName)) return THICK;
    	if ("DASH".equals(underlineName)) return DASH;
    	if ("DASH_LONG".equals(underlineName)) return DASH_LONG;
    	if ("DOT_DASH".equals(underlineName)) return DOT_DASH;
    	if ("DOT_DOT_DASH".equals(underlineName)) return DOT_DOT_DASH;
    	if ("WAVY".equals(underlineName)) return WAVY;
    	if ("DOTTED_HEAVY".equals(underlineName)) return DOTTED_HEAVY;
    	if ("DASH_HEAVY".equals(underlineName)) return DASH_HEAVY;
    	if ("DASH_LONG_HEAVY".equals(underlineName)) return DASH_LONG_HEAVY;
    	if ("DOT_DASH_HEAVY".equals(underlineName)) return DOT_DASH_HEAVY;
    	if ("DOT_DOT_DASH_HEAVY".equals(underlineName)) return DOT_DOT_DASH_HEAVY;
    	if ("WAVY_HEAVY".equals(underlineName)) return WAVY_HEAVY;
    	if ("WAVY_DOUBLE".equals(underlineName)) return WAVY_DOUBLE;
    	throw new IllegalArgumentException("Unknown Underline name.");
    }
    
    /// <summary>
    /// Returns array of Underline values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		SINGLE,
    		WORDS,
    		DOUBLE,
    		DOTTED,
    		THICK,
    		DASH,
    		DASH_LONG,
    		DOT_DASH,
    		DOT_DOT_DASH,
    		WAVY,
    		DOTTED_HEAVY,
    		DASH_HEAVY,
    		DASH_LONG_HEAVY,
    		DOT_DASH_HEAVY,
    		DOT_DOT_DASH_HEAVY,
    		WAVY_HEAVY,
    		WAVY_DOUBLE,
    	};
    }

    public static final int length = 18;
}

