// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/03/2017 by Denis Shvydkiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



public /*static*/ class LocaleClassifier
{
	/* Simulation of static class by using private constructor */
	private LocaleClassifier()
	{}

    /// <summary>
    /// Indicates whether the specified locale is Latin.
    /// </summary>
    public static boolean isLatin(int localeId)
    {
        switch ((/*Language*/int)localeId)
        {
            case Language.AFRIKAANS:
            case Language.AFRIKAANS_SOUTH_AFRICA:
            case Language.ALBANIAN:
            case Language.ALBANIAN_ALBANIA:
            case Language.ALSATIAN:
            case Language.CATALAN:
            case Language.CATALAN_CATALAN:
            case Language.BOSNIAN_LATIN:
            case Language.BRETON:
            case Language.CZECH:
            case Language.CZECH_CZECH_REPUBLIC:
            case Language.DANISH:
            case Language.DANISH_DENMARK:
            case Language.ENGLISH:
            case Language.ENGLISH_AUSTRALIA:
            case Language.ENGLISH_BELIZE:
            case Language.ENGLISH_CANADA:
            case Language.ENGLISH_CARIBBEAN:
            case Language.ENGLISH_HONG_KONG:
            case Language.ENGLISH_INDIA:
            case Language.ENGLISH_INDONESIA:
            case Language.ENGLISH_IRELAND:
            case Language.ENGLISH_JAMAICA:
            case Language.ENGLISH_MALAYSIA:
            case Language.ENGLISH_NEW_ZEALAND:
            case Language.ENGLISH_PHILIPPINES:
            case Language.ENGLISH_SINGAPORE:
            case Language.ENGLISH_SOUTH_AFRICA:
            case Language.ENGLISH_TRINIDAD_AND_TOBAGO:
            case Language.ENGLISH_UK:
            case Language.ENGLISH_US:
            case Language.ENGLISH_ZIMBABWE:
            case Language.FINNISH:
            case Language.FINNISH_FINLAND:
            case Language.FRENCH:
            case Language.FRENCH_BELGIUM:
            case Language.FRENCH_CAMEROON:
            case Language.FRENCH_CANADA:
            case Language.FRENCH_CONGO:
            case Language.FRENCH_COTE_D_IVOIRE:
            case Language.FRENCH_FRANCE:
            case Language.FRENCH_HAITI:
            case Language.FRENCH_LUXEMBOURG:
            case Language.FRENCH_MALI:
            case Language.FRENCH_MONACO:
            case Language.FRENCH_MOROCCO:
            case Language.FRENCH_REUNION:
            case Language.FRENCH_SENEGAL:
            case Language.FRENCH_SWITZERLAND:
            case Language.FRENCH_WEST_INDIES:
            case Language.GERMAN:
            case Language.GERMAN_AUSTRIA:
            case Language.GERMAN_GERMANY:
            case Language.GERMAN_LIECHTENSTEIN:
            case Language.GERMAN_LUXEMBOURG:
            case Language.GERMAN_SWITZERLAND:
            case Language.IGBO:
            case Language.LATIN:
            case Language.LATVIAN:
            case Language.LATVIAN_LATVIA:
            case Language.LITHUANIAN:
            case Language.LITHUANIAN_LITHUANIA:
            case Language.LUXEMBOUGISH_LUXEMBURG:
            case Language.POLISH:
            case Language.POLISH_POLAND:
            case Language.PORTUGUESE:
            case Language.PORTUGUESE_BRAZIL:
            case Language.PORTUGUESE_PORTUGAL:
            case Language.SPANISH:
            case Language.SPANISH_ARGENTINA:
            case Language.SPANISH_BOLIVIA:
            case Language.SPANISH_CHILE:
            case Language.SPANISH_COLOMBIA:
            case Language.SPANISH_COSTA_RICA:
            case Language.SPANISH_DOMINICAN_REPUBLIC:
            case Language.SPANISH_ECUADOR:
            case Language.SPANISH_EL_SALVADOR:
            case Language.SPANISH_GUATEMALA:
            case Language.SPANISH_HONDURAS:
            case Language.SPANISH_MEXICO:
            case Language.SPANISH_NICARAGUA:
            case Language.SPANISH_PANAMA:
            case Language.SPANISH_PARAGUAY:
            case Language.SPANISH_PERU:
            case Language.SPANISH_PUERTO_RICO:
            case Language.SPANISH_SPAIN_MODERN_SORT:
            case Language.SPANISH_SPAIN_TRADITIONAL_SORT:
            case Language.SPANISH_URUGUAY:
            case Language.SPANISH_VENEZUELA:
            case Language.SWEDISH:
            case Language.SWEDISH_FINLAND:
            case Language.SWEDISH_SWEDEN:
            case Language.XHOSA:
            case Language.ZULU:
                return true;

            default:
                return false;
        }
    }

    /// <summary>
    /// Indicates whether the current culture is Arabic.
    /// </summary>
    public static boolean isArabicCurrentCulture()
    {
       return (ARABIC_CULTURE_TWO_LETTER_NAME.equals(SystemPal.getCurrentCultureTwoLetterName()));
    }

    /// <summary>
    /// Indicates whether the specified locale is Cyrillic.
    /// </summary>
    public static boolean isCyrillic(int localeId)
    {
        switch ((/*Language*/int)localeId)
        {
            case Language.BELARUSIAN:
            case Language.BELARUSIAN_BELARUS:
            case Language.BULGARIAN:
            case Language.BULGARIAN_BULGARIA:
            case Language.BOSNIAN_CYRILLIC:
            case Language.CROATIAN:
            case Language.CROATIAN_BOZNIA_AND_HERZEGOVINA:
            case Language.CROATIAN_CROATIA:
            case Language.KAZAKH:
            case Language.KAZAKH_KAZAKHSTAN:
            case Language.KYRGYZ:
            case Language.KYRGYZ_KYRGYZSTAN:
            case Language.MACEDONIAN:
            case Language.MACEDONIAN_FYROM:
            case Language.RUSSIAN:
            case Language.RUSSIAN_MOLDOVA:
            case Language.RUSSIAN_RUSSIA:
            case Language.SERBIAN:
            case Language.SERBIAN_CYRILLIC_BOSNIA_AND_HERZEGOVINA:
            case Language.SERBIAN_CYRILLIC_SERBIA_AND_MONTENEGRO:
            case Language.UKRAINIAN:
            case Language.UKRAINIAN_UKRAINE:
                return true;

            default:
                return false;
        }

    }

    /// <summary>
    /// Indicates whether the specified locale is Chinese Simplified.
    /// </summary>
    public static boolean isChineseSimplified(int localeId)
    {
        return ((localeId == (int)Language.CHINESE_CHINA) || (localeId == (int)Language.CHINESE_SINGAPORE));
    }

    /// <summary>
    /// Indicates whether the specified locale is Chinese Traditional.
    /// </summary>
    public static boolean isChineseTraditional(int localeId)
    {
        /*Language*/int lang = (/*Language*/int)localeId;
        return
            lang == Language.CHINESE_TAIWAN ||
            lang == Language.CHINESE_HONG_KONG ||
            lang == Language.CHINESE_MACAO;
    }

    /// <summary>
    /// Indicates whether the specified locale is Japanese.
    /// </summary>
    public static boolean isJapanese(int localeId)
    {
        switch((/*Language*/int)localeId)
        {
            case Language.JAPANESE:
            case Language.JAPANESE_JAPAN:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Indicates whether the specified locale is either Chinese or Japanese.
    /// </summary>
    public static boolean isChineseOrJapanese(int localeId)
    {
        switch ((/*Language*/int)localeId)
        {
            case Language.CHINESE_CHINA:
            case Language.CHINESE_HONG_KONG:
            case Language.CHINESE_MACAO:
            case Language.CHINESE_SINGAPORE:
            case Language.CHINESE_TAIWAN:
            case Language.JAPANESE:
            case Language.JAPANESE_JAPAN:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Indicates whether the specified locale is Korean.
    /// </summary>
    public static boolean isKorean(int locale)
    {
        return locale == (int)Language.KOREAN || locale == (int)Language.KOREAN_KOREA;
    }

    /// <summary>
    /// Indicates if the specified locale is Chinese, Japanese or Korean.
    /// </summary>
    public static boolean isCjk(int locale)
    {
        return isChineseOrJapanese(locale) || isKorean(locale);
    }

    /// <summary>
    /// Indicates whether the specified locale is Arabic.
    /// </summary>
    public static boolean isArabic(int localeId)
    {
        return LanguageOnly.compare(localeId, LanguageOnly.ARABIC) ||
            LanguageOnly.compare(localeId, LanguageOnly.PERSIAN) ||
            LanguageOnly.compare(localeId, LanguageOnly.URDU);
    }

    /// <summary>
    /// Indicates whether the specified locale is Hebrew.
    /// </summary>
    public static boolean isHebrew(int localeId)
    {
        /*Language*/int lang = (/*Language*/int)localeId;

        return lang == Language.HEBREW ||
               lang == Language.HEBREW_ISRAEL;
    }

    /// <summary>
    /// Indicates whether the specified Language enum value is defined.
    ///
    /// </summary>
    /// <remarks>
    /// The body of the switch clause is generated by TestLanguage.PrintSwitchBody() and tested by TestLanguage.TestEnumIsDefined().
    /// </remarks>
    public static boolean isDefined(/*Language*/int value)
    {
        switch (value)
        {
            case Language.LANGUAGE_NOT_SET:
            case Language.ARABIC:
            case Language.BULGARIAN:
            case Language.CATALAN:
            case Language.CHINESE_SIMPLIFIED:
            case Language.CZECH:
            case Language.DANISH:
            case Language.GERMAN:
            case Language.GREEK:
            case Language.ENGLISH:
            case Language.SPANISH:
            case Language.FINNISH:
            case Language.FRENCH:
            case Language.HEBREW:
            case Language.HUNGARIAN:
            case Language.ICELANDIC:
            case Language.ITALIAN:
            case Language.JAPANESE:
            case Language.KOREAN:
            case Language.DUTCH:
            case Language.NORWEGIAN:
            case Language.POLISH:
            case Language.PORTUGUESE:
            case Language.ROMANIAN:
            case Language.RUSSIAN:
            case Language.CROATIAN:
            case Language.SLOVAK:
            case Language.ALBANIAN:
            case Language.SWEDISH:
            case Language.THAI:
            case Language.TURKISH:
            case Language.URDU:
            case Language.INDONESIAN:
            case Language.UKRAINIAN:
            case Language.BELARUSIAN:
            case Language.SLOVENIAN:
            case Language.ESTONIAN:
            case Language.LATVIAN:
            case Language.LITHUANIAN:
            case Language.PERSIAN:
            case Language.VIETNAMESE:
            case Language.ARMENIAN:
            case Language.AZERI:
            case Language.BASQUE:
            case Language.MACEDONIAN:
            case Language.AFRIKAANS:
            case Language.GEORGIAN:
            case Language.FAEROESE:
            case Language.HINDI:
            case Language.MALAY:
            case Language.KAZAKH:
            case Language.KYRGYZ:
            case Language.KISWAHILI:
            case Language.UZBEK:
            case Language.TATAR:
            case Language.PUNJABI:
            case Language.GUJARATI:
            case Language.TAMIL:
            case Language.TELUGU:
            case Language.KANNADA:
            case Language.MARATHI:
            case Language.SANSKRIT:
            case Language.MONGOLIAN:
            case Language.GALICIAN:
            case Language.KONKANI:
            case Language.SYRIAC:
            case Language.DIVEHI:
            case Language.INVARIANT_CULTURE:
            case Language.NO_PROOF:
            case Language.ARABIC_SAUDI_ARABIA:
            case Language.BULGARIAN_BULGARIA:
            case Language.CATALAN_CATALAN:
            case Language.CHINESE_TAIWAN:
            case Language.CZECH_CZECH_REPUBLIC:
            case Language.DANISH_DENMARK:
            case Language.GERMAN_GERMANY:
            case Language.GREEK_GREECE:
            case Language.ENGLISH_US:
            case Language.SPANISH_SPAIN_TRADITIONAL_SORT:
            case Language.FINNISH_FINLAND:
            case Language.FRENCH_FRANCE:
            case Language.HEBREW_ISRAEL:
            case Language.HUNGARIAN_HUNGARY:
            case Language.ICELANDIC_ICELAND:
            case Language.ITALIAN_ITALY:
            case Language.JAPANESE_JAPAN:
            case Language.KOREAN_KOREA:
            case Language.DUTCH_NETHERLANDS:
            case Language.NORWEGIAN_BOKMAL:
            case Language.POLISH_POLAND:
            case Language.PORTUGUESE_BRAZIL:
            case Language.ROMANSH_SWITZERLAND:
            case Language.ROMANIAN_ROMANIA:
            case Language.RUSSIAN_RUSSIA:
            case Language.CROATIAN_CROATIA:
            case Language.SLOVAK_SLOVAKIA:
            case Language.ALBANIAN_ALBANIA:
            case Language.SWEDISH_SWEDEN:
            case Language.THAI_THAILAND:
            case Language.TURKISH_TURKEY:
            case Language.URDU_PAKISTAN:
            case Language.INDONESIAN_INDONESIA:
            case Language.UKRAINIAN_UKRAINE:
            case Language.BELARUSIAN_BELARUS:
            case Language.SLOVENIAN_SLOVENIA:
            case Language.ESTONIAN_ESTONIA:
            case Language.LATVIAN_LATVIA:
            case Language.LITHUANIAN_LITHUANIA:
            case Language.TAJIK:
            case Language.PERSIAN_IRAN:
            case Language.VIETNAMESE_VIETNAM:
            case Language.ARMENIAN_ARMENIA:
            case Language.AZERI_LATIN:
            case Language.BASQUE_BASQUE:
            case Language.SORBIAN:
            case Language.MACEDONIAN_FYROM:
            case Language.SUTU:
            case Language.TSONGA:
            case Language.TSWANA:
            case Language.VENDA:
            case Language.XHOSA:
            case Language.ZULU:
            case Language.AFRIKAANS_SOUTH_AFRICA:
            case Language.GEORGIAN_GEORGIA:
            case Language.FAEROESE_FAROE_ISLANDS:
            case Language.HINDI_INDIA:
            case Language.MALTESE:
            case Language.SAMI_NORTHERN_NORWAY:
            case Language.GAELIC_SCOTLAND:
            case Language.YIDDISH:
            case Language.MALAY_MALAYSIA:
            case Language.KAZAKH_KAZAKHSTAN:
            case Language.KYRGYZ_KYRGYZSTAN:
            case Language.KISWAHILI_KENYA:
            case Language.TURKMEN:
            case Language.UZBEK_LATIN:
            case Language.TATAR_RUSSIA:
            case Language.BENGALI:
            case Language.PUNJABI_INDIA:
            case Language.GUJARATI_INDIA:
            case Language.ORIYA:
            case Language.TAMIL_INDIA:
            case Language.TELUGU_INDIA:
            case Language.KANNADA_INDIA:
            case Language.MALAYALAM:
            case Language.ASSAMESE:
            case Language.MARATHI_INDIA:
            case Language.SANSKRIT_INDIA:
            case Language.MONGOLIAN_CYRILLIC:
            case Language.TIBETAN_CHINA:
            case Language.WELSH:
            case Language.KHMER:
            case Language.LAO:
            case Language.BURMESE:
            case Language.GALICIAN_GALICIAN:
            case Language.KONKANI_INDIA:
            case Language.MANIPURI:
            case Language.SINDHI_DEVANAGARIC:
            case Language.SYRIAC_SYRIA:
            case Language.SINHALESE:
            case Language.CHEROKEE:
            case Language.INUKTITUT:
            case Language.AMHARIC:
            case Language.TAMAZIGHT:
            case Language.KASHMIRI_ARABIC:
            case Language.NEPALI:
            case Language.FRISIAN_NETHERLANDS:
            case Language.PASHTO:
            case Language.FILIPINO:
            case Language.DIVEHI_MALDIVES:
            case Language.EDO:
            case Language.FULFULDE:
            case Language.HAUSA:
            case Language.IBIBIO:
            case Language.YORUBA:
            case Language.QUECHUA_BOLIVIA:
            case Language.SEPEDI:
            case Language.LUXEMBOUGISH_LUXEMBURG:
            case Language.IGBO:
            case Language.KANURI:
            case Language.OROMO:
            case Language.TIGRIGNA_ETHIOPIA:
            case Language.GUARANI:
            case Language.HAWAIIAN:
            case Language.LATIN:
            case Language.SOMALI:
            case Language.YI:
            case Language.PAPIAMENTU:
            case Language.MAPUDUNGUN_CHILE:
            case Language.MOHAWK_MOHAWK:
            case Language.BRETON:
            case Language.MAORI:
            case Language.ALSATIAN:
            case Language.ARABIC_IRAQ:
            case Language.CHINESE_CHINA:
            case Language.GERMAN_SWITZERLAND:
            case Language.ENGLISH_UK:
            case Language.SPANISH_MEXICO:
            case Language.FRENCH_BELGIUM:
            case Language.ITALIAN_SWITZERLAND:
            case Language.DUTCH_BELGIUM:
            case Language.NORWEGIAN_NYNORSK:
            case Language.PORTUGUESE_PORTUGAL:
            case Language.ROMANIAN_MOLDOVA:
            case Language.RUSSIAN_MOLDOVA:
            case Language.SERBIAN_LATIN:
            case Language.SERBIAN_LATIN_SERBIA_AND_MONTENEGRO:
            case Language.SWEDISH_FINLAND:
            case Language.URDU_INDIAN:
            case Language.AZERI_CYRILLIC:
            case Language.SAMI_NOTHERN_SWEDEN:
            case Language.IRISH_IRELAND:
            case Language.MALAY_BRUNEI_DARUSSALAM:
            case Language.UZBEK_CYRILLIC:
            case Language.BENGALI_BANGLADESH:
            case Language.PUNJABI_PAKISTAN:
            case Language.MONGOLIAN_MONGOLIAN:
            case Language.TIBETAN_BUTAN:
            case Language.SINDHI:
            case Language.INUKTITUT_LATIN_CANADA:
            case Language.TAMAZIGHT_LATIN:
            case Language.KASHMIRI:
            case Language.NEPALI_INDIA:
            case Language.QUECHUA_ECUADOR:
            case Language.TIGRIGNA_ERITREA:
            case Language.ARABIC_EGYPT:
            case Language.CHINESE_HONG_KONG:
            case Language.GERMAN_AUSTRIA:
            case Language.ENGLISH_AUSTRALIA:
            case Language.SPANISH_SPAIN_MODERN_SORT:
            case Language.FRENCH_CANADA:
            case Language.SERBIAN_CYRILLIC_SERBIA_AND_MONTENEGRO:
            case Language.SAMI_NORTHERN_FINLAND:
            case Language.QUECHUA_PERU:
            case Language.ARABIC_LIBYA:
            case Language.CHINESE_SINGAPORE:
            case Language.GERMAN_LUXEMBOURG:
            case Language.ENGLISH_CANADA:
            case Language.SPANISH_GUATEMALA:
            case Language.FRENCH_SWITZERLAND:
            case Language.CROATIAN_BOZNIA_AND_HERZEGOVINA:
            case Language.SAMI_LULE_NORWAY:
            case Language.ARABIC_ALGERIA:
            case Language.CHINESE_MACAO:
            case Language.GERMAN_LIECHTENSTEIN:
            case Language.ENGLISH_NEW_ZEALAND:
            case Language.SPANISH_COSTA_RICA:
            case Language.FRENCH_LUXEMBOURG:
            case Language.BOSNIAN_LATIN:
            case Language.SAMI_LULE_SWEDEN:
            case Language.ARABIC_MOROCCO:
            case Language.ENGLISH_IRELAND:
            case Language.SPANISH_PANAMA:
            case Language.FRENCH_MONACO:
            case Language.SERBIAN_LATIN_BOSNIA_AND_HERZEGOVINA:
            case Language.SAMI_SOUTHERN_NORWAY:
            case Language.ARABIC_TUNISIA:
            case Language.ENGLISH_SOUTH_AFRICA:
            case Language.SPANISH_DOMINICAN_REPUBLIC:
            case Language.FRENCH_WEST_INDIES:
            case Language.SERBIAN_CYRILLIC_BOSNIA_AND_HERZEGOVINA:
            case Language.SAMI_SOUTHERN_SWEDEN:
            case Language.ARABIC_OMAN:
            case Language.ENGLISH_JAMAICA:
            case Language.SPANISH_VENEZUELA:
            case Language.FRENCH_REUNION:
            case Language.BOSNIAN_CYRILLIC:
            case Language.SAMI_SKOLT_FINLAND:
            case Language.ARABIC_YEMEN:
            case Language.ENGLISH_CARIBBEAN:
            case Language.SPANISH_COLOMBIA:
            case Language.FRENCH_CONGO:
            case Language.SAMI_INARI_FINLAND:
            case Language.ARABIC_SYRIA:
            case Language.ENGLISH_BELIZE:
            case Language.SPANISH_PERU:
            case Language.FRENCH_SENEGAL:
            case Language.ARABIC_JORDAN:
            case Language.ENGLISH_TRINIDAD_AND_TOBAGO:
            case Language.SPANISH_ARGENTINA:
            case Language.FRENCH_CAMEROON:
            case Language.ARABIC_LEBANON:
            case Language.ENGLISH_ZIMBABWE:
            case Language.SPANISH_ECUADOR:
            case Language.FRENCH_COTE_D_IVOIRE:
            case Language.ARABIC_KUWAIT:
            case Language.ENGLISH_PHILIPPINES:
            case Language.SPANISH_CHILE:
            case Language.FRENCH_MALI:
            case Language.ARABIC_UAE:
            case Language.ENGLISH_INDONESIA:
            case Language.SPANISH_URUGUAY:
            case Language.FRENCH_MOROCCO:
            case Language.ARABIC_BAHRAIN:
            case Language.ENGLISH_HONG_KONG:
            case Language.SPANISH_PARAGUAY:
            case Language.FRENCH_HAITI:
            case Language.ARABIC_QATAR:
            case Language.ENGLISH_INDIA:
            case Language.SPANISH_BOLIVIA:
            case Language.ENGLISH_MALAYSIA:
            case Language.SPANISH_EL_SALVADOR:
            case Language.ENGLISH_SINGAPORE:
            case Language.SPANISH_HONDURAS:
            case Language.SPANISH_NICARAGUA:
            case Language.SPANISH_PUERTO_RICO:
            case Language.CHINESE_TRADITIONAL:
            case Language.SERBIAN:
                return true;
            default:
                return false;
        }
    }

    private static final String ARABIC_CULTURE_TWO_LETTER_NAME = "ar";
}

