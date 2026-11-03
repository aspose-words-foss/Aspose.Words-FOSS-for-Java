// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/11/2016 by Konstantin Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.Globalization.msDateTimeFormatInfo;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.Globalization.DateTimeStyles;
import com.aspose.words.ref.Ref;


@Test
public class TestDateTimeFormatInfo
{
    @Test
    public void testConstants()
    {
        msDateTimeFormatInfo info = msDateTimeFormatInfo.getInvariantInfo();

        msAssert.areEqual("AM", info.getAMDesignator());
        msAssert.areEqual("PM", info.getPMDesignator());
        msAssert.areEqual("/", info.getDateSeparator());
        msAssert.areEqual("dddd, dd MMMM yyyy HH:mm:ss", info.getFullDateTimePattern());
        msAssert.areEqual("dddd, dd MMMM yyyy", info.getLongDatePattern());
        msAssert.areEqual("MMMM dd", info.getMonthDayPattern());
        msAssert.areEqual("HH:mm:ss", info.getLongTimePattern());
        msAssert.areEqual("ddd, dd MMM yyyy HH':'mm':'ss 'GMT'", info.getRFC1123Pattern());
        msAssert.areEqual("MM/dd/yyyy", info.getShortDatePattern());
        msAssert.areEqual("HH:mm", info.getShortTimePattern());
        msAssert.areEqual("yyyy'-'MM'-'dd'T'HH':'mm':'ss", info.getSortableDateTimePattern());
        msAssert.areEqual("yyyy'-'MM'-'dd HH':'mm':'ss'Z'", info.getUniversalSortableDateTimePattern());
        msAssert.areEqual(":", info.getTimeSeparator());
        msAssert.areEqual("yyyy MMMM", info.getYearMonthPattern());

        // Not implemented in Java:
        //Assert.AreEqual("Gregorian Calendar", info.NativeCalendarName);
    }

    @Test
    public void testArrayConstants()
    {
        msDateTimeFormatInfo info = msDateTimeFormatInfo.getInvariantInfo();

        String[] abbreviatedDayNames = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
        msAssert.areEqual(abbreviatedDayNames, info.getAbbreviatedDayNames());

        String[] abbreviatedMonthNames = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", "" };
        msAssert.areEqual(abbreviatedMonthNames, info.getAbbreviatedMonthNames());

        String[] dayNames = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
        msAssert.areEqual(dayNames, info.getDayNames());

        String[] monthNames =
        {
            "January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December", ""
        };
        msAssert.areEqual(monthNames, info.getMonthNames());

        String[] shortestDayNames =
            {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        msAssert.areEqual(shortestDayNames, info.getShortestDayNames());

        // Java-deleed code:
        // Genitive month Names not implemented in ms DateTimeFormatInfo: Java's DateFormatSymbols doesn't contain 
        // genitive month names because java adds genitive suffixes during formatting.
    }

    @Test
    public void testHebrewConstants()
    {
        msDateTimeFormatInfo info = msDateTimeFormatInfo.getInstance(new msCultureInfo("he-IL"));

        msAssert.areEqual("AM", info.getAMDesignator());
        msAssert.areEqual("PM", info.getPMDesignator());
        msAssert.areEqual("/", info.getDateSeparator());
        msAssert.areEqual("dddd dd MMMM yyyy HH:mm:ss", info.getFullDateTimePattern());
        msAssert.areEqual("dddd dd MMMM yyyy", info.getLongDatePattern());
        msAssert.areEqual("dd MMMM", info.getMonthDayPattern());
        msAssert.areEqual("HH:mm:ss", info.getLongTimePattern());
        msAssert.areEqual("ddd, dd MMM yyyy HH':'mm':'ss 'GMT'", info.getRFC1123Pattern());
        msAssert.areEqual("dd/MM/yyyy", info.getShortDatePattern());
        msAssert.areEqual("HH:mm", info.getShortTimePattern());
        msAssert.areEqual("yyyy'-'MM'-'dd'T'HH':'mm':'ss", info.getSortableDateTimePattern());
        msAssert.areEqual("yyyy'-'MM'-'dd HH':'mm':'ss'Z'", info.getUniversalSortableDateTimePattern());
        msAssert.areEqual(":", info.getTimeSeparator());
        msAssert.areEqual("MMMM yyyy", info.getYearMonthPattern());
    }

    @Test
    public void testHebrewArrayConstants()
    {
        msDateTimeFormatInfo info = msDateTimeFormatInfo.getInstance(new msCultureInfo("he-IL"));

        String[] abbreviatedDayNames = { "יום א", "יום ב", "יום ג", "יום ד", "יום ה", "יום ו", "שבת" };
        msAssert.areEqual(abbreviatedDayNames, info.getAbbreviatedDayNames());

        String[] abbreviatedMonthNames = { "ינו", "פבר", "מרץ", "אפר", "מאי", "יונ", "יול", "אוג", "ספט", "אוק", "נוב", "דצמ", "" };
        msAssert.areEqual(abbreviatedMonthNames, info.getAbbreviatedMonthNames());

        String[] dayNames = { "יום ראשון", "יום שני", "יום שלישי", "יום רביעי", "יום חמישי", "יום שישי", "שבת" };
        msAssert.areEqual(dayNames, info.getDayNames());

        String[] monthNames =
        {
            "ינואר", "פברואר", "מרץ", "אפריל", "מאי", "יוני", "יולי", "אוגוסט",
            "ספטמבר", "אוקטובר", "נובמבר", "דצמבר", ""
        };
        msAssert.areEqual(monthNames, info.getMonthNames());

        String[] shortestDayNames = { "א", "ב", "ג", "ד", "ה", "ו", "ש" };
        msAssert.areEqual(shortestDayNames, info.getShortestDayNames());

        // Java-deleed code: Genitive month Names not applicable to java, see comments above (TestArrayConstants()).
    }

    /// <summary>
    /// WORDSJAVA-1680 Java can't properly parse "dd/MM/YYYY"-formatted Date when set to Canada culture
    /// - it reads "MM/dd/YYYY" instead (in US-style).
    /// </summary>
    @Test
    public void testJiraJ1680()
    {
        String value = "09/06/2015 15:39";
        DateTime result = null;

        // US
        msCultureInfo culture = new msCultureInfo("en-US");
        Ref<DateTime> referenceToResult = new Ref<DateTime>(result);
        DateTime.tryParse(value, culture, DateTimeStyles.ADJUST_TO_UNIVERSAL, /*out*/ referenceToResult);
        result = referenceToResult.get();

        msAssert.areEqual(result.getDay(), 6);
        msAssert.areEqual(result.getMonth(), 9);

        // Canada
        culture = new msCultureInfo("en-CA");
        referenceToResult.set(result);
        DateTime.tryParse(value, culture, DateTimeStyles.ADJUST_TO_UNIVERSAL, /*out*/ referenceToResult);
        result = referenceToResult.get();

        msAssert.areEqual(result.getDay(), 6);
        msAssert.areEqual(result.getMonth(), 9);
    }
}

