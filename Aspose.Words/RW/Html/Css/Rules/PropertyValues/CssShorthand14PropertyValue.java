// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/09/2015 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Implements a shorthand property value related to edges of a box, 
/// like border-style, margin or padding, always use a consistent 1-to-4-value syntax representing edges.
/// </summary>
class CssShorthand14PropertyValue extends CssPropertyValue
{
    CssShorthand14PropertyValue(
        CssPropertyValue topValue,
        CssPropertyValue rightValue,
        CssPropertyValue bottomValue,
        CssPropertyValue leftValue)
    {
    	super(new CssValueList(
            topValue.getFirstValue(),
            rightValue.getFirstValue(),
            bottomValue.getFirstValue(),
            leftValue.getFirstValue()));
	
        Debug.assertTrue(topValue.getCount() == 1);
        Debug.assertTrue(rightValue.getCount() == 1);
        Debug.assertTrue(bottomValue.getCount() == 1);
        Debug.assertTrue(leftValue.getCount() == 1);

        mTopValue = topValue.getFirstValue();
        mRightValue = rightValue.getFirstValue();
        mBottomValue = bottomValue.getFirstValue();
        mLeftValue = leftValue.getFirstValue();
    }

    CssShorthand14PropertyValue(
        double topValue,
        double rightValue,
        double bottomValue,
        double leftValue,
        /*CssUnit*/int unitType)
    {
    	this(
            new CssPropertyValue(new CssLengthValue(topValue, unitType)),
            new CssPropertyValue(new CssLengthValue(rightValue, unitType)),
            new CssPropertyValue(new CssLengthValue(bottomValue, unitType)),
            new CssPropertyValue(new CssLengthValue(leftValue, unitType)));
	
        // Empty constructor.
    }

    protected /*override*/ void valueToCss(StringBuilder sb)
    {
        ArrayList<CssValue> values = new ArrayList<CssValue>();
        if (mTopValue.equals(mRightValue) && mTopValue.equals(mBottomValue) && mTopValue.equals(mLeftValue))
        {
            msArrayList.add(values, mTopValue);
        }
        else if (mTopValue.equals(mBottomValue) && mRightValue.equals(mLeftValue))
        {
            msArrayList.add(values, mTopValue);
            msArrayList.add(values, mRightValue);
        }
        else if (mRightValue.equals(mLeftValue))
        {
            msArrayList.add(values, mTopValue);
            msArrayList.add(values, mRightValue);
            msArrayList.add(values, mBottomValue);
        }
        else
        {
            msArrayList.add(values, mTopValue);
            msArrayList.add(values, mRightValue);
            msArrayList.add(values, mBottomValue);
            msArrayList.add(values, mLeftValue);
        }

        boolean firstValue = true;
        for (CssValue value : values)
        {
            if (!firstValue)
                sb.append(' ');
            value.toCss(sb);
            firstValue = false;
        }
    }

    private /*final*/ CssValue mTopValue;
    private /*final*/ CssValue mRightValue;
    private /*final*/ CssValue mBottomValue;
    private /*final*/ CssValue mLeftValue;
}

