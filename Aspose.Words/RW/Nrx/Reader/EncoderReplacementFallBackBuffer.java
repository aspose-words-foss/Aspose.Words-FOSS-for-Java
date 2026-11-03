// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/07/2020 by Anatoly Sidorenko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.EncoderFallbackBuffer;


/// <summary>
/// Represents a substitute input string that is used when the original input character cannot be encoded
/// </summary>
/// <remarks>
/// An instance of this class is created and used by an instance of the <see cref="Encoding"/> class.
/// For details see the MSDN document: http://msdn.microsoft.com/ru-ru/library/system.text.encoderfallbackbuffer.aspx
/// </remarks>
final class EncoderReplacementFallBackBuffer extends EncoderFallbackBuffer
{
    public EncoderReplacementFallBackBuffer(String defaultString)
    {
        mBuffer = defaultString.toCharArray();
        mIndex = 0;
    }

    public /*override*/ boolean fallback(char charUnknown, int index)
    {
        mIndex = 0;
        return true;
    }

    public /*override*/ boolean fallback(char charUnknownHigh, char charUnknownLow, int index)
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

