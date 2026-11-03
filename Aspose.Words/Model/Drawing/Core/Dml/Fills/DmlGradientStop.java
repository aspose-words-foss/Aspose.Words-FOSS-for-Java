// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/03/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msDouble;
import com.aspose.ms.System.msInt;
import com.aspose.ArgumentUtil;
import com.aspose.ms.System.msObject;
import com.aspose.MathUtil;
import com.aspose.ms.System.msNumber;


/// <summary>
/// 20.1.8.36 gs (Gradient stops)
/// This element defines a gradient stop. A gradient stop consists of
/// a position where the stop appears in the color band.
/// </summary>
class DmlGradientStop implements Comparable<DmlGradientStop>
{
    /// <summary>
    /// Ctor without parameters.
    /// </summary>
    DmlGradientStop()
    {
    }

    /// <summary>
    /// Ctor that allows setting object properties.
    /// </summary>
    DmlGradientStop(double position, DmlColor color)
    {
        mPosition = position;
        mColor = color;
    }

    public int compareTo(DmlGradientStop gradientStop)
    {
        if (gradientStop == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "obj");

        int val = msDouble.compareTo(getPosition(), gradientStop.getPosition());
        if (val == 0)
            return msInt.compareTo(getOriginalOrder(), gradientStop.getOriginalOrder());

        return val;
    }

    public DmlGradientStop deepClone()
    {
        DmlGradientStop result = new DmlGradientStop();
        result.setColor(getColor().deepClone());
        result.setPosition(getPosition());
        return result;
    }


    public /*override*/ boolean equals(Object obj)
    {
        // Same instance.
        if (obj == this)
            return true;

        // Type or hashcode does not match.
        if (!ArgumentUtil.typeAndHashCodeMatches(this, obj))
            return false;

        DmlGradientStop value = (DmlGradientStop)obj;

        return msObject.equals(value.getColor(), getColor()) && MathUtil.areEqual(value.getPosition(), getPosition());
    }

    public /*override*/ int hashCode()
    {
        int hash = 0;
        hash ^= getColor().hashCode();
        hash ^= msNumber.doubleHashCode(getPosition());
        return hash;
    }



    DmlColor getColor()
    {
        if (mColor == null)
            mColor = new DmlHexRgbColor();
        return mColor;
    }
    void setColor(DmlColor value) { mColor = value; }

    /// <summary>
    /// Specifies where this gradient stop should appear in the color band.
    /// This position is specified in the range [0%, 100%], which corresponds
    /// to the beginning and the end of the color band respectively.
    /// Value is in fraction representation.
    /// </summary>
    double getPosition() { return mPosition; }
    void setPosition(double value) { mPosition = value; }

    int getOriginalOrder() { return mOriginalOrder; }
    void setOriginalOrder(int value) { mOriginalOrder = value; }

    private DmlColor mColor;
    private double mPosition;
    private int mOriginalOrder;
}

