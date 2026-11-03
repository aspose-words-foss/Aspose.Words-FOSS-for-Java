// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 31/05/2018 by Konstantin Sidorenko

package com.aspose;

import com.aspose.ms.java.text.CollatorProvider;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/// <summary>
/// Port this class manually to Java, C++, etc.
/// </summary>
//[JavaManual("Platform abstraction for List utilities. Manual porting by design.")]
/*static*/ class ListUtilPal
{
	/* Simulation of static class by using private constructor */
	private ListUtilPal()
	{}

    /// <summary>
    /// Manual implementation for <see cref="ListUtil.EnsureCapacity<T>(List<T> list, int capacity)"/>
    /// </summary>
    public static <T> void ensureCapacity(ArrayList<T> list, int capacity)
    {
        list.ensureCapacity(capacity);
    }

    /// <summary>
    /// WORDSNET-15886. Sorts list of strings using unicode rules for sorting strings
    /// </summary>
    public static void sort(ArrayList<String> list)
    {
        final Collator collator = CollatorProvider.getStringSortCollator();

        Collections.sort(list, new Comparator<String>()
        {
            public int compare(String obj1, String obj2)
            {
                return collator.compare(obj1, obj2);
            }
        });

    }
}

