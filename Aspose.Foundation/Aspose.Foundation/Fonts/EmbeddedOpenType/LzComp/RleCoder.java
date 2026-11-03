// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.MemoryStream;


/// <summary>
/// Allows to process data using RLE.
/// </summary>
class RleCoder
{
    /// <summary>
    /// Ctor.
    /// </summary>
    RleCoder()
    {
        mState = RleDecoderState.INITIAL;
    }

    /// <summary>
    /// Encodes data.
    /// </summary>
    static byte[] encode(byte[] data) throws Exception
    {
        int[] counters = new int[256];

        // Initialize counters.
        for (int i = 0; i < data.length; i++)
            counters[(data[i] & 0xFF)]++;

        // Find the least frequently used byte to use as the 'escape' symbol.
        byte escape = (byte)getIndexOfMinElement(counters);

        MemoryStream stream = new MemoryStream();
        try /*JAVA: was using*/
        {
            // First write 'escape' symbol.
            stream.writeByte(escape);

            int i = 0;
            while (i < data.length)
            {
                byte runLength = (byte) 1;
                // Find chains of same symbols (runs).
                while (((i + (runLength & 0xFF)) < data.length) && (data[i + (runLength & 0xFF)] == data[i]) && ((runLength & 0xFF) < 255))
                    runLength++;

                if ((runLength & 0xFF) > 3)
                {
                    // Write: escape, runLength and byte symbol
                    stream.writeByte(escape);
                    stream.writeByte(runLength);
                    stream.writeByte(data[i]);
                }
                else
                {
                    runLength = (byte) 1;
                    // Just write out the byte symbol
                    stream.writeByte(data[i]);

                    // If current symbol is 'escape' symbol,
                    // we should write 0 after 'escape'.
                    if (data[i] == escape)
                        stream.writeByte((byte) 0);
                }

                i += (runLength & 0xFF);
            }
            return stream.toArray();
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Decodes data.
    /// </summary>
    static byte[] decode(byte[] data) throws Exception
    {
        MemoryStream outputStream = new MemoryStream();
        try /*JAVA: was using*/
        {
            RleCoder coder = new RleCoder();
            for (int i = 0; i < data.length; i++)
            {
                byte[] decodedData = coder.decodeByte(data[i]);
                if (decodedData.length > 0)
                    outputStream.write(decodedData, 0, decodedData.length);
            }
            return outputStream.toArray();
        }
        finally { if (outputStream != null) outputStream.close(); }
    }

    /// <summary>
    /// Decodes single byte.
    /// </summary>
    private byte[] decodeByte(byte value)
    {
        switch (mState)
        {
            case RleDecoderState.INITIAL:
                mEscapeValue = value;
                mState = RleDecoderState.NORMAL;
                return ArrayUtil.EMPTY_BYTE_ARRAY;
            case RleDecoderState.NORMAL:
                if (value == mEscapeValue)
                {
                    mState = RleDecoderState.SEEN_ESCAPE;
                    return ArrayUtil.EMPTY_BYTE_ARRAY;
                }
                else
                {
                    return getByteArray(value, 1);
                }
            case RleDecoderState.SEEN_ESCAPE:
                if (value == 0)
                {
                    mState = RleDecoderState.NORMAL;
                    return getByteArray(mEscapeValue, 1);
                }
                else
                {
                    mCount = (value & 0xFF);
                    mState = RleDecoderState.NEED_BYTES;
                    return ArrayUtil.EMPTY_BYTE_ARRAY;
                }
            case RleDecoderState.NEED_BYTES:
                mState = RleDecoderState.NORMAL;
                return getByteArray(value, mCount);
            default:
                return ArrayUtil.EMPTY_BYTE_ARRAY;
        }
    }

    /// <summary>
    /// Returns byte array specified length initialized by <paramref name="value"/> values.
    /// </summary>
    private static byte[] getByteArray(byte value, int length)
    {
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++)
            result[i] = value;
        return result;
    }


    /// <summary>
    /// Returns index of minimal element in array.
    /// </summary>
    private static int getIndexOfMinElement(int[] data)
    {
        int minIndex = 0;
        int minValue = data[0];

        for (int i = 1; i < data.length; i++)
        {
            // Despite array has 'int' type, its elements cannot be negative,
            // as it has a special purpose of symbols frequencies in RLE.
            if (minValue == 0)
                return minIndex;

            if (data[i] >= minValue)
                continue;

            minIndex = i;
            minValue = data[i];
        }

        return minIndex;
    }

    private /*RleDecoderState*/int mState;
    private byte mEscapeValue;
    private int mCount;
}

