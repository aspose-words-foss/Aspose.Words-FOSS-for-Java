// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.Debug;


/// <summary>
/// Parses arguments of the :lang() pseudo-class.
/// </summary>
/// <remarks>
/// An argument must be a valid CSS identifier, optionally surrounded by whitespace.
/// See http://www.w3.org/TR/css3-selectors/#lang-pseudo 
/// </remarks>
class CssLangArgumentParser
{
    /// <summary>
    /// Initializes a new instance of the class.
    /// </summary>
    /// <param name="tokens">Tokens of the argument.</param>
    private CssLangArgumentParser(ArrayList<CssToken> tokens)
    {
        Debug.assertTrue(tokens != null);
        mTokens = tokens;
    }

    /// <summary>
    /// Parses the tokens into a pseudo-class argument.
    /// </summary>
    /// <returns>The parsed argument or <c>null</c> in case of an error.</returns>
    static String parse(ArrayList<CssToken> tokens)
    {
        CssLangArgumentParser parser = new CssLangArgumentParser(tokens);
        return parser.run();
    }

    /// <summary>
    /// Parses the tokens into a pseudo-class argument.
    /// </summary>
    /// <returns>The parsed argument or <c>null</c> in case of an error.</returns>
    private String run()
    {
        mTokenIndex = 0;
        if ((mTokens == null) || (mTokenIndex >= mTokens.size()))
        {
            // Parsing error. The list of argument tokens is empty.
            return null;
        }

        // Pre-fetch the current token.
        mCurrentToken = (mTokenIndex < mTokens.size())
            ? mTokens.get(mTokenIndex)
            : CssToken.EOF;

        // Optional leading whitespace.
        skipOptionalWhitespace();

        // The argument token, which must be an identifier.
        if (mCurrentToken.getType() != CssTokenType.IDENT)
        {
            // Parsing error. Unexpected token.
            return null;
        }

        String result = ((CssTextToken)mCurrentToken).getText();
        if (result.length() == 0)
        {
            // Parsing error. Unexpected token value.
            return null;
        }
        advance();

        // Optional trailing whitespace.
        skipOptionalWhitespace();

        if (!mCurrentToken.isEof())
        {
            // Parsing error. There are some additional tokens after the parsed argument.
            return null;
        }

        return result;
    }

    private void skipOptionalWhitespace()
    {
        if (mCurrentToken.isWhitespace())
        {
            advance();
        }
    }

    /// <summary>
    /// Go to the next token of the argument.
    /// </summary>
    private void advance()
    {
        if (mTokenIndex < mTokens.size())
        {
            ++mTokenIndex;
            mCurrentToken = (mTokenIndex < mTokens.size())
                ? mTokens.get(mTokenIndex)
                : CssToken.EOF;
        }
        else
        {
            Debug.fail("Attempt to read past the end of a token list.");
        }
    }

    /// <summary>
    /// The index of the token being parsed currently.
    /// </summary>
    private int mTokenIndex;

    /// <summary>
    /// Tokens of the argument.
    /// </summary>
    private /*final*/ ArrayList<CssToken> mTokens;

    /// <summary>
    /// The token being parsed currently.
    /// </summary>
    private CssToken mCurrentToken;
}

