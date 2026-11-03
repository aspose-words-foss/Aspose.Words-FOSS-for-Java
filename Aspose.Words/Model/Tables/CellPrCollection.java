// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/05/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import java.util.Iterator;
import java.util.ArrayList;


/// <summary>
/// Typed collection of CellPr.
/// </summary>
class CellPrCollection implements IComplexAttr, Iterable<AttrCollection>
{
    void add(AttrCollection item)
    {
        msArrayList.add(mItems, item);
    }

    void insert(int index, AttrCollection item)
    {
        mItems.add(index, item);
    }


    public /*override*/ int hashCode()
    {
        int hashCode = 0;

        for (AttrCollection collection : mItems)
        {
            if (collection != null)
                hashCode = (hashCode * 397) ^ collection.hashCode();
        }

        return hashCode;
    }

    public Iterator<AttrCollection> iterator()
    {
        return mItems.iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    CellPr get(int index) { return (CellPr)mItems.get(index); }
    void set(int index, CellPr value) { mItems.set(index, value); }

    public boolean isInheritedComplexAttr() { return false; }

    int getCount() { return mItems.size(); }

    public IComplexAttr deepCloneComplexAttr()
    {
        CellPrCollection lhs = new CellPrCollection();
        for (int index = 0; index < mItems.size(); index++)
            lhs.add(AttrCollectionExtension.deepClone(this.get(index)));
        return lhs;
    }

    private /*final*/ ArrayList<AttrCollection> mItems = new ArrayList<AttrCollection>();
}

