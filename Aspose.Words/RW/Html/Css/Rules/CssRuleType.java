// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/06/2019 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Types of rules returned by our CSS parser.
/// </summary>
/*enum*/ final class CssRuleType
{
    private CssRuleType(){}
    
    /// <summary>
    /// A style rule: selectors plus declarations.
    /// </summary>
    public static final int STYLE = 0;

    /// <summary>
    /// An @import rule.
    /// </summary>
    public static final int IMPORT = 1;

    /// <summary>
    /// A @media rule.
    /// </summary>
    public static final int MEDIA = 2;

    /// <summary>
    /// A @page rule.
    /// </summary>
    public static final int PAGE = 3;

    /// <summary>
    /// A @font-face rule.
    /// </summary>
    public static final int FONT_FACE = 4;

    /// <summary>
    /// A @list rule.
    /// </summary>
    public static final int LIST = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CssRuleType value.
    /// </summary>
    public static String getName(/*CssRuleType*/int cssRuleType)
    {
    	switch (cssRuleType)
    	{
    		case STYLE: return "STYLE";
    		case IMPORT: return "IMPORT";
    		case MEDIA: return "MEDIA";
    		case PAGE: return "PAGE";
    		case FONT_FACE: return "FONT_FACE";
    		case LIST: return "LIST";
    		default: return "Unknown CssRuleType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CssRuleType value.
    /// </summary>
    public static String toString(/*CssRuleType*/int cssRuleType)
    {
    	switch (cssRuleType)
    	{
    		case STYLE: return "Style";
    		case IMPORT: return "Import";
    		case MEDIA: return "Media";
    		case PAGE: return "Page";
    		case FONT_FACE: return "FontFace";
    		case LIST: return "List";
    		default: return "Unknown CssRuleType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CssRuleType name.
    /// </summary>
    public static /*CssRuleType*/int fromName(String cssRuleTypeName)
    {
    	if ("STYLE".equals(cssRuleTypeName)) return STYLE;
    	if ("IMPORT".equals(cssRuleTypeName)) return IMPORT;
    	if ("MEDIA".equals(cssRuleTypeName)) return MEDIA;
    	if ("PAGE".equals(cssRuleTypeName)) return PAGE;
    	if ("FONT_FACE".equals(cssRuleTypeName)) return FONT_FACE;
    	if ("LIST".equals(cssRuleTypeName)) return LIST;
    	throw new IllegalArgumentException("Unknown CssRuleType name.");
    }
    
    /// <summary>
    /// Returns array of CssRuleType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		STYLE,
    		IMPORT,
    		MEDIA,
    		PAGE,
    		FONT_FACE,
    		LIST,
    	};
    }

    public static final int length = 6;
}

