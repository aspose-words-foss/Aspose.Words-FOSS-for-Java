// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Represents the CSS :nth-last-child() pseudo-class selector. For example, ':nth-last-child(3n+1)'.
/// </summary>
/// <remarks>
/// See http://www.w3.org/TR/css3-selectors/#nth-last-child-pseudo
/// </remarks>
class CssNthLastChildSelector extends CssPseudoClassSelector
{
    CssNthLastChildSelector(CssIndexArgument argument)
    {
        Debug.assertTrue(argument != null);
        mArgument = argument;
    }

    /*override*/ boolean selects(IElementProvider element, /*CssDocumentMode*/int documentMode)
    {
        IElementProvider parent = element.getParentElement();
        if (parent == null)
            return false;

        // The children are examined in reverse order, but are indexed in direct order.
        // The last child will have the index value of zero, its sibling will have the index value of one, and so on.
        IElementProvider[] childElements = parent.getChildElements();
        for (int i = 0; i < childElements.length; i++)
        {
            if (childElements[childElements.length - i - 1] == element)
                return mArgument.matches(i);
        }

        // We should not get here. If the element has a parent, the parent's list of children must contain
        // the element.
        Debug.assertTrue(false);
        return false;
    }

    /*override*/ String toCss()
    {
        return ":nth-last-child(" + mArgument.getText() + ")";
    }

    protected /*override*/ String makePreferableStyleName()
    {
        return "nth-last-child(" + mArgument.getText() + ")";
    }

    private /*final*/ CssIndexArgument mArgument;
}

