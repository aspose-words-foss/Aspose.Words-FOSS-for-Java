// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/03/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements 'font-style' CSS property.
/// </summary>
class CssFontStylePropertyDef extends CssIndividualSimplePropertyDef
{
    CssFontStylePropertyDef()
    {
    	super(
            "font-style",
            true,
            CssValue.Normal,
            // normal | italic | oblique
            CssValueFilter.values(
                CssValue.Normal,
                CssValue.Italic,
                CssValue.Oblique));
	
        // Empty constructor.
    }
}

