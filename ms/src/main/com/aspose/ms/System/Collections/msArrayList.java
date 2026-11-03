//sk 19/01/2010

package com.aspose.ms.System.Collections;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Extensions methods that are available on .net ArrayList, but not available on Java ArrayList.
 */
public class msArrayList
{
	public static <T> ArrayList<T> ctor(Collection<T>  c)
    {
        return new ArrayList<T>(c);
    }

	public static <T> ArrayList<T> ctor(Iterable<T>  iterable)
    {
        ArrayList<T> list = new ArrayList<T>();
        for (T value : iterable)
            list.add(value);
        return list;
    }

	public static <T> ArrayList<T> ctor(T[] array)
    {
        return new ArrayList<T>(Arrays.asList(array));
    }

    public static ArrayList ctor(short... array)
    {
	    ArrayList list = new ArrayList(array.length);
	    for (int i = 0; i < array.length; i++)
		    list.add(array[i]);

	    return list;
    }

    public static ArrayList ctor(int... array)
    {
	    ArrayList list = new ArrayList(array.length);
	    for (int i = 0; i < array.length; i++)
		    list.add(array[i]);

	    return list;
    }

    public static ArrayList ctor(long... array)
    {
	    ArrayList list = new ArrayList(array.length);
	    for (int i = 0; i < array.length; i++)
		    list.add(array[i]);

	    return list;
    }

    public static ArrayList ctor(String... array)
    {
	    ArrayList list = new ArrayList(array.length);
	    for (int i = 0; i < array.length; i++)
		    list.add(array[i]);

	    return list;
    }

    // This allows to autoport .NETs int ArrayList.Add that returns an integer index of the newly added element.
    public static <T> int add(ArrayList<T> list, T item)
    {
        list.add(item);
        return list.size() - 1;
    }

	public static ArrayList getRange(ArrayList list, int index, int count)
	{
		return new ArrayList(list.subList(index, index + count));
	}

    public static <T,V> void addRange(ArrayList<T> list, Iterable<V> iterable)
    {
        for(V item : iterable)
        {
            list.add((T)item);
        }
    }

    public static void addRange(ArrayList list, Object[] c)
    {
        list.addAll(Arrays.asList(c));
    }

    public static void addRange(ArrayList list, int[] c)
    {
        // Have to loop. Cannot use Arrays.asList because it does not work with primitive data types.
        for (int i : c)
            list.add(i);
    }

    public static void addRange(ArrayList list, long[] c)
    {
        // Have to loop. Cannot use Arrays.asList because it does not work with primitive data types.
        for (long i : c)
            list.add(i);
    }

    public static void addRange(ArrayList list, byte[] c)
    {
        // Have to loop. Cannot use Arrays.asList because it does not work with primitive data types.
        for (byte i : c)
            list.add(i);
    }

    public static void addRange(ArrayList list, String[] c)
    {
        list.addAll(Arrays.asList(c));
    }

    public static void insertRange(ArrayList list, int index, Collection c)
    {
        list.addAll(index, c);        
    }
    public static <T> void insertRange(ArrayList<T> list, int index, T[] c)
    {
        list.addAll(index, Arrays.asList(c));
    }

	public static Object[] toArray(ArrayList list, Class type)
	{
		Object[] array = (Object[]) Array.newInstance(type, list.size());
		return list.toArray(array);
	}

	public static <T> T[] toArray(ArrayList<T> list, T[] array)
    {
		return list.toArray(array);
	}

    public static <T> void copyTo(ArrayList<T> list, T[] array)
    {
        copyTo(list, array, 0);
    }

    public static <T> void copyTo(ArrayList<T> list, T[] array, int index)
    {
        copyTo(list, 0, array, index, list.size());
    }

    public static <T> void copyTo(Set<T> set, T[] array, int index)
    {
        int arrayIndex = index;
        for (T val : set)
            array[arrayIndex++] = val;
    }

    public static <T> void copyTo(ArrayList<T> list, int index, T[] array, int arrayIndex, int count)
    {
        if (count < 0)
            throw new IllegalArgumentException("negative count");

        for (int i = 0; i < count; i++)
            array[arrayIndex + i] = list.get(index + i);
    }

    public static void removeRange(ArrayList list, int index, int count)
    {
        if (count < 0)
            throw new IllegalArgumentException("negative count");

        for (int i = 0; i < count; i++)
            list.remove(index);
    }

    public static void reverse(ArrayList list)
    {
        Collections.reverse(list);
    }

    // Duplicate of msArray.reverse(int[] array, int index, int length)
    public static void reverse(ArrayList list, int index, int length)
    {
        if (list == null)
            throw new NullPointerException("list");

        if (index < 0 || length < 0)
            throw new IndexOutOfBoundsException((index < 0 ? "index" : "length"));

        if (list.size() - index < length)
            throw new IndexOutOfBoundsException("length");


        int i = index;
        int j = index + length - 1;
        while (i < j)
        {
            Object temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
            i++;
            j--;
        }
    }

	public static ArrayList repeat(Object value, int count)
	{
		ArrayList list = new ArrayList(count);
		for (int i = 0; i < count; i++)
			list.add(value);

		return list;
	}

    public static int binarySearch(ArrayList list, Object value, Comparator comparer)
    {
        int lo = 0;
        int hi = list.size() - 1;

        while (lo <= hi)
        {
            int median = (lo + hi) >> 1;
            Object v = list.get(median);

            int compare = comparer.compare(v, value);
            if ( compare == 0)
                return median;

            if (compare < 0)
                lo = median + 1;
            else
                hi = median - 1;
        }

        return ~lo;
    }

    /**
	 * Used to emulate .Net ability to construct ArrayList from a given array.
	 * Excludes spare arraycopies and iteration over array elements for adding them to list.
	 */
	private static class ObjectArrayWrapper extends AbstractList
	{
		private ObjectArrayWrapper(Object[] array)
		{
			if (array == null)
			    throw new NullPointerException();
			this.mArray = array;
		}

		public Object get(int index)
		{
			return mArray[index];
		}

		public int size()
		{
			return mArray.length;
		}

		/**
		 * This whole class needed solely for this single method.
		 */
		public Object[] toArray()
		{
			return mArray;
		}

		private Object[] mArray;
	}
}
