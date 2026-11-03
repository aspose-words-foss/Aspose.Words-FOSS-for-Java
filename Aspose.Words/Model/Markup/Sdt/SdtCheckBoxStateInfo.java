// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/11/2011 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents state information for <see cref="SdtCheckBox" />.
/// </summary>
class SdtCheckBoxStateInfo implements Cloneable
{
    SdtCheckBoxStateInfo(String fontName, int characterCode)
    {
        mFontName = fontName;
        mCharacterCode = characterCode;
    }

    SdtCheckBoxStateInfo deepClone()
    {
        SdtCheckBoxStateInfo lhs = (SdtCheckBoxStateInfo)memberwiseClone();
        return lhs;
    }

    /// <summary>
    /// Font name used in checkbox for certain state.
    /// </summary>
    String getFontName() { return mFontName; }

    /// <summary>
    /// Character code used for certain state.
    /// </summary>
    int getCharacterCode() { return mCharacterCode; }

    /// <summary>
    /// Default checked state info when control created in Office2010.
    /// </summary>
    static /*final*/ SdtCheckBoxStateInfo DEFAULT_CHECKED_STATE_INFO = new SdtCheckBoxStateInfo("MS Gothic", 0x2612);
    /// <summary>
    /// Default unchecked state info when control created in Office2010.
    /// </summary>
    static /*final*/ SdtCheckBoxStateInfo DEFAULT_UNCHECKED_STATE_INFO = new SdtCheckBoxStateInfo("MS Gothic", 0x2610);

    private /*final*/ String mFontName;
    private /*final*/ int mCharacterCode;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

