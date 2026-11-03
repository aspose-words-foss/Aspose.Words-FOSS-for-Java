// A Java port, made by Aspose, of a .NET Framework class. Microsoft publishes the source of that class
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/collections/sortedlist.cs
//
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

// ported 4/03/2009 by Konstantin Sidorenko

package com.aspose.ms.System.Collections;

import com.aspose.words.ref.Ref;
import com.aspose.ms.System.msArray;
import com.aspose.words.ref.RefInt;

import java.util.*;

// The SortedList class implements a sorted list of keys and values. Entries in
// a sorted list are sorted by their keys and are accessible both by key and by
// index. The keys of a sorted list can be ordered either according to a
// specific IComparer implementation given when the sorted list is
// instantiated, or according to the IComparable implementation provided
// by the keys themselves. In either case, a sorted list does not allow entries
// with duplicate keys.
//
// A sorted list internally maintains two arrays that store the keys and
// values of the entries. The capacity of a sorted list is the allocated
// length of these internal arrays. As elements are added to a sorted list, the
// capacity of the sorted list is automatically increased as required by
// reallocating the internal arrays.  The capacity is never automatically
// decreased, but users can call either TrimToSize or
// Capacity explicitly.

/// <summary>
/// Stolen from Rotor and aspose.SortedIntegerList (that stolen from Rotor too:). Some things borrowed from java.util.TreeMap.
/// Used by SortedStringList.
/// </summary>
public class SortedList<K,V> implements Iterable<Map.Entry<K,V>>
{
	private K[] keys;
	private V[] values;
	private int _size;
	private Comparator<? super K> comparer = null;
	private KeyList keyList;
	private ValueList valueList;

	private final int _defaultCapacity = 16;

	public SortedList()
	{
		keys = (K[]) new Object[_defaultCapacity];
		values = (V[]) new Object[_defaultCapacity];
	}

	public SortedList(int initialCapacity)
	{
		if (initialCapacity < 0)
			throw new IndexOutOfBoundsException("initialCapacity");

		keys = (K[]) new Object[initialCapacity];
		values = (V[]) new Object[initialCapacity];
	}

	public SortedList(HashMap<K,V> map)
	{
		this(map.size());
		for (Map.Entry<K,V> entry : map.entrySet())
			this.add(entry.getKey(), entry.getValue());
	}

	public SortedList(Comparator comparer)
	{
		this();
		if (comparer != null)
			this.comparer = comparer;
	}

	public SortedList(Comparator comparer, int capacity)
	{
		this(capacity);
		if (comparer != null)
			this.comparer = comparer;
	}

	public SortedList(Iterable<DictionaryEntry<K,V>> dictionary, Comparator comparer)
	{
		this(comparer);
		for (DictionaryEntry<K,V> entry : dictionary)
			this.add(entry.getKey(), entry.getValue());
	}

	public void add(K key, V value)
	{
		if (key == null)
			throw new IllegalArgumentException("Key cannot be null");

		int i = binarySearch(keys, 0, _size, key, comparer);
		if (i >= 0)
			throw new IllegalArgumentException("duplicate");

		insert(~i, key, value);
	}

	public int getCapacity()
	{
		return keys.length;
	}

	public void setCapacity(int value)
	{
		if (value != keys.length)
		{
			if (value < _size)
				throw new IndexOutOfBoundsException("value");

			if (value > 0)
			{
				K[] newKeys = (K[]) new Object[value];
				V[] newValues = (V[]) new Object[value];
				if (_size > 0)
				{
					System.arraycopy(keys, 0, newKeys, 0, _size);
					System.arraycopy(values, 0, newValues, 0, _size);
				}
				keys = newKeys;
				values = newValues;
			}
			else
			{
				keys = (K[]) new Object[_defaultCapacity];
				values = (V[]) new Object[_defaultCapacity];
			}
		}
	}

	public int getCount()
	{
		return _size;
	}

	//JAVA-deleted properties:
	//Keys
	//Values
	//IsReadOnly
	//IsFixedSize
	//IsSynchronized
	//SyncRoot

	public void clear()
	{
		_size = 0;
		keys = (K[]) new Object[_defaultCapacity];
		values = (V[]) new Object[_defaultCapacity];
	}

	/// <summary>
	/// Returns shallow copy of this instance.
	/// </summary>
	public SortedList<K,V> deepClone()
	{
		SortedList<K,V> clone = new SortedList<K,V>(_size);
		clone.keys = keys.clone();
		clone.values = values.clone();
		clone._size = _size;
		clone.comparer = comparer;
		return clone;
	}

	public boolean contains(K key)
	{
		return indexOfKey(key) >= 0;
	}

	/// <summary>
	/// Returns true if specified key is in the list.
	/// Performs binary search.
	/// </summary>
	public boolean containsKey(K key)
	{
		// Yes, this is a SPEC'ed duplicate of contains().
		return indexOfKey(key) >= 0;
	}

	public boolean containsValue(V value)
	{
		return indexOfValue(value) >= 0;
	}

	//JAVA-deleted: does not used anywere in AW at the moment:
	//public virtual void CopyTo(Array array, int arrayIndex) {

	private void ensureCapacity(int min)
	{
		int newCapacity = keys.length == 0? 16: keys.length << 1;
		if (newCapacity < min)
			newCapacity = min;

		setCapacity(newCapacity);
	}

	public V getByIndex(int index)
	{
		if (index < 0 || index >= _size)
			throw new IndexOutOfBoundsException("index");

		return values[index];
	}

	// Returns an IEnumerator for this sorted list.  If modifications
	// made to the sorted list while an enumeration is in progress,
	// the MoveNext and Remove methods
	// of the enumerator will throw an exception.
	//
	public Iterator<Map.Entry<K,V>> iterator()
	{
		return new SortedListEnumerator();
	}

	public K getKey(int index)
	{
		if (index < 0 || index >= _size)
			throw new IndexOutOfBoundsException("index");

		return keys[index];
	}

	public Iterable<K> getKeyList()
	{
	    if (keyList == null)
            keyList = new KeyList(this);

	    return keyList;
	}

	public List<K> getKeys()
	{
		return new ComparableList<K>((Collection<K>)getKeyList(), (Comparator<K>)comparer);
	}

	public Collection<V> getValueList()
	{
	    if (valueList == null)
            valueList = new ValueList(this);

	    return valueList;
	}

	public ArrayList<V> getValues()
	{
	    return new ArrayList<V>(getValueList());
	}

	public V get(K key)
	{
		int i = indexOfKey(key);
		if (i >= 0 && i < _size)
			return values[i];

		return null;
	}

	public boolean tryGetValue(K key, /*out*/ Ref<V> value)
	{
		if ((key != null) && containsKey(key))
		{
			value.set(get(key));
			return true;
		}

		return false;
	}

	public boolean tryGetValue(K key, /*out*/ RefInt value)
	{
		if ((key != null) && containsKey(key))
		{
			value.set((Integer)get(key));
			return true;
		}

		return false;
	}

	public void set(K key, V value)
	{
		if (key == null)
			throw new NullPointerException("key");

		int i = binarySearch(keys, 0, _size, key, comparer);
		if (i >= 0) {
			values[i] = value;
			return;
		}

		insert(~i, key, value);
	}

	public int indexOfKey(K key)
	{
		if (key == null)
			throw new NullPointerException("key");

		int ret = binarySearch(keys, 0, _size, key, comparer);
		return ret >=0 ? ret : -1;
	}

	public int indexOfValue(V value)
	{
		return msArray.indexOf(values, value, 0, _size);
	}

	private void insert(int index, K key, V value)
	{
		if (_size == keys.length)
			ensureCapacity(_size + 1);

		if (index < _size)
		{
			System.arraycopy(keys, index, keys, index + 1, _size - index);
			System.arraycopy(values, index, values, index + 1, _size - index);
		}

		keys[index] = key;
		values[index] = value;
		_size++;
	}

	public void removeAt(int index)
	{
		if (index < 0 || index >= _size)
			throw new IndexOutOfBoundsException("index");

		_size--;
		if (index < _size)
		{
			System.arraycopy(keys, index + 1, keys, index, _size - index);
			System.arraycopy(values, index + 1, values, index, _size - index);
		}

		keys[_size] = null;
		values[_size] = null;
	}

	public void remove(K key)
	{
		int i = indexOfKey(key);
		if (i >= 0)
			removeAt(i);
	}

	public void setByIndex(int index, V value)
	{
		if (index < 0 || index >= _size)
			throw new IndexOutOfBoundsException("index");

		values[index] = value;
	}

	//JAVA-deleted:
	//public static SortedList Synchronized(SortedList list)

	// Sets the capacity of this sorted list to the size of the sorted list.
	// This method can be used to minimize a sorted list's memory overhead once
	// it is known that no new elements will be added to the sorted list. To
	// completely clear a sorted list and release all memory referenced by the
	// sorted list, execute the following statements:
	//
	// sortedList.Clear();
	// sortedList.TrimToSize();
	//
	public void trimToSize()
	{
		setCapacity(_size);
	}

	private class SortedListEnumerator implements Iterator<Map.Entry<K,V>>
	{
		private SortedListEnumerator()
		{
			index = -1;
		}

		public boolean hasNext()
		{
			return (index + 1 < getCount());
		}

		public void reset() //java Iterator hasn't such a method
		{
			index = -1;
		}

		public DictionaryEntry<K,V> next()
		{
			if (++index >= getCount())
				throw new NoSuchElementException("Iteration has no more elements.");

			return new DictionaryEntry<K,V>(getKey(index), getByIndex(index));
		}

		//java: C# Enumerator hasn't such a method, java Iterator has, but it rarely implemented:)
		public void remove()
		{
			throw new UnsupportedOperationException();
		}

		private int index;
	}

	private class KeyList implements Collection<K>// ICollection, IEnumerable
	{
		private SortedList<K,V> sortedList;

		KeyList(SortedList<K,V> sortedList)
		{
			this.sortedList = sortedList;
		}

		public int size()
		{
			return sortedList._size;
		}

		public boolean isReadOnly()
		{
			return true;
		}

		public boolean isFixedSize()
		{
			return true;
		}

/*
	    public virtual bool IsSynchronized {
	        get { return sortedList.IsSynchronized; }
	    }

	    public virtual Object SyncRoot {
	        get { return sortedList.SyncRoot; }
	    }
*/

		public boolean add(Object key)
		{
			throw new UnsupportedOperationException();
		}

		public void clear()
		{
			throw new UnsupportedOperationException();
		}

		//param should be K but is Object to implement java.util.Collection
		public boolean contains(/*K*/Object key)
		{
			return sortedList.contains((K)key);
		}

		public void copyTo(K[] array, int arrayIndex)
		{
			// defer error checking to Array.Copy
			//Array.Copy(sortedList.keys, 0, array, arrayIndex, sortedList.Count);
			System.arraycopy(sortedList.keys, 0, array, arrayIndex, sortedList.getCount());
		}

		public void insert(int index, Object value)
		{
			throw new UnsupportedOperationException();
		}

		public K get(int index)
		{
			return sortedList.getKey(index);
		}

		public K set(int index, K value)
		{
			throw new UnsupportedOperationException();
		}

		//>>>>>>>>>>>>>>>>>>>> Collection<K> implementation >>>>>>>>>>>>>>>>>>>

		public boolean isEmpty()
		{
			return size() <= 0;
		}

		public  Iterator<K> iterator()
		{
			return new SortedListKeyEnumerator();
		}

		public Object[] toArray()
		{
			// defer error checking to Array.Copy
			Object[] array = new Object[size()];
			System.arraycopy(sortedList.keys, 0, array, 0, sortedList.getCount());
			return array;
		}

		public <T> T[] toArray(T[] a)
		{
			// defer error checking to Array.Copy
			System.arraycopy(sortedList.keys, 0, a, 0, sortedList.getCount());
			return a;
		}

		public int indexOf(K key)
		{
			if (key == null)
				throw new NullPointerException("key");

			int i = binarySearch(sortedList.keys, 0, sortedList.getCount(), key, sortedList.comparer);
			if (i >= 0)
				return i;

			return -1;
		}

		public boolean remove(Object key)
		{
			throw new UnsupportedOperationException();
		}

		public boolean containsAll(Collection<?> c)
		{
			throw new UnsupportedOperationException();
		}

		public boolean addAll(Collection<? extends K> c)
		{
			throw new UnsupportedOperationException();
		}

		public boolean removeAll(Collection<?> c)
		{
			throw new UnsupportedOperationException();
		}

		public boolean retainAll(Collection<?> c)
		{
			throw new UnsupportedOperationException();
		}

		public void removeAt(int index)
		{
			throw new UnsupportedOperationException();
		}

		//>>>>>>>>>>>>>>>>>>>> Collection<K> implementation >>>>>>>>>>>>>>>>>>>

		//JAVA-added. in .Net one Enumerator used for all three: SortedList, KeyList and ValueList.
		//in java we should add an iterator for each list to properly implement generics.
		private class SortedListKeyEnumerator implements Iterator<K>
		{
			private SortedListKeyEnumerator()
			{
				index = -1;
			}

			public boolean hasNext()
			{
				return (index + 1 < size());
			}

			public void reset() //java Iterator hasn't such a method
			{
				index = -1;
			}

			public K next()
			{
				if (++index >= size())
					throw new NoSuchElementException("Iteration has no more elements.");

				return sortedList.getKey(index);
			}

			//java: C# Enumerator hasn't such a method, java Iterator has, but it rarely implemented:)
			public void remove()
			{
				throw new UnsupportedOperationException();
			}

			private int index;
		}
	}

	private class ValueList implements Collection<V>// Iterable<V> //: IList<V>
	{
		private SortedList<K,V> sortedList;

		ValueList(SortedList<K,V> sortedList)
		{
			this.sortedList = sortedList;
		}

		public int size()
		{
			return sortedList._size;
		}

		public boolean isReadOnly()
		{
			return true;
		}

		public boolean isFixedSize()
		{
			return true;
		}

/*
	    public virtual bool IsSynchronized {
	        get { return sortedList.IsSynchronized; }
	    }

	    public virtual Object SyncRoot {
	        get { return sortedList.SyncRoot; }
	    }
*/

		public boolean add(Object key)
		{
			throw new UnsupportedOperationException();
		}

		public void clear()
		{
			throw new UnsupportedOperationException();
		}

		//param should be V but is Object to implement java.util.Collection
		public boolean contains(Object value)
		{
			return sortedList.containsValue((V)value);
		}

		public void copyTo(V[] array, int arrayIndex)
		{
			// defer error checking to Array.Copy
			//Array.Copy(sortedList.values, 0, array, arrayIndex, sortedList.Count);
			System.arraycopy(sortedList.values, 0, array, arrayIndex, sortedList.getCount());
		}

		public void insert(int index, Object value)
		{
			throw new UnsupportedOperationException();
		}

		public V get(int index)
		{
			return sortedList.getByIndex(index);
		}

		public K set(int index, V value)
		{
			throw new UnsupportedOperationException();
		}

		//>>>>>>>>>>>>>>>>>>>> Collection<V> implementation >>>>>>>>>>>>>>>>>>>

		public boolean isEmpty()
		{
			return size() <= 0;
		}

		public Iterator<V> iterator()
		{
			return new SortedListValueEnumerator();
		}

		public Object[] toArray()
		{
			// defer error checking to Array.Copy
			Object[] array = new Object[size()];
			System.arraycopy(sortedList.values, 0, array, 0, sortedList.getCount());
			return array;
		}

		public <T> T[] toArray(T[] a)
		{
			// defer error checking to Array.Copy
			System.arraycopy(sortedList.values, 0, a, 0, sortedList.getCount());
			return a;
		}

		public int indexOf(V value)
		{
			return msArray.indexOf(sortedList.values, value, 0, sortedList.getCount());

		}

		public boolean remove(Object value)
		{
			throw new UnsupportedOperationException();
		}

		public boolean containsAll(Collection<?> c)
		{
			throw new UnsupportedOperationException();
		}

		public boolean addAll(Collection<? extends V> c)
		{
			throw new UnsupportedOperationException();
		}

		public boolean removeAll(Collection<?> c)
		{
			throw new UnsupportedOperationException();
		}

		public boolean retainAll(Collection<?> c)
		{
			throw new UnsupportedOperationException();
		}

		public void removeAt(int index)
		{
			throw new UnsupportedOperationException();
		}

		//>>>>>>>>>>>>>>>>>>>> Collection<V> implementation >>>>>>>>>>>>>>>>>>>

		//JAVA-added. in .Net one Enumerator used for all three: SortedList, KeyList and ValueList.
		//in java we should add an iterator for each list to properly implement generics.
		private class SortedListValueEnumerator implements Iterator<V>
		{
			private SortedListValueEnumerator()
			{
				index = -1;
			}

			public boolean hasNext()
			{
				return (index + 1 < size());
			}

			public void reset() //java Iterator hasn't such a method
			{
				index = -1;
			}

			public V next()
			{
				if (++index >= size())
					throw new NoSuchElementException("Iteration has no more elements.");

				return sortedList.getByIndex(index);
			}

			//java: C# Enumerator hasn't such a method, java Iterator has, but it rarely implemented:)
			public void remove()
			{
				throw new UnsupportedOperationException();
			}

			private int index;
		}
	}

	//@todo 1 sk move to ArrayUtil
	/// <summary>
	/// This code was copied and modified from java.util.Arrays.
	/// Performs a binary search within specified range of the array.
	/// </summary>
	public static int binarySearch(Object[] array, int index, int length, Object value)
	{
		assert (array != null);
		assert ((index >= 0) && (length >= 0));
		assert (array.length - index >= length);

		int low = index;
		int high = index + length - 1;

		while (low <= high)
		{
			int mid = (low + high) >> 1;
			Comparable midVal = (Comparable)array[mid];
			int cmp = midVal.compareTo(value);

			if (cmp < 0)
				low = mid + 1;
			else if (cmp > 0)
				high = mid - 1;
			else
				return mid; // key found
		}

		return ~low;  // key not found.
	}

	//@todo 1 sk move to ArrayUtil
	public static <T> int binarySearch(T[] a, int index, int length, T key, Comparator<? super T> c)
	{
		assert (a != null);
		assert ((index >= 0) && (length >= 0));
		assert (a.length - index >= length);

		if (c==null)
			return binarySearch(a, index, length, key);

		int low = index;
		int high = index + length - 1;

		while (low <= high)
		{
			int mid = (low + high) >> 1;
			T midVal = a[mid];
			int cmp = c.compare(midVal, key);

			if (cmp < 0)
				low = mid + 1;
			else if (cmp > 0)
				high = mid - 1;
			else
				return mid; // key found
		}

		return ~low;  // key not found.
	}

	//JAVA-added
	public String toString()
	{
		StringBuilder builder = new StringBuilder("\r\n");
		for (Map.Entry entry : this)
			builder.append(entry.getKey()).append(" => ").append(entry.getValue()).append("\r\n");

		return builder.toString();
	}
}
