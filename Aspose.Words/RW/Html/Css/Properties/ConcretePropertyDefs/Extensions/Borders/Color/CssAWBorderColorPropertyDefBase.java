// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/09/2025 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Base class for '-aw-border-top-color', '-aw-border-right-color', '-aw-border-bottom-color', 
/// '-aw-border-left-color' CSS properties. 
/// </summary>
/// <remarks>CSS border color is applied to a model format with <see cref="CssBorderStyleConverter"/></remarks>
abstract class CssAWBorderColorPropertyDefBase extends CssIndividualSimplePropertyDef
{
    // This property has no default value. This guarantees that all values written to this property by AW will always
    // be considered non-default and will never be dropped during inline style resolution.
    protected CssAWBorderColorPropertyDefBase(String name)
    {
    	super(
            name,
            false,
            null,
            // <color>
            CssValueFilter.COLOR);
	
        // Empty constructor.
    }
}

