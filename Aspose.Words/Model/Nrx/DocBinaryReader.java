// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/02/2017 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.StreamUtil;
import com.aspose.ms.System.Text.Encoding;


/// <summary>
/// Utility methods for reading binary format DOC files.
/// </summary>
/*static*/ class DocBinaryReader
{
	/* Simulation of static class by using private constructor */
	private DocBinaryReader()
	{}

    /// <summary>
    /// Reads Word style string in Unicode or 1252 encoding. 
    /// String is prefixed with length, then body and optional terminator.
    /// The length stored in the string does not include the terminator.
    /// The prefix of the string length is int16 for Unicode strings and byte for 1252 encoding strings.
    /// </summary>
    /// <param name="reader">Reader can be in any encoding.</param>
    /// <param name="isUnicode"></param>
    /// <param name="isTerminator">Specifies whether to read the zero terminator.</param>
    static String readWordString(BinaryReader reader, boolean isUnicode, boolean isTerminator) throws Exception
    {
        if (isUnicode)
        {
            int length = (reader.readUInt16() & 0xFFFF) * 2;

            // WORDSNET-5512 Resilience from reading invalid strings.
            if (!StreamUtil.hasEnoughBytesToRead(reader, length))
                length = 0;

            byte[] data = reader.readBytes(length);
            String s = DocStringUtil.getEncoding(isUnicode).getString(data);
            if (isTerminator)
                reader.readUInt16();
            return s;
        }
        else
        {
            int length = (reader.readByte() & 0xFF);
            byte[] data = reader.readBytes(length);
            String s = DocStringUtil.getEncoding(isUnicode).getString(data);
            if (isTerminator)
                reader.readByte();
            return s;
        }
    }

    /// <summary>
    /// Reads a string that has UInt16 length in chars, consists of Unicode characters, padded
    /// with zeroes to total length. Strips padding from the end of the string and positions
    /// the reader to be at the end of the padding.
    /// </summary>
    static String readWordStringWithPad(BinaryReader reader, int totalLength) throws Exception
    {
        int startPos = (int)reader.getBaseStream().getPosition();

        int lengthBytes = (reader.readUInt16() & 0xFFFF) * 2;

        // RESILIENCY Prevent reading more than specified by the caller.
        // Minus 2 because read two bytes already.
        lengthBytes = Math.min(lengthBytes, totalLength - 2);

        byte[] data = reader.readBytes(lengthBytes);
        Encoding unicodeEncoding = DocStringUtil.getEncoding(true);
        String result = unicodeEncoding.getString(data);

        // We must set the position to the end of the padding.
        reader.getBaseStream().setPosition(startPos + totalLength);

        return result;
    }

    /// <summary>
    /// Reads a WCHAR string. It is a unicode string with a zero terminator.
    /// </summary>
    static String readWChar(BinaryReader reader, int lengthBytes) throws Exception
    {
        if (lengthBytes == 0)
            return "";

        byte[] data = reader.readBytes(lengthBytes);
        Encoding unicodeEncoding = DocStringUtil.getEncoding(true);
        return unicodeEncoding.getString(data, 0, lengthBytes - 2);
    }

    /// <summary>
    /// Reads int32 length of a string in bytes and then reads a WCHAR string.
    /// </summary>
    static String readWCharWithLengthBytes(BinaryReader reader) throws Exception
    {
        int lengthBytes = reader.readInt32();
        return readWChar(reader, lengthBytes);
    }

    /// <summary>
    /// Reads int32 length of a string in chars and then reads a WCHAR string.
    /// </summary>
    static String readWCharWithLengthChars(BinaryReader reader) throws Exception
    {
        int lengthBytes = reader.readInt32() * 2;
        return readWChar(reader, lengthBytes);
    }

    /// <summary>
    /// Reads an ACHAR 1252 encoding string null terminated.
    /// </summary>
    static String readAChar(BinaryReader reader, int lengthBytes) throws Exception
    {
        if (lengthBytes == 0)
            return "";

        byte[] data = reader.readBytes(lengthBytes);
        Encoding win1252Encoding = DocStringUtil.getEncoding(false);
        return win1252Encoding.getString(data, 0, lengthBytes - 1);
    }

    /// <summary>
    ///  Reads an ACHAR null terminated string using given codepage.
    /// </summary>
    /// <remarks>
    /// AM. Above method uses cached encoding, think it's better do not refactor these methods.
    /// </remarks>
    static String readAChar(BinaryReader reader, int lengthBytes, int codepage) throws Exception
    {
        if (lengthBytes == 0)
            return "";

        byte[] data = reader.readBytes(lengthBytes);
        Encoding encoding = Encoding.getEncoding(codepage);
        return encoding.getString(data, 0, lengthBytes - 1);
    }

    /// <summary>
    /// Reads int32 length of a string in bytes and then reads an ACHAR (1252 encoding) string.
    /// </summary>
    static String readACharLengthBytes(BinaryReader reader) throws Exception
    {
        int lengthBytes = reader.readInt32();
        return readAChar(reader, lengthBytes);
    }

    /// <summary>
    /// Reds the specified number of characters in unicode or in 1252 encoding.
    /// </summary>
    /// <param name="reader">Read can be in any encoding.</param>
    /// <param name="isUnicode"></param>
    /// <param name="count"></param>
    /// <returns></returns>
    static char[] readChars(BinaryReader reader, boolean isUnicode, int count) throws Exception
    {
        byte[] bytes = reader.readBytes((isUnicode) ? count * 2 : count);
        return DocStringUtil.getEncoding(isUnicode).getChars(bytes);
    }

    /// <summary>
    /// Reads ANSI string encoded with given code page.
    /// </summary>
    /// <param name="reader"></param>
    /// <param name="length">Lenght of string including trailing terminator.</param>
    /// <param name="codePage"></param>
    /// <returns></returns>
    static String readAnsiString(BinaryReader reader, int length, int codePage) throws Exception
    {
        byte[] data = reader.readBytes(length - 1);
        reader.readByte();
        return Encoding.getEncoding(codePage).getString(data);
    }

    /// <summary>
    /// Reads ANSI string encoded with given codepage and prefixed with 16-bit length.
    /// </summary>
    /// <param name="reader"></param>
    /// <param name="codePage"></param>
    /// <returns></returns>
    static String readAnsiString(BinaryReader reader, int codePage) throws Exception
    {
        return readAnsiString(reader, reader.readInt16(), codePage);
    }
}

