// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/04/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents a U+002F SOLIDUS (/) value.
/// </summary>
class CssSolidusValue extends CssValue
{
    CssSolidusValue()
    {
    	super(CssValueType.SOLIDUS, SOLIDUS_CHAR);
	
        // Empty constructor.
    }

    /*override*/ void toCss(StringBuilder sb)
    {
        sb.append(SOLIDUS_CHAR);
    }

    private static final char SOLIDUS_CHAR = '/';
}

