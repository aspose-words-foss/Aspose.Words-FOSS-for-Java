// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.ms.System.msString;

/// <summary>
/// Represents the CSS 'attribute value suffix' selector. For example, '[lang$=en]'.
/// </summary>
/// <remarks>
/// The selector '[att$=val]' selects an element with the "att" attribute whose value ends with the suffix "val".
/// If "val" is the empty string then the selector does not represent anything.
/// For details see http://www.w3.org/TR/css3-selectors/#attribute-substrings
/// </remarks>
class CssAttributeSuffixSelector extends CssAttributeSelector
{
    CssAttributeSuffixSelector(CssNamespace attributeNamespace, String attributeLocalName, String value)
    {
    	super(attributeNamespace, attributeLocalName, "$=", value);
	
        // Empty constructor. Everything is set up by the base class.
    }

    /*override*/ boolean selects(IElementProvider element, /*CssDocumentMode*/int documentMode)
    {
        // HTML has no means to specify namespaces of attributes, and all of HTML attribute have empty namespace.
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

        String attributeSuffix = actualValue.substring((actualValue.length() - getValue().length()), (actualValue.length() - getValue().length()) + (getValue().length()));
        if (attributeValueIsCaseInsensitive(getAttributeLocalName()))
        {
            return StringUtil.equalsIgnoreCase(attributeSuffix, getValue());
        }
        else
        {
            // Ordinal (case-sensitive) comparison.
            return msString.equals(attributeSuffix, getValue());
        }
    }
}

