// A Java port, made by Aspose, of a .NET Framework class. Microsoft publishes the source of that class
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/globalization/gregoriancalendar.cs
//
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

package com.aspose.ms.System.Globalization;

import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.DayOfWeek;


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

public class GregorianCalendar extends Calendar
{
    /*
            A.D. = anno Domini (after the birth of ---- ----)
         */

    public static final int AD_ERA = 1;


    static final int DATE_PART_YEAR = 0;
    static final int DATE_PART_DAY_OF_YEAR = 1;
    static final int DATE_PART_MONTH = 2;
    static final int DATE_PART_DAY = 3;

    //
    // This is the max Gregorian year can be represented by DateTime class.  The limitation 
    // is derived from DateTime class. 
    //
    static final int MAX_YEAR = 9999;

    /*GregorianCalendarTypes*/int m_type;

    static /*final*/ int[] DAYS_TO_MONTH_365 =
    {
        0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365
    };

    static /*final*/ int[] DAYS_TO_MONTH_366 =
    {
        0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366
    };

    static Calendar m_defaultInstance = null;

    //>>>>>>>> #region  Serialization 
//    @OnDeserialized
//    private void onDeserialized(StreamingContext ctx)
//    {
//        if (m_type < GregorianCalendarTypes.Localized ||
//            m_type > GregorianCalendarTypes.TransliteratedFrench)
//        {
//            throw new SerializationException(
//                        String.Format(
//                            CultureInfo.getCurrentCulture(),
//                            Environment.GetResourceString(
//                                            "Serialization_MemberOutOfRange"),
//                                            "type",
//                                            "GregorianCalendar"));
//        }
//    }
    //<<<<<<<< #endregion  Serialization


    
    public /*override*/ DateTime getMinSupportedDateTime()
    {
        return (DateTime.MinValue);
    }

    
    public /*override*/ DateTime getMaxSupportedDateTime()
    {
        return (DateTime.MaxValue);
    }

    // Return the type of the Gregorian calendar.
    // 

    
    public /*override*/ /*CalendarAlgorithmType*/int getAlgorithmType()
    {
        return CalendarAlgorithmType.SOLAR_CALENDAR;
    }

    /*=================================GetDefaultInstance==========================
        **Action: Internal method to provide a default intance of GregorianCalendar.  Used by NLS+ implementation
        **       and other calendars. 
        **Returns:
        **Arguments: 
        **Exceptions: 
        ============================================================================*/

    static Calendar getDefaultInstance()
    {
        if (m_defaultInstance == null)
        {
            m_defaultInstance = new GregorianCalendar();
        }
        return (m_defaultInstance);
    }

    // Construct an instance of gregorian calendar.

    public GregorianCalendar()
    {
    	this(GregorianCalendarTypes.LOCALIZED);
	
    }


    public GregorianCalendar(/*GregorianCalendarTypes*/int type)
    {
        if ((int)type < (int)GregorianCalendarTypes.LOCALIZED || (int)type > (int)GregorianCalendarTypes.TRANSLITERATED_FRENCH)
        {
            throw new IllegalArgumentException("ArgumentOutOfRange_Range");
        }
        this.m_type = type;
    }

    public /*virtual*/ /*GregorianCalendarTypes*/int getCalendarType()
    {
        return (m_type);
    }

    public /*virtual*/ void setCalendarType(/*GregorianCalendarTypes*/int value)
    {
        verifyWritable();

        switch (value)
        {
            case GregorianCalendarTypes.LOCALIZED:
            case GregorianCalendarTypes.US_ENGLISH:
            case GregorianCalendarTypes.MIDDLE_EAST_FRENCH:
            case GregorianCalendarTypes.ARABIC:
            case GregorianCalendarTypes.TRANSLITERATED_ENGLISH:
            case GregorianCalendarTypes.TRANSLITERATED_FRENCH:
                m_type = value;
                break;

            default:
                throw new IllegalArgumentException("ArgumentOutOfRange_Enum");
        }
    }

    public /*override*/ int getID()
    {
        // By returning different ID for different variations of GregorianCalendar, 
        // we can support the Transliterated Gregorian calendar. 
        // DateTimeFormatInfo will use this ID to get formatting information about
        // the calendar. 
        return ((int)m_type);
    }


    // Returns a given date part of this DateTime. This method is used 
    // to compute the year, day-of-year, month, or day part. 
    /*virtual*/ int getDatePart(long ticks, int part)
    {
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
        if (part == DATE_PART_MONTH)
            return (m);
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
        if (year >= 1 && year <= MAX_YEAR && month >= 1 && month <= 12)
        {
            int[] days = ((year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))) ? DAYS_TO_MONTH_366 : DAYS_TO_MONTH_365;
            if (day >= 1 && (day <= days[month] - days[month - 1]))
            {
                int y = year - 1;
                int absoluteDate = y * 365 + y / 4 - y / 100 + y / 400 + days[month - 1] + day - 1;
                return (absoluteDate);
            }
        }
        throw new IllegalArgumentException("ArgumentOutOfRange_BadYearMonthDay");
    }

    // Returns the tick count corresponding to the given year, month, and day. 
    // Will check the if the parameters are valid.
    /*virtual*/ long dateToTicks(int year, int month, int day)
    {
        return (getAbsoluteDate(year, month, day) * TICKS_PER_DAY);
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
        long ticks = dateToTicks(y, m, d) + time.getTicks() % TICKS_PER_DAY;
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
        return ((/*DayOfWeek*/int)((time.getTicks() / TICKS_PER_DAY + 1) % 7));
    }


    // This is copied from the generic implementation of Calendar.cs.  The generic implementation is not good enough 
    // in the case of FristFullWeek and FirstFourDayWeek since it needs the data for B.C. year 1 near 0001/1/1. 
    // We override the generic implementation to handle this special case.
    public /*override*/ int getWeekOfYear(DateTime time, /*CalendarWeekRule*/int rule, /*DayOfWeek*/int firstDayOfWeek)
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
                return (internalGetWeekOfYearFullDays(this, time, (int)firstDayOfWeek, 7, 365));
            case CalendarWeekRule.FIRST_FOUR_DAY_WEEK:
                return (internalGetWeekOfYearFullDays(this, time, (int)firstDayOfWeek, 4, 365));
        }
        throw new IllegalArgumentException("ArgumentOutOfRange_Range");

    }



    // This is copied from the generic implementation of GetWeekOfYearFullDays() in Calendar.cs.  The generic implementation is not good enough 
    // in the case of FristFullWeek and FirstFourDayWeek since it needs the data for B.C. year 1 near 0001/1/1. 
    // We override the generic implementation to handle this special case.

    // Parameters
    //
    static int internalGetWeekOfYearFullDays(Calendar cal, DateTime time, int firstDayOfWeek, int fullDays, int daysOfMinYearMinusOne)
    {
        int dayForJan1;
        int offset;
        int day;

        int dayOfYear = cal.getDayOfYear(time) - 1; // Make the day of year to be 0-based, so that 1/1 is day 0.
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
        dayForJan1 = (int)cal.getDayOfWeek(time) - (dayOfYear % 7);

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
        // Note that it is not always week 52 or 53, because it depends on the calendar.  Different calendars have different number of days in a year. 
        // 

        // Repeat the previous calculation logic using the previous year and calculate the week of year for the last day of previous year. 
        int year = cal.getYear(time);
        if (year <= cal.getYear(cal.getMinSupportedDateTime()))
        {
            // This specified time is in 0001/1/1 ~ 0001/1/7.
            dayOfYear = daysOfMinYearMinusOne;
        }
        else
        {
            dayOfYear = cal.getDaysInYear(year - 1);
        }
        dayForJan1 = dayForJan1 - (dayOfYear % 7);
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
        return (day / 7 + 1);
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
        if (era == CURRENT_ERA || era == AD_ERA)
        {
            if (year < 1 || year > MAX_YEAR)
            {
                throw new IllegalArgumentException("year ArgumentOutOfRange_Range");
            }
            if (month < 1 || month > 12)
            {
                throw new IllegalArgumentException("ArgumentOutOfRange_Month");
            }
            int[] days = ((year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? DAYS_TO_MONTH_366 : DAYS_TO_MONTH_365);
            return (days[month] - days[month - 1]);
        }
        throw new IllegalArgumentException("ArgumentOutOfRange_InvalidEraValue");
    }

    // Returns the number of days in the year given by the year argument for the current era.
    //

    public /*override*/ int getDaysInYear(int year, int era)
    {
        if (era == CURRENT_ERA || era == AD_ERA)
        {
            if (year >= 1 && year <= MAX_YEAR)
            {
                return ((year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? 366 : 365);
            }
            throw new IllegalArgumentException("year ArgumentOutOfRange_Range");
        }
        throw new IllegalArgumentException("ArgumentOutOfRange_InvalidEraValue");
    }

    // Returns the era for the specified DateTime value. 

    public /*override*/ int getEra(DateTime time)
    {
        return (AD_ERA);
    }


    public /*override*/ int[] getEras()
    {
        return (new int[] { AD_ERA });
    }


    // Returns the month part of the specified DateTime. The returned value is an
    // integer between 1 and 12. (i.e. months are 1-indexed).
    //

    public /*override*/ int getMonth(DateTime time)
    {
        return (getDatePart(time.getTicks(), DATE_PART_MONTH));
    }

    // Returns the number of months in the specified year and era.

    public /*override*/ int getMonthsInYear(int year, int era)
    {
        if (era == CURRENT_ERA || era == AD_ERA)
        {
            if (year >= 1 && year <= MAX_YEAR)
            {
                return (12);
            }
            throw new IllegalArgumentException("ArgumentOutOfRange_Range");
        }
        throw new IllegalArgumentException("ArgumentOutOfRange_InvalidEraValue");
    }

    // Returns the year part of the specified DateTime. The returned value is an
    // integer between 1 and 9999. 
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
        if (era != CURRENT_ERA && era != AD_ERA)
        {
            throw new IllegalArgumentException("ArgumentOutOfRange_InvalidEraValue");
        }
        if (year < 1 || year > MAX_YEAR)
        {
            throw new IllegalArgumentException("ArgumentOutOfRange_Range");
        }

        if (month < 1 || month > 12)
        {
            throw new IllegalArgumentException("ArgumentOutOfRange_Range");
        }
        if (day < 1 || day > getDaysInMonth(year, month))
        {
            throw new IllegalArgumentException("ArgumentOutOfRange_Range");
        }
        if (!isLeapYear(year))
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

    public /*override*/ int getLeapMonth(int year, int era)
    {
        if (era != CURRENT_ERA && era != AD_ERA)
        {
            throw new IllegalArgumentException("ArgumentOutOfRange_InvalidEraValue");
        }
        if (year < 1 || year > MAX_YEAR)
        {
            throw new IllegalArgumentException("ArgumentOutOfRange_Range");
        }
        return (0);
    }

    // Checks whether a given month in the specified era is a leap month. This method returns true if
    // month is a leap month, or false if not.
    // 

    public /*override*/ boolean isLeapMonth(int year, int month, int era)
    {
        if (era != CURRENT_ERA && era != AD_ERA)
        {
            throw new IllegalArgumentException("ArgumentOutOfRange_InvalidEraValue");
        }

        if (year < 1 || year > MAX_YEAR)
        {
            throw new IllegalArgumentException("ArgumentOutOfRange_Range");
        }

        if (month < 1 || month > 12)
        {
            throw new IllegalArgumentException("ArgumentOutOfRange_Range");
        }
        return (false);

    }

    // Checks whether a given year in the specified era is a leap year. This method returns true if
    // year is a leap year, or false if not.
    //

    public /*override*/ boolean isLeapYear(int year, int era)
    {
        if (era == CURRENT_ERA || era == AD_ERA)
        {
            if (year >= 1 && year <= MAX_YEAR)
            {
                return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
            }

            throw new IllegalArgumentException("ArgumentOutOfRange_Range");
        }
        throw new IllegalArgumentException("ArgumentOutOfRange_InvalidEraValue");
    }

    // Returns the date and time converted to a DateTime value.  Throws an exception if the n-tuple is invalid.
    //

    public /*override*/ DateTime toDateTime(int year, int month, int day, int hour, int minute, int second, int millisecond, int era)
    {
        if (era == CURRENT_ERA || era == AD_ERA)
        {
            return new DateTime(year, month, day, hour, minute, second, millisecond);
        }
        throw new IllegalArgumentException("ArgumentOutOfRange_InvalidEraValue");
    }

//    /*override*/ boolean tryToDateTime(int year, int month, int day, int hour, int minute, int second, int millisecond, int era, /*out*/ DateTime[] result)
//    {
//        if (era == CURRENT_ERA || era == AD_ERA)
//        {
//            return DateTime.TryCreate(year, month, day, hour, minute, second, millisecond, /*out*/ result);
//        }
//        result[0] = DateTime.MinValue;
//        return false;
//    }

    private static final int DEFAULT_TWO_DIGIT_YEAR_MAX = 2029;


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
        if (value < 99 || value > MAX_YEAR)
        {
            throw new IllegalArgumentException("ArgumentOutOfRange_Range");

        }
        twoDigitYearMax = value;
    }


    public /*override*/ int toFourDigitYear(int year)
    {
        if (year > MAX_YEAR)
        {
            throw new IllegalArgumentException("ArgumentOutOfRange_Range");
        }
        return (super.toFourDigitYear(year));
    }
}
