// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/01/2018 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a CSS dash array property value.
/// </summary>
class CssDashArrayPropertyValue extends CssPropertyValue
{
    CssDashArrayPropertyValue(CssValue value)
    {
    	super(value);
	
        // Empty constructor.
    }

    CssDashArrayPropertyValue(CssValueList values)
    {
    	super(values);
	
        // Empty constructor.
    }

    CssDashArrayPropertyValue(CssPropertyValue value)
    {
    	super(value);
	
        // Empty constructor.
    }

    /// <summary>
    /// 'none' dash array.
    /// </summary>
    static /*final*/ CssDashArrayPropertyValue NONE = new CssDashArrayPropertyValue(CssValue.None);
}

