// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/07/2020 by Anatoly Sidorenko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.EncoderFallback;
import com.aspose.ms.System.Text.EncoderFallbackBuffer;


/// <summary>
/// Provides a failure handling mechanism, called a fallback, 
/// for an input character that cannot be converted to an output byte sequence. 
/// The fallback uses a user-specified replacement string instead of the original input character. 
/// </summary>
/// <remarks>
/// If a Unicode code point cannot be represented using the selected encoding,
/// this class replaces the replacement string instead of the original input character.
/// </remarks>
class EncoderReplacementFallBack extends EncoderFallback
{
    EncoderReplacementFallBack()
    {
        setDefaultString("?");
        mBuffer = new EncoderReplacementFallBackBuffer(getDefaultString());
    }

    EncoderReplacementFallBack(String replacement)
    {
        setDefaultString(replacement);
        mBuffer = new EncoderReplacementFallBackBuffer(getDefaultString());
    }

    public String getDefaultString() { return mDefaultString; };
    private void setDefaultString(String value) { mDefaultString = value; };

    private String mDefaultString;

    public /*override*/ EncoderFallbackBuffer createFallbackBuffer()
    {
        return mBuffer;
    }

    public /*override*/ int getMaxCharCount() { return getDefaultString().length(); }

    private /*final*/ EncoderReplacementFallBackBuffer mBuffer;
}

