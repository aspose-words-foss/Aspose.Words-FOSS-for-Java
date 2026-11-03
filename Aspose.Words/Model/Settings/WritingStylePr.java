// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/05/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Defines the writing style you want Word to use to check grammar in this document 
/// (Spelling and Grammar option).
/// </summary>
class WritingStylePr implements Cloneable
{
    WritingStylePr deepClone()
    {
        return (WritingStylePr)memberwiseClone();
    }

    /// <summary>
    /// Gets or sets writing-style language as an ISO-6391-letter code or 4-digit hexadecimal code for a language.
    /// </summary>
    String Lang = null;

    /// <summary>
    /// Gets or sets writing-style DLL vendor ID.
    /// </summary>
    int VendorId = 0;

    /// <summary>
    /// Gets or sets writing-style DLL version.
    /// </summary>
    int DllVersion = 0;

    /// <summary>
    /// Specifies whether the DLL is NLCheck or not.
    /// </summary>
    boolean NlCheck = false;

    /// <summary>
    /// Gets or sets the rule set for the writing style.
    /// </summary>
    int OptionSet = 0;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

