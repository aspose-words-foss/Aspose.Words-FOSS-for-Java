// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/04/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MemoryStorage;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.StreamUtil;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.StringUtil;


/// <summary>
/// Implements reading of OCXNAME stream which contains name of ActiveX control.
/// </summary>
class OcxNameStream extends Ole2StreamBase
{
    static OcxNameStream read(MemoryStorage storage) throws Exception
    {
        return storage.containsKey(OCX_NAME_STREAM_NAME) ? new OcxNameStream(storage.getStreamZeroPositioned(OCX_NAME_STREAM_NAME)) : null;
    }

    OcxNameStream(String ocxName)
    {
        mValue = ocxName;
    }

    private OcxNameStream(Stream stream) throws Exception
    {
        BinaryReader reader = new BinaryReader(stream, Encoding.getUnicode());

        StringBuilder sb = new StringBuilder();
        while (StreamUtil.hasEnoughBytesToRead(reader, 2))
        {
            char ch = reader.readChar();

            if (ch == '\u0000')
                break;

            sb.append(ch);
        }

        mValue = sb.toString();
    }

    protected /*override*/ void write(BinaryWriter writer) throws Exception
    {
        if (StringUtil.hasChars(mValue))
        {
            for (int i = 0; i < mValue.length(); i++)
            {
                char ch = mValue.charAt(i);
                writer.writeByte((byte) ch);
                writer.writeByte((byte) 0);
            }
        }
        writer.writeInt32(0x00);
    }

    protected /*override*/ String getName() { return OCX_NAME_STREAM_NAME; }

    String getValue() { return mValue; }

    private /*final*/ String mValue;
}

