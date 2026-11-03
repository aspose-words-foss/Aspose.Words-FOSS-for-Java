// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/11/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a scoped value of a CSS counter.
/// </summary>
/// <remarks>
/// See http://www.w3.org/TR/2011/REC-CSS2-20110607/generate.html#scope to learn more about CSS counter scopes.
/// </remarks>
class CssCounterScope
{
    CssCounterScope(int initialValue)
    {
        mValue = initialValue;
    }

    void increment(int incrementValue)
    {
        // In modern browsers, counters are 32-bit signed integers. However, browsers behave differently when counter values
        // get too high or too low. We stick to IE behavior that doesn't allow counter values to overflow (in IE, counter
        // values don't wrap around min/max bounds).
        long newValue = (long)mValue + incrementValue;
        if (newValue > Integer.MAX_VALUE)
        {
            newValue = Integer.MAX_VALUE;
        }
        if (newValue < Integer.MIN_VALUE)
        {
            newValue = Integer.MIN_VALUE;
        }
        mValue = (int)newValue;
    }

    int getValue() { return mValue; }

    private int mValue;
}
