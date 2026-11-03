// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/02/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.CssEscape;
import com.aspose.DrColor;


/// <summary>
/// Represents an identifier CSS property value.
/// </summary>
class CssIdentifierValue extends CssValue
{
    CssIdentifierValue(String identifier)
    {
    	super(CssValueType.IDENTIFIER, identifier);
	
        Debug.assertTrue(StringUtil.hasChars(identifier));
        mIdentifier = identifier;
        mLowerCaseIdentifier = msString.toLowerInvariant(identifier);
    }

    /*override*/ void toCss(StringBuilder sb)
    {
        msStringBuilder.append(sb, CssEscape.escapeIdentifier(mIdentifier));
    }

    /*override*/ DrColor parseAsColor()
    {
        return CssColorParser.parseColorName(mIdentifier);
    }

    protected /*override*/ boolean doEquals(CssValue other)
    {
        // This behaviour increases performance by ~2%.
        return msString.equals(mLowerCaseIdentifier, ((CssIdentifierValue)other).mLowerCaseIdentifier);
    }

    public /*override*/ int hashCode()
    {
        /*unchecked*/
        {
            int result = mLowerCaseIdentifier.hashCode();
            //Java-changed: casting enum to int is analog of GetHashCode().
            result = (result * 397) ^ (int)getValueType();
            return result;
        }
    }

    /*new*/ String getValue() { return mIdentifier; }

    private /*final*/ String mIdentifier;
    /// <summary>
    /// Lower case identifier for performance optimization.
    /// </summary>
    private /*final*/ String mLowerCaseIdentifier;
}

