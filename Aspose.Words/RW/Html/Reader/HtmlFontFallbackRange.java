// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/11/2014 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;

/// <summary>
/// Represents a range of text that should be optionally rendered with a fallback font.
/// </summary>
class HtmlFontFallbackRange
{
    /// <summary>
    /// Constructor. Creates a range without a fallback font.
    /// </summary>
    HtmlFontFallbackRange(String text)
    {
    	this(text, null);
	
        // Empty constructor.
    }

    /// <summary>
    /// Constructor. Creates a range with a fallback font.
    /// </summary>
    HtmlFontFallbackRange(String text, String fallbackFontName)
    {
        mText = text;
        mFallbackFontName = fallbackFontName;
    }

    String getText() { return mText; }

    String getFallbackFontName() { return mFallbackFontName; }

    boolean getNeedsFontFallback() { return StringUtil.hasChars(mFallbackFontName); }

    private /*final*/ String mText;
    private /*final*/ String mFallbackFontName;
}
