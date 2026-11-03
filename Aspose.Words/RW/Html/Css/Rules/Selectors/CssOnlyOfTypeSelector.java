// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;

/// <summary>
/// Represents the CSS :only-of-type pseudo-class selector.
/// </summary>
/// <remarks>
/// See http://www.w3.org/TR/css3-selectors/#only-of-type-pseudo
/// </remarks>
class CssOnlyOfTypeSelector extends CssPseudoClassSelector
{
    /*override*/ boolean selects(IElementProvider element, /*CssDocumentMode*/int documentMode)
    {
        IElementProvider parent = element.getParentElement();
        if (parent == null)
        {
            return false;
        }


        IElementProvider sibling = element.getPreviousSiblingElement();
        while (sibling != null)
        {
            if ((msString.equals(sibling.getElementName(), element.getElementName())) && (msString.equals(sibling.getElementNamespace(), element.getElementNamespace())))
                return false;
            sibling = sibling.getPreviousSiblingElement();
        }

        sibling = element.getNextSiblingElement();
        while (sibling != null)
        {
            if ((msString.equals(sibling.getElementName(), element.getElementName())) && (msString.equals(sibling.getElementNamespace(), element.getElementNamespace())))
                return false;
            sibling = sibling.getNextSiblingElement();
        }

        // The parent contains the current element, and there are no other child elements with the same name and namespace.
        return true;
    }

    /*override*/ String toCss()
    {
        return ":only-of-type";
    }

    protected /*override*/ String makePreferableStyleName()
    {
        return "only-of-type";
    }
}

