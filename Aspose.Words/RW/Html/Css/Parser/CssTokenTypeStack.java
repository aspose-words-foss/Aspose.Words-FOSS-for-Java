// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/06/2022 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IntList;


/// <summary>
/// A stack of <see cref="CssTokenType"/> values.
/// </summary>
/// <remarks>
/// This class is optimized to reduce memory usage in scenarios where most of stored values are equal.
/// It utilizes a run-length encoding to reduce the number of values in memory.
/// </remarks>
class CssTokenTypeStack
{
    void push(/*CssTokenType*/int tokenType)
    {
        // Check if the new token is equal to the token that has been pushed last.
        if (!isEmpty() && (tokenType == peek()))
        {
            // If tokens are equal, we don't push the new token and just increase the token counter instead.
            // That is, we use run-length encoding for space optimization.
            int lastItemIndex = mTokenCount.getCount() - 1;
            mTokenCount.set(lastItemIndex, mTokenCount.get(lastItemIndex) + 1);
        }
        else
        {
            // If tokens are not equal (or no tokens have been pushed yet), we push the new token normally. 
            mTokens.add((int)tokenType);
            mTokenCount.add(1);
        }
    }

    void pop()
    {
        if (isEmpty())
        {
            throw new IllegalStateException();
        }

        int index = mTokenCount.getCount() - 1;
        mTokenCount.set(index, mTokenCount.get(index) - 1);

        if (mTokenCount.get(index) <= 0)
        {
            mTokens.removeAt(index);
            mTokenCount.removeAt(index);
        }
    }

    /*CssTokenType*/int peek()
    {
        if (isEmpty())
        {
            throw new IllegalStateException();
        }

        return (/*CssTokenType*/int)mTokens.get(mTokens.getCount() - 1);
    }

    boolean isEmpty() { return mTokens.getCount() == 0; }

    private /*final*/ IntList mTokens = new IntList();
    private /*final*/ IntList mTokenCount = new IntList();
}

