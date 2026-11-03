// A Java port, made by Aspose, of a .NET Framework class. Microsoft publishes the source of that class
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/datetimeoffset.cs
//
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

package com.aspose.ms.System;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Globalization.*;
import com.aspose.ms.System.Threading.CurrentThread;
import com.aspose.ms.java.logging.Log;


// DateTimeOffset is a value type that consists of a DateTime and a time zone offset, 
// ie. how far away the time is from GMT. The DateTime is stored whole, and the offset 
// is stored as an Int16 internally to save space, but presented as a TimeSpan.
// 
// The range is constrained so that both the represented clock time and the represented
// UTC time fit within the boundaries of MaxValue. This gives it the same range as DateTime
// for actual UTC times, and a slightly constrained range on one end when an offset is
// present. 
//
// This class should be substitutable for date time in most cases; so most operations 
// effectively work on the clock time. However, the underlying UTC time is what counts 
// for the purposes of identity, sorting and subtracting two instances.
// 
//
// There are theoretically two date times stored, the UTC and the relative local representation
// or the 'clock' time. It actually does not matter which is stored in m_dateTime, so it is desirable
// for most methods to go through the helpers UtcDateTime and ClockDateTime both to abstract this 
// out and for internal readability.

public /*struct*/ final class DateTimeOffset implements Comparable //, IFormattable, ISerializable, IDeserializationCallback, IComparable, IEquatable
{
	public DateTimeOffset(){}
	

    // Constants
    public static final long MAX_OFFSET = TimeSpan.TICKS_PER_HOUR * 14;
    public static final long MIN_OFFSET = -MAX_OFFSET;

    // Static Fields
    public static final DateTimeOffset MIN_VALUE = new DateTimeOffset(DateTime.MinTicks, TimeSpan.ZERO);
    public static final DateTimeOffset MAX_VALUE = new DateTimeOffset(DateTime.MaxTicks, TimeSpan.ZERO);

    // Instance Fields
    private DateTime m_dateTime;
    private short m_offsetMinutes;

    // Constructors 

    // Constructs a DateTimeOffset from a tick count and offset
    public DateTimeOffset(long ticks, TimeSpan offset)
    {
        m_offsetMinutes = validateOffset(offset);
        // Let the DateTime constructor do the range checks
        DateTime dateTime = new DateTime(ticks);
        m_dateTime = validateDate(dateTime, offset);
    }

    // Constructs a DateTimeOffset from a DateTime. For UTC and Unspecified kinds, creates a 
    // UTC instance with a zero offset. For local, extracts the local offset.
    public DateTimeOffset(DateTime dateTime)
    {
        TimeSpan offset;
        if (dateTime.getKind() != DateTimeKind.UTC)
        {
            // Local and Unspecified are both treated as Local
            //JAVA-changed:
            //offset = CurrentThreadSettings.getThreadTimeZoneOrDefault().getUtcOffset(dateTime);
            int unixOffset = CurrentThread.getTimeZone().getOffset(dateTime.toJavaTicks());
            offset = TimeSpan.fromJava(unixOffset);

        }
        else
        {
            offset = new TimeSpan(0);
        }
        m_offsetMinutes = validateOffset(offset);
        m_dateTime = validateDate(dateTime, offset);
    }

    // Constructs a DateTimeOffset from a DateTime. And an offset. Always makes the clock time 
    // consistent with the DateTime. For Utc ensures the offset is zero. For local, ensures that
    // the offset corresponds to the local. 
    public DateTimeOffset(DateTime dateTime, TimeSpan offset)
    {
        if (dateTime.getKind() == DateTimeKind.LOCAL)
        {
            //JAVA-changed:
            int unixOffset = CurrentThread.getTimeZone().getOffset(dateTime.toJavaTicks());
            TimeSpan systemOffset = TimeSpan.fromJava(unixOffset);
            if (!TimeSpan.equals(offset, systemOffset))
            {
                throw new IllegalArgumentException("Argument_OffsetLocalMismatch");
            }
        }
        else if (dateTime.getKind() == DateTimeKind.UTC)
        {
            if (!TimeSpan.equals(offset, TimeSpan.ZERO))
            {
                throw new IllegalArgumentException("Argument_OffsetUtcMismatch");
            }
        }
        m_offsetMinutes = validateOffset(offset);
        m_dateTime = validateDate(dateTime, offset);
    }

    // Constructs a DateTimeOffset from a given year, month, day, hour, 
    // minute, second and offset.
    public DateTimeOffset(int year, int month, int day, int hour, int minute, int second, TimeSpan offset)
    {
        m_offsetMinutes = validateOffset(offset);
        m_dateTime = validateDate(new DateTime(year, month, day, hour, minute, second), offset);
    }

    // Constructs a DateTimeOffset from a given year, month, day, hour,
    // minute, second, millsecond and offset
    public DateTimeOffset(int year, int month, int day, int hour, int minute, int second, int millisecond, TimeSpan offset)
    {
        m_offsetMinutes = validateOffset(offset);
        m_dateTime = validateDate(new DateTime(year, month, day, hour, minute, second, millisecond), offset);
    }

    // Constructs a DateTimeOffset from a given year, month, day, hour,
    // minute, second, millsecond, Calendar and offset.
    public DateTimeOffset(int year, int month, int day, int hour, int minute, int second, int millisecond, Calendar calendar, TimeSpan offset)
    {
        m_offsetMinutes = validateOffset(offset);
        m_dateTime = validateDate(new DateTime(year, month, day, hour, minute, second, millisecond, calendar), offset);
    }

    // Returns a DateTimeOffset representing the current date and time. The
    // resolution of the returned value depends on the system timer. For 
    // Windows NT 3.5 and later the timer resolution is approximately 10ms,
    // for Windows NT 3.1 it is approximately 16ms, and for Windows 95 and 98
    // it is approximately 55ms.
    // 
    public static DateTimeOffset getNow()
    {
        return new DateTimeOffset(DateTime.getNow());
    }

    public static DateTimeOffset getUtcNow()
    {
        return new DateTimeOffset(DateTime.getUtcNow());
    }

    public DateTime getDateTime()
    {
        return getClockDateTime();
    }

    public DateTime getUtcDateTime()
    {
        return DateTime.specifyKind(m_dateTime, (int) DateTimeKind.UTC);
    }

    public DateTime getLocalDateTime()
    {
        return getUtcDateTime().toLocalTime();
    }

    // Adjust to a given offset with the same UTC time.  Can throw ArgumentException
    // 
    public DateTimeOffset toOffset(TimeSpan offset)
    {
        return new DateTimeOffset((DateTime.add(m_dateTime, offset)).getTicks(), offset);
    }


    // Instance Properties 

    // The clock or visible time represented. This is just a wrapper around the internal date because this is
    // the chosen storage mechanism. Going through this helper is good for readability and maintainability. 
    // This should be used for display but not identity.
    private DateTime getClockDateTime()
    {
        return new DateTime((DateTime.add(m_dateTime, getOffset())).getTicks(), DateTimeKind.UNSPECIFIED);
    }

    // Returns the date part of this DateTimeOffset. The resulting value
    // corresponds to this DateTimeOffset with the time-of-day part set to 
    // zero (midnight).
    //
    public DateTime getDate()
    {
        return getClockDateTime().getDate();
    }

    // Returns the day-of-month part of this DateTimeOffset. The returned 
    // value is an integer between 1 and 31.
    //
    public int getDay()
    {
        return getClockDateTime().getDay();
    }

    // Returns the day-of-week part of this DateTimeOffset. The returned value 
    // is an integer between 0 and 6, where 0 indicates Sunday, 1 indicates
    // Monday, 2 indicates Tuesday, 3 indicates Wednesday, 4 indicates
    // Thursday, 5 indicates Friday, and 6 indicates Saturday.
    // 
    public /*DayOfWeek*/int getDayOfWeek()
    {
        return getClockDateTime().getDayOfWeek();
    }

    // Returns the day-of-year part of this DateTimeOffset. The returned value
    // is an integer between 1 and 366.
    // 
    public int getDayOfYear()
    {
        return getClockDateTime().getDayOfYear();
    }

    // Returns the hour part of this DateTimeOffset. The returned value is an
    // integer between 0 and 23.
    // 
    public int getHour()
    {
        return getClockDateTime().getHour();
    }


    // Returns the millisecond part of this DateTimeOffset. The returned value
    // is an integer between 0 and 999. 
    //
    public int getMillisecond()
    {
        return getClockDateTime().getMillisecond();
    }

    // Returns the minute part of this DateTimeOffset. The returned value is
    // an integer between 0 and 59. 
    //
    public int getMinute()
    {
        return getClockDateTime().getMinute();
    }

    // Returns the month part of this DateTimeOffset. The returned value is an
    // integer between 1 and 12. 
    //
    public int getMonth()
    {
        return getClockDateTime().getMonth();
    }

    public TimeSpan getOffset()
    {
        return new TimeSpan(0, m_offsetMinutes, 0);
    }

    // Returns the second part of this DateTimeOffset. The returned value is 
    // an integer between 0 and 59.
    //
    public int getSecond()
    {
        return getClockDateTime().getSecond();
    }

    // Returns the tick count for this DateTimeOffset. The returned value is 
    // the number of 100-nanosecond intervals that have elapsed since 1/1/0001
    // 12:00am.
    //
    public long getTicks()
    {
        return getClockDateTime().getTicks();
    }

    public long getUtcTicks()
    {
        return getUtcDateTime().getTicks();
    }

    // Returns the time-of-day part of this DateTimeOffset. The returned value 
    // is a TimeSpan that indicates the time elapsed since midnight.
    // 
    public TimeSpan getTimeOfDay()
    {
        return getClockDateTime().getTimeOfDay();
    }

    // Returns the year part of this DateTimeOffset. The returned value is an 
    // integer between 1 and 9999.
    // 
    public int getYear()
    {
        return getClockDateTime().getYear();
    }

    // Returns the DateTimeOffset resulting from adding the given 
    // TimeSpan to this DateTimeOffset.
    // 
    public DateTimeOffset add(TimeSpan timeSpan)
    {
        return new DateTimeOffset(getClockDateTime().add(timeSpan), getOffset());
    }

    // Returns the DateTimeOffset resulting from adding a fractional number of
    // days to this DateTimeOffset. The result is computed by rounding the 
    // fractional number of days given by value to the nearest 
    // millisecond, and adding that interval to this DateTimeOffset. The
    // value argument is permitted to be negative. 
    //
    public DateTimeOffset addDays(double days)
    {
        return new DateTimeOffset(getClockDateTime().addDays(days), getOffset());
    }

    // Returns the DateTimeOffset resulting from adding a fractional number of 
    // hours to this DateTimeOffset. The result is computed by rounding the 
    // fractional number of hours given by value to the nearest
    // millisecond, and adding that interval to this DateTimeOffset. The 
    // value argument is permitted to be negative.
    //
    public DateTimeOffset addHours(double hours)
    {
        return new DateTimeOffset(getClockDateTime().addHours(hours), getOffset());
    }

    // Returns the DateTimeOffset resulting from the given number of 
    // milliseconds to this DateTimeOffset. The result is computed by rounding
    // the number of milliseconds given by value to the nearest integer, 
    // and adding that interval to this DateTimeOffset. The value
    // argument is permitted to be negative.
    //
    public DateTimeOffset addMilliseconds(double milliseconds)
    {
        return new DateTimeOffset(getClockDateTime().addMilliseconds(milliseconds), getOffset());
    }

    // Returns the DateTimeOffset resulting from adding a fractional number of
    // minutes to this DateTimeOffset. The result is computed by rounding the 
    // fractional number of minutes given by value to the nearest
    // millisecond, and adding that interval to this DateTimeOffset. The
    // value argument is permitted to be negative.
    // 
    public DateTimeOffset addMinutes(double minutes)
    {
        return new DateTimeOffset(getClockDateTime().addMinutes(minutes), getOffset());
    }

    public DateTimeOffset addMonths(int months)
    {
        return new DateTimeOffset(getClockDateTime().addMonths(months), getOffset());
    }

    // Returns the DateTimeOffset resulting from adding a fractional number of 
    // seconds to this DateTimeOffset. The result is computed by rounding the
    // fractional number of seconds given by value to the nearest 
    // millisecond, and adding that interval to this DateTimeOffset. The 
    // value argument is permitted to be negative.
    // 
    public DateTimeOffset addSeconds(double seconds)
    {
        return new DateTimeOffset(getClockDateTime().addSeconds(seconds), getOffset());
    }

    // Returns the DateTimeOffset resulting from adding the given number of
    // 100-nanosecond ticks to this DateTimeOffset. The value argument 
    // is permitted to be negative. 
    //
    public DateTimeOffset addTicks(long ticks)
    {
        return new DateTimeOffset(getClockDateTime().addTicks(ticks), getOffset());
    }

    // Returns the DateTimeOffset resulting from adding the given number of 
    // years to this DateTimeOffset. The result is computed by incrementing
    // (or decrementing) the year part of this DateTimeOffset by value 
    // years. If the month and day of this DateTimeOffset is 2/29, and if the 
    // resulting year is not a leap year, the month and day of the resulting
    // DateTimeOffset becomes 2/28. Otherwise, the month, day, and time-of-day 
    // parts of the result are the same as those of this DateTimeOffset.
    //
    public DateTimeOffset addYears(int years)
    {
        return new DateTimeOffset(getClockDateTime().addYears(years), getOffset());
    }

    // Compares two DateTimeOffset values, returning an integer that indicates 
    // their relationship.
    // 
    public static int compare(DateTimeOffset first, DateTimeOffset second)
    {
        return DateTime.compare(first.getUtcDateTime(), second.getUtcDateTime());
    }

    // Compares this DateTimeOffset to a given object. This method provides an
    // implementation of the IComparable interface. The object 
    // argument must be another DateTimeOffset, or otherwise an exception 
    // occurs.  Null is considered less than any instance.
    // 
    public int /*IComparable.*/compareTo(Object obj)
    {
        if (obj == null)
            return 1;
        if (!(obj instanceof DateTimeOffset))
        {
            throw new IllegalArgumentException("Arg_MustBeDateTimeOffset");
        }

        DateTime objUtc = ((DateTimeOffset)obj).getUtcDateTime();
        DateTime utc = getUtcDateTime();
        if (DateTime.greater(utc, objUtc))
            return 1;
        if (DateTime.less(utc, objUtc))
            return -1;
        return 0;
    }

    public int compareTo(DateTimeOffset other)
    {
        DateTime otherUtc = other.getUtcDateTime();
        DateTime utc = getUtcDateTime();
        if (DateTime.greater(utc, otherUtc))
            return 1;
        if (DateTime.less(utc, otherUtc))
            return -1;
        return 0;
    }

    // Checks if this DateTimeOffset is equal to a given object. Returns
    // true if the given object is a boxed DateTimeOffset and its value 
    // is equal to the value of this DateTimeOffset. Returns false 
    // otherwise.
    // 
    public /*override*/ boolean equals(Object obj)
    {
        if (obj instanceof DateTimeOffset)
        {
            return getUtcDateTime().equals(((DateTimeOffset)obj).getUtcDateTime());
        }
        return false;
    }

    public boolean equals(DateTimeOffset other)
    {
        return getUtcDateTime().equals(other.getUtcDateTime());
    }

    public boolean equalsExact(DateTimeOffset other)
    {
        // 
        // returns true when the ClockDateTime, Kind, and Offset match
        // 
        // currently the Kind should always be Unspecified, but there is always the possibility that a future version 
        // of DateTimeOffset overloads the Kind field
        // 
        return (DateTime.equals(getClockDateTime(), other.getClockDateTime()) && TimeSpan.equals(getOffset(), other.getOffset()) && getClockDateTime().getKind() == other.getClockDateTime().getKind());
    }

    // Compares two DateTimeOffset values for equality. Returns true if 
    // the two DateTimeOffset values are equal, or false if they are
    // not equal. 
    // 
    public static boolean equals(DateTimeOffset first, DateTimeOffset second)
    {
        return DateTime.equals(first.getUtcDateTime(), second.getUtcDateTime());
    }

    // Creates a DateTimeOffset from a Windows filetime. A Windows filetime is
    // a long representing the date and time as the number of 
    // 100-nanosecond intervals that have elapsed since 1/1/1601 12:00am.
    // 
    public static DateTimeOffset fromFileTime(long fileTime)
    {
        return new DateTimeOffset(DateTime.fromFileTime(fileTime));
    }


    //JAVA-deleted .Net serialization:
    // ----- SECTION: private serialization instance methods  ----------------*
    /*
    public void *//*IDeserializationCallback.*//*onDeserialization(Object sender)
    {
        try
        {
            m_offsetMinutes = validateOffset(getOffset());
            m_dateTime = validateDate(getClockDateTime(), getOffset());
        }
        catch (IllegalArgumentException e)
        {
            throw new SerializationException("Serialization_InvalidData");
        }
    }


    @SecurityPermission (SecurityAction.LinkDemandFlags = SecurityPermissionFlag.SerializationFormatter)
    public void *//*ISerializable.*//*getObjectData(SerializationInfo info, StreamingContext context)
    {
        if (info == null)
        {
            throw new NullPointerException("info");
        }


        info.AddValue("DateTime", m_dateTime);
        info.AddValue("OffsetMinutes", m_offsetMinutes);
    }


    private DateTimeOffset(SerializationInfo info, StreamingContext context)
    {
        if (info == null)
        {
            throw new NullPointerException("info");
        }

        m_dateTime = (DateTime)info.GetValue("DateTime", DateTime.class);
        m_offsetMinutes = (*//**//*Short)info.GetValue("OffsetMinutes", short.class);
    }*/

    // Returns the hash code for this DateTimeOffset.
    //
    public /*override*/ int hashCode()
    {
        return getUtcDateTime().hashCode();
    }

    //JAVA-deleted: not ported since not used yet.
/*
    // Constructs a DateTimeOffset from a string. The string must specify a
    // date and optionally a time in a culture-specific or universal format.
    // Leading and trailing whitespace characters are allowed. 
    //
    public static DateTimeOffset parse(String input)
    {
        TimeSpan offset = null;
        TimeSpan[] referenceToOffset = { offset };
        DateTime dateResult = DateTimeParse.parse(input,
                                                  DateTimeFormatInfo.getCurrentInfo(),
                                                  DateTimeStyles.NONE,
                                                  *//*out*//* referenceToOffset);
        offset = referenceToOffset[0];
        return new DateTimeOffset(dateResult.getTicks(), offset);
    }

    // Constructs a DateTimeOffset from a string. The string must specify a
    // date and optionally a time in a culture-specific or universal format.
    // Leading and trailing whitespace characters are allowed. 
    //
    public static DateTimeOffset parse(String input, IFormatProvider formatProvider)
    {
        return parse(input, formatProvider, DateTimeStyles.NONE);
    }

    public static DateTimeOffset parse(String input, IFormatProvider formatProvider, DateTimeStyles styles)
    {
        styles = validateStyles(styles, "styles");
        TimeSpan offset = null;
        TimeSpan[] referenceToOffset = { offset };
        DateTime dateResult = DateTimeParse.Parse(input,
                                                  DateTimeFormatInfo.getInstance(formatProvider),
                                                  styles,
                                                  *//*out*//* referenceToOffset);
        offset = referenceToOffset[0];
        return new DateTimeOffset(dateResult.getTicks(), offset);
    }

    // Constructs a DateTimeOffset from a string. The string must specify a
    // date and optionally a time in a culture-specific or universal format.
    // Leading and trailing whitespace characters are allowed. 
    //
    public static DateTimeOffset parseExact(String input, String format, IFormatProvider formatProvider)
    {
        return parseExact(input, format, formatProvider, DateTimeStyles.NONE);
    }

    // Constructs a DateTimeOffset from a string. The string must specify a
    // date and optionally a time in a culture-specific or universal format.
    // Leading and trailing whitespace characters are allowed.
    // 
    public static DateTimeOffset parseExact(String input, String format, IFormatProvider formatProvider, DateTimeStyles styles)
    {
        styles = validateStyles(styles, "styles");
        TimeSpan offset = null;
        TimeSpan[] referenceToOffset = { offset };
        DateTime dateResult = DateTimeParse.parseExact(input,
                                                       format,
                                                       DateTimeFormatInfo.getInstance(formatProvider),
                                                       styles,
                                                       *//*out*//* referenceToOffset);
        offset = referenceToOffset[0];
        return new DateTimeOffset(dateResult.getTicks(), offset);
    }

    public static DateTimeOffset parseExact(String input, String[] formats, IFormatProvider formatProvider, DateTimeStyles styles)
    {
        styles = validateStyles(styles, "styles");
        TimeSpan offset = null;
        TimeSpan[] referenceToOffset = { offset };
        DateTime dateResult = DateTimeParse.parseExactMultiple(input,
                                                               formats,
                                                               DateTimeFormatInfo.getInstance(formatProvider),
                                                               styles,
                                                               *//*out*//* referenceToOffset);
        offset = referenceToOffset[0];
        return new DateTimeOffset(dateResult.getTicks(), offset);
    }*/

    public TimeSpan subtract(DateTimeOffset value)
    {
        return DateTime.subtract(getUtcDateTime(),value.getUtcDateTime());
    }

    public DateTimeOffset subtract(TimeSpan value)
    {
        return new DateTimeOffset(DateTime.subtract(getClockDateTime(),value), getOffset());
    }


    public long toFileTime()
    {
        return getUtcDateTime().toFileTime();
    }

    public DateTimeOffset toLocalTime()
    {
        return new DateTimeOffset(getUtcDateTime().toLocalTime());
    }

    public /*override*/ String toString()
    {
        try
        {
            return DateTimeFormat.format(getClockDateTime(), null, msDateTimeFormatInfo.getCurrentInfo(), getOffset());
        }
        catch (Exception e)
        {
            Log.error(e);
            return "Format Exception!";
        }
    }

    public String toString(String format) throws Exception
    {
        return DateTimeFormat.format(getClockDateTime(), format, msDateTimeFormatInfo.getCurrentInfo(), getOffset());
    }

/*
    public String toString(IFormatProvider formatProvider)
    {
        return DateTimeFormat.Format(getClockDateTime(), null, DateTimeFormatInfo.getInstance(formatProvider), getOffset());
    }

    public String toString(String format, IFormatProvider formatProvider)
    {
        return DateTimeFormat.Format(getClockDateTime(), format, DateTimeFormatInfo.getInstance(formatProvider), getOffset());
    }
*/

    public DateTimeOffset toUniversalTime()
    {
        return new DateTimeOffset(getUtcDateTime());
    }

//    public static boolean tryParse(String input, /*out*/ DateTimeOffset[] result)
//    {
//        TimeSpan offset = null;
//        DateTime dateResult = null;
//        boolean parsed = DateTimeParse.tryParse(input,
//                                                DateTimeFormatInfo.getCurrentInfo(),
//                                                DateTimeStyles.NONE,
//                                                /*out*/ dateResult,
//                                                /*out*/ offset);
//        result[0] = new DateTimeOffset(dateResult.getTicks(), offset);
//        return parsed;
//    }
//
//    public static boolean tryParse(String input, IFormatProvider formatProvider, DateTimeStyles styles, /*out*/ DateTimeOffset[] result)
//    {
//        styles = validateStyles(styles, "styles");
//        TimeSpan offset = null;
//        DateTime dateResult = null;
//        DateTime[] referenceToDateResult = { dateResult };
//        TimeSpan[] referenceToOffset = { offset };
//        boolean parsed = DateTimeParse.TryParse(input,
//                                                DateTimeFormatInfo.getInstance(formatProvider),
//                                                styles,
//                                                /*out*/ referenceToDateResult,
//                                                /*out*/ referenceToOffset);
//        dateResult = referenceToDateResult[0];
//        offset = referenceToOffset[0];
//        result[0] = new DateTimeOffset(dateResult.getTicks(), offset);
//        return parsed;
//    }
//
//    public static boolean tryParseExact(String input, String format, IFormatProvider formatProvider, DateTimeStyles styles,
//                                        /*out*/ DateTimeOffset[] result)
//    {
//        styles = validateStyles(styles, "styles");
//        TimeSpan offset = null;
//        DateTime dateResult = null;
//        DateTime[] referenceToDateResult = { dateResult };
//        TimeSpan[] referenceToOffset = { offset };
//        boolean parsed = DateTimeParse.TryParseExact(input,
//                                                     format,
//                                                     DateTimeFormatInfo.getInstance(formatProvider),
//                                                     styles,
//                                                     /*out*/ referenceToDateResult,
//                                                     /*out*/ referenceToOffset);
//        dateResult = referenceToDateResult[0];
//        offset = referenceToOffset[0];
//        result[0] = new DateTimeOffset(dateResult.getTicks(), offset);
//        return parsed;
//    }
//
//    public static boolean tryParseExact(String input, String[] formats, IFormatProvider formatProvider, DateTimeStyles styles,
//                                        /*out*/ DateTimeOffset[] result)
//    {
//        styles = validateStyles(styles, "styles");
//        TimeSpan offset = null;
//        DateTime dateResult = null;
//        DateTime[] referenceToDateResult = { dateResult };
//        TimeSpan[] referenceToOffset = { offset };
//        boolean parsed = DateTimeParse.TryParseExactMultiple(input,
//                                                             formats,
//                                                             DateTimeFormatInfo.getInstance(formatProvider),
//                                                             styles,
//                                                             /*out*/ referenceToDateResult,
//                                                             /*out*/ referenceToOffset);
//        dateResult = referenceToDateResult[0];
//        offset = referenceToOffset[0];
//        result[0] = new DateTimeOffset(dateResult.getTicks(), offset);
//        return parsed;
//    }

    // Ensures the TimeSpan is valid to go in a DateTimeOffset.
    private static short validateOffset(TimeSpan offset)
    {
        long ticks = offset.getTicks();
        if (ticks % TimeSpan.TICKS_PER_MINUTE != 0)
        {
            throw new IllegalArgumentException("Argument_OffsetPrecision");
        }
        if (ticks < MIN_OFFSET || ticks > MAX_OFFSET)
        {
            throw new IllegalArgumentException("Argument_OffsetOutOfRange");
        }
        return (short)(offset.getTicks() / TimeSpan.TICKS_PER_MINUTE);
    }

    // Ensures that the time and offset are in range.
    private static DateTime validateDate(DateTime dateTime, TimeSpan offset)
    {
        // The key validation is that both the UTC and clock times fit. The clock time is validated 
        // by the DateTime constructor.
        //BCLDebug.Assert(offset.getTicks() >= MIN_OFFSET && offset.getTicks() <= MAX_OFFSET, "Offset not validated.");
        // This operation cannot overflow because offset should have already been validated to be within
        // 14 hours and the DateTime instance is more than that distance from the boundaries of Int64.
        long utcTicks = dateTime.getTicks() - offset.getTicks();
        if (utcTicks < DateTime.MinTicks || utcTicks > DateTime.MaxTicks)
        {
            throw new IllegalArgumentException("Argument_UTCOutOfRange");
        }
        // make sure the Kind is set to Unspecified 
        //
        return new DateTime(utcTicks, DateTimeKind.UNSPECIFIED);
    }

//    private static /*DateTimeStyles*/int validateStyles(/*DateTimeStyles*/int style, String parameterName)
//    {
//        if ((style & DateTimeFormatInfo.InvalidDateTimeStyles) != 0)
//        {
//            throw new IllegalArgumentException("Argument_InvalidDateTimeStyles");
//        }
//        if (((style & (DateTimeStyles.ASSUME_LOCAL)) != 0) && ((style & (DateTimeStyles.ASSUME_UNIVERSAL)) != 0))
//        {
//            throw new IllegalArgumentException("Argument_ConflictingDateTimeStyles");
//        }
//        if ((style & DateTimeStyles.NO_CURRENT_DATE_DEFAULT) != 0)
//        {
//            throw new IllegalArgumentException("Argument_DateTimeOffsetInvalidDateTimeStyles");
//        }
//
//        // RoundtripKind does not make sense for DateTimeOffset; ignore this flag for backward compatability with DateTime
//        style &= ~DateTimeStyles.ROUNDTRIP_KIND;
//
//        // AssumeLocal is also ignored as that is what we do by default with DateTimeOffset.Parse
//        style &= ~DateTimeStyles.ASSUME_LOCAL;
//
//        return style;
//    }

    // Operators

    public static /*implicit*/ /*operator*/ DateTimeOffset op_Implicit_DateTimeOffset(DateTime dateTime)
    {
        return new DateTimeOffset(dateTime);
    }

    public static DateTimeOffset op_Addition(DateTimeOffset dateTimeTz, TimeSpan timeSpan)
    {
        return new DateTimeOffset(DateTime.add(dateTimeTz.getClockDateTime(), timeSpan), dateTimeTz.getOffset());
    }

    public static DateTimeOffset op_Subtraction(DateTimeOffset dateTimeTz, TimeSpan timeSpan)
    {
        return new DateTimeOffset(DateTime.subtract(dateTimeTz.getClockDateTime(), timeSpan), dateTimeTz.getOffset());
    }

    public static TimeSpan op_Subtraction(DateTimeOffset left, DateTimeOffset right)
    {
        return DateTime.subtract(left.getUtcDateTime(), right.getUtcDateTime());
    }

    public static boolean op_Equality(DateTimeOffset left, DateTimeOffset right)
    {
        return DateTime.equals(left.getUtcDateTime(), right.getUtcDateTime());
    }

    public static boolean op_Inequality(DateTimeOffset left, DateTimeOffset right)
    {
        return !DateTime.equals(left.getUtcDateTime(), right.getUtcDateTime());
    }

    public static boolean op_LessThan(DateTimeOffset left, DateTimeOffset right)
    {
        return DateTime.less(left.getUtcDateTime(), right.getUtcDateTime());
    }

    public static boolean op_LessThanOrEqual(DateTimeOffset left, DateTimeOffset right)
    {
        return DateTime.lessOrEqual(left.getUtcDateTime(), right.getUtcDateTime());
    }

    public static boolean op_GreaterThan(DateTimeOffset left, DateTimeOffset right)
    {
        return DateTime.greater(left.getUtcDateTime(), right.getUtcDateTime());
    }

    public static boolean op_GreaterThanOrEqual(DateTimeOffset left, DateTimeOffset right)
    {
        return DateTime.greaterOrEqual(left.getUtcDateTime(), right.getUtcDateTime());
    }
}
