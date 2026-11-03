// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/03/2014 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.Debug;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.Guid;


/// <summary>
/// Implements [MS-OSHARED] 2.3.7.6 URLMoniker. http://msdn.microsoft.com/en-us/library/dd906718(v=office.12).aspx
/// </summary>
class UrlMoniker extends MonikerBase
{
    UrlMoniker()
    {
    }

    UrlMoniker(String url)
    {
        mUrl = url;
    }

    /*override*/ void read(BinaryReader reader) throws Exception
    {
        int length = reader.readInt32();

        int startPos = (int) reader.getBaseStream().getPosition();

        StringBuilder sb = new StringBuilder();
        while ((reader.getBaseStream().getPosition() - startPos) < length)
        {
            char ch = (char) (reader.readUInt16() & 0xFFFF);
            if (ch == 0x00)
                break;

            sb.append(ch);
        }
        mUrl = sb.toString();

        if ((reader.getBaseStream().getPosition() - startPos) < length)
        {
            // This field MUST equal {0xF4815879, 0x1D3B, 0x487F, 0xAF, 0x2C, 0x82, 0x5D, 0xC4, 0x85, 0x27, 0x63} if present.
            reader.readBytes(16); // Guid serialGuid

            int serialVersion = reader.readInt32();
            Debug.assertTrue(serialVersion == 0);

            reader.readInt32(); // int uriFlags
        }
    }

    /*override*/ void write(BinaryWriter writer) throws Exception
    {
        byte[] unicodeBytes = Encoding.getUnicode().getBytes((mUrl != null) ? mUrl : "");

        writer.writeInt32(unicodeBytes.length + 2 /* zero terminator */);
        writer.writeBytes(unicodeBytes);
        writer.writeByte((byte)0x00);
        writer.writeByte((byte)0x00);

        // Do not write other data.
    }

    /*override*/ Guid getClsId() { return URL_MONIKER_CLS_ID; }

    String getUrl() { return mUrl; }

    private String mUrl;
}

