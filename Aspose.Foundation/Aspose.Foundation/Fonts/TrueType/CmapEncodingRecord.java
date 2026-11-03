// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2012 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents encoding record in 'cmap' table.
/// </summary>
class CmapEncodingRecord
{
    /// <summary>
    /// Ctor.
    /// </summary>
    public CmapEncodingRecord(int platformId, int encodingId, int format, long position)
    {
        mPlatformId = platformId;
        mEncodingId = encodingId;
        mFormat = format;
        mPosition = position;
    }

    /// <summary>
    /// Platform ID.
    /// </summary>
    public int getPlatformId() { return mPlatformId; }

    /// <summary>
    /// Platform-specific encoding ID.
    /// </summary>
    public int getEncodingId() { return mEncodingId; }

    /// <summary>
    /// Encoding record format.
    /// </summary>
    public int getFormat() { return mFormat; }

    /// <summary>
    /// Position in the stream of the subtable for this encoding.
    /// </summary>
    public long getPosition() { return mPosition; }

    private /*final*/ int mPlatformId;
    private /*final*/ int mEncodingId;
    private /*final*/ int mFormat;
    private /*final*/ long mPosition;
}

