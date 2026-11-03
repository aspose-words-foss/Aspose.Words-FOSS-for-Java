// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/02/2021 by Roman Korchagin, Alexey Minenkov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Provides methods for converting <see cref="Language"/> values to their string representations (language tags).
/// </summary>
public /*static*/ class LocaleConverter
{
	/* Simulation of static class by using private constructor */
	private LocaleConverter()
	{}

    /// <summary>
    /// Gets <see cref="Language"/> code for the specified language tag.
    /// </summary>
    /// <param name="tag">Language tag specified in the format used in .docx.</param>
    public static int docxTagToLocale(String tag)
    {
        return tagToLocale(tag, G_LANGUAGE_MAP_DOCX, G_OLD_TAG_TO_CODE_DICTIONARY_DOCX);
    }

    /// <summary>
    /// Gets <see cref="Language"/> code for the specified language tag.
    /// </summary>
    /// <param name="tag">Language tag specified in the format used in WordML.</param>
    public static int wmlTagToLocale(String tag)
    {
        return tagToLocale(tag, G_LANGUAGE_MAP_WML, G_OLD_TAG_TO_CODE_DICTIONARY_WML);
    }

    /// <summary>
    /// Gets language tag (in the format used in .docx) for the specified <see cref="Language"/> code.
    /// </summary>
    public static String localeToDocxTag(int locale)
    {
        return G_LANGUAGE_MAP_DOCX.getValue(locale, "");
    }

    /// <summary>
    /// Gets language tag (in the format used in WordML) for the specified <see cref="Language"/> code.
    /// </summary>
    public static String localeToWmlTag(int locale)
    {
        String code = G_LANGUAGE_MAP_WML.tryGetValue(locale);

        if (code == null && G_ADDITIONAL_LANGUAGE_MAP_WML.containsKey(locale))
            code = G_ADDITIONAL_LANGUAGE_MAP_WML.get(locale);

        return code == null ? "" : code;
    }

    private static void additionalLanguageToMapWml(String langCode, int langId)
    {
        G_ADDITIONAL_LANGUAGE_MAP_WML.add(langId, langCode);
    }

    /// <summary>
    /// Returns <see cref="Language"/> code for the specified language tag
    /// or <see cref="Language.InvariantCulture"/> if the specified language tag does not exist.
    /// </summary>
    /// <remarks>
    /// Old tags are also supported for backward compatibility, so a different set of dictionaries is needed.
    /// </remarks>
    private static int tagToLocale(
        String tag, StringToIntBidirectionalMap mainDictionary, StringToIntDictionary oldTagToCodeDictionary)
    {
        int code = mainDictionary.tryGetValue(tag);

        if (code != Integer.MIN_VALUE)
            return code;

        return oldTagToCodeDictionary.containsKey(tag)
            ? oldTagToCodeDictionary.get(tag)
            : (int)Language.INVARIANT_CULTURE;
    }

    private static StringToIntDictionary initOldTagToCodeDictionaryDocx()
    {
        StringToIntDictionary oldTagToCodeDictionaryDocx = new StringToIntDictionary(false);
        oldTagToCodeDictionaryDocx.add("ro-MO", (int)Language.ROMANIAN_MOLDOVA);
        oldTagToCodeDictionaryDocx.add("ru-MO", (int)Language.RUSSIAN_MOLDOVA);
        return oldTagToCodeDictionaryDocx;
    }

    private static StringToIntDictionary initOldTagToCodeDictionaryWml()
    {
        StringToIntDictionary oldTagToCodeDictionaryWml = new StringToIntDictionary(false);
        oldTagToCodeDictionaryWml.add("RO-MO", (int)Language.ROMANIAN_MOLDOVA);
        oldTagToCodeDictionaryWml.add("RU-MO", (int)Language.RUSSIAN_MOLDOVA);
        return oldTagToCodeDictionaryWml;
    }

    private static /*final*/ StringToIntBidirectionalMap G_LANGUAGE_MAP_DOCX = new StringToIntBidirectionalMap(false);
    private static /*final*/ StringToIntBidirectionalMap G_LANGUAGE_MAP_WML = new StringToIntBidirectionalMap(false);
    private static /*final*/ IntToObjDictionary<String> G_ADDITIONAL_LANGUAGE_MAP_WML = new IntToObjDictionary<String>();
    private static /*final*/ StringToIntDictionary G_OLD_TAG_TO_CODE_DICTIONARY_DOCX = initOldTagToCodeDictionaryDocx();
    private static /*final*/ StringToIntDictionary G_OLD_TAG_TO_CODE_DICTIONARY_WML = initOldTagToCodeDictionaryWml();

    static/* LocaleConverter()*/
    {
        G_LANGUAGE_MAP_DOCX.addEntry("af", (int)Language.AFRIKAANS);
        G_LANGUAGE_MAP_DOCX.addEntry("af-ZA", (int)Language.AFRIKAANS_SOUTH_AFRICA);
        G_LANGUAGE_MAP_DOCX.addEntry("sq", (int)Language.ALBANIAN);
        G_LANGUAGE_MAP_DOCX.addEntry("sq-AL", (int)Language.ALBANIAN_ALBANIA);
        G_LANGUAGE_MAP_DOCX.addEntry("gsw-FR", (int)Language.ALSATIAN);
        G_LANGUAGE_MAP_DOCX.addEntry("am-ET", (int)Language.AMHARIC);
        G_LANGUAGE_MAP_DOCX.addEntry("ar", (int)Language.ARABIC);
        G_LANGUAGE_MAP_DOCX.addEntry("ar-DZ", (int)Language.ARABIC_ALGERIA);
        G_LANGUAGE_MAP_DOCX.addEntry("ar-BH", (int)Language.ARABIC_BAHRAIN);
        G_LANGUAGE_MAP_DOCX.addEntry("ar-EG", (int)Language.ARABIC_EGYPT);
        G_LANGUAGE_MAP_DOCX.addEntry("ar-IQ", (int)Language.ARABIC_IRAQ);
        G_LANGUAGE_MAP_DOCX.addEntry("ar-JO", (int)Language.ARABIC_JORDAN);
        G_LANGUAGE_MAP_DOCX.addEntry("ar-KW", (int)Language.ARABIC_KUWAIT);
        G_LANGUAGE_MAP_DOCX.addEntry("ar-LB", (int)Language.ARABIC_LEBANON);
        G_LANGUAGE_MAP_DOCX.addEntry("ar-LY", (int)Language.ARABIC_LIBYA);
        G_LANGUAGE_MAP_DOCX.addEntry("ar-MA", (int)Language.ARABIC_MOROCCO);
        G_LANGUAGE_MAP_DOCX.addEntry("ar-OM", (int)Language.ARABIC_OMAN);
        G_LANGUAGE_MAP_DOCX.addEntry("ar-QA", (int)Language.ARABIC_QATAR);
        G_LANGUAGE_MAP_DOCX.addEntry("ar-SA", (int)Language.ARABIC_SAUDI_ARABIA);
        G_LANGUAGE_MAP_DOCX.addEntry("ar-SY", (int)Language.ARABIC_SYRIA);
        G_LANGUAGE_MAP_DOCX.addEntry("ar-TN", (int)Language.ARABIC_TUNISIA);
        G_LANGUAGE_MAP_DOCX.addEntry("ar-AE", (int)Language.ARABIC_UAE);
        G_LANGUAGE_MAP_DOCX.addEntry("ar-YE", (int)Language.ARABIC_YEMEN);
        G_LANGUAGE_MAP_DOCX.addEntry("hy", (int)Language.ARMENIAN);
        G_LANGUAGE_MAP_DOCX.addEntry("hy-AM", (int)Language.ARMENIAN_ARMENIA);
        G_LANGUAGE_MAP_DOCX.addEntry("as-IN", (int)Language.ASSAMESE);
        G_LANGUAGE_MAP_DOCX.addEntry("az", (int)Language.AZERI);
        G_LANGUAGE_MAP_DOCX.addEntry("az-Cyrl-AZ", (int)Language.AZERI_CYRILLIC);
        G_LANGUAGE_MAP_DOCX.addEntry("az-Latn-AZ", (int)Language.AZERI_LATIN);
        G_LANGUAGE_MAP_DOCX.addEntry("eu", (int)Language.BASQUE);
        G_LANGUAGE_MAP_DOCX.addEntry("eu-ES", (int)Language.BASQUE_BASQUE);
        G_LANGUAGE_MAP_DOCX.addEntry("be", (int)Language.BELARUSIAN);
        G_LANGUAGE_MAP_DOCX.addEntry("be-BY", (int)Language.BELARUSIAN_BELARUS);
        G_LANGUAGE_MAP_DOCX.addEntry("bn-IN", (int)Language.BENGALI);
        G_LANGUAGE_MAP_DOCX.addEntry("bn-BD", (int)Language.BENGALI_BANGLADESH);
        G_LANGUAGE_MAP_DOCX.addEntry("bs-Cyrl-BA", (int)Language.BOSNIAN_CYRILLIC);
        G_LANGUAGE_MAP_DOCX.addEntry("bs-Latn-BA", (int)Language.BOSNIAN_LATIN);
        G_LANGUAGE_MAP_DOCX.addEntry("br-FR", (int)Language.BRETON);
        G_LANGUAGE_MAP_DOCX.addEntry("bg", (int)Language.BULGARIAN);
        G_LANGUAGE_MAP_DOCX.addEntry("bg-BG", (int)Language.BULGARIAN_BULGARIA);
        G_LANGUAGE_MAP_DOCX.addEntry("my-MM", (int)Language.BURMESE);
        G_LANGUAGE_MAP_DOCX.addEntry("ca", (int)Language.CATALAN);
        G_LANGUAGE_MAP_DOCX.addEntry("ca-ES", (int)Language.CATALAN_CATALAN);
        G_LANGUAGE_MAP_DOCX.addEntry("chr-US", (int)Language.CHEROKEE);
        G_LANGUAGE_MAP_DOCX.addEntry("zh-HK", (int)Language.CHINESE_HONG_KONG);
        G_LANGUAGE_MAP_DOCX.addEntry("zh-MO", (int)Language.CHINESE_MACAO);
        G_LANGUAGE_MAP_DOCX.addEntry("zh-CN", (int)Language.CHINESE_CHINA);
        G_LANGUAGE_MAP_DOCX.addEntry("zh-Hans", (int)Language.CHINESE_SIMPLIFIED);
        G_LANGUAGE_MAP_DOCX.addEntry("zh-SG", (int)Language.CHINESE_SINGAPORE);
        G_LANGUAGE_MAP_DOCX.addEntry("zh-TW", (int)Language.CHINESE_TAIWAN);
        G_LANGUAGE_MAP_DOCX.addEntry("zh-Hant", (int)Language.CHINESE_TRADITIONAL);
        G_LANGUAGE_MAP_DOCX.addEntry("hr", (int)Language.CROATIAN);
        G_LANGUAGE_MAP_DOCX.addEntry("hr-BA", (int)Language.CROATIAN_BOZNIA_AND_HERZEGOVINA);
        G_LANGUAGE_MAP_DOCX.addEntry("hr-HR", (int)Language.CROATIAN_CROATIA);
        G_LANGUAGE_MAP_DOCX.addEntry("cs", (int)Language.CZECH);
        G_LANGUAGE_MAP_DOCX.addEntry("cs-CZ", (int)Language.CZECH_CZECH_REPUBLIC);
        G_LANGUAGE_MAP_DOCX.addEntry("da", (int)Language.DANISH);
        G_LANGUAGE_MAP_DOCX.addEntry("da-DK", (int)Language.DANISH_DENMARK);
        G_LANGUAGE_MAP_DOCX.addEntry("dv", (int)Language.DIVEHI);
        G_LANGUAGE_MAP_DOCX.addEntry("dv-MV", (int)Language.DIVEHI_MALDIVES);
        G_LANGUAGE_MAP_DOCX.addEntry("nl", (int)Language.DUTCH);
        G_LANGUAGE_MAP_DOCX.addEntry("nl-NL", (int)Language.DUTCH_NETHERLANDS);
        G_LANGUAGE_MAP_DOCX.addEntry("nl-BE", (int)Language.DUTCH_BELGIUM);
        G_LANGUAGE_MAP_DOCX.addEntry("bin-NG", (int)Language.EDO);
        G_LANGUAGE_MAP_DOCX.addEntry("en", (int)Language.ENGLISH);
        G_LANGUAGE_MAP_DOCX.addEntry("en-AU", (int)Language.ENGLISH_AUSTRALIA);
        G_LANGUAGE_MAP_DOCX.addEntry("en-BZ", (int)Language.ENGLISH_BELIZE);
        G_LANGUAGE_MAP_DOCX.addEntry("en-CA", (int)Language.ENGLISH_CANADA);
        G_LANGUAGE_MAP_DOCX.addEntry("en-029", (int)Language.ENGLISH_CARIBBEAN);
        G_LANGUAGE_MAP_DOCX.addEntry("en-HK", (int)Language.ENGLISH_HONG_KONG);
        G_LANGUAGE_MAP_DOCX.addEntry("en-IN", (int)Language.ENGLISH_INDIA);
        G_LANGUAGE_MAP_DOCX.addEntry("en-ID", (int)Language.ENGLISH_INDONESIA);
        G_LANGUAGE_MAP_DOCX.addEntry("en-IE", (int)Language.ENGLISH_IRELAND);
        G_LANGUAGE_MAP_DOCX.addEntry("en-JM", (int)Language.ENGLISH_JAMAICA);
        G_LANGUAGE_MAP_DOCX.addEntry("en-MY", (int)Language.ENGLISH_MALAYSIA);
        G_LANGUAGE_MAP_DOCX.addEntry("en-NZ", (int)Language.ENGLISH_NEW_ZEALAND);
        G_LANGUAGE_MAP_DOCX.addEntry("en-PH", (int)Language.ENGLISH_PHILIPPINES);
        G_LANGUAGE_MAP_DOCX.addEntry("en-SG", (int)Language.ENGLISH_SINGAPORE);
        G_LANGUAGE_MAP_DOCX.addEntry("en-ZA", (int)Language.ENGLISH_SOUTH_AFRICA);
        G_LANGUAGE_MAP_DOCX.addEntry("en-TT", (int)Language.ENGLISH_TRINIDAD_AND_TOBAGO);
        G_LANGUAGE_MAP_DOCX.addEntry("en-GB", (int)Language.ENGLISH_UK);
        G_LANGUAGE_MAP_DOCX.addEntry("en-US", (int)Language.ENGLISH_US);
        G_LANGUAGE_MAP_DOCX.addEntry("en-ZW", (int)Language.ENGLISH_ZIMBABWE);
        G_LANGUAGE_MAP_DOCX.addEntry("et", (int)Language.ESTONIAN);
        G_LANGUAGE_MAP_DOCX.addEntry("et-EE", (int)Language.ESTONIAN_ESTONIA);
        G_LANGUAGE_MAP_DOCX.addEntry("fo", (int)Language.FAEROESE);
        G_LANGUAGE_MAP_DOCX.addEntry("fo-FO", (int)Language.FAEROESE_FAROE_ISLANDS);
        G_LANGUAGE_MAP_DOCX.addEntry("fil-PH", (int)Language.FILIPINO);
        G_LANGUAGE_MAP_DOCX.addEntry("fi", (int)Language.FINNISH);
        G_LANGUAGE_MAP_DOCX.addEntry("fi-FI", (int)Language.FINNISH_FINLAND);
        G_LANGUAGE_MAP_DOCX.addEntry("fr", (int)Language.FRENCH);
        G_LANGUAGE_MAP_DOCX.addEntry("fr-BE", (int)Language.FRENCH_BELGIUM);
        G_LANGUAGE_MAP_DOCX.addEntry("fr-CM", (int)Language.FRENCH_CAMEROON);
        G_LANGUAGE_MAP_DOCX.addEntry("fr-CA", (int)Language.FRENCH_CANADA);
        G_LANGUAGE_MAP_DOCX.addEntry("fr-CD", (int)Language.FRENCH_CONGO);
        G_LANGUAGE_MAP_DOCX.addEntry("fr-CI", (int)Language.FRENCH_COTE_D_IVOIRE);
        G_LANGUAGE_MAP_DOCX.addEntry("fr-FR", (int)Language.FRENCH_FRANCE);
        G_LANGUAGE_MAP_DOCX.addEntry("fr-HT", (int)Language.FRENCH_HAITI);
        G_LANGUAGE_MAP_DOCX.addEntry("fr-LU", (int)Language.FRENCH_LUXEMBOURG);
        G_LANGUAGE_MAP_DOCX.addEntry("fr-ML", (int)Language.FRENCH_MALI);
        G_LANGUAGE_MAP_DOCX.addEntry("fr-MC", (int)Language.FRENCH_MONACO);
        G_LANGUAGE_MAP_DOCX.addEntry("fr-MA", (int)Language.FRENCH_MOROCCO);
        G_LANGUAGE_MAP_DOCX.addEntry("fr-RE", (int)Language.FRENCH_REUNION);
        G_LANGUAGE_MAP_DOCX.addEntry("fr-SN", (int)Language.FRENCH_SENEGAL);
        G_LANGUAGE_MAP_DOCX.addEntry("fr-CH", (int)Language.FRENCH_SWITZERLAND);
        G_LANGUAGE_MAP_DOCX.addEntry("fr-WINDIES", (int)Language.FRENCH_WEST_INDIES);
        G_LANGUAGE_MAP_DOCX.addEntry("fy-NL", (int)Language.FRISIAN_NETHERLANDS);
        G_LANGUAGE_MAP_DOCX.addEntry("ff-NG", (int)Language.FULFULDE);
        G_LANGUAGE_MAP_DOCX.addEntry("gd-GB", (int)Language.GAELIC_SCOTLAND);
        G_LANGUAGE_MAP_DOCX.addEntry("gl", (int)Language.GALICIAN);
        G_LANGUAGE_MAP_DOCX.addEntry("gl-ES", (int)Language.GALICIAN_GALICIAN);
        G_LANGUAGE_MAP_DOCX.addEntry("ka", (int)Language.GEORGIAN);
        G_LANGUAGE_MAP_DOCX.addEntry("ka-GE", (int)Language.GEORGIAN_GEORGIA);
        G_LANGUAGE_MAP_DOCX.addEntry("de", (int)Language.GERMAN);
        G_LANGUAGE_MAP_DOCX.addEntry("de-AT", (int)Language.GERMAN_AUSTRIA);
        G_LANGUAGE_MAP_DOCX.addEntry("de-DE", (int)Language.GERMAN_GERMANY);
        G_LANGUAGE_MAP_DOCX.addEntry("de-LI", (int)Language.GERMAN_LIECHTENSTEIN);
        G_LANGUAGE_MAP_DOCX.addEntry("de-LU", (int)Language.GERMAN_LUXEMBOURG);
        G_LANGUAGE_MAP_DOCX.addEntry("de-CH", (int)Language.GERMAN_SWITZERLAND);
        G_LANGUAGE_MAP_DOCX.addEntry("el", (int)Language.GREEK);
        G_LANGUAGE_MAP_DOCX.addEntry("el-GR", (int)Language.GREEK_GREECE);
        G_LANGUAGE_MAP_DOCX.addEntry("gn-PY", (int)Language.GUARANI);
        G_LANGUAGE_MAP_DOCX.addEntry("gu", (int)Language.GUJARATI);
        G_LANGUAGE_MAP_DOCX.addEntry("gu-IN", (int)Language.GUJARATI_INDIA);
        G_LANGUAGE_MAP_DOCX.addEntry("ha-Latn-NG", (int)Language.HAUSA);
        G_LANGUAGE_MAP_DOCX.addEntry("haw-US", (int)Language.HAWAIIAN);
        G_LANGUAGE_MAP_DOCX.addEntry("he", (int)Language.HEBREW);
        G_LANGUAGE_MAP_DOCX.addEntry("he-IL", (int)Language.HEBREW_ISRAEL);
        G_LANGUAGE_MAP_DOCX.addEntry("hi", (int)Language.HINDI);
        G_LANGUAGE_MAP_DOCX.addEntry("hi-IN", (int)Language.HINDI_INDIA);
        G_LANGUAGE_MAP_DOCX.addEntry("hu", (int)Language.HUNGARIAN);
        G_LANGUAGE_MAP_DOCX.addEntry("hu-HU", (int)Language.HUNGARIAN_HUNGARY);
        G_LANGUAGE_MAP_DOCX.addEntry("ibb-NG", (int)Language.IBIBIO);
        G_LANGUAGE_MAP_DOCX.addEntry("is", (int)Language.ICELANDIC);
        G_LANGUAGE_MAP_DOCX.addEntry("is-IS", (int)Language.ICELANDIC_ICELAND);
        G_LANGUAGE_MAP_DOCX.addEntry("ig-NG", (int)Language.IGBO);
        G_LANGUAGE_MAP_DOCX.addEntry("id", (int)Language.INDONESIAN);
        G_LANGUAGE_MAP_DOCX.addEntry("id-ID", (int)Language.INDONESIAN_INDONESIA);
        G_LANGUAGE_MAP_DOCX.addEntry("iu-Cans-CA", (int)Language.INUKTITUT);
        G_LANGUAGE_MAP_DOCX.addEntry("iu-Latn-CA", (int)Language.INUKTITUT_LATIN_CANADA);
        G_LANGUAGE_MAP_DOCX.addEntry("ga-IE", (int)Language.IRISH_IRELAND);
        G_LANGUAGE_MAP_DOCX.addEntry("it", (int)Language.ITALIAN);
        G_LANGUAGE_MAP_DOCX.addEntry("it-IT", (int)Language.ITALIAN_ITALY);
        G_LANGUAGE_MAP_DOCX.addEntry("it-CH", (int)Language.ITALIAN_SWITZERLAND);
        G_LANGUAGE_MAP_DOCX.addEntry("ja", (int)Language.JAPANESE);
        G_LANGUAGE_MAP_DOCX.addEntry("ja-JP", (int)Language.JAPANESE_JAPAN);
        G_LANGUAGE_MAP_DOCX.addEntry("kn", (int)Language.KANNADA);
        G_LANGUAGE_MAP_DOCX.addEntry("kn-IN", (int)Language.KANNADA_INDIA);
        G_LANGUAGE_MAP_DOCX.addEntry("kr-NG", (int)Language.KANURI);
        G_LANGUAGE_MAP_DOCX.addEntry("ks-Deva", (int)Language.KASHMIRI);
        G_LANGUAGE_MAP_DOCX.addEntry("ks-Arab", (int)Language.KASHMIRI_ARABIC);
        G_LANGUAGE_MAP_DOCX.addEntry("kk", (int)Language.KAZAKH);
        G_LANGUAGE_MAP_DOCX.addEntry("kk-KZ", (int)Language.KAZAKH_KAZAKHSTAN);
        G_LANGUAGE_MAP_DOCX.addEntry("km-KH", (int)Language.KHMER);
        G_LANGUAGE_MAP_DOCX.addEntry("sw", (int)Language.KISWAHILI);
        G_LANGUAGE_MAP_DOCX.addEntry("sw-KE", (int)Language.KISWAHILI_KENYA);
        G_LANGUAGE_MAP_DOCX.addEntry("kok", (int)Language.KONKANI);
        G_LANGUAGE_MAP_DOCX.addEntry("kok-IN", (int)Language.KONKANI_INDIA);
        G_LANGUAGE_MAP_DOCX.addEntry("ko", (int)Language.KOREAN);
        G_LANGUAGE_MAP_DOCX.addEntry("ko-KR", (int)Language.KOREAN_KOREA);
        G_LANGUAGE_MAP_DOCX.addEntry("ky", (int)Language.KYRGYZ);
        G_LANGUAGE_MAP_DOCX.addEntry("ky-KG", (int)Language.KYRGYZ_KYRGYZSTAN);
        G_LANGUAGE_MAP_DOCX.addEntry("lo-LA", (int)Language.LAO);
        G_LANGUAGE_MAP_DOCX.addEntry("la-Latn", (int)Language.LATIN);
        G_LANGUAGE_MAP_DOCX.addEntry("lv", (int)Language.LATVIAN);
        G_LANGUAGE_MAP_DOCX.addEntry("lv-LV", (int)Language.LATVIAN_LATVIA);
        G_LANGUAGE_MAP_DOCX.addEntry("lt", (int)Language.LITHUANIAN);
        G_LANGUAGE_MAP_DOCX.addEntry("lt-LT", (int)Language.LITHUANIAN_LITHUANIA);
        G_LANGUAGE_MAP_DOCX.addEntry("lb-LU", (int)Language.LUXEMBOUGISH_LUXEMBURG);
        G_LANGUAGE_MAP_DOCX.addEntry("mk", (int)Language.MACEDONIAN);
        G_LANGUAGE_MAP_DOCX.addEntry("mk-MK", (int)Language.MACEDONIAN_FYROM);
        G_LANGUAGE_MAP_DOCX.addEntry("ms", (int)Language.MALAY);
        G_LANGUAGE_MAP_DOCX.addEntry("ms-MY", (int)Language.MALAY_MALAYSIA);
        G_LANGUAGE_MAP_DOCX.addEntry("ms-BN", (int)Language.MALAY_BRUNEI_DARUSSALAM);
        G_LANGUAGE_MAP_DOCX.addEntry("ml-IN", (int)Language.MALAYALAM);
        G_LANGUAGE_MAP_DOCX.addEntry("mt-MT", (int)Language.MALTESE);
        G_LANGUAGE_MAP_DOCX.addEntry("mni-IN", (int)Language.MANIPURI);
        G_LANGUAGE_MAP_DOCX.addEntry("mi-NZ", (int)Language.MAORI);
        G_LANGUAGE_MAP_DOCX.addEntry("arn-CL", (int)Language.MAPUDUNGUN_CHILE);
        G_LANGUAGE_MAP_DOCX.addEntry("mr", (int)Language.MARATHI);
        G_LANGUAGE_MAP_DOCX.addEntry("mr-IN", (int)Language.MARATHI_INDIA);
        G_LANGUAGE_MAP_DOCX.addEntry("moh-CA", (int)Language.MOHAWK_MOHAWK);
        G_LANGUAGE_MAP_DOCX.addEntry("mn", (int)Language.MONGOLIAN);
        G_LANGUAGE_MAP_DOCX.addEntry("mn-MN", (int)Language.MONGOLIAN_CYRILLIC);
        G_LANGUAGE_MAP_DOCX.addEntry("mn-Mong-CN", (int)Language.MONGOLIAN_MONGOLIAN);
        G_LANGUAGE_MAP_DOCX.addEntry("ne-NP", (int)Language.NEPALI);
        G_LANGUAGE_MAP_DOCX.addEntry("ne-IN", (int)Language.NEPALI_INDIA);
        G_LANGUAGE_MAP_DOCX.addEntry("no", (int)Language.NORWEGIAN);
        G_LANGUAGE_MAP_DOCX.addEntry("nb-NO", (int)Language.NORWEGIAN_BOKMAL);
        G_LANGUAGE_MAP_DOCX.addEntry("nn-NO", (int)Language.NORWEGIAN_NYNORSK);
        G_LANGUAGE_MAP_DOCX.addEntry("or-IN", (int)Language.ORIYA);
        G_LANGUAGE_MAP_DOCX.addEntry("om-Ethi-ET", (int)Language.OROMO);
        G_LANGUAGE_MAP_DOCX.addEntry("pap-AN", (int)Language.PAPIAMENTU);
        G_LANGUAGE_MAP_DOCX.addEntry("ps-AF", (int)Language.PASHTO);
        G_LANGUAGE_MAP_DOCX.addEntry("fa", (int)Language.PERSIAN);
        G_LANGUAGE_MAP_DOCX.addEntry("fa-IR", (int)Language.PERSIAN_IRAN);
        G_LANGUAGE_MAP_DOCX.addEntry("pl", (int)Language.POLISH);
        G_LANGUAGE_MAP_DOCX.addEntry("pl-PL", (int)Language.POLISH_POLAND);
        G_LANGUAGE_MAP_DOCX.addEntry("pt", (int)Language.PORTUGUESE);
        G_LANGUAGE_MAP_DOCX.addEntry("pt-BR", (int)Language.PORTUGUESE_BRAZIL);
        G_LANGUAGE_MAP_DOCX.addEntry("pt-PT", (int)Language.PORTUGUESE_PORTUGAL);
        G_LANGUAGE_MAP_DOCX.addEntry("pa", (int)Language.PUNJABI);
        G_LANGUAGE_MAP_DOCX.addEntry("pa-IN", (int)Language.PUNJABI_INDIA);
        G_LANGUAGE_MAP_DOCX.addEntry("pa-PK", (int)Language.PUNJABI_PAKISTAN);
        G_LANGUAGE_MAP_DOCX.addEntry("quz-BO", (int)Language.QUECHUA_BOLIVIA);
        G_LANGUAGE_MAP_DOCX.addEntry("quz-EC", (int)Language.QUECHUA_ECUADOR);
        G_LANGUAGE_MAP_DOCX.addEntry("quz-PE", (int)Language.QUECHUA_PERU);
        G_LANGUAGE_MAP_DOCX.addEntry("ro", (int)Language.ROMANIAN);
        G_LANGUAGE_MAP_DOCX.addEntry("ro-MD", (int)Language.ROMANIAN_MOLDOVA);
        G_LANGUAGE_MAP_DOCX.addEntry("ro-RO", (int)Language.ROMANIAN_ROMANIA);
        G_LANGUAGE_MAP_DOCX.addEntry("rm-CH", (int)Language.ROMANSH_SWITZERLAND);
        G_LANGUAGE_MAP_DOCX.addEntry("ru", (int)Language.RUSSIAN);
        G_LANGUAGE_MAP_DOCX.addEntry("ru-MD", (int)Language.RUSSIAN_MOLDOVA);
        G_LANGUAGE_MAP_DOCX.addEntry("ru-RU", (int)Language.RUSSIAN_RUSSIA);
        G_LANGUAGE_MAP_DOCX.addEntry("smn-FI", (int)Language.SAMI_INARI_FINLAND);
        G_LANGUAGE_MAP_DOCX.addEntry("smj-NO", (int)Language.SAMI_LULE_NORWAY);
        G_LANGUAGE_MAP_DOCX.addEntry("smj-SE", (int)Language.SAMI_LULE_SWEDEN);
        G_LANGUAGE_MAP_DOCX.addEntry("se-FI", (int)Language.SAMI_NORTHERN_FINLAND);
        G_LANGUAGE_MAP_DOCX.addEntry("se-NO", (int)Language.SAMI_NORTHERN_NORWAY);
        G_LANGUAGE_MAP_DOCX.addEntry("se-SE", (int)Language.SAMI_NOTHERN_SWEDEN);
        G_LANGUAGE_MAP_DOCX.addEntry("sms-FI", (int)Language.SAMI_SKOLT_FINLAND);
        G_LANGUAGE_MAP_DOCX.addEntry("sma-NO", (int)Language.SAMI_SOUTHERN_NORWAY);
        G_LANGUAGE_MAP_DOCX.addEntry("sma-SE", (int)Language.SAMI_SOUTHERN_SWEDEN);
        G_LANGUAGE_MAP_DOCX.addEntry("sa", (int)Language.SANSKRIT);
        G_LANGUAGE_MAP_DOCX.addEntry("sa-IN", (int)Language.SANSKRIT_INDIA);
        G_LANGUAGE_MAP_DOCX.addEntry("nso-ZA", (int)Language.SEPEDI);
        G_LANGUAGE_MAP_DOCX.addEntry("sr", (int)Language.SERBIAN);
        G_LANGUAGE_MAP_DOCX.addEntry("sr-Latn-RS", (int)Language.SERBIAN_LATIN);
        G_LANGUAGE_MAP_DOCX.addEntry("sr-Cyrl-BA", (int)Language.SERBIAN_CYRILLIC_BOSNIA_AND_HERZEGOVINA);
        G_LANGUAGE_MAP_DOCX.addEntry("sr-Cyrl-CS", (int)Language.SERBIAN_CYRILLIC_SERBIA_AND_MONTENEGRO);
        G_LANGUAGE_MAP_DOCX.addEntry("sr-Latn-BA", (int)Language.SERBIAN_LATIN_BOSNIA_AND_HERZEGOVINA);
        G_LANGUAGE_MAP_DOCX.addEntry("sr-Latn-CS", (int)Language.SERBIAN_LATIN_SERBIA_AND_MONTENEGRO);
        G_LANGUAGE_MAP_DOCX.addEntry("sd-Arab-PK", (int)Language.SINDHI);
        G_LANGUAGE_MAP_DOCX.addEntry("sd-Deva-IN", (int)Language.SINDHI_DEVANAGARIC);
        G_LANGUAGE_MAP_DOCX.addEntry("si-LK", (int)Language.SINHALESE);
        G_LANGUAGE_MAP_DOCX.addEntry("sk", (int)Language.SLOVAK);
        G_LANGUAGE_MAP_DOCX.addEntry("sk-SK", (int)Language.SLOVAK_SLOVAKIA);
        G_LANGUAGE_MAP_DOCX.addEntry("sl", (int)Language.SLOVENIAN);
        G_LANGUAGE_MAP_DOCX.addEntry("sl-SI", (int)Language.SLOVENIAN_SLOVENIA);
        G_LANGUAGE_MAP_DOCX.addEntry("so-SO", (int)Language.SOMALI);
        G_LANGUAGE_MAP_DOCX.addEntry("hsb-DE", (int)Language.SORBIAN);
        G_LANGUAGE_MAP_DOCX.addEntry("es", (int)Language.SPANISH);
        G_LANGUAGE_MAP_DOCX.addEntry("es-AR", (int)Language.SPANISH_ARGENTINA);
        G_LANGUAGE_MAP_DOCX.addEntry("es-BO", (int)Language.SPANISH_BOLIVIA);
        G_LANGUAGE_MAP_DOCX.addEntry("es-CL", (int)Language.SPANISH_CHILE);
        G_LANGUAGE_MAP_DOCX.addEntry("es-CO", (int)Language.SPANISH_COLOMBIA);
        G_LANGUAGE_MAP_DOCX.addEntry("es-CR", (int)Language.SPANISH_COSTA_RICA);
        G_LANGUAGE_MAP_DOCX.addEntry("es-DO", (int)Language.SPANISH_DOMINICAN_REPUBLIC);
        G_LANGUAGE_MAP_DOCX.addEntry("es-EC", (int)Language.SPANISH_ECUADOR);
        G_LANGUAGE_MAP_DOCX.addEntry("es-SV", (int)Language.SPANISH_EL_SALVADOR);
        G_LANGUAGE_MAP_DOCX.addEntry("es-GT", (int)Language.SPANISH_GUATEMALA);
        G_LANGUAGE_MAP_DOCX.addEntry("es-HN", (int)Language.SPANISH_HONDURAS);
        G_LANGUAGE_MAP_DOCX.addEntry("es-MX", (int)Language.SPANISH_MEXICO);
        G_LANGUAGE_MAP_DOCX.addEntry("es-NI", (int)Language.SPANISH_NICARAGUA);
        G_LANGUAGE_MAP_DOCX.addEntry("es-PA", (int)Language.SPANISH_PANAMA);
        G_LANGUAGE_MAP_DOCX.addEntry("es-PY", (int)Language.SPANISH_PARAGUAY);
        G_LANGUAGE_MAP_DOCX.addEntry("es-PE", (int)Language.SPANISH_PERU);
        G_LANGUAGE_MAP_DOCX.addEntry("es-PR", (int)Language.SPANISH_PUERTO_RICO);
        G_LANGUAGE_MAP_DOCX.addEntry("es-ES", (int)Language.SPANISH_SPAIN_MODERN_SORT);
        G_LANGUAGE_MAP_DOCX.addEntry("es-ES_tradnl", (int)Language.SPANISH_SPAIN_TRADITIONAL_SORT);
        G_LANGUAGE_MAP_DOCX.addEntry("es-UY", (int)Language.SPANISH_URUGUAY);
        G_LANGUAGE_MAP_DOCX.addEntry("es-VE", (int)Language.SPANISH_VENEZUELA);
        G_LANGUAGE_MAP_DOCX.addEntry("st-ZA", (int)Language.SUTU);
        G_LANGUAGE_MAP_DOCX.addEntry("sv", (int)Language.SWEDISH);
        G_LANGUAGE_MAP_DOCX.addEntry("sv-FI", (int)Language.SWEDISH_FINLAND);
        G_LANGUAGE_MAP_DOCX.addEntry("sv-SE", (int)Language.SWEDISH_SWEDEN);
        G_LANGUAGE_MAP_DOCX.addEntry("syr", (int)Language.SYRIAC);
        G_LANGUAGE_MAP_DOCX.addEntry("syr-SY", (int)Language.SYRIAC_SYRIA);
        G_LANGUAGE_MAP_DOCX.addEntry("tg-Cyrl-TJ", (int)Language.TAJIK);
        G_LANGUAGE_MAP_DOCX.addEntry("tzm-Arab-MA", (int)Language.TAMAZIGHT);
        G_LANGUAGE_MAP_DOCX.addEntry("tzm-Latn-DZ", (int)Language.TAMAZIGHT_LATIN);
        G_LANGUAGE_MAP_DOCX.addEntry("ta", (int)Language.TAMIL);
        G_LANGUAGE_MAP_DOCX.addEntry("ta-IN", (int)Language.TAMIL_INDIA);
        G_LANGUAGE_MAP_DOCX.addEntry("tt", (int)Language.TATAR);
        G_LANGUAGE_MAP_DOCX.addEntry("tt-RU", (int)Language.TATAR_RUSSIA);
        G_LANGUAGE_MAP_DOCX.addEntry("te", (int)Language.TELUGU);
        G_LANGUAGE_MAP_DOCX.addEntry("te-IN", (int)Language.TELUGU_INDIA);
        G_LANGUAGE_MAP_DOCX.addEntry("th", (int)Language.THAI);
        G_LANGUAGE_MAP_DOCX.addEntry("th-TH", (int)Language.THAI_THAILAND);
        G_LANGUAGE_MAP_DOCX.addEntry("bo-BT", (int)Language.TIBETAN_BUTAN);
        G_LANGUAGE_MAP_DOCX.addEntry("bo-CN", (int)Language.TIBETAN_CHINA);
        G_LANGUAGE_MAP_DOCX.addEntry("ti-ER", (int)Language.TIGRIGNA_ERITREA);
        G_LANGUAGE_MAP_DOCX.addEntry("ti-ET", (int)Language.TIGRIGNA_ETHIOPIA);
        G_LANGUAGE_MAP_DOCX.addEntry("ts-ZA", (int)Language.TSONGA);
        G_LANGUAGE_MAP_DOCX.addEntry("tn-ZA", (int)Language.TSWANA);
        G_LANGUAGE_MAP_DOCX.addEntry("tr", (int)Language.TURKISH);
        G_LANGUAGE_MAP_DOCX.addEntry("tr-TR", (int)Language.TURKISH_TURKEY);
        G_LANGUAGE_MAP_DOCX.addEntry("tk-TM", (int)Language.TURKMEN);
        G_LANGUAGE_MAP_DOCX.addEntry("uk", (int)Language.UKRAINIAN);
        G_LANGUAGE_MAP_DOCX.addEntry("uk-UA", (int)Language.UKRAINIAN_UKRAINE);
        G_LANGUAGE_MAP_DOCX.addEntry("ur", (int)Language.URDU);
        G_LANGUAGE_MAP_DOCX.addEntry("ur-PK", (int)Language.URDU_PAKISTAN);
        G_LANGUAGE_MAP_DOCX.addEntry("ur-IN", (int)Language.URDU_INDIAN);
        G_LANGUAGE_MAP_DOCX.addEntry("uz", (int)Language.UZBEK);
        G_LANGUAGE_MAP_DOCX.addEntry("uz-Cyrl-UZ", (int)Language.UZBEK_CYRILLIC);
        G_LANGUAGE_MAP_DOCX.addEntry("uz-Latn-UZ", (int)Language.UZBEK_LATIN);
        G_LANGUAGE_MAP_DOCX.addEntry("ve-ZA", (int)Language.VENDA);
        G_LANGUAGE_MAP_DOCX.addEntry("vi", (int)Language.VIETNAMESE);
        G_LANGUAGE_MAP_DOCX.addEntry("vi-VN", (int)Language.VIETNAMESE_VIETNAM);
        G_LANGUAGE_MAP_DOCX.addEntry("cy-GB", (int)Language.WELSH);
        G_LANGUAGE_MAP_DOCX.addEntry("xh-ZA", (int)Language.XHOSA);
        G_LANGUAGE_MAP_DOCX.addEntry("ii-CN", (int)Language.YI);
        G_LANGUAGE_MAP_DOCX.addEntry("yi-Hebr", (int)Language.YIDDISH);
        G_LANGUAGE_MAP_DOCX.addEntry("yo-NG", (int)Language.YORUBA);
        G_LANGUAGE_MAP_DOCX.addEntry("zu-ZA", (int)Language.ZULU);

        additionalLanguageToMapWml("AF", (int)Language.AFRIKAANS);
        G_LANGUAGE_MAP_WML.addEntry("AF", (int)Language.AFRIKAANS_SOUTH_AFRICA);
        additionalLanguageToMapWml("SQ", (int)Language.ALBANIAN);
        G_LANGUAGE_MAP_WML.addEntry("SQ", (int)Language.ALBANIAN_ALBANIA);
        G_LANGUAGE_MAP_WML.addEntry("GSW-FR", (int)Language.ALSATIAN);
        G_LANGUAGE_MAP_WML.addEntry("AMH", (int)Language.AMHARIC);
        G_LANGUAGE_MAP_WML.addEntry("AR-DZ", (int)Language.ARABIC_ALGERIA);
        G_LANGUAGE_MAP_WML.addEntry("AR-BH", (int)Language.ARABIC_BAHRAIN);
        G_LANGUAGE_MAP_WML.addEntry("AR-EG", (int)Language.ARABIC_EGYPT);
        G_LANGUAGE_MAP_WML.addEntry("AR-IQ", (int)Language.ARABIC_IRAQ);
        G_LANGUAGE_MAP_WML.addEntry("AR-JO", (int)Language.ARABIC_JORDAN);
        G_LANGUAGE_MAP_WML.addEntry("AR-KW", (int)Language.ARABIC_KUWAIT);
        G_LANGUAGE_MAP_WML.addEntry("AR-LB", (int)Language.ARABIC_LEBANON);
        G_LANGUAGE_MAP_WML.addEntry("AR-LY", (int)Language.ARABIC_LIBYA);
        G_LANGUAGE_MAP_WML.addEntry("AR-MA", (int)Language.ARABIC_MOROCCO);
        G_LANGUAGE_MAP_WML.addEntry("AR-OM", (int)Language.ARABIC_OMAN);
        G_LANGUAGE_MAP_WML.addEntry("AR-QA", (int)Language.ARABIC_QATAR);
        additionalLanguageToMapWml("AR-SA", (int)Language.ARABIC);
        G_LANGUAGE_MAP_WML.addEntry("AR-SA", (int)Language.ARABIC_SAUDI_ARABIA);
        G_LANGUAGE_MAP_WML.addEntry("AR-SY", (int)Language.ARABIC_SYRIA);
        G_LANGUAGE_MAP_WML.addEntry("AR-TN", (int)Language.ARABIC_TUNISIA);
        G_LANGUAGE_MAP_WML.addEntry("AR-AE", (int)Language.ARABIC_UAE);
        G_LANGUAGE_MAP_WML.addEntry("AR-YE", (int)Language.ARABIC_YEMEN);
        additionalLanguageToMapWml("HY", (int)Language.ARMENIAN);
        G_LANGUAGE_MAP_WML.addEntry("HY", (int)Language.ARMENIAN_ARMENIA);
        G_LANGUAGE_MAP_WML.addEntry("AS", (int)Language.ASSAMESE);
        G_LANGUAGE_MAP_WML.addEntry("AZ-CYR", (int)Language.AZERI_CYRILLIC);
        additionalLanguageToMapWml("AZ-LATIN", (int)Language.AZERI);
        G_LANGUAGE_MAP_WML.addEntry("AZ-LATIN", (int)Language.AZERI_LATIN);
        additionalLanguageToMapWml("EU", (int)Language.BASQUE);
        G_LANGUAGE_MAP_WML.addEntry("EU", (int)Language.BASQUE_BASQUE);
        additionalLanguageToMapWml("BE", (int)Language.BELARUSIAN);
        G_LANGUAGE_MAP_WML.addEntry("BE", (int)Language.BELARUSIAN_BELARUS);
        G_LANGUAGE_MAP_WML.addEntry("BN", (int)Language.BENGALI);
        G_LANGUAGE_MAP_WML.addEntry("0845", (int)Language.BENGALI_BANGLADESH);
        G_LANGUAGE_MAP_WML.addEntry("201A", (int)Language.BOSNIAN_CYRILLIC);
        G_LANGUAGE_MAP_WML.addEntry("141A", (int)Language.BOSNIAN_LATIN);
        G_LANGUAGE_MAP_WML.addEntry("BR-FR", (int)Language.BRETON);
        additionalLanguageToMapWml("BG", (int)Language.BULGARIAN);
        G_LANGUAGE_MAP_WML.addEntry("BG", (int)Language.BULGARIAN_BULGARIA);
        G_LANGUAGE_MAP_WML.addEntry("MY", (int)Language.BURMESE);
        additionalLanguageToMapWml("CA", (int)Language.CATALAN);
        G_LANGUAGE_MAP_WML.addEntry("CA", (int)Language.CATALAN_CATALAN);
        G_LANGUAGE_MAP_WML.addEntry("CHR", (int)Language.CHEROKEE);
        G_LANGUAGE_MAP_WML.addEntry("ZH-HK", (int)Language.CHINESE_HONG_KONG);
        G_LANGUAGE_MAP_WML.addEntry("ZH-MO", (int)Language.CHINESE_MACAO);
        G_LANGUAGE_MAP_WML.addEntry("ZH-CN", (int)Language.CHINESE_CHINA);
        G_LANGUAGE_MAP_WML.addEntry("ZH-SG", (int)Language.CHINESE_SINGAPORE);
        G_LANGUAGE_MAP_WML.addEntry("ZH-TW", (int)Language.CHINESE_TAIWAN);
        G_LANGUAGE_MAP_WML.addEntry("SH", (int)Language.CROATIAN_BOZNIA_AND_HERZEGOVINA);
        additionalLanguageToMapWml("HR", (int)Language.CROATIAN);
        G_LANGUAGE_MAP_WML.addEntry("HR", (int)Language.CROATIAN_CROATIA);
        additionalLanguageToMapWml("CS", (int)Language.CZECH);
        G_LANGUAGE_MAP_WML.addEntry("CS", (int)Language.CZECH_CZECH_REPUBLIC);
        additionalLanguageToMapWml("DA", (int)Language.DANISH);
        G_LANGUAGE_MAP_WML.addEntry("DA", (int)Language.DANISH_DENMARK);
        additionalLanguageToMapWml("DIV", (int)Language.DIVEHI);
        G_LANGUAGE_MAP_WML.addEntry("DIV", (int)Language.DIVEHI_MALDIVES);
        additionalLanguageToMapWml("NL", (int)Language.DUTCH);
        G_LANGUAGE_MAP_WML.addEntry("NL", (int)Language.DUTCH_NETHERLANDS);
        G_LANGUAGE_MAP_WML.addEntry("NL-BE", (int)Language.DUTCH_BELGIUM);
        G_LANGUAGE_MAP_WML.addEntry("0466", (int)Language.EDO);
        G_LANGUAGE_MAP_WML.addEntry("EN-AU", (int)Language.ENGLISH_AUSTRALIA);
        G_LANGUAGE_MAP_WML.addEntry("EN-BZ", (int)Language.ENGLISH_BELIZE);
        G_LANGUAGE_MAP_WML.addEntry("EN-CA", (int)Language.ENGLISH_CANADA);
        G_LANGUAGE_MAP_WML.addEntry("EN-CARRIBEAN", (int)Language.ENGLISH_CARIBBEAN);
        G_LANGUAGE_MAP_WML.addEntry("EN-HK", (int)Language.ENGLISH_HONG_KONG);
        G_LANGUAGE_MAP_WML.addEntry("EN-IN", (int)Language.ENGLISH_INDIA);
        G_LANGUAGE_MAP_WML.addEntry("EN-ID", (int)Language.ENGLISH_INDONESIA);
        G_LANGUAGE_MAP_WML.addEntry("EN-IE", (int)Language.ENGLISH_IRELAND);
        G_LANGUAGE_MAP_WML.addEntry("EN-JM", (int)Language.ENGLISH_JAMAICA);
        G_LANGUAGE_MAP_WML.addEntry("EN-MY", (int)Language.ENGLISH_MALAYSIA);
        G_LANGUAGE_MAP_WML.addEntry("EN-NZ", (int)Language.ENGLISH_NEW_ZEALAND);
        G_LANGUAGE_MAP_WML.addEntry("EN-PH", (int)Language.ENGLISH_PHILIPPINES);
        G_LANGUAGE_MAP_WML.addEntry("EN-SG", (int)Language.ENGLISH_SINGAPORE);
        G_LANGUAGE_MAP_WML.addEntry("EN-ZA", (int)Language.ENGLISH_SOUTH_AFRICA);
        G_LANGUAGE_MAP_WML.addEntry("EN-TT", (int)Language.ENGLISH_TRINIDAD_AND_TOBAGO);
        G_LANGUAGE_MAP_WML.addEntry("EN-GB", (int)Language.ENGLISH_UK);
        additionalLanguageToMapWml("EN-US", (int)Language.ENGLISH);
        G_LANGUAGE_MAP_WML.addEntry("EN-US", (int)Language.ENGLISH_US);
        G_LANGUAGE_MAP_WML.addEntry("EN-ZW", (int)Language.ENGLISH_ZIMBABWE);
        additionalLanguageToMapWml("ET", (int)Language.ESTONIAN);
        G_LANGUAGE_MAP_WML.addEntry("ET", (int)Language.ESTONIAN_ESTONIA);
        additionalLanguageToMapWml("FO", (int)Language.FAEROESE);
        G_LANGUAGE_MAP_WML.addEntry("FO", (int)Language.FAEROESE_FAROE_ISLANDS);
        G_LANGUAGE_MAP_WML.addEntry("0464", (int)Language.FILIPINO);
        additionalLanguageToMapWml("FI", (int)Language.FINNISH);
        G_LANGUAGE_MAP_WML.addEntry("FI", (int)Language.FINNISH_FINLAND);
        G_LANGUAGE_MAP_WML.addEntry("FR-BE", (int)Language.FRENCH_BELGIUM);
        G_LANGUAGE_MAP_WML.addEntry("FR-CM", (int)Language.FRENCH_CAMEROON);
        G_LANGUAGE_MAP_WML.addEntry("FR-CA", (int)Language.FRENCH_CANADA);
        G_LANGUAGE_MAP_WML.addEntry("FR-CD", (int)Language.FRENCH_CONGO);
        G_LANGUAGE_MAP_WML.addEntry("FR-CI", (int)Language.FRENCH_COTE_D_IVOIRE);
        additionalLanguageToMapWml("FR", (int)Language.FRENCH);
        G_LANGUAGE_MAP_WML.addEntry("FR", (int)Language.FRENCH_FRANCE);
        G_LANGUAGE_MAP_WML.addEntry("FR-HT", (int)Language.FRENCH_HAITI);
        G_LANGUAGE_MAP_WML.addEntry("FR-LU", (int)Language.FRENCH_LUXEMBOURG);
        G_LANGUAGE_MAP_WML.addEntry("FR-ML", (int)Language.FRENCH_MALI);
        G_LANGUAGE_MAP_WML.addEntry("FR-MC", (int)Language.FRENCH_MONACO);
        G_LANGUAGE_MAP_WML.addEntry("FR-MA", (int)Language.FRENCH_MOROCCO);
        G_LANGUAGE_MAP_WML.addEntry("FR-RE", (int)Language.FRENCH_REUNION);
        G_LANGUAGE_MAP_WML.addEntry("FR-SN", (int)Language.FRENCH_SENEGAL);
        G_LANGUAGE_MAP_WML.addEntry("FR-CH", (int)Language.FRENCH_SWITZERLAND);
        G_LANGUAGE_MAP_WML.addEntry("FR-WINDIES", (int)Language.FRENCH_WEST_INDIES);
        G_LANGUAGE_MAP_WML.addEntry("FY", (int)Language.FRISIAN_NETHERLANDS);
        G_LANGUAGE_MAP_WML.addEntry("0467", (int)Language.FULFULDE);
        G_LANGUAGE_MAP_WML.addEntry("GD", (int)Language.GAELIC_SCOTLAND);
        additionalLanguageToMapWml("GL", (int)Language.GALICIAN);
        G_LANGUAGE_MAP_WML.addEntry("GL", (int)Language.GALICIAN_GALICIAN);
        additionalLanguageToMapWml("GEO/KAT", (int)Language.GEORGIAN);
        G_LANGUAGE_MAP_WML.addEntry("GEO/KAT", (int)Language.GEORGIAN_GEORGIA);
        G_LANGUAGE_MAP_WML.addEntry("DE-AT", (int)Language.GERMAN_AUSTRIA);
        additionalLanguageToMapWml("DE", (int)Language.GERMAN);
        G_LANGUAGE_MAP_WML.addEntry("DE", (int)Language.GERMAN_GERMANY);
        G_LANGUAGE_MAP_WML.addEntry("DE-LI", (int)Language.GERMAN_LIECHTENSTEIN);
        G_LANGUAGE_MAP_WML.addEntry("DE-LU", (int)Language.GERMAN_LUXEMBOURG);
        G_LANGUAGE_MAP_WML.addEntry("DE-CH", (int)Language.GERMAN_SWITZERLAND);
        additionalLanguageToMapWml("EL", (int)Language.GREEK);
        G_LANGUAGE_MAP_WML.addEntry("EL", (int)Language.GREEK_GREECE);
        G_LANGUAGE_MAP_WML.addEntry("GN", (int)Language.GUARANI);
        additionalLanguageToMapWml("GU", (int)Language.GUJARATI);
        G_LANGUAGE_MAP_WML.addEntry("GU", (int)Language.GUJARATI_INDIA);
        G_LANGUAGE_MAP_WML.addEntry("HA", (int)Language.HAUSA);
        G_LANGUAGE_MAP_WML.addEntry("0475", (int)Language.HAWAIIAN);
        additionalLanguageToMapWml("HE", (int)Language.HEBREW);
        G_LANGUAGE_MAP_WML.addEntry("HE", (int)Language.HEBREW_ISRAEL);
        additionalLanguageToMapWml("HI", (int)Language.HINDI);
        G_LANGUAGE_MAP_WML.addEntry("HI", (int)Language.HINDI_INDIA);
        additionalLanguageToMapWml("HU", (int)Language.HUNGARIAN);
        G_LANGUAGE_MAP_WML.addEntry("HU", (int)Language.HUNGARIAN_HUNGARY);
        G_LANGUAGE_MAP_WML.addEntry("0469", (int)Language.IBIBIO);
        additionalLanguageToMapWml("IS", (int)Language.ICELANDIC);
        G_LANGUAGE_MAP_WML.addEntry("IS", (int)Language.ICELANDIC_ICELAND);
        G_LANGUAGE_MAP_WML.addEntry("0470", (int)Language.IGBO);
        additionalLanguageToMapWml("IN", (int)Language.INDONESIAN);
        G_LANGUAGE_MAP_WML.addEntry("IN", (int)Language.INDONESIAN_INDONESIA);
        G_LANGUAGE_MAP_WML.addEntry("IKU", (int)Language.INUKTITUT);
        G_LANGUAGE_MAP_WML.addEntry("085D", (int)Language.INUKTITUT_LATIN_CANADA);
        G_LANGUAGE_MAP_WML.addEntry("GA", (int)Language.IRISH_IRELAND);
        additionalLanguageToMapWml("IT", (int)Language.ITALIAN);
        G_LANGUAGE_MAP_WML.addEntry("IT", (int)Language.ITALIAN_ITALY);
        G_LANGUAGE_MAP_WML.addEntry("IT-CH", (int)Language.ITALIAN_SWITZERLAND);
        additionalLanguageToMapWml("JA", (int)Language.JAPANESE);
        G_LANGUAGE_MAP_WML.addEntry("JA", (int)Language.JAPANESE_JAPAN);
        additionalLanguageToMapWml("KN", (int)Language.KANNADA);
        G_LANGUAGE_MAP_WML.addEntry("KN", (int)Language.KANNADA_INDIA);
        G_LANGUAGE_MAP_WML.addEntry("0471", (int)Language.KANURI);
        G_LANGUAGE_MAP_WML.addEntry("KS-IN", (int)Language.KASHMIRI);
        G_LANGUAGE_MAP_WML.addEntry("KS", (int)Language.KASHMIRI_ARABIC);
        additionalLanguageToMapWml("KZ", (int)Language.KAZAKH);
        G_LANGUAGE_MAP_WML.addEntry("KZ", (int)Language.KAZAKH_KAZAKHSTAN);
        G_LANGUAGE_MAP_WML.addEntry("KHM", (int)Language.KHMER);
        additionalLanguageToMapWml("SW", (int)Language.KISWAHILI);
        G_LANGUAGE_MAP_WML.addEntry("SW", (int)Language.KISWAHILI_KENYA);
        additionalLanguageToMapWml("KOK", (int)Language.KONKANI);
        G_LANGUAGE_MAP_WML.addEntry("KOK", (int)Language.KONKANI_INDIA);
        additionalLanguageToMapWml("KO", (int)Language.KOREAN);
        G_LANGUAGE_MAP_WML.addEntry("KO", (int)Language.KOREAN_KOREA);
        additionalLanguageToMapWml("KY", (int)Language.KYRGYZ);
        G_LANGUAGE_MAP_WML.addEntry("KY", (int)Language.KYRGYZ_KYRGYZSTAN);
        G_LANGUAGE_MAP_WML.addEntry("LAO", (int)Language.LAO);
        G_LANGUAGE_MAP_WML.addEntry("LA", (int)Language.LATIN);
        additionalLanguageToMapWml("LV", (int)Language.LATVIAN);
        G_LANGUAGE_MAP_WML.addEntry("LV", (int)Language.LATVIAN_LATVIA);
        additionalLanguageToMapWml("LT", (int)Language.LITHUANIAN);
        G_LANGUAGE_MAP_WML.addEntry("LT", (int)Language.LITHUANIAN_LITHUANIA);
        G_LANGUAGE_MAP_WML.addEntry("046E", (int)Language.LUXEMBOUGISH_LUXEMBURG);
        additionalLanguageToMapWml("MK", (int)Language.MACEDONIAN);
        G_LANGUAGE_MAP_WML.addEntry("MK", (int)Language.MACEDONIAN_FYROM);
        additionalLanguageToMapWml("MS", (int)Language.MALAY);
        G_LANGUAGE_MAP_WML.addEntry("MS", (int)Language.MALAY_MALAYSIA);
        G_LANGUAGE_MAP_WML.addEntry("MS-BN", (int)Language.MALAY_BRUNEI_DARUSSALAM);
        G_LANGUAGE_MAP_WML.addEntry("ML", (int)Language.MALAYALAM);
        G_LANGUAGE_MAP_WML.addEntry("MT", (int)Language.MALTESE);
        G_LANGUAGE_MAP_WML.addEntry("MNI", (int)Language.MANIPURI);
        G_LANGUAGE_MAP_WML.addEntry("0481", (int)Language.MAORI);
        G_LANGUAGE_MAP_WML.addEntry("047A", (int)Language.MAPUDUNGUN_CHILE);
        additionalLanguageToMapWml("MR", (int)Language.MARATHI);
        G_LANGUAGE_MAP_WML.addEntry("MR", (int)Language.MARATHI_INDIA);
        G_LANGUAGE_MAP_WML.addEntry("047C", (int)Language.MOHAWK_MOHAWK);
        additionalLanguageToMapWml("MN", (int)Language.MONGOLIAN);
        G_LANGUAGE_MAP_WML.addEntry("MN", (int)Language.MONGOLIAN_CYRILLIC);
        G_LANGUAGE_MAP_WML.addEntry("MN-MN", (int)Language.MONGOLIAN_MONGOLIAN);
        G_LANGUAGE_MAP_WML.addEntry("NE", (int)Language.NEPALI);
        G_LANGUAGE_MAP_WML.addEntry("NE-IN", (int)Language.NEPALI_INDIA);
        additionalLanguageToMapWml("NO-BOK", (int)Language.NORWEGIAN);
        G_LANGUAGE_MAP_WML.addEntry("NO-BOK", (int)Language.NORWEGIAN_BOKMAL);
        G_LANGUAGE_MAP_WML.addEntry("NO-NYN", (int)Language.NORWEGIAN_NYNORSK);
        G_LANGUAGE_MAP_WML.addEntry("OR", (int)Language.ORIYA);
        G_LANGUAGE_MAP_WML.addEntry("OM", (int)Language.OROMO);
        G_LANGUAGE_MAP_WML.addEntry("0479", (int)Language.PAPIAMENTU);
        G_LANGUAGE_MAP_WML.addEntry("0463", (int)Language.PASHTO);
        additionalLanguageToMapWml("FA", (int)Language.PERSIAN);
        G_LANGUAGE_MAP_WML.addEntry("FA", (int)Language.PERSIAN_IRAN);
        additionalLanguageToMapWml("PL", (int)Language.POLISH);
        G_LANGUAGE_MAP_WML.addEntry("PL", (int)Language.POLISH_POLAND);
        additionalLanguageToMapWml("PT-BR", (int)Language.PORTUGUESE);
        G_LANGUAGE_MAP_WML.addEntry("PT-BR", (int)Language.PORTUGUESE_BRAZIL);
        G_LANGUAGE_MAP_WML.addEntry("PT", (int)Language.PORTUGUESE_PORTUGAL);
        additionalLanguageToMapWml("PA", (int)Language.PUNJABI);
        G_LANGUAGE_MAP_WML.addEntry("PA", (int)Language.PUNJABI_INDIA);
        G_LANGUAGE_MAP_WML.addEntry("0846", (int)Language.PUNJABI_PAKISTAN);
        G_LANGUAGE_MAP_WML.addEntry("046B", (int)Language.QUECHUA_BOLIVIA);
        G_LANGUAGE_MAP_WML.addEntry("086B", (int)Language.QUECHUA_ECUADOR);
        G_LANGUAGE_MAP_WML.addEntry("0C6B", (int)Language.QUECHUA_PERU);
        G_LANGUAGE_MAP_WML.addEntry("RO-MD", (int)Language.ROMANIAN_MOLDOVA);
        additionalLanguageToMapWml("RO", (int)Language.ROMANIAN);
        G_LANGUAGE_MAP_WML.addEntry("RO", (int)Language.ROMANIAN_ROMANIA);
        G_LANGUAGE_MAP_WML.addEntry("RM", (int)Language.ROMANSH_SWITZERLAND);
        G_LANGUAGE_MAP_WML.addEntry("RU-MD", (int)Language.RUSSIAN_MOLDOVA);
        additionalLanguageToMapWml("RU", (int)Language.RUSSIAN);
        G_LANGUAGE_MAP_WML.addEntry("RU", (int)Language.RUSSIAN_RUSSIA);
        G_LANGUAGE_MAP_WML.addEntry("243B", (int)Language.SAMI_INARI_FINLAND);
        G_LANGUAGE_MAP_WML.addEntry("103B", (int)Language.SAMI_LULE_NORWAY);
        G_LANGUAGE_MAP_WML.addEntry("143B", (int)Language.SAMI_LULE_SWEDEN);
        G_LANGUAGE_MAP_WML.addEntry("0C3B", (int)Language.SAMI_NORTHERN_FINLAND);
        G_LANGUAGE_MAP_WML.addEntry("I-SAMI-NO", (int)Language.SAMI_NORTHERN_NORWAY);
        G_LANGUAGE_MAP_WML.addEntry("083B", (int)Language.SAMI_NOTHERN_SWEDEN);
        G_LANGUAGE_MAP_WML.addEntry("203B", (int)Language.SAMI_SKOLT_FINLAND);
        G_LANGUAGE_MAP_WML.addEntry("183B", (int)Language.SAMI_SOUTHERN_NORWAY);
        G_LANGUAGE_MAP_WML.addEntry("1C3B", (int)Language.SAMI_SOUTHERN_SWEDEN);
        additionalLanguageToMapWml("SA", (int)Language.SANSKRIT);
        G_LANGUAGE_MAP_WML.addEntry("SA", (int)Language.SANSKRIT_INDIA);
        G_LANGUAGE_MAP_WML.addEntry("046C", (int)Language.SEPEDI);
        G_LANGUAGE_MAP_WML.addEntry("1C1A", (int)Language.SERBIAN_CYRILLIC_BOSNIA_AND_HERZEGOVINA);
        G_LANGUAGE_MAP_WML.addEntry("SR-CYR", (int)Language.SERBIAN_CYRILLIC_SERBIA_AND_MONTENEGRO);
        G_LANGUAGE_MAP_WML.addEntry("181A", (int)Language.SERBIAN_LATIN_BOSNIA_AND_HERZEGOVINA);
        additionalLanguageToMapWml("SR", (int)Language.SERBIAN);
        G_LANGUAGE_MAP_WML.addEntry("SR", (int)Language.SERBIAN_LATIN_SERBIA_AND_MONTENEGRO);
        G_LANGUAGE_MAP_WML.addEntry("0859", (int)Language.SINDHI);
        G_LANGUAGE_MAP_WML.addEntry("SD", (int)Language.SINDHI_DEVANAGARIC);
        G_LANGUAGE_MAP_WML.addEntry("045B", (int)Language.SINHALESE);
        additionalLanguageToMapWml("SK", (int)Language.SLOVAK);
        G_LANGUAGE_MAP_WML.addEntry("SK", (int)Language.SLOVAK_SLOVAKIA);
        additionalLanguageToMapWml("SL", (int)Language.SLOVENIAN);
        G_LANGUAGE_MAP_WML.addEntry("SL", (int)Language.SLOVENIAN_SLOVENIA);
        G_LANGUAGE_MAP_WML.addEntry("SO", (int)Language.SOMALI);
        G_LANGUAGE_MAP_WML.addEntry("SB", (int)Language.SORBIAN);
        G_LANGUAGE_MAP_WML.addEntry("ES-AR", (int)Language.SPANISH_ARGENTINA);
        G_LANGUAGE_MAP_WML.addEntry("ES-BO", (int)Language.SPANISH_BOLIVIA);
        G_LANGUAGE_MAP_WML.addEntry("ES-CL", (int)Language.SPANISH_CHILE);
        G_LANGUAGE_MAP_WML.addEntry("ES-CO", (int)Language.SPANISH_COLOMBIA);
        G_LANGUAGE_MAP_WML.addEntry("ES-CR", (int)Language.SPANISH_COSTA_RICA);
        G_LANGUAGE_MAP_WML.addEntry("ES-DO", (int)Language.SPANISH_DOMINICAN_REPUBLIC);
        G_LANGUAGE_MAP_WML.addEntry("ES-EC", (int)Language.SPANISH_ECUADOR);
        G_LANGUAGE_MAP_WML.addEntry("ES-SV", (int)Language.SPANISH_EL_SALVADOR);
        G_LANGUAGE_MAP_WML.addEntry("ES-GT", (int)Language.SPANISH_GUATEMALA);
        G_LANGUAGE_MAP_WML.addEntry("ES-HN", (int)Language.SPANISH_HONDURAS);
        G_LANGUAGE_MAP_WML.addEntry("ES-MX", (int)Language.SPANISH_MEXICO);
        G_LANGUAGE_MAP_WML.addEntry("ES-NI", (int)Language.SPANISH_NICARAGUA);
        G_LANGUAGE_MAP_WML.addEntry("ES-PA", (int)Language.SPANISH_PANAMA);
        G_LANGUAGE_MAP_WML.addEntry("ES-PY", (int)Language.SPANISH_PARAGUAY);
        G_LANGUAGE_MAP_WML.addEntry("ES-PE", (int)Language.SPANISH_PERU);
        G_LANGUAGE_MAP_WML.addEntry("ES-PR", (int)Language.SPANISH_PUERTO_RICO);
        additionalLanguageToMapWml("ES", (int)Language.SPANISH);
        G_LANGUAGE_MAP_WML.addEntry("ES", (int)Language.SPANISH_SPAIN_MODERN_SORT);
        G_LANGUAGE_MAP_WML.addEntry("ES-TRAD", (int)Language.SPANISH_SPAIN_TRADITIONAL_SORT);
        G_LANGUAGE_MAP_WML.addEntry("ES-UY", (int)Language.SPANISH_URUGUAY);
        G_LANGUAGE_MAP_WML.addEntry("ES-VE", (int)Language.SPANISH_VENEZUELA);
        G_LANGUAGE_MAP_WML.addEntry("SX", (int)Language.SUTU);
        G_LANGUAGE_MAP_WML.addEntry("SV-FI", (int)Language.SWEDISH_FINLAND);
        additionalLanguageToMapWml("SV", (int)Language.SWEDISH);
        G_LANGUAGE_MAP_WML.addEntry("SV", (int)Language.SWEDISH_SWEDEN);
        additionalLanguageToMapWml("SYR", (int)Language.SYRIAC);
        G_LANGUAGE_MAP_WML.addEntry("SYR", (int)Language.SYRIAC_SYRIA);
        G_LANGUAGE_MAP_WML.addEntry("TG", (int)Language.TAJIK);
        G_LANGUAGE_MAP_WML.addEntry("045F", (int)Language.TAMAZIGHT);
        G_LANGUAGE_MAP_WML.addEntry("085F", (int)Language.TAMAZIGHT_LATIN);
        additionalLanguageToMapWml("TA", (int)Language.TAMIL);
        G_LANGUAGE_MAP_WML.addEntry("TA", (int)Language.TAMIL_INDIA);
        additionalLanguageToMapWml("TT", (int)Language.TATAR);
        G_LANGUAGE_MAP_WML.addEntry("TT", (int)Language.TATAR_RUSSIA);
        additionalLanguageToMapWml("TE", (int)Language.TELUGU);
        G_LANGUAGE_MAP_WML.addEntry("TE", (int)Language.TELUGU_INDIA);
        additionalLanguageToMapWml("TH", (int)Language.THAI);
        G_LANGUAGE_MAP_WML.addEntry("TH", (int)Language.THAI_THAILAND);
        G_LANGUAGE_MAP_WML.addEntry("0851", (int)Language.TIBETAN_BUTAN);
        G_LANGUAGE_MAP_WML.addEntry("BO", (int)Language.TIBETAN_CHINA);
        G_LANGUAGE_MAP_WML.addEntry("TI-ER", (int)Language.TIGRIGNA_ERITREA);
        G_LANGUAGE_MAP_WML.addEntry("TI-ET", (int)Language.TIGRIGNA_ETHIOPIA);
        G_LANGUAGE_MAP_WML.addEntry("TS", (int)Language.TSONGA);
        G_LANGUAGE_MAP_WML.addEntry("TN", (int)Language.TSWANA);
        additionalLanguageToMapWml("TR", (int)Language.TURKISH);
        G_LANGUAGE_MAP_WML.addEntry("TR", (int)Language.TURKISH_TURKEY);
        G_LANGUAGE_MAP_WML.addEntry("TK", (int)Language.TURKMEN);
        additionalLanguageToMapWml("UK", (int)Language.UKRAINIAN);
        G_LANGUAGE_MAP_WML.addEntry("UK", (int)Language.UKRAINIAN_UKRAINE);
        additionalLanguageToMapWml("ER", (int)Language.URDU);
        G_LANGUAGE_MAP_WML.addEntry("ER", (int)Language.URDU_PAKISTAN);
        G_LANGUAGE_MAP_WML.addEntry("UZ-CYR", (int)Language.UZBEK_CYRILLIC);
        additionalLanguageToMapWml("UZ", (int)Language.UZBEK);
        G_LANGUAGE_MAP_WML.addEntry("UZ", (int)Language.UZBEK_LATIN);
        G_LANGUAGE_MAP_WML.addEntry("VEN", (int)Language.VENDA);
        additionalLanguageToMapWml("VI", (int)Language.VIETNAMESE);
        G_LANGUAGE_MAP_WML.addEntry("VI", (int)Language.VIETNAMESE_VIETNAM);
        G_LANGUAGE_MAP_WML.addEntry("CY", (int)Language.WELSH);
        G_LANGUAGE_MAP_WML.addEntry("XH", (int)Language.XHOSA);
        G_LANGUAGE_MAP_WML.addEntry("0478", (int)Language.YI);
        G_LANGUAGE_MAP_WML.addEntry("JI", (int)Language.YIDDISH);
        G_LANGUAGE_MAP_WML.addEntry("YO", (int)Language.YORUBA);
        G_LANGUAGE_MAP_WML.addEntry("ZU", (int)Language.ZULU);
    }
}

