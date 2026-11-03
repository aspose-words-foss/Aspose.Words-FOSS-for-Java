// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/07/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryWriter;


/// <summary>
/// Represent empty PresentationObject which is used when object has no presentation data.
/// </summary>
class PresentationObjectEmpty extends PresentationObject
{
    /*override*/ void write(BinaryWriter writer) throws Exception
    {
        writer.writeInt32(OleUtil.OLE_VERSION);

        // FormatId value 0x00 means no PresentationData.
        writer.writeInt32(0x00);
    }
}

