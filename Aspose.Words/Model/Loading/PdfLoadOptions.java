// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/01/2020 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Allows to specify additional options when loading Pdf document into a <see cref="Document"/> object.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/specify-load-options/">Specify Load Options</a> documentation article.</para>
/// </summary>
public class PdfLoadOptions extends LoadOptions
{
    /// <summary>
    /// Gets or sets the 0-based index of the first page to read. Default is 0.
    /// </summary>
    public int getPageIndex() { return mPageIndex; }
    public void setPageIndex(int value) { mPageIndex = value; }

    /// <summary>
    /// Gets or sets the number of pages to read. Default is MaxValue which means all pages of the document will be read.
    /// </summary>
    public int getPageCount() { return mPageCount; }
    public void setPageCount(int value) { mPageCount = value; }

    /// <summary>
    /// Gets or sets the flag indicating whether images must be skipped while loading PDF document. Default is <c>false</c>.
    /// </summary>
    public boolean getSkipPdfImages() { return mSkipPdfImages; }
    public void setSkipPdfImages(boolean value) { mSkipPdfImages = value; }

    private int mPageCount = Integer.MAX_VALUE;
    private int mPageIndex;
    private boolean mSkipPdfImages;
}

