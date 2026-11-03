// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/10/2017 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements 'stroke-miterlimit' CSS property for SVG.
/// </summary>
class CssStrokeMiterlimitPropertyDef extends CssIndividualSimplePropertyDef
{
    CssStrokeMiterlimitPropertyDef()
    {
    	super(
            "stroke-miterlimit",
            true,
            new CssNumberValue(4.0),
            new StrokeMiterlimitValueFilter());
	
        // Nothing to do.
    }

    private static class StrokeMiterlimitValueFilter implements ICssValueFilter
    {
        public boolean accepts(CssValue value, boolean isInQuirksMode)
        {
            // <miterlimit>
            return (value.getValueType() == CssValueType.NUMBER) &&
               (value.getDoubleValue() >= 1);
        }
    }
}

