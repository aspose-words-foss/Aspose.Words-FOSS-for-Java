// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/08/2007 by Vladimir Averkin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Iterator;
import com.aspose.ms.System.Collections.SortedList;


public class PackagePartComparisonInfoCollection implements Iterable<PackagePartComparisonInfo>
{
    void add(PackagePartComparisonInfo info)
    {
        mInfoTable.add(info.getName(), info);
    }

    PackagePartComparisonInfo get(String name) { return DictionaryExtension.getValueOrNull(mInfoTable, name); }

    public Iterator<PackagePartComparisonInfo> iterator()
    {
        return mInfoTable.getValues().iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    public int getCount() { return mInfoTable.getCount(); }

    private /*final*/ SortedList<String, PackagePartComparisonInfo> mInfoTable =
        new SortedList<String, PackagePartComparisonInfo>();
}

