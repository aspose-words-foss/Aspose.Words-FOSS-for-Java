// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/10/2008 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Comparator;
import com.aspose.ms.System.msString;


/// <summary>
/// You need to use this comparer when all of the following is true:
/// 1. You need to create a <see cref="SortedList"/> with case-sensitive string keys.
/// 2. You will export this list to document formats that have text golds.
/// 
/// This comparer implements string compare using Unicode character values exactly the way strings 
/// are sorted in Java. If you don't use this comparer, then golds in .NET and Java could be different 
/// because .NET default string sorting algorithm is different (it looks more like case-insensitive).
/// 
/// <seealso cref="StringOrdinalIgnoreCaseComparer"/>
/// </summary>
public class StringOrdinalComparer implements Comparator<String>
{
    /// <summary>
    /// Use the singleton instance instead.
    /// </summary>
    private StringOrdinalComparer()
    {
    }

    public int compare(String x, String y)
    {
        return msString.compareOrdinal(x, y);
    }

    /// <summary>
    /// Do not create instances of this class. Use this instance instead.
    /// </summary>
    public static /*final*/ StringOrdinalComparer DEFAULT = new StringOrdinalComparer();
}

