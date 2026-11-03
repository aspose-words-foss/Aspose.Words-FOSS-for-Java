// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/12/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryWriter;


/// <summary>
/// Represents OCXDATA OLE2.0 stream which is used internally by ActiveX controls.
/// </summary>
class OcxDataStream extends Ole2StreamBase
{
    protected /*override*/ void write(BinaryWriter writer) throws Exception
    {
        writer.writeBytes(mData);
    }

    protected /*override*/ String getName() { return OCX_DATA_STREAM_NAME; }

    byte[] getData() { return mData; }
    void setData(byte[] value) { mData = value; }

    private byte[] mData;
}

