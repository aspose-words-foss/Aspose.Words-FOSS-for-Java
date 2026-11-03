//sk 24/08/2009

package com.aspose.ms.java.IO;

import java.io.*;

/**
 * Thin wrapper around BufferedOutputStream that wrpped around given FileOutputStream
 * - so we get a Buffered FileOutputStream.
 * Should be used instead of java.io.FileOutputStream.
 */
public class FileOutputStreamBuffered extends BufferedOutputStream
{
	/**
	 * Convinience ctor. Creates a new FileOutputStream using given fileName and
	 * wraps around it a BufferedOutputStream with the default buffer size.
	 */
	public FileOutputStreamBuffered(String fileName) throws FileNotFoundException
	{
		super(new FileOutputStream(fileName), DEFAULT_BUFFER_SIZE);
	}

	/**
	 * Convinience ctor. Creates a new FileOutputStream using given File and
	 * wraps around it a BufferedOutputStream with the default buffer size.
	 */
	public FileOutputStreamBuffered(java.io.File file) throws FileNotFoundException
	{
		super(new FileOutputStream(file), DEFAULT_BUFFER_SIZE);
	}

	/**
	 * Wraps a BufferedOutputStream with the default buffer size around given FileOutputStream.
	 */
	public FileOutputStreamBuffered(FileOutputStream out)
	{
		super(out, DEFAULT_BUFFER_SIZE);
	}

	/**
	 * Wraps a BufferedInputStream with the specified buffer size around given FileInputStream.
	 */
	public FileOutputStreamBuffered(OutputStream out, int size)
	{
		super(out, size);
	}

	private static final int DEFAULT_BUFFER_SIZE = 4096;
}
