// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/04/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// The token that represents the text of a comment node.
/// </summary>
class HtmlCommentToken extends HtmlToken
{
    /// <summary>
    /// Constructor.
    /// </summary>
    /// <param name="text">The text of the comment.</param>
    HtmlCommentToken(String text)
    {
    	super(HtmlTokenType.COMMENT);
	
        Debug.assertTrue(text != null);

        mText = text;
    }

    /// <summary>
    /// Gets the text of the comment.
    /// </summary>
    String getText() { return mText; }

    private /*final*/ String mText;
}
