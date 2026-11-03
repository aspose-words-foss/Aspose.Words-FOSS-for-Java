package com.aspose.ms.java.IO;

import com.aspose.ms.System.IO.SeekOrigin;
import com.aspose.ms.System.IO.Stream;

import java.io.IOException;
import java.io.InputStream;

/**
 * Ms Stream wrapper around java.io.InputStream.
 * <p>
 * Only rewindable two-way streams can be wrapped. java.io.InputStream.markSupported() -> mark() -> reset() -> skip()
 * are used to rewind backing java.io.InputStreams.
 * <p>
 * This implementation doesn't support write since java.io.InputStream doesn't support it.
 * <p>
 * Rewindable java.io.InputStreams (not all): ByteArrayInputStream, BufferedInputStream, RewindableInputStream,
 * FileInputStream, RandomFileInputStream
 */
public class JavaInputStream extends Stream
{
    public JavaInputStream(InputStream inputStream) throws IOException
    {
        if (inputStream == null)
            throw new IOException("InputStream is null");

        if (!isRewindable(inputStream))
            throw new IOException("Only rewindable two-way streams can be wrapped!");

        mInputStream = inputStream;

        // WORDSJAVA-2248: `available()` method coming from InputStream doesn't guarantee to return exact length of a stream.
        // So there is no way to determine stream length without reading till the end of a stream.
        mInputStream.mark(Integer.MAX_VALUE);
    }

    /**
     * JavaInputStream accepts only rewindable two-way java.io.InputStreams. The method just checks markSupported()
     * because methods markSupported() -> mark() -> reset() -> skip() are used to rewind backing java.io.InputStreams
     */
    public static boolean isRewindable(InputStream inputStream)
    {
        return inputStream.markSupported();
    }

    @Override
    public boolean canRead()
    {
        return !mClosed;
    }

    @Override
    public boolean canSeek()
    {
        return !mClosed;
    }

    @Override
    public boolean canWrite()
    {
        return false;
    }

    @Override
    public long getLength() throws IOException
    {
        if (mLength >= 0)
            return mLength;

        // WORDSJAVA-2248: Start scanning for stream end from the greatest position ever reached.
        long originalPosition = mPosition.getCurrent();
        setPosition(mPosition.getMax());

        // WORDSJAVA-2248: We buffer stream's content as much as possible because we must reach end of a stream.
        long bytesBuffered = ensureStreamIsBuffered(Long.MAX_VALUE);
        long streamLength = bytesBuffered + mPosition.getCurrent();
        mLength = streamLength;

        // WORDSJAVA-2248: We got all stream's content buffered so we can safely restore original position.
        setPosition(originalPosition);

        return streamLength;
    }

    @Override
    public void setLength(long length)
    {
        mLength = length;
    }

    @Override
    public long getPosition() throws IOException
    {
        return mPosition.getCurrent();
    }

    @Override
    public void setPosition(long position) throws IOException
    {
        // WORDSJAVA-2241: read from stream up to desired position in order
        // extend internal buffer of 'BufferedInputStream' and set new
        // position ahead currently buffered content.
        ensureStreamIsBuffered(position);
        mInputStream.reset();
        long newPosition = mInputStream.skip(position);
        mPosition.set(newPosition);
    }

    @Override
    public long seek(long offset, int origin) throws IOException
    {
        switch (origin)
        {
            case SeekOrigin.BEGIN:
                setPosition(offset);
                break;
            case SeekOrigin.CURRENT:
                setPosition(mPosition.increment(offset));
                break;
            case SeekOrigin.END:
                setPosition(getLength() + offset);
                break;
            default:
                throw new IllegalStateException("Unknown SeekOrigin type.");
        }

        return mPosition.getCurrent();
    }

    @Override
    public void flush() throws IOException
    {
        // Don't write so no need to flush.
    }

    @Override
    public void close() throws IOException
    {
        mInputStream.close();
        mClosed = true;
    }

    @Override
    public int read(byte[] buffer, int offset, int count) throws IOException
    {
        int res = mInputStream.read(buffer, offset, count);
        if (res < 0)
            return 0;

        mPosition.increment(res);
        return res;
    }

    @Override
    public int readByte() throws IOException
    {
        int result = mInputStream.read();
        if (result >= 0)
            mPosition.increment(1);

        return result;
    }

    @Override
    public void write(byte[] buffer, int offset, int count) throws IOException
    {
        throw new IOException("Can't write to the backing InputStream.");
    }

    @Override
    public void writeByte(byte value) throws IOException
    {
        throw new IOException("Can't write to the backing InputStream.");
    }

    private long readUpToStreamEnd(long bytesAhead) throws IOException
    {
        // WORDSJAVA-2248: We could read one big chunk at once. But such approach might lead to OOM exception since
        // we got two large buffers allocated: one local buffer in this method and one internal buffer inside
        // 'BufferedInputStream' wrapper.
        //
        // Also, we read one more chunk since actual stream length may not be divisible by buffer size.
        long numberOfBuffers = bytesAhead / BUFFER_LENGTH + 1;
        final byte[] buffer = new byte[BUFFER_LENGTH];

        long bytesReadTotal = 0;
        for (long i = 0; i < numberOfBuffers; i++)
        {
            int bytesReadAtOnce = mInputStream.read(buffer);
            if (bytesReadAtOnce < 0)
                return bytesReadTotal;
            bytesReadTotal += bytesReadAtOnce;
        }
        return bytesReadTotal;
    }

    private long ensureStreamIsBuffered(long targetPosition) throws IOException
    {
        long bytesAhead = targetPosition - mPosition.getCurrent();

        if (bytesAhead <= 0)
            return 0;

        return readUpToStreamEnd(bytesAhead);
    }

    private static class StreamPosition
    {
        long increment(long value)
        {
            currentPosition += value;
            updateMaxPosition();
            return currentPosition;
        }

        void updateMaxPosition()
        {
            if (currentPosition > maxPosition)
                maxPosition = currentPosition;
        }

        void set(long value)
        {
            currentPosition = value;
            updateMaxPosition();
        }

        long getCurrent()
        {
            return currentPosition;
        }

        long getMax()
        {
            return maxPosition;
        }

        long currentPosition = 0;
        long maxPosition = 0;
    }

    private InputStream mInputStream;
    private StreamPosition mPosition = new StreamPosition();
    private long mLength = -1;
    private boolean mClosed = false;
    private static final int BUFFER_LENGTH = 65_536;
}


