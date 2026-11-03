// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents the CSS 'attribute name' selector. For example, '[style]'.
/// </summary>
/// <remarks>
/// The selector '[att]' selects an element with the "att" attribute, whatever the value of the attribute.
/// For details see http://www.w3.org/TR/css3-selectors/#attribute-representation
/// </remarks>
class CssAttributeNameSelector extends CssAttributeSelector
{
    CssAttributeNameSelector(CssNamespace attributeNamespace, String attributeLocalName)
    {
    	super(attributeNamespace, attributeLocalName, null, null);
	
        // Empty constructor. Everything is set up by the base class.
    }

    /*override*/ boolean selects(IElementProvider element, /*CssDocumentMode*/int documentMode)
    {
        // HTML has no means to specify namespaces of attributes, and none of HTML attribute has a namespace.
        if (getAttributeNamespace().isSpecific() && !getAttributeNamespace().isEmpty())
        {
            return false;
        }

        return element.getAttributeValue(getAttributeLocalName()) != null;
    }
}

