// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/09/2015 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Represents a background shorthand CSS property value.
/// </summary>
class CssBackgroundPropertyValue extends CssPropertyValue
{
    CssBackgroundPropertyValue(CssValue color, CssValue image, CssValue repeat,
                                        CssValue attachment, CssBackgroundPositionPropertyValue position)
    {
    	super(createValues(color, image, repeat, attachment, position));
	
    }

    CssBackgroundPropertyValue(CssValueList linearGradientValues)
    {
    	super(new CssFunctionValue("linear-gradient", linearGradientValues));
	
        Debug.assertTrue(linearGradientValues != null);
    }

    private static CssValueList createValues(CssValue color, CssValue image, CssValue repeat,
        CssValue attachment, CssBackgroundPositionPropertyValue position)
    {
        CssValueList values = new CssValueList();
        if (color != null)
            values.add(color);
        if (image != null)
            values.add(image);
        if (repeat != null)
            values.add(repeat);
        if (attachment != null)
            values.add(attachment);
        if (position != null)
        {
            values.add(position.getX());
            values.add(position.getY());
        }

        Debug.assertTrue(values.getCount() != 0);
        return values;
    }
}
