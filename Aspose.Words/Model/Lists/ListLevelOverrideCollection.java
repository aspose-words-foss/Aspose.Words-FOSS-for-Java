// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/01/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import java.util.Iterator;
import com.aspose.HashSetGeneric;
import com.aspose.Pair;
import java.util.ArrayList;


class ListLevelOverrideCollection implements Iterable<ListLevelOverride>
{
    void add(ListLevelOverride item)
    {
        msArrayList.add(mItems, item);
    }

    void remove(ListLevelOverride item)
    {
        mItems.remove(item);
    }

    void removeAt(int index)
    {
        mItems.remove(index);
    }

    public Iterator<ListLevelOverride> iterator()
    {
        return mItems.iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Compares with the specified list level overrides.
    /// </summary>
    public boolean equals(ListLevelOverrideCollection listLevelOverrides)
    {
        return equalsCore(listLevelOverrides, new HashSetGeneric<Pair>());
    }

    /// <summary>
    /// Core method for comparing with the specified list level overrides.
    /// </summary>
    /// <param name="listLevelOverrides">List level overrides that will be compared with this list level overrides.</param>
    /// <param name="alreadyComparedLinkedStyles">HashSet for collecting already compared styles to avoid dead loops.</param>
    boolean equalsCore(ListLevelOverrideCollection listLevelOverrides, HashSetGeneric<Pair> alreadyComparedLinkedStyles)
    {
        if (mItems.size() != listLevelOverrides.getCount())
            return false;

        for (int i = 0; i < mItems.size(); i++)
        {
            if (!mItems.get(i).equals(listLevelOverrides.get(i), alreadyComparedLinkedStyles))
                return false;
        }

        return true;
    }

    /// <summary>
    /// Calculates hash code for this object.
    /// </summary>
    /// <dev>
    /// To be compatible with the <see cref="Equals(ListLevelOverrideCollection)"/> method, only properties that
    /// affect visual representation of the list can be included into the calculation. Properties like object name
    /// or ID should be ignored.
    /// </dev>
    public /*override*/ int hashCode()
    {
        int hashCode = 0;

        for (ListLevelOverride item : mItems)
            hashCode = (hashCode * 397) ^ item.hashCode();

        return hashCode;
    }

    ListLevelOverride get(int index) { return mItems.get(index); }

    int getCount() { return mItems.size(); }

    void clear()
    {
        mItems.clear();
    }

    private /*final*/ ArrayList<ListLevelOverride> mItems = new ArrayList<ListLevelOverride>();
}

