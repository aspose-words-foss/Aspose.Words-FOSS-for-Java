// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/05/2024 by Ilya Navrotskiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import java.util.Iterator;
import com.aspose.words.ref.Ref;


/// <summary>
/// Represents generic unsorted list of strings mapped to <see cref="TValue"/>.
/// </summary>
/// <remarks>
/// This is implementation based on <see cref="Dictionary{TKey,TValue}"/> cache,
/// so that the reading from the list is very fast in case of unique string keys (O(1)).
/// The string keys can be non-unique. In this case the cache is not applicable.
/// </remarks>
public class StringListGeneric<TValue> implements Iterable<Map.Entry<String, TValue>>
{
    /// <summary>
    /// Creates instance of a <see cref="StringListGeneric{TValue}"/> class.
    /// </summary>
    public StringListGeneric()
    {
        mStringList = new ArrayList<String>();
        mValuesList = new ArrayList<TValue>();

        mCache = new HashMap<String, TValue>();
    }

    /// <summary>
    /// Creates instance of <see cref="StringListGeneric{TValue}"/> from specified arrays.
    /// </summary>
    public StringListGeneric(String[] keys, TValue[] values)
    {
        ArgumentUtil.checkNotNull(keys, "keys");
        ArgumentUtil.checkNotNull(values, "values");

        if (keys.length != values.length)
            throw new IllegalArgumentException("Keys length does not equal to values length.");

        mStringList = new ArrayList<String>(keys.length);
        mValuesList = new ArrayList<TValue>(values.length);
        mCache = new HashMap<String, TValue>(keys.length);

        for (int i = 0; i < keys.length; i++)
        {
            String key = keys[i];
            msArrayList.add(mStringList, key);

            TValue value = values[i];
            msArrayList.add(mValuesList, value);

            mCache.put(key, value);
        }
    }

    /// <summary>
    /// Creates instance from other <see cref="StringListGeneric{TValue}"/> collection.
    /// </summary>
    public StringListGeneric(StringListGeneric<TValue> other)
    {
    	this();
	
        ArgumentUtil.checkNotNull(other, "other");

        for (int i = 0; i < other.getCount(); i++)
        {
            String key = other.mStringList.get(i);
            TValue value = other.mValuesList.get(i);

            msArrayList.add(mStringList, key);
            msArrayList.add(mValuesList, value);
            mCache.put(key, value);
        }
    }

    /// <summary>
    /// Returns value at a specified index.
    /// </summary>
    public TValue getByIndex(int index)
    {
        return mValuesList.get(index);
    }

    /// <summary>
    /// Removes key and value at a specified index.
    /// </summary>
    public void removeAt(int index)
    {
        String key = mStringList.get(index);

        mStringList.remove(index);
        mValuesList.remove(index);

        msDictionary.remove(mCache, key);
    }

    /// <summary>
    /// Returns the zero-based index of the first occurrence of a key in the <see cref="StringListGeneric{TValue}"/>.
    /// </summary>
    public int indexOfKey(String key)
    {
        return mStringList.indexOf(key);
    }

    /// <summary>
    /// Determines whether an element with a specified key is in the <see cref="StringListGeneric{TValue}"/>.
    /// </summary>
    public boolean containsKey(String key)
    {
        if (isValidCache())
            return mCache.containsKey(key);

        return (mStringList.indexOf(key) != -1);
    }

    /// <summary>
    /// Adds element with a specified key and value to the end of the <see cref="StringListGeneric{TValue}"/>.
    /// </summary>
    public void add(String key, TValue value)
    {
        msArrayList.add(mStringList, key);
        msArrayList.add(mValuesList, value);

        mCache.put(key, value);
    }

    /// <summary>
    /// Removes last occurence of element with a specified key from the <see cref="StringListGeneric{TValue}"/>.
    /// </summary>
    public void remove(String key)
    {
        // Fast check if all keys in the list are unique and the key does not exist.
        if (isValidCache() && !mCache.containsKey(key))
            return;

        int index = mStringList.lastIndexOf(key);
        if (index == -1)
            return;

        mStringList.remove(index);
        mValuesList.remove(index);

        msDictionary.remove(mCache, key);
    }

    /// <summary>
    /// Returns value corresponded to a last occurence of a specified key from the <see cref="StringListGeneric{TValue}"/> safely.
    /// </summary>
    /// <remarks>
    /// Does not throw if the specified key is not within the collection, but returns a specified default value.
    /// </remarks>
    public TValue getValueSafe(String key, TValue defaultValue)
    {
        TValue value = null;
        Ref<TValue> referenceToValue = new Ref<TValue>(value);
        boolean outRefCondition0 = msDictionary.tryGetValue(mCache, key, /*out*/ referenceToValue);
        value = referenceToValue.get();
        if (outRefCondition0)
            return value;

        // The cache is valid and value is not found. In this case we return the default value.
        if (isValidCache())
            return defaultValue;

        // The cache is invalid. Lets get value directly from the unsorted list collection.
        int index = mStringList.lastIndexOf(key);
        if (index == -1)
            return defaultValue;

        return mValuesList.get(index);
    }

    /// <summary>
    /// Returns key at a specified index.
    /// </summary>
    public String getKey(int index)
    {
        return mStringList.get(index);
    }

    /// <summary>
    /// Sorts list by key.
    /// </summary>
    public StringListGeneric<TValue> sort()
    {
        for (int i = 0; i < mStringList.size() - 1; i++)
        {
            for (int j = i + 1; j < mStringList.size(); j++)
            {
                int comparision = msString.compare(mStringList.get(i), mStringList.get(j), StringComparison.ORDINAL);
                if (comparision > 0)
                {
                    swap(mStringList, i, j);
                    swap(mValuesList, i, j);
                }
            }
        }

        return this;
    }

    /// <summary>
    /// Swaps two values in <see cref="StringListGeneric{TValue}"/>.
    /// </summary>
    private static <T> void swap(ArrayList<T> list, int indexA, int indexB)
    {
        T tmp = list.get(indexA);
        list.set(indexA, list.get(indexB));
        list.set(indexB, tmp);
    }

    //>>>>>>>> #region  IEnumerable implementation

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    public Iterator<Map.Entry<String, TValue>> iterator()
    {
        return new StringListEnumerator<TValue>(this);
    }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Gets or sets the value associated with the specified string key.
    /// </summary>
    public Object get(String key)
    {
        TValue value = null;
        Ref<TValue> referenceToValue = new Ref<TValue>(value);
        boolean outRefCondition1 = msDictionary.tryGetValue(mCache, key, /*out*/ referenceToValue);
        value = referenceToValue.get();
        if (outRefCondition1)
            return value;

        // The cache is valid and value is not found.
        if (isValidCache())
            return null;

        // The cache is invalid. Lets get value directly from the unsorted list collection.
        int index = mStringList.lastIndexOf(key);
        if (index == -1)
            return null;

        return mValuesList.get(index);
    }
    public void set(String key, Object value)
    {
        // Fast insert in case of all keys in the list are unique (i.e., the cache is valid).
        if (isValidCache() && !mCache.containsKey(key))
        {
            msArrayList.add(mStringList, key);
            msArrayList.add(mValuesList, (TValue)value);
        }
        else
        {
            int index = mStringList.lastIndexOf(key);
            if (index == -1)
            {
                msArrayList.add(mStringList, key);
                msArrayList.add(mValuesList, (TValue)value);
            }
            else
            {
                mValuesList.set(index, (TValue)value);
            }
        }

        mCache.put(key, (TValue)value);
    }

    public int getCount() { return mStringList.size(); }

    /// <summary>
    /// Returns true if the cache <see cref="mCache"/> is valid.
    /// </summary>
    private boolean isValidCache() { return (mCache.size() == mStringList.size()); }

    /// <summary>
    /// The collection of string keys in <see cref="StringListGeneric{TValue}"/>.
    /// </summary>
    private /*final*/ ArrayList<String> mStringList;

    /// <summary>
    /// The collection of <see cref="TValue"/> objects in <see cref="StringListGeneric{TValue}"/>.
    /// </summary>
    private /*final*/ ArrayList<TValue> mValuesList;

    /// <summary>
    /// The cache for fast access to <see cref="StringListGeneric{TValue}"/> when its keys are unique.
    /// </summary>
    private /*final*/ HashMap<String, TValue> mCache;
}

