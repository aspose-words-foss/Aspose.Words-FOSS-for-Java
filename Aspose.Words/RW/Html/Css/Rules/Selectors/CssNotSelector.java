// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Represents the CSS :not() pseudo-class selector. For example, ':not(div)'.
/// </summary>
/// <remarks>
/// See http://www.w3.org/TR/css3-selectors/#negation
/// </remarks>
class CssNotSelector extends CssPseudoClassSelector
{
    CssNotSelector(CssSimpleSelector argument)
    {
        Debug.assertTrue(argument != null);

        mArgument = argument;
    }

    /*override*/ boolean selects(IElementProvider element, /*CssDocumentMode*/int documentMode)
    {
        return !mArgument.selects(element, documentMode);
    }

    /*override*/ CssSelectorSpecificity getSpecificity()
    {
        // The :not() pseudo-class itself is not counted.
        return mArgument.getSpecificity();
    }

    /*override*/ String toCss()
    {
        return ":not(" + mArgument.toCss() + ")";
    }

    protected /*override*/ String makePreferableStyleName()
    {
        return "not(" + mArgument.toCss() + ")";
    }

    private /*final*/ CssSimpleSelector mArgument;
}

