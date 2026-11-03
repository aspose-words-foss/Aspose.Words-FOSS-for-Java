// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/07/2019 by Alexander Sevidov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.Debug;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;


/// <summary>
/// Implements RLE compression used in VBA projects.
/// See [MS-OVBA] 2.4.1 Compression and Decompression.
/// </summary>
class RleCompressor
{
    private RleCompressor(byte[] chunk, BinaryWriter writer)
    {
        mUncompressedChunk = chunk;
        mWriter = writer;
    }

    /// <summary>
    /// Implements RLE decompression used in VBA projects.
    /// See [MS-OVBA] 2.4.1 Compression and Decompression
    /// </summary>
    static void decompress(Stream inStream, MemoryStream outStream) throws Exception
    {
        BinaryReader reader = new BinaryReader(inStream);
        BinaryWriter writer = new BinaryWriter(outStream);

        byte signatureByte = reader.readByte();
        Debug.assertTrue(signatureByte == 1);

        while (reader.getBaseStream().getPosition() < reader.getBaseStream().getLength() - 1)
        {
            // Chunks array.
            int decompressedChunkStart = (int)writer.getBaseStream().getPosition();

            int chunkStart = (int)reader.getBaseStream().getPosition();

            int compressedHeader = (reader.readUInt16() & 0xFFFF);
            int compressedChunkSize = (compressedHeader & 0xfff) + 3;
            int compressedChunkSignature = (compressedHeader >> 12) & 0x7;
            Debug.assertTrue(compressedChunkSignature == 3);
            boolean compressedChunkFlag = (compressedHeader >> 15) != 0;

            if (compressedChunkFlag)
            {
                while (reader.getBaseStream().getPosition() < chunkStart + compressedChunkSize)
                {
                    // Tokens array.
                    byte flagByte = reader.readByte();
                    for (int i = 0; i < 8 && (reader.getBaseStream().getPosition() < chunkStart + compressedChunkSize); i++)
                    {
                        boolean literal = ((flagByte & 0xFF) & 1) == 0;
                        if (literal)
                        {
                            byte literalToken = reader.readByte();
                            writer.writeByte(literalToken);
                        }
                        else
                        {
                            /*ushort*/short copyToken = reader.readUInt16();
                            int difference = (int)writer.getBaseStream().getPosition() - decompressedChunkStart;

                            RleToken.writeDecompressedToken(copyToken, difference, writer);
                        }

                        flagByte = (byte)((flagByte & 0xFF) >>> 1);
                    }
                }
            }
            else
            {
                // So far seen only compressed chunks.
                throw new IllegalStateException();
            }
        }
    }

    /// <summary>
    /// [MS-OVBA] 2.4.1 Compression.
    /// </summary>
    static void compress(Stream inStream, MemoryStream outStream) throws Exception
    {
        BinaryWriter writer = new BinaryWriter(outStream);
        
        writer.writeByte(SIGNATURE_BYTE);

        BinaryReader reader = new BinaryReader(inStream);
        try /*JAVA: was using*/
        {
            while (reader.getBaseStream().getPosition() < reader.getBaseStream().getLength())
            {
                int bytesToRead = (int)(reader.getBaseStream().getLength() - reader.getBaseStream().getPosition());
                if (bytesToRead > MAX_BYTES_PER_CHUNK)
                    bytesToRead = MAX_BYTES_PER_CHUNK;

                byte[] decompressedChunk = reader.readBytes(bytesToRead);

                RleCompressor compressor = new RleCompressor(decompressedChunk, writer);
                compressor.writeCompressedChunk();
            }
        }
        finally { if (reader != null) reader.close(); }
    }

    /// <summary>
    /// [MS-OVBA] 2.4.1.3.7 Compressing a DecompressedChunk.
    /// </summary>
    private void writeCompressedChunk() throws Exception
    {
        readData();

        if (mCompressedSize >= MAX_BYTES_PER_CHUNK)
        {
            writeRawChunk();
        }
        else
        {
            writeCompressed();
        }
    }

    /// <summary>
    /// Reads data from uncompressed array and creates a list of TokenSequences 
    /// ([MS-OVBA]2.4.1.1.7 TokenSequence).
    /// </summary>
    private void readData() throws Exception
    {
        mTokenSequences.clear();

        mCompressedSize = 0;

        int position = 0;

        while ((position < mUncompressedChunk.length) && (mCompressedSize < MAX_BYTES_PER_CHUNK))
        {
            RleToken token = getToken(position);
            position += token.getLength();
            addToken(token);
            mCompressedSize += token.getBytes().length;
        }

        if (mSequence.getTokensCount() > 0)
        {
            msArrayList.add(mTokenSequences, mSequence);
            mCompressedSize++;
        }
    }

    private void addToken(RleToken token)
    {
        if (mSequence.getTokensCount() == MAX_TOKENS_PER_SEQUENCE)
        {
            msArrayList.add(mTokenSequences, mSequence);
            mSequence = new TokenSequence();
            mCompressedSize++;
        }

        mSequence.addToken(token);
    }

    /// <summary>
    /// [MS-OVBA] 2.4.1.3.9 Compressing a Token. 
    /// </summary>
    private RleToken getToken(int position) throws Exception
    {            
        // [MS-OVBA] 2.4.1.3.19.4 Matching
        int decompressedCurrent = position;
        int decompressedEnd = mUncompressedChunk.length;
        final int DECOMPRESSED_CHUNK_START = 0;

        int candidate = decompressedCurrent - 1;

        int bestLength = 0;
        int bestCandidate = 0;

        while (candidate >= DECOMPRESSED_CHUNK_START)
        {
            int c = candidate;
            int d = decompressedCurrent;
            int len = 0;

            while ((d < decompressedEnd) && (mUncompressedChunk[d] == mUncompressedChunk[c]))
            {
                len++;
                c++;
                d++;
            }

            if (len > bestLength)
            {
                bestLength = len;
                bestCandidate = candidate;
            }
            candidate--;
        }

        if (bestLength >= 3)
        {
            return RleToken.copyToken(position, bestCandidate, bestLength);
        }
        else 
        {
            return RleToken.literalToken(mUncompressedChunk[position]);
        }            
    }

    /// <summary>
    /// [MS-OVBA] 2.4.1.3.10 Compressing a RawChunk.
    /// </summary>
    private void writeRawChunk() throws Exception
    {
        /*ushort*/short header = (/*ushort*/short)(0x3000 | (MAX_BYTES_PER_CHUNK - 1));

        mWriter.writeUInt16(header);
        mWriter.writeBytes(mUncompressedChunk);

        int padLength = MAX_BYTES_PER_CHUNK - mUncompressedChunk.length;
        for (int i = 0; i < padLength; i++)
            mWriter.writeByte(PADDING_BYTE);
    }

    /// <summary>
    /// [MS-OVBA] 2.4.1.3.7 Compressing a DecompressedChunk.
    /// </summary>
    private void writeCompressed() throws Exception
    {
        /*ushort*/short header = (/*ushort*/short)(0xb000 | (mCompressedSize - 1));

        mWriter.writeUInt16(header);

        for (TokenSequence seq : mTokenSequences)
            mWriter.writeBytes(seq.getData());
    }

    private static final byte SIGNATURE_BYTE = (byte) 0x1;
    private static final int MAX_BYTES_PER_CHUNK = 4096;
    private static final int MAX_TOKENS_PER_SEQUENCE = 8;
    private static final byte PADDING_BYTE = (byte) 0x0;

    private /*final*/ byte[] mUncompressedChunk;
    private /*final*/ ArrayList<TokenSequence> mTokenSequences = new ArrayList<TokenSequence>();
    private TokenSequence mSequence = new TokenSequence();
    private /*final*/ BinaryWriter mWriter;
    private int mCompressedSize;
}

