// A Java port, made by Aspose, of a .NET Framework class. Microsoft publishes the source of that class
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/collections/dictionaryentry.cs
//
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

// ported 5/03/2009 by Konstantin Sidorenko

package com.aspose.ms.System.Collections;

import java.util.Map;

/**
 * Stolen from Rotor:
 *
 *  A DictionaryEntry holds a key and a value from a dictionary.
 *  It is returned by IDictionaryEnumerator::GetEntry().
 *
 *  In java returned by SortedList iterator.
 * //JAVA-changed: java.util.Map.Entry<K,V> implemented so users can iterate over SortedList-backed collections
 * using public Map.Entry instead of (obfuscated) DictionaryEntry.
 */
public class DictionaryEntry<K,V> implements Map.Entry<K,V>
{
	private K _key;
	private V _value;

	//JAVA-added default ctor (.net DictionaryEntry is struct - initialized automatically)
	public DictionaryEntry()
	{
		_key = null;
		_value = null;
	}

	// Constructs a new DictionaryEnumerator by setting the Key
	// and Value fields appropriately.
	public DictionaryEntry(K key, V value)
	{
		_key = key;
		_value = value;
	}

	public K getKey()
	{
		return _key;
	}

	//Note: java.util.Map.Entry hasn't setter for the Key property. And this setter doesn't used in AW yet. 
	public void setKey(K value)
	{
		_key = value;
	}

	public V getValue()
	{
		return _value;
	}

	//JAVA-changed to implement java.util.Map.Entry<K,V>.setValue() - .net "returns" void.
	public V setValue(V value)
	{
		_value = value;
		return _value;
	}
}

