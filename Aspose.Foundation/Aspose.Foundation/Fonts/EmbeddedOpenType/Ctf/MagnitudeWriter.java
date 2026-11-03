// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/03/2016 by Ilya Navrotskiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IDisposable;
import com.aspose.ms.System.IO.MemoryStream;


/// <summary>
/// Allows to write magnitude dependent encoded data to stream.
/// </summary>
/// <remarks>
/// See http://www.w3.org/Submission/2008/SUBM-MTX-20080305/#Magnitude for more info.
/// Stream position should not be altered while writing because BitBinaryWriter is used.
/// </remarks>
final class MagnitudeWriter implements IDisposable
{
    /// <summary>
    /// Ctor.
    /// </summary>
    MagnitudeWriter(MemoryStream stream) throws Exception
    {
        mBitWriter = new BitBinaryWriter(stream, false);
    }

    public void dispose() throws Exception
    {
        if (!mDisposed)
        {
            mBitWriter.dispose();
            mDisposed = true;
        }
    }

    /// <summary>
    /// Writes next value to stream.
    /// </summary>
    /// <returns>The number of the written bits.</returns>
    int writeValue(short value) throws Exception
    {
        if (value == 0)
        {
            mBitWriter.writeBit(false);
            return 1;
        }

        boolean isNegative = (value < 0);

        // The value will be encoded as a number of '1' bits before '0' bit.
        int bitsCount = (isNegative) ? -value : value;

        for (int i = 0; i < bitsCount; i++)
            mBitWriter.writeBit(true);

        // Write '0' bit after value bits are written.
        mBitWriter.writeBit(false);

        // If value is negative, then after '0' bit we should write '1' bit, otherwise '0' bit.
        mBitWriter.writeBit(isNegative);

        return bitsCount + 2;
    }

    /// <summary>
    /// Flushes current buffer of underlying bit binary writer to a stream.
    /// </summary>
    void flush() throws Exception
    {
        mBitWriter.flush();
    }

    private /*final*/ BitBinaryWriter mBitWriter;
    private boolean mDisposed;
}

