// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/02/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements 'unicode-bidi' CSS property.
/// </summary>
class CssUnicodeBidiPropertyDef extends CssIndividualSimplePropertyDef
{
    CssUnicodeBidiPropertyDef()
    {
    	super(
            "unicode-bidi",
            false,
            CssValue.Normal,
            // normal | embed | bidi-override
            CssValueFilter.values(CssValue.Normal, CssValue.Embed, CssValue.BidiOverride));
	
        // Empty constructor.
    }
}

