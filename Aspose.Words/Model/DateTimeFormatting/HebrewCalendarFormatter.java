// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/08/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Globalization.HebrewCalendar;
import com.aspose.ms.System.DateTime;
import com.aspose.Debug;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.ms.System.DayOfWeek;
import com.aspose.SystemPal;


class HebrewCalendarFormatter extends CalendarFormatter
{
    public HebrewCalendarFormatter()
    {
    	super(new HebrewCalendar());
	
    }

    protected /*override*/ boolean useInvariantCulture(char token, int tokenLength)
    {
        switch (token)
        {
            case 'd':
            case 'M':
            case 'y':
                return false;
            default:
                return true;
        }
    }

    protected /*override*/ String formatCore(DateTime value, char token, int tokenLength)
    {
        Debug.assertTrue(!useInvariantCulture(token, tokenLength));

        switch (token)
        {
            case 'd':
                return formatDay(value, tokenLength);
            case 'M':
                return formatMonth(value);
            case 'y':
                return formatYear(value);
            default:
                throw new IllegalArgumentException();
        }
    }

    protected /*override*/ msCultureInfo getInvariantCulture() { return G_INVARIANT_CULTURE; }

    private String formatDay(DateTime value, int tokenLength)
    {
        return (tokenLength > 2)
            ? formatDayOfWeek(value)
            : formatDayOfMonth(value);
    }

    private String formatDayOfWeek(DateTime value)
    {
        /*DayOfWeek*/int dayOfWeek = getCalendar().getDayOfWeek(value);
        switch (dayOfWeek)
        {
            case DayOfWeek.SUNDAY: return "Yom Rishon";
            case DayOfWeek.MONDAY: return "Yom Sheni";
            case DayOfWeek.TUESDAY: return "Yom Shlishi";
            case DayOfWeek.WEDNESDAY: return "Yom Revi'i";
            case DayOfWeek.THURSDAY: return "Yom Chamishi";
            case DayOfWeek.FRIDAY: return "Yom Shishi";
            case DayOfWeek.SATURDAY: return "Shabat";
            default:
                throw new IllegalArgumentException();
        }
    }

    private String formatDayOfMonth(DateTime value)
    {
        int dayOfMonth = getCalendar().getDayOfMonth(value);
        return Integer.toString(dayOfMonth);
    }

    private String formatMonth(DateTime value)
    {
        int month = getCalendar().getMonth(value);

        // Adjust month index aware leap year.
        // Based on https://msdn.microsoft.com/en-us/library/system.globalization.hebrewcalendar(v=vs.110).aspx#Anchor_6
        if (getCalendar().isLeapYear(getCalendar().getYear(value)))
        {
            if (month > 5)
                month++;
        }
        else
        {
            if (month > 6)
                month += 2;
        }

        switch (month)
        {
            case 1: return "Tishrei";
            case 2: return "Cheshvan";
            case 3: return "Kislev";
            case 4: return "Tevet";
            case 5: return "Shevat";
            case 6: return "Adar";
            case 7: return "Adar Alef";
            case 8: return "Adar Beit";
            case 9: return "Nissan";
            case 10: return "Iyar";
            case 11: return "Sivan";
            case 12: return "Tamuz";
            case 13: return "Av";
            case 14: return "Elul";
            default:
                throw new IllegalArgumentException();
        }
    }

    private String formatYear(DateTime value)
    {
        int year = getCalendar().getYear(value);
        return Integer.toString(year);
    }

    private static /*final*/ msCultureInfo G_INVARIANT_CULTURE = SystemPal.getCulture("he-IL");
}

