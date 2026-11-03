// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/04/2025 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how Aspose.Words exports empty paragraphs to Markdown.
/// </summary>
public /*enum*/ final class MarkdownEmptyParagraphExportMode
{
    private MarkdownEmptyParagraphExportMode(){}
    
    /// <summary>
    /// Export as empty lines.
    /// </summary>
    /// <remarks>
    /// Note, empty lines are not meaningful in Markdown and will be lost upon loading.
    /// </remarks>
    public static final int EMPTY_LINE = 0;

    /// <summary>
    /// Export as Markdown HardLineBreak character '\'.
    /// </summary>
    public static final int MARKDOWN_HARD_LINE_BREAK = 1;

    /// <summary>
    /// Don't export empty paragraphs.
    /// </summary>
    public static final int NONE = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MarkdownEmptyParagraphExportMode value.
    /// </summary>
    public static String getName(/*MarkdownEmptyParagraphExportMode*/int markdownEmptyParagraphExportMode)
    {
    	switch (markdownEmptyParagraphExportMode)
    	{
    		case EMPTY_LINE: return "EMPTY_LINE";
    		case MARKDOWN_HARD_LINE_BREAK: return "MARKDOWN_HARD_LINE_BREAK";
    		case NONE: return "NONE";
    		default: return "Unknown MarkdownEmptyParagraphExportMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MarkdownEmptyParagraphExportMode value.
    /// </summary>
    public static String toString(/*MarkdownEmptyParagraphExportMode*/int markdownEmptyParagraphExportMode)
    {
    	switch (markdownEmptyParagraphExportMode)
    	{
    		case EMPTY_LINE: return "EmptyLine";
    		case MARKDOWN_HARD_LINE_BREAK: return "MarkdownHardLineBreak";
    		case NONE: return "None";
    		default: return "Unknown MarkdownEmptyParagraphExportMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MarkdownEmptyParagraphExportMode name.
    /// </summary>
    public static /*MarkdownEmptyParagraphExportMode*/int fromName(String markdownEmptyParagraphExportModeName)
    {
    	if ("EMPTY_LINE".equals(markdownEmptyParagraphExportModeName)) return EMPTY_LINE;
    	if ("MARKDOWN_HARD_LINE_BREAK".equals(markdownEmptyParagraphExportModeName)) return MARKDOWN_HARD_LINE_BREAK;
    	if ("NONE".equals(markdownEmptyParagraphExportModeName)) return NONE;
    	throw new IllegalArgumentException("Unknown MarkdownEmptyParagraphExportMode name.");
    }
    
    /// <summary>
    /// Returns array of MarkdownEmptyParagraphExportMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		EMPTY_LINE,
    		MARKDOWN_HARD_LINE_BREAK,
    		NONE,
    	};
    }

    public static final int length = 3;
}

