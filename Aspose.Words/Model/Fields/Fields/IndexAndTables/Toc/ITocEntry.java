// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/03/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// When implemented, represents an object that may participate in building a TOC (a paragraph with a special style
/// or outline level or a TC field).
/// </summary>
interface ITocEntry
{
    /// <summary>
    /// Inserts a hidden bookmark used to reference the entry from the TOC and mark the contents.
    /// </summary>
    /// <returns>Bookmark's node range.</returns>
    public NodeRange insertBookmark(String bookmarkName) throws Exception;

    /// <summary>
    /// Gets the level of the entry.
    /// </summary>
    public int getLevel();

    /// <summary>
    /// Gets whether the page number should be omitted for this entry.
    /// </summary>
    public 
    boolean getOmitPageNumber() throws Exception;

    /// <summary>
    /// Gets <see cref="Paragraph"/> associated with the entry.
    /// </summary>
    /// <returns></returns>
    public 
    Paragraph getParagraph() throws Exception;

    /// <summary>
    /// Returns title of the entry.
    /// </summary>
    /// <returns></returns>
    public String getDocumentOutlineTitle() throws Exception;

    /// <summary>
    /// Returns <see cref="NodeRange"/> associated with the entry label.
    /// </summary>
    public NodeRange getLabelRange() throws Exception;

    /// <summary>
    /// Gets whether the entry is in field code.
    /// </summary>
    public 
    boolean isInFieldCode() throws Exception;

    /// <summary>
    /// Gets whether the entry supports bookmark which can be referenced by
    /// <see cref="FieldHyperlink"/>, <see cref="FieldPageRef"/> and <see cref="FieldSeq"/> fields.
    /// </summary>
    public boolean hasBookmark();

    /// <summary>
    /// Gets whether the entry is populated from linked character style.
    /// </summary>
    public boolean isLinkedStyleTocEntry();

    /// <summary>
    /// Returns static sequence value of the entry to be used instead of the <see cref="FieldSeq"/> field.
    /// </summary>
    public int getSequenceValue(String sequenceIdentifier) throws Exception;

    /// <summary>
    /// Returns static page number of the entry to be used instead of the <see cref="FieldPageRef"/> field.
    /// </summary>
    public int getPageNumber() throws Exception;
}

