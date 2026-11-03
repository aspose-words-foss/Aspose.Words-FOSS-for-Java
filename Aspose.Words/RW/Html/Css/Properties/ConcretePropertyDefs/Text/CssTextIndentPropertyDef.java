// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/03/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements 'text-indent' CSS property.
/// </summary>
class CssTextIndentPropertyDef extends CssIndividualSimplePropertyDef
{
    CssTextIndentPropertyDef()
    {
    	super(
            "text-indent",
            true,
            CssValue.Zero,
            CssValueFilter.anyOf(
                CssValueFilter.PERCENTAGE,
                CssValueFilter.QUIRKY_LENGTH));
	
        // Empty constructor.
    }
}

