// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/19/2011 by Denis Darkin 

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ObjToIntDictionary;
import com.aspose.Debug;


/// <summary>
/// Counts references of objects <see cref="object"/>, and allows checking if a particular object has been referenced or not.
/// </summary>
class ReferenceCounter
{
    /// <summary>
    /// Resets counters for any references.
    /// </summary>
    void reset()
    {
        mStorage.clear();
    }

    /// <summary>
    /// Increment reference counter for given obj by one.
    /// </summary>
    void incrementReference(Object obj)
    {
        int number = mStorage.get(obj);
        if (!ObjToIntDictionary.isNullSubstitute(number))
        {
            Debug.assertTrue(number != Integer.MAX_VALUE);
            mStorage.set(obj, ++number);
        }
        else
        {
            mStorage.add(obj, 1);
        }
    }

    /// <summary>
    /// Decrement reference counter for a given obj by one.
    /// </summary>
    void decrementReference(Object  obj)
    {
        int number = mStorage.get(obj);
        if (!ObjToIntDictionary.isNullSubstitute(number))
        {
            if (number > 0)
                number--;

            if (number == 0)
                mStorage.remove(obj);
            else
                mStorage.set(obj, number);
        }
    }

    /// <summary>
    /// Returns true if this object has non-zero reference counter.
    /// </summary>
    boolean isReferenced(Object obj)
    {
        return mStorage.containsKey(obj);
    }

    /// <summary>
    /// Returns a collection of objects containing non-zero reference count.
    /// </summary>
    Iterable<Object> getReferencedObjects() { return mStorage.getKeys(); }

    private /*final*/ ObjToIntDictionary<Object> mStorage = new ObjToIntDictionary<Object>();
}

