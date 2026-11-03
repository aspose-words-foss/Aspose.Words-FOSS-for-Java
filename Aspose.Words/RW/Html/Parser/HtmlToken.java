// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/04/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// The base class for different types of tokens returned by the <see cref="HtmlTokenizer"/>.
/// </summary>
abstract class HtmlToken
{
    protected HtmlToken(/*HtmlTokenType*/int type)
    {
        mType = type;
    }

    /// <summary>
    /// The type of the token.
    /// </summary>
    /*HtmlTokenType*/int getType() { return mType; }

    private /*final*/ /*HtmlTokenType*/int mType;
}

