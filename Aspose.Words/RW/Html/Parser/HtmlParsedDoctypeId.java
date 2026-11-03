// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/05/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Utility class containing results of parsing ID string of a DOCTYPE token.
/// </summary>
class HtmlParsedDoctypeId
{
    /// <summary>
    /// Initializes a new instance of the class.
    /// </summary>
    /// <param name="value">Parsed ID string. Can be <c>null</c>.</param>
    /// <param name="correct">Value indicating whether any parsing errors occurred (<c>true</c> - no errors).</param>
    HtmlParsedDoctypeId(String value, boolean correct)
    {
        mValue = value;
        mCorrect = correct;
    }

    /// <summary>
    /// Gets the parsed ID string. Can be <c>null</c>.
    /// </summary>
    String getValue() { return mValue; }

    /// <summary>
    /// Gets a value indicating whether any parsing errors occurred (<c>true</c> - no errors).
    /// </summary>
    boolean getCorrect() { return mCorrect; }

    private /*final*/ String mValue;

    private /*final*/ boolean mCorrect;
}

