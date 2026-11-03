// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2017 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements 'opacity' CSS property for SVG.
/// </summary>
class CssOpacityPropertyDef extends CssIndividualSimplePropertyDef
{
    // The documentation says that 'opacity' is not inherited by default, 
    // but this workaround ensures satisfactory results on import.
    CssOpacityPropertyDef()
    {
    	super("opacity", true, new CssNumberValue(1.0), CssValueFilter.NUMBER);
	
        // Nothing to do.
    }
}

