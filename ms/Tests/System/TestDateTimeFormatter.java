// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/08/2012 by Vyacheslav Durin
// 2016/02/18 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.Globalization.msDateTimeFormatInfo;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.ms.System.DateTime;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;



@Test
public class TestDateTimeFormatter
{
    @Test
    public void testDateTimeToStringWithUnsupportedSymbols()
    {
        msDateTimeFormatInfo locale = new msCultureInfo("en-US", false).getDateTimeFormat();
        DateTime d = new DateTime(2006, 8, 22, 6, 30, 7);
        msAssert.areEqual("2006-08-22T06:30:07.0000000", d.toString("yyyy-MM-ddTHH:mm:ss.fffffff", locale));
    }

    @Test
    public void testDateTimeToString()
    {
        msDateTimeFormatInfo locale = new msCultureInfo("en-US", false).getDateTimeFormat();
        DateTime d = new DateTime(2006, 8, 22, 6, 30, 7);

        msAssert.areEqual("08/22/2006", d.toString("MM/dd/yyyy", locale));
        msAssert.areEqual("Tuesday, 22 August 2006", d.toString("dddd, dd MMMM yyyy", locale));
        msAssert.areEqual("Tuesday, 22 August 2006 06:30", d.toString("dddd, dd MMMM yyyy HH:mm", locale));
        msAssert.areEqual("Tuesday, 22 August 2006 06:30 AM", d.toString("dddd, dd MMMM yyyy hh:mm tt", locale));
        msAssert.areEqual("Tuesday, 22 August 2006 6:30", d.toString("dddd, dd MMMM yyyy H:mm", locale));
        msAssert.areEqual("Tuesday, 22 August 2006 6:30 AM", d.toString("dddd, dd MMMM yyyy h:mm tt", locale));
        msAssert.areEqual("Tuesday, 22 August 2006 06:30:07", d.toString("dddd, dd MMMM yyyy HH:mm:ss", locale));
        msAssert.areEqual("08/22/2006 06:30", d.toString("MM/dd/yyyy HH:mm", locale));
        msAssert.areEqual("08/22/2006 06:30 AM", d.toString("MM/dd/yyyy hh:mm tt", locale));
        msAssert.areEqual("08/22/2006 6:30", d.toString("MM/dd/yyyy H:mm", locale));
        msAssert.areEqual("08/22/2006 6:30 AM", d.toString("MM/dd/yyyy h:mm tt", locale));
        msAssert.areEqual("08/22/2006 06:30:07", d.toString("MM/dd/yyyy HH:mm:ss", locale));
        msAssert.areEqual("August 22", d.toString("MMMM dd", locale));
        msAssert.areEqual("Tue, 22 Aug 2006 06:30:07 GMT", d.toString("ddd, dd MMM yyyy HH':'mm':'ss 'GMT'", locale));
        msAssert.areEqual("Tue, 22 Aug 2006 06:30:07 GMT", d.toString("ddd, dd MMM yyyy HH:mm:ss 'GMT'", locale));
        msAssert.areEqual("2006-08-22T06:30:07", d.toString("yyyy'-'MM'-'dd'T'HH':'mm':'ss", locale));
        msAssert.areEqual("2006-08-22T06:30:07", d.toString("yyyy-MM-ddTHH:mm:ss", locale));
        msAssert.areEqual("06:30", d.toString("HH:mm", locale));
        msAssert.areEqual("06:30 AM", d.toString("hh:mm tt", locale));
        msAssert.areEqual("6:30", d.toString("H:mm", locale));
        msAssert.areEqual("6:30 AM", d.toString("h:mm tt", locale));
        msAssert.areEqual("06:30:07", d.toString("HH:mm:ss", locale));
        msAssert.areEqual("2006-08-22 06:30:07Z", d.toString("yyyy'-'MM'-'dd HH':'mm':'ss'Z'", locale));
        msAssert.areEqual("2006-08-22 06:30:07Z", d.toString("yyyy-MM-dd HH:mm:ssZ", locale));
        msAssert.areEqual("Tuesday, 22 August 2006 06:30:07", d.toString("dddd, dd MMMM yyyy HH:mm:ss", locale));
        msAssert.areEqual("2006 August", d.toString("yyyy MMMM", locale));
    }

    @Test
    public void testDateTimeToXmlUtc()
    {
        //yyyy-MM-dd'T'HH:mm:ss'Z'
        DateTime d = new DateTime(2010, 12, 1, 9, 30, 11);
        msAssert.areEqual("2010-12-01T09:30:11Z", d.toString("yyyy-MM-ddTHH:mm:ssZ"));
    }

    @Test
    public void testDateTimeToXmlNoTimezone()
    {
        //yyyy-MM-dd'T'HH:mm:ss
        DateTime d = new DateTime(2010, 12, 1, 9, 30, 11);
        msAssert.areEqual("2010-12-01T09:30:11", d.toString("yyyy-MM-ddTHH:mm:ss"));
    }

    @Test
    public void testDateTimeToStr_yyyyMMddHHmmssZ()
    {
        //yyyyMMddHHmmss'Z'
        DateTime d = new DateTime(2012, 9, 1, 10, 0, 0);
        msAssert.areEqual("20120901100000Z", d.toString("yyyyMMddHHmmssZ"));
    }

    @Test
    public void testDateTimeToXmlRus()
    {
        SystemPal.saveCulture();
        try
        {
            SystemPal.setCulture("ru-RU");

            DateTime d = new DateTime(2010, 12, 1, 9, 30, 11);
            msAssert.areEqual("2010-12-01T09:30:11Z", d.toString("yyyy-MM-ddTHH:mm:ssZ"));
            msAssert.areEqual("2010-12-01T09:30:11", d.toString("yyyy-MM-ddTHH:mm:ss"));
        }
        finally
        {
            SystemPal.restoreCulture();
        }
    }

    @Test
    public void testDateTimeToXml()
    {
        DateTime d = new DateTime(2010, 12, 1, 9, 30, 11);
        msAssert.areEqual("2010-12-01T09:30:11Z", d.toString("yyyy-MM-ddTHH:mm:ssZ"));
        msAssert.areEqual("2010-12-01T09:30:11", d.toString("yyyy-MM-ddTHH:mm:ss"));
    }

    @Test
    public void testInvariantNetFormatter()
    {
        try
        {
            TestDateTime.setInvariantLocale();

            DateTime d = new DateTime(2010, 12, 1, 9, 30, 11);

            String longDatePattern = "dddd, dd MMMM yyyy";
            String longTimePattern = "HH:mm:ss";
            String shortDatePattern = "MM/dd/yyyy";
            String shortTimePattern = "HH:mm";
            String monthDayPattern = "MMMM dd";
            String yearMonthPattern = "yyyy MMMM";
            String fullDateTimePattern = "dddd, dd MMMM yyyy HH:mm:ss";
            String rFC1123Pattern = "ddd, dd MMM yyyy HH':'mm':'ss 'GMT'";
            String sortableDateTimePattern = "yyyy'-'MM'-'dd'T'HH':'mm':'ss";
            String universalSortableDateTimePattern = "yyyy'-'MM'-'dd HH':'mm':'ss'Z'";

            String longDatePatternResult = d.toString(longDatePattern);
            String longTimePatternResult = d.toString(longTimePattern);
            String shortDatePatternResult = d.toString(shortDatePattern);
            String shortTimePatternResult = d.toString(shortTimePattern);
            String monthDayPatternResult = d.toString(monthDayPattern);
            String yearMonthPatternResult = d.toString(yearMonthPattern);
            String fullDateTimePatternResult = d.toString(fullDateTimePattern);
            String rFC1123PatternResult = d.toString(rFC1123Pattern);
            String sortableDateTimePatternResult = d.toString(sortableDateTimePattern);
            String universalSortableDateTimePatternResult = d.toString(universalSortableDateTimePattern);

            msAssert.areEqual(longDatePatternResult, "Wednesday, 01 December 2010");
            msAssert.areEqual(longTimePatternResult, "09:30:11");
            msAssert.areEqual(shortDatePatternResult, "12/01/2010");
            msAssert.areEqual(shortTimePatternResult, "09:30");
            msAssert.areEqual(monthDayPatternResult, "December 01");
            msAssert.areEqual(yearMonthPatternResult, "2010 December");
            msAssert.areEqual(fullDateTimePatternResult, "Wednesday, 01 December 2010 09:30:11");
            msAssert.areEqual(rFC1123PatternResult, "Wed, 01 Dec 2010 09:30:11 GMT");
            msAssert.areEqual(sortableDateTimePatternResult, "2010-12-01T09:30:11");
            msAssert.areEqual(universalSortableDateTimePatternResult, "2010-12-01 09:30:11Z");
        }
        finally
        {
            TestDateTime.restoreLocale();
        }
    }

    /// <summary>
    /// WORDSNET-13842 Date field not transformed correctly if used Hijri/Lunar Calendar. 
    /// </summary>
    @Test
    public void testJira13842OriginalFeedback()
    {
        DateTime d = new DateTime(2016, 12, 7);
        msDateTimeFormatInfo englishFormat = new msCultureInfo("en-US", false).getDateTimeFormat();
        msDateTimeFormatInfo hebrewFormat = new msCultureInfo("he-IL", false).getDateTimeFormat();

        msAssert.areEqual("07 December 2016", d.toString("dd MMMM yyyy", englishFormat));
        msAssert.areEqual("07 דצמבר 2016", d.toString("dd MMMM yyyy", hebrewFormat));
        msAssert.areEqual("07 December 2016", d.toString("dd MMMM yyyy", englishFormat));
    }
}

