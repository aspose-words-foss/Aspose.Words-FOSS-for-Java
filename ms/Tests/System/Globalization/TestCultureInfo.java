// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/08/2020 by Vyacheslav Durin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.ms.System.Globalization.CultureTypes;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.words.net.System.Globalization.CultureNotFoundException;
import com.aspose.ms.System.msConsole;
import com.aspose.ms.System.Globalization.msDateTimeFormatInfo;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Globalization.Calendar;


@Test
/// <summary>
/// Tests the methods of the CultureInfo class except the following
/// 
///   CultureInfo.DefaultThreadCurrentCulture();
///   CultureInfo.DefaultThreadCurrentUICulture();
///   CultureInfo.CurrentUICulture(); 
///   CultureInfo.InstalledUICulture();
///   CultureInfo.GetCultureInfoByIetfLanguageTag();
/// </summary>
public class TestCultureInfo
{
    @Test
    public void testGetCulturesByType()
    {
        // The numbers could be different, e.g. on my machine 
        // AllCultures = 850
        // SpecificCultures = 569
        // NeutralCultures = 281
        // So we just check that we can retrieve at least one record of each type.
        msAssert.lessOrEqual(800, msCultureInfo.getCultures(CultureTypes.ALL_CULTURES).length);
        msAssert.lessOrEqual(500, msCultureInfo.getCultures(CultureTypes.SPECIFIC_CULTURES).length);
        msAssert.lessOrEqual(200, msCultureInfo.getCultures(CultureTypes.NEUTRAL_CULTURES).length);
    }

    @Test
    public void testCreateSpecificCultureRandomShortLanguage()
    {
        msAssert.areEqual("Invariant Language (Invariant Country)", msCultureInfo.createSpecificCulture("gib").getDisplayName());
        msAssert.areEqual("Invariant Language (Invariant Country)", msCultureInfo.createSpecificCulture("xyz").getDisplayName());
    }

    @Test (expectedExceptions = CultureNotFoundException.class)
    public void testCreateSpecificCultureRandomLongLanguage()
    {
        msCultureInfo culture = msCultureInfo.createSpecificCulture("abcd");
    }

    @Test
    public void testCreateSpecificCulture()
    {
        assertInvariantCulture(msCultureInfo.createSpecificCulture("xc"));
        assertInvariantCulture(msCultureInfo.createSpecificCulture("xx"));
        msAssert.areEqual("Unknown Locale (lz-ZZ)", msCultureInfo.createSpecificCulture("lz-ZZ").getDisplayName());
        assertInvariantCulture(msCultureInfo.createSpecificCulture("lz"));
        assertCustomRuEnCulture(msCultureInfo.createSpecificCulture("ru-EN"));
        assertCustomEnUaCulture(msCultureInfo.createSpecificCulture("en-UA"));
    }

    @Test
    public void testInvariantCulture()
    {
        msCultureInfo invariant = msCultureInfo.getInvariantCulture();
        assertInvariantCulture(invariant);
        assertGregorianCalendar(invariant.getCalendar());
    }

    /// <summary>
    /// At the moment Java's test fails. 
    /// You will see that CultureInfo.GetCultureInfo("xx") returns 'Unknown Language (xx)'
    /// But CultureInfo.CreateSpecificCulture("xx") returns the Invariant culture
    /// 
    /// I haven't found any explanation how these methods work with custom (non-existing) cultures.
    /// There are many things are going on in C# native code. 
    /// I replicated the similar workflow at some scale, but in needs to be improved in the future.
    /// </summary>
    @Test (enabled = false, description = "Ignore the test that depend on local culture")
    public void testControversialConditions()
    {
        msAssert.areEqual("Unknown Locale (lz-ZZ)", msCultureInfo.getCultureInfo("lz-ZZ").getDisplayName());
        msAssert.areEqual("Unknown Language (xx)", msCultureInfo.getCultureInfo("xx").getDisplayName());

        assertInvariantCulture(msCultureInfo.createSpecificCulture("xx"));
        msAssert.areEqual("Unknown Language (xx)", new msCultureInfo("xx").getDisplayName());
        msAssert.areEqual("Unknown Language (xx)", new msCultureInfo("xx", false).getDisplayName());

        msAssert.areEqual("Unknown Locale (xx-XY)", msCultureInfo.createSpecificCulture("xx-XY").getDisplayName());
        msAssert.areEqual("Unknown Locale (xx-XY)", new msCultureInfo("xx-XY").getDisplayName());
    }

    @Test
    public void testGetCultureInfoByName()
    {
        assertEnNzCulture(msCultureInfo.getCultureInfo("en-NZ"));
        assertEnUsCulture(msCultureInfo.getCultureInfo("en-US"));
        assertJaJpCulture(msCultureInfo.getCultureInfo("ja-JP"));
        assertKoKpCulture(msCultureInfo.getCultureInfo("ko-KP"));
        assertHeIlCulture(msCultureInfo.getCultureInfo("he-IL"));
        assertHiInCulture(msCultureInfo.getCultureInfo("hi-IN")); // Java iw_IL
        assertRuRuCulture(msCultureInfo.getCultureInfo("ru-RU"));
    }

    @Test (expectedExceptions = CultureNotFoundException.class)
    public void testGetCultureInfoByCultureNonExistingID()
    {
        msCultureInfo.getCultureInfo(70000);
    }

    @Test
    public void testGetCultureInfoByCultureID()
    {
        assertEnNzCulture(msCultureInfo.getCultureInfo(5129));
        assertEnUsCulture(msCultureInfo.getCultureInfo(1033));
        assertJaJpCulture(msCultureInfo.getCultureInfo(1041));
        assertHeIlCulture(msCultureInfo.getCultureInfo(1037));
        assertHiInCulture(msCultureInfo.getCultureInfo(1081));
        assertRuRuCulture(msCultureInfo.getCultureInfo(1049));

        assertEsEsTraditionalCulture(msCultureInfo.getCultureInfo(1034));
    }

    @Test
    public void testCreateNewCultureInfoByName()
    {
        assertRuRuCulture(new msCultureInfo("ru-RU"));
        assertRuCulture(new msCultureInfo("ru"));
    }

    @Test
    public void testCreateNewCultureInfoByCultureId()
    {
        assertEnNzCulture(new msCultureInfo(5129));
        assertRuRuCulture(new msCultureInfo(1049));
    }

    @Test (expectedExceptions = CultureNotFoundException.class)
    public void testCreateNewCultureInfoByCultureNonExistingID()
    {
        new msCultureInfo(70000);
    }

    private void assertHiInCulture(msCultureInfo candidate)
    {
        assertCulture(1081, 0, "hi-IN", "Hindi (India)", "Hindi (India)",
                              "हिन्दी (भारत)", "hi-IN", "hin",
                               "HIN", "hi", candidate);
    }

    private void assertHeIlCulture(msCultureInfo candidate)
    {
        assertCulture(1037, 1255, "he-IL", "Hebrew (Israel)", "Hebrew (Israel)",
                               "עברית (ישראל)", "he-IL", "heb",
                               "HEB", "he", candidate);
    }


    private void assertKoKpCulture(msCultureInfo candidate)
    {
        System.out.println("KOREA=" + candidate.getLCID());
        assertCulture(4096, 0, "ko-KP", "Korean (North Korea)", "Korean (North Korea)",
                                 "한국어 (조선민주주의인민공화국)", "ko-KP", "kor",
                                 "ZZZ", "ko", candidate);
    }

    private void assertJaJpCulture(msCultureInfo candidate)
    {
        assertCulture(1041, 932, "ja-JP", "Japanese (Japan)", "Japanese (Japan)",
                                 "日本語 (日本)", "ja-JP", "jpn",
                                 "JPN", "ja", candidate);
    }


    private void assertEnNzCulture(msCultureInfo candidate)
    {
        assertCulture(5129, 1252, "en-NZ", "English (New Zealand)", "English (New Zealand)",
                       "English (New Zealand)", "en-NZ", "eng",
                       "ENZ", "en", candidate);
    }

    private void assertEnUsCulture(msCultureInfo candidate)
    {
        assertCulture(1033, 1252, "en-US", "English (United States)", "English (United States)",
                       "English (United States)", "en-US", "eng",
                       "ENU", "en", candidate);
    }

    private void assertRuCulture(msCultureInfo candidate)
    {
        assertCulture(25, 1251, "ru", "Russian", "Russian",
                       "русский", "ru", "rus",
                       "RUS", "ru", candidate);
        msDateTimeFormatInfo dtfi = candidate.getDateTimeFormat();
        assertDateTimeFormat(dtfi, "dd.MM.yyyy", "H:mm", "d MMMM yyyy 'г.'",
               "H:mm:ss", "d MMMM", "MMMM yyyy", ".", ":", "d MMMM yyyy 'г.' H:mm:ss",
               "", "", "ddd, dd MMM yyyy HH':'mm':'ss 'GMT'", "yyyy'-'MM'-'dd'T'HH':'mm':'ss",
                "yyyy'-'MM'-'dd HH':'mm':'ss'Z'"
            );
    }

    private void assertRuRuCulture(msCultureInfo candidate)
    {
        assertCulture(1049, 1251, "ru-RU", "Russian (Russia)", "Russian (Russia)",
                       "русский (Россия)", "ru-RU", "rus",
                       "RUS", "ru", candidate);
    }


    private void assertCustomRuEnCulture(msCultureInfo candidate)
    {
        assertCulture(4096, 1251, "ru-EN", "Unknown Locale (ru-EN)", "Unknown Locale (ru-EN)",
            "Unknown Locale (ru-EN)", "ru-EN", "rus",
            "RUS", "ru", candidate);
    }

    private void assertCustomEnUaCulture(msCultureInfo candidate)
    {
        assertCulture(4096, 1252, "en-UA", "Unknown Locale (en-UA)", "Unknown Locale (en-UA)",
            "Unknown Locale (en-UA)", "en-UA", "eng",
            "ENU", "en", candidate);
    }

    private void assertInvariantCulture(msCultureInfo candidate)
    {
        assertCulture(
             127,
             1252,
             "",
             "Invariant Language (Invariant Country)",
             "Invariant Language (Invariant Country)",
             "Invariant Language (Invariant Country)",
             "",
             "ivl",
             "IVL",
             "iv",
             candidate);
    }

    private void assertEsEsTraditionalCulture(msCultureInfo candidate)
    {
        assertCulture(candidate, 1034, "es-ES", "Spanish (Spain)", "Spanish (Spain, Traditional Sort)",
            "español (España, alfabetización tradicional)", "es-ES", "spa", "ESP", "es");
    }

    private void assertCulture(int lcid, int ansiCodePage, String name, String displayName, String englishName, String nativeName, msCultureInfo culture)
    {
        assertCulture(lcid, ansiCodePage, name, displayName, englishName, nativeName, null, null, null, null, culture);
    }

    private void assertCulture(int lcid, int ansiCodePage, String name,
        String displayName, String englishName, String nativeName,
        String ietfLanguageTag, String threeLetterIsoName, String threeLetterWinName,
        String twoLetterIsoLngName,
        msCultureInfo culture
    )
    {
        msAssert.isNotNull(culture);
        msAssert.areEqual(ansiCodePage, culture.getTextInfo().getANSICodePage());
        msAssert.areEqual(lcid, culture.getLCID());
        msAssert.areEqual(name, culture.getName());
        msAssert.areEqual(displayName, culture.getDisplayName());
        msAssert.areEqual(englishName, culture.getEnglishName());
        msAssert.areEqual(nativeName, culture.getNativeName());
        msAssert.areEqual(ietfLanguageTag, culture.getIetfLanguageTag());
        msAssert.areEqual(threeLetterIsoName, culture.getThreeLetterISOLanguageName());
        msAssert.areEqual(threeLetterWinName, culture.getThreeLetterWindowsLanguageName());
        msAssert.areEqual(twoLetterIsoLngName, culture.getTwoLetterISOLanguageName());
        msAssert.areEqual(culture.getName(), culture.toString());
    }

    private void assertCulture(msCultureInfo culture, int lcid, /*params*/ String... names)
    {
        msAssert.isNotNull(culture);
        msAssert.areEqual(lcid, culture.getLCID());

        String actual = msString.join(";", names);

        String[] expectedArray = new String[] {
            culture.getName(), culture.getDisplayName(), culture.getEnglishName(), culture.getNativeName(), culture.getIetfLanguageTag(),
            culture.getThreeLetterISOLanguageName(), culture.getThreeLetterWindowsLanguageName(), culture.getTwoLetterISOLanguageName()
        };
        String expected = msString.join(";", expectedArray);

        msAssert.areEqual(expected, actual);
    }

    private void assertDateTimeFormat(msDateTimeFormatInfo dateTimeFormatInfo,
        String shortDatePattern,
        String shortTimePattern,
        String longDatePattern,
        String longTimePattern,
        String monthDayPattern,
        String yearMonthPattern,
        String dateSeparator,
        String timeSeparator,
        String fullDateTimePattern,
        String pMDesignator,
        String aMDesignator,
        String rFC1123Pattern,
        String sortableDateTimePattern,
        String universalSortableDateTimePattern
        )
    {
        msAssert.isNotNull(dateTimeFormatInfo);
        msAssert.areEqual(dateTimeFormatInfo.getShortDatePattern(), shortDatePattern);
        msAssert.areEqual(dateTimeFormatInfo.getShortTimePattern(), shortTimePattern);
        msAssert.areEqual(dateTimeFormatInfo.getLongDatePattern(), longDatePattern);
        msAssert.areEqual(dateTimeFormatInfo.getLongTimePattern(), longTimePattern);
        msAssert.areEqual(dateTimeFormatInfo.getMonthDayPattern(), monthDayPattern);
        msAssert.areEqual(dateTimeFormatInfo.getYearMonthPattern(), yearMonthPattern);
        msAssert.areEqual(dateTimeFormatInfo.getDateSeparator(), dateSeparator);
        msAssert.areEqual(dateTimeFormatInfo.getTimeSeparator(), timeSeparator);
        msAssert.areEqual(dateTimeFormatInfo.getFullDateTimePattern(), fullDateTimePattern);
        msAssert.areEqual(dateTimeFormatInfo.getPMDesignator(), pMDesignator);
        msAssert.areEqual(dateTimeFormatInfo.getAMDesignator(), aMDesignator);
        msAssert.areEqual(dateTimeFormatInfo.getRFC1123Pattern(), rFC1123Pattern);
        msAssert.areEqual(dateTimeFormatInfo.getSortableDateTimePattern(), sortableDateTimePattern);
        msAssert.areEqual(dateTimeFormatInfo.getUniversalSortableDateTimePattern(), universalSortableDateTimePattern);

    }

    private void assertGregorianCalendar(Calendar calendar)
    {
        msAssert.isNotNull(calendar);
        // Extend the verification if needed
    }

}

