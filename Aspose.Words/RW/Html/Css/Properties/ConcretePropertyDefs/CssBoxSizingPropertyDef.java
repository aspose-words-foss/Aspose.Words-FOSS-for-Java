// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/06/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements the 'box-sizing' CSS property.
/// </summary>
/// <remarks>
/// The property is a part of CSS 3. Specification: http://www.w3.org/TR/css3-ui/#box-sizing
/// </remarks>
class CssBoxSizingPropertyDef extends CssIndividualSimplePropertyDef
{
    CssBoxSizingPropertyDef()
    {
    	super(
            "box-sizing",
            true,
            CssValue.Separate,
            // content-box | padding-box | border-box
            CssValueFilter.values(CssValue.ContentBox, CssValue.PaddingBox, CssValue.BorderBox));
	
        // Empty constructor.
    }
}

