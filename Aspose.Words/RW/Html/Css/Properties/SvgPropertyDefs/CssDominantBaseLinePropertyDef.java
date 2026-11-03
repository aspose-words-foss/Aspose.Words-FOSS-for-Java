// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/04/2020 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements 'dominant-baseline' CSS property for SVG.
/// </summary>
class CssDominantBaseLinePropertyDef extends CssIndividualSimplePropertyDef
{
    CssDominantBaseLinePropertyDef()
    {
    	super(
            "dominant-baseline",
            true,
            CssValue.Auto,
            // auto | text-bottom | alphabetic | ideographic | middle | central | mathematical | hanging | text-top
            CssValueFilter.values(
                CssValue.Auto,
                CssValue.TextBottom,
                CssValue.Alphabetic,
                CssValue.Ideographic,
                CssValue.Middle,
                CssValue.Central,
                CssValue.Mathematical,
                CssValue.Hanging,
                CssValue.TextTop));
	
        // Empty constructor.
    }
}

