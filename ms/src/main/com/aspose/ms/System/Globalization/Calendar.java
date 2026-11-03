// A Java port, made by Aspose, of a .NET Framework class. Microsoft publishes the source of that class
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/globalization/calendar.cs
//
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

package com.aspose.ms.System.Globalization;

import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.TimeSpan;


// This abstract class represents a calendar. A calendar reckons time in
// divisions such as weeks, months and years. The number, length and start of
// the divisions vary in each calendar. 
//
// Any instant in time can be represented as an n-tuple of numeric values using 
// a particular calendar. For example, the next vernal equinox occurs at (0.0, 0 
// , 46, 8, 20, 3, 1999) in the Gregorian calendar. An  implementation of
// Calendar can map any DateTime value to such an n-tuple and vice versa. The 
// DateTimeFormat class can map between such n-tuples and a textual
// representation such as "8:46 AM [....] 20th 1999 AD".
//
// Most calendars identify a year which begins the current era. There may be any 
// number of previous eras. The Calendar class identifies the eras as enumerated
// integers where the current era (CurrentEra) has the value zero. 
// 
// For consistency, the first unit in each interval, e.g. the first month, is
// assigned the value one. 
// The calculation of hour/minute/second is moved to Calendar from GregorianCalendar,
// since most of the calendars (or all?) have the same way of calcuating hour/minute/second.

public abstract class Calendar implements Cloneable
{

    // Number of 100ns (10E-7 second) ticks per time unit 
    static final long TICKS_PER_MILLISECOND = 10000;
    static final long TICKS_PER_SECOND = TICKS_PER_MILLISECOND * 1000;
    static final long TICKS_PER_MINUTE = TICKS_PER_SECOND * 60;
    static final long TICKS_PER_HOUR = TICKS_PER_MINUTE * 60;
    static final long TICKS_PER_DAY = TICKS_PER_HOUR * 24;

    // Number of milliseconds per time unit 
    static final int MILLIS_PER_SECOND = 1000;
    static final int MILLIS_PER_MINUTE = MILLIS_PER_SECOND * 60;
    static final int MILLIS_PER_HOUR = MILLIS_PER_MINUTE * 60;
    static final int MILLIS_PER_DAY = MILLIS_PER_HOUR * 24;

    // Number of days in a non-leap year 
    static final int DAYS_PER_YEAR = 365;
    // Number of days in 4 years 
    static final int DAYS_PER_4_YEARS = DAYS_PER_YEAR * 4 + 1;
    // Number of days in 100 years
    static final int DAYS_PER_100_YEARS = DAYS_PER_4_YEARS * 25 - 1;
    // Number of days in 400 years
    static final int DAYS_PER_400_YEARS = DAYS_PER_100_YEARS * 4 + 1;

    // Number of days from 1/1/0001 to 1/1/10000 
    static final int DAYS_TO_10000 = DAYS_PER_400_YEARS * 25 - 366;

    static final long MAX_MILLIS = (long)DAYS_TO_10000 * MILLIS_PER_DAY;

    // 
    //  Calendar ID Values.  This is used to get data from calendar.nlp.
    //  The order of calendar ID means the order of data items in the table.
    //

    public static final int CAL_GREGORIAN = 1;     // Gregorian (localized) calendar
    public static final int CAL_GREGORIAN_US = 2;     // Gregorian (U.S.) calendar
    public static final int CAL_JAPAN = 3;     // Japanese Emperor Era calendar
    public static final int CAL_TAIWAN = 4;     // Taiwan Era calendar
    public static final int CAL_KOREA = 5;     // Korean Tangun Era calendar
    public static final int CAL_HIJRI = 6;     // Hijri (Arabic Lunar) calendar
    public static final int CAL_THAI = 7;     // Thai calendar
    public static final int CAL_HEBREW = 8;     // Hebrew (Lunar) calendar
    public static final int CAL_GREGORIAN_ME_FRENCH = 9;     // Gregorian Middle East French calendar
    public static final int CAL_GREGORIAN_ARABIC = 10;     // Gregorian Arabic calendar
    public static final int CAL_GREGORIAN_XLIT_ENGLISH = 11;     // Gregorian Transliterated English calendar
    public static final int CAL_GREGORIAN_XLIT_FRENCH = 12;
    public static final int CAL_JULIAN = 13;
    public static final int CAL_JAPANESELUNISOLAR = 14;
    public static final int CAL_CHINESELUNISOLAR = 15;
    public static final int CAL_SAKA = 16;     // reserved to match Office but not implemented in our code
    public static final int CAL_LUNAR_ETO_CHN = 17;     // reserved to match Office but not implemented in our code
    public static final int CAL_LUNAR_ETO_KOR = 18;     // reserved to match Office but not implemented in our code
    public static final int CAL_LUNAR_ETO_ROKUYOU = 19;     // reserved to match Office but not implemented in our code
    public static final int CAL_KOREANLUNISOLAR = 20;
    public static final int CAL_TAIWANLUNISOLAR = 21;
    public static final int CAL_PERSIAN = 22;
    public static final int CAL_UMALQURA = 23;

    int m_currentEraValue = -1;

    private boolean m_isReadOnly = false;

    // The minimum supported DateTime range for the calendar. 

    public /*virtual*/ DateTime getMinSupportedDateTime()
    {
        return (DateTime.MinValue);
    }

    // The maximum supported DateTime range for the calendar. 

    public /*virtual*/ DateTime getMaxSupportedDateTime()
    {
        return (DateTime.MaxValue);
    }




    protected Calendar()
    {
        //Do-nothing constructor. 
    }

    /// 
    // This can not be abstract, otherwise no one can create a subclass of Calendar.
    //
    public /*virtual*/ int getID()
    {
        return (-1);
    }

    /// 
    // Return the Base calendar ID for calendars that didn't have defined data in culture.nlp
    //

    /*virtual*/ int getBaseCalendarID() { return getID(); }

    // Returns  the type of the calendar. 
    //
    
    public /*virtual*/ /*CalendarAlgorithmType*/int getAlgorithmType()
    {
        return CalendarAlgorithmType.UNKNOWN;
    }

    ////////////////////////////////////////////////////////////////////////
    //
    //  IsReadOnly 
    //
    //  Detect if the object is readonly. 
    // 
    ////////////////////////////////////////////////////////////////////////
    
    public boolean isReadOnly() { return (m_isReadOnly); }

    //////////////////////////////////////////////////////////////////////// 
    // 
    //  Clone
    // 
    //  Is the implementation of IColnable.
    //
    ////////////////////////////////////////////////////////////////////////
    public /*virtual*/ Object deepClone()
    {
        Object o = memberwiseClone();
        ((Calendar)o).setReadOnlyState(false);
        return (o);
    }

    ////////////////////////////////////////////////////////////////////////
    // 
    //  ReadOnly
    // 
    //  Create a cloned readonly instance or return the input one if it is 
    //  readonly.
    // 
    ////////////////////////////////////////////////////////////////////////
    public static Calendar readOnly(Calendar calendar)
    {
        if (calendar == null) { throw new NullPointerException("calendar"); }
        if (calendar.isReadOnly()) { return (calendar); }

        Calendar clonedCalendar = (Calendar)(calendar.memberwiseClone());
        clonedCalendar.setReadOnlyState(true);

        return (clonedCalendar);
    }

    void verifyWritable()
    {
        if (m_isReadOnly)
        {
            throw new IllegalStateException("InvalidOperation_ReadOnly");
        }
    }

    void setReadOnlyState(boolean readOnly)
    {
        m_isReadOnly = readOnly;
    }


    /*=================================CurrentEraValue==========================
        **Action: This is used to convert CurretEra(0) to an appropriate era value.
        **Returns:
        **Arguments: 
        **Exceptions:
        **Notes: 
        ** The value is from calendar.nlp. 
        ============================================================================*/

    /*virtual*/ int getCurrentEraValue()
    {
        // The following code assumes that the current era value can not be -1.
        if (m_currentEraValue == -1)
        {
            //JAVA-changed from:
            //BCLDebug.Assert(getBaseCalendarID() > 0, "[Calendar.CurrentEraValue] Expected ID > 0");
            //m_currentEraValue = CalendarTable.Default.ICURRENTERA(getBaseCalendarID());

            // JAVA: Hardcoded era values are taken from .Net runtime.
            // .Net reads CalendarTable from culture.nlp file.
            switch (getBaseCalendarID())
            {
                case 0:
                    m_currentEraValue = 0;
                    break;
                case CAL_JAPAN:
                    m_currentEraValue = 4;
                    break;
                case (Calendar.CAL_GREGORIAN):
                case (Calendar.CAL_GREGORIAN_US):
                case (Calendar.CAL_TAIWAN):
                case (Calendar.CAL_KOREA):
                case (Calendar.CAL_HIJRI):
                case (Calendar.CAL_THAI):
                case (Calendar.CAL_HEBREW):
                case (Calendar.CAL_GREGORIAN_ME_FRENCH):
                case (Calendar.CAL_GREGORIAN_ARABIC):
                case (Calendar.CAL_GREGORIAN_XLIT_ENGLISH):
                case (Calendar.CAL_GREGORIAN_XLIT_FRENCH):
                case (Calendar.CAL_JULIAN):
                case (Calendar.CAL_JAPANESELUNISOLAR):
                case (Calendar.CAL_CHINESELUNISOLAR):
                case (Calendar.CAL_SAKA):
                case (Calendar.CAL_LUNAR_ETO_CHN):
                case (Calendar.CAL_LUNAR_ETO_KOR):
                case (Calendar.CAL_LUNAR_ETO_ROKUYOU):
                case (Calendar.CAL_KOREANLUNISOLAR):
                case (Calendar.CAL_TAIWANLUNISOLAR):
                case (Calendar.CAL_PERSIAN):
                case (Calendar.CAL_UMALQURA):
                    m_currentEraValue = 1;
                    break;
                default:
                    throw new IllegalArgumentException("BaseCalendarID");
            }
        }
        return (m_currentEraValue);
    }

    // The current era for a calendar.

    public static final int CURRENT_ERA = 0;

    int twoDigitYearMax = -1;

    static void checkAddResult(long ticks, DateTime minValue, DateTime maxValue)
    {
        if (ticks < minValue.getTicks() || ticks > maxValue.getTicks())
        {
            throw new IllegalArgumentException("Argument_ResultCalendarRange");
        }
    }

    DateTime add(DateTime time, double value, int scale)
    {
        long millis = (long)(value * scale + (value >= 0 ? 0.5 : -0.5));
        if (millis <= -MAX_MILLIS || millis >= MAX_MILLIS)
        {
            throw new IllegalArgumentException("ArgumentOutOfRange_AddValue");
        }
        long ticks = time.getTicks() + millis * TICKS_PER_MILLISECOND;
        checkAddResult(ticks, getMinSupportedDateTime(), getMaxSupportedDateTime());
        return (new DateTime(ticks));
    }

    // Returns the DateTime resulting from adding the given number of 
    // milliseconds to the specified DateTime. The result is computed by rounding
    // the number of milliseconds given by value to the nearest integer,
    // and adding that interval to the specified DateTime. The value
    // argument is permitted to be negative. 
    //

    public /*virtual*/ DateTime addMilliseconds(DateTime time, double milliseconds)
    {
        return (add(time, milliseconds, 1));
    }


    // Returns the DateTime resulting from adding a fractional number of
    // days to the specified DateTime. The result is computed by rounding the 
    // fractional number of days given by value to the nearest
    // millisecond, and adding that interval to the specified DateTime. The 
    // value argument is permitted to be negative. 
    //

    public /*virtual*/ DateTime addDays(DateTime time, int days)
    {
        return (add(time, days, MILLIS_PER_DAY));
    }

    // Returns the DateTime resulting from adding a fractional number of
    // hours to the specified DateTime. The result is computed by rounding the 
    // fractional number of hours given by value to the nearest 
    // millisecond, and adding that interval to the specified DateTime. The
    // value argument is permitted to be negative. 
    //

    public /*virtual*/ DateTime addHours(DateTime time, int hours)
    {
        return (add(time, hours, MILLIS_PER_HOUR));
    }


    // Returns the DateTime resulting from adding a fractional number of
    // minutes to the specified DateTime. The result is computed by rounding the 
    // fractional number of minutes given by value to the nearest
    // millisecond, and adding that interval to the specified DateTime. The
    // value argument is permitted to be negative.
    // 

    public /*virtual*/ DateTime addMinutes(DateTime time, int minutes)
    {
        return (add(time, minutes, MILLIS_PER_MINUTE));
    }


    // Returns the DateTime resulting from adding the given number of
    // months to the specified DateTime. The result is computed by incrementing
    // (or decrementing) the year and month parts of the specified DateTime by 
    // value months, and, if required, adjusting the day part of the
    // resulting date downwards to the last day of the resulting month in the 
    // resulting year. The time-of-day part of the result is the same as the 
    // time-of-day part of the specified DateTime.
    // 
    // In more precise terms, considering the specified DateTime to be of the
    // form y / m / d + t, where y is the
    // year, m is the month, d is the day, and t is the
    // time-of-day, the result is y1 / m1 / d1 + t, 
    // where y1 and m1 are computed by adding value months
    // to y and m, and d1 is the largest value less than 
    // or equal to d that denotes a valid day in month m1 of year 
    // y1.
    // 

    public abstract DateTime addMonths(DateTime time, int months);

    // Returns the DateTime resulting from adding a number of 
    // seconds to the specified DateTime. The result is computed by rounding the
    // fractional number of seconds given by value to the nearest 
    // millisecond, and adding that interval to the specified DateTime. The 
    // value argument is permitted to be negative.
    // 

    public /*virtual*/ DateTime addSeconds(DateTime time, int seconds)
    {
        return add(time, seconds, MILLIS_PER_SECOND);
    }

    // Returns the DateTime resulting from adding a number of 
    // weeks to the specified DateTime. The 
    // value argument is permitted to be negative.
    // 

    public /*virtual*/ DateTime addWeeks(DateTime time, int weeks)
    {
        return (addDays(time, weeks * 7));
    }


    // Returns the DateTime resulting from adding the given number of 
    // years to the specified DateTime. The result is computed by incrementing
    // (or decrementing) the year part of the specified DateTime by value 
    // years. If the month and day of the specified DateTime is 2/29, and if the
    // resulting year is not a leap year, the month and day of the resulting
    // DateTime becomes 2/28. Otherwise, the month, day, and time-of-day
    // parts of the result are the same as those of the specified DateTime. 
    //

    public abstract DateTime addYears(DateTime time, int years);

    // Returns the day-of-month part of the specified DateTime. The returned 
    // value is an integer between 1 and 31.
    //

    public abstract int getDayOfMonth(DateTime time);

    // Returns the day-of-week part of the specified DateTime. The returned value 
    // is an integer between 0 and 6, where 0 indicates Sunday, 1 indicates 
    // Monday, 2 indicates Tuesday, 3 indicates Wednesday, 4 indicates
    // Thursday, 5 indicates Friday, and 6 indicates Saturday. 
    //

    public abstract /*DayOfWeek*/int getDayOfWeek(DateTime time);

    // Returns the day-of-year part of the specified DateTime. The returned value
    // is an integer between 1 and 366. 
    // 

    public abstract int getDayOfYear(DateTime time);

    // Returns the number of days in the month given by the year and
    // month arguments.
    // 

    public /*virtual*/ int getDaysInMonth(int year, int month)
    {
        return (getDaysInMonth(year, month, CURRENT_ERA));
    }

    // Returns the number of days in the month given by the year and
    // month arguments for the specified era.
    // 

    public abstract int getDaysInMonth(int year, int month, int era);

    // Returns the number of days in the year given by the year argument for the current era.
    // 

    public /*virtual*/ int getDaysInYear(int year)
    {
        return (getDaysInYear(year, CURRENT_ERA));
    }

    // Returns the number of days in the year given by the year argument for the current era. 
    //

    public abstract int getDaysInYear(int year, int era);

    // Returns the era for the specified DateTime value.

    public abstract int getEra(DateTime time);

    /*=================================Eras========================== 
        **Action: Get the list of era values.
        **Returns: The int array of the era names supported in this calendar. 
        **      null if era is not used.
        **Arguments: None.
        **Exceptions: None.
        ============================================================================*/


    public abstract int[] getEras();


    // Returns the hour part of the specified DateTime. The returned value is an
    // integer between 0 and 23. 
    //

    public /*virtual*/ int getHour(DateTime time)
    {
        return ((int)((time.getTicks() / TICKS_PER_HOUR) % 24));
    }

    // Returns the millisecond part of the specified DateTime. The returned value
    // is an integer between 0 and 999.
    // 

    public /*virtual*/ double getMilliseconds(DateTime time)
    {
        return (double)((time.getTicks() / TICKS_PER_MILLISECOND) % 1000);
    }

    // Returns the minute part of the specified DateTime. The returned value is
    // an integer between 0 and 59.
    //

    public /*virtual*/ int getMinute(DateTime time)
    {
        return ((int)((time.getTicks() / TICKS_PER_MINUTE) % 60));
    }

    // Returns the month part of the specified DateTime. The returned value is an 
    // integer between 1 and 12.
    //

    public abstract int getMonth(DateTime time);

    // Returns the number of months in the specified year in the current era. 

    public /*virtual*/ int getMonthsInYear(int year)
    {
        return (getMonthsInYear(year, CURRENT_ERA));
    }

    // Returns the number of months in the specified year and era. 

    public abstract int getMonthsInYear(int year, int era);

    // Returns the second part of the specified DateTime. The returned value is
    // an integer between 0 and 59. 
    //

    public /*virtual*/ int getSecond(DateTime time)
    {
        return ((int)((time.getTicks() / TICKS_PER_SECOND) % 60));
    }

    /*=================================GetFirstDayWeekOfYear========================== 
        **Action: Get the week of year using the FirstDay rule.
        **Returns:  the week of year. 
        **Arguments:
        **  time
        **  firstDayOfWeek  the first day of week (0=Sunday, 1=Monday, ... 6=Saturday)
        **Notes: 
        **  The CalendarWeekRule.FirstDay rule: Week 1 begins on the first day of the year.
        **  Assume f is the specifed firstDayOfWeek, 
        **  and n is the day of week for January 1 of the specified year. 
        **  Assign offset = n - f;
        **  Case 1: offset = 0 
        **      E.g.
        **                     f=1
        **          weekday 0  1  2  3  4  5  6  0  1
        **          date       1/1 
        **          week#      1                    2
        **      then week of year = (GetDayOfYear(time) - 1) / 7 + 1 
        ** 
        **  Case 2: offset < 0
        **      e.g. 
        **                     n=1   f=3
        **          weekday 0  1  2  3  4  5  6  0
        **          date       1/1
        **          week#      1     2 
        **      This means that the first week actually starts 5 days before 1/1.
        **      So week of year = (GetDayOfYear(time) + (7 + offset) - 1) / 7 + 1 
        **  Case 3: offset > 0 
        **      e.g.
        **                  f=0   n=2 
        **          weekday 0  1  2  3  4  5  6  0  1  2
        **          date          1/1
        **          week#         1                    2
        **      This means that the first week actually starts 2 days before 1/1. 
        **      So Week of year = (GetDayOfYear(time) + offset - 1) / 7 + 1
        ============================================================================*/

    int getFirstDayWeekOfYear(DateTime time, int firstDayOfWeek)
    {
        int dayOfYear = getDayOfYear(time) - 1;   // Make the day of year to be 0-based, so that 1/1 is day 0. 
        // Calculate the day of week for the first day of the year.
        // dayOfWeek - (dayOfYear % 7) is the day of week for the first day of this year.  Note that
        // this value can be less than 0.  It's fine since we are making it positive again in calculating offset.
        int dayForJan1 = (int)getDayOfWeek(time) - (dayOfYear % 7);
        int offset = (dayForJan1 - firstDayOfWeek + 14) % 7;
        //BCLDebug.Assert(offset >= 0, "Calendar.GetFirstDayWeekOfYear(): offset >= 0");
        return ((dayOfYear + offset) / 7 + 1);
    }

    int getWeekOfYearFullDays(DateTime time, /*CalendarWeekRule*/int rule, int firstDayOfWeek, int fullDays)
    {
        int dayForJan1;
        int offset;
        int day;

        int dayOfYear = getDayOfYear(time) - 1; // Make the day of year to be 0-based, so that 1/1 is day 0. 
        // 
        // Calculate the number of days between the first day of year (1/1) and the first day of the week.
        // This value will be a positive value from 0 ~ 6.  We call this value as "offset". 
        //
        // If offset is 0, it means that the 1/1 is the start of the first week.
        //     Assume the first day of the week is Monday, it will look like this:
        //     Sun      Mon     Tue     Wed     Thu     Fri     Sat 
        //     12/31    1/1     1/2     1/3     1/4     1/5     1/6
        //              +--> First week starts here. 
        // 
        // If offset is 1, it means that the first day of the week is 1 day ahead of 1/1.
        //     Assume the first day of the week is Monday, it will look like this: 
        //     Sun      Mon     Tue     Wed     Thu     Fri     Sat
        //     1/1      1/2     1/3     1/4     1/5     1/6     1/7
        //              +--> First week starts here.
        // 
        // If offset is 2, it means that the first day of the week is 2 days ahead of 1/1.
        //     Assume the first day of the week is Monday, it will look like this: 
        //     Sat      Sun     Mon     Tue     Wed     Thu     Fri     Sat 
        //     1/1      1/2     1/3     1/4     1/5     1/6     1/7     1/8
        //                      +--> First week starts here. 



        // Day of week is 0-based. 
        // Get the day of week for 1/1.  This can be derived from the day of week of the target day.
        // Note that we can get a negative value.  It's ok since we are going to make it a positive value when calculating the offset. 
        dayForJan1 = (int)getDayOfWeek(time) - (dayOfYear % 7);

        // Now, calucalte the offset.  Substract the first day of week from the dayForJan1.  And make it a positive value. 
        offset = (firstDayOfWeek - dayForJan1 + 14) % 7;
        if (offset != 0 && offset >= fullDays)
        {
            // 
            // If the offset is greater than the value of fullDays, it means that
            // the first week of the year starts on the week where Jan/1 falls on. 
            // 
            offset -= 7;
        }
        //
        // Calculate the day of year for specified time by taking offset into account.
        //
        day = dayOfYear - offset;
        if (day >= 0)
        {
            // 
            // If the day of year value is greater than zero, get the week of year. 
            //
            return (day / 7 + 1);
        }
        //
        // Otherwise, the specified time falls on the week of previous year.
        // Call this method again by passing the last day of previous year. 
        //
        return (getWeekOfYearFullDays(time.addDays(-(dayOfYear + 1)), rule, firstDayOfWeek, fullDays));
    }

    // Returns the week of year for the specified DateTime. The returned value is an 
    // integer between 1 and 53.
    //

    public /*virtual*/ int getWeekOfYear(DateTime time, /*CalendarWeekRule*/int rule, /*DayOfWeek*/int firstDayOfWeek)
    {
        if ((int)firstDayOfWeek < 0 || (int)firstDayOfWeek > 6)
        {
            throw new IllegalArgumentException("ArgumentOutOfRange_Range");
        }
        switch (rule)
        {
            case CalendarWeekRule.FIRST_DAY:
                return (getFirstDayWeekOfYear(time, (int)firstDayOfWeek));
            case CalendarWeekRule.FIRST_FULL_WEEK:
                return (getWeekOfYearFullDays(time, rule, (int)firstDayOfWeek, 7));
            case CalendarWeekRule.FIRST_FOUR_DAY_WEEK:
                return (getWeekOfYearFullDays(time, rule, (int)firstDayOfWeek, 4));
        }
        throw new IllegalArgumentException("ArgumentOutOfRange_Range");

    }

    // Returns the year part of the specified DateTime. The returned value is an 
    // integer between 1 and 9999.
    // 

    public abstract int getYear(DateTime time);

    // Checks whether a given day in the current era is a leap day. This method returns true if 
    // the date is a leap day, or false if not.
    // 

    public /*virtual*/ boolean isLeapDay(int year, int month, int day)
    {
        return (isLeapDay(year, month, day, CURRENT_ERA));
    }

    // Checks whether a given day in the specified era is a leap day. This method returns true if 
    // the date is a leap day, or false if not.
    // 

    public abstract boolean isLeapDay(int year, int month, int day, int era);

    // Checks whether a given month in the current era is a leap month. This method returns true if
    // month is a leap month, or false if not.
    //

    public /*virtual*/ boolean isLeapMonth(int year, int month)
    {
        return (isLeapMonth(year, month, CURRENT_ERA));
    }

    // Checks whether a given month in the specified era is a leap month. This method returns true if 
    // month is a leap month, or false if not.
    //

    public abstract boolean isLeapMonth(int year, int month, int era);

    // Returns  the leap month in a calendar year of the current era. This method returns 0 
    // if this calendar does not have leap month, or this year is not a leap year. 
    //

    public /*virtual*/ int getLeapMonth(int year)
    {
        return (getLeapMonth(year, CURRENT_ERA));
    }

    // Returns  the leap month in a calendar year of the specified era. This method returns 0 
    // if this calendar does not have leap month, or this year is not a leap year.
    // 

    public /*virtual*/ int getLeapMonth(int year, int era)
    {
        if (!isLeapYear(year, era))
            return 0;

        int monthsCount = getMonthsInYear(year, era);
        for (int month = 1; month <= monthsCount; month++)
        {
            if (isLeapMonth(year, month, era))
                return month;
        }

        return 0;
    }

    // Checks whether a given year in the current era is a leap year. This method returns true if 
    // year is a leap year, or false if not.
    //

    public /*virtual*/ boolean isLeapYear(int year)
    {
        return (isLeapYear(year, CURRENT_ERA));
    }

    // Checks whether a given year in the specified era is a leap year. This method returns true if 
    // year is a leap year, or false if not.
    //

    public abstract boolean isLeapYear(int year, int era);

    // Returns the date and time converted to a DateTime value.  Throws an exception if the n-tuple is invalid. 
    // 

    public /*virtual*/ DateTime toDateTime(int year, int month, int day, int hour, int minute, int second, int millisecond)
    {
        return (toDateTime(year, month, day, hour, minute, second, millisecond, CURRENT_ERA));
    }

    // Returns the date and time converted to a DateTime value.  Throws an exception if the n-tuple is invalid.
    // 

    public abstract DateTime toDateTime(int year, int month, int day, int hour, int minute, int second, int millisecond, int era);

    //JAVA-changed to remove out value.
    /*virtual*/ DateTime tryToDateTime(int year, int month, int day, int hour, int minute, int second, int millisecond, int era, /*out*/ DateTime result)
    {
        try
        {
            result = toDateTime(year, month, day, hour, minute, second, millisecond, era);
            return result;
        }
        catch (Exception e)
        {
            result = DateTime.MinValue;
            return result;
        }
    }

    /*virtual*/ boolean isValidYear(int year, int era)
    {
        return (year >= getYear(getMinSupportedDateTime()) && year <= getYear(getMaxSupportedDateTime()));
    }

    /*virtual*/ boolean isValidMonth(int year, int month, int era)
    {
        return (isValidYear(year, era) && month >= 1 && month <= getMonthsInYear(year, era));
    }

    /*virtual*/ boolean isValidDay(int year, int month, int day, int era)
    {
        return (isValidMonth(year, month, era) && day >= 1 && day <= getDaysInMonth(year, month, era));
    }



//    @MethodImpl (MethodImplOptions.InternalCall)
//    static extern int nativeGetTwoDigitYearMax(int calID);

    // Returns and assigns the maximum value to represent a two digit year.  This
    // value is the upper boundary of a 100 year range that allows a two digit year 
    // to be properly translated to a four digit year.  For example, if 2029 is the
    // upper boundary, then a two digit value of 30 should be interpreted as 1930 
    // while a two digit value of 29 should be interpreted as 2029.  In this example 
    // , the 100 year range would be from 1930-2029.  See ToFourDigitYear().

    public /*virtual*/ int getTwoDigitYearMax()
    {
        return (twoDigitYearMax);
    }

    public /*virtual*/ void setTwoDigitYearMax(int value)
    {
        verifyWritable();
        twoDigitYearMax = value;
    }

    // Converts the year value to the appropriate century by using the 
    // TwoDigitYearMax property.  For example, if the TwoDigitYearMax value is 2029, 
    // then a two digit value of 30 will get converted to 1930 while a two digit
    // value of 29 will get converted to 2029. 

    public /*virtual*/ int toFourDigitYear(int year)
    {
        if (year < 0)
        {
            throw new IllegalArgumentException("ArgumentOutOfRange_NeedNonNegNum");
        }
        if (year < 100)
        {
            return ((getTwoDigitYearMax() / 100 - (year > getTwoDigitYearMax() % 100 ? 1 : 0)) * 100 + year);
        }
        // If the year value is above 100, just return the year value.  Don't have to do
        // the TwoDigitYearMax comparison.
        return (year);
    }

    // Return the tick count corresponding to the given hour, minute, second. 
    // Will check the if the parameters are valid. 
    static long timeToTicks(int hour, int minute, int second, int millisecond)
    {
        if (hour >= 0 && hour < 24 && minute >= 0 && minute < 60 && second >= 0 && second < 60)
        {
            if (millisecond < 0 || millisecond >= MILLIS_PER_SECOND)
            {
                throw new IllegalArgumentException("ArgumentOutOfRange_Range");
            }
            return TimeSpan.timeToTicks(hour, minute, second) + millisecond * TICKS_PER_MILLISECOND;
        }
        throw new IllegalArgumentException("ArgumentOutOfRange_BadHourMinuteSecond");
    }

    private static final String TWO_DIGIT_YEAR_MAX_SUB_KEY = "Control Panel\\International\\Calendars\\TwoDigitYearMax";

    // JAVA-changed: in .Net launches native method, reads registry,
    // - just return default value for java.
    static int getSystemTwoDigitYearSetting(int CalID, int defaultYearValue)
    {
        return defaultYearValue;
    }

    //@ResourceExposure (ResourceScope.None)
    //@ResourceConsumption (ResourceScope.Machine, ResourceScope.Machine)
//    static int getSystemTwoDigitYearSetting(int CalID, int defaultYearValue)
//    {
//
//        //
//        // Call Win32 ::GetCalendarInfo() to retrieve CAL_ITWODIGITYEARMAX value.
//        // This function only exists after Windows 98 and Windows 2000.
//        //
//
//        int twoDigitYearMax = nativeGetTwoDigitYearMax(CalID);
//        if (twoDigitYearMax < 0)
//        {
//            //
//            // The Win32 call fails, use the registry setting instead.
//            //
//            RegistryKey key = null;
//
//            try
//            {
//                key = Registry.CurrentUser.InternalOpenSubKey(TWO_DIGIT_YEAR_MAX_SUB_KEY, false);
//            }
//            // If we can't read the key, we'll just keep going anyway.
//            catch (IllegalStateException e)
//            {
//            }
//            catch (IllegalArgumentException e)
//            {
//            }
//
//            if (key != null)
//            {
//                try
//                {
//                    Object value = key.InternalGetValue(CalID.ToString(CultureInfo.getInvariantCulture()), null, false, false);
//                    if (value != null)
//                    {
//                        try
//                        {
//                            twoDigitYearMax = int.Parse(value.toString(), CultureInfo.getInvariantCulture());
//                        }
//                        //
//                        // If error happens in parsing the string. Leave as it is. We will
//                        // set twoDigitYearMax to the default value later.
//                        //
//                        catch (IllegalArgumentException e)
//                        {
//                        }
//                        catch (NumberFormatException e)
//                        {
//                        }
//                        catch (OverflowException e)
//                        {
//                        }
//                    }
//                }
//                finally
//                {
//                    key.Close();
//                }
//            }
//
//            if (twoDigitYearMax < 0)
//            {
//                twoDigitYearMax = defaultYearValue;
//            }
//        }
//        return (twoDigitYearMax);
//    }

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }

}
