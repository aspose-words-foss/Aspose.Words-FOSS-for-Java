// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/08/2020 by Dmitry Burov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.NoSuchElementException;
import java.util.Iterator;


class PageSetOnRangesEnumerator extends PageSetEnumerator implements Iterator<Integer>
{
    PageSetOnRangesEnumerator(/*PageParity*/int parity, PageRange[] ranges, int documentPageCount)
    {
        mParity = parity;
        mRanges = new PageRange[ranges.length];

        for (int i = 0; i < ranges.length; i++)
            mRanges[i] = ranges[i].initialize(documentPageCount);
    }

    public /*override*/ boolean hasNext()
    {
        boolean moveResult = moveNextUnconditional();

        switch (mParity)
        {
            case PageParity.ANY:
                break;
            case PageParity.EVEN:
                while (moveResult && !isCurrentEven())
                    moveResult = moveNextUnconditional();
                break;
            case PageParity.ODD:
                while (moveResult && isCurrentEven())
                    moveResult = moveNextUnconditional();
                break;
            default:
                throw new IllegalStateException("Unknown parity type encountered.");
        }

        return moveResult;
    }

    /// <summary>
    /// Since page indices are zero-based, even pages have odd indices.
    /// </summary>
    private boolean isCurrentEven() { return (next() % 2) != 0; }

    private boolean moveNextUnconditional()
    {
        if (mCurrentItem < 0)
        {
            // Empty set?
            if (mRanges.length == 0)
                return false;

            // Move iterator to start.
            mCurrentItem = 0;
            mCurrentOffset = 0;
            return true;
        }
        else
        {
            // Move to the next page in range if possible.
            if (mRanges[mCurrentItem].getFrom() + mCurrentOffset < mRanges[mCurrentItem].getTo())
            {
                mCurrentOffset++;
                return true;
            }
            // Otherwise try to switch to the next range.
            else if (mCurrentItem < mRanges.length - 1)
            {
                mCurrentItem++;
                mCurrentOffset = 0;
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    public /*override*/ void reset()
    {
        mCurrentItem = -1;
        mCurrentOffset = -1;
    }

    public /*override*/ Integer next() { return mRanges[mCurrentItem].getFrom() + mCurrentOffset; }

    private /*final*/ /*PageParity*/int mParity;
    private /*final*/ PageRange[] mRanges;
    private int mCurrentItem = -1;
    private int mCurrentOffset = -1;

    //JAVA-added for IEnumerator emulation
    public void remove() 
    {
        throw new UnsupportedOperationException();
    }
}

