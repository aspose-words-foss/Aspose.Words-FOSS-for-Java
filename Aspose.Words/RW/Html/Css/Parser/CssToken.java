// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/01/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a CSS parser token.
/// </summary>
class CssToken
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="tokenType">Token type.</param>
    CssToken(/*CssTokenType*/int tokenType)
    {
        mType = tokenType;
    }

    /// <summary>
    /// Token type.
    /// </summary>
    /*CssTokenType*/int getType() { return mType; };

    private /*CssTokenType*/int mType;

    boolean isDelim(char delimChar)
    {
        return (getType() == CssTokenType.DELIM) && (((CssDelimToken)this).getValue() == delimChar);
    }

    boolean isEof()
    {
        return getType() == CssTokenType.EOF;
    }

    boolean isWhitespace()
    {
        return getType() == CssTokenType.WHITESPACE;
    }

    static /*final*/ CssToken EOF = new CssToken(CssTokenType.EOF);
}

