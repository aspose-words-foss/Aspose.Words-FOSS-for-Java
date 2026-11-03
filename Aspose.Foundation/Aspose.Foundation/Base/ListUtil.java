// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/07/2017 by Alexey Butalov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msObject;
import java.util.Collections;


/// <summary>
/// Provides utility methods to work with <see cref="List{T}"/> class.
/// </summary>
public /*static*/ class ListUtil
{
	/* Simulation of static class by using private constructor */
	private ListUtil()
	{}

    /// <summary>
    /// Increases the capacity of the specified list, if necessary, to ensure that it can hold at least 
    /// the number of elements specified by the minimum capacity argument.
    /// </summary>
    public static <T> void ensureCapacity(ArrayList<T> list, int capacity)
    {
        ListUtilPal.ensureCapacity(list, capacity);
    }

    public static <T> void addRange(ArrayList<T> destination, ArrayList<T> source)
    {
        msArrayList.addRange(destination, source);
    }

    public static <TD, TS extends TD> void addRange(ArrayList<TD> destination, Iterable<TS> source)
    {
        for (TS item : source)
            msArrayList.add(destination, item);
    }

    /// <summary>
    /// Checks whether values in the lists are equals.
    /// </summary>
    public static <T> boolean checkAreEquals(ArrayList<T> arg1, ArrayList<T> arg2)
    {
        if (arg1.size() != arg2.size())
            return false;

        boolean valuesAreEquals = true;
        for (int i = 0; i < arg1.size(); i++)
        {
            valuesAreEquals &= msObject.equals(arg1.get(i), arg2.get(i));
            if (!valuesAreEquals)
                break;
        }

        return valuesAreEquals;
    }

    /// <summary>
    /// Returns an List containing the elements of the specified collection in reverse order.
    /// </summary>
    public static <T> ArrayList<T> reverse(Iterable<T> collection)
    {
        ArrayList<T> list = msArrayList.ctor(collection);
        Collections.reverse(list);
        return list;
    }

    public static <T> ArrayList<T> repeat(T value, int count)
    {
        ArrayList<T> list = new ArrayList<T>();
        for (int i = 0; i < count; i++)
            msArrayList.add(list, value);
        return list;
    }

    public static void sort(ArrayList<String> strings)
    {
        ListUtilPal.sort(strings);
    }
}

