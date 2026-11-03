// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/08/2016 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements the 'min-height' CSS property.
/// </summary>
class CssMinHeightPropertyDef extends CssIndividualSimplePropertyDef
{
    CssMinHeightPropertyDef()
    {
    	super("min-height", false, CssValue.Zero,
            // <length> | <percentage>
            CssValueFilter.anyOf(
                CssValueFilter.NON_NEGATIVE_QUIRKY_LENGTH,
                CssValueFilter.NON_NEGATIVE_PERCENTAGE));
	
        // Empty constructor.
    }
}

