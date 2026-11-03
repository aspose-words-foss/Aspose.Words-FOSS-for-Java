// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/04/2008 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;


/// <summary>
/// Represents a collection of string builders each of which represents a content line in text-based formats.
/// </summary>
class TxtContentLines
{
    /// <summary>
    /// Returns the current line. Adds lines if current line index is greater than the number of lines
    /// in the collection.
    /// </summary>
    StringBuilder getCurrentLine()
    {
        int linesToAdd = mCurrentLineIndex - mLines.size() + 1;

        for (int i = 0; i < linesToAdd; i++)
            msArrayList.add(mLines, new StringBuilder());

        return this.get(mCurrentLineIndex);
    }

    /// <summary>
    /// The index of the current line.
    /// </summary>
    int getCurrentLineIndex() { return mCurrentLineIndex; }
    void setCurrentLineIndex(int value) { mCurrentLineIndex = value; }

    /// <summary>
    /// Gets lines count.
    /// </summary>
    int getCount() { return mLines.size(); }

    /// <summary>
    /// Gets a line at a specified index.
    /// </summary>
    StringBuilder get(int index) { return mLines.get(index); }

    /// <summary>
    /// Gets a length of the longest line.
    /// </summary>
    int getMaxLength()
    {
        int maxLength = 0;
        for (StringBuilder line : mLines)
            maxLength = Math.max(maxLength, line.length());

        return maxLength;
    }

    /// <summary>
    /// Removes all lines.
    /// </summary>
    void clear()
    {
        mCurrentLineIndex = 0;
        mLines.clear();
    }

    /// <summary>
    /// Adds a new line.
    /// </summary>
    void newLine()
    {
        mCurrentLineIndex++;
    }

    private int mCurrentLineIndex;   
    private /*final*/ ArrayList<StringBuilder> mLines = new ArrayList<StringBuilder>();
}

