// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/03/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Allows to specify the version of HTML output of any web page 
/// produced by this document. 
/// </summary>
/*enum*/ final class WebTarget
{
    private WebTarget(){}
    
    /// <summary>
    /// If document does not contain any definition, this one is default.
    /// </summary>
    public static final int NONE = 0;
    
    /// <summary>
    /// W3C XHTML 1.0 + CSS 1
    /// </summary>
    public static final int XHTML_PLUS_CSS_1 = 1;
    
    /// <summary>
    /// W3C HTML 4.01 + CSS 1
    /// </summary>
    public static final int HTML_4_PLUS_CSS_1 = 2;
    
    /// <summary>
    /// W3C XHTML 1.0 + CSS 2
    /// </summary>
    public static final int XHTML_PLUS_CSS_2 = 3;
    
    /// <summary>
    /// W3C HTML 4.01 + CSS 2
    /// </summary>
    public static final int HTML_4_PLUS_CSS_2 = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer WebTarget value.
    /// </summary>
    public static String getName(/*WebTarget*/int webTarget)
    {
    	switch (webTarget)
    	{
    		case NONE: return "NONE";
    		case XHTML_PLUS_CSS_1: return "XHTML_PLUS_CSS_1";
    		case HTML_4_PLUS_CSS_1: return "HTML_4_PLUS_CSS_1";
    		case XHTML_PLUS_CSS_2: return "XHTML_PLUS_CSS_2";
    		case HTML_4_PLUS_CSS_2: return "HTML_4_PLUS_CSS_2";
    		default: return "Unknown WebTarget value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer WebTarget value.
    /// </summary>
    public static String toString(/*WebTarget*/int webTarget)
    {
    	switch (webTarget)
    	{
    		case NONE: return "None";
    		case XHTML_PLUS_CSS_1: return "XhtmlPlusCss1";
    		case HTML_4_PLUS_CSS_1: return "Html4PlusCss1";
    		case XHTML_PLUS_CSS_2: return "XhtmlPlusCss2";
    		case HTML_4_PLUS_CSS_2: return "Html4PlusCss2";
    		default: return "Unknown WebTarget value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by WebTarget name.
    /// </summary>
    public static /*WebTarget*/int fromName(String webTargetName)
    {
    	if ("NONE".equals(webTargetName)) return NONE;
    	if ("XHTML_PLUS_CSS_1".equals(webTargetName)) return XHTML_PLUS_CSS_1;
    	if ("HTML_4_PLUS_CSS_1".equals(webTargetName)) return HTML_4_PLUS_CSS_1;
    	if ("XHTML_PLUS_CSS_2".equals(webTargetName)) return XHTML_PLUS_CSS_2;
    	if ("HTML_4_PLUS_CSS_2".equals(webTargetName)) return HTML_4_PLUS_CSS_2;
    	throw new IllegalArgumentException("Unknown WebTarget name.");
    }
    
    /// <summary>
    /// Returns array of WebTarget values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		XHTML_PLUS_CSS_1,
    		HTML_4_PLUS_CSS_1,
    		XHTML_PLUS_CSS_2,
    		HTML_4_PLUS_CSS_2,
    	};
    }

    public static final int length = 5;
}

