// Parts of this file are a Java port, made by Aspose, of .NET Framework code. Microsoft publishes that source
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/globalization/cultureinfo.cs
//
// Those parts: Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 07/02/12 by Durin Vyacheslav

package com.aspose.ms.System.Globalization;

import com.aspose.ms.System.Threading.CurrentThread;
import com.aspose.ms.System.msString;
import com.aspose.ms.java.logging.Log;
import com.aspose.words.net.System.Globalization.CultureInfo;
import com.aspose.words.net.System.Globalization.CultureNotFoundException;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Java implementation of
 * http://msdn.microsoft.com/en-us/library/system.globalization.cultureinfo.aspx
 *
 * Many .Net public properties and methods set private on Java because not used.
 * Any of the members can be set public again after appropriate tests.
 */
public final class msCultureInfo implements IFormatProvider, Cloneable
{
    /**
     * .Net-specific: can return the Windows system CultureInfo or user-modified one
     * (through the regional and language options of Control Panel).
     * In java we always return system CultureInfo (imported from developer's Windows).
     */
    public msCultureInfo(String name, boolean useUserOverride)
    {
        if (name == null)
            throw new IllegalArgumentException("name cannot be null");

        // FIX WORDSJAVA-2767: use "he-IL" culture instead of "iw-IL".
        name = "iw-IL".equals(name) ? "he-IL" : name;
        // FIX WORDSJAVA-2830: use "id-ID" culture instead of "in-ID".
        name = "in-ID".equals(name) ? "id-ID" : name;

        // Get our data providing record
        this.mCultureData = CultureData.getCultureData(name, useUserOverride);

        if (this.mCultureData == null)
            throw new CultureNotFoundException("Culture name '" + name + "' is not supported");

        mName = mCultureData.getCultureName();
    }

    // JAVA-added.
    msCultureInfo(CultureData cultureData)
    {
        if (cultureData == null)
            throw new IllegalArgumentException("CultureData cannot be null");

        this.mCultureData = cultureData;
        mName = mCultureData.getCultureName();
    }

    public msCultureInfo(int cultureID)
    {
        this(cultureID, true);
    }

    public msCultureInfo(int cultureID, boolean useUserOverride)
    {
        // We don't check for other invalid LCIDS here...
        if (cultureID < 0)
            throw new IllegalArgumentException("culture id is out of range");

        initializeFromCultureId(cultureID, useUserOverride);
        mName = mCultureData.getCultureName();
    }

    // Gets a cached copy of the specified culture from an internal hashtable.
    public static msCultureInfo getCultureInfo(int cultureID)
    {
        if (cultureID <= 0)
            throw new IllegalArgumentException("culture is out or range");

        msCultureInfo cultureInfo = getCultureInfo(cultureID, null, null);

        if (null == cultureInfo)
            throw new CultureNotFoundException("cultureID '" + cultureID + "' not supported");

        return cultureInfo;
    }

    // Gets a cached copy of the specified culture from an internal hashtable.
    public static msCultureInfo getCultureInfo(String name, String altName)
    {
        // Make sure we have a valid, non-zero length string as name
        if (name == null)
            throw new IllegalArgumentException("name");

        if (null == altName)
            throw new IllegalArgumentException("altName");

        msCultureInfo cultureInfo = getCultureInfo(-1, name, altName);

        if (cultureInfo == null)
            throw new CultureNotFoundException(name, altName);

        return cultureInfo;
    }

    // Helper function both overloads of GetCachedReadOnlyCulture.  If cultureID is 0, we use the name.
    // If cultureID is -1, use the altName and create one of those special SQL cultures.
    private static msCultureInfo getCultureInfo(int cultureID, String name, String altName)
    {
        msCultureInfo cultureInfo = null;

        // Let's check our hash tables first.
        if (cultureID == -1)
            cultureInfo = gNameToCultureInfo.get(name + "\\xfffd" + altName);
        else if (cultureID == 0)
            cultureInfo = gNameToCultureInfo.get(name);
        else if (cultureID > 0)
            cultureInfo = gCultureIDToCultureInfo.get(cultureID);

        if (cultureInfo != null)
            return cultureInfo;

        // The desired object was not found in our hash tables.
        // Let's construct it. Return null if constructor fails.
        try
        {
                if (cultureID == -1)
                    cultureInfo = new msCultureInfo(name, altName);
                else if (cultureID == 0)
                    cultureInfo = new msCultureInfo(name, false);
                else
                    cultureInfo = new msCultureInfo(cultureID, false);
        }
        catch (Exception e)
        {
            // Not logged: the callers turn null into a CultureNotFoundException, and asking for an unknown culture is
            // ordinary - 0x400, "the default language", comes with plenty of documents and the caller falls back.
            return null;
        }

        // Set it to read-only
        cultureInfo.m_isReadOnly = true;

        if (cultureID == -1)
        {
            // This new culture will be added only to the name hash table.
            gNameToCultureInfo.put(name + "\\xfffd" + altName, cultureInfo);
        }
        else
        {
            // Remember our name (as constructed).  Do NOT use alternate sort name versions because
            // we have internal state representing the sort.  (So someone would get the wrong cached version)
            String newName = cultureInfo.mName;
            gNameToCultureInfo.put(newName, cultureInfo);

            final int LCID_ZH_CHS_HANS = 0x0004;
            final int LCID_ZH_CHT_HANT = 0x7c04;

            if ((cultureInfo.getLCID() == LCID_ZH_CHS_HANS && "zh-hans".equals(newName))
                    || (cultureInfo.getLCID() == LCID_ZH_CHT_HANT && "zh-hant".equals(newName)))
            {
                // do nothing because we only want zh-CHS and zh-CHT to cache by cultureID
            }
            else
            {
                gCultureIDToCultureInfo.put(cultureInfo.getLCID(), cultureInfo);
            }
        }

        return cultureInfo;
    }

    public msDateTimeFormatInfo getDateTimeFormat()
    {
        // WORDSJAVA-2187 In fact, keeping DTFI instances in the cache using the key 'this' is the same as lazy init in this case.
        if (mDtfi == null)
            mDtfi = new msDateTimeFormatInfo(mCultureData, getCalendar());

        return mDtfi;
    }

    public void setDateTimeFormat(msDateTimeFormatInfo dtfi)
    {
        mDtfi = dtfi;
    }

    @Override
    public final String toString()
    {
        return mCultureData.getCultureName();
    }

    /**
     * JAVA-added. Returns Java's Locale that correspondents to this .Net CultureInfo.
     * Can't name it 'toJava()' since we already have 'toJava()' that returns our public-API CultureInfo.
     */
    public Locale getLocale()
    {
        return mCultureData.getLocale();
    }

    public String getDisplayName()
    {
        return mCultureData.getLocalizedDisplayName();
    }

    public String getEnglishName()
    {
        return mCultureData.getEngDisplayName();
    }

    public String getNativeName()
    {
        return mCultureData.getNativeDisplayName();
    }

    public String getIetfLanguageTag()
    {
        // special case the compatibility cultures
        String name = getName();
        switch (name)
        {
            case "zh-CHT":
                return "zh-Hant";
            case "zh-CHS":
                return "zh-Hans";
            default:
                return name;
        }
    }

    public String getThreeLetterISOLanguageName()
    {
        return mCultureData.getISO639LangName2();
    }

    ////////////////////////////////////////////////////////////////////////
    //
    //  ThreeLetterWindowsLanguageName
    //
    //  Returns the 3 letter windows language name for the current instance.  eg: "ENU"
    //  The ISO names are much preferred
    //
    ////////////////////////////////////////////////////////////////////////
    public String getThreeLetterWindowsLanguageName()
    {
        return mCultureData.getAbbrevLangName();
    }

    public String getTwoLetterISOLanguageName()
    {
        return mCultureData.getISO639LangName();
    }

    CultureData getCultureData()
    {
        return mCultureData;
    }

    /*=================================Calendar==========================
        **Action: Return/set the default calendar used by this culture.
        ** This value can be overridden by regional option if this is a current culture.
        **Returns:
        **Arguments:
        **Exceptions:
        **  ArgumentNull_Obj if the set value is null.
        ============================================================================*/
    public /*virtual*/ Calendar getCalendar()
    {
        if (calendar == null)
        {
            //BCLDebug.Assert(this.m_cultureTableRecord.IOPTIONALCALENDARS.Length > 0, "this.m_cultureTableRecord.IOPTIONALCALENDARS.Length > 0");
            // Get the default calendar for this culture.  Note that the value can be
            // from registry if this is a user default culture.
            //JAVA-changed from:
            //int calType = this.m_cultureTableRecord.ICALENDARTYPE;
            //Thread.MemoryBarrier();
            //newObj.setReadOnlyState(m_isReadOnly);
            calendar = mCultureData.getDefaultCalendar();
        }
        return (calendar);
    }

    public static msCultureInfo[] getCultures(int types)
    {
        return CultureData.getCultures(types);
    }

    /*=================================GetCalendarInstance==========================
        **Action: Map a Win32 CALID to an instance of supported calendar.
        **Returns: An instance of calendar.
        **Arguments: calType    The Win32 CALID
        **Exceptions:
        **      Shouldn't throw exception since the calType value is from our data table or from Win32 registry.
        **      If we are in trouble (like getting a weird value from Win32 registry), just return the GregorianCalendar.
        ============================================================================*/
    static Calendar getCalendarInstance(int calType)
    {
        if (calType == Calendar.CAL_GREGORIAN)
        {
            return (new GregorianCalendar());
        }
        return getCalendarInstanceRare(calType);
    }

    //This function exists as a shortcut to prevent us from loading all of the non-gregorian
    //calendars unless they're required.
    static Calendar getCalendarInstanceRare(int calType)
    {
        //BCLDebug.Assert(calType != Calendar.CAL_GREGORIAN, "calType!=Calendar.CAL_GREGORIAN");

        switch (calType)
        {
            case Calendar.CAL_GREGORIAN_US:               // Gregorian (U.S.) calendar
            case Calendar.CAL_GREGORIAN_ME_FRENCH:        // Gregorian Middle East French calendar
            case Calendar.CAL_GREGORIAN_ARABIC:           // Gregorian Arabic calendar
            case Calendar.CAL_GREGORIAN_XLIT_ENGLISH:     // Gregorian Transliterated English calendar
            case Calendar.CAL_GREGORIAN_XLIT_FRENCH:      // Gregorian Transliterated French calendar
                return (new GregorianCalendar((/*GregorianCalendarTypes*/int) calType));
            case Calendar.CAL_HIJRI:                      // Hijri (Arabic Lunar) calendar
                return (new HijriCalendar());
            case Calendar.CAL_HEBREW:                     // Hebrew (Lunar) calendar
                return (new HebrewCalendar());
            case Calendar.CAL_THAI:                       // Thai calendar
                return (new ThaiBuddhistCalendar());
            case Calendar.CAL_UMALQURA:
                return (new UmAlQuraCalendar());
            // Aspose.Words FOSS has the Gregorian, Hijri and Hebrew calendars of the fields plus the Thai Buddhist and Um Al Qura
            // ones that some culture uses by default. The rest (the era and lunisolar calendars of East Asia, the Persian one)
            // give the Gregorian calendar: Aspose.Words itself never asks for them.
        }
        return (new GregorianCalendar());
    }

    /*=================================OptionCalendars==========================
       **Action: Return an array of the optional calendar for this culture.
       **Returns: an array of Calendar.
       **Arguments:
       **Exceptions:
       ============================================================================*/
    public /*virtual*/ Calendar[] getOptionalCalendars()
    {
        //
        // This property always returns a new copy of the calendar array.
        //
        //JAVA-changed: optionalCalendars read from CultureInfoCore generated by autoporter.
        //int[] calID = this.m_cultureTableRecord.IOPTIONALCALENDARS;
        int[] calendarIds = mCultureData.getCalendarIds();
        Calendar[] cals = new Calendar[calendarIds.length];
        for (int i = 0; i < cals.length; i++)
            cals[i] = getCalendarInstance(calendarIds[i]);
        return (cals);
    }

    ////////////////////////////////////////////////////////////////////////
    //
    //  Name
    //
    //  Returns the full name of the CultureInfo. The name is in format like
    //  "en-US"  This version does NOT include sort information in the name.
    //
    ////////////////////////////////////////////////////////////////////////
    public String getName()
    {
        return mCultureData.getName();
    }

    @Override
    public Object getFormat(Class formatType)
    {
        return (formatType == msCultureInfo.class) ? this : null;
    }

    public Integer getLCID()
    {
        return mCultureData.getLanguage();
    }

    /**
     * JAVA-added for PublicApiChangeMap.
     * Converts internal (~.Net) type to public (~Java) type.
     */
    public static CultureInfo toJava(msCultureInfo msCultureInfo)
    {
        return new CultureInfo(msCultureInfo);
    }

    /**
     * JAVA-added for PublicApiChangeMap.
     * Converts public (~Java) type to internal (~.Net) type.
     */
    public static msCultureInfo fromJava(CultureInfo cultureInfo)
    {
        if (cultureInfo == null)
            return null;

        return cultureInfo.getMsCultureInfo();
    }

    public static msCultureInfo getCurrentCulture()
    {
        return CurrentThread.getCurrentCulture();
    }

    public static msCultureInfo getInvariantCulture()
    {
        return gInvariant;
    }

    /**
     * The method tries to replicate the vague business logic of his analogue.
     * The problem is that .NET does all of the guessing in its native code.
     *
     * @param cultureName
     * @return
     */
    public static msCultureInfo createSpecificCulture(String cultureName)
    {

        msCultureInfo culture;

        try
        {
            culture = new msCultureInfo(cultureName);
        }
        catch (Exception exp)
        {
            int index = cultureName.indexOf('-');
            if (index >= 0)
            {
                try
                {
                    culture = new msCultureInfo(cultureName.substring(0, index));
                    return culture;
                }
                catch (CultureNotFoundException e)
                {
                    // JAVA-changed: an attempt to replicate .NET's behaviour
                    return new msCultureInfo(CultureData.getUnknownLocaleCulture(new CultureData(), cultureName));
                }
            }

            // wrong language
            if (cultureName.length() > 3)
                throw new CultureNotFoundException(exp);

                // JAVA-changed: an attempt to replicate .NET's behaviour
                // FIX AMAS: cultureName.length() must be 2 or 3 to replicate .NET's behaviour
            else if (cultureName.length() > 1)
                return getInvariantCulture();
            else
                return new msCultureInfo(CultureData.getUnknownLocaleCulture(new CultureData(), cultureName));
        }

        return culture;
    }

    // LOCALE constants of interest to us internally and privately for LCID functions
    // (ie: avoid using these and use names if possible)
    private static final int LOCALE_NEUTRAL = 0x0000;
    private static final int LOCALE_USER_DEFAULT = 0x0400;
    private static final int LOCALE_SYSTEM_DEFAULT = 0x0800;
    private static final int LOCALE_CUSTOM_DEFAULT = 0x0c00;
    private static final int LOCALE_CUSTOM_UNSPECIFIED = 0x1000;
    private static final int LOCALE_INVARIANT = 0x007F;
    private static final int LOCALE_TRADITIONAL_SPANISH = 0x040a;
    private static final Map<String, msCultureInfo> gNameToCultureInfo = new ConcurrentHashMap<>();
    private static final Map<Integer, msCultureInfo> gCultureIDToCultureInfo = new ConcurrentHashMap<>();
    private static final msCultureInfo gInvariant;
    private static final msCultureInfo gUserDefaultCulture;


    private static msCultureInfo getCultureByName(String name, boolean userOverride)
    {
        // Try to get our culture
        try
        {
            return userOverride ? new msCultureInfo(name) : msCultureInfo.getCultureInfo(name);
        }
        catch (Exception e)
        {
            Log.error(e);
        }

        return null;
    }

    static msCultureInfo initUserDefaultCulture()
    {
        String strDefault = getDefaultLocaleName(LOCALE_USER_DEFAULT);
        if (strDefault == null)
        {
            strDefault = getDefaultLocaleName(LOCALE_SYSTEM_DEFAULT);

            if (strDefault == null)
            {
                // If system default doesn't work, keep using the invariant
                return (msCultureInfo.getInvariantCulture());
            }
        }

        return (getCultureByName(strDefault, true));
    }

    private static String getDefaultLocaleName(int localeType)
    {
        Locale defaultLocale = Locale.getDefault();
        return defaultLocale.getLanguage() + "-" + defaultLocale.getCountry();
    }

    static
    {
        gInvariant = new msCultureInfo("");
        gUserDefaultCulture = initUserDefaultCulture();
    }

    /**
     * JAVA-added. Should find .Net CultureInfo corresponding to the given Java Locale.
     */
    public msCultureInfo(Locale locale)
    {
        this(msCultureInfo.getCultureName(locale));
    }

    public msCultureInfo(String cultureName)
    {
        this(cultureName, true);
    }

    private static String getCultureName(Locale locale)
    {
        if (locale == null)
            return null;

        String country = locale.getCountry();
        country = msString.isNullOrEmpty(country) ? "" : "-" + country;
        return locale.getLanguage() + country;
    }

    private void initializeFromCultureId(int culture, boolean useUserOverride)
    {
        switch (culture)
        {
            case LOCALE_CUSTOM_DEFAULT:
            case LOCALE_SYSTEM_DEFAULT:
            case LOCALE_NEUTRAL:
            case LOCALE_USER_DEFAULT:
            case LOCALE_CUSTOM_UNSPECIFIED:
                // Can't support unknown custom cultures and we do not support neutral or
                // non-custom user locales.
                throw new CultureNotFoundException(culture);

            default:
                // Now see if this LCID is supported in the system default CultureData  table.
                this.mCultureData = CultureData.getCultureData(culture, useUserOverride);
                break;
        }
    }

    // Constructor called by SQL Server's special munged culture - creates a culture with
    // a TextInfo and CompareInfo that come from a supplied alternate source. This object
    // is ALWAYS read-only.
    // Note that we really cannot use an LCID version of this override as the cached
    // name we create for it has to include both names, and the logic for this is in
    // the GetCultureInfo override *only*.
    private msCultureInfo(String cultureName, String textAndCompareCultureName)
    {
        if (cultureName == null)
            throw new IllegalArgumentException("cultureName cannot be null");

        this.mCultureData = CultureData.getCultureData(cultureName, false);
        if (this.mCultureData == null)
            throw new CultureNotFoundException(cultureName);

        mName = this.mCultureData.getCultureName();

        msCultureInfo altCulture = getCultureInfo(textAndCompareCultureName);
        //  this.compareInfo = altCulture.CompareInfo;
        mTextInfo = altCulture.getTextInfo();
    }

    // Gets a cached copy of the specified culture from an internal hashtable.
    public static msCultureInfo getCultureInfo(String name)
    {
        // Make sure we have a valid, non-zero length string as name
        if (name == null)
            throw new IllegalArgumentException("name");

        msCultureInfo retval = getCultureInfo(0, name, null);
        if (retval == null)
            throw new CultureNotFoundException(name);

        return retval;
    }

    public TextInfo getTextInfo()
    {
        if (mTextInfo == null)
            mTextInfo = new TextInfo(mCultureData);

        return mTextInfo;
    }

    public msCultureInfo deepClone()
    {
        msCultureInfo dci = (msCultureInfo) memberwiseClone();

        // FIX WORDSJAVA-2838 - mDtfi can be null. Let's initialize it here.
        if (mDtfi == null)
            mDtfi = new msDateTimeFormatInfo(mCultureData, getCalendar());

        msDateTimeFormatInfo dtfi = mDtfi.deepClone();
        dci.setDateTimeFormat(dtfi);
        return dci;
    }

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try
        {
            return clone();
        }
        catch (CloneNotSupportedException e)
        {
            throw new IllegalStateException(e);
        }
    }

    private String mName;
    private volatile TextInfo mTextInfo;
    private boolean m_isReadOnly;
    private msDateTimeFormatInfo mDtfi;
    private Calendar calendar;
    private int cultureID;
    private CultureData mCultureData;
    private Locale locale;

}

