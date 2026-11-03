// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/06/2017 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Matches a <see cref="CssSelector"/> against HTML elements during depth-first traversal of an HTML tree.
/// </summary>
abstract class CssSelectorMatcher
{
    protected CssSelectorMatcher(CssSelector selector, /*CssDocumentMode*/int documentMode)
    {
        Debug.assertTrue(selector != null);

        mSelector = selector;
        mDocumentMode = documentMode;
    }

    abstract boolean push(IElementProvider element);

    abstract void pop();

    CssSelector getSelector() { return mSelector; }

    /*CssDocumentMode*/int getDocumentMode() { return mDocumentMode; }

    private /*final*/ CssSelector mSelector;
    private /*final*/ /*CssDocumentMode*/int mDocumentMode;
}

