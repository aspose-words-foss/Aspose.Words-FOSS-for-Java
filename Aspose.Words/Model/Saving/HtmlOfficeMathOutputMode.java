// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/05/2016 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how Aspose.Words exports OfficeMath to HTML, MHTML and EPUB.
/// </summary>
public /*enum*/ final class HtmlOfficeMathOutputMode
{
    private HtmlOfficeMathOutputMode(){}
    
    /// <summary>
    /// OfficeMath is converted to HTML as image specified by &lt;img&gt; tag.
    /// </summary>
    public static final int IMAGE = 0;
    /// <summary>
    /// OfficeMath is converted to HTML using MathML.
    /// </summary>
    public static final int MATH_ML = 1;
    /// <summary>
    /// OfficeMath is converted to HTML as sequence of runs specified by &lt;span&gt; tags.
    /// </summary>
    public static final int TEXT = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer HtmlOfficeMathOutputMode value.
    /// </summary>
    public static String getName(/*HtmlOfficeMathOutputMode*/int htmlOfficeMathOutputMode)
    {
    	switch (htmlOfficeMathOutputMode)
    	{
    		case IMAGE: return "IMAGE";
    		case MATH_ML: return "MATH_ML";
    		case TEXT: return "TEXT";
    		default: return "Unknown HtmlOfficeMathOutputMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer HtmlOfficeMathOutputMode value.
    /// </summary>
    public static String toString(/*HtmlOfficeMathOutputMode*/int htmlOfficeMathOutputMode)
    {
    	switch (htmlOfficeMathOutputMode)
    	{
    		case IMAGE: return "Image";
    		case MATH_ML: return "MathML";
    		case TEXT: return "Text";
    		default: return "Unknown HtmlOfficeMathOutputMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by HtmlOfficeMathOutputMode name.
    /// </summary>
    public static /*HtmlOfficeMathOutputMode*/int fromName(String htmlOfficeMathOutputModeName)
    {
    	if ("IMAGE".equals(htmlOfficeMathOutputModeName)) return IMAGE;
    	if ("MATH_ML".equals(htmlOfficeMathOutputModeName)) return MATH_ML;
    	if ("TEXT".equals(htmlOfficeMathOutputModeName)) return TEXT;
    	throw new IllegalArgumentException("Unknown HtmlOfficeMathOutputMode name.");
    }
    
    /// <summary>
    /// Returns array of HtmlOfficeMathOutputMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		IMAGE,
    		MATH_ML,
    		TEXT,
    	};
    }

    public static final int length = 3;
}

