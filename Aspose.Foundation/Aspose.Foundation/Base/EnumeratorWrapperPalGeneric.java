// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 22/11/2017 by Alexey Butalov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Iterator;


/// <summary>
/// If you want to use <see cref="IEnumerator{T}"/> methods <see cref="IEnumerator{T}.MoveNext"/> and <see cref="IEnumerator{T}.Current"/>
/// in your C# code directly, then you need to wrap your enumerator instance into an object of this class to achieve autoportability to Java.
/// </summary>
public class EnumeratorWrapperPalGeneric<T>
{
    public EnumeratorWrapperPalGeneric(Iterator<T> wrappedEnumerator)
    {
        mWrappedEnumerator = wrappedEnumerator;
    }

    public boolean moveNext()
    {
        if (mWrappedEnumerator.hasNext())
        {
            mCurrent = mWrappedEnumerator.next();
            return true;
        }
        else
        {
            mCurrent = null;
            return false;
        }
    }

    public void reset()
    {
        throw new UnsupportedOperationException();
    }

    public T getCurrent() { return mCurrent; }

    protected Iterator getWrappedEnumerator() { return mWrappedEnumerator; }

    private /*final*/ Iterator<T> mWrappedEnumerator;
    private T mCurrent;
}
