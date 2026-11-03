// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/06/2019 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class CssHashToken extends CssTextToken
{
    CssHashToken(String text, boolean isIdentifier)
    {
    	super(CssTokenType.HASH, text);
	
        mIsIdentifier = isIdentifier;
    }

    boolean isIdentifier() { return mIsIdentifier; };

    private boolean mIsIdentifier;
}

