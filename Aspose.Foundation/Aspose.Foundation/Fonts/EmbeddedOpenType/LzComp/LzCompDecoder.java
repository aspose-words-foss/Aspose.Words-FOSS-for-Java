// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.msArray;


/// <summary>
/// Allows to decompress data using LZCOMP algorithm.
/// </summary>
/// <remarks>
/// This code is ported and refactored version of C code.
/// Original C code is available at http://www.w3.org/Submission/2008/SUBM-MTX-20080305/#Sample
/// or http://code.google.com/p/ttf2eot/source/browse/trunk/ (files lzcomp.c and lzcomp.h).
/// All constant values are taken from C code and altering them may lead to incorrect decoding result.
/// </remarks>
/*static*/ class LzCompDecoder
{
	/* Simulation of static class by using private constructor */
	private LzCompDecoder()
	{}

    /// <summary>
    /// Unpacks data.
    /// </summary>
    /// <param name="data">Compressed data.</param>
    /// <returns>Decompressed data.</returns>
    static byte[] unpackData(byte[] data) throws Exception
    {
        // kvk: C code from http://code.google.com/p/ttf2eot/source/browse/trunk/ always uses Version0
        // for compression and decompression. I have no idea where this value should be taken from.
        return unpackData(data, LzCompVersion.VERSION_0);
    }

    /// <summary>
    /// Unpacks data.
    /// </summary>
    /// <param name="data">Compressed data.</param>
    /// <param name="version">Codec version.</param>
    /// <returns>Decompressed data.</returns>
    private static byte[] unpackData(byte[] data, /*LzCompVersion*/int version) throws Exception
    {
        if (data == null || data.length == 0)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "data");

        MemoryStream stream = new MemoryStream(data);
        try /*JAVA: was using*/
        {
            BitBinaryReader reader = new BitBinaryReader(stream, true);

            boolean rleUsed = false;
            if (version == LzCompVersion.VERSION_0)
                rleUsed = reader.readBit();

            int outputLength = (int)reader.readUnsignedValue(24);

            byte[] decodedLzComp = decodeLzComp(reader, outputLength);

            if (rleUsed)
                return RleCoder.decode(decodedLzComp);
            else
                return decodedLzComp;
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Decodes data from <paramref name="reader"/>.
    /// </summary>
    private static byte[] decodeLzComp(BitBinaryReader reader, int outputLength) throws Exception
    {
        if (outputLength == 0)
            return ArrayUtil.EMPTY_BYTE_ARRAY;

        MemoryStream outputStream = new MemoryStream(outputLength);
        try /*JAVA: was using*/
        {
            LzCompDecoderContext context = new LzCompDecoderContext(outputLength, reader);

            while (outputStream.getPosition() < outputLength)
            {
                int symbol = context.getSymbolCoder().readSymbolAndUpdateTree();

                if (symbol < 256)
                {
                    // Literal item.
                    outputStream.writeByte((byte)symbol);
                }
                else if (symbol == context.getDup2CommandSymbol())
                {
                    // One byte copy item.
                    byte copyItem = getOneByteCopyItem(context, outputStream, 2);
                    outputStream.writeByte(copyItem);
                }
                else if (symbol == context.getDup4CommandSymbol())
                {
                    // One byte copy item.
                    byte copyItem = getOneByteCopyItem(context, outputStream, 4);
                    outputStream.writeByte(copyItem);
                }
                else if (symbol == context.getDup6CommandSymbol())
                {
                    // One byte copy item.
                    byte copyItem = getOneByteCopyItem(context, outputStream, 6);
                    outputStream.writeByte(copyItem);
                }
                else
                {
                    // Multi byte copy item.
                    int length = decodeLength(context, symbol);
                    int distance = decodeDistance(context, symbol);
                    if (distance >= 512)
                        length++;
                    byte[] copyItem = getCopyItem(context, outputStream, distance + length - 1, length);
                    outputStream.write(copyItem, 0, copyItem.length);
                }
            }
            return outputStream.toArray();
        }
        finally { if (outputStream != null) outputStream.close(); }
    }

    /// <summary>
    /// Reads one byte copy item from stream. Stream position is not modified.
    /// </summary>
    /// <param name="context">Decoder context.</param>
    /// <param name="stream">Stream.</param>
    /// <param name="offset">Offset of the copy item from current stream position.</param>
    /// <returns>Copy item.</returns>
    private static byte getOneByteCopyItem(LzCompDecoderContext context, Stream stream, int offset) throws Exception
    {
        byte[] copyItem = getCopyItem(context, stream, offset, 1);
        return copyItem[0];
    }

    /// <summary>
    /// Reads copy item from stream. Stream position is not modified.
    /// </summary>
    /// <param name="context">Decoder context.</param>
    /// <param name="stream">Stream.</param>
    /// <param name="offset">Offset of the beginning of copy item from current stream position.</param>
    /// <param name="length">Length of copy item.</param>
    /// <returns>Copy item.</returns>
    private static byte[] getCopyItem(LzCompDecoderContext context, Stream stream, int offset, int length) throws Exception
    {
        // Copy item start position should not exceeds PreLoadData.
        if (stream.getPosition() - offset < -LzCompDecoderContext.PRE_LOAD_DATA_SIZE)
            throw new IllegalStateException("Invalid copy item offset.");

        // Copy item start position should not be greater than offset.
        if (length > offset)
            throw new IllegalStateException("Invalid copy item length.");

        byte[] copyItem = new byte[length];

        long startPosition = stream.getPosition() - offset;

        // If copy item position exceeds the stream we should read bytes from the end of mPreLoadData array.
        if (startPosition < 0)
        {
            long startPositionInPreLoadData = LzCompDecoderContext.PRE_LOAD_DATA_SIZE + startPosition;
            long lenghtInPreLoadData = Math.min(LzCompDecoderContext.PRE_LOAD_DATA_SIZE - startPositionInPreLoadData, length);
            System.arraycopy(context.getPreLoadData(), (int)(startPositionInPreLoadData), copyItem, (int)(0), (int)(lenghtInPreLoadData));
        }

        if (startPosition + length > 0)
        {
            long startPositionInStream = Math.max(0, startPosition);
            int lenghtInStream = (int)(startPosition + length - startPositionInStream);
            byte[] dataInStream = readDataFromStremAndKeepPosition(stream, startPositionInStream, lenghtInStream);
            msArray.copy(dataInStream, 0, copyItem, length - lenghtInStream, lenghtInStream);
        }

        return copyItem;
    }

    private static byte[] readDataFromStremAndKeepPosition(Stream stream, long startPosition, int length) throws Exception
    {
        long originalPosition = stream.getPosition();

        byte[] data = new byte[length];
        stream.setPosition(startPosition);
        StreamUtil.read(stream, data, 0, length);

        stream.setPosition(originalPosition);

        return data;
    }


    private static int decodeLength(LzCompDecoderContext context, int symbol) throws Exception
    {
        final int STOP_BIT_MASK = 0x4;
        final int VALUE_MASK = 0x3;

        // Read values from reader using mLengthDecoder until stop bit found.
        // First value is (symbol - 256)%8.
        // First 2 bits of the value is next 2 bits of length.
        // 3rd bit is stop bit.
        int bits = (symbol - 256) % 8;
        int length = 0;
        while (true)
        {
            length = length << 2;
            length += (bits & VALUE_MASK);
            if ((bits & STOP_BIT_MASK) == 0)
                break;

            bits = context.getLengthCoder().readSymbolAndUpdateTree();
        }
        length += 2;

        return length;
    }

    private static int decodeDistance(LzCompDecoderContext context, int symbol) throws Exception
    {
        int numberDistanceRanges = (symbol - 256) / 8 + 1;

        int distance = 0;
        for (int i = 0; i < numberDistanceRanges; i++)
        {
            int bits = context.getDistanceCoder().readSymbolAndUpdateTree();
            distance = distance << 3;
            distance += bits;
        }
        distance++;

        return distance;
    }
}

