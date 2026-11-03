// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/07/2019 by Alexander Sevidov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.msArray;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// [MS-OVBA] 2.4.1.1.7 TokenSequence
/// A TokenSequence is a FlagByte followed by an array of Tokens. The number of Tokens in the final 
/// TokenSequence MUST be greater than or equal to 1. The number of Tokens in the final 
/// TokenSequence MUST less than or equal to eight. All other TokenSequences in the 
/// CompressedChunkData MUST contain eight Tokens.
/// </summary>
class TokenSequence
{
    TokenSequence()
    {
        mFlagByte = (byte) 0x0;
        mCopyTokensCount = 0;
        mLiteralTokensCount = 0;
        mTokens = new ArrayList<RleToken>();
    }

    /// <summary>
    /// Count of tokens in the sequence.
    /// </summary>
    int getTokensCount() { return mTokens.size(); }

    /// <summary>
    /// Adds a token to the sequence.
    /// </summary>
    void addToken(RleToken token)
    {
        if (token.isLiteral())
        {
            addLiteralToken(token);
        }
        else
        {
            addCopyToken(token);
        }                
    }

    /// <summary>
    /// Returnes compressed data of the sequence.
    /// </summary>
    byte[] getData()
    {
        int size = mCopyTokensCount * BYTES_PER_COPY_TOKEN + mLiteralTokensCount + 1;
        byte[] data = new byte[size];

        data[0] = mFlagByte;
        int position = 1;

        for (RleToken token : mTokens)
        {
            msArray.copy(token.getBytes(), 0, data, position, token.getBytes().length);
            position += token.getBytes().length;
        }

        return data;
    }

    private void addCopyToken(RleToken token)
    {
        if (getTokensCount() >= MAX_TOKENS_PER_SEQUENCE)
            throw new IllegalArgumentException();

        mFlagByte |= (byte)(1 << mTokens.size());
        mCopyTokensCount++;
        msArrayList.add(mTokens, token);
    }

    private void addLiteralToken(RleToken token)
    {
        if (getTokensCount() >= MAX_TOKENS_PER_SEQUENCE)
            throw new IllegalArgumentException();

        mLiteralTokensCount++;
        msArrayList.add(mTokens, token);
    }

    private byte mFlagByte;
    private /*final*/ ArrayList<RleToken> mTokens;
    private int mCopyTokensCount;
    private int mLiteralTokensCount;

    private static final int MAX_TOKENS_PER_SEQUENCE = 8;
    private static final int BYTES_PER_COPY_TOKEN = 2;
}

