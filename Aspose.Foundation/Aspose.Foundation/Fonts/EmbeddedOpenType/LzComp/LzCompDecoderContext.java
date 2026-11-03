// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents context of the LZCOMP decoder.
/// </summary>
class LzCompDecoderContext
{
    /// <summary>
    /// Ctor.
    /// </summary>
    LzCompDecoderContext(int outputLength, BitBinaryIoBase bitIo)
    {
        mOutputLength = outputLength;

        initSymbolsCount();

        createDistanceCoder(bitIo);
        createLengthCoder(bitIo);
        createSymbolCoder(bitIo);

        initPreLoadData();
    }

    /// <summary>
    /// Initializes symbols count based on output length.
    /// </summary>
    private void initSymbolsCount()
    {
        int numberDistanceRanges = 1;
        mDistanceMax = (DISTANCE_MIN + (1 << (DISTANCE_WIDTH * numberDistanceRanges)) - 1);
        while (getDistanceMax() < mOutputLength)
        {
            numberDistanceRanges++;
            mDistanceMax = (DISTANCE_MIN + (1 << (DISTANCE_WIDTH * numberDistanceRanges)) - 1);
        }
        mDup2CommandSymbol = 256 + (1 << LENGHT_WIDTH) * numberDistanceRanges;
        mDup4CommandSymbol = mDup2CommandSymbol + 1;
        mDup6CommandSymbol = mDup4CommandSymbol + 1;
        mSymbolsCount = mDup6CommandSymbol + 1;
    }

    /// <summary>
    /// Creates distance coder.
    /// </summary>
    private void createDistanceCoder(BitBinaryIoBase bitIo)
    {
        mDistanceCoder = new AdaptiveHuffmanCodec((1 << DISTANCE_WIDTH), bitIo);

        for (int i = 0; i < 2; i++)
            for (int j = 0; j < (1 << DISTANCE_WIDTH); j++)
            {
                mDistanceCoder.increaseSymbolWeight(j);
            }
    }

    /// <summary>
    /// Creates length coder.
    /// </summary>
    private void createLengthCoder(BitBinaryIoBase bitIo)
    {
        mLengthCoder = new AdaptiveHuffmanCodec((1 << LENGHT_WIDTH), bitIo);

        for (int i = 0; i < 2; i++)
            for (int j = 0; j < (1 << LENGHT_WIDTH); j++)
            {
                mLengthCoder.increaseSymbolWeight(j);
            }
    }

    /// <summary>
    /// Creates symbol coder.
    /// </summary>
    private void createSymbolCoder(BitBinaryIoBase bitIo)
    {
        mSymbolCoder = new AdaptiveHuffmanCodec(mSymbolsCount, bitIo);

        mSymbolCoder.increaseSymbolWeight(256);
        mSymbolCoder.increaseSymbolWeight(257);

        for (int i = 0; i < 12; i++)
        {
            mSymbolCoder.increaseSymbolWeight(mDup2CommandSymbol);
        }

        for (int i = 0; i < 6; i++)
        {
            mSymbolCoder.increaseSymbolWeight(mDup4CommandSymbol);
        }
    }

    /// <summary>
    /// Initializes pre-load data.
    /// </summary>
    private void initPreLoadData()
    {
        mPreLoadData = new byte[PRE_LOAD_DATA_SIZE];
        int position = 0;

        for (byte i = (byte) 0; (i & 0xFF) < 32; i++)
            for (byte j = (byte) 0; (j & 0xFF) < 96; j++)
            {
                mPreLoadData[position++] = i;
                mPreLoadData[position++] = j;
            }

        for (int i = 0; i < 256; i++)
        {
            mPreLoadData[position++] = (byte)i;
            mPreLoadData[position++] = (byte)i;
            mPreLoadData[position++] = (byte)i;
            mPreLoadData[position++] = (byte)i;
        }
    }

    /// <summary>
    /// Symbols table of predefined values for hashes.
    /// </summary>
    byte[] getPreLoadData() { return mPreLoadData; }

    /// <summary>
    /// Distance coder.
    /// </summary>
    AdaptiveHuffmanCodec getDistanceCoder() { return mDistanceCoder; }

    /// <summary>
    /// Length coder.
    /// </summary>
    AdaptiveHuffmanCodec getLengthCoder() { return mLengthCoder; }

    /// <summary>
    /// Symbol coder.
    /// </summary>
    AdaptiveHuffmanCodec getSymbolCoder() { return mSymbolCoder; }

    /// <summary>
    /// DUP2 command symbol.
    /// </summary>
    int getDup2CommandSymbol() { return mDup2CommandSymbol; }

    /// <summary>
    /// DUP4 command symbol.
    /// </summary>
    int getDup4CommandSymbol() { return mDup4CommandSymbol; }

    /// <summary>
    /// DUP6 command symbol.
    /// </summary>
    int getDup6CommandSymbol() { return mDup6CommandSymbol; }

    /// <summary>
    /// Maximum allowed distance.
    /// </summary>
    long getDistanceMax() { return mDistanceMax; }

    /// <summary>
    /// Maximum allowed index of data elements.
    /// </summary>
    int getMaxIndex() { return mOutputLength + PRE_LOAD_DATA_SIZE; }

    private /*final*/ int mOutputLength;

    private AdaptiveHuffmanCodec mDistanceCoder;
    private AdaptiveHuffmanCodec mLengthCoder;
    private AdaptiveHuffmanCodec mSymbolCoder;

    private int mDup2CommandSymbol;
    private int mDup4CommandSymbol;
    private int mDup6CommandSymbol;
    private int mSymbolsCount;
    private byte[] mPreLoadData;

    private int mDistanceMax;

    /// <summary>
    /// Predefined by LZCOMP algorithm.
    /// </summary>
    static final int PRE_LOAD_DATA_SIZE = 2 * 32 * 96 + 4 * 256;
    static final int DISTANCE_WIDTH = 3;
    private static final int LENGHT_WIDTH = 3;
    private static final int DISTANCE_MIN = 1;
}

