// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/08/2016 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements the 'max-height' CSS property.
/// </summary>
class CssMaxHeightPropertyDef extends CssIndividualSimplePropertyDef
{
    CssMaxHeightPropertyDef()
    {
    	super(
            "max-height",
            false,
            CssValue.None,
            // <length> | <percentage> | none
            // Note that this property also accepts unitless lengths in the Quirks mode.
            CssValueFilter.anyOf(
                CssValueFilter.NON_NEGATIVE_QUIRKY_LENGTH,
                CssValueFilter.NON_NEGATIVE_PERCENTAGE,
                CssValueFilter.value(CssValue.None)));
	
        // Empty constructor.
    }
}

