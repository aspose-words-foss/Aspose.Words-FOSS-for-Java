// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2006 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.SortedIntegerListGeneric;


/// <summary>
/// Holds rsid (Revision state identifier) table.
/// </summary>
class RsidTable
{
    /// <summary>
    /// Adds an RSID value to the RSID table unless this value is already in the table.
    /// </summary>
    void add(Object rsid)
    {
        if (rsid == null)
            return;

        if ((/*int*/Integer)rsid == Integer.MIN_VALUE)
            return;

        if ((/*int*/Integer)rsid == 0)
            return;

        if (!mRsidTable.containsKey((/*int*/Integer)rsid))
            mRsidTable.add((/*int*/Integer)rsid, 0);
    }
    
    /// <summary>
    /// Returns an RSID value by the specified index.
    /// </summary>
    int get(int index) { return mRsidTable.getKey(index); }
    
    /// <summary>
    /// Returns the total number of RSID values in the table.
    /// </summary>
    int getCount() { return mRsidTable.getCount(); }

    void clear()
    {
        mRsidTable.clear();
    }

    public RsidTable deepClone()
    {
        RsidTable lhs = new RsidTable();

        for(int i = 0; i < mRsidTable.getCount(); i++)
            lhs.mRsidTable.add(mRsidTable.getKey(i), 0);

        return lhs;
    }

    private /*final*/ SortedIntegerListGeneric<Object> mRsidTable = new SortedIntegerListGeneric<Object>();
}

