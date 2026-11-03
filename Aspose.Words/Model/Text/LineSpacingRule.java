// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies line spacing values for a paragraph.
/// </summary>
public /*enum*/ final class LineSpacingRule
{
    private LineSpacingRule(){}
    
    /// <summary>
    /// The line spacing can be greater than or equal to, but never less than,
    /// the value specified in the <see cref="ParagraphFormat.LineSpacing"/> property.
    /// </summary>
    public static final int AT_LEAST = 0;
    /// <summary>
    /// The line spacing never changes from the value specified in the
    /// <see cref="ParagraphFormat.LineSpacing"/> property,
    /// even if a larger font is used within the paragraph.
    /// </summary>
    public static final int EXACTLY = 1;
    /// <summary>
    /// The line spacing is specified in the <see cref="ParagraphFormat.LineSpacing"/>
    /// property as the number of lines. One line equals 12 points.
    /// </summary>
    public static final int MULTIPLE = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer LineSpacingRule value.
    /// </summary>
    public static String getName(/*LineSpacingRule*/int lineSpacingRule)
    {
    	switch (lineSpacingRule)
    	{
    		case AT_LEAST: return "AT_LEAST";
    		case EXACTLY: return "EXACTLY";
    		case MULTIPLE: return "MULTIPLE";
    		default: return "Unknown LineSpacingRule value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer LineSpacingRule value.
    /// </summary>
    public static String toString(/*LineSpacingRule*/int lineSpacingRule)
    {
    	switch (lineSpacingRule)
    	{
    		case AT_LEAST: return "AtLeast";
    		case EXACTLY: return "Exactly";
    		case MULTIPLE: return "Multiple";
    		default: return "Unknown LineSpacingRule value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by LineSpacingRule name.
    /// </summary>
    public static /*LineSpacingRule*/int fromName(String lineSpacingRuleName)
    {
    	if ("AT_LEAST".equals(lineSpacingRuleName)) return AT_LEAST;
    	if ("EXACTLY".equals(lineSpacingRuleName)) return EXACTLY;
    	if ("MULTIPLE".equals(lineSpacingRuleName)) return MULTIPLE;
    	throw new IllegalArgumentException("Unknown LineSpacingRule name.");
    }
    
    /// <summary>
    /// Returns array of LineSpacingRule values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		AT_LEAST,
    		EXACTLY,
    		MULTIPLE,
    	};
    }

    public static final int length = 3;
}

