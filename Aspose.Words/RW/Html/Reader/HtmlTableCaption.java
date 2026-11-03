// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/02/2014 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Represents a table caption during import of an HTML table.
/// </summary>
class HtmlTableCaption
{
    /// <summary>
    /// Creates a caption from a 'caption' node.
    /// </summary>
    HtmlTableCaption(HtmlElementNode node, CssDeclarationCollection captionDeclarations)
    {
        Debug.assertTrue(node != null);
        Debug.assertTrue(captionDeclarations != null);
        mNode = node;
        CssDeclaration captionSideDeclaration = captionDeclarations.get("caption-side");
        mIsTopCaption = (captionSideDeclaration == null) || (!captionSideDeclaration.getValue().equals(CssValue.Bottom));
    }

    /// <summary>
    /// Returns the HTML 'caption' node that this caption represents.
    /// </summary>
    HtmlElementNode getNode() { return mNode; }

    boolean isTopCaption() { return mIsTopCaption; }

    private /*final*/ HtmlElementNode mNode;
    private /*final*/ boolean mIsTopCaption;
}

