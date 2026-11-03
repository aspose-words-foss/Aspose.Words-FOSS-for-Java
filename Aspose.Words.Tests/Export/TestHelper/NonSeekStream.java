// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/10/2020 by Alexander Sevidov.

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.SeekOrigin;


/// <summary>
/// A non-seekable stream.
/// </summary>
/// <remarks>
/// It is used for test purposes. Please check Test21179() for the details.
/// </remarks>
class NonSeekStream extends Stream
{
    public NonSeekStream(Stream baseStream)
    {
        mBaseStream = baseStream;
    }

    public /*override*/ boolean canRead()
    {
        return true;
    }

    public /*override*/ boolean canSeek()
    {
        return false;
    }

    public /*override*/ boolean canWrite() throws Exception
    {
        return mBaseStream.canWrite();
    }

    public /*override*/ long getLength() throws Exception
    {
        return mBaseStream.getLength();
    }

    public /*override*/ long getPosition() throws Exception { return mBaseStream.getPosition(); }
    public /*override*/ void setPosition(long value) { throw new UnsupportedOperationException("Seeking is not supported"); }

    /// <summary>
    /// Sets a position. Used for a test purposes.
    /// </summary>
    public void setPositionInternal(long value) throws Exception
    {
        mBaseStream.setPosition(value);
    }

    /// <summary>
    /// Reads bytes from the stream. Used for a test purposes.
    /// </summary>
    public int readBytes(byte[] buffer, int offset, int count) throws Exception 
    {
        return mBaseStream.read(buffer, offset, count);
    }

    public /*override*/ void flush() throws Exception
    {
        mBaseStream.flush();
    }

    public /*override*/ void close() throws java.io.IOException
    {
        mBaseStream.close();
    }

    public /*override*/ int read(byte[] buffer, int offset, int count)
    {
        throw new UnsupportedOperationException("Reading is not supported");
    }
    
    public /*override*/ int readByte()
    {
        throw new UnsupportedOperationException("Reading is not supported");
    }

    public /*override*/ long seek(long offset, /*SeekOrigin*/int origin)
    {
        throw new UnsupportedOperationException("Seeking is not supported");
    }

    public /*override*/ void setLength(long value)
    {
        throw new UnsupportedOperationException("Seeking is not supported");
    }

    public /*override*/ void write(byte[] buffer, int offset, int count) throws Exception
    {
        mBaseStream.write(buffer, offset, count);
    }

    public /*override*/ void writeByte(byte value) throws Exception
    {
        mBaseStream.writeByte(value);
    }

    protected /*override*/ void dispose(boolean disposing) throws Exception
    {
        if (disposing)
            mBaseStream.dispose();
    }

    private Stream mBaseStream;
}

