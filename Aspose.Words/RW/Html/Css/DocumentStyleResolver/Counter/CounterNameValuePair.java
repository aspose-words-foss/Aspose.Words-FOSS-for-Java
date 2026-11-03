// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/11/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;

/// <summary>
/// An auxiliary class used during parsing of CSS counter modifications. Countains a counter name and a related value. 
/// </summary>
class CounterNameValuePair
{
    CounterNameValuePair(String counterName, int value)
    {
        Debug.assertTrue(StringUtil.hasChars(counterName));

        mCounterName = counterName;
        mValue = value;
    }

    String getCounterName() { return mCounterName; }

    int getValue() { return mValue; }

    private /*final*/ String mCounterName;

    private /*final*/ int mValue;
}
