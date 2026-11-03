// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/11/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents the CSS :link pseudo-class selector.
/// </summary>
/// <remarks>
/// See http://www.w3.org/TR/CSS2/selector.html#link-pseudo-classes
/// </remarks>
class CssLinkSelector extends CssPseudoClassSelector
{
    /*override*/ boolean selects(IElementProvider element, /*CssDocumentMode*/int documentMode)
    {
        // In HTML4, the link pseudo-classes applies to A elements with an "href" attribute. 
        return ("a".equals(element.getElementName())) && (element.getAttributeValue("href") != null);
    }

    /*override*/ String toCss()
    {
        return ":link";
    }

    protected /*override*/ String makePreferableStyleName()
    {
        return "link";
    }
}

