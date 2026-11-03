// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/06/2018 by Victor Moskvitin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.EncoderFallbackBuffer;


/// <summary>
/// Encapsulates a property indicating that all characters
/// can be correctly encoded using the selected encoding.
/// </summary>
/// <remarks>
/// An instance of this class is created and used by an instance of the <see cref="Encoding"/> class.
/// For details see the MSDN document: http://msdn.microsoft.com/ru-ru/library/system.text.encoderfallbackbuffer.aspx
/// </remarks>
class EncoderErrorFallBackBuffer extends EncoderFallbackBuffer
{
    public /*override*/ boolean fallback(char charUnknown, int index)
    {
        // this method is executed only if error occurs.
        mIsError = true;
        return false;
    }

    public /*override*/ boolean fallback(char charUnknownHigh, char charUnknownLow, int index)
    {
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
    /// Whether an error has occurred during encoding process or not.
    /// </summary>
    boolean isError() { return mIsError; }

    private boolean mIsError;
}

