// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/09/2024 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the replacement format. 
/// </summary>
public /*enum*/ final class ReplacementFormat
{
    private ReplacementFormat(){}
    
    /// <summary>
    /// Replacement is treated as simple text.
    /// </summary>
    public static final int TEXT = 0;

    /// <summary>
    /// Replacement is interpreted as Markdown text.
    /// </summary>
    public static final int MARKDOWN = 1;

    /// <summary>
    /// Replacement is interpreted as Html formatted string.
    /// </summary>
    public static final int HTML = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ReplacementFormat value.
    /// </summary>
    public static String getName(/*ReplacementFormat*/int replacementFormat)
    {
    	switch (replacementFormat)
    	{
    		case TEXT: return "TEXT";
    		case MARKDOWN: return "MARKDOWN";
    		case HTML: return "HTML";
    		default: return "Unknown ReplacementFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ReplacementFormat value.
    /// </summary>
    public static String toString(/*ReplacementFormat*/int replacementFormat)
    {
    	switch (replacementFormat)
    	{
    		case TEXT: return "Text";
    		case MARKDOWN: return "Markdown";
    		case HTML: return "Html";
    		default: return "Unknown ReplacementFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ReplacementFormat name.
    /// </summary>
    public static /*ReplacementFormat*/int fromName(String replacementFormatName)
    {
    	if ("TEXT".equals(replacementFormatName)) return TEXT;
    	if ("MARKDOWN".equals(replacementFormatName)) return MARKDOWN;
    	if ("HTML".equals(replacementFormatName)) return HTML;
    	throw new IllegalArgumentException("Unknown ReplacementFormat name.");
    }
    
    /// <summary>
    /// Returns array of ReplacementFormat values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TEXT,
    		MARKDOWN,
    		HTML,
    	};
    }

    public static final int length = 3;
}

