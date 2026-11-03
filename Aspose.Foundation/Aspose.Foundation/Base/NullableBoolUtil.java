// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/06/2023 by Edward Voronov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



public /*static*/ class NullableBoolUtil
{
	/* Simulation of static class by using private constructor */
	private NullableBoolUtil()
	{}

    public static /*NullableBool*/byte asNullable(boolean value)
    {
        return value
            ? NullableBool.TRUE
            : NullableBool.FALSE;
    }

    public static boolean hasValue(/*NullableBool*/byte value)
    {
        return value != NullableBool.NOT_DEFINED;
    }

    public static boolean getValue(/*NullableBool*/byte value)
    {
        if (hasValue(value))
            return value == NullableBool.TRUE;

        throw new IllegalStateException();
    }

    public static boolean getValueOrDefault(/*NullableBool*/byte value, boolean defaultValue)
    {
        return hasValue(value)
            ? getValue(value)
            : defaultValue;
    }
}

