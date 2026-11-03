// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/04/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Holds an information used to build page numbers while an INDEX field result building.
/// </summary>
class IndexEntryPageNumberInfo
{
    IndexEntryPageNumberInfo(
        int firstPageNumber,
        int lastPageNumber,
        FieldSeq firstFieldSeq,
        FieldSeq lastFieldSeq,
        FieldCodeXE refFieldCode,
        FieldXE refField,
        FieldSeqDataProvider fieldSeqDataProvider)
    {
        mFieldSeqDataProvider = fieldSeqDataProvider;
        mFirstPageNumber = firstPageNumber;
        mLastPageNumber = lastPageNumber;
        mFirstFieldSeq = firstFieldSeq;
        mRefFieldCode = refFieldCode;
        mRefField = refField;
        mLastFieldSeq = lastFieldSeq;
    }

    /// <summary>
    /// Gets the number of the first page in range if any or the number of a single page otherwise.
    /// </summary>
    int getFirstPageNumber() { return mFirstPageNumber; };

    private int mFirstPageNumber;

    /// <summary>
    /// Gets the number of the last page in range if any.
    /// </summary>
    int getLastPageNumber() { return mLastPageNumber; };

    private int mLastPageNumber;

    int getFirstSequenceNumber() throws Exception
    {
        return getSequenceNumber(mFirstFieldSeq);
    }

    int getLastSequenceNumber() throws Exception
    {
        return getSequenceNumber(mLastFieldSeq);
    }

    /// <summary>
    /// Gets a value indicating whether this instance represents a valid page range.
    /// </summary>
    boolean hasPageRange() { return getLastPageNumber() != INVALID_PAGE_NUMBER; }

    /// <summary>
    /// Gets a value indicating whether this instance represents a valid page range where the first
    /// and the last page numbers are the same.
    /// </summary>
    boolean hasSinglePageRange() { return getFirstPageNumber() == getLastPageNumber(); }

    /// <summary>
    /// Gets the corresponding <see cref="FieldCodeXE"/> instance.
    /// </summary>
    FieldCodeXE getRefFieldCode() { return mRefFieldCode; };

    private FieldCodeXE mRefFieldCode;

    /// <summary>
    /// Gets the corresponding <see cref="FieldXE"/> instance.
    /// </summary>
    FieldXE getRefField() { return mRefField; };

    private FieldXE mRefField;

    private int getSequenceNumber(FieldSeq field) throws Exception
    {
        if (field == null)
            return 0;

        Constant constant = mFieldSeqDataProvider.getValue(field);
        if (constant == null)
            return 0;

        return (int)constant.getValueDouble();
    }

    private /*final*/ FieldSeq mFirstFieldSeq;
    private /*final*/ FieldSeq mLastFieldSeq;
    private /*final*/ IFieldUpdateDataProvider mFieldSeqDataProvider;

    static final int INVALID_PAGE_NUMBER = -1;
}

