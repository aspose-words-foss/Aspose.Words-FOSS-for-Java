// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/12/2018 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


public /*static*/ class EmbeddedFontTestUtil
{
	/* Simulation of static class by using private constructor */
	private EmbeddedFontTestUtil()
	{}

    /// <summary>
    /// Checks that embedded fonts are absent for all fonts in <paramref name="fontInfoColection"/>.
    /// </summary>
    public static void checkEmbeddedFontsAreNull(FontInfoCollection fontInfoColection)
    {
        for (FontInfo fontInfo : fontInfoColection)
            checkEmbeddedFontsAreNull(fontInfo);
    }

    /// <summary>
    /// Checks that embedded fonts are absent for <paramref name="fontInfo"/>.
    /// </summary>
    public static void checkEmbeddedFontsAreNull(FontInfo fontInfo)
    {
        checkEmbeddedFontsAreNull(fontInfo, EmbeddedFontFormat.EMBEDDED_OPEN_TYPE);
        checkEmbeddedFontsAreNull(fontInfo, EmbeddedFontFormat.OPEN_TYPE);
    }

    /// <summary>
    /// Checks that embedded fonts of specified format are absent for <paramref name="fontInfo"/>.
    /// </summary>
    private static void checkEmbeddedFontsAreNull(FontInfo fontInfo, /*EmbeddedFontFormat*/int embeddedFontFormat)
    {
        msAssert.isNull(fontInfo.getEmbeddedFont(embeddedFontFormat, EmbeddedFontStyle.REGULAR));
        msAssert.isNull(fontInfo.getEmbeddedFont(embeddedFontFormat, EmbeddedFontStyle.BOLD));
        msAssert.isNull(fontInfo.getEmbeddedFont(embeddedFontFormat, EmbeddedFontStyle.ITALIC));
        msAssert.isNull(fontInfo.getEmbeddedFont(embeddedFontFormat, EmbeddedFontStyle.BOLD_ITALIC));
    }
}

