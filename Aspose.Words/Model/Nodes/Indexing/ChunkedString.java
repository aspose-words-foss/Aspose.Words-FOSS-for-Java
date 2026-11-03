// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/11/2022 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.msString;
import java.util.ArrayList;


/// <summary>
/// Implements fast insertion string.
/// </summary>
/// <remarks>
/// AM. During WORDSNET-22225 profiling I found that half of execution time spent to string.Insert() operations.
/// WORDSNET-22225 documents are huge and contains about 300k of text and insert operations made many times on such
/// string are slow.
///
/// This class implements string split into smaller chunks which allow to do insert operation much faster.
/// </remarks>
class ChunkedString
{
    ChunkedString(String text)
    {
        final int DEFAULT_CHUNK_LENGTH = 1000;

        int completeItems = text.length() / DEFAULT_CHUNK_LENGTH;
        int completeItemsLength = completeItems * DEFAULT_CHUNK_LENGTH;

        for (int i = 0; i < completeItems; i++)
            msArrayList.add(mChunks, text.substring((i * DEFAULT_CHUNK_LENGTH), (i * DEFAULT_CHUNK_LENGTH) + (DEFAULT_CHUNK_LENGTH)));

        if (text.length() - completeItemsLength > 0)
            msArrayList.add(mChunks, text.substring((completeItemsLength), (completeItemsLength) + (text.length() - completeItemsLength)));

        setLength(text.length());

        invalidate();
    }

    String substring(int startIndex, int length)
    {
        if (length == 0)
            return "";

        int itemIndex = 0;
        int chunkStart = 0;
        int thisIndex = startIndex;

        if (mLastChunkStart <= thisIndex)
        {
            itemIndex = mLastChunkIndex;
            chunkStart = mLastChunkStart;

            thisIndex -= mLastChunkStart;
        }

        for (int i = itemIndex; i < mChunks.size(); i++)
        {
            if (thisIndex < mChunks.get(i).length())
            {
                // Here we found needed part.
                int thisLength = length;

                mLastChunkIndex = i;
                mLastChunkStart = chunkStart;

                StringBuilder sb = new StringBuilder(length);
                while (thisLength > 0)
                {
                    String part = mChunks.get(i).substring((thisIndex), (thisIndex) + (Math.min(thisLength, mChunks.get(i).length() - thisIndex)));

                    msStringBuilder.append(sb, part);

                    thisLength -= part.length();
                    thisIndex = 0;
                    i++;
                }

                return sb.toString();
            }

            thisIndex -= mChunks.get(i).length();
            chunkStart += mChunks.get(i).length();
        }

        throw new IndexOutOfBoundsException();
    }

    void insert(int startIndex, String value)
    {
        int thisIndex = startIndex;
        for (int i = 0; i < mChunks.size(); i++)
        {
            if (thisIndex <= mChunks.get(i).length())
            {
                mChunks.set(i, msString.insert(mChunks.get(i), thisIndex, value));

                setLength(getLength() + value.length());

                invalidate();

                return;
            }

            thisIndex -= mChunks.get(i).length();
        }

        throw new IndexOutOfBoundsException();
    }

    char get(int index)
    {
        int itemIndex = 0;
        int chunkStart = 0;

        if (mLastChunkStart <= index)
        {
            itemIndex = mLastChunkIndex;
            chunkStart = mLastChunkStart;
            index -= mLastChunkStart;
        }

        for (int i = itemIndex; i < mChunks.size(); i++)
        {
            String chunk = mChunks.get(i);

            if (index < chunk.length())
            {
                mLastChunkIndex = i;
                mLastChunkStart = chunkStart;

                return chunk.charAt(index);
            }

            index -= chunk.length();
            chunkStart += chunk.length();
        }

        throw new IndexOutOfBoundsException();
    }

    private void invalidate()
    {
        mLastChunkIndex = 0;
        mLastChunkStart = 0;
    }

    int getLength() { return mLength; }; private void setLength(int value) { mLength = value; };

    private int mLength;

    String getAsString()
    {
        StringBuilder sb = new StringBuilder(getLength());

        for (String chunk : mChunks)
            msStringBuilder.append(sb, chunk);

        return sb.toString();

    }

    private int mLastChunkIndex;
    private int mLastChunkStart;

    private /*final*/ ArrayList<String> mChunks = new ArrayList<String>();
}

