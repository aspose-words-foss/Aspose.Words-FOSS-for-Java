// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/02/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements 'background-repeat' CSS property.
/// </summary>
class CssBackgroundRepeatPropertyDef extends CssIndividualSimplePropertyDef
{
    CssBackgroundRepeatPropertyDef()
    {
    	super(
            "background-repeat",
            false,
            CssValue.Repeat,
            // repeat | repeat-x | repeat-y | no-repeat
            CssValueFilter.values(
                CssValue.Repeat,
                CssValue.RepeatX,
                CssValue.RepeatY,
                CssValue.NoRepeat));
	
        // Empty constructor.
    }
}

