// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/11/2022 by amorozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.SortedIntegerListGeneric;


/// <summary>
/// Implements few optimization hacks to increase performance of FindReplace and Comparison features.
/// </summary>
class NodeIndex extends SortedIntegerListGeneric<Node>
{
    /// <summary>
    /// Do low-level keys shift.
    /// </summary>
    /// <remarks>
    /// Adds given value for all keys starting from given.
    /// </remarks>
    void shift(int key, int len)
    {
        int index = indexOfKey(key);

        if (index != -1)
            for (int i = index; i < getCount(); i++)
                getKeys()[i] += len;
    }

    /// <summary>
    /// Replaces entry with new key/value pair.
    /// </summary>
    public void replace(int key, int newKey, Node value)
    {
        int index = indexOfKey(key);

        if(index == -1)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "key");

        // Previous key must be less than new key.
        if ((index > 0) && (getKeys()[index - 1] >= newKey))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "newKey");

        // Next key must be greater than new key.
        if ((index < getCount() - 1) && (getKeys()[index + 1] <= newKey))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "newKey");

        getKeys()[index] = newKey;
        getValues()[index] = value;
    }
}

