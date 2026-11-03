package com.aspose.ms.System.IO;

import com.aspose.ms.System.IDisposable;
import com.aspose.ms.java.IO.*;

import java.io.*;
import java.util.ArrayList;

/**
 * User: romeok
 * Date: Jul 27, 2005
 *
 * Copycat of .NET System.IO.Stream. See MSDN for more info.
 * Note that only methods used by Aspose.Words are implemented.
 */
public abstract class Stream implements IDisposable
{
    public abstract boolean canRead() throws Exception;
	public abstract boolean canSeek() throws Exception;
	public abstract boolean canWrite() throws Exception;

    public abstract long getLength() throws Exception;
    public abstract long getPosition() throws Exception;
    public abstract void setPosition(long position) throws Exception;
    public abstract long seek(long offset, /*SeekOrigin*/ int origin) throws Exception;

    public abstract void flush() throws Exception;
    public abstract void close() throws IOException;
    public abstract void setLength(long length) throws Exception;
    public abstract int read(byte[] buffer, int offset, int count) throws Exception;
	public abstract int readByte() throws Exception;
    public abstract void write(byte[] buffer, int offset, int count) throws Exception;
    public abstract void writeByte(byte value) throws Exception;

	public void dispose() throws Exception
	{
		close();
	}

	//>>>>>> JAVA-added for Api change maps

	public static InputStream toJavaInputStream(Stream stream)
	{
		return new NetInputStream(stream);
	}

	public static InputStream[] toJavaInputStream(Stream[] streams)
	{
		InputStream[] result = new NetInputStream[streams.length];
		int i = 0;
		for (Stream stream : streams)
			result[i++] = toJavaInputStream(stream);
		return result;
	}

	public static Stream[] fromJavaInputStream(InputStream[] inputStreams)
	{
		Stream[] result = new Stream[inputStreams.length];
		int i = 0;
		for (InputStream inputStream : inputStreams)
			result[i++] = fromJavaInputStream(inputStream);
		return result;
	}

	public static Stream fromJavaInputStream(InputStream inputStream)
	{
		// Two-way rewindable stream? Just wrap it into ms Stream.
		try
		{
			//WORDSJAVA-2528 It can be null like in DocumentBuilder.insertOleObject(...)
			if (inputStream == null)
				return null;

			if (inputStream instanceof FileInputStream)
				return new JavaFileInputStream((FileInputStream) inputStream);

			// It can be ByteArrayInputStream, BufferedInputStream, RewindableInputStream,
			// FileInputStream, RandomFileInputStream and some others.
			if (JavaInputStream.isRewindable(inputStream))
				return new JavaInputStream(inputStream);
		}
		catch (Exception e1)
		{
			// No luck. Just copy InputStream to MemoryStream below.
		}

		return getMemoryStream(inputStream);
	}

	private static MemoryStream getMemoryStream(InputStream inputStream)
	{
		try
		{
			return JavaOnlyStreamUtil.copyToMemoryStream(inputStream);
		}
		catch (Exception e)
		{
			// Wrap into RuntimeException so we don't need to change throw signature of calling method.
			throw new RuntimeException(e);
		}
	}

	public static OutputStream toJavaOutputStream(Stream stream)
	{
		return new NetOutputStream(stream);
	}
	public static OutputStream[] toJavaOutputStream(Stream[] streams)
	{
		OutputStream[] result = new NetOutputStream[streams.length];
		int i = 0;
		for (Stream stream : streams)
			result[i++] = toJavaOutputStream(stream);
		return result;
	}

	// JAVA: flush() should be called to copy content of backing MemoryStream to java's OutputStream.
	public static MemoryToJavaOutputStream fromJavaOutputStream(OutputStream outputStream)
    {
        return new MemoryToJavaOutputStream(outputStream);
    }

	public static ArrayList<Stream> fromJavaOutputStream(ArrayList<OutputStream> outputStreams)
	{
		ArrayList<Stream> result = new ArrayList<>();
		for (OutputStream output : outputStreams)
			result.add(Stream.fromJavaOutputStream(output));
		return result;
	}

    public static final Stream Null = new NullStream();

    private static class NullStream extends Stream
	{
		@Override
		public boolean canRead() throws Exception
		{
			return true;
		}

		@Override
		public boolean canSeek() throws Exception
		{
			return false;
		}

		@Override
		public boolean canWrite() throws Exception
		{
			return true;
		}

		@Override
		public long getLength() throws Exception
		{
			return 0;
		}

		@Override
		public long getPosition() throws Exception
		{
			return 0;
		}

		@Override
		public void setPosition(long position) throws Exception
		{

		}

		@Override
		public long seek(long offset, int origin) throws Exception
		{
			return 0;
		}

		@Override
		public void flush() throws Exception
		{

		}

		@Override
		public void close() throws IOException
		{

		}

		@Override
		public void setLength(long length) throws Exception
		{

		}

		@Override
		public int read(byte[] buffer, int offset, int count) throws Exception
		{
			return 0;
		}

		@Override
		public int readByte() throws Exception
		{
			return 0;
		}

		@Override
		public void write(byte[] buffer, int offset, int count) throws Exception
		{

		}

		@Override
		public void writeByte(byte value) throws Exception
		{

		}
	}
}
