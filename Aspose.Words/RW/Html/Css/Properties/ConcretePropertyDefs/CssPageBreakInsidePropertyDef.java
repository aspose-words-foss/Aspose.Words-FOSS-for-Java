// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/03/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Implements 'page-break-inside' CSS property.
/// </summary>
class CssPageBreakInsidePropertyDef extends CssIndividualSimplePropertyDef
{
    CssPageBreakInsidePropertyDef()
    {
    	super(
            "page-break-inside",
            false,
            CssValue.Auto,
            CssValueFilter.values(CssValue.Avoid, CssValue.Auto));
	
        // Empty constructor.
    }

    /*override*/ void toRow(CssPropertyValue propertyValue, Row row)
    {
        Debug.assertTrue(propertyValue.getCount() == 1);

        CssValue cssValue = propertyValue.getFirstValue();
        row.getRowFormat().setAllowBreakAcrossPages(cssValue.equals(CssValue.Auto));
    }
}

