// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/04/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MemoryStorage;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.StreamUtil;
import com.aspose.ms.System.IO.BinaryWriter;


/// <summary>
/// Implements 2.1.4.1 ObjInfo Stream.
/// Contains an ODT structure which specifies information about that embedded OLE object.
/// </summary>
class ObjInfoStream extends Ole2StreamBase
{
    static ObjInfoStream read(MemoryStorage storage) throws Exception
    {
        if (!storage.containsKey(OBJ_INFO_STREAM_NAME))
            return null;
        
        // WORDSJAVA-2266 The resilience against a zero length stream.
        MemoryStream memoryStream = storage.getStreamZeroPositioned(OBJ_INFO_STREAM_NAME);
        if (memoryStream.getLength() == 0)
            return null;
        
        return new ObjInfoStream(new BinaryReader(memoryStream));
    }

    private ObjInfoStream()
    {
    }

    static ObjInfoStream defaultControl()
    {
        ObjInfoStream objInfoStream = new ObjInfoStream();
        objInfoStream.mFlags1 = OdtPersist1.RECOMPOSE_ON_RESIZE | OdtPersist1.OCX;
        objInfoStream.mFlags2 = OdtPersist2.QUERIED_EMF;
        objInfoStream.mClipboardFormat = OdtClipboardFormat.METAFILE;

        return objInfoStream;
    }

    static ObjInfoStream defaultObject(boolean isIcon, boolean isLink)
    {
        ObjInfoStream objInfoStream = new ObjInfoStream();
        objInfoStream.mFlags1 = (isIcon ? OdtPersist1.ICON : OdtPersist1.NONE) |
            (isLink ? OdtPersist1.LINK : OdtPersist1.NONE);
        objInfoStream.mFlags2 = OdtPersist2.EMF;
        objInfoStream.mClipboardFormat = OdtClipboardFormat.METAFILE;

        return objInfoStream;
    }

    ObjInfoStream(BinaryReader reader) throws Exception
    {
        mFlags1 = (/*OdtPersist1*/int)(reader.readUInt16() & 0xFFFF);
        mClipboardFormat = (/*OdtClipboardFormat*/int)(reader.readUInt16() & 0xFFFF);

        // According to spec this member does not exist if the ObjInfo stream is not large enough to accommodate it.
        if (StreamUtil.hasEnoughBytesToRead(reader, 2))
            mFlags2 = (/*OdtPersist2*/int)((reader.readUInt16() & 0xFFFF) & 0x0f);
    }

    protected /*override*/ void write(BinaryWriter writer) throws Exception
    {
        writer.writeUInt16((/*ushort*/short)mFlags1);
        writer.writeUInt16((/*ushort*/short)mClipboardFormat);
        writer.writeUInt16((/*ushort*/short)mFlags2);
    }

    protected /*override*/ String getName() { return OBJ_INFO_STREAM_NAME; }

    /*OdtPersist1*/int getFlags1() { return mFlags1; }
    void setFlags1(/*OdtPersist1*/int value) { mFlags1 = value; }

    /*OdtPersist2*/int getFlags2() { return mFlags2; }
    void setFlags2(/*OdtPersist2*/int value) { mFlags2 = value; }

    /// <summary>
    /// Indicates that object is drawn as icon.
    /// </summary>
    boolean isIcon() { return (mFlags1 & OdtPersist1.ICON) != 0; }
    void isIcon(boolean value)
    {
        if(value)
            mFlags1 |= OdtPersist1.ICON;
        else
            mFlags1 &= ~OdtPersist1.ICON;
    }

    private /*OdtPersist1*/int mFlags1;
    private /*OdtPersist2*/int mFlags2;
    private /*OdtClipboardFormat*/int mClipboardFormat = OdtClipboardFormat.NONE;
}

