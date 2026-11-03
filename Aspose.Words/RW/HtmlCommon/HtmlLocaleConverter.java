// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/06/2021 by Artem Tsetkhalin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.StringUtil;
import com.aspose.LocaleConverter;
import com.aspose.Language;
import com.aspose.ms.System.msString;

/// <summary>
/// Converts locale IDs to and from language tags compliant with BCP 47.
/// </summary>
/// <remarks>
/// See https://www.rfc-editor.org/info/bcp47 and WORDSNET-21963
/// </remarks>
/*static*/ class HtmlLocaleConverter
{
	/* Simulation of static class by using private constructor */
	private HtmlLocaleConverter()
	{}

    /// <summary>
    /// Converts a locale ID to a language tag.
    /// </summary>
    /// <remarks>
    /// If this method cannot convert the locale ID to a language tag, it returns an empty string.
    /// </remarks>
    static String localeToTag(int localeId)
    {
        String tag = additionalLocaleToTag(localeId);
        if (StringUtil.hasChars(tag))
        {
            return tag;
        }

        tag = LocaleConverter.localeToDocxTag(localeId);
        return tag;
    }

    /// <summary>
    /// Converts a language tag to a locale ID.
    /// </summary>
    /// <returns>
    /// If the language tag is unknown, this method returns <see cref="Language.InvariantCulture"/>.
    /// </returns>
    static int tagToLocale(String tag)
    {
        // First, check if this is an additional language tag that we support.
        int localeId = additionalTagToLocale(tag);
        if (localeId != (int)Language.INVARIANT_CULTURE)
        {
            return localeId;
        }

        // Try to parse the value as a DOCX language tag.
        localeId = LocaleConverter.docxTagToLocale(tag);

        // If the value is not a valid DOCX language tag, try parse it as a WML tag. Note that MS Word writes WML language
        // tags to HTML documents it generates and this part allows us to correctly load language info from such documents.
        if (localeId == (int)Language.INVARIANT_CULTURE)
        {
            localeId = LocaleConverter.wmlTagToLocale(msString.toUpperInvariant(tag));
        }

        return localeId;
    }

    private static String additionalLocaleToTag(int localeId)
    {
        // We normally use DOCX language tags, because in most cases they confirm with BCP 47. However, there are some
        // non-compliant tags in the DOCX set that we have to correct manually.
        switch ((/*Language*/int)localeId)
        {
            case Language.SPANISH_SPAIN_TRADITIONAL_SORT:
                // We cannot preserve the sorting method in this case.
                return "es-ES";
            case Language.FRENCH_WEST_INDIES:
                return "fr-029";
            default:
                return "";
        }
    }

    private static int additionalTagToLocale(String tag)
    {
        // This method parses language tags that we support in addition to DOCX and WML language tags.
        /*Language*/int language;
        // Language tags are case-insensitive, see RFC 5646 section "2.1.1. Formatting of Language Tags".
        switch (gStringSwitchMap.of(msString.toLowerInvariant(tag)))
        {
            case /*"fr-029"*/0:
                language = Language.FRENCH_WEST_INDIES;
                break;
            default:
                language = Language.INVARIANT_CULTURE;
                break;
        }
        return (int)language;
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"fr-029"
	);

}

