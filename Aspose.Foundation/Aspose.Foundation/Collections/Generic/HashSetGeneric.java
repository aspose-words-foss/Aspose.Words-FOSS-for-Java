// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2011 by Andrey Soldatov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.HashMap;
import com.aspose.ms.System.Collections.msDictionary;
import java.util.Iterator;


/// <summary>
/// A set is a collection that contains no duplicate elements, and whose elements are in no particular order.
///
/// The HashSet is a replacement of HashSet class existing in .NET 3.5 and higher. So its methods are
/// created compatible with this class. For now, not all HashSet operations are implemented, check interfaces of .NET
/// HashSet operations when adding new ones to this class.
/// </summary>
public class HashSetGeneric<T> implements ISetGeneric<T>
{
    /// <summary>
    /// Initializes a new, empty set object using the default initial capacity.
    /// </summary>
    public HashSetGeneric()
    {
        mDictionary = new HashMap<T, Boolean>();
    }

    /// <summary>
    /// Initializes a new, empty set object using the specified initial capacity.
    /// </summary>
    public HashSetGeneric(int capacity)
    {
        mDictionary = new HashMap<T, Boolean>(capacity);
    }

    public HashSetGeneric(Iterable<T> items)
    {
        mDictionary = new HashMap<T, Boolean>();
        for (T item : items)
            add(item);
    }

    /// <summary>
    /// Adds the specified element to a set object.
    /// Returns <c>true</c> if the element is added or <c>false</c> if the element is already present.
    /// </summary>
    public boolean add(T item)
    {
        if (mDictionary.containsKey(item))
            return false;

        msDictionary.add(mDictionary, item, false);
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
    public boolean contains(T item)
    {
        return mDictionary.containsKey(item);
    }

    /// <summary>
    /// Removes the specified element from a set object.
    /// Returns <c>true</c> if the element is successfully found and removed; otherwise, <c>false</c>.
    /// </summary>
    public boolean remove(T item)
    {
        if (!mDictionary.containsKey(item))
            return false;

        msDictionary.remove(mDictionary, item);
        return true;
    }

    /// <summary>
    /// Returns an enumerator that can iterate through a set object.
    /// </summary>
    public Iterator<T> iterator()
    {
        return mDictionary.keySet().iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Gets the number of elements contained in a set object.
    /// </summary>
    public int getCount() { return mDictionary.size(); }

    /// <summary>
    /// Underlying container.
    /// </summary>
    private /*final*/ HashMap<T, Boolean> mDictionary;
}

