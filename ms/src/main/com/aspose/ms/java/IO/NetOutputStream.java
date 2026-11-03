/**
 * Copyright Aspose.com, 2006
 * Helper Java code to port Aspose.Word C# source, Sep 20, 2007
 *
 * @author Konstantin Sidorenko
 */

package com.aspose.ms.java.IO;

import com.aspose.ms.System.IO.Stream;

import java.io.OutputStream;
import java.io.IOException;


/**
 * Helper class that wraps aspose.net.Stream into java.io.OutputStream.
 *
 * .Net Streams are both-way: input and output. But Java IO streams are one-way only:
 * either input or output (InputStream or OutputStream superclasses respectively).
 * So we can easily wrap aspose.net.Stream into InputStream OR OutputStream.
 */
public class NetOutputStream extends OutputStream
{
	public NetOutputStream(Stream internalStream)
	{
		mInternalStream = internalStream;
	}

	public void write(int b) throws IOException
	{
        try
        {
            mInternalStream.writeByte((byte) b);
        }
        catch (Exception e)
        {
            throw new IOException(e.getMessage());
        }
	}


	public void write(byte b[], int off, int len) throws IOException
	{
        try
        {
            mInternalStream.write(b, off, len);
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

	public void flush() throws IOException
	{
        try
        {
            mInternalStream.flush();
        }
        catch (Exception e)
        {
            throw new IOException(e.getMessage());
        }
	}

	Stream mInternalStream;
}
