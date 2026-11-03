// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/06/2019 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class CssNumberToken extends CssToken
{
    CssNumberToken(/*CssTokenType*/int tokenType, double value, boolean isInteger, boolean isSigned)
    {
    	super(tokenType);
	
        mValue = value;
        mIsInteger = isInteger;
        mIsSigned = isSigned;
    }

    double getValue() { return mValue; };

    private double mValue;

    boolean isInteger() { return mIsInteger; };

    private boolean mIsInteger;

    boolean isSigned() { return mIsSigned; };

    private boolean mIsSigned;
}

