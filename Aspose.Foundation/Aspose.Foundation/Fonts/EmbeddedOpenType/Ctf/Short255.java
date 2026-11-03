// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/04/2016 by Ilya Navrotskiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Class for reading/writing MTX special data type 255SHORT.
/// </summary>
/// <remarks>
/// See http://www.w3.org/Submission/2008/SUBM-MTX-20080305/#id_255SHORT and.
/// See http://www.w3.org/Submission/2008/SUBM-MTX-20080305/#glyfTable for more info.
/// </remarks>
/*static*/ class Short255
{
	/* Simulation of static class by using private constructor */
	private Short255()
	{}

    /// <summary>
    /// Reads 255SHORT value from reader.
    /// </summary>
    static short read(BigEndianBinaryReader reader) throws Exception
    {
        short value;
        short code = (short)(reader.readByte() & 0xFF);
        if (code == (WORD_CODE & 0xFF))
        {
            value = reader.readInt16();
        }
        else
        {
            short sign = 1;

            if (code == FLIP_SIGN_CODE)
            {
                sign = -1;
                code = (short)(reader.readByte() & 0xFF);
            }

            if (code == (ONE_MORE_BYTE_CODE_1 & 0xFF) || code == (ONE_MORE_BYTE_CODE_2 & 0xFF))
            {
                short byte2 = (short)(reader.readByte() & 0xFF);
                int multiplier = code == (ONE_MORE_BYTE_CODE_1 & 0xFF) ? 1 : 2;
                value = (short)(byte2 + LOWEST_CODE * multiplier);
            }
            else
            {
                value = code;
            }

            value *= sign;
        }

        return value;
    }

    /// <summary>
    /// Writes 255SHORT value to writer.
    /// </summary>
    static void write(short value, BigEndianBinaryWriter writer) throws Exception
    {
        // Note, that if value is less than (-1*LowestCode),
        // then we are forced to write that value using 3 bytes (FlipSignCode, OneMoreByteCode1/2 and coded value itself).
        // So in this case, seems MS Word simply writes WordCode and value 'as is', that is 3 bytes too.
        if ((Math.abs((int)value) > (LOWEST_CODE * 2 + 255)) || (value <= -LOWEST_CODE))
        {
            writer.writeByte(WORD_CODE);
            writer.writeInt16(value);
        }
        else
        {
            if (value < 0)
            {
                writer.writeByte((byte)FLIP_SIGN_CODE);
                value *= -1;
            }

            if (value < LOWEST_CODE)
            {
                writer.writeByte((byte)value);
            }
            else if (value <= (LOWEST_CODE + 255))
            {
                writer.writeByte(ONE_MORE_BYTE_CODE_1);
                writer.writeByte((byte)(value - LOWEST_CODE));
            }
            else
            {
                writer.writeByte(ONE_MORE_BYTE_CODE_2);
                writer.writeByte((byte)(value - LOWEST_CODE * 2));
            }
        }
    }

    private static final byte WORD_CODE = (byte) 253;
    private static final byte ONE_MORE_BYTE_CODE_2 = (byte) 254;
    private static final byte ONE_MORE_BYTE_CODE_1 = (byte) 255;

    private static final short FLIP_SIGN_CODE = 250;
    private static final short LOWEST_CODE = 250;
}

