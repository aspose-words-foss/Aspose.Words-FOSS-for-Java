// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/04/2014 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Groups of user agent (default) CSS styles by formatting they affect.
/// </summary>
/*enum*/ final class CssUserAgentFormatting
{
    private CssUserAgentFormatting(){}
    
    /// <summary>
    /// None of user agent CSS styles.
    /// </summary>
    public static final int NONE = 0;

    /// <summary>
    /// User agent CSS styles that affect font formatting.
    /// </summary>
    public static final int FONT = 1;

    /// <summary>
    /// User agent CSS styles that affect paragraph formatting.
    /// </summary>
    public static final int PARAGRAPH = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CssUserAgentFormatting value.
    /// </summary>
    public static String getName(/*CssUserAgentFormatting*/int cssUserAgentFormatting)
    {
    	switch (cssUserAgentFormatting)
    	{
    		case NONE: return "NONE";
    		case FONT: return "FONT";
    		case PARAGRAPH: return "PARAGRAPH";
    		default: return "Unknown CssUserAgentFormatting value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer CssUserAgentFormatting value.
    /// </summary>
    public static Set<String> getNames(/*CssUserAgentFormatting*/int cssUserAgentFormatting)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NONE & cssUserAgentFormatting) == NONE) names.add("NONE");
    	if ((FONT & cssUserAgentFormatting) == FONT) names.add("FONT");
    	if ((PARAGRAPH & cssUserAgentFormatting) == PARAGRAPH) names.add("PARAGRAPH");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CssUserAgentFormatting value.
    /// </summary>
    public static String toString(/*CssUserAgentFormatting*/int cssUserAgentFormatting)
    {
    	switch (cssUserAgentFormatting)
    	{
    		case NONE: return "None";
    		case FONT: return "Font";
    		case PARAGRAPH: return "Paragraph";
    		default: return toStringSet(cssUserAgentFormatting);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CssUserAgentFormatting value.
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
    /// Returns integer representation by CssUserAgentFormatting name.
    /// </summary>
    public static /*CssUserAgentFormatting*/int fromName(String cssUserAgentFormattingName)
    {
    	if ("NONE".equals(cssUserAgentFormattingName)) return NONE;
    	if ("FONT".equals(cssUserAgentFormattingName)) return FONT;
    	if ("PARAGRAPH".equals(cssUserAgentFormattingName)) return PARAGRAPH;
    	throw new IllegalArgumentException("Unknown CssUserAgentFormatting name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by CssUserAgentFormatting names.
    /// </summary>
    public static /*CssUserAgentFormatting*/int fromNames(Set<String> cssUserAgentFormattingNames)
    {
    	/*CssUserAgentFormatting*/int result = 0;
    	for (String name : cssUserAgentFormattingNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of CssUserAgentFormatting values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		FONT,
    		PARAGRAPH,
    	};
    }

    public static final int length = 3;
}

