// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/01/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;

/// <summary>
/// Represents a single CSS declaration (a property and a value). The value is specified (can be context-dependent).
/// </summary>
/// <remarks>
/// See http://www.w3.org/TR/CSS2/cascade.html#specified-value for more information on specified CSS property values.
/// </remarks>
class CssSpecifiedDeclaration extends CssDeclaration
{
    CssSpecifiedDeclaration(String name, CssPropertyValue value, boolean isImportant)
    {
    	super(value, isImportant);
	
        Debug.assertTrue(StringUtil.hasChars(name));
        Debug.assertTrue(msString.equals(name, msString.toLowerInvariant(name)));

        if (!value.isInherit() && !value.isInitial())
        {
            Debug.assertTrue((!"font-family".equals(name)) || (value instanceof CssFontFamilyPropertyValue));
            Debug.assertTrue((!"background-position".equals(name)) || (value instanceof CssBackgroundPositionPropertyValue));
            Debug.assertTrue((!"background".equals(name)) || (value instanceof CssBackgroundPropertyValue));
            Debug.assertTrue((!"font".equals(name)) || (value instanceof CssFontPropertyValue));
            Debug.assertTrue((!"page-size".equals(name)) || (value instanceof CssPageSizePropertyValue));
            Debug.assertTrue((!"text-decoration".equals(name)) || (value instanceof CssTextDecorationPropertyValue));
        }

        mName = name;
    }

    CssSpecifiedDeclaration(String name, CssPropertyValue value)
    {
    	this(name, value, false);
	
    }

    CssSpecifiedDeclaration(String name, CssValue cssValue, boolean isImportant)
    {
    	this(name, new CssPropertyValue(cssValue), isImportant);
	
    }

    CssSpecifiedDeclaration(String name, CssValue cssValue)
    {
    	this(name, cssValue, false);
	
    }

    /*override*/ String getProperty() { return mName; }

    private /*final*/ String mName;
}

