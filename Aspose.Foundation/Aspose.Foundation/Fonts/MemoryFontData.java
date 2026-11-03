// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.MemoryStream;


/// <summary>
/// Represents binary font data in memory.
/// </summary>
public class MemoryFontData implements IFontData
{
    /// <summary>
    /// Ctor.
    /// </summary>
    public MemoryFontData(byte[] data)
    {
    	this(data, null, false);
	
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    public MemoryFontData(byte[] data, String cacheKey, boolean isEmbedded)
    {
        Debug.assertTrue(data != null);

        mData = data;
        mCacheKey = cacheKey;
        mIsEmbedded = isEmbedded;
    }

    /// <summary>
    /// Byte array with font data.
    /// </summary>
    public byte[] getData() { return mData; }

    /// <summary>
    /// Opens the stream with font data.
    /// </summary>
    public Stream openStream() throws Exception
    {
        return new MemoryStream(getData());
    }

    /// <summary>
    /// Returns the size of data in bytes.
    /// </summary>
    public int getSize()
    {
        return getData().length;
    }

    public String getFilePath()
    {
        return null;
    }

    public String getCacheKeyInternal()
    {
        return mCacheKey;
    }

    public byte[] getFontBytes()
    {
        return mData;
    }

    public boolean isEmbedded() { return mIsEmbedded; };

    private  boolean mIsEmbedded;

    private /*final*/ byte[] mData;
    private /*final*/ String mCacheKey;
}

