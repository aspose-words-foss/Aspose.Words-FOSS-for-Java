// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/08/2016 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements the 'min-width' CSS property.
/// </summary>
class CssMinWidthPropertyDef extends CssIndividualSimplePropertyDef
{
    CssMinWidthPropertyDef()
    {
    	super(
            "min-width",
            false,
            CssValue.Zero,
            CssValueFilter.anyOf(
                CssValueFilter.NON_NEGATIVE_QUIRKY_LENGTH,
                CssValueFilter.NON_NEGATIVE_PERCENTAGE));
	
        // Empty constructor.
    }
}

