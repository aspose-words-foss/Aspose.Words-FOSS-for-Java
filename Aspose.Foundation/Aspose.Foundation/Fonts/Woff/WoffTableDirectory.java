// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/11/2022 by Nikolay Sezganov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;


/// <summary>
/// Directory of font tables, indicating the original size, compressed size and location of each table within the WOFF file.
/// </summary>
class WoffTableDirectory
{
    WoffTableDirectory(
        String name,
        /*uint*/int offset,
        /*uint*/int compLength,
        /*uint*/int origLength,
        /*uint*/int origChecksum)
    {
        mName = name;
        mOffset = offset;
        mCompLength = compLength;
        mOrigLength = origLength;
        mOrigChecksum = origChecksum;
    }

    static WoffTableDirectory read(BigEndianBinaryReader reader) throws Exception
    {
        return new WoffTableDirectory(
            tagToString(reader.readUInt32()), // tag - 4-byte sfnt table identifier
            reader.readUInt32(), // offset - Offset to the data, from beginning of WOFF file.
            reader.readUInt32(), // compLength - Length of the compressed data, excluding padding.
            reader.readUInt32(), // origLength - Length of the uncompressed table, excluding padding.
            reader.readUInt32()); // origChecksum - Checksum of the uncompressed table.
    }

    String getName() { return mName; };

    private String mName;

    /// <summary>
    /// Offset to the data, from beginning of WOFF file.
    /// </summary>
    /*uint*/int getOffset() { return mOffset; };

    private /*uint*/int mOffset;

    /// <summary>
    /// Length of the compressed data, excluding padding.
    /// </summary>
    /*uint*/int getCompLength() { return mCompLength; };

    private /*uint*/int mCompLength;

    /// <summary>
    /// Length of the uncompressed table, excluding padding.
    /// </summary>
    /*uint*/int getOrigLength() { return mOrigLength; };

    private /*uint*/int mOrigLength;

    /// <summary>
    /// Gets a value indicating whether the data are compressed.
    /// </summary>
    boolean isCompressed() { return getCompLength() != getOrigLength(); }

    /// <summary>
    /// Checksum of the uncompressed table.
    /// </summary>
    /*uint*/int getOrigChecksum() { return mOrigChecksum; };

    private /*uint*/int mOrigChecksum;

    private static String tagToString(/*uint*/int tag)
    {
        // Convert bytes to chars in big-endian order (high-order byte becomes the first char).
        char[] chars = new char[ms.sizeofUInt32()];
        for (int i = chars.length - 1; i >= 0; i--)
        {
            chars[i] = (char)((tag & 0xFFFFFFFFL) & 0xFF);
            tag = (/*uint*/int)(tag >>> 8);
        }
        return new String(chars);
    }
}

