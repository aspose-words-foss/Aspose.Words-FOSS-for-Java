// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/12/2023 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.awt.Color;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Provides methods to be used when testing chart features.
/// </summary>
class TestChartUtil
{
    /// <summary>
    /// Sets the specified values to the properties of the <see cref="Font"/> instance.
    /// </summary>
    static void setFontProperties(Font font, String name, double size, double kerning, double spacing,
        boolean bold, boolean italic, boolean strikeThrough, boolean doubleStrikeThrough, boolean allCaps, boolean smallCaps,
        Color color, /*Underline*/int underline, /*RunVerticalAlignment*/int verticalAlignment, int localeId)
    {
        font.setName(name);
        font.setNameAscii(name);
        font.setNameBi(name);
        font.setNameFarEast(name);
        font.setNameOther(name);
        font.setSize(size);
        font.setKerning(kerning);
        font.setSpacing(spacing);
        font.setBold(bold);
        font.setItalic(italic);
        font.setStrikeThrough(strikeThrough);
        font.setDoubleStrikeThrough(doubleStrikeThrough);
        font.setAllCaps(allCaps);
        font.setSmallCaps(smallCaps);
        font.setColor(color);
        font.setUnderline(underline);

        if ((int)verticalAlignment >= 0)
            font.setVerticalAlignment(verticalAlignment);

        if (localeId > 0)
            font.setLocaleId(localeId);
    }

    /// <summary>
    /// Checks properties of the specified <see cref="Font"/> instance.
    /// </summary>
    static void checkFontProperties(Font font, String name, String nameBi, String nameFarEast,
        String nameOther, double size, double kerning, double spacing, boolean bold, boolean italic, boolean strikeThrough,
        boolean doubleStrikeThrough, boolean allCaps, boolean smallCaps, Color color, Color highlightColor,
        /*Underline*/int underline, /*RunVerticalAlignment*/int verticalAlignment, int localeId)
    {
        msAssert.areEqual(name, font.getName());
        msAssert.areEqual(name, font.getNameAscii());

        if (nameBi != null)
            msAssert.areEqual(nameBi, font.getNameBi());

        if (nameFarEast != null)
            msAssert.areEqual(nameFarEast, font.getNameFarEast());

        if (nameOther != null)
            msAssert.areEqual(nameOther, font.getNameOther());

        msAssert.areEqual(size, font.getSize());
        msAssert.areEqual(kerning, font.getKerning());
        msAssert.areEqual(spacing, font.getSpacing());
        msAssert.areEqual(bold, font.getBold());
        msAssert.areEqual(italic, font.getItalic());
        msAssert.areEqual(strikeThrough, font.getStrikeThrough());
        msAssert.areEqual(doubleStrikeThrough, font.getDoubleStrikeThrough());
        msAssert.areEqual(allCaps, font.getAllCaps());
        msAssert.areEqual(smallCaps, font.getSmallCaps());
        msAssert.areEqual(color, font.getColor());
        msAssert.areEqual(highlightColor, font.getHighlightColor());
        msAssert.areEqual(underline, font.getUnderline());
        msAssert.areEqual(verticalAlignment, font.getVerticalAlignment());

        if (localeId > 0)
            msAssert.areEqual(localeId, font.getLocaleId());
    }
}

