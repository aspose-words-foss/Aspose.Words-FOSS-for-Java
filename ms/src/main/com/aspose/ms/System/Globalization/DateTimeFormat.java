// A Java port, made by Aspose, of a .NET Framework class. Microsoft publishes the source of that class
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/globalization/datetimeformat.cs
//
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
//23/08/12 by Vyacheslav Durin

package com.aspose.ms.System.Globalization;

import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.DateTimeKind;
import com.aspose.ms.System.TimeSpan;
import com.aspose.ms.java.debug.JavaDebug;
import com.aspose.ms.java.logging.Log;
import com.aspose.ms.java.text.NumberFormatProvider;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

/*
     Customized format patterns:
     P.S. Format in the table below is the internal number format used to display the pattern.

     Patterns   Format      Description                           Example
     =========  ==========  ===================================== ========
        "h"     "0"         hour (12-hour clock)w/o leading zero  3
        "hh"    "00"        hour (12-hour clock)with leading zero 03
        "hh*"   "00"        hour (12-hour clock)with leading zero 03

        "H"     "0"         hour (24-hour clock)w/o leading zero  8
        "HH"    "00"        hour (24-hour clock)with leading zero 08
        "HH*"   "00"        hour (24-hour clock)                  08

        "m"     "0"         minute w/o leading zero
        "mm"    "00"        minute with leading zero
        "mm*"   "00"        minute with leading zero

        "s"     "0"         second w/o leading zero
        "ss"    "00"        second with leading zero
        "ss*"   "00"        second with leading zero

        "f"     "0"         second fraction (1 digit)
        "ff"    "00"        second fraction (2 digit)
        "fff"   "000"       second fraction (3 digit)
        "ffff"  "0000"      second fraction (4 digit)
        "fffff" "00000"         second fraction (5 digit)
        "ffffff"    "000000"    second fraction (6 digit)
        "fffffff"   "0000000"   second fraction (7 digit)

        "F"     "0"         second fraction (up to 1 digit)
        "FF"    "00"        second fraction (up to 2 digit)
        "FFF"   "000"       second fraction (up to 3 digit)
        "FFFF"  "0000"      second fraction (up to 4 digit)
        "FFFFF" "00000"         second fraction (up to 5 digit)
        "FFFFFF"    "000000"    second fraction (up to 6 digit)
        "FFFFFFF"   "0000000"   second fraction (up to 7 digit)

        "t"                 first character of AM/PM designator   A
        "tt"                AM/PM designator                      AM
        "tt*"               AM/PM designator                      PM

        "d"     "0"         day w/o leading zero                  1
        "dd"    "00"        day with leading zero                 01
        "ddd"               short weekday name (abbreviation)     Mon
        "dddd"              full weekday name                     Monday
        "dddd*"             full weekday name                     Monday


        "M"     "0"         month w/o leading zero                2
        "MM"    "00"        month with leading zero               02
        "MMM"               short month name (abbreviation)       Feb
        "MMMM"              full month name                       Febuary
        "MMMM*"             full month name                       Febuary

        "y"     "0"         two digit year (year % 100) w/o leading zero           0
        "yy"    "00"        two digit year (year % 100) with leading zero          00
        "yyy"   "D3"        year                                  2000
        "yyyy"  "D4"        year                                  2000
        "yyyyy" "D5"        year                                  2000
        ...

        "z"        "+0;-0"     timezone offset w/o leading zero      -8
        "zz"       "+00;-00"   timezone offset with leading zero     -08
        "zzz"      "+00;-00" for hour offset, "00" for minute offset  full timezone offset   -07:30
        "zzz*"  "+00;-00" for hour offset, "00" for minute offset   full timezone offset   -08:00

        "K"    -Local           "zzz", e.g. -08:00
               -Utc             "'Z'", representing UTC
               -Unspecified     ""
               -DateTimeOffset      "zzzzz" e.g -07:30:15

        "g*"                the current era name                  A.D.

        ":"                 time separator                        :
        "/"                 date separator                        /
        "'"                 quoted string                         'ABC' will insert ABC into the formatted string.
        '"'                 quoted string                         "ABC" will insert ABC into the formatted string.
        "%"                 used to quote a single pattern characters      E.g.The format character "%y" is to print two digit year.
        "\"                 escaped character                     E.g. '\d' insert the character 'd' into the format string.
        other characters    insert the character into the format string.

    Pre-defined format characters:
        (U) to indicate Universal time is used.
        (G) to indicate Gregorian calendar is used.

        Format              Description                             Real format                             Example
        =========           =================================       ======================                  =======================
        "d"                 short date                              culture-specific                        10/31/1999
        "D"                 long data                               culture-specific                        Sunday, October 31, 1999
        "f"                 full date (long date + short time)      culture-specific                        Sunday, October 31, 1999 2:00 AM
        "F"                 full date (long date + long time)       culture-specific                        Sunday, October 31, 1999 2:00:00 AM
        "g"                 general date (short date + short time)  culture-specific                        10/31/1999 2:00 AM
        "G"                 general date (short date + long time)   culture-specific                        10/31/1999 2:00:00 AM
        "m"/"M"             Month/Day date                          culture-specific                        October 31
(G)     "o"/"O"             Round Trip XML                          "yyyy-MM-ddTHH:mm:ss.fffffffK"          1999-10-31 02:00:00.0000000Z
(G)     "r"/"R"             RFC 1123 date,                          "ddd, dd MMM yyyy HH':'mm':'ss 'GMT'"   Sun, 31 Oct 1999 10:00:00 GMT
(G)     "s"                 Sortable format, based on ISO 8601.     "yyyy-MM-dd'T'HH:mm:ss"                 1999-10-31T02:00:00
                                                                    ('T' for local time)
        "t"                 short time                              culture-specific                        2:00 AM
        "T"                 long time                               culture-specific                        2:00:00 AM
(G)     "u"                 Universal time with sortable format,    "yyyy'-'MM'-'dd HH':'mm':'ss'Z'"        1999-10-31 10:00:00Z
                            based on ISO 8601.
(U)     "U"                 Universal time with full                culture-specific                        Sunday, October 31, 1999 10:00:00 AM
                            (long date + long time) format
                            "y"/"Y"             Year/Month day                          culture-specific                        October, 1999

    */

//This class contains only static members and does not require the serializable attribute.
public class DateTimeFormat
{
    private static char[] allStandardFormats =
    {
            'd', 'D', 'f', 'F', 'g', 'G',
            'm', 'M', 'o', 'O', 'r', 'R',
            's', 't', 'T', 'u', 'U', 'y', 'Y',
    };

    private static final TimeSpan NullOffset = null;
    public static final int MaxSecondsFractionDigits = 7;
    public static final String RoundtripFormat = "yyyy'-'MM'-'dd'T'HH':'mm':'ss.fffffffK";
    public static final double TICKS_PER_DAY = 864000000000L;
    private static final String RoundtripDateTimeUnfixed = "yyyy'-'MM'-'ddTHH':'mm':'ss zzz";
    private static final int DEFAULT_ALL_DATETIMES_SIZE = 132;
    public static final long TICKS_PER_SECONDS = 10000000L;

    private static int parseRepeatPattern(String format, int pos, char patternChar)
    {
        int len = format.length();
        int index = pos + 1;
        while ((index < len) && (format.charAt(index) == patternChar))
            index++;
        return (index - pos);
    }

    private static String formatDayOfWeek(int dayOfWeek, int repeat, msDateTimeFormatInfo dtfi)
    {
        if (repeat == 3)
            return (dtfi.getAbbreviatedDayName(dayOfWeek));

        // Call dtfi.GetDayName() here, instead of accessing DayNames property, because we don't
        // want a clone of DayNames, which will hurt perf.
        return (dtfi.getDayName(dayOfWeek));
    }

    //private static String FormatHebrewMonthName(DateTime time, int month, int repeatCount, DateTimeFormatInfo dtfi)

    // The pos should point to a quote character. This method will
    // get the string encloed by the quote character.
    private static int parseQuoteString(String format, int pos, StringBuilder result) throws Exception
    {
        //
        // NOTE : pos will be the index of the quote character in the 'format' string.
        //
        int formatLen = format.length();
        int beginPos = pos;
        char quoteChar = format.charAt(pos++); // Get the character used to quote the following string.

        boolean foundQuote = false;
        while (pos < formatLen)
        {
            char ch = format.charAt(pos++);
            if (ch == quoteChar)
            {
                foundQuote = true;
                break;
            }
            else if (ch == '\\')
            {
                // The following are used to support escaped character.
                // Escaped character is also supported in the quoted string.
                // Therefore, someone can use a format like "'minute:' mm\"" to display:
                //  minute: 45"
                // because the second double quote is escaped.
                if (pos < formatLen)
                    result.append(format.charAt(pos++));
                else // This means that '\' is at the end of the formatting string.
                    throw new Exception("Format_InvalidString");
            }
            else
            {
                result.append(ch);
            }
        }

        // Here we can't find the matching quote.
        if (!foundQuote)
            throw new Exception("Format_BadQuote=" + quoteChar);

        // Return the character count including the begin/end quote characters and enclosed string.
        return (pos - beginPos);
    }

    // Get the next character at the index of 'pos' in the 'format' string.
    // Return value of -1 means 'pos' is already at the end of the 'format' string.
    // Otherwise, return value is the int value of the next character.
    private static int parseNextChar(String format, int pos)
    {
        if (pos >= format.length() - 1)
            return (-1);

        return ((int) format.charAt(pos + 1));
    }

    //  IsUseGenitiveForm
    //
    //  Actions: Check the format to see if we should use genitive month in the formatting.
    //      Starting at the position (index) in the (format) string, look back and look ahead to
    //      see if there is "d" or "dd".  In the case like "d MMMM" or "MMMM dd", we can use
    //      genitive form.  Genitive form is not used if there is more than two "d".
    //  Arguments:
    //      format      The format string to be scanned.
    //      index       Where we should start the scanning.  This is generally where "M" starts.
    //      tokenLen    The len of the current pattern character.  This indicates how many "M" that we have.
    //      patternToMatch  The pattern that we want to search. This generally uses "d"
    private static boolean isUseGenitiveForm(String format, int index, int tokenLen, char patternToMatch)
    {
        int i;
        int repeat = 0;
        //
        // Look back to see if we can find "d" or "ddd"
        //

        // Find first "d".
        for (i = index - 1; i >= 0 && format.charAt(i) != patternToMatch; i--)
        {  /*Do nothing here */ }

        if (i >= 0)
        {
            // Find a "d", so look back to see how many "d" that we can find.
            while (--i >= 0 && format.charAt(i) == patternToMatch)
                repeat++;

            // repeat == 0 means that we have one (patternToMatch)
            // repeat == 1 means that we have two (patternToMatch)
            if (repeat <= 1)
                return (true);

            // Note that we can't just stop here.  We may find "ddd" while looking back, and we have to look
            // ahead to see if there is "d" or "dd".
        }

        // If we can't find "d" or "dd" by looking back, try look ahead.

        // Find first "d"
        for (i = index + tokenLen; i < format.length() && format.charAt(i) != patternToMatch; i++)
        { /* Do nothing here */ }

        if (i < format.length())
        {
            repeat = 0;
            // Find a "d", so contine the walk to see how may "d" that we can find.
            while (++i < format.length() && format.charAt(i) == patternToMatch)
                repeat++;

            // repeat == 0 means that we have one (patternToMatch)
            // repeat == 1 means that we have two (patternToMatch)
            if (repeat <= 1)
                return (true);
        }

        return (false);
    }

    public static String getRealFormat(String format, msDateTimeFormatInfo dtfi) throws Exception
    {
        String realFormat = null;

        if (dtfi == null)
        {
            if (JavaDebug.ON)
                System.out.println("=== DateTimeFormat#getRealFormat is NULL, replace it with InvariantCulture ==");
            dtfi = msDateTimeFormatInfo.getInvariantInfo();
        }

        switch (format.charAt(0))
        {
            case 'd':       // Short Date
                realFormat = dtfi.getShortDatePattern();
                break;
            case 'D':       // Long Date
                realFormat = dtfi.getLongDatePattern();
                break;
            case 'f':       // Full (long date + short time)
                realFormat = dtfi.getLongDatePattern() + " " + dtfi.getShortTimePattern();
                break;
            case 'F':       // Full (long date + long time)
                realFormat = dtfi.getFullDateTimePattern();
                break;
            case 'g':       // General (short date + short time)
                realFormat = dtfi.getGeneralShortTimePattern();
                break;
            case 'G':       // General (short date + long time)
                realFormat = dtfi.getGeneralLongTimePattern();
                break;
            case 'm':
            case 'M':       // Month/Day Date
                realFormat = dtfi.getMonthDayPattern();
                break;
            case 'o':
            case 'O':
                realFormat = RoundtripFormat;
                break;
            case 'r':
            case 'R':       // RFC 1123 Standard
                realFormat = dtfi.getRFC1123Pattern();
                break;
            case 's':       // Sortable without Time Zone Info
                realFormat = dtfi.getSortableDateTimePattern();
                break;
            case 't':       // Short Time
                realFormat = dtfi.getShortTimePattern();
                break;
            case 'T':       // Long Time
                realFormat = dtfi.getLongTimePattern();
                break;
            case 'u':       // Universal with Sortable format
                realFormat = dtfi.getUniversalSortableDateTimePattern();
                break;
            case 'U':       // Universal with Full (long date + long time) format
                realFormat = dtfi.getFullDateTimePattern();
                break;
            case 'y':
            case 'Y':       // Year/Month Date
                realFormat = dtfi.getYearMonthPattern();
                break;
            default:
                throw new Exception("Format_InvalidString");
        }
        return realFormat;
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // Format the positive integer value to a string and perfix with assigned
    // length of leading zero.
    //
    // Parameters:
    //  value: The value to format
    //  len: The maximum length for leading zero.
    //  If the digits of the value is greater than len, no leading zero is added.
    //
    // Notes:
    //  The function can format to Int32.MaxValue.
    //
    ////////////////////////////////////////////////////////////////////////////
    private static void formatDigits(StringBuilder outputBuffer, int value, int len)
    {
        // Limit the use of this function to be two-digits, so that we have the same behavior
        // as RTM bits.
        if (len > 2)
            len = 2;

        char[] buffer = new char[16];
        int p = buffer.length;
        int n = value;
        do
        {
            buffer[--p] = (char) (n % 10 + '0');
            n /= 10;
        }
        while ((n != 0) && (p > 0));

        int digits = (buffer.length - p);

        //If the repeat count is greater than 0, we're trying
        //to emulate the "00" format, so we have to prepend
        //a zero if the string only has one character.
        while ((digits < len) && (p > 0))
        {
            buffer[--p] = '0';
            digits++;
        }

        //outputBuffer.append(p, digits);
        for (int i = 0; i < digits; i++)
        {
            outputBuffer.append(buffer[p + i]);
        }
    }

    //
    //  FormatCustomized
    //
    //  Actions: Format the DateTime instance using the specified format.
    //
    private static String formatCustomized(DateTime dateTime, String format, msDateTimeFormatInfo dtfi, TimeSpan offset) throws Exception
    {
        Calendar cal = dtfi.getCalendar();
        StringBuilder result = new StringBuilder();
        // This is a flag to indicate if we are format the dates using Hebrew calendar.

        boolean isHebrewCalendar = cal.getID() == Calendar.CAL_HEBREW;
        // This is a flag to indicate if we are formating hour/minute/second only.
        boolean bTimeOnly = true;

        int i = 0;
        int tokenLen, hour12;

        while (i < format.length())
        {
            char ch = format.charAt(i);
            int nextChar;
            switch (ch)
            {
                case 'g':
                    tokenLen = parseRepeatPattern(format, i, ch);
                    result.append(dtfi.getEraName(cal.getEra(dateTime)));
                    break;
                case 'h':
                    tokenLen = parseRepeatPattern(format, i, ch);
                    hour12 = dateTime.getHour() % 12;
                    if (hour12 == 0)
                        hour12 = 12;

                    formatDigits(result, hour12, tokenLen);
                    break;
                case 'H':
                    tokenLen = parseRepeatPattern(format, i, ch);
                    formatDigits(result, dateTime.getHour(), tokenLen);
                    break;
                case 'm':
                    tokenLen = parseRepeatPattern(format, i, ch);
                    formatDigits(result, dateTime.getMinute(), tokenLen);
                    break;
                case 's':
                    tokenLen = parseRepeatPattern(format, i, ch);
                    formatDigits(result, dateTime.getSecond(), tokenLen);
                    break;
                case 'f':
                case 'F':
                    tokenLen = parseRepeatPattern(format, i, ch);
                    if (tokenLen <= MaxSecondsFractionDigits)
                    {
                        long fraction = (dateTime.getTicks() % Calendar.TICKS_PER_SECOND);
                        fraction = fraction / (long) Math.pow(10, 7 - tokenLen);
                        if (ch == 'f')
                        {
                            result.append(intToStr(fraction, tokenLen));
                        }
                        else
                        {
                            int effectiveDigits = tokenLen;
                            while (effectiveDigits > 0)
                            {
                                if (fraction % 10 == 0)
                                {
                                    fraction = fraction / 10;
                                    effectiveDigits--;
                                }
                                else
                                {
                                    break;
                                }
                            }
                            if (effectiveDigits > 0)
                            {
                                result.append(intToStr(fraction, effectiveDigits));
                            }
                            else
                            {
                                // No fraction to emit, so see if we should remove decimal also.
                                if (result.length() > 0 && result.charAt(result.length() - 1) == '.')
                                    result.delete(result.length() - 1, 1);
                            }
                        }
                    }
                    else
                    {
                        throw new Exception("Format_InvalidString");
                    }
                    break;
                case 't':
                    tokenLen = parseRepeatPattern(format, i, ch);
                    if (tokenLen == 1)
                    {
                        if (dateTime.getHour() < 12)
                        {
                            if (dtfi.getAMDesignator().length() >= 1)
                                result.append(dtfi.getAMDesignator().charAt(0));
                        }
                        else
                        {
                            if (dtfi.getPMDesignator().length() >= 1)
                                result.append(dtfi.getPMDesignator().charAt(0));
                        }
                    }
                    else
                    {
                        result.append((dateTime.getHour() < 12 ? dtfi.getAMDesignator() : dtfi.getPMDesignator()));
                    }
                    break;
                case 'd':
                    //
                    // tokenLen == 1 : Day of month as digits with no leading zero.
                    // tokenLen == 2 : Day of month as digits with leading zero for single-digit months.
                    // tokenLen == 3 : Day of week as a three-leter abbreviation.
                    // tokenLen >= 4 : Day of week as its full name.
                    //
                    tokenLen = parseRepeatPattern(format, i, ch);
                    if (tokenLen <= 2)
                    {
                        int day = cal.getDayOfMonth(dateTime);
                        if (isHebrewCalendar)
                        {
                            // For Hebrew calendar, we need to convert numbers to Hebrew text for yyyy, MM, and dd values.
                            hebrewFormatDigits(result, day);
                        }
                        else
                        {
                            formatDigits(result, day, tokenLen);
                        }
                    }
                    else
                    {
                        int dayOfWeek = cal.getDayOfWeek(dateTime);
                        result.append(formatDayOfWeek(dayOfWeek, tokenLen, dtfi));
                    }
                    bTimeOnly = false;
                    break;
                case 'M':
                    //
                    // tokenLen == 1 : Month as digits with no leading zero.
                    // tokenLen == 2 : Month as digits with leading zero for single-digit months.
                    // tokenLen == 3 : Month as a three-letter abbreviation.
                    // tokenLen >= 4 : Month as its full name.
                    //
                    tokenLen = parseRepeatPattern(format, i, ch);
                    int month = cal.getMonth(dateTime);
                    if (tokenLen <= 2)
                    {
                        if (isHebrewCalendar)
                        {
                            // For Hebrew calendar, we need to convert numbers to Hebrew text for yyyy, MM, and dd values.
                            hebrewFormatDigits(result, month);
                        }
                        else
                        {
                            formatDigits(result, month, tokenLen);
                        }
                    }
                    else
                    {
                        if (isHebrewCalendar)
                        {
                            result.append(formatHebrewMonthName(dateTime, month, tokenLen, dtfi));
                        }
                        else if ((dtfi.getFormatFlags() & DateTimeFormatFlags.UseGenitiveMonth) != 0 && tokenLen >= 4)
                        {
                            result.append(
                                    dtfi.internalGetMonthName(
                                            month,
                                            isUseGenitiveForm(format, i, tokenLen, 'd')? MonthNameStyles.Genitive : MonthNameStyles.Regular,
                                            false));
                        }
                        else
                        {
                            result.append(formatMonth(month, tokenLen, dtfi));
                        }
                    }
                    bTimeOnly = false;
                    break;
                case 'y':
                    // Notes about OS behavior:
                    // y: Always print (year % 100). No leading zero.
                    // yy: Always print (year % 100) with leading zero.
                    // yyy/yyyy/yyyyy/... : Print year value.  No leading zero.

                    int year = cal.getYear(dateTime);
                    tokenLen = parseRepeatPattern(format, i, ch);
                    if (dtfi.hasForceTwoDigitYears())
                    {
                        formatDigits(result, year, tokenLen <= 2 ? tokenLen : 2);
                    }
                    else if (isHebrewCalendar)
                    {
                        hebrewFormatDigits(result, year);
                    }
                    else
                    {
                        if (tokenLen <= 2)
                            formatDigits(result, year % 100, tokenLen);
                        else
                            // yyy/yyyy/yyyyy/... : Print year value.  No leading zero.
                            result.append(intToStr(year, 4));
                    }
                    bTimeOnly = false;
                    break;
                case 'z':
                    tokenLen = parseRepeatPattern(format, i, ch);
                    formatCustomizedTimeZone(dateTime, offset, format, tokenLen, bTimeOnly, result);
                    break;
                case 'K':
                    tokenLen = 1;
                    formatCustomizedRoundripTimeZone(dateTime, offset, result);
                    break;
                case ':':
                    result.append(dtfi.getTimeSeparator());
                    tokenLen = 1;
                    break;
                case '/':
                    result.append(dtfi.getDateSeparator());
                    tokenLen = 1;
                    break;
                case '\'':
                case '\"':
                    StringBuilder enquotedString = new StringBuilder();
                    tokenLen = parseQuoteString(format, i, enquotedString);
                    result.append(enquotedString);
                    break;
                case '%':
                    // Optional format character.
                    // For example, format string "%d" will print day of month
                    // without leading zero.  Most of the cases, "%" can be ignored.
                    nextChar = parseNextChar(format, i);
                    // nextChar will be -1 if we already reach the end of the format string.
                    // Besides, we will not allow "%%" appear in the pattern.
                    if (nextChar >= 0 && nextChar != (int) '%')
                    {
                        result.append(formatCustomized(dateTime, String.valueOf((char) nextChar), dtfi, offset));
                        tokenLen = 2;
                    }
                    else
                    {
                        //
                        // This means that '%' is at the end of the format string or
                        // "%%" appears in the format string.
                        //
                        throw new Exception("Format_InvalidString");
                    }
                    break;
                case '\\':
                    // Escaped character.  Can be used to insert character into the format string.
                    // For exmple, "\d" will insert the character 'd' into the string.
                    //
                    // NOTENOTE : we can remove this format character if we enforce the enforced quote
                    // character rule.
                    // That is, we ask everyone to use single quote or double quote to insert characters,
                    // then we can remove this character.
                    //
                    nextChar = parseNextChar(format, i);
                    if (nextChar >= 0)
                    {
                        result.append(((char) nextChar));
                        tokenLen = 2;
                    }
                    else
                    {
                        //
                        // This means that '\' is at the end of the formatting string.
                        //
                        throw new Exception("Format_InvalidString");
                    }
                    break;
                default:
                    // NOTENOTE : we can remove this rule if we enforce the enforced quote
                    // character rule.
                    // That is, if we ask everyone to use single quote or double quote to insert characters,
                    // then we can remove this default block.
                    result.append(ch);
                    tokenLen = 1;
                    break;
            }
            i += tokenLen;
        }
        return (result.toString());
    }

    private static String formatHebrewMonthName(DateTime time, int month, int repeatCount, msDateTimeFormatInfo dtfi)
    {
        // This month is in a leap year
        if (dtfi.getCalendar().isLeapYear(dtfi.getCalendar().getYear(time)))
            return dtfi.internalGetMonthName(month, MonthNameStyles.LeapYear, repeatCount == 3);

        // This is in a regular year.
        if (month >= 7)
            month++;

        if (repeatCount == 3)
            return dtfi.getAbbreviatedMonthName(month);

        return dtfi.getMonthName(month);
    }

    private static void hebrewFormatDigits(StringBuilder outputBuffer, int digits)
    {
        outputBuffer.append(HebrewNumber.toString(digits));
    }

    // output the 'z' family of formats, which output a the offset from UTC, e.g. "-07:30"
    private static void formatCustomizedTimeZone(DateTime dateTime, TimeSpan offset, String format, int tokenLen, boolean timeOnly, StringBuilder result)
    {
        // See if the instance already has an offset
        boolean dateTimeFormat = (offset == NullOffset);
        if (dateTimeFormat)
        {
            // No offset. The instance is a DateTime and the output should be the local time zone

            if (timeOnly && dateTime.getTicks() < Calendar.TICKS_PER_DAY)
            {
                // For time only format and a time only input, the time offset on 0001/01/01 is less
                // accurate than the system's current offset because of daylight saving time.
                offset = new TimeSpan(DateTime.getNow().getTimeZoneOffset());
            }
            else
            {
                if (dateTime.getKind() == DateTimeKind.UTC)
                {
                    // This code path points to a bug in user code. It would make sense to return a 0 offset in this case.
                    // However, because it was only possible to detect this in Whidbey, there is user code that takes a
                    // dependency on being serialize a UTC DateTime using the 'z' format, and it will work almost all the
                    // time if it is offset by an incorrect conversion to local time when parsed. Therefore, we need to
                    // explicitly emit the local time offset, which we can do by removing the UTC flag.
                    invalidFormatForUtc(format, dateTime);
                    dateTime = DateTime.specifyKind(dateTime, (int) DateTimeKind.LOCAL);
                    offset = TimeSpan.ZERO;
                }
                else
                {
                    offset = new TimeSpan(dateTime.getTimeZoneOffset());
                }
            }
        }

        //if (offset. >= TimeSpan.Zero)
        if (offset.getHours() >= 0 || offset.getMinutes() >= 0)
        {
            result.append('+');
        }
        else
        {
            result.append('-');
            // get a positive offset, so that you don't need a separate code path for the negative numbers.
            offset = offset.negate();
        }

        if (tokenLen <= 1)
        {
            // 'z' format e.g "-7"
            result.append(intToStr(offset.getHours(), 1));
        }
        else
        {
            // 'zz' or longer format e.g "-07"
            result.append(intToStr(offset.getHours(), 2));
            if (tokenLen >= 3)
            {
                // 'zzz*' or longer format e.g "-07:30"
                result.append(":" + intToStr(offset.getMinutes(), 2));
            }
        }
    }

    // output the 'K' format, which is for round-tripping the data
    private static void formatCustomizedRoundripTimeZone(DateTime dateTime, TimeSpan offset, StringBuilder result)
    {
        // The objective of this format is to round trip the data in the type
        // For DateTime it should round-trip the Kind value and preserve the time zone.
        // DateTimeOffset instance, it should do so by using the internal time zone.

        //if (offset == NullOffset) {
        if (offset.getHours() == 0 || offset.getMinutes() == 0)
        {
            // source is a date time, so behavior depends on the kind.
            switch ((int) dateTime.getKind())
            {
                case (int) DateTimeKind.LOCAL:
                    // This should output the local offset, e.g. "-07:30"
                    // offset = TimeZone.CurrentTimeZone.GetUtcOffset(dateTime);
                    offset = new TimeSpan(dateTime.getTimeZoneOffset());
                    // fall through to shared time zone output code
                    break;
                case (int) DateTimeKind.UTC:
                    // The 'Z' constant is a marker for a UTC date
                    result.append("Z");
                    return;

                default:
                    // If the kind is unspecified, we output nothing here
                    return;
            }
        }
        //if (offset >= TimeSpan.Zero)
        if (offset.getHours() > 0 || offset.getMinutes() > 0)
        {
            result.append('+');
        }
        else
        {
            result.append('-');
            // get a positive offset, so that you don't need a separate code path for the negative numbers.
            offset = offset.negate();
        }

        result.append(intToStr(offset.getHours(), 2)).append(":").append(intToStr(offset.getMinutes(), 2));
    }

    private static String formatMonth(int month, int repeatCount, msDateTimeFormatInfo dtfi)
    {
        if (repeatCount == 3)
            return (dtfi.getAbbreviatedMonthName(month));

        // Call GetMonthName() here, instead of accessing MonthNames property, because we don't
        // want a clone of MonthNames, which will hurt perf.
        return (dtfi.getMonthName(month));
    }

    // Expand a pre-defined format string (like "D" for long date) to the real format that
    // we are going to use in the date time parsing.
    // This method also convert the dateTime if necessary (e.g. when the format is in Universal time),
    // and change dtfi if necessary (e.g. when the format should use invariant culture).
    private static String expandPredefinedFormat(String format, DateTime dateTime, msDateTimeFormatInfo dtfi, TimeSpan offset) throws Exception
    {
        switch (format.charAt(0))
        {
            case 'o':
            case 'O':       // Round trip format
                dtfi = msDateTimeFormatInfo.getInvariantInfo();
                break;
            case 'r':
            case 'R':       // RFC 1123 Standard
                if (offset != NullOffset)
                {
                    // Convert to UTC invariants mean this will be in range
                    //dateTime = dateTime - offset;
                    dateTime = dateTime.subtruct(offset);
                }
                else if (dateTime.getKind() == DateTimeKind.LOCAL)
                {
                    invalidFormatForLocal(format, dateTime);
                }
                dtfi = msDateTimeFormatInfo.getInvariantInfo();
                break;
            case 's':       // Sortable without Time Zone Info
                dtfi = msDateTimeFormatInfo.getInvariantInfo();
                break;
            case 'u':       // Universal time in sortable format.
                if (offset != NullOffset)
                {
                    // Convert to UTC invariants mean this will be in range
                    dateTime = dateTime.subtruct(offset);
                }
                else if (dateTime.getKind() == DateTimeKind.LOCAL)
                {
                    invalidFormatForLocal(format, dateTime);
                }
                dtfi = msDateTimeFormatInfo.getInvariantInfo();
                break;
            case 'U':       // Universal time in culture dependent format.
                if (offset != NullOffset)
                {
                    // This format is not supported by DateTimeOffset
                    throw new Exception("Format_InvalidString");
                }
                // Universal time is always in Greogrian calendar.
                //
                // Change the Calendar to be Gregorian Calendar.
                //
                if (dtfi.getCalendar().getBaseCalendarID() != Calendar.CAL_GREGORIAN)
                {
                    dtfi = (msDateTimeFormatInfo)dtfi.deepClone();
                    dtfi.setCalendar(GregorianCalendar.getDefaultInstance());
                }
                dateTime = dateTime.toUniversalTime();
                break;
        }
        format = getRealFormat(format, dtfi);
        return (format);
    }

    public static String format(DateTime dateTime, String format, msDateTimeFormatInfo dtfi)
    {
        try
        {
            return format(dateTime, format, dtfi, NullOffset);
        }
        catch (Exception e)
        {
            Log.warn(e);
        }
        return "DateTime error";
    }

    public static String format(DateTime dateTime, String format, msDateTimeFormatInfo dtfi, TimeSpan offset) throws Exception
    {
        if (format == null || format.length() == 0)
        {
            boolean timeOnlySpecialCase = false;
            if (dateTime.getTicks() < Calendar.TICKS_PER_DAY)
            {
                // If the time is less than 1 day, consider it as time of day.
                // Just print out the short time format.
                //
                // This is a workaround for VB, since they use ticks less then one day to be
                // time of day.  In cultures which use calendar other than Gregorian calendar, these
                // alternative calendar may not support ticks less than a day.
                // For example, Japanese calendar only supports date after 1868/9/8.
                // This will pose a problem when people in VB get the time of day, and use it
                // to call ToString(), which will use the general format (short date + long time).
                // Since Japanese calendar does not support Gregorian year 0001, an exception will be
                // thrown when we try to get the Japanese year for Gregorian year 0001.
                // Therefore, the workaround allows them to call ToString() for time of day from a DateTime by
                // formatting as ISO 8601 format.
                switch (dtfi.getCalendar().getID())
                {
                    case Calendar.CAL_JAPAN:
                    case Calendar.CAL_TAIWAN:
                    case Calendar.CAL_HIJRI:
                    case Calendar.CAL_HEBREW:
                    case Calendar.CAL_JULIAN:
                    case Calendar.CAL_UMALQURA:
                        timeOnlySpecialCase = true;
                        dtfi = msDateTimeFormatInfo.getInvariantInfo();
                        break;
                }
            }

            if (offset == NullOffset) // Default DateTime.ToString case.
                format = timeOnlySpecialCase ? "s" : "G";
            else // Default DateTimeOffset.ToString case.
                format = timeOnlySpecialCase ? RoundtripDateTimeUnfixed : dtfi.getDateTimeOffsetPattern();
        }

        if (format.length() == 1)
            format = expandPredefinedFormat(format, dateTime, dtfi, offset);

        return (formatCustomized(dateTime, format, dtfi, offset));
    }

    private static String[] getAllDateTimes(DateTime dateTime, msDateTimeFormatInfo dtfi) throws Exception
    {
        ArrayList results = new ArrayList(DEFAULT_ALL_DATETIMES_SIZE);

        for (int i = 0; i < allStandardFormats.length; i++)
        {
            String[] strings = getAllDateTimes(dateTime, allStandardFormats[i], dtfi);
            for (int j = 0; j < strings.length; j++)
                results.add(strings[j]);
        }

        String[] value = new String[results.size()];
        // results.copyTo(0, value, 0, results.size());
        System.arraycopy(results, 0, value, 0, results.size());
        return (value);
    }

    private static String[] getAllDateTimes(DateTime dateTime, char format, msDateTimeFormatInfo dtfi) throws Exception
    {
        String[] allFormats = null;
        String[] results = null;

        switch (format)
        {
            case 'd':
            case 'D':
            case 'f':
            case 'F':
            case 'g':
            case 'G':
            case 'm':
            case 'M':
            case 't':
            case 'T':
            case 'y':
            case 'Y':
                allFormats = dtfi.getAllDateTimePatterns(format);
                results = new String[allFormats.length];
                for (int i = 0; i < allFormats.length; i++)
                    results[i] = format(dateTime, allFormats[i], dtfi);
                break;
            case 'U':
                DateTime universalTime = dateTime.toUniversalTime();
                allFormats = dtfi.getAllDateTimePatterns(format);
                results = new String[allFormats.length];
                for (int i = 0; i < allFormats.length; i++)
                    results[i] = format(universalTime, allFormats[i], dtfi);
                break;
            //
            // The following ones are special cases because these patterns are read-only in
            // DateTimeFormatInfo.
            //
            case 'r':
            case 'R':
            case 'o':
            case 'O':
            case 's':
            case 'u':
                results = new String[]{format(dateTime, new String(new char[]{format}), dtfi)};
                break;
            default:
                throw new Exception("Format_InvalidString");
        }

        return (results);
    }

    // This is a placeholder for an MDA to detect when the user is using a
    // local DateTime with a format that will be interpreted as UTC.
    private static void invalidFormatForLocal(String format, DateTime dateTime)
    {
    }

    // This is an MDA for cases when the user is using a local format with
    // a Utc DateTime.
    private static void invalidFormatForUtc(String format, DateTime dateTime)
    {
    }

    /**
     * Formats the value as integer string with given number of digits:
     * (9, 1) -> "9"
     * (9, 2) -> "09"
     * (9, 3) -> "009"
     * etc.
     */
    private static String intToStr(long value, int digits)
    {
        return gFixedIntegerFormats.get()[digits].format(value);
    }

    /**
     * Duplicate of above with int value argument.
     */
    private static String intToStr(int value, int digits)
    {
        return gFixedIntegerFormats.get()[digits].format(value);
    }

    /**
     * DecimalFormat is not thread-safe so we can't use static array here.
     * <p/>
     * ThreadLocal is most effective for such things but... it can cause memory leaks
     * in some web-server environments that use thread pulls and reusable threads
     * since ThreadLocal variable is automatically released only after parent Thread died.
     * (So launching Aspose.Words in such reusable thread will create new ThreadLocal variable
     * in addition to existing (non-deleted) variables from former AW launches)
     * <p/>
     * From other hand we can't use ThreadLocal.release() to manually release the object
     * since Aspose.Words hasn't finalizer.
     * <p/>
     * If customers will report memory problems with their server environment - we have to
     * change these formats to simple local variables.
     */
    private static final ThreadLocal<DecimalFormat[]> gFixedIntegerFormats = new ThreadLocal<DecimalFormat[]>()
    {
        @Override
        protected DecimalFormat[] initialValue()
        {
            //HACK: first digits number is zero so we can get a format using just digits number:
            //formats[1] is '0' format, formats[2] is for '00', etc.
            DecimalFormat[] formats = new DecimalFormat[8];
            for (int digits = 0; digits < formats.length; digits++)
            {
                formats[digits] = NumberFormatProvider.newIntegerInstance(Locale.US);
                formats[digits].setMaximumIntegerDigits(digits);
                formats[digits].setMinimumIntegerDigits(digits);
                formats[digits].setDecimalSeparatorAlwaysShown(false);
                formats[digits].setGroupingUsed(false);
            }

            return formats;
        }
    };


    class MonthNameStyles
    {
        static final int Regular = 0;
        static final int Genitive = 1;
        static final int LeapYear = 2;
    }
}
