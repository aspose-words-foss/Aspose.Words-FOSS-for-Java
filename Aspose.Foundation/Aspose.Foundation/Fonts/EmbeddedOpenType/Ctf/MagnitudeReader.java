// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IDisposable;
import com.aspose.ms.System.IO.MemoryStream;


/// <summary>
/// Allows to read magnitude dependent encoded data from stream.
/// </summary>
/// <remarks>
/// See http://www.w3.org/Submission/2008/SUBM-MTX-20080305/#Magnitude for more info.
/// Stream position should not be altered while reading because BitBinaryReader is used.
/// </remarks>
final class MagnitudeReader implements IDisposable
{
    /// <summary>
    /// Ctor.
    /// </summary>
    MagnitudeReader(MemoryStream stream) throws Exception
    {
        mBitReader = new BitBinaryReader(stream, false);
    }

    public void dispose() throws Exception
    {
        if (!mDisposed)
        {
            mBitReader.dispose();
            mDisposed = true;
        }
    }

    /// <summary>
    /// Reads next value from stream.
    /// </summary>
    short readValue() throws Exception
    {
        short value = 0;

        // Number of '1' bits before '0' bit equals absolute value.
        while (mBitReader.readBit())
        {
            value++;
        }

        // If absolute value > 0, next bit means value sign.
        if ((value > 0) && mBitReader.readBit())
            value *= -1;

        return value;
    }

    private /*final*/ BitBinaryReader mBitReader;
    private boolean mDisposed;
}

