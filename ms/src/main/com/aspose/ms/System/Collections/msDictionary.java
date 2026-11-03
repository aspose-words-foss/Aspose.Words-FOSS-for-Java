// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 28.11.2017 by Anatoly Sidorenko

package com.aspose.ms.System.Collections;

import com.aspose.ms.System.Convert;
import com.aspose.words.ref.Ref;
import com.aspose.words.ref.RefBoolean;
import com.aspose.words.ref.RefDouble;
import com.aspose.words.ref.RefInt;

import java.util.Map;

// Realise Dictionary.TryGetValue(key, out value) method
public class msDictionary
{
	public static<K,V> boolean tryGetValue(Map<K,V> dictionary, K key, Ref<V> outValue)
	{
		if ((key != null) && dictionary.containsKey(key))
		{
			outValue.set(dictionary.get(key));
			return true;
		}
		return false;
	}

	public static<K> boolean tryGetValue(Map<K,Integer> dictionary, K key, RefInt outValue)
	{
		if ((key != null) && dictionary.containsKey(key))
		{
			outValue.set(dictionary.get(key));
			return true;
		}
		return false;
	}

	public static<K> boolean tryGetValue(Map<K,Boolean> dictionary, K key, RefBoolean outValue)
	{
		if ((key != null) && dictionary.containsKey(key))
		{
			outValue.set(dictionary.get(key));
			return true;
		}
		return false;
	}

	public static<K> boolean tryGetValue(Map<K,Double> dictionary, K key, RefDouble outValue)
	{
		if ((key != null) && dictionary.containsKey(key))
		{
			outValue.set(dictionary.get(key));
			return true;
		}
		return false;
	}

	public static<K,V> boolean remove(Map<K,V> dictionary, K key)
	{
		if ((key != null) && dictionary.containsKey(key))
		{
			dictionary.remove(key);
			return true;
		}
		return false;
	}

	public static<K,V> void add(Map<K,V> dictionary, K key, V value)
	{
		if ((key != null) && dictionary.containsKey(key))
			throw new IllegalArgumentException("error: addition of a duplicate key to a dictionary");
		else
			dictionary.put(key,value);
	}
}

