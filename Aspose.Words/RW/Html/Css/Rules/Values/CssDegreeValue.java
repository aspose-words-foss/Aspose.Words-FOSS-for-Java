// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/10/2015 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.FormatterPal;
import com.aspose.MathUtil;


/// <summary>
/// Represents a CSS degree value such as 90deg.
/// </summary>
class CssDegreeValue extends CssValue
{
    CssDegreeValue(double value)
    {
    	super(CssValueType.DEGREE, value);
	
        mValue = value;
    }

    /*override*/ void toCss(StringBuilder sb)
    {
        msStringBuilder.appendFormat(sb, "{0}deg", FormatterPal.doubleToStr2Decimals(mValue));
    }

    protected /*override*/ boolean doEquals(CssValue other)
    {
        if (!(other instanceof CssDegreeValue))
            return false;

        final double TOLERANCE = 0.005; // This accuracy is enough in HTML.
        CssDegreeValue otherDegree = (CssDegreeValue)other;
        return MathUtil.areEqual(mValue, otherDegree.mValue, TOLERANCE);
    }

    /*override*/ double getDoubleValue() { return mValue; }

    private /*final*/ double mValue;
}
