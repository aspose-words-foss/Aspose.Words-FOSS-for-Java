// 25/06/2010 by Roman Korchagin
package com.aspose.ms.java.IO;

import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.Stream;

import java.io.*;

public class JavaOnlyStreamUtil
{
    /// <summary>
    /// Copies from the current position in a Java srcStream stream into a new MemoryStream object
    /// and sets the position in the returned stream to the beginning of the stream.
    /// </summary>
    public static MemoryStream copyToMemoryStream(InputStream srcStream) throws Exception
    {
        MemoryStream dstStream = new MemoryStream();
        copyStream(srcStream, dstStream);
        dstStream.setPosition(0);
        return dstStream;
    }

    /// <summary>
    /// Copies 'maxLength' bytes from the current position of Java srcStream
    /// to the current position of .NET dstStream stream.
    /// </summary>
    public static void copyStream(InputStream srcStream, Stream dstStream, int maxLength) throws Exception
    {
        if (maxLength == 0)
            return;

        // Unknown size
        if (maxLength < 0)
        {
            copyStream(srcStream, dstStream);
            return;
        }

        // Copy to a MemoryStream with only one memory allocation.
        if (dstStream instanceof MemoryStream)
        {
            int pos = (int)dstStream.getPosition();
            int length = (int)dstStream.getLength();
            int newLength = Math.min(length, pos) + maxLength;

            if (newLength > length)
                dstStream.setLength(newLength);

            byte[] buffer = ((MemoryStream)dstStream).getBuffer();

            do
            {
                int bytesRead = srcStream.read(buffer, pos, maxLength);
                //Java: InputStream returns -1 when end of stream is reached.
                if (bytesRead < 0)
                    break;

                maxLength -= bytesRead;
                pos += bytesRead;
            }
            while (maxLength > 0);

            dstStream.setPosition(pos);
        }
        else
        {
            byte[] buf = new byte[4096];
            while (true)
            {
                int bytesRead = srcStream.read(buf, 0, Math.min(buf.length, maxLength));
                //Java: InputStream returns -1 when end of stream is reached.
                if (bytesRead <= 0)
                {
                    break;
                }
                else
                {
                    maxLength -= bytesRead;
                    dstStream.write(buf, 0, bytesRead);
                }

                if (maxLength <= 0)
                    break;
            }
        }
        dstStream.flush();
    }

    /// <summary>
    /// Copies from the current position in a Java srcStream stream till the end.
    /// Copies into the current position in a .NET dstStream stream.
    /// </summary>
    public static void copyStream(InputStream srcStream, Stream dstStream) throws Exception
    {
        byte[] buf = new byte[4096];
        while (true)
        {
            int bytesRead = srcStream.read(buf, 0, buf.length);
            //Java: InputStream returns -1 when end of stream is reached.
            if (bytesRead <= 0)
                break;
            else
                dstStream.write(buf, 0, bytesRead);
        }
        dstStream.flush();
    }

    /// <summary>
    /// Copies from the current position in a .NET srcStream stream till the end.
    /// Copies into the current position in a Java dstStream stream.
    /// </summary>
    public static void copyStream(Stream srcStream, OutputStream dstStream) throws Exception
    {
        byte[] buf = new byte[8192];
        while (true)
        {
            int bytesRead = srcStream.read(buf, 0, buf.length);
            // Read returns 0 when reached end of stream, but check for negative too to make it conceptually closer to Java streams.
            if (bytesRead <= 0)
                break;
            else
                dstStream.write(buf, 0, bytesRead);
        }
        dstStream.flush();
    }

    /// <summary>
    /// Copies from the current position in a .NET srcStream stream till the end.
    /// Copies into the current position in a Java dstStream stream.
    /// </summary>
    public static void copyStream(Stream srcStream, OutputStream dstStream, int buffLength) throws Exception
    {
        byte[] buf = new byte[buffLength];
        while (true)
        {
            int bytesRead = srcStream.read(buf, 0, buffLength);
            // Read returns 0 when reached end of stream, but check for negative too to make it conceptually closer to Java streams.
            if (bytesRead <= 0)
                break;
            else
                dstStream.write(buf, 0, buffLength);
        }
        dstStream.flush();
    }

    public static void copyStreamFromZero(Stream srcStream, OutputStream dstStream) throws Exception
    {
        srcStream.setPosition(0);
        copyStream(srcStream, dstStream);
    }

    /// <summary>
    /// Copies from the current position in a .Java srcStream stream till the end.
    /// Copies into the current position in a Java dstStream stream.
    /// </summary>
    public static void copyStream(InputStream srcStream, OutputStream dstStream) throws Exception
    {
        byte[] buf = new byte[8192];
        while (true)
        {
            int bytesRead = srcStream.read(buf, 0, buf.length);
            // Read returns 0 when reached end of stream, but check for negative too to make it conceptually closer to Java streams.
            if (bytesRead <= 0)
                break;
            else
                dstStream.write(buf, 0, bytesRead);
        }
        dstStream.flush();
    }

    /// <summary>
    /// Copies from the current position in a .NET srcStream stream till the end.
    /// Copies into byte array.
    /// </summary>
    public static byte[] copyStreamToByteArray(Stream srcStream) throws Exception
    {
        if (srcStream == null)
            throw new IllegalArgumentException("srcStream");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] buf = new byte[4096];
        while (true)
        {
            int bytesRead = srcStream.read(buf, 0, buf.length);
            if (bytesRead <= 0)
                break;
            else
                baos.write(buf, 0, bytesRead);
        }

        return baos.toByteArray();
    }

    public static void closeQuietly(Closeable stream)
    {
        try
        {
            if (stream != null)
                stream.close();
        }
        catch (IOException e)
        {
            // ignore
        }
    }
}
