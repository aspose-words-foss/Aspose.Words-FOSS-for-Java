// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/04/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MemoryStorage;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.Guid;
import com.aspose.StreamUtil;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.StringUtil;


/// <summary>
/// Implements reading/writing of [MS-OLEDS] 2.3.8 CompObjStream.
/// </summary>
class CompObjStream extends Ole2StreamBase
{
    static CompObjStream read(MemoryStorage storage) throws Exception
    {
        try
        {
            return storage.containsKey(COMP_OBJ_STREAM_NAME) 
                ? new CompObjStream(new BinaryReader(storage.getStreamZeroPositioned(COMP_OBJ_STREAM_NAME))) 
                : null;
        }
        catch(Exception e)
        {
            // Skip any errors.
            // TestDefect11473.
        }

        return null;
    }

    CompObjStream()
    {
    }

    /// <summary>
    /// Reads CompObjStream stream. 
    /// </summary>
    CompObjStream(BinaryReader reader) throws Exception
    {
        // Skip header reserved.
        reader.readInt32();
        // Skip header version
        reader.readInt32();
        // Skip header reserved.
        reader.readBytes(4);
        mClsid = new Guid(reader.readBytes(16));

        // AM. I've not seen UNICODE part filled so it seems that only ANSI part of this stream is used.
        mAnsiUserType = OleUtil.readLengthPrefixedAnsiString(reader);
        mAnsiClipboardFormat = ClipboardFormat.read(reader, false);

        mReserved1 = OleUtil.readLengthPrefixedAnsiString(reader);

        // According to spec: If this field is present and is NOT set to 0x71B239F4, 
        // the remaining fields of the structure MUST be ignored on processing. 
        if (StreamUtil.hasEnoughBytesToRead(reader, 4))
        {
            if (reader.readInt32() == UNICODE_MARKER)
            {
                // Skip unicode user type.
                DocBinaryReader.readWCharWithLengthBytes(reader);
                // Skip unicode clipboard format.
                ClipboardFormat.read(reader, true);

                mReserved2 = DocBinaryReader.readWCharWithLengthBytes(reader);
            }
        }
    }

    protected /*override*/ void write(BinaryWriter writer) throws Exception
    {
        // Write CompObjHeader. According to spec this can be set to any arbitrary value and 
        // MUST be ignored on processing. I write values extracted from test file.
        writer.writeUInt32((int) 0xfffe0001L);
        writer.writeInt32(0x00000a03);
        writer.writeUInt32((int) 0xffffffffL);
        writer.writeBytes(mClsid.toByteArray());

        OleUtil.writeLengthPrefixedAnsiString(writer, mAnsiUserType);
        ((mAnsiClipboardFormat == null) ? new ClipboardFormat("") : mAnsiClipboardFormat).write(writer, false);
        OleUtil.writeLengthPrefixedAnsiString(writer, mReserved1);

        writer.writeInt32(UNICODE_MARKER);
        writer.writeInt32(0x00);
        writer.writeInt32(0x00);
        writer.writeInt32(0x00);
    }

    protected /*override*/ String getName() { return COMP_OBJ_STREAM_NAME; }

    String getUserType() { return mAnsiUserType; }
    void setUserType(String value) { mAnsiUserType = value; }

    Guid getClsid() { return mClsid; }
    void setClsid(Guid value) { mClsid = value; }

    /// <summary>
    /// Programmatic identifier.
    /// </summary>
    /// <remarks>
    /// Identifies a class but with less precision because it is not guaranteed to be globally unique.
    /// </remarks>
    String getProgId() { return StringUtil.hasChars(mReserved1) ? mReserved1 : mReserved2; }
    void setProgId(String value) { mReserved1 = value; }

    /// <summary>
    /// Gets or sets a ClipboardFormat of the CompObj stream.
    /// </summary>
    ClipboardFormat getAnsiClipboardFormat() { return mAnsiClipboardFormat; }
    void setAnsiClipboardFormat(ClipboardFormat value) { mAnsiClipboardFormat = value; }

    // ReSharper disable UnaccessedField.Local
    // Although some values are unused I left it for debug purpose it seems they might contain something useful.

    // CompObjHeader
    private /*final*/ String mReserved2;
    // ReSharper restore UnaccessedField.Local

    // According to spec this is reserved field and must be ignored. 
    // However sometimes it seems to be the only way to get embedded clsid without Registry interaction.
    private Guid mClsid = Guid.Empty; // Struct initialization is added for Java.

    // CompObjStream.
    private String mReserved1;
    private String mAnsiUserType;
    private ClipboardFormat mAnsiClipboardFormat;

    private static final int UNICODE_MARKER = 0x71B239F4;
}

