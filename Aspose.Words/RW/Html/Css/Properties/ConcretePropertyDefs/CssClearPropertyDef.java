// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/03/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements 'clear' CSS property.
/// </summary>
class CssClearPropertyDef extends CssIndividualSimplePropertyDef
{
    CssClearPropertyDef()
    {
    	super(
            "clear",
            false,
            CssValue.None,
            // none | left | right | both
            CssValueFilter.values(CssValue.None, CssValue.Left, CssValue.Right, CssValue.Both));
	
        // Empty constructor.
    }
}

