// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/09/2016 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Indicates the version of HTML is used when saving the document to <see cref="SaveFormat.Html"/> and 
/// <see cref="SaveFormat.Mhtml"/> formats.
/// </summary>
public /*enum*/ final class HtmlVersion
{
    private HtmlVersion(){}
    
    /// <summary>
    /// Saves the document in compliance with the XHTML 1.0 Transitional standard. 
    /// </summary>
    /// <remarks>
    /// <p>Aspose.Words aims to output XHTML according to the XHTML 1.0 Transitional standard, 
    /// but the output will not always validate against the DTD. Some structures inside a Microsoft Word 
    /// document are hard or impossible to map to a document that will validate against the XHTML schema. 
    /// For example, XHTML does not allow nested lists (UL cannot be nested inside another UL element), 
    /// but in Microsoft Word document multilevel lists occur quite often.</p>
    /// </remarks>
    public static final int XHTML = 0;
    /// <summary>
    /// Saves the document in compliance with the HTML 5 standard. 
    /// </summary>
    public static final int HTML_5 = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer HtmlVersion value.
    /// </summary>
    public static String getName(/*HtmlVersion*/int htmlVersion)
    {
    	switch (htmlVersion)
    	{
    		case XHTML: return "XHTML";
    		case HTML_5: return "HTML_5";
    		default: return "Unknown HtmlVersion value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer HtmlVersion value.
    /// </summary>
    public static String toString(/*HtmlVersion*/int htmlVersion)
    {
    	switch (htmlVersion)
    	{
    		case XHTML: return "Xhtml";
    		case HTML_5: return "Html5";
    		default: return "Unknown HtmlVersion value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by HtmlVersion name.
    /// </summary>
    public static /*HtmlVersion*/int fromName(String htmlVersionName)
    {
    	if ("XHTML".equals(htmlVersionName)) return XHTML;
    	if ("HTML_5".equals(htmlVersionName)) return HTML_5;
    	throw new IllegalArgumentException("Unknown HtmlVersion name.");
    }
    
    /// <summary>
    /// Returns array of HtmlVersion values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		XHTML,
    		HTML_5,
    	};
    }

    public static final int length = 2;
}

