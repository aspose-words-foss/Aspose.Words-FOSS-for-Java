// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 28/07/2010 by Roman Korchagin

package com.aspose;


/**
 * Port this class manually to Java.
 * .
 * This build has no rendering, so nothing here creates or caches native java.awt fonts any more.
 * What is left is the font name heuristic the font metrics code relies on.
 */
public /*static*/ class FontPal
{
    /* Simulation of static class by using private constructor */
    private FontPal()
    {}

    public static boolean isWindowsFont(TTFont font)
    {
        if (font == null)
            return true;

        String fontName = font.getFamilyName();
        return fontName.startsWith("Adobe") || fontName.startsWith("Arial")
                || fontName.startsWith("Times") || fontName.startsWith("Microsoft")
                || fontName.startsWith("Tahoma") || fontName.startsWith("Courier")
                || fontName.startsWith("Comic") || fontName.startsWith("Verdana")
                || fontName.startsWith("Webdings") || fontName.startsWith("Calibri")
                || fontName.startsWith("Cambria") || fontName.startsWith("Comic")
                || fontName.startsWith("Georgia") || fontName.startsWith("Segoe")
                || fontName.startsWith("FangSong") || fontName.startsWith("SimSun")
                || fontName.startsWith("Symbol") || fontName.startsWith("Trebuchet")
                || fontName.startsWith("Yu") || fontName.contains("MS");
    }
}
