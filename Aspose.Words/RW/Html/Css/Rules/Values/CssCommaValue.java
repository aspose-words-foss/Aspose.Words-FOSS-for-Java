// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/04/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents a comma (,) value.
/// </summary>
class CssCommaValue extends CssValue
{
    CssCommaValue()
    {
    	super(CssValueType.COMMA, COMMA_CHAR);
	
        // Empty constructor.
    }

    /*override*/ void toCss(StringBuilder sb)
    {
        sb.append(COMMA_CHAR);
    }

    private static final char COMMA_CHAR = ',';
}

