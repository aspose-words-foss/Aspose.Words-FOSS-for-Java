// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/11/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringToObjDictionary;


/// <summary>
/// Keeps track of all CSS counters during traversal of an HTML tree. Counters are accessed by name.
/// </summary>
class CssCounters
{
    /// <summary>
    /// Resets a counter to an initial value. Corresponds to the 'counter-reset' CSS property.
    /// </summary>
    void reset(String counterName, int initialValue)
    {
        // According to the CSS specification, 'none', 'initial, and 'inherit' must not be used as counter names
        // in the 'counter-reset' property.
        Debug.assertTrue(!"none".equals(counterName));
        Debug.assertTrue(!"initial".equals(counterName));
        Debug.assertTrue(!"inherit".equals(counterName));

        CssCounter counter = getCounter(counterName);
        counter.reset(initialValue);
    }

    /// <summary>
    /// Increments the value of a counter by a specified value. Corresponds to the 'counter-increment' CSS property.
    /// </summary>
    void increment(String counterName, int incrementValue)
    {
        // According to the CSS specification, 'none', 'initial, and 'inherit' must not be used as counter names
        // in the 'counter-increment' property.
        Debug.assertTrue(!"none".equals(counterName));
        Debug.assertTrue(!"initial".equals(counterName));
        Debug.assertTrue(!"inherit".equals(counterName));

        CssCounter counter = getCounter(counterName);
        counter.increment(incrementValue);
    }

    /// <summary>
    /// Gets the current value of a counter. Corresponds to the 'counter' CSS function.
    /// </summary>
    int getValue(String counterName)
    {
        CssCounter counter = getCounter(counterName);
        return counter.getValue();
    }

    /// <summary>
    /// Gets all scoped values of a counter, from outermost to innermost. Corresponds to the 'counters' CSS function.
    /// </summary>
    int[] getAllValues(String counterName)
    {
        CssCounter counter = getCounter(counterName);
        return counter.getAllValues();
    }

    /// <summary>
    /// Starts processing an element during an HTML tree traversal.
    /// </summary>
    void increaseNesting()
    {
        ++mNestingLevel;
        for (CssCounter counter : mCounters.getValues())
        {
            counter.increaseNesting();
        }
    }

    /// <summary>
    /// Stops processing an element and goes back to its parent during an HTML tree traversal.
    /// </summary>
    void decreaseNesting()
    {
        for (CssCounter counter : mCounters.getValues())
        {
            counter.decreaseNesting();
        }
        --mNestingLevel;
    }


    /// <summary>
    /// Gets a counter by name. Creates a new counter if needed.
    /// </summary>
    /// <returns>
    /// The counter that has the specified name. Never <c>null</c>.
    /// </returns>
    private CssCounter getCounter(String counterName)
    {
        CssCounter result = mCounters.get(counterName);
        if (result == null)
        {
            result = new CssCounter(mNestingLevel);
            mCounters.add(counterName, result);
        }
        return result;
    }

    /// <summary>
    /// Stores counters that have been referenced so far. Keys are counter names and values are instances of
    /// <see cref="CssCounter"/>.
    /// </summary>
    private /*final*/ StringToObjDictionary<CssCounter> mCounters = new StringToObjDictionary<CssCounter>();

    /// <summary>
    /// Keeps track of the current nesting level. This value is used to created counters that are first encountered on
    /// deeply nested levels.
    /// </summary>
    /// <remarks>
    /// 0 means the topmost level, 1 - child level, 2 - grand-child level, and so on.
    /// </remarks>
    private int mNestingLevel;
}

