// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/01/2006 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.BinaryWriter;


/// <summary>
/// BITMAPFILEHEADER contains information about the type, size, and layout of a file that contains a DIB. 
/// 
/// A BITMAPINFO immediately follows the BITMAPFILEHEADER structure in the DIB file.
/// </summary>
public class BitmapFileHeader
{
    BitmapFileHeader()
    {
    }

    void read(BinaryReader reader) throws Exception
    {
        FileType = reader.readUInt16();
        Size = reader.readUInt32();
        Reserved1 = reader.readUInt16();
        Reserved2 = reader.readUInt16();
        OffBits = reader.readUInt32();
    }

    void write(BinaryWriter writer) throws Exception
    {
        writer.writeInt16((short)(FileType & 0xFFFF));
        writer.writeInt32((int)(Size & 0xFFFFFFFFL));
        writer.writeInt16((short)(Reserved1 & 0xFFFF));
        writer.writeInt16((short)(Reserved2 & 0xFFFF));
        writer.writeInt32((int)(OffBits & 0xFFFFFFFFL));
    }

    /// <summary>
    /// Specifies the file type, must be BM. 
    /// </summary>
    /*ushort*/short FileType = (short) 0x4d42;    //BM

    /// <summary>
    /// Specifies the size, in bytes, of the bitmap file. 
    /// </summary>
    /*uint*/int Size = 0;

    /// <summary>
    /// Reserved; must be zero. 
    /// </summary>
    /*ushort*/short Reserved1 = (short) 0;

    /// <summary>
    /// Reserved; must be zero. 
    /// </summary>
    /*ushort*/short Reserved2 = (short) 0;

    /// <summary>
    /// Specifies the offset, in bytes, from the beginning of the BITMAPFILEHEADER structure to the bitmap bits.
    /// </summary>
    /*uint*/int OffBits = 0;

    /// <summary>
    /// Size of the BITMAPFILEHEADER structure.
    /// </summary>
    public static final int STRUCTURE_SIZE = 14;
}

