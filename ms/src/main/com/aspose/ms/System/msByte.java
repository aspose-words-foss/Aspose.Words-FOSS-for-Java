// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 14.02.2017 by Anatoly Sidorenko

package com.aspose.ms.System;

/// <summary>
/// Contains extension methods to emulate .Net unsigned System.Byte.
/// Note that .Net unsigned byte is stored in java signed byte.
/// </summary>
public class msByte
{
    private static int toUnsignedInt(byte byteValue)
    {
        return ((int)byteValue) & BYTE_MASK;
    }

    public static String toString(byte byteValue)
    {
        return Integer.toString(toUnsignedInt(byteValue));
    }

    public static String toString(byte byteValue, String format)
    {
        return  msNumber.formatInt32(toUnsignedInt(byteValue), format);
    }

    public static final byte MAX_VALUE = (byte)0xFF;
    public static final byte MIN_VALUE = (byte)0;

    private static final int BYTE_MASK = 0xFF;
}
