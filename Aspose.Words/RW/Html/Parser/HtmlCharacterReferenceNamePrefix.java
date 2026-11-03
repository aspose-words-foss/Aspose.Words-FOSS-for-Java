// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/04/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// An auxiliary class used by the <see cref="HtmlCharacterReferenceNames"/> data structure.
/// </summary>
/// <remarks>
/// Every instance of this class represents a state of the finite-state automaton used by the data structure.
/// </remarks>
class HtmlCharacterReferenceNamePrefix
{
    /// <summary>
    /// The characters associated with this prefix (state).
    /// </summary>
    String getCharacters() { return mCharacters; }
    void setCharacters(String value)
    {
        Debug.assertTrue(value != null);
        mCharacters = value;
    }

    /// <summary>
    /// The flag indicating whether this prefix (state) is final.
    /// </summary>
    boolean isFinal() { return mIsFinal; }
    void isFinal(boolean value) { mIsFinal = value; }

    private String mCharacters = "";

    /// <summary>
    /// The flag indicating whether this prefix (state) is final.
    /// </summary>
    /// <remarks>
    /// All new prefixes (states) are initially considered final; it simplifies adding new prefixes (states)
    /// to the <see cref="HtmlCharacterReferenceNames"/> data structure.
    /// </remarks>
    private boolean mIsFinal = true;
}

