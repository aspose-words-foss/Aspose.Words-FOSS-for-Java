// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/03/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Implements 'writing-mode' CSS property.
/// </summary>
class CssWritingModePropertyDef extends CssIndividualSimplePropertyDef
{
    CssWritingModePropertyDef()
    {
    	super(
            "writing-mode",
            true,
            CssValue.HorizontalTb,
            // lr-tb | rl-tb | tb-rl | bt-rl | tb-lr | bt-lr | horizontal-tb | vertical-rl | vertical-lr
            CssValueFilter.values(
                CssValue.HorizontalTb,
                CssValue.VerticalRl,
                CssValue.VerticalLr,
                CssValue.LrTb,
                CssValue.RlTb,
                CssValue.TbRl,
                CssValue.BtRl,
                CssValue.TbLr,
                CssValue.BtLr));
	
        // Empty constructor.
    }

    /*override*/ void toCellFormat(CssPropertyValue propertyValue, CellFormat cellFormat)
    {
        Debug.assertTrue(propertyValue.getCount() == 1);

        CssValue cssValue = propertyValue.getFirstValue();
        if (cssValue.getValueType() == CssValueType.IDENTIFIER)
            cellFormat.setOrientation(CssUtil.cssToTextOrientation(((CssIdentifierValue)cssValue).getValue()));
        else
            Debug.assertTrue(false);
    }
}

