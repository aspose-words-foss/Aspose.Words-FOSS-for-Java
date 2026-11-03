// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/03/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements 'font-variant' CSS property.
/// </summary>
class CssFontVariantPropertyDef extends CssIndividualSimplePropertyDef
{
    CssFontVariantPropertyDef()
    {
    	super(
            "font-variant",
            true,
            CssValue.Normal,
            // normal | small-caps
            CssValueFilter.values(
                CssValue.Normal,
                CssValue.SmallCaps));
	
        // Empty constructor.
    }
}

