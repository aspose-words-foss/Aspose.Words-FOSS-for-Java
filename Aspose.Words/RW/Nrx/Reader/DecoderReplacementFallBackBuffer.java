// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/07/2020 by Anatoly Sidorenko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.DecoderFallbackBuffer;


/// <summary>
/// Represents a substitute output string that is emitted when the original input byte sequence cannot be decoded.
/// </summary>
/// <remarks>
/// An instance of this class is created and used by an instance of the <see cref="Encoding"/> class.
/// For details see the MSDN document: http://msdn.microsoft.com/ru-ru/library/system.text.encoderfallbackbuffer.aspx
/// </remarks>
final class DecoderReplacementFallBackBuffer extends DecoderFallbackBuffer
{
    public DecoderReplacementFallBackBuffer(String defaultString)
    {
        mBuffer = defaultString.toCharArray();
        mIndex = 0;
    }

    public /*override*/ boolean fallback(byte[] bytesUnknown, int index)
    {
        mIndex = 0;
        return true;
    }

    public /*override*/ char getNextChar()
    {
        if (mIndex < mBuffer.length)
            return mBuffer[mIndex++];
        else
            return '\u0000';
    }

    public /*override*/ boolean movePrevious()
    {
        return false;
    }

    public /*override*/ int getRemaining() { return mBuffer.length - mIndex; }

    private /*final*/ char[] mBuffer;
    private int mIndex;
}

