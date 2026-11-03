// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/06/2020 by Artem Shabarshin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Base class for '-aw-border-top-width', '-aw-border-right-width', '-aw-border-bottom-width', 
/// '-aw-border-left-width' CSS extended border properties. 
/// </summary>
/// <remarks>
/// CSS border extension width is applied to a model format with <see cref="CssBorderStyleConverter"/>
/// </remarks>
abstract class CssAWBorderWidthPropertyDefBase extends CssIndividualSimplePropertyDef
{
    // This property has no default value. This guarantees that all values written to this property by AW will always
    // be considered non-default and will never be dropped during inline style resolution.
    protected CssAWBorderWidthPropertyDefBase(String name)
    {
    	super(
            name,
            false,
            null,
            // <length>
            CssValueFilter.NON_NEGATIVE_LENGTH);
	
        // Empty constructor. Everything is set up by the base class.
    }
}

