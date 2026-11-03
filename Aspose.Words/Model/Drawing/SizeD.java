// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/10/2017 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;

/// <summary>
/// Stores shape size as double precision floating-point numbers.
/// </summary>
class SizeD
{
    SizeD(double width, double height)
    {
        setWidth(width);
        setHeight(height);
    }

    SizeD()
    {
    }

    /// <summary>
    /// Returns true, if width or height of this SizeD is greater
    /// than width or height of the specified SizeD.
    /// </summary>
    boolean isExceeds(SizeD size)
    {
        return ((mWidth > size.getWidth()) || (mHeight > size.getHeight()));
    }

    /// <summary>
    /// Gets a value indicating whether this SizeD has zero width and height.
    /// </summary>
    boolean isEmpty() { return MathUtil.isZero(mWidth) && MathUtil.isZero(mHeight); }

    /// <summary>
    /// Gets or sets the horizontal component of size.
    /// </summary>
    double getWidth() { return mWidth; }
    void setWidth(double value) { mWidth = value; }

    /// <summary>
    /// Gets or sets the vertical component of size.
    /// </summary>
    double getHeight() { return mHeight; }
    void setHeight(double value) { mHeight = value; }

    private double mWidth;
    private double mHeight;
}

