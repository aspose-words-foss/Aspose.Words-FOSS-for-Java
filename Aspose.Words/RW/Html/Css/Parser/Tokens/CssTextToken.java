// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/07/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a CSS token whose value is text. For example, class, ID, function, URL.
/// </summary>
/// <remarks>
/// The tokens this class represents share the following features:
/// a) the value of such a token is a text (class name for class token, function name for function, and so on);
/// b) the text of such a token can contain CSS escape sequences (that is, the value of the token can differ from its
///    representation in the source file; 'text' of the token can differ from its 'raw text').
/// </remarks>
class CssTextToken extends CssToken
{
    CssTextToken(/*CssTokenType*/int tokenType, String text)
    {
    	super(tokenType);
	
        mText = text;
    }

    String getText() { return mText; };

    private String mText;
}

