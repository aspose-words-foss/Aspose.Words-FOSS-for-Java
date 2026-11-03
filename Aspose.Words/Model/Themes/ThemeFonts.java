// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/06/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.HashMap;
import java.util.Map;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;


/// <summary>
/// Represents a collection of fonts in the font scheme, allowing to specify different fonts for different languages <see cref="Latin"/>, <see cref="EastAsian"/> and <see cref="ComplexScript"/>.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-styles-and-themes/">Working with Styles and Themes</a> documentation article.</para>
/// </summary>
/// <dev>
/// Corresponds to 5.1.4.1.24 majorFont (Major Font) or 5.1.4.1.25 minorFont (Minor fonts).
/// </dev>
public class ThemeFonts implements Cloneable
{
    ThemeFonts()
    {
    }

    /// <summary>
    /// Clones this instance of theme fonts.
    /// </summary>
    ThemeFonts deepClone()
    {
        ThemeFonts lhs = (ThemeFonts)memberwiseClone();

        if (ComplexScriptFontInfo != null)
            lhs.ComplexScriptFontInfo = ComplexScriptFontInfo.deepClone();

        if (EastAsianFontInfo != null)
            lhs.EastAsianFontInfo = EastAsianFontInfo.deepClone();

        if (LatinFontInfo != null)
            lhs.LatinFontInfo = LatinFontInfo.deepClone();

        lhs.mSupplementalFonts = new HashMap<String, ThemeSupplementalFont>();
        for (Map.Entry<String, ThemeSupplementalFont> entry : mSupplementalFonts.entrySet())
            msDictionary.add(lhs.mSupplementalFonts, entry.getKey(), entry.getValue().deepClone());

        return lhs;
    }

    /// <summary>
    /// Specifies font name for Latin characters.
    /// </summary>
    public String getLatin() { return LatinFontInfo != null ? LatinFontInfo.getName() : ""; }
    public void setLatin(String value)
    {
        if (msString.equals(value, getLatin()))
            return;

        LatinFontInfo = StringUtil.hasChars(value) ? new FontInfo(value) : null;
        mIsModified = true;
    }

    /// <summary>
    /// Specifies font name for EastAsian characters.
    /// </summary>
    public String getEastAsian() { return EastAsianFontInfo != null ? EastAsianFontInfo.getName() : ""; }
    public void setEastAsian(String value)
    {
        if (msString.equals(value, getEastAsian()))
            return;

        EastAsianFontInfo = StringUtil.hasChars(value) ? new FontInfo(value) : null; 
        mIsModified = true;
    }

    /// <summary>
    /// Specifies font name for ComplexScript characters.
    /// </summary>
    public String getComplexScript() { return ComplexScriptFontInfo != null ? ComplexScriptFontInfo.getName() : ""; }
    public void setComplexScript(String value)
    {
        if (msString.equals(value, getComplexScript()))
            return;

        ComplexScriptFontInfo = StringUtil.hasChars(value) ? new FontInfo(value) : null;
        mIsModified = true;
    }

    /// <summary>
    /// Can be null.
    /// </summary>
    FontInfo ComplexScriptFontInfo;
    /// <summary>
    /// Can be null.
    /// </summary>
    FontInfo EastAsianFontInfo;
    /// <summary>
    /// Can be null.
    /// </summary>
    FontInfo LatinFontInfo;

    /// <summary>
    /// Key is a string value of <see cref="ThemeSupplementalFont.Script"/>, 
    /// value is a <see cref="ThemeSupplementalFont"/> object.
    /// </summary>
    Map<String, ThemeSupplementalFont> getSupplementalFonts() { return mSupplementalFonts; }

    /// <summary>
    /// Indicates that the object was modified by public accessors.
    /// </summary>
    boolean isModified() { return mIsModified; }

    private boolean mIsModified;

    private HashMap<String, ThemeSupplementalFont> mSupplementalFonts = 
        new HashMap<String, ThemeSupplementalFont>();

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

