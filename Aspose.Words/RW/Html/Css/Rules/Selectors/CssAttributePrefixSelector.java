// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.ms.System.msString;

/// <summary>
/// Represents the CSS 'attribute value prefix' selector. For example, '[lang^=en]'.
/// </summary>
/// <remarks>
/// The selector '[att^=val]' selects an element with the "att" attribute whose value begins with the prefix "val".
/// If "val" is the empty string then the selector does not select anything.
/// For details see http://www.w3.org/TR/css3-selectors/#attribute-substrings
/// </remarks>
class CssAttributePrefixSelector extends CssAttributeSelector
{
    CssAttributePrefixSelector(CssNamespace attributeNamespace, String attributeLocalName, String value)
    {
    	super(attributeNamespace, attributeLocalName, "^=", value);
	
        // Empty constructor. Everything is set up by the base class.
    }

    /*override*/ boolean selects(IElementProvider element, /*CssDocumentMode*/int documentMode)
    {
        // HTML has no means to specify namespaces of attributes, and none of HTML attribute has a namespace.
        if (getAttributeNamespace().isSpecific() && !getAttributeNamespace().isEmpty())
        {
            return false;
        }

        if (getValue().length() == 0)
        {
            return false;
        }

        String actualValue = element.getAttributeValue(getAttributeLocalName());
        if ((actualValue == null) || (actualValue.length() < getValue().length()))
        {
            return false;
        }

        String attributePrefix = actualValue.substring((0), (0) + (getValue().length()));
        if (attributeValueIsCaseInsensitive(getAttributeLocalName()))
        {
            return StringUtil.equalsIgnoreCase(attributePrefix, getValue());
        }
        else
        {
            // Ordinal (case-sensitive) comparison.
            return msString.equals(attributePrefix, getValue());
        }
    }
}

