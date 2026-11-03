// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/02/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements 'background-attachment' CSS property.
/// </summary>
class CssBackgroundAttachmentPropertyDef extends CssIndividualSimplePropertyDef
{
    CssBackgroundAttachmentPropertyDef()
    {
    	super(
            "background-attachment",
            false,
            CssValue.Scroll,
            // scroll | fixed
            CssValueFilter.values(CssValue.Scroll, CssValue.Fixed));
	
        // Empty constructor.
    }
}

