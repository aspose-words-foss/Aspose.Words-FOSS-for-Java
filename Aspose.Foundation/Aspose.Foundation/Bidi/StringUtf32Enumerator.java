// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/07/2012 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.NoSuchElementException;
import java.util.Iterator;


/// <summary>
/// Enumerates UTF-32 code points in <see cref="string"/> instance.
/// </summary>
public final class StringUtf32Enumerator implements Iterable<Integer>, Iterator<Integer>
{
    public StringUtf32Enumerator(String text)
    {
        mNextCharPosition = 0;
        mCurrentOffset = 0;
        mText = (text != null ? text : "");
        mCurrentChar = '\u0000';
    }

    public Iterator<Integer> iterator()
    {
        return this;
    }

    public boolean hasNext()
    {
        if (mNextCharPosition >= mText.length())
        {
            return false;
        }

        char currentChar = mText.charAt(mNextCharPosition);
        mCurrentOffset = mNextCharPosition;
        ++mNextCharPosition;

        if (UnicodeUtil.isLeadSurrogate(currentChar) &&
            (mNextCharPosition < mText.length()) &&
            UnicodeUtil.isTailSurrogate(mText.charAt(mNextCharPosition)))
        {
            mCurrentChar = UnicodeUtil.convertToUtf32(currentChar, mText.charAt(mNextCharPosition));
            ++mNextCharPosition;
        }
        else
        {
            mCurrentChar = currentChar;
        }

        return true;
    }

    public void reset()
    {
        mNextCharPosition = 0;
        mCurrentOffset = 0;
    }

    public void dispose()
    {
        // Nothing to free. But you should call this Dispose if some code will be added here.
    }

    public void setText(String text)
    {
        mText = (text == null)
            ? ""
            : text;
        reset();
    }

    /// <summary>
    /// Returns string length in UTF-32 code units.
    /// </summary>
    // kvk:  GetStringLength result depends on enumerator implementation.
    //       If we implement another enumerator then it is better to keep this method in enumerator class instead of
    //       having two different methods in UnicodeUtil class.
    public static int getStringLength( String text)
    {
        int length = 0;
        StringUtf32Enumerator stringUtf32Enumerator = new StringUtf32Enumerator(text);

        while (stringUtf32Enumerator.hasNext())
            length++;

        return length;
    }
    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    public Integer next()
    {
        if (mNextCharPosition == 0)
            throw new IllegalStateException("Enumerator has not been started.");
        return mCurrentChar;
    }

    /// <summary>
    /// Gets the index of the current character in the source string.
    /// </summary>
    public int getOffset() { return mCurrentOffset; }
    //JAVA-deleted Namesake Explicit Interface Implementation property:
    //Object IEnumerator.Current

    private String mText;
    private int mNextCharPosition;
    private int mCurrentChar;
    private int mCurrentOffset;

    //JAVA-added for IEnumerator emulation
    public void remove() 
    {
        throw new UnsupportedOperationException();
    }
}

