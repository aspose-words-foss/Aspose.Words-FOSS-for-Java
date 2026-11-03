// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/01/2007 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how CSS (Cascading Style Sheet) styles are exported to HTML.
/// </summary>
/// <seealso cref="HtmlSaveOptions.CssStyleSheetType"/>
public /*enum*/ final class CssStyleSheetType
{
    private CssStyleSheetType(){}
    
    /// <summary>
    /// CSS styles are written inline (as a value of the <b>style</b> attribute on every element).
    /// </summary>
    public static final int INLINE = 0;
    /// <summary>
    /// CSS styles are written separately from the content in a style sheet embedded in the HTML file.
    /// </summary>
    public static final int EMBEDDED = 1;
    /// <summary>
    /// CSS styles are written separately from the content in a style sheet in an external file.
    /// The HTML file links the style sheet. 
    /// </summary>
    public static final int EXTERNAL = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CssStyleSheetType value.
    /// </summary>
    public static String getName(/*CssStyleSheetType*/int cssStyleSheetType)
    {
    	switch (cssStyleSheetType)
    	{
    		case INLINE: return "INLINE";
    		case EMBEDDED: return "EMBEDDED";
    		case EXTERNAL: return "EXTERNAL";
    		default: return "Unknown CssStyleSheetType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CssStyleSheetType value.
    /// </summary>
    public static String toString(/*CssStyleSheetType*/int cssStyleSheetType)
    {
    	switch (cssStyleSheetType)
    	{
    		case INLINE: return "Inline";
    		case EMBEDDED: return "Embedded";
    		case EXTERNAL: return "External";
    		default: return "Unknown CssStyleSheetType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CssStyleSheetType name.
    /// </summary>
    public static /*CssStyleSheetType*/int fromName(String cssStyleSheetTypeName)
    {
    	if ("INLINE".equals(cssStyleSheetTypeName)) return INLINE;
    	if ("EMBEDDED".equals(cssStyleSheetTypeName)) return EMBEDDED;
    	if ("EXTERNAL".equals(cssStyleSheetTypeName)) return EXTERNAL;
    	throw new IllegalArgumentException("Unknown CssStyleSheetType name.");
    }
    
    /// <summary>
    /// Returns array of CssStyleSheetType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		INLINE,
    		EMBEDDED,
    		EXTERNAL,
    	};
    }

    public static final int length = 3;
}

