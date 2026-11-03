// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/06/2024 by Victor Chebotok

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;


/// <summary>
/// Stores entries (inner files) of a package.
/// </summary>
public class PackageEntryList
{
    public void addEntry(String fileName, byte[] data)
    {
        msArrayList.add(mEntries, new Entry(fileName, data));
    }

    public String getEntryFileName(int index)
    {
        return mEntries.get(index).getFileName();
    }

    public byte[] getEntryData(int index)
    {
        return mEntries.get(index).getData();
    }

    public int getCount() { return mEntries.size(); }

    private static class Entry
    {
        Entry(String fileName, byte[] data)
        {
            mFileName = fileName;
            mData = data;
        }

        String getFileName() { return mFileName; };

        private String mFileName;

        byte[] getData() { return mData; };

        private byte[] mData;
    }

    private /*final*/ ArrayList<PackageEntryList.Entry> mEntries = new ArrayList<PackageEntryList.Entry>();
}

