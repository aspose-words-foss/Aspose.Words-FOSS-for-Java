// A Java port, made by Aspose, of a .NET Framework class. Microsoft publishes the source of that class
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/globalization/culturedata.cs
//
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

package com.aspose.ms.System.Globalization;

import com.aspose.ms.System.msString;
import com.aspose.words.net.System.Globalization.CultureNotFoundException;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class CultureData implements Cloneable
{

    public Calendar getDefaultCalendar()
    {
        if (mDefaultCalendarId == 0)
            mDefaultCalendarId = this.getCalendarIds()[0];

        return msCultureInfo.getCalendarInstance(mDefaultCalendarId);
    }

    public int[] getCalendarIds()
    {
        return waCalendars;
    }

    public void setCalendarIds(int[] ids)
    {
        waCalendars = ids;
    }

    public String getNonSortName()
    {
        return sName;
    }

    public String getCultureName()
    {
        // since windows doesn't know about zh-CHS and zh-CHT,
        // we leave sRealName == zh-Hanx but we still need to
        // pretend that it was zh-CHX.

        if (sName != null)
        {
            switch (this.sName)
            {
                case "zh-CHS":
                case "zh-CHT":
                    return this.sName;
            }
        }

        return sRealName;
    }

    public void setDefaultCalendarId(int id)
    {
        mDefaultCalendarId = id;
    }

    public String getLocalizedDisplayName()
    {
        return sLocalizedDisplayName;
    }

    void setLocalizedDisplayName(String val)
    {
        sLocalizedDisplayName = val;
    }

    public String getEngDisplayName()
    {
        return sEnglishDisplayName;
    }

    void setEngDisplayName(String val)
    {
        sEnglishDisplayName = val;
    }

    public String getNativeDisplayName()
    {
        return sNativeDisplayName;
    }

    void setNativeDisplayName(String val)
    {
        sNativeDisplayName = val;
    }

    public String getISO639LangName2()
    {
        return sISO639Language2;
    }

    void setISO639LangName2(String val)
    {
        sISO639Language2 = val;
    }

    public String getAbbrevLangName()
    {
        return sAbbrevLang;
    }

    void setAbbrevLangName(String val)
    {
        sAbbrevLang = val;
    }

    public String getISO639LangName()
    {
        return sISO639Language;
    }

    void setISO639LangName(String val)
    {
        sISO639Language = val;
    }

    public int getLanguage()
    {
        return iLanguage;
    }

    void setLanguage(int val)
    {
        iLanguage = val;
    }

    public String getName()
    {
        return sName;
    }

    void setName(String val)
    {
        sName = val;
    }

    void setCalendarData(CalendarData calendarData)
    {
        if (calendars == null || calendars.length == 0)
            calendars = new CalendarData[1];

        calendars[0] = calendarData;
    }

    CalendarData getCalendarData()
    {
        return calendars.length > 0 ? calendars[0] : new CalendarData();
    }

    void setCultureType(int type)
    {
        this.cultureType = type;
    }

    void setListSeparator(String listSeparator)
    {
        sListSeparator = listSeparator;
    }

    String getListSeparator()
    {
        return sListSeparator;
    }

    void setANSICodePage(int ansiCodePage)
    {
        iAnsiCodePage = ansiCodePage;
    }

    int getANSICodePage()
    {
        return iAnsiCodePage;
    }

    void setNeutralCulture(boolean flag)
    {
        bNeutral = flag;
    }

    Locale getLocale()
    {
        if (mLocale == null)
            setLocale(sRealName);

        return mLocale;
    }

    void setLocale(String locale)
    {
        LanguageCountryPair languageCountry = new LanguageCountryPair(locale);
        this.mLocale = new Locale(languageCountry.language, languageCountry.country);
    }

    // We'd rather people use the named version since this doesn't allow custom locales
    public static CultureData getCultureData(int culture, boolean useUserOverride)
    {
        String localeName = null;
        CultureData retVal = null;

        // Convert the lcid to a name, then use that
        // Note that this'll return neutral names (unlike Vista native API)
        localeName = LCIDToLocaleName(culture);

        // If its not valid, then throw
        if (msString.isNullOrEmpty(localeName))
        {
            // Could be valid for Invariant
            if (culture == 0x007f)
                return getInvariant();
        }
        else
        {
            switch (localeName)
            {
                // for compatibility with Whidbey, when requesting
                // a locale from LCID, return the old localeName
                case "zh-Hans":
                    localeName = "zh-CHS";
                    break;
                case "zh-Hant":
                    localeName = "zh-CHT";
                    break;
            }

            // Valid name, use it
            retVal = getCultureData(localeName, useUserOverride);
        }

        if (retVal == null)
            throw new CultureNotFoundException(culture);

        // Return the one we found
        return retVal;
    }

    /**
     * Returns cultures by their {@link CultureTypes}.
     * <p>
     * WARNING: This method is heavy, it reads the culture names first then populate the created CultureData objects.
     * All the newly created CultureData will be cached in memory.
     * <p>
     * At the moment, there is a only method  SystemPal#getSpecificCultures() which call this method
     * but SystemPal#getSpecificCultures() itself is not used anywhere.
     * <p>
     * I believe we shouldn't retrieve all the cultures in Java so in the case if we do need this method
     * then the method will be improved or .NET will be changed to avoid using this method.
     *
     * @param types
     * @return cultures by the given type
     */
    public static msCultureInfo[] getCultures(int types)
    {
        if ((int) types <= 0 || ((int) types & (int) ~(CultureTypes.NEUTRAL_CULTURES | CultureTypes.SPECIFIC_CULTURES | CultureTypes.INSTALLED_WIN_32_CULTURES)) != 0)
            throw new IllegalArgumentException("See CultureTypes for the valid values");

        List<CultureData> cultureNames = CultureDataLoader.getCultureNamesByType(types);
        if (cultureNames.isEmpty())
            return new msCultureInfo[0];

        msCultureInfo[] cultures = new msCultureInfo[cultureNames.size()];
        for (int i = 0; i < cultureNames.size(); i++)
            cultures[i] = new msCultureInfo(cultureNames.get(i));

        return cultures;
    }

    // Obtain locale name from LCID
    // NOTE: This will get neutral names, unlike the OS API
    private static String LCIDToLocaleName(int culture)
    {
        if (!gLcidToCultureName.containsKey(culture))
            gLcidToCultureName.put(culture, CultureDataLoader.LCIDToLocaleName(culture));

        return gLcidToCultureName.get(culture);
    }

    static CultureData getInvariant()
    {
        if (s_Invariant == null)
        {
            // Make a new culturedata
            CultureData invariant = new CultureData();

            // Basics
            // Note that we override the resources since this IS NOT supposed to change (by definition)
            invariant.bUseOverrides = false;
            invariant.sRealName = "";                     // Name you passed in (ie: en-US, en, or de-DE_phoneb)
            invariant.sWindowsName = "";                     // Name OS thinks the object is (ie: de-DE_phoneb, or en-US (even if en was passed in))

            // Identity
            invariant.sName = "";                     // locale name (ie: en-us)
            invariant.sParent = "";                     // Parent name (which may be a custom locale/culture)
            invariant.bNeutral = false;                   // Flags for the culture (ie: neutral or not right now)

            invariant.sEnglishDisplayName = "Invariant Language (Invariant Country)"; // English pretty name for this locale
            invariant.sNativeDisplayName = "Invariant Language (Invariant Country)";  // Native pretty name for this locale
            invariant.sLocalizedDisplayName = "Invariant Language (Invariant Country)";
            invariant.sSpecificCulture = "";                     // The culture name to be used in CultureInfo.CreateSpecificCulture()

            // Language
            invariant.sISO639Language = "iv";                   // ISO 639 Language Name
            invariant.sLocalizedLanguage = "Invariant Language";   // Display name for this Language
            invariant.sEnglishLanguage = "Invariant Language";   // English name for this language
            invariant.sNativeLanguage = "Invariant Language";   // Native name of this language

            // Region
            invariant.sRegionName = "IV";                   // (RegionInfo)
            // Unused for now:
            //            invariant.iCountry              =1;                      // ---- code (RegionInfo)
            invariant.iGeoId = 244;                    // GeoId (Windows Only)
            invariant.sEnglishCountry = "Invariant Country";    // english country name (RegionInfo)
            invariant.sNativeCountry = "Invariant Country";    // native country name (Windows Only)
            invariant.sISO3166CountryName = "IV";                   // (RegionInfo), ie: US

            // Numbers
            invariant.sPositiveSign = "+";                    // positive sign
            invariant.sNegativeSign = "-";                    // negative sign
            invariant.saNativeDigits = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}; // native characters for digits 0-9
            invariant.iDigitSubstitution = 1;                      // Digit substitution 0=context, 1=none/arabic, 2=Native/national (2 seems to be unused) (Windows Only)
            invariant.iLeadingZeros = 1;                      // leading zeros 0=no leading zeros, 1=leading zeros
            invariant.iDigits = 2;                      // number of fractional digits
            invariant.iNegativeNumber = 1;                      // negative number format
            invariant.waGrouping = new int[]{3};          // grouping of digits
            invariant.sDecimalSeparator = ".";                    // decimal separator
            invariant.sThousandSeparator = ",";                    // thousands separator
            invariant.sNaN = "NaN";                  // Not a Number
            invariant.sPositiveInfinity = "Infinity";             // + Infinity
            invariant.sNegativeInfinity = "-Infinity";            // - Infinity

            // Percent
            invariant.iNegativePercent = 0;                      // Negative Percent (0-3)
            invariant.iPositivePercent = 0;                      // Positive Percent (0-11)
            invariant.sPercent = "%";                    // Percent (%) symbol
            invariant.sPerMille = "\\x2030";               // PerMille(‰) symbol

            // Currency
            invariant.sCurrency = "\\x00a4";                // local monetary symbol "¤: for international monetary symbol
            invariant.sIntlMonetarySymbol = "XDR";                  // international monetary symbol (RegionInfo)
            invariant.sEnglishCurrency = "International Monetary Fund"; // English name for this currency (Windows Only)
            invariant.sNativeCurrency = "International Monetary Fund"; // Native name for this currency (Windows Only)
            invariant.iCurrencyDigits = 2;                      // # local monetary fractional digits
            invariant.iCurrency = 0;                      // positive currency format
            invariant.iNegativeCurrency = 0;                      // negative currency format
            invariant.waMonetaryGrouping = new int[]{3};          // monetary grouping of digits
            invariant.sMonetaryDecimal = ".";                    // monetary decimal separator
            invariant.sMonetaryThousand = ",";                    // monetary thousands separator

            // Misc
            invariant.iMeasure = 0;                      // system of measurement 0=metric, 1=US (RegionInfo)
            invariant.sListSeparator = ",";                    // list separator
            invariant.iAnsiCodePage = 1252;
            // Unused for now:
            //            invariant.iPaperSize            =9;                      // default paper size (RegionInfo)
            //            invariant.waFontSignature       ="\x0002\x0000\x0000\x0000\x0000\x0000\x0000\x8000\x0001\x0000\x0000\x8000\x0001\x0000\x0000\x8000"; // Font signature (16 WORDS) (Windows Only)

            // Time
            invariant.sAM1159 = "AM";                   // AM designator
            invariant.sPM2359 = "PM";                   // PM designator
            invariant.saLongTimes = new String[]{"HH:mm:ss"};                             // time format
            invariant.saShortTimes = new String[]{"HH:mm", "hh:mm tt", "H:mm", "h:mm tt"}; // short time format
            invariant.saDurationFormats = new String[]{"HH:mm:ss"};                             // time duration format

            // Calendar specific data
            invariant.iFirstDayOfWeek = 0;                      // first day of week
            invariant.iFirstWeekOfYear = 0;                      // first week of year
            invariant.waCalendars = new int[]{(int) Calendar.CAL_GREGORIAN};       // all available calendar type(s).  The first one is the default calendar

            // Store for specific data about each calendar
            invariant.calendars = new CalendarData[CalendarData.MAX_CALENDARS];
            invariant.calendars[0] = CalendarData.getInvariant();

            // Text information
            invariant.iReadingLayout = 0;                      // Reading Layout = RTL

            invariant.sTextInfo = "";                     // Text info name to use for custom
            invariant.sCompareInfo = "";                     // Compare info name (including sorting key) to use if custom
            invariant.sScripts = "Latn;";                // Typical Scripts for this locale (latn,cyrl, etc)

            // These are desktop only, not coreclr
            invariant.iLanguage = 0x007f;                 // locale ID (0409) - NO sort information
            invariant.iDefaultAnsiCodePage = 1252;                   // default ansi code page ID (ACP)
            invariant.iDefaultOemCodePage = 437;                    // default oem code page ID (OCP or OEM)
            invariant.iDefaultMacCodePage = 10000;                  // default macintosh code page
            invariant.iDefaultEbcdicCodePage = 037;                    // default EBCDIC code page
            invariant.sAbbrevLang = "IVL";                  // abbreviated language name (Windows Language Name)
            invariant.sAbbrevCountry = "IVC";                  // abbreviated country name (RegionInfo) (Windows Region Name)
            invariant.sISO639Language2 = "ivl";                  // 3 char ISO 639 lang name 2
            invariant.sISO3166CountryName2 = "ivc";                  // 3 char ISO 3166 country name 2 2(RegionInfo)
            invariant.iInputLanguageHandle = 0x007f;                 // input language handle
            invariant.sConsoleFallbackName = "";                     // The culture name for the console fallback UI culture
            invariant.sKeyboardsToInstall = "0409:00000409";        // Keyboard installation string.

            // Remember it
            s_Invariant = invariant;
        }
        return s_Invariant;
    }

    public static CultureData getCultureData(String cultureName, boolean useUserOverride)
    {
        if (msString.isNullOrEmpty(cultureName))
            return CultureData.getInvariant();

        CultureData culture = gCultureNameToData.get(cultureName);
        if (culture != null)
            return culture;

        // Not found in the hash table, need to see if we can build one that works for us
        culture = createCultureData(cultureName, useUserOverride);
        if (culture == null)
            return null;

        gCultureNameToData.put(cultureName, culture); // hashName
        return culture;
    }

    private static CultureData createCultureData(String cultureName, boolean useUserOverride)
    {
        CultureData culture = new CultureData();
        culture.bUseOverrides = useUserOverride;
        culture.sRealName = cultureName;

        // Ask native code if that one's real
        if (culture.initCultureData())
            return culture;

        LanguageCountryPair languageCountry = new LanguageCountryPair(cultureName);
        if (msString.isNullOrEmpty(languageCountry.language))
            return getInvariant();

        culture.sRealName = languageCountry.language;

        if (culture.initCultureData())
            return getUnknownLocaleCulture(culture, languageCountry);

        return null;
    }

    static CultureData getUnknownLocaleCulture(CultureData cultureData, LanguageCountryPair languageCountryPair)
    {
        cultureData.sRealName = languageCountryPair.cultureName;
        cultureData.setName(languageCountryPair.cultureName);
        cultureData.setLanguage(4096);
        String unknownLocaleName = languageCountryPair.hasLanguageAndCountry()
                ? msString.format("Unknown Locale ({0})", languageCountryPair.cultureName)
                : msString.format("Unknown Language ({0})", languageCountryPair.cultureName);
        cultureData.setLocalizedDisplayName(unknownLocaleName);
        cultureData.setEngDisplayName(unknownLocaleName);
        cultureData.setNativeDisplayName(unknownLocaleName);
        return cultureData;
    }

    static CultureData getUnknownLocaleCulture(CultureData cultureData, String cultureName)
    {
        return getUnknownLocaleCulture(cultureData, new LanguageCountryPair(cultureName));
    }


    private boolean initCultureData()
    {
        return CultureDataLoader.loadCultureData(this);
    }

    private static class LanguageCountryPair
    {
        public LanguageCountryPair(String candidate)
        {
            this.cultureName = candidate;
            if (msString.isNullOrEmpty(candidate) || "Invariant".equals(candidate))
            {
                language = "en";
                country = "US";
            }

            else
            {
                String[] loc = candidate.split("-");
                if (loc.length > 1)
                {
                    language = loc[0];
                    country = loc[1];
                }
                else
                {
                    language = loc[0];
                    country = msString.Empty;
                }
            }
        }

        public boolean hasLanguageAndCountry()
        {
            return !msString.isNullOrEmpty(language) && !msString.isNullOrEmpty(country);
        }

        private final String cultureName;
        private String language;
        private String country;
    }

    private static final int undef = -1;
    private static final Map<String, CultureData> gCultureNameToData = new ConcurrentHashMap<>();
    private volatile static CultureData s_Invariant;

    public CultureData()
    {
        // intentionally left blank
    }

    CultureData(String realName)
    {
        // user for test purposes
        this.sRealName = realName;
    }

    static
    {
        // getCultureData("en-NZ", false);
        //    getCultureData("ru-RU", false);
    }

    // Override flag
    private String sRealName; // Name you passed in (ie: en-US, en, or de-DE_phoneb)
    private String sWindowsName; // Name OS thinks the object is (ie: de-DE_phoneb, or en-US (even if en was passed in))
    // Identity
    private String sName; // locale name (ie: en-us, NO sort info, but could be neutral)
    private String sParent; // Parent name (which may be a custom locale/culture)
    private String sLocalizedDisplayName; // Localized pretty name for this locale
    private String sEnglishDisplayName; // English pretty name for this locale
    private String sNativeDisplayName; // Native pretty name for this locale
    private String sSpecificCulture; // The culture name to be used in CultureInfo.CreateSpecificCulture(), en-US form if neutral, sort name if sort
    // Language
    private String sISO639Language; // ISO 639 Language Name
    private String sLocalizedLanguage; // Localized name for this language
    private String sEnglishLanguage; // English name for this language
    private String sNativeLanguage; // Native name of this language
    // Region
    private String sRegionName; // (RegionInfo)
    //        private int    iCountry=undef           ; // (user can override) ---- code (RegionInfo)
    private int iGeoId = undef; // GeoId
    private String sLocalizedCountry; // localized country name
    private String sEnglishCountry; // english country name (RegionInfo)
    private String sNativeCountry; // native country name
    private String sISO3166CountryName; // ISO 3166 (RegionInfo), ie: US
    // Numbers
    private String sPositiveSign; // (user can override) positive sign
    private String sNegativeSign; // (user can override) negative sign
    private String[] saNativeDigits; // (user can override) native characters for digits 0-9
    // (nfi populates these 5, don't have to be = undef)
    private int iDigitSubstitution; // (user can override) Digit substitution 0=context, 1=none/arabic, 2=Native/national (2 seems to be unused)
    private int iLeadingZeros; // (user can override) leading zeros 0 = no leading zeros, 1 = leading zeros
    private int iDigits; // (user can override) number of fractional digits
    private int iNegativeNumber; // (user can override) negative number format
    private int[] waGrouping; // (user can override) grouping of digits
    private String sDecimalSeparator; // (user can override) decimal separator
    private String sThousandSeparator; // (user can override) thousands separator
    private String sNaN; // Not a Number
    private String sPositiveInfinity; // + Infinity
    private String sNegativeInfinity; // - Infinity
    // Percent
    private int iNegativePercent = undef; // Negative Percent (0-3)
    private int iPositivePercent = undef; // Positive Percent (0-11)
    private String sPercent; // Percent (%) symbol
    private String sPerMille; // PerMille (‰) symbol
    // Currency
    private String sCurrency; // (user can override) local monetary symbol
    private String sIntlMonetarySymbol; // international monetary symbol (RegionInfo)
    private String sEnglishCurrency; // English name for this currency
    private String sNativeCurrency; // Native name for this currency
    // (nfi populates these 4, don't have to be = undef)
    private int iCurrencyDigits; // (user can override) # local monetary fractional digits
    private int iCurrency; // (user can override) positive currency format
    private int iNegativeCurrency; // (user can override) negative currency format
    private int[] waMonetaryGrouping; // (user can override) monetary grouping of digits
    private String sMonetaryDecimal; // (user can override) monetary decimal separator
    private String sMonetaryThousand; // (user can override) monetary thousands separator
    //        private int    iPaperSize               ; // default paper size (RegionInfo)
    // Misc
    private int iMeasure = undef; // (user can override) system of measurement 0=metric, 1=US (RegionInfo)
    private String sListSeparator; // (user can override) list separator
    private int iAnsiCodePage; // (user can override) ANSI CodePage
    // Time
    private String sAM1159; // (user can override) AM designator
    private String sPM2359; // (user can override) PM designator
    private String sTimeSeparator;
    private volatile String[] saLongTimes; // (user can override) time format
    private volatile String[] saShortTimes; // short time format
    private volatile String[] saDurationFormats; // time duration format
    // Calendar specific data
    private int iFirstDayOfWeek = undef; // (user can override) first day of week (gregorian really)
    // Store for specific data about each calendar
    //private CalendarData[] calendars; // Store for specific calendar data
    private int iFirstWeekOfYear = undef; // (user can override) first week of year (gregorian really)
    // 0 - Left to right (eg en-US)
    // 1 - Right to left (eg arabic locales)
    // 2 - Vertical top to bottom with columns to the left and also left to right (ja-JP locales)
    // 3 - Vertical top to bottom with columns proceeding to the right
    private volatile int[] waCalendars; // all available calendar type(s).  The first one is the default calendar
    // Text information
    private int iReadingLayout = undef; // Reading layout data
    private String sTextInfo; // Text info name to use for custom
    private String sCompareInfo; // Compare info name (including sorting key) to use if custom
    private String sScripts; // Typical Scripts for this locale (latn;cyrl; etc)
    // CoreCLR depends on this even though its not exposed publicly.
    private int iDefaultAnsiCodePage = undef; // default ansi code page ID (ACP)
    private int iDefaultOemCodePage = undef; // default oem code page ID (OCP or OEM)
    private int iDefaultMacCodePage = undef; // default macintosh code page
    private int iDefaultEbcdicCodePage = undef; // default EBCDIC code page
    // These are desktop only, not coreclr
    private int iLanguage; // locale ID (0409) - NO sort information
    private String sAbbrevLang; // abbreviated language name (Windows Language Name) ex: ENU
    private String sAbbrevCountry; // abbreviated country name (RegionInfo) (Windows Region Name) ex: USA
    private String sISO639Language2; // 3 char ISO 639 lang name 2 ex: eng
    private String sISO3166CountryName2; // 3 char ISO 3166 country name 2 2(RegionInfo) ex: USA (ISO)
    private int iInputLanguageHandle = undef;// input language handle
    private String sConsoleFallbackName; // The culture name for the console fallback UI culture
    private String sKeyboardsToInstall; // Keyboard installation string.
    private String fontSignature; // Font signature (16 WORDS)
    // The bools all need to be in one spot
    private boolean bUseOverrides; // use user overrides?
    private boolean bNeutral; // Flags for the culture (ie: neutral or not right now)
    private CalendarData[] calendars;

    ///////
    private int mDefaultCalendarId;
    private int cultureType;
    private volatile Locale mLocale;
    private static final Map<Integer, String> gLcidToCultureName = new ConcurrentHashMap<>();
}
