// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/12/2010 by Andrey Soldatov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.Guid;
import com.aspose.RandomUtil;
import com.aspose.ArrayUtil;


/// <summary>
/// <para>Embedded fonts are saved in DOCX package in obfuscated form. This class helps to obfuscate them on saving and
/// de-obfuscate on loading.</para>
/// <para>For algorithm see ISO/IEC_29500-1_2008, 17.8.1 Font Embedding.</para>
/// <para>To write obfuscated font to file, call <see cref="WriteObfuscatedToStream"/>.</para>
/// <para>To de-obfuscate font read from file, call <see cref="DeObfuscate"/>.</para>
/// </summary>
/*static*/ class DocxFontObfuscator
{
	/* Simulation of static class by using private constructor */
	private DocxFontObfuscator()
	{}

    /// <summary>
    /// <para>Writes obfuscated font to stream.</para>
    /// </summary>
    /// <param name="embeddedFont">Written font.</param>
    /// <param name="outputStream">Stream to write obfuscated font.</param>
    static String writeObfuscatedToStream(EmbeddedFont embeddedFont, Stream outputStream) throws Exception
    {
        byte[] fontData = embeddedFont.getData();

        if (fontData == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "fontData");

        byte[] obfuscatedHead = new byte[Math.min(fontData.length, OBFUSCATED_DATA_LENGTH)];
        System.arraycopy((fontData), 0, (obfuscatedHead), 0, (obfuscatedHead.length));

        Guid guid = RandomUtil.newGuid(fontData);

        obfuscateOrDeobfuscateFontData(obfuscatedHead, guid);

        outputStream.write(obfuscatedHead, 0, obfuscatedHead.length);
        outputStream.write(fontData, obfuscatedHead.length, embeddedFont.getData().length - obfuscatedHead.length);
        
        return guid.toString("B").toUpperCase(); // "B" means "in braces"
    }

    /// <summary>
    /// De-obfuscates font read from DOCX package.
    /// </summary>
    /// <param name="fontData">Font data, obfuscated on input and de-obfuscated on output.</param>
    /// <param name="fontKey">Key in GUID format, read from fontKey attribute int fontTable.xml.</param>
    static void deObfuscate(byte[] fontData, String fontKey)
    {
        if (fontData == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "fontData");

        Guid guid = new Guid(fontKey);

        obfuscateOrDeobfuscateFontData(fontData, guid);
    }

    /// <summary>
    /// <para>Obfuscates or deobfuscate font data using obfuscation guid.</para>
    /// <para>The algorithm is described in ISO/IEC_29500-1_2008, 17.8.1 Font Embedding.</para>
    /// </summary>
    private static void obfuscateOrDeobfuscateFontData(byte[] fontData, Guid guid)
    {
        int dataPos = 0;
        byte[] guidBytes = ArrayUtil.guidToByteArray(guid);

        for (int pass = 0; pass < 2; pass++)
            for (int i = guidBytes.length - 1; (i >= 0) && (dataPos < fontData.length); i--)
                fontData[dataPos++] ^= guidBytes[i];
    }

    private static final int OBFUSCATED_DATA_LENGTH = 32;
}

