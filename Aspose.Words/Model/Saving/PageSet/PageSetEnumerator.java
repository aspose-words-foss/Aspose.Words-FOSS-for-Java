// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2020 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.NoSuchElementException;
import java.util.Iterator;


abstract class PageSetEnumerator implements Iterable<Integer>, Iterator<Integer>
{
    public Iterator<Integer> iterator()
    {
        return this;
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    public void dispose()
    {
        // Nothing to dispose.
    }

    public abstract boolean hasNext();

    public abstract void reset();

    public abstract Integer next();

    //JAVA-deleted Namesake Explicit Interface Implementation property:
    //Object IEnumerator.Current

    //JAVA-added for IEnumerator emulation
    public void remove() 
    {
        throw new UnsupportedOperationException();
    }
}

