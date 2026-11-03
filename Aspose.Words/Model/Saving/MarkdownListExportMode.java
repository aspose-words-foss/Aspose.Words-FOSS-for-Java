// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/12/2022 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how lists are exported into Markdown.
/// </summary>
public /*enum*/ final class MarkdownListExportMode
{
    private MarkdownListExportMode(){}
    
    /// <summary>
    /// Export list items compatible with Markdown syntax.
    /// </summary>
    public static final int MARKDOWN_SYNTAX = 0;

    /// <summary>
    /// Export list items as plain text.
    /// </summary>
    public static final int PLAIN_TEXT = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MarkdownListExportMode value.
    /// </summary>
    public static String getName(/*MarkdownListExportMode*/int markdownListExportMode)
    {
    	switch (markdownListExportMode)
    	{
    		case MARKDOWN_SYNTAX: return "MARKDOWN_SYNTAX";
    		case PLAIN_TEXT: return "PLAIN_TEXT";
    		default: return "Unknown MarkdownListExportMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MarkdownListExportMode value.
    /// </summary>
    public static String toString(/*MarkdownListExportMode*/int markdownListExportMode)
    {
    	switch (markdownListExportMode)
    	{
    		case MARKDOWN_SYNTAX: return "MarkdownSyntax";
    		case PLAIN_TEXT: return "PlainText";
    		default: return "Unknown MarkdownListExportMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MarkdownListExportMode name.
    /// </summary>
    public static /*MarkdownListExportMode*/int fromName(String markdownListExportModeName)
    {
    	if ("MARKDOWN_SYNTAX".equals(markdownListExportModeName)) return MARKDOWN_SYNTAX;
    	if ("PLAIN_TEXT".equals(markdownListExportModeName)) return PLAIN_TEXT;
    	throw new IllegalArgumentException("Unknown MarkdownListExportMode name.");
    }
    
    /// <summary>
    /// Returns array of MarkdownListExportMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		MARKDOWN_SYNTAX,
    		PLAIN_TEXT,
    	};
    }

    public static final int length = 2;
}

