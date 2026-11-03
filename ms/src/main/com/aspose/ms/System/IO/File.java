package com.aspose.ms.System.IO;

import com.aspose.ms.System.Text.Encoding;

import java.io.*;
import java.util.ArrayList;

public final class File
{
	private File() {}

	/**
	 * Creates a file in a particular path.  If the file exists, it is replaced.
     * The file is opened with ReadWrite access
	 */
	public static FileStream create(String path) throws IOException
	{
	    return new FileStream(path, FileMode.CREATE, FileAccess.READ_WRITE);
	}

	/**
	 * Creates or opens a file for writing UTF-8 encoded text.
	 */
	public static StreamWriter createText(String path) throws Exception
	{
		return new StreamWriter(path);
	}

	/**
	 * Opens existing file for writing UTF-8 encoded text.
	 */
	public static StreamWriter appendText(String path) throws Exception
	{
		return new StreamWriter(path, true);
	}

	/**
	 * Opens a System.IO.FileStream on the specified path with read/write access.
	 *
	 * @param path The file to open.
	 * @param mode A System.IO.FileMode value that specifies whether a file is created if one does not exist,
	 * and determines whether the contents of existing files are retained or overwritten.
	 */
	public static FileStream open(String path, /*FileMode*/ int mode) throws IOException
	{
	    return open(path, mode, (mode == FileMode.APPEND ? FileAccess.WRITE : FileAccess.READ_WRITE));
	}

	/**
	 * Opens a System.IO.FileStream on the specified path, with the specified mode and access.
	 *
	 * @param path The file to open.
	 * @param mode A System.IO.FileMode value that specifies whether a file is created if one does not exist,
	 * and determines whether the contents of existing files are retained or overwritten.
	 * @param access A System.IO.FileAccess value that specifies the operations that can be performed on the file.
	 */
	public static FileStream open(String path, /*FileMode*/ int mode, /*FileAccess*/ int access) throws IOException
	{
	    return new FileStream(path, mode, access);
	}

	/**
	 * Note: The 'share' parameter is not used since returned FileStream is backed by RandomAccessFile that hasn't
	 * analogs of such a parameter.
	 */
    public static FileStream open(String path, /*FileMode*/ int mode, /*FileAccess*/ int access, /*FileShare*/ int share) throws IOException
    {
        return new FileStream(path, mode, access);
    }

	/**
	 * Opens an existing file for reading.
	 */
	public static FileStream openRead(String path) throws IOException
	{
	    return new FileStream(path, FileMode.OPEN, FileAccess.READ);
	}

    public static FileStream openWrite(String path) throws IOException
    {
        return new FileStream(path, FileMode.OPEN_OR_CREATE, FileAccess.WRITE);
    }

	/**
	 * .Net File.Exists(string)
	 *
	 * This method also returns false if path is a null reference or a zero-length string.
	 * If path describes a directory, this method returns false.
	 */
	public static boolean exists(java.lang.String fileName)
	{
		if (fileName == null || "".equals(fileName))
			return false;

		java.io.File file = new java.io.File(fileName);
		return file.exists() && !file.isDirectory();
	}

	/**
	 * Simplified copycat of .Net File.Delete(string).
	 *
	 * Simply launches java.io.File.delete().
	 */
	public static void delete(java.lang.String path) throws IOException
	{
        java.io.File file = new java.io.File(path);
        // This mimics .NET behavior. It does not throw if the file does not exist,
        // but throws if cannot delete due to any other reason.
        if (file.exists() && !file.delete())
			throw new IOException("Can't delete " + path + ".");
	}

	/**
	 * Simplified copycat of .Net File.Move(string sourceFileName, string destFileName).
	 */
	public static void move(java.lang.String sourceFileName, String destFileName) throws IOException
	{
		java.io.File sourceFile = new java.io.File(sourceFileName);
		java.io.File destFile = new java.io.File(destFileName);
		boolean moved = sourceFile.renameTo(destFile);

		//JAVA-added. really .net doesn't throw every time it can't move the file.
		if (!moved)
			throw new IOException("Can't move file from " + sourceFileName + " to " + destFile + ".");
	}

	public static void copy(String sourceFileName, String destFileName) throws IOException
	{
		copy(sourceFileName,destFileName,true);
	}

    public static void copy(String sourceFileName, String destFileName, boolean isOverride) throws IOException
    {
        java.io.File destFile = new java.io.File(destFileName);
        if (destFile.exists() && !isOverride)
            throw new IOException("Can't copy " + sourceFileName + " to " + destFileName + " because the destination already exists.");

        // RK There is no standard method in Java to copy a file. We have to write our own.
        InputStream in = null;
        OutputStream out = null;
        try
        {
            in = new FileInputStream(sourceFileName);
            out = new FileOutputStream(destFileName);
            byte[] buffer = new byte[16384];
            while (true)
            {
                int n = in.read(buffer);
                if (n == -1)
                    break;
                out.write(buffer, 0, n);
            }
        }
        finally
        {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
        }
    }

	public static java.lang.String readAllText(java.lang.String path) throws Exception
	{
		return readAllText(path, Encoding.getUTF8());//JAVA was: Encoding.getUTF8Unmarked()
	}

	public static java.lang.String readAllText(java.lang.String path, Encoding enc) throws Exception
	{
		StreamReader sr = null;
		try
		{
			sr = new StreamReader(path, enc);
			return sr.readToEnd();
		}
		finally { if (sr != null) sr.close();}
	}

	public static void writeAllText(String path, String contents) throws Exception
	{
		writeAllText(path, contents, Encoding.getUTF8());
	}

	public static void writeAllText(String path, String contents, Encoding encoding) throws Exception
	{
		StreamWriter sw = null;
		try
		{
			sw = new StreamWriter(path, false, encoding);
			sw.write(contents);
		}
		finally { if (sw != null) sw.close(); }
	}

	public static java.lang.String[] readAllLines(java.lang.String path) throws Exception
	{
		return readAllLines(path, Encoding.getUTF8());
	}

	public static java.lang.String[] readAllLines(java.lang.String path, Encoding enc) throws Exception
	{
        String line;
        ArrayList<String> lines = new ArrayList<String>();

        StreamReader sr = null;
		try
		{
			sr = new StreamReader(path, enc);
            while ((line = sr.readLine()) != null)
                lines.add(line);

            String[] array = new String[lines.size()];
            return lines.toArray(array);
		}
		finally { if (sr != null) sr.close();}
	}

    public static StreamReader openText(String path) throws Exception
    {
        return new StreamReader(path);
    }

	public static byte[] readAllBytes(java.lang.String path) throws Exception
	{
		InputStream in = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
		byte[] buffer = new byte[2048];
		try
		{
		    in = new FileInputStream(path);
		    while (true)
		    {
		        int n = in.read(buffer);
		        if (n == -1)
		            break;
		        out.write(buffer, 0, n);
		    }
		}
		finally { if (in != null) in.close(); }

		return out.toByteArray();
	}

	public static void writeAllBytes(java.lang.String path, byte [] data) throws Exception
	{
		Stream stream = null;
		try
		{
			stream = new FileStream(path, true, true);
			stream.write (data, 0, data.length);
		}
		finally { if (stream != null) stream.close(); }
	}

	/**
	 * Deletes a directory recursively.
	 * @param path directory directory to delete
	 * @throws java.io.IOException in case deletion is unsuccessful
	 * */
	public static boolean deleteDirectory(String path) throws IOException
	{
		return deleteDirectory(new java.io.File(path));
	}

	/**
	 * Deletes a directory recursively.
	 * @param path directory directory to delete
	 * @throws java.io.IOException in case deletion is unsuccessful
	 * */
	public static boolean deleteDirectory(java.io.File path) throws IOException
	{
		if (path == null || !path.exists())
			return false;

		java.io.File[] found = path.listFiles();
		if (found != null)
		{
			for (int i = 0; i < found.length; i++)
			{
				if (found[i].isDirectory())
					deleteDirectory(found[i]);

				else
					found[i].delete();
			}

			if (!path.delete())
				throw new IOException("Unable to delete directory " + path.getAbsolutePath()  + ".");

			return true;
		}
		return false;
	}
}
