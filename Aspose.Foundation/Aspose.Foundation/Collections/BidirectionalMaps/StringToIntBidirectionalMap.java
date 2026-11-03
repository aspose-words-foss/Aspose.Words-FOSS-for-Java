// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/09/2013 by Ivan Lyagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a bidirectional map of string-to-integer and integer-to-string value pairs.
/// </summary>
public class StringToIntBidirectionalMap
{
    /// <summary>
    /// Initializes a case-sensitive <see cref="StringToIntBidirectionalMap"/> instance.
    /// </summary>
    public StringToIntBidirectionalMap()
    {
    	this(true);
	
    }

    /// <summary>
    /// Initializes a case-sensitive or case-insensitive <see cref="StringToIntBidirectionalMap"/> instance.
    /// </summary>
    public StringToIntBidirectionalMap(boolean isCaseSensitive)
    {
        mFirstDictionary = new StringToIntDictionary(isCaseSensitive);
        mSecondDictionary = new IntToObjDictionary<String>();
    }

    /// <summary>
    /// Adds corresponding string-to-integer and integer-to-string value pairs to the map.
    /// </summary>
    public void addEntry(String stringValue, int int32Value)
    {
        mFirstDictionary.add(stringValue, int32Value);
        mSecondDictionary.add(int32Value, stringValue);
    }

    /// <summary>
    /// Adds corresponding string-to-integer and integer-to-string value pairs to the map.
    /// </summary>
    public void addEntry(int int32Value, String stringValue)
    {
        addEntry(stringValue, int32Value);
    }

    /// <summary>
    /// Returns an integer value associated with the given string value. Throws, if it is not found.
    /// </summary>
    public int getValue(String key)
    {
        return getValue(key, NULL_SUBSTITUTE);
    }

    /// <summary>
    /// Returns an integer value associated with the given string value. If it is not found, returns
    /// the given default value. But if the default value is equal to <see cref="int.MinValue"/>, throws.
    /// </summary>
    public int getValue(String key, int defaultValue)
    {
        int value = tryGetValue(key);
        return BidirectionalMapUtil.validateValue(value, defaultValue);
    }

    /// <summary>
    /// Returns an integer value associated with the given string value. If it is not found, returns
    /// the null substitute value. Use the <see cref="IsNullSubstitute"/> method to check the returned value.
    /// </summary>
    public int tryGetValue(String key)
    {
        if (key == null)
            return NULL_SUBSTITUTE;

        int value = mFirstDictionary.get(key);
        return StringToIntDictionary.isNullSubstitute(value) ? NULL_SUBSTITUTE : value;
    }

    /// <summary>
    /// Returns a string value associated with the given integer value. Throws, if it is not found.
    /// </summary>
    public String getValue(int key)
    {
        return getValue(key, null);
    }

    /// <summary>
    /// Returns a string value associated with the given integer value. If it is not found, returns
    /// the given default value. But if the default value is <b>null</b>, throws.
    /// </summary>
    public String getValue(int key, String defaultValue)
    {
        String value = tryGetValue(key);
        return BidirectionalMapUtil.validateValue(value, defaultValue);
    }

    /// <summary>
    /// Returns a string value associated with the given integer value. If it is not found, returns
    /// <c>null</c>.
    /// </summary>
    public String tryGetValue(int key)
    {
        if (isNullSubstitute(key))
            return null;

        return (mSecondDictionary.containsKey(key))
            ? mSecondDictionary.get(key)
            : null;
    }

    /// <summary>
    /// Returns a value indicating whether the specified value is null substitute value (i.e. semantic analogue 
    /// of null for non-nullable value types) defined for the class. 
    /// </summary>
    public static boolean isNullSubstitute(int value)
    {
        return value == NULL_SUBSTITUTE;
    }

    /// <summary>
    /// Gets a number of entries in this map.
    /// </summary>
    public int getCount() { return mFirstDictionary.getCount(); }

    /// <summary>
    /// Shows if <see cref="StringToIntBidirectionalMap"/> contains an integer value.
    /// </summary>
    public boolean containsValue(int value)
    {
        return mFirstDictionary.containsValue(value);
    }

    private /*final*/ StringToIntDictionary mFirstDictionary;
    private /*final*/ IntToObjDictionary<String> mSecondDictionary;

    /// <summary>
    /// The null substitute value (i.e. semantic analogue of null for non-nullable value types) defined
    /// for the class.
    /// </summary>
    private static final int NULL_SUBSTITUTE = Integer.MIN_VALUE;
}

