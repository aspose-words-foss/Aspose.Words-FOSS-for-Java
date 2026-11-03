// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/05/2011 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.RandomUtil;
import com.aspose.HashSetGeneric;


/// <summary>
/// Handles unique integer IDs.
/// Provides facilities to:
/// - create new unique IDs
/// - track uniqueness of existing IDs
/// </summary>
class UniqueIdManager
{
    /// <summary>
    /// Tries to add id to the set of unique ids.
    /// If such id is already present, then generates a new one and returns it.
    /// </summary>
    int addUniqueId(int id)
    {
        int result = id;

        if (!mUsedIds.add(id))
            result = addUniqueId(generateInteger());

        return result;
    }

    /// <summary>
    /// Generate a new Id for an object.
    /// </summary>
    int getUniqueId()
    {
        return addUniqueId(generateInteger());
    }

    /// <summary>
    /// Generate a new positive Pseudo-random integer that is not random in TEST builds and pseudo-random in RELEASE builds.
    /// </summary>
    static int generateInteger()
    {
        // andrnosk: WORDSNET-7613 Return only positive values, because for example MS Word 2010 does not allow negative ID for DrawingML.
        return  Math.abs(RandomUtil.newGuid().hashCode());
    }

    private /*final*/ HashSetGeneric<Integer> mUsedIds = new HashSetGeneric<Integer>();
}

