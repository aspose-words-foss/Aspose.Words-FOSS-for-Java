// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/09/2013 by Ivan Lyagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a bidirectional map of integer-to-integer direct and indirect value pairs.
/// </summary>
public class IntToIntBidirectionalMap
{
    /// <summary>
    /// Adds corresponding integer-to-integer direct and indirect value pairs to the map.
    /// </summary>
    public void addEntry(int value1, int value2)
    {
        mDirectMap.add(value1, value2);
        mIndirectMap.add(value2, value1);
    }

    /// <summary>
    /// Gets value from the direct mapping or sets value to direct mapping for the specified key
    /// and vise versa for indirect one.
    /// </summary>
    public int get(int key) { return tryGetValueDirect(key); }
    public void set(int key, int value)
    {
        mDirectMap.set(key, value);
        mIndirectMap.set(value, key);
    }

    /// <summary>
    /// Returns an integer value associated with the given integer value using direct mappings. Throws, if it is not found.
    /// </summary>
    public int getValueDirect(int key)
    {
        return getValue(mDirectMap, key, Integer.MIN_VALUE);
    }

    /// <summary>
    /// Returns an integer value associated with the given integer value using direct mappings. If it is not found, 
    /// returns the given default value. But if the default value is <see cref="int.MinValue"/>, throws.
    /// </summary>
    public int getValueDirect(int key, int defaultValue)
    {
        return getValue(mDirectMap, key, defaultValue);
    }

    /// <summary>
    /// Returns an integer value associated with the given integer value using direct mappings. If it is not found, 
    /// returns <see cref="int.MinValue"/>.
    /// </summary>
    public int tryGetValueDirect(int key)
    {
        return tryGetValue(mDirectMap, key);
    }

    /// <summary>
    /// Returns an integer value associated with the given integer value using indirect mappings. Throws, if it is not found.
    /// </summary>
    public int getValueIndirect(int key)
    {
        return getValue(mIndirectMap, key, Integer.MIN_VALUE);
    }

    /// <summary>
    /// Returns an integer value associated with the given integer value using indirect mappings. If it is not found, 
    /// returns the given default value. But if the default value is <see cref="int.MinValue"/>, throws.
    /// </summary>
    public int getValueIndirect(int key, int defaultValue)
    {
        return getValue(mIndirectMap, key, defaultValue);
    }

    /// <summary>
    /// Returns an integer value associated with the given integer value using indirect mappings. If it is not found, 
    /// returns <see cref="int.MinValue"/>.
    /// </summary>
    public int tryGetValueIndirect(int key)
    {
        return tryGetValue(mIndirectMap, key);
    }

    private static int getValue(IntToIntDictionary map, int key, int defaultValue)
    {
        int value = tryGetValue(map, key);
        return BidirectionalMapUtil.validateValue(value, defaultValue);
    }

    private static int tryGetValue(IntToIntDictionary map, int key)
    {
        if (key == Integer.MIN_VALUE)
            return Integer.MIN_VALUE;

        int value = map.get(key);
        return IntToIntDictionary.isNullSubstitute(value) ? Integer.MIN_VALUE : value;
    }

    private /*final*/ IntToIntDictionary mDirectMap = new IntToIntDictionary();
    private /*final*/ IntToIntDictionary mIndirectMap = new IntToIntDictionary();
}

