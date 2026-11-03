// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/02/2016 by Dmitry Burov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.IO.Stream;
import java.io.EOFException;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.BinaryReader;


/// <summary>
/// Contains utility methods for working with streams.
/// </summary>
public /*static*/ class StreamUtil
{
	/* Simulation of static class by using private constructor */
	private StreamUtil()
	{}

    /// <summary>
    /// Moves position in the stream to the next boundary of the page of the specified size.
    /// Grows the stream if necessary.
    /// </summary>
    public static void seekToNextPage(Stream stream, int pageSize) throws Exception
    {
        int newPos = MathUtil.roundUp(stream.getPosition(), pageSize);

        if (stream.getLength() < newPos)
            stream.setLength(newPos);

        stream.setPosition(newPos);
    }

    /// <summary>
    /// Reads an exact number of bytes from the specified stream into the buffer starting at the given offset.
    /// Throws <see cref="EndOfStreamException"/> if the stream ends before the requested number of bytes is read.
    /// </summary>
    /// <param name="stream">The input stream to read from.</param>
    /// <param name="buffer">The buffer to store the read bytes.</param>
    /// <param name="offset">The zero-based byte offset in <paramref name="buffer"/> at which to begin storing data.</param>
    /// <param name="count">The exact number of bytes to read.</param>
    /// <exception cref="EndOfStreamException">
    /// Thrown if the stream ends before <paramref name="count"/> bytes could be read.
    /// </exception>
    public static void read(Stream stream, byte[] buffer, int offset, int count) throws Exception
    {
        int totalBytesRead = tryRead(stream, buffer, offset, count);

        if (totalBytesRead != count)
            throw new EOFException("End of stream reached with " + (count - totalBytesRead) + " bytes left to read");
    }

    /// <summary>
    /// Attempts to read the specified number of bytes from the stream into the buffer starting at the given offset.
    /// Returns the total number of bytes actually read, which may be less than requested if the end of the stream is reached.
    /// </summary>
    /// <param name="stream">The input stream to read from.</param>
    /// <param name="buffer">The buffer to store the read bytes.</param>
    /// <param name="offset">The zero-based byte offset in <paramref name="buffer"/> at which to begin storing data.</param>
    /// <param name="count">The number of bytes to attempt to read.</param>
    /// <returns>The total number of bytes read into the buffer, which may be less than <paramref name="count"/> if the end of the stream is reached.</returns>
    public static int tryRead(Stream stream, byte[] buffer, int offset, int count) throws Exception
    {
        int totalBytesRead = 0;

        while (totalBytesRead < count)
        {
            int bytesRead = stream.read(buffer, offset + totalBytesRead, count - totalBytesRead);
            totalBytesRead += bytesRead;

            // We have reached the end of the stream.
            if (bytesRead == 0)
                break;
        }

        return totalBytesRead;
    }

    /// <summary>
    /// Reads up to the specified length from the stream and returns the stream position to original.
    /// </summary>
    public static byte[] readAndReturn(Stream stream, int length) throws Exception
    {
        long savePos = stream.getPosition();

        if (stream.getLength() - stream.getPosition() < length)
            length = (int)(stream.getLength() - stream.getPosition()); // read as much as we have

        byte[] data = new byte[length];
        read(stream, data, 0, data.length);
        stream.setPosition(savePos);
        return data;
    }

    /// <summary>
    /// Copies from the current position in src stream till the end.
    /// Copies into the current position in dst stream.
    /// </summary>
    public static void copyStream(Stream srcStream, Stream dstStream) throws Exception
    {
        if (srcStream == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "srcStream");
        if (dstStream == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "dstStream");

        byte[] buf = new byte[65536];
        while (true)
        {
            int bytesRead = srcStream.read(buf, 0, buf.length);
            // Read returns 0 when reached end of stream. Checking for negative too to make it conceptually close to Java.
            if (bytesRead <= 0)
                break;
            else
                dstStream.write(buf, 0, bytesRead);
        }
    }

    /// <summary>
    /// Reads first part of the Stream.
    /// </summary>
    public static byte[] readStream(Stream srcStream, int numberOfBytesToRead) throws Exception
    {
        MemoryStream dstStream = new MemoryStream();
        try /*JAVA: was using*/
        {
            byte[] buf = new byte[numberOfBytesToRead];
            int totalBytes = 0;
            while (totalBytes < numberOfBytesToRead)
            {
                int bytesRead = srcStream.read(buf, 0, buf.length);
                totalBytes += bytesRead;
                if (bytesRead <= 0)
                    break;

                dstStream.write(buf, 0, bytesRead);
            }

            return dstStream.toArray();
        }
        finally { if (dstStream != null) dstStream.close(); }
    }

    /// <summary>
    /// If the source stream is a memory stream, then returns it.
    /// Otherwise copies the source into a new memory stream and returns it.
    /// </summary>
    public static MemoryStream copyStreamIfNotMemory(Stream srcStream) throws Exception
    {
        MemoryStream srcMemoryStream = ms.as(srcStream, MemoryStream.class);
        if (srcMemoryStream != null)
            return srcMemoryStream;

        MemoryStream result = new MemoryStream((int)srcStream.getLength());
        copyStream(srcStream, result);
        return result;
    }

    /// <summary>
    /// Copies a complete stream into a new byte array.
    /// </summary>
    public static byte[] copyStreamToByteArray(Stream srcStream)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	if (srcStream == null)
            	throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "srcStream");

        	// WORDSJAVA-2279 Proved that Java's System.arraycopy is faster on small arrays copying.
        	// But we haven't tested it on .NET so we have to make this solution Java-only at the moment.
        	if (srcStream instanceof MemoryStream && srcStream.getLength() < 2e+6)
            	return ((MemoryStream)srcStream).toArray();

        	// RK Casting length to integer to make code autoportable to Java.
        	byte[] result = new byte[(int)srcStream.getLength()];
        	MemoryStream dstStream = new MemoryStream(result);
        	try /*JAVA: was using*/
        	{
            	srcStream.setPosition(0);
            	copyStream(srcStream, dstStream);
            	return result;
        	}
        	finally { if (dstStream != null) dstStream.close(); }
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    /// <summary>
    /// Opens a file, reads the contents of the file into a byte array, and then closes the file.
    /// </summary>
    /// <param name="fileName">The file to open for reading</param>
    /// <returns>A byte array containing the contents of the file.</returns>
    public static byte[] copyFileToByteArray(String fileName) throws Exception
    {
        Stream stream = File.openRead(fileName);
        try /*JAVA: was using*/
    	{
            return copyStreamToByteArray(stream);
    	}
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Checks if reader has enough bytes to read.
    /// </summary>
    public static boolean hasEnoughBytesToRead(BinaryReader reader, int bytesCount) throws Exception
    {
        return bytesCount <= (reader.getBaseStream().getLength() - reader.getBaseStream().getPosition());
    }

    /// <summary>
    /// Writes string in ANSI encoding to stream at the specified offset.
    /// </summary>
    /// <param name="value">The string value to write in ANSI encoding to stream.</param>
    /// <param name="stream">The stream to write to.</param>
    public static void writeAnsiStringToStream(String value, Stream stream) throws Exception
    {
        for (int i = 0; i < value.length(); i++)
        {
            char c = value.charAt(i);
            stream.writeByte((byte)c);
        }
    }

    /// <summary>
    /// Returns true, if a specified stream ends with a specified bytes.
    /// </summary>
    public static boolean isEndsWith(Stream stream, byte[] bytes) throws Exception
	{
		return isEndsWith(stream, bytes, 1024);
	}

    /// <summary>
    /// Returns true, if a specified stream ends with a specified bytes.
    /// </summary>
    public static boolean isEndsWith(Stream stream, byte[] bytes, int defaultBufferLength) throws Exception
    {
        if ((bytes.length == 0) || (stream.getLength() < bytes.length))
            return false;

        // Allocate buffer for reading the stream.
        byte[] buffer = new byte[Math.min(defaultBufferLength, bytes.length)];

        // Save current stream position.
        long position = stream.getPosition();

        // Move to position from which we should start to read the stream.
        stream.setPosition(stream.getLength() - bytes.length);

        // The current position within bytes array.
        int bytesPos = 0;

        // Read first portion of stream bytes.
        int count = stream.read(buffer, 0, buffer.length);
        while (count > 0)
        {
            int i = 0;
            while (i < count)
            {
                if (buffer[i] != bytes[bytesPos])
                {
                    // Restore original stream position before exit.
                    stream.setPosition(position);
                    return false;
                }

                i++;
                bytesPos++;
            }

            // Read next portion of stream bytes.
            count = stream.read(buffer, 0, buffer.length);
        }

        // Restore original stream position before exit.
        stream.setPosition(position);
        return true;
    }
}

