// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/01/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.Text.Encoding;


/// <summary>
/// Specifies the format of a standard font structure as persisted to a stream.
/// </summary>
/// <dev>
/// [MS-OFORMS] 2.4.12 StdFont.
/// </dev>
class StdFont
{
    /// <summary>
    /// Reads StdFont structure into <see cref="Forms2Attr.Font"/> key.
    /// </summary>
    static void read(BinaryReader reader, Forms2Pr pr) throws Exception
    {
        StdFont font = new StdFont();
        font.read(reader);

        pr.setAttr(Forms2Attr.FONT, font);
    }

    /// <summary>
    /// Writes to a binary writer.
    /// </summary>
    void write(BinaryWriter writer) throws Exception
    {
        writer.writeByte(mVersion); 
        writer.writeInt16(mCharset); 
        writer.writeByte(mFlags); 
        writer.writeInt16(mWeight); 
        writer.writeUInt32(mHeight);

        byte[] fontFaceBytes = Encoding.getASCII().getBytes(mFontFace);
        writer.writeByte((byte)fontFaceBytes.length);
        writer.writeBytes(fontFaceBytes);
    }

    /// <summary>
    /// Returns true, if this instance has default value in a specified collection.
    /// </summary>
    static boolean hasDefaultValue(Forms2Pr pr)
    {
        return pr.isDefaultValue(Forms2Attr.FONT);
    }

    /// <summary>
    /// Reads from a binary reader.
    /// </summary>
    private void read(BinaryReader reader) throws Exception
    {
        mVersion = reader.readByte();
        mCharset = reader.readInt16();
        mFlags = reader.readByte();
        mWeight = reader.readInt16();
        mHeight = reader.readUInt32();
        
        int fontFaceLen = (reader.readByte() & 0xFF);
        byte[] fontFaceBytes = reader.readBytes(fontFaceLen);
        mFontFace = Encoding.getASCII().getString(fontFaceBytes);
    }

    private byte mVersion;
    private short mCharset;
    private byte mFlags;
    private short mWeight;
    private /*uint*/int mHeight;
    private String mFontFace;

    static final String GUID = "0be35203-8f91-11ce-9de3-00aa004bb851";
}

