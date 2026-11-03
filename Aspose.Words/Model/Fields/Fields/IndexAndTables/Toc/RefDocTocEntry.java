// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/06/2021 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Lazy;
import com.aspose.RandomUtil;
import com.aspose.NullableInt32;


/// <summary>
/// A TOC entry that is represented by another TOC entry in the referenced document specified in the <see cref="FieldRD"/> field.
/// </summary>
class RefDocTocEntry implements ITocEntry
{
    RefDocTocEntry(ITocEntry entry, Document document, Lazy<FieldSeqValueEvaluator> fieldSeqValueEvaluator) throws Exception
    {
        mDocument = document;
        mRefTocEntry = entry;
        mFieldSeqValueEvaluator = fieldSeqValueEvaluator;

        mBookmarkName = "_" + RandomUtil.newGuid().toString("N");
        mEntryRange = getRefTocEntry().insertBookmark(mBookmarkName);
    }

    /// <summary>
    /// Gets the referenced document.
    /// </summary>
    Document getDocument() { return mDocument; };

    private Document mDocument;

    /// <summary>
    /// Gets the referenced TOC entry.
    /// </summary>
    ITocEntry getRefTocEntry() { return mRefTocEntry; };

    private ITocEntry mRefTocEntry;

    public NodeRange /*ITocEntry.*/insertBookmark(String bookmarkName)
    {
        return mEntryRange;
    }

    public int /*ITocEntry.*/getLevel() { return getRefTocEntry().getLevel(); }

    public boolean /*ITocEntry.*/getOmitPageNumber() throws Exception { return getRefTocEntry().getOmitPageNumber(); }

    public Paragraph /*ITocEntry.*/getParagraph() { return null; }

    public String /*ITocEntry.*/getDocumentOutlineTitle()
    {
        return null;
    }

    public NodeRange /*ITocEntry.*/getLabelRange() throws Exception
    {
        return getRefTocEntry().getLabelRange();
    }

    public boolean /*ITocEntry.*/isInFieldCode() { return false; }

    public boolean /*ITocEntry.*/hasBookmark() { return false; }

    public boolean /*ITocEntry.*/isLinkedStyleTocEntry() { return false; }

    public int /*ITocEntry.*/getSequenceValue(String sequenceIdentifier) throws Exception
    {
        FieldSeqValueEvaluator evaluator = mFieldSeqValueEvaluator.getValue();
        evaluator.addSequenceBookmark(mBookmarkName);
        NullableInt32 value = evaluator.getBookmarkSequenceValue(mBookmarkName, sequenceIdentifier);
        return value.getValueOrDefault();
    }

    public int /*ITocEntry.*/getPageNumber()
    {
        // FOSS
        return 0;
    }

    private /*final*/ Lazy<FieldSeqValueEvaluator> mFieldSeqValueEvaluator;
    private /*final*/ String mBookmarkName;
    private /*final*/ NodeRange mEntryRange;
}

