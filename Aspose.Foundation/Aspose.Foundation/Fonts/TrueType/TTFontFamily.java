// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/09/2005 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Drawing.FontStyle;


/// <summary>
/// Collection of true type fonts of the same family name.
/// </summary>
/// <remarks>
/// Instances of this class are immutable and therefore thread-safe.
/// </remarks>
final class TTFontFamily
{
    TTFontFamily(String familyName, Iterable<TTFont> fonts)
    {
        Debug.assertTrue(fonts != null);

        mFamilyName = familyName;
        mFonts = new IntToObjDictionary<TTFont>();
        for (TTFont font : fonts)
        {
            // We allow for font duplication in the source collection.
            mFonts.set((int)font.getStyle(), font);
        }
    }

    String getFamilyName() { return mFamilyName; }

    int getCount() { return mFonts.getCount(); }

    /// <summary>
    /// Gets a font by style.
    /// </summary>
    /// <param name="style">The font style.</param>
    /// <param name="isExactStyle">When true, returns null if a font file with the
    /// requested style is not found. When false, returns a most suitable font
    /// within the same family if the font with the requested style is not found.</param>
    /// <returns>Can return null if no suitable font was found.</returns>
    TTFont getFont(/*FontStyle*/int style, boolean isExactStyle)
    {
        //Try an exact match first.
        TTFont font = mFonts.get((int)style);
        if (font != null)
            return font;

        if (isExactStyle)
            return null;

        // Try a non underline font
        /*FontStyle*/int tryStyle = (style & (~FontStyle.UNDERLINE));
        font = mFonts.get((int)tryStyle);
        if (font != null)
            return font;

        // Try a non strikeout font
        tryStyle = (style & (~FontStyle.STRIKEOUT));
        font = mFonts.get((int)tryStyle);
        if (font != null)
            return font;

        //Try a non italic font.
        tryStyle = (style & (~FontStyle.ITALIC));
        font = mFonts.get((int)tryStyle);
        if (font != null)
            return font;

        //Try a non bold font. Need to try this last - more precedence since it is more important
        //to get wide characters than to get italic characters. Italic is easier to simulate.
        tryStyle = (style & (~FontStyle.BOLD));
        font = mFonts.get((int)tryStyle);
        if (font != null)
            return font;

        //Now try the regular font.
        tryStyle = FontStyle.REGULAR;
        font = mFonts.get((int)tryStyle);
        if (font != null)
            return font;

        // WORDSNET-24634 In case when regular style is not available MW seems to prefer Italic over Bold or BoldItalic.
        tryStyle = FontStyle.ITALIC;
        font = mFonts.get((int)tryStyle);
        if (font != null)
            return font;

        // WORDSNET-25845 In this case there is only bold styles in family. We should use BoldItalic for Italic instead of Bold.
        tryStyle = style | FontStyle.BOLD;
        font = mFonts.get((int)tryStyle);
        if (font != null)
            return font;

        //Finally, return any font.
        for (TTFont obj : mFonts.getValues())
            return obj;

        return null;
    }

    private /*final*/ String mFamilyName;

    /// <summary>
    /// Map of FontStyle to TTFont.
    /// </summary>
    /// <remarks>AS It looks like only Regular, Bold, Italic and Bold Italic should be used as keys.
    /// The other font style bits represent internal font information only.
    /// See http://http://www.microsoft.com/typography/otspec/name.htm: "Up to four fonts
    /// can share the Font Family name, forming a font style linking group (regular, italic, bold,
    /// bold italic - as defined by OS/2.fsSelection bit settings)".
    /// </remarks>
    private /*final*/ IntToObjDictionary<TTFont> mFonts;
}

