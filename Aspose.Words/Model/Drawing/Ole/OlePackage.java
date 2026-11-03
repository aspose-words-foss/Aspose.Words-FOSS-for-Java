// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/03/2014 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.StringUtil;
import com.aspose.StreamUtil;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.Text.Encoding;


/// <summary>
/// Allows to access OLE Package properties.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-ole-objects/">Working with Ole Objects</a> documentation article.</para>
/// </summary>
/// <remarks>
/// OLE package is a legacy and "undocumented" way to store embedded object if OLE handler is unknown.
/// Early Windows versions such as Windows 3.1, 95 and 98 had Packager.exe application which could be used to embed any type of data into document.
/// Now this application is excluded from Windows but MS Word and other applications still use it to embed data if OLE handler is missing or unknown.
/// </remarks>
public class OlePackage
{
    /// <summary>
    /// Gets or sets OLE Package file name.
    /// </summary>
    public String getFileName()
    {
        return mFileName;
    }
    public void setFileName(String value) throws Exception
    {
        if (value == null)
        {
            value = "";
        }
        if (!msString.equals(value, mFileName, StringComparison.ORDINAL))
        {
            mFileName = value;
            TempFileName = mFileName;
            updateOleObjectData();
        }
    }

    /// <summary>
    /// Gets or sets OLE Package display name.
    /// </summary>
    public String getDisplayName()
    {
        return mDisplayName;
    }
    public void setDisplayName(String value) throws Exception
    {
        if (value == null)
        {
            value = "";
        }
        if (!msString.equals(value, mDisplayName, StringComparison.ORDINAL))
        {
            mDisplayName = value;
            updateOleObjectData();
        }
    }

    OlePackage()
    {
    }

    /// <summary>
    /// Attaches OLE Package to parent OleObject.
    /// </summary>
    OlePackage(OleObject oleObject)
    {
        mOleObject = oleObject;
    }

    void read(BinaryReader reader) throws Exception
    {
        int header1 = (reader.readUInt16() & 0xFFFF);
        if (header1 != 0x0002)
            return;

        mDisplayName = readNullTerminatedString(reader);
        mFileName = readNullTerminatedString(reader);

        // There should be 2bytes of 0.
        reader.readUInt16(); // int zero =

        // Read second header, should be 03 00 or 01 00 (have no idea what does this header mean).
        int header2 = (reader.readUInt16() & 0xFFFF);

        switch (header2)
        {
            // WORDSNET-10087 Ole10NativeStream ends with filename2 if header2 is 0x0001, so there is no content.
            case 0x0001:
            {
                // read 2 more bytes, which should be 01 00
                reader.readUInt16(); // int bytes0001 =

                // Read file name 2.
                // I think it is ok to read it as a null-terminated string, instead of using the preceding length.
                TempFileName = readNullTerminatedString(reader);

                Data = null;

                break;
            }
            case 0x0003:
            {
                // Read length of the filename that goes next.
                reader.readInt32(); // int filenameLength =

                // Read file name 2.
                // I think it is ok to read it as a null-terminated string, instead of using the preceding length.
                TempFileName = readNullTerminatedString(reader);

                // Read file length.
                int dataLength = reader.readInt32();
                Data = reader.readBytes(dataLength);
                break;
            }
            default:
            {
                // If we read unknown header, stop reading and write nothing into the output stream.
                return;
            }
        }

        // WORDSNET-20288 Try to read Unicode properties.
        readUnicodeProperties(reader);
    }

    /// <summary>
    /// Read Unicode properties that may be present.
    /// </summary>
    private void readUnicodeProperties(BinaryReader reader) throws Exception
    {
        String unicodeString = readUnicodeProperty(reader);
        TempFileName = StringUtil.hasChars(unicodeString) ? unicodeString : TempFileName;

        unicodeString = readUnicodeProperty(reader);
        mDisplayName = StringUtil.hasChars(unicodeString) ? unicodeString : mDisplayName;

        unicodeString = readUnicodeProperty(reader);
        mFileName = StringUtil.hasChars(unicodeString) ? unicodeString : mFileName;
    }

    private static String readUnicodeProperty(BinaryReader reader) throws Exception
    {
        if (StreamUtil.hasEnoughBytesToRead(reader, 4))
        {
            int len = reader.readInt32();

            if (len > 0)
                return readLengthPrefixedUnicodeString(reader, len);
        }

        return "";
    }

    void write(BinaryWriter writer) throws Exception
    {
        // Header1
        writer.writeUInt16((/*ushort*/short)0x0002);

        writeNullTerminatedString(writer, mDisplayName);
        writeNullTerminatedString(writer, mFileName);

        writer.writeUInt16((/*ushort*/short)0x00);
        if ((Data != null) && (Data.length > 0))
        {
            // Header 2
            writer.writeUInt16((/*ushort*/short)0x0003);

            // Here goes temporary file name that was used by Packager for unknown reason.
            writer.writeInt32(TempFileName.length() + 1 /* include terminating zero */);
            writeNullTerminatedString(writer, TempFileName);

            writer.writeInt32(Data.length);
            writer.writeBytes(Data);

            // WORDSNET-20288 After 'reverse engineering' of the word files it was found was Word writes these values
            // in that order and by length prefixed unicode strings without terminating zero.
            // It's not documented, and may change in future.
            writeLengthPrefixedUnicodeString(writer, TempFileName);
            writeLengthPrefixedUnicodeString(writer, mDisplayName);
            writeLengthPrefixedUnicodeString(writer, mFileName);
        }
        else
        {
            // Header 2
            writer.writeUInt16((/*ushort*/short)0x0001);

            // Here goes temporary file name that was used by Packager for unknown reason.
            writer.writeUInt16((/*ushort*/short)1);
            writeNullTerminatedString(writer, TempFileName);
        }
    }

    boolean isLink() { return Data == null; }

    /// <summary>
    /// WORDSNET-15616 Provide ability to set file name and extension when inserting ole object using MemoryStream.
    /// Have to change properties on fly because Document.Сlone() clones only attributes
    /// and OleObject stores as attribute.
    /// </summary>
    private void updateOleObjectData() throws Exception
    {
        if (null != mOleObject)
        {
            // Make OLE Package object.
            MemoryStream packageStream = new MemoryStream();
            write(new BinaryWriter(packageStream));

            Ole10NativeStream ole10NativeStream = new Ole10NativeStream();
            ole10NativeStream.setNativeData(packageStream.toArray());

            // Update parent MemoryStorage with new OLE1 Native stream.
            ole10NativeStream.write(mOleObject.getData());
        }
    }

    private static String readNullTerminatedString(BinaryReader reader) throws Exception
    {
        StringBuilder sb = new StringBuilder();

        byte[] bytes = new byte[1];

        while (true)
        {
            reader.read(bytes, 0, 1);

            char[] ch = Encoding.getEncoding(DEFAULT_CODE_PAGE).getChars(bytes);

            if (ch[0] == '\u0000')
                break;
            else
                sb.append(ch[0]);
        }

        return sb.toString();
    }

    private static void writeNullTerminatedString(BinaryWriter writer, String text) throws Exception
    {
        writer.writeBytes(Encoding.getEncoding(DEFAULT_CODE_PAGE).getBytes(text));
        writer.writeByte((byte)0);
    }

    private static void writeLengthPrefixedUnicodeString(BinaryWriter writer, String text) throws Exception
    {
        byte[] bytes = Encoding.getUnicode().getBytes(text);
        writer.writeInt32(text.length());
        writer.writeBytes(bytes);
    }

    private static String readLengthPrefixedUnicodeString(BinaryReader reader, int len) throws Exception
    {
        // Limit to reasonable length.
        len = Math.min(len, 0xffff);

        byte[] bytes = reader.readBytes(len * 2); //assume there is no 4bytes characters
        String result = Encoding.getUnicode().getString(bytes);
        return result;
    }

    String TempFileName;

    byte[] Data;

    static final int DEFAULT_CODE_PAGE = 1251;

    private String mFileName;

    private String mDisplayName;

    private /*final*/ OleObject mOleObject;
}

