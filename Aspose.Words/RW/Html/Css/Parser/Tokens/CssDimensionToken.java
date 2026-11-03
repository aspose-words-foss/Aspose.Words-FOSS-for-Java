// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/06/2019 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class CssDimensionToken extends CssNumberToken
{
    CssDimensionToken(double value, String unit, boolean isInteger, boolean isSigned)
    {
    	super(CssTokenType.DIMENSION, value, isInteger, isSigned);
	
        mUnit = unit;
    }

    String getUnit() { return mUnit; };

    private String mUnit;
}

