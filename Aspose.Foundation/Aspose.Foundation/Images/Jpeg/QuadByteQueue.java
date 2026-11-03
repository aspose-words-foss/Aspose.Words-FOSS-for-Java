// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/03/2022 by Dmitry Burov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// A fast 4-byte queue that can build an integer value from its content.
/// </summary>
class QuadByteQueue
{
    /// <summary>
    /// Adds a byte to the end of the queue.
    /// </summary>
    void enqueue(byte item)
    {
        mTaleIndex = (mTaleIndex < 3) ? mTaleIndex + 1 : 0;
        mBuffer[mTaleIndex] = item;
    }

    /// <summary>
    /// Gets a big-endian integer value from all four bytes in the queue.
    /// </summary>
    int getIntValue()
    {
        int index = mTaleIndex;
        int result = 0;
        for (int i = 0; i < 32; i += 8)
        {
            result |= (mBuffer[index] & 0xFF) << i;
            index = (index == 0) ? 3 : index - 1;
        }

        return result;
    }

    private int mTaleIndex = -1;
    private /*final*/ byte[] mBuffer = new byte[4];
}

