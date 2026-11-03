// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/07/2019 by Alexander Sevidov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.BitConverter;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.msMath;


class RleToken
{
    private RleToken(byte[] bytes, int length)
    {
        mBytes = bytes;
        mLength = length;
    }

    static RleToken literalToken(byte b)
    {
        byte[] bytes = new byte[1];
        bytes[0] = b;
        return new RleToken(bytes, 1);
    }

    /// <summary>
    /// [MS-OVBA] 2.4.1.3.19.3 Pack CopyToken.
    /// </summary>
    /// <remarks> 
    /// The comments above the code is a corresponding pseudocode from the specification.
    /// </remarks>
    static RleToken copyToken(int difference, int bestCandidate, int bestLength) throws Exception
    {
        // [MS-OVBA] 2.4.1.3.19.1 CopyToken Help
        int bitCount = getBitCount(difference);

        // SET MaximumLength TO (0xFFFF RIGHT SHIFT BY BitCount) PLUS 3
        /*ushort*/short maximumLength = (/*ushort*/short)((0xffff >> bitCount) + 3);

        // [MS-OVBA] 2.4.1.3.19.3 Pack CopyToken

        // SET Length TO the MINIMUM of BestLength and MaximumLength
        int length = Math.min(bestLength, (maximumLength & 0xFFFF));

        // SET Offset TO DecompressedCurrent MINUS BestCandidate
        /*ushort*/short offset = (/*ushort*/short)(difference - bestCandidate);

        //SET temp1 TO Offset MINUS 1
        /*ushort*/short temp1 = (/*ushort*/short)((offset & 0xFFFF) - 1);

        //SET temp2 TO 16 MINUS BitCount
        /*ushort*/short temp2 = (/*ushort*/short)(16 - bitCount);

        //SET temp3 TO Length MINUS 3
        /*ushort*/short temp3 = (/*ushort*/short)(length - 3);

        //SET Token TO (temp1 LEFT SHIFT BY temp2) BITWISE OR temp3
        /*ushort*/short packedToken = (/*ushort*/short)(((temp1 & 0xFFFF) << (temp2 & 0xFFFF)) | (temp3 & 0xFFFF));

        byte[] bytes = BitConverter.getBytes(packedToken);

        return new RleToken(bytes, length);
    }

    /// <summary>
    /// Decompresses copyToken and write it with using of <paramref name="writer"/>
    /// </summary>
    static void writeDecompressedToken(/*ushort*/short copyToken, int difference, BinaryWriter writer) throws Exception
    {
        int bitCount = getBitCount(difference);

        /*ushort*/short lengthMask = (/*ushort*/short)(0xffff >> bitCount);
        /*ushort*/short offsetMask = (/*ushort*/short)~(lengthMask & 0xFFFF);

        int length = ((copyToken & 0xFFFF) & (lengthMask & 0xFFFF)) + 3;
        int temp1 = (copyToken & 0xFFFF) & (offsetMask & 0xFFFF);
        int temp2 = 16 - bitCount;
        int offset = (temp1 >> temp2) + 1;

        int start = (int)writer.getBaseStream().getPosition() - offset;
        for (int j = 0; j < length; j++)
        {
            byte[] buffer = ((MemoryStream)writer.getBaseStream()).getBuffer();
            byte b = buffer[start + j];
            writer.writeByte(b);
        }
    }

    private static int getBitCount(int difference) throws Exception
    {
        // SET BitCount TO the smallest integer that is GREATER THAN OR EQUAL TO LOGARITHM base 2 
        // of difference
        int bitCount = (int)Math.ceil(msMath.log(difference, 2.0));

        // The number of bits used to encode Length MUST be greater than or equal to four. The 
        // number of bits used to encode Length MUST be less than or equal to 12
        // SET BitCount TO the maximum of BitCount and 4
        bitCount = Math.max(bitCount, 4);

        if (bitCount > 12)
            throw new IllegalArgumentException();

        return bitCount;
    }

    /// <summary>
    /// Compressed bytes of a token.
    /// </summary>
    byte[] getBytes() { return mBytes;  }

    /// <summary>
    /// Amount of bytes from uncompressed data.
    /// </summary>
    int getLength() { return mLength; }

    /// <summary>
    /// Returnes true if this token is a literal token.
    /// </summary>
    boolean isLiteral() { return (mBytes.length == 1); }

    private /*final*/ byte[] mBytes;
    private /*final*/ int mLength;
}

