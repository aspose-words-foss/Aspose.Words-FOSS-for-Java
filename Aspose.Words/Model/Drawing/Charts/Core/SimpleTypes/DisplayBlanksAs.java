// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This enum specifies the possible ways to display blanks.
/// Corresponds ST_DispBlanksAs simple type (5.7.3.10).
/// </summary>
/*enum*/ final class DisplayBlanksAs
{
    private DisplayBlanksAs(){}
    
    /// <summary>
    /// Specifies that blank values shall be left as a gap.
    /// </summary>
    public static final int GAP = 0;
    /// <summary>
    /// Specifies that blank values shall be spanned with a line.
    /// </summary>
    public static final int SPAN = 1;
    /// <summary>
    /// Specifies that blank values shall be treated as zero.
    /// </summary>
    public static final int ZERO = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DisplayBlanksAs value.
    /// </summary>
    public static String getName(/*DisplayBlanksAs*/int displayBlanksAs)
    {
    	switch (displayBlanksAs)
    	{
    		case GAP: return "GAP";
    		case SPAN: return "SPAN";
    		case ZERO: return "ZERO";
    		default: return "Unknown DisplayBlanksAs value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DisplayBlanksAs value.
    /// </summary>
    public static String toString(/*DisplayBlanksAs*/int displayBlanksAs)
    {
    	switch (displayBlanksAs)
    	{
    		case GAP: return "Gap";
    		case SPAN: return "Span";
    		case ZERO: return "Zero";
    		default: return "Unknown DisplayBlanksAs value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DisplayBlanksAs name.
    /// </summary>
    public static /*DisplayBlanksAs*/int fromName(String displayBlanksAsName)
    {
    	if ("GAP".equals(displayBlanksAsName)) return GAP;
    	if ("SPAN".equals(displayBlanksAsName)) return SPAN;
    	if ("ZERO".equals(displayBlanksAsName)) return ZERO;
    	throw new IllegalArgumentException("Unknown DisplayBlanksAs name.");
    }
    
    /// <summary>
    /// Returns array of DisplayBlanksAs values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		GAP,
    		SPAN,
    		ZERO,
    	};
    }

    public static final int length = 3;
}

