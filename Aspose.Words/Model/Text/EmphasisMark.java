// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/07/2011 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies possible types of emphasis mark.
/// </summary>
/// <dev>
/// Names borrowed from https://docs.microsoft.com/en-us/dotnet/api/microsoft.office.interop.word.wdemphasismark?view=word-pia
/// </dev>
public /*enum*/ final class EmphasisMark
{
    private EmphasisMark(){}
    
    /// <summary>
    /// No emphasis mark.
    /// </summary>
    public static final int NONE = 0x00;

    /// <summary>
    /// Emphasis mark is a solid black circle displayed above text.
    /// </summary>
    public static final int OVER_SOLID_CIRCLE = 0x01;

    /// <summary>
    /// Emphasis mark is a comma character displayed above text.
    /// </summary>
    public static final int OVER_COMMA = 0x02;

    /// <summary>
    /// Emphasis mark is an empty white circle displayed above text.
    /// </summary>
    public static final int OVER_WHITE_CIRCLE = 0x03;

    /// <summary>
    /// Emphasis mark is a solid black circle displayed below text.
    /// </summary>
    public static final int UNDER_SOLID_CIRCLE = 0x04;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer EmphasisMark value.
    /// </summary>
    public static String getName(/*EmphasisMark*/int emphasisMark)
    {
    	switch (emphasisMark)
    	{
    		case NONE: return "NONE";
    		case OVER_SOLID_CIRCLE: return "OVER_SOLID_CIRCLE";
    		case OVER_COMMA: return "OVER_COMMA";
    		case OVER_WHITE_CIRCLE: return "OVER_WHITE_CIRCLE";
    		case UNDER_SOLID_CIRCLE: return "UNDER_SOLID_CIRCLE";
    		default: return "Unknown EmphasisMark value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer EmphasisMark value.
    /// </summary>
    public static String toString(/*EmphasisMark*/int emphasisMark)
    {
    	switch (emphasisMark)
    	{
    		case NONE: return "None";
    		case OVER_SOLID_CIRCLE: return "OverSolidCircle";
    		case OVER_COMMA: return "OverComma";
    		case OVER_WHITE_CIRCLE: return "OverWhiteCircle";
    		case UNDER_SOLID_CIRCLE: return "UnderSolidCircle";
    		default: return "Unknown EmphasisMark value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by EmphasisMark name.
    /// </summary>
    public static /*EmphasisMark*/int fromName(String emphasisMarkName)
    {
    	if ("NONE".equals(emphasisMarkName)) return NONE;
    	if ("OVER_SOLID_CIRCLE".equals(emphasisMarkName)) return OVER_SOLID_CIRCLE;
    	if ("OVER_COMMA".equals(emphasisMarkName)) return OVER_COMMA;
    	if ("OVER_WHITE_CIRCLE".equals(emphasisMarkName)) return OVER_WHITE_CIRCLE;
    	if ("UNDER_SOLID_CIRCLE".equals(emphasisMarkName)) return UNDER_SOLID_CIRCLE;
    	throw new IllegalArgumentException("Unknown EmphasisMark name.");
    }
    
    /// <summary>
    /// Returns array of EmphasisMark values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		OVER_SOLID_CIRCLE,
    		OVER_COMMA,
    		OVER_WHITE_CIRCLE,
    		UNDER_SOLID_CIRCLE,
    	};
    }

    public static final int length = 5;
}

