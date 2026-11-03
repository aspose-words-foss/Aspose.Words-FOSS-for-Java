// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/06/2015 by Alexey Morozov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.msObject;

/// <summary>
/// Simple class to hold pair of object.
/// </summary>
/// <remarks>
/// This class is intended to be transparent to comparison and compares contained objects instead of self.
/// </remarks>
public final class Pair
{
    public Pair(Object first, Object second)
    {
        mFirst = first;
        mSecond = second;
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (obj == null)
            return false;

        Pair rhs = ms.as(obj, Pair.class);
        if (rhs == null)
            return false;

        return equals(rhs);
    }

    public boolean equals(Pair rhs)
    {
        if (rhs == null)
            return false;

        return msObject.referenceEquals(mFirst, rhs.mFirst) && msObject.referenceEquals(mSecond, rhs.mSecond);
    }

    public /*override*/ int hashCode()
    {
        return mFirst.hashCode() ^ mSecond.hashCode();
    }

    public Object getFirst() { return mFirst; }


    public Object getSecond() { return mSecond; }

    private /*final*/ Object mFirst;
    private /*final*/ Object mSecond;
}

