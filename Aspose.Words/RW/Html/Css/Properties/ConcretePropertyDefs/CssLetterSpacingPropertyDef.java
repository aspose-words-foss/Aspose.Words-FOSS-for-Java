// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/04/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements 'letter-spacing' CSS property.
/// </summary>
class CssLetterSpacingPropertyDef extends CssIndividualSimplePropertyDef
{
    CssLetterSpacingPropertyDef()
    {
    	super(
              "letter-spacing",
              true,
              CssValue.Normal,
              // normal | <length>
              CssValueFilter.anyOf(
                  CssValueFilter.value(CssValue.Normal),
                  CssValueFilter.QUIRKY_LENGTH));
	
        // Empty constructor.
    }
}

