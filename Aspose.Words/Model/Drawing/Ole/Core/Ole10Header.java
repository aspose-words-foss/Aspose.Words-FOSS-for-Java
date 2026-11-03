// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/04/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;


/// <summary>
/// We do not know exactly what this is, but it seems to occur at the beginning of 
/// NativeData of OLE10 objects.
/// </summary>
class Ole10Header
{
    Ole10Header(BinaryReader reader) throws Exception
    {
        TotalLength = reader.readInt32();
        Header1 = (reader.readUInt16() & 0xFFFF);
        if (Header1 == 0x0002)
        {
            DisplayName = readAsciizString(reader);

            FileName1 = readAsciizString(reader);
            
            // There should be 2bytes of 0. Skip it.
            reader.readUInt16();

            // Read second header, should be 03 00 or 01 00 (have no idea what does this header mean).
            Header2 = (reader.readUInt16() & 0xFFFF);

            switch (Header2)
            {
                    // WORDSNET-10087 Ole10NativeStream ends with filename2 if header2 is 0x0001, so there is no content.
                case 0x0001:
                {
                    // read 2 more bytes, which should be 01 00
                    reader.readUInt16();

                    // Read file name 2.
                    // I think it is ok to read it as a null-terminated string, instead of using the preceding length.
                    FileName2 = readAsciizString(reader);
                    reader.readUInt16();

                    break;
                }
                case 0x0003:
                {
                    // Read length of the filename that goes next.
                    reader.readInt32();

                    // Read file name 2.
                    // I think it is ok to read it as a null-terminated string, instead of using the preceding length.
                    FileName2 = readAsciizString(reader);

                    // Read file length.
                    ContentLength = reader.readInt32();
                    break;
                }
                default:
                {
                    // If we read unknown header, stop reading and write nothing into the output stream.
                    break;
                }
            }
        }
    }

    /// <summary>
    /// Read a null-terminated ASCII string.
    /// The reader must be using the ASCII encoding.
    /// </summary>
    private static String readAsciizString(BinaryReader reader) throws Exception
    {
        StringBuilder sb = new StringBuilder();

        while (true)
        {
            char ch = reader.readChar();

            if (ch == '\u0000')
                break;
            else
                sb.append(ch);
        }

        return sb.toString();
    }

    int TotalLength;
    int Header1;
    String DisplayName;
    String FileName1;
    int Header2;
    String FileName2;
    int ContentLength;
}

