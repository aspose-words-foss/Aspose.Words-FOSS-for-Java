// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/07/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies how text wraps inside a shape.
/// </summary>
public /*enum*/ final class TextBoxWrapMode
{
    private TextBoxWrapMode(){}
    
    /// <summary>
    /// Text wraps inside a shape.
    /// </summary>
    public static final int SQUARE = 0;
// In MS Word I've only seen Square and None.
//        ByPoints = 1,
    /// <summary>
    /// Text does not wrap inside a shape.
    /// </summary>
    public static final int NONE = 2;
//        TopBottom = 3,
//        Through = 4

    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TextBoxWrapMode value.
    /// </summary>
    public static String getName(/*TextBoxWrapMode*/int textBoxWrapMode)
    {
    	switch (textBoxWrapMode)
    	{
    		case SQUARE: return "SQUARE";
    		case NONE: return "NONE";
    		default: return "Unknown TextBoxWrapMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TextBoxWrapMode value.
    /// </summary>
    public static String toString(/*TextBoxWrapMode*/int textBoxWrapMode)
    {
    	switch (textBoxWrapMode)
    	{
    		case SQUARE: return "Square";
    		case NONE: return "None";
    		default: return "Unknown TextBoxWrapMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TextBoxWrapMode name.
    /// </summary>
    public static /*TextBoxWrapMode*/int fromName(String textBoxWrapModeName)
    {
    	if ("SQUARE".equals(textBoxWrapModeName)) return SQUARE;
    	if ("NONE".equals(textBoxWrapModeName)) return NONE;
    	throw new IllegalArgumentException("Unknown TextBoxWrapMode name.");
    }
    
    /// <summary>
    /// Returns array of TextBoxWrapMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		SQUARE,
    		NONE,
    	};
    }

    public static final int length = 2;
}

