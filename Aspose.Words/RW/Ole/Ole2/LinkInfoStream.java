// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/04/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MemoryStorage;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.StringUtil;


/// <summary>
/// Implements reading/writing of LinkInfo stream.
/// </summary>
/// <remarks>
/// AM. There is no information about this stream in spec. It's good thing that it is simple enough.
/// </remarks>
class LinkInfoStream extends Ole2StreamBase
{
    static LinkInfoStream read(MemoryStorage storage) throws Exception
    {
        return storage.containsKey(LINK_INFO_STREAM_NAME) ? new LinkInfoStream(new BinaryReader(storage.getStreamZeroPositioned(LINK_INFO_STREAM_NAME))) : null;
    }

    LinkInfoStream(String topicName, String itemName)
    {
        mTopicName = topicName;
        mItemName = itemName;
    }

    private LinkInfoStream(BinaryReader reader) throws Exception
    {
        int topicNameLength = reader.readInt16();
        mTopicName = com.aspose.ms.System.Text.Encoding.getEncoding(1251).getString(reader.readBytes(topicNameLength));
        mTopicName = msString.trim(mTopicName, '\u0000');

        int itemNameLength = reader.readInt16();
        mItemName = com.aspose.ms.System.Text.Encoding.getEncoding(1251).getString(reader.readBytes(itemNameLength));
        mItemName = msString.trim(mItemName, '\u0000');

        // Seems here is Unicode marker 0xccccbbbb and the same TopicName/ItemName in Unicode.
    }

    protected /*override*/ void write(BinaryWriter writer) throws Exception
    {
        DocBinaryWriter.writeAnsiString(writer, StringUtil.hasChars(mTopicName) ? mTopicName : "", 1251, true);
        DocBinaryWriter.writeAnsiString(writer, StringUtil.hasChars(mItemName) ? mItemName : "", 1251, true);
    }

    protected /*override*/ String getName() { return LINK_INFO_STREAM_NAME; }

    String getTopicName() { return mTopicName; }

    String getItemName() { return mItemName; }

    private /*final*/ String mTopicName;
    private /*final*/ String mItemName;
}

