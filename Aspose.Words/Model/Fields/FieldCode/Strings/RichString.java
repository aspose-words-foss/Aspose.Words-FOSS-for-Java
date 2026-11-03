// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/03/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IString;
import java.util.ArrayList;
import com.aspose.IChar;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.msChar;
import java.util.Iterator;
import com.aspose.ms.System.msArray;


/// <summary>Represents collection of <see cref="RichChar"/> instances.</summary>
class RichString implements IString
{
    /// <summary>Creates <see cref="RichString"/> from the specified string.</summary>
    static RichString createFromString(String srcString, RunPr runPr)
    {
        if (srcString == null)
            return null;

        RichStringBuilder builder = new RichStringBuilder(srcString.length());
        builder.append(srcString, runPr);
        return builder.toRichString();
    }

    /// <summary>Creates <see cref="RichString"/> from the specified string.</summary>
    static RichString createFromString(String srcString)
    {
        return createFromString(srcString, new RunPr());
    }

    static /*final*/ RichString EMPTY = new RichString();

    RichString(ArrayList<IChar> chars)
    {
        mChars = msArrayList.toArray(chars, new IChar[0]);
    }

    RichString(/*params*/ RichChar... chars)
    {
        mChars = new RichChar[chars.length];
        System.arraycopy(chars, 0, mChars, 0, chars.length);
    }

    public IChar /*IString.*/get(int index) { return getInternal(index); }

    RichChar getInternal(int index)
    {
        return (RichChar)mChars[index];
    }

    public int indexOf(String value)
    {
        return msString.indexOf(toSystemString(), value, StringComparison.ORDINAL);
    }

    public int indexOf(String value, int startIndex)
    {
        return msString.indexOf(toSystemString(), value, startIndex, StringComparison.ORDINAL);
    }

    RichString substring(int startIndex, int length)
    {
        ArrayList<IChar> chars = msArrayList.getRange(msArrayList.ctor(mChars), startIndex, length);
        return new RichString(chars);
    }

    public IString /*IString.*/replace(String oldValue, String newValue)
    {
        return replaceInternal(oldValue, newValue);
    }

    RichString replaceInternal(String oldValue, String newValue)
    {
        RichStringBuilder builder = new RichStringBuilder(this);
        return builder
            .replaceInternal(oldValue, newValue)
            .toRichString();
    }

    public IString /*IString.*/remove(int startIndex, int count)
    {
        return removeInternal(startIndex, count);
    }

    RichString removeInternal(int startIndex, int count)
    {
        RichStringBuilder builder = new RichStringBuilder(this);
        return builder
            .removeInternal(startIndex, count)
            .toRichString();
    }

    public IString /*IString.*/toUpper()
    {
        return toUpperInternal();
    }

    RichString toUpperInternal()
    {
        RichStringBuilder builder = new RichStringBuilder(mChars.length);

        for (IChar _char : mChars)
            builder.appendInternal((RichChar)_char.toUpper());

        return builder.toRichString();
    }

    public IString /*IString.*/toLower()
    {
        return toLowerInternal();
    }

    RichString toLowerInternal()
    {
        RichStringBuilder builder = new RichStringBuilder(mChars.length);

        for (IChar _char : mChars)
            builder.appendInternal((RichChar)_char.toLower());

        return builder.toRichString();
    }

    public String toSystemString()
    {
        if (mSystemString == null)
        {
            StringBuilder builder = new StringBuilder(mChars.length);

            for (IChar c : mChars)
                builder.append(c.toSystemChar());

            mSystemString = builder.toString();
        }

        return mSystemString;
    }

    /// <summary>Removes all leading and trailing white-space characters from the current <see cref="RichString" /> object.</summary>
    RichString trim()
    {
        if (getLength() == 0)
            return this;

        int firstNonWhiteSpaceChar = -1;
        int lastNonWhiteSpaceChar = -1;

        for (int i = 0; i < getLength(); i++)
        {
            boolean isWhiteSpace = msChar.isWhiteSpace(getInternal(i).getCharacter());
            if (!isWhiteSpace)
            {
                lastNonWhiteSpaceChar = i;
                if (firstNonWhiteSpaceChar == -1)
                    firstNonWhiteSpaceChar = i;
            }
        }

        if (firstNonWhiteSpaceChar == -1)
            return EMPTY;

        RichStringBuilder builder = new RichStringBuilder();

        for (int i = firstNonWhiteSpaceChar; i <= lastNonWhiteSpaceChar; i++)
            builder.appendInternal(getInternal(i));

        return builder.toRichString();
    }

    RichString trimFormat(/*params*/ int... attrs)
    {
        RichStringBuilder builder = new RichStringBuilder();

        for (IChar ichar : (Iterable<IChar>) this)
        {
            RichChar _char = (RichChar)ichar;
            RunPr format = AttrCollectionExtension.deepClone(_char.getRunPr());
            for (int attr : attrs)
                format.remove(attr);
            builder.append(_char.getCharacter(), format);
        }

        return builder.toRichString();
    }

    RichStringChunk[] toChunks()
    {
        if (mChunks == null)
        {
            mChunks = new ArrayList<RichStringChunk>();

            StringBuilder builder = new StringBuilder();
            RunPr runPr = null;
            for (IChar ichar : mChars)
            {
                RichChar _char = (RichChar)ichar;
                if ((runPr != null) && !AttrCollection.equals(runPr, _char.getRunPr(), null))
                {
                    msArrayList.add(mChunks, new RichStringChunk(builder.toString(), runPr));
                    builder.setLength(0);
                }

                runPr = _char.getRunPr();
                builder.append(_char.getCharacter());
            }

            msArrayList.add(mChunks, new RichStringChunk(builder.toString(), runPr));
        }

        return msArrayList.toArray(mChunks, new RichStringChunk[0]);
    }

    public int getLength() { return mChars.length; }

    public Iterator<IChar> /*IEnumerable<IChar>.*/iterator()
    {
        return msArray.iteratorForArray(mChars);
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    private /*final*/ IChar[] mChars;
    private String mSystemString;
    private ArrayList<RichStringChunk> mChunks;
}

