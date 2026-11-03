// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/03/2014 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Represents HTML 'colgroup' element.
/// </summary>
class HtmlColGroup
{
    /// <summary>
    /// Ctor.
    /// </summary>
    HtmlColGroup()
    {
        mCssBorders = CssBoxBorders.createEmpty();
        mDeclarations = CssDeclarationCollection.EMPTY;
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="declarations">Element CSS declarations.</param>
    HtmlColGroup(CssDeclarationCollection declarations)
    {
        Debug.assertTrue(declarations != null);
        mDeclarations = declarations;
        mCssBorders = CssBoxBorders.createBorders(declarations, false);
    }

    /// <summary>
    /// CSS borders declared in the element.
    /// </summary>
    CssBoxBorders getCssBorders() { return mCssBorders; }

    /// <summary>
    /// Gets CSS declarations of the element.
    /// </summary>
    CssDeclarationCollection getDeclarations() { return mDeclarations; }

    private /*final*/ CssBoxBorders mCssBorders;
    private /*final*/ CssDeclarationCollection mDeclarations;
}

