// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/08/2006 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Min and max widths are often used in the layout algorithm in aggregate so it's reasonable
/// to encapsulate them together.
/// </summary>
class LayoutWidth
{
    void add(double value)
    {
        mMin += value;
        mMax += value;
    }

    void update(LayoutWidth newWidth)
    {
        mMin = Math.max(mMin, newWidth.getMin());
        mMax = Math.max(mMax, newWidth.getMax());
    }

    LayoutWidth deepClone()
    {
        LayoutWidth lhs = new LayoutWidth();

        lhs.setMin(mMin);
        lhs.setMax(mMax);

        return lhs;
    }

    double getMin() { return mMin; }
    void setMin(double value) { mMin = value; }

    double getMax() { return mMax; }
    void setMax(double value) { mMax = value; }


    private double mMin;
    private double mMax;
}
