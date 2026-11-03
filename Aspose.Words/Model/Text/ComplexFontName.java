// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/06/2013 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.IFontProvider;
import com.aspose.FontUtil;


/// <summary>
/// Implements union of font represented as string and font represented as ThemeFont.
/// </summary>
class ComplexFontName extends InternableComplexAttr implements IComplexAttr, Cloneable
{
    private ComplexFontName(String name)
    {
        if(!StringUtil.hasChars(name))
           throw new IllegalStateException("Empty name.") ;

        mName = name;
    }

    private ComplexFontName(/*ThemeFontCore*/int themeFontCore)
    {
        mThemeFontCore = themeFontCore;
    }

    /// <summary>
    /// Creates instance from its name.
    /// </summary>
    /// <param name="name">Font name. Can not be null.</param>
    static ComplexFontName fromName(String name)
    {
        // WORDSNET-23252 Seems Word always truncates too long font name.
        return new ComplexFontName(
            (name.length() > FontInfo.MAX_FONT_NAME_LENGTH)
                ? name.substring((0), (0) + (FontInfo.MAX_FONT_NAME_LENGTH))
                : name);
    }

    /// <summary>
    /// Creates instance from theme font name.
    /// </summary>
    static ComplexFontName fromTheme(/*ThemeFontCore*/int themeFont)
    {
        return new ComplexFontName(themeFont);
    }

    /// <summary>
    /// Creates instance from theme font and region mask.
    /// </summary>
    static ComplexFontName fromTheme(/*ThemeFont*/int themeFont, /*ThemeFontCore*/int regionMask)
    {
        return new ComplexFontName(toThemeFontCore(themeFont) | regionMask);
    }

    /// <summary>
    /// Returns resolved font name.
    /// </summary>
    String resolve(Theme theme)
    {
        return resolve(this, theme);
    }

    /// <summary>
    /// Returns resolved font name.
    /// </summary>
    static String resolve(Object fontNameObject, Theme theme)
    {
        return resolve(fontNameObject, theme, RunPr.getDefaultNameAscii());
    }

    /// <summary>
    /// Returns resolved font name.
    /// </summary>
    static String resolve(Object fontNameObject, Theme theme, String defaultFontName)
    {
        return resolve(fontNameObject, theme, defaultFontName, RunPr.getDefaultNameAscii());
    }

    /// <summary>
    /// Returns resolved font name.
    /// </summary>
    static String resolve(Object fontNameObject, Theme theme, String defaultFontName, String themeDefaultFontName)
    {
        if (fontNameObject == null)
            return null;

        ComplexFontName fontName = (ComplexFontName)fontNameObject;

        // Simple font name, return as is.
        if (!fontName.isThemeFont())
            return fontName.getName();

        // Theme font but theme is null, return default "Times New Roman".
        if (theme == null)
            return themeDefaultFontName;

        String resolvedName = ((IThemeProvider)theme).getFontName(fontName.getThemeFontCore());
        return StringUtil.hasChars(resolvedName) ? resolvedName : defaultFontName;
    }

    public /*override*/ boolean equals(Object obj)
    {
        return (obj instanceof ComplexFontName) && (equals(this, (ComplexFontName)obj));
    }

    public static boolean equals(ComplexFontName a, ComplexFontName b)
    {
        if (msObject.referenceEquals(a, b))
            return true;

        if (msObject.referenceEquals(null, a))
            return false;

        if (msObject.referenceEquals(null, b))
            return false;

        return msString.equals(a.mName, b.mName, StringComparison.ORDINAL_IGNORE_CASE) &&
               (a.mThemeFontCore == b.mThemeFontCore);
    }

    public /*override*/ int hashCode()
    {
        String name = mName != null
            ? msString.toLowerInvariant(mName)
            : null;
        return msString.format("{0}{1}", name, (int)mThemeFontCore).hashCode();
    }

    public /*override*/ String toString()
    {
        return isThemeFont() ? ThemeFontConverter.toString(mThemeFontCore) : mName;
    }

    public IComplexAttr deepCloneComplexAttr()
    {
        return (ComplexFontName)memberwiseClone();
    }

    /// <summary>
    /// Converts specified <see cref="ThemeFont"/> value to a corresponding <see cref="ThemeFontCore"/> group mask.
    /// </summary>
    private static /*ThemeFontCore*/int toThemeFontCore(/*ThemeFont*/int themeFont)
    {
        switch (themeFont)
        {
            case ThemeFont.MAJOR:
                return ThemeFontCore.MAJOR;

            case ThemeFont.MINOR:
                return ThemeFontCore.MINOR;

            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "themeFont");
        }
    }

    /// <summary>
    /// Converts specified <see cref="ThemeFontCore"/> value to a corresponding <see cref="ThemeFont"/>.
    /// </summary>
    private static /*ThemeFont*/int toThemeFont(/*ThemeFontCore*/int themeFontCore)
    {
        switch (themeFontCore)
        {
            case ThemeFontCore.MAJOR_ASCII:
            case ThemeFontCore.MAJOR_BIDI:
            case ThemeFontCore.MAJOR_EAST_ASIA:
            case ThemeFontCore.MAJOR_H_ANSI:
                return ThemeFont.MAJOR;

            case ThemeFontCore.MINOR_ASCII:
            case ThemeFontCore.MINOR_BIDI:
            case ThemeFontCore.MINOR_EAST_ASIA:
            case ThemeFontCore.MINOR_H_ANSI:
                return ThemeFont.MINOR;

            case ThemeFontCore.NONE:
                return ThemeFont.NONE;

            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "themeFontCore");
        }
    }

    String getName()
    {
        if(isThemeFont())
            throw new IllegalStateException("Inaccessible because font is defined by theme font.");
        return mName;
    }

    /// <summary>
    /// Gets Theme font name.
    /// </summary>
    /*ThemeFontCore*/int getThemeFontCore()
    {
        if(!isThemeFont())
            throw new IllegalStateException("Inaccessible because font is defined by font name.");
        return mThemeFontCore;
    }

    /// <summary>
    /// Gets Theme font name.
    /// </summary>
    /*ThemeFont*/int getThemeFont()
    {
        if (isThemeFont())
            return toThemeFont(getThemeFontCore());

        return ThemeFont.NONE;
    }

    boolean isThemeFont() { return (mThemeFontCore != ThemeFontCore.NONE) && !StringUtil.hasChars(mName); }

    /// <summary>
    /// Detects if a font is Asian or not.
    /// </summary>
    static boolean isAsianFont(IFontProvider fontProvider, ComplexFontName fontName) throws Exception
    {
        boolean result = false;

        if (fontName != null)
        {
            if (fontName.isThemeFont())
            {
                result = ((fontName.getThemeFontCore() & ThemeFontCore.REGION_MASK) == ThemeFontCore.EAST_ASIA);
            }
            else
            {
                result = FontUtil.isAsianFont(fontProvider, fontName.getName());
            }
        }

        return result;
    }

    public boolean isInheritedComplexAttr() { return false; }

    private /*final*/ String mName;
    private /*final*/ /*ThemeFontCore*/int mThemeFontCore = ThemeFontCore.NONE;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

