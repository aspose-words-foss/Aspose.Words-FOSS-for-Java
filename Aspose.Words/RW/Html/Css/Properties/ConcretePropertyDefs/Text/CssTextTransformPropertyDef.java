// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/03/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements 'text-transform' CSS property.
/// </summary>
class CssTextTransformPropertyDef extends CssIndividualSimplePropertyDef
{
    CssTextTransformPropertyDef()
    {
    	super(
            "text-transform",
            true,
            CssValue.None,
            // capitalize | uppercase | lowercase | none
            CssValueFilter.values(
                CssValue.Capitalize,
                CssValue.UpperCase,
                CssValue.LowerCase,
                CssValue.None));
	
        // Empty constructor.
    }
}

