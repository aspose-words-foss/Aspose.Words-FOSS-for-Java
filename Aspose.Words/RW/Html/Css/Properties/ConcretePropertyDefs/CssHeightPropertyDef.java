// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/03/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.MathUtil;


/// <summary>
/// Implements 'height' CSS property.
/// </summary>
class CssHeightPropertyDef extends CssIndividualSimplePropertyDef
{
    CssHeightPropertyDef()
    {
    	super(
            "height",
            false,
            CssValue.Auto,
            // <length> | <percentage> | auto
            CssValueFilter.anyOf(
                CssValueFilter.NON_NEGATIVE_QUIRKY_LENGTH,
                CssValueFilter.NON_NEGATIVE_PERCENTAGE,
                CssValueFilter.value(CssValue.Auto)));
	
        // Empty constructor.
    }

    /*override*/ void toRow(CssPropertyValue propertyValue, Row row)
    {
        Debug.assertTrue(propertyValue.getCount() == 1);

        double height = CssUtil.lengthToPoint(propertyValue);
        if (!MathUtil.areEqual(height, -Double.MAX_VALUE))
        {
            row.getRowFormat().setHeight(height);
            row.getRowFormat().setHeightRule(HeightRule.AT_LEAST);
        }
    }
}

