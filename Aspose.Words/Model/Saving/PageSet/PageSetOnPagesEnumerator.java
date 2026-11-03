// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/08/2020 by Dmitry Burov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.NoSuchElementException;
import java.util.Iterator;


class PageSetOnPagesEnumerator extends PageSetEnumerator implements Iterator<Integer>
{
    PageSetOnPagesEnumerator(int[] pages, int documentPageCount)
    {
        int maxPageIndex = documentPageCount - 1;
        mPages = new int[pages.length];
        for (int i = 0; i < pages.length; i++)
        {
            int value = pages[i];
            if (value == Integer.MAX_VALUE)
                value = maxPageIndex;
            else if (value >= documentPageCount)
                throw new IllegalArgumentException("Page index " + value +" exceeds the index of the last page "+(documentPageCount - 1) + " in the document." + "\r\nParameter name: " + "pageIndex");

            mPages[i] = value;
        }
    }

    public /*override*/ boolean hasNext()
    {
        mCurrentItem++;
        return mCurrentItem < mPages.length;
    }

    public /*override*/ void reset()
    {
        mCurrentItem = -1;
    }

    public /*override*/ Integer next() { return mPages[mCurrentItem]; }

    private /*final*/ int[] mPages;
    private int mCurrentItem = -1;

    //JAVA-added for IEnumerator emulation
    public void remove() 
    {
        throw new UnsupportedOperationException();
    }
}

