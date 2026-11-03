// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/12/2016 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.Text.Encoding;


/// <summary>
/// Utility methods to work with encrypted/signed etc Office documents.
/// </summary>
/*static*/ class OfficeCryptoUtil
{
	/* Simulation of static class by using private constructor */
	private OfficeCryptoUtil()
	{}

    /// <summary>
    /// Reads a WCHAR string. It is a unicode string with a zero terminator.
    /// </summary>
    static String readWChar(BinaryReader reader, int lengthBytes) throws Exception
    {
        if (lengthBytes == 0)
            return "";

        byte[] data = reader.readBytes(lengthBytes);
        return G_UNICODE_ENCODING.getString(data, 0, lengthBytes - 2);
    }

    /// <summary>
    /// Writes a WCHAR string. It is a unicode string with a zero termnator.
    /// Returns number of bytes written.
    /// </summary>
    static int writeWChar(String value, BinaryWriter writer) throws Exception
    {
        byte[] data = new byte[getWCharLengthBytes(value)];
        G_UNICODE_ENCODING.getBytes(value, 0, value.length(), data, 0);
        writer.writeBytes(data);
        return data.length;
    }

    /// <summary>
    /// Returns the number of bytes needed to write a unicode string with a zero terminator.
    /// </summary>
    private static int getWCharLengthBytes(String value)
    {
        return getWCharLengthChars(value) * 2;
    }

    /// <summary>
    /// Returns the number of characters in a unicide string plus one for zero terminator.
    /// </summary>
    private static int getWCharLengthChars(String value)
    {
        return value.length() + 1;
    }

    private static /*final*/ Encoding G_UNICODE_ENCODING = Encoding.getUnicode();
}

