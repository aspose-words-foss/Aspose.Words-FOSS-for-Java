// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/10/2024 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;

/// <summary>
/// Represents 16.16 fixed-point value.
/// </summary>
public class FixedPoint16Dot16 extends FixedPoint
{
    public FixedPoint16Dot16(int value)
    {
        mValue = value;
    }

    private FixedPoint16Dot16(long value)
    {
        mValue = (int)clamp(value);
    }

    public FixedPoint16Dot16(double value)
    {
        mValue = (int)clamp(fromDoubleCore(value));
    }

    public FixedPoint16Dot16 add(FixedPoint16Dot16 other)
    {
        return new FixedPoint16Dot16(addCore(getValue(), other.getValue()));
    }

    public FixedPoint16Dot16 sub(FixedPoint16Dot16 other)
    {
        return new FixedPoint16Dot16(subCore(getValue(), other.getValue()));
    }

    public FixedPoint16Dot16 mul(FixedPoint16Dot16 other)
    {
        return new FixedPoint16Dot16(mulCore(getValue(), other.getValue()));
    }

    public FixedPoint16Dot16 div(FixedPoint16Dot16 other)
    {
        return new FixedPoint16Dot16(divCore(getValue(), other.getValue()));
    }

    public FixedPoint16Dot16 sqrt()
    {
        return new FixedPoint16Dot16(sqrtCore(getValue()));
    }

    public FixedPoint16Dot16 neg()
    {
        return new FixedPoint16Dot16(negCore(getValue()));
    }

    public FixedPoint16Dot16 abs()
    {
        return new FixedPoint16Dot16(absCore(getValue()));
    }

    public boolean less(FixedPoint16Dot16 other)
    {
        return lessCore(getValue(), other.getValue());
    }

    public FixedPoint16Dot16 clamp(FixedPoint16Dot16 min, FixedPoint16Dot16 max)
    {
        if (getValue() < min.getValue())
            return min;
        if (getValue() > max.getValue())
            return max;
        return this;
    }

    public FixedPoint2Dot14 to2Dot14()
    {
        return new FixedPoint2Dot14((long)getValue() >> 2);
    }

    protected boolean equals(FixedPoint16Dot16 other)
    {
        return getValue() == other.getValue();
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (msObject.referenceEquals(null, obj))
            return false;
        if (msObject.referenceEquals(this, obj))
            return true;
        if (obj.getClass() != getClass())
            return false;
        return equals((FixedPoint16Dot16)obj);
    }

    public /*override*/ int hashCode()
    {
        return getValue();
    }

    public int getValue() { return mValue; };

    private  int mValue;
    public /*override*/ double getDoubleValue() { return toDoubleCore(getValue()); }

    protected /*override*/ int getFractionLength() { return 16; }
    protected /*override*/ long getMinValue() { return Integer.MIN_VALUE; }
    protected /*override*/ long getMaxValue() { return Integer.MAX_VALUE; }
}

