/**
 * Copyright Aspose.com, 2006
 * Helper Java code to port Aspose.Word C# source, Sep 20, 2007
 *
 * @author Konstantin Sidorenko
 */

package com.aspose.ms.java.IO;

import com.aspose.ms.System.IO.SeekOrigin;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.java.debug.JavaDebug;
import com.aspose.ms.java.logging.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Helper class that wraps aspose.net.Stream into java.io.InputStream.
 *
 * .Net Streams are both-way: input and output. But Java IO streams are one-way:
 * either input or output (InputStream or OutputStream superclasses).
 * So we can easily wrap aspose.net.Stream into InputStream OR OutputStream.
 */
public class NetInputStream extends InputStream
{
    public NetInputStream(Stream internalStream)
    {
        mInternalStream = internalStream;
    }

    public int read() throws IOException
    {
        try
        {
            return mInternalStream.readByte();
        }
        catch (Exception e)
        {
            throw new IOException(e.getMessage());
        }
    }


    public int read(byte b[], int off, int len) throws IOException
    {
        try
        {
            if (len == 0)
                return 0;

            int bytesRead = mInternalStream.read(b, off, len);
            return (bytesRead == 0) ? -1 : bytesRead;   //Convert result from .NET to Java style.
        }
        catch (Exception e)
        {
            throw new IOException(e.getMessage());
        }
    }


    public long skip(long n) throws IOException
    {
        try
        {
            long currentPosition = mInternalStream.getPosition();
            long length = mInternalStream.getLength();
            if (currentPosition >= length)
                return 0;

            if (currentPosition + n > length)
                n = length - currentPosition;

            long newPosition = mInternalStream.seek(n, SeekOrigin.CURRENT);
            return newPosition - currentPosition;
        }
        catch (Exception e)
        {
            throw new IOException(e.getMessage());
        }
    }

    public int available() throws IOException
    {
        try
        {
            return (int) (mInternalStream.getLength() - mInternalStream.getPosition());
        }
        catch (Exception e)
        {
            throw new IOException(e.getMessage());
        }
    }


    public void close() throws IOException
    {
        try
        {
            mInternalStream.close();
        }
        catch (Exception e)
        {
            throw new IOException(e.getMessage());
        }
    }

    @Override
    public boolean markSupported()
    {
        return true;
    }

    @Override
    public synchronized void mark(int readlimit)
    {
        try
        {
            // very simple implementation of mark. readLimit is not supported. The current stream's position will be saved
            mMarkPosition = mInternalStream.getPosition();
        }
        catch (Exception e)
        {
            Log.warn(e);
        }
    }

    @Override
    public synchronized void reset() throws IOException
    {
        if (mMarkPosition > -1)
        {
            try
            {
                // set position back to stored one
                mInternalStream.setPosition(mMarkPosition);
            }
            catch (Exception e)
            {
                Log.warn(e);
            }
        }
    }

    //Note: mark/reset methods not implemented (not overrided) so markSupported() returns false,
    // mark() does nothing and reset() throws.

    Stream mInternalStream;
    private long mMarkPosition = -1;
}
