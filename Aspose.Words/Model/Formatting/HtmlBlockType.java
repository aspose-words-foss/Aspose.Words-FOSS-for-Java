// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/02/2013 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies type for <see cref="HtmlBlock" /> element.
/// </summary>
/*enum*/ final class HtmlBlockType
{
    private HtmlBlockType(){}
    
    /// <summary>
    /// Specifies that HtmlBlock represents an HTML div element. 
    /// </summary>
    public static final int DIV = 0;

    /// <summary>
    /// Specifies that HtmlBlock represents an HTML blockquote element. 
    /// This element shall specify that this container shall be written out using the blockquote element 
    /// if this document is subsequently saved as HTML.
    /// </summary>
    public static final int BLOCK_QUOTE = 1;

    /// <summary>
    /// Specifies that HtmlBlock represents formatting properties on the HTML body element. 
    /// This element shall specify that the properties specified by this container shall be written 
    /// out onto the body element if this document is subsequently saved as HTML.
    /// </summary>
    public static final int BODY = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer HtmlBlockType value.
    /// </summary>
    public static String getName(/*HtmlBlockType*/int htmlBlockType)
    {
    	switch (htmlBlockType)
    	{
    		case DIV: return "DIV";
    		case BLOCK_QUOTE: return "BLOCK_QUOTE";
    		case BODY: return "BODY";
    		default: return "Unknown HtmlBlockType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer HtmlBlockType value.
    /// </summary>
    public static String toString(/*HtmlBlockType*/int htmlBlockType)
    {
    	switch (htmlBlockType)
    	{
    		case DIV: return "Div";
    		case BLOCK_QUOTE: return "BlockQuote";
    		case BODY: return "Body";
    		default: return "Unknown HtmlBlockType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by HtmlBlockType name.
    /// </summary>
    public static /*HtmlBlockType*/int fromName(String htmlBlockTypeName)
    {
    	if ("DIV".equals(htmlBlockTypeName)) return DIV;
    	if ("BLOCK_QUOTE".equals(htmlBlockTypeName)) return BLOCK_QUOTE;
    	if ("BODY".equals(htmlBlockTypeName)) return BODY;
    	throw new IllegalArgumentException("Unknown HtmlBlockType name.");
    }
    
    /// <summary>
    /// Returns array of HtmlBlockType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DIV,
    		BLOCK_QUOTE,
    		BODY,
    	};
    }

    public static final int length = 3;
}

