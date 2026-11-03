// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/03/2014 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.Debug;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.StringUtil;
import com.aspose.ms.System.Guid;


/// <summary>
/// Implements [MS-OSHARED] 2.3.7.8 FileMoniker. http://msdn.microsoft.com/en-us/library/dd951987(v=office.12).aspx
/// </summary>
class FileMoniker extends MonikerBase
{
    /*override*/ void read(BinaryReader reader) throws Exception
    {
        // cAnti
        reader.readInt16();

        int ansiLength = reader.readInt32();
        mAnsiPath = DocBinaryReader.readAChar(reader, ansiLength);

        // endServer
        reader.readUInt16();

        int versionNumber = (reader.readUInt16() & 0xFFFF);
        Debug.assertTrue(versionNumber == 0xdead);

        // reserved1
        reader.readBytes(16);

        // reserved2
        reader.readBytes(4);

        int cbUnicodePathSize = reader.readInt32();

        if(cbUnicodePathSize > 0)
        {
            int cbUnicodePathBytes = reader.readInt32();
            int usKeyValue = reader.readInt16();
            Debug.assertTrue(usKeyValue == 3);

            byte[] unicodePathBytes = reader.readBytes(cbUnicodePathBytes);

            mUnicodePath = Encoding.getUnicode().getString(unicodePathBytes);
        }
    }

    /*override*/ void write(BinaryWriter writer) throws Exception
    {
        // cAnti
        writer.writeInt16((short)0);

        // AnsiPath
        if (StringUtil.hasChars(mAnsiPath))
        {
            writer.writeInt32(mAnsiPath.length() + 1);
            DocBinaryWriter.writeAnsiString(writer, mAnsiPath, 1251, false);
        }
        else
        {
            writer.writeInt32(0);
        }

        // EndServer
        writer.writeUInt16((/*ushort*/short)0xffff);

        // VersionNumber
        writer.writeUInt16((/*ushort*/short)0xdead);

        // Reserved1
        writer.writeBytes(new byte[16]);

        // Reserved2
        writer.writeBytes(new byte[4]);

        byte[] unicodePathBytes = Encoding.getUnicode().getBytes(mUnicodePath);

        // cbUnicodePathSize
        writer.writeInt32(unicodePathBytes.length + 6 /* cbUnicodePathBytes + usKeyValue */);

        // cbUnicodePathBytes
        writer.writeInt32(unicodePathBytes.length);

        // usKeyValue
        writer.writeUInt16((/*ushort*/short)3);

        writer.writeBytes(unicodePathBytes);
    }

    /*override*/ Guid getClsId() { return FILE_MONIKER_CLS_ID; }

    String getAnsiPath() { return mAnsiPath; }

    String getUnicodePath() { return mUnicodePath; }
    void setUnicodePath(String value) { mUnicodePath = value; }

    private String mAnsiPath;
    private String mUnicodePath;
}

