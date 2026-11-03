// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/04/2016 by Ilya Navrotskiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Class for reading/writing MTX special data type 255USHORT.
/// </summary>
/// <remarks>
/// See http://www.w3.org/Submission/2008/SUBM-MTX-20080305/#id_255USHORT and.
/// See http://www.w3.org/Submission/2008/SUBM-MTX-20080305/#glyfTable for more info.
/// </remarks>
class UShort255
{
    /// <summary>
    /// No ctor.
    /// </summary>
    private UShort255()
    {
    }

    /// <summary>
    /// Reads 255USHORT value from reader.
    /// </summary>
    static int read(BigEndianBinaryReader reader) throws Exception
    {
        short code = (short)(reader.readByte() & 0xFF);

        switch (code)
        {
            case WORD_CODE:
                return (reader.readUInt16() & 0xFFFF);
            case ONE_MORE_BYTE_CODE_1:
                return (reader.readByte() & 0xFF) + LOWEST_U_CODE;
            case ONE_MORE_BYTE_CODE_2:
                return (reader.readByte() & 0xFF) + LOWEST_U_CODE * 2;
            default:
                return code;
        }
    }

    /// <summary>
    /// Writes 255USHORT value to writer.
    /// </summary>
    static void write(int value, BigEndianBinaryWriter writer) throws Exception
    {
        if (value < LOWEST_U_CODE)
        {
            writer.writeByte((byte)value);
        }
        else if (value <= (LOWEST_U_CODE + 255))
        {
            writer.writeByte((byte)ONE_MORE_BYTE_CODE_1);
            writer.writeByte((byte)(value - LOWEST_U_CODE));
        }
        else if (value <= (LOWEST_U_CODE * 2 + 255))
        {
            writer.writeByte((byte)ONE_MORE_BYTE_CODE_2);
            writer.writeByte((byte)(value - LOWEST_U_CODE * 2));
        }
        else
        {
            writer.writeByte((byte)WORD_CODE);
            writer.writeUInt16(value);
        }
    }

    // Originally was byte but made short for java, since java's byte is signed so 253 is -3 etc.
    private static final short WORD_CODE = 253;
    private static final short ONE_MORE_BYTE_CODE_2 = 254;
    private static final short ONE_MORE_BYTE_CODE_1 = 255;
    private static final short LOWEST_U_CODE = 253;
}

