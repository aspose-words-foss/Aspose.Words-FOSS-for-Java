// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/04/2025 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Allows to specify the export mode of hard line breaks in Markdown.
/// </summary>
/// <dev>
/// A line ending (not in a code span or HTML tag) that is preceded by two or more spaces
/// and does not occur at the end of a block is parsed as a hard line break (rendered in
/// HTML as a <br /> tag). For a more visible alternative, a backslash before the line
/// ending may be used instead of two or more spaces.
/// </dev>
/*enum*/ final class MarkdownLineBreakExportMode
{
    private MarkdownLineBreakExportMode(){}
    
    /// <summary>
    /// Hard line break is represented by backslash character.
    /// </summary>
    public static final int BACKSLASH = 0x0000;

    /// <summary>
    /// Hard line break is represented by two spaces.
    /// </summary>
    public static final int SPACES = 0x0001;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MarkdownLineBreakExportMode value.
    /// </summary>
    public static String getName(/*MarkdownLineBreakExportMode*/int markdownLineBreakExportMode)
    {
    	switch (markdownLineBreakExportMode)
    	{
    		case BACKSLASH: return "BACKSLASH";
    		case SPACES: return "SPACES";
    		default: return "Unknown MarkdownLineBreakExportMode value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer MarkdownLineBreakExportMode value.
    /// </summary>
    public static Set<String> getNames(/*MarkdownLineBreakExportMode*/int markdownLineBreakExportMode)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((BACKSLASH & markdownLineBreakExportMode) == BACKSLASH) names.add("BACKSLASH");
    	if ((SPACES & markdownLineBreakExportMode) == SPACES) names.add("SPACES");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MarkdownLineBreakExportMode value.
    /// </summary>
    public static String toString(/*MarkdownLineBreakExportMode*/int markdownLineBreakExportMode)
    {
    	switch (markdownLineBreakExportMode)
    	{
    		case BACKSLASH: return "Backslash";
    		case SPACES: return "Spaces";
    		default: return toStringSet(markdownLineBreakExportMode);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MarkdownLineBreakExportMode value.
    /// </summary>
    public static String toStringSet(int attr)
    {
    	String result = "";
    	String separator = "";
    	for (int value : getValues())
    		if (value != 0 && (value & attr) == value)
    		{
    			result += separator + toString(value);
    			separator = ", ";
    		}
    	return result;
    }
    
    /// <summary>
    /// Returns integer representation by MarkdownLineBreakExportMode name.
    /// </summary>
    public static /*MarkdownLineBreakExportMode*/int fromName(String markdownLineBreakExportModeName)
    {
    	if ("BACKSLASH".equals(markdownLineBreakExportModeName)) return BACKSLASH;
    	if ("SPACES".equals(markdownLineBreakExportModeName)) return SPACES;
    	throw new IllegalArgumentException("Unknown MarkdownLineBreakExportMode name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by MarkdownLineBreakExportMode names.
    /// </summary>
    public static /*MarkdownLineBreakExportMode*/int fromNames(Set<String> markdownLineBreakExportModeNames)
    {
    	/*MarkdownLineBreakExportMode*/int result = 0;
    	for (String name : markdownLineBreakExportModeNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of MarkdownLineBreakExportMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BACKSLASH,
    		SPACES,
    	};
    }

    public static final int length = 2;
}

