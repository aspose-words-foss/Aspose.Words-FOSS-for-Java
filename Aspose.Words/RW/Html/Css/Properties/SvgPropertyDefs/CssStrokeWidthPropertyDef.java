// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/10/2017 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements 'stroke-width' CSS property for SVG.
/// </summary>
class CssStrokeWidthPropertyDef extends CssIndividualSimplePropertyDef
{
    CssStrokeWidthPropertyDef()
    {
    	super(
              "stroke-width",
              true,
              new CssNumberValue(1.0),
              // <percentage> | <length>
              // Note that this is a SVG property so it also accepts unitless lengths (numbers).
              CssValueFilter.anyOf(
                  CssValueFilter.PERCENTAGE,
                  CssValueFilter.NUMBER,
                  CssValueFilter.LENGTH));
	
        // Nothing to do.
    }
}

