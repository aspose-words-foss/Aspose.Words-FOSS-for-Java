// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/05/2024 by Ilya Navrotskiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.Map;
import com.aspose.ms.System.Collections.DictionaryEntry;


/// <summary>
/// Implements <see cref="IEnumerator"/> for <see cref="StringListGeneric{TValue}"/> class.
/// </summary>
public class StringListEnumerator<TValue> implements Iterator<Map.Entry<String, TValue>>
{
    /// <summary>
    /// Initializes new instance of <see cref="StringListEnumerator{TValue}"/> class.
    /// </summary>
    public StringListEnumerator(StringListGeneric<TValue> list)
    {
        mList = list;
        mCurIndex = -1;
    }

    //>>>>>>>> #region  IEnumerator implementation

    public boolean hasNext()
    {
        if (++mCurIndex >= mList.getCount())
            return false;

        mCurItem = new DictionaryEntry<>(mList.getKey(mCurIndex), mList.getByIndex(mCurIndex));
        return true;
    }

    public void reset()
    {
        mCurIndex = -1;
    }

    public void /*IDisposable.*/dispose()
    {
    }

    //JAVA-deleted Namesake Explicit Interface Implementation property:
    //Object IEnumerator.Current

    public  Map.Entry<String, TValue> next() { return mCurItem; }

    //<<<<<<<< #endregion 

    private /*final*/ StringListGeneric<TValue> mList;
    private int mCurIndex;
    private Map.Entry<String, TValue> mCurItem;

    //JAVA-added for IEnumerator emulation
    public void remove() 
    {
        throw new UnsupportedOperationException();
    }
}

