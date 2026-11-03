//sk 24/08/2009
package com.aspose.ms.java.IO;

import java.io.*;

/**
 * Thin wrapper around BufferedInputStream that wrapped around given FileInputStream
 * - so we get a Buffered FileInputStream.
 * Should be used instead of java.io.FileInputStream.
 */
public class FileInputStreamBuffered extends BufferedInputStream
{
	/**
	 * Convenience ctor. Creates a new FileInputStream using given fileName and
	 * wraps around it a BufferedInputStream with the default buffer size.
	 */
	public FileInputStreamBuffered(String fileName) throws FileNotFoundException
	{
		super(new FileInputStream(fileName), DEFAULT_BUFFER_SIZE);
	}

	/**
	 * Convenience ctor. Creates a new FileInputStream using given File and
	 * wraps around it a BufferedInputStream with the default buffer size.
	 */
	public FileInputStreamBuffered(java.io.File file) throws FileNotFoundException
	{
		super(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
	}

	/**
	 * Wraps a BufferedInputStream with default buffer size around given FileInputStream.
	 */
	public FileInputStreamBuffered(FileInputStream in)
	{
		super(in, DEFAULT_BUFFER_SIZE);
	}

	/**
	 * Wraps a BufferedInputStream with the specified buffer size around given FileInputStream.
	 */
	public FileInputStreamBuffered(FileInputStream in, int size)
	{
		super(in, size);
	}

	private static final int DEFAULT_BUFFER_SIZE = 4096;
}
