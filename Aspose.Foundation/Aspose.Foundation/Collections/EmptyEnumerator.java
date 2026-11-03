// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/10/2019 by Edward Voronov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.NoSuchElementException;
import java.util.Iterator;


public final class EmptyEnumerator<T> implements Iterator<T>
{
    private EmptyEnumerator()
    {
    }

    public void /*IDisposable.*/dispose()
    {
        // Do nothing
    }

    public boolean /*IEnumerator.*/hasNext()
    {
        return false;
    }

    public void /*IEnumerator.*/reset()
    {
        // Do nothing
    }

    public T /*IEnumerator<T>.*/next() { throw new IllegalStateException(); }

    // We can't create a static singleton instance with generic parameters in Java
    private static /*final*/ Iterator<Object> G_INSTANCE = new EmptyEnumerator<Object>();

    public static <T> Iterator<T> getInstance()
    {
        return (Iterator<T>)G_INSTANCE;
    }

    //JAVA-added for IEnumerator emulation
    public void remove() 
    {
        throw new UnsupportedOperationException();
    }
}

