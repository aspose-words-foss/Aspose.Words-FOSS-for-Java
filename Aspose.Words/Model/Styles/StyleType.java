// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/05/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents type of the style.
/// </summary>
public /*enum*/ final class StyleType
{
    private StyleType(){}
    
    // Integer value of the type is made to match with std.sgc value.
    /// <summary>
    /// The style is a paragraph style.
    /// </summary>
    public static final int PARAGRAPH = 1;
    /// <summary>
    /// The style is a character style.
    /// </summary>
    public static final int CHARACTER = 2;
    /// <summary>
    /// The style is a table style.
    /// </summary>
    public static final int TABLE = 3;
    /// <summary>
    /// The style is a list style.
    /// </summary>
    public static final int LIST = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer StyleType value.
    /// </summary>
    public static String getName(/*StyleType*/int styleType)
    {
    	switch (styleType)
    	{
    		case PARAGRAPH: return "PARAGRAPH";
    		case CHARACTER: return "CHARACTER";
    		case TABLE: return "TABLE";
    		case LIST: return "LIST";
    		default: return "Unknown StyleType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer StyleType value.
    /// </summary>
    public static String toString(/*StyleType*/int styleType)
    {
    	switch (styleType)
    	{
    		case PARAGRAPH: return "Paragraph";
    		case CHARACTER: return "Character";
    		case TABLE: return "Table";
    		case LIST: return "List";
    		default: return "Unknown StyleType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by StyleType name.
    /// </summary>
    public static /*StyleType*/int fromName(String styleTypeName)
    {
    	if ("PARAGRAPH".equals(styleTypeName)) return PARAGRAPH;
    	if ("CHARACTER".equals(styleTypeName)) return CHARACTER;
    	if ("TABLE".equals(styleTypeName)) return TABLE;
    	if ("LIST".equals(styleTypeName)) return LIST;
    	throw new IllegalArgumentException("Unknown StyleType name.");
    }
    
    /// <summary>
    /// Returns array of StyleType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		PARAGRAPH,
    		CHARACTER,
    		TABLE,
    		LIST,
    	};
    }

    public static final int length = 4;
}

