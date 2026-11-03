// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/02/2016 by Dmitry Burov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msArray;


/// <summary>
/// This is a copy of <see cref="SortedIntegerList" /> but short[] array is used for keys instead of int[] 
/// to reduce memory consumption. Fallback is added for cases when we try to put the key that greater than 
/// short.MaxValue: the keys are switched from short[] to int[].
/// See MailMergePerf.TimeMergeField().
/// </summary>
public class SortedShortListIntegerFallback implements Cloneable
{
    private short[] mShortKeys;
    private int[] mKeys;
    private Object[] mValues;
    private int mSize;

    // WORDSNET-21593 Reduce default capacity to optimize memory consumption.
    // WORDSNET-20975 In most cases AW is about 5% faster and takes 5-30% less memory if
    // set defaultCapacity to 2, instead of 16.
    protected /*virtual*/ int getDefaultCapacity() { return 16; }

    public SortedShortListIntegerFallback()
    {
        mShortKeys = null;
        mKeys = null;
        mValues = null;
    }

    public SortedShortListIntegerFallback(int initialCapacity)
    {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "initialCapacity");

        mShortKeys = new short[initialCapacity];
        mKeys = null;
        mValues = new Object[initialCapacity];
    }

    private void init()
    {
        if (mValues != null)
            return;

        mShortKeys = new short[getDefaultCapacity()];
        mKeys = null;
        mValues = new Object[getDefaultCapacity()];
    }

    private void switchToIntKeys()
    {
        init();
        mKeys = new int[mShortKeys.length];
        // Don't use System.arraycopy(shortKeys, 0, keys, 0, shortKeys.length) because it throws
        // java.lang.ArrayStoreException. shortKeys and keys are different types.
        for (int i = 0; i < mShortKeys.length; i++)
            mKeys[i] = mShortKeys[i];

        mShortKeys = null;
    }

    public /*virtual*/ void add(int key, Object value)
    {
        init();
        if (mKeys != null)
        {
            addInt(key, value);
        }
        else if (key > Short.MAX_VALUE)
        {
            switchToIntKeys();
            addInt(key, value);
        }
        else
        {
            addShort((short)key, value);
        }
    }

    private void addInt(int key, Object value)
    {
        init();
        int i = ArrayUtil.binarySearch(mKeys, 0, mSize, key);
        if (i >= 0)
            throw new IllegalArgumentException("duplicate");
        insertInt(~i, key, value);
    }

    private void addShort(short key, Object value)
    {
        init();
        int i = ArrayUtil.binarySearch(mShortKeys, 0, mSize, key);
        if (i >= 0)
            throw new IllegalArgumentException("duplicate");
        insertShort(~i, key, value);
    }

    public int getCapacity()
    {
        if (mValues == null)
            return 0;

        return mKeys != null ? mKeys.length : mShortKeys.length;
    }
    public void setCapacity(int value)
    {
        init();
        if (mKeys != null)
            setCapacityInt(value);
        else
            setCapacityShort(value);
    }

    private void setCapacityInt(int value)
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
                mKeys = new int[getDefaultCapacity()];
                mValues = new Object[getDefaultCapacity()];
            }
        }
    }

    private void setCapacityShort(int value)
    {
        if (value != mShortKeys.length)
        {
            if (value < mSize)
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");

            if (value > 0)
            {
                short[] newKeys = new short[value];
                Object[] newValues = new Object[value];
                if (mSize > 0)
                {
                    msArray.copy(mShortKeys, 0, newKeys, 0, mSize);
                    msArray.copy(mValues, 0, newValues, 0, mSize);
                }
                mShortKeys = newKeys;
                mValues = newValues;
            }
            else
            {
                mShortKeys = new short[getDefaultCapacity()];
                mValues = new Object[getDefaultCapacity()];
            }
        }
    }

    public /*virtual*/ int getCount() { return mValues == null ? 0 : mSize; }

    public /*virtual*/ void clear()
    {
        mSize = 0;

        mKeys = null;
        mShortKeys = null;
        mValues = null;
    }

    protected SortedShortListIntegerFallback createEmptyCopy()
    {
        if (mValues == null)
        {
            // fast copy of empty collection.
            return (SortedShortListIntegerFallback)this.memberwiseClone();
        }

        init();

        SortedShortListIntegerFallback sl = (SortedShortListIntegerFallback)this.memberwiseClone();
        sl.mSize = 0;

        if (mKeys != null)
            sl.mKeys = new int[mKeys.length];
        else
            sl.mShortKeys = new short[mShortKeys.length];

        sl.mValues = new Object[mValues.length];
        return sl;
    }

    public /*virtual*/ boolean contains(int key)
    {
        if (mValues == null)
            return false;

        return indexOfKey(key) >= 0;
    }

    /// <summary>
    /// Returns true if specified key is in the list.
    /// Performs binary search.
    /// </summary>
    public /*virtual*/ boolean containsKey(int key)
    {
        if (mValues == null)
            return false;

        // Yes, this is a SPEC'ed duplicate of Contains().
        return indexOfKey(key) >= 0;
    }

    /// <summary>
    /// Returns true if at least one of specified keys is in the list.
    /// Performs binary search.
    /// </summary>
    public /*virtual*/ boolean containsAnyKey(/*params*/ int... keys)
    {
        if (mValues == null)
            return false;

        for (int i = 0; i < keys.length; i++)
        {
            // Stop search on the first occurrence.
            if (containsKey(keys[i]))
                return true;
        }

        return false;
    }

    public boolean containsValue(Object value)
    {
        if (mValues == null)
            return false;

        return indexOfValue(value) >= 0;
    }

    private void ensureCapacityInt(int min)
    {
        int newCapacity = mKeys.length == 0 ? 16 : mKeys.length * 2;
        if (newCapacity < min)
            newCapacity = min;
        setCapacityInt(newCapacity);
    }

    private void ensureCapacityShort(int min)
    {
        int newCapacity = mShortKeys.length == 0 ? 16 : mShortKeys.length * 2;
        if (newCapacity < min)
            newCapacity = min;
        setCapacityShort(newCapacity);
    }

    public /*virtual*/ Object getByIndex(int index)
    {
        if (mValues == null || index < 0 || index >= mSize)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "index");
        return mValues[index];
    }

    public /*virtual*/ int getKey(int index)
    {
        if (mValues == null || index < 0 || index >= mSize)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "index");
        return (mKeys != null) ? mKeys[index] : mShortKeys[index];
    }

    public /*virtual*/ Object get(int key)
    {
        if (mValues == null)
            return null;

        int index = indexOfKey(key);
        return (index >= 0) ? mValues[index] : null;
    }
    public /*virtual*/ void set(int key, Object value)
    {
        init();

        if (mKeys != null)
        {
            setInt(key, value);
        }
        else if (key > Short.MAX_VALUE)
        {
            switchToIntKeys();
            setInt(key, value);
        }
        else
        {
            setShort((short)key, value);
        }
    }

    private void setInt(int key, Object value)
    {
        init();

        int i = ArrayUtil.binarySearch(mKeys, 0, mSize, key);
        if (i >= 0)
        {
            mValues[i] = value;
            return;
        }
        insertInt(~i, key, value);
    }

    private void setShort(short key, Object value)
    {
        init();

        int i = ArrayUtil.binarySearch(mShortKeys, 0, mSize, key);
        if (i >= 0)
        {
            mValues[i] = value;
            return;
        }
        insertShort(~i, key, value);
    }

    public /*virtual*/ int indexOfKey(int key)
    {
        if (mValues == null)
            return -1;

        int ret;

        if (mKeys != null)
            ret = ArrayUtil.binarySearch(mKeys, 0, mSize, key);
        else
            ret = (key > Short.MAX_VALUE) ? -1 : ArrayUtil.binarySearch(mShortKeys, 0, mSize, (short)key);

        return ret >= 0 ? ret : -1;
    }

    public /*virtual*/ int indexOfValue(Object value)
    {
        if (mValues == null)
            return -1;

        return msArray.indexOf(mValues, value, 0, mSize);
    }

    private void insertInt(int index, int key, Object value)
    {
        init();

        if (mSize == mKeys.length)
            ensureCapacityInt(mSize + 1);
        if (index < mSize)
        {
            msArray.copy(mKeys, index, mKeys, index + 1, mSize - index);
            msArray.copy(mValues, index, mValues, index + 1, mSize - index);
        }
        mKeys[index] = key;
        mValues[index] = value;
        mSize++;
    }

    private void insertShort(int index, short key, Object value)
    {
        init();

        if (mSize == mShortKeys.length)
            ensureCapacityShort(mSize + 1);
        if (index < mSize)
        {
            msArray.copy(mShortKeys, index, mShortKeys, index + 1, mSize - index);
            msArray.copy(mValues, index, mValues, index + 1, mSize - index);
        }
        mShortKeys[index] = key;
        mValues[index] = value;
        mSize++;
    }

    public /*virtual*/ void removeAt(int index)
    {
        if (mValues == null || index < 0 || index >= mSize)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "index");

        mSize--;
        if (index < mSize)
        {
            if (mKeys != null)
                msArray.copy(mKeys, index + 1, mKeys, index, mSize - index);
            else
                msArray.copy(mShortKeys, index + 1, mShortKeys, index, mSize - index);

            msArray.copy(mValues, index + 1, mValues, index, mSize - index);
        }
        if (mKeys != null)
            mKeys[mSize] = 0;
        else
            mShortKeys[mSize] = 0;

        mValues[mSize] = null;
    }

    public /*virtual*/ void remove(int key)
    {
        int i = indexOfKey(key);
        if (i >= 0)
            removeAt(i);
    }

    public /*virtual*/ void setByIndex(int index, Object value)
    {
        init();

        if (index < 0 || index >= mSize)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "index");
        mValues[index] = value;
    }

    public void trimToSize()
    {
        setCapacity(mSize);
    }

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

