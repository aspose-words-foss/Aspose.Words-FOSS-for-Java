// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/07/2021 by Edward Voronov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Provides support for lazy initialization.
/// </summary>
/// <typeparam name="T">The type of object that is being lazily initialized.</typeparam>
public abstract class Lazy<T extends Object>
{
    /// <summary>
    /// Gets the lazily initialized value of the current <see cref="Lazy{T}"/> instance.
    /// </summary>
    public T getValue() { return (mValue != null ? mValue : (mValue = initValue())); }

    /// <summary>
    /// The method that is invoked to produce the lazily initialized value when it is needed.
    /// </summary>
    protected abstract T initValue();

    private T mValue;
}

