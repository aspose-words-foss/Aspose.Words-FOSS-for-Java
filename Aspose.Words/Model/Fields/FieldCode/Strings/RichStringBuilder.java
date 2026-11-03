// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/17/2017 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IStringBuilder;
import java.util.ArrayList;
import com.aspose.IChar;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.IString;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;


class RichStringBuilder implements IStringBuilder
{
    RichStringBuilder()
    {
    	this(0);
	
    }

    RichStringBuilder(RichString s)
    {
    	this();
	
        appendInternal(s);
    }

    RichStringBuilder(int capacity)
    {
        mChars = new ArrayList<IChar>(capacity);
    }

    public int getLength() { return mChars.size(); }
    public void setLength(int value)
    {
        if (getLength() == value)
            return;

        if (getLength() > value)
        {
            msArrayList.removeRange(mChars, value, mChars.size() - value);
        }
        else
        {
            while (getLength() != value)
                appendInternal(RichChar.NULL_CHAR);
        }
    }

    public IChar /*IStringBuilder.*/get(int index) { return getInternal(index); }
    public void /*IStringBuilder.*/set(int index, IChar value) { setInternal(index, (RichChar)value); }

    RichChar getInternal(int index)
    {
        return (RichChar)mChars.get(index);
    }

    void setInternal(int index, RichChar value)
    {
        mChars.set(index, value);
    }

    public IString /*IStringBuilder.*/toIString()
    {
        return toRichString();
    }

    public IStringBuilder /*IStringBuilder.*/append(IChar c)
    {
        return appendInternal((RichChar)c);
    }

    RichStringBuilder appendInternal(RichChar c)
    {
        msArrayList.add(mChars, c);
        return this;
    }

    public IStringBuilder /*IStringBuilder.*/append(char c, IChar source)
    {
        return appendInternal(c, (RichChar)source);
    }

    RichStringBuilder appendInternal(char c, RichChar source)
    {
        return append(c, getSourceRunPrOrDefault(source));
    }

    public IStringBuilder /*IStringBuilder.*/append(char c, int count, IChar source)
    {
        return appendInternal(c, count, (RichChar)source);
    }

    RichStringBuilder appendInternal(char c, int count, RichChar source)
    {
        RunPr runPr = getSourceRunPrOrDefault(source);

        for (int i = 0; i < count; i++)
            append(c, runPr);

        return this;
    }

    public IStringBuilder /*IStringBuilder.*/append(IString s)
    {
        return appendInternal((RichString)s);
    }

    RichStringBuilder appendInternal(RichString s)
    {
        for (IChar c : (Iterable<IChar>) s)
            appendInternal((RichChar)c);

        return this;
    }

    public IStringBuilder /*IStringBuilder.*/append(String s, IChar source)
    {
        return appendInternal(s, (RichChar)source);
    }

    RichStringBuilder appendInternal(String s, RichChar source)
    {
        return append(s, getSourceRunPrOrDefault(source));
    }

    public IStringBuilder /*IStringBuilder.*/insert(int index, char c, IChar source)
    {
        return insertInternal(index, c, (RichChar)source);
    }

    RichStringBuilder insertInternal(int index, char c, RichChar source)
    {
        mChars.add(index, new RichChar(c, getSourceRunPrOrDefault(source)));
        return this;
    }

    public IStringBuilder /*IStringBuilder.*/insert(int index, String s, IChar source)
    {
        return insertInternal(index, s, (RichChar)source);
    }

    RichStringBuilder insertInternal(int index, String s, RichChar source)
    {
        RunPr runPr = getSourceRunPrOrDefault(source);

        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            mChars.add(index++, new RichChar(c, runPr));
        }

        return this;
    }

    public IStringBuilder /*IStringBuilder.*/insert(int index, String s, int count, ArrayList<IChar> sources)
    {
        return insertInternal(index, s, count, sources);
    }

    RichStringBuilder insertInternal(int index, String s, int count, ArrayList<IChar> sources)
    {
        for (int j = 0; j < count; j++)
        {
            RichChar source = (sources != null) && (j < sources.size())
                ? (RichChar)sources.get(j)
                : null;

            RunPr runPr = getSourceRunPrOrDefault(source);

            for (int i = 0; i < s.length(); i++)
            {
            	char c = s.charAt(i);
                mChars.add(index++, new RichChar(c, runPr));
            }
        }

        return this;
    }

    public IStringBuilder /*IStringBuilder.*/replace(String oldValue, String newValue)
    {
        return replaceInternal(oldValue, newValue);
    }

    RichStringBuilder replaceInternal(String oldValue, String newValue)
    {
        if (msString.isNullOrEmpty(oldValue))
            return this;

        int index = 0;
        while (true)
        {
            String systemString = toRichString().toSystemString();
            index = msString.indexOf(systemString, oldValue, index, StringComparison.ORDINAL);
            if (index == -1)
                return this;

            RichChar source = getInternal(index);
            removeInternal(index, oldValue.length());
            insertInternal(index, newValue, source);

            index += newValue.length();
        }
    }

    public IStringBuilder /*IStringBuilder.*/replace(int index, char c, IChar source)
    {
        return replaceInternal(index, c, (RichChar)source);
    }

    RichStringBuilder replaceInternal(int index, char c, RichChar source)
    {
        mChars.set(index, new RichChar(c, getSourceRunPrOrDefault(source)));
        return this;
    }

    public IStringBuilder /*IStringBuilder.*/remove(int startIndex, int length)
    {
        return removeInternal(startIndex, length);
    }

    RichStringBuilder removeInternal(int startIndex, int length)
    {
        msArrayList.removeRange(mChars, startIndex, length);
        return this;
    }

    void clear()
    {
        mChars.clear();
    }

    RichStringBuilder append(char c, RunPr runPr)
    {
        return appendInternal(new RichChar(c, runPr));
    }

    RichStringBuilder append(String text, RunPr runPr)
    {
        for (int i = 0; i < text.length(); i++)
        {
            char c = text.charAt(i);
            append(c, runPr);
        }

        return this;
    }

    RichString toRichString()
    {
        return new RichString(mChars);
    }

    private static RunPr getSourceRunPrOrDefault(RichChar source)
    {
        return source != null
            ? source.getRunPr()
            : new RunPr();
    }

    private /*final*/ ArrayList<IChar> mChars;
}

