// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/08/2005 by Roman Korchagin

// Parts of this file are derived, by Aspose, from .NET Framework code. Microsoft publishes that source
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/collections/generic/arraysorthelper.cs
//
// Those parts: Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see THIRD-PARTY-NOTICES.md in the root of this repository.

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.Guid;
import com.aspose.ms.System.msArray;


/// <summary>
/// Array related utility functions.
/// </summary>
public /*static*/ class ArrayUtil
{
	/* Simulation of static class by using private constructor */
	private ArrayUtil()
	{}

    /// <summary>
    /// Compares two arrays.
    /// </summary>
    public static boolean isArrayEqual(char[] array1, char[] array2)
    {
        if (array1 == array2)
            return true;

        if (array1 == null || array2 == null)
            return false;

        if (array1.length != array2.length)
            return false;

        for (int i = 0; i < array1.length; ++i)
        {
            if (array1[i] != array2[i])
                return false;
        }

        return true;
    }

    public static boolean isArrayEqual(byte[] array1, byte[] array2)
    {
        if (array1 == array2)
            return true;

        if (array1 == null || array2 == null)
            return false;

        if (array1.length != array2.length)
            return false;

        for (int i = 0; i < array1.length; ++i)
        {
            if (array1[i] != array2[i])
                return false;
        }
        return true;
    }

    public static boolean isArrayEqual(short[] array1, short[] array2)
    {
        if (array1 == array2)
            return true;

        if (array1 == null || array2 == null)
            return false;

        if (array1.length != array2.length)
            return false;

        for (int i = 0; i < array1.length; ++i)
        {
            if (array1[i] != array2[i])
                return false;
        }
        return true;
    }

    public static boolean isArrayEqual(int[] array1, int[] array2)
    {
        if (array1 == array2)
            return true;

        if (array1 == null || array2 == null)
            return false;

        if (array1.length != array2.length)
            return false;

        for (int i = 0; i < array1.length; ++i)
        {
            if (array1[i] != array2[i])
                return false;
        }
        return true;
    }

    public static boolean isArrayEqual(long[] array1, long[] array2)
    {
        if (array1 == array2)
            return true;

        if (array1 == null || array2 == null)
            return false;

        if (array1.length != array2.length)
            return false;

        for (int i = 0; i < array1.length; ++i)
        {
            if (array1[i] != array2[i])
                return false;
        }
        return true;
    }

    public static boolean isArrayEqual(float[] array1, float[] array2)
    {
        if (array1 == array2)
            return true;

        if (array1 == null || array2 == null)
            return false;

        if (array1.length != array2.length)
            return false;

        for (int i = 0; i < array1.length; ++i)
        {
            if (array1[i] != array2[i])
                return false;
        }
        return true;
    }

    public static boolean isArrayEqual(double[] array1, double[] array2)
    {
        if (array1 == array2)
            return true;

        if (array1 == null || array2 == null)
            return false;

        if (array1.length != array2.length)
            return false;

        for (int i = 0; i < array1.length; ++i)
        {
            if (array1[i] != array2[i])
                return false;
        }
        return true;
    }

    public static <T> boolean isArrayEqual(T[] array1, T[] array2)
    {
        if (array1 == array2)
            return true;

        if (array1 == null || array2 == null)
            return false;

        if (array1.length != array2.length)
            return false;

        for (int i = 0; i < array1.length; ++i)
        {
            if (!msObject.equals(array1[i], array2[i]))
                return false;
        }
        return true;
    }

    /// <summary>
    /// Dumps byte array into a string for debugging.
    /// </summary>
    public static String dumpArray(byte[] data)
    {
        return data == null ? "Null" : dumpArray(data, 0, data.length);
    }

    public static String dumpArray(byte[] data, int start, int count)
    {
        if (data == null)
            return "Null";

        StringBuilder builder = new StringBuilder();
        while (count > 0)
        {
            msStringBuilder.appendFormat(builder, "{0:X2} ", (data[start] & 0xFF));
            start++;
            count--;
        }
        return builder.toString();
    }

    /// <summary>
    /// Dumps integer array into a string for debugging.
    /// </summary>
    public static String dumpArray(int[] data, int start, int count)
    {
        if (data == null)
            return "Null";

        StringBuilder builder = new StringBuilder();
        for (int i = start; (i < data.length) && (count > 0); i++, count--)
            msStringBuilder.appendFormat(builder, "{0:X8} ", data[i]);

        return builder.toString();
    }

    public static String dumpArray(float[] data)
    {
        StringBuilder builder = new StringBuilder(data.length * 10);
        builder.append('[');
        boolean first = true;
        for (float f : data)
        {
            if (!first)
                msStringBuilder.append(builder, ", ");
            else
                first = false;

            builder.append(f);
        }

        builder.append(']');
        return builder.toString();
    }

    /// <summary>
    /// This code was copied and modified from Rotor.
    /// Performs a binary search without boxing elements of the array.
    /// </summary>
    /// <param name="array">Array to search in.</param>
    /// <param name="index">Start index.</param>
    /// <param name="length">Length of search area.</param>
    /// <param name="value">Value to search for.</param>
    /// <returns>Index of the found value or negated index of possible insertion.</returns>
    public static int binarySearch(int[] array, int index, int length, int value)
    {
        Debug.assertTrue(array != null);
        Debug.assertTrue((index >= 0) && (length >= 0));
        Debug.assertTrue(index + length - 1 <= array.length);

        int lo = index;
        int hi = index + length - 1;

        while (lo <= hi)
        {
            int median = (lo + hi) >> 1;
            int v = array[median];

            if (v == value)
                return median;

            if (v < value)
                lo = median + 1;
            else
                hi = median - 1;
        }

        return ~lo;
    }

    /// <summary>
    /// This code was copied and modified from Rotor.
    /// Performs a binary search without boxing elements of the array. If duplicate elements are encountered then the leftmost is returned.
    /// Please refer to https://en.wikipedia.org/wiki/Binary_search_algorithm#Procedure_for_finding_the_leftmost_element for details.
    /// </summary>
    /// <param name="array">Array to search in.</param>
    /// <param name="index">Start index.</param>
    /// <param name="length">Length of search area.</param>
    /// <param name="value">Value to search for.</param>
    /// <returns>Index of the found value or negated index of possible insertion.</returns>
    public static int binarySearchLeftmost(int[] array, int index, int length, int value)
    {
        Debug.assertTrue(array != null);
        Debug.assertTrue((index >= 0) && (length >= 0));
        Debug.assertTrue(index + length - 1 <= array.length);

        int lo = index;
        int hi = index + length;

        while (lo < hi)
        {
            int median = (lo + hi) >> 1;
            int v = array[median];

            if (v < value)
                lo = median + 1;
            else
                hi = median;
        }

        return (lo < array.length && array[lo] == value) ? lo : ~lo;
    }

    /// <summary>
    /// A copy of int[] BinarySearch() - see above.
    /// </summary>
    public static int binarySearch(short[] array, int index, int length, short value)
    {
        Debug.assertTrue(array != null);
        Debug.assertTrue((index >= 0) && (length >= 0));
        Debug.assertTrue(index + length - 1 <= array.length);

        int lo = index;
        int hi = index + length - 1;

        while (lo <= hi)
        {
            int median = (lo + hi) >> 1;
            short v = array[median];

            if (v == value)
                return median;

            if (v < value)
                lo = median + 1;
            else
                hi = median - 1;
        }

        return ~lo;
    }

    /// <summary>
    /// A copy of int[] BinarySearch() - see above.
    /// </summary>
    public static int binarySearch(long[] array, int index, int length, long value)
    {
        Debug.assertTrue(array != null);
        Debug.assertTrue((index >= 0) && (length >= 0));
        Debug.assertTrue(index + length - 1 <= array.length);

        int lo = index;
        int hi = index + length - 1;

        while (lo <= hi)
        {
            int median = (lo + hi) >> 1;
            long v = array[median];

            if (v == value)
                return median;

            if (v < value)
                lo = median + 1;
            else
                hi = median - 1;
        }

        return ~lo;
    }

    /// <summary>
    /// A copy of int[] BinarySearch() - see above.
    /// </summary>
    public static int binarySearch(byte[] array, int index, int length, byte value)
    {
        Debug.assertTrue(array != null);
        Debug.assertTrue((index >= 0) && (length >= 0));
        Debug.assertTrue(index + length - 1 <= array.length);

        int lo = index;
        int hi = index + length - 1;

        while (lo <= hi)
        {
            int median = (lo + hi) >> 1;
            byte v = array[median];

            if (v == value)
                return median;

            if ((v & 0xFF) < (value & 0xFF))
                lo = median + 1;
            else
                hi = median - 1;
        }

        return ~lo;
    }

    /// <summary>
    /// A copy of int[] BinarySearch() - see above.
    /// </summary>
    public static int binarySearch(float[] array, int index, int length, float value)
    {
        Debug.assertTrue(array != null);
        Debug.assertTrue((index >= 0) && (length >= 0));
        Debug.assertTrue(index + length - 1 <= array.length);

        int lo = index;
        int hi = index + length - 1;

        while (lo <= hi)
        {
            int median = (lo + hi) >> 1;
            float v = array[median];

            if (v == value)
                return median;

            if (v < value)
                lo = median + 1;
            else
                hi = median - 1;
        }

        return ~lo;
    }

    /// <summary>
    /// A copy of int[] BinarySearch() - see above.
    /// </summary>
    public static int binarySearch(double[] array, int index, int length, double value)
    {
        Debug.assertTrue(array != null);
        Debug.assertTrue((index >= 0) && (length >= 0));
        Debug.assertTrue(index + length - 1 <= array.length);

        int lo = index;
        int hi = index + length - 1;

        while (lo <= hi)
        {
            int median = (lo + hi) >> 1;
            double v = array[median];

            if (v == value)
                return median;

            if (v < value)
                lo = median + 1;
            else
                hi = median - 1;
        }

        return ~lo;
    }

    /// <summary>
    /// A copy of int[] BinarySearch() - see above.
    /// </summary>
    public static int binarySearch(char[] array, int index, int length, char value)
    {
        Debug.assertTrue(array != null);
        Debug.assertTrue((index >= 0) && (length >= 0));
        Debug.assertTrue(index + length - 1 <= array.length);

        int lo = index;
        int hi = index + length - 1;

        while (lo <= hi)
        {
            int median = (lo + hi) >> 1;
            char v = array[median];

            if (v == value)
                return median;

            if (v < value)
                lo = median + 1;
            else
                hi = median - 1;
        }

        return ~lo;
    }

    public static int binarySearch(String[] array, String value)
    {
        return binarySearch(array, 0, array.length, value);
    }

    public static int binarySearch(String[] array, int index, int length, String value)
    {
        Debug.assertTrue(array != null);
        Debug.assertTrue((index >= 0) && (length >= 0));
        Debug.assertTrue(index + length - 1 <= array.length);

        int lo = index;
        int hi = index + length - 1;

        while (lo <= hi)
        {
            int median = (lo + hi) / 2;
            String v = array[median];

            int compare = StringUtil.compareStringSort(v, value);

            if (compare == 0)
                return median;

            if (compare < 0)
                lo = median + 1;
            else
                hi = median - 1;
        }

        return ~lo;
    }

    /// <summary>
    /// Analogue of C memset.
    /// </summary>
    public static void setBytes(byte[] array, byte value, int offset, int length)
    {
        for (int i = 0; i < length; i++)
            array[i + offset] = value;
    }

    /// <summary>
    /// Copies safely one array into another.
    /// </summary>
    /// <remarks>
    /// Checks that the passed parameters are within the allowed bounds.
    /// </remarks>
    public static int copy(byte[] srcArray, int srcIndex, int length, byte[] dstArray, int dstIndex)
    {
        if (length < 1)
            return 0;

        if ((srcIndex < 0) || (srcIndex >= srcArray.length))
            return 0;

        if ((dstIndex < 0) || (dstIndex >= dstArray.length))
            return 0;

        // Ensure index will be in range of the source array.
        int maxLength = Math.min(length, srcArray.length - srcIndex);
        // Ensure index will be in range of the destination array.
        maxLength = Math.min(maxLength, dstArray.length - dstIndex);

        for (int i = 0; i < maxLength; i++)
            dstArray[dstIndex + i] = srcArray[srcIndex + i];

        return maxLength;
    }

    /// <summary>
    /// Analogue of C memcmp.
    /// </summary>
    public static boolean compareBytes(byte[] array1, byte[] array2, int length)
    {
        if (array1.length < length || array2.length < length)
            return false;

        for (int i = 0; i < length; i++)
        {
            if (array1[i] != array2[i])
                return false;
        }
        return true;
    }

    public static void checkArraysEqual(char[] array1, char[] array2)
    {
        int minLength = Math.min(array1.length, array2.length);
        for (int i = 0; i < minLength; ++i)
        {
            if (array1[i] != array2[i])
                throw new IllegalStateException(msString.format(EXCEPTION_ARRAY_VALUES,
                    i, array1[i], array2[i]));
        }

        if (array1.length != array2.length)
            throw new IllegalStateException(msString.format(EXCEPTION_ARRAY_LENGTHS, array1.length, array2.length));
    }

    public static void checkArraysEqual(byte[] array1, byte[] array2)
    {
        int minLength = Math.min(array1.length, array2.length);
        for (int i = 0; i < minLength; ++i)
        {
            if (array1[i] != array2[i])
                throw new IllegalStateException(msString.format(EXCEPTION_ARRAY_VALUES,
                    i, (array1[i] & 0xFF), (array2[i] & 0xFF)));
        }

        if (array1.length != array2.length)
            throw new IllegalStateException(msString.format(EXCEPTION_ARRAY_LENGTHS, array1.length, array2.length));
    }

    public static void checkArraysEqual(short[] array1, short[] array2)
    {
        int minLength = Math.min(array1.length, array2.length);
        for (int i = 0; i < minLength; ++i)
        {
            if (array1[i] != array2[i])
                throw new IllegalStateException(msString.format(EXCEPTION_ARRAY_VALUES,
                    i, array1[i], array2[i]));
        }

        if (array1.length != array2.length)
            throw new IllegalStateException(msString.format(EXCEPTION_ARRAY_LENGTHS, array1.length, array2.length));
    }

    public static void checkArraysEqual(int[] array1, int[] array2)
    {
        int minLength = Math.min(array1.length, array2.length);
        for (int i = 0; i < minLength; ++i)
        {
            if (array1[i] != array2[i])
                throw new IllegalStateException(msString.format(EXCEPTION_ARRAY_VALUES,
                    i, array1[i], array2[i]));
        }

        if (array1.length != array2.length)
            throw new IllegalStateException(msString.format(EXCEPTION_ARRAY_LENGTHS, array1.length, array2.length));
    }

    public static void checkArraysEqual(float[] array1, float[] array2, float delta)
    {
        int minLength = Math.min(array1.length, array2.length);
        for (int i = 0; i < minLength; ++i)
        {
            if ((array1[i] - array2[i]) > delta)
                throw new IllegalStateException(msString.format("Array values are different at position {0}: {1} vs. {2} with delta {3}.",
                    i, array1[i], array2[i], delta));
        }

        if (array1.length != array2.length)
            throw new IllegalStateException(msString.format(EXCEPTION_ARRAY_LENGTHS, array1.length, array2.length));
    }

    public static void printArrayDiff(byte[] array1, byte[] array2)
    {
        if (array1.length != array2.length)
            throw new IllegalStateException(msString.format(EXCEPTION_ARRAY_LENGTHS, array1.length, array2.length));

        for (int i = 0; i < array1.length; ++i)
        {
            if (array1[i] != array2[i])
                Debug.writeLine(msString.format("Array values are different at position 0x{0:X2}, 0x{1:X2} 0x{2:X2}.", i, (array1[i] & 0xFF), (array2[i] & 0xFF)));
        }
    }

    /// <summary>
    /// Creates a new byte array and copies first elements of the source array to it. Number of copied elements is
    /// min(source array length, destination array length).
    /// </summary>
    /// <param name="srcArray">Source array.</param>
    /// <param name="length">New array length.</param>
    /// <returns>New array.</returns>
    public static byte[] resizeArray(byte[] srcArray, int length)
    {
        byte[] dstArray = new byte[length];

        if (length > srcArray.length)
            length = srcArray.length;
        System.arraycopy((srcArray), 0, (dstArray), 0, (length));
        return dstArray;
    }

    /// <summary>
    /// Returns true if the byte array is not null and not empty.
    /// </summary>
    public static boolean hasData(byte[] byteArray)
    {
        return (byteArray != null) && (byteArray.length > 0);
    }

    /// <summary>
    /// Returns true if the string array is not null and not empty.
    /// </summary>
    public static boolean hasData(String[] stringArray)
    {
        return (stringArray != null) && (stringArray.length > 0);
    }

    /// <summary>
    /// Merges string array to string.
    /// </summary>
    public static String stringArrayToString(String[] stringArray)
    {
        return hasData(stringArray) ? msString.concat(stringArray) : "";
    }

    /// <summary>
    /// Behave exactly as ArrayList.Contains(). Full duplicate of above method.
    /// </summary>
    public static boolean contains(String[] array, String value)
    {
        if (value == null)
        {
            for (String item : array)
            {
                if (item == null)
                    return true;
            }
        }
        else
        {
            for (String item : array)
            {
                if (msString.equals(value, item, StringComparison.ORDINAL))
                    return true;
            }
        }

        return false;
    }

    /// <summary>
    /// Behave exactly as ArrayList.Contains().
    /// </summary>
    public static boolean contains(byte[] array, byte value)
    {
        for (byte item : array)
        {
            if (value == item)
                return true;
        }

        return false;
    }

    /// <summary>
    /// Behave exactly as ArrayList.Contains().
    /// </summary>
    public static boolean contains(int[] array, int value)
    {
        for (int item : array)
        {
            if (value == item)
                return true;
        }
        return false;
    }

    /// <summary>
    /// Returns true if source array contains target array.
    /// </summary>
    public static boolean contains(byte[] source, byte[] target)
    {
        if (source == null || target == null)
            return false;

        byte first = target[0];
        int srcLength = source.length;
        int targetLength = target.length;

        if (targetLength > srcLength)
            return false;

        for (int i = 0; i < srcLength; i++)
        {
            /* Look for first character. */
            if (source[i] != first)
                continue;

            /* Found first character, now look at the rest of v2 */
            int j = i + 1;
            int end = j + targetLength - 1;
            if (end > srcLength)
                return false;

            for (int k = 1; j < end; j++, k++)
            {
                if (source[j] != target[k])
                    return false;
            }

            /* Found whole string. */
            return true;
        }

        return false;
    }

    /// <summary>
    /// Returns a position of subarray in a source array.
    /// </summary>
    public static int indexOf(byte[] source, byte[] subarray)
    {
        return indexOf(source, subarray, 0);
    }

    /// <summary>
    /// Returns a position of subarray in a source array.
    /// </summary>
    public static int indexOf(byte[] source, byte[] subarray, int sourceStartIndex)
    {
        return indexOf(source, subarray, sourceStartIndex, source.length);
    }

    /// <summary>
    /// Returns a position of subarray in a source array.
    /// </summary>
    public static int indexOf(byte[] source, byte[] subarray, int sourceStartIndex, int sourceEndIndex)
    {
        int endIndex = Math.min(source.length - subarray.length + 1, sourceEndIndex);
        for (int i = sourceStartIndex; i < endIndex; i++)
        {
            if (source[i] == subarray[0])
            {
                boolean found = true;

                for (int j = 1; j < subarray.length; j++)
                {
                    if (source[i + j] != subarray[j])
                    {
                        found = false;
                        break;
                    }
                }

                if (found)
                    return i;
            }
        }

        return -1;
    }

    /// <summary>
    /// Added for debug
    /// </summary>
    public static String arrayToString(byte[] data, int start, int count)
    {
        StringBuilder builder = new StringBuilder();
        while (count > 0)
        {
            char c = (char)(data[start] & 0xFF);
            /*uint*/int codePoint = (/*uint*/int)(data[start] & 0xFF);
            if ((codePoint & 0xFFFFFFFFL) <= 0x9F && (codePoint >>> 5 == 0))
                c = '.';
            else if (c > 127)
                c = (char)((data[start] & 0xFF) + 1040 - 128);//Cyrillic 'A' so the output will be as in WinHex
            builder.append(c);
            start++;
            count--;
        }
        return builder.toString();
    }

    /// <summary>
    /// Added for debug in Java
    /// </summary>
    public static String arrayToStringCombined(byte[] data, int start, int count)
    {
        StringBuilder builder = new StringBuilder();
        count -= start;
        int len = 16;
        int i = start;
        while (i < count)
        {
            if (i + len > count)
                len = count - i;

            msStringBuilder.append(msStringBuilder.append(msStringBuilder.append(builder, dumpArray(data, i, len)), " | "), arrayToString(data, i, len)).
                    append('\n');

            i += 16;
        }
        return builder.toString();
    }

    /// <summary>
    /// Compares the specified char with each member of array of chars until the first successful comparison.
    /// </summary>
    public static boolean findCharInArray(char[] chars, char sample)
    {
        for (char c : chars)
        {
            if (sample == c)
                return true;
        }
        return false;
    }

    /// <summary>
    /// Fills byte array in natural order. It's different from <see ref="Guid.ToByteArray"/>.
    /// </summary>
    public static byte[] guidToByteArray(Guid guid)
    {
        byte[] guidBytes = guid.toByteArray();

        msArray.reverse(guidBytes, 0, 4);
        msArray.reverse(guidBytes, 4, 2);
        msArray.reverse(guidBytes, 6, 2);

        return guidBytes;
    }

    /// <summary>
    /// Writes 32-bit unsigned integer represented as BigEndian sequence of 4 bytes to byte array at the specified offset.
    /// </summary>
    /// <param name="value">The 32-bit unsigned integer value to write to byte array.</param>
    /// <param name="bytes">The one-dimensional array of bytes./></param>
    /// <param name="offset">A 32-bit integer that represents the index in the byte array at which copying begins.</param>
    public static void writeUInt32ToByteArray(int value, byte[] bytes, int offset)
    {
        for (int i = 0; i < 4; i++)
        {
            bytes[offset + i] = (byte)value;
            value >>= 8;
        }
    }

    /// <summary>
    /// Returns the index of the first occurrence of a <paramref name="value"/> in an <paramref name="array"/>.
    /// Uses a case-insensitive search.
    /// </summary>
    public static int indexOfIgnoreCase(String[] array, String value)
    {
        for (int i = 0; i < array.length; i++)
            if (StringUtil.equalsIgnoreCase(array[i], value))
                return i;

        return -1;
    }

    /// <summary>
    /// Concatenates two arrays of type T and returns an array with the combined elements.
    /// </summary>
    /// <typeparam name="T">The type of the elements in the arrays.</typeparam>
    /// <param name="first">The first array.</param>
    /// <param name="second">The second array.</param>
    /// <returns>An array containing the elements of both input arrays in the order they were passed.</returns>
    public static <T> T[] concat(T[] first, T[] second, T[] result)
    {
        if (first == null)
            return second;

        if (second == null)
            return first;

        if (result.length == first.length + second.length)
        {
            System.arraycopy(first, 0, result, 0, first.length);
            System.arraycopy(second, 0, result, first.length, second.length);

            return result;
        }

        return null;
    }

    public static int[] concat(int[] first, int[] second)
    {
        if (first == null)
            return second;

        if (second == null)
            return first;

        int[] result = new int[first.length + second.length];

        System.arraycopy(first, 0, result, 0, first.length);
        System.arraycopy(second, 0, result, first.length, second.length);

        return result;
    }

    public static <T> int concatLength(T[] first, T[] second)
    {
        if (second == null && first == null)
            return 0;

        if (first == null)
            return second.length;

        if (second == null)
            return first.length;

        return first.length + second.length;
    }

    public static /*final*/ byte[] EMPTY_BYTE_ARRAY = { };
    public static /*final*/ String[] EMPTY_STRING_ARRAY = { };
    public static /*final*/ Object[] EMPTY_OBJECT_ARRAY = { };

    private static final String EXCEPTION_ARRAY_LENGTHS = "Array lengths are different: {0} and {1}.";
    private static final String EXCEPTION_ARRAY_VALUES = "Array values are different at position 0x{0:X2}, 0x{1:X2}, 0x{2:X2}.";
}

