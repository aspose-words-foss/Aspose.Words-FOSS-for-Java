// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 2012/08/23 by Vyacheslav Durin
// 2016/03/18 by Anatoliy Sidorenko

package com.aspose;

import com.aspose.ms.System.DateTimeKind;
import com.aspose.ms.System.Globalization.DateTimeParse;
import com.aspose.ms.System.Threading.CurrentThread;
import org.testng.annotations.Test;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.ms.System.Globalization.msDateTimeFormatInfo;
import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.Globalization.DateTimeStyles;
import com.aspose.ms.NUnit.Framework.msAssert;

import java.util.TimeZone;

@Test
public class TestDateTimeTryParse
{
    /**
     * WORDSJAVA-1196: Dates being pushed back by one day when converting from Word to PDF.
     * TimeZone offset is calculated wrong. We should use {@code TimeSpan.fromJava(unixTicks)}
     * instead of {@code new TimeSpan(unixTicks)} to get correct instance of {@code TimeSpan}.
     * See {@code DateTimeParse.determineTimeZoneAdjustments()}.
     */
    @Test
    public void testJiraJ1196()
    {
        TimeZone tz = CurrentThread.getTimeZone();
        msCultureInfo culture = CurrentThread.getCurrentCulture();
        try
        {
            CurrentThread.setCurrentCulture("en-NZ");
            CurrentThread.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));

            // Emulate the call FormatterPal.xmlToDateTimeExact("2015-10-21T00:00:00")
            msDateTimeFormatInfo dfti = new msCultureInfo("en-NZ").getDateTimeFormat();
            int style = (int) DateTimeStyles.ADJUST_TO_UNIVERSAL | (int) DateTimeStyles.ASSUME_LOCAL;
            DateTime dt = DateTimeParse.tryParseExact("2015-10-21T00:00:00", "yyyy-MM-ddTHH:mm:ss.FFFFFFFK", dfti, style);

            // UTC time offset is -04:00 hours
            msAssert.areEqual(dt, new DateTime(2015, 10, 21, 4, 0, 0, DateTimeKind.UTC));

            DateTime localDt = dt.toLocalTime();
            msAssert.areEqual(localDt, new DateTime(2015, 10, 21, 0, 0, 0, DateTimeKind.LOCAL));

            msAssert.areEqual(localDt.toString("dddd, MMMM dd, yyyy"), "Wednesday, October 21, 2015");
        }
        finally
        {
            CurrentThread.setTimeZone(tz);
            CurrentThread.setCurrentCulture(culture);
        }
    }
}
