// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/06/2018 by Victor Moskvitin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.EncoderFallback;
import com.aspose.ms.System.Text.EncoderFallbackBuffer;


/// <summary>
/// A simple fallback testing that all characters can be correctly encoded
/// using the selected encoding.
/// </summary>
/// <remarks>
/// If a Unicode code point cannot be represented using the selected encoding,
/// this class sets a flag showing that encoding operation has failed.
/// </remarks>
class EncoderErrorFallBack extends EncoderFallback
{
    EncoderErrorFallBack()
    {
        buffer = new EncoderErrorFallBackBuffer();
    }

    public /*override*/ EncoderFallbackBuffer createFallbackBuffer()
    {
        return buffer;
    }

    public /*override*/ int getMaxCharCount() { return 0; }

    /// <summary>
    /// Whether an error has occurred during encoding process or not.
    /// </summary>
    boolean isError()
    {
        return buffer.isError();
    }

    private /*final*/ EncoderErrorFallBackBuffer buffer;
}

