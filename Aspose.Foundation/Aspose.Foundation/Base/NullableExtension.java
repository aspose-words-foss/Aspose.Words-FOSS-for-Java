// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.


package com.aspose;

import java.math.BigDecimal;

/// <summary>Extension methods for nullable types.</summary>
public /*static*/ class NullableExtension
{
    /* Simulation of static class by using private constructor */
    private NullableExtension()
    {}

    public static NullableByte asNullable(/*this*/ byte value)
    {
        return new NullableByte(value);
    }

    public static NullableInt16 asNullable(/*this*/ short value)
    {
        return new NullableInt16(value);
    }

    public static NullableInt32 asNullable(/*this*/ int value)
    {
        return new NullableInt32(value);
    }

    public static NullableInt64 asNullable(/*this*/ long value)
    {
        return new NullableInt64(value);
    }

    public static NullableSingle asNullable(/*this*/ float value)
    {
        return new NullableSingle(value);
    }

    public static NullableDouble asNullable(/*this*/ double value)
    {
        return new NullableDouble(value);
    }

    public static NullableDecimal asNullable(/*this*/ BigDecimal value)
    {
        return new NullableDecimal(value);
    }

    public static NullableChar asNullable(/*this*/ char value)
    {
        return new NullableChar(value);
    }
}
