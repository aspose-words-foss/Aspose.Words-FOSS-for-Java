package com.aspose.ms.System.IO;

import com.aspose.ms.java.IO.BufferedFileAccess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * User: romeok
 * Date: Jul 27, 2005
 *
 * Simple implementation of the .NET compatible Stream to read and write into a disk file
 * that uses Java's RandomAccessFile through a buffer (BufferedFileAccess).
 */
public class FileStream extends Stream 
{
	//JAVA-added
	//When isWritable is true, it opens the file for read and write.
    //When isCreate is true, it deletes any existing file and creates a new in its place.
    //isCreate is only considered when isWritable true.
    //Throws FileNotFoundException when file opened read-only doesn't exist.
    public FileStream(String fileName, boolean isWritable, boolean isCreate) throws IOException
    {
        File file = new File(fileName);

        if (isWritable && isCreate)
        {
            if (file.exists())
                file.delete();
        }
        else if (!isWritable && !isCreate)//open read-only
        {
            if (!(file.exists()))
                throw new FileNotFoundException("Can't find read-only file: " + fileName);
        }

        mIsWritable = isWritable;
        mFile = new BufferedFileAccess(file, mIsWritable);
        mFileName = file.getPath();
    }

	public FileStream(String path, int mode) throws IOException//JAVA was: FileMode enum
	{
		this(path, mode, (mode == FileMode.APPEND ? FileAccess.WRITE : FileAccess.READ_WRITE));
	}

	public FileStream(String path, int mode, int access) throws IOException//JAVA was: FileMode, FileAccess enums
	{
		//check params
		if(path == null)
			throw new NullPointerException("File path can't be null.");
		if("".equals(path))
			throw new IllegalArgumentException("File path can't be \"\".");

		if (mode < FileMode.CREATE_NEW || mode > FileMode.APPEND)
			throw new IndexOutOfBoundsException("Illegal FileMode: " + mode + ".");
		else if (access < FileAccess.READ || access > FileAccess.READ_WRITE)
			throw new IndexOutOfBoundsException("access = " + access);

		//file acces
		//JAVA RandomAccessFile hasn't write-only mode.
		if (access == FileAccess.READ)
		{
			mIsWritable = false;
		}
		else
		{
			mIsWritable = true;
		}

		//file mode
		File file = new File(path);
		if (file.exists())
		{
			if (mode == FileMode.CREATE_NEW)
				throw new IOException("Can't create new file. File '" + path + "' already exist.");

			if (mode == FileMode.CREATE || mode == FileMode.TRUNCATE)
				file.delete();

			mFile = new BufferedFileAccess(file, mIsWritable);

			if (mode == FileMode.APPEND)
				mFile.setPosition(mFile.getLength());
		}
		else
		{
			if (mode == FileMode.OPEN || mode == FileMode.TRUNCATE)
				throw new FileNotFoundException("Can't find file: " + path + ".");

			mFile = new BufferedFileAccess(file, mIsWritable);
		}

		mFileName = file.getPath();
	}

    public boolean canRead() throws IOException
    {
        return true;
    }

    public boolean canSeek()
	{
		return true;
	}

	public boolean canWrite()
	{
		return mIsWritable;
	}

	public long getLength() throws IOException
    {
        return mFile.getLength();
    }

    public long getPosition() throws IOException
    {
        return mFile.getPosition();
    }

    public void setPosition(long position) throws IOException
    {
        mFile.setPosition(position);
    }

    public void flush() throws IOException
    {
        mFile.flush();
    }

    public long seek(long offset, /*SeekOrigin*/ int origin) throws IOException
	{
		switch (origin)
		{
			case SeekOrigin.BEGIN:
					mFile.setPosition(offset);
					break;
			case SeekOrigin.CURRENT:
					mFile.setPosition(mFile.getPosition() + offset);
					break;
			case SeekOrigin.END:
					mFile.setPosition(mFile.getLength() + offset);
					break;
			default:
				throw new IllegalStateException("Unknown SeekOrigin type.");
		}
		return mFile.getPosition();
	}

	public void close() throws IOException
	{
		mFile.close();
	}

	public void setLength(long length) throws IOException
    {
        mFile.setLength(length);
    }

    public int read(byte[] buffer, int offset, int count) throws IOException
    {
        int bytesRead = mFile.read(buffer, offset, count);
        return (bytesRead == -1) ? 0 : bytesRead;   //Convert result to match .NET specification.
    }


	public int readByte() throws IOException
	{
		return mFile.read();
	}

	public void write(byte[] buffer, int offset, int count) throws IOException
    {
	    mFile.write(buffer, offset, count);
    }

	public void writeByte(byte value) throws IOException
	{
		mFile.write(value);
	}

	public String getFileName()
	{
    	return mFileName;
	}

	public String getName()
	{
    	return mFileName;
	}

//	void Write(short[] buffer, int offset, int count) throws IOException
//	{
//		for (int i = offset; i < offset + count; i++)
//			mFile.writeByte(buffer[i]);
//	}

	private boolean mIsWritable;
	private BufferedFileAccess mFile;
	private String mFileName;

	public void copyTo(MemoryStream ms) throws IOException
	{
		int count = (int)mFile.getLength();
		byte[] buffer = new byte[count];
		int bytesRead = mFile.read(buffer, 0, count);
		ms.write(buffer, 0, count);
	}
}
