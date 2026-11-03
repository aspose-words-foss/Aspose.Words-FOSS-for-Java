// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/27/2025 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how Aspose.Words exports OfficeMath to <see cref="SaveFormat.Text"/>.
/// </summary>
///
/// <dev>
/// The enumeration values are preserved to be similar to <see cref="MarkdownOfficeMathExportMode"/>.
/// But just only two enumerations for a while.
/// </dev>
public /*enum*/ final class TxtOfficeMathExportMode
{
    private TxtOfficeMathExportMode(){}
    
    /// <summary>
    /// Export OfficeMath as plain text.
    /// </summary>
    public static final int TEXT = 0;

    /// <summary>
    /// Export OfficeMath as LaTeX.
    /// </summary>
    public static final int LATEX = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TxtOfficeMathExportMode value.
    /// </summary>
    public static String getName(/*TxtOfficeMathExportMode*/int txtOfficeMathExportMode)
    {
    	switch (txtOfficeMathExportMode)
    	{
    		case TEXT: return "TEXT";
    		case LATEX: return "LATEX";
    		default: return "Unknown TxtOfficeMathExportMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TxtOfficeMathExportMode value.
    /// </summary>
    public static String toString(/*TxtOfficeMathExportMode*/int txtOfficeMathExportMode)
    {
    	switch (txtOfficeMathExportMode)
    	{
    		case TEXT: return "Text";
    		case LATEX: return "Latex";
    		default: return "Unknown TxtOfficeMathExportMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TxtOfficeMathExportMode name.
    /// </summary>
    public static /*TxtOfficeMathExportMode*/int fromName(String txtOfficeMathExportModeName)
    {
    	if ("TEXT".equals(txtOfficeMathExportModeName)) return TEXT;
    	if ("LATEX".equals(txtOfficeMathExportModeName)) return LATEX;
    	throw new IllegalArgumentException("Unknown TxtOfficeMathExportMode name.");
    }
    
    /// <summary>
    /// Returns array of TxtOfficeMathExportMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TEXT,
    		LATEX,
    	};
    }

    public static final int length = 2;
}

