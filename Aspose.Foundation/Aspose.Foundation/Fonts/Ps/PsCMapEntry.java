// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/06/2015 by Alexey Noskov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msInt;


class PsCMapEntry implements Comparable<PsCMapEntry>
{
    PsCMapEntry(int charCode, int index)
    {
        mCharCode = charCode;
        mIndex = index;
    }

    public int compareTo(PsCMapEntry other)
    {
        return msInt.compareTo(getIndex(), other.getIndex());
    }

    int getCharCode() { return mCharCode; }

    int getIndex() { return mIndex; }

    private /*final*/ int mCharCode;
    private /*final*/ int mIndex;
}

