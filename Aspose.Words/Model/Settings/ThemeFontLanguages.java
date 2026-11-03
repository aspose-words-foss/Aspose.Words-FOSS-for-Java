// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/07/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Language;

/// <summary>
/// Corresponds to 17.15.1.88 themeFontLang (Theme Font Languages).
/// </summary>
class ThemeFontLanguages implements Cloneable
{
    ThemeFontLanguages deepClone()
    {
        return (ThemeFontLanguages)memberwiseClone();
    }

    /*Language*/int Latin = Language.NO_PROOF;
    /*Language*/int EastAsia = Language.NO_PROOF;
    /*Language*/int Bidi = Language.NO_PROOF;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

