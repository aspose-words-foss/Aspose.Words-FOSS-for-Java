// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/29/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToIntBidirectionalMap;


/*static*/ class DmlEnumUtil
{
	/* Simulation of static class by using private constructor */
	private DmlEnumUtil()
	{}

    static StringToIntBidirectionalMap initHashtableWithValues(String[] keys, int[] values)
    {
        if (keys.length != values.length)
            throw new IllegalArgumentException("Length of keys and values arrays must be the same.");

        StringToIntBidirectionalMap dictionary = new StringToIntBidirectionalMap();

        for (int i = 0; i < keys.length; i++)
            dictionary.addEntry(keys[i], values[i]);

        return dictionary;
    }
}

