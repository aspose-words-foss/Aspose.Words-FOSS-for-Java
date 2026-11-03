// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/02/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements 'background-image' CSS property.
/// </summary>
class CssBackgroundImagePropertyDef extends CssIndividualSimplePropertyDef
{
    CssBackgroundImagePropertyDef()
    {
    	super(
            "background-image",
            false,
            CssValue.None,
            // <uri> | none
            CssValueFilter.anyOf(
                CssValueFilter.URI,
                CssValueFilter.value(CssValue.None)));
	
        // Empty constructor.
    }
}

