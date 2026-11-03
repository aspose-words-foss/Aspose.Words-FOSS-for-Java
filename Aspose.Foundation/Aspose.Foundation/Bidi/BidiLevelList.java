// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/01/2018 by Victor Chebotok

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;

/// <summary>
/// A read-only list of <see cref="BidiLevel"/> elements.
/// </summary>
public class BidiLevelList
{
    /// <summary>
    /// Constructor. Creates an empty list.
    /// </summary>
    public BidiLevelList()
    {
        mLevels = new /*BidiLevel*/int[0];
    }

    /// <summary>
    /// Constructor. Creates a list with one element.
    /// </summary>
    public BidiLevelList(/*BidiLevel*/int level)
    {
        mLevels = new /*BidiLevel*/int[] { level };
    }

    /// <summary>
    /// Constructor. Creates a read-only wrapper for an array.
    /// </summary>
    public BidiLevelList(/*BidiLevel*/int[] levels)
    {
        mLevels = levels;
    }

    public /*override*/ boolean equals(Object other)
    {
        if (msObject.referenceEquals(null, other))
        {
            return false;
        }
        if (msObject.referenceEquals(this, other))
        {
            return true;
        }
        if (other.getClass() != BidiLevelList.class)
        {
            return false;
        }
        return equals((BidiLevelList)other);
    }

    boolean equals(BidiLevelList other)
    {
        if (msObject.referenceEquals(null, other))
        {
            return false;
        }
        if (msObject.referenceEquals(this, other))
        {
            return true;
        }
        if (mLevels.length != other.mLevels.length)
        {
            return false;
        }
        for (int i = 0; i < mLevels.length; i++)
        {
            if (mLevels[i] != other.mLevels[i])
            {
                return false;
            }
        }
        return true;
    }

    public /*override*/ int hashCode()
    {
        /*unchecked*/
        {
            int hashCode = -1220689852;
            for (int i = 0; i < mLevels.length; i++)
            {
                hashCode = (hashCode * -1521134295) + (int)mLevels[i];
            }
            return hashCode;
        }
    }

    public int getLength() { return mLevels.length; }

    public /*BidiLevel*/int get(int index) { return mLevels[index]; }

    private /*final*/ /*BidiLevel*/int[] mLevels;
}

