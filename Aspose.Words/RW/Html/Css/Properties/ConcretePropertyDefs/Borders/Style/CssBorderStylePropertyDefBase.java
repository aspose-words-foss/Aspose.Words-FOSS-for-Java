// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/03/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Base class for 'border-top-style', 'border-right-style', 'border-bottom-style', 
/// 'border-left-style' CSS properties. 
/// </summary>
/// <remarks>CSS border style is applied to a model format with <see cref="CssBorderStyleConverter"/></remarks>
abstract class CssBorderStylePropertyDefBase extends CssIndividualSimplePropertyDef
{
    protected CssBorderStylePropertyDefBase(String name)
    {
    	super(
            name,
            false,
            CssValue.None,
            // none | hidden | dotted | dashed | solid | double | groove | ridge | inset | outset
            CssValueFilter.values(
                CssValue.None,
                CssValue.Hidden,
                CssValue.Dotted,
                CssValue.Dashed,
                CssValue.Solid,
                CssValue.DoubleId,
                CssValue.Groove,
                CssValue.Ridge,
                CssValue.Inset,
                CssValue.Outset));
	
        // Empty constructor.
    }
}

