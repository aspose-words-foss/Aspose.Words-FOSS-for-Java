//Konstantin Sidorenko 26.02.10

package com.aspose.ms.java.collections;

/**
 * Used by Aspose CsPorter for .net string switch emulation.
 *
 * Simplified immutable hash map of String keys to int values.
 * The values are indexes of the keys in the insertion order.
 *
 * The code inspired by java.util.HashMap and google-collect RegularImmutableMap.
 */
public final class StringSwitchMap
{
	public StringSwitchMap(String... inputKeys)
	{
		keys = inputKeys;
		int tableSize = chooseTableSize(keys.length);
		table = new Entry[tableSize];
		mask = tableSize - 1;

		// values calculated as indexes of keys in insertion order
		for (int value = 0; value < keys.length ; value++)
		{
			String key = keys[value];
			assert key != null : "The map doesn't accept null keys.";
			int keyHashCode = key.hashCode();

			for (int i = hashHash(keyHashCode); true; i++)
			{
				int index = i & mask;
				Entry existing = table[index];
				if (existing == null)
				{
					table[index] = new Entry(key, value);
					break;
				}
				else if (existing.key.equals(key))
				{
					throw new IllegalArgumentException("duplicate key: " + key);
				}
			}
		}
	}

	/**
	 * Returns integer mapping for given string key.
	 * Returns -1 for absent keys.
	 */
	public int of(String key)
	{
		if (key == null)
			return -1;

		for (int i = hashHash(key.hashCode()); true; i++)
		{
			int index = i & mask;
			Entry candidate = table[index];
			if (candidate == null)
				return -1;

			if (candidate.key.equals(key))
				return candidate.value;
		}
	}

	/*
	 * This method was written by Doug Lea with assistance from members of JCP
	 * JSR-166 Expert Group and released to the public domain, as explained at
	 * http://creativecommons.org/licenses/publicdomain
	 */
	static private int hashHash(int hashCode)
	{
		hashCode ^= (hashCode >>> 20) ^ (hashCode >>> 12);
		return hashCode ^ (hashCode >>> 7) ^ (hashCode >>> 4);
	}

	// Size the table to be at most 50% full, if possible
	static private int chooseTableSize(int setSize)
	{
		if (setSize < CUTOFF)
			return Integer.highestOneBit(setSize) << 2;

		// The table can't be completely full or we'll get infinite reprobes
		assert setSize < MAX_TABLE_SIZE : "collection too large";
		return MAX_TABLE_SIZE;
	}

	public String toString()
	{
		StringBuilder result = new StringBuilder(keys.length * 16).append('{');
		for (String key : keys)
			result.append(key).append(" : ").append(of(key)).append("\r\n");
		return result.append('}').toString();
	}

	static class Entry
	{
		final String key;
		final int value;

		Entry(String key, int value)
		{
			assert key != null;

			this.key = key;
			this.value = value;
		}
	}

	private final String[] keys; // keys in insertion order
	private final Entry[] table; // hashtable of key-value entries
	// 'and' with an int to get a table index
	private final int mask;

	// We use power-of-2 tables, and this is the highest int that's a power of 2
	private static final int MAX_TABLE_SIZE = 1 << 30;
	// If the set has this many elements, it will "max out" the table size
	private static final int CUTOFF = 1 << 29;
}
