// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/11/2003 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Bit utility functions.
/// Functions that operate on bits within values of integral types.
/// </summary>
public /*static*/ class BitUtil
{
	/* Simulation of static class by using private constructor */
	private BitUtil()
	{}

    /// <summary>
    /// Changes byte order.
    /// </summary>
    public static long swapInt64(long value)
    {
        return
            ((value & 0x00000000000000FF) << 56) |
            ((value & 0x000000000000FF00) << 40) |
            ((value & 0x0000000000FF0000) << 24) |
            ((value & 0x00000000FF000000L) << 8) |
            ((value & 0x000000FF00000000L) >> 8) |
            ((value & 0x0000FF0000000000L) >> 24) |
            ((value & 0x00FF000000000000L) >> 40) |
            ((value >> 56) & 0x00000000000000FF);
    }

    /// <summary>
    /// Changes byte order.
    /// </summary>
    public static int swapInt32(int value)
    {
        return (int)(swapUInt32((/*uint*/int)value) & 0xFFFFFFFFL);
    }

    /// <summary>
    /// Changes byte order.
    /// </summary>
    public static /*uint*/int swapUInt32(/*uint*/int value)
    {
        return
            (/*uint*/int)(((((value & 0xFFFFFFFFL) & 0x000000FF) << 24) |
            (((value & 0xFFFFFFFFL) & 0x0000FF00) << 8) |
            (((value & 0xFFFFFFFFL) & 0x00FF0000) >> 8) |
            (((value & 0xFFFFFFFFL) & 0xFF000000L) >> 24)));    //Since the value is unsigned, it is safe to shift right.
    }

    /// <summary>
    /// Changes byte order.
    /// </summary>
    public static short swapInt16(short value)
    {
        return (short)(swapUInt16((/*ushort*/short)value) & 0xFFFF);
    }

    /// <summary>
    /// Changes byte order.
    /// </summary>
    public static /*ushort*/short swapUInt16(/*ushort*/short value)
    {
        //Since the value is unsigned, it is safe to shift right.
        return (/*ushort*/short)((((value & 0xFFFF) & 0x00FF) << 8) | (((value & 0xFFFF) & 0xFF00) >> 8));
    }

    /// <summary>
    /// Sets bit(s) specified by the bitmask to 0 or 1 in the specified value.
    /// </summary>
    public static int setBit(int curValue, int bitMask, boolean newValue)
    {
        if (newValue)
            return curValue | bitMask;
        else
            return curValue & ~bitMask;
    }

    /// <summary>
    /// Returns true if the bit is set, false otherwise.
    /// </summary>
    public static boolean isSetInt32(int curValue, int bitMask)
    {
        return ((curValue & bitMask) != 0);
    }

    /// <summary>
    /// Returns true if the bit is set, false otherwise.
    /// </summary>
    public static boolean isSetUInt32(/*uint*/int curValue, /*uint*/int bitMask)
    {
        return (((curValue & 0xFFFFFFFFL) & (bitMask & 0xFFFFFFFFL)) != 0);
    }

    /// <summary>
    /// Returns true if the bit is set, false otherwise.
    /// </summary>
    public static boolean isSetUInt16(/*ushort*/short curValue, /*ushort*/short bitMask)
    {
        return (((curValue & 0xFFFF) & (bitMask & 0xFFFF)) != 0);
    }

    /// <summary>
    /// Returns true if the bit is set, false otherwise.
    /// </summary>
    public static boolean isSetByte(byte curValue, byte bitMask)
    {
        return (((curValue & 0xFF) & (bitMask & 0xFF)) != 0);
    }

    public static int invertBit(int curValue, int bitMask)
    {
        return curValue ^ bitMask;
    }

    /// <summary>
    /// This is from http://everything2.com/index.pl?node=counting%201%20bits
    /// </summary>
    public static int countBitsInt32(int i)
    {
        final int MASK_1 = 0x55555555;
        final int MASK_2 = 0x33333333;
        final int MASK_4 = 0x0f0f0f0f;
        final int MASK_8 = 0x00ff00ff;
        final int MASK_16 = 0x0000ffff;

        i = (i & MASK_1) + ((i >> 1) & MASK_1);
        i = (i & MASK_2) + ((i >> 2) & MASK_2);
        i = (i & MASK_4) + ((i >> 4) & MASK_4);
        i = (i & MASK_8) + ((i >> 8) & MASK_8);
        i = (i & MASK_16) + ((i >> 16) & MASK_16);

        return i;
    }

    /// <summary>
    /// This is from http://everything2.com/index.pl?node=counting%201%20bits
    /// Expanded to 64 bit.
    /// </summary>
    public static int countBitsInt64(long i)
    {
        final long MASK_1 = 0x5555555555555555L;
        final long MASK_2 = 0x3333333333333333L;
        final long MASK_4 = 0x0f0f0f0f0f0f0f0fL;
        final long MASK_8 = 0x00ff00ff00ff00ffL;
        final long MASK_16 = 0x0000ffff0000ffffL;
        final long MASK_32 = 0x00000000ffffffffL;

        i = (i & MASK_1) + ((i >> 1) & MASK_1);
        i = (i & MASK_2) + ((i >> 2) & MASK_2);
        i = (i & MASK_4) + ((i >> 4) & MASK_4);
        i = (i & MASK_8) + ((i >> 8) & MASK_8);
        i = (i & MASK_16) + ((i >> 16) & MASK_16);
        i = (i & MASK_32) + ((i >> 32) & MASK_32);

        return (int)i;
    }

    public static byte reverseBits(byte b)
    {
        return (byte)((((b & 0xFF) * 0x0802L & 0x22110L) | ((b & 0xFF) * 0x8020L & 0x88440L)) * 0x10101L >> 16);
    }

    /// <summary>
    /// Converts byte to bool array.
    /// </summary>
    public static boolean[] byteToBool(byte input)
    {
        boolean[] output = new boolean[8];
        for (int i = 0; i < output.length; i++)
            output[i] = (((input & 0xFF) & (1 << i)) != 0);

        return output;
    }

    /// <summary>
    /// Returns number of bits used in the specified value (position of the last '1' bit in value).
    /// Zero value also uses one bit.
    /// </summary>
    public static int bitsUsed(int value)
    {
        return bitsUsedUint32((/*uint*/int)value);
    }

    /// <summary>
    /// Returns number of bits used in the specified value (position of the last '1' bit in value).
    /// Zero value also uses one bit.
    /// </summary>
    public static int bitsUsedUint32(/*uint*/int value)
    {
        /*uint*/int curBit = (int) 0x80000000L;
        int bitsUsed = 32;
        while ((((value & 0xFFFFFFFFL) & (curBit & 0xFFFFFFFFL)) == 0) && (bitsUsed > 1))
        {
            curBit = (/*uint*/int)(curBit >>> 1);
            bitsUsed--;
        }

        return bitsUsed;
    }

    /// <summary>
    /// Sets high-order bits starting from <paramref name="position"/> (zero-based) to 0.
    /// </summary>
    public static int truncateBits(int value, int position)
    {
        Debug.assertTrue(position >= 0);
        return value & ((1 << position) - 1);
    }
}

