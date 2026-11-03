// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/04/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Represents a CSS background position property value.
/// </summary>
class CssBackgroundPositionPropertyValue extends CssPropertyValue
{
    /// <summary>
    /// Ctor. Creates a CSS background position property value.
    /// </summary>
    CssBackgroundPositionPropertyValue(CssValue x, CssValue y)
    {
    	super(new CssValueList(x, y));
	
        Debug.assertTrue(x != null);
        Debug.assertTrue(y != null);
        mX = x;
        mY = y;
    }

    CssValue getX() { return mX; }

    CssValue getY() { return mY; }

    private /*final*/ CssValue mX;
    private /*final*/ CssValue mY;
}

