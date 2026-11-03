// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/04/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a CSS text decoration property value.
/// </summary>
class CssTextDecorationPropertyValue extends CssPropertyValue
{
    CssTextDecorationPropertyValue(CssValue textDecoration)
    {
    	super(textDecoration);
	
    }

    CssTextDecorationPropertyValue(CssValueList values)
    {
    	super(values);
	
    }

    CssTextDecorationPropertyValue(CssPropertyValue value)
    {
    	super(value);
	
    }

    boolean isNone() { return (getCount() == 1) && (getFirstValue().equals(CssValue.None)); }

    boolean isUnderline() { return contains(CssValue.Underline); }

    boolean isOverline() { return contains(CssValue.Overline); }

    boolean isLineThrough() { return contains(CssValue.LineThrough); }

    boolean isBlink() { return contains(CssValue.Blink); }

    /// <summary>
    /// 'none' text decoration.
    /// </summary>
    static /*final*/ CssTextDecorationPropertyValue NONE =
        new CssTextDecorationPropertyValue(CssValue.None);
}

