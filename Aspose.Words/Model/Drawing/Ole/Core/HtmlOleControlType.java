// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/12/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies types for <see cref="HtmlOleControl" />.
/// </summary>
/*enum*/ final class HtmlOleControlType
{
    private HtmlOleControlType(){}
    
    public static final int UNKNOWN = 0;
    public static final int SELECT = 1;
    public static final int OPTION = 2;
    public static final int SUBMIT_BUTTON = 3;
    public static final int HIDDEN = 4;
    public static final int TEXT = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer HtmlOleControlType value.
    /// </summary>
    public static String getName(/*HtmlOleControlType*/int htmlOleControlType)
    {
    	switch (htmlOleControlType)
    	{
    		case UNKNOWN: return "UNKNOWN";
    		case SELECT: return "SELECT";
    		case OPTION: return "OPTION";
    		case SUBMIT_BUTTON: return "SUBMIT_BUTTON";
    		case HIDDEN: return "HIDDEN";
    		case TEXT: return "TEXT";
    		default: return "Unknown HtmlOleControlType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer HtmlOleControlType value.
    /// </summary>
    public static String toString(/*HtmlOleControlType*/int htmlOleControlType)
    {
    	switch (htmlOleControlType)
    	{
    		case UNKNOWN: return "Unknown";
    		case SELECT: return "Select";
    		case OPTION: return "Option";
    		case SUBMIT_BUTTON: return "SubmitButton";
    		case HIDDEN: return "Hidden";
    		case TEXT: return "Text";
    		default: return "Unknown HtmlOleControlType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by HtmlOleControlType name.
    /// </summary>
    public static /*HtmlOleControlType*/int fromName(String htmlOleControlTypeName)
    {
    	if ("UNKNOWN".equals(htmlOleControlTypeName)) return UNKNOWN;
    	if ("SELECT".equals(htmlOleControlTypeName)) return SELECT;
    	if ("OPTION".equals(htmlOleControlTypeName)) return OPTION;
    	if ("SUBMIT_BUTTON".equals(htmlOleControlTypeName)) return SUBMIT_BUTTON;
    	if ("HIDDEN".equals(htmlOleControlTypeName)) return HIDDEN;
    	if ("TEXT".equals(htmlOleControlTypeName)) return TEXT;
    	throw new IllegalArgumentException("Unknown HtmlOleControlType name.");
    }
    
    /// <summary>
    /// Returns array of HtmlOleControlType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		UNKNOWN,
    		SELECT,
    		OPTION,
    		SUBMIT_BUTTON,
    		HIDDEN,
    		TEXT,
    	};
    }

    public static final int length = 6;
}

