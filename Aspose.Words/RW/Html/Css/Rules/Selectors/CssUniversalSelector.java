// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/01/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Represents the CSS universal selector (asterisk, *).
/// </summary>
/// <remarks>
/// See http://www.w3.org/TR/css3-selectors/#universal-selector
/// </remarks>
class CssUniversalSelector extends CssSimpleSelector
{
    CssUniversalSelector(CssNamespace ns)
    {
        Debug.assertTrue(ns != null);

        mNamespace = ns;
    }

    /*override*/ boolean selects(IElementProvider element, /*CssDocumentMode*/int documentMode)
    {
        return mNamespace.matches(element.getElementNamespace());
    }

    /*override*/ CssSelectorSpecificity getSpecificity() { return G_SPECIFICITY; }

    /*override*/ String toCss()
    {
        return mNamespace.getPrefixedName("*", CssNamespace.ANY.getName());
    }

    protected /*override*/ String makePreferableStyleName()
    {
        return "any";
    }

    private /*final*/ CssNamespace mNamespace;

    private static /*final*/ CssSelectorSpecificity G_SPECIFICITY = new CssSelectorSpecificity(0, 0, 0);
}

