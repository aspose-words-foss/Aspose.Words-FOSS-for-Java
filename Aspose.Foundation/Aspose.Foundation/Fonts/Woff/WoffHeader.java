// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/11/2022 by Nikolay Sezganov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// File header with basic font type and version, along with offsets to metadata and private data blocks.
/// https://www.w3.org/TR/WOFF/#OverallStructure
/// </summary>
class WoffHeader
{
    WoffHeader(int numTables)
    {
        mNumTables = numTables;
    }

    static WoffHeader read(BigEndianBinaryReader reader) throws Exception
    {
        /*uint*/int signature = reader.readUInt32(); // signature - 0x774F4646 'wOFF'
        if (signature != 0x774F4646)
        {
            return null;
        }

        reader.readUInt32(); // flavor - The "sfnt version" of the input font.
        reader.readUInt32(); // length - Total size of the WOFF file.
        int numTables = (reader.readUInt16() & 0xFFFF); // numTables - Number of entries in directory of font tables.
        reader.readUInt16(); // reserved - Reserved; set to zero.
        reader.readUInt32(); // totalSfntSize - Total size needed for the uncompressed font data, including the sfnt header,
                             // directory, and font tables(including padding).
        reader.readUInt16(); // majorVersion - Major version of the WOFF file.
        reader.readUInt16(); // minorVersion - Minor version of the WOFF file.
        reader.readUInt32(); // metaOffset - Offset to metadata block, from beginning of WOFF file.
        reader.readUInt32(); // metaLength - Length of compressed metadata block.
        reader.readUInt32(); // metaOrigLength - Uncompressed size of metadata block.
        reader.readUInt32(); // privOffset - Offset to private data block, from beginning of WOFF file.
        reader.readUInt32(); // privLength - Length of private data block.

        return new WoffHeader(numTables);
    }

    /// <summary>
    /// Number of entries in directory of font tables.
    /// </summary>
    int getNumTables() { return mNumTables; };

    private int mNumTables;
}

