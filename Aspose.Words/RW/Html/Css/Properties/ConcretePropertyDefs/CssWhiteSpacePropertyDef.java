// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/03/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Implements 'white-space' CSS property.
/// </summary>
class CssWhiteSpacePropertyDef extends CssIndividualSimplePropertyDef
{
    CssWhiteSpacePropertyDef()
    {
    	super(
            "white-space",
            true,
            CssValue.Normal,
            // normal | pre | nowrap | pre-wrap | pre-line
            CssValueFilter.values(
                CssValue.Normal,
                CssValue.Pre,
                CssValue.Nowrap,
                CssValue.PreWrap,
                CssValue.PreLine));
	
        // Empty constructor.
    }

    /*override*/ void toCellFormat(CssPropertyValue propertyValue, CellFormat cellFormat)
    {
        Debug.assertTrue(propertyValue.getCount() == 1);

        cellFormat.setWrapText(!propertyValue.getFirstValue().equals(CssValue.Nowrap));
    }
}

