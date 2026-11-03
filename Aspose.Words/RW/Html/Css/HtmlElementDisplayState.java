// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/10/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents HTML element's display state based on CSS 'display' and 'visibility' properties.
/// </summary>
/// <remarks>
/// The support for visibility:collapse is missing or partially incorrect in some modern browsers. We ignore it too.
/// </remarks>
/*enum*/ final class HtmlElementDisplayState
{
    private HtmlElementDisplayState(){}
    
    /// <summary>
    /// Element is visible.
    /// </summary>
    public static final int VISIBLE = 0;
    /// <summary>
    /// Element should be hidden (see 'visibility:hidden').
    /// </summary>
    public static final int HIDDEN = 1;
    /// <summary>
    /// Element shouldn't be displayed (see 'display:none'). 
    /// </summary>
    public static final int NONE = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer HtmlElementDisplayState value.
    /// </summary>
    public static String getName(/*HtmlElementDisplayState*/int htmlElementDisplayState)
    {
    	switch (htmlElementDisplayState)
    	{
    		case VISIBLE: return "VISIBLE";
    		case HIDDEN: return "HIDDEN";
    		case NONE: return "NONE";
    		default: return "Unknown HtmlElementDisplayState value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer HtmlElementDisplayState value.
    /// </summary>
    public static String toString(/*HtmlElementDisplayState*/int htmlElementDisplayState)
    {
    	switch (htmlElementDisplayState)
    	{
    		case VISIBLE: return "Visible";
    		case HIDDEN: return "Hidden";
    		case NONE: return "None";
    		default: return "Unknown HtmlElementDisplayState value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by HtmlElementDisplayState name.
    /// </summary>
    public static /*HtmlElementDisplayState*/int fromName(String htmlElementDisplayStateName)
    {
    	if ("VISIBLE".equals(htmlElementDisplayStateName)) return VISIBLE;
    	if ("HIDDEN".equals(htmlElementDisplayStateName)) return HIDDEN;
    	if ("NONE".equals(htmlElementDisplayStateName)) return NONE;
    	throw new IllegalArgumentException("Unknown HtmlElementDisplayState name.");
    }
    
    /// <summary>
    /// Returns array of HtmlElementDisplayState values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		VISIBLE,
    		HIDDEN,
    		NONE,
    	};
    }

    public static final int length = 3;
}
