// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.msString;

/// <summary>
/// Represents the CSS :nth-last-of-type() pseudo-class selector. For example, ':nth-last-of-type(3n+1)'.
/// </summary>
/// <remarks>
/// See http://www.w3.org/TR/css3-selectors/#nth-last-of-type-pseudo
/// </remarks>
class CssNthLastOfTypeSelector extends CssPseudoClassSelector
{
    CssNthLastOfTypeSelector(CssIndexArgument argument)
    {
        Debug.assertTrue(argument != null);
        mArgument = argument;
    }

    /*override*/ boolean selects(IElementProvider element, /*CssDocumentMode*/int documentMode)
    {
        IElementProvider parent = element.getParentElement();
        if (parent == null)
            return false;

        // Determine and check the index of this element among the children that have the same name and namespace.
        // The children are examined in reverse order, but are indexed in direct order.
        // The last child will have the index value of zero, its sibling will have the index value of one, and so on.
        int index = 0;
        IElementProvider[] childElements = parent.getChildElements();
        for (int i = 0; i < childElements.length; i++)
        {
            IElementProvider child = childElements[childElements.length - i - 1];
            if (child == element)
                return mArgument.matches(index);

            if ((msString.equals(child.getElementName(), element.getElementName())) && (msString.equals(child.getElementNamespace(), element.getElementNamespace())))
                ++index;
        }

        // We should not get here. If the element has a parent, the parent's list of children must contain
        // the element.
        Debug.assertTrue(false);
        return false;
    }

    /*override*/ String toCss()
    {
        return ":nth-last-of-type(" + mArgument.getText() + ")";
    }

    protected /*override*/ String makePreferableStyleName()
    {
        return "nth-last-of-type(" + mArgument.getText() + ")";
    }

    private /*final*/ CssIndexArgument mArgument;
}

