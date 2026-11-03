// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/10/2017 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements 'stroke' CSS property for SVG.
/// </summary>
class CssStrokePropertyDef extends CssIndividualSimplePropertyDef
{
    CssStrokePropertyDef()
    {
    	super(
            "stroke",
            true,
            CssValue.None,
            // none | currentColor | <color> [<icccolor>] | <funciri> [none | currentColor | <color> [<icccolor>]]
            // Identifiers are included here for named colors (green, black, etc) and they also cover
            // the "none" and "currentColor" values.
            CssValueFilter.anyOf(
                CssValueFilter.IDENTIFIER,
                CssValueFilter.COLOR,
                CssValueFilter.URI));
	
        // Nothing to do.
    }
}

