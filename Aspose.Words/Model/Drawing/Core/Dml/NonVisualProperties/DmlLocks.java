// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/07/2014 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IntToObjDictionary;


/// <summary>
/// Specifies all locking properties for a Dml node.
/// 
/// We can use Flags instead of DmlLocks collection, but for roundtrip it is better to keep collection.
/// Sometimes MS Word writes lock with false value, that means lock is not applied, but it is still present in xml.
/// </summary>
class DmlLocks extends DmlExtensionListSource implements Cloneable
{
    DmlLocks deepClone()
    {
        DmlLocks lhs = (DmlLocks)memberwiseClone();

        if (mLocks != null)
        {
            lhs.mLocks = new IntToObjDictionary<Object>();
            IntToObjDictionary<Object>.Enumerator enumerator = mLocks.iterator();
            while (enumerator.moveNext())
                lhs.mLocks.add(enumerator.getCurrentKey(), enumerator.getCurrentValue());
        }

        lhs.setExtensions(cloneExtensions());

        return lhs;
    }

    void addLock(/*DmlLock*/int key, boolean value)
    {
        mLocks.set((int)key, value);
    }
    
    Object getLock(/*DmlLock*/int key)
    {
        return mLocks.get((int)key);
    }

    int getCount() { return mLocks.getCount(); }

    private IntToObjDictionary<Object> mLocks = new IntToObjDictionary<Object>();

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

