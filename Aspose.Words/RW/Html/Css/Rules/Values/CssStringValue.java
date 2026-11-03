// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/02/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.CssEscape;


/// <summary>
/// Represents an identifier CSS string value.
/// </summary>
class CssStringValue extends CssValue
{
    CssStringValue(String value)
    {
    	super(CssValueType.STRING, value);
	
        mValue = value;
    }

    /*override*/ void toCss(StringBuilder sb)
    {
        sb.append('\'');
        msStringBuilder.append(sb, CssEscape.escapeSingleQuotedString(mValue));
        sb.append('\'');
    }

    /*new*/ String getValue() { return mValue; }

    private /*final*/ String mValue;
}

