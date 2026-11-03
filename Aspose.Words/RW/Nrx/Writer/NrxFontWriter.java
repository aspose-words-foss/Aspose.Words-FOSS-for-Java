// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2019 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;


/// <summary>
/// Implements writing common elements related to font for both DOCX and WordML formats.
/// </summary>
/*static*/ class NrxFontWriter
{
	/* Simulation of static class by using private constructor */
	private NrxFontWriter()
	{}

    static void writeAltName(FontInfo fontInfo, NrxXmlBuilder builder, /*SaveFormat*/int sf)
    {
        Debug.assertTrue((sf == SaveFormat.DOCX) || (sf == SaveFormat.WORD_ML));

        if (StringUtil.hasChars(fontInfo.getAltName()))
        {
            // WORDSNET-19341 On Linux String.IndexOf() works slightly different and returns zero 
            // instead of negative value (like on Windows), use String.Contains() instead of IndexOf() 
            // to make it works on both Linux and Windows the same way.
            String altName = fontInfo.getAltName();
            if (altName.contains("\u0000"))
                altName = altName.substring((0), (0) + (msString.indexOf(altName, "\u0000", StringComparison.ORDINAL)));

            builder.writeVal("w:altName", altName);
        }
    }
}

