// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/06/2024 by Victor Chebotok

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Enumerates entries of a package.
/// </summary>
public class ComparisonPackage
{
    public ComparisonPackage(PackageEntryList entries)
    {
        mEntries = entries;
        mEntryIndex = -1;
    }

    public boolean moveToNextEntry()
    {
        int nextIndex = mEntryIndex + 1;
        if (nextIndex >= mEntries.getCount())
        {
            return false;
        }
        mEntryIndex = nextIndex;
        return true;
    }

    public byte[] loadEntryToByteArray()
    {
        return mEntries.getEntryData(mEntryIndex);
    }

    public String getEntryFileName() { return mEntries.getEntryFileName(mEntryIndex); }

    private /*final*/ PackageEntryList mEntries;
    private int mEntryIndex;
}

