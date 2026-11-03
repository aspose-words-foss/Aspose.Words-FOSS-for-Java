// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/03/2024 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents the reflection formatting for an object.
/// </summary>
/// <remarks>
/// <p>Use the <see cref="ShapeBase.Reflection"/> property to access reflection properties of an object.
/// You do not create instances of the <see cref="ReflectionFormat"/> class directly.</p>
/// </remarks>
/// <dev>
/// This class is the public facade for <see cref="IReflection"/>.
/// https://learn.microsoft.com/en-us/office/vba/api/word.reflectionformat
/// </dev>
public class ReflectionFormat
{
    /// <summary>
    /// Creates an instance of the <see cref="ReflectionFormat"/> class.
    /// </summary>
    ReflectionFormat(IReflection parent)
    {
        mParent = parent;
    }

    /// <summary>
    /// Removes <see cref="ReflectionFormat"/> from the parent object.
    /// </summary>
    public void remove()
    {
        mParent.removeReflection();
    }

    /// <summary>
    /// Gets or sets a double value that specifies the degree of blur effect applied to the reflection effect in points.
    /// The default value is 0.0.
    /// </summary>
    public double getBlur() { return mParent.getBlur(); }
    public void setBlur(double value) { mParent.setBlur(value); }

    /// <summary>
    /// Gets or sets a double value that specifies the amount of separation of the reflected image from the object in points.
    /// The default value is 0.0.
    /// </summary>
    public double getDistance() { return mParent.getDistance(); }
    public void setDistance(double value) { mParent.setDistance(value); }

    /// <summary>
    /// Gets or sets a double value between 0.0 and 1.0 representing the size of the reflection
    /// as a percentage of the reflected object.
    /// The default value is 0.0.
    /// </summary>
    public double getSize() { return mParent.getReflectionSize(); }
    public void setSize(double value) { mParent.setReflectionSize(value); }

    /// <summary>
    /// Gets or sets a double value between 0.0 (opaque) and 1.0 (clear) representing the degree
    /// of transparency for the reflection effect.
    /// The default value is 0.0.
    /// </summary>
    public double getTransparency() { return mParent.getReflectionTransparency(); }
    public void setTransparency(double value) { mParent.setReflectionTransparency(value); }

    /// <summary>
    /// The parent object.
    /// </summary>
    private /*final*/ IReflection mParent;
}

