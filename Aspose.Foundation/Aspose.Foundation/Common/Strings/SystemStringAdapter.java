// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/01/2020 by Edward Voronov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import java.util.Iterator;
import com.aspose.ms.System.msArray;


/// <summary>Adapts <see cref="System.String"/> to <see cref="IString"/>.</summary>
public class SystemStringAdapter implements IString
{
    private SystemStringAdapter(String value)
    {
        mValue = value;
        mChars = new SystemCharAdapter[getValue().length()];
    }

    public static SystemStringAdapter create(String value)
    {
        return msString.equals(value, "")
            ? EMPTY
            : new SystemStringAdapter(value);
    }

    public static /*final*/ SystemStringAdapter EMPTY = new SystemStringAdapter("");

    public int getLength() { return getValue().length(); }

    public IChar /*IString.*/get(int index) { return getInternal(index); }

    public SystemCharAdapter getInternal(int index)
    {
        return ensureChar(index);
    }

    public int indexOf(String value)
    {
        return msString.indexOf(getValue(), value, StringComparison.ORDINAL);
    }

    public IString /*IString.*/replace(String oldValue, String newValue)
    {
        return replaceInternal(oldValue, newValue);
    }

    public SystemStringAdapter replaceInternal(String oldValue, String newValue)
    {
        return create(getValue().replace(oldValue, newValue));
    }

    public IString /*IString.*/remove(int startIndex, int count)
    {
        return removeInternal(startIndex, count);
    }

    public SystemStringAdapter removeInternal(int startIndex, int count)
    {
        return create(msString.remove(getValue(), startIndex, count));
    }

    public IString /*IString.*/toUpper()
    {
        return toUpperInternal();
    }

    public SystemStringAdapter toUpperInternal()
    {
        return create(getValue().toUpperCase());
    }

    public IString /*IString.*/toLower()
    {
        return toLowerInternal();
    }

    public SystemStringAdapter toLowerInternal()
    {
        return create(getValue().toLowerCase());
    }

    public String toSystemString()
    {
        return getValue();
    }

    public Iterator<IChar> /*IEnumerable<IChar>.*/iterator()
    {
        return msArray.iteratorForArray(ensureChars());
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    public String getValue() { return mValue; };

    private  String mValue;

    private SystemCharAdapter ensureChar(int index)
    {
        return (mChars[index] != null ? mChars[index] : (mChars[index] = SystemCharAdapter.create(getValue().charAt(index))));
    }

    private IChar[] ensureChars()
    {
        for (int i = 0; i < mChars.length; i++)
            ensureChar(i);

        return mChars;
    }

    private /*final*/ SystemCharAdapter[] mChars;
}

