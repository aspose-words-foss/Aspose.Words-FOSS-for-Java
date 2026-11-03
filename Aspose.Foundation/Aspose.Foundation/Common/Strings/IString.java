// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/01/2020 by Edward Voronov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>Represents text as a sequence of <see cref="IChar"/> objects.</summary>
public interface IString extends Iterable<IChar>
{
    /// <summary>Gets the number of characters in the current <see cref="IString" /> object.</summary>
    public int getLength();

    /// <summary>Gets the <see cref="IChar" /> object at a specified position in the current <see cref="IString" /> object.</summary>
    public IChar get(int index);

    /// <summary>Reports the zero-based index of the first occurrence of the specified string in this instance.</summary>
    public int indexOf(String value);

    /// <summary>Returns a new string in which all occurrences of a specified string in the current instance are replaced with another specified string.</summary>
    public IString replace(String oldValue, String newValue);

    /// <summary>Returns a new string in which a specified number of characters in the current instance beginning at a specified position have been deleted.</summary>
    public IString remove(int startIndex, int count);

    /// <summary>Returns a copy of this string converted to uppercase.</summary>
    public IString toUpper();

    /// <summary>Returns a copy of this string converted to lowercase.</summary>
    public IString toLower();

    /// <summary>Converts the value of this instance to its equivalent string representation.</summary>
    public String toSystemString();
}

