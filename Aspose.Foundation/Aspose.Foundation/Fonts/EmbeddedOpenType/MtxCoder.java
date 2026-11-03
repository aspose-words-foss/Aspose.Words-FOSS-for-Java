// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.MemoryStream;


/// <summary>
/// Allows coding with MicroType Express (MTX) Font Format.
/// </summary>
/// <remarks>
/// See http://www.w3.org/Submission/2008/SUBM-MTX-20080305 for more info.
/// </remarks>
/*static*/ class MtxCoder
{
	/* Simulation of static class by using private constructor */
	private MtxCoder()
	{}

    /// <summary>
    /// Encodes OpenType font to MTX.
    /// </summary>
    public static byte[] encode(byte[] openTypeFont) throws Exception
    {
        MemoryStream outStream = new MemoryStream();
        try /*JAVA: was using*/
        {
            BigEndianBinaryWriter writer = new BigEndianBinaryWriter(outStream);
            // Write MTX version.
            writer.writeByte((byte) 3);
            // Copy limit. This value is not used for a moment.
            writer.writeUInt24(0xffffff);

            CtfEncodedData ctfEncodedData = CtfEncoder.encode(openTypeFont);
            if (ctfEncodedData == null)
                return null;

            byte[] lzcompEncodedFontTables = LzCompEncoder.packData(ctfEncodedData.getFontTable());
            byte[] lzcompEncodedPushData = LzCompEncoder.packData(ctfEncodedData.getPushData());
            byte[] lzcompEncodedInstructions = LzCompEncoder.packData(ctfEncodedData.getInstructions());

            // MTX header is 10 bytes length.
            final int MTX_HEADER_LENGTH = 10;
            int pushDataOffset = MTX_HEADER_LENGTH + lzcompEncodedFontTables.length;
            int instructionsOffset = lzcompEncodedPushData.length + pushDataOffset;

            writer.writeUInt24(pushDataOffset);
            writer.writeUInt24(instructionsOffset);

            writer.writeBytes(lzcompEncodedFontTables);
            writer.writeBytes(lzcompEncodedPushData);
            writer.writeBytes(lzcompEncodedInstructions);

            return outStream.toArray();
        }
        finally { if (outStream != null) outStream.close(); }
    }

    /// <summary>
    /// Decodes MTX data.
    /// </summary>
    public static byte[] decode(byte[] data) throws Exception
    {
        MemoryStream stream = new MemoryStream(data);
        try /*JAVA: was using*/
        {
            BigEndianBinaryReader reader = new BigEndianBinaryReader(stream);

            short version = (short)(reader.readByte() & 0xFF);
            if (version != 3)
                throw new IllegalStateException("Unsupported MTX version.");

            reader.readUInt24();
            int pushDataOffset = reader.readUInt24();
            int instructionsOffset = reader.readUInt24();

            if (pushDataOffset > instructionsOffset || instructionsOffset >= data.length)
                throw new IllegalStateException("The MTX data is not valid.");

            byte[] lzcompEncodedFontTables = reader.readBytes(pushDataOffset - (int)stream.getPosition());
            byte[] lzcompEncodedPushData = reader.readBytes(instructionsOffset - (int)stream.getPosition());
            byte[] lzcompEncodedInstructions = reader.readBytes(data.length - (int)stream.getPosition());

            byte[] lzcompDecodedFontTables = LzCompDecoder.unpackData(lzcompEncodedFontTables);
            byte[] lzcompDecodedPushData = LzCompDecoder.unpackData(lzcompEncodedPushData);
            byte[] lzcompDecodedInstructions = LzCompDecoder.unpackData(lzcompEncodedInstructions);

            return CtfDecoder.decode(lzcompDecodedFontTables, lzcompDecodedPushData, lzcompDecodedInstructions);
        }
        finally { if (stream != null) stream.close(); }
    }
}

