// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/06/2018 by Victor Moskvitin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.DecoderFallback;
import com.aspose.ms.System.Text.DecoderFallbackBuffer;


/// <summary>
/// A simple fallback testing that all characters can be decoded.
/// </summary>
class DecoderErrorFallBack extends DecoderFallback
{
    DecoderErrorFallBack()
    {
        buffer = new DecoderErrorFallbackBuffer();
    }

    public /*override*/ DecoderFallbackBuffer createFallbackBuffer()
    {
        return buffer;
    }

    public /*override*/ int getMaxCharCount() { return 0; }

    /// <summary>
    /// Whether an error has occurred during decoding process or not.
    /// </summary>
    boolean isError() { return buffer.isError(); }

    private /*final*/ DecoderErrorFallbackBuffer buffer;
}

