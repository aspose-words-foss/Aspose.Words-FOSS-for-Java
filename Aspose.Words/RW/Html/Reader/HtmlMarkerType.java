// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/06/2019 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Type of a list item marker imported from HTML.
/// </summary>
/*enum*/ final class HtmlMarkerType
{
    private HtmlMarkerType(){}
    
    /// <summary>
    /// Marker specified by '-aw-*' CSS properties.
    /// </summary>
    public static final int AW = 0;

    /// <summary>
    /// Marker specified by a &lt;ul&gt; HTML element.
    /// </summary>
    public static final int HTML = 1;

    /// <summary>
    /// Marker specified by a "::before" or "::after" pseudo-element.
    /// </summary>
    public static final int PSEUDO_ELEMENT = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer HtmlMarkerType value.
    /// </summary>
    public static String getName(/*HtmlMarkerType*/int htmlMarkerType)
    {
    	switch (htmlMarkerType)
    	{
    		case AW: return "AW";
    		case HTML: return "HTML";
    		case PSEUDO_ELEMENT: return "PSEUDO_ELEMENT";
    		default: return "Unknown HtmlMarkerType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer HtmlMarkerType value.
    /// </summary>
    public static String toString(/*HtmlMarkerType*/int htmlMarkerType)
    {
    	switch (htmlMarkerType)
    	{
    		case AW: return "Aw";
    		case HTML: return "Html";
    		case PSEUDO_ELEMENT: return "PseudoElement";
    		default: return "Unknown HtmlMarkerType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by HtmlMarkerType name.
    /// </summary>
    public static /*HtmlMarkerType*/int fromName(String htmlMarkerTypeName)
    {
    	if ("AW".equals(htmlMarkerTypeName)) return AW;
    	if ("HTML".equals(htmlMarkerTypeName)) return HTML;
    	if ("PSEUDO_ELEMENT".equals(htmlMarkerTypeName)) return PSEUDO_ELEMENT;
    	throw new IllegalArgumentException("Unknown HtmlMarkerType name.");
    }
    
    /// <summary>
    /// Returns array of HtmlMarkerType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		AW,
    		HTML,
    		PSEUDO_ELEMENT,
    	};
    }

    public static final int length = 3;
}

