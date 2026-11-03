// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/05/2007 by Vladimir Averkin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Comparator;
import java.util.Iterator;


public final class OpcPackagePartCollection implements Iterable<OpcPackagePart>
{
    public OpcPackagePartCollection()
    {
        // Have to use sorted string list to ensure the same order of items in .NET and Java so the golds are the same.
        mItems = new SortedStringListGeneric<OpcPackagePart>(false);
    }

    /// <summary>
    /// Creates the opc package part collection using a specific comparator.
    /// </summary>
    public OpcPackagePartCollection(Comparator<String> comparer)
    {
        // Have to use sorted string list to ensure the same order of items in .NET and Java so the golds are the same.
        mItems = new SortedStringListGeneric<OpcPackagePart>(comparer);
    }

    public int getCount() { return mItems.getCount(); }

    public void add(OpcPackagePart part)
    {
        mItems.add(part.getName(), part);
    }

    public boolean contains(String name)
    {
        return mItems.containsKey(name);
    }

    public OpcPackagePart get(String name) { return DictionaryExtension.getValueOrNull(mItems, name); }

    public void remove(String name)
    {
        mItems.remove(name);
    }

    public Iterator<OpcPackagePart> iterator()
    {
        return mItems.getValues().iterator();
    }
    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    private /*final*/ SortedStringListGeneric<OpcPackagePart> mItems;
}

