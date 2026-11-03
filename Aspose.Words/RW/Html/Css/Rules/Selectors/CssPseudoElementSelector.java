// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;

/// <summary>
/// Base class for all CSS pseudo-element selectors.
/// </summary>
class CssPseudoElementSelector extends CssSimpleSelector
{
    CssPseudoElementSelector(/*HtmlElementPart*/int part, String name)
    {
        Debug.assertTrue(part != HtmlElementPart.ELEMENT);
        Debug.assertTrue(StringUtil.hasChars(name));
        Debug.assertTrue(("before".equals(name)) ||
                     ("after".equals(name)) ||
                     ("first-line".equals(name)) ||
                     ("first-letter".equals(name)));

        mPart = part;
        mName = name;
    }

    /*override*/ boolean selects(IElementProvider element, /*CssDocumentMode*/int documentMode)
    {
        // Pseudo-elements always exist. If an element itself is selected, all its pseudo-elements are selected as well.
        return true;
    }

    /*override*/ /*HtmlElementPart*/int getSelectedPart() { return mPart; }

    /*override*/ CssSelectorSpecificity getSpecificity() { return G_SPECIFICITY; }

    /*override*/ String toCss()
    {
        boolean pseudoElementIsDefinedInCss21 =
            (mPart == HtmlElementPart.BEFORE) ||
            (mPart == HtmlElementPart.AFTER) ||
            (mPart == HtmlElementPart.FIRST_LETTER) ||
            (mPart == HtmlElementPart.FIRST_LINE);

        // In CSS 2.1, the 'one-colon' syntax was used for pseudo-elements. For example, ':before' instead of '::before'.
        String prefix = pseudoElementIsDefinedInCss21
            ? ":"
            : "::";

        return prefix + mName;
    }

    /*override*/ boolean selectsPseudoElement()
    {
        return true;
    }

    protected /*override*/ String makePreferableStyleName()
    {
        return mName;
    }

    private /*final*/ /*HtmlElementPart*/int mPart;

    private /*final*/ String mName;

    private static /*final*/ CssSelectorSpecificity G_SPECIFICITY = new CssSelectorSpecificity(0, 0, 1);
}

