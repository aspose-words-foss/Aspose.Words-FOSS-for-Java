// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/12/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Represents an unsupported version vector (a version number) of <see cref="Feature"/>. We currently support only a subset
/// of all possible version vector variants (see <see cref="NumericVersionVector"/>. But we have to parse unsupported 
/// version vectors too, because if we failed to do so, we could fail to parse otherwise valid conditional expressions.
/// </summary>
class UnsupportedVersionVector extends VersionVector
{
    UnsupportedVersionVector(String text)
    {
        Debug.assertTrue(text != null);
        mText = text;
    }

    /*override*/ boolean compareTo(VersionVector other, /*ComparisonOperation*/int operation)
    {
        return false;
    }

    public /*override*/ String toString()
    {
        return mText;
    }

    private /*final*/ String mText;
}

