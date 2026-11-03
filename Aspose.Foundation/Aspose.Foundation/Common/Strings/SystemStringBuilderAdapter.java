// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/01/2020 by Edward Voronov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.msStringBuilder;
import java.util.ArrayList;


/// <summary>Adapts <see cref="StringBuilder"/> to <see cref="IStringBuilder"/>.</summary>
public class SystemStringBuilderAdapter implements IStringBuilder
{
    public SystemStringBuilderAdapter()
    {
    	this(new StringBuilder());
	
    }

    public SystemStringBuilderAdapter(int capacity)
    {
    	this(new StringBuilder(capacity));
	
    }

    public SystemStringBuilderAdapter(StringBuilder value)
    {
        mValue = value;
    }

    public int getLength() { return getValue().length(); }
    public void setLength(int value) { getValue().setLength(value); }

    public IChar /*IStringBuilder.*/get(int index) { return getChar(index); }
    public void /*IStringBuilder.*/set(int index, IChar value) { setChar(index, (SystemCharAdapter)value); }

    public SystemCharAdapter getChar(int index)
    {
        return SystemCharAdapter.create(getValue().charAt(index));
    }

    public void setChar(int index, SystemCharAdapter value)
    {
        getValue().setCharAt(index, value.getValue());
    }

    public IString /*IStringBuilder.*/toIString()
    {
        return SystemStringAdapter.create(getValue().toString());
    }

    public IStringBuilder /*IStringBuilder.*/append(IChar c)
    {
        return append((SystemCharAdapter)c);
    }

    public SystemStringBuilderAdapter append(SystemCharAdapter c)
    {
        getValue().append(c.getValue());
        return this;
    }

    public IStringBuilder /*IStringBuilder.*/append(char c, IChar source)
    {
        return append(c);
    }

    public SystemStringBuilderAdapter append(char c)
    {
        getValue().append(c);
        return this;
    }

    public IStringBuilder /*IStringBuilder.*/append(char c, int count, IChar source)
    {
        return append(c, count);
    }

    public SystemStringBuilderAdapter append(char c, int count)
    {
        msStringBuilder.append(getValue(), c, count);
        return this;
    }

    public IStringBuilder /*IStringBuilder.*/append(IString s)
    {
        return append((SystemStringAdapter)s);
    }

    public SystemStringBuilderAdapter append(SystemStringAdapter s)
    {
        msStringBuilder.append(getValue(), s.getValue());
        return this;
    }

    public IStringBuilder /*IStringBuilder.*/append(String s, IChar source)
    {
        return append(s);
    }

    public SystemStringBuilderAdapter append(String s)
    {
        msStringBuilder.append(getValue(), s);
        return this;
    }

    public IStringBuilder /*IStringBuilder.*/insert(int index, char c, IChar source)
    {
        return insert(index, c);
    }

    public SystemStringBuilderAdapter insert(int index, char c)
    {
        getValue().insert(index, c);
        return this;
    }

    public IStringBuilder /*IStringBuilder.*/insert(int index, String s, IChar source)
    {
        return insert(index, s);
    }

    public SystemStringBuilderAdapter insert(int index, String s)
    {
        getValue().insert(index, s);
        return this;
    }

    public IStringBuilder /*IStringBuilder.*/insert(int index, String s, int count, ArrayList<IChar> sources)
    {
        return insert(index, s, count);
    }

    public SystemStringBuilderAdapter insert(int index, String s, int count)
    {
        msStringBuilder.insert(getValue(), index, s, count);
        return this;
    }

    public IStringBuilder /*IStringBuilder.*/replace(String oldValue, String newValue)
    {
        return replaceInternal(oldValue, newValue);
    }

    public SystemStringBuilderAdapter replaceInternal(String oldValue, String newValue)
    {
        msStringBuilder.replace(getValue(), oldValue, newValue);
        return this;
    }

    public IStringBuilder /*IStringBuilder.*/replace(int index, char c, IChar source)
    {
        return replace(index, c);
    }

    public SystemStringBuilderAdapter replace(int index, char c)
    {
        getValue().setCharAt(index, c);
        return this;
    }

    public IStringBuilder /*IStringBuilder.*/remove(int startIndex, int length)
    {
        return removeInternal(startIndex, length);
    }

    public SystemStringBuilderAdapter removeInternal(int startIndex, int length)
    {
        getValue().delete((startIndex), (startIndex) + (length));
        return this;
    }

    public StringBuilder getValue() { return mValue; };

    private StringBuilder mValue;
}

