// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/10/2016 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Indicates the version of the HTML standard and DOCTYPE declaration which are used when saving a document to
/// HTML-based formats.
/// </summary>
/*enum*/ final class WebStandard
{
    private WebStandard(){}
    
    /// <summary>
    /// Saves the document in compliance with the XHTML 1.0 Transitional standard. 
    /// Omits DOCTYPE declaration, that triggers the quirks mode when rendering the output HTML document
    /// in modern browsers.
    /// </summary>
    public static final int XHTML_TRANSITIONAL_QUIRKS = 0;

    /// <summary>
    /// Saves the document in compliance with the XHTML 1.0 Transitional standard.
    /// Writes the corresponding DOCTYPE declaration.
    /// </summary>
    public static final int XHTML_TRANSITIONAL = 1;

    /// <summary>
    /// Saves the document in compliance with the HTML 5 standard.
    /// </summary>
    public static final int HTML_5 = 2;

    /// <summary>
    /// Saves the document in compliance with the Epub standard.
    /// </summary>
    public static final int EPUB = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer WebStandard value.
    /// </summary>
    public static String getName(/*WebStandard*/int webStandard)
    {
    	switch (webStandard)
    	{
    		case XHTML_TRANSITIONAL_QUIRKS: return "XHTML_TRANSITIONAL_QUIRKS";
    		case XHTML_TRANSITIONAL: return "XHTML_TRANSITIONAL";
    		case HTML_5: return "HTML_5";
    		case EPUB: return "EPUB";
    		default: return "Unknown WebStandard value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer WebStandard value.
    /// </summary>
    public static String toString(/*WebStandard*/int webStandard)
    {
    	switch (webStandard)
    	{
    		case XHTML_TRANSITIONAL_QUIRKS: return "XhtmlTransitionalQuirks";
    		case XHTML_TRANSITIONAL: return "XhtmlTransitional";
    		case HTML_5: return "Html5";
    		case EPUB: return "Epub";
    		default: return "Unknown WebStandard value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by WebStandard name.
    /// </summary>
    public static /*WebStandard*/int fromName(String webStandardName)
    {
    	if ("XHTML_TRANSITIONAL_QUIRKS".equals(webStandardName)) return XHTML_TRANSITIONAL_QUIRKS;
    	if ("XHTML_TRANSITIONAL".equals(webStandardName)) return XHTML_TRANSITIONAL;
    	if ("HTML_5".equals(webStandardName)) return HTML_5;
    	if ("EPUB".equals(webStandardName)) return EPUB;
    	throw new IllegalArgumentException("Unknown WebStandard name.");
    }
    
    /// <summary>
    /// Returns array of WebStandard values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		XHTML_TRANSITIONAL_QUIRKS,
    		XHTML_TRANSITIONAL,
    		HTML_5,
    		EPUB,
    	};
    }

    public static final int length = 4;
}

