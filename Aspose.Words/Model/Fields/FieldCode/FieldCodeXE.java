// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/06/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Caches the XE field code for reusing. See corresponding <see cref="FieldXE"/> properties for any details.
/// </summary>
class FieldCodeXE
{
    /// <summary>
    /// Ctor.
    /// </summary>
    FieldCodeXE(FieldXE field)
    {
        // WORDSNET-22419 NullReferenceException occurs during UpdateFields.
        mTextRange = field.getTextRange();
        mText = field.getText();
        mIsBold = field.isBold();
        mIsItalic = field.isItalic();
        mEntryType = field.getEntryType();
        mEntryTypeCore = FieldIndexAndTablesUtil.getIndexEntryType(getEntryType());
        mPageRangeBookmarkName = field.getPageRangeBookmarkName();
        mPageNumberReplacementRange = field.getPageNumberReplacementRange();
        mPageNumberReplacement = field.getPageNumberReplacement();
        mYomi = field.getYomi();
    }

    /// <summary>
    /// Gets a node range of the text argument.
    /// </summary>
    NodeRange getTextRange() { return mTextRange; };

    private NodeRange mTextRange;

    /// <summary>
    /// Gets the text of the entry.
    /// </summary>
    String getText() { return mText; };

    private String mText;

    /// <summary>
    /// Gets whether to apply bold formatting to the entry's page number.
    /// </summary>
    boolean isBold() { return mIsBold; };

    private boolean mIsBold;

    /// <summary>
    /// Gets whether to apply italic formatting to the entry's page number.
    /// </summary>
    boolean isItalic() { return mIsItalic; };

    private boolean mIsItalic;

    /// <summary>
    /// Gets an index entry type.
    /// </summary>
    String getEntryType() { return mEntryType; };

    private String mEntryType;

    /// <summary>
    /// Gets an internal index entry type.
    /// </summary>
    int getEntryTypeCore() { return mEntryTypeCore; };

    private int mEntryTypeCore;

    /// <summary>
    /// Gets the name of the bookmark that marks a range of pages that is inserted as the entry's page number.
    /// </summary>
    String getPageRangeBookmarkName() { return mPageRangeBookmarkName; };

    private String mPageRangeBookmarkName;

    /// <summary>
    /// Gets a value indicating whether a page range bookmark name is provided through the field's code.
    /// </summary>
    boolean hasPageRangeBookmarkName() { return getPageRangeBookmarkName() != null; }

    /// <summary>
    /// Gets a node range of the text argument to be used in place of a page number.
    /// </summary>
    NodeRange getPageNumberReplacementRange() { return mPageNumberReplacementRange; };

    private NodeRange mPageNumberReplacementRange;

    /// <summary>
    /// Gets a text used in place of a page number.
    /// </summary>
    String getPageNumberReplacement() { return mPageNumberReplacement; };

    private String mPageNumberReplacement;

    /// <summary>
    /// Gets a value indicating whether a text used in place of a page number is provided through the field's code.
    /// </summary>
    boolean hasPageNumberReplacement() { return getPageNumberReplacement() != null; }

    /// <summary>
    /// Gets the yomi (first phonetic character for sorting indexes) for the index entry
    /// </summary>
    String getYomi() { return mYomi; };

    private String mYomi;
}

