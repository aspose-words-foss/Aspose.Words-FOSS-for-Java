// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/01/2017 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/*static*/ class ZOrderUtil
{
	/* Simulation of static class by using private constructor */
	private ZOrderUtil()
	{}

    /// <summary>
    /// Converts z-index that Word uses into z-order used in Aspose.Words.
    /// </summary>
    static int zIndexToZOrder(int zIndex, boolean isBehindText)
    {
        return zIndex - (isBehindText ? Z_INDEX_BASE_BEHIND : Z_INDEX_BASE);
    }

    /// <summary>
    /// Converts z-order that Aspose.Words uses into z-index used in Word.
    /// </summary>
    static int zOrderToZIndex(int zOrder, boolean isBehindText)
    {
        return (zOrder * 0x0400) + (isBehindText ? Z_INDEX_BASE_BEHIND : Z_INDEX_BASE);
    }

    /// <summary>
    /// Converts Int64 to Int32 by truncating to Int32.Min and Int32.Max values.
    /// </summary>
    static int truncateLongToInt(long longValue)
    {
        return (longValue < Integer.MIN_VALUE)
            ? Integer.MIN_VALUE
            : (longValue > Integer.MAX_VALUE) ? Integer.MAX_VALUE : (int)longValue;
    }

    /// <summary>
    /// Base z-index value Word uses for shapes behind text.
    /// </summary>
    private static final int Z_INDEX_BASE_BEHIND = /*unchecked*/((int)(int) 0xf1000000L);

    /// <summary>
    /// Base z-index value Word uses for shapes in front of text.
    /// </summary>
    private static final int Z_INDEX_BASE = 0x0f000000;
}

