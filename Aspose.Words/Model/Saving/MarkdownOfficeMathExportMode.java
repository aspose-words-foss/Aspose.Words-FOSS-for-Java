// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/12/2024 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how Aspose.Words exports OfficeMath to Markdown.
/// </summary>
public /*enum*/ final class MarkdownOfficeMathExportMode
{
    private MarkdownOfficeMathExportMode(){}
    
    /// <summary>
    /// Export OfficeMath as plain text.
    /// </summary>
    public static final int TEXT = 0;

    /// <summary>
    /// Export OfficeMath as image.
    /// </summary>
    public static final int IMAGE = 1;

    /// <summary>
    /// Export OfficeMath as MathML.
    /// </summary>
    public static final int MATH_ML = 2;

    /// <summary>
    /// Export OfficeMath as LaTeX.
    /// </summary>
    public static final int LATEX = 3;

    /// <summary>
    /// Export OfficeMath as LaTeX that is compatible with MarkItDown.
    /// </summary>
    /// <remarks>Please see https://github.com/microsoft/markitdown for details on MarkItDown.</remarks>
    public static final int MARK_IT_DOWN = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MarkdownOfficeMathExportMode value.
    /// </summary>
    public static String getName(/*MarkdownOfficeMathExportMode*/int markdownOfficeMathExportMode)
    {
    	switch (markdownOfficeMathExportMode)
    	{
    		case TEXT: return "TEXT";
    		case IMAGE: return "IMAGE";
    		case MATH_ML: return "MATH_ML";
    		case LATEX: return "LATEX";
    		case MARK_IT_DOWN: return "MARK_IT_DOWN";
    		default: return "Unknown MarkdownOfficeMathExportMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MarkdownOfficeMathExportMode value.
    /// </summary>
    public static String toString(/*MarkdownOfficeMathExportMode*/int markdownOfficeMathExportMode)
    {
    	switch (markdownOfficeMathExportMode)
    	{
    		case TEXT: return "Text";
    		case IMAGE: return "Image";
    		case MATH_ML: return "MathML";
    		case LATEX: return "Latex";
    		case MARK_IT_DOWN: return "MarkItDown";
    		default: return "Unknown MarkdownOfficeMathExportMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MarkdownOfficeMathExportMode name.
    /// </summary>
    public static /*MarkdownOfficeMathExportMode*/int fromName(String markdownOfficeMathExportModeName)
    {
    	if ("TEXT".equals(markdownOfficeMathExportModeName)) return TEXT;
    	if ("IMAGE".equals(markdownOfficeMathExportModeName)) return IMAGE;
    	if ("MATH_ML".equals(markdownOfficeMathExportModeName)) return MATH_ML;
    	if ("LATEX".equals(markdownOfficeMathExportModeName)) return LATEX;
    	if ("MARK_IT_DOWN".equals(markdownOfficeMathExportModeName)) return MARK_IT_DOWN;
    	throw new IllegalArgumentException("Unknown MarkdownOfficeMathExportMode name.");
    }
    
    /// <summary>
    /// Returns array of MarkdownOfficeMathExportMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TEXT,
    		IMAGE,
    		MATH_ML,
    		LATEX,
    		MARK_IT_DOWN,
    	};
    }

    public static final int length = 5;
}

