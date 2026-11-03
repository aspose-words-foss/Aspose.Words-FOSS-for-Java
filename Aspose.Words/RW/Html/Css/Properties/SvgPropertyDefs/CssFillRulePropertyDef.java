// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/04/2020 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements the 'fill-rule' CSS property for SVG.
/// </summary>
class CssFillRulePropertyDef extends CssIndividualSimplePropertyDef
{
    CssFillRulePropertyDef()
    {
    	super(
              "fill-rule",
              true,
              CssValue.Nonzero,
              // nonzero | evenodd
              CssValueFilter.values(CssValue.Evenodd, CssValue.Nonzero));
	
        // Empty constructor.
    }
}

