// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/04/2025 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents base class for individual 'mso-para-margin-xxx' CSS properties.
/// </summary>
abstract class CssMsoParaMarginIndividualPropertyDefBase extends CssIndividualSimplePropertyDef
{
    protected CssMsoParaMarginIndividualPropertyDefBase(String name)
    {
    	super(
            name,
            true,
            CssValue.Zero,
            CssValueFilter.anyOf(
                CssValueFilter.value(CssValue.Auto),
                CssValueFilter.PERCENTAGE,
                CssValueFilter.QUIRKY_LENGTH));
	
        // Empty constructor.
    }
}

