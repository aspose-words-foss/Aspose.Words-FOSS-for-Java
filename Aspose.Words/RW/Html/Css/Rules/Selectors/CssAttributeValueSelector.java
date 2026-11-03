// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.ms.System.msString;

/// <summary>
/// Represents the CSS 'attribute value' selector. For example, '[lang=en]'.
/// </summary>
/// <remarks>
/// The selector '[att=val]' selects an element with the "att" attribute whose value is exactly "val".
/// For details see http://www.w3.org/TR/css3-selectors/#attribute-representation
/// </remarks>
class CssAttributeValueSelector extends CssAttributeSelector
{
    CssAttributeValueSelector(CssNamespace attributeNamespace, String attributeLocalName, String value)
    {
    	super(attributeNamespace, attributeLocalName, "=", value);
	
        // Empty constructor. Everything is set up by the base class.
    }

    /*override*/ boolean selects(IElementProvider element, /*CssDocumentMode*/int documentMode)
    {
        // HTML has no means to specify namespaces of attributes, and none of HTML attribute has a namespace.
        if (getAttributeNamespace().isSpecific() && !getAttributeNamespace().isEmpty())
        {
            return false;
        }

        String attributeValue = element.getAttributeValue(getAttributeLocalName());
        if (attributeValue == null)
        {
            return false;
        }
        else
        {
            if (attributeValueIsCaseInsensitive(getAttributeLocalName()))
            {
                return StringUtil.equalsIgnoreCase(getValue(), attributeValue);
            }
            else
            {
                // Case-sensitive ordinal comparison.
                return msString.equals(getValue(), attributeValue);
            }
        }
    }
}

