// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2018 by Alexey Butalov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Map;
import com.aspose.ms.System.Collections.SortedList;



/// <summary>
/// This class is a compromise to implement autoportable Generics. 
/// </summary>
/// <remarks>
/// .Net has changed getter contract for Generic collections: it just throws if found nothing.
/// So we need in safe getters for .Net generic collections now.
/// 
/// Java doesn't throw but it has another constrains:
/// 1. Java hasn't dynamic access to the Generic type: we can't use default(T), typeof(tValue) etc.
/// 2. Java's collection hierarchy is different.
/// 
/// So, we added two safe getters: dictionary.GetValueOrNull(key) and dictionary.GetValueOrDefault().
/// 
/// The first one can't use default(T) so always returns null for not-found value. As a result, the T can't be
/// value type.
/// 
/// The both methods have overloads for IDictionary, SortedList and SortedStringListGeneric to satisfy
/// Java collections hierarchy.
/// </remarks>
public /*static*/ class DictionaryExtension
{
	/* Simulation of static class by using private constructor */
	private DictionaryExtension()
	{}

    /// <summary>
    /// Returns value if found or null otherwise. Supports only reference types for TValue.
    /// </summary>
    public static <TKey, TValue extends Object> TValue getValueOrNull(/*this*/ Map<TKey, TValue> dictionary, TKey key)
    {
        if (dictionary.containsKey(key))
            return dictionary.get(key);

        return null;
    }

    /// <summary>
    /// Returns value if found or null otherwise. Supports only reference types for TValue.
    /// </summary>
    public static <TKey, TValue extends Object> TValue getValueOrNull(/*this*/ SortedList<TKey, TValue> dictionary, TKey key)
    {
        if (dictionary.containsKey(key))
            return dictionary.get(key);

        return null;
    }

    /// <summary>
    /// Returns value if found or null otherwise. Supports only reference types for TValue.
    /// </summary>
    public static <TValue extends Object> TValue getValueOrNull(/*this*/ SortedStringListGeneric<TValue> dictionary, String key)
    {
        if (dictionary.containsKey(key))
            return dictionary.get(key);

        return null;
    }

    /// <summary>
    /// Returns value if found; or <c>defaultValue</c> otherwise.
    /// </summary>
    public static <TValue, TKey> TValue getValueOrDefault(/*this*/ Map<TKey, TValue> dictionary, TKey key,
        TValue defaultValue)
    {
        if (dictionary.containsKey(key))
            return dictionary.get(key);

        return defaultValue;
    }

    /// <summary>
    /// Returns value if found; or <c>defaultValue</c> otherwise.
    /// </summary>
    public static <TValue, TKey> TValue getValueOrDefault(/*this*/ SortedList<TKey, TValue> dictionary, TKey key,
        TValue defaultValue)
    {
        if (dictionary.containsKey(key))
            return dictionary.get(key);

        return defaultValue;
    }

    /// <summary>
    /// Returns value if found; or <c>defaultValue</c> otherwise.
    /// </summary>
    public static <TValue> TValue getValueOrDefault(/*this*/ SortedStringListGeneric<TValue> dictionary, String key,
        TValue defaultValue)
    {
        if (dictionary.containsKey(key))
            return dictionary.get(key);

        return defaultValue;
    }
}

