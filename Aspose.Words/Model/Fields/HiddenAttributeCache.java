// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/08/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Convert;
import com.aspose.IntToIntDictionary;


/// <summary>
/// Represents a cache of hidden attribute values.
/// </summary>
class HiddenAttributeCache
{
    boolean getHiddenAttribute(Inline node)
    {
        int hashCode = node.hashCode();

        if (mCache.containsKey(hashCode))
            return Convert.toBoolean(mCache.get(hashCode));

        boolean isHidden = node.isHiddenOrDeleted();
        mCache.add(hashCode, Convert.toInt32(isHidden));

        return isHidden;
    }

    void invalidate()
    {
        mCache.clear();
    }

    private /*final*/ IntToIntDictionary mCache = new IntToIntDictionary();
}

