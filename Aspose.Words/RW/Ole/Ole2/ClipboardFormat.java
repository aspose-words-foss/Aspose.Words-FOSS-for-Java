// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/04/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.StringUtil;


/// <summary>
/// Implements read/write for [MS-OLEDS] 2.3.1 ClipboardFormatOrAnsiString structure.
/// </summary>
class ClipboardFormat
{
    ClipboardFormat(/*StandardClipboardFormat*/int cf)
    {
        mStandardFormat = cf;
    }

    ClipboardFormat(String cf)
    {
        mRegisteredFormat = cf;
    }

    static ClipboardFormat read(BinaryReader reader, boolean isUnicode) throws Exception
    {
        ClipboardFormat clipboardFormat;
        // Read clipboard format
        int markerOrlength = reader.readInt32();
        if (markerOrlength > 0)
        {
            clipboardFormat = new ClipboardFormat((isUnicode)
                ? DocBinaryReader.readWChar(reader, markerOrlength)
                : OleUtil.readLengthPrefixedAnsiString(reader, markerOrlength));
        }
        else if (markerOrlength < 0)
        {
            clipboardFormat = new ClipboardFormat((/*StandardClipboardFormat*/int)reader.readInt32());
        }
        else
        {
            // In case markerOrLength equal 0 there is no data in this structure.
            clipboardFormat = new ClipboardFormat(StandardClipboardFormat.NONE);
        }

        return clipboardFormat;
    }

    void write(BinaryWriter writer, boolean isUnicode) throws Exception
    {
        if(mStandardFormat != StandardClipboardFormat.NONE)
        {
            writer.writeInt32(-1);              // markerOrlength
            writer.writeInt32((int)mStandardFormat);
        }
        else if (StringUtil.hasChars(mRegisteredFormat))
        {
            if (isUnicode)
                DocBinaryWriter.writeWCharWithLengthChars(mRegisteredFormat, writer);
            else
                OleUtil.writeLengthPrefixedAnsiString(writer, mRegisteredFormat);
        }
        else
            writer.writeInt32(0x00);
    }

    /*StandardClipboardFormat*/int getStandardFormat() { return mStandardFormat; }

    String getRegisteredFormat() { return mRegisteredFormat; }

    private /*final*/ /*StandardClipboardFormat*/int mStandardFormat = StandardClipboardFormat.NONE;
    private /*final*/ String mRegisteredFormat;
}

