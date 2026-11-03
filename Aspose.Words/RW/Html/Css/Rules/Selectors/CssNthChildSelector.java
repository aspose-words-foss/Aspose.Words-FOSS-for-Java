// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Represents the CSS :nth-child() pseudo-class selector. For example, ':nth-child(3n+1)'.
/// </summary>
/// <remarks>
/// See http://www.w3.org/TR/css3-selectors/#nth-child-pseudo
/// </remarks>
class CssNthChildSelector extends CssPseudoClassSelector
{
    CssNthChildSelector(CssIndexArgument argument)
    {
        Debug.assertTrue(argument != null);
        mArgument = argument;
    }

    /*override*/ boolean selects(IElementProvider element, /*CssDocumentMode*/int documentMode)
    {
        IElementProvider parent = element.getParentElement();
        if (parent == null)
            return false;

        IElementProvider[] childElements = parent.getChildElements();
        for (int i = 0; i < childElements.length; i++)
        {
            if (childElements[i] == element)
                return mArgument.matches(i);
        }

        // We should not get here. If the element has a parent, the parent's list of children must contain the element.
        Debug.assertTrue(false);
        return false;
    }

    /*override*/ String toCss()
    {
        return ":nth-child(" + mArgument.getText() + ")";
    }

    protected /*override*/ String makePreferableStyleName()
    {
        return "nth-child(" + mArgument.getText() + ")";
    }

    private /*final*/ CssIndexArgument mArgument;
}

