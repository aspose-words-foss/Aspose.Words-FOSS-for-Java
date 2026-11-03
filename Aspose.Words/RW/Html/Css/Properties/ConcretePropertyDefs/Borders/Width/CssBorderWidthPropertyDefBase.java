// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/03/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Base class for 'border-top-width', 'border-right-width', 'border-bottom-width', 
/// 'border-left-width' CSS properties. 
/// </summary>
/// <remarks>CSS border width is applied to a model format with <see cref="CssBorderStyleConverter"/></remarks>
abstract class CssBorderWidthPropertyDefBase extends CssIndividualSimplePropertyDef
{
    protected CssBorderWidthPropertyDefBase(String name)
    {
    	super(
            name,
            false,
            CssValue.Medium,
            // thin | medium | thick | <length>
            CssValueFilter.anyOf(
                CssValueFilter.values(
                    CssValue.Thin,
                    CssValue.Medium,
                    CssValue.Thick),
                CssValueFilter.NON_NEGATIVE_QUIRKY_LENGTH));
	
        // Empty constructor.
    }
}

