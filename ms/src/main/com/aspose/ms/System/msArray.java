// Parts of this file are a Java port, made by Aspose, of .NET Framework code. Microsoft publishes that source
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/array.cs
//
// Those parts: Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

package com.aspose.ms.System;

import com.aspose.ms.System.Drawing.Rectangle;
import com.aspose.ms.System.Drawing.RectangleF;
import com.aspose.ms.java.util.JavaUtil;
import com.aspose.ms.java.text.CollatorProvider;
import com.aspose.words.ref.Ref;

import java.lang.reflect.Array;
import java.text.Collator;
import java.util.*;

/**
 * Some Array utility methods ported from Rotor System.Array.
 */
public final class msArray
{
	private msArray() {}

	/**
	 * Returns the index of the first occurrence of a given value in a range of
	 * an array. The array is searched forwards, starting at index
	 * startIndex and up to count elements.
	 */
	public static int indexOf(int[] array, int value, int startIndex, int count)
	{
		if (array == null)
			throw new NullPointerException("array");

		if (startIndex < 0 || startIndex > array.length)
			throw new IllegalArgumentException("startIndex");

		int endIndex = startIndex + count;
		if (count < 0 || endIndex > array.length)
			throw new IllegalArgumentException("count");

		for (int i = startIndex; i < endIndex; i++)
		{
			if (value == array[i])
				return i;
		}

		return -1;
	}

	//full copy of above indexOf(int[]...)
	public static int indexOf(long[] array, long value, int startIndex, int count)
	{
		if (array == null)
			throw new NullPointerException("array");

		if (startIndex < 0 || startIndex > array.length)
			throw new IllegalArgumentException("startIndex");

		int endIndex = startIndex + count;
		if (count < 0 || endIndex > array.length)
			throw new IllegalArgumentException("count");

		for (int i = startIndex; i < endIndex; i++)
		{
			if (value == array[i])
				return i;
		}

		return -1;
	}

	//full copy of above indexOf(int[]...)
	public static int indexOf(byte[] array, byte value, int startIndex, int count)
	{
		if (array == null)
			throw new NullPointerException("array");

		if (startIndex < 0 || startIndex > array.length)
			throw new IllegalArgumentException("startIndex");


		int endIndex = startIndex + count;
		if (count < 0 || endIndex > array.length)
			throw new IllegalArgumentException("count");

		for (int i = startIndex; i < endIndex; i++)
		{
			if (value == array[i])
				return i;
		}

		return -1;
	}

	//full copy of above indexOf(int[]...)
	public static int indexOf(char[] array, char value, int startIndex, int count)
	{
		if (array == null)
			throw new NullPointerException("array");

		if (startIndex < 0 || startIndex > array.length)
			throw new IllegalArgumentException("startIndex");

		int endIndex = startIndex + count;
		if (count < 0 || endIndex > array.length)
			throw new IllegalArgumentException("count");

		for (int i = startIndex; i < endIndex; i++)
		{
			if (value == array[i])
				return i;
		}

		return -1;
	}

	//full copy of above indexOf(int[]...)
	public static int indexOf(float[] array, float value, int startIndex, int count)
	{
		if (array == null)
			throw new NullPointerException("array");

		if (startIndex < 0 || startIndex > array.length)
			throw new IllegalArgumentException("startIndex");

		int endIndex = startIndex + count;
		if (count < 0 || endIndex > array.length)
			throw new IllegalArgumentException("count");

		for (int i = startIndex; i < endIndex; i++)
		{
			if (value == array[i])
				return i;
		}

		return -1;
	}

	//full copy of above indexOf(int[]...)
	public static int indexOf(double[] array, double value, int startIndex, int count)
	{
		if (array == null)
			throw new NullPointerException("array");

		if (startIndex < 0 || startIndex > array.length)
			throw new IllegalArgumentException("startIndex");

		int endIndex = startIndex + count;
		if (count < 0 || endIndex > array.length)
			throw new IllegalArgumentException("count");

		for (int i = startIndex; i < endIndex; i++)
		{
			if (value == array[i])
				return i;
		}

		return -1;
	}

	//full copy of above indexOf(int[]...)
	public static int indexOf(boolean[] array, boolean value, int startIndex, int count)
	{
		if (array == null)
			throw new NullPointerException("array");

		if (startIndex < 0 || startIndex > array.length)
			throw new IllegalArgumentException("startIndex");

		int endIndex = startIndex + count;
		if (count < 0 || endIndex > array.length)
			throw new IllegalArgumentException("count");

		for (int i = startIndex; i < endIndex; i++)
		{
			if (value == array[i])
				return i;
		}

		return -1;
	}

	// mf
	public static int indexOf(int[] array, int value)
	{
		return indexOf(array, value, 0, array.length);
	}

	// mf
	public static int indexOf(Object[] array, Object value)
	{
		return indexOf(array, value, 0, array.length);
	}

	/**
	 * Returns the index of the first occurrence of a given value in a range of
	 * an array. The array is searched forwards, starting at index startIndex
	 * and upto count elements. The elements of the array are compared
	 * to the given value using the Object.Equals method.
	 */
	public static int indexOf(Object[] array, Object value, int startIndex, int count)
	{
		if (array == null)
			throw new NullPointerException("array");

		if (startIndex < 0 || startIndex > array.length)
			throw new IllegalArgumentException("startIndex");

		int endIndex = startIndex + count;
		if (count < 0 || endIndex > array.length)
			throw new IllegalArgumentException("count");

		if (value == null)
		{
			for (int i = startIndex; i < endIndex; i++)
			{
				if (array[i] == null)
					return i;
			}
		}
		else
		{
			for (int i = startIndex; i < endIndex; i++)
			{
				if (value.equals(array[i]))
					return i;
			}
		}
		return -1;
	}

	/**
	 * Returns the index of the last occurrence of a given value in a range of
	 * an array. The array is searched _backwards_, starting at index
	 * startIndex and counting upto count elements.
	 */
	public static int lastIndexOf(int[] array, int value, int startIndex, int count)
	{
		if (array == null)
			throw new NullPointerException("array");

		if (array.length == 0)
			return -1;

		if (startIndex < 0 || startIndex >= array.length)
			throw new IllegalArgumentException("startIndex");

		if (count < 0 || count > startIndex + 1)
			throw new IllegalArgumentException("count");

		int endIndex = startIndex - count + 1;

		for (int i = startIndex; i >= endIndex; i--)
		{
			if (value == array[i])
				return i;
		}

		return -1;
	}

	//full copy of above lastIndexOf(int[]...)
	public static int lastIndexOf(long[] array, long value, int startIndex, int count)
	{
		if (array == null)
			throw new NullPointerException("array");

		if (array.length == 0)
			return -1;

		if (startIndex < 0 || startIndex >= array.length)
			throw new IllegalArgumentException("startIndex");

		if (count < 0 || count > startIndex + 1)
			throw new IllegalArgumentException("count");

		int endIndex = startIndex - count + 1;

		for (int i = startIndex; i >= endIndex; i--)
		{
			if (value == array[i])
				return i;
		}

		return -1;
	}

	//full copy of above lastIndexOf(int[]...)
	public static int lastIndexOf(byte[] array, byte value, int startIndex, int count)
	{
		if (array == null)
			throw new NullPointerException("array");

		if (array.length == 0)
			return -1;

		if (startIndex < 0 || startIndex >= array.length)
			throw new IllegalArgumentException("startIndex");

		if (count < 0 || count > startIndex + 1)
			throw new IllegalArgumentException("count");

		int endIndex = startIndex - count + 1;

		for (int i = startIndex; i >= endIndex; i--)
		{
			if (value == array[i])
				return i;
		}

		return -1;
	}

	//full copy of above lastIndexOf(int[]...)
	public static int lastIndexOf(char[] array, char value, int startIndex, int count)
	{
		if (array == null)
			throw new NullPointerException("array");

		if (array.length == 0)
			return -1;

		if (startIndex < 0 || startIndex >= array.length)
			throw new IllegalArgumentException("startIndex");

		if (count < 0 || count > startIndex + 1)
			throw new IllegalArgumentException("count");

		int endIndex = startIndex - count + 1;

		for (int i = startIndex; i >= endIndex; i--)
		{
			if (value == array[i])
				return i;
		}

		return -1;
	}

	//full copy of above lastIndexOf(int[]...)
	public static int lastIndexOf(float[] array, float value, int startIndex, int count)
	{
		if (array == null)
			throw new NullPointerException("array");

		if (array.length == 0)
			return -1;

		if (startIndex < 0 || startIndex >= array.length)
			throw new IllegalArgumentException("startIndex");

		if (count < 0 || count > startIndex + 1)
			throw new IllegalArgumentException("count");

		int endIndex = startIndex - count + 1;

		for (int i = startIndex; i >= endIndex; i--)
		{
			if (value == array[i])
				return i;
		}

		return -1;
	}

	//full copy of above lastIndexOf(int[]...)
	public static int lastIndexOf(double[] array, double value, int startIndex, int count)
	{
		if (array == null)
			throw new NullPointerException("array");

		if (array.length == 0)
			return -1;

		if (startIndex < 0 || startIndex >= array.length)
			throw new IllegalArgumentException("startIndex");

		if (count < 0 || count > startIndex + 1)
			throw new IllegalArgumentException("count");

		int endIndex = startIndex - count + 1;

		for (int i = startIndex; i >= endIndex; i--)
		{
			if (value == array[i])
				return i;
		}

		return -1;
	}

	//full copy of above lastIndexOf(int[]...)
	public static int lastIndexOf(boolean[] array, boolean value, int startIndex, int count)
	{
		if (array == null)
			throw new NullPointerException("array");

		if (array.length == 0)
			return -1;

		if (startIndex < 0 || startIndex >= array.length)
			throw new IllegalArgumentException("startIndex");

		if (count < 0 || count > startIndex + 1)
			throw new IllegalArgumentException("count");

		int endIndex = startIndex - count + 1;

		for (int i = startIndex; i >= endIndex; i--)
		{
			if (value == array[i])
				return i;
		}

		return -1;
	}

	//full copy of above lastIndexOf(int[]...)
	public static int lastIndexOf(Object[] array, Object value, int startIndex, int count)
	{
		if (array == null)
			throw new NullPointerException("array");

		if (array.length == 0)
			return -1;

		if (startIndex < 0 || startIndex >= array.length)
			throw new IllegalArgumentException("startIndex");

		if (count < 0 || count > startIndex + 1)
			throw new IllegalArgumentException("count");

		int endIndex = startIndex - count + 1;

		if (value == null)
		{
			for (int i = startIndex; i >= endIndex; i--)
			{
				if (array[i] == null)
					return i;
			}
		}
		else
		{
			for (int i = startIndex; i >= endIndex; i--)
			{
				if (value.equals(array[i]))
					return i;
			}
		}

		return -1;
	}

	//full copy of above lastIndexOf(int[]...)
	public static int lastIndexOf(RectangleF[] array, RectangleF value, int startIndex, int count)
	{
		if (array == null)
			throw new NullPointerException("array");

		if (array.length == 0)
			return -1;

		if (startIndex < 0 || startIndex >= array.length)
			throw new IllegalArgumentException("startIndex");

		if (count < 0 || count > startIndex + 1)
			throw new IllegalArgumentException("count");

		int endIndex = startIndex - count + 1;

		for (int i = startIndex; i >= endIndex; i--)
		{
			if (value == array[i])
				return i;
		}

		return -1;
	}

	//full copy of above lastIndexOf(int[]...)
	public static int lastIndexOf(Rectangle[] array, Rectangle value, int startIndex, int count)
	{
		if (array == null)
			throw new NullPointerException("array");

		if (array.length == 0)
			return -1;

		if (startIndex < 0 || startIndex >= array.length)
			throw new IllegalArgumentException("startIndex");

		if (count < 0 || count > startIndex + 1)
			throw new IllegalArgumentException("count");

		int endIndex = startIndex - count + 1;

		for (int i = startIndex; i >= endIndex; i--)
		{
			if (value == array[i])
				return i;
		}

		return -1;
	}

	public static void reverse(float[] array)
	{
		reverse(array, 0, array.length);
	}

	// Duplicate of reverse(int[] array, int index, int length)
	public static void reverse(float[] array, int index, int length)
	{
		if (array == null)
			throw new NullPointerException("array");

		if (index < 0 || length < 0)
			throw new IllegalArgumentException((index < 0 ? "index" : "length"));

		if (array.length - index < length)
			throw new IllegalArgumentException("length");

		int i = index;
		int j = index + length - 1;
		while (i < j)
		{
			float temp = array[i];
			array[i] = array[j];
			array[j] = temp;
			i++;
			j--;
		}
	}

	public static void reverse(boolean[] array)
	{
		reverse(array, 0, array.length);
	}

	// Duplicate of reverse(int[] array, int index, int length)
	public static void reverse(boolean[] array, int index, int length)
	{
		if (array == null)
			throw new NullPointerException("array");

		if (index < 0 || length < 0)
			throw new IllegalArgumentException((index < 0 ? "index" : "length"));

		if (array.length - index < length)
			throw new IllegalArgumentException("length");

		int i = index;
		int j = index + length - 1;
		while (i < j)
		{
			boolean temp = array[i];
			array[i] = array[j];
			array[j] = temp;
			i++;
			j--;
		}
	}

	public static void reverse(double[] array)
	{
		reverse(array, 0, array.length);
	}

	// Duplicate of reverse(int[] array, int index, int length)
	public static void reverse(double[] array, int index, int length)
	{
		if (array == null)
			throw new NullPointerException("array");

		if (index < 0 || length < 0)
			throw new IllegalArgumentException((index < 0 ? "index" : "length"));

		if (array.length - index < length)
			throw new IllegalArgumentException("length");

		int i = index;
		int j = index + length - 1;
		while (i < j)
		{
			double temp = array[i];
			array[i] = array[j];
			array[j] = temp;
			i++;
			j--;
		}
	}

	public static void reverse(Object[] array)
	{
		reverse(array, 0, array.length);
	}

	// Duplicate of reverse(int[] array, int index, int length)
	public static void reverse(Object[] array, int index, int length)
	{
		if (array == null)
			throw new NullPointerException("array");

		if (index < 0 || length < 0)
			throw new IllegalArgumentException((index < 0 ? "index" : "length"));

		if (array.length - index < length)
			throw new IllegalArgumentException("length");

		int i = index;
		int j = index + length - 1;
		while (i < j)
		{
			Object temp = array[i];
			array[i] = array[j];
			array[j] = temp;
			i++;
			j--;
		}
	}

	public static void reverse(int[] array)
	{
		reverse(array, 0, array.length);
	}

	/**
	 * Reverses the elements in a range of an array. Following a call to this
	 * method, an element in the range given by index and count
	 * which was previously located at index i will now be located at
	 * index index + (index + count - i - 1).
	 */
	public static void reverse(int[] array, int index, int length)
	{
		if (array == null)
			throw new NullPointerException("array");

		if (index < 0 || length < 0)
			throw new IllegalArgumentException((index < 0 ? "index" : "length"));

		if (array.length - index < length)
			throw new IllegalArgumentException("length");

		int i = index;
		int j = index + length - 1;
		while (i < j)
		{
			int temp = array[i];
			array[i] = array[j];
			array[j] = temp;
			i++;
			j--;
		}
	}

	public static void reverse(long[] array)
	{
		reverse(array, 0, array.length);
	}

	// Duplicate of reverse(int[] array, int index, int length)
	public static void reverse(long[] array, int index, int length)
	{
		if (array == null)
			throw new NullPointerException("array");

		if (index < 0 || length < 0)
			throw new IllegalArgumentException((index < 0 ? "index" : "length"));

		if (array.length - index < length)
			throw new IllegalArgumentException("length");

		int i = index;
		int j = index + length - 1;
		while (i < j)
		{
			long temp = array[i];
			array[i] = array[j];
			array[j] = temp;
			i++;
			j--;
		}
	}

	// Duplicate of reverse(int[] array, int index, int length)
	public static void reverse(byte[] array, int index, int length)
	{
		if (array == null)
			throw new NullPointerException("array");

		if (index < 0 || length < 0)
			throw new IllegalArgumentException((index < 0 ? "index" : "length"));

		if (array.length - index < length)
			throw new IndexOutOfBoundsException("length");

		int i = index;
		int j = index + length - 1;
		while (i < j)
		{
			byte temp = array[i];
			array[i] = array[j];
			array[j] = temp;
			i++;
			j--;
		}
	}

	public static void reverse(byte[] array)
	{
		reverse(array, 0, array.length);
	}


	// Duplicate of reverse(int[] array, int index, int length)
	public static void reverse(char[] array, int index, int length)
	{
		if (array == null)
			throw new NullPointerException("array");

		if (index < 0 || length < 0)
			throw new IndexOutOfBoundsException((index < 0 ? "index" : "length"));

		if (array.length - index < length)
			throw new IndexOutOfBoundsException("length");

		int i = index;
		int j = index + length - 1;
		while (i < j)
		{
			char temp = array[i];
			array[i] = array[j];
			array[j] = temp;
			i++;
			j--;
		}
	}

	public static void reverse(char[] array)
	{
		reverse(array, 0, array.length);
	}

	public static void reverse(short[] array)
	{
		reverse(array, 0, array.length);
	}

	// Duplicate of reverse(int[] array, int index, int length)
	public static void reverse(short[] array, int index, int length)
	{
		if (array == null)
			throw new NullPointerException("array");

		if (index < 0 || length < 0)
			throw new IllegalArgumentException((index < 0 ? "index" : "length"));

		if (array.length - index < length)
			throw new IllegalArgumentException("length");

		int i = index;
		int j = index + length - 1;
		while (i < j)
		{
			short temp = array[i];
			array[i] = array[j];
			array[j] = temp;
			i++;
			j--;
		}
	}


	/**
	 * Sets a range of elements in the Array to zero, to false, or to a null reference,
	 * depending on the element type.
	 */

	public static void clear(byte[] array, int index, int length)
	{
		Arrays.fill(array, index, index + length, (byte)0);
	}

	public static void clear(short[] array, int index, int length)
	{
		Arrays.fill(array, index, index + length, (short)0);
	}

	public static void clear(char[] array, int index, int length)
	{
		Arrays.fill(array, index, index + length, (char)0);
	}

	public static void clear(int[] array, int index, int length)
	{
		Arrays.fill(array, index, index + length, 0);
	}

	public static void clear(long[] array, int index, int length)
	{
		Arrays.fill(array, index, index + length, 0);
	}

	public static void clear(float[] array, int index, int length)
	{
		Arrays.fill(array, index, index + length, 0);
	}

	public static void clear(double[] array, int index, int length)
	{
		Arrays.fill(array, index, index + length, 0);
	}

	public static void clear(boolean[] array, int index, int length)
	{
		Arrays.fill(array, index, index + length, false);
	}

	public static void clear(Object[] array, int index, int length)
	{
		Arrays.fill(array, index, index + length, null);
	}

	// JAVA-added
	public static void fill(byte[] buffer, int offset, int count, byte value)
	{
		//Note Java fill takes two indexes, not index and count.
		Arrays.fill(buffer, offset, offset + count, value);
	}

	public static void sort(int[] array, int index, int length)
	{
		Arrays.sort(array, index, index + length);
	}

	public static void sort(long[] array, int index, int length)
	{
		Arrays.sort(array, index, index + length);
	}

	public static void sort(byte[] array, int index, int length)
	{
		Arrays.sort(array, index, index + length);
	}

	public static void sort(char[] array, int index, int length)
	{
		Arrays.sort(array, index, index + length);
	}

	public static void sort(float[] array, int index, int length)
	{
		Arrays.sort(array, index, index + length);
	}

	public static void sort(double[] array, int index, int length)
	{
		Arrays.sort(array, index, index + length);
	}

	public static void sort(int[] array)
	{
		Arrays.sort(array);
	}

	public static void sort(long[] array)
	{
		Arrays.sort(array);
	}

	public static void sort(byte[] array)
	{
		Arrays.sort(array);
	}

	public static void sort(double[] array)
	{
		Arrays.sort(array);
	}

	public static void sort(float[] array)
	{
		Arrays.sort(array);
	}
	public static void sort(char[] array)
	{
		Arrays.sort(array);
	}

	/**
	 * Emulates System.Array.Sort(string[]): sorts strings using "string-sort" algorithm.
	 */
	public static void sort(String[] array)
	{
		final Collator collator = CollatorProvider.getStringSortCollator();

		Arrays.sort(array, new Comparator<String>()
		{
			public int compare(String obj1, String obj2)
			{
				return collator.compare(obj1, obj2);
			}
		});
	}

	/**
	 * Emulates System.Array.Sort(string[]): sorts strings using "string-sort" algorithm.
	 */
	public static void sort(String[] array, int index, int count)
	{
		final Collator collator = CollatorProvider.getStringSortCollator();

		// fromIndex - inclusive; toIndex - exclusive
		int fromIndex = index;
		int toIndex = index + count;
		Arrays.sort(array, fromIndex, toIndex, new Comparator<String>()
		{
			public int compare(String obj1, String obj2)
			{
				return collator.compare(obj1, obj2);
			}
		});
	}

	public static void sort(String[] array, Comparator comparator)
	{
		Arrays.sort(array, comparator);
	}

	public static void sort(Object[] array, Comparator comparator)
	{
		Arrays.sort(array, comparator);
	}

	public static void sort(Object[] array)
	{
		Arrays.sort(array);
	}

	public static void sort(Object[] array, int index, int count)
	{
		Arrays.sort(array, index, index + count);
	}

	//JAVA-added
	/**
	 * The method works only for flat arrays.
	 * <p>
	 * Unlike C# arrays that extend Array, Java arrays extend Object.
	 * So we can't use in java constructs like ((Array)array).clone() for arrays of
	 * unknown type - we have to know exact type of an array to call this type's clone().
	 */
	public static Object clone(Object array)
	{
		if (array instanceof int[])
			return ((int[])array).clone();

		if (array instanceof Object[])
			return ((Object[])array).clone();

		if (array instanceof byte[])
			return ((byte[])array).clone();

		if (array instanceof char[])
			return ((char[])array).clone();

		if (array instanceof short[])
			return ((short[])array).clone();

		if (array instanceof long[])
			return ((long[])array).clone();

		if (array instanceof float[])
			return ((float[])array).clone();

		if (array instanceof double[])
			return ((double[])array).clone();

		return null;
	}

	//JAVA-added
	/**
	 * The method works only for flat arrays.
	 * <p>
	 * Unlike C# arrays that extend Array, Java arrays extend Object.
	 * So we can't use in Java System.arraycopy() like Array.Copy in C# for arrays of
	 * diferrent type.
	 */
	public static void copy(Object src,  int  srcPos,
							  Object dest, int destPos,
							  int length)
	{
		if (src.getClass().equals(dest.getClass()))
		{
			System.arraycopy(src, srcPos, dest, destPos, length);
			return;
		}

		if (dest instanceof int[])
		{
			for (int i = 0; i < length; i++)
				((int[])dest)[destPos + i] = Array.getInt(src,srcPos + i);
			return;
		}

		if (dest instanceof Object[])
		{
			for (int i = 0; i < length; i++)
				((Object[])dest)[destPos + i] = Array.get(src,srcPos + i);
			return;
		}


		if (dest instanceof byte[])
		{
			for (int i = 0; i < length; i++)
				((byte[])dest)[destPos + i] = Array.getByte(src,srcPos + i);
			return;
		}

		if (dest instanceof char[])
		{
			for (int i = 0; i < length; i++)
				((char[])dest)[destPos + i] = Array.getChar(src,srcPos + i);
			return;
		}

		if (dest instanceof short[])
		{
			for (int i = 0; i < length; i++)
				((short[])dest)[destPos + i] = Array.getShort(src,srcPos + i);
			return;
		}

		if (dest instanceof long[])
		{
			for (int i = 0; i < length; i++)
				((long[])dest)[destPos + i] = Array.getLong(src,srcPos + i);
			return;
		}

		if (dest instanceof float[])
		{
			for (int i = 0; i < length; i++)
				((float[])dest)[destPos + i] = Array.getFloat(src,srcPos + i);
			return;
		}

		if (dest instanceof double[])
		{
			for (int i = 0; i < length; i++)
				((double[])dest)[destPos + i] = Array.getDouble(src,srcPos + i);
			return;
		}

		throw new ArrayStoreException("Not found type: " + dest.getClass().toString());
	}

	public static void blockCopy(Object src,  int  srcPos,
			Object dest, int destPos,
			int length)
	{
		if (destPos < 0)
			System.arraycopy(src, srcPos - destPos, dest, 0, length + destPos);
		else
			System.arraycopy(src, srcPos, dest, destPos, length);
	}

	//java-added method: when we use arithmetic, we have to convert C# bytes to java shorts because C# bytes unsigned.
	public static short[] toShortArray(byte[] byteArray)
	{
		short[] shortArray = new short[byteArray.length];

		for (int i = 0; i < byteArray.length; i++)
			shortArray[i] = (short) (byteArray[i] & 0xff);

		return shortArray;
	}

	//JAVA-added
	public static byte[] toByteArray(short[] shortArray)
	{
		byte[] byteArray = new byte[shortArray.length];

		for (int i = 0; i < byteArray.length; i++)
			byteArray[i] = (byte) (shortArray[i] & 0xff);

		return byteArray;
	}

	//JAVA-added, stolen from java.lang.String
	/*
	 * Common private utility method used to bounds check the byte array
	 * and requested offset & length values used by the String(byte[],..)
	 * constructors.
	 */
	public static void checkBounds(byte[] bytes, int offset, int length)
	{
		if (bytes == null)
			throw new NullPointerException( "bytes");
		if (length < 0)
			throw new IndexOutOfBoundsException("length");
		if (offset < 0)
			throw new IndexOutOfBoundsException("offset");
		if (offset > bytes.length - length)
			throw new IndexOutOfBoundsException("offset + length");
	}

	public static void checkBounds(int[] array, int offset, int length)
	{
		if (length < 0)
			throw new IndexOutOfBoundsException("length");
		if (offset < 0)
			throw new IndexOutOfBoundsException("offset");
		if (offset > array.length - length)
			throw new IndexOutOfBoundsException("offset + length");
	}

	//JAVA-added for debug
	public static String arrayToString(byte[] data, int start, int count)
	{
		StringBuilder builder = new StringBuilder();
		while (count > 0)
		{
			char c = (char) data[start];
			if (Character.isISOControl(c))
				c = '.';
			else if (c > 127)
				c += (1040 + 128);//Cyrillic 'A' so the output will be as in WinHex
			builder.append(c);
			start++;
			count--;
		}
		return builder.toString();
	}

	//JAVA-added for debug
	public static String arrayToStringCombined(byte[] data, int start, int count)
	{
		StringBuilder builder = new StringBuilder();
		count -= start;
		for (int i = start, len = 16; i < count; i += 16)
		{
			if (i + len > count)
				len = count - i;

			builder.append(dumpArray(data, i, len)).
					append(" | ").
					append(arrayToString(data, i, len)).
					append("\n");
		}

		return builder.toString();
	}

	//JAVA-added (from ArayUtil) for debug
	public static String dumpArray(byte[] data, int start, int count)
	{
		StringBuilder builder = new StringBuilder();
		while (count > 0)
		{
			//JAVA-changed from:
			//builder.AppendFormat("{0:X2} ", data[start]);
			builder.append(JavaUtil.toHexString(data[start], 2)).append(" ");
			start++;
			count--;
		}
		return builder.toString();
	}

	public static <T> void resize(Ref<T> array, int newSize)
	{
		if (array == null)
			return;

		T val =  array.get();
		if (val == null || !val.getClass().isArray())
			return;

		if (val instanceof int[])
			array.set((T) Arrays.copyOf((int[]) val, newSize));

		else if (val instanceof byte[])
			array.set((T) Arrays.copyOf((byte[]) val, newSize));

		else if (val instanceof short[])
			array.set((T) Arrays.copyOf((short[]) val, newSize));

		else if (val instanceof boolean[])
			array.set((T) Arrays.copyOf((boolean[]) val, newSize));

		else if (val instanceof long[])
			array.set((T) Arrays.copyOf((long[]) val, newSize));

		else if (val instanceof double[])
			array.set((T) Arrays.copyOf((double[]) val, newSize));

		else if (val instanceof float[])
			array.set((T) Arrays.copyOf((float[]) val, newSize));

		else if (val instanceof char[])
			array.set((T) Arrays.copyOf((char[]) val, newSize));

		else
			array.set((T) Arrays.copyOf((T[]) val, newSize));
	}

	/**
	 * JAVA-added to emulate .Net enumerable arrays.
	 */
	public static <T> Iterable<T> iterableArray(final Object array)
	{
		return new Iterable<T>()
		{
			public Iterator<T> iterator()
			{
				if (array instanceof Object[])
					return iteratorForObjectArray((Object[])array);

                //In JAVA primitive arrays looks like one object. We create list with autoboxing primitive type to class
				if (array instanceof int[])
				{
					List<Integer> li = new ArrayList<Integer>();
					for (int value : (int[])array)
						li.add(value);
					return (Iterator<T>) li.iterator();
				}

				if (array instanceof byte[])
				{
					List<Byte> li = new ArrayList<Byte>();
					for (byte value : (byte[])array)
						li.add(value);
					return (Iterator<T>) li.iterator();
				}

				if (array instanceof char[])
				{
					List<Character> li = new ArrayList<Character>();
					for (char value : (char[])array)
						li.add(value);
					return (Iterator<T>) li.iterator();
				}

				if (array instanceof short[])
				{
					List<Short> li = new ArrayList<Short>();
					for (short value : (short[])array)
						li.add(value);
					return (Iterator<T>) li.iterator();
				}

				if (array instanceof long[])
				{
					List<Long> li = new ArrayList<Long>();
					for (long value : (long[])array)
						li.add(value);
					return (Iterator<T>) li.iterator();
				}

				if (array instanceof float[])
				{
					List<Float> li = new ArrayList<Float>();
					for (float value : (float[])array)
						li.add(value);
					return (Iterator<T>) li.iterator();
				}

				if (array instanceof double[])
				{
					List<Double> li = new ArrayList<Double>();
					for (double value : (double[])array)
						li.add(value);
					return (Iterator<T>) li.iterator();
				}

				if (array instanceof boolean[])
				{
					List<Boolean> li = new ArrayList<Boolean>();
					for (boolean value : (boolean[])array)
						li.add(value);
					return (Iterator<T>) li.iterator();
				}

				return iteratorForArray((T)array);
			}
		};
	}

	/**
	 * JAVA-added to emulate .Net enumeration over arrays.
	 * Note: because of generics and Iterator<T> contract this doesn't work for primitive types!
	 */
	public static <T> Iterator<T> iteratorForArray(final T... array)
	{
		// Optimized code from Google Collections.
		return new Iterator<T>()
		{
			final int length = array.length;
			int index = 0;

			public boolean hasNext()
			{
				return index < length;
			}

			public T next()
			{
				try
				{
					T node = array[index];
					index++;
					return node;
				}
				catch (ArrayIndexOutOfBoundsException e)
				{
					throw new NoSuchElementException();
				}
			}

			public void remove()
			{
				throw new UnsupportedOperationException();
			}
		};
	}

	public static <T> Iterator<T> iteratorForObjectArray(final Object[] array)
	{
		return new Iterator<T>()
		{
			final int length = array.length;
			int index = 0;

			public boolean hasNext()
			{
				return index < length;
			}

			public T next()
			{
				try
				{
					T node = (T) array[index];
					index++;
					return node;
				}
				catch (ArrayIndexOutOfBoundsException e)
				{
					throw new NoSuchElementException();
				}
			}

			public void remove()
			{
				throw new UnsupportedOperationException();
			}
		};
	}
}
