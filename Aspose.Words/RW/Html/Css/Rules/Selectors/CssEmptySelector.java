// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/04/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents the CSS :empty pseudo-class selector.
/// </summary>
/// <remarks>
/// See http://www.w3.org/TR/css3-selectors/#empty-pseudo
/// </remarks>
class CssEmptySelector extends CssPseudoClassSelector
{
    /*override*/ boolean selects(IElementProvider element, /*CssDocumentMode*/int documentMode)
    {
        // Check that the element contains neither other elements nor text nodes.
        return (element.getFirstChildElement() == null) && (element.getInnerText().length() == 0);
    }

    /*override*/ String toCss()
    {
        return ":empty";
    }

    protected /*override*/ String makePreferableStyleName()
    {
        return "empty";
    }
}

