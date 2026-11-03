// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/04/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents the CSS :root pseudo-class selector.
/// </summary>
/// <remarks>
/// See http://www.w3.org/TR/css3-selectors/#root-pseudo
/// </remarks>
class CssRootSelector extends CssPseudoClassSelector
{
    /*override*/ boolean selects(IElementProvider element, /*CssDocumentMode*/int documentMode)
    {
        // In HTML documents, the root is always the <html> element.
        return "html".equals(element.getElementName());
    }

    /*override*/ String toCss()
    {
        return ":root";
    }

    protected /*override*/ String makePreferableStyleName()
    {
        return "root";
    }
}

