// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/08/2016 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements the 'max-width' CSS property.
/// </summary>
class CssMaxWidthPropertyDef extends CssIndividualSimplePropertyDef
{
    CssMaxWidthPropertyDef()
    {
    	super(
            "max-width",
            false,
            CssValue.None,
            // <length> | <percentage> | none
            CssValueFilter.anyOf(
                CssValueFilter.NON_NEGATIVE_QUIRKY_LENGTH,
                CssValueFilter.NON_NEGATIVE_PERCENTAGE,
                CssValueFilter.value(CssValue.None)));
	
        // Empty constructor.
    }
}

