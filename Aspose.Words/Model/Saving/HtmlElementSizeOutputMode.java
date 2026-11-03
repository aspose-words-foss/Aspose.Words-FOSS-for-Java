// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/03/2011 by Viktor Sazhaev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how Aspose.Words exports element widths and heights to HTML, MHTML and EPUB.
/// </summary>
/// <seealso cref="HtmlSaveOptions.TableWidthOutputMode"/>
public /*enum*/ final class HtmlElementSizeOutputMode
{
    private HtmlElementSizeOutputMode(){}
    
    /// <summary>
    /// All element sizes, both in absolute and relative units, specified in the document are exported.
    /// </summary>
    public static final int ALL = 0;
    /// <summary>
    /// Element sizes are exported only if they are specified in relative units in the document. 
    /// Fixed sizes are not exported in this mode. Visual agents will calculate missing sizes to make 
    /// document layout more natural.
    /// </summary>
    public static final int RELATIVE_ONLY = 1;
    /// <summary>
    /// Element sizes are not exported. Visual agents will build layout automatically according to relationship between elements.
    /// </summary>
    public static final int NONE = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer HtmlElementSizeOutputMode value.
    /// </summary>
    public static String getName(/*HtmlElementSizeOutputMode*/int htmlElementSizeOutputMode)
    {
    	switch (htmlElementSizeOutputMode)
    	{
    		case ALL: return "ALL";
    		case RELATIVE_ONLY: return "RELATIVE_ONLY";
    		case NONE: return "NONE";
    		default: return "Unknown HtmlElementSizeOutputMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer HtmlElementSizeOutputMode value.
    /// </summary>
    public static String toString(/*HtmlElementSizeOutputMode*/int htmlElementSizeOutputMode)
    {
    	switch (htmlElementSizeOutputMode)
    	{
    		case ALL: return "All";
    		case RELATIVE_ONLY: return "RelativeOnly";
    		case NONE: return "None";
    		default: return "Unknown HtmlElementSizeOutputMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by HtmlElementSizeOutputMode name.
    /// </summary>
    public static /*HtmlElementSizeOutputMode*/int fromName(String htmlElementSizeOutputModeName)
    {
    	if ("ALL".equals(htmlElementSizeOutputModeName)) return ALL;
    	if ("RELATIVE_ONLY".equals(htmlElementSizeOutputModeName)) return RELATIVE_ONLY;
    	if ("NONE".equals(htmlElementSizeOutputModeName)) return NONE;
    	throw new IllegalArgumentException("Unknown HtmlElementSizeOutputMode name.");
    }
    
    /// <summary>
    /// Returns array of HtmlElementSizeOutputMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ALL,
    		RELATIVE_ONLY,
    		NONE,
    	};
    }

    public static final int length = 3;
}

