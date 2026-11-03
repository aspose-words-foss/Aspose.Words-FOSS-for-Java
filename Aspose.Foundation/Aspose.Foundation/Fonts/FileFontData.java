// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.FileInfo;


/// <summary>
/// Represents binary font data in file.
/// </summary>
public class FileFontData implements IFontData, IFileFontData
{
    /// <summary>
    /// Ctor.
    /// </summary>
    public FileFontData(String fileName)
    {
    	this(fileName, fileName);
	
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    public FileFontData(String fileName, String cacheKey)
    {
        mFileName = fileName;
        mCacheKey = cacheKey;
    }

    /// <summary>
    /// Font file name.
    /// </summary>
    public String getFileName() { return mFileName; }

    /// <summary>
    /// Opens the stream with font data.
    /// </summary>
    public Stream openStream() throws Exception
    {
        return File.openRead(getFileName());
    }

    /// <summary>
    /// Returns the size of data in bytes.
    /// </summary>
    public int getSize()
    {
        FileInfo fileInfo = new FileInfo(getFileName());
        return (int)fileInfo.getLength();
    }

    public String getFilePath()
    {
        return mFileName;
    }

    public String getCacheKeyInternal()
    {
        return mCacheKey;
    }

    public byte[] getFontBytes() throws Exception
    {
        Stream stream = openStream();
        try /*JAVA: was using*/
    	{
            return StreamUtil.copyStreamToByteArray(stream);
    	}
        finally { if (stream != null) stream.close(); }
    }

    public boolean isEmbedded() { return false; }

    private /*final*/ String mFileName;
    private /*final*/ String mCacheKey;
}

