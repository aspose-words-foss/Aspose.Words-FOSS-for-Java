// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/01/2014 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.MemoryStorage;
import com.aspose.Debug;
import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.Guid;
import com.aspose.StringUtil;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.UriUtil;
import com.aspose.DateTimeUtil;


/// <summary>
/// Implements [MS-OLEDS] 2.3.3 OLEStream. http://msdn.microsoft.com/en-us/library/dd942499.aspx
/// </summary>
class OleStream extends Ole2StreamBase
{
    static OleStream read(MemoryStorage storage) throws Exception
    {
        return storage.containsKey(OLE_STREAM_NAME) ? new OleStream(storage.getStreamZeroPositioned(OLE_STREAM_NAME)) : null;
    }

    OleStream(/*OleObjectType*/int objectType)
    {
        Debug.assertTrue(isValidObjectType(objectType));

        mObjectType = objectType;

        mLocalUpdateTime = DateTime.getNow();
        mLocalCheckUpdateTime = DateTime.getNow();
        mRemoteUpdateTime = DateTime.getNow();
    }

    private OleStream(MemoryStream stream) throws Exception
    {
        Debug.assertTrue(stream.getPosition() == 0);
        BinaryReader reader = new BinaryReader(stream);

        int version = reader.readInt32();
        Debug.assertTrue(version == 0x02000001);

        int flags = reader.readInt32();

        mObjectType = (flags == 0x00000001) ? OleObjectType.LINKED : OleObjectType.EMBEDDED;

        // Skip unused OLEUPDATE.
        reader.readInt32();

        int reserved1 = reader.readInt32();
        Debug.assertTrue(reserved1 == 0x00);

        // ReservedMoniker, ignored.
        int monikerSize = reader.readInt32();
        MonikerStream.read(reader, monikerSize);

        // Below should be only present for Linked object.
        if (mObjectType == OleObjectType.LINKED)
        {
            // RelativeSourceMoniker, ignored.
            monikerSize = reader.readInt32();
            MonikerStream.read(reader, monikerSize); 

            monikerSize = reader.readInt32();
            mAbsoluteSourceMoniker = MonikerStream.read(reader, monikerSize);

            int clsidIndicator = reader.readInt32();
            Debug.assertTrue(clsidIndicator == -1);
            mClsId = new Guid(reader.readBytes(16));

            // Skip unused reservedDisplayName.
            DocBinaryReader.readWCharWithLengthBytes(reader);
            // Skip unused reserved.
            reader.readInt32();

            mLocalUpdateTime = DateTime.fromFileTimeUtc(reader.readInt64());
            mLocalCheckUpdateTime = DateTime.fromFileTimeUtc(reader.readInt64());
            mRemoteUpdateTime = DateTime.fromFileTimeUtc(reader.readInt64());
        }

        FileMoniker pathMoniker = ms.as(mAbsoluteSourceMoniker, FileMoniker.class);
        
        if(pathMoniker != null)
            mPath = StringUtil.hasChars(pathMoniker.getUnicodePath()) ? pathMoniker.getUnicodePath() : pathMoniker.getAnsiPath();
    }

    protected /*override*/ void write(BinaryWriter writer) throws Exception
    {
        // Version
        writer.writeInt32(0x02000001);

        writer.writeInt32(mObjectType == OleObjectType.LINKED ? (int)0x01 : 0x00);

        // LinkUpdateOptions
        writer.writeInt32((int)0x00000002);

        // Reserved1
        writer.writeInt32((int)0x00);

        // ReservedMonikerStreamSize. We don't write this moniker.
        writer.writeInt32((int)0x00);

        if (mObjectType == OleObjectType.LINKED)
        {
            // RelativeSourceMonikerStreamSize. We don't write this moniker.
            writer.writeInt32((int)0x00);

            // prepare AbsoluteSourceMoniker
            MonikerBase moniker;
            if (UriUtil.isHrefWithScheme(mPath))
            {
                // WORDSNET-16067 If link to URI resources, MS Word inserts UrlMoniker
                moniker = new UrlMoniker(mPath);
            }
            else
            {
                // For local files seems that we can skip AnsiPath and set only UnicodePath.
                FileMoniker fileMoniker = new FileMoniker();
                fileMoniker.setUnicodePath(mPath);
                moniker = fileMoniker;
            }
            MonikerStream.write(writer, moniker);

            // ClsidIndicator
            writer.writeInt32(-1);

            // ClsId
            writer.writeBytes(mClsId.toByteArray());

            // ReservedDisplayName
            writer.writeInt32((int)0x00);

            // Reserved2 
            writer.writeInt32((int)0x00);

            // LocalUpdateTime
            writer.writeInt64(DateTimeUtil.toFileTimeUtc(mLocalUpdateTime, null));

            // LocalCheckUpdateTime 
            writer.writeInt64(DateTimeUtil.toFileTimeUtc(mLocalCheckUpdateTime, null));

            // RemoteUpdateTime 
            writer.writeInt64(DateTimeUtil.toFileTimeUtc(mRemoteUpdateTime, null));
        }
    }

    protected /*override*/ String getName() { return OLE_STREAM_NAME; }

    /*OleObjectType*/int getObjectType() { return mObjectType; }

    String getPath() { return mPath; }
    void setPath(String value) { mPath = value; }

    Guid getClsId() { return mClsId; }
    void setClsId(Guid value) { mClsId = value; }

    /// <summary>
    /// Time when the container application last updated the RemoteUpdateTime field.
    /// </summary>
    DateTime getLocalUpdateTime() { return mLocalUpdateTime; }
    void setLocalUpdateTime(DateTime value) { mLocalUpdateTime = value; }

    /// <summary>
    /// Time when the container application last checked the update time of the linked object.
    /// </summary>
    DateTime getLocalCheckUpdateTime() { return mLocalCheckUpdateTime; }
    void setLocalCheckUpdateTime(DateTime value) { mLocalCheckUpdateTime = value; }

    /// <summary>
    /// Time when the linked object was last updated.
    /// </summary>
    DateTime getRemoteUpdateTime() { return mRemoteUpdateTime; }
    void setRemoteUpdateTime(DateTime value) { mRemoteUpdateTime = value; }

    private static boolean isValidObjectType(/*OleObjectType*/int objectType)
    {
        return (objectType == OleObjectType.LINKED) || (objectType == OleObjectType.EMBEDDED);
    }

    private String mPath;

    private DateTime mLocalUpdateTime;
    private DateTime mLocalCheckUpdateTime;
    private DateTime mRemoteUpdateTime;

    private Guid mClsId;

    private /*final*/ MonikerBase mAbsoluteSourceMoniker;

    private /*final*/ /*OleObjectType*/int mObjectType;
}

