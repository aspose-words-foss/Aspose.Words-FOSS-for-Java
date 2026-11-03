// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/02/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.FormatterPal;


/// <summary>
/// Represents an identifier CSS percentage value.
/// </summary>
class CssPercentageValue extends CssValue
{
    CssPercentageValue(double value)
    {
    	super(CssValueType.PERCENTAGE, value);
	
        mValue = value;
    }

    /*override*/ void toCss(StringBuilder sb)
    {
        msStringBuilder.appendFormat(sb, "{0}%", FormatterPal.doubleToStr2Decimals(mValue));
    }

    /*override*/ double getDoubleValue() { return mValue; }

    private /*final*/ double mValue;
}
