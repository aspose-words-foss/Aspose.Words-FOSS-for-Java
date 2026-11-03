// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/06/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Enumerates all possible Markdown emphasis writer states.
/// </summary>
/*enum*/ final class MarkdownEmphasisWriterState
{
    private MarkdownEmphasisWriterState(){}
    
    /// <summary>
    /// The state is not changing.
    /// </summary>
    public static final int NONE = 0;

    /// <summary>
    /// The state is changing to 'opened'.
    /// </summary>
    public static final int OPENING = 1;

    /// <summary>
    /// The state is changing to 'closing'.
    /// </summary>
    public static final int CLOSING = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MarkdownEmphasisWriterState value.
    /// </summary>
    public static String getName(/*MarkdownEmphasisWriterState*/int markdownEmphasisWriterState)
    {
    	switch (markdownEmphasisWriterState)
    	{
    		case NONE: return "NONE";
    		case OPENING: return "OPENING";
    		case CLOSING: return "CLOSING";
    		default: return "Unknown MarkdownEmphasisWriterState value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MarkdownEmphasisWriterState value.
    /// </summary>
    public static String toString(/*MarkdownEmphasisWriterState*/int markdownEmphasisWriterState)
    {
    	switch (markdownEmphasisWriterState)
    	{
    		case NONE: return "None";
    		case OPENING: return "Opening";
    		case CLOSING: return "Closing";
    		default: return "Unknown MarkdownEmphasisWriterState value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MarkdownEmphasisWriterState name.
    /// </summary>
    public static /*MarkdownEmphasisWriterState*/int fromName(String markdownEmphasisWriterStateName)
    {
    	if ("NONE".equals(markdownEmphasisWriterStateName)) return NONE;
    	if ("OPENING".equals(markdownEmphasisWriterStateName)) return OPENING;
    	if ("CLOSING".equals(markdownEmphasisWriterStateName)) return CLOSING;
    	throw new IllegalArgumentException("Unknown MarkdownEmphasisWriterState name.");
    }
    
    /// <summary>
    /// Returns array of MarkdownEmphasisWriterState values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		OPENING,
    		CLOSING,
    	};
    }

    public static final int length = 3;
}

