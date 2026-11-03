// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/08/2020 by Dmitry Burov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Iterator;


/// <summary>
/// Base for classes describing a random set of pages.
/// </summary>
abstract class PageSetBase
{
    /// <summary>
    /// Gets the total number of pages specified in set.
    /// </summary>
    int getPageCount(int documentPageCount)
    {
        int pageCount = 0;
        for (int unused : iterator(documentPageCount))
            pageCount++;

        return pageCount;
    }

    /// <summary>
    /// Gets the zero-based index of the first page in the set.
    /// </summary>
    int getStartIndex(int documentPageCount)
    {
        Iterator<Integer> enumerator = iterator(documentPageCount);
        enumerator.hasNext();
        return enumerator.next();
    }

    public abstract PageSetEnumerator iterator(int documentPageCount);
}

