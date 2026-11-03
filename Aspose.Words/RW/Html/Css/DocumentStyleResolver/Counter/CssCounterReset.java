// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/11/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;

/// <summary>
/// Represents a modification that resets a counter to an initial value. Corresponds to the 'counter-reset' CSS property.
/// </summary>
class CssCounterReset extends CssCounterModification
{
    CssCounterReset(String counterName, int initialValue)
    {
        Debug.assertTrue(StringUtil.hasChars(counterName));

        mCounterName = counterName;
        mInitialValue = initialValue;
    }

    /*override*/ void applyTo(CssCounters counters)
    {
        counters.reset(mCounterName, mInitialValue);
    }


    private /*final*/ String mCounterName;

    private /*final*/ int mInitialValue;
}

