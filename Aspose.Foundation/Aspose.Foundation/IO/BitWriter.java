// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/05/2011 by Alexey Titov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents a class that can sequentially write bits in array of bytes.
/// See unit tests in Aspose.Tests.Base.TestBitWriter for usage examples.
/// </summary>
class BitWriter
{
    public BitWriter(byte[] bytes)
    {
        mBytes = bytes;
        resetValue();
    }

    public void writeOneInCurrentBit()
    {
        mByteValue += (((byte)mBitValue) & 0xFF);
    }

    public void moveToNextBit()
    {
        if (isByteEnded())
        {
            writeCurrentByte();
            setByteIndex(getByteIndex() + 1)/*Property++*/;
        }
        else
            mBitValue >>= 1;
    }

    public void flush()
    {
        if (mBitValue != 128 && mByteIndex < mBytes.length)
            writeCurrentByte();
    }

    private void resetValue()
    {
        mBitValue = 128;
        mByteValue = 0;
    }

    private void writeCurrentByte()
    {
        mBytes[getByteIndex()] = (byte)mByteValue;
    }

    public int getByteIndex() { return mByteIndex; }
    public void setByteIndex(int value)
    {
        flush(); // Write current value
        resetValue();
        mByteIndex = value;
    }

    private boolean isByteEnded() { return mBitValue == 1; }

    private int mBitValue;
    private int mByteIndex;
    private int mByteValue;
    private /*final*/ byte[] mBytes;
}

