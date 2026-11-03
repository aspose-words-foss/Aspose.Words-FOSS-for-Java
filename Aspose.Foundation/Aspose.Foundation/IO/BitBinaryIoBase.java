// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/03/2016 by Ilya Navrotskiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Base class for binary reader and writer.
/// </summary>
abstract class BitBinaryIoBase
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="useMsbFirstBitOrdering">
    /// True for MSB first bit ordering.
    /// False for LSB first bit ordering.
    /// </param>
    protected BitBinaryIoBase(boolean useMsbFirstBitOrdering)
    {
        UseMsbFirstBitOrdering = useMsbFirstBitOrdering;
        initBuffer();
    }

    /// <summary>
    /// Initializes buffer.
    /// </summary>
    protected void initBuffer()
    {
        Buffer = (byte) 0;
        CurrentBufferPosition = 0;
    }

    /// <summary>
    /// Returns bit mask for specified bit position.
    /// </summary>
    protected byte getCurrentMask()
    {
        return UseMsbFirstBitOrdering ? (byte)(0x80 >> CurrentBufferPosition) : (byte)(0x01 << CurrentBufferPosition);
    }

    protected /*final*/ boolean UseMsbFirstBitOrdering;
    protected byte Buffer;
    protected int CurrentBufferPosition;
    protected static final int BUFFER_SIZE = 8;
}

