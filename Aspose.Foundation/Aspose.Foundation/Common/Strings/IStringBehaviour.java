// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/01/2020 by Edward Voronov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>Provides common functionality related to <see cref="IChar"/>, <see cref="IString"/> and <see cref="IStringBuilder"/>.</summary>
public interface IStringBehaviour
{
    /// <summary>Returns the empty <see cref="IString"/> string.</summary>
    public IString getEmptyString();

    /// <summary>Indicates whether the specified string is null or an empty string.</summary>
    public boolean isNullOrEmpty(IString value);

    /// <summary>Initializes a new instance of the <see cref="IString" /> class.</summary>
    public IString createString(String value);

    /// <summary>Initializes a new instance of the <see cref="IStringBuilder" /> class.</summary>
    public IStringBuilder createBuilder();

    /// <summary>Initializes a new instance of the <see cref="IStringBuilder" /> class.</summary>
    public IStringBuilder createBuilder(IString value);

    /// <summary>Initializes a new instance of the <see cref="IStringBuilder" /> class.</summary>
    public IStringBuilder createBuilder(int capacity);
}

