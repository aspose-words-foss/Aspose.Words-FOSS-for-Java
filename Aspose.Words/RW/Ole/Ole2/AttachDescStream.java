// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/08/2013 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MemoryStorage;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.Debug;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.StringUtil;
import com.aspose.ms.System.Text.Encoding;


/// <summary>
/// Represent AttachDesc stream which is description for Outlook Attach object.
/// http://msdn.microsoft.com/en-us/library/ee157577(v=exchg.80).aspx
/// </summary>
/// <remarks>
/// AM. For a while we ignore almost whole data in this stream except file extension information.
/// </remarks>
class AttachDescStream extends Ole2StreamBase
{
    static AttachDescStream read(MemoryStorage storage) throws Exception
    {
        return storage.containsKey(ATTACH_DESC_STREAM_NAME) ? new AttachDescStream(storage.getStreamZeroPositioned(ATTACH_DESC_STREAM_NAME)) : null;
    }

    private AttachDescStream(MemoryStream stream) throws Exception
    {
        stream.setPosition(0);

        mReader = new BinaryReader(stream);

        int version = (mReader.readUInt16() & 0xFFFF);

        // Stop reading if stream version is unknown.
        if (!isSupportedVersion(version))
            return;

        readAnsiString();       // ansiLongPathName 
        readAnsiString();       // ansiPathName 
        readAnsiString();       // ansiDisplayName
        readAnsiString();       // ansiLongFileName
        mAnsiFileName = readAnsiString();

        mAnsiExtension = readAnsiString();

        mReader.readBytes(8);   // creationTime
        mReader.readBytes(8);   // lastModifiedTime
        mReader.readInt32();    // attachMethod

        if (version == RIGHTS_MANAGED_MESSAGE_VERSION)
        {
            readUnicodeString();    // contentId
            readUnicodeString();    // contentLocation
            readUnicodeString();    // unicodeLongPathName

            readUnicodeString();    // unicodePathName
            readUnicodeString();    // unicodeDisplayName
            readUnicodeString();    // unicodeLongFileName
            mUnicodeFileName = readUnicodeString();
            
            mUnicodeExtension = readUnicodeString();

            readUnicodeString();    // imagePreviewSmall
            readUnicodeString();    // imagePreviewMedium
            readUnicodeString();    // imagePreviewLarge

            mReader.readInt32();    // rendered
            mReader.readInt32();    // flags
        }

        Debug.assertTrue(stream.getPosition() == stream.getLength());
    }

    protected /*override*/ void write(BinaryWriter writer)
    {
        // AM. We never write this so implementation is postponed.
        throw new UnsupportedOperationException();
    }

    protected /*override*/ String getName() { return ATTACH_DESC_STREAM_NAME; }

    /// <summary>
    /// Extension of attached file.
    /// </summary>
    String getExtension() { return StringUtil.hasChars(mUnicodeExtension) ? mUnicodeExtension : mAnsiExtension; }

    /// <summary>
    /// File name of attached file.
    /// </summary>
    String getFileName() { return StringUtil.hasChars(mUnicodeFileName) ? mUnicodeFileName : mAnsiFileName; }

    private static boolean isSupportedVersion(int version)
    {
        return (version == RIGHTS_MANAGED_MESSAGE_VERSION) || (version == UNDOCUMENTED_MESSAGE_VERSION);
    }

    /// <summary>
    /// Reads 2.2.4.2 Non-Unicode LPString. Prefixed with 1-byte length, ANSI characters, not null terminated.
    /// </summary>
    private String readAnsiString() throws Exception
    {
        int length = (mReader.readByte() & 0xFF);
        return (length > 0) ? Encoding.getEncoding(1251).getString(mReader.readBytes(length)) : "";
    }

    /// <summary>
    /// Reads 2.2.4.1 LPString. Prefixed with 1-byte length, Unicode characters, not null terminated.
    /// </summary>
    /// <returns></returns>
    private String readUnicodeString() throws Exception
    {
        int length = (mReader.readByte() & 0xFF);
        return (length > 0) ? Encoding.getUnicode().getString(mReader.readBytes(length*2)) : "";
    }

    private /*final*/ BinaryReader mReader;

    private /*final*/ String mAnsiExtension;
    private /*final*/ String mUnicodeExtension;

    private /*final*/ String mAnsiFileName;
    private /*final*/ String mUnicodeFileName;


    /// <summary>
    /// Seen in TestJira9338. Undocumented, seems to be limited version of rights managed message.
    /// </summary>
    private static final int UNDOCUMENTED_MESSAGE_VERSION = 0x0100;

    private static final int RIGHTS_MANAGED_MESSAGE_VERSION = 0x0203;
}

