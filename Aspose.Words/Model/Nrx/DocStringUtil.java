// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/02/2017 by Alexey Butalov


package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.Encoding;


/// <summary>
/// Utility methods for working with DOC strings.
/// </summary>
/*static*/ class DocStringUtil
{
	/* Simulation of static class by using private constructor */
	private DocStringUtil()
	{}

    /// <summary>
    /// Gets a Unicode or Windows-1252 encoding that works well for apostrophes etc.
    /// </summary>
    static Encoding getEncoding(boolean isUnicode)
    {
        return (isUnicode) ? G_UNICODE_ENCODING : G_WIN_1252_ENCODING;
    }

    /// <summary>
    /// Returns the number of bytes needed to write a unicode string with a zero terminator.
    /// </summary>
    static int getWCharLengthBytes(String value)
    {
        return getWCharLengthChars(value) * 2;
    }

    /// <summary>
    /// Returns the number of characters in a unicode string plus one for zero terminator.
    /// </summary>
    static int getWCharLengthChars(String value)
    {
        return value.length() + 1;
    }

    private static /*final*/ Encoding G_UNICODE_ENCODING;
    private static /*final*/ Encoding G_WIN_1252_ENCODING = Encoding.getEncoding(1252);

    static/* DocStringUtil()*/
    {
        // WORDSNET-20508 Incomplete surrogate character. Lets start with very simple replacement fallback. 
        G_UNICODE_ENCODING = Encoding.getEncoding(1200,//"Unicode" 
            new EncoderReplacementFallBack(), new DecoderReplacementFallBack("\u0001"));
    }
}

