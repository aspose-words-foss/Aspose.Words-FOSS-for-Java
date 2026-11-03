// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/08/2020 by Dmitry Burov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.msString;
import java.lang.Integer;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Describes a random set of pages based on page ranges.
/// </summary>
class PageSetOnRanges extends PageSetBase
{
    /// <summary>
    /// Creates a page set based on ranges.
    /// </summary>
    /// <param name="parity">Parity of pages to select.</param>
    /// <param name="ranges">Array of page ranges.</param>
    /// <remarks>
    /// Even pages have odd indices and vice versa, since page indices are zero-based.
    /// </remarks>
    PageSetOnRanges(/*PageParity*/int parity, /*params*/ PageRange... ranges)
    {
        mParity = parity;

        // Protect against external data changes.
        mRanges = copyRanges(ranges);            
    }

    /// <summary>
    /// Creates a page set based on the page numbers and page ranges, separated by commas.
    /// For example, "3, 5-8" includes page 3 and pages 5 through 8.
    /// </summary>
    /// <param name="pages">String description of pages and page ranges</param>
    PageSetOnRanges(String pages)
    {
        ArrayList<PageRange> pageSet = new ArrayList<PageRange>();

        // Build a list of page ranges.
        for (String pageRangeString : msString.split(pages, ','))
        {
            String[] pageRangeParts = msString.split(pageRangeString, '-');

            int from = Integer.parseInt(msString.trim(pageRangeParts[0]));
            int to = (pageRangeParts.length > 1) ? Integer.parseInt(msString.trim(pageRangeParts[1])) : from;

            PageRange pageRange = new PageRange(from, to);
            msArrayList.add(pageSet, pageRange);
        }

        mRanges = new PageRange[pageSet.size()];
        msArrayList.copyTo(pageSet, mRanges);
    }

    private static PageRange[] copyRanges(PageRange[] ranges)
    {
        PageRange[] copy = new PageRange[ranges.length];
        for (int i = 0; i < ranges.length; i++)
            copy[i] = ranges[i];

        return copy;
    }

    public /*override*/ PageSetEnumerator iterator(int documentPageCount)
    {
        return new PageSetOnRangesEnumerator(mParity, mRanges, documentPageCount);
    }

    private /*final*/ /*PageParity*/int mParity = PageParity.ANY;
    private /*final*/ PageRange[] mRanges;
}

