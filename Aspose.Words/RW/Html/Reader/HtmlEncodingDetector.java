// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/06/2023 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.IO.Stream;
import com.aspose.CustomTextReader;


/// <summary>
/// Detects encoding of HTML documents.
/// </summary>
/*static*/ class HtmlEncodingDetector
{
	/* Simulation of static class by using private constructor */
	private HtmlEncodingDetector()
	{}

    static Encoding detect(
        Encoding encoding,
        Stream htmlStream) throws Exception
    {
        if (encoding == null)
        {
            // WORDSNET-19885 FileFormatDetector has been replaced with HtmlFormatDetector to prevent
            // a wrong encoding result. We're now sure that the document is in the HTML format but FileFormatDetector
            // can detect it as something else and return a wrong encoding.
            FileFormatInfo fileFormatInfo;
            CustomTextReader textReader = new CustomTextReader(htmlStream, null);
            try
            {
                fileFormatInfo = HtmlFormatDetector.detect(textReader);
            }
            finally
            {
                textReader.rewindStream();
            }
            // WORDSNET-22633 Here we're not interested whether the format detector is sure about the format
            // of the document. We only want it to detect encoding. That's why we don't verify the returned load format.
            // For some documents the format detector may be unsure if the format of a document is HTML but still be able
            // to correctly detect encoding of that document.
            encoding = fileFormatInfo.getEncodingInternal();
        }

        // Perform encoding replacements to work around a common error where an ISO encoding is declared,
        // but a windows encoding is used in an HTML document. This replacement is required by the HTML 5 specification.
        encoding = replaceWithWindowsEncoding(encoding);

        return encoding;
    }

    /// <summary>
    /// Performs document encoding replacement as required by the HTML 5 specification.
    /// </summary>
    /// <remarks>
    /// The replacement helps to work around a common error where an ISO encoding is declared for an HTML document
    /// that in fact uses a windows encoding. ISO and windows encodings are almost equal, except for characters in range
    /// 0x80 - 0x9F, which are control characters in ISO encodings, but map to visible characters in windows encodings.
    /// The HTML 5 specification also requires some other encoding replacements, but at the moment we perform only those
    /// requested by customers.
    /// For details, see http://www.w3.org/TR/html5/syntax.html#character-encodings-0
    /// </remarks>
    private static Encoding replaceWithWindowsEncoding(Encoding encoding)
    {
        switch (gStringSwitchMap.of(encoding.getWebName()))
        {
            case /*"us-ascii"*/0:
            case /*"iso-8859-1"*/1:
                return Encoding.getEncoding("windows-1252");
            case /*"iso-8859-9"*/2:
                return Encoding.getEncoding("windows-1254");
            // WORDSJAVA-2606 - Java does not support circled characters (①, ②, etc.) in iso-2022-jp encoding.
            case /*"iso-2022-jp"*/3:
                return tryToReplaceEncoding(encoding, "x-windows-iso2022jp");
            default:
                return encoding;
        }
    }

    /// <summary>
    /// Currently, this method is only used in Java.
    /// </summary>
    private static Encoding tryToReplaceEncoding(Encoding inputEncoding, String desiredEncodingName)
    {
        Encoding desiredEncoding;
        try
        {
            desiredEncoding = Encoding.getEncoding(desiredEncodingName);
        }
        catch (Exception e)
        {
            return inputEncoding;
        }
        return desiredEncoding == Encoding.getUTF8() ? inputEncoding : desiredEncoding;
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"us-ascii",
		"iso-8859-1",
		"iso-8859-9",
		"iso-2022-jp"
	);

}

