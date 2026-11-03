// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/08/2012 by Vyacheslav Durin
// 2016/02/19 by Anatoliy Sidorenko

/**
 * http://www.dotnetperls.com/datetime-format
 * http://msdn.microsoft.com/en-us/library/az4se3k1.aspx
 * http://www.geekzilla.co.uk/View00FF7904-B510-468C-A2C8-F859AA20581F.htm
 * http://msdn.microsoft.com/en-us/library/8kb3ddd4.aspx
 */

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.Threading.CurrentThread;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.Globalization.msDateTimeFormatInfo;
import com.aspose.ms.System.Globalization.DateTimeStyles;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.words.ref.Ref;


@Test
public class TestDateTimeParse
{
    @Test
    public void testDateTimeString()
    {
        try
        {
            CurrentThread.setCurrentCulture(new msCultureInfo("en-US", false));
            int nowadaysYear = DateTime.getNow().getYear();

            checkTime("11:06", 11, 6, 0);
            checkTime("11:07:03", 11, 7, 3);
            checkDate("01/05/2012", 2012, 1, 5);
            checkDate("2006-01-05", 2006, 1, 5);
            checkTime("7:09:01 pm", 19, 9, 1);
            checkTime("9:01 am", 9, 1, 0);
            checkDateTime("7:09:02 pm 5/01/2006 ", 2006, 5, 1, 19, 9, 2);
            checkDate("5 January 2006", 2006, 1, 5);
            checkDate("Thursday, 5 January 2006", 2006, 1, 5);
            checkDate("5/01/06", 2006, 5, 1);
            checkDate("5.01.2006", 2006, 5, 1);
            checkDate("Jan-06", nowadaysYear, 1, 6);
            checkDate("5-Jan-06", 2006, 1, 5);
            checkDate("5 Jan 06", 2006, 1, 5);
            checkDateTime("2012-09-01 10:00:00", 2012, 9, 1, 10, 0, 0);
            checkDate("2012/09/01", 2012, 9, 1);
            checkDate("01 Sep 2012", 2012, 9, 1);
            checkDate("5 Sep. 06", 2006, 9, 5);
            checkDate("03 Feb. 2012", 2012, 2, 3);
            checkDateTime("01 Sep 2012 10:00:00", 2012, 9, 1, 10, 0, 0);
            checkDate("January 06", nowadaysYear, 1, 6);
        }
        finally
        {
            TestDateTime.restoreLocale();
        }
    }

    @Test
    public void testDateTimeStringEnNZ()
    {
        try
        {
            TestDateTime.setInvariantLocale();
            checkDate("01/09/2002", 2002, 9, 1);
        }
        finally
        {
            TestDateTime.restoreLocale();
        }
    }

    @Test
    public void testDateTimeStringEnUS()
    {
        try
        {
            CurrentThread.setCurrentCulture(new msCultureInfo("en-US", false));
            checkDate("01/09/2002", 2002, 1, 9);
        }
        finally
        {
            TestDateTime.restoreLocale();
        }
    }

    // Neither .Net or Java can parse it,
    @Test
    public void testDateTimeStringNotParseable()
    {
        try
        {
            TestDateTime.setInvariantLocale();
            // 1/01/0001 12:00:00 a.m.
            checkDateTime("01.09.12 10.00", 1, 1, 1, 0, 0, 0);
            checkDate("06-Thu-2011", 1, 1, 1);
            checkDateTime("Jun-30 16:05", 1, 1, 1, 0, 0, 0);
            checkDateTime("June, 30 16:5", 1, 1, 1, 0, 0, 0);
        }
        finally
        {
            TestDateTime.restoreLocale();
        }
    }

    @Test
    public void testDateTimeStringTimeZones()
    {
        try
        {
            TestDateTime.setInvariantLocaleAndTZ();
            // .Net has DateTimeStyle.NONE by default, timezone ignored.
            checkDateTime("Tue, 22 Aug 2006 06:30:07 GMT", 2006, 8, 22, 6, 30, 7);
            checkDateTime("2008-12-23T10:01:00Z", 2008, 12, 23, 10, 1, 0);
        }
        finally
        {
            TestDateTime.restoreLocaleAndTZ();
        }
    }

    @Test
    public void testDateTimeStringLocales()
    {
        try
        {
            CurrentThread.setCurrentCulture(new msCultureInfo("ru-RU", false));
            int nowadaysYear = DateTime.getNow().getYear();

            checkDate("26 августа", nowadaysYear, 8, 26);
            checkDateTime("01 Сен 2012 10:00:00", 2012, 9, 1, 10, 0, 0);
            checkDateTime("5.01.2006 19:09:02", 2006, 1, 5, 19, 9, 2);
        }
        finally
        {
            TestDateTime.restoreLocale();
        }
    }

    @Test
    public void testDateTimeNZLocale()
    {
        try
        {
            TestDateTime.setInvariantLocale();
            checkDateTime("5/01/2006 7:09:02 PM", 2006, 1, 5, 19, 9, 2);
        }
        finally
        {
            TestDateTime.restoreLocale();
        }
    }

    @Test
    public void testParseExact()
    {
        try
        {
            TestDateTime.setInvariantLocale();
            msDateTimeFormatInfo dfti = msCultureInfo.getCurrentCulture().getDateTimeFormat();
            DateTime dt = DateTime.parseExact("09:01", "H:mm", dfti);
            DateTime dt2 = DateTime.parseExact("17:01:05", "H:mm:ss", dfti);
            DateTime dt3 = DateTime.parseExact("5/01/2006", "d/MM/yyyy", dfti);
            DateTime dt4 = DateTime.parseExact("Wed, 01 Dec 2010 09:30:11 GMT", "ddd, d MMM yyyy HH':'mm':'ss GMT", dfti);
            DateTime dt5 = DateTime.parseExact("01.09.12 10.00", "dd.MM.yy H.mm", dfti);
            DateTime dt6 = DateTime.parseExact("2010-08-31T00:32:48Z",
                new String[] { "yyyy-MM-ddTHH:mm:ss,FF", "yyyy-MM-ddTHH:mm:ssZ", "yyyy-MM-ddTHH:mm:sszzz" },
                new msCultureInfo("en-NZ", false).getDateTimeFormat(), DateTimeStyles.ADJUST_TO_UNIVERSAL);

            checkTime(dt, 9, 1, 0);
            checkTime(dt2, 17, 1, 5);
            checkDate(dt3, 2006, 1, 5);
            checkDateTime(dt4, 2010, 12, 1, 9, 30, 11);
            checkDateTime(dt5, 2012, 9, 1, 10, 0, 0);
            checkDateTime(dt6, 2010, 8, 31, 0, 32, 48);
        }
        finally
        {
            TestDateTime.restoreLocale();
        }
    }

    /// <summary>
    /// This is simplified cause of TestDocumentInformation.TestJira4290() fail.
    /// </summary>
    @Test
    public void testTryParseFormatInfo()
    {
        //the test sets such DateTimeFormatInfo (to emulate real word?)
        msDateTimeFormatInfo formatInfo = new msDateTimeFormatInfo();
        formatInfo.setShortDatePattern("d/MM/yyyy'y.'");
        formatInfo.setLongTimePattern("'h: 'HH' m: 'mm");
        formatInfo.setTimeSeparator(" m: ");

        //then it gets date from document and formats it to the string (using DateTimeFormatInfo)
        DateTime dateTime = new DateTime(2012, 6, 8, 16, 51, 0);
        String str = "8/06/2012y. h: 16 m: 51";
        DateTime parsed = null;
        Ref<DateTime> referenceToParsed = new Ref<DateTime>(parsed);
        DateTime.tryParse(str, formatInfo, DateTimeStyles.ADJUST_TO_UNIVERSAL, /*out*/ referenceToParsed);
        parsed = referenceToParsed.get();
        msAssert.areEqual(dateTime, parsed);

        String strWithNormalTimeSep = "8/06/2012y. 16:51";
        DateTime parsedWithNormalTimeSep = null;
        Ref<DateTime> referenceToParsedWithNormalTimeSep = new Ref<DateTime>(parsedWithNormalTimeSep);
        DateTime.tryParse(strWithNormalTimeSep, formatInfo,
            DateTimeStyles.ADJUST_TO_UNIVERSAL, /*out*/ referenceToParsedWithNormalTimeSep);
        parsedWithNormalTimeSep = referenceToParsedWithNormalTimeSep.get();
        msAssert.areEqual(dateTime, parsedWithNormalTimeSep);
    }

    @Test
    public void testTryParseHebrew()
    {
        msDateTimeFormatInfo hebrewDTFI = new msCultureInfo("he-IL", false).getDateTimeFormat();
        DateTime dt = null;
        Ref<DateTime> referenceToDt = new Ref<DateTime>(dt);
        DateTime.tryParse("4 יולי 1986", hebrewDTFI, DateTimeStyles.NONE, /*out*/ referenceToDt);
        dt = referenceToDt.get();
        DateTime dt2 = null;
        Ref<DateTime> referenceToDt2 = new Ref<DateTime>(dt2);
        DateTime.tryParse("23 פברואר 2012", hebrewDTFI, DateTimeStyles.NONE, /*out*/ referenceToDt2);
        dt2 = referenceToDt2.get();
        DateTime dt3 = null;
        Ref<DateTime> referenceToDt3 = new Ref<DateTime>(dt3);
        DateTime.tryParse("ג אדר ה'תשע\"ב", hebrewDTFI, DateTimeStyles.NONE, /*out*/ referenceToDt3);
        dt3 = referenceToDt3.get();

        checkDate(new DateTime(1986, 7, 4, 0, 0, 0), dt.getYear(), dt.getMonth(), dt.getDay());
        checkDate(new DateTime(2012, 2, 23, 0, 0, 0), dt2.getYear(), dt2.getMonth(), dt2.getDay());
        checkDate(DateTime.MinValue, dt3.getYear(), dt3.getMonth(), dt3.getDay());
    }

    @Test
    public void testDateTimeFormatInfoForDifferentLocales()
    {
        DateTime dt = new DateTime(1986, 7, 4, 17, 45, 0);
        msCultureInfo ru = new msCultureInfo("ru-RU", false);
        msCultureInfo us = new msCultureInfo("en-US", false);
        msCultureInfo ko = new msCultureInfo("ko-KR", false);
        msCultureInfo nz = new msCultureInfo("en-NZ", false);
        msCultureInfo fr = new msCultureInfo("fr-FR", false);

        int style = (int)DateTimeStyles.ADJUST_TO_UNIVERSAL;
        DateTime parse = null;
        Ref<DateTime> referenceToParse = new Ref<DateTime>(parse);
        DateTime.tryParse("4 июля 1986 г. 17:45:00", ru.getDateTimeFormat(), (/*DateTimeStyles*/int)style, /*out*/ referenceToParse);
        parse = referenceToParse.get();
        msAssert.areEqual(parse, dt);
        referenceToParse.set(parse);
        DateTime.tryParse("07/04/1986. 5:45:00 pm", us.getDateTimeFormat(), (/*DateTimeStyles*/int)style, /*out*/ referenceToParse);
        parse = referenceToParse.get();
        msAssert.areEqual(parse, dt);
        referenceToParse.set(parse);
        DateTime.tryParse("04/07/1986. 5:45:00 PM", nz.getDateTimeFormat(), (/*DateTimeStyles*/int)style, /*out*/ referenceToParse);
        parse = referenceToParse.get();
        msAssert.areEqual(parse, dt);
        referenceToParse.set(parse);
        DateTime.tryParse("1986년 07월 04일 오후 5:45:00", ko.getDateTimeFormat(), (/*DateTimeStyles*/int)style, /*out*/ referenceToParse);
        parse = referenceToParse.get();
        msAssert.areEqual(parse, dt);
        referenceToParse.set(parse);
        DateTime.tryParse("4 juillet 1986 17:45:00", fr.getDateTimeFormat(), (/*DateTimeStyles*/int)style, /*out*/ referenceToParse);
        parse = referenceToParse.get();
        msAssert.areEqual(parse, dt);
    }

    @Test
    public void testUkUaLocaleJ645()
    {
        msDateTimeFormatInfo dtfi = new msCultureInfo("uk-UA", false).getDateTimeFormat();
        DateTime d = new DateTime(1986, 7, 4, 17, 45, 0);

        String dateStr = d.toString("dddd d MMMM yyyy H:mm", dtfi);
        msAssert.areEqual("п'ятниця 4 липня 1986 17:45", dateStr);

        DateTime dt = null;
        Ref<DateTime> referenceToDt = new Ref<DateTime>(dt);
        DateTime.tryParse("п'ятниця 4 липня 1986 17:45", dtfi, DateTimeStyles.NONE, /*out*/ referenceToDt);
        dt = referenceToDt.get();
        msAssert.areEqual(dt, d);
    }

    private void checkDate(DateTime actual, int year, int month, int day)
    {
        checkDateTime(actual, year, month, day, 0, 0, 0);
    }

    private void checkTime(DateTime actual, int hour, int min, int sec)
    {
        DateTime now = DateTime.getNow();
        checkDateTime(actual, now.getYear(), now.getMonth(), now.getDay(), hour, min, sec);
    }

    private void checkDateTime(DateTime actual, int year, int month, int day, int hour, int min, int sec)
    {
        DateTime expected = new DateTime(year, month, day, hour, min, sec);
        msAssert.areEqual(actual, expected);
    }

    private void checkDate(String dateStr, int year, int month, int day)
    {
        checkDateTime(dateStr, year, month, day, 0, 0, 0);
    }

    private void checkTime(String timeStr, int hour, int min, int sec)
    {
        DateTime now = DateTime.getNow();
        checkDateTime(timeStr, now.getYear(), now.getMonth(), now.getDay(), hour, min, sec);
    }

    private void checkDateTime(String dateTimeStr, int year, int month, int day, int hour, int min, int sec)
    {
        DateTime parsed;
        parsed = DateTime.tryParse(dateTimeStr);

        DateTime expected = new DateTime(year, month, day, hour, min, sec);
        msAssert.areEqual(parsed, expected);
    }
}

