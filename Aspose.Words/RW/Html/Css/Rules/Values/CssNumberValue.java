// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/02/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.FormatterPal;


/// <summary>
/// Represents an identifier CSS number value.
/// </summary>
class CssNumberValue extends CssValue
{
    CssNumberValue(double value)
    {
    	super(CssValueType.NUMBER, value);
	
        mValue = value;
    }

    /*override*/ void toCss(StringBuilder sb)
    {
        msStringBuilder.append(sb, FormatterPal.doubleToStr2Decimals(mValue));
    }

    /*override*/ double getDoubleValue() { return mValue; }

    double quirkyLengthToPoint()
    {
        return new CssLengthValue(mValue, CssUnit.PX).getLength(CssUnit.PT);
    }

    private /*final*/ double mValue;
}

