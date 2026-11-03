// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/01/2020 by Edward Voronov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


/// <summary>Represents a mutable <see cref="IString"/> string of <see cref="IChar"/> characters.</summary>
public interface IStringBuilder
{
    /// <summary>Gets or sets the length of the current <see cref="IStringBuilder" /> object.</summary>
    public int getLength(); public void setLength(int value);

    /// <summary>Gets or sets the character at the specified character position in this instance.</summary>
    public IChar get(int index); public void set(int index, IChar value);

    /// <summary>Converts the value of this instance to a <see cref="IString" />.</summary>
    public IString toIString();

    /// <summary>Appends the string representation of a specified character to this instance.</summary>
    public IStringBuilder append(IChar c);

    /// <summary>Appends a copy of the specified string to this instance.</summary>
    public IStringBuilder append(IString s);

    /// <summary>Appends the string representation of a specified character to this instance.</summary>
    public IStringBuilder append(char c, IChar source);

    public IStringBuilder append(char c, int count, IChar source);

    /// <summary>Appends a copy of the specified string to this instance.</summary>
    public IStringBuilder append(String s, IChar source);

    /// <summary>Inserts the string representation of a specified character into this instance at the specified character position.</summary>
    public IStringBuilder insert(int index, char c, IChar source);

    /// <summary>Inserts a string into this instance at the specified character position.</summary>
    public IStringBuilder insert(int index, String s, IChar source);

    /// <summary>Inserts one or more copies of a specified string into this instance at the specified character position.</summary>
    public IStringBuilder insert(int index, String s, int count, ArrayList<IChar> sources);

    /// <summary>Replaces all occurrences of a specified string in this instance with another specified string.</summary>
    public IStringBuilder replace(String oldValue, String newValue);

    /// <summary>Replaces the character at the specified character position in this instance.</summary>
    public IStringBuilder replace(int index, char c, IChar source);

    /// <summary>Removes the specified range of characters from this instance.</summary>
    public IStringBuilder remove(int startIndex, int length);
}

