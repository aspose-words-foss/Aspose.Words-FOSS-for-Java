// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/10/2007 by Konstantin Sidorenko
// 2016/02/09 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.DateTime;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.DateTimeKind;
import com.aspose.ms.System.TimeSpan;
import com.aspose.ms.System.Globalization.msDateTimeFormatInfo;
import com.aspose.ms.System.Globalization.DateTimeStyles;
import com.aspose.ms.System.Threading.CurrentThread;
import com.aspose.ms.System.Globalization.msCultureInfo;
import java.util.TimeZone;


/// <summary>
/// NOTE about invariant locale (en-NZ).
/// 
/// Sometimes generated resources\CultureInfo.xml (used on Java) can differ from the CultureInfo-s used here
/// (i.e. on .Net AW solution). In this case: this test will pass on .Net but fail on Java.
/// 
/// To fix see comments to CsPorer's XmlCultureInfoGenerator.
/// </summary>
@Test
public class TestDateTime
{
    @Test
    public void testConstants()
    {
        try
        {
            setInvariantLocale();

            DateTime max = DateTime.MaxValue;
            msAssert.areEqual(max.getTicks(), 3155378975999999999L);
            msAssert.areEqual(max.getKind(), DateTimeKind.UNSPECIFIED);
            msAssert.areEqual(max.toString(), "31/12/9999 11:59:59 pm");

            DateTime min = DateTime.MinValue;
            msAssert.areEqual(min.getTicks(), 0L);
            msAssert.areEqual(min.getKind(), DateTimeKind.UNSPECIFIED);
            msAssert.areEqual(min.getDay(), 1);
            msAssert.areEqual(min.getMonth(), 1);
            msAssert.areEqual(min.getYear(), 1);
            msAssert.areEqual(min.getHour(), 0);
            msAssert.areEqual(min.getMinute(), 0);
            msAssert.areEqual(min.getSecond(), 0);
            msAssert.areEqual(min.getMillisecond(), 0);
            //But ToString works not as in .net for dates before Gregorian Calendar CutOver date
            //(see TestGregorianCutOver()). - fixed (hacked) for DateTime.MinValue only.
            msAssert.areEqual(min.toString(), "1/01/0001 12:00:00 am");

            //round trip
            DateTime newMax = new DateTime(DateTime.MaxValue.getTicks());
            msAssert.areEqual(newMax.getTicks(), 3155378975999999999L);
            msAssert.areEqual(newMax.getKind(), DateTimeKind.UNSPECIFIED);
            msAssert.areEqual(newMax.toString(), "31/12/9999 11:59:59 pm");

            //Note: GregorianCutOver hack works not only for DateTime.MinValue instances,
            //for new DateTime(0L) works now too.
            DateTime newMin = new DateTime(DateTime.MinValue.getTicks());
            msAssert.areEqual(newMin.getTicks(), 0L);
            msAssert.areEqual(newMin.getKind(), DateTimeKind.UNSPECIFIED);
            msAssert.areEqual(newMin.toString(), "1/01/0001 12:00:00 am");
        }
        finally
        {
            restoreLocale();
        }
    }

    @Test
    public void testCtors()
    {
        try
        {
            setInvariantLocale();

            DateTime dateTime = new DateTime(1970, 1, 27);
            msAssert.areEqual(dateTime.getKind(), DateTimeKind.UNSPECIFIED);
            msAssert.areEqual(dateTime.toString(), "27/01/1970 12:00:00 am");

            dateTime = new DateTime(2007, 12, 31, 23, 59, 59);
            msAssert.areEqual(dateTime.getKind(), DateTimeKind.UNSPECIFIED);
            msAssert.areEqual(dateTime.toString(), "31/12/2007 11:59:59 pm");

            dateTime = new DateTime(2007, 12, 31, 23, 59, 59, 999);
            msAssert.areEqual(dateTime.getKind(), DateTimeKind.UNSPECIFIED);
            msAssert.areEqual(dateTime.toString(), "31/12/2007 11:59:59 pm");

            dateTime = new DateTime(2007, 12, 31, 23, 59, 59, DateTimeKind.LOCAL);
            msAssert.areEqual(dateTime.getKind(), DateTimeKind.LOCAL);
            msAssert.areEqual(dateTime.toString(), "31/12/2007 11:59:59 pm");

            dateTime = new DateTime(2007, 12, 31, 23, 59, 59, DateTimeKind.UTC);
            msAssert.areEqual(dateTime.getKind(), DateTimeKind.UTC);
            msAssert.areEqual(dateTime.toString(), "31/12/2007 11:59:59 pm");
        }
        finally
        {
            restoreLocale();
        }
    }

    /**
         * All dates before the Gregorian Calendar CutOver Date (October 15, 1582 in most vestern countries)
         * interpreted uncorrectly (or rather not as in .Net) by toUnixDate() method
         * because these mehtods use java.lang.Date internally.
         * DateTime.ToString() method does so too because uses toUnixDate() internally.
         */
    @Test
    public void testGregorianCutOver()
    {
        try
        {
            setInvariantLocale();

            msAssert.areEqual(new DateTime(9999, 12, 31, 23, 59, 59).toString(), "31/12/9999 11:59:59 pm");
            msAssert.areEqual(new DateTime(5555, 12, 31, 23, 59, 59).toString(), "31/12/5555 11:59:59 pm");
            msAssert.areEqual(new DateTime(2099, 12, 31, 23, 59, 59).toString(), "31/12/2099 11:59:59 pm");
            msAssert.areEqual(new DateTime(1999, 12, 31, 23, 59, 59).toString(), "31/12/1999 11:59:59 pm");
            msAssert.areEqual(new DateTime(1700, 12, 31, 23, 59, 59).toString(), "31/12/1700 11:59:59 pm");
            //the last correct date
            msAssert.areEqual(new DateTime(1582, 10, 15, 23, 59, 59).toString(), "15/10/1582 11:59:59 pm");

            //hack for DateTime.MinValue.ToString() works also for DateTime(0).
            msAssert.areEqual(DateTime.MinValue.toString(), "1/01/0001 12:00:00 am");
            msAssert.areEqual(new DateTime(1, 1, 1, 0, 0, 0).toString(), "1/01/0001 12:00:00 am");
            msAssert.areEqual(new DateTime(0).toString(), "1/01/0001 12:00:00 am");
        }
        finally
        {
            restoreLocale();
        }
    }

    @Test
    public void testDateTimesBefore1582Year()
    {
        try
        {
            setInvariantLocale();

            msAssert.areEqual(new DateTime(1582, 10, 14, 23, 59, 59).toString(), "14/10/1582 11:59:59 pm");
            msAssert.areEqual(new DateTime(1582, 10, 5, 23, 59, 59).toString(), "5/10/1582 11:59:59 pm");

            msAssert.areEqual(new DateTime(1582, 10, 4, 23, 59, 59).toString(), "4/10/1582 11:59:59 pm");
            msAssert.areEqual(new DateTime(1, 1, 1, 1, 1, 1).toString(), "1/01/0001 1:01:01 am");
        }
        finally
        {
            restoreLocale();
        }
    }

    /**
         * Few "features" because of slightly different interpretation of dates before Gregorian Calendar
         * CutOver Date (October 15, 1582 in most vestern countries):
         *
         * 1. The DateTime.MinValue converted by ours DateTime.toUnixDate() to "3/01/0001" instead
         * of "1/01/0001".
         * 2. DateTime.ToString() does the same since uses DateTime.toUnixDate() internally.
         *
         * -- Fixed by hack only for DateTime.MinValue.toUnixDate() (so for .ToString() too).
         * Because DateTime.MinValue.ToString() used widely by unit tests.
         */
    @Test
    public void testDateTimeMinValueHack()
    {
        try
        {
            setInvariantLocale();

            //What the hack works for:
            msAssert.areEqual(DateTime.MinValue.toString(), "1/01/0001 12:00:00 am");

            //Doesn't work for:
            Assert.assertNotSame(DateTime.MinValue.toJavaTicks(), DateTime.MinValueToUnixTicks);
            msAssert.areNotSame(DateTime.MinValue.toString(), new DateTime(DateTime.MinValue.getTicks()).toString());
        }
        finally
        {
            restoreLocale();
        }
    }

    @Test
    public void testLocalTime()
    {
        try
        {
            setInvariantLocale();

            DateTime dateTime = new DateTime(2007, 12, 31, 0, 0, 0);
            msAssert.areEqual(dateTime.getKind(), DateTimeKind.UNSPECIFIED);
            msAssert.areEqual(dateTime.toString(), "31/12/2007 12:00:00 am");

            DateTime localConverted = dateTime.toLocalTime();
            msAssert.areEqual(localConverted.getKind(), DateTimeKind.LOCAL);
            // This check depends on local timezone and even day light saving offset - 
            // the result will be different on different systems.
            //Assert.AreEqual(localConverted.ToString(), "31/12/2007 7:00:00 a.m.");
        }
        finally
        {
            restoreLocale();
        }
    }

    @Test
    public void testUniversalTime()
    {
        try
        {
            setInvariantLocale();

            DateTime dateTime = new DateTime(2007, 12, 31, 12, 0, 0);
            msAssert.areEqual(dateTime.getKind(), DateTimeKind.UNSPECIFIED);
            msAssert.areEqual(dateTime.toString(), "31/12/2007 12:00:00 pm");

            DateTime universalConverted = dateTime.toUniversalTime();
            msAssert.areEqual(universalConverted.getKind(), DateTimeKind.UTC);
            //sk: in my timezone this looks like this:
            //Assert.AreEqual(universalConverted.ToString(), "31/12/2007 06:00:00");
            //but we shuld calculate Local timezone offset so test can pass in any timezone:
            int javaOffset = CurrentThread.getTimeZone().getOffset(dateTime.toJavaTicks());
            DateTime universalCalculated = dateTime.addMilliseconds(-javaOffset);
            msAssert.areEqual(universalConverted.toString(), universalCalculated.toString());
        }
        finally
        {
            restoreLocale();
        }
    }

    /**
         * FileTime is Utc by nature. So Local DateTime always converted to Utc, but Unspecified treated
         * differently: ToFileTime() treats Unspecified as Local, ToFileTimeUtc() - as Utc.
         */
    @Test
    public void testToFileTime()
    {
        try
        {
            setInvariantLocale();

            //Utc doesn't converted by both methods.
            DateTime dateTime = new DateTime(2007, 12, 31, 12, 0, 0, DateTimeKind.UTC);
            msAssert.areEqual(dateTime.toFileTime(), 128435760000000000L);
            msAssert.areEqual(dateTime.toFileTimeUtc(), 128435760000000000L);

            //Local converted to Utc by both methods.
            //We shuld to calculate Local timezone offset so test can pass in any timezone.
            dateTime = new DateTime(2007, 12, 31, 12, 0, 0, DateTimeKind.LOCAL);
            DateTime universalCalculated = dateTime.toUniversalTime();
            msAssert.areEqual(dateTime.toFileTime(), universalCalculated.toFileTime());
            msAssert.areEqual(dateTime.toFileTimeUtc(), universalCalculated.toFileTimeUtc());
            msAssert.areEqual(dateTime.toFileTime(), dateTime.toFileTimeUtc());

            //ToFileTime() treats Unspecified as Local, ToFileTimeUtc() - as Utc.
            dateTime = new DateTime(2007, 12, 31, 12, 0, 0, DateTimeKind.UNSPECIFIED);
            msAssert.areEqual(dateTime.toFileTime(), universalCalculated.toFileTime());//Unspecified treated as Local.
            msAssert.areEqual(dateTime.toFileTimeUtc(), 128435760000000000L);//Unspecified treated as Utc.
        }
        finally
        {
            restoreLocale();
        }
    }

    /**
         * FileTime is Utc by nature.
         * FromFileTimeUtc() return Utc DateTime.
         * FromFileTime() returns DateTime comverted to Local timezone.
         */
    @Test
    public void testFromFileTime()
    {
        try
        {
            setInvariantLocale();

            DateTime dateTime = DateTime.fromFileTimeUtc(128435760000000000L);
            msAssert.areEqual(dateTime.getKind(), DateTimeKind.UTC);
            msAssert.areEqual(dateTime.toString(), "31/12/2007 12:00:00 pm");

            dateTime = DateTime.fromFileTime(128435760000000000L);
            msAssert.areEqual(dateTime.getKind(), DateTimeKind.LOCAL);
            msAssert.areEqual(dateTime.toUniversalTime().toString(), "31/12/2007 12:00:00 pm");
        }
        finally
        {
            restoreLocale();
        }
    }

    @Test
    public void testEquals()
    {
        //Equals() takes into account only ticks, DateTimeKind is ignored.
        DateTime dateTime1 = new DateTime(2000, 1, 1, 1, 1, 1, 1, DateTimeKind.UTC);
        DateTime dateTime2 = new DateTime(2000, 1, 1, 1, 1, 1, 1, DateTimeKind.LOCAL);
        msAssert.isTrue(dateTime1.equals(dateTime2));
        msAssert.isTrue(DateTime.equals(dateTime1, dateTime2));
        msAssert.areEqual(dateTime1.getTicks(), dateTime2.getTicks());
        msAssert.areEqual(dateTime1.getDay(), dateTime2.getDay());
        msAssert.areEqual(dateTime1.getDayOfWeek(), dateTime2.getDayOfWeek());
        msAssert.areEqual(dateTime1.getDayOfYear(), dateTime2.getDayOfYear());
        msAssert.areEqual(dateTime1.hashCode(), dateTime2.hashCode());
        msAssert.areEqual(dateTime1.getHour(), dateTime2.getHour());
        msAssert.areEqual(dateTime1.getMillisecond(), dateTime2.getMillisecond());
        msAssert.areEqual(dateTime1.getMinute(), dateTime2.getMinute());
        msAssert.areEqual(dateTime1.getMonth(), dateTime2.getMonth());
        msAssert.areEqual(dateTime1.getSecond(), dateTime2.getSecond());
        msAssert.areEqual(dateTime1.getTimeOfDay(), dateTime2.getTimeOfDay());
        msAssert.areEqual(dateTime1.getYear(), dateTime2.getYear());

        msAssert.isTrue(dateTime1.getKind() != dateTime2.getKind());

        TimeSpan span1 = new TimeSpan(1, 1, 1, 1, 1);
        TimeSpan span2 = new TimeSpan(1, 1, 1, 1, 1);
        msAssert.isTrue(span1.equals(span2));
        msAssert.isTrue(TimeSpan.equals(span1, span2));
    }

    @Test
    public void testAdd()
    {
        DateTime dateTime = new DateTime(2000, 1, 1, 1, 1, 1, 1);

        msAssert.areEqual(dateTime.add(new TimeSpan(1, 1, 1, 1, 1)), new DateTime(2000, 1, 2, 2, 2, 2, 2));
        msAssert.areEqual(dateTime.addYears(10), new DateTime(2010, 1, 1, 1, 1, 1, 1));
        msAssert.areEqual(dateTime.addMonths(10), new DateTime(2000, 11, 1, 1, 1, 1, 1));
        msAssert.areEqual(dateTime.addDays(10.0), new DateTime(2000, 1, 11, 1, 1, 1, 1));
        msAssert.areEqual(dateTime.addHours(10.0), new DateTime(2000, 1, 1, 11, 1, 1, 1));
        msAssert.areEqual(dateTime.addMinutes(10.0), new DateTime(2000, 1, 1, 1, 11, 1, 1));
        msAssert.areEqual(dateTime.addSeconds(10.0), new DateTime(2000, 1, 1, 1, 1, 11, 1));
        msAssert.areEqual(dateTime.addMilliseconds(10.0), new DateTime(2000, 1, 1, 1, 1, 1, 11));
        msAssert.areEqual(dateTime.addTicks(10000), new DateTime(2000, 1, 1, 1, 1, 1, 2));
    }

    @Test
    public void testSubstract()
    {
        DateTime dateTime1 = new DateTime(2001, 1, 2, 2, 2, 2, 2);
        DateTime dateTime2 = new DateTime(2001, 1, 1, 1, 1, 1, 1);
        TimeSpan difference = new TimeSpan(1, 1, 1, 1, 1);

        msAssert.areEqual(DateTime.subtract(dateTime1,difference), dateTime2);
        msAssert.areEqual(DateTime.subtract(dateTime1,dateTime2), difference);
    }

    @Test
    public void testToString()
    {
        try
        {
            setInvariantLocaleAndTZ();

            DateTime dateTime = new DateTime(1970, 1, 27, 15, 45, 1, 100);

            msAssert.areEqual("27/01/1970", dateTime.toString("d"));//american Locale hardcoded (month first)
            msAssert.areEqual("Tuesday, 27 January 1970", dateTime.toString("D"));
            msAssert.areEqual("Tuesday, 27 January 1970 3:45 pm", dateTime.toString("f"));
            msAssert.areEqual("Tuesday, 27 January 1970 3:45:01 pm", dateTime.toString("F"));
            msAssert.areEqual("27/01/1970 3:45 pm", dateTime.toString("g"));
            msAssert.areEqual("27/01/1970 3:45:01 pm", dateTime.toString("G"));
            msAssert.areEqual("27 January", dateTime.toString("m"));
            msAssert.areEqual("27 January", dateTime.toString("M"));
            msAssert.areEqual("Tue, 27 Jan 1970 15:45:01 GMT", dateTime.toString("r"));
            msAssert.areEqual("Tue, 27 Jan 1970 15:45:01 GMT", dateTime.toString("R"));
            msAssert.areEqual("1970-01-27T15:45:01", dateTime.toString("s"));
            msAssert.areEqual("3:45 pm", dateTime.toString("t"));
            msAssert.areEqual("3:45:01 pm", dateTime.toString("T"));
            msAssert.areEqual("1970-01-27 15:45:01Z", dateTime.toString("u"));

            //Custom patterns.
            msAssert.areEqual("27 January 1970", dateTime.toString("dd MMMM yyyy"));
            msAssert.areEqual("27 01 70", dateTime.toString("dd MM yy"));
            msAssert.areEqual("Tuesday, January 27 1970", dateTime.toString("dddd, MMMM dd yyyy"));
            msAssert.areEqual("Tuesday, January 27", dateTime.toString("dddd, MMMM dd"));
            msAssert.areEqual("1/70", dateTime.toString("M/yy"));
            msAssert.areEqual("27-01-70", dateTime.toString("dd-MM-yy"));
            //Xml datetime pattern used in NrxUtil.dateTimeToXml()
            msAssert.areEqual("1970-01-27T15:45:01Z", dateTime.toString("yyyy-MM-dd'T'HH:mm:ss'Z'"));

            //ToString() without format
            msAssert.areEqual("27/01/1970 3:45:01 pm", dateTime.toString());
            msAssert.areEqual("27/01/1970", dateTime.toShortDateString());
        }
        finally
        {
            restoreLocaleAndTZ();
        }
    }

    /**
         * This test depends on time zone info and works correctly for "good old" timezones.
         * Timezone that was changed recently can work incorrectly on "non-fresh" jre that
         * doesn't include changed timezone data yet.
         *
         * For instance, russian Locales can fail here because of recent removing of winter time.
         * But they will work fine on very last jre that already includes the info.
         */
    @Test
    public void testToUtcString()
    {
        try
        {
            setInvariantLocaleAndTZ();

            DateTime dateTimeUtc = new DateTime(1970, 1, 27, 15, 45, 1, 100, DateTimeKind.UTC);
            msAssert.areEqual("Tuesday, 27 January 1970 3:45:01 pm", dateTimeUtc.toString("U"));
        }
        finally
        {
            restoreLocaleAndTZ();
        }
    }

    @Test
    public void testArabic()
    {
        SystemPal.saveCulture();
        try
        {
            SystemPal.setCulture("ar-SA");

            DateTime dateUTC = new DateTime(2007, 1, 1, 1, 1, 1, DateTimeKind.UTC);
            msAssert.areEqual("11/12/27", dateUTC.toString("d"));
            DateTime date = new DateTime(2007, 1, 1);
            msAssert.areEqual("11/12/27", date.toString("d"));
            DateTime universalTime = date.toUniversalTime();
            msAssert.areEqual("10/12/27", universalTime.toString("d"));
            DateTime universalTimeFromUtc = dateUTC.toUniversalTime();
            msAssert.areEqual("11/12/27", universalTimeFromUtc.toString("d"));
        }
        finally
        {
            SystemPal.restoreCulture();
        }
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testOverflow()
    {
        DateTime dateTime = new DateTime(DateTime.MaxValue.getTicks() + 1);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testUnderflow()
    {
        DateTime dateTime = new DateTime(DateTime.MinValue.getTicks() - 1);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testWrongYear()
    {
        DateTime dateTime = new DateTime(10000, 12, 31);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testWrongYear2()
    {
        DateTime dateTime = new DateTime(-1, 12, 31);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testWrongMonth()
    {
        DateTime dateTime = new DateTime(2007, 13, 1);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testWrongMonth2()
    {
        DateTime dateTime = new DateTime(2007, -1, 1);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testWrongDay()
    {
        DateTime dateTime = new DateTime(2007, 1, 32);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testWrongDay2()
    {
        DateTime dateTime = new DateTime(2007, 1, -1);
    }

    @Test
    public void testDateCtor()
    {
        DateTime dt = new DateTime(2001, 10, 26, 21, 32, 52, 0, DateTimeKind.UTC);
        msAssert.areEqual(631397287720000000L, dt.getTicks());
    }

    @Test
    public void testToLocal()
    {
        DateTime dt = DateTime.parse("2001-10-26T21:32:52+02:00");
        msAssert.isNotNull(dt);

        //DateTime.ToUniversalTime() is used to eliminate local TimeZones.
        DateTime universal = dt.toUniversalTime();

        msAssert.areEqual(universal.getHour(), 19);
        msAssert.areEqual(universal.getDay(), 26);

        DateTime local = dt.toLocalTime();
        DateTime expectedLocal = universal.toLocalTime();

        msAssert.areEqual(local.getHour(), expectedLocal.getHour());
        msAssert.areEqual(local.getDay(), expectedLocal.getDay());
    }


    @Test
    public void testUtcToLocal()
    {
        DateTime dt = DateTime.parse("2001-10-26T21:32:52");
        msAssert.isNotNull(dt);
        msAssert.areEqual(dt.getHour(), 21);
        msAssert.areEqual(dt.getDay(), 26);

        DateTime local = dt.toLocalTime();
        //DateTime.ToUniversalTime() is used to eliminate local TimeZones.
        DateTime expectedLocal = local.toUniversalTime().toLocalTime();
        msAssert.areEqual(local.getHour(), expectedLocal.getHour());
        msAssert.areEqual(local.getDay(), expectedLocal.getDay());
    }

    @Test
    public void testDateTimeRoundtripKind()
    {
        String value = "2001-10-26T21:32:52+02:00";
        DateTime r = DateTime.parse(value, msDateTimeFormatInfo.getCurrentInfo(), DateTimeStyles.ROUNDTRIP_KIND);

        msAssert.isNotNull(r);
        //DateTime.ToUniversalTime() is used to eliminate local TimeZones.
        DateTime universal = r.toUniversalTime();

        msAssert.areEqual(631397215720000000L, universal.getTicks());
        msAssert.areEqual(26, universal.getDay());
        msAssert.areEqual(19, universal.getHour());
    }

    @Test
    public void testDateTimeAdjustToUniversal()
    {
        String value = "2001-10-26T21:32:52+02:00";
        DateTime r = DateTime.parse(value, msDateTimeFormatInfo.getCurrentInfo(), DateTimeStyles.ADJUST_TO_UNIVERSAL);
        msAssert.isNotNull(r);
        msAssert.areEqual(631397215720000000L, r.getTicks());
        msAssert.areEqual(26, r.getDay());
        msAssert.areEqual(19, r.getHour());
    }

    @Test
    public void testDateTimeToOADateConversion()
    {
        DateTime r = DateTime.parse("2001-10-26T21:32:52+02:00").toUniversalTime();
        // Test conversion to OADate
        double oadate = r.toOADate();
        msAssert.isNotNull(r);
        msAssert.areEqual(631397215720000000L, r.getTicks());
        msAssert.areEqual(37190.81449074074d, oadate);
        // Test conversion from OADate
        r = DateTime.fromOADate(oadate);
        msAssert.areEqual(631397215720000000L, r.getTicks());
        // Test conversion from Java Date
        java.util.Date date = r.toJava();
        String originalDate = date.toString();
        oadate = DateTime.fromJava(date).toOADate();
        msAssert.areEqual(37190.81449074074d, oadate);
        date = DateTime.fromOADate(oadate).toJava();
        msAssert.areEqual(originalDate, date.toString());
    }

    // Share Locale and TimeZone set/restore methods to other TestData* classes.

    @Test (enabled = false)
    public static void setInvariantLocale()
    {
        CurrentThread.setCurrentCulture(gInvariantLocale);
    }

    @Test (enabled = false)
    public static void setInvariantLocaleAndTZ()
    {
        setInvariantLocale();
        // .Net 2.0 does not support change of local TimeZone
        CurrentThread.setTimeZone(gInvariantTZ);
    }

    @Test (enabled = false)
    public static void restoreLocale()
    {
        CurrentThread.setCurrentCulture(gOriginalLocale);
    }

    @Test (enabled = false)
    public static void restoreLocaleAndTZ()
    {
        restoreLocale();
        // .Net 2.0 does not support change of local TimeZone
        CurrentThread.setTimeZone(gOriginalTZ);
    }

    private static msCultureInfo gOriginalLocale = CurrentThread.getCurrentCulture();
    private static TimeZone gOriginalTZ = CurrentThread.getTimeZone();

    // .Net 2.0 does not support change of local TimeZone
    private static TimeZone gInvariantTZ = TimeZone.getTimeZone("Pacific/Auckland");
    private static msCultureInfo gInvariantLocale = new msCultureInfo("en-NZ", false);

}

