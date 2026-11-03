// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents the CSS :only-child pseudo-class selector.
/// </summary>
/// <remarks>
/// See http://www.w3.org/TR/css3-selectors/#only-child-pseudo
/// </remarks>
class CssOnlyChildSelector extends CssPseudoClassSelector
{
    /*override*/ boolean selects(IElementProvider element, /*CssDocumentMode*/int documentMode)
    {
        IElementProvider parent = element.getParentElement();
        if (parent == null)
            return false;

        // If the element's parent has only one child, this child must be this element.
        IElementProvider firstChild = parent.getFirstChildElement();
        return (firstChild != null) && (firstChild.getNextSiblingElement() == null);
    }

    /*override*/ String toCss()
    {
        return ":only-child";
    }

    protected /*override*/ String makePreferableStyleName()
    {
        return "only-child";
    }
}

