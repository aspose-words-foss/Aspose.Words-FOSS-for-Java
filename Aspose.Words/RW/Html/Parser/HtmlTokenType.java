// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/04/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Types of tokens returned by <see cref="HtmlTokenizer"/>.
/// </summary>
/*enum*/ final class HtmlTokenType
{
    private HtmlTokenType(){}
    
    /// <summary>
    /// The text that is not a part of HTML markup.
    /// </summary>
    public static final int TEXT = 0;

    /// <summary>
    /// The text from a comment node.
    /// </summary>
    public static final int COMMENT = 1;
    
    /// <summary>
    /// The DOCTYPE node.
    /// </summary>
    public static final int DOCTYPE = 2;

    /// <summary>
    /// The tag node.
    /// </summary>
    public static final int TAG = 3;

    /// <summary>
    /// The special token that indicates that the end of HTML is reached and no other tokens will be returned.
    /// </summary>
    public static final int END_OF_FILE = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer HtmlTokenType value.
    /// </summary>
    public static String getName(/*HtmlTokenType*/int htmlTokenType)
    {
    	switch (htmlTokenType)
    	{
    		case TEXT: return "TEXT";
    		case COMMENT: return "COMMENT";
    		case DOCTYPE: return "DOCTYPE";
    		case TAG: return "TAG";
    		case END_OF_FILE: return "END_OF_FILE";
    		default: return "Unknown HtmlTokenType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer HtmlTokenType value.
    /// </summary>
    public static String toString(/*HtmlTokenType*/int htmlTokenType)
    {
    	switch (htmlTokenType)
    	{
    		case TEXT: return "Text";
    		case COMMENT: return "Comment";
    		case DOCTYPE: return "Doctype";
    		case TAG: return "Tag";
    		case END_OF_FILE: return "EndOfFile";
    		default: return "Unknown HtmlTokenType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by HtmlTokenType name.
    /// </summary>
    public static /*HtmlTokenType*/int fromName(String htmlTokenTypeName)
    {
    	if ("TEXT".equals(htmlTokenTypeName)) return TEXT;
    	if ("COMMENT".equals(htmlTokenTypeName)) return COMMENT;
    	if ("DOCTYPE".equals(htmlTokenTypeName)) return DOCTYPE;
    	if ("TAG".equals(htmlTokenTypeName)) return TAG;
    	if ("END_OF_FILE".equals(htmlTokenTypeName)) return END_OF_FILE;
    	throw new IllegalArgumentException("Unknown HtmlTokenType name.");
    }
    
    /// <summary>
    /// Returns array of HtmlTokenType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TEXT,
    		COMMENT,
    		DOCTYPE,
    		TAG,
    		END_OF_FILE,
    	};
    }

    public static final int length = 5;
}

