// A Java port, made by Aspose, of a .NET Framework class. Microsoft publishes the source of that class
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/globalization/hijricalendar.cs
//
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

package com.aspose.ms.System.Globalization;

import com.aspose.ms.System.DateTime;


////////////////////////////////////////////////////////////////////////////
// 
//  Rules for the Hijri calendar: 
//    - The Hijri calendar is a strictly Lunar calendar.
//    - Days begin at sunset. 
//    - Islamic Year 1 (Muharram 1, 1 A.H.) is equivalent to absolute date
//        227015 (Friday, July 16, 622 C.E. - Julian).
//    - Leap Years occur in the 2, 5, 7, 10, 13, 16, 18, 21, 24, 26, & 29th
//        years of a 30-year cycle.  Year = leap iff ((11y+14) mod 30 < 11). 
//    - There are 12 months which contain alternately 30 and 29 days.
//    - The 12th month, Dhu al-Hijjah, contains 30 days instead of 29 days 
//        in a leap year. 
//    - Common years have 354 days.  Leap years have 355 days.
//    - There are 10,631 days in a 30-year cycle. 
//    - The Islamic months are:
//        1.  Muharram   (30 days)     7.  Rajab          (30 days)
//        2.  Safar      (29 days)     8.  Sha'ban        (29 days)
//        3.  Rabi I     (30 days)     9.  Ramadan        (30 days) 
//        4.  Rabi II    (29 days)     10. Shawwal        (29 days)
//        5.  Jumada I   (30 days)     11. Dhu al-Qada    (30 days) 
//        6.  Jumada II  (29 days)     12. Dhu al-Hijjah  (29 days) {30} 
//
//  NOTENOTE 
//      The calculation of the HijriCalendar is based on the absolute date.  And the
//      absolute date means the number of days from January 1st, 1 A.D.
//      Therefore, we do not support the days before the January 1st, 1 A.D.
// 
////////////////////////////////////////////////////////////////////////////
/* 
    **  Calendar support range: 
    **      Calendar    Minimum     Maximum
    **      ==========  ==========  ========== 
    **      Gregorian   0622/07/18   9999/12/31
    **      Hijri       0001/01/01   9666/04/03
    */

public class HijriCalendar extends Calendar
{


    public static final int HIJRI_ERA = 1;

    static final int DATE_PART_YEAR = 0;
    static final int DATE_PART_DAY_OF_YEAR = 1;
    static final int DATE_PART_MONTH = 2;
    static final int DATE_PART_DAY = 3;

    static final int MIN_ADVANCED_HIJRI = -2;
    static final int MAX_ADVANCED_HIJRI = 2;

    static /*final*/ int[] HIJRI_MONTH_DAYS = { 0, 30, 59, 89, 118, 148, 177, 207, 236, 266, 295, 325, 355 };

    //internal static Calendar m_defaultInstance; 

    private static String m_InternationalRegKey = "Control Panel\\International";
    private static String m_HijriAdvanceRegKeyEntry = "AddHijriDate";

    private int m_HijriAdvance = Integer.MIN_VALUE;

    // DateTime.MaxValue = Hijri calendar (year:9666, month: 4, day: 3). 
    static final int MAX_CALENDAR_YEAR = 9666;
    static final int MAX_CALENDAR_MONTH = 4;
    static final int MAX_CALENDAR_DAY = 3;
    // Hijri calendar (year: 1, month: 1, day:1 ) = Gregorian (year: 622, month: 7, day: 18)
    // This is the minimal Gregorian date that we support in the HijriCalendar. 
    static /*final*/ DateTime CALENDAR_MIN_VALUE = new DateTime(622, 7, 18);
    static /*final*/ DateTime CALENDAR_MAX_VALUE = DateTime.MaxValue;


    
    public /*override*/ DateTime getMinSupportedDateTime()
    {
        return (CALENDAR_MIN_VALUE);
    }


    
    public /*override*/ DateTime getMaxSupportedDateTime()
    {
        return (CALENDAR_MAX_VALUE);
    }

    // Return the type of the Hijri calendar. 
    // 

    
    public /*override*/ /*CalendarAlgorithmType*/int getAlgorithmType()
    {
        return CalendarAlgorithmType.LUNAR_CALENDAR;
    }

    /*=================================GetDefaultInstance========================== 
        **Action: Internal method to provide a default intance of HijriCalendar.  Used by NLS+ implementation
        **       and other calendars.
        **Returns:
        **Arguments: 
        **Exceptions:
        ============================================================================*/
    /* 
        internal static Calendar GetDefaultInstance() {
            if (m_defaultInstance == null) { 
                m_defaultInstance = new HijriCalendar();
            }
            return (m_defaultInstance);
        } 
        */

    // Construct an instance of Hijri calendar. 

    public HijriCalendar()
    {
    }

    public /*override*/ int getID()
    {
        return (CAL_HIJRI);
    }

    /*=================================GetAbsoluteDateHijri========================== 
        **Action: Gets the Absolute date for the given Hijri date.  The absolute date means
        **       the number of days from January 1st, 1 A.D.
        **Returns:
        **Arguments: 
        **Exceptions:
        ============================================================================*/

    private long getAbsoluteDateHijri(int y, int m, int d)
    {
        return (long)(daysUpToHijriYear(y) + HIJRI_MONTH_DAYS[m - 1] + d - 1 - getHijriAdjustment());
    }

    /*=================================DaysUpToHijriYear==========================
        **Action: Gets the total number of days (absolute date) up to the given Hijri Year. 
        **       The absolute date means the number of days from January 1st, 1 A.D.
        **Returns: Gets the total number of days (absolute date) up to the given Hijri Year. 
        **Arguments: HijriYear year value in Hijri calendar. 
        **Exceptions: None
        **Notes: 
        ============================================================================*/

    private long daysUpToHijriYear(int HijriYear)
    {
        long NumDays;           // number of absolute days 
        int NumYear30;         // number of years up to current 30 year cycle
        int NumYearsLeft;      // number of years into 30 year cycle 

        //
        //  Compute the number of years up to the current 30 year cycle. 
        //
        NumYear30 = ((HijriYear - 1) / 30) * 30;

        // 
        //  Compute the number of years left.  This is the number of years
        //  into the 30 year cycle for the given year. 
        // 
        NumYearsLeft = HijriYear - NumYear30 - 1;

        //
        //  Compute the number of absolute days up to the given year.
        //
        NumDays = ((NumYear30 * 10631L) / 30L) + 227013L;
        while (NumYearsLeft > 0)
        {
            // Common year is 354 days, and leap year is 355 days. 
            NumDays += 354 + (isLeapYear(NumYearsLeft, CURRENT_ERA) ? 1 : 0);
            NumYearsLeft--;
        }

        //
        //  Return the number of absolute days.
        // 
        return (NumDays);
    }


    public int getHijriAdjustment()
    {
        if (m_HijriAdvance == Integer.MIN_VALUE)
        {
            // Never been set before.  Use the system value from registry.
            m_HijriAdvance = getAdvanceHijriDate();
        }
        return (m_HijriAdvance);
    }

    public void setHijriAdjustment(int value)
    {
        verifyWritable();

        // NOTE: Check the value of Min/MaxAdavncedHijri with Arabic speakers to see if the assumption is good.
        if (value < MIN_ADVANCED_HIJRI || value > MAX_ADVANCED_HIJRI)
        {
            throw new IllegalArgumentException("ArgumentOutOfRange_Bounds_Lower_Upper");
        }
        m_HijriAdvance = value;
    }

    //JAVA-changed to default value:

    /*=================================GetAdvanceHijriDate==========================
        **Action: Gets the AddHijriDate value from the registry. 
        **Returns:
        **Arguments:    None.
        **Exceptions:
        **Note: 
        **  The HijriCalendar has a user-overidable calculation.  That is, use can set a value from the control
        **  panel, so that the calculation of the Hijri Calendar can move ahead or backwards from -2 to +2 days. 
        ** 
        **  The valid string values in the registry are:
        **      "AddHijriDate-2"  =>  Add -2 days to the current calculated Hijri date. 
        **      "AddHijriDate"    =>  Add -1 day to the current calculated Hijri date.
        **      ""              =>  Add 0 day to the current calculated Hijri date.
        **      "AddHijriDate+1"  =>  Add +1 days to the current calculated Hijri date.
        **      "AddHijriDate+2"  =>  Add +2 days to the current calculated Hijri date. 
        ============================================================================*/
    //@ResourceExposure (ResourceScope.None)
    //@ResourceConsumption (ResourceScope.Machine, ResourceScope.Machine)
    private int getAdvanceHijriDate()
    {

        int hijriAdvance = 0;
//        RegistryKey key = null;
//
//        try
//        {
//            // Open in read-only mode.
//            // Use InternalOpenSubKey so that we avoid the security check.
//            key = Registry.CurrentUser.InternalOpenSubKey(m_InternationalRegKey, false);
//        }
//        //If this fails for any reason, we'll just return 0.
//        catch (IllegalStateException e) { return 0; }
//        catch (IllegalArgumentException e) { return 0; }
//
//        if (key != null)
//        {
//            try
//            {
//                Object value = key.InternalGetValue(m_HijriAdvanceRegKeyEntry, null, false, false);
//                if (value == null)
//                {
//                    return (0);
//                }
//                String str = value.toString();
//                if (String.Compare(str, 0, m_HijriAdvanceRegKeyEntry, 0, m_HijriAdvanceRegKeyEntry.length(), StringComparison.OrdinalIgnoreCase) == 0)
//                {
//                    if (str.length() == m_HijriAdvanceRegKeyEntry.length())
//                        hijriAdvance = -1;
//                    else
//                    {
//                        str = str.substring(m_HijriAdvanceRegKeyEntry.length());
//                        try
//                        {
//                            int advance = int.Parse(str.toString(), CultureInfo.getInvariantCulture());
//                            if ((advance >= MIN_ADVANCED_HIJRI) && (advance <= MAX_ADVANCED_HIJRI))
//                            {
//                                hijriAdvance = advance;
//                            }
//                        }
//                        // If we got garbage from registry just ignore it.
//                        // hijriAdvance = 0 because of declaraction assignment up above.
//                        catch (IllegalArgumentException e) { }
//                        catch (NumberFormatException e) { }
//                        catch (OverflowException e) { }
//                    }
//                }
//            }
//            finally
//            {
//                key.Close();
//            }
//
//        }
        return (hijriAdvance);

    }

    void checkTicksRange(long ticks)
    {
        if (ticks < CALENDAR_MIN_VALUE.getTicks() || ticks > CALENDAR_MAX_VALUE.getTicks())
        {
            throw new IllegalArgumentException("ArgumentOutOfRange_CalendarRange");
        }
    }

    void checkEraRange(int era)
    {
        if (era != CURRENT_ERA && era != HIJRI_ERA)
        {
            throw new IllegalArgumentException("ArgumentOutOfRange_InvalidEraValue");
        }
    }

    void checkYearRange(int year, int era)
    {
        checkEraRange(era);
        if (year < 1 || year > MAX_CALENDAR_YEAR)
        {
            throw new IllegalArgumentException("ArgumentOutOfRange_Range");
        }
    }

    void checkYearMonthRange(int year, int month, int era)
    {
        checkYearRange(year, era);
        if (year == MAX_CALENDAR_YEAR)
        {
            if (month > MAX_CALENDAR_MONTH)
            {
                throw new IllegalArgumentException("ArgumentOutOfRange_Range");
            }
        }

        if (month < 1 || month > 12)
        {
            throw new IllegalArgumentException("ArgumentOutOfRange_Month");
        }
    }

    /*=================================GetDatePart==========================
        **Action: Returns a given date part of this <i>DateTime</i>. This method is used 
        **       to compute the year, day-of-year, month, or day part. 
        **Returns:
        **Arguments: 
        **Exceptions:  ArgumentException if part is incorrect.
        **Notes:
        **      First, we get the absolute date (the number of days from January 1st, 1 A.C) for the given ticks.
        **      Use the formula (((AbsoluteDate - 227013) * 30) / 10631) + 1, we can a rough value for the Hijri year. 
        **      In order to get the exact Hijri year, we compare the exact absolute date for HijriYear and (HijriYear + 1).
        **      From here, we can get the correct Hijri year. 
        ============================================================================*/

    /*virtual*/ int getDatePart(long ticks, int part)
    {
        int HijriYear;                   // Hijri year
        int HijriMonth;                  // Hijri month
        int HijriDay;                    // Hijri day
        long NumDays;                 // The calculation buffer in number of days. 

        checkTicksRange(ticks);

        //
        //  Get the absolute date.  The absolute date is the number of days from January 1st, 1 A.D. 
        //  1/1/0001 is absolute date 1.
        //
        NumDays = ticks / GregorianCalendar.TICKS_PER_DAY + 1;

        //
        //  See how much we need to backup or advance 
        // 
        NumDays += getHijriAdjustment();

        //
        //  Calculate the appromixate Hijri Year from this magic formula.
        //
        HijriYear = (int)(((NumDays - 227013) * 30) / 10631) + 1;

        long daysToHijriYear = daysUpToHijriYear(HijriYear);            // The absoulte date for HijriYear 
        long daysOfHijriYear = getDaysInYear(HijriYear, CURRENT_ERA);    // The number of days for (HijriYear+1) year. 

        if (NumDays < daysToHijriYear)
        {
            daysToHijriYear -= daysOfHijriYear;
            HijriYear--;
        }
        else if (NumDays == daysToHijriYear)
        {
            HijriYear--;
            daysToHijriYear -= getDaysInYear(HijriYear, CURRENT_ERA);
        }
        else
        {
            if (NumDays > daysToHijriYear + daysOfHijriYear)
            {
                daysToHijriYear += daysOfHijriYear;
                HijriYear++;
            }
        }
        if (part == DATE_PART_YEAR)
        {
            return (HijriYear);
        }

        // 
        //  Calculate the Hijri Month.
        // 

        HijriMonth = 1;
        NumDays -= daysToHijriYear;

        if (part == DATE_PART_DAY_OF_YEAR)
        {
            return ((int)NumDays);
        }

        while ((HijriMonth <= 12) && (NumDays > HIJRI_MONTH_DAYS[HijriMonth - 1]))
        {
            HijriMonth++;
        }
        HijriMonth--;

        if (part == DATE_PART_MONTH)
        {
            return (HijriMonth);
        }

        // 
        //  Calculate the Hijri Day.
        //
        HijriDay = (int)(NumDays - HIJRI_MONTH_DAYS[HijriMonth - 1]);

        if (part == DATE_PART_DAY)
        {
            return (HijriDay);
        }
        // Incorrect part value.
        throw new IllegalStateException("InvalidOperation_DateTimeParsing");
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

    public /*override*/ DateTime addMonths(DateTime time, int months)
    {
        if (months < -120000 || months > 120000)
        {
            throw new IllegalArgumentException("ArgumentOutOfRange_Range");
        }
        // Get the date in Hijri calendar. 
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
        int days = getDaysInMonth(y, m);
        if (d > days)
        {
            d = days;
        }
        long ticks = getAbsoluteDateHijri(y, m, d) * TICKS_PER_DAY + (time.getTicks() % TICKS_PER_DAY);
        Calendar.checkAddResult(ticks, getMinSupportedDateTime(), getMaxSupportedDateTime());
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

    public /*override*/ DateTime addYears(DateTime time, int years)
    {
        return (addMonths(time, years * 12));
    }

    // Returns the day-of-month part of the specified DateTime. The returned
    // value is an integer between 1 and 31.
    // 

    public /*override*/ int getDayOfMonth(DateTime time)
    {
        return (getDatePart(time.getTicks(), DATE_PART_DAY));
    }

    // Returns the day-of-week part of the specified DateTime. The returned value
    // is an integer between 0 and 6, where 0 indicates Sunday, 1 indicates
    // Monday, 2 indicates Tuesday, 3 indicates Wednesday, 4 indicates
    // Thursday, 5 indicates Friday, and 6 indicates Saturday. 
    //

    public /*override*/ /*DayOfWeek*/int getDayOfWeek(DateTime time)
    {
        return ((/*DayOfWeek*/Integer)((int)(time.getTicks() / TICKS_PER_DAY + 1) % 7));
    }

    // Returns the day-of-year part of the specified DateTime. The returned value
    // is an integer between 1 and 366.
    // 

    public /*override*/ int getDayOfYear(DateTime time)
    {
        return (getDatePart(time.getTicks(), DATE_PART_DAY_OF_YEAR));
    }

    // Returns the number of days in the month given by the year and
    // month arguments.
    //

    public /*override*/ int getDaysInMonth(int year, int month, int era)
    {
        checkYearMonthRange(year, month, era);
        if (month == 12)
        {
            // For the 12th month, leap year has 30 days, and common year has 29 days.
            return (isLeapYear(year, CURRENT_ERA) ? 30 : 29);
        }
        // Other months contain 30 and 29 days alternatively.  The 1st month has 30 days.
        return (((month % 2) == 1) ? 30 : 29);
    }

    // Returns the number of days in the year given by the year argument for the current era. 
    // 

    public /*override*/ int getDaysInYear(int year, int era)
    {
        checkYearRange(year, era);
        // Common years have 354 days.  Leap years have 355 days.
        return (isLeapYear(year, CURRENT_ERA) ? 355 : 354);
    }

    // Returns the era for the specified DateTime value. 

    public /*override*/ int getEra(DateTime time)
    {
        checkTicksRange(time.getTicks());
        return (HIJRI_ERA);
    }


    public /*override*/ int[] getEras()
    {
        return (new int[] { HIJRI_ERA });
    }

    // Returns the month part of the specified DateTime. The returned value is an
    // integer between 1 and 12.
    // 

    public /*override*/ int getMonth(DateTime time)
    {
        return (getDatePart(time.getTicks(), DATE_PART_MONTH));
    }

    // Returns the number of months in the specified year and era.

    public /*override*/ int getMonthsInYear(int year, int era)
    {
        checkYearRange(year, era);
        return (12);
    }

    // Returns the year part of the specified DateTime. The returned value is an
    // integer between 1 and MaxCalendarYear. 
    //

    public /*override*/ int getYear(DateTime time)
    {
        return (getDatePart(time.getTicks(), DATE_PART_YEAR));
    }

    // Checks whether a given day in the specified era is a leap day. This method returns true if 
    // the date is a leap day, or false if not.
    // 

    public /*override*/ boolean isLeapDay(int year, int month, int day, int era)
    {
        // The year/month/era value checking is done in GetDaysInMonth().
        int daysInMonth = getDaysInMonth(year, month, era);
        if (day < 1 || day > daysInMonth)
        {
            throw new IllegalArgumentException("ArgumentOutOfRange_Day");
        }
        return (isLeapYear(year, era) && month == 12 && day == 30);
    }

    // Returns  the leap month in a calendar year of the specified era. This method returns 0
    // if this calendar does not have leap month, or this year is not a leap year. 
    //

    public /*override*/ int getLeapMonth(int year, int era)
    {
        checkYearRange(year, era);
        return (0);
    }

    // Checks whether a given month in the specified era is a leap month. This method returns true if
    // month is a leap month, or false if not.
    //

    public /*override*/ boolean isLeapMonth(int year, int month, int era)
    {
        checkYearMonthRange(year, month, era);
        return (false);
    }

    // Checks whether a given year in the specified era is a leap year. This method returns true if
    // year is a leap year, or false if not.
    //

    public /*override*/ boolean isLeapYear(int year, int era)
    {
        checkYearRange(year, era);
        return ((((year * 11) + 14) % 30) < 11);
    }

    // Returns the date and time converted to a DateTime value.  Throws an exception if the n-tuple is invalid.
    //

    public /*override*/ DateTime toDateTime(int year, int month, int day, int hour, int minute, int second, int millisecond, int era)
    {
        // The year/month/era checking is done in GetDaysInMonth().
        int daysInMonth = getDaysInMonth(year, month, era);
        if (day < 1 || day > daysInMonth)
        {
            //BCLDebug.Log("year = " + year + ", month = " + month + ", day = " + day);
            throw new IllegalArgumentException("ArgumentOutOfRange_Day");
        }

        long lDate = getAbsoluteDateHijri(year, month, day);

        if (lDate >= 0)
        {
            return (new DateTime(lDate * GregorianCalendar.TICKS_PER_DAY + timeToTicks(hour, minute, second, millisecond)));
        }
        else
        {
            throw new IllegalArgumentException("ArgumentOutOfRange_BadYearMonthDay");
        }
    }

    private static final int DEFAULT_TWO_DIGIT_YEAR_MAX = 1451;


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
        if (value < 99 || value > MAX_CALENDAR_YEAR)
        {
            throw new IllegalArgumentException("ArgumentOutOfRange_Range");

        }
        twoDigitYearMax = value;
    }


    public /*override*/ int toFourDigitYear(int year)
    {
        if (year < 100)
        {
            return (super.toFourDigitYear(year));
        }

        if (year > MAX_CALENDAR_YEAR)
        {
            throw new IllegalArgumentException("ArgumentOutOfRange_Range");
        }
        return (year);
    }

}
