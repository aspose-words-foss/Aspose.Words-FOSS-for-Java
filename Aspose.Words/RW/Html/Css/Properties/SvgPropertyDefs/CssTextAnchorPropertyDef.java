// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2017 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements 'text-anchor' CSS property for SVG.
/// </summary>
class CssTextAnchorPropertyDef extends CssIndividualSimplePropertyDef
{
    CssTextAnchorPropertyDef()
    {
    	super(
            "text-anchor",
            true,
            CssValue.Start,
            // start | middle | end
            CssValueFilter.values(
                CssValue.Start,
                CssValue.Middle,
                CssValue.End));
	
        // Nothing to do.
    }
}

