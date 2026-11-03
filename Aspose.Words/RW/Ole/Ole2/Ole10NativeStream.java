// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/01/2014 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MemoryStorage;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.BinaryWriter;


/// <summary>
/// Implements Ole10NativeStream used to wrap OLE1 objects into OLE2 structured storage.
/// </summary>
class Ole10NativeStream extends Ole2StreamBase
{
    static Ole10NativeStream read(MemoryStorage storage) throws Exception
    {
        return storage.containsKey(OLE_10_NATIVE_STREAM_NAME)
                   ? new Ole10NativeStream(new BinaryReader(storage.getStreamZeroPositioned(OLE_10_NATIVE_STREAM_NAME)))
                   : null;
    }

    Ole10NativeStream()
    {
    }

    private Ole10NativeStream(BinaryReader reader) throws Exception
    {
        int nativeDataSize = reader.readInt32();
        mNativeData = reader.readBytes(nativeDataSize);
    }

    protected /*override*/ void write(BinaryWriter writer) throws Exception
    {
        writer.writeInt32(mNativeData.length);
        writer.writeBytes(mNativeData);
    }

    protected /*override*/ String getName() { return OLE_10_NATIVE_STREAM_NAME; }

    byte[] getNativeData() { return mNativeData; }
    void setNativeData(byte[] value) { mNativeData = value; }

    private byte[] mNativeData;
}

