// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/08/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Globalization.HijriCalendar;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.ms.System.DateTime;
import com.aspose.Debug;
import com.aspose.ms.System.DayOfWeek;
import com.aspose.SystemPal;


class HijriCalendarFormatter extends CalendarFormatter
{
    public HijriCalendarFormatter()
    {
    	super(new HijriCalendar());
	
    }

    protected /*override*/ boolean useInvariantCulture(char token, int tokenLength)
    {
        switch (token)
        {
            case 'd':
                return tokenLength <= 2;
            case 'M':
                return tokenLength <= 2;
            default:
                return true;
        }
    }

    protected /*override*/ msCultureInfo getInvariantCulture() { return G_INVARIANT_CULTURE; }

    protected /*override*/ String formatCore(DateTime value, char token, int tokenLength)
    {
        Debug.assertTrue(!useInvariantCulture(token, tokenLength));

        switch (token)
        {
            case 'd':
                Debug.assertTrue(tokenLength > 2);
                return formatDay(value);
            case 'M':
                Debug.assertTrue(tokenLength > 2);
                return formatMonth(value);
            default:
                throw new IllegalArgumentException();
        }
    }

    private String formatDay(DateTime value)
    {
        /*DayOfWeek*/int dayOfWeek = getCalendar().getDayOfWeek(value);
        switch (dayOfWeek)
        {
            case DayOfWeek.SUNDAY: return "AlAhad";
            case DayOfWeek.MONDAY: return "AlEthnien";
            case DayOfWeek.TUESDAY: return "AthThulatha";
            case DayOfWeek.WEDNESDAY: return "AlArbia'a";
            case DayOfWeek.THURSDAY: return "AlKhamis";
            case DayOfWeek.FRIDAY: return "AlJumaa";
            case DayOfWeek.SATURDAY: return "AsSabt";
            default:
                throw new IllegalArgumentException();
        }
    }

    private String formatMonth(DateTime value)
    {
        int month = getCalendar().getMonth(value);
        switch (month)
        {
            case 1: return "Muharram";
            case 2: return "Safar";
            case 3: return "Rabi' Awwal";
            case 4: return "Rabi' Thani";
            case 5: return "Jamada El Oula";
            case 6: return "Jamada El Thaniah";
            case 7: return "Rajab";
            case 8: return "Sha'ban";
            case 9: return "Ramadan";
            case 10: return "Shawwal";
            case 11: return "Thoul Ki'dah";
            case 12: return "Thoul Hijjah";
            default:
                throw new IllegalArgumentException();
        }
    }

    private static /*final*/ msCultureInfo G_INVARIANT_CULTURE = SystemPal.getCulture("ar-SA");
}

