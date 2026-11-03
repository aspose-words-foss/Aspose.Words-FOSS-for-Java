// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/06/2018 by Alexey Butalov

// Parts of this file are derived, by Aspose, from .NET Framework code. Microsoft publishes that source
// under the MIT License at https://github.com/microsoft/referencesource : System/compmod/system/collections/generic/sortedlist.cs
//
// Those parts: Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see THIRD-PARTY-NOTICES.md in the root of this repository.

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msArray;


/// <summary>
/// This is an equivalent of .NET's <see cref="System.Collections.Generic.SortedList{TKey,TValue}" />
/// where TKey is int taken from Rotor source, extended for performance benefits.
/// This class is used to prevent performance degradation in Java ported code because Java uses
/// boxing and unboxing for value types used as generic parameters.
/// </summary>
public class SortedIntegerListGeneric<T>
{
    public SortedIntegerListGeneric()
    {
        mKeys = new int[DEFAULT_CAPACITY];
        mValues = new Object[DEFAULT_CAPACITY];
    }

    public SortedIntegerListGeneric(int initialCapacity)
    {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "initialCapacity");
        mKeys = new int[initialCapacity];
        mValues = new Object[initialCapacity];
    }

    public SortedIntegerListGeneric(SortedIntegerListGeneric<T> sortedIntegerList)
    {
        mKeys = new int[sortedIntegerList.getCapacity()];
        mValues = new Object[sortedIntegerList.getCapacity()];
        mSize = sortedIntegerList.getCount();

        System.arraycopy((sortedIntegerList.mKeys), 0, (mKeys), 0, (mKeys.length));
        System.arraycopy((sortedIntegerList.mValues), 0, (mValues), 0, (mValues.length));
    }

    public void add(int key, T value)
    {
        int i = ArrayUtil.binarySearch(mKeys, 0, mSize, key);
        if (i >= 0)
            throw new IllegalArgumentException("duplicate");
        insert(~i, key, value);
    }

    /// <summary>
    /// Appends to end of sorted list. Make sure that you add keys in sorted order.
    /// </summary>
    /// <remarks>
    /// AM. Sometimes we add keys sorted by nature. Using this method reduces binary search performed.
    /// </remarks>
    public void append(int key, T value)
    {
        if ((mSize > 0) && (key <= mKeys[mSize - 1]))
            throw new IllegalStateException("Key value should be greater than max key value in list.");

        insert(mSize, key, value);
    }

    public void clear()
    {
        mSize = 0;
        mKeys = new int[DEFAULT_CAPACITY];
        mValues = new Object[DEFAULT_CAPACITY];
    }

    public boolean contains(int key)
    {
        return indexOfKey(key) >= 0;
    }

    /// <summary>
    /// Returns true if specified key is in the list.
    /// Performs binary search.
    /// </summary>
    public boolean containsKey(int key)
    {
        // Yes, this is a SPEC'ed duplicate of Contains().
        return indexOfKey(key) >= 0;
    }

    /// <summary>
    /// Returns value if found; or <c>defaultValue</c> otherwise.
    /// </summary>
    public T getSafe(int key, T defaultValue)
    {
        int index = indexOfKey(key);
        if (index >= 0)
            return (T)mValues[index];
        else
            return defaultValue;
    }

    public boolean containsValue(T value)
    {
        return indexOfValue(value) >= 0;
    }

    public T getByIndex(int index)
    {
        if (index < 0 || index >= mSize)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "index");
        return (T)mValues[index];
    }

    public int getKey(int index)
    {
        if (index < 0 || index >= mSize)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "index");
        return mKeys[index];
    }

    public int indexOfKey(int key)
    {
        int ret = ArrayUtil.binarySearch(mKeys, 0, mSize, key);
        return ret >= 0 ? ret : -1;
    }

    public int indexOfValue(T value)
    {
        return msArray.indexOf(mValues, value, 0, mSize);
    }

    public void removeAt(int index)
    {
        if (index < 0 || index >= mSize)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "index");
        mSize--;
        if (index < mSize)
        {
            msArray.copy(mKeys, index + 1, mKeys, index, mSize - index);
            msArray.copy(mValues, index + 1, mValues, index, mSize - index);
        }
        mKeys[mSize] = 0;
        mValues[mSize] = mDefaultValue;
    }

    public void remove(int key)
    {
        int i = indexOfKey(key);
        if (i >= 0)
            removeAt(i);
    }

    public /*override*/ int hashCode()
    {
        int hashCode = 0;
        for (int i = 0; i < getCount(); i++)
        {
            hashCode = (hashCode * 397) ^ (/*int.hashCode()*/mKeys[i]);
            if (mValues[i] != null)
                hashCode = (hashCode * 397) ^ mValues[i].hashCode();
        }

        return hashCode;
    }

    public int getCapacity() { return mKeys.length; }
    public void setCapacity(int value)
    {
        if (value != mKeys.length)
        {
            if (value < mSize)
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");
            if (value > 0)
            {
                int[] newKeys = new int[value];
                Object[] newValues = new Object[value];
                if (mSize > 0)
                {
                    msArray.copy(mKeys, 0, newKeys, 0, mSize);
                    msArray.copy(mValues, 0, newValues, 0, mSize);
                }
                mKeys = newKeys;
                mValues = newValues;
            }
            else
            {
                mKeys = new int[DEFAULT_CAPACITY];
                mValues = new Object[DEFAULT_CAPACITY];
            }
        }
    }

    public int getCount() { return mSize; }

    public T get(int key)
    {
        // In Java mDefaultValue = null and it works incorrect with value types like int and bool
        return getSafe(key, mDefaultValue);
    }
    public void set(int key, T value)
    {
        int i = ArrayUtil.binarySearch(mKeys, 0, mSize, key);
        if (i >= 0)
        {
            mValues[i] = value;
            return;
        }
        insert(~i, key, value);
    }

    protected int[] getKeys() { return mKeys; }

    protected Object[] getValues() { return mValues; }

    private void ensureCapacity(int min)
    {
        int newCapacity = (mKeys.length == 0)
            ? DEFAULT_CAPACITY
            : mKeys.length * 2;
        if (newCapacity < min)
            newCapacity = min;
        setCapacity(newCapacity);
    }

    private void insert(int index, int key, T value)
    {
        if (mSize == mKeys.length)
            ensureCapacity(mSize + 1);

        if (index < mSize)
        {
            msArray.copy(mKeys, index, mKeys, index + 1, mSize - index);
            msArray.copy(mValues, index, mValues, index + 1, mSize - index);
        }
        mKeys[index] = key;
        mValues[index] = value;
        mSize++;
    }

    private int[] mKeys;
    // Java-changed: was T[] but we can't instantiate generic arrays on Java.
    private Object[] mValues;
    private int mSize;

    private T mDefaultValue = null;

    private static final int DEFAULT_CAPACITY = 16;
}

