// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/06/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Corresponds to 5.1.4.1.16 font (Font)
/// </summary>
class ThemeSupplementalFont implements Cloneable
{
    ThemeSupplementalFont(String script, String typeface)
    {
        mScript = script;
        mTypeface = typeface;
    }

    /// <summary>
    /// Clones this instance of supplemental font.
    /// </summary>
    ThemeSupplementalFont deepClone()
    {
        return (ThemeSupplementalFont)memberwiseClone();
    }

    /// <summary>
    /// Can be null.
    /// Specifies the script, or language, in which the typeface is supposed to be used.
    /// Cannot set because this is used as a key in the collection of supplemental fonts.
    /// 
    /// I think the values here are from this table http://unicode.org/iso15924/iso15924-codes.html
    /// </summary>
    String getScript() { return mScript; }

    /// <summary>
    /// Can be null.
    /// Specifies the font face to use.
    /// </summary>
    String getTypeface() { return mTypeface; }
    void setTypeface(String value) { mTypeface = value; }

    private /*final*/ String mScript;
    private String mTypeface;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

