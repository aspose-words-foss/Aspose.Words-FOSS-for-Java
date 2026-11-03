// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/07/2016 by Edward Voronov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;


/// <summary>
/// Converts a numbers to an ordinal numbers.
/// </summary>
/*static*/ class OrdinalNumber
{
	/* Simulation of static class by using private constructor */
	private OrdinalNumber()
	{}

    /// <summary>
    /// Returns an ordinal number (1st, 2nd, 3rd, ...).
    /// </summary>
    static String toOrdinalNumber(long value)
    {
        return msString.format("{0}{1}", value, englishOrdinalSuffix(value));
    }

    /// <summary>
    /// Returns an ordinal number considering localization. (1st, 2nd, 3rd, or 1-й, 2-й, 3-й...).
    /// </summary>
    static String toLocalizedOrdinalNumber(long value, int localeId)
    {
        return msString.format("{0}{1}", value, localizedOrdinalSuffix(value, localeId));
    }

    /// <summary>
    /// Gets ordinal suffix based on localeId.
    /// </summary>
    /// <param name="value">The value to convert.</param>
    /// <param name="localeId">Locale identifier (language).</param>
    private static String localizedOrdinalSuffix(long value, int localeId)
    {
        /*Language*/int language = (/*Language*/int)localeId;
        /*OrdinalGroup*/int ordinalGroup = getOrdinalGroup(language);
        switch (ordinalGroup)
        {
            case OrdinalGroup.ENGLISH:
                return englishOrdinalSuffix(value);
            case OrdinalGroup.EAST_EUROPE:
                return ".";
            case OrdinalGroup.WEST_EUROPE:
                return "º";
            case OrdinalGroup.CENTER_EUROPE:
                return centerEuropeOrdinalSuffix(value, language);
            case OrdinalGroup.RUSSIAN:
                return "-й";
            case OrdinalGroup.ITALIAN:
                return "°";
            case OrdinalGroup.GREEK:
                return "ο";
            case OrdinalGroup.DANISH:
                return danishOrdinalSuffix(value);
            case OrdinalGroup.SWEDISH:
                return swedishOrdinalSuffix(value);
            case OrdinalGroup.CATALAN:
                return catalanOrdinalSuffix(value);
            default:
                throw new IllegalArgumentException();
        }
    }

    private static String catalanOrdinalSuffix(long value)
    {
        switch ((int)Math.abs(value))
        {
            case 1:
            case 3:
                return "r";
            case 2:
                return "n";
            case 4:
                return "t";
            case 14:
                return "èh";
            default:
                return "è";
        }
    }

    private static String swedishOrdinalSuffix(long value)
    {
        int twoDigit = (int)(Math.abs(value) % 100);
        if (twoDigit < 10 || twoDigit > 20)
        {
            switch (twoDigit % 10)
            {
                case 1:
                case 2:
                    return ":a";
                default:
                    // do nothing;
                    break;

            }
        }

        return ":e";
    }

    private static String danishOrdinalSuffix(long value)
    {
        if (value == 0)
            return "te";

        switch ((int)Math.abs(value) % 100)
        {
            case 0:
                return "ende";
            case 1:
                return "ste";
            case 2:
                return "nden";
            case 3:
                return "dje";
            case 4:
                return "rde";
            case 5:
            case 6:
            case 11:
            case 12:
            case 30:
                return "te";
            default:
                return "nde";
        }
    }

    private static String englishOrdinalSuffix(long value)
    {
        int twoDigits = (int)(Math.abs(value) % 100);
        if ((twoDigits < 4) || (twoDigits > 20))
        {
            switch (twoDigits % 10)
            {
                case 1:
                    return "st";
                case 2:
                    return "nd";
                case 3:
                    return "rd";
                default:
                    // do nothing;
                    break;
            }
        }

        return "th";
    }

    private static String centerEuropeOrdinalSuffix(long value, /*Language*/int language)
    {
        if (LanguageOnly.compare(LanguageOnly.DUTCH, (int)language))
            return "e";

        switch ((int)value)
        {
            case -1:
            case 1:
                return "er";
            case 0:
                return "";
            default:
                return "e";
        }
    }

    private static /*OrdinalGroup*/int getOrdinalGroup(/*Language*/int language)
    {
        switch (language)
        {
            case Language.CZECH:
            case Language.GERMAN:
            case Language.FINNISH:
            case Language.HUNGARIAN:
            case Language.NORWEGIAN:
            case Language.POLISH:
            case Language.CROATIAN:
            case Language.TURKISH:
            case Language.SLOVENIAN:
            case Language.ESTONIAN:
            case Language.BASQUE:
            case Language.CZECH_CZECH_REPUBLIC:
            case Language.GERMAN_GERMANY:
            case Language.FINNISH_FINLAND:
            case Language.HUNGARIAN_HUNGARY:
            case Language.NORWEGIAN_BOKMAL:
            case Language.POLISH_POLAND:
            case Language.CROATIAN_CROATIA:
            case Language.TURKISH_TURKEY:
            case Language.SLOVENIAN_SLOVENIA:
            case Language.ESTONIAN_ESTONIA:
            case Language.BASQUE_BASQUE:
            case Language.GERMAN_SWITZERLAND:
            case Language.NORWEGIAN_NYNORSK:
            case Language.SERBIAN_LATIN_SERBIA_AND_MONTENEGRO:
            case Language.GERMAN_AUSTRIA:
            case Language.SERBIAN_CYRILLIC_SERBIA_AND_MONTENEGRO:
            case Language.GERMAN_LUXEMBOURG:
            case Language.CROATIAN_BOZNIA_AND_HERZEGOVINA:
            case Language.GERMAN_LIECHTENSTEIN:
            case Language.BOSNIAN_LATIN:
            case Language.SERBIAN_LATIN_BOSNIA_AND_HERZEGOVINA:
            case Language.SERBIAN_CYRILLIC_BOSNIA_AND_HERZEGOVINA:
            case Language.BOSNIAN_CYRILLIC:
            case Language.SERBIAN:
                return OrdinalGroup.EAST_EUROPE;

            case Language.SPANISH:
            case Language.PORTUGUESE:
            case Language.SPANISH_SPAIN_TRADITIONAL_SORT:
            case Language.PORTUGUESE_BRAZIL:
            case Language.SPANISH_MEXICO:
            case Language.PORTUGUESE_PORTUGAL:
            case Language.SPANISH_SPAIN_MODERN_SORT:
            case Language.SPANISH_GUATEMALA:
            case Language.SPANISH_COSTA_RICA:
            case Language.SPANISH_PANAMA:
            case Language.SPANISH_DOMINICAN_REPUBLIC:
            case Language.SPANISH_VENEZUELA:
            case Language.SPANISH_COLOMBIA:
            case Language.SPANISH_PERU:
            case Language.SPANISH_ARGENTINA:
            case Language.SPANISH_ECUADOR:
            case Language.SPANISH_CHILE:
            case Language.SPANISH_URUGUAY:
            case Language.SPANISH_PARAGUAY:
            case Language.SPANISH_BOLIVIA:
            case Language.SPANISH_EL_SALVADOR:
            case Language.SPANISH_HONDURAS:
            case Language.SPANISH_NICARAGUA:
            case Language.SPANISH_PUERTO_RICO:
                return OrdinalGroup.WEST_EUROPE;

            case Language.FRENCH:
            case Language.DUTCH:
            case Language.FRENCH_FRANCE:
            case Language.DUTCH_NETHERLANDS:
            case Language.FRENCH_BELGIUM:
            case Language.DUTCH_BELGIUM:
            case Language.FRENCH_CANADA:
            case Language.FRENCH_SWITZERLAND:
            case Language.FRENCH_LUXEMBOURG:
            case Language.FRENCH_MONACO:
            case Language.FRENCH_WEST_INDIES:
            case Language.FRENCH_REUNION:
            case Language.FRENCH_CONGO:
            case Language.FRENCH_SENEGAL:
            case Language.FRENCH_CAMEROON:
            case Language.FRENCH_COTE_D_IVOIRE:
            case Language.FRENCH_MALI:
            case Language.FRENCH_MOROCCO:
            case Language.FRENCH_HAITI:
                return OrdinalGroup.CENTER_EUROPE;

            case Language.CATALAN:
            case Language.CATALAN_CATALAN:
                return OrdinalGroup.CATALAN;

            case Language.DANISH:
            case Language.DANISH_DENMARK:
                return OrdinalGroup.DANISH;

            case Language.GREEK:
            case Language.GREEK_GREECE:
                return OrdinalGroup.GREEK;

            case Language.ITALIAN:
            case Language.ITALIAN_ITALY:
            case Language.ITALIAN_SWITZERLAND:
                return OrdinalGroup.ITALIAN;

            case Language.RUSSIAN:
            case Language.RUSSIAN_RUSSIA:
            case Language.RUSSIAN_MOLDOVA:
                return OrdinalGroup.RUSSIAN;

            case Language.SWEDISH:
            case Language.SWEDISH_SWEDEN:
            case Language.SWEDISH_FINLAND:
                return OrdinalGroup.SWEDISH;

            default:
                return OrdinalGroup.ENGLISH;
        }
    }

    private /*enum*/ final class OrdinalGroup
    {
        private OrdinalGroup(){}
        
        public static final int ENGLISH = 0;
        public static final int EAST_EUROPE = 1;
        public static final int WEST_EUROPE = 2;
        public static final int CENTER_EUROPE = 3;
        public static final int RUSSIAN = 4;
        public static final int ITALIAN = 5;
        public static final int GREEK = 6;
        public static final int DANISH = 7;
        public static final int SWEDISH = 8;
        public static final int CATALAN = 9;

        public static final int length = 10;
    }
}

