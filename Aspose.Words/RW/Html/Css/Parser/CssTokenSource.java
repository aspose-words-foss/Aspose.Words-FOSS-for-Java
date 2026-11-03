// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/06/2019 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// A source that provides CSS tokens for the CSS parser.
/// </summary>
abstract class CssTokenSource
{
    boolean hasMoreTokens()
    {
        CssToken nextToken = consume();
        reconsume();
        return !nextToken.isEof();
    }

    abstract CssToken consume();

    abstract void reconsume();

    void consumeOptionalWhitespace()
    {
        CssToken token = consume();
        if (token.isWhitespace())
        {
            consume();
        }
        reconsume();
    }

    /// <summary>
    /// Skips optional whitespace and consumes a token.
    /// </summary>
    CssToken consumeWhitespaceAndToken()
    {
        CssToken token = consume();
        return token.isWhitespace()
            ? consume()
            : token;
    }
}

