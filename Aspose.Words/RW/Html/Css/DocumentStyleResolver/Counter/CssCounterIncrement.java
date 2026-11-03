// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/11/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;

/// <summary>
/// Represents a modification that increments a counter by a specified value.
/// Corresponds to the 'counter-increment' CSS property.
/// </summary>
class CssCounterIncrement extends CssCounterModification
{
    CssCounterIncrement(String counterName, int incrementValue)
    {
        Debug.assertTrue(StringUtil.hasChars(counterName));

        mCounterName = counterName;
        mIncrementValue = incrementValue;
    }

    /*override*/ void applyTo(CssCounters counters)
    {
        counters.increment(mCounterName, mIncrementValue);
    }


    private /*final*/ String mCounterName;

    private /*final*/ int mIncrementValue;
}

