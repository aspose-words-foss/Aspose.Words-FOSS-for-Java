// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 14.02.2017 by Anatoly Sidorenko

package com.aspose.ms.System;

/// <summary>
/// Contains extension methods to emulate .Net unsigned System.UInt.
/// Note that .Net unsigned Int is stored in java signed Int.
/// </summary>
public class msUInt
{
    public static long toUnsignedLong(int uIntValue)
    {
        return uIntValue & UINT_MASK;
    }

    public static String toString(int uIntValue)
    {
        return Long.toString(toUnsignedLong(uIntValue));
    }

    public static String toString(int uIntValue, String format)
    {
        return  msNumber.formatInt64(toUnsignedLong(uIntValue), format);
    }

    public static final int MAX_VALUE = (int)0xFFFFFFFFL;
    public static final int MIN_VALUE = (int)0;

    private static final long UINT_MASK = 0xFFFFFFFFL;
}
