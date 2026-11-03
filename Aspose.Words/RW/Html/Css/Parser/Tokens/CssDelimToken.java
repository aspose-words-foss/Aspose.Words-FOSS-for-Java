// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/06/2019 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class CssDelimToken extends CssToken
{
    CssDelimToken(char value)
    {
    	super(CssTokenType.DELIM);
	
        mValue = value;
    }

    char getValue() { return mValue; };

    private char mValue;
}

