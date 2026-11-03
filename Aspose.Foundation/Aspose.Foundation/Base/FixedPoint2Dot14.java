// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/10/2024 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;

/// <summary>
/// Represents 2.14 fixed-point value.
/// </summary>
public class FixedPoint2Dot14 extends FixedPoint
{
    public FixedPoint2Dot14(short value)
    {
        mValue = value;
    }

    FixedPoint2Dot14(long value)
    {
        mValue = (short)clamp(value);
    }

    public FixedPoint2Dot14(double value)
    {
        mValue = (short)clamp(fromDoubleCore(value));
    }

    public boolean less(FixedPoint2Dot14 other)
    {
        return getValue() < other.getValue();
    }

    public boolean greater(FixedPoint2Dot14 other)
    {
        return getValue() > other.getValue();
    }

    public boolean greaterOrEqual(FixedPoint2Dot14 other)
    {
        return getValue() >= other.getValue();
    }

    public boolean greaterOrEqual(FixedPoint16Dot16 other)
    {
        return to16Dot16Value() >= other.getValue();
    }

    public FixedPoint16Dot16 to16Dot16()
    {
        return new FixedPoint16Dot16(to16Dot16Value());
    }

    private int to16Dot16Value()
    {
        return getValue() << 2;
    }

    protected boolean equals(FixedPoint2Dot14 other)
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
        return equals((FixedPoint2Dot14)obj);
    }

    public /*override*/ int hashCode()
    {
        return (/*short.hashCode()*/(int) (getValue()));
    }

    public short getValue() { return mValue; };

    private  short mValue;
    public /*override*/ double getDoubleValue() { return toDoubleCore(getValue()); }
    public float getFloatValue() { return (float)getDoubleValue(); }

    protected /*override*/ int getFractionLength() { return 14; }
    protected /*override*/ long getMaxValue() { return Short.MAX_VALUE; }
    protected /*override*/ long getMinValue() { return Short.MIN_VALUE; }
}

