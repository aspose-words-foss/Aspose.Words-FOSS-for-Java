// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/10/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Identifies a part of an HTML element to which CSS formatting is applied.
/// </summary>
/// <remarks>
/// CSS formatting can be applied not only to whole CSS elements but also to specific parts of an element -
/// to pseudo-elements, identified by double colon (::) syntax.
/// Examples of such parts are: first letter (::first-letter) and first line of text (::first-line), additional content
/// before (::before) or after (::after) the element. Not all of them are supported by Aspose.Words.
/// </remarks>
/*enum*/ final class HtmlElementPart
{
    private HtmlElementPart(){}
    
    /// <summary>
    /// A real HTML element as a whole. By default, CSS formatting is applied to this part.
    /// </summary>
    public static final int ELEMENT = 0;

    /// <summary>
    /// Additional content inserted before the real element (::before).
    /// </summary>
    public static final int BEFORE = 1;

    /// <summary>
    /// Additional content inserted after the real element (::after).
    /// </summary>
    public static final int AFTER = 2;

    /// <summary>
    /// First letter of text inside the real element (::first-letter).
    /// </summary>
    public static final int FIRST_LETTER = 3;

    /// <summary>
    /// First line of text inside the real element (::first-line).
    /// </summary>
    public static final int FIRST_LINE = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer HtmlElementPart value.
    /// </summary>
    public static String getName(/*HtmlElementPart*/int htmlElementPart)
    {
    	switch (htmlElementPart)
    	{
    		case ELEMENT: return "ELEMENT";
    		case BEFORE: return "BEFORE";
    		case AFTER: return "AFTER";
    		case FIRST_LETTER: return "FIRST_LETTER";
    		case FIRST_LINE: return "FIRST_LINE";
    		default: return "Unknown HtmlElementPart value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer HtmlElementPart value.
    /// </summary>
    public static String toString(/*HtmlElementPart*/int htmlElementPart)
    {
    	switch (htmlElementPart)
    	{
    		case ELEMENT: return "Element";
    		case BEFORE: return "Before";
    		case AFTER: return "After";
    		case FIRST_LETTER: return "FirstLetter";
    		case FIRST_LINE: return "FirstLine";
    		default: return "Unknown HtmlElementPart value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by HtmlElementPart name.
    /// </summary>
    public static /*HtmlElementPart*/int fromName(String htmlElementPartName)
    {
    	if ("ELEMENT".equals(htmlElementPartName)) return ELEMENT;
    	if ("BEFORE".equals(htmlElementPartName)) return BEFORE;
    	if ("AFTER".equals(htmlElementPartName)) return AFTER;
    	if ("FIRST_LETTER".equals(htmlElementPartName)) return FIRST_LETTER;
    	if ("FIRST_LINE".equals(htmlElementPartName)) return FIRST_LINE;
    	throw new IllegalArgumentException("Unknown HtmlElementPart name.");
    }
    
    /// <summary>
    /// Returns array of HtmlElementPart values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ELEMENT,
    		BEFORE,
    		AFTER,
    		FIRST_LETTER,
    		FIRST_LINE,
    	};
    }

    public static final int length = 5;
}

