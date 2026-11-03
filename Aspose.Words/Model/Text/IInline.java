// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/08/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implemented by nodes that represent inline content
/// (nodes that occur inside paragraphs and basically exclude bookmarks).
/// </summary>
interface IInline extends IRunAttrSource
{
    /// <summary>
    /// Gets the immediate parent paragraph.
    /// Can be null if the node is not inserted into the document.
    /// </summary>
    public Paragraph getParentParagraph_IInline();

    /// <summary>
    /// Gets the owner document. Cannot be null.
    /// </summary>
    public DocumentBase getDocument_IInline();

    /// <summary>
    /// These methods have such ugly names to avoid name clashes during autoporting to Java.
    /// </summary>
    public RunPr getExpandedRunPr_IInline(/*RunPrExpandFlags*/int flags);

    /// <summary>
    /// These methods have such ugly names to avoid name clashes during autoporting to Java.
    /// </summary>
    public RunPr getRunPr_IInline(); public void setRunPr_IInline(RunPr value);
}

