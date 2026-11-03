// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/05/2016 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Represents 'list-style' shorthand CSS property value.
/// </summary>
class CssListStylePropertyValue extends CssPropertyValue
{
    CssListStylePropertyValue(CssValue image, CssValue type, CssValue position)
    {
    	super(createValues(image, type, position));
	
    }

    private static CssValueList createValues(CssValue image, CssValue type, CssValue position)
    {
        CssValueList values = new CssValueList();
        if (image != null)
            values.add(image);
        if (type != null)
            values.add(type);
        if (position != null)
            values.add(position);

        Debug.assertTrue(values.getCount() != 0);
        return values;
    }
}
