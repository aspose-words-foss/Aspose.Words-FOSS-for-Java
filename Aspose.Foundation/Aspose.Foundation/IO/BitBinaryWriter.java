// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/03/2016 by Ilya Navrotskiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IDisposable;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.BitConverter;


/// <summary>
/// Binary writer that allows to write data to stream by bits.
/// </summary>
/// <remarks>
/// This class uses buffering. Therefore altering of stream position during writing 
/// may lead to incorrect results.
/// </remarks>
final class BitBinaryWriter extends BitBinaryIoBase implements IDisposable
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="stream">Stream.</param>
    /// <param name="useMsbFirstBitOrdering">
    /// True for MSB first bit ordering.
    /// False for LSB first bit ordering.
    /// </param>
    BitBinaryWriter(Stream stream, boolean useMsbFirstBitOrdering) throws Exception
    {
    	super(useMsbFirstBitOrdering);
	
        mWriter = new BinaryWriter(stream);
    }

    /// <summary>
    /// Writes single bit to a stream.
    /// </summary>
    public void writeBit(boolean value) throws Exception
    {
        setCurrentBitToBuffer(value);

        CurrentBufferPosition++;
        if (CurrentBufferPosition < BUFFER_SIZE)
            return;

        writeCurBuffer();
        initBuffer();
    }

    /// <summary>
    /// Writes specified number of bits from the byte value.
    /// </summary>
    public void writeValue(byte value, int numberOfBitsFromRight) throws Exception
    {
        if (numberOfBitsFromRight == 0)
            return;

        if ((numberOfBitsFromRight < 0) || (numberOfBitsFromRight > 8))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "numberOfBitsFromRight");

        int curBit = 1 << (numberOfBitsFromRight - 1);

        for (int i = 0; i < numberOfBitsFromRight; i++)
        {
            boolean curBitVal = (((value & 0xFF) & curBit) != 0);
            writeBit(curBitVal);
            curBit = curBit >> 1;
        }
    }

    /// <summary>
    /// Writes specified number of bits from the int value.
    /// </summary>
    public void writeValue(int value, int numberOfBitsFromRight) throws Exception
    {
        if (numberOfBitsFromRight == 0)
            return;

        if ((numberOfBitsFromRight < 0) || (numberOfBitsFromRight > 31))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "numberOfBitsFromRight");

        byte[] valueBytes = BitConverter.getBytes(value);

        int startByte = (numberOfBitsFromRight - 1) / 8;
        int startBit = numberOfBitsFromRight - (startByte * 8);

        writeValue(valueBytes[startByte--], startBit);

        while (startByte >= 0)
            writeValue(valueBytes[startByte--], 8);
    }

    public void dispose() throws Exception
    {
        mWriter.close();
    }

    /// <summary>
    /// Flushes current buffer to a stream.
    /// </summary>
    void flush() throws Exception
    {
        if (CurrentBufferPosition < 1)
            return;

        writeCurBuffer();
    }

    /// <summary>
    /// Writes current buffer to a stream.
    /// </summary>
    private void writeCurBuffer() throws Exception
    {
        mWriter.writeByte(Buffer);
        mWriter.flush();
    }

    /// <summary>
    /// Sets bit at the current position in buffer.
    /// </summary>
    private void setCurrentBitToBuffer(boolean value)
    {
        Buffer = (byte)BitUtil.setBit((Buffer & 0xFF), (getCurrentMask() & 0xFF), value);
    }

    private /*final*/ BinaryWriter mWriter;
}

