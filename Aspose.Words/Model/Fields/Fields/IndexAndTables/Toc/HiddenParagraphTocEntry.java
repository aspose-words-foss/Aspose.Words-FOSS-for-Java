// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/03/2017 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// A TOC entry that is represented by a paragraph with list label whithin field code.
/// </summary>
class HiddenParagraphTocEntry implements ITocEntry
{
    HiddenParagraphTocEntry(Paragraph paragraph, int level)
    {
        Debug.assertTrue(paragraph.isListItemFinal());
        mParagraph = paragraph;
        mLevel = level;
    }

    public NodeRange /*ITocEntry.*/insertBookmark(String bookmarkName)
    {
        BookmarkStart bookmarkStart = new BookmarkStart(getDocument(), bookmarkName);
        BookmarkEnd bookmarkEnd = new BookmarkEnd(getDocument(), bookmarkName);

        mParagraph.appendChild(bookmarkStart);
        mParagraph.appendChild(bookmarkEnd);

        return NodeRange.VOID;
    }

    public int /*ITocEntry.*/getLevel() { return mLevel; }

    public boolean /*ITocEntry.*/getOmitPageNumber() { return false; }

    public Paragraph /*ITocEntry.*/getParagraph() { return mParagraph; }

    public String /*ITocEntry.*/getDocumentOutlineTitle()
    {
        return getParagraphListLabel();
    }

    public NodeRange /*ITocEntry.*/getLabelRange()
    {
        Run run = new Run(getDocument(), getParagraphListLabel());
        return new NodeRange(run, run);
    }

    private String getParagraphListLabel()
    {
        int listId = (/*int*/Integer)((IParaAttrSource)mParagraph).fetchParaAttr(ParaAttr.LIST_ID);
        int listLevel = (/*int*/Integer)((IParaAttrSource)mParagraph).fetchParaAttr(ParaAttr.LIST_LEVEL);

        List list = mParagraph.getDocument().getLists().fetchListByListId(listId);

        ListNumberState numberState = new ListNumberState(list);
        numberState.nextItem(list, listLevel);

        return ListLabelUtil.buildFullListLabel(numberState, null, null);
    }

    public boolean /*ITocEntry.*/isInFieldCode() { return true; }

    public boolean /*ITocEntry.*/hasBookmark() { return true; }

    public boolean /*ITocEntry.*/isLinkedStyleTocEntry() { return false; }

    public int /*ITocEntry.*/getSequenceValue(String sequenceIdentifier)
    {
        throw new IllegalStateException();
    }

    public int /*ITocEntry.*/getPageNumber()
    {
        throw new IllegalStateException();
    }

    private Document getDocument() { return mParagraph.fetchDocument(); }

    private /*final*/ Paragraph mParagraph;
    private /*final*/ int mLevel;
}

