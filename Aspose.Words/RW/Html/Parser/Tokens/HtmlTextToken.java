// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/04/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// The token that represents text that is not a part of HTML markup. 
/// </summary>
class HtmlTextToken extends HtmlToken
{
    /// <summary>
    /// Constructor.
    /// </summary>
    /// <param name="text">The text of the token.</param>
    HtmlTextToken(String text)
    {
    	super(HtmlTokenType.TEXT);
	
        Debug.assertTrue(text != null);

        mText = text;
    }

    /// <summary>
    /// Gets the text of the token.
    /// </summary>
    String getText() { return mText; }

    private /*final*/ String mText;
}

