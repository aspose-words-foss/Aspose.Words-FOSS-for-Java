// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/02/2025 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents the CSS :active pseudo-class selector.
/// </summary>
/// <remarks>
/// See https://www.w3.org/TR/CSS2/selector.html#dynamic-pseudo-classes
/// </remarks>
class CssActiveSelector extends CssPseudoClassSelector
{
    /*override*/ boolean selects(IElementProvider element, /*CssDocumentMode*/int documentMode)
    {
        // Our HTML model don't support dynamic states.
        return false;
    }

    /*override*/ String toCss()
    {
        return ":active";
    }

    protected /*override*/ String makePreferableStyleName()
    {
        return "active";
    }
}

