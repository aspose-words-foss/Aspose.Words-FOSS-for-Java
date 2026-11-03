// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/09/2013 by Ivan Lyagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.HashMap;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.words.ref.Ref;


/// <summary>
/// Represents a bidirectional map of string key-to-value and value-to-string key value pairs.
/// </summary>
public class StringToObjBidirectionalMap<TValue extends Object>
{
    /// <summary>
    /// Initializes a case-sensitive <see cref="StringToObjBidirectionalMap{TValue}"/> instance.
    /// </summary>
    public StringToObjBidirectionalMap()
    {
    	this(true);
	
    }

    /// <summary>
    /// Initializes a case-sensitive or case-insensitive <see cref="StringToObjBidirectionalMap{TValue}"/> instance.
    /// </summary>
    public StringToObjBidirectionalMap(boolean isCaseSensitive)
    {
        mKeyToValueDictionary = new StringToObjDictionary<TValue>(isCaseSensitive);
        mValueToKeyDictionary = new HashMap<TValue, String>();
    }

    /// <summary>
    /// Adds corresponding string key-to-value and value-to-string key value pairs to the map.
    /// </summary>
    public void addEntry(String key, TValue value)
    {
        mKeyToValueDictionary.add(key, value);
        msDictionary.add(mValueToKeyDictionary, value, key);
    }

    /// <summary>
    /// Returns a value associated with the given string key. Throws, if it is not found.
    /// </summary>
    public TValue getValue(String key)
    {
        TValue value = null;
        Ref<TValue> referenceToValue = new Ref<TValue>(value);
        boolean outRefCondition0 = !tryGetValue(key, /*out*/ referenceToValue);
        value = referenceToValue.get();
        if (outRefCondition0)
            throw new IllegalStateException("A value for the specified key is missed.");

        return value;
    }

    /// <summary>
    /// Returns a value associated with the given key. If it is not found, returns
    /// the given default value. But if the default value is <c>null</c>, throws.
    /// </summary>
    public TValue getValue(String key, TValue defaultValue)
    {
        TValue value = null;
        Ref<TValue> referenceToValue = new Ref<TValue>(value);
        boolean outRefCondition1 = tryGetValue(key, /*out*/ referenceToValue);
        value = referenceToValue.get();
        if (outRefCondition1)
            return value;

        if (defaultValue == null)
            throw new IllegalStateException(
                "A value for the specified key is missed and the default value is not provided.");

        return defaultValue;
    }

    /// <summary>
    /// Returns a string key associated with the given value. Throws, if it is not found.
    /// </summary>
    public String getKey(TValue value)
    {
        String key = null;
        Ref<String> referenceToKey = new Ref<String>(key);
        boolean outRefCondition2 = !tryGetKey(value, /*out*/ referenceToKey);
        key = referenceToKey.get();
        if (outRefCondition2)
            throw new IllegalStateException("A key for the specified value is missed.");

        return key;
    }

    /// <summary>
    /// Returns a string key associated with the given value. If it is not found, returns
    /// the given default value. But if the default value is <c>null</c>, throws.
    /// </summary>
    public String getKey(TValue value, String defaultKey)
    {
        String key = null;
        Ref<String> referenceToKey = new Ref<String>(key);
        boolean outRefCondition3 = tryGetKey(value, /*out*/ referenceToKey);
        key = referenceToKey.get();
        if (outRefCondition3)
            return key;

        if (defaultKey == null)
            throw new IllegalStateException(
                "A key for the specified value is missed and the default key value is not provided.");

        return defaultKey;
    }

    private boolean tryGetValue(String key, /*out*/Ref<TValue> value)
    {
        if ((key != null) && mKeyToValueDictionary.containsKey(key))
        {
            value.set(mKeyToValueDictionary.get(key));
            return true;
        }

        value.set(null);
        return false;
    }

    private boolean tryGetKey(TValue value, /*out*/Ref<String> key)
    {
        key.set((value != null)
            ? DictionaryExtension.getValueOrNull(mValueToKeyDictionary, value)
            : null);
        return key.get() != null;
    }

    private /*final*/ StringToObjDictionary<TValue> mKeyToValueDictionary;
    private /*final*/ HashMap<TValue, String> mValueToKeyDictionary;
}

