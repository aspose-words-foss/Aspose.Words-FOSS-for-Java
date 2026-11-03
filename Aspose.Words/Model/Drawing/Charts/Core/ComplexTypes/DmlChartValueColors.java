// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/03/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// This class represents the 2.24.3.86 CT_ValueColors complex type [MS-ODRAWXML]. It specifies
/// the color of individual stops composing a gradient for representing data values as color.
/// </summary>
class DmlChartValueColors implements Cloneable
{
    /// <summary>
    /// Returns a copy of this instance.
    /// </summary>
    DmlChartValueColors deepClone()
    {
        DmlChartValueColors lhs = (DmlChartValueColors)memberwiseClone();

        if (mMinimumColor != null)
            lhs.mMinimumColor = mMinimumColor.deepClone();
        if (mMiddleColor != null)
            lhs.mMiddleColor = mMiddleColor.deepClone();
        if (mMaximumColor != null)
            lhs.mMaximumColor = mMaximumColor.deepClone();

        return lhs;
    }

    /// <summary>
    /// Gets or sets the color of the lowest position of the gradient.
    /// </summary>
    DmlColor getMinimumColor() { return mMinimumColor; }
    void setMinimumColor(DmlColor value) { mMinimumColor = value; }

    /// <summary>
    /// Gets or sets the color of the position between the lowest and highest positions of the gradient.
    /// </summary>
    DmlColor getMiddleColor() { return mMiddleColor; }
    void setMiddleColor(DmlColor value) { mMiddleColor = value; }

    /// <summary>
    /// Gets or sets the color of the highest position of the gradient.
    /// </summary>
    DmlColor getMaximumColor() { return mMaximumColor; }
    void setMaximumColor(DmlColor value) { mMaximumColor = value; }

    /// <summary>
    /// Returns true if this instance of <see cref="DmlChartValueColors"/> is empty (all the colors are <c>null</c>).
    /// </summary>
    public boolean isEmpty() { return (mMinimumColor == null) && (mMiddleColor == null) && (mMaximumColor == null); }

    private DmlColor mMinimumColor;
    private DmlColor mMiddleColor;
    private DmlColor mMaximumColor;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

