// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/07/2020 by Anatoly Sidorenko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.DecoderFallback;
import com.aspose.ms.System.Text.DecoderFallbackBuffer;


/// <summary>
/// Provides a failure-handling mechanism, called a fallback,
/// for an encoded input byte sequence that cannot be converted to an output character.
/// The fallback emits a user-specified replacement string instead of a decoded input byte sequence.  
/// </summary>
/// <remarks>
/// If a Unicode code point cannot be represented using the selected encoding,
/// this class replaces the replacement string instead of a decoded input byte sequence.
/// </remarks>
class DecoderReplacementFallBack extends DecoderFallback
{
    DecoderReplacementFallBack()
    {
        setDefaultString("?");
        mBuffer = new DecoderReplacementFallBackBuffer(getDefaultString());
    }

    DecoderReplacementFallBack(String replacement)
    {
        setDefaultString(replacement);
        mBuffer = new DecoderReplacementFallBackBuffer(getDefaultString());
    }

    public String getDefaultString() { return mDefaultString; };
    private void setDefaultString(String value) { mDefaultString = value; };

    private String mDefaultString;

    public /*override*/ DecoderFallbackBuffer createFallbackBuffer()
    {
        return mBuffer;
    }

    public /*override*/ int getMaxCharCount() { return getDefaultString().length(); }

    private /*final*/ DecoderReplacementFallBackBuffer mBuffer;
}

