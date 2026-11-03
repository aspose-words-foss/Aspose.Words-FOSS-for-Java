// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/08/2024 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents the CSS :visited pseudo-class selector.
/// </summary>
/// <remarks>
/// See http://www.w3.org/TR/CSS2/selector.html#link-pseudo-classes
/// </remarks>
class CssVisitedSelector extends CssPseudoClassSelector
{
    /*override*/ boolean selects(IElementProvider element, /*CssDocumentMode*/int documentMode)
    {
        // Our HTML model don't support visited hyperlinks.
        return false;
    }

    /*override*/ String toCss()
    {
        return ":visited";
    }

    protected /*override*/ String makePreferableStyleName()
    {
        return "visited";
    }
}

