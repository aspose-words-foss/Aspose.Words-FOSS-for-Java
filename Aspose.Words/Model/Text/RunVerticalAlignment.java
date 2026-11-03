// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Defines the possible settings for vertical alignment of a run of text.
/// </summary>
/*enum*/ final class RunVerticalAlignment
{
    private RunVerticalAlignment(){}
    
    public static final int BASELINE = 0;
    public static final int SUPERSCRIPT = 1;
    public static final int SUBSCRIPT = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer RunVerticalAlignment value.
    /// </summary>
    public static String getName(/*RunVerticalAlignment*/int runVerticalAlignment)
    {
    	switch (runVerticalAlignment)
    	{
    		case BASELINE: return "BASELINE";
    		case SUPERSCRIPT: return "SUPERSCRIPT";
    		case SUBSCRIPT: return "SUBSCRIPT";
    		default: return "Unknown RunVerticalAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer RunVerticalAlignment value.
    /// </summary>
    public static String toString(/*RunVerticalAlignment*/int runVerticalAlignment)
    {
    	switch (runVerticalAlignment)
    	{
    		case BASELINE: return "Baseline";
    		case SUPERSCRIPT: return "Superscript";
    		case SUBSCRIPT: return "Subscript";
    		default: return "Unknown RunVerticalAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by RunVerticalAlignment name.
    /// </summary>
    public static /*RunVerticalAlignment*/int fromName(String runVerticalAlignmentName)
    {
    	if ("BASELINE".equals(runVerticalAlignmentName)) return BASELINE;
    	if ("SUPERSCRIPT".equals(runVerticalAlignmentName)) return SUPERSCRIPT;
    	if ("SUBSCRIPT".equals(runVerticalAlignmentName)) return SUBSCRIPT;
    	throw new IllegalArgumentException("Unknown RunVerticalAlignment name.");
    }
    
    /// <summary>
    /// Returns array of RunVerticalAlignment values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BASELINE,
    		SUPERSCRIPT,
    		SUBSCRIPT,
    	};
    }

    public static final int length = 3;
}

