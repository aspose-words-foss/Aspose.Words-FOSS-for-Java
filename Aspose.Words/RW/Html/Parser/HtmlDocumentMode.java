// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/04/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// The mode in which an HTML document should be processed and displayed.
/// </summary>
/*enum*/ final class HtmlDocumentMode
{
    private HtmlDocumentMode(){}
    
    /// <remarks>
    /// The document should be processed according to the HTML and CSS specifications. Also known as 'no-quirks' mode.
    /// </remarks>
    public static final int STANDARDS = 0;

    /// <summary>
    /// The document should be processed according to the HTML and CSS specifications, except for calculation of height of table
    /// cells containing only images. Also known as 'almost-standards' mode.
    /// </summary>
    public static final int LIMITED_QUIRKS = 1;

    /// <summary>
    /// A number of layout and other quirks should be used during processing the document. Also known as 'compatibility' mode.
    /// </summary>
    public static final int QUIRKS = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer HtmlDocumentMode value.
    /// </summary>
    public static String getName(/*HtmlDocumentMode*/int htmlDocumentMode)
    {
    	switch (htmlDocumentMode)
    	{
    		case STANDARDS: return "STANDARDS";
    		case LIMITED_QUIRKS: return "LIMITED_QUIRKS";
    		case QUIRKS: return "QUIRKS";
    		default: return "Unknown HtmlDocumentMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer HtmlDocumentMode value.
    /// </summary>
    public static String toString(/*HtmlDocumentMode*/int htmlDocumentMode)
    {
    	switch (htmlDocumentMode)
    	{
    		case STANDARDS: return "Standards";
    		case LIMITED_QUIRKS: return "LimitedQuirks";
    		case QUIRKS: return "Quirks";
    		default: return "Unknown HtmlDocumentMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by HtmlDocumentMode name.
    /// </summary>
    public static /*HtmlDocumentMode*/int fromName(String htmlDocumentModeName)
    {
    	if ("STANDARDS".equals(htmlDocumentModeName)) return STANDARDS;
    	if ("LIMITED_QUIRKS".equals(htmlDocumentModeName)) return LIMITED_QUIRKS;
    	if ("QUIRKS".equals(htmlDocumentModeName)) return QUIRKS;
    	throw new IllegalArgumentException("Unknown HtmlDocumentMode name.");
    }
    
    /// <summary>
    /// Returns array of HtmlDocumentMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		STANDARDS,
    		LIMITED_QUIRKS,
    		QUIRKS,
    	};
    }

    public static final int length = 3;
}

