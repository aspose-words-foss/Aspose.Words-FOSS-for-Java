// A Java port, made by Aspose, of a .NET Framework class. Microsoft publishes the source of that class
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/timespan.cs
//
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

/**
* Helper Java code to port Aspose.Word C# source, 23/10/2007
*
* @author Konstantin Sidorenko
*/

package com.aspose.ms.System;

import com.aspose.ms.java.util.JavaUtil;

import java.lang.*;

/**
* .Net System.TimeSpan stolen from Rotor.
*/
// TimeSpan represents a duration of time.  A TimeSpan can be negative
// or positive.
//
// TimeSpan is internally represented as a number of milliseconds.  While
// this maps well into units of time such as hours and days, any
// periods longer than that aren't representable in a nice fashion.
// For instance, a month can be between 28 and 31 days, while a year
// can contain 365 or 364 days.  A decade can have between 1 and 3 leapyears,
// depending on when you map the TimeSpan into the calendar.  This is why
// we do not provide Years() or Months().
//
public final class TimeSpan implements Comparable<Object>//JAVA was: struct
{

	public static final long TICKS_PER_MILLISECOND =  10000;
	private static final double MILLISECONDS_PER_TICK = 1.0 / TICKS_PER_MILLISECOND;

	public static final long TICKS_PER_SECOND = TICKS_PER_MILLISECOND * 1000;
	private static final double SECONDS_PER_TICK =  1.0 / TICKS_PER_SECOND;

	public static final long TICKS_PER_MINUTE = TICKS_PER_SECOND * 60;
	private static final double MINUTES_PER_TICK = 1.0 / TICKS_PER_MINUTE;

	public static final long TICKS_PER_HOUR = TICKS_PER_MINUTE * 60;
	private static final double HOURS_PER_TICK = 1.0 / TICKS_PER_HOUR;

	public static final long TICKS_PER_DAY = TICKS_PER_HOUR * 24;
	private static final double DAYS_PER_TICK = 1.0 / TICKS_PER_DAY;

	private static final int MILLIS_PER_SECOND = 1000;
	private static final int MILLIS_PER_MINUTE = MILLIS_PER_SECOND * 60;
	private static final int MILLIS_PER_HOUR = MILLIS_PER_MINUTE * 60;
	private static final int MILLIS_PER_DAY = MILLIS_PER_HOUR * 24;

	private static final long MAX_SECONDS = Long.MAX_VALUE / TICKS_PER_SECOND;
	private static final long MIN_SECONDS = Long.MIN_VALUE / TICKS_PER_SECOND;

	private static final long MAX_MILLI_SECONDS = Long.MAX_VALUE / TICKS_PER_MILLISECOND;
	private static final long MIN_MILLI_SECONDS = Long.MIN_VALUE / TICKS_PER_MILLISECOND;


	public static final TimeSpan ZERO = new TimeSpan(0L);

	public static final TimeSpan MAX_VALUE = new TimeSpan(Long.MAX_VALUE);
	public static final TimeSpan MIN_VALUE = new TimeSpan(Long.MIN_VALUE);

	// so that DateTime doesn't have to call an extra get
	// method for some arithmetic operations.
	long _ticks;

	public TimeSpan() {
	    _ticks = 0;
	}

	public TimeSpan(long ticks)
	{
		this._ticks = ticks;
	}

	public TimeSpan(int hours, int minutes, int seconds)
	{
		_ticks = timeToTicks(hours, minutes, seconds);
	}

	public TimeSpan(int days, int hours, int minutes, int seconds)
	{
		this(days,hours,minutes,seconds,0);
	}

	public TimeSpan(int days, int hours, int minutes, int seconds, int milliseconds)
	{
		long totalMilliSeconds = ((long)days * 3600 * 24 + (long)hours * 3600 + (long)minutes * 60 + seconds) * 1000 + milliseconds;
		if (totalMilliSeconds > MAX_MILLI_SECONDS || totalMilliSeconds < MIN_MILLI_SECONDS)
			throw new IndexOutOfBoundsException("Overflow_TimeSpanTooLong");
		_ticks =  (long)totalMilliSeconds * TICKS_PER_MILLISECOND;
	}

	public long getTicks()
	{
		return _ticks;
	}

	public int getDays()
	{
		return (int) (_ticks/ TICKS_PER_DAY);
	}

	public int getHours()
	{
		return (int) ((_ticks/ TICKS_PER_HOUR) % 24);
	}

	public long getMilliseconds()
	{
		return _ticks/ TICKS_PER_MILLISECOND;
	}

	public int getMinutes()
	{
		return (int) ((_ticks/ TICKS_PER_MINUTE)%60);
	}

	public int getSeconds()
	{
		return (int) ((_ticks/ TICKS_PER_SECOND)%60);
	}

	public double getTotalDays()
	{
		return ((double)_ticks) * DAYS_PER_TICK;
	}

	public double getTotalHours()
	{
		return (double) _ticks* HOURS_PER_TICK;
	}

	public double getTotalMilliseconds()
	{
		double temp = (double) _ticks* MILLISECONDS_PER_TICK;
		if (temp > MAX_MILLI_SECONDS)
			return (double) MAX_MILLI_SECONDS;

		if (temp < MIN_MILLI_SECONDS)
			return (double) MIN_MILLI_SECONDS;

		return temp;
	}

	public double getTotalMinutes()
	{
		return (double) _ticks* MINUTES_PER_TICK;
	}

	public double getTotalSeconds()
	{
		return (double) _ticks* SECONDS_PER_TICK;
	}

	public TimeSpan add(TimeSpan ts)
	{
		long result = _ticks + ts._ticks;
		// Overflow if signs of operands was identical and result's
		// sign was opposite.
		// >> 63 gives the sign bit (either 64 1's or 64 0's).
		if ((_ticks >> 63 == ts._ticks >> 63) && (_ticks >> 63 != result >> 63))
			throw new IllegalStateException("Overflow_TimeSpanTooLong");
		return new TimeSpan(result);
	}

	public static TimeSpan add(TimeSpan t1, TimeSpan t2)
	{
		return t1 != null && t2 != null ? t1.add(t2) : null;
	}


	// Compares two TimeSpan values, returning an integer that indicates their
	// relationship.
	//
	public static int compare(TimeSpan t1, TimeSpan t2)
	{
		if (t1._ticks > t2._ticks)
			return 1;
		if (t1._ticks < t2._ticks)
			return -1;
		return 0;
	}

	// Returns a value less than zero if this  Object
	public int compareTo(Object value)
	{
		if (value == null)
			return 1;
		if (!(value instanceof TimeSpan))
			throw new IllegalArgumentException("Arg_MustBeTimeSpan");
		long t = ((TimeSpan)value)._ticks;
		if (_ticks > t)
			return 1;
		if (_ticks < t)
			return -1;
		return 0;
	}

	public int compareTo(TimeSpan value)
	{
		long t = value._ticks;
		if (_ticks > t)
			return 1;
		if (_ticks < t)
			return -1;
		return 0;
	}

	public static TimeSpan fromDays(double value)
	{
		return interval(value, MILLIS_PER_DAY);
	}

	public TimeSpan duration()
	{
		if (_ticks==TimeSpan.MIN_VALUE._ticks)
			throw new IllegalStateException("Overflow_Duration");
		return new TimeSpan(_ticks >= 0L ? _ticks : -_ticks);
	}

    // This is needed because java equals is overridden.
    // Implementation by Rotor.
    public int hashCode()
    {
        return (int)_ticks ^ (int)(_ticks >> 32);
    }

    // This overrides java equals.
    // Implementation by RK.
	public boolean equals(Object value)
	{
		return (value instanceof TimeSpan) && equals(this, (TimeSpan)value);
	}

    // This is called by the autoporter when it encounters == in C#. It is the actual implementation of equality.
    // Implementation by RK.
    public static boolean equals(TimeSpan a, TimeSpan b)
    {
        if (a == b)
            return true;

        return ((a != null) && (b != null) &&
            (a._ticks == b._ticks));
    }

	public static TimeSpan fromHours(double value)
	{
		return interval(value, MILLIS_PER_HOUR);
	}

	private static TimeSpan interval(double value, int scale)
	{
		if (Double.isNaN(value))
			throw new IllegalArgumentException("Arg_CannotBeNaN");
		double tmp = value * scale;
		double millis = tmp + (value >= 0 ? 0.5 : -0.5);
		if ((millis > Long.MAX_VALUE / TICKS_PER_MILLISECOND) || (millis < Long.MIN_VALUE / TICKS_PER_MILLISECOND))
			throw new IllegalStateException("Overflow_TimeSpanTooLong");
		return new TimeSpan((long)millis * TICKS_PER_MILLISECOND);
	}

	public static TimeSpan fromMilliseconds(double value)
	{
		return interval(value, 1);
	}

	public static TimeSpan fromMinutes(double value)
	{
		return interval(value, MILLIS_PER_MINUTE);
	}

	public TimeSpan negate()
	{
		if (_ticks==TimeSpan.MIN_VALUE._ticks)
			throw new IllegalStateException("Overflow_NegateTwosCompNum");
		return new TimeSpan(-_ticks);
	}

//	// Constructs a TimeSpan from a String.  Leading and trailing white
//	// space characters are allowed.
//	//
//	public static TimeSpan Parse(String s)
//	{
//		return new TimeSpan(new StringParser().Parse(s));
//	}
//
//	public static boolean TryParse(String s, out TimeSpan result)
//	{
//		long longResult;
//		if (new StringParser().TryParse(s, out longResult))
//		{
//			result = new TimeSpan(longResult);
//			return true;
//		}
//		else
//		{
//			result = TimeSpan.Zero;
//			return false;
//		}
//	}

	public static TimeSpan fromSeconds(double value)
	{
		return interval(value, MILLIS_PER_SECOND);
	}

	public TimeSpan subtract(TimeSpan t)
	{
		return subtract(this, t);
	}
	public static TimeSpan subtract(TimeSpan t1, TimeSpan t2)
	{
		long result = t1._ticks - t2._ticks;
		// Overflow if signs of operands was different and result's
		// sign was opposite from the first argument's sign.
		// >> 63 gives the sign bit (either 64 1's or 64 0's).
		if ((t1._ticks >> 63 != t2._ticks >> 63) && (t1._ticks >> 63 != result >> 63))
			throw new IllegalStateException("Overflow_TimeSpanTooLong");
		return new TimeSpan(result);
	}

	public static TimeSpan fromTicks(long value) {
		return new TimeSpan(value);
	}

	public static long timeToTicks(int hour, int minute, int second)
	{
		// totalSeconds is bounded by 2^31 * 2^12 + 2^31 * 2^8 + 2^31,
		// which is less than 2^44, meaning we won't overflow totalSeconds.
		long totalSeconds = (long)hour * 3600 + (long)minute * 60 + (long)second;
		if (totalSeconds > MAX_SECONDS || totalSeconds < MIN_SECONDS)
			throw new IndexOutOfBoundsException("Overflow_TimeSpanTooLong");
		return totalSeconds * TICKS_PER_SECOND;
	}

	private java.lang.String intToString(int n, int digits)
	{
		//JAVA-changed from:
		//return ParseNumbers.IntToString(n, 10, digits, '0', 0);
		return JavaUtil.intToString(n, digits);
	}

	public java.lang.String toString()
	{
		java.lang.StringBuilder sb = new java.lang.StringBuilder();
		int day = (int)(_ticks / TICKS_PER_DAY);
		long time = _ticks % TICKS_PER_DAY;
		if (_ticks < 0) {
			sb.append("-");
			day = -day;
			time = -time;
		}
		if (day != 0) {
			sb.append(day);
			sb.append(".");
		}
		sb.append(intToString((int)(time / TICKS_PER_HOUR % 24), 2));
		sb.append(":");
		sb.append(intToString((int)(time / TICKS_PER_MINUTE % 60), 2));
		sb.append(":");
		sb.append(intToString((int)(time / TICKS_PER_SECOND % 60), 2));
		int t = (int)(time % TICKS_PER_SECOND);
		if (t != 0) {
			sb.append(".");
			sb.append(intToString(t, 7));
		}
		return sb.toString();
	}

//        public static TimeSpan operator -(TimeSpan t) {
//            if (t._ticks==TimeSpan.MinValue._ticks)
//                throw new OverflowException(Environment.GetResourceString("Overflow_NegateTwosCompNum"));
//            return new TimeSpan(-t._ticks);
//        }
//
//        public static TimeSpan operator -(TimeSpan t1, TimeSpan t2) {
//            return t1.Subtract(t2);
//        }
//
//        public static TimeSpan operator +(TimeSpan t) {
//            return t;
//        }
//
//        public static TimeSpan operator +(TimeSpan t1, TimeSpan t2) {
//            return t1.Add(t2);
//        }
//
//        public static boolean operator ==(TimeSpan t1, TimeSpan t2) {
//            return t1._ticks == t2._ticks;
//        }
//
//        public static boolean operator !=(TimeSpan t1, TimeSpan t2) {
//            return t1._ticks != t2._ticks;
//        }

        public static boolean less(TimeSpan t1, TimeSpan t2)
		{
            return t1._ticks < t2._ticks;
        }

//        public static boolean operator <=(TimeSpan t1, TimeSpan t2) {
//            return t1._ticks <= t2._ticks;
//        }
//
//        public static boolean operator >(TimeSpan t1, TimeSpan t2) {
//            return t1._ticks > t2._ticks;
//        }
//
//        public static boolean operator >=(TimeSpan t1, TimeSpan t2) {
//            return t1._ticks >= t2._ticks;
//        }

//	private class StringParser
//	{
//
//		private final class ParseError
//		{
//			public static final int Format = 1;
//			public static final int Overflow = 2;
//			public static final int OverflowHoursMinutesSeconds = 3;
//			public static final int ArgumentNull = 4;
//		}
//
//		private java.lang.String str;
//		private char ch;
//		private int pos;
//		private int len;
//		private int error;//JAVA was: ParseError enum
//
//		void NextChar()
//		{
//			if (pos < len) pos++;
//			ch = pos < len? str.charAt(pos) : (char) 0;
//		}
//
//		char NextNonDigit()
//		{
//			int i = pos;
//			while (i < len)
//			{
//				char ch = str.charAt(i);
//				if (ch < '0' || ch > '9')
//					return ch;
//				i++;
//			}
//			return (char) 0;
//		}
//
//		long Parse(java.lang.String s)
//		{
//			long value;
//			if (TryParse(s, out value))
//			{
//				return value;
//			}
//			else
//			{
//				switch (error) {
//					case ParseError.ArgumentNull:
//						throw new NullPointerException("s");
//					case ParseError.Format:
//						throw new IllegalStateException("Format_InvalidString");
//					case ParseError.Overflow:
//						throw new IllegalStateException("Overflow_TimeSpanTooLong");
//					case ParseError.OverflowHoursMinutesSeconds:
//						throw new IllegalStateException("Overflow_TimeSpanElementTooLarge");
//					default:
//						assert false : "Unknown error: " + error;
//						return 0;
//				}
//			}
//		}
//
//		boolean TryParse(String s, out long value)
//		{
//			value = 0;
//			if (s == null) {
//				error = ParseError.ArgumentNull;
//				return false;
//			}
//			str = s;
//			len = s.Length;
//			pos = -1;
//			NextChar();
//			SkipBlanks();
//			boolean negative = false;
//			if (ch == '-') {
//				negative = true;
//				NextChar();
//			}
//			long time;
//			if (NextNonDigit() == ':') {
//				if (!ParseTime(out time)) {
//					return false;
//				};
//			}
//			else {
//				int days;
//				if (!ParseInt((int)(0x7FFFFFFFFFFFFFFFL / TicksPerDay), out days)) {
//					return false;
//				}
//				time = days * TicksPerDay;
//				if (ch == '.') {
//					NextChar();
//					long remainingTime;
//					if (!ParseTime(out remainingTime)) {
//						return false;
//					};
//					time += remainingTime;
//				}
//			}
//			if (negative) {
//				time = -time;
//				// Allow -0 as well
//				if (time > 0) {
//					error = ParseError.Overflow;
//					return false;
//				}
//			}
//			else {
//				if (time < 0) {
//					error = ParseError.Overflow;
//					return false;
//				}
//			}
//			SkipBlanks();
//			if (pos < len) {
//				error = ParseError.Format;
//				return false;
//			}
//			value = time;
//			return true;
//		}
//
//		boolean ParseInt(int max, out int i)
//		{
//			i = 0;
//			int p = pos;
//			while (ch >= '0' && ch <= '9') {
//				if ((i & 0xF0000000) != 0) {
//					error = ParseError.Overflow;
//					return false;
//				}
//				i = i * 10 + ch - '0';
//				if (i < 0) {
//					error = ParseError.Overflow;
//					return false;
//				}
//				NextChar();
//			}
//			if (p == pos) {
//				error = ParseError.Format;
//				return false;
//			}
//			if (i > max) {
//				error = ParseError.Overflow;
//				return false;
//			}
//			return true;
//		}
//
//		boolean ParseTime(out long time)
//		{
//			time = 0;
//			int unit;
//			if (!ParseInt(23, out unit)) {
//				if (error == ParseError.Overflow) {
//					error = ParseError.OverflowHoursMinutesSeconds;
//				}
//				return false;
//			}
//			time = unit * TicksPerHour;
//			if (ch != ':') {
//				error = ParseError.Format;
//				return false;
//			}
//			NextChar();
//			if (!ParseInt(59, out unit)) {
//				if (error == ParseError.Overflow) {
//					error = ParseError.OverflowHoursMinutesSeconds;
//				}
//				return false;
//			}
//			time += unit * TicksPerMinute;
//			if (ch == ':') {
//				NextChar();
//				// allow seconds with the leading zero
//				if (ch != '.') {
//					if (!ParseInt(59, out unit)) {
//						if (error == ParseError.Overflow) {
//							error = ParseError.OverflowHoursMinutesSeconds;
//						}
//						return false;
//					}
//					time += unit * TicksPerSecond;
//				}
//				if (ch == '.') {
//					NextChar();
//					int f = (int)TicksPerSecond;
//					while (f > 1 && ch >= '0' && ch <= '9') {
//						f /= 10;
//						time += (ch - '0') * f;
//						NextChar();
//					}
//				}
//			}
//			return true;
//		}
//
//		void SkipBlanks() {
//			while (ch == ' ' || ch == '\t') NextChar();
//		}
//	}

	//JAVA-added

	/*
	 * Converts unux (java) ticks to windows ones.
	 * 1. Windows tick = 100 nanosecond, Unix tick = 1 millisecond.
	 * 2. epoch diff doesn't taken into account because we need here only time span.
	 */
	public static long fromUnixTicks(long unixTicks)
	{
		return unixTicks * WINDOWS_TICKS_IN_UNIX_ONE;
	}

    // Core java hasn't analog of TimeSpan. Only external libraries like JodaTime has the analog.
    // Let's use just milliseconds for TimeSpan for Java public API.
	public static TimeSpan fromJava(long milliseconds)
	{
		return new TimeSpan(TimeSpan.fromUnixTicks(milliseconds));
	}

	public static long toJava(TimeSpan timeSpan)
	{
		return timeSpan.getMilliseconds();
	}

	/**
	 * Ticks differ too:
	 * Windows tick = 100 nanosecond, Unix tick = 1 millisecond.
	 * So 1 Unix tick = 1000 * 10 Windows ticks.
	 */
	private static final long WINDOWS_TICKS_IN_UNIX_ONE = 10000L;
}
