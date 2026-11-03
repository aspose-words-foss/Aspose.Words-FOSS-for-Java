// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/03/2014 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MemoryStorage;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.BinaryWriter;


/// <summary>
/// Implements base class for miscellaneous OLE2 stream implementations.
/// </summary>
abstract class Ole2StreamBase
{
    void write(MemoryStorage storage) throws Exception
    {
        MemoryStream stream = new MemoryStream();
        write(new BinaryWriter(stream));
        storage.set(getName(), stream);
    }

    protected abstract void write(BinaryWriter writer) throws Exception;

    protected abstract String getName();

    static final String OLE_10_NATIVE_STREAM_NAME = "\u0001Ole10Native";
    static final String OCX_NAME_STREAM_NAME = "\u0003OCXNAME";
    static final String OBJ_INFO_STREAM_NAME = "\u0003ObjInfo";
    static final String PRINT_STREAM_NAME = "\u0003PRINT";
    static final String OCX_DATA_STREAM_NAME = "\u0003OCXDATA";
    static final String COMP_OBJ_STREAM_NAME = "\u0001CompObj";
    static final String LINK_INFO_STREAM_NAME = "\u0003LinkInfo";
    static final String ATTACH_DESC_STREAM_NAME = "AttachDesc";
    static final String OLE_STREAM_NAME = "\u0001Ole";
    static final String META_STREAM_NAME = "\u0003META";

    static final String PIC_STREAM_NAME = "\u0003PIC";
}

