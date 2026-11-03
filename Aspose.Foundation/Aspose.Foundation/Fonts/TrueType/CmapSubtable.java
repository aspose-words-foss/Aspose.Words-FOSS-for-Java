// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2012 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.MemoryStream;


/// <summary>
/// Represents subtable in 'cmap' table.
/// </summary>
abstract class CmapSubtable
{
    protected CmapSubtable(int platformId, int encodingId, int language, SortedIntegerListGeneric<Integer> charMap)
    {
        mPlatformId = platformId;
        mEncodingId = encodingId;
        mCharMap = charMap;
        mLanguage = language;
    }

    public abstract void write(BigEndianBinaryWriter writer) throws Exception;

    public byte[] writeToBytes() throws Exception
    {
        MemoryStream stream = new MemoryStream();
        try /*JAVA: was using*/
        {
            BigEndianBinaryWriter writer = new BigEndianBinaryWriter(stream);
            write(writer);
            return stream.toArray();
        }
        finally { if (stream != null) stream.close(); }
    }

    public int getEncodingId() { return mEncodingId; }

    public int getPlatformId() { return mPlatformId; }

    public int getLanguage() { return mLanguage; }

    /// <summary>
    /// Sorted list of characters - glyph ids pairs.
    /// Key - int char code. Value - int glyph id.
    /// </summary>
    public SortedIntegerListGeneric<Integer> getCharMap() { return mCharMap; }

    private /*final*/ int mPlatformId;
    private /*final*/ int mEncodingId;
    private /*final*/ SortedIntegerListGeneric<Integer> mCharMap;
    private /*final*/ int mLanguage;
}

