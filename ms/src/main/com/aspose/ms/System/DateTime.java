// Parts of this file are a Java port, made by Aspose, of .NET Framework code. Microsoft publishes that source
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/datetime.cs
//
// Those parts: Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

/**
 * Copyright Aspose.com, 2007
 * Helper Java code to port Aspose.Word C# source, 23/10/2007
 *
 * @author Konstantin Sidorenko
 */

package com.aspose.ms.System;

import com.aspose.ms.java.logging.Log;
import com.aspose.words.ref.Ref;
import com.aspose.ms.System.Globalization.*;
import com.aspose.ms.System.Threading.CurrentThread;
import com.aspose.ms.java.debug.JavaDebug;

import java.util.Date;
import java.util.TimeZone;


/**
 * Port of .net System.Datetime.
 * Stolen mainly from Rotor.
 *
 * Not implemented some members not used in Aspose.Words.
 * Some methods (ToLocalTime(), Parse(), ToString()) that demand porting of classes
 * tied to internalization (or rather families of classes) backed by java analogs.
 *
 * Note:
 * All dates before the Gregorian Calendar CutOver Date (October 15, 1582 in most western countries)
 * interpreted incorrectly (or rather not as in .Net) by toJava() method
 * because these methods use java.lang.Date internally.
 * DateTime.toString() method does so too because uses toJava() internally.
 */
// This value type represents a date and time.  Every DateTime
// object has a private field (Ticks) of type Int64 that stores the
// date and time as the number of 100 nanosecond intervals since
// 12:00 AM January 1, year 1 A.D. in the proleptic Gregorian Calendar.
//
// Starting from V2.0, DateTime also stored some context about its time
// zone in the form of a 3-state value representing Unspecified, Utc or
// Local. This is stored in the two top bits of the 64-bit numeric value
// with the remainder of the bits storing the tick count. This information
// is only used during time zone conversions and is not part of the
// identity of the DateTime. Thus, operations like Compare and Equals
// ignore this state. This is to stay compatible with earlier behavior
// and performance characteristics and to avoid forcing  people into dealing
// with the effects of daylight savings. Note, that this has little effect
// on how the DateTime works except in a context where its specific time
// zone is needed, such as during conversions and some parsing and formatting
// cases.
//
// There is also 4th state stored that is a special type of Local value that
// is used to avoid data loss when round-tripping between local and UTC time.
// See below for more information on this 4th state, although it is
// effectively hidden from most users, who just see the 3-state DateTimeKind
// enumeration.
//
// For compatibility, DateTime does not serialize the Kind data when used in
// binary serialization.
//
// For a description of various calendar issues, look at
//
// Calendar Studies web site, at
// http://serendipity.nofadz.com/hermetic/cal_stud.htm.
//
//
public final class DateTime implements Comparable<Object>//JAVA was: struct
{

	public static DateTime ConvertTime(DateTime dateTime, TimeZone customTimeZone)
	{
		// java.util.TimeZone wants milliseconds of the Unix epoch. Given the raw .NET ticks it looked up the daylight saving
		// rules of a date millions of years away and was an hour off wherever those differ from the rules of the real date.
		int offset = customTimeZone.getOffset(dateTime.toJavaTicks());
		return dateTime.addMilliseconds(offset);
	}

	public DateTime getUtcOffset()
	{
		throw new UnsupportedOperationException();
	}

	// Number of 100ns ticks per time unit
	private static final long TicksPerMillisecond = 10000;
	private static final long TicksPerSecond = TicksPerMillisecond * 1000;
	private static final long TicksPerMinute = TicksPerSecond * 60;
	private static final long TicksPerHour = TicksPerMinute * 60;
	private static final long TicksPerDay = TicksPerHour * 24;

	// Number of milliseconds per time unit
	private static final int MillisPerSecond = 1000;
	private static final int MillisPerMinute = MillisPerSecond * 60;
	private static final int MillisPerHour = MillisPerMinute * 60;
	private static final int MillisPerDay = MillisPerHour * 24;

	// Number of days in a non-leap year
	private static final int DaysPerYear = 365;
	// Number of days in 4 years
	private static final int DaysPer4Years = DaysPerYear * 4 + 1;
	// Number of days in 100 years
	private static final int DaysPer100Years = DaysPer4Years * 25 - 1;
	// Number of days in 400 years
	private static final int DaysPer400Years = DaysPer100Years * 4 + 1;

	// Number of days from 1/1/0001 to 12/31/1600
	private static final int DaysTo1601 = DaysPer400Years * 4;
	// Number of days from 1/1/0001 to 12/30/1899
	private static final int DaysTo1899 = DaysPer400Years * 4 + DaysPer100Years * 3 - 367;
	// Number of days from 1/1/0001 to 12/31/9999
	private static final int DaysTo10000 = DaysPer400Years * 25 - 366;

	public static final long MinTicks = 0;
	public static final long MaxTicks = DaysTo10000 * TicksPerDay - 1;
	private static final long MaxMillis = (long)DaysTo10000 * MillisPerDay;

	private static final long FileTimeOffset = DaysTo1601 * TicksPerDay;
	private static final long DoubleDateOffset = DaysTo1899 * TicksPerDay;
	// The minimum OA date is 0100/01/01 (Note it's year 100).
	// The maximum OA date is 9999/12/31
	private static final long OADateMinAsTicks = (DaysPer100Years - DaysPerYear) * TicksPerDay;
	// All OA dates must be greater than (not >=) OADateMinAsDouble
	private static final double OADateMinAsDouble = -657435.0;
	// All OA dates must be less than (not <=) OADateMaxAsDouble
	private static final double OADateMaxAsDouble = 2958466.0;

	private static final int DatePartYear = 0;
	private static final int DatePartDayOfYear = 1;
	private static final int DatePartMonth = 2;
	private static final int DatePartDay = 3;

	private static final int[] DaysToMonth365 = {
		0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365};
	private static final int[] DaysToMonth366 = {
		0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366};

	/**
	 * Due calendar diffs this min value is 03.01.0001 (but ticks = 0).
	 * see TestConstants.TestConstants() and TestGregorianCutOver().
	 */
	public static final DateTime MinValue = new DateTime(MinTicks, DateTimeKind.UNSPECIFIED);
	public static final DateTime MaxValue = new DateTime(MaxTicks, DateTimeKind.UNSPECIFIED);
	/**
	 * JAVA-added HACK. Hardcoded .Net MinValue (01.01.0001 00:00:00 GMT) in java.util.Date ticks.
	 * Needed for several unit tests.
 	 */
	public static final long MinValueToUnixTicks = -62135769599766L;

	//JAVA: all UInt64 changed to long
	private static final long TicksMask             = 0x3FFFFFFFFFFFFFFFL;
	private static final long FlagsMask             = 0xC000000000000000L;
	private static final long LocalMask             = 0x8000000000000000L;
	private static final long TicksCeiling           = 0x4000000000000000L;
	private static final long KindUnspecified       = 0x0000000000000000L;
	private static final long KindUtc               = 0x4000000000000000L;
	private static final long KindLocal             = 0x8000000000000000L;
	private static final long KindLocalAmbiguousDst = 0xC000000000000000L;
	private static final int KindShift = 62;

	private static final java.lang.String TicksField            = "ticks";
	private static final java.lang.String DateDataField         = "dateData";

	// The data is stored as an unsigned 64-bit integeter
	//   Bits 01-62: The value of 100-nanosecond ticks where 0 represents 1/1/0001 12:00am, up until the value
	//               12/31/9999 23:59:59.9999999
	//   Bits 63-64: A four-state value that describes the DateTimeKind value of the date time, with a 2nd
	//               value for the rare case where the date time is local, but is in an overlapped daylight
	//               savings time hour and it is in daylight savings time. This allows distinction of these
	//               otherwise ambiguous local times and prevents data loss when round tripping from Local to
	//               UTC time.
	private long dateData;//JAVA: UInt64 changed to long

	//JAVA-added for struct initialization. Equals to DateTime.MinValue
	public DateTime()
	{
		dateData = MinTicks;
	}

	// Constructs a DateTime from a tick count. The ticks
	// argument specifies the date as the number of 100-nanosecond intervals
	// that have elapsed since 1/1/0001 12:00am.
	//
	public DateTime(long ticks)
	{
		if (ticks < MinTicks || ticks > MaxTicks)
			throw new IllegalArgumentException("ticks");
		dateData = ticks;
	}

	//JAVA-changed from  DateTime(UInt64 dateData) that conflicts with DateTime(long dateData)
	private static DateTime dateTimeFromDateData(long dateData)
	{
		DateTime dateTime = new DateTime();
		dateTime.dateData = dateData;
		return dateTime;
	}

	public DateTime(long ticks, long kind)//JAVA was: DateTimeKind enum
	{
		if (ticks < MinTicks || ticks > MaxTicks) {
			throw new IllegalArgumentException("ticks");
		}
		if (kind < DateTimeKind.UNSPECIFIED || kind > DateTimeKind.LOCAL) {
			throw new IllegalArgumentException("kind");
		}
		this.dateData = (ticks | (kind << KindShift));
	}

	DateTime(long ticks, long kind, boolean isAmbiguousDst)//JAVA was: DateTimeKind enum
	{
		if (ticks < MinTicks || ticks > MaxTicks) {
			throw new IllegalArgumentException("ticks");
		}
		assert kind == DateTimeKind.LOCAL : "Internal Constructor is for local times only";
		dateData = (ticks | (isAmbiguousDst ? KindLocalAmbiguousDst : KindLocal));
	}

	// Constructs a DateTime from a given year, month, and day. The
	// time-of-day of the resulting DateTime is always midnight.
	//
	public DateTime(int year, int month, int day)
	{
		this.dateData = dateToTicks(year, month, day);
	}

    // Constructs a DateTime from a given year, month, and day for
    // the specified calendar. The
    // time-of-day of the resulting DateTime is always midnight.
    //
    public DateTime(int year, int month, int day, Calendar calendar)
    {
        this(year, month, day, 0, 0, 0, calendar);
    }

	// Constructs a DateTime from a given year, month, day, hour,
	// minute, and second.
	//
	public DateTime(int year, int month, int day, int hour, int minute, int second)
	{
		this.dateData = dateToTicks(year, month, day) + timeToTicks(hour, minute, second);
	}

	public DateTime(int year, int month, int day, int hour, int minute, int second, long kind)//JAVA was: DateTimeKind enum
	{
		long ticks = dateToTicks(year, month, day) + timeToTicks(hour, minute, second);
		if (kind < DateTimeKind.UNSPECIFIED || kind > DateTimeKind.LOCAL) {
			throw new IllegalArgumentException("kind");
		}
		this.dateData = (ticks | (kind << KindShift));
	}

    // Constructs a DateTime from a given year, month, day, hour,
    // minute, and second for the specified calendar.
    //
    public DateTime(int year, int month, int day, int hour, int minute, int second, Calendar calendar)
    {
        if (calendar == null)
            throw new NullPointerException("calendar");

        this.dateData = calendar.toDateTime(year, month, day, hour, minute, second, 0).getTicks();
    }

	// Constructs a DateTime from a given year, month, day, hour,
	// minute, and second.
	//
	public DateTime(int year, int month, int day, int hour, int minute, int second, int millisecond)
	{
		long ticks = dateToTicks(year, month, day) + timeToTicks(hour, minute, second);
		if (millisecond < 0 || millisecond >= MillisPerSecond) {
			throw new IllegalArgumentException("millisecond");
		}
		ticks += millisecond * TicksPerMillisecond;
		if (ticks < MinTicks || ticks > MaxTicks)
			throw new IllegalArgumentException("Arg_DateTimeRange");
		this.dateData = ticks;//JAVA was: (UInt64)
	}

	public DateTime(int year, int month, int day, int hour, int minute, int second, int millisecond, long kind)//JAVA was: DateTimeKind enum
	{
		long ticks = dateToTicks(year, month, day) + timeToTicks(hour, minute, second);
		if (millisecond < 0 || millisecond >= MillisPerSecond) {
			throw new IllegalArgumentException("millisecond");
		}
		ticks += millisecond * TicksPerMillisecond;
		if (ticks < MinTicks || ticks > MaxTicks)
			throw new IllegalArgumentException("Arg_DateTimeRange");
		if (kind < DateTimeKind.UNSPECIFIED || kind > DateTimeKind.LOCAL) {
			throw new IllegalArgumentException("Argument_InvalidDateTimeKind");
		}
		this.dateData = (ticks | (kind << KindShift));
	}

    // Constructs a DateTime from a given year, month, day, hour,
    // minute, and second for the specified calendar.
    //
    public DateTime(int year, int month, int day, int hour, int minute, int second, int millisecond, Calendar calendar)
    {
        if (calendar == null)
            throw new NullPointerException("calendar");

        long ticks = calendar.toDateTime(year, month, day, hour, minute, second, 0).getTicks();
        if (millisecond < 0 || millisecond >= MillisPerSecond) {
            throw new IllegalArgumentException("millisecond");
        }
        ticks += millisecond * TicksPerMillisecond;
        if (ticks < MinTicks || ticks > MaxTicks)
            throw new IllegalArgumentException("Arg_DateTimeRange");
        this.dateData = ticks;
    }

        public DateTime(int year, int month, int day, int hour, int minute, int second, int millisecond, Calendar calendar, /*DateTimeKind*/long kind)
        {
            if (calendar == null)
                throw new NullPointerException("calendar");

            long ticks = calendar.toDateTime(year, month, day, hour, minute, second, 0).getTicks();

            if (millisecond < 0 || millisecond >= MillisPerSecond)
                throw new IllegalArgumentException("millisecond");

            ticks += millisecond * TicksPerMillisecond;
            if (ticks < MinTicks || ticks > MaxTicks)
                throw new IllegalArgumentException("Arg_DateTimeRange");

            if (kind < DateTimeKind.UNSPECIFIED || kind > DateTimeKind.LOCAL)
                throw new IllegalArgumentException("Argument_InvalidDateTimeKind");

            this.dateData = (ticks | (kind << KindShift));
        }

//        private DateTime(SerializationInfo info, StreamingContext context) {
//            if (info==null)
//                throw new NullPointerException("info");
//
//            boolean foundTicks = false;
//            boolean foundDateData = false;
//            Int64 serializedTicks = 0;
//            UInt64 serializedDateData = 0;
//
//
//            // Get the data
//            SerializationInfoEnumerator enumerator = info.GetEnumerator();
//            while( enumerator.MoveNext()) {
//                switch( enumerator.Name) {
//                    case TicksField:
//                        serializedTicks = Convert.ToInt64(enumerator.Value, CultureInfo.INVARIANT_CULTURE);
//                        foundTicks = true;
//                        break;
//                    case DateDataField:
//                        serializedDateData = Convert.ToUInt64(enumerator.Value, CultureInfo.INVARIANT_CULTURE);
//                        foundDateData = true;
//                        break;
//                    default:
//                        // Ignore other fields for forward compatability.
//                        break;
//                }
//            }
//            if (foundDateData) {
//                this.dateData = serializedDateData;
//            }
//            else if (foundTicks) {
//                this.dateData = (UInt64)serializedTicks;
//            }
//            else {
//                throw new SerializationException("Serialization_MissingDateTimeData"));
//            }
//            Int64 ticks = InternalTicks;
//            if (ticks < MinTicks || ticks > MaxTicks) {
//                throw new SerializationException("Serialization_DateTimeTicksOutOfRange"));
//            }
//        }


	private long getInternalTicks()
	{
		return (dateData & TicksMask);
	}

	private long getInternalKind() //JAVA was: ulong
	{
		return (dateData & FlagsMask);
	}

	// Returns the DateTime resulting from adding the given
	// TimeSpan to this DateTime.
	//
	public DateTime add(TimeSpan value)
	{
		return addTicks(value._ticks);
	}

    public static DateTime add(DateTime d, TimeSpan t)
    {
        return d.add(t);
    }

	// Returns the DateTime resulting from adding a fractional number of
	// time units to this DateTime.
	private DateTime add(double value, int scale)
	{
		long millis = (long)(value * scale + (value >= 0.0 ? 0.5 : -0.5));
		if (millis <= -MaxMillis || millis >= MaxMillis)
			throw new IllegalArgumentException("value");
		return addTicks(millis * TicksPerMillisecond);
	}

	// Returns the DateTime resulting from adding a fractional number of
	// days to this DateTime. The result is computed by rounding the
	// fractional number of days given by value to the nearest
	// millisecond, and adding that interval to this DateTime. The
	// value argument is permitted to be negative.
	//
	public DateTime addDays(double value)
	{
		return add(value, MillisPerDay);
	}

	// Returns the DateTime resulting from adding a fractional number of
	// hours to this DateTime. The result is computed by rounding the
	// fractional number of hours given by value to the nearest
	// millisecond, and adding that interval to this DateTime. The
	// value argument is permitted to be negative.
	//
	public DateTime addHours(double value)
	{
		return add(value, MillisPerHour);
	}

	// Returns the DateTime resulting from the given number of
	// milliseconds to this DateTime. The result is computed by rounding
	// the number of milliseconds given by value to the nearest integer,
	// and adding that interval to this DateTime. The value
	// argument is permitted to be negative.
	//
	public DateTime addMilliseconds(double value)
	{
		return add(value, 1);
	}

	// Returns the DateTime resulting from adding a fractional number of
	// minutes to this DateTime. The result is computed by rounding the
	// fractional number of minutes given by value to the nearest
	// millisecond, and adding that interval to this DateTime. The
	// value argument is permitted to be negative.
	//
	public DateTime addMinutes(double value)
	{
		return add(value, MillisPerMinute);
	}

	// Returns the DateTime resulting from adding the given number of
	// months to this DateTime. The result is computed by incrementing
	// (or decrementing) the year and month parts of this DateTime by
	// months months, and, if required, adjusting the day part of the
	// resulting date downwards to the last day of the resulting month in the
	// resulting year. The time-of-day part of the result is the same as the
	// time-of-day part of this DateTime.
	//
	// In more precise terms, considering this DateTime to be of the
	// form y / m / d + t, where y is the
	// year, m is the month, d is the day, and t is the
	// time-of-day, the result is y1 / m1 / d1 + t,
	// where y1 and m1 are computed by adding months months
	// to y and m, and d1 is the largest value less than
	// or equal to d that denotes a valid day in month m1 of year
	// y1.
	//
	public DateTime addMonths(int months)
	{
		if (months < -120000 || months > 120000)
			throw new IllegalArgumentException("months");
		int y = getDatePart(DatePartYear);
		int m = getDatePart(DatePartMonth);
		int d = getDatePart(DatePartDay);
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
		if (y < 1 || y > 9999)
			throw new IllegalArgumentException("months");

		int days = daysInMonth(y, m);
		if (d > days) d = days;
		return dateTimeFromDateData((dateToTicks(y, m, d) + getInternalTicks() % TicksPerDay) | getInternalKind());//JAVA-changed to fabric
	}

	// Returns the DateTime resulting from adding a fractional number of
	// seconds to this DateTime. The result is computed by rounding the
	// fractional number of seconds given by value to the nearest
	// millisecond, and adding that interval to this DateTime. The
	// value argument is permitted to be negative.
	//
	public DateTime addSeconds(double value)
	{
		return add(value, MillisPerSecond);
	}

	// Returns the DateTime resulting from adding the given number of
	// 100-nanosecond ticks to this DateTime. The value argument
	// is permitted to be negative.
	//
	public DateTime addTicks(long value)
	{
		long ticks = getInternalTicks();
		if (value > MaxTicks - ticks || value < MinTicks - ticks) {
			throw new IllegalArgumentException("value");
		}
		return dateTimeFromDateData((ticks + value) | getInternalKind());//JAVA-changed to fabric
	}

	// Returns the DateTime resulting from adding the given number of
	// years to this DateTime. The result is computed by incrementing
	// (or decrementing) the year part of this DateTime by value
	// years. If the month and day of this DateTime is 2/29, and if the
	// resulting year is not a leap year, the month and day of the resulting
	// DateTime becomes 2/28. Otherwise, the month, day, and time-of-day
	// parts of the result are the same as those of this DateTime.
	//
	public DateTime addYears(int value)
	{
		if (value < -10000 || value > 10000)
			throw new IllegalArgumentException("years");
		return addMonths(value * 12);
	}

	// Compares two DateTime values, returning an integer that indicates
	// their relationship.
	//
	public static int compare(DateTime t1, DateTime t2)
	{
		long ticks1 = t1.getInternalTicks();
		long ticks2 = t2.getInternalTicks();
		if (ticks1 > ticks2)
			return 1;
		if (ticks1 < ticks2)
			return -1;
		return 0;
	}

	// Compares this DateTime to a given Object. This method provides an
	// implementation of the IComparable interface. The Object
	// argument must be another DateTime, or otherwise an exception
	// occurs.  Null is considered less than any instance.
	//
	// Returns a value less than zero if this  Object
	public int compareTo(Object value)
	{
		if (value == null)
			return 1;
		if (!(value instanceof DateTime)) {
			throw new IllegalArgumentException("Arg_MustBeDateTime");
		}

		long valueTicks = ((DateTime)value).getInternalTicks();
		long ticks = getInternalTicks();
		if (ticks > valueTicks)
			return 1;
		if (ticks < valueTicks)
			return -1;
		return 0;
	}

	public int compareTo(DateTime value)
	{
		long valueTicks = value.getInternalTicks();
		long ticks = getInternalTicks();
		if (ticks > valueTicks)
			return 1;
		if (ticks < valueTicks)
			return -1;
		return 0;
	}

	// Returns the tick count corresponding to the given year, month, and day.
	// Will check the if the parameters are valid.
	private static long dateToTicks(int year, int month, int day)
	{
		if (year >= 1 && year <= 9999 && month >= 1 && month <= 12)
		{
			int[] days = isLeapYear(year)? DaysToMonth366: DaysToMonth365;
			if (day >= 1 && day <= days[month] - days[month - 1])
			{
				int y = year - 1;
				int n = y * 365 + y / 4 - y / 100 + y / 400 + days[month - 1] + day - 1;
				return n * TicksPerDay;
			}
		}
		throw new IllegalArgumentException("Year = " + year + ", Month = " + month);
	}

	// Return the tick count corresponding to the given hour, minute, second.
	// Will check the if the parameters are valid.
	private static long timeToTicks(int hour, int minute, int second)
	{
		//TimeSpan.TimeToTicks is a family access function which does no error checking, so
		//we need to put some error checking out here.
		if (hour >= 0 && hour < 24 && minute >= 0 && minute < 60 && second >=0 && second < 60)
		{
			return (TimeSpan.timeToTicks(hour, minute, second));
		}
		throw new IllegalArgumentException("ArgumentOutOfRange_BadHourMinuteSecond");
	}

	// Returns the number of days in the month given by the year and
	// month arguments.
	//
	public static int daysInMonth(int year, int month)
	{
		if (month < 1 || month > 12)
			throw new IllegalArgumentException("month");
		// IsLeapYear checks the year argument
		int[] days = isLeapYear(year)? DaysToMonth366: DaysToMonth365;
		return days[month] - days[month - 1];
	}

	// Converts an OLE Date to a tick count.
	// This function is duplicated in COMDateTime.cpp
	static long doubleDateToTicks(double value)
	{
		if (value >= OADateMaxAsDouble || value <= OADateMinAsDouble)
			throw new IllegalArgumentException("Arg_OleAutDateInvalid");
		long millis = (long)(value * MillisPerDay + (value >= 0.0 ? 0.5: -0.5));
		// The interesting thing here is when you have a value like 12.5 it all positive 12 days and 12 hours from 01/01/1899
		// However if you a value of -12.25 it is minus 12 days but still positive 6 hours, almost as though you meant -11.75 all negative
		// This line below fixes up the millis in the negative case
		if (millis < 0L) {
			millis -= (millis % MillisPerDay) * 2;
		}

		millis += DoubleDateOffset / TicksPerMillisecond;

		if (millis < 0L || millis >= MaxMillis)
			throw new IllegalArgumentException("Arg_OleAutDateScale");
		return millis * TicksPerMillisecond;
	}

    // This is needed because java equals is overridden.
    // Implementation by Rotor.
    public int hashCode()
    {
        long ticks = getInternalTicks();
        return ((int)ticks) ^ (int)(ticks >> 32);
    }

    // This overrides java equals.
    // Implementation by RK.
	public boolean equals(Object value)
	{
		return (value instanceof DateTime) && equals(this, (DateTime)value);
	}

    // This is called by the autoporter when it encounters == in C#. It is the actual implementation of equality.
    // Implementation by RK.
    public static boolean equals(DateTime a, DateTime b)
    {
        if (a == b)
            return true;

        return ((a != null) && (b != null) &&
            (a.getInternalTicks() == b.getInternalTicks()));
    }

    // This is called by the autoporter when it encounters < in C#.
    public static boolean less(DateTime a, DateTime b)
    {
        if (a == b)
            return false;

	    if (a == null || b == null)
		    return false;

        return a.getInternalTicks() < b.getInternalTicks();
    }

    // This is called by the autoporter when it encounters <= in C#.
    public static boolean lessOrEqual(DateTime a, DateTime b)
    {
        if (a == b)
            return true;

	    if (a == null || b == null)
		    return false;

        return a.getInternalTicks() <= b.getInternalTicks();
    }

    // This is called by the autoporter when it encounters > in C#.
    public static boolean greater(DateTime a, DateTime b)
    {
        if (a == b)
            return false;

	    if (a == null || b == null)
		    return false;

        return a.getInternalTicks() > b.getInternalTicks();
    }

    // This is called by the autoporter when it encounters >= in C#.
    public static boolean greaterOrEqual(DateTime a, DateTime b)
    {
        if (a == b)
            return true;

	    if (a == null || b == null)
		    return false;

        return a.getInternalTicks() >= b.getInternalTicks();
    }

//        public static DateTime FromBinary(Int64 dateData) {
//            if ((dateData & (unchecked( (Int64) LocalMask))) != 0) {
//                // Local times need to be adjusted as you move from one time zone to another,
//                // just as they are when serializing in text. As such the format for local times
//                // changes to store the ticks of the UTC time, but with flags that look like a
//                // local date.
//                Int64 ticks = dateData & (unchecked((Int64)TicksMask));
//                // Negative ticks are stored in the top part of the range and should be converted back into a negative number
//                if (ticks > TicksCeiling - TicksPerDay) {
//                    ticks = ticks - TicksCeiling;
//                }
//                // Convert the ticks back to local. If the UTC ticks are out of range, we need to default to
//                // the UTC offset from MinValue and MaxValue to be consistent with Parse.
//                boolean isAmbiguousLocalDst = false;
//                Int64 offsetTicks;
//                if (ticks < MinTicks) {
//                    offsetTicks = TimeZone.CurrentTimeZone.GetUtcOffset(DateTime.MinValue).Ticks;
//                }
//                else if (ticks > MaxTicks) {
//                    offsetTicks = TimeZone.CurrentTimeZone.GetUtcOffset(DateTime.MaxValue).Ticks;
//                }
//                else {
//                    // Because the ticks conversion between UTC and local is lossy, we need to capture whether the
//                    // time is in a repeated hour so that it can be passed to the DateTime constructor.
//                    CurrentSystemTimeZone tz = (CurrentSystemTimeZone)TimeZone.CurrentTimeZone;
//                    offsetTicks = tz.GetUtcOffsetFromUniversalTime(new DateTime(ticks), ref isAmbiguousLocalDst);
//                }
//                ticks += offsetTicks;
//                // Another behaviour of parsing is to cause small times to wrap around, so that they can be used
//                // to compare times of day
//                if (ticks < 0) {
//                    ticks += TicksPerDay;
//                }
//                if (ticks < MinTicks || ticks > MaxTicks) {
//                    throw new IllegalArgumentException("Argument_DateTimeBadBinaryData"), "dateData");
//                }
//                return new DateTime(ticks, DateTimeKind.Local, isAmbiguousLocalDst);
//            }
//            else {
//                return DateTime.FromBinaryRaw(dateData);
//            }
//        }

	// A version of ToBinary that uses the real representation and does not adjust local times. This is needed for
	// scenarios where the serialized data must maintain compatability
	static DateTime fromBinaryRaw(long dateData)
	{
		long ticks = dateData & TicksMask;
		if (ticks < MinTicks || ticks > MaxTicks)
			throw new IllegalArgumentException("dateData");
		return dateTimeFromDateData(dateData);//JAVA-changed to fabric
	}

	/**
	 * Converts the specified Windows file time to an equivalent local time.
	 *
	 * Starting with the .NET Framework version 2.0, the return value is a DateTime
	 * whose Kind property is Local.
	 *
	 * A Windows filetime is a long representing the date and time as the number of
	 * 100-nanosecond intervals that have elapsed since 1/1/1601 12:00am.
	 */
	public static DateTime fromFileTime(long fileTime)
	{
		return fromFileTimeUtc(fileTime).toLocalTime();
	}

	/**
	 * Converts the specified Windows file time to an equivalent UTC time.
	 *
	 * Starting with the .NET Framework version 2.0, the return value is a DateTime
	 * whose Kind property is Utc.
	 */
	public static DateTime fromFileTimeUtc(long fileTime)
	{
		if (fileTime < 0L || fileTime > MaxTicks - FileTimeOffset) {
			throw new IllegalArgumentException("fileTime");
		}

		// This is the ticks in Universal time for this fileTime.
		long universalTicks = fileTime + FileTimeOffset;
		return new DateTime(universalTicks, DateTimeKind.UTC);
	}

	/**
	 * Creates a DateTime from an OLE Automation Date.
	 */
	public static DateTime fromOADate(double d)
	{
		return new DateTime(doubleDateToTicks(d), DateTimeKind.UNSPECIFIED);
	}

//	[SecurityPermissionAttribute(SecurityAction.LinkDemand, Flags=SecurityPermissionFlag.SerializationFormatter)]
//        void ISerializable.GetObjectData(SerializationInfo info, StreamingContext context) {
//            if (info==null) {
//                throw new NullPointerException("info");
//            }
//
//            // Serialize both the old and the new format
//            info.AddValue(TicksField, GetInternalTicks());
//            info.AddValue(DateDataField, dateData);
//        }

//        public boolean IsDaylightSavingTime() {
//            return TimeZone.CurrentTimeZone.IsDaylightSavingTime(this);
//        }

        public static DateTime specifyKind(DateTime value, int kind)//JAVA was: DateTimeKind enum
		{
            return new DateTime(value.getInternalTicks(), kind);
        }

        public static DateTime specifyKind(DateTime value, long kind)//JAVA was: DateTimeKind enum
		{
            return new DateTime(value.getInternalTicks(), kind);
        }

//        public Int64 ToBinary() {
//            if (Kind == DateTimeKind.Local) {
//                // Local times need to be adjusted as you move from one time zone to another,
//                // just as they are when serializing in text. As such the format for local times
//                // changes to store the ticks of the UTC time, but with flags that look like a
//                // local date.
//
//                // To match serialization in text we need to be able to handle cases where
//                // the UTC value would be out of range. Unused parts of the ticks range are
//                // used for this, so that values just past max value are stored just past the
//                // end of the maximum range, and values just below minimum value are stored
//                // at the end of the ticks area, just below 2^62.
//                TimeSpan offset = TimeZone.CurrentTimeZone.GetUtcOffset(this);
//                Int64 ticks = Ticks;
//                Int64 storedTicks = ticks - offset.Ticks;
//                if (storedTicks < 0) {
//                    storedTicks = TicksCeiling + storedTicks;
//                }
//                return storedTicks | (unchecked((Int64) LocalMask));
//            }
//            else {
//                return (Int64)dateData;
//            }
//        }

	// Return the underlying data, without adjust local times to the right time zone. Needed if performance
	// or compatability are important.
	long toBinaryRaw()
	{
		return dateData;
	}

	// Returns the date part of this DateTime. The resulting value
	// corresponds to this DateTime with the time-of-day part set to
	// zero (midnight).
	//

	public DateTime getDate()
	{
		long ticks = getInternalTicks();
		return dateTimeFromDateData((ticks - ticks%TicksPerDay) | getInternalKind());
	}

	// Returns a given date part of this DateTime. This method is used
	// to compute the year, day-of-year, month, or day part.
	private int getDatePart(int part)
	{
		long ticks = getInternalTicks();
		// n = number of days since 1/1/0001
		int n = (int)(ticks / TicksPerDay);
		// y400 = number of whole 400-year periods since 1/1/0001
		int y400 = n / DaysPer400Years;
		// n = day number within 400-year period
		n -= y400 * DaysPer400Years;
		// y100 = number of whole 100-year periods within 400-year period
		int y100 = n / DaysPer100Years;
		// Last 100-year period has an extra day, so decrement result if 4
		if (y100 == 4) y100 = 3;
		// n = day number within 100-year period
		n -= y100 * DaysPer100Years;
		// y4 = number of whole 4-year periods within 100-year period
		int y4 = n / DaysPer4Years;
		// n = day number within 4-year period
		n -= y4 * DaysPer4Years;
		// y1 = number of whole years within 4-year period
		int y1 = n / DaysPerYear;
		// Last year has an extra day, so decrement result if 4
		if (y1 == 4) y1 = 3;
		// If year was requested, compute and return it
		if (part == DatePartYear) {
			return y400 * 400 + y100 * 100 + y4 * 4 + y1 + 1;
		}
		// n = day number within year
		n -= y1 * DaysPerYear;
		// If day-of-year was requested, return it
		if (part == DatePartDayOfYear) return n + 1;
		// Leap year calculation looks different from IsLeapYear since y1, y4,
		// and y100 are relative to year 1, not year 0
		boolean leapYear = y1 == 3 && (y4 != 24 || y100 == 3);
		int[] days = leapYear? DaysToMonth366: DaysToMonth365;
		// All months have less than 32 days, so n >> 5 is a good conservative
		// estimate for the month
		int m = n >> 5 + 1;
		// m = 1-based month number
		while (n >= days[m]) m++;
		// If month was requested, return it
		if (part == DatePartMonth) return m;
		// Return 1-based day-of-month
		return n - days[m - 1] + 1;
	}

	// Returns the day-of-month part of this DateTime. The returned
	// value is an integer between 1 and 31.
	//
	public int getDay()
	{
		return getDatePart(DatePartDay);
	}

	// Returns the day-of-week part of this DateTime. The returned value
	// is an integer between 0 and 6, where 0 indicates Sunday, 1 indicates
	// Monday, 2 indicates Tuesday, 3 indicates Wednesday, 4 indicates
	// Thursday, 5 indicates Friday, and 6 indicates Saturday.
	//
	public int getDayOfWeek()//JAVA was: DayOfWeek enum
	{
		return (int) ((getInternalTicks()/TicksPerDay + 1L) % 7L);
	}

	// Returns the day-of-year part of this DateTime. The returned value
	// is an integer between 1 and 366.
	//
	public int getDayOfYear()
	{
		return getDatePart(DatePartDayOfYear);
	}

	// Returns the hour part of this DateTime. The returned value is an
	// integer between 0 and 23.
	//

	public int getHour()
	{
		return (int) ((getInternalTicks()/TicksPerHour) % 24L);
	}

	boolean isAmbiguousDaylightSavingTime()
	{
		return (getInternalKind() == KindLocalAmbiguousDst);
	}

	public long getKind()//JAVA was: DateTimeKind enum
	{
		long kind = getInternalKind();

		if (kind == KindUnspecified)
			return DateTimeKind.UNSPECIFIED;
		else if (kind == KindUtc)
			return DateTimeKind.UTC;
		else
			return DateTimeKind.LOCAL;
	}

	// Returns the millisecond part of this DateTime. The returned value
	// is an integer between 0 and 999.
	//
	public int getMillisecond()
	{
		return (int) ((getInternalTicks()/TicksPerMillisecond) % 1000L);
	}

	// Returns the minute part of this DateTime. The returned value is
	// an integer between 0 and 59.
	//
	public int getMinute()
	{
		return (int) ((getInternalTicks()/TicksPerMinute) % 60L);
	}

	// Returns the month part of this DateTime. The returned value is an
	// integer between 1 and 12.
	//
	public int getMonth()
	{
		return getDatePart(DatePartMonth);
	}

	// Returns a DateTime representing the current date and time. The
	// resolution of the returned value depends on the system timer. For
	// Windows NT 3.5 and later the timer resolution is approximately 10ms,
	// for Windows NT 3.1 it is approximately 16ms, and for Windows 95 and 98
	// it is approximately 55ms.
	//
	public static DateTime getNow()
	{
		return getUtcNow().toLocalTime();
	}

	public static DateTime getUtcNow()
	{
		////JAVA-changed
		long unixTicks = System.currentTimeMillis();
		long windowsTicks = fromJavaTicks(unixTicks);
		return dateTimeFromDateData(windowsTicks | KindUtc);
	}

	//JAVA-changed completelly
	static long getSystemTimeAsFileTime()
	{
		long unixTicksSince1970 = System.currentTimeMillis();//Unix epoch in unix ticks.
		long unixTicksSince1601 = unixTicksSince1970 + UNIX_TO_FILETIME_OFFSET;//Windows epoch.
		return unixTicksSince1601 * WINDOWS_TICKS_IN_UNIX_ONE;//at the last in windows ticks.
	}



	// Returns the second part of this DateTime. The returned value is
	// an integer between 0 and 59.
	//
	public int getSecond()
	{
		return (int) ((getInternalTicks()/TicksPerSecond)%60);
	}

	// Returns the tick count for this DateTime. The returned value is
	// the number of 100-nanosecond intervals that have elapsed since 1/1/0001
	// 12:00am.
	//

	public long getTicks()
	{
		return getInternalTicks();
	}

	// Returns the time-of-day part of this DateTime. The returned value
	// is a TimeSpan that indicates the time elapsed since midnight.
	//

	public TimeSpan getTimeOfDay()
	{
		return new TimeSpan(getInternalTicks()%TicksPerDay);
	}

	// Returns a DateTime representing the current date. The date part
	// of the returned value is the current date, and the time-of-day part of
	// the returned value is zero (midnight).
	//

	public static DateTime getToday()
	{
		return DateTime.getNow().getDate();
	}

	// Returns the year part of this DateTime. The returned value is an
	// integer between 1 and 9999.
	//

	public int getYear()
	{
		return getDatePart(DatePartYear);
	}

	// Checks whether a given year is a leap year. This method returns true if
	// year is a leap year, or false if not.
	//
	public static boolean isLeapYear(int year)
	{
		if (year < 1 || year > 9999) {
			throw new IllegalArgumentException("year");
		}
		return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
	}

//	// Constructs a DateTime from a String. The String must specify a
//	// date and optionally a time in a culture-specific or universal format.
//	// Leading and trailing whitespace characters are allowed.
//	//
//	public static DateTime Parse(String s)
//	{
//		return (DateTimeParse.Parse(s, DateTimeFormatInfo.CurrentInfo, DateTimeStyles.None));
//	}

//	// Constructs a DateTime from a String. The String must specify a
//	// date and optionally a time in a culture-specific or universal format.
//	// Leading and trailing whitespace characters are allowed.
//	//
//	public static DateTime Parse(String s, IFormatProvider provider) {
//		return (DateTimeParse.Parse(s, DateTimeFormatInfo.GetInstance(provider), DateTimeStyles.None));
//	}
//
//        public static DateTime Parse(String s, IFormatProvider provider, DateTimeStyles styles) {
//            DateTimeFormatInfo.ValidateStyles(styles, "styles");
//            return (DateTimeParse.Parse(s, DateTimeFormatInfo.GetInstance(provider), styles));
//        }
//
//	// Constructs a DateTime from a String. The String must specify a
//	// date and optionally a time in a culture-specific or universal format.
//	// Leading and trailing whitespace characters are allowed.
//	//
//	public static DateTime ParseExact(String s, String format, IFormatProvider provider) {
//		return (DateTimeParse.ParseExact(s, format, DateTimeFormatInfo.GetInstance(provider), DateTimeStyles.None));
//	}
//
//	// Constructs a DateTime from a String. The String must specify a
//	// date and optionally a time in a culture-specific or universal format.
//	// Leading and trailing whitespace characters are allowed.
//	//
//	public static DateTime ParseExact(String s, String format, IFormatProvider provider, DateTimeStyles style) {
//		DateTimeFormatInfo.ValidateStyles(style, "style");
//		return (DateTimeParse.ParseExact(s, format, DateTimeFormatInfo.GetInstance(provider), style));
//	}
//
//	public static DateTime ParseExact(String s, String[] formats, IFormatProvider provider, DateTimeStyles style) {
//		DateTimeFormatInfo.ValidateStyles(style, "style");
//		return DateTimeParse.ParseExactMultiple(s, formats, DateTimeFormatInfo.GetInstance(provider), style);
//	}

	public static TimeSpan subtract(DateTime d1, DateTime d2)
	{
		return new TimeSpan(d1.getInternalTicks() - d2.getInternalTicks());
	}

	public static DateTime subtract(DateTime d, TimeSpan t)
	{
		long ticks = d.getInternalTicks();
		long valueTicks = t._ticks;
		if (ticks - MinTicks < valueTicks || ticks - MaxTicks > valueTicks) {
			throw new IllegalArgumentException("t");
		}
		return dateTimeFromDateData((ticks - valueTicks) | d.getInternalKind());//JAVA-changed to fabric
	}

	// This function is duplicated in COMDateTime.cpp
	private static double ticksToOADate(long value)
	{
		if (value == 0)
			return 0;  // Returns OleAut's zero'ed date value.
		if (value < TicksPerDay) // This is a fix for VB. They want the default day to be 1/1/0001 rathar then 12/30/1899.
			value += DoubleDateOffset; // We could have moved this fix down but we would like to keep the bounds check.
		if (value < OADateMinAsTicks)
			throw new IllegalStateException("Arg_OleAutDateInvalid");
		// Currently, our max date == OA's max date (12/31/9999), so we don't
		// need an overflow check in that direction.
		long millis = (value  - DoubleDateOffset) / TicksPerMillisecond;
		if (millis < 0)
		{
			long frac = millis % MillisPerDay;
			if (frac != 0) millis -= (MillisPerDay + frac) * 2;
		}
		return (double) millis / MillisPerDay;
	}

	/**
	 * Converts the DateTime instance into Microsoft OLE Automation - OADate type
	 *
	 * Previous versions of the toOADate method returns double value without fraction part
	 * and can correctly represent only days
	 */
	public double toOADate()
	{
		return ticksToOADate(getInternalTicks());
	}

	/**
	 * A Windows file time is a 64-bit value that represents the number of 100-nanosecond intervals
	 * that have elapsed since 12:00 midnight, January 1, 1601 A.D. (C.E.) Coordinated Universal Time (UTC).
	 * Windows uses a file time to record when an application creates, accesses, or writes to a file.
	 *
	 * Previous versions of the ToFileTime method assume the current DateTime object is a local time.
	 * Starting with the.NET Framework version 2.0, the ToFileTime method uses the Kind property to
	 * determine whether the current DateTime object is a local time, a UTC time, or an unspecified kind
	 * of time which is treated as a local time.
	 */
	public long toFileTime()
	{
		// Treats the input as local if it is not specified
		return toUniversalTime().toFileTimeUtc();
	}

	/**
	 * A Windows file time is a 64-bit value that represents the number of 100-nanosecond intervals
	 * that have elapsed since 12:00 midnight, January 1, 1601 A.D. (C.E.) Coordinated Universal Time (UTC).
	 * Windows uses a file time to record when an application creates, accesses, or writes to a file.
	 *
	 * Previous versions of the ToFileTimeUtc method assume the current DateTime object is a UTC time.
	 * Starting with the.NET Framework version 2.0, the ToFileTimeUtc method uses the Kind property to
	 * determine whether the current DateTime object is a local time, a UTC time, or an unspecified kind
	 * of time which is treated as a UTC time.
	 */
	public long toFileTimeUtc()
	{
		// Treats the input as universal if it is not specified
		long ticks = ((getInternalKind() & LocalMask) != 0) ? toUniversalTime().getInternalTicks() : this.getInternalTicks();
		ticks -= FileTimeOffset;
		if (ticks < 0) {
			throw new IllegalArgumentException("ArgumentOutOfRange_FileTimeInvalid");
		}
		return ticks;
	}

	//JAVA-changed completely to avoid porting of .net TimeZones.
	public DateTime toLocalTime()
	{
		//JAVA-changed from:
		//return TimeZone.CurrentTimeZone.ToLocalTime(this);

		if (getInternalKind() == KindLocal)
			return this;

		long windowsTicks = getInternalTicks() + getTimeZoneOffset();

		if (windowsTicks > DateTime.MaxTicks)
			return new DateTime(DateTime.MaxTicks, DateTimeKind.LOCAL);
		if (windowsTicks < DateTime.MinTicks)
			return new DateTime(DateTime.MinTicks, DateTimeKind.LOCAL);

		return new DateTime(windowsTicks, DateTimeKind.LOCAL);
	}

	/**
	 * java.util.TimeZone used to calculate offset from local time to UTC.
	 * The method count DayLite saving time in the specified Date.
	 */
	protected int getJavaTimeZoneOffset(long javaTicks)
	{
		return CurrentThread.getTimeZone().getOffset(javaTicks);
	}

	//java.util.TimeZone used to calculate offset from local time to UTC
	public long getTimeZoneOffset()
	{
		long javaTicks = toJavaTicks();
		return getJavaTimeZoneOffset(javaTicks) * WINDOWS_TICKS_IN_UNIX_ONE;
	}

	/*
	 * JAVA-added.
	 * Converts this datetime ticks to unix (java) ones.
	 * 1. Windows tick = 100 nanosecond, Unix tick = 1 millisecond.
	 * 2. Windows (DateTime) epoch: 12:00 AM January 1, year 1 A.D. (0001-01-01 00:00:00),
	 *    Unix epoch: 1970-01-01 00:00:00.
	 */
	public long toJavaTicks()
	{
		return getInternalTicks() / WINDOWS_TICKS_IN_UNIX_ONE - UNIX_TO_DATETIME_OFFSET;
	}

    /*
	 * JAVA-added.
	 * Converts this datetime to java.lang.Date in GMT timezone.
	 *
	 * 1. Windows tick = 100 nanosecond, Unix tick = 1 millisecond.
	 * 2. Windows (DateTime) epoch: 12:00 AM January 1, year 1 A.D. (0001-01-01 00:00:00),
	 *    Unix epoch: 1970-01-01 00:00:00.
	 * 3. The Julian calendar day Thursday, 4 October 1582 was followed by the first day of the Gregorian calendar,
	 *    Friday, 15 October 1582. All dates between 05.10.1582 and 15.10.1582 inclusively are interpreted incorrectly
	 *    (or rather not as in .Net) by this method because java.lang.Date is used internally.
	 *    DateTime.MinValue corrected manually since its used widely in AW.
	 *
	 * see also TestDateTime.TestGregorianCutOver().
	 */
    public Date toJava()
    {
        //HACK: DateTime.MinValue used widely in AW as by-default value - let's substitute it
        //by hardcoded MinValue ticks so some unit test can pass.
        long unixTicks;
        if (DateTime.MinValue.equals(this))
        {
            unixTicks = DateTime.MinValueToUnixTicks;
        }
        else
        {
            if ( getInternalTicks() < JULIAN_LAST_DATE.getInternalTicks() )  // is date before 05.10.1582 ?
            {
                // The year duration in the Julian calendar is 365 + 1/4 days.
                // The year duration in the Gregorian calendar is 365 + 1/4 - 1/100 + 1/400.
                // So we should correct all dates before 05.10.1582 - last day of the Julian Calendar.
                int y = getDatePart(DatePartYear);
                int m = getDatePart(DatePartMonth);
                // Offset for date ranges:
                // from 01.01.01 inclusively to 01.03.100 exclusively is 0 days.
                // from 01.03.100 inclusively to 01.03.200 exclusively is 1 days.
                // from 01.03.200 inclusively to 01.03.300 exclusively is 2 days.
                // from 01.03.300 inclusively to 01.03.500 exclusively is 3 days.
                // from 01.03.500 inclusively to 01.03.600 exclusively is 4 days.
                // from 01.03.600 inclusively to 01.03.700 exclusively is 5 days.
                // from 01.03.700 inclusively to 01.03.900 exclusively is 6 days.
                // from 01.03.900 inclusively to 01.03.1000 exclusively is 7 days.
                // from 01.03.1000 inclusively to 01.03.1100 exclusively is 8 days.
                // from 01.03.1100 inclusively to 01.03.1300 exclusively is 9 days.
                // from 01.03.1300 inclusively to 01.03.1400 exclusively is 10 days.
                // from 01.03.1400 inclusively to 01.03.1500 exclusively is 11 days.
                // from 01.03.1500 inclusively to 05.10.1582 exclusively is 12 days.
                if (m < 3) // all days before 01.03.yyyy
                    --y;
                long ticks = (y / 100 - y / 400) * TicksPerDay;
                unixTicks = DateTime.MinValueToUnixTicks + (getInternalTicks() + ticks) / WINDOWS_TICKS_IN_UNIX_ONE;
            }
            else
            {
                unixTicks = this.toJavaTicks();
				if (getInternalKind() != KindUtc)
				{
					// The MSD and MSK time zones conflict and therefore the behavior of the Native DateTime.to Native()
					// method does not correspond to its counterpart in .NET. A small hack to fix this situation (see J2681).
					int javaTimeZoneOffset = correctTimeZoneOffset(unixTicks);

					unixTicks -= javaTimeZoneOffset;
				}
			}
        }
        return new Date(unixTicks);
    }

	//JAVA-added
	/**
	 * This static method should be used instead of dateTime.toJava() to avoid NullPointerException
	 * if dateTime == null. For instance, the method used by ApiChangeMap.
	 *
	 * Note: this method converts dateTime to UTC, instance method (above) - does not.
	 */
	public static Date toJava(DateTime dateTime)
	{
		if (dateTime == null)
			return null;

		return dateTime.toUniversalTime().toJava();
	}
	/*
	 * JAVA-added.
	 * Converts unix (java) ticks to datetime ones.
	 * 1. Windows tick = 100 nanosecond, Unix tick = 1 millisecond.
	 * 2. Windows (DateTime) epoch: 12:00 AM January 1, year 1 A.D. (0001-01-01 00:00:00),
	 *    Unix epoch: 1970-01-01 00:00:00.
	 */
	public static long fromJavaTicks(long unixTicks)
	{
		long unixTicksWithWindowsOffset = UNIX_TO_DATETIME_OFFSET + unixTicks;
		//JAVA: since dates before the Gregorian Calendar CutOver Date interpreted not as in .Net
		//we can get negative ticks count for 0001-01-01 00:00:00.
		if (unixTicksWithWindowsOffset < 0)
			unixTicksWithWindowsOffset = 0;

		return unixTicksWithWindowsOffset * WINDOWS_TICKS_IN_UNIX_ONE;
	}

	/*
	 * JAVA-added.
	 * Converts java.lang.Date to DateTime.
	 * 1. Windows tick = 100 nanosecond, Unix tick = 1 millisecond.
	 * 2. Windows (DateTime) epoch: 12:00 AM January 1, year 1 A.D. (0001-01-01 00:00:00),
	 *    Unix epoch: 1970-01-01 00:00:00.
	 * 3. NOTE: All dates before the Gregorian Calendar CutOver Date (October 15, 1582 in most western counties)
	 * interpreted incorrectly (or rather not as in .Net) by this method because java.lang.Date used internally.
	 *
	 * see also TestDateTime.TestGregorianCutOver().
	 */
	public static DateTime fromJava(Date javaDate)
	{
		// Note: java Date.getTime() returns UTC.
		// I don't know why but for most of tests we need in Local Time.
        DateTime dateTime = new DateTime(fromJavaTicks(javaDate.getTime()), DateTimeKind.UTC);
        return dateTime.toLocalTime();
	}

	public static DateTime[] fromJava(Date[] javaDates)
	{
        DateTime[] netDates = new DateTime[javaDates.length];
        for (int i = 0; i < javaDates.length; i++)
            netDates[i] = fromJava(javaDates[i]);

        return netDates;
	}

	public static DateTime fromJava(java.util.Calendar javaCalendarDate)
	{
		// WORDSJAVA-1097: One tick diff in chart DateTime axis value.
		// Calendar.getTimeInMillis() returns time as UTC milliseconds.
		// So we should add time zone offset to UTC milliseconds to receive local time. Otherwise, we loss information
		// about timezone and DateTime and Calendar will represent different timestamps. In particular,
		// getInternalTicks() will return wrong value.
		long msUtc = javaCalendarDate.getTimeInMillis();
		long offset = javaCalendarDate.getTimeZone().getOffset(msUtc);
		return new DateTime(fromJavaTicks(msUtc + offset), DateTimeKind.LOCAL);
	}

	public String toLongDateString()
    {
		return DateTimeFormat.format(this, "D",  msDateTimeFormatInfo.getCurrentInfo());
	}

	public String toLongTimeString()
    {
		return DateTimeFormat.format(this, "T",  msDateTimeFormatInfo.getCurrentInfo());
	}

	public String toShortDateString()
    {
		return DateTimeFormat.format(this, "d",   msDateTimeFormatInfo.getCurrentInfo());
	}

	public String toShortTimeString()
    {
		return DateTimeFormat.format(this, "t", msDateTimeFormatInfo.getCurrentInfo());
	}

	public java.lang.String toString()
	{
        try
        {
            return DateTimeFormat.format(this, null,  msDateTimeFormatInfo.getCurrentInfo());
        }
        catch (Exception e)
        {
			Log.warn(e);
        }
        return "DateTime wrong";
	}

	public java.lang.String toString(java.lang.String format)
	{
        return DateTimeFormat.format(this, format, msDateTimeFormatInfo.getCurrentInfo());
	}

    public String toString(String format, IFormatProvider provider)
    {
        return DateTimeFormat.format(this, format, msDateTimeFormatInfo.getInstance(provider));
    }

    public String toString(IFormatProvider provider)
    {
        return DateTimeFormat.format(this, null, msDateTimeFormatInfo.getInstance(provider));
    }

	//JAVA-changed completely to avoid porting of .net TimeZones.
	public DateTime toUniversalTime()
	{
		//JAVA-changed from:
		//return TimeZone.CurrentTimeZone.ToUniversalTime(this);

		if (getInternalKind() == KindUtc)
			return this;

		long windowsTicks = getInternalTicks() - getTimeZoneOffset();

		if (windowsTicks > DateTime.MaxTicks)
			return new DateTime(DateTime.MaxTicks, DateTimeKind.UTC);
		if (windowsTicks < DateTime.MinTicks)
			return new DateTime(DateTime.MinTicks, DateTimeKind.UTC);

		return new DateTime(windowsTicks, DateTimeKind.UTC);
	}

    public static DateTime parse(String s, IFormatProvider provider, int style)
    {
        return DateTimeParse.tryParse(s, msDateTimeFormatInfo.getInstance(provider), style);
    }

	public static DateTime parse(String s, msCultureInfo culture)
	{
		return DateTimeParse.tryParse(s, culture.getDateTimeFormat(), DateTimeStyles.NONE);
	}

	public static DateTime parse(String s)
	{
		return tryParse(s);
	}

	public static DateTime tryParse(String s)
    {
		return DateTimeParse.tryParse(s, msDateTimeFormatInfo.getCurrentInfo(), DateTimeStyles.NONE);
	}

    public static boolean tryParse(String s, IFormatProvider provider, /*DateTimeStyles*/int style, Ref<DateTime> referenceToResult)
    {
        referenceToResult.set(DateTimeParse.tryParse(s, msDateTimeFormatInfo.getInstance(provider), style));
        return !DateTime.equals(referenceToResult.get(), DateTime.MinValue);
    }

	public static boolean tryParseExact(String value, String format, IFormatProvider provider, /*DateTimeStyles*/int style, Ref<DateTime> referenceToResult)
	{
		return tryParseExact(value, new String[] {format}, provider, style, referenceToResult);
	}

	public static boolean tryParseExact(String value, String[] formats, IFormatProvider provider, /*DateTimeStyles*/int style, Ref<DateTime> referenceToResult)
    {
        referenceToResult.set(DateTimeParse.tryParseExactMultiple(value, formats, msDateTimeFormatInfo.getInstance(provider), style));

        return !DateTime.equals(referenceToResult.get(), DateTime.MinValue);
    }

    public static DateTime parseExact(String value, String format)
    {
        return DateTimeParse.tryParseExact(value, format);
    }

    public static DateTime parseExact(String value, String format, IFormatProvider provider)
    {
        return DateTimeParse.tryParseExact(value, format, msDateTimeFormatInfo.getInstance(provider));
    }

	public static DateTime parseExact(String value, String format, IFormatProvider provider, int adjustToUniversal)
	{
		return DateTimeParse.tryParseExact(value, format, msDateTimeFormatInfo.getInstance(provider), adjustToUniversal);
	}

    public static DateTime parseExact(String value, String[] formats, IFormatProvider provider, int adjustToUniversal)
    {
        return DateTimeParse.tryParseExactMultiple(value, formats, msDateTimeFormatInfo.getInstance(provider), adjustToUniversal);
    }

//        public static DateTime operator +(DateTime d, TimeSpan t) {
//            long ticks = d.GetInternalTicks();
//            long valueTicks = t._ticks;
//            if (valueTicks > MaxTicks - ticks || valueTicks < MinTicks - ticks) {
//                throw new IllegalArgumentException("t", "Overflow_DateArithmetic"));
//            }
//            return new DateTime((UInt64)(ticks + valueTicks) | d.GetInternalKind());
//        }

	public TimeSpan subtruct(DateTime dt)
	{
		return new TimeSpan(this.getInternalTicks() - dt.getInternalTicks());
	}

	public DateTime subtruct(TimeSpan t)
	{
		long ticks = this.getInternalTicks();
		long valueTicks = t._ticks;
		if (ticks - MinTicks < valueTicks || ticks - MaxTicks > valueTicks)
		{
			throw new IllegalArgumentException();
		}
		return dateTimeFromDateData((long)(ticks - valueTicks) | this.getInternalKind());
	}

	private int correctTimeZoneOffset(long unixTicks)
	{
		int javaTimeZoneOffset = getJavaTimeZoneOffset(unixTicks);

		if("Europe/Moscow".equals(CurrentThread.getTimeZone().getID()))
		{
			long unixTicksWithOffset = unixTicks - javaTimeZoneOffset;
			int newJavaTimeZoneOffset = getJavaTimeZoneOffset(unixTicksWithOffset);

			// A similar transition of the time zone occurs when the clocks are changed to summer and winter time,
			// as a result of which an extra hour is added or lost and a movement occurs to yesterday or tomorrow.
			// This is a condition for compensating for such a temporary jump.
			if(javaTimeZoneOffset == MSK_TIME_ZONE_OFFSET && newJavaTimeZoneOffset == MSD_TIME_ZONE_OFFSET)
			{
				javaTimeZoneOffset = MSD_TIME_ZONE_OFFSET;
			}
			else if(javaTimeZoneOffset == MSD_TIME_ZONE_OFFSET && newJavaTimeZoneOffset == MSK_TIME_ZONE_OFFSET)
			{
				javaTimeZoneOffset = MSK_TIME_ZONE_OFFSET;
			}
		}

		return javaTimeZoneOffset;
	}

//        public static DateTime operator -(DateTime d, TimeSpan t) {
//            long ticks = d.GetInternalTicks();
//            long valueTicks = t._ticks;
//            if (ticks - MinTicks < valueTicks || ticks - MaxTicks > valueTicks) {
//                throw new IllegalArgumentException("t", "Overflow_DateArithmetic"));
//            }
//            return new DateTime((UInt64)(ticks - valueTicks) | d.GetInternalKind());
//        }
//
//        public static TimeSpan operator -(DateTime d1, DateTime d2) {
//            return new TimeSpan(d1.GetInternalTicks() - d2.GetInternalTicks());
//        }
//
//        public static boolean operator ==(DateTime d1, DateTime d2) {
//            return d1.GetInternalTicks() == d2.GetInternalTicks();
//        }
//
//        public static boolean operator !=(DateTime d1, DateTime d2) {
//            return d1.GetInternalTicks() != d2.GetInternalTicks();
//        }
//
//        public static boolean operator <(DateTime t1, DateTime t2) {
//            return t1.GetInternalTicks() < t2.GetInternalTicks();
//        }
//
//        public static boolean operator <=(DateTime t1, DateTime t2) {
//            return t1.GetInternalTicks() <= t2.GetInternalTicks();
//        }
//
//        public static boolean operator >(DateTime t1, DateTime t2) {
//            return t1.GetInternalTicks() > t2.GetInternalTicks();
//        }
//
//        public static boolean operator >=(DateTime t1, DateTime t2) {
//            return t1.GetInternalTicks() >= t2.GetInternalTicks();
//        }


//        // Returns a String array containing all of the known date and time options for the
//        // current culture.  The strings returned are properly formatted date and
//        // time strings for the current instance of DateTime.
//        public String[] GetDateTimeFormats()
//        {
//            return (GetDateTimeFormats(CultureInfo.CurrentCulture));
//        }
//
//        // Returns a String array containing all of the known date and time options for the
//        // using the information provided by IFormatProvider.  The strings returned are properly formatted date and
//        // time strings for the current instance of DateTime.
//        public String[] GetDateTimeFormats(IFormatProvider provider)
//        {
//            return (DateTimeFormat.GetAllDateTimes(this, DateTimeFormatInfo.GetInstance(provider)));
//        }
//
//
//        // Returns a String array containing all of the date and time options for the
//        // given format format and current culture.  The strings returned are properly formatted date and
//        // time strings for the current instance of DateTime.
//        public String[] GetDateTimeFormats(char format)
//        {
//            return (GetDateTimeFormats(format, CultureInfo.CurrentCulture));
//        }
//
//        // Returns a String array containing all of the date and time options for the
//        // given format format and given culture.  The strings returned are properly formatted date and
//        // time strings for the current instance of DateTime.
//        public String[] GetDateTimeFormats(char format, IFormatProvider provider)
//        {
//            return (DateTimeFormat.GetAllDateTimes(this, format, DateTimeFormatInfo.GetInstance(provider)));
//        }


//	static boolean TryCreate(int year, int month, int day, int hour, int minute, int second, int millisecond, out DateTime result)
//	{
//		result = DateTime.MinValue;
//		if (year < 1 || year > 9999 || month < 1 || month > 12) {
//			return false;
//		}
//		int[] days = IsLeapYear(year) ? DaysToMonth366 : DaysToMonth365;
//		if (day < 1 || day > days[month] - days[month - 1]) {
//			return false;
//		}
//		if (hour < 0 || hour >= 24 || minute < 0 || minute >= 60 || second < 0 || second >= 60) {
//			return false;
//		}
//		if (millisecond < 0 || millisecond >= MillisPerSecond) {
//			return false;
//		}
//		long ticks = DateToTicks(year, month, day) + TimeToTicks(hour, minute, second);
//
//		ticks += millisecond * TicksPerMillisecond;
//		if (ticks < MinTicks || ticks > MaxTicks) {
//			return false;
//		}
//		result = new DateTime(ticks, DateTimeKind.Unspecified);
//		return true;
//	}

	//JAVA-added
	/**
	  * The difference between the Windows (FileTime) epoch (1601-01-01
	  * 00:00:00) and the Unix epoch (1970-01-01 00:00:00) in
	  * milliseconds: 11644473600000L.
	  */
	public static final long UNIX_TO_FILETIME_OFFSET = 11644473600000L;
	/**
	 * Ticks differ too:
	 * Windows tick = 100 nanosecond, Unix tick = 1 millisecond.
	 * So 1 Unix tick = 1000 * 10 Windows ticks.
	 */
	public static final long WINDOWS_TICKS_IN_UNIX_ONE = 10000L;
	/**
	 * The difference between the Windows (DateTime) epoch (12:00 AM January 1, year 1 A.D.)
	 * and the Unix epoch (1970-01-01 00:00:00) in milliseconds
	 */
	public static final long UNIX_TO_DATETIME_OFFSET = FileTimeOffset / WINDOWS_TICKS_IN_UNIX_ONE + UNIX_TO_FILETIME_OFFSET;

    private static DateTime JULIAN_LAST_DATE = new DateTime(1582, 10, 5);

	private static final int MSK_TIME_ZONE_OFFSET = 10800000;
	private static final int MSD_TIME_ZONE_OFFSET = 14400000;

}
