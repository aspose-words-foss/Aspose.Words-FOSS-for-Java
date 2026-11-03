// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/04/2020 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements 'baseline-shift' CSS property for SVG.
/// </summary>
class CssBaseLineShiftPropertyDef extends CssIndividualSimplePropertyDef
{
    CssBaseLineShiftPropertyDef()
    {
    	super(
            "baseline-shift",
            false,
            CssValue.Zero,
            CssValueFilter.anyOf(
                CssValueFilter.NUMBER,
                CssValueFilter.LENGTH,
                CssValueFilter.PERCENTAGE,
                CssValueFilter.values(CssValue.Sub, CssValue.Super)));
	
        // Empty constructor.
    }
}

