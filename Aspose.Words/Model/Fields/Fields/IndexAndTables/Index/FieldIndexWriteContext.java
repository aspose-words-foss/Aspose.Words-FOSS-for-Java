// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Passes data between methods while writing INDEX field result.
/// </summary>
class FieldIndexWriteContext
{
    /// <summary>
    /// Ctor.
    /// </summary>
    FieldIndexWriteContext(FieldCodeIndex fieldCode, DocumentBuilder documentBuilder)
    {
        mFieldCode = fieldCode;
        mDocumentBuilder = documentBuilder;
    }

    /// <summary>
    /// Performs common tasks while starting of page numbers' writing.
    /// </summary>
    void startWritePageNumbers()
    {
        setCurrentSinglePageNumberInfo(null);
        setUsePageNumberListSeparator(false);
    }

    /// <summary>
    /// Performs common tasks while ending of single page number's writing.
    /// </summary>
    void endWritePageNumber()
    {
        setCurrentSinglePageNumberInfo(null);
        setUsePageNumberListSeparator(true);
    }

    /// <summary>
    /// Gets a shared <see cref="FieldCodeIndex"/> instance.
    /// </summary>
    FieldCodeIndex getFieldCode() { return mFieldCode; };

    private FieldCodeIndex mFieldCode;

    /// <summary>
    /// Gets a shared <see cref="DocumentBuilder"/> instance.
    /// </summary>
    DocumentBuilder getDocumentBuilder() { return mDocumentBuilder; };

    private DocumentBuilder mDocumentBuilder;

    /// <summary>
    /// Gets or sets an <see cref="IndexEntryPageNumberInfo"/> object starting a merged sequence
    /// of single page numbers.
    /// </summary>
    IndexEntryPageNumberInfo getCurrentSinglePageNumberInfo() { return mCurrentSinglePageNumberInfo; }; void setCurrentSinglePageNumberInfo(IndexEntryPageNumberInfo value) { mCurrentSinglePageNumberInfo = value; };

    private IndexEntryPageNumberInfo mCurrentSinglePageNumberInfo;

    /// <summary>
    /// Gets a value indicating whether <see cref="CurrentSinglePageNumberInfo"/> is not null.
    /// </summary>
    boolean hasCurrentSinglePageNumberInfo() { return getCurrentSinglePageNumberInfo() != null; }

    /// <summary>
    /// Gets a value indicating whether page number list separator should be used istead of page number
    /// separator while writing of the next page number.
    /// </summary>
    boolean getUsePageNumberListSeparator() { return mUsePageNumberListSeparator; }; private void setUsePageNumberListSeparator(boolean value) { mUsePageNumberListSeparator = value; };

    private boolean mUsePageNumberListSeparator;
}

