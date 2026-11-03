// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/03/2014 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Represents HTML 'col' element.
/// </summary>
class HtmlCol
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="declarations">Element CSS declarations.</param>
    /// <param name="colGroup">HTML col group.</param>
    HtmlCol(CssDeclarationCollection declarations, HtmlColGroup colGroup)
    {
        Debug.assertTrue(declarations != null);
        Debug.assertTrue(colGroup != null);
        mDeclarations = declarations;
        mColGroup = colGroup;
        mCssBorders = CssBoxBorders.createBorders(declarations, false);
    }

    /// <summary>
    /// Gets CSS declarations of the element.
    /// </summary>
    CssDeclarationCollection getDeclarations() { return mDeclarations; }

    /// <summary>
    /// CSS borders declared in the element.
    /// </summary>
    CssBoxBorders getCssBorders() { return mCssBorders; }

    /// <summary>
    /// HTML col group of the element.
    /// </summary>
    HtmlColGroup getColGroup() { return mColGroup; }

    private /*final*/ CssBoxBorders mCssBorders;
    private /*final*/ CssDeclarationCollection mDeclarations;
    private /*final*/ HtmlColGroup mColGroup;
}

