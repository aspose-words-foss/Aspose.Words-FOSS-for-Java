// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/06/2018 by Victor Moskvitin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.DecoderFallbackBuffer;


/// <summary>
/// Encapsulates a property indicating that all characters can be decoded.
/// </summary>
/// <remarks>
/// An instance of this class is created and used by an instance of the <see cref="Encoding"/> class.
/// For details see the MSDN document: http://msdn.microsoft.com/ru-ru/library/system.text.encoderfallbackbuffer.aspx
/// </remarks>
class DecoderErrorFallbackBuffer extends DecoderFallbackBuffer
{
    public /*override*/ boolean fallback(byte[] bytesUnknown, int index)
    {
        // this method is executed only if error occurs.
        mIsError = true;
        return false;
    }

    public /*override*/ char getNextChar()
    {
        return '\u0000';
    }

    public /*override*/ boolean movePrevious()
    {
        return false;
    }

    public /*override*/ int getRemaining() { return 0; }

    /// <summary>
    /// Whether an error has occurred during decoding process or not.
    /// </summary>
    boolean isError() { return mIsError; }

    private boolean mIsError;
}

