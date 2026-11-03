// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/08/2020 by Dmitry Burov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IntList;
import com.aspose.ms.System.msString;
import java.lang.Integer;


/// <summary>
/// Describes a random set of pages based on exact pages.
/// </summary>
class PageSetOnPages extends PageSetBase
{
    /// <summary>
    /// Creates a page set based on exact page indices.
    /// </summary>
    /// <param name="pages">Zero-based indices of pages.</param>
    /// <remarks>
    /// <see cref="int.MaxValue"/> means the last page in the document.
    /// </remarks>
    PageSetOnPages(/*params*/ int... pages)
    {
        checkForNegativeIndices(pages);

        // Protect against external data changes.
        mPages = (int[])pages.clone();
    }

    /// <summary>
    /// Creates a page set based on the page numbers, separated by commas.
    /// For example, "3, 5" includes page 3 and page 5.
    /// </summary>
    /// <param name="pages">String description of pages.</param>
    PageSetOnPages(String pages)
    {
        IntList pageSet = new IntList();

        // Build a list of pages.
        for (String pageString : msString.split(pages, ','))
        {
            int page = Integer.parseInt(msString.trim(pageString));
            pageSet.add(page);                
        }

        mPages = new int[pageSet.getCount()];
        pageSet.copyTo(mPages);

        checkForNegativeIndices(mPages);
    }

    private static void checkForNegativeIndices(int[] pages)
    {
        for (int i = 0; i < pages.length; i++)
            if (pages[i] < 0)
                throw new IllegalArgumentException("Negative page index specified." + "\r\nParameter name: " + "pages");
    }

    public /*override*/ PageSetEnumerator iterator(int documentPageCount)
    {
        return new PageSetOnPagesEnumerator(mPages, documentPageCount);
    }

    private /*final*/ int[] mPages;
}

