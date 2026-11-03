// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/09/2013 by Ivan Lyagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Iterator;


/// <summary>
/// Represents <see cref="ISetGeneric{T}"/> which elements are case-insensitive strings.
/// This is a complementary class to HashSet.
/// </summary>
public class CaseInsensitiveHashSet implements ISetGeneric<String>
{
    /// <summary>
    /// Initializes a new, empty set object using the default initial capacity.
    /// </summary>
    public CaseInsensitiveHashSet()
    {
        mDictionary = new StringToObjDictionary<String>(false);
    }

    /// <summary>
    /// Initializes a new, empty set object using the specified initial capacity.
    /// </summary>
    public CaseInsensitiveHashSet(int capacity)
    {
        mDictionary = new StringToObjDictionary<String>(capacity, false);
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="CaseInsensitiveHashSet"/> class that contains specified items.
    /// </summary>
    public CaseInsensitiveHashSet(/*params*/ String... items)
    {
        mDictionary = new StringToObjDictionary<String>(items.length, false);
        for (String item : items)
            add(item);
    }

    /// <summary>
    /// Adds the specified element to a set object.
    /// Returns <c>true</c> if the element is added or <c>false</c> if the element is already present.
    /// </summary>
    public boolean add(String item)
    {
        String key = getDictionaryKey(item);
        if (mDictionary.containsKey(key))
            return false;

        // Value of a dictionary is used to make possible to iterate over the dictionary
        // returning null (not NullKey string) where appropriate.
        mDictionary.add(key, item);
        return true;
    }

    /// <summary>
    /// Removes all elements from a set object.
    /// </summary>
    public void clear()
    {
        mDictionary.clear();
    }

    /// <summary>
    /// Determines whether a set contains the specified element.
    /// </summary>
    public boolean contains(String item)
    {
        return mDictionary.containsKey(getDictionaryKey(item));
    }

    /// <summary>
    /// Removes the specified element from a set object.
    /// Returns <c>true</c> if the element is successfully found and removed; otherwise, <c>false</c>.
    /// </summary>
    public boolean remove(String item)
    {
        return mDictionary.remove(getDictionaryKey(item));
    }

    /// <summary>
    /// Returns an enumerator that can iterate through a set object.
    /// </summary>
    public Iterator<String> iterator()
    {
        return mDictionary.getValues().iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    private static String getDictionaryKey(Object item)
    {
        return (item != null)
            ? (String)item
            : NULL_KEY;
    }

    /// <summary>
    /// Gets the number of elements contained in a set object.
    /// </summary>
    public int getCount() { return mDictionary.getCount(); }

    /// <summary>
    /// Underlying container.
    /// </summary>
    private /*final*/ StringToObjDictionary<String> mDictionary;

    /// <summary>
    /// A key value corresponding to a null item. Actually this is a hack, but let's hope that
    /// this value will not match a real item, which can be neglected according to GUID concept.
    /// </summary>
    private static final String NULL_KEY = "{62C5CE33-64E0-4EEC-9E16-1CA57A484BD2}";
}

