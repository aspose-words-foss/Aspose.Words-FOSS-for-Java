// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/04/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// Represents a CSS font family property value.
/// </summary>
class CssFontFamilyPropertyValue extends CssPropertyValue
{
    /// <summary>
    /// Ctor.
    /// </summary>
    CssFontFamilyPropertyValue(CssValue fontFamily)
    {
    	super(fontFamily);
	
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    CssFontFamilyPropertyValue(/*params*/ CssValue... fontFamilies)
    {
    	super(new CssValueList(fontFamilies));
	
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    CssFontFamilyPropertyValue(CssValueList fontFamilies)
    {
    	super(fontFamilies);
	
    }

    protected /*override*/ void valueToCss(StringBuilder sb)
    {
        final String DELIMITER = ", ";
        for (int i = 0; i < getCount(); i++)
        {
            if (i != 0)
                msStringBuilder.append(sb, DELIMITER);
            this.get(i).toCss(sb);
        }
    }
}

