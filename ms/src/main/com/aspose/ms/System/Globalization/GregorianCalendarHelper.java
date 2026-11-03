// A Java port, made by Aspose, of a .NET Framework class. Microsoft publishes the source of that class
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/globalization/gregoriancalendarhelper.cs
//
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

package com.aspose.ms.System.Globalization;

import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.TimeSpan;

/*=================================EraInfo==========================
    ** 
    ** This is the structure to store information about an era.
    **
    ============================================================================*/

class EraInfo
{
    int era;       // The value of the era. 
    long ticks;    // The time in ticks when the era starts
    int yearOffset;    // The offset to Gregorian year when the era starts. 
                                // Gregorian Year = Era Year + yearOffset
                                // Era Year = Gregorian Year - yearOffset
    int minEraYear;        // Min year value in this era. Generally, this value is 1, but this may
                                    // be affected by the DateTime.MinValue; 
    int maxEraYear;       // Max year value in this era. (== the year length of the era + 1)

    EraInfo(int era, long ticks, int yearOffset, int minEraYear, int maxEraYear)
    {
        this.era = era;
        this.ticks = ticks;
        this.yearOffset = yearOffset;
        this.minEraYear = minEraYear;
        this.maxEraYear = maxEraYear;
    }
}

// 
// This class implements the Gregorian calendar. In 1582, Pope Gregory XIII made
// minor changes to the solar Julian or "Old Style" calendar to make it more 
// accurate. Thus the calendar became known as the Gregorian or "New Style"
// calendar, and adopted in Catholic ---- such as Spain and France. Later
// the Gregorian calendar became popular throughout Western Europe because it
// was accurate and convenient for international trade. Scandinavian ---- 
// adopted it in 1700, Great Britain in 1752, the ----n colonies in 1752 and
// India in 1757. China adopted the same calendar in 1911, Russia in 1918, and 
// some Eastern European ---- as late as 1940. 
//
// This calendar recognizes two era values: 
// 0 CurrentEra (AD)
// 1 BeforeCurrentEra (BC)
class GregorianCalendarHelper
{

    // 1 tick = 100ns = 10E-7 second
    // Number of ticks per time unit 
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

    static final int DATE_PART_YEAR = 0;
    static final int DATE_PART_DAY_OF_YEAR = 1;
    static final int DATE_PART_MONTH = 2;
    static final int DATE_PART_DAY = 3;

    //
    // This is the max Gregorian year can be represented by DateTime class.  The limitation
    // is derived from DateTime class. 
    //
    int getMaxYear()
    {
        return (m_maxYear);
    }

    static /*final*/ int[] DAYS_TO_MONTH_365 =
    {
        0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365
    };

    static /*final*/ int[] DAYS_TO_MONTH_366 =
    {
        0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366
    };

    int m_maxYear = 9999;
    int m_minYear;
    Calendar m_Cal;
    EraInfo[] m_EraInfo;
    int[] m_eras = null;

    // m_minDate is existing here just to keep the serialization compatibility.
    // it has nothing to do with the code anymore.
    DateTime m_minDate;

    /*=================================InitEraInfo==========================
        **Action: Get the era range information from calendar.nlp. 
        **Returns: An array of EraInfo, which contains the information about an era. 
        **Arguments:
        **      calID     the Calendar ID defined in Calendar.cs 
        ============================================================================*/
    static EraInfo[] initEraInfo(int calID)
    {
        //
        // Initialize era information. 
        //

        /* 
            Japaense era info example:
            eraInfo[0] = new EraInfo(4, new DateTime(1989, 1, 8).Ticks, 1988, 1, GregorianCalendar.MaxYear - 1988);    // Heisei. Most recent 
            eraInfo[1] = new EraInfo(3, new DateTime(1926, 12, 25).Ticks, 1925, 1, 1989 - 1925);  // Showa
            eraInfo[2] = new EraInfo(2, new DateTime(1912, 7, 30).Ticks, 1911, 1, 1926 - 1911);   // Taisho
            eraInfo[3] = new EraInfo(1, new DateTime(1868, 9, 8).Ticks, 1867, 1, 1912 - 1867);    // Meiji
            */

        //JAVA-changed from:

//        BCLDebug.Assert(calID > 0, "[GregorianCalendarHelper.InitEraInfo] Expected calID > 0");
//        int[][] eraRanges = CalendarTable.Default.SERARANGES(calID);
//        EraInfo[] eraInfo = new EraInfo[eraRanges.length];
//        int maxEraYear = GregorianCalendar.MAX_YEAR;
//        for (int i = 0; i < eraRanges.length; i++)
//        {
//            //
//            // The eraRange arrays are each the form of "4 1989 1 8 1988 1".
//            // eraRanges[i][0] is the era value.
//            // eraRanges[i][1] is the year when the era starts.
//            // eraRanges[i][2] is the month when the era starts.
//            // eraRanges[i][3] is the day when the era starts.
//            // eraRanges[i][4] is the offset to Gregorian year (1988).
//            // eraRanges[i][5] is the minimum era year for this era.
//            //
//            //BCLDebug.Assert(eraRanges[i].Length == 6, "[GregorianCalendarHelper.InitEraInfo] Expected 6 SERARANGE elements (0-5), not " + eraRanges[i].Length);
//            eraInfo[i] = new EraInfo(eraRanges[i][0], new DateTime(eraRanges[i][1], eraRanges[i][2], eraRanges[i][3]).getTicks(), eraRanges[i][4], eraRanges[i][5], maxEraYear - eraRanges[i][4]);
//            new EraInfo(er[i][0], new DateTime(e[i][1], e[i][2], e[i][3]).getTicks(), e[i][4], e[i][5], maxEraYear - e[i][4]);
//            maxEraYear = eraRanges[i][1];
//        }
//        return (eraInfo);

        // JAVA: Hardcoded eraInfos taken from .Net runtime.
        // .Net reads CalendarTable from culture.nlp file.
        EraInfo[] eraInfo;
        switch (calID)
        {
            case (Calendar.CAL_JAPAN):
                eraInfo = new EraInfo[4];
                eraInfo[0] = new EraInfo(4, 627358176000000000L, 1988, 1, 8011);// Heisei. Most recent
                eraInfo[1] = new EraInfo(3, 607779936000000000L, 1925, 1, 64);  // Showa
                eraInfo[2] = new EraInfo(2, 603234432000000000L, 1911, 1, 15);  // Taisho
                eraInfo[3] = new EraInfo(1, 589167648000000000L, 1867, 1, 45);  // Meiji
                return eraInfo;
            case (Calendar.CAL_TAIWAN):
                eraInfo = new EraInfo[1];
                eraInfo[0] = new EraInfo(1, 603052128000000000L, 1911, 1, 8088);  // new EraInfo(1, new DateTime(1912, 1, 1).Ticks, 1911, 1, GregorianCalendar.MaxYear - 1911)
                return eraInfo;
            case (Calendar.CAL_KOREA):
                eraInfo = new EraInfo[1];
                eraInfo[0] = new EraInfo(1, 0L, -2333, 2334, 12332);
                return eraInfo;
            case (Calendar.CAL_THAI):
                eraInfo = new EraInfo[1];
                eraInfo[0] = new EraInfo(1, 0L, -543, 544, 10542);
                return eraInfo;
            case (Calendar.CAL_JAPANESELUNISOLAR):
                eraInfo = new EraInfo[2];
                eraInfo[0] = new EraInfo(4, 627358176000000000L, 1988, 1, 8011);
                eraInfo[1] = new EraInfo(3, 607779936000000000L, 1925, 1, 64);
                return eraInfo;
            case (0):
            case (Calendar.CAL_GREGORIAN):
            case (Calendar.CAL_GREGORIAN_US):
            case (Calendar.CAL_HIJRI):
            case (Calendar.CAL_HEBREW):
            case (Calendar.CAL_GREGORIAN_ME_FRENCH):
            case (Calendar.CAL_GREGORIAN_ARABIC):
            case (Calendar.CAL_GREGORIAN_XLIT_ENGLISH):
            case (Calendar.CAL_GREGORIAN_XLIT_FRENCH):
            case (Calendar.CAL_JULIAN):
            case (Calendar.CAL_CHINESELUNISOLAR):
            case (Calendar.CAL_SAKA):
            case (Calendar.CAL_LUNAR_ETO_CHN):
            case (Calendar.CAL_LUNAR_ETO_KOR):
            case (Calendar.CAL_LUNAR_ETO_ROKUYOU):
            case (Calendar.CAL_KOREANLUNISOLAR):
            case (Calendar.CAL_TAIWANLUNISOLAR):
            case (Calendar.CAL_PERSIAN):
            case (Calendar.CAL_UMALQURA):
                eraInfo = new EraInfo[0];
                return eraInfo;
            default:
                throw new IllegalArgumentException("Calendar ID");
        }
    }

    // Construct an instance of gregorian calendar. 
    GregorianCalendarHelper(Calendar cal, EraInfo[] eraInfo)
    {
        m_Cal = cal;
        m_EraInfo = eraInfo;
        // m_minDate is existing here just to keep the serialization compatibility. 
        // it has nothing to do with the code anymore.
        m_minDate = m_Cal.getMinSupportedDateTime();
        m_maxYear = m_EraInfo[0].maxEraYear;
        m_minYear = m_EraInfo[0].minEraYear; ;
    }

    /*=================================GetGregorianYear==========================
        **Action: Get the Gregorian year value for the specified year in an era.
        **Returns: The Gregorian year value. 
        **Arguments:
        **      year    the year value in Japanese calendar 
        **      era     the Japanese emperor era value. 
        **Exceptions:
        **      ArgumentOutOfRangeException if year value is invalid or era value is invalid. 
        ============================================================================*/

    int getGregorianYear(int year, int era)
    {
        if (year < 0)
        {
            throw new IllegalArgumentException("Parameter name: " + "ArgumentOutOfRange_NeedNonNegNum");
        }

        if (era == Calendar.CURRENT_ERA)
        {
            era = m_Cal.getCurrentEraValue();
        }

        for (int i = 0; i < m_EraInfo.length; i++)
        {
            if (era == m_EraInfo[i].era)
            {
                if (year < m_EraInfo[i].minEraYear || year > m_EraInfo[i].maxEraYear)
                {
                    throw new IllegalArgumentException("Parameter name: " + "ArgumentOutOfRange_Range");
                }
                return (m_EraInfo[i].yearOffset + year);
            }
        }
        throw new IllegalArgumentException("Parameter name: " + "ArgumentOutOfRange_InvalidEraValue");
    }

    boolean isValidYear(int year, int era)
    {
        if (year < 0)
        {
            return false;
        }

        if (era == Calendar.CURRENT_ERA)
        {
            era = m_Cal.getCurrentEraValue();
        }

        for (int i = 0; i < m_EraInfo.length; i++)
        {
            if (era == m_EraInfo[i].era)
            {
                if (year < m_EraInfo[i].minEraYear || year > m_EraInfo[i].maxEraYear)
                {
                    return false;
                }
                return true;
            }
        }
        return false;
    }


    // Returns a given date part of this DateTime. This method is used 
    // to compute the year, day-of-year, month, or day part. 
    /*virtual*/ int getDatePart(long ticks, int part)
    {
        checkTicksRange(ticks);
        // n = number of days since 1/1/0001
        int n = (int)(ticks / TICKS_PER_DAY);
        // y400 = number of whole 400-year periods since 1/1/0001 
        int y400 = n / DAYS_PER_400_YEARS;
        // n = day number within 400-year period 
        n -= y400 * DAYS_PER_400_YEARS;
        // y100 = number of whole 100-year periods within 400-year period
        int y100 = n / DAYS_PER_100_YEARS;
        // Last 100-year period has an extra day, so decrement result if 4
        if (y100 == 4) y100 = 3;
        // n = day number within 100-year period
        n -= y100 * DAYS_PER_100_YEARS;
        // y4 = number of whole 4-year periods within 100-year period
        int y4 = n / DAYS_PER_4_YEARS;
        // n = day number within 4-year period 
        n -= y4 * DAYS_PER_4_YEARS;
        // y1 = number of whole years within 4-year period 
        int y1 = n / DAYS_PER_YEAR;
        // Last year has an extra day, so decrement result if 4
        if (y1 == 4) y1 = 3;
        // If year was requested, compute and return it 
        if (part == DATE_PART_YEAR)
        {
            return (y400 * 400 + y100 * 100 + y4 * 4 + y1 + 1);
        }
        // n = day number within year 
        n -= y1 * DAYS_PER_YEAR;
        // If day-of-year was requested, return it
        if (part == DATE_PART_DAY_OF_YEAR)
        {
            return (n + 1);
        }
        // Leap year calculation looks different from IsLeapYear since y1, y4, 
        // and y100 are relative to year 1, not year 0
        boolean leapYear = (y1 == 3 && (y4 != 24 || y100 == 3));
        int[] days = leapYear ? DAYS_TO_MONTH_366 : DAYS_TO_MONTH_365;
        // All months have less than 32 days, so n >> 5 is a good conservative
        // estimate for the month
        int m = n >> 5 + 1;
        // m = 1-based month number
        while (n >= days[m]) m++;
        // If month was requested, return it 
        if (part == DATE_PART_MONTH) return (m);
        // Return 1-based day-of-month 
        return (n - days[m - 1] + 1);
    }

    /*=================================GetAbsoluteDate========================== 
        **Action: Gets the absolute date for the given Gregorian date.  The absolute date means
        **       the number of days from January 1st, 1 A.D. 
        **Returns:  the absolute date 
        **Arguments:
        **      year    the Gregorian year 
        **      month   the Gregorian month
        **      day     the day
        **Exceptions:
        **      ArgumentOutOfRangException  if year, month, day value is valid. 
        **Note:
        **      This is an internal method used by DateToTicks() and the calculations of Hijri and Hebrew calendars. 
        **      Number of Days in Prior Years (both common and leap years) + 
        **      Number of Days in Prior Months of Current Year +
        **      Number of Days in Current Month 
        **
        ============================================================================*/

    static long getAbsoluteDate(int year, int month, int day)
    {
        if (year >= 1 && year <= 9999 && month >= 1 && month <= 12)
        {
            int[] days = ((year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))) ? DAYS_TO_MONTH_366 : DAYS_TO_MONTH_365;
            if (day >= 1 && (day <= days[month] - days[month - 1]))
            {
                int y = year - 1;
                int absoluteDate = y * 365 + y / 4 - y / 100 + y / 400 + days[month - 1] + day - 1;
                return (absoluteDate);
            }
        }
        throw new IllegalArgumentException("Parameter name: " + "ArgumentOutOfRange_BadYearMonthDay");
    }

    // Returns the tick count corresponding to the given year, month, and day.
    // Will check the if the parameters are valid. 
    static long dateToTicks(int year, int month, int day)
    {
        return (getAbsoluteDate(year, month, day) * TICKS_PER_DAY);
    }

    // Return the tick count corresponding to the given hour, minute, second.
    // Will check the if the parameters are valid. 
    static long timeToTicks(int hour, int minute, int second, int millisecond)
    {
        //TimeSpan.TimeToTicks is a family access function which does no error checking, so 
        //we need to put some error checking out here.
        if (hour >= 0 && hour < 24 && minute >= 0 && minute < 60 && second >= 0 && second < 60)
        {
            if (millisecond < 0 || millisecond >= MILLIS_PER_SECOND)
            {
                throw new IllegalArgumentException("Parameter name: " + "ArgumentOutOfRange_Range");
            }
            return (TimeSpan.timeToTicks(hour, minute, second) + millisecond * TICKS_PER_MILLISECOND);
        }
        throw new IllegalArgumentException("Parameter name: " + "ArgumentOutOfRange_BadHourMinuteSecond");
    }


    void checkTicksRange(long ticks)
    {
        if (ticks < m_Cal.getMinSupportedDateTime().getTicks() || ticks > m_Cal.getMaxSupportedDateTime().getTicks())
        {
            throw new IllegalArgumentException("Parameter name: " + "ArgumentOutOfRange_CalendarRange");
        }
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
    public DateTime addMonths(DateTime time, int months)
    {
        checkTicksRange(time.getTicks());
        if (months < -120000 || months > 120000)
        {
            throw new IllegalArgumentException("Parameter name: " + "ArgumentOutOfRange_Range");
        }
        int y = getDatePart(time.getTicks(), DATE_PART_YEAR);
        int m = getDatePart(time.getTicks(), DATE_PART_MONTH);
        int d = getDatePart(time.getTicks(), DATE_PART_DAY);
        int i = m - 1 + months;
        if (i >= 0)
        {
            m = i % 12 + 1;
            y = y + i / 12;
        }
        else
        {
            m = 12 + (i + 1) % 12;
            y = y + (i - 11) / 12;
        }
        int[] daysArray = (y % 4 == 0 && (y % 100 != 0 || y % 400 == 0)) ? DAYS_TO_MONTH_366 : DAYS_TO_MONTH_365;
        int days = (daysArray[m] - daysArray[m - 1]);

        if (d > days)
        {
            d = days;
        }
        long ticks = dateToTicks(y, m, d) + (time.getTicks() % TICKS_PER_DAY);
        Calendar.checkAddResult(ticks, m_Cal.getMinSupportedDateTime(), m_Cal.getMaxSupportedDateTime());
        return (new DateTime(ticks));
    }

    // Returns the DateTime resulting from adding the given number of
    // years to the specified DateTime. The result is computed by incrementing 
    // (or decrementing) the year part of the specified DateTime by value 
    // years. If the month and day of the specified DateTime is 2/29, and if the
    // resulting year is not a leap year, the month and day of the resulting 
    // DateTime becomes 2/28. Otherwise, the month, day, and time-of-day
    // parts of the result are the same as those of the specified DateTime.
    //
    public DateTime addYears(DateTime time, int years)
    {
        return (addMonths(time, years * 12));
    }

    // Returns the day-of-month part of the specified DateTime. The returned 
    // value is an integer between 1 and 31.
    //
    public int getDayOfMonth(DateTime time)
    {
        return (getDatePart(time.getTicks(), DATE_PART_DAY));
    }

    // Returns the day-of-week part of the specified DateTime. The returned value
    // is an integer between 0 and 6, where 0 indicates Sunday, 1 indicates 
    // Monday, 2 indicates Tuesday, 3 indicates Wednesday, 4 indicates
    // Thursday, 5 indicates Friday, and 6 indicates Saturday.
    //
    public /*DayOfWeek*/int getDayOfWeek(DateTime time)
    {
        checkTicksRange(time.getTicks());
        return ((/*DayOfWeek*/int)((time.getTicks() / TICKS_PER_DAY + 1) % 7));
    }

    // Returns the day-of-year part of the specified DateTime. The returned value
    // is an integer between 1 and 366.
    //
    public int getDayOfYear(DateTime time)
    {
        return (getDatePart(time.getTicks(), DATE_PART_DAY_OF_YEAR));
    }

    // Returns the number of days in the month given by the year and 
    // month arguments.
    //
    public int getDaysInMonth(int year, int month, int era)
    {
        // 
        // Convert year/era value to Gregorain year value.
        // 
        year = getGregorianYear(year, era);
        if (month < 1 || month > 12)
        {
            throw new IllegalArgumentException("Parameter name: " + "ArgumentOutOfRange_Month");
        }
        int[] days = ((year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? DAYS_TO_MONTH_366 : DAYS_TO_MONTH_365);
        return (days[month] - days[month - 1]);
    }

    // Returns the number of days in the year given by the year argument for the current era. 
    // 

    public int getDaysInYear(int year, int era)
    {
        //
        // Convert year/era value to Gregorain year value.
        // 
        year = getGregorianYear(year, era);
        return ((year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? 366 : 365);
    }

    // Returns the era for the specified DateTime value. 
    public int getEra(DateTime time)
    {
        long ticks = time.getTicks();
        // The assumption here is that m_EraInfo is listed in reverse order. 
        for (int i = 0; i < m_EraInfo.length; i++)
        {
            if (ticks >= m_EraInfo[i].ticks)
            {
                return (m_EraInfo[i].era);
            }
        }
        throw new IllegalArgumentException("Parameter name: " + "ArgumentOutOfRange_Era");
    }


    public int[] getEras()
    {
        if (m_eras == null)
        {
            m_eras = new int[m_EraInfo.length];
            for (int i = 0; i < m_EraInfo.length; i++)
            {
                m_eras[i] = m_EraInfo[i].era;
            }
        }
        return ((int[])m_eras.clone());
    }

    // Returns the month part of the specified DateTime. The returned value is an
    // integer between 1 and 12. 
    //
    public int getMonth(DateTime time)
    {
        return (getDatePart(time.getTicks(), DATE_PART_MONTH));
    }

    // Returns the number of months in the specified year and era. 
    public int getMonthsInYear(int year, int era)
    {
        year = getGregorianYear(year, era);
        return (12);
    }

    // Returns the year part of the specified DateTime. The returned value is an
    // integer between 1 and 9999. 
    // 
    public int getYear(DateTime time)
    {
        long ticks = time.getTicks();
        int year = getDatePart(ticks, DATE_PART_YEAR);
        for (int i = 0; i < m_EraInfo.length; i++)
        {
            if (ticks >= m_EraInfo[i].ticks)
            {
                return (year - m_EraInfo[i].yearOffset);
            }
        }
        throw new IllegalArgumentException("Argument_NoEra");
    }

    // Returns the year that match the specified Gregorian year. The returned value is an
    // integer between 1 and 9999.
    // 
    public int getYear(int year, DateTime time)
    {
        long ticks = time.getTicks();
        for (int i = 0; i < m_EraInfo.length; i++)
        {
            if (ticks >= m_EraInfo[i].ticks)
            {
                return (year - m_EraInfo[i].yearOffset);
            }
        }
        throw new IllegalArgumentException("Argument_NoEra");
    }

    // Checks whether a given day in the specified era is a leap day. This method returns true if 
    // the date is a leap day, or false if not.
    // 
    public boolean isLeapDay(int year, int month, int day, int era)
    {
        // year/month/era checking is done in GetDaysInMonth()
        if (day < 1 || day > getDaysInMonth(year, month, era))
        {
            throw new IllegalArgumentException("Parameter name: " + "ArgumentOutOfRange_Range");
        }

        if (!isLeapYear(year, era))
        {
            return (false);
        }

        if (month == 2 && day == 29)
        {
            return (true);
        }

        return (false);
    }

    // Returns  the leap month in a calendar year of the specified era. This method returns 0 
    // if this calendar does not have leap month, or this year is not a leap year.
    // 
    public int getLeapMonth(int year, int era)
    {
        year = getGregorianYear(year, era);
        return (0);
    }

    // Checks whether a given month in the specified era is a leap month. This method returns true if 
    // month is a leap month, or false if not.
    // 
    public boolean isLeapMonth(int year, int month, int era)
    {
        year = getGregorianYear(year, era);
        if (month < 1 || month > 12)
        {
            throw new IllegalArgumentException("Parameter name: " + "ArgumentOutOfRange_Range");
        }
        return (false);
    }

    // Checks whether a given year in the specified era is a leap year. This method returns true if 
    // year is a leap year, or false if not.
    // 
    public boolean isLeapYear(int year, int era)
    {
        year = getGregorianYear(year, era);
        return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
    }

    // Returns the date and time converted to a DateTime value.  Throws an exception if the n-tuple is invalid. 
    // 
    public DateTime toDateTime(int year, int month, int day, int hour, int minute, int second, int millisecond, int era)
    {
        year = getGregorianYear(year, era);
        long ticks = dateToTicks(year, month, day) + timeToTicks(hour, minute, second, millisecond);
        checkTicksRange(ticks);
        return (new DateTime(ticks));
    }

    public /*virtual*/ int getWeekOfYear(DateTime time, /*CalendarWeekRule*/int rule, /*DayOfWeek*/int firstDayOfWeek)
    {
        checkTicksRange(time.getTicks());
        // Use GregorianCalendar to get around the problem that the implmentation in Calendar.GetWeekOfYear()
        // can call GetYear() that exceeds the supported range of the Gregorian-based calendars. 
        return (GregorianCalendar.getDefaultInstance().getWeekOfYear(time, rule, firstDayOfWeek));
    }


    public int toFourDigitYear(int year, int twoDigitYearMax)
    {
        if (year < 0)
        {
            throw new IllegalArgumentException("Parameter name: " + "ArgumentOutOfRange_NeedPosNum");
        }

        if (year < 100)
        {
            int y = year % 100;
            return ((twoDigitYearMax / 100 - (y > twoDigitYearMax % 100 ? 1 : 0)) * 100 + y);
        }

        if (year < m_minYear || year > m_maxYear)
        {
            throw new IllegalArgumentException("Parameter name: " + "ArgumentOutOfRange_Range");
        }
        // If the year value is above 100, just return the year value.  Don't have to do
        // the TwoDigitYearMax comparison. 
        return (year);
    }
}
