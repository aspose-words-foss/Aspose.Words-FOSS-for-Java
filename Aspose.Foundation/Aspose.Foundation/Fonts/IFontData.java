// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;


/// <summary>
/// Base class for binary font data.
/// </summary>
public interface IFontData
{
    /// <summary>
    /// Opens the stream with font data. The caller is responsible for disposing the stream.
    /// </summary>
    public Stream openStream() throws Exception;

    /// <summary>
    /// Returns the size of the font data in bytes.
    /// </summary>
    public int getSize();

    /// <summary>
    /// Returns path to the font file if any.
    /// </summary>
    public String getFilePath();

    /// <summary>
    /// Returns the cache key of this source.
    /// </summary>
    /// <remarks>
    /// The name is changed to *Internal for Java.
    /// It was name conflict in StreamFontSource between this method IFontData.GetCacheKey() and public property CacheKey:
    /// both of them became public getCacheKey() on Java.
    /// </remarks>
    public String getCacheKeyInternal();

    /// <summary>
    /// Returns font data as byte array.
    /// </summary>
    public byte[] getFontBytes() throws Exception;

    /// <summary>
    /// True if font data is embedded into source document.
    /// </summary>
    public boolean isEmbedded();
}

