// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.

package com.aspose.ms.java.IO;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Random access to a disk file through one in-memory buffer, the back end of {@link com.aspose.ms.System.IO.FileStream}.
 *
 * java.io.RandomAccessFile makes a system call for every read and write, which is far too slow for code that works
 * with a file byte by byte the way .NET code works with a FileStream.
 *
 * The buffer mirrors the bytes of the file range [mBufferStart, mBufferStart + mBufferLength). Reads fill it from the
 * file, writes change it in memory and mark it dirty; a dirty buffer goes to the file when the position leaves its range,
 * on flush, on setLength and on close. The position is kept here, the file itself is positioned only when it is touched.
 *
 * Not thread-safe, like the stream that uses it.
 */
public class BufferedFileAccess implements Closeable
{
    /**
     * Opens the file, creating it when it is opened for writing and does not exist.
     * Throws FileNotFoundException when a file opened for reading only does not exist.
     */
    public BufferedFileAccess(File file, boolean isWritable) throws IOException
    {
        this(file, isWritable, DEFAULT_BUFFER_SIZE);
    }

    BufferedFileAccess(File file, boolean isWritable, int bufferSize) throws IOException
    {
        if (bufferSize <= 0)
            throw new IllegalArgumentException("bufferSize");

        mFile = new RandomAccessFile(file, isWritable ? "rw" : "r");
        mIsWritable = isWritable;
        mBuffer = new byte[bufferSize];
    }

    public long getPosition()
    {
        return mPosition;
    }

    /**
     * The position may be set beyond the end of the file; the file grows when something is written there.
     */
    public void setPosition(long position) throws IOException
    {
        if (position < 0)
            throw new IOException("Negative file position: " + position);

        mPosition = position;
    }

    /**
     * Length of the file including the bytes that are written but still sit in the buffer.
     */
    public long getLength() throws IOException
    {
        long length = mFile.length();
        return mIsDirty ? Math.max(length, mBufferStart + mBufferLength) : length;
    }

    /**
     * Truncates or extends the file. A position beyond the new end moves to the new end.
     */
    public void setLength(long length) throws IOException
    {
        ensureWritable();
        flush();
        mFile.setLength(length);
        dropBuffer();

        if (mPosition > length)
            mPosition = length;
    }

    /**
     * Returns the byte at the current position as 0..255, or -1 at the end of the file.
     */
    public int read() throws IOException
    {
        if (!isPositionBuffered() && !fillBuffer())
            return -1;

        return mBuffer[(int) (mPosition++ - mBufferStart)] & 0xFF;
    }

    /**
     * Reads until count bytes are read or the end of the file is reached.
     * Returns the number of bytes read, or -1 when count is positive and there was nothing to read.
     */
    public int read(byte[] bytes, int offset, int count) throws IOException
    {
        checkRange(bytes, offset, count);

        int total = 0;
        while (total < count)
        {
            int left = count - total;
            if (isPositionBuffered())
            {
                int index = (int) (mPosition - mBufferStart);
                int n = Math.min(left, mBufferLength - index);
                System.arraycopy(mBuffer, index, bytes, offset + total, n);
                mPosition += n;
                total += n;
            }
            else if (left >= mBuffer.length)
            {
                // A large block goes straight to the caller. The buffer stays valid: nothing is written here.
                flush();
                mFile.seek(mPosition);
                int n = mFile.read(bytes, offset + total, left);
                if (n <= 0)
                    break;
                mPosition += n;
                total += n;
            }
            else if (!fillBuffer())
            {
                break;
            }
        }

        return (total == 0 && count > 0) ? -1 : total;
    }

    /**
     * Writes the low eight bits of the value at the current position.
     */
    public void write(int value) throws IOException
    {
        ensureWritable();
        int index = prepareBufferForWrite();
        mBuffer[index] = (byte) value;
        bufferWritten(index, 1);
    }

    public void write(byte[] bytes, int offset, int count) throws IOException
    {
        checkRange(bytes, offset, count);
        ensureWritable();

        if (count >= mBuffer.length)
        {
            // A large block goes straight to the file. It may overlap what is buffered, so the buffer is dropped.
            flush();
            mFile.seek(mPosition);
            mFile.write(bytes, offset, count);
            mPosition += count;
            dropBuffer();
            return;
        }

        while (count > 0)
        {
            int index = prepareBufferForWrite();
            int n = Math.min(count, mBuffer.length - index);
            System.arraycopy(bytes, offset, mBuffer, index, n);
            bufferWritten(index, n);
            offset += n;
            count -= n;
        }
    }

    /**
     * Writes the buffered changes to the file.
     */
    public void flush() throws IOException
    {
        if (!mIsDirty)
            return;

        mFile.seek(mBufferStart);
        mFile.write(mBuffer, 0, mBufferLength);
        mIsDirty = false;
    }

    public void close() throws IOException
    {
        if (mIsClosed)
            return;

        mIsClosed = true;
        try
        {
            flush();
        }
        finally
        {
            mFile.close();
        }
    }

    private boolean isPositionBuffered()
    {
        return mPosition >= mBufferStart && mPosition < mBufferStart + mBufferLength;
    }

    /**
     * Loads the buffer with the file content at the current position. Returns false at the end of the file.
     */
    private boolean fillBuffer() throws IOException
    {
        flush();
        mFile.seek(mPosition);

        int length = 0;
        while (length < mBuffer.length)
        {
            int n = mFile.read(mBuffer, length, mBuffer.length - length);
            if (n <= 0)
                break;
            length += n;
        }

        mBufferStart = mPosition;
        mBufferLength = length;
        return length > 0;
    }

    /**
     * Makes sure the byte at the current position can be stored in the buffer and returns its index there.
     * The position fits when it falls into the buffered range or directly continues it and the buffer has room.
     */
    private int prepareBufferForWrite() throws IOException
    {
        long index = mPosition - mBufferStart;
        if (index >= 0 && index <= mBufferLength && index < mBuffer.length)
            return (int) index;

        flush();
        mBufferStart = mPosition;
        mBufferLength = 0;
        return 0;
    }

    private void bufferWritten(int index, int count)
    {
        mBufferLength = Math.max(mBufferLength, index + count);
        mPosition += count;
        mIsDirty = true;
    }

    private void dropBuffer()
    {
        mBufferStart = mPosition;
        mBufferLength = 0;
    }

    private void ensureWritable() throws IOException
    {
        if (!mIsWritable)
            throw new IOException("The file is opened for reading only.");
    }

    private static void checkRange(byte[] bytes, int offset, int count)
    {
        if (bytes == null)
            throw new NullPointerException("bytes");
        if (offset < 0 || count < 0 || count > bytes.length - offset)
            throw new IndexOutOfBoundsException("offset = " + offset + ", count = " + count + ", array length = " + bytes.length);
    }

    private static final int DEFAULT_BUFFER_SIZE = 0x10000;

    private final RandomAccessFile mFile;
    private final boolean mIsWritable;
    private final byte[] mBuffer;

    private long mPosition;
    private long mBufferStart;
    private int mBufferLength;
    private boolean mIsDirty;
    private boolean mIsClosed;
}
