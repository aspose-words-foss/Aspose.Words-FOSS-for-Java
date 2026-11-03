// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/03/2015 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how a paragraph break char '\r' should be replaced.
/// </summary>
/*enum*/ final class ParagraphBreakCharReplacement
{
    private ParagraphBreakCharReplacement(){}
    
    public static final int PARAGRAPH = 0;
    public static final int PARAGRAPH_BREAK_CHAR = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ParagraphBreakCharReplacement value.
    /// </summary>
    public static String getName(/*ParagraphBreakCharReplacement*/int paragraphBreakCharReplacement)
    {
    	switch (paragraphBreakCharReplacement)
    	{
    		case PARAGRAPH: return "PARAGRAPH";
    		case PARAGRAPH_BREAK_CHAR: return "PARAGRAPH_BREAK_CHAR";
    		default: return "Unknown ParagraphBreakCharReplacement value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ParagraphBreakCharReplacement value.
    /// </summary>
    public static String toString(/*ParagraphBreakCharReplacement*/int paragraphBreakCharReplacement)
    {
    	switch (paragraphBreakCharReplacement)
    	{
    		case PARAGRAPH: return "Paragraph";
    		case PARAGRAPH_BREAK_CHAR: return "ParagraphBreakChar";
    		default: return "Unknown ParagraphBreakCharReplacement value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ParagraphBreakCharReplacement name.
    /// </summary>
    public static /*ParagraphBreakCharReplacement*/int fromName(String paragraphBreakCharReplacementName)
    {
    	if ("PARAGRAPH".equals(paragraphBreakCharReplacementName)) return PARAGRAPH;
    	if ("PARAGRAPH_BREAK_CHAR".equals(paragraphBreakCharReplacementName)) return PARAGRAPH_BREAK_CHAR;
    	throw new IllegalArgumentException("Unknown ParagraphBreakCharReplacement name.");
    }
    
    /// <summary>
    /// Returns array of ParagraphBreakCharReplacement values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		PARAGRAPH,
    		PARAGRAPH_BREAK_CHAR,
    	};
    }

    public static final int length = 2;
}
