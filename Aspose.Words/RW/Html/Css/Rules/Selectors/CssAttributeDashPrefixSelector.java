// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.ms.System.msString;

/// <summary>
/// Represents the CSS 'attribute dash match' selector. For example, '[lang|=en]'.
/// </summary>
/// <remarks>
/// The selector '[att|=val]' selects an element with the "att" attribute, its value either being exactly "val" 
/// or beginning with "val" immediately followed by "-" (U+002D).
/// For details see http://www.w3.org/TR/css3-selectors/#attribute-representation
/// </remarks>
class CssAttributeDashPrefixSelector extends CssAttributeSelector
{
    CssAttributeDashPrefixSelector(CssNamespace attributeNamespace, String attributeLocalName, String value)
    {
    	super(attributeNamespace, attributeLocalName, "|=", value);
	
        // Empty constructor. Everything is set up by the base class.
    }

    /*override*/ boolean selects(IElementProvider element, /*CssDocumentMode*/int documentMode)
    {
        // HTML has no means to specify namespaces of attributes, and none of HTML attribute has a namespace.
        if (getAttributeNamespace().isSpecific() && !getAttributeNamespace().isEmpty())
        {
            return false;
        }

        String actualValue = element.getAttributeValue(getAttributeLocalName());
        if (actualValue == null)
        {
            return false;
        }

        if (attributeValueIsCaseInsensitive(getAttributeLocalName()))
        {
            if (StringUtil.equalsIgnoreCase(actualValue, getValue()))
            {
                return true;
            }
        }
        else
        {
            // Ordinal (case-sensitive) comparison.
            if (msString.equals(actualValue, getValue()))
            {
                return true;
            }
        }

        String prefix = getValue() + '-';
        if (actualValue.length() < prefix.length())
        {
            return false;
        }

        String attributePrefix = actualValue.substring((0), (0) + (prefix.length()));
        if (attributeValueIsCaseInsensitive(getAttributeLocalName()))
        {
            return StringUtil.equalsIgnoreCase(attributePrefix, prefix);
        }
        else
        {
            // Ordinal (case-sensitive) comparison.
            return msString.equals(attributePrefix, prefix);
        }
    }
}

