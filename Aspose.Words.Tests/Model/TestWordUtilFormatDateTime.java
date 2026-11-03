// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/05/2017 by Konstantin Sidorenko


package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import com.aspose.SystemPal;
import org.testng.Assert;
import com.aspose.ms.System.DateTime;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// Autoportable tests of Hebrew and Arabic DateTime formatting: the test should pass on Java after autoporting.
///
/// Formally these tests should be in JavaMs.Tests module but moved here to:
/// 1. Remove links from JavaMs.Tests to Aspose.Words (WordUtil.FormatDateTime()).
/// 2. To place duplication in the same place: DateTime.ToString() and WordUtil.FormatDateTime()
///    have the same tests.
/// </summary>
@Test
public class TestWordUtilFormatDateTime
{
    @BeforeMethod (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// WordUtil.FormatDateTime() should produce the same Hebrew DateTime string constants (month, day, etc. names) on .Net and Java.
    /// </summary>
    @Test
    public void testHebrewDateFormattingWordUtil()
    {
        SystemPal.saveCulture();
        try
        {
            SystemPal.setCulture("he-IL");

            for (int i = 1; i <= G_HEBREW_MONTH_DAYS.length; i++)
            {
                msAssert.areEqual(G_HEBREW_MONTH_DAYS[i - 1], WordUtil.formatDateTime(new DateTime(2007, 1, i), "d", 0, CalendarType.HEBREW), "Month's day - d");
                msAssert.areEqual(G_HEBREW_MONTH_DAYS[i - 1], WordUtil.formatDateTime(new DateTime(2007, 1, i), "dd", 0, CalendarType.HEBREW), "Month's day - dd");

                msAssert.areEqual(G_HEBREW_MONTH_DAYS[i - 1], WordUtil.formatDateTime(new DateTime(2007, 1, i), "D", 0, CalendarType.HEBREW), "Month's day - D");
                msAssert.areEqual(G_HEBREW_MONTH_DAYS[i - 1], WordUtil.formatDateTime(new DateTime(2007, 1, i), "DD", 0, CalendarType.HEBREW), "Month's day - DD");
            }

            // Short week day name - ddd
            String[] shortWeekDays = { "ב", "ג", "ד", "ה", "ו", "ש", "א" };
            for (int i = 1; i <= shortWeekDays.length; i++)
            {
                msAssert.areEqual(shortWeekDays[i - 1], WordUtil.formatDateTime(new DateTime(2007, 1, i), "ddd", 0, CalendarType.HEBREW), "Short name of week's day - ddd");
                msAssert.areEqual(shortWeekDays[i - 1], WordUtil.formatDateTime(new DateTime(2007, 1, i), "DDD", 0, CalendarType.HEBREW), "Short name of week's day - DDD");
            }

            // Full week day name - dddd
            for (int i = 1; i <= G_HEBREW_WEEK_DAYS.length; i++)
            {
                msAssert.areEqual(G_HEBREW_WEEK_DAYS[i - 1], WordUtil.formatDateTime(new DateTime(2007, 1, i), "dddd", 0, CalendarType.HEBREW), "Long name of week's day - dddd");
                msAssert.areEqual(G_HEBREW_WEEK_DAYS[i - 1], WordUtil.formatDateTime(new DateTime(2007, 1, i), "DDDD", 0, CalendarType.HEBREW), "Long name of week's day - DDDD");
            }

            // Months - M,MM
            String[] shortMonths = { "ד'", "ה'", "ו'", "ז'", "ח'", "ט'", "י'", "י\"א", "י\"ב", "א'", "ב'", "ג'" };
            for (int i = 1; i <= shortMonths.length; i++)
            {
                msAssert.areEqual(shortMonths[i - 1], normalizeString(WordUtil.formatDateTime(new DateTime(2007, i, 1), "M", 0, CalendarType.HEBREW)), "Months - M");
                msAssert.areEqual(shortMonths[i - 1], normalizeString(WordUtil.formatDateTime(new DateTime(2007, i, 1), "MM", 0, CalendarType.HEBREW)), "Months - MM");
            }

            // Months - MMM,MMMM
            String[] months = { "טבת", "שבט", "אדר", "ניסן", "אייר", "סיון", "תמוז", "אב", "אלול", "תשרי", "חשון", "כסלו" };
            for (int i = 1; i <= months.length; i++)
            {
                msAssert.areEqual(months[i - 1], normalizeString(WordUtil.formatDateTime(new DateTime(2007, i, 1), "MMM", 0, CalendarType.HEBREW)), "Months - MMM");
                msAssert.areEqual(months[i - 1], normalizeString(WordUtil.formatDateTime(new DateTime(2007, i, 1), "MMMM", 0, CalendarType.HEBREW)), "Months - MMMM");
            }

            // Years - y,yy,yyy,yyyy
            String[] years = { "תשס\"ז", "תשס\"ח", "תשס\"ט", "תש\"ע", "תשע\"א", "תשע\"ב", "תשע\"ג", "תשע\"ד", "תשע\"ה", "תשע\"ו", "תשע\"ז", "תשע\"ח" };
            for (int i = 1; i <= years.length; i++)
            {
                msAssert.areEqual(years[i - 1], normalizeString(WordUtil.formatDateTime(new DateTime(2007 + i - 1, 1, 1), "y", 0, CalendarType.HEBREW)), "Years - y");
                msAssert.areEqual(years[i - 1], normalizeString(WordUtil.formatDateTime(new DateTime(2007 + i - 1, 1, 1), "yy", 0, CalendarType.HEBREW)), "Years - yy");
                msAssert.areEqual(years[i - 1], normalizeString(WordUtil.formatDateTime(new DateTime(2007 + i - 1, 1, 1), "yyy", 0, CalendarType.HEBREW)), "Years - yyy");
                msAssert.areEqual(years[i - 1], normalizeString(WordUtil.formatDateTime(new DateTime(2007 + i - 1, 1, 1), "yyyy", 0, CalendarType.HEBREW)), "Years - yyyy");
            }

            // Hours - hh,HH
            for (int i = 1; i < 24; i++)
            {
                msAssert.areEqual(alignNumber(i > 12 ? i - 12 : i, 2), WordUtil.formatDateTime(new DateTime(2007, 1, 1).addHours(i), "hh", 0, CalendarType.HEBREW), "Hours - hh");
                msAssert.areEqual(alignNumber(i, 2), WordUtil.formatDateTime(new DateTime(2007, 1, 1).addHours(i), "HH", 0, CalendarType.HEBREW), "Hours - HH");
            }

            // Minutes, seconds
            for (int i = 0; i < 60; i++)
            {
                msAssert.areEqual(alignNumber(i, 1), WordUtil.formatDateTime(new DateTime(2007, 1, 1).addMinutes(i), "m", 0, CalendarType.HEBREW), "Minutes - m");
                msAssert.areEqual(alignNumber(i, 2), WordUtil.formatDateTime(new DateTime(2007, 1, 1).addMinutes(i), "mm", 0, CalendarType.HEBREW), "Minutes - mm");

                msAssert.areEqual(alignNumber(i, 1), WordUtil.formatDateTime(new DateTime(2007, 1, 1).addSeconds(i), "s", 0, CalendarType.HEBREW), "Seconds - s");
                msAssert.areEqual(alignNumber(i, 2), WordUtil.formatDateTime(new DateTime(2007, 1, 1).addSeconds(i), "ss", 0, CalendarType.HEBREW), "Seconds - ss");
            }

            // AP/PM
            msAssert.areEqual("AM", WordUtil.formatDateTime(new DateTime(2007, 1, 1, 10, 0, 0), "tt", 0, CalendarType.HEBREW), "AP/PM - tt");
            msAssert.areEqual("PM", WordUtil.formatDateTime(new DateTime(2007, 1, 1, 17, 0, 0), "tt", 0, CalendarType.HEBREW), "AP/PM - tt");


        }
        finally
        {
            SystemPal.restoreCulture();
        }
    }

    /// <summary>
    /// DateTime.ToString() should produce the same Hebrew DateTime string constants (month, day, etc. names) on .Net and Java.
    /// </summary>
    @Test
    public void testHebrewDateFormattingToString()
    {
        SystemPal.saveCulture();
        try
        {
            SystemPal.setCulture("he-IL");

            // ShortDatePattern	M/d/yyyy - d
            for (int i = 1; i <= 31; i++)
                msAssert.areEqual(alignNumber(i, 2) + "/01/2007", new DateTime(2007, 1, i).toString("d"), "ShortDatePattern\tM/d/yyyy - d");

            // Month's day - dd
            for (int i = 1; i <= 31; i++)
                msAssert.areEqual(alignNumber(i, 2), new DateTime(2007, 1, i).toString("dd"), "Month's day - dd");

            // Short week day name - ddd
            String[] shortWeekDays = { "יום ב", "יום ג", "יום ד", "יום ה", "יום ו", "שבת", "יום א" };
            for (int i = 1; i <= shortWeekDays.length; i++)
                msAssert.areEqual(shortWeekDays[i - 1], new DateTime(2007, 1, i).toString("ddd"), "Short name of week's day - ddd");

            // Hebrew full week day name - dddd
            for (int i = 1; i <= G_HEBREW_WEEK_DAYS.length; i++)
                msAssert.areEqual(G_HEBREW_WEEK_DAYS[i - 1], new DateTime(2007, 1, i).toString("dddd"), "Long name of week's day - dddd");

            // LongDatePattern dddd, MMMM dd, yyyy - D
            String[] longDates =
            {
                "יום שני 01 ינואר 2007",
                "יום שלישי 02 ינואר 2007",
                "יום רביעי 03 ינואר 2007",
                "יום חמישי 04 ינואר 2007",
                "יום שישי 05 ינואר 2007",
                "שבת 06 ינואר 2007",
                "יום ראשון 07 ינואר 2007",
            };
            for (int i = 1; i <= longDates.length; i++)
                msAssert.areEqual(longDates[i - 1], new DateTime(2007, 1, i).toString("D"), "LongDatePattern dddd, MMMM dd, yyyy - D");

            // The period or era - gg
            msAssert.areEqual("לספירה", new DateTime(2007, 1, 1).toString("gg"), "The period or era - gg");

            // Months and date - M
            String[] dayAndMonths =
            {
                "01 ינואר",
                "01 פברואר",
                "01 מרץ",
                "01 אפריל",
                "01 מאי",
                "01 יוני",
                "01 יולי",
                "01 אוגוסט",
                "01 ספטמבר",
                "01 אוקטובר",
                "01 נובמבר",
                "01 דצמבר"
            };
            for (int i = 1; i <= dayAndMonths.length; i++)
                msAssert.areEqual(dayAndMonths[i - 1], new DateTime(2007, i, 1).toString("M"), "Months and date - M");

            // Number of month - MM
            for (int i = 1; i <= dayAndMonths.length; i++)
                msAssert.areEqual(alignNumber(i, 2), new DateTime(2007, i, 1).toString("MM"), "Number of month - MM");

            // Short month name - MMM
            String[] shortMonths = { "ינו", "פבר", "מרץ", "אפר", "מאי", "יונ", "יול", "אוג", "ספט", "אוק", "נוב", "דצמ" };
            for (int i = 1; i <= shortMonths.length; i++)
                msAssert.areEqual(shortMonths[i - 1], new DateTime(2007, i, 1).toString("MMM"), "Short month name - MMM");

            // Long month name - MMMM
            String[] longMonths =
            {
                "ינואר",
                "פברואר",
                "מרץ",
                "אפריל",
                "מאי",
                "יוני",
                "יולי",
                "אוגוסט",
                "ספטמבר",
                "אוקטובר",
                "נובמבר",
                "דצמבר"
            };
            for (int i = 1; i <= longMonths.length; i++)
                msAssert.areEqual(longMonths[i - 1], new DateTime(2007, i, 1).toString("MMMM"), "Long month name - MMMM");

            // Month and year - y
            String[] yearAndMonths =
            {
                "ינואר 2007",
                "פברואר 2007",
                "מרץ 2007",
                "אפריל 2007",
                "מאי 2007",
                "יוני 2007",
                "יולי 2007",
                "אוגוסט 2007",
                "ספטמבר 2007",
                "אוקטובר 2007",
                "נובמבר 2007",
                "דצמבר 2007"
            };
            for (int i = 1; i <= yearAndMonths.length; i++)
                msAssert.areEqual(yearAndMonths[i - 1], new DateTime(2007, i, 1).toString("y"), "Month and year - y");

            // Year - yy
            for (int i = 7; i <= 13; i++)
                msAssert.areEqual(alignNumber(i, 2), new DateTime(2000 + i, 1, 1).toString("yy"), "Year - yy");

            // Year - yyyy
            for (int i = 2007; i <= 2013; i++)
                msAssert.areEqual(alignNumber(i, 1), new DateTime(i, 1, 1).toString("yyyy"), "Year - yyyy");

            // Hours - hh,HH
            for (int i = 1; i < 24; i++)
            {
                msAssert.areEqual(alignNumber(i > 12 ? i - 12 : i, 2), new DateTime(2007, 1, 1).addHours(i).toString("hh"), "Hours - hh");
                msAssert.areEqual(alignNumber(i, 2), new DateTime(2007, 1, 1).addHours(i).toString("HH"), "Hours - HH");
            }

            // Minutes, seconds
            for (int i = 0; i < 60; i++)
            {
                msAssert.areEqual(alignNumber(i, 2), new DateTime(2007, 1, 1).addMinutes(i).toString("mm"), "Minutes - mm");
                msAssert.areEqual(alignNumber(i, 2), new DateTime(2007, 1, 1).addSeconds(i).toString("ss"), "Seconds - ss");
            }

            // AP/PM
            msAssert.areEqual("AM", new DateTime(2007, 1, 1, 10, 0, 0).toString("tt"), "AP/PM - tt");
            msAssert.areEqual("PM", new DateTime(2007, 1, 1, 17, 0, 0).toString("tt"), "AP/PM - tt");

        }
        finally
        {
            SystemPal.restoreCulture();
        }
    }

    /// <summary>
    /// Check that WordUtil.FormatDateTime() produces the same Hebrew and Arabic string constants on different platforms.
    /// </summary>
    @Test
    public void testArabicDateFormattingWordUtil()
    {
        SystemPal.saveCulture();
        try
        {
            SystemPal.setCulture("ar-SA");

            // Month's day - d,dd
            msAssert.areEqual("12", WordUtil.formatDateTime(new DateTime(2007, 1, 1), "d", 0, CalendarType.HIJRI), "ShortDatePattern - d");
            msAssert.areEqual("2", WordUtil.formatDateTime(new DateTime(2007, 1, 20), "d", 0, CalendarType.HIJRI), "ShortDatePattern - d");
            msAssert.areEqual("1", WordUtil.formatDateTime(new DateTime(2007, 2, 18), "d", 0, CalendarType.HIJRI), "ShortDatePattern - d");

            msAssert.areEqual("12", WordUtil.formatDateTime(new DateTime(2007, 1, 1), "dd", 0, CalendarType.HIJRI), "ShortDatePattern - dd");
            msAssert.areEqual("02", WordUtil.formatDateTime(new DateTime(2007, 1, 20), "dd", 0, CalendarType.HIJRI), "ShortDatePattern - dd");
            msAssert.areEqual("01", WordUtil.formatDateTime(new DateTime(2007, 2, 18), "dd", 0, CalendarType.HIJRI), "ShortDatePattern - dd");

            // Short name of week's day - ddd
            String[] weekDays = { "الاثنين", "الثلاثاء", "الاربعاء", "الخميس", "الجمعة", "السبت", "الاحد" };
            for (int i = 1; i <= weekDays.length; i++)
            {
                msAssert.areEqual(normalizeString(weekDays[i - 1]), normalizeString(WordUtil.formatDateTime(
                        new DateTime(2007, 1, i), "ddd", 0, CalendarType.HIJRI)), "Short name of week's day - ddd");
                msAssert.areEqual(normalizeString(weekDays[i - 1]), normalizeString(WordUtil.formatDateTime(
                        new DateTime(2007, 1, i), "DDD", 0, CalendarType.HIJRI)), "Short name of week's day - DDD");
            }

            // Long name of week's day - dddd
            String[] longWeekDays = { "الاثنين", "الثلاثاء", "الاربعاء", "الخميس", "الجمعة", "السبت", "الاحد" };
            for (int i = 1; i <= longWeekDays.length; i++)
            {
                msAssert.areEqual(normalizeString(longWeekDays[i - 1]), normalizeString(WordUtil.formatDateTime(new DateTime(2007, 1, i), "dddd", 0, CalendarType.HIJRI)), "Long name of week's day - dddd");
                msAssert.areEqual(normalizeString(longWeekDays[i - 1]), normalizeString(WordUtil.formatDateTime(new DateTime(2007, 1, i), "DDDD", 0, CalendarType.HIJRI)), "Long name of week's day - DDDD");
            }

            // Months and date - M
            msAssert.areEqual("12", WordUtil.formatDateTime(new DateTime(2007, 1, 1), "M", 0, CalendarType.HIJRI), "Months and date - M");
            msAssert.areEqual("1", WordUtil.formatDateTime(new DateTime(2007, 1, 20), "M", 0, CalendarType.HIJRI), "Months and date - M");
            msAssert.areEqual("2", WordUtil.formatDateTime(new DateTime(2007, 2, 18), "M", 0, CalendarType.HIJRI), "Months and date - M");

            // Number of month - MM
            msAssert.areEqual("12", WordUtil.formatDateTime(new DateTime(2007, 1, 1), "MM", 0, CalendarType.HIJRI), "Months and date - MM");
            msAssert.areEqual("01", WordUtil.formatDateTime(new DateTime(2007, 1, 20), "MM", 0, CalendarType.HIJRI), "Months and date - MM");
            msAssert.areEqual("02", WordUtil.formatDateTime(new DateTime(2007, 2, 18), "MM", 0, CalendarType.HIJRI), "Months and date - MM");

            // Short month name - MMM
            msAssert.areEqual(normalizeString("ذو الحجة"), normalizeString(WordUtil.formatDateTime(new DateTime(2007, 1, 1), "MMM", 0, CalendarType.HIJRI)), "Short month name - MMM");
            msAssert.areEqual(normalizeString("محرم"), normalizeString(WordUtil.formatDateTime(new DateTime(2007, 1, 20), "MMM", 0, CalendarType.HIJRI)), "Short month name - MMM");
            msAssert.areEqual(normalizeString("صفر"), normalizeString(WordUtil.formatDateTime(new DateTime(2007, 2, 19), "MMM", 0, CalendarType.HIJRI)), "Short month name - MMM");
            msAssert.areEqual(normalizeString("ربيع الاول"), normalizeString(WordUtil.formatDateTime(new DateTime(2007, 3, 20), "MMM", 0, CalendarType.HIJRI)), "Short month name - MMM");
            msAssert.areEqual(normalizeString("ربيع الثاني"), normalizeString(WordUtil.formatDateTime(new DateTime(2007, 4, 18), "MMM", 0, CalendarType.HIJRI)), "Short month name - MMM");
            msAssert.areEqual(normalizeString("جمادى الاولى"), normalizeString(WordUtil.formatDateTime(new DateTime(2007, 5, 18), "MMM", 0, CalendarType.HIJRI)), "Short month name - MMM");
            msAssert.areEqual(normalizeString("جمادى الثانية"), normalizeString(WordUtil.formatDateTime(new DateTime(2007, 6, 16), "MMM", 0, CalendarType.HIJRI)), "Short month name - MMM");
            msAssert.areEqual(normalizeString("رجب"), normalizeString(WordUtil.formatDateTime(new DateTime(2007, 7, 15), "MMM", 0, CalendarType.HIJRI)), "Short month name - MMM");
            msAssert.areEqual(normalizeString("شعبان"), normalizeString(WordUtil.formatDateTime(new DateTime(2007, 8, 14), "MMM", 0, CalendarType.HIJRI)), "Short month name - MMM");
            msAssert.areEqual(normalizeString("رمضان"), normalizeString(WordUtil.formatDateTime(new DateTime(2007, 9, 13), "MMM", 0, CalendarType.HIJRI)), "Short month name - MMM");
            msAssert.areEqual(normalizeString("شوال"), normalizeString(WordUtil.formatDateTime(new DateTime(2007, 10, 13), "MMM", 0, CalendarType.HIJRI)), "Short month name - MMM");
            msAssert.areEqual(normalizeString("ذو القعدة"), normalizeString(WordUtil.formatDateTime(new DateTime(2007, 11, 11), "MMM", 0, CalendarType.HIJRI)), "Short month name - MMM");

            // Year - y, yy
            msAssert.areEqual("27", WordUtil.formatDateTime(new DateTime(2007, 1, 1), "y", 0, CalendarType.HIJRI), "Year - y");
            msAssert.areEqual("28", WordUtil.formatDateTime(new DateTime(2007, 1, 20), "y", 0, CalendarType.HIJRI), "Year - y");
            msAssert.areEqual("29", WordUtil.formatDateTime(new DateTime(2008, 1, 10), "y", 0, CalendarType.HIJRI), "Year - y");

            msAssert.areEqual("27", WordUtil.formatDateTime(new DateTime(2007, 1, 1), "yy", 0, CalendarType.HIJRI), "Year - yy");
            msAssert.areEqual("28", WordUtil.formatDateTime(new DateTime(2007, 1, 20), "yy", 0, CalendarType.HIJRI), "Year - yy");
            msAssert.areEqual("29", WordUtil.formatDateTime(new DateTime(2008, 1, 10), "yy", 0, CalendarType.HIJRI), "Year - yy");

            // Year - yyy,yyyy
            msAssert.areEqual("1427", WordUtil.formatDateTime(new DateTime(2007, 1, 1), "yyy", 0, CalendarType.HIJRI), "Year - yyy");
            msAssert.areEqual("1428", WordUtil.formatDateTime(new DateTime(2007, 1, 20), "yyy", 0, CalendarType.HIJRI), "Year - yyy");
            msAssert.areEqual("1429", WordUtil.formatDateTime(new DateTime(2008, 1, 10), "yyy", 0, CalendarType.HIJRI), "Year - yyy");

            msAssert.areEqual("1427", WordUtil.formatDateTime(new DateTime(2007, 1, 1), "yyyy", 0, CalendarType.HIJRI), "Year - yyyy");
            msAssert.areEqual("1428", WordUtil.formatDateTime(new DateTime(2007, 1, 20), "yyyy", 0, CalendarType.HIJRI), "Year - yyyy");
            msAssert.areEqual("1429", WordUtil.formatDateTime(new DateTime(2008, 1, 10), "yyyy", 0, CalendarType.HIJRI), "Year - yyyy");

            // Hours - hh,HH
            for (int i = 1; i < 24; i++)
            {
                msAssert.areEqual(alignNumber(i > 12 ? i - 12 : i, 2), WordUtil.formatDateTime(new DateTime(2007, 1, 1).addHours(i), "hh", 0, CalendarType.HIJRI), "Hours - hh");
                msAssert.areEqual(alignNumber(i, 2), WordUtil.formatDateTime(new DateTime(2007, 1, 1).addHours(i), "HH", 0, CalendarType.HIJRI), "Hours - HH");
            }

            // Minutes, seconds
            for (int i = 0; i < 60; i++)
            {
                msAssert.areEqual(alignNumber(i, 1), WordUtil.formatDateTime(new DateTime(2007, 1, 1).addMinutes(i), "m", 0, CalendarType.HIJRI), "Minutes - m");
                msAssert.areEqual(alignNumber(i, 2), WordUtil.formatDateTime(new DateTime(2007, 1, 1).addMinutes(i), "mm", 0, CalendarType.HIJRI), "Minutes - mm");

                msAssert.areEqual(alignNumber(i, 1), WordUtil.formatDateTime(new DateTime(2007, 1, 1).addSeconds(i), "s", 0, CalendarType.HIJRI), "Seconds - s");
                msAssert.areEqual(alignNumber(i, 2), WordUtil.formatDateTime(new DateTime(2007, 1, 1).addSeconds(i), "ss", 0, CalendarType.HIJRI), "Seconds - ss");
            }

            // AP/PM
            msAssert.areEqual("ص", WordUtil.formatDateTime(new DateTime(2007, 1, 1, 10, 0, 0), "tt", 0, CalendarType.HIJRI), "AM/PM - tt");
            msAssert.areEqual("م", WordUtil.formatDateTime(new DateTime(2007, 1, 1, 17, 0, 0), "tt", 0, CalendarType.HIJRI), "AM/PM - tt");

        }
        finally
        {
            SystemPal.restoreCulture();
        }
    }

    /// <summary>
    /// Autoportable tests of Hebrew and Arabic DateTime string constants.
    /// Formally this test should be in JavaMs.Tests module but leaved here to remove duplication
    /// and links from JavaMs.Tests to Aspose.Words.
    /// </summary>
    @Test
    public void testArabicDateFormattingToString()
    {
        SystemPal.saveCulture();
        try
        {
            // Reset TestMode.
            // TestMode flag is used only to ensure CultureInfo ignores user overrides in Windows Control Panel.
            // But it works different for Java, .NET and .NET Standard platforms (One day shift in Arabic DateTime formatting)
            // That is why we decided to reset TestMode for this test, to get equals results on all platforms.
            SystemPal.setTestMode(false);

            SystemPal.setCulture("ar-SA");

            // ShortDatePattern - d
            msAssert.areEqual("11/12/27", new DateTime(2007, 1, 1).toString("d"), "ShortDatePattern - d");
            msAssert.areEqual("01/01/28", new DateTime(2007, 1, 20).toString("d"), "ShortDatePattern - d");
            msAssert.areEqual("30/01/28", new DateTime(2007, 2, 18).toString("d"), "ShortDatePattern - d");

            // Month's day - dd
            msAssert.areEqual("11", new DateTime(2007, 1, 1).toString("dd"), "Month's day - dd");
            msAssert.areEqual("01", new DateTime(2007, 1, 20).toString("dd"), "Month's day - dd");
            msAssert.areEqual("30", new DateTime(2007, 2, 18).toString("dd"), "Month's day - dd");

            // Short name of week's day - ddd
            String[] weekDays = { "الاثنين", "الثلاثاء", "الاربعاء", "الخميس", "الجمعة", "السبت", "الاحد" };
            for (int i = 1; i <= weekDays.length; i++)
            {
                msAssert.areEqual(normalizeString(weekDays[i - 1]), normalizeString(new DateTime(2007, 1, i).toString("ddd")), "Short name of week's day - ddd");
                msAssert.areEqual(normalizeString(weekDays[i - 1]), normalizeString(new DateTime(2007, 1, i).toString("dddd")), "Short name of week's day - dddd");
            }

            // LongDatePattern - D
            msAssert.areEqual(normalizeString("11/ذو الحجة/1427"), normalizeString(new DateTime(2007, 1, 1).toString("D")), "LongDatePattern - D");
            msAssert.areEqual(normalizeString("01/محرم/1428"), normalizeString(new DateTime(2007, 1, 20).toString("D")), "LongDatePattern - D");
            msAssert.areEqual(normalizeString("01/صفر/1428"), normalizeString(new DateTime(2007, 2, 19).toString("D")), "LongDatePattern - D");

            // The period or era - gg
            msAssert.areEqual(normalizeString("بعد الهجرة"), normalizeString(new DateTime(2007, 1, 1).toString("gg")), "The period or era - gg");

            // Months and date - M
            msAssert.areEqual(normalizeString("11 ذو الحجة"), normalizeString(new DateTime(2007, 1, 1).toString("M")), "Months and date - M");
            msAssert.areEqual(normalizeString("01 محرم"), normalizeString(new DateTime(2007, 1, 20).toString("M")), "Months and date - M");
            msAssert.areEqual(normalizeString("01 صفر"), normalizeString(new DateTime(2007, 2, 19).toString("M")), "Months and date - M");

            // Number of month - MM
            msAssert.areEqual("12", new DateTime(2007, 1, 1).toString("MM"), "Months and date - MM");
            msAssert.areEqual("01", new DateTime(2007, 1, 20).toString("MM"), "Months and date - MM");
            msAssert.areEqual("01", new DateTime(2007, 2, 18).toString("MM"), "Months and date - MM");

            // Short month name - MMM
            msAssert.areEqual(normalizeString("ذو الحجة"), normalizeString(new DateTime(2007, 1, 1).toString("MMM")), "Short month name - MMM");
            msAssert.areEqual(normalizeString("محرم"), normalizeString(new DateTime(2007, 1, 20).toString("MMM")), "Short month name - MMM");
            msAssert.areEqual(normalizeString("صفر"), normalizeString(new DateTime(2007, 2, 19).toString("MMM")), "Short month name - MMM");
            msAssert.areEqual(normalizeString("ربيع الاول"), normalizeString(new DateTime(2007, 3, 20).toString("MMM")), "Short month name - MMM");
            msAssert.areEqual(normalizeString("ربيع الثاني"), normalizeString(new DateTime(2007, 4, 18).toString("MMM")), "Short month name - MMM");
            msAssert.areEqual(normalizeString("جمادى الاولى"), normalizeString(new DateTime(2007, 5, 18).toString("MMM")), "Short month name - MMM");
            msAssert.areEqual(normalizeString("جمادى الثانية"), normalizeString(new DateTime(2007, 6, 16).toString("MMM")), "Short month name - MMM");
            msAssert.areEqual(normalizeString("رجب"), normalizeString(new DateTime(2007, 7, 15).toString("MMM")), "Short month name - MMM");
            msAssert.areEqual(normalizeString("شعبان"), normalizeString(new DateTime(2007, 8, 14).toString("MMM")), "Short month name - MMM");
            msAssert.areEqual(normalizeString("رمضان"), normalizeString(new DateTime(2007, 9, 13).toString("MMM")), "Short month name - MMM");
            msAssert.areEqual(normalizeString("شوال"), normalizeString(new DateTime(2007, 10, 13).toString("MMM")), "Short month name - MMM");
            msAssert.areEqual(normalizeString("ذو القعدة"), normalizeString(new DateTime(2007, 11, 11).toString("MMM")), "Short month name - MMM");

            // Long month name - MMMM
            msAssert.areEqual(normalizeString("ذو الحجة"), normalizeString(new DateTime(2007, 1, 1).toString("MMMM")), "Long month name - MMMM");
            msAssert.areEqual(normalizeString("محرم"), normalizeString(new DateTime(2007, 1, 20).toString("MMMM")), "Long month name - MMMM");
            msAssert.areEqual(normalizeString("صفر"), normalizeString(new DateTime(2007, 2, 19).toString("MMMM")), "Long month name - MMMM");
            msAssert.areEqual(normalizeString("ربيع الأول"), normalizeString(new DateTime(2007, 3, 20).toString("MMMM")), "Long month name - MMMM");
            msAssert.areEqual(normalizeString("ربيع الثاني"), normalizeString(new DateTime(2007, 4, 18).toString("MMMM")), "Long month name - MMMMM");
            msAssert.areEqual(normalizeString("جمادى الأولى"), normalizeString(new DateTime(2007, 5, 18).toString("MMMM")), "Long month name - MMMM");
            msAssert.areEqual(normalizeString("جمادى الثانية"), normalizeString(new DateTime(2007, 6, 16).toString("MMMM")), "Long month name - MMMM");
            msAssert.areEqual(normalizeString("رجب"), normalizeString(new DateTime(2007, 7, 15).toString("MMMM")), "Long month name - MMMM");
            msAssert.areEqual(normalizeString("شعبان"), normalizeString(new DateTime(2007, 8, 14).toString("MMMM")), "Long month name - MMMM");
            msAssert.areEqual(normalizeString("رمضان"), normalizeString(new DateTime(2007, 9, 13).toString("MMMM")), "Long month name - MMMM");
            msAssert.areEqual(normalizeString("شوال"), normalizeString(new DateTime(2007, 10, 13).toString("MMMM")), "Long month name - MMMM");
            msAssert.areEqual(normalizeString("ذو القعدة"), normalizeString(new DateTime(2007, 11, 11).toString("MMMM")), "Long month name - MMMM");

            // Month and year - y
            msAssert.areEqual(normalizeString("ذو الحجة, 1427"), normalizeString(new DateTime(2007, 1, 1).toString("y")), "Month and year - y");
            msAssert.areEqual(normalizeString("محرم, 1428"), normalizeString(new DateTime(2007, 1, 20).toString("y")), "Month and year - y");
            msAssert.areEqual(normalizeString("محرم, 1429"), normalizeString(new DateTime(2008, 1, 10).toString("y")), "Month and year - y");

            // Year - yy
            msAssert.areEqual("27", new DateTime(2007, 1, 1).toString("yy"), "Year - yy");
            msAssert.areEqual("28", new DateTime(2007, 1, 20).toString("yy"), "Year - yy");
            msAssert.areEqual("29", new DateTime(2008, 1, 10).toString("yy"), "Year - yy");

            // Year - yyyy
            msAssert.areEqual("1427", new DateTime(2007, 1, 1).toString("yyyy"), "Year - yyyy");
            msAssert.areEqual("1428", new DateTime(2007, 1, 20).toString("yyyy"), "Year - yyyy");
            msAssert.areEqual("1429", new DateTime(2008, 1, 10).toString("yyyy"), "Year - yyyy");

            // Hours - hh,HH
            for (int i = 1; i < 24; i++)
            {
                msAssert.areEqual(alignNumber(i > 12 ? i - 12 : i, 2), new DateTime(2007, 1, 1).addHours(i).toString("hh"), "Hours - hh");
                msAssert.areEqual(alignNumber(i, 2), new DateTime(2007, 1, 1).addHours(i).toString("HH"), "Hours - HH");
            }

            // Minutes, seconds
            for (int i = 0; i < 60; i++)
            {
                msAssert.areEqual(alignNumber(i, 2), new DateTime(2007, 1, 1).addMinutes(i).toString("mm"), "Minutes - mm");
                msAssert.areEqual(alignNumber(i, 2), new DateTime(2007, 1, 1).addSeconds(i).toString("ss"), "Seconds - ss");
            }

            // AM/PM
            msAssert.areEqual("ص", new DateTime(2007, 1, 1, 10, 0, 0).toString("tt"), "AM/PM - tt");
            msAssert.areEqual("م", new DateTime(2007, 1, 1, 17, 0, 0).toString("tt"), "AM/PM - tt");
        }
        finally
        {
            SystemPal.restoreCulture();
            SystemPal.setTestMode(true);
        }
    }

    private static String alignNumber(int value, int amount)
    {
        return msString.padLeft(Integer.toString(value), amount, '0');
    }

    private static String normalizeString(String source)
    {
        return msString.replace(msString.replace(msString.trim(source), '\u0623', '\u0627'), '\u0625', '\u0627');
    }

    // Hebrew Month's day names - d,dd
    private static /*final*/ String[] G_HEBREW_MONTH_DAYS =
    {
        "י\"א", "י\"ב", "י\"ג", "י\"ד", "ט\"ו", "ט\"ז", "י\"ז", "י\"ח", "י\"ט", "כ'",
        "כ\"א", "כ\"ב", "כ\"ג", "כ\"ד", "כ\"ה", "כ\"ו", "כ\"ז", "כ\"ח", "כ\"ט", "א'",
        "ב'", "ג'", "ד'", "ה'", "ו'", "ז'", "ח'", "ט'", "י'", "י\"א", "י\"ב"
    };

    // Hebrew full week day name - dddd
    private static /*final*/ String[] G_HEBREW_WEEK_DAYS = { "יום שני", "יום שלישי", "יום רביעי", "יום חמישי", "יום שישי", "שבת", "יום ראשון" };
}


