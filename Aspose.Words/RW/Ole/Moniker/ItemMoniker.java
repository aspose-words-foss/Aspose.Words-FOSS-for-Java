// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/03/2014 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.Guid;
import com.aspose.ms.System.Text.Encoding;


/// <summary>
/// Implements [MS-OSHARED] 2.3.7.5 ItemMoniker. http://msdn.microsoft.com/en-us/library/dd926939(v=office.12).aspx
/// </summary>
class ItemMoniker extends MonikerBase
{
    ItemMoniker()
    {
    }

    ItemMoniker(String delimeter, String item)
    {
        mDelimeter = delimeter;
        mItem = item;
    }

    /*override*/ void read(BinaryReader reader) throws Exception
    {
        int delimeterLength = reader.readInt32();
        mDelimeter = readAnsiNullTerminated(reader);

        int delimeterUnicodeSize = delimeterLength - (mDelimeter.length() + 1 /* include terminator */);
        if(delimeterUnicodeSize > 0)
        {
            // There is delimeterUnicode field.
            mDelimeter = DocBinaryReader.readWChar(reader, delimeterUnicodeSize/2);
        }

        int itemLength = reader.readInt32();
        mItem = readAnsiNullTerminated(reader);

        int itemUnicodeSize = itemLength - (mItem.length() + 1 /* include terminator */);
        if (itemUnicodeSize > 0)
        {
            // There is itemUnicode field.
            mItem = DocBinaryReader.readWChar(reader, itemUnicodeSize / 2);
        }
    }

    /*override*/ void write(BinaryWriter writer)
    {
        // AM. We never write this moniker so implementaion is postponed.
        throw new UnsupportedOperationException();
    }

    /*override*/ Guid getClsId() { return ITEM_MONIKER_CLS_ID; }

// Seems I have this in OleUtil.
    private static String readAnsiNullTerminated(BinaryReader reader) throws Exception
    {
        int startPos = (int)reader.getBaseStream().getPosition();

        while (reader.readByte() != 0)
        {
            // Do nothing, just read the bytes.
        }

        int endPos = (int)reader.getBaseStream().getPosition();

        reader.getBaseStream().setPosition(startPos);
        byte[] bytes = reader.readBytes(endPos - startPos);

        return Encoding.getDefault().getString(bytes, 0, bytes.length - 1);
    }

    String getDelimeter() { return mDelimeter; }

    String getItem() { return mItem; }

    private String mDelimeter;
    private String mItem;
}


