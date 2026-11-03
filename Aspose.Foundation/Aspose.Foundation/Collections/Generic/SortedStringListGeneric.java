// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/10/2008 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.SortedList;
import java.util.Comparator;
import java.util.Map;


/// <summary>
/// Use this class instead of <see cref="SortedList{TKey,TValue}"/> whenever you need a sorted list with string keys.
/// This class simplifies creation and ensures that sort order is same as in Java.
/// </summary>
public class SortedStringListGeneric<TValue> extends SortedList<String, TValue>
{
    /// <summary>
    /// Creates a case-sensitive sorted list.
    /// </summary>
    public SortedStringListGeneric()
    {
    	this(true);
	
    }

    /// <summary>
    /// Creates a sorted list with a specific comparator.
    /// </summary>
    public SortedStringListGeneric(Comparator<String> comparer) { 	super(comparer);
	 }

    /// <summary>
    /// Creates a case-sensitive or case-insensitive string list.
    /// </summary>
    public SortedStringListGeneric(boolean isCaseSensitive)
    {
    	super(((isCaseSensitive) ? (Comparator<String>)StringOrdinalComparer.DEFAULT : StringOrdinalIgnoreCaseComparer.DEFAULT));
	
    }

    public void addRange(SortedStringListGeneric<TValue> other)
    {
        for (Map.Entry<String, TValue> item : (SortedList<String, TValue>)other)
        {
            add(item.getKey(), item.getValue());
        }
    }

    public void setByIndex(int index, TValue value)
    {
        String key = getKeys().get(index);
        this.set(key, value);
    }

    public TValue getByIndex(int index)
    {
        return getValues().get(index);
    }

    public TValue getSafe(String key, TValue defaultValue)
    {
        if (containsKey(key))
            return this.get(key);

        return defaultValue;
    }

    public String getKey(int index)
    {
        return getKeys().get(index);
    }
}

