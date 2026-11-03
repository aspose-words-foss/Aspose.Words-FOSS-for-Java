// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IDisposable;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.BinaryReader;


/// <summary>
/// Binary reader which allows to read data from stream by bits.
/// </summary>
/// <remarks>
/// This class uses buffering. Therefore altering of stream position during reading 
/// may lead to incorrect results.
/// </remarks>
final class BitBinaryReader extends BitBinaryIoBase implements IDisposable
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="stream">Stream.</param>
    /// <param name="useMsbFirstBitOrdering">
    /// True for MSB first bit ordering.
    /// False for LSB first bit ordering.
    /// </param>
    public BitBinaryReader(Stream stream, boolean useMsbFirstBitOrdering) throws Exception
    {
    	super(useMsbFirstBitOrdering);
	
        mReader = new BinaryReader(stream);
    }

    /// <summary>
    /// Reads single bit from stream.
    /// </summary>
    public boolean readBit() throws Exception
    {
        if (CurrentBufferPosition == 0)
            Buffer = (byte)mReader.readByte();//JAVA's ReadByte() returns short.

        boolean result = getCurrentBitFromBuffer();

        CurrentBufferPosition++;
        if (CurrentBufferPosition == BUFFER_SIZE)
            CurrentBufferPosition = 0;

        return result;
    }

    /// <summary>
    /// Reads <paramref name="length"/> bits from stream and returns as an unsigned value.
    /// </summary>
    public long readUnsignedValue(int length) throws Exception
    {
        // kvk: I'm not quite sure how to implement reading of values with LSB ordering.
        // While it is not required just throw NotSupportedException.
        if (!UseMsbFirstBitOrdering)
            throw new UnsupportedOperationException("Reading LSB ordered values is not supported.");

        if (length < 0 || length > 63)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "length");

        if (length == 0)
            return 0;

        long value = 0;
        long mask = 1 << (length - 1);
        for (int i = 0; i < length; i++)
        {
            value += readBit() ? mask : 0;
            mask = mask >> 1;
        }

        return value;
    }

    public void dispose() throws Exception
    {
        mReader.close();
    }

    /// <summary>
    /// Returns true if bit in buffer at the current position is set to '1'.
    /// </summary>
    private boolean getCurrentBitFromBuffer()
    {
        return BitUtil.isSetByte(Buffer, getCurrentMask());
    }

    private /*final*/ BinaryReader mReader;
}

