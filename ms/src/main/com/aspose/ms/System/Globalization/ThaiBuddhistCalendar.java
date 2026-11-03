// A Java port, made by Aspose, of a .NET Framework class. Microsoft publishes the source of that class
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/globalization/thaibuddhistcalendar.cs
//
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

package com.aspose.ms.System.Globalization;

import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.DayOfWeek;


/*=================================ThaiBuddhistCalendar========================== 
    **
    ** ThaiBuddhistCalendar is based on Gregorian calendar.  Its year value has
    ** an offset to the Gregorain calendar.
    ** 
    **  Calendar support range:
    **      Calendar    Minimum     Maximum 
    **      ==========  ==========  ========== 
    **      Gregorian   0001/01/01   9999/12/31
    **      Thai        0544/01/01  10542/12/31 
    ============================================================================*/


public class ThaiBuddhistCalendar extends Calendar
{

    static EraInfo[] m_EraInfo;
    //
    // The era value for the current era. 
    //

    public static final int THAI_BUDDHIST_ERA = 1;

    //internal static Calendar m_defaultInstance;

    GregorianCalendarHelper helper;


    
    public /*override*/ DateTime getMinSupportedDateTime()
    {
        return (DateTime.MinValue);
    }

    
    public /*override*/ DateTime getMaxSupportedDateTime()
    {
        return (DateTime.MaxValue);
    }

    // Return the type of the Thai Buddhist calendar.
    //

    
    public /*override*/ /*CalendarAlgorithmType*/int getAlgorithmType()
    {
        return CalendarAlgorithmType.SOLAR_CALENDAR;
    }


    public ThaiBuddhistCalendar()
    {
        helper = new GregorianCalendarHelper(this, m_EraInfo);
    }

    public /*override*/ int getID()
    {
        return (CAL_THAI);
    }


    public /*override*/ DateTime addMonths(DateTime time, int months)
    {
        return (helper.addMonths(time, months));
    }


    public /*override*/ DateTime addYears(DateTime time, int years)
    {
        return (helper.addYears(time, years));
    }


    public /*override*/ int getDaysInMonth(int year, int month, int era)
    {
        return (helper.getDaysInMonth(year, month, era));
    }


    public /*override*/ int getDaysInYear(int year, int era)
    {
        return (helper.getDaysInYear(year, era));
    }


    public /*override*/ int getDayOfMonth(DateTime time)
    {
        return (helper.getDayOfMonth(time));
    }


    public /*override*/ /*DayOfWeek*/int getDayOfWeek(DateTime time)
    {
        return (helper.getDayOfWeek(time));
    }


    public /*override*/ int getDayOfYear(DateTime time)
    {
        return (helper.getDayOfYear(time));
    }


    public /*override*/ int getMonthsInYear(int year, int era)
    {
        return (helper.getMonthsInYear(year, era));
    }


    public /*override*/ int getWeekOfYear(DateTime time, /*CalendarWeekRule*/int rule, /*DayOfWeek*/int firstDayOfWeek)
    {
        return (helper.getWeekOfYear(time, rule, firstDayOfWeek));
    }


    public /*override*/ int getEra(DateTime time)
    {
        return (helper.getEra(time));
    }

    public /*override*/ int getMonth(DateTime time)
    {
        return (helper.getMonth(time));
    }


    public /*override*/ int getYear(DateTime time)
    {
        return (helper.getYear(time));
    }


    public /*override*/ boolean isLeapDay(int year, int month, int day, int era)
    {
        return (helper.isLeapDay(year, month, day, era));
    }


    public /*override*/ boolean isLeapYear(int year, int era)
    {
        return (helper.isLeapYear(year, era));
    }

    // Returns  the leap month in a calendar year of the specified era. This method returns 0 
    // if this calendar does not have leap month, or this year is not a leap year.
    // 

    public /*override*/ int getLeapMonth(int year, int era)
    {
        return (helper.getLeapMonth(year, era));
    }


    public /*override*/ boolean isLeapMonth(int year, int month, int era)
    {
        return (helper.isLeapMonth(year, month, era));
    }


    public /*override*/ DateTime toDateTime(int year, int month, int day, int hour, int minute, int second, int millisecond, int era)
    {
        return (helper.toDateTime(year, month, day, hour, minute, second, millisecond, era));
    }


    public /*override*/ int[] getEras()
    {
        return (helper.getEras());
    }

    private static final int DEFAULT_TWO_DIGIT_YEAR_MAX = 2572;


    public /*override*/ int getTwoDigitYearMax()
    {
        if (twoDigitYearMax == -1)
        {
            twoDigitYearMax = getSystemTwoDigitYearSetting(getID(), DEFAULT_TWO_DIGIT_YEAR_MAX);
        }
        return (twoDigitYearMax);
    }

    public /*override*/ void setTwoDigitYearMax(int value)
    {
        verifyWritable();
        if (value < 99 || value > helper.getMaxYear())
        {
            throw new IllegalArgumentException("Parameter name: " + "ArgumentOutOfRange_Range");
        }
        twoDigitYearMax = value;
    }


    public /*override*/ int toFourDigitYear(int year)
    {
        return (helper.toFourDigitYear(year, this.getTwoDigitYearMax()));
    }

    /*=================================GetDefaultInstance========================== 
        **Action: Internal method to provide a default intance of ThaiBuddhistCalendar.  Used by NLS+ implementation
        **       and other calendars. 
        **Returns: 
        **Arguments:
        **Exceptions: 
        ============================================================================*/
    /*
        internal static Calendar GetDefaultInstance() {
            if (m_defaultInstance == null) { 
                m_defaultInstance = new ThaiBuddhistCalendar();
            } 
            return (m_defaultInstance); 
        }
        */

    static/* ThaiBuddhistCalendar()*/
    {
        // Since
        //    Gregorian Year = Era Year + yearOffset 
        // When Gregorian Year 1 is Thai Buddhist year 544, so that
        //    1 = 544 + yearOffset 
        //  So yearOffset = -543 
        // Gregorian Year 2001 is Thai Buddhist Year 2544.
        //m_EraInfo[0] = new EraInfo(1, new DateTime(1, 1, 1).Ticks, -543, 544, GregorianCalendar.MaxYear + 543); 
        m_EraInfo = GregorianCalendarHelper.initEraInfo(CAL_THAI);
    }
}
