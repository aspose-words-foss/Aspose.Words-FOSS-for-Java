// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/02/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// Represents a CSS URI value.
/// </summary>
/// <remarks>
/// <para>
/// URI stands for Uniform Resource Identifiers. See [RFC3986], which includes URLs, URNs, etc.
/// </para>
/// <para>
/// The format of a URI value is 'url(' followed by optional white space followed by an optional single
/// quote (') or double quote (") character followed by the URI itself, followed by an optional single
/// quote (') or double quote (") character followed by optional white space followed by ')'.
/// The two quote characters must be the same.
/// </para>
/// </remarks>
class CssUriValue extends CssValue
{
    CssUriValue(String uriValue)
    {
    	super(CssValueType.URI, uriValue);
	
        mValue = uriValue;
    }

    /*override*/ void toCss(StringBuilder sb)
    {
        msStringBuilder.append(sb, "url('");
        msStringBuilder.append(sb, getValue());
        msStringBuilder.append(sb, "')");
    }

    /*new*/ String getValue() { return mValue; };

    private  String mValue;
}

