// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/03/2014 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Represents HTML row group element ('thead', 'tbody' or 'tfoot').
/// </summary>
class HtmlRowGroup
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="node"></param>
    /// <param name="declarations">Element CSS declarations.</param>
    HtmlRowGroup(HtmlElementNode node, CssDeclarationCollection declarations)
    {
        Debug.assertTrue(node != null);
        Debug.assertTrue(declarations != null);
        mNode = node;
        mCssBorders = CssBoxBorders.createBorders(declarations, false);
        mDeclarations = declarations;
    }

    HtmlElementNode getNode() { return mNode; }

    /// <summary>
    /// CSS borders declared in the element.
    /// </summary>
    CssBoxBorders getCssBorders() { return mCssBorders; }

    boolean isThead() { return "thead".equals(mNode.getName()); }

    /// <summary>
    /// CSS declarations of the table row group.
    /// </summary>
    public CssDeclarationCollection getDeclarations() { return mDeclarations; }

    private /*final*/ CssBoxBorders mCssBorders;
    private /*final*/ HtmlElementNode mNode;
    private /*final*/ CssDeclarationCollection mDeclarations;
}

