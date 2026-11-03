// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 14.02.2017 by Anatoly Sidorenko

package com.aspose.ms.System;

/// <summary>
/// Contains extension methods to emulate .Net unsigned System.UShort.
/// Note that .Net unsigned Short is stored in java signed Short.
/// </summary>
public class msUShort
{
    private static int toUnsignedInt(short uShortValue)
    {
        return ((int)uShortValue) & SHORT_MASK;
    }

    public static String toString(short uShortValue)
    {
        return Integer.toString(toUnsignedInt(uShortValue));
    }

    public static String toString(short uShortValue, String format)
    {
        return  msNumber.formatInt32(toUnsignedInt(uShortValue), format);
    }

    // Do not use this in arithmetic as is: without mask (short)0xFFFF is -1.
    public static final short MAX_VALUE = (short)0xFFFF;
    public static final short MIN_VALUE = (short)0;

    private static final int SHORT_MASK = 0xFFFF;
}
