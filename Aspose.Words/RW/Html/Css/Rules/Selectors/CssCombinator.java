// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/10/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Represents a combinator part of a CSS selector (the combinator operation and the right-hand selector).
/// Base class for concrete CSS combinators.
/// </summary>
abstract class CssCombinator
{
    protected CssCombinator(CssCombinableSelector selector)
    {
        Debug.assertTrue(selector != null);

        mSelector = selector;
    }

    /// <summary>
    /// Gets the CSS declaration of the selector.
    /// </summary>
    abstract String toCss();

    abstract String getPreferableStyleName();

    CssCombinableSelector getSelector() { return mSelector; }

    /// <summary>
    /// Returns a value indicating whether this combinator is supported by MS Word in HTML documents.
    /// </summary>
    /*virtual*/ boolean isSupportedByMsWord() { return false; }

    private /*final*/ CssCombinableSelector mSelector;
}

