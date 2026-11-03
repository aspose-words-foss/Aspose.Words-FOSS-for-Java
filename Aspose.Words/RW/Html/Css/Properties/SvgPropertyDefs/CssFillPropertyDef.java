// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/10/2017 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements 'fill' CSS property for SVG.
/// </summary>
class CssFillPropertyDef extends CssIndividualSimplePropertyDef
{
    CssFillPropertyDef()
    {
    	super(
              "fill",
              true,
              CssValue.Black,
              // none | currentColor | <color> [<icccolor>] | <funciri> [none | currentColor | <color> [<icccolor>]]
              // Identifiers are included here for named colors (green, black, etc).
              CssValueFilter.anyOf(
                  CssValueFilter.COLOR,
                  CssValueFilter.IDENTIFIER,
                  CssValueFilter.URI));
	
        // Nothing to do.
    }
}

