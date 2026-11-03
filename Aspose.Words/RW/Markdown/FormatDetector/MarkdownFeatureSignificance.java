// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/04/2019 by Denis Panov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// The significance of the markdown feature. 
/// </summary>
/*enum*/ final class MarkdownFeatureSignificance
{
    private MarkdownFeatureSignificance(){}
    
    public static final int LOW = 1;
    public static final int MEDIUM = 2;
    public static final int BIG = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MarkdownFeatureSignificance value.
    /// </summary>
    public static String getName(/*MarkdownFeatureSignificance*/int markdownFeatureSignificance)
    {
    	switch (markdownFeatureSignificance)
    	{
    		case LOW: return "LOW";
    		case MEDIUM: return "MEDIUM";
    		case BIG: return "BIG";
    		default: return "Unknown MarkdownFeatureSignificance value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MarkdownFeatureSignificance value.
    /// </summary>
    public static String toString(/*MarkdownFeatureSignificance*/int markdownFeatureSignificance)
    {
    	switch (markdownFeatureSignificance)
    	{
    		case LOW: return "Low";
    		case MEDIUM: return "Medium";
    		case BIG: return "Big";
    		default: return "Unknown MarkdownFeatureSignificance value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MarkdownFeatureSignificance name.
    /// </summary>
    public static /*MarkdownFeatureSignificance*/int fromName(String markdownFeatureSignificanceName)
    {
    	if ("LOW".equals(markdownFeatureSignificanceName)) return LOW;
    	if ("MEDIUM".equals(markdownFeatureSignificanceName)) return MEDIUM;
    	if ("BIG".equals(markdownFeatureSignificanceName)) return BIG;
    	throw new IllegalArgumentException("Unknown MarkdownFeatureSignificance name.");
    }
    
    /// <summary>
    /// Returns array of MarkdownFeatureSignificance values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		LOW,
    		MEDIUM,
    		BIG,
    	};
    }

    public static final int length = 3;
}

