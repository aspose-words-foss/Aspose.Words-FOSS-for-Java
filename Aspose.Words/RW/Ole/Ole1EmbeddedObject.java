// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/07/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.msMath;
import com.aspose.MemoryStorage;
import com.aspose.FileSystem;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.BinaryWriter;


/// <summary>
/// Implements [MS-OLEDS] 2.2.5 EmbeddedObject.
/// </summary>
class Ole1EmbeddedObject extends Ole1Object
{
    Ole1EmbeddedObject(ObjectHeader objectHeader, BinaryReader reader) throws Exception
    {
        mClassName = objectHeader.getClassName();

        /*uint*/int nativeDataSize = reader.readUInt32();
        // WORDSNET-18063 Resilience against invalid size.
        nativeDataSize = msMath.minUint(nativeDataSize, (/*uint*/int)(reader.getBaseStream().getLength() - reader.getBaseStream().getPosition()));

        mNativeData = reader.readBytes((int)(nativeDataSize & 0xFFFFFFFFL));

        mPresentationObject = PresentationObject.read(reader);
    }

    Ole1EmbeddedObject(String progId, MemoryStorage embeddedData, byte[] metafileData) throws Exception
    {
        mClassName = progId;
        
        FileSystem fs = new FileSystem(embeddedData);
        MemoryStream ms = new MemoryStream();
        fs.save(ms);
        mNativeData = ms.toArray();

        // So far we support just Metafile Presentation object. Later we can add more objects if needed.
        mPresentationObject = (metafileData != null) ?
            (PresentationObject)new PresentationObjectMetafile(metafileData) :
            new PresentationObjectEmpty();
    }

    /*override*/ void write(BinaryWriter writer) throws Exception
    {
        ObjectHeader objectHeader = new ObjectHeader(OleObjectType.EMBEDDED, mClassName, "", "");
        objectHeader.write(writer);

        writer.writeInt32(mNativeData.length);
        writer.writeBytes(mNativeData);

        mPresentationObject.write(writer);
    }

    /*override*/ /*OleObjectType*/int getObjectType() { return OleObjectType.EMBEDDED; }

    /// <summary>
    /// Specifies OLE1.0 Application specific native data.
    /// </summary>
    byte[] getNativeData() { return mNativeData; }

    /// <summary>
    /// Specifies OLE1.0 presentation data to display this object in container applications.
    /// </summary>
    PresentationObject getPresentationObject() { return mPresentationObject; }

    private /*final*/ byte[] mNativeData;
    private /*final*/ PresentationObject mPresentationObject;
}

