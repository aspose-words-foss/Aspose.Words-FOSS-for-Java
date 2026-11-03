// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/12/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Represents a token into which a conditional expression is split during parsing.
/// </summary>
class Token
{
    Token(/*TokenType*/int type, String text)
    {
        Debug.assertTrue(text != null);
        mType = type;
        mText = text;
    }

    /*TokenType*/int getTokenType() { return mType; }

    String getText() { return mText; }

    private /*final*/ /*TokenType*/int mType;
    private /*final*/ String mText;
}

