// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/03/2009 by Konstantin Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Comparator;


/// <summary>
/// A complementary class to <see cref="StringOrdinalComparer"/>.
/// Use this class when you need to create a <see cref="SortedList"/> with case-insensitive 
/// string keys and you will export this list to document formats that have text golds.
/// </summary>
public class StringOrdinalIgnoreCaseComparer implements Comparator<String>
{
    /// <summary>
    /// Use the singleton instance instead.
    /// </summary>
    private StringOrdinalIgnoreCaseComparer()
    {
    }

    public int compare(String x, String y)
    {
        return StringUtil.compareOrdinalIgnoreCase(x, y);
    }

    /// <summary>
    /// Do not create instances of this class. Use this instance instead.
    /// </summary>
    public static /*final*/ StringOrdinalIgnoreCaseComparer DEFAULT = new StringOrdinalIgnoreCaseComparer();
}

