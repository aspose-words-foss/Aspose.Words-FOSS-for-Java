// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/03/2024 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements interface for object that can have <see cref="ReflectionFormat"/>.
/// </summary>
interface IReflection
{
    /// <summary>
    /// Removes reflection from the parent object;
    /// </summary>
    public void removeReflection();

    /// <summary>
    /// Gets or sets a double value that specifies the degree of blur effect applied to the reflection effect.
    /// </summary>
    public double getBlur(); public void setBlur(double value);

    /// <summary>
    /// Gets or sets a double value that specifies the amount of separation of the reflected image from the object.
    /// </summary>
    public double getDistance(); public void setDistance(double value);

    /// <summary>
    /// Gets or sets a double value between 0.0 and 1.0 representing the size of the reflection
    /// as a percentage of the reflected object.
    /// </summary>
    public double getReflectionSize(); public void setReflectionSize(double value);

    /// <summary>
    /// Gets or sets a double value between 0.0 (opaque) and 1.0 (clear) representing the degree
    /// of transparency for the reflection effect.
    /// </summary>
    public double getReflectionTransparency(); public void setReflectionTransparency(double value);
}

