// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/02/2017 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.Text.Encoding;


/// <summary>
/// Utility methods for writing binary format DOC files.
/// </summary>
/*static*/ class DocBinaryWriter
{
	/* Simulation of static class by using private constructor */
	private DocBinaryWriter()
	{}

    /// <summary>
    /// Writes 16 bit length and string characters in Unicode or 1252 encoding, optional 2 byte terminator.
    /// </summary>
    /// <returns>
    /// Returns total number of bytes written.
    /// </returns>
    static int writeWordString(String value, int maxLengthChars, BinaryWriter writer, boolean isUnicode, boolean isTerminator) throws Exception
    {
        int startPos = (int)writer.getBaseStream().getPosition();

        value = (value != null) ? value : "";

        if (value.length() > maxLengthChars)
            value = value.substring((0), (0) + (maxLengthChars));

        if (isUnicode)
        {
            writer.writeUInt16((/*ushort*/short)value.length());
            byte[] data = DocStringUtil.getEncoding(true).getBytes(value);
            writer.writeBytes(data);
            if (isTerminator)
                writer.writeUInt16((/*ushort*/short)0);
        }
        else
        {
            // WORDSNET-4502 A string is longer than maximum that can be encoded by a byte. Let's truncate.
            final int MAX_1252_STRING_LENGTH = 0xff;
            int length = (((byte)Math.min(value.length(), MAX_1252_STRING_LENGTH)) & 0xFF);
            writer.writeByte((byte)length);
            byte[] data = DocStringUtil.getEncoding(false).getBytes(value);
            writer.writeBytes(data, 0, length);
            if (isTerminator)
                writer.writeByte((byte)0);
        }

        return (int)writer.getBaseStream().getPosition() - startPos;
    }

    /// <summary>
    /// Writes a UInt16 length in chars, followed by Unicode characters, followed
    /// by padding up to the total length.
    /// </summary>
    static void writeWordStringWithPad(String value, BinaryWriter writer, int totalLength) throws Exception
    {
        // RESILIENCY Prevent writing more than specified by the caller. 
        int maxLengthChars = (totalLength / 2) - 1;
        int lengthChars = Math.min(value.length(), maxLengthChars);

        writer.writeUInt16((/*ushort*/short)lengthChars);

        Encoding unicodeEncoding = DocStringUtil.getEncoding(true);
        byte[] data = unicodeEncoding.getBytes(value);
        int lengthBytes = lengthChars * 2;
        writer.writeBytes(data, 0, lengthBytes);

        // Minus 2 because wrote two bytes already.
        int padLength = totalLength - lengthBytes - 2;
        writer.writeBytes(new byte[padLength]);
    }

    /// <summary>
    /// Writes a WCHAR string. It is a unicode string with a zero termnator.
    /// Returns number of bytes written.
    /// </summary>
    static int writeWChar(String value, BinaryWriter writer) throws Exception
    {
        byte[] data = new byte[DocStringUtil.getWCharLengthBytes(value)];
        Encoding unicodeEncoding = DocStringUtil.getEncoding(true);
        unicodeEncoding.getBytes(value, 0, value.length(), data, 0);
        writer.writeBytes(data);
        return data.length;
    }

    static void writeWCharWithLengthChars(String value, BinaryWriter writer) throws Exception
    {
        writer.writeInt32(DocStringUtil.getWCharLengthChars(value));
        writeWChar(value, writer);
    }

    static void writeWCharWithLengthBytes(String value, BinaryWriter writer) throws Exception
    {
        writer.writeInt32(DocStringUtil.getWCharLengthBytes(value));
        writeWChar(value, writer);
    }

    /// <summary>
    /// Writes ANSI string with given encoding. Optionally writes 16-bit length prefix.
    /// </summary>
    /// <param name="writer"></param>
    /// <param name="text"></param>
    /// <param name="codePage"></param>
    /// <param name="writeLength"></param>
    static void writeAnsiString(BinaryWriter writer, String text, int codePage, boolean writeLength) throws Exception
    {
        if (writeLength)
            writer.writeInt16((short)(text.length() + 1));

        writer.writeBytes(Encoding.getEncoding(codePage).getBytes(text));
        writer.writeByte((byte)0x00);
    }
}

