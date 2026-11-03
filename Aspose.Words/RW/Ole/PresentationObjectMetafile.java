// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/07/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.Debug;
import com.aspose.StreamUtil;
import com.aspose.ms.System.IO.BinaryWriter;


/// <summary>
/// Implements [MS-OLEDS] 2.2.2.1 MetaFilePresentationObject.
/// </summary>
class PresentationObjectMetafile extends PresentationObject
{
    PresentationObjectMetafile(BinaryReader reader) throws Exception
    {
        mWidth = reader.readInt32();
        mHeight = reader.readInt32();

        int dataSize = reader.readInt32();

        Debug.assertTrue(StreamUtil.hasEnoughBytesToRead(reader, dataSize - RESERVED_LENGTH));// already read 'reserved' 

        mMetafileData = reader.readBytes(dataSize - RESERVED_LENGTH);
    }

    /// <summary>
    /// Creates empty MetaFilePresentationObject.
    /// </summary>
    PresentationObjectMetafile(byte[] metafileData)
    {
        mMetafileData = metafileData;
    }

    /*override*/ void write(BinaryWriter writer) throws Exception
    {
        writer.writeInt32(OleUtil.OLE_VERSION);
        writer.writeInt32(0x05);
        OleUtil.writeLengthPrefixedAnsiString(writer, "METAFILEPICT");

        writer.writeInt32(0x00); // Width
        writer.writeInt32(0x00); // Height

        writer.writeInt32(mMetafileData.length + RESERVED_LENGTH);
        // Reserved.
        writer.writeInt32(0x00);
        writer.writeInt32(0x00);

        writer.writeBytes(mMetafileData);
    }

    /// <summary>
    /// Contains the width of a metafile in logical units. 
    /// The MM_ANISOTROPIC mapping mode MUST be used to convert the logical units to physical units.
    /// </summary>
    int getWidth() { return mWidth; }

    /// <summary>
    /// Contains the height of a metafile in logical units. 
    /// The MM_ANISOTROPIC mapping mode MUST be used to convert the logical units to physical units.
    /// </summary>
    int getHeight() { return mHeight; }

    /// <summary>
    /// Windows metafile.
    /// </summary>
    byte[] getMetafileData() { return mMetafileData; }

    private /*final*/ int mWidth;
    private /*final*/ int mHeight;

    private /*final*/ byte[] mMetafileData;
    private static final int RESERVED_LENGTH = 8;
}

