// ported 26/08/2008 by Konstantin Sidorenko
package com.aspose.ms.System.IO;

// contains constants for specifying how the OS should open a file.
// These will control whether you overwrite a file, open an existing
// file, or some combination thereof.
//
// To append to a file, use Append (which maps to OpenOrCreate then we seek
// to the end of the file).  To truncate a file or create it if it doesn't
// exist, use Create.
//
public final class FileMode
{
	/**
	 * Creates a new file. An exception is raised if the file already exists.
	 */
	public static final int CREATE_NEW = 1;

	/**
	 * Creates a new file. If the file already exists, it is overwritten.
	 */
	public static final int CREATE = 2;

	/**
	 * Opens an existing file. An exception is raised if the file does not exist.
	 */
	public static final int OPEN = 3;

	/**
	 * Opens the file if it exists. Otherwise, creates a new file.
	 */
	public static final int OPEN_OR_CREATE = 4;

	/**
	 * Opens an existing file. Once opened, the file is truncated so that its
	 * size is zero bytes. The calling process must open the file with at least
	 * WRITE access. An exception is raised if the file does not exist.
	 */
	public static final int TRUNCATE = 5;

	/**
	 * Opens the file if it exists and seeks to the end.  Otherwise,
	 * creates a new file.
	 */
	public static final int APPEND = 6;
}
