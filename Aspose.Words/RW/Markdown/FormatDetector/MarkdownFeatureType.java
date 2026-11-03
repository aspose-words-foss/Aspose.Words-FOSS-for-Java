// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/04/2019 by Denis Panov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Enumerates markdown features to use in markdown format detection.
/// </summary>
/*enum*/ final class MarkdownFeatureType
{
    private MarkdownFeatureType(){}
    
    public static final int FENCED_CODE = 1;
    public static final int EMPHASIS = 2;
    public static final int HEADING = 3;
    public static final int LINK = 4;
    public static final int LIST = 5;
    public static final int HORIZONTAL_RULE = 6;
    public static final int QUOTE = 7;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MarkdownFeatureType value.
    /// </summary>
    public static String getName(/*MarkdownFeatureType*/int markdownFeatureType)
    {
    	switch (markdownFeatureType)
    	{
    		case FENCED_CODE: return "FENCED_CODE";
    		case EMPHASIS: return "EMPHASIS";
    		case HEADING: return "HEADING";
    		case LINK: return "LINK";
    		case LIST: return "LIST";
    		case HORIZONTAL_RULE: return "HORIZONTAL_RULE";
    		case QUOTE: return "QUOTE";
    		default: return "Unknown MarkdownFeatureType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MarkdownFeatureType value.
    /// </summary>
    public static String toString(/*MarkdownFeatureType*/int markdownFeatureType)
    {
    	switch (markdownFeatureType)
    	{
    		case FENCED_CODE: return "FencedCode";
    		case EMPHASIS: return "Emphasis";
    		case HEADING: return "Heading";
    		case LINK: return "Link";
    		case LIST: return "List";
    		case HORIZONTAL_RULE: return "HorizontalRule";
    		case QUOTE: return "Quote";
    		default: return "Unknown MarkdownFeatureType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MarkdownFeatureType name.
    /// </summary>
    public static /*MarkdownFeatureType*/int fromName(String markdownFeatureTypeName)
    {
    	if ("FENCED_CODE".equals(markdownFeatureTypeName)) return FENCED_CODE;
    	if ("EMPHASIS".equals(markdownFeatureTypeName)) return EMPHASIS;
    	if ("HEADING".equals(markdownFeatureTypeName)) return HEADING;
    	if ("LINK".equals(markdownFeatureTypeName)) return LINK;
    	if ("LIST".equals(markdownFeatureTypeName)) return LIST;
    	if ("HORIZONTAL_RULE".equals(markdownFeatureTypeName)) return HORIZONTAL_RULE;
    	if ("QUOTE".equals(markdownFeatureTypeName)) return QUOTE;
    	throw new IllegalArgumentException("Unknown MarkdownFeatureType name.");
    }
    
    /// <summary>
    /// Returns array of MarkdownFeatureType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		FENCED_CODE,
    		EMPHASIS,
    		HEADING,
    		LINK,
    		LIST,
    		HORIZONTAL_RULE,
    		QUOTE,
    	};
    }

    public static final int length = 7;
}

