// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/05/2025 by Ilya Navrotskiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;


/// <summary>
/// Represents a helper class that allows to work with text ranges in <see cref="CharsetDetector"/>.
/// </summary>
public class TextRange
{
    /// <summary>
    /// Initializes a new instance of <see cref="TextRange"/> class.
    /// </summary>
    /// <param name="start">The text at the very beginning of the range.</param>
    /// <param name="end">The text at the very end of the range.</param>
    /// <param name="requiredTextsInside">The array of text snippets that are required between start and end of the range.</param>
    public TextRange(String start, String end)
	{
		this(start, end, null);
	}
    /// <summary>
    /// Initializes a new instance of <see cref="TextRange"/> class.
    /// </summary>
    /// <param name="start">The text at the very beginning of the range.</param>
    /// <param name="end">The text at the very end of the range.</param>
    /// <param name="requiredTextsInside">The array of text snippets that are required between start and end of the range.</param>
    public TextRange(String start, String end, String[] requiredTextsInside)
    {
        mStartText = Encoding.getUTF8().getBytes(start);
        mEndText = Encoding.getUTF8().getBytes(end);
        if (requiredTextsInside != null)
        {
            for (String requiredText : requiredTextsInside)
                msArrayList.add(mRequiredTextsInside, Encoding.getUTF8().getBytes(requiredText));
        }

        setStartIndex(-1);
    }

    /// <summary>
    /// Finds range in a specified input array.
    /// </summary>
    /// <remarks>
    /// Sets appropriate values to the corresponding <see cref="StartIndex"/> and <see cref="EndIndex"/>, if found.
    /// Note, <see cref="EndIndex"/> may be not found and then it sets to -1. In this case you can continue search
    /// it in another buffer using <see cref="FindEnd"/>.
    /// </remarks>
    public boolean find(byte[] input, int startIndex)
    {
        setEndIndex(-1);
        setStartIndex(indexOfIgnoreAsciiCase(input, mStartText, startIndex));
        if (getStartIndex() == -1)
            return false;

        for (byte[] requiredTextInside : mRequiredTextsInside)
        {
            int requiredTextIndex = indexOfIgnoreAsciiCase(input, requiredTextInside, getStartIndex() + mStartText.length);
            if (requiredTextIndex == -1)
                return false;

            int endIndex = indexOfIgnoreAsciiCase(input, mEndText, getStartIndex() + mStartText.length, requiredTextIndex);
            if (endIndex != -1)
                return false;
        }

        setEndIndex(indexOfIgnoreAsciiCase(input, mEndText, getStartIndex() + mStartText.length));

        return true;
    }

    /// <summary>
    /// Finds end text for the range in a specified input array.
    /// </summary>
    public boolean findEnd(byte[] input, int startIndex)
    {
        setEndIndex(indexOfIgnoreAsciiCase(input, mEndText, startIndex));
        return getEndIndex() != -1;
    }

    /// <summary>
    /// Returns true, if <see cref="StartIndex"/> of this range is less, than start index of the other specified range.
    /// </summary>
    public boolean isBefore(TextRange other)
    {
        if (other == null || other.getStartIndex() == -1)
            return getStartIndex() != -1;

        if (getStartIndex() == -1)
            return false;

        return getStartIndex() < other.getStartIndex();
    }

    /// <summary>
    /// Returns a position of subarray in a source array.
    /// Ignores case for ASCII letters (A-Z, a-z) on comparison.
    /// </summary>
    private static int indexOfIgnoreAsciiCase(byte[] source, byte[] subArray, int sourceStartIndex)
    {
        return indexOfIgnoreAsciiCase(source, subArray, sourceStartIndex, source.length);
    }

    /// <summary>
    /// Returns a position of subarray in a source array.
    /// Ignores case for ASCII letters (A-Z, a-z) on comparison.
    /// </summary>
    private static int indexOfIgnoreAsciiCase(byte[] source, byte[] subArray, int sourceStartIndex, int sourceEndIndex)
    {
        int endIndex = Math.min(source.length - subArray.length + 1, sourceEndIndex);
        for (int i = sourceStartIndex; i < endIndex; i++)
        {
            int j = 0;
            while ((j < subArray.length) && areEqualIgnoreAsciiCase(source[i + j], subArray[j]))
                j++;

            if (j == subArray.length)
                return i;
        }

        return -1;
    }

    /// <summary>
    /// Returns true, if two bytes are equal.
    /// Ignores case for ASCII letters (A-Z, a-z) on comparison.
    /// </summary>
    private static boolean areEqualIgnoreAsciiCase(byte b1, byte b2)
    {
        if (b1 == b2)
            return true;

        if ((b1 & 0xFF) >= 'A' && (b1 & 0xFF) <= 'Z')
            return ((b1 & 0xFF) + 32) == (b2 & 0xFF);

        if ((b1 & 0xFF) >= 'a' && (b1 & 0xFF) <= 'z')
            return ((b1 & 0xFF) - 32) == (b2 & 0xFF);

        return false;
    }


    /// <summary>
    /// Gets an integer value representing start index of the range in a byte array after <see cref="Find"/> is called.
    /// </summary>
    public int getStartIndex() { return mStartIndex; }; private void setStartIndex(int value) { mStartIndex = value; };

    private int mStartIndex;

    /// <summary>
    /// Gets an integer value representing end index of the range in a byte array after <see cref="Find"/>,
    /// or <see cref="FindEnd"/> is called.
    /// </summary>
    public int getEndIndex() { return mEndIndex; }; private void setEndIndex(int value) { mEndIndex = value; };

    private int mEndIndex;

    /// <summary>
    /// Gets an integer value representing next index just after end text of the range in a byte array.
    /// </summary>
    public int getNextIndex() { return getEndIndex() == -1 ? 0 : getEndIndex() + mEndText.length; }

    private /*final*/ byte[] mStartText;
    private /*final*/ byte[] mEndText;
    private /*final*/ ArrayList<byte[]> mRequiredTextsInside = new ArrayList<byte[]>();
}

