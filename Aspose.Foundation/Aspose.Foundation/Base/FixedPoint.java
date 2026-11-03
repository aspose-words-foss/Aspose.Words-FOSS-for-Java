// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/10/2024 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msMath;


/// <summary>
/// Base class for fixed point numbers.
/// </summary>
/// <remarks>
/// There is a lot of room for optimization. For now these classes are not used in heavy scenarios.
/// The target for now are precise calculations.
/// </remarks>
public abstract class FixedPoint
{
    protected long clamp(long value)
    {
        if (value > getMaxValue())
            return getMaxValue();
        if (value < getMinValue())
            return getMinValue();
        return value;
    }

    protected double toDoubleCore(long value)
    {
        return value / getFractionDivider();
    }

    protected long fromDoubleCore(double value)
    {
        return fromDoubleCore(value, getFractionDivider());
    }

    protected static long fromDoubleCore(double value, double fractionDivider)
    {
        return (long)msMath.round(value * fractionDivider);
    }

    protected long fromIntCore(int value)
    {
        return (long)(value * getFractionDivider());
    }

    protected static long addCore(long a, long b)
    {
        return a + b;
    }

    protected static long subCore(long a, long b)
    {
        return a - b;
    }

    protected long mulCore(long a, long b)
    {
        return (a * b) >> getFractionLength();
    }

    protected long divCore(long a, long b)
    {
        return (a << getFractionLength()) / b;
    }

    protected long sqrtCore(long a)
    {
        // Not exactly correct implementation. Ignore for now.
        return fromDoubleCore(Math.sqrt(toDoubleCore(a)));
    }

    protected static long negCore(long a)
    {
        return -a;
    }

    protected static long absCore(long a)
    {
        return Math.abs(a);
    }

    protected static boolean lessCore(long a, long b)
    {
        return a < b;
    }

    public abstract double getDoubleValue();


    protected abstract int getFractionLength();
    private double getFractionDivider() { return 1 << getFractionLength(); }
    protected abstract long getMaxValue();
    protected abstract long getMinValue();
}

