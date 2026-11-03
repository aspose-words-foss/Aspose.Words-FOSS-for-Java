// A Java port, made by Aspose, of a .NET Framework class. Microsoft publishes the source of that class
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/globalization/datetimeparse.cs
//
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
//23/08/12 by Vyacheslav Durin

package com.aspose.ms.System.Globalization;

import com.aspose.ms.System.*;
import com.aspose.ms.System.Threading.CurrentThread;
import com.aspose.ms.java.debug.JavaDebug;
import com.aspose.ms.java.logging.Log;


////////////////////////////////////////////////////////////////////////
/*

There are some nasty cases in the parsing of date/time:

0x438    fo  (country:Faroe Islands, language:Faroese)
   LOCALE_STIME=[.]
   LOCALE_STIMEFOR=[HH.mm.ss]
   LOCALE_SDATE=[-]
   LOCALE_SSHORTDATE=[dd-MM-yyyy]
   LOCALE_SLONGDATE=[d. MMMM yyyy]

   The time separator is ".", However, it also has a "." in the long date format.

0x437: (country:Georgia, language:Georgian)
   Short date: dd.MM.yy
   Long date:  yyyy ???? dd MM, dddd

   The order in long date is YDM, which is different from the common ones: YMD/MDY/DMY.

0x0404: (country:----, language:Chinese)
   LOCALE_STIMEFORMAT=[tt hh:mm:ss]

   When general date is used, the pattern is "yyyy/M/d tt hh:mm:ss". Note that the "tt" is after "yyyy/M/d".
   And this is different from most cultures.

0x0437: (country:Georgia, language:Georgian)
   Short date: dd.MM.yy
   Long date:  yyyy ???? dd MM, dddd

0x0456:

*/
//This class contains only static members
public class DateTimeParse
{
    // Constructs a DateTime from a string. The string must specify a
    // date and optionally a time in a culture-specific or universal format.
    // Leading and trailing whitespace characters are allowed.
    //
    public static DateTime tryParse(String s, msDateTimeFormatInfo dateTimeFormatInfo, int dateTimeStyle)
    {
        return tryParseDotNet(s, dateTimeFormatInfo, dateTimeStyle);
    }

    public static DateTime tryParseExact(String dateValue, String format, msDateTimeFormatInfo dtfi)
    {
        return tryParseExact(dateValue, format, dtfi, DateTimeStyles.NONE);
    }

    public static DateTime tryParseExact(String dateValue, String format, msDateTimeFormatInfo dtfi, int style)
    {
        DateTimeString.DateTimeResult result = new DateTimeString.DateTimeResult();       // The buffer to store the parsing result.
        result.init();

        if (doStrictParse(dateValue, format, style, dtfi, result))
            return result.getParsedDate();

        return DateTime.MinValue;
    }

    /*=================================DoStrictParse==================================
    **Action: Do DateTime parsing using the format in formatParam.
    **Returns: The parsed DateTime.
    **Arguments:
    **Exceptions:
    **
    **Notes:
    **  When the following general formats are used, InvariantInfo is used in dtfi:
    **      'r', 'R', 's'.
    **  When the following general formats are used, the time is assumed to be in Universal time.
    **
    **Limitations:
    **  Only GregarianCalendar is supported for now.
    **  Only support GMT timezone.
    ==============================================================================*/
    private static boolean doStrictParse(String s,
                                         String formatParam,
                                         int styles,
                                         msDateTimeFormatInfo dtfi,
                                         /*ref*/ DateTimeString.DateTimeResult result)
    {
        boolean bTimeOnly;
        DateTimeString.ParsingInfo parseInfo = new DateTimeString.ParsingInfo();
        parseInfo.init();

        parseInfo.setCalendar(dtfi.getCalendar());
        parseInfo.setfAllowInnerWhite(((styles & DateTimeStyles.ALLOW_INNER_WHITE) != 0));
        parseInfo.setfAllowTrailingWhite(((styles & DateTimeStyles.ALLOW_TRAILING_WHITE) != 0));

        if (formatParam.length() == 1)
        {
            if (((result.getFlags() & DateTimeString.ParseFlags.CaptureOffset) != 0) && formatParam.charAt(0) == 'U')
            {
                // The 'U' format is not allowed for DateTimeOffset
                return false;
            }
            formatParam = expandPredefinedFormat(formatParam, dtfi, parseInfo, result);
        }

        result.setCalendar(parseInfo.getCalendar());

        if (parseInfo.getCalendar().getID() == Calendar.CAL_HEBREW)
        {
            //JAVA-changed: only m_hebrewNumberParser used in .Net, so let's skip delegate.
            //parseInfo.parseNumberDelegate = m_hebrewNumberParser;
            parseInfo.setfCustomNumberParser(true);
        }

        // Reset these values to negative one so that we could throw exception
        // if we have parsed every item twice.
        result.setHour(-1);
        result.setMinute(-1);
        result.setSecond(-1);

        DateTimeString format = new DateTimeString(formatParam, dtfi);
        DateTimeString str = new DateTimeString(s, dtfi);

        if (parseInfo.isfAllowTrailingWhite())
        {
            // Trim trailing spaces if AllowTrailingWhite.
            format.trimTail();
            format.removeTrailingInQuoteSpaces();
            str.trimTail();
        }

        if ((styles & DateTimeStyles.ALLOW_LEADING_WHITE) != 0)
        {
            format.skipWhiteSpaces();
            format.removeLeadingInQuoteSpaces();
            str.skipWhiteSpaces();
        }

        //
        // Scan every character in format and match the pattern in str.
        //
        while (format.getNext())
        {
            // We trim inner spaces here, so that we will not eat trailing spaces when
            // AllowTrailingWhite is not used.
            if (parseInfo.isfAllowInnerWhite())
                str.skipWhiteSpaces();

            if (!parseByFormat(str, format, parseInfo, dtfi, result))
                return (false);
        }

        // sidor: ignore remaining chars: it is several cases in our real-word tests
        // when java doesn't properly strip chars after the date.
        //if (str.index < str.value.length() - 1)
        //{
        //  // There are still remaining character in str.
        //  return false;
        //}


        if (parseInfo.isfUseTwoDigitYear() && ((dtfi.formatFlags & DateTimeFormatFlags.UseHebrewRule) == 0))
        {
            // A two digit year value is expected. Check if the parsed year value is valid.
            if (result.getYear() >= 100)
            {
                //result.SetFailure(ParseFailureKind.Format, "Format_BadDateTime", null);
                return false;
            }
            result.setYear(toFourDigitYear(result.getYear()));
        }

        if (parseInfo.isfUseHour12())
        {
            if (parseInfo.getTimeMark() == DateTimeString.TM.NotSet)
            {
                // hh is used, but no AM/PM designator is specified.
                // Assume the time is AM.
                // Don't throw exceptions in here becasue it is very confusing for the caller.
                // I always got confused myself when I use "hh:mm:ss" to parse a time string,
                // and ParseExact() throws on me (because I didn't use the 24-hour clock 'HH').
                parseInfo.setTimeMark(DateTimeString.TM.AM);
            }
            if (result.getHour() > 12)
            {
                // AM/PM is used, but the value for HH is too big.
                //result.SetFailure(ParseFailureKind.Format, "Format_BadDateTime", null);
                return false;
            }

            if (parseInfo.getTimeMark() == DateTimeString.TM.AM)
            {
                if (result.getHour() == 12)
                    result.setHour(0);
            }
            else
            {
                result.setHour((result.getHour() == 12) ? 12 : result.getHour() + 12);
            }
        }

        // Check if the parsed string only contains hour/minute/second values.
        bTimeOnly = (result.getYear() == -1 && result.getMonth() == -1 && result.getDay() == -1);
        if (!checkDefaultDateTime(result/*,  parseInfo.calendar*/, styles))
            return false;

        if (!bTimeOnly && dtfi.hasYearMonthAdjustment())
        {
            if (!dtfi.yearMonthAdjustment(new int[]{result.getYear(), result.getMonth()}, ((result.getFlags() & DateTimeString.ParseFlags.ParsedMonthName) != 0)))
            {
                //result.SetFailure(ParseFailureKind.FormatBadDateTimeCalendar, "Format_BadDateTimeCalendar", null);
                return false;
            }
        }

        //JAVA-changed: from out value
        result.setParsedDate(parseInfo.getCalendar().tryToDateTime(result.getYear(), result.getMonth(), result.getDay(),
                result.getHour(), result.getMinute(), result.getSecond(), 0, result.getEra(), result.getParsedDate()));
        if (result.getParsedDate() == DateTime.MinValue || result.getParsedDate() == null)
        {
            //result.SetFailure(ParseFailureKind.FormatBadDateTimeCalendar, "Format_BadDateTimeCalendar", null);
            return false;
        }

        if (result.getFraction() > 0)
            result.setParsedDate(result.getParsedDate().addTicks((long) Math.round(result.getFraction() * DateTimeFormat.TICKS_PER_SECONDS)));

        //
        // We have to check day of week before we adjust to the time zone.
        // It is because the value of day of week may change after adjusting
        // to the time zone.
        //
        if (parseInfo.getDayOfWeek() != -1)
        {
            //
            // Check if day of week is correct.
            //
            if (parseInfo.getDayOfWeek() != (int) parseInfo.getCalendar().getDayOfWeek(result.getParsedDate()))
            {
                //result.SetFailure(ParseFailureKind.Format, "Format_BadDayOfWeek", null);
                return false;
            }
        }

        //AMAS FIX: applying Time Zone offset only if styles is not DateTimeStyles.NONE
        if(styles != DateTimeStyles.NONE)
        {
            if (!determineTimeZoneAdjustments(result, styles, bTimeOnly))
                return false;
        }

        // Was the date fully parsed? If not, the condition will not be met. GMTName.length() has maximum possible value.
        if(str.getLen() - str.getIndex() > GMTName.length())
            return false;

        return true;
    }

    private static boolean parseByFormat(DateTimeString str, DateTimeString format, DateTimeString.ParsingInfo parseInfo, msDateTimeFormatInfo dtfi, DateTimeString.DateTimeResult result)
    {
        int tokenLen = 0;
        int tempYear = 0, tempMonth = 0, tempDay = 0, tempDayOfWeek = 0, tempHour = 0, tempMinute = 0, tempSecond = 0;
        double tempFraction = 0;
        int tempTimeMark = DateTimeString.TM.AM;

        char ch = (char) format.getChar();

        switch (ch)
        {
            case 'y':
                tokenLen = format.getRepeatCount();
                if (dtfi.hasForceTwoDigitYears())
                {
                    tempYear = parseDigits(str, 1, 4);
                }
                else
                {
                    if (tokenLen <= 2)
                    {
                        parseInfo.setfUseTwoDigitYear(true);
                    }
                    tempYear = parseDigits(str, tokenLen);
                }
                //JAVA-changed: '!parseResult' is 'tempYear == -1' in java.
                if (tempYear == -1 && parseInfo.isfCustomNumberParser())
                {
                    tempYear = parseInfo.parseNumberDelegate(str, tokenLen);
                }

                if (tempYear == -1)
                {
                    return (false);
                }
                if (checkNewValue(result.getYear(), tempYear, ch, result) == -1)
                {
                    return false;
                }
                result.setYear(tempYear);
                break;

            case 'M':

                tokenLen = format.getRepeatCount();
                if (tokenLen <= 2)
                {
                    if ((tempMonth = parseDigits(str, tokenLen)) == -1)
                    {
                        if (!parseInfo.isfCustomNumberParser() || (tempMonth = parseInfo.parseNumberDelegate(str, tokenLen)) == -1)
                        {
                            return (false);
                        }
                    }
                }
                else
                {
                    if (tokenLen == 3)
                    {
                        if ((tempMonth = matchAbbreviatedMonthName(str, dtfi)) == -1)
                        {
                            return (false);
                        }
                    }
                    else
                    {
                        if ((tempMonth = matchMonthName(str, dtfi)) == -1)
                        {
                            return (false);
                        }
                    }
                    result.setFlags(result.getFlags() | DateTimeString.ParseFlags.ParsedMonthName);
                }
                if (checkNewValue(result.getMonth(), tempMonth, ch, result) == -1)
                {
                    return (false);
                }
                result.setMonth(tempMonth);
                break;

            case 'd':
                // Day & Day of week
                tokenLen = format.getRepeatCount();
                if (tokenLen <= 2)
                {
                    // "d" & "dd"

                    if ((tempDay = parseDigits(str, tokenLen)) == -1)
                    {
                        if (!parseInfo.isfCustomNumberParser() || (tempDay = parseInfo.parseNumberDelegate(str, tokenLen)) == -1)
                        {
                            //result.SetFailure(ParseFailureKind.Format, "Format_BadDateTime", null);
                            return (false);
                        }
                    }
                    if (checkNewValue(result.getDay(), tempDay, ch, result) == -1)
                    {
                        return (false);
                    }
                }
                else
                {
                    if (tokenLen == 3)
                    {
                        // "ddd"
                        if ((tempDayOfWeek = matchAbbreviatedDayName(str, dtfi)) == -1)
                        {
                            return (false);
                        }
                    }
                    else
                    {
                        // "dddd*"
                        if ((tempDayOfWeek = matchDayName(str, dtfi)) == -1)
                        {
                            return (false);
                        }
                    }
                    if (checkNewValue(parseInfo.getDayOfWeek(), tempDayOfWeek, ch, result) == -1)
                    {
                        return (false);
                    }
                }
                result.setDay(tempDay);
                break;

            case 'g':
                tokenLen = format.getRepeatCount();
                // Put the era value in result.era.
                result.setEra(matchEraName(str, dtfi));
                if (result.getEra()== -1)
                {
                    return (false);
                }
                break;

            case 'h':
                parseInfo.setfUseHour12(true);
                tokenLen = format.getRepeatCount();
                if ((tempHour = parseDigits(str, (tokenLen < 2 ? 1 : 2))) == -1)
                {
                    return (false);
                }
                if (checkNewValue(result.getHour(), tempHour, ch, result) == -1)
                {
                    return (false);
                }
                result.setHour(tempHour);
                break;

            case 'H':
                tokenLen = format.getRepeatCount();
                if ((tempHour = parseDigits(str, (tokenLen < 2 ? 1 : 2))) == -1)
                {
                    return (false);
                }
                if (checkNewValue(result.getHour(), tempHour, ch, result) == -1)
                {
                    return (false);
                }
                result.setHour(tempHour);
                break;

            case 'm':
                tokenLen = format.getRepeatCount();
                if ((tempMinute = parseDigits(str, (tokenLen < 2 ? 1 : 2))) == -1)
                {
                    return (false);
                }
                if (checkNewValue(result.getMinute(), tempMinute, ch, result) == -1)
                {
                    return (false);
                }
                result.setMinute(tempMinute);
                break;

            case 's':
                tokenLen = format.getRepeatCount();
                if ((tempSecond = parseDigits(str, (tokenLen < 2 ? 1 : 2))) == -1)
                {
                    return (false);
                }
                if (checkNewValue(result.getSecond(), tempSecond, ch, result) == -1)
                {
                    return (false);
                }
                result.setSecond(tempSecond);
                break;

            case 'f':
            case 'F':
                tokenLen = format.getRepeatCount();
                if (tokenLen <= DateTimeFormat.MaxSecondsFractionDigits)
                {
                    if ((tempFraction = parseFractionExact(str, tokenLen)) == -1)
                    {
                        if (ch == 'f')
                        {
                            return (false);
                        }
                    }
                    if (result.getFraction() < 0)
                    {
                        result.setFraction(tempFraction);
                    }
                    else
                    {
                        if (tempFraction != result.getFraction())
                        {
                            return (false);
                        }
                    }
                }
                else
                {
                    return (false);
                }
                result.setFraction(tempFraction);
                break;

            case 't':
                // AM/PM designator
                tokenLen = format.getRepeatCount();
                if (tokenLen == 1)
                {
                    if ((tempTimeMark = matchAbbreviatedTimeMark(str, dtfi)) == -1)
                    {
                        return (false);
                    }
                }
                else
                {
                    if ((tempTimeMark = matchTimeMark(str, dtfi)) == -1)
                    {
                        return (false);
                    }
                }

                if (parseInfo.getTimeMark() == DateTimeString.TM.NotSet)
                {
                    parseInfo.setTimeMark(tempTimeMark);
                }
                else
                {
                    if (parseInfo.getTimeMark() != tempTimeMark)
                    {
                        return (false);
                    }
                }

                break;

            case 'z':
                // timezone offset
                tokenLen = format.getRepeatCount();
                {
                    TimeSpan tempTimeZoneOffset = new TimeSpan(0);
                    if ((tempTimeZoneOffset = parseTimeZoneOffset(str, tokenLen)) == null)
                    {
                        //result.SetFailure(ParseFailureKind.Format, "Format_BadDateTime", null);
                        return (false);
                    }
                    if ((result.getFlags() & DateTimeString.ParseFlags.TimeZoneUsed) != 0 && tempTimeZoneOffset != result.getTimeZoneOffset())
                    {
                        //result.SetFailure(ParseFailureKind.FormatWithParameter, "Format_RepeatDateTimePattern", 'z');
                        return (false);
                    }
                    result.setTimeZoneOffset(tempTimeZoneOffset);
                    result.setFlags(result.getFlags() | DateTimeString.ParseFlags.TimeZoneUsed);
                }
            break;

            case 'Z':
                if ((result.getFlags() & DateTimeString.ParseFlags.TimeZoneUsed) != 0 && result.getTimeZoneOffset() != TimeSpan.ZERO)
                {
                    //result.SetFailure(ParseFailureKind.FormatWithParameter, "Format_RepeatDateTimePattern", 'Z');
                    return (false);
                }

                result.setFlags(result.getFlags() | DateTimeString.ParseFlags.TimeZoneUsed);
                result.setTimeZoneOffset(new TimeSpan(0));
                result.setFlags(result.getFlags() | DateTimeString.ParseFlags.TimeZoneUtc);

                // The updating of the indexes is to reflect that ParseExact MatchXXX methods assume that
                // they need to increment the index and Parse GetXXX do not. Since we are calling a Parse
                // method from inside ParseExact we need to adjust this. Long term, we should try to
                // eliminate this discrepancy.
                str.setIndex(str.getIndex() + 1);
                if (!getTimeZoneName(str))
                {
                    //result.SetFailure(ParseFailureKind.Format, "Format_BadDateTime", null);
                    return false;
                }
                str.setIndex(str.getIndex() - 1);
                break;

            case 'K':
                // This should parse either as a blank, the 'Z' character or a local offset like "-07:00"
                if (str.match('Z'))
                {
                    if ((result.getFlags() & DateTimeString.ParseFlags.TimeZoneUsed) != 0 && result.getTimeZoneOffset() != TimeSpan.ZERO)
                    {
                        //result.SetFailure(ParseFailureKind.FormatWithParameter, "Format_RepeatDateTimePattern", 'K');
                        return (false);
                    }

                    result.setFlags(result.getFlags() | DateTimeString.ParseFlags.TimeZoneUsed);
                    result.setTimeZoneOffset(new TimeSpan(0));
                    result.setFlags(result.getFlags() | DateTimeString.ParseFlags.TimeZoneUtc);
                }
                else if (str.match('+') || str.match('-'))
                {
                    str.setIndex(str.getIndex() - 1); // Put the character back for the parser
                    TimeSpan tempTimeZoneOffset = new TimeSpan(0);
                    if ((tempTimeZoneOffset = parseTimeZoneOffset(str, 3)) == null)
                    {
                        return (false);
                    }
                    if ((result.getFlags() & DateTimeString.ParseFlags.TimeZoneUsed) != 0 && tempTimeZoneOffset != result.getTimeZoneOffset())
                    {
                        return (false);
                    }
                    result.setTimeZoneOffset(tempTimeZoneOffset);
                    result.setFlags(result.getFlags() | DateTimeString.ParseFlags.TimeZoneUsed);
                }
                // Otherwise it is unspecified and we consume no characters
                break;

            case ':':
                if (!str.match(dtfi.getTimeSeparator().charAt(0)))
                {
                    // A time separator is expected.
                    return false;
                }
                break;
            case '/':
                if (!str.match(dtfi.getDateSeparator().charAt(0)))
                {
                    // A date separator is expected.
                    return false;
                }
                break;

            case '\"':
            case '\'':
                StringBuilder enquotedString = new StringBuilder();
                // Use ParseQuoteString so that we can handle escape characters within the quoted string.
                if ((tokenLen = tryParseQuoteString(format.getValue(), format.getIndex(), enquotedString)) == -1)
                {
                    return (false);
                }
                format.setIndex(format.getIndex() + tokenLen - 1);

                // Some cultures uses space in the quoted string.  E.g. Spanish has long date format as:
                // "dddd, dd' de 'MMMM' de 'yyyy".  When inner spaces flag is set, we should skip whitespaces if there is space
                // in the quoted string.
                String quotedStr = enquotedString.toString();

                for (int i = 0; i < quotedStr.length(); i++)
                {
                    if (quotedStr.charAt(i) == ' ' && parseInfo.isfAllowInnerWhite())
                    {
                        str.skipWhiteSpaces();
                    }
                    else if (!str.match(quotedStr.charAt(i)))
                    {
                        // Can not find the matching quoted string.
                        return false;
                    }
                }

                // The "r" and "u" formats incorrectly quoted 'GMT' and 'Z', respectively.  We cannot
                // correct this mistake for DateTime.ParseExact for compatibility reasons, but we can
                // fix it for DateTimeOffset.ParseExact as DateTimeOffset has not been publically released
                // with this issue.
                if ((result.getFlags() & DateTimeString.ParseFlags.CaptureOffset) != 0)
                {
                    if ((result.getFlags() & DateTimeString.ParseFlags.Rfc1123Pattern) != 0 && DateTimeString.GMTName.equals(quotedStr))
                    {
                        result.setFlags(result.getFlags() | DateTimeString.ParseFlags.TimeZoneUsed);
                        result.setTimeZoneOffset(TimeSpan.ZERO);
                    }
                    else if ((result.getFlags() & DateTimeString.ParseFlags.UtcSortPattern) != 0 && DateTimeString.ZuluName.equals(quotedStr))
                    {
                        result.setFlags(result.getFlags() | DateTimeString.ParseFlags.TimeZoneUsed);
                        result.setTimeZoneOffset(TimeSpan.ZERO);
                    }
                }

                break;

            case '%':
                // Skip this so we can get to the next pattern character.
                // Used in case like "%d", "%y"

                // Make sure the next character is not a '%' again.
                if (format.getIndex() >= format.getValue().length() - 1 || format.getValue().charAt(format.getIndex() + 1) == '%')
                {
                    return false;
                }
                break;

            case '\\':
                // Escape character. For example, "\d".
                // Get the next character in format, and see if we can
                // find a match in str.
                if (format.getNext())
                {
                    if (!str.match((char) format.getChar()))
                    {
                        // Can not find a match for the escaped character.
                        return false;
                    }

                }
                else
                {
                    return false;
                }
                break;

            case '.':
                if (!str.match(ch))
                {
                    if (format.getNext())
                    {
                        // If we encounter the pattern ".F", and the dot is not present, it is an optional
                        // second fraction and we can skip this format.
                        if (format.match('F'))
                        {
                            format.getRepeatCount();
                            break;
                        }
                    }
                    return false;
                }
                break;
            default:
                if (ch == ' ')
                {
                    if (parseInfo.isfAllowInnerWhite())
                    {
                        // Skip whitespaces if AllowInnerWhite.
                        // Do nothing here.
                    }
                    else
                    {
                        if (!str.match(ch))
                        {
                            // If the space does not match, and trailing space is allowed, we do
                            // one more step to see if the next format character can lead to
                            // successful parsing.
                            // This is used to deal with special case that a empty string can match
                            // a specific pattern.
                            // The example here is af-ZA, which has a time format like "hh:mm:ss tt".  However,
                            // its AM symbol is "" (empty string).  If fAllowTrailingWhite is used, and time is in
                            // the AM, we will trim the whitespaces at the end, which will lead to a failure
                            // when we are trying to match the space before "tt".
                            if (parseInfo.isfAllowTrailingWhite())
                            {
                                if (format.getNext())
                                {
                                    if (!parseByFormat(str, format, parseInfo, dtfi, result))
                                    {
                                        return (false);
                                    }
                                }
                            }
                            return false;
                        }
                        // Found a macth.
                    }
                }
                else
                {
                    if (format.matchSpecifiedWord(DateTimeString.GMTName))
                    {
                        format.setIndex(format.getIndex() + DateTimeString.GMTName.length() - 1);
                        // Found GMT string in format.  This means the DateTime string
                        // is in GMT timezone.
                        result.setFlags(result.getFlags() | DateTimeString.ParseFlags.TimeZoneUsed);
                        result.setTimeZoneOffset(TimeSpan.ZERO);

                        if (!str.match(DateTimeString.GMTName.charAt(0)))
                        {
                            return false;
                        }
                    }
                    else if (!str.match(ch))
                    {
                        // ch is expected.
                        return false;
                    }
                }
                break;

        }
        return true;
    }

    //
    // Check the word at the current index to see if it matches GMT name or Zulu name.
    //
    private static boolean getTimeZoneName(DateTimeString str)
    {
        //
        // <

        if (matchWord(str, DateTimeString.GMTName))
        {
            return (true);
        }

        if (matchWord(str, DateTimeString.ZuluName))
        {
            return (true);
        }

        return (false);
    }

    //
    // Search from the index of str at str.Index to see if the target string exists in the str.
    //
    private static boolean matchWord(DateTimeString str, String target)
    {
        int length = target.length();
        if (length > (str.getValue().length() - str.getIndex()))
        {
            return false;
        }

        if (str.compare(str.getValue(), str.getIndex(), length, target, 0, length, true) != 0)
        {
            return (false);
        }

        int nextCharIndex = str.getIndex() + target.length();

        if (nextCharIndex < str.getValue().length())
        {
            char nextCh = str.getValue().charAt(nextCharIndex);
            if (Character.isLetter(nextCh))
            {
                return (false);
            }
        }
        str.setIndex(nextCharIndex);
        if (str.getIndex() < str.getLen())
        {
            str.setCurrent(str.getValue().charAt(str.getIndex()));
        }

        return (true);
    }

    /*=================================MatchEraName==================================
    **Action: Parse era name from string starting at str.Index.
    **Returns: An era value.
    **Arguments:    str: a __DTString.  The parsing will start from the
    **              next character after str.Index.
    **Exceptions: FormatException if an era name can not be found.
    ==============================================================================*/
    private static int matchEraName(DateTimeString str, msDateTimeFormatInfo dtfi)
    {
        int result = -1;
        if (str.getNext())
        {
            String[] eras = dtfi.getEraNames();

            if (eras != null)
            {
                for (int i = 0; i < eras.length; i++)
                {
                    String searchStr = dtfi.getEraName(i);
                    if (str.matchSpecifiedWord(searchStr))
                    {
                        str.setIndex(str.getIndex() + searchStr.length() - 1);
                        result = i;
                        return (result);
                    }
                    searchStr = dtfi.getAbbreviatedEraName(i);

                    if (str.matchSpecifiedWord(searchStr))
                    {
                        str.setIndex(str.getIndex() + searchStr.length() - 1);
                        result = i;
                        return (result);
                    }
                }
            }
        }
        return result;
    }

    /*=================================ParseTimeZoneOffset==================================
        **Action: Parse the string formatted using "z", "zz", "zzz" in DateTime.Format().
        **Returns: the TimeSpan for the parsed timezone offset.
        **Arguments:    str: a __DTString.  The parsing will start from the
        **              next character after str.Index.
        **              len: the repeated number of the "z"
        **Exceptions: FormatException if errors in parsing.
        ==============================================================================*/
    private static TimeSpan parseTimeZoneOffset(DateTimeString str, int len)
    {
        TimeSpan result = TimeSpan.ZERO;
        boolean isPositive = true;
        int hourOffset;
        int minuteOffset = 0;

        switch (len)
        {
            case 1:
            case 2:

                try
                {
                    isPositive = parseSign(str);
                }
                catch (Exception e)
                {
                    Log.warn(e);

                    return null;
                }

                if ((hourOffset = parseDigits(str, len)) != -1)
                {
                    return (result);
                }
                break;

            default:

                try
                {
                    isPositive = parseSign(str);
                }
                catch (Exception e)
                {
                    Log.warn(e);

                    return null;
                }

                // Parsing 1 digit will actually parse 1 or 2.
                if ((hourOffset = parseDigits(str, 1)) == -1)
                {
                    return (result);
                }
                // ':' is optional.
                if (str.match(':'))
                {
                    // Found ':'
                    if ((minuteOffset = parseDigits(str, 2)) == -1)
                    {
                        return (result);
                    }
                }
                else
                {
                    // Since we can not match ':', put the char back.
                    str.setIndex(str.getIndex() - 1);
                    if ((minuteOffset = parseDigits(str, 2)) == -1)
                    {
                        return (result);
                    }
                }
                break;
        }
        if (minuteOffset < 0 || minuteOffset >= 60)
        {
            return result;
        }

        result = (new TimeSpan(hourOffset, minuteOffset, 0));

        if (!isPositive)
        {
            result = result.negate();
        }
        return (result);
    }

    /*=================================ParseSign==================================
    **Action: Parse a positive or a negative sign.
    **Returns:      true if postive sign.  flase if negative sign.
    **Arguments:    str: a __DTString.  The parsing will start from the
    **              next character after str.Index.
    **Exceptions:   FormatException if end of string is encountered or a sign
    **              symbol is not found.
    ==============================================================================*/
    private static boolean parseSign(DateTimeString str) throws Exception
    {
        if (!str.getNext())
        {
            // A sign symbol ('+' or '-') is expected. However, end of string is encountered.
            throw new IllegalStateException(" A sign symbol ('+' or '-') is expected. However, end of string is encountered.");
        }
        char ch = (char) str.getChar();
        if (ch == '+')
        {
            return (true);
        }
        else if (ch == '-')
        {
            return (false);
        }
        else
        {
            // @nixspirit Just an experiment.
            // Let's try and find '+' or '-' if we can't the exception above occurs.
            return parseSign(str);
        }
    }

    /*=================================MatchDayName==================================
        **Action: Parse the day of week name from string starting at str.Index.
        **Returns: A value from 0 to 6 indicating Sunday to Saturday.
        **Arguments:    str: a __DTString.  The parsing will start from the
        **              next character after str.Index.
        **Exceptions: FormatException if a day of week name can not be found.
        ==============================================================================*/
    private static int matchDayName(DateTimeString str, msDateTimeFormatInfo dtfi)
    {
        // Turkish (tr-TR) got day names with the same prefix.
        int maxMatchStrLen = 0;
        int result = -1;
        if (str.getNext())
        {
            for (int i = 0; i <= 6; i++)
            {
                String searchStr = dtfi.getDayName(i);
                int matchStrLen = searchStr.length();
                //matchStrLen = dtfi.hasSpacesInDayNames ? str.matchSpecifiedWords(searchStr, false) : str.matchSpecifiedWord(searchStr);

                if (matchStrLen > maxMatchStrLen)
                {
                    maxMatchStrLen = matchStrLen;
                    result = (int) i;
                }

            }
        }
        if (result >= 0)
        {
            str.setIndex(str.getIndex() + maxMatchStrLen - 1);
            return (result);
        }

        return result;
    }

    /*=================================MatchTimeMark==================================
        **Action: Parse the time mark (AM/PM) from string starting at str.Index.
        **Returns: TM_AM or TM_PM.
        **Arguments:    str: a __DTString.  The parsing will start from the
        **              next character after str.Index.
        **Exceptions: FormatException if a time mark can not be found.
        ==============================================================================*/
    private static int matchTimeMark(DateTimeString str, msDateTimeFormatInfo dtfi)
    {
        int result = DateTimeString.TM.NotSet;
        // In some cultures have empty strings in AM/PM mark. E.g. af-ZA (0x0436), the AM mark is "", and PM mark is "nm".
        if (dtfi.getAMDesignator().length() == 0)
        {
            result = DateTimeString.TM.AM;
        }
        if (dtfi.getPMDesignator().length() == 0)
        {
            result = DateTimeString.TM.PM;
        }

        if (str.getNext())
        {
            String searchStr = dtfi.getAMDesignator();
            if (searchStr.length() > 0)
            {
                if (str.matchSpecifiedWord(searchStr))
                {
                    // Found an AM timemark with length > 0.
                    str.setIndex(str.getIndex() + searchStr.length() - 1);
                    result = DateTimeString.TM.AM;
                    return (result);
                }
            }
            searchStr = dtfi.getPMDesignator();
            if (searchStr.length() > 0)
            {
                if (str.matchSpecifiedWord(searchStr))
                {
                    // Found a PM timemark with length > 0.
                    str.setIndex(str.getIndex() + searchStr.length() - 1);
                    result = DateTimeString.TM.PM;
                    return (result);
                }
            }
            str.setIndex(str.getIndex() - 1); // Undo the GetNext call.
        }
        if (result != DateTimeString.TM.NotSet)
        {
            // If one of the AM/PM marks is empty string, return the result.
            return (result);
        }
        return result;
    }

    /*=================================MatchAbbreviatedTimeMark==================================
    **Action: Parse the abbreviated time mark (AM/PM) from string starting at str.Index.
    **Returns: TM_AM or TM_PM.
    **Arguments:    str: a __DTString.  The parsing will start from the
    **              next character after str.Index.
    **Exceptions: FormatException if a abbreviated time mark can not be found.
    ==============================================================================*/
    private static int matchAbbreviatedTimeMark(DateTimeString str, msDateTimeFormatInfo dtfi)
    {
        int result = -1;
        // NOTENOTE : the assumption here is that abbreviated time mark is the first
        // character of the AM/PM designator.  If this invariant changes, we have to
        // change the code below.
        if (str.getNext())
        {
            if (str.getChar() == dtfi.getAMDesignator().charAt(0))
            {
                result = DateTimeString.TM.AM;
                return (result);
            }
            if (str.getChar() == dtfi.getPMDesignator().charAt(0))
            {
                result = DateTimeString.TM.PM;
                return (result);
            }
        }
        return result;
    }

    /*=================================MatchMonthName==================================
        **Action: Parse the month name from string starting at str.Index.
        **Returns: A value from 1 to 12 indicating the first month to the twelveth month.
        **Arguments:    str: a __DTString.  The parsing will start from the
        **              next character after str.Index.
        **Exceptions: FormatException if a month name can not be found.
        ==============================================================================*/
    private static int matchMonthName(DateTimeString str, msDateTimeFormatInfo dtfi)
    {
        int maxMatchStrLen = 0;
        int result = -1;
        if (str.getNext())
        {
            //
            // Scan the month names (note that some calendars has 13 months) and find
            // the matching month name which has the max string length.
            // We need to do this because some cultures (e.g. "vi-VN") which have
            // month names with the same prefix.
            //
            int monthsInYear = (dtfi.getMonthName(13).length() == 0 ? 12 : 13);
            for (int i = 1; i <= monthsInYear; i++)
            {
                String searchStr = dtfi.getMonthName(i);
                int matchStrLen = searchStr.length();
                //matchStrLen = dtfi.hasSpacesInMonthNames  ? str.matchSpecifiedWords(searchStr, false) : str.matchSpecifiedWord(searchStr);
                {
                    if (matchStrLen > maxMatchStrLen)
                    {
                        maxMatchStrLen = matchStrLen;
                        result = i;
                    }
                }
            }

            /* TODO
            // Search genitive form.
            if ((dtfi.FormatFlags & DateTimeFormatFlags.UseGenitiveMonth) != 0) {
                int tempResult = str.MatchLongestWords(dtfi.MonthGenitiveNames, ref maxMatchStrLen);
                // We found a longer match in the genitive month name.  Use this as the result.
                // The result from MatchLongestWords is 0 ~ length of word array.
                // So we increment the result by one to become the month value.
                if (tempResult >= 0) {
                    result = tempResult + 1;
                }
            }

            // Search leap year form.
            if ((dtfi.FormatFlags & DateTimeFormatFlags.UseLeapYearMonth) != 0) {
                int tempResult = str.MatchLongestWords(dtfi.internalGetLeapYearMonthNames(), ref maxMatchStrLen);
                // We found a longer match in the leap year month name.  Use this as the result.
                // The result from MatchLongestWords is 0 ~ length of word array.
                // So we increment the result by one to become the month value.
                if (tempResult >= 0) {
                    result = tempResult + 1;
                }
            }
            */


        }

        if (result > 0)
        {
            str.setIndex(str.getIndex() + maxMatchStrLen - 1);
            return (result);
        }
        return result;
    }

    /*=================================MatchAbbreviatedMonthName==================================
    **Action: Parse the abbreviated month name from string starting at str.Index.
    **Returns: A value from 1 to 12 for the first month to the twelveth month.
    **Arguments:    str: a __DTString.  The parsing will start from the
    **              next character after str.Index.
    **Exceptions: FormatException if an abbreviated month name can not be found.
    ==============================================================================*/
    private static int matchAbbreviatedMonthName(DateTimeString str, msDateTimeFormatInfo dtfi)
    {
        int maxMatchStrLen = 0;
        int result = -1;
        if (str.getNext())
        {
            //
            // Scan the month names (note that some calendars has 13 months) and find
            // the matching month name which has the max string length.
            // We need to do this because some cultures (e.g. "cs-CZ") which have
            // abbreviated month names with the same prefix.
            //
            int monthsInYear = (dtfi.getMonthName(13).length() == 0 ? 12 : 13);

            for (int i = 1; i <= monthsInYear; i++)
            {
                String searchStr = dtfi.getAbbreviatedMonthName(i);
                int matchStrLen = searchStr.length();

                //if ( dtfi.HasSpacesInMonthNames ? str.MatchSpecifiedWords(searchStr, false, ref matchStrLen) : str.MatchSpecifiedWord(searchStr)) {

                matchStrLen = str.matchSpecifiedWords(searchStr, false);
                if (matchStrLen != -1)
                {
                    if (matchStrLen > maxMatchStrLen)
                    {
                        maxMatchStrLen = matchStrLen;
                        result = i;
                    }
                }
            }
            /* TODO
           // Search leap year form.
           if ((dtfi.FormatFlags & DateTimeFormatFlags.UseLeapYearMonth) != 0)
           {
               int tempResult = str.MatchLongestWords(dtfi.internalGetLeapYearMonthNames(), maxMatchStrLen);
               // We found a longer match in the leap year month name.  Use this as the result.
               // The result from MatchLongestWords is 0 ~ length of word array.
               // So we increment the result by one to become the month value.
               if (tempResult >= 0)
               {
                   result = tempResult + 1;
               }
           } */


        }
        if (result > 0)
        {
            str.setIndex(str.getIndex() + maxMatchStrLen - 1);
            return (result);
        }
        return result;
    }

    /*=================================MatchAbbreviatedDayName==================================
    **Action: Parse the abbreviated day of week name from string starting at str.Index.
    **Returns: A value from 0 to 6 indicating Sunday to Saturday.
    **Arguments:    str: a __DTString.  The parsing will start from the
    **              next character after str.Index.
    **Exceptions: FormatException if a abbreviated day of week name can not be found.
    ==============================================================================*/
    private static int matchAbbreviatedDayName(DateTimeString str, msDateTimeFormatInfo dtfi)
    {
        int result = -1;
        int maxMatchStrLen = 0;
        if (str.getNext())
        {
            for (int i = 0; i <= 6; i++)
            {
                String searchStr = dtfi.getAbbreviatedDayName(i);
                int matchStrLen = searchStr.length();
                //matchStrLen = dtfi.hasSpacesInDayNames ? str.matchSpecifiedWords(searchStr, false) : str.matchSpecifiedWord(searchStr);

                matchStrLen = str.matchSpecifiedWords(searchStr, false);
                if (matchStrLen > maxMatchStrLen)
                {
                    maxMatchStrLen = matchStrLen;
                    result = i;
                }
            }
        }
        if (result >= 0)
        {
            str.setIndex(str.getIndex() + maxMatchStrLen - 1);
            return (result);
        }
        return result;
    }

    //JAVA-changed to remove out int returnValue: returns (returnValue) -1 instead of boolean false.
    //
    // The pos should point to a quote character. This method will
    // get the string encloed by the quote character.
    //
    private static int tryParseQuoteString(String format, int pos, StringBuilder result)
    {
        //
        // NOTE : pos will be the index of the quote character in the 'format' string.
        //
        int returnValue = 0;
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
                {
                    result.append(format.charAt(pos++));
                }
                else
                {
                    //
                    // This means that '\' is at the end of the formatting string.
                    //
                    return -1;
                }
            }
            else
            {
                result.append(ch);
            }
        }

        if (!foundQuote)
        {
            // Here we can't find the matching quote.
            return -1;
        }

        //
        // Return the character count including the begin/end quote characters and enclosed string.
        //
        returnValue = (pos - beginPos);
        return returnValue;
    }

    /*=================================ParseFractionExact==================================
    **Action: Parse the number string in __DTString that are formatted using
    **        the following patterns:
    **        "0", "00", and "000..0"
    **Returns: the fraction value
    **Arguments:    str: a __DTString.  The parsing will start from the
    **              next character after str.Index.
    **Exceptions: FormatException if error in parsing number.
    ==============================================================================*/
    private static double parseFractionExact(DateTimeString str, int maxDigitLen)
    {
        double result = -1;
        if (!str.getNextDigit())
        {
            str.setIndex(str.getIndex() - 1);
            return result;
        }
        result = str.getDigit();

        int digitLen = 1;
        for (; digitLen < maxDigitLen; digitLen++)
        {
            if (!str.getNextDigit())
            {
                str.setIndex(str.getIndex() - 1);
                break;
            }
            result = result * 10 + str.getDigit();
        }

        if (digitLen != maxDigitLen)
            return -1;

        result = (result / Math.pow(10, digitLen));
        return result;
    }

    /*=================================CheckNewValue==================================
    **Action: Check if currentValue is initialized.  If not, return the newValue.
    **        If yes, check if the current value is equal to newValue.  Return false
    **        if they are not equal.  This is used to check the case like "d" and "dd" are both
    **        used to format a string.
    **Returns: the correct value for currentValue.
    **Arguments:
    **Exceptions:
    ==============================================================================*/
    private static int checkNewValue(int currentValue, int newValue, char patternChar, DateTimeString.DateTimeResult result)
    {
        if (currentValue == -1)
        {
            return newValue;
        }
        else
        {
            if (newValue != currentValue)
            {
                return currentValue;
            }
        }
        return currentValue;
    }

    // Expand a pre-defined format string (like "D" for long date) to the real format that
    // we are going to use in the date time parsing.
    // This method also set the dtfi according/parseInfo to some special pre-defined
    // formats.
    //
    private static String expandPredefinedFormat(String format, msDateTimeFormatInfo dtfi, DateTimeString.ParsingInfo parseInfo, DateTimeString.DateTimeResult result)
    {
        //
        // Check the format to see if we need to override the dtfi to be InvariantInfo,
        // and see if we need to set up the userUniversalTime flag.
        //
        switch (format.charAt(0))
        {
            case 'o':
            case 'O':       // Round Trip Format
                parseInfo.setCalendar(GregorianCalendar.getDefaultInstance());
                dtfi = msDateTimeFormatInfo.getInvariantInfo();
                break;
            case 'r':
            case 'R':       // RFC 1123 Standard.  (in Universal time)
                parseInfo.setCalendar(GregorianCalendar.getDefaultInstance());
                dtfi = msDateTimeFormatInfo.getInvariantInfo();

                if ((result.getFlags() & DateTimeString.ParseFlags.CaptureOffset) != 0)
                {
                    result.setFlags(result.getFlags() | DateTimeString.ParseFlags.Rfc1123Pattern);
                }
                break;
            case 's':       // Sortable format (in local time)
                dtfi = msDateTimeFormatInfo.getInvariantInfo();
                parseInfo.setCalendar(GregorianCalendar.getDefaultInstance());
                //parseInfo.calendar = GregorianCalendar.GetDefaultInstance();
                break;
            case 'u':       // Universal time format in sortable format.
                parseInfo.setCalendar(GregorianCalendar.getDefaultInstance());
                dtfi = msDateTimeFormatInfo.getInvariantInfo();

                if ((result.getFlags() & DateTimeString.ParseFlags.CaptureOffset) != 0)
                {
                    result.setFlags(result.getFlags() | DateTimeString.ParseFlags.UtcSortPattern);
                }
                break;
            case 'U':       // Universal time format with culture-dependent format.
                parseInfo.setCalendar(GregorianCalendar.getDefaultInstance());
                result.setFlags(result.getFlags() | DateTimeString.ParseFlags.TimeZoneUsed);
                result.setTimeZoneOffset(new TimeSpan(0));
                result.setFlags(result.getFlags() | DateTimeString.ParseFlags.TimeZoneUtc);

                if (dtfi.getCalendar().getID() != Calendar.CAL_GREGORIAN)
                {
                    dtfi = (msDateTimeFormatInfo)dtfi.deepClone();
                    dtfi.setCalendar(GregorianCalendar.getDefaultInstance());
                }
                break;
        }

        //
        // Expand the pre-defined format character to the real format from DateTimeFormatInfo.
        //
        try
        {
            return (DateTimeFormat.getRealFormat(format, dtfi));
        }
        catch (Exception e)
        {
            Log.warn(e);
        }

        //JAVA-added:
        // ??? TODO: return dtfi
        return null;
    }

    public static DateTime tryParseExact(String s, String format)
    {
        return tryParseExact(s, format, new msDateTimeFormatInfo(CurrentThread.getCurrentCulture()));
    }

    public static DateTime tryParseExactMultiple(String s, String[] formats, msDateTimeFormatInfo dtfi, int style)
    {
        //
        // Do a loop through the provided formats and see if we can parse successfully in
        // one of the formats.
        //
        for (int i = 0; i < formats.length; i++)
        {
            if (formats[i] == null || formats[i].length() == 0)
            {
                return DateTime.MinValue;
            }

            // Create a new result each time to ensure the runs are independent. Carry through
            // flags from the caller and return the result.
            DateTime dt = tryParseExact(s, formats[i], dtfi, style);
            if (dt != DateTime.MinValue)
                return dt;
        }
        return DateTime.MinValue;
    }

    /**
     * This method is analog of .Net's DateTime.TryParse.
     * It is manually ported from .net according to .Net's sources.
     */
    private static DateTime tryParseDotNet(String s, msDateTimeFormatInfo dtfi, int styles)
    {
        // Emulate .NET behaviour here.
        if(s == null)
            return DateTime.MinValue;

        DateTime time = DateTime.MinValue;

        DateTimeString str = new DateTimeString(s, dtfi);

        DateTimeString.DateTimeResult result = new DateTimeString.DateTimeResult();  // The buffer to store the parsing result.
        result.init();

        DateTimeString.DateTimeRawInfo raw = new DateTimeString.DateTimeRawInfo();
        raw.init(new int[12]);

        result.setCalendar(dtfi.getCalendar());
        result.setEra(0);

        int dps = DateTimeString.DS.BEGIN;
        boolean reachTerminalState = false;

        DateTimeString.DateTimeToken dtok = new DateTimeString.DateTimeToken();
        dtok.setSuffix(DateTimeString.TokenType.SEP_Unk);

        str.getNext();

        do
        {
            if (!DateTimeString.lex(dps, str, dtok, raw, result, dtfi))
                return DateTime.MinValue;

            if (dtok.getDtt() != DateTimeString.DTT.Unk)
            {

                if (dtok.getSuffix() != DateTimeString.TokenType.SEP_Unk)
                {
                    if (!processDateTimeSuffix(result, raw, dtok))
                    {
                        return DateTime.MinValue;
                    }

                    dtok.setSuffix(DateTimeString.TokenType.SEP_Unk);  // Reset suffix to SEP_Unk;
                }

                if (dtok.getDtt() == DateTimeString.DTT.NumLocalTimeMark)
                {
                    if (dps == DateTimeString.DS.D_YNd || dps == DateTimeString.DS.D_YN)
                    {
                        // Consider this as ISO 8601 format:
                        // "yyyy-MM-dd'T'HH:mm:ss"                 1999-10-31T02:00:00
                        return parseISO8601(raw, str, styles, result);
                    }
                    else
                    {
                        return DateTime.MinValue;
                    }
                }

                dps = DateTimeString.dateParsingStates[dps][dtok.getDtt()];

                if (dps == DateTimeString.DS.ERROR)
                    return DateTime.MinValue;

                else if (dps > DateTimeString.DS.ERROR)
                {
                    if ((dtfi.formatFlags & DateTimeFormatFlags.UseHebrewRule) != DateTimeFormatFlags.None)
                    {
                        if (!processHebrewTerminalState(dps, result, styles, raw, dtfi))
                        {
                            return DateTime.MinValue;
                        }
                    }
                    else if (!processTerminalState(dps, result, styles, raw, dtfi))
                        return DateTime.MinValue;

                    reachTerminalState = true;
                    dps = DateTimeString.DS.BEGIN;
                }
            }
        }
        while (dtok.getDtt() != DateTimeString.DTT.End && dtok.getDtt() != DateTimeString.DTT.NumEnd && dtok.getDtt() != DateTimeString.DTT.MonthEnd);

        if (!reachTerminalState)
        {
            return DateTime.MinValue;
        }

        adjustTimeMark(dtfi, raw);
        if (!AdjustHour(result, raw))
        {
            return DateTime.MinValue;
        }

        boolean bTimeOnly = ((result.getYear() == -1) && (result.getMonth() == -1)) && (result.getDay() == -1);
        if (!checkDefaultDateTime(result, styles))
        {
            return DateTime.MinValue;
        }

        //JAVA-changed to workaround out time param.
        time = result.getCalendar().tryToDateTime(result.getYear(), result.getMonth(), result.getDay(),
                result.getHour(), result.getMinute(), result.getSecond(), 0, result.getEra(), time);
        if (time == DateTime.MinValue)
        {
            return DateTime.MinValue;
        }

        if (raw.getFraction() > 0.0)
            time = time.addTicks(Math.round(raw.getFraction() * Calendar.TICKS_PER_SECOND));

        //
        // We have to check day of week before we adjust to the time zone.
        // Otherwise, the value of day of week may change after adjustting to the time zone.
        //
        if (raw.getDayOfWeek() != -1)
        {
            //
            // Check if day of week is correct.
            //
            if (raw.getDayOfWeek() != (int) result.getCalendar().getDayOfWeek(time)) {
                //result.SetFailure(ParseFailureKind.Format, "Format_BadDayOfWeek", null);
                return DateTime.MinValue;
            }
        }

        result.setParsedDate(time);

        if (!determineTimeZoneAdjustments(result, styles, bTimeOnly))
        {
            return DateTime.MinValue;
        }

        return result.getParsedDate();
    }

    private static boolean AdjustHour(DateTimeString.DateTimeResult result, DateTimeString.DateTimeRawInfo raw)
    {
        int hour = result.getHour();
        int timeMark = raw.getTimeMark();

        if (timeMark != DateTimeString.TM.NotSet)
        {
            if (timeMark == DateTimeString.TM.AM)
            {
                if ((hour < 0) || (hour > 12))
                    return false;

                hour = (hour == 12) ? 0 : hour;
            }
            else
            {
                if ((hour < 0) || (hour > 0x17))
                    return false;

                if (hour < 12)
                    hour += 12;
            }
        }
        result.setHour(hour);
        raw.setTimeMark(timeMark);
        return true;
    }

    private static boolean determineTimeZoneAdjustments(DateTimeString.DateTimeResult result, int styles, boolean bTimeOnly)
    {
        if ((result.getFlags() & DateTimeString.ParseFlags.CaptureOffset) != 0)
            return dateTimeOffsetTimeZonePostProcessing(result, styles);

        if ((result.getFlags() & DateTimeString.ParseFlags.TimeZoneUsed) == 0)
        {
            if ((styles & DateTimeStyles.ASSUME_LOCAL) != DateTimeStyles.NONE)
            {
                if ((styles & DateTimeStyles.ADJUST_TO_UNIVERSAL) != DateTimeStyles.NONE)
                {
                    result.setFlags(result.getFlags() | DateTimeString.ParseFlags.TimeZoneUsed);
                    //JAVA-changed from:
                    //result.timeZoneOffset = TimeZone.CurrentTimeZone.GetUtcOffset(result.parsedDate);
                    result.setTimeZoneOffset(CurrentThread.getUtcOffsetFromCurrentTimeZone(result.getParsedDate()));
                }
                else
                {
                    result.setParsedDate(DateTime.specifyKind(result.getParsedDate(), (int) DateTimeKind.LOCAL));
                    return true;
                }
            }
            else if ((styles & DateTimeStyles.ASSUME_UNIVERSAL) != DateTimeStyles.NONE)
            {
                if ((styles & DateTimeStyles.ADJUST_TO_UNIVERSAL) != DateTimeStyles.NONE)
                {
                    result.setParsedDate(DateTime.specifyKind(result.getParsedDate(), (int) DateTimeKind.UTC));
                    return true;
                }
                else
                {
                    result.setFlags(result.getFlags() | DateTimeString.ParseFlags.TimeZoneUsed);
                    result.setTimeZoneOffset(TimeSpan.ZERO);
                }
            }
            else
            {
                // No time zone and no Assume flags, so DateTimeKind.Unspecified is fine
                //BCLDebug.Assert(result.parsedDate.Kind == DateTimeKind.Unspecified, "result.parsedDate.Kind == DateTimeKind.Unspecified");
                return true;
            }
        }

        if (((styles & DateTimeStyles.ROUNDTRIP_KIND) != DateTimeStyles.NONE) && ((result.getFlags() & DateTimeString.ParseFlags.TimeZoneUtc) != 0))
        {
            // WORDSJAVA-2178
            // result.parsedDate = DateTime.specifyKind(result.parsedDate, (int) DateTimeKind.UTC);
            return true;
        }

        if ((styles & DateTimeStyles.ADJUST_TO_UNIVERSAL) != DateTimeStyles.NONE && isNeedAdjustTimeZoneInZulu(result))
            return adjustTimeZoneToUniversal(result);

        if ((styles & DateTimeStyles.ROUNDTRIP_KIND) != DateTimeStyles.NONE)
           {
               // JAVA-added
               // WORDSJAVA-2178
               long resultTicks = result.getParsedDate().getTicks();

               resultTicks -= result.getTimeZoneOffset().getTicks();

               // If the time is time of day, use the current timezone offset.
               resultTicks += CurrentThread.getUtcOffsetFromCurrentTimeZone(bTimeOnly ? DateTime.getNow() : result.getParsedDate()).getTicks();

               if (resultTicks < 0)
                   resultTicks += Calendar.TICKS_PER_DAY;

               result.setParsedDate(new DateTime(resultTicks, DateTimeKind.LOCAL));
               return true;
           }

        // WORDSJAVA-2178
        if (result.getTimeZoneOffset() != null && (result.getTimeZoneOffset().getHours() > 0 || result.getTimeZoneOffset().getMinutes() > 0))
            return adjustTimeZoneToLocal(result, bTimeOnly);

            // Added: we lost hours if just use adjustTimeZoneToLocal() for DateTimeStyles.NONE styles.
        if (styles == DateTimeStyles.NONE)
           return true;

        return adjustTimeZoneToLocal(result, bTimeOnly);
    }

    // WORDSJAVA-2605: When the time is in Z format, the local time plus the time offset may be
    // returned in such cases (see TestJava2605()).
    private static boolean isNeedAdjustTimeZoneInZulu(DateTimeString.DateTimeResult value)
    {
        return value.getTimeZoneOffset().getTicks() != 0 ||
                value.getYear() == -1 || value.getMonth() == -1 || value.getDay() == -1 ||
                ((value.getFlags() & DateTimeString.ParseFlags.TimeZoneUsed) == 0) || ((value.getFlags() & DateTimeString.ParseFlags.TimeZoneUtc) == 0);
    }

    // Apply validation and adjustments specific to DateTimeOffset
    private static boolean dateTimeOffsetTimeZonePostProcessing(DateTimeString.DateTimeResult result, int styles)
    {
        // For DateTimeOffset, default to the Utc or Local offset when an offset was not specified by
        // the input string.
        if ((result.getFlags() & DateTimeString.ParseFlags.TimeZoneUsed) == 0)
        {
            if ((styles & DateTimeStyles.ASSUME_UNIVERSAL) != 0)
            {
                // AssumeUniversal causes the offset to default to zero (0)
                result.setTimeZoneOffset(TimeSpan.ZERO);
            }
            else
                {
                // AssumeLocal causes the offset to default to Local.  This flag is on by default for DateTimeOffset.
                //JAVA-changed from:
                //result.timeZoneOffset = TimeZone.CurrentTimeZone.GetUtcOffset(result.parsedDate);
                result.setTimeZoneOffset(CurrentThread.getUtcOffsetFromCurrentTimeZone(result.getParsedDate()));
            }
        }

        long offsetTicks = result.getTimeZoneOffset().getTicks();

        // there should be no overflow, because the offset can be no more than -+100 hours and the date already
        // fits within a DateTime.
        long utcTicks = result.getParsedDate().getTicks() - offsetTicks;

        // For DateTimeOffset, both the parsed time and the corresponding UTC value must be within the boundaries
        // of a DateTime instance.
        if (utcTicks < DateTime.MinTicks || utcTicks > DateTime.MaxTicks) {
            //result.SetFailure(ParseFailureKind.Format, "Format_UTCOutOfRange", null);
            return false;
        }

        // For a DateTime offset, the offset must be within +- 14:00 hours. For the regular DateTime.Parse it can
        // be up to +-99:59. This was unintentional behavior that must be retained on DateTime for compatibility
        // but should not apply to DateTimeOffset.
        if (offsetTicks < DateTimeOffset.MIN_OFFSET || offsetTicks > DateTimeOffset.MAX_OFFSET)
        {
            //result.SetFailure(ParseFailureKind.Format, "Format_OffsetOutOfRange", null);
            return false;
        }

        // DateTimeOffset should still honor the AdjustToUniversal flag for consistency with DateTime. It means you
        // want to return an adjusted UTC value, so store the utcTicks in the DateTime and set the offset to zero
        if ((styles & DateTimeStyles.ADJUST_TO_UNIVERSAL) != 0)
        {
            if (((result.getFlags() & DateTimeString.ParseFlags.TimeZoneUsed) == 0) && ((styles & DateTimeStyles.ASSUME_UNIVERSAL) == 0))
            {
                // Handle the special case where the timeZoneOffset was defaulted to Local
                Boolean toUtcResult = adjustTimeZoneToUniversal(/*ref*/ result);
                result.setTimeZoneOffset(TimeSpan.ZERO);
                return toUtcResult;
            }

            // The constructor should always succeed because of the range check earlier in the function
            // Althought it is UTC, internally DateTimeOffset does not use this flag
            result.setParsedDate(new DateTime(utcTicks, DateTimeKind.UTC));
            result.setTimeZoneOffset(TimeSpan.ZERO);
        }

        return true;
    }

    //
    // Adjust the specified time to universal time based on the supplied timezone,
    // and then convert to local time.
    // E.g. when parsing "2001/06/08 14:00-04:00", and local timezone is GMT-7.
    // the time is 2001/06/08 14:00, and timeZoneOffset = -05:00.
    // The result will be "2001/06/08 11:00"
    //
    private static Boolean adjustTimeZoneToLocal(/*ref*/ DateTimeString.DateTimeResult result, boolean bTimeOnly)
    {
        long resultTicks = result.getParsedDate().getTicks();
        // Convert to local ticks
        //JAVA-changed: ThreadLocalTimeZone used instead
        //CurrentSystemTimeZone tz = (CurrentSystemTimeZone)TimeZone.CurrentTimeZone;

        Boolean isAmbiguousLocalDst = false;

        // WORDSJAVA-2178
        boolean hasTimeZoneOffet = (result.getTimeZoneOffset() != null && (result.getTimeZoneOffset().getHours() > 0 || result.getTimeZoneOffset().getMinutes() > 0));

        if (resultTicks < Calendar.TICKS_PER_DAY || hasTimeZoneOffet)
        {
            //
            // This is time of day.
            //

            // Adjust timezone.
            resultTicks -= result.getTimeZoneOffset().getTicks();
            // If the time is time of day, use the current timezone offset.
            resultTicks += CurrentThread.getUtcOffsetFromCurrentTimeZone(bTimeOnly ? DateTime.getNow() : result.getParsedDate()).getTicks();

            if (resultTicks < 0)
                resultTicks += Calendar.TICKS_PER_DAY;
        }
        else
        {
            // Adjust timezone to GMT.
            resultTicks -= result.getTimeZoneOffset().getTicks();
            if (resultTicks < DateTime.MinTicks || resultTicks > DateTime.MaxTicks)
            {
                // If the result ticks is greater than DateTime.MaxValue, we can not create a DateTime from this ticks.
                // In this case, keep using the old code.
                resultTicks += CurrentThread.getUtcOffsetFromCurrentTimeZone(result.getParsedDate()).getTicks();
            }
            else
            {
                //JAVA-deleted. Anyway, (new DateTime(resultTicks) will throw if
                // Convert the GMT time to local time.
                //resultTicks += ThreadLocalTimeZone.GetUtcOffsetFromUniversalTime(new DateTime(resultTicks), ref isAmbiguousLocalDst);
            }
        }
        if (resultTicks < DateTime.MinTicks || resultTicks > DateTime.MaxTicks)
        {
            result.setParsedDate(DateTime.MinValue);
            //result.SetFailure(ParseFailureKind.Format, "Format_DateOutOfRange", null);
            return false;
        }
        //result.parsedDate = new DateTime(resultTicks, DateTimeKind.LOCAL, isAmbiguousLocalDst);
        result.setParsedDate(new DateTime(resultTicks, DateTimeKind.LOCAL));
        return true;
    }

    private static boolean adjustTimeZoneToUniversal(DateTimeString.DateTimeResult result)
    {
        long ticks = result.getParsedDate().getTicks() - result.getTimeZoneOffset().getTicks();
        if (ticks < 0L)
            ticks += 0xc92a69c000L;

        if ((ticks < 0L) || (ticks > 0x2bca2875f4373fffL))
            return false;

        result.setParsedDate(new DateTime(ticks, DateTimeKind.UTC));
        return true;
    }

    private static boolean checkDefaultDateTime(DateTimeString.DateTimeResult result, int styles)
    {
        if (((result.getYear() == -1) || (result.getMonth() == -1)) || (result.getDay() == -1))
        {
            DateTime dateTimeNow = getDateTimeNow(result);
            if ((result.getMonth() == -1) && (result.getDay() == -1))
            {
                if (result.getYear() == -1)
                {
                    if ((styles & DateTimeStyles.NO_CURRENT_DATE_DEFAULT) != 0)
                    {
                        result.setYear(1);
                        result.setMonth(1);
                        result.setDay(1);
                    }
                    else
                    {
                        result.setYear(dateTimeNow.getYear());
                        result.setMonth(dateTimeNow.getMonth());
                        result.setDay(dateTimeNow.getDay());
                    }
                }
                else
                {
                    result.setMonth(1);
                    result.setDay(1);
                }
            }
            else
            {
                if (result.getYear() == -1)
                    result.setYear(dateTimeNow.getYear());
                if (result.getMonth() == -1)
                    result.setMonth(1);
                if (result.getDay() == -1)
                    result.setDay(1);
            }
        }
        if (result.getHour() == -1)
            result.setHour(0);
        if (result.getMinute() == -1)
            result.setMinute(0);
        if (result.getSecond() == -1)
            result.setSecond(0);
        if (result.getEra() == -1)
            result.setEra(0);
        return true;
    }

    private static DateTime getDateTimeNow(DateTimeString.DateTimeResult result)
    {
        return DateTime.getNow();
    }

    private static void adjustTimeMark(msDateTimeFormatInfo dtfi, DateTimeString.DateTimeRawInfo raw)
    {
        // Specail case for culture which uses AM as empty string.
        // E.g. af-ZA (0x0436)
        //    S1159                  \x0000
        //    S2359                  nm
        // In this case, if we are parsing a string like "2005/09/14 12:23", we will assume this is in AM.

        if (raw.getTimeMark() == DateTimeString.TM.NotSet)
        {
            if (dtfi.getAMDesignator() != null && dtfi.getPMDesignator() != null)
            {
                if (dtfi.getAMDesignator().length() == 0 && dtfi.getPMDesignator().length() != 0)
                    raw.setTimeMark(DateTimeString.TM.AM);

                if (dtfi.getPMDesignator().length() == 0 && dtfi.getAMDesignator().length() != 0)
                    raw.setTimeMark(DateTimeString.TM.PM);
            }
        }
    }

    private static DateTime parseISO8601(DateTimeString.DateTimeRawInfo raw, DateTimeString str, int styles, DateTimeString.DateTimeResult result)
    {
        int num2;
        DateTime time;
        if ((raw.getYear() >= 0) && (raw.getNumber(0) >= 0))
            raw.getNumber(1);

        str.setIndex(str.getIndex() - 1);
        int num3 = 0;
        double num4 = 0.0;
        str.skipWhiteSpaces();
        int num = parseDigits(str, 2);
        if (num == -1)
        {
            return DateTime.MinValue;
        }

        str.skipWhiteSpaces();
        if (!str.match(':'))
            return DateTime.MinValue;

        str.skipWhiteSpaces();
        num2 = parseDigits(str, 2);
        if (num2 == -1)
        {
            return DateTime.MinValue;
        }

        str.skipWhiteSpaces();
        if (str.match(':'))
        {
            str.skipWhiteSpaces();
            num3 = parseDigits(str, 2);
            if (num3 == -1)
            {
                return DateTime.MinValue;
            }
            if (str.match('.'))
            {
                num4 = parseFraction(str);
                if (num4 == -1)
                {
                    return DateTime.MinValue;
                }
                str.setIndex(str.getIndex() - 1);
            }
            str.skipWhiteSpaces();
        }

        if (str.getNext())
        {
            switch (str.getChar())
            {
                case '+':
                case '-':
                    result.setFlags(result.getFlags() | DateTimeString.ParseFlags.TimeZoneUsed);
                    TimeSpan ts = parseTimeZone(str);
                    if (ts == null)
                    {
                        return DateTime.MinValue;
                    }
                    result.setTimeZoneOffset(ts);
                    break;

                case 'Z':
                case 'z':
                    result.setFlags(result.getFlags() | DateTimeString.ParseFlags.TimeZoneUsed);
                    result.setTimeZoneOffset(TimeSpan.ZERO);
                    result.setFlags(result.getFlags() | DateTimeString.ParseFlags.TimeZoneUtc);
                    break;

                default:
                    str.setIndex(str.getIndex() - 1);
                    break;
            }
            str.skipWhiteSpaces();
            if (str.match('#'))
            {
                if (!verifyValidPunctuation(str))
                {
                    return DateTime.MinValue;
                }
                str.skipWhiteSpaces();
            }
            if (str.match('\0') && !verifyValidPunctuation(str))
            {
                return DateTime.MinValue;
            }
            if (str.getNext())
                return DateTime.MinValue;
        }

        try
        {
            time = new DateTime(raw.getYear(), raw.getNumber(0), raw.getNumber(1), num, num2, num3, 0, result.getEra());
            time = time.addTicks(Math.round((num4 * 10000000.0)));
            result.setParsedDate(time);
        }
        catch (Exception e)
        {
            Log.warn(e);

            return DateTime.MinValue;
        }

        if (!determineTimeZoneAdjustments(result, styles, false))
        {
            return DateTime.MinValue;
        }

        return result.getParsedDate();
    }

    /*=================================ParseTimeZone==========================
    **Action: Parse the timezone offset in the following format:
    **          "+8", "+08", "+0800", "+0800"
    **        This method is used by DateTime.Parse().
    **Returns:      The TimeZone offset.
    **Arguments:
    **      str the parsing string
    **Exceptions:
    **      FormatException if invalid timezone format is found.
    ============================================================================*/
    public static TimeSpan parseTimeZone(DateTimeString str)
    {
        // The hour/minute offset for timezone.
        int hours = 0;
        int minutes = 0;
        DateTimeString.DTSubString subString = str.getSubString();
        // Consume the +/- character that has already been read
        if (subString.getLength() != 1)
            return null;

        char ch = subString.getChar(0);
        if ((ch != '+') && (ch != '-'))
            return null;

        str.consumeSubString(subString);
        subString = str.getSubString();
        if (subString.getType() != DateTimeString.DTSubStringType.Number)
            return null;

        int num3 = subString.getValue();
        int length = subString.getLength();
        switch (length)
        {
            case 1:
            case 2:
                hours = num3;
                str.consumeSubString(subString);
                subString = str.getSubString();
                if ((subString.getLength() == 1) && (subString.getChar(0) == ':'))
                {
                    str.consumeSubString(subString);
                    subString = str.getSubString();
                    if (((subString.getType() != DateTimeString.DTSubStringType.Number) || (subString.getLength() < 1)) || (subString.getLength() > 2))
                        return null;

                    minutes = subString.getValue();
                    str.consumeSubString(subString);
                }
                break;

            default:
                if ((length != 3) && (length != 4))
                    return null;

                hours = num3 / 100;
                minutes = num3 % 100;
                str.consumeSubString(subString);
                break;
        }
        if ((minutes < 0) || (minutes >= 60))
        {
            return null;
        }
        TimeSpan result = new TimeSpan(hours, minutes, 0);
        if (ch == '-')
            result = result.negate();

        return result;
    }

    /*=================================ParseFraction==========================
    **Action: Starting at the str.Index, which should be a decimal symbol.
    ** if the current character is a digit, parse the remaining
    **      numbers as fraction.  For example, if the sub-string starting at str.Index is "123", then
    **      the method will return 0.123
    **Returns:      The fraction number.
    **Arguments:
    **      str the parsing string
    **Exceptions:
    ============================================================================*/
    public static double parseFraction(DateTimeString str)
    {
        char ch;
        double result = 0.0;
        double num = 0.1;
        int num2 = 0;
        while (str.getNext() && DateTimeString.isDigit(ch = str.getCurrent()))
        {
            result += (ch - '0') * num;
            num *= 0.1;
            num2++;
        }
        return (num2 > 0) ? result : -1;
    }

    private static boolean verifyValidPunctuation(DateTimeString str)
    {
        char c = str.getValue().charAt(str.getIndex());
        if (c == '#')
        {
            boolean flag = false;
            boolean flag2 = false;
            for (int j = 0; j < str.getLen(); j++)
            {
                c = str.getValue().charAt(j);
                if (c == '#')
                {
                    if (flag)
                    {
                        if (flag2)
                            return false;
                        flag2 = true;
                    }
                    else
                        flag = true;
                }
                else if (c == '\0')
                {
                    if (!flag2)
                        return false;
                }
                else if (!msChar.isWhiteSpace(c) && (!flag || flag2))
                    return false;
            }
            if (!flag2)
                return false;

            str.getNext();
            return true;
        }
        if (c != '\0')
            return false;

        for (int i = str.getIndex(); i < str.getLen(); i++)
        {
            if (str.getValue().charAt(i) != '\0')
                return false;
        }
        str.setIndex(str.getLen());
        return true;
    }

    public static int parseDigits(DateTimeString str, int digitLen)
    {
        if (digitLen == 1)
        {
            return parseDigits(str, 1, 2);
        }
        return parseDigits(str, digitLen, digitLen);
    }

    //JAVA-changed: returns result or -1 if fail, so doesn't use 'out int result'.
    private static int parseDigits(DateTimeString str, int minDigitLen, int maxDigitLen)
    {
        int result = 0;
        int index = str.getIndex();
        int tokenLength = 0;
        while (tokenLength < maxDigitLen)
        {
            if (!str.getNextDigit())
            {
                str.setIndex(str.getIndex() - 1);
                break;
            }
            result = (result * 10) + str.getDigit();
            tokenLength++;
        }
        if (tokenLength < minDigitLen)
        {
            str.setIndex(index);
            return -1;
        }
        return result;
    }

    private static boolean processDateTimeSuffix(DateTimeString.DateTimeResult result, DateTimeString.DateTimeRawInfo raw, DateTimeString.DateTimeToken dtok)
    {
        switch (dtok.getSuffix())
        {
            case DateTimeString.TokenType.SEP_HourSuff:
                if ((result.getFlags() & DateTimeString.ParseFlags.HaveHour) != 0)
                {
                    return false;
                }
                result.setFlags(result.getFlags() | DateTimeString.ParseFlags.HaveHour);
                result.setHour(dtok.getNum());
                break;

            case DateTimeString.TokenType.SEP_MinuteSuff:
                if ((result.getFlags() & DateTimeString.ParseFlags.HaveMinute) != 0)
                {
                    return false;
                }
                result.setFlags(result.getFlags() | DateTimeString.ParseFlags.HaveMinute);
                result.setMinute(dtok.getNum());
                break;

            case DateTimeString.TokenType.SEP_SecondSuff:
                if ((result.getFlags() & DateTimeString.ParseFlags.HaveSecond) != 0)
                {
                    return false;
                }
                result.setFlags(result.getFlags() | DateTimeString.ParseFlags.HaveSecond);
                result.setSecond(dtok.getNum());
                break;

            case DateTimeString.TokenType.SEP_YearSuff:
                if ((result.getFlags() & DateTimeString.ParseFlags.HaveYear) != 0)
                {
                    return false;
                }
                result.setFlags(result.getFlags() | DateTimeString.ParseFlags.HaveYear);
                result.setYear(raw.setYear(dtok.getNum()));
                break;

            case DateTimeString.TokenType.SEP_MonthSuff:
                if ((result.getFlags() & DateTimeString.ParseFlags.HaveMonth) != 0)
                {
                    return false;
                }
                result.setFlags(result.getFlags() | DateTimeString.ParseFlags.HaveMonth);
                result.setMonth(raw.setMonth(dtok.getNum()));
                break;

            case DateTimeString.TokenType.SEP_DaySuff:
                if ((result.getFlags() & DateTimeString.ParseFlags.HaveDay) != 0)
                {
                    return false;
                }
                result.setFlags(result.getFlags() | DateTimeString.ParseFlags.HaveDay);
                result.setDay(dtok.getNum());
                break;
        }
        return true;
    }

    private static boolean processTerminalState(int dps, DateTimeString.DateTimeResult result, int styles, DateTimeString.DateTimeRawInfo raw, msDateTimeFormatInfo dtfi)
    {
        boolean dateOfDSN = true;
        switch (dps)
        {
            case DateTimeString.DS.DX_NN:
                dateOfDSN = getDayOfNN(dtfi, result, styles, raw);
                break;
            case DateTimeString.DS.DX_NNN:
                dateOfDSN = getDayOfNNN(result, raw, dtfi);
                break;
            case DateTimeString.DS.DX_MN:
                dateOfDSN = getDayOfMN(dtfi, result, styles, raw);
                break;
            case DateTimeString.DS.DX_NM:
                dateOfDSN = getDayOfNM(dtfi, result, styles, raw);
                break;
            case DateTimeString.DS.DX_MNN:
                dateOfDSN = getDayOfMNN(result, raw, dtfi);
                break;
            case DateTimeString.DS.DX_DS:
                // The result has got the correct value. No need to process.
                break;
            case DateTimeString.DS.DX_YNN:
                dateOfDSN = getDayOfYNN(dtfi, result, raw);
                break;
            case DateTimeString.DS.DX_NNY:
                dateOfDSN = getDayOfNNY(result, raw, dtfi);
                break;
            case DateTimeString.DS.DX_YMN:
                dateOfDSN = getDayOfYMN(dtfi, result, raw);
                break;
            case DateTimeString.DS.DX_YN:
                dateOfDSN = getDayOfYN(dtfi, result, raw);
                break;
            case DateTimeString.DS.DX_YM:
                dateOfDSN = getDayOfYM(dtfi, result, raw);
                break;
            case DateTimeString.DS.TX_N:
                dateOfDSN = getTimeOfN(dtfi, result, raw);
                break;
            case DateTimeString.DS.TX_NN:
                dateOfDSN = getTimeOfNN(dtfi, result, raw);
                break;
            case DateTimeString.DS.TX_NNN:
                dateOfDSN = getTimeOfNNN(dtfi, result, raw);
                break;
            case DateTimeString.DS.TX_TS:
                break;
            case DateTimeString.DS.DX_DSN:
                dateOfDSN = getDateOfDSN(dtfi, result, styles, raw);
                break;
            case DateTimeString.DS.DX_NDS:
                dateOfDSN = getDateOfNDS(dtfi, result, styles, raw);
                break;
            case DateTimeString.DS.DX_NNDS:
                dateOfDSN = getDateOfNNDS(dtfi, result, styles, raw);
                break;
        }

        if (dps > DateTimeString.DS.ERROR)
        {
            // We have reached a terminal state. Reset the raw num count.
            raw.setNumCount(0);
        }

        return dateOfDSN;
    }

    private static boolean getDateOfNNDS(msDateTimeFormatInfo dtfi, DateTimeString.DateTimeResult result, int styles, DateTimeString.DateTimeRawInfo raw)
    {
        //if ((result.flags & ParseFlags.HaveYear) != 0)
        //  {
        //    if ((((result.flags & ParseFlags.HaveMonth) == 0) && ((result.flags & ParseFlags.HaveDay) == 0)) && setDateYMD(result, result.year = adjustYear( result, raw.year), raw.getNumber(0), raw.getNumber(1)))
        if (raw.getYear() != -1 && result.getDay() == -1 && result.getMonth() == -1 && setDateYMD(result, result.setYear(adjustYear(result, raw.getYear())), raw.getNumber(0), raw.getNumber(1)))
            return true;

            // else if ((((result.flags & ParseFlags.HaveMonth) != 0) && ((result.flags & ParseFlags.HaveYear) == 0)) && ((result.flags & ParseFlags.HaveDay) == 0))
        else if (raw.getYear() != -1 && result.getDay() == -1 && result.getMonth() != -1)
        {
            int num = getYearMonthDayOrder(dtfi);
            if (num == DateTimeString.ORDER_ERROR)
                return false;

            if (num == DateTimeString.ORDER_YMD)
            {
                if (setDateYMD(result, adjustYear(result, raw.getNumber(0)), result.getMonth(), raw.getNumber(1)))
                    return true;
            }
            else if (setDateYMD(result, adjustYear(result, raw.getNumber(1)), result.getMonth(), raw.getNumber(0)))
                return true;
        }
        return false;
    }

    private static boolean getDateOfNDS(msDateTimeFormatInfo dtfi, DateTimeString.DateTimeResult result, int styles, DateTimeString.DateTimeRawInfo raw)
    {
        if (result.getMonth() == -1)
            return false;

        if (result.getYear() != -1)
            return false;

        result.setYear(adjustYear(result, raw.getNumber(0)));
        result.setDay(1);
        return true;
    }

    private static boolean getDateOfDSN(msDateTimeFormatInfo dtfi, DateTimeString.DateTimeResult result, int styles, DateTimeString.DateTimeRawInfo raw)
    {
        if ((raw.getNumCount() != 1) || (result.getDay() != -1))
            return false;
        result.setDay(raw.getNumber(0));
        return true;
    }

    private static boolean getTimeOfN(msDateTimeFormatInfo dtfi, DateTimeString.DateTimeResult result, DateTimeString.DateTimeRawInfo raw)
    {
        if (raw.getTimeMark() == DateTimeString.TM.NotSet)
            return false;

        result.setHour(raw.getNumber(0));
        return true;
    }

    private static boolean getDayOfYM(msDateTimeFormatInfo dtfi, DateTimeString.DateTimeResult result, DateTimeString.DateTimeRawInfo raw)
    {
        if (setDateYMD(result, raw.getYear(), raw.getMonth(), 1))
            return true;

        return false;
    }

    private static boolean getDayOfYN(msDateTimeFormatInfo dtfi, DateTimeString.DateTimeResult result, DateTimeString.DateTimeRawInfo raw)
    {
        if (setDateYMD(result, raw.getYear(), raw.getNumber(0), 1))
            return true;
        return false;
    }

    private static boolean getDayOfNN(msDateTimeFormatInfo dtfi, DateTimeString.DateTimeResult result, int styles, DateTimeString.DateTimeRawInfo raw)
    {

        int number = raw.getNumber(0);
        int day = raw.getNumber(1);
        getDefaultYear(result, styles);
        int num3 = getMonthDayOrder(dtfi.getMonthDayPattern(), dtfi);
        if (num3 == DateTimeString.ORDER_ERROR)
            return false;

        if (num3 == DateTimeString.ORDER_MD)
        {
            if (setDateYMD(result, result.getYear(), number, day))
                return true;
        }
        else if (setDateYMD(result, result.getYear(), day, number))
            return true;

        return false;
    }

    private static boolean getDayOfMNN(DateTimeString.DateTimeResult result, DateTimeString.DateTimeRawInfo raw, msDateTimeFormatInfo dtfi)
    {
        int number = raw.getNumber(0);
        int year = raw.getNumber(1);

        int num4;
        int num3 = getYearMonthDayOrder(dtfi);

        if (num3 == DateTimeString.ORDER_ERROR)
            return false;

        if (num3 == 1)
        {
            num4 = adjustYear(result, year);
            if (isValidDay(num4, raw.getMonth(), number, result.getEra(), result.getCalendar()))
            {
                result.setDate(num4, raw.getMonth(), number);
                return true;
            }

            num4 = adjustYear(result, number);
            if (isValidDay(num4, raw.getMonth(), year, result.getEra(), result.getCalendar()))
            {
                result.setDate(num4, raw.getMonth(), year);
                return true;
            }
        }
        else if (num3 == 0)
        {
            num4 = adjustYear(result, number);
            if (isValidDay(num4, raw.getMonth(), year, result.getEra(), result.getCalendar()))
            {
                result.setDate(num4, raw.getMonth(), year);
                return true;
            }
            if (isValidDay(num4 = adjustYear(result, year), raw.getMonth(), number, result.getEra(), result.getCalendar()))
            {
                result.setDate(num4, raw.getMonth(), number);
                return true;
            }
        }
        else if (num3 == 2)
        {
            num4 = adjustYear(result, year);
            if (isValidDay(num4, raw.getMonth(), number, result.getEra(), result.getCalendar()))
            {
                result.setDate(num4, raw.getMonth(), number);
                return true;
            }
            if (isValidDay(num4 = adjustYear(result, number), raw.getMonth(), year, result.getEra(), result.getCalendar()))
            {
                result.setDate(num4, raw.getMonth(), year);
                return true;
            }
        }
        return false;
    }

    private static boolean isValidDay(int day, int month, int year, int era, Calendar cal)
    {
        return true;
    }

	// The interpretation is based on the MonthDayPattern and YearMonthPattern
	//
	//    MonthDayPattern   YearMonthPattern  Interpretation
	//    ---------------   ----------------  ---------------
	//    MMMM dd           MMMM yyyy         Day
	//    MMMM dd           yyyy MMMM         Year
	//    dd MMMM           MMMM yyyy         Day
	//    dd MMMM           yyyy MMMM         Day
	//
	// In the first and last cases, it could be either or neither, but a day is a better default interpretation
	// than a 2 digit year.
	private static boolean getDayOfNM(msDateTimeFormatInfo dtfi, DateTimeString.DateTimeResult result, int styles, DateTimeString.DateTimeRawInfo raw)
    {
        int num = getMonthDayOrder(dtfi.getMonthDayPattern(), dtfi);
        if (num == DateTimeString.ORDER_ERROR)
            return false;

        if (num == DateTimeString.ORDER_MD)
        {
            int num2 = getYearMonthOrder(dtfi.getYearMonthPattern(), dtfi);
            if (num2 == DateTimeString.ORDER_ERROR)
                return false;

            if (num2 == DateTimeString.ORDER_YM)
            {
                if (!setDateYMD(result, adjustYear(result, raw.getNumber(0)), raw.getMonth(), 1))
                    return false;

                return true;
            }
        }
        getDefaultYear(result, styles);
        if (!setDateYMD(result, result.getYear(), raw.getMonth(), raw.getNumber(0)))
            return false;

        return true;
    }

    // The interpretation is based on the MonthDayPattern and YearMonthPattern
    //
    //    MonthDayPattern   YearMonthPattern  Interpretation
    //    ---------------   ----------------  ---------------
    //    MMMM dd           MMMM yyyy         Day
    //    MMMM dd           yyyy MMMM         Day
    //    dd MMMM           MMMM yyyy         Year
    //    dd MMMM           yyyy MMMM         Day
    //
    // In the first and last cases, it could be either or neither, but a day is a better default interpretation
    // than a 2 digit year.
    private static boolean getDayOfMN(msDateTimeFormatInfo dtfi, DateTimeString.DateTimeResult result, int styles, DateTimeString.DateTimeRawInfo raw)
    {
        int num = getMonthDayOrder(dtfi.getMonthDayPattern(), dtfi);
        if (num == DateTimeString.ORDER_ERROR)
            return false;

        if (num == DateTimeString.ORDER_DM)
        {
            int num2 = getYearMonthOrder(dtfi.getYearMonthPattern(), dtfi);
            if (num2 == DateTimeString.ORDER_ERROR)
                return false;

            if (num2 == DateTimeString.ORDER_MY)
            {
                if (!setDateYMD(result, adjustYear(result, raw.getNumber(0)), raw.getMonth(), 1))
                    return false;

                return true;
            }
        }

        getDefaultYear(result, styles);
        if (!setDateYMD(result, result.getYear(), raw.getMonth(), raw.getNumber(0)))
            return false;

        return true;
    }

    private static int getYearMonthOrder(String pattern, msDateTimeFormatInfo dtfi)
    {
        int num = -1;
        int num2 = -1;
        int num3 = 0;
        boolean flag = false;
        for (int i = 0; (i < pattern.length()) && (num3 < 2); i++)
        {
            char ch = pattern.charAt(i);
            switch (ch)
            {
                case '\'':
                case '"':
                    flag = !flag;
                    break;
            }
            if (!flag)
            {
                switch (ch)
                {
                    case 'y':
                        num = num3++;
                        while (((i + 1) < pattern.length()) && (pattern.charAt(i + 1) == 'y'))
                        {
                            i++;
                        }
                        break;

                    case 'M':
                        num2 = num3++;
                        while (((i + 1) < pattern.length()) && (pattern.charAt(i + 1) == 'M'))
                        {
                            i++;
                        }
                        break;
                }
            }
        }
        if ((num == 0) && (num2 == 1))
        {
            return DateTimeString.ORDER_YM;
        }
        if ((num2 == 0) && (num == 1))
        {
            return DateTimeString.ORDER_MY;
        }
        return DateTimeString.ORDER_ERROR;
    }

    private static int getMonthDayOrder(String pattern, msDateTimeFormatInfo dtfi)
    {
        int num = -1;
        int num2 = -1;
        int num3 = 0;
        boolean flag = false;
        for (int i = 0; (i < pattern.length()) && (num3 < 2); i++)
        {
            char ch = pattern.charAt(i);
            switch (ch)
            {
                case '\'':
                case '"':
                    flag = !flag;
                    break;
            }
            if (!flag)
            {
                switch (ch)
                {
                    case 'd':
                    {
                        int num5 = 1;
                        while (((i + 1) < pattern.length()) && (pattern.charAt(i + 1) == 'd'))
                        {
                            num5++;
                            i++;
                        }
                        if (num5 <= 2)
                        {
                            num2 = num3++;
                        }
                        break;
                    }
                    case 'M':
                        num = num3++;
                        while (((i + 1) < pattern.length()) && (pattern.charAt(i + 1) == 'M'))
                        {
                            i++;
                        }
                        break;
                }
            }
        }
        if ((num == 0) && (num2 == 1))
        {
            return DateTimeString.ORDER_MD;
        }
        if ((num2 == 0) && (num == 1))
        {
            return DateTimeString.ORDER_DM;
        }
        return DateTimeString.ORDER_ERROR;
    }

    private static void getDefaultYear(DateTimeString.DateTimeResult result, int styles)
    {
        result.setYear(getDateTimeNow(result).getYear());
    }

    private static boolean getDayOfNNN(DateTimeString.DateTimeResult result, DateTimeString.DateTimeRawInfo raw, msDateTimeFormatInfo dtfi)
    {
        int number = raw.getNumber(0);
        int month = raw.getNumber(1);
        int day = raw.getNumber(2);

        int num4 = getYearMonthDayOrder(dtfi);
        if (num4 == DateTimeString.ORDER_ERROR)
            return false;

        if (num4 == DateTimeString.ORDER_YMD)
        {
            if (setDateYMD(result, adjustYear(result, number), month, day))
                return true;
        }
        else if (num4 == DateTimeString.ORDER_MDY)
        {
            if (setDateMDY(result, number, month, adjustYear(result, day)))
                return true;
        }
        else if (num4 == DateTimeString.ORDER_DMY)
        {
            if (setDateDMY(result, number, month, adjustYear(result, day)))
                return true;
        }
        else if ((num4 == DateTimeString.ORDER_YDM) && setDateYDM(result, adjustYear(result, number), month, day))
            return true;

        return false;
    }

    private static boolean setDateYDM(DateTimeString.DateTimeResult result, int year, int day, int month)
    {
        return setDateYMD(result, year, month, day);
    }

    private static boolean setDateDMY(DateTimeString.DateTimeResult result, int day, int month, int year)
    {
        return setDateYMD(result, year, month, day);
    }

    private static boolean setDateMDY(DateTimeString.DateTimeResult result, int month, int day, int year)
    {
        return setDateYMD(result, year, month, day);
    }

    private static int adjustYear(DateTimeString.DateTimeResult result, int year)
    {
        if (year < 100)
            year = toFourDigitYear(year);

        return year;
    }

    public static int toFourDigitYear(int year)
    {
        if (year < 0)
            throw new IllegalArgumentException("wrong year " + year);

        if (year < 100)
            return ((((twoDigitYearMax / 100) - ((year > (twoDigitYearMax % 100)) ? 1 : 0)) * 100) + year);

        return year;
    }

    private static boolean getDayOfYMN(msDateTimeFormatInfo dtfi, DateTimeString.DateTimeResult result, DateTimeString.DateTimeRawInfo raw)
    {
        if (setDateYMD(result, raw.getYear(), raw.getMonth(), raw.getNumber(0)))
            return true;

        return false;
    }

    private static boolean getDayOfYNN(msDateTimeFormatInfo dtfi, DateTimeString.DateTimeResult result, DateTimeString.DateTimeRawInfo raw)
    {
        int number = raw.getNumber(0);
        int month = raw.getNumber(1);

        int order = getYearMonthDayOrder(dtfi);
        if (order == DateTimeString.ORDER_YDM)
        {
            if (setDateYMD(result, raw.getYear(), month, number))
                return true;
        }
        else if (setDateYMD(result, raw.getYear(), number, month))
            return true;

        return false;
    }

    private static boolean getDayOfNNY(DateTimeString.DateTimeResult result, DateTimeString.DateTimeRawInfo raw, msDateTimeFormatInfo dtfi)
    {
        int number = raw.getNumber(0);
        int day = raw.getNumber(1);

        int order = getYearMonthDayOrder(dtfi);
        if (order == DateTimeString.ORDER_ERROR)
            return false;

        if (order == DateTimeString.ORDER_MDY || order == DateTimeString.ORDER_YMD)
        {
            if (setDateYMD(result, raw.getYear(), number, day))
                return true; // MD + Year
        }
        else
        {
            if (setDateYMD(result, raw.getYear(), day, number))
                return true; // DM + Year

        }
        return false;
    }

    private static boolean setDateYMD(DateTimeString.DateTimeResult result, int year, int month, int day)
    {
        result.setDate(year, month, day);   // YMD
        return (true);
    }

    private static boolean getTimeOfNNN(msDateTimeFormatInfo dtfi, DateTimeString.DateTimeResult result, DateTimeString.DateTimeRawInfo raw)
    {
        result.setHour(raw.getNumber(0));
        result.setMinute(raw.getNumber(1));
        result.setSecond(raw.getNumber(2));
        return true;
    }

    private static boolean getTimeOfNN(msDateTimeFormatInfo dtfi, DateTimeString.DateTimeResult result, DateTimeString.DateTimeRawInfo raw)
    {
        result.setHour(raw.getNumber(0));
        result.setMinute(raw.getNumber(1));
        return true;
    }

    //
    // Decide the year/month/day order from the datePattern.
    // Return 0 for YMD, 1 for MDY, 2 for DMY, otherwise -1.
    //
    public static int getYearMonthDayOrder(msDateTimeFormatInfo dtfi)
    {
        int yearOrder = -1;
        int monthOrder = -1;
        int dayOrder = -1;
        int orderCount = 0;

        String datePattern = dtfi.getShortDatePattern();

        if ("ka-GE".equals(dtfi.getCultureName()))
        {
            // 0x0437 = Georgian - Georgia (ka-GE)
            // Very special case for ka-GE:
            //  Its short date patten is "dd.MM.yyyy" (ORDER_DMY).
            //  However, its long date pattern is "yyyy '\x10ec\x10da\x10d8\x10e1' dd MM, dddd" (ORDER_YDM)
            //datePattern = ((SimpleDateFormat) DateFormat.getDateInstance(DateFormat.LONG, locale)).toLocalizedPattern();
            datePattern = dtfi.getLongDatePattern();
        }

        boolean inQuote = false;
        for (int i = 0; i < datePattern.length() && orderCount < 3; i++)
        {
            char ch = datePattern.charAt(i);
            if (ch == '\'' || ch == '"')
                inQuote = !inQuote;

            if (!inQuote)
            {
                if (ch == 'y')
                {
                    yearOrder = orderCount++;
                    // Skip all year pattern charaters.
                    for (; i + 1 < datePattern.length() && datePattern.charAt(i + 1) == 'y'; i++)
                    {
                        // Do nothing here.
                    }
                }
                else if (ch == 'M')
                {
                    monthOrder = orderCount++;
                    // Skip all month pattern characters.
                    for (; i + 1 < datePattern.length() && datePattern.charAt(i + 1) == 'M'; i++)
                    {
                        // Do nothing here.
                    }
                }
                else if (ch == 'd')
                {
                    int patternCount = 1;
                    // Skip all day pattern characters.
                    for (; i + 1 < datePattern.length() && datePattern.charAt(i + 1) == 'd'; i++)
                    {
                        patternCount++;
                    }
                    // Make sure this is not "ddd" or "dddd", which means day of week.
                    if (patternCount <= 2)
                        dayOrder = orderCount++;
                }
            }
        }

        if (yearOrder == 0 && monthOrder == 1 && dayOrder == 2)
            return DateTimeString.ORDER_YMD;

        if (monthOrder == 0 && dayOrder == 1 && yearOrder == 2)
            return DateTimeString.ORDER_MDY;

        if (dayOrder == 0 && monthOrder == 1 && yearOrder == 2)
            return DateTimeString.ORDER_DMY;

        if (yearOrder == 0 && dayOrder == 1 && monthOrder == 2)
            return DateTimeString.ORDER_YDM;

        return -1;
    }

    private static boolean processHebrewTerminalState(int dps, DateTimeString.DateTimeResult result, int styles, DateTimeString.DateTimeRawInfo raw, msDateTimeFormatInfo dtfi)
    {
        switch (dps)
        {
            case DateTimeString.DS.DX_NM:
                getDefaultYear(result, styles);
                if (dtfi.yearMonthAdjustment(new int[]{result.getYear(), raw.getMonth()}, true))
                {
                    if (!getHebrewDayOfNM(result, raw, dtfi))
                    {
                        return false;
                    }
                    break;
                }
                return false;

            case DateTimeString.DS.DX_MNN:
                raw.setYear(raw.getNumber(1));
                if (dtfi.yearMonthAdjustment(new int[]{raw.getYear(), raw.getMonth()}, true))
                {
                    if (!getDayOfMNN(result, raw, dtfi))
                    {
                        return false;
                    }
                    break;
                }
                return false;

            case DateTimeString.DS.DX_YMN:
                if (dtfi.yearMonthAdjustment(new int[]{raw.getYear(), raw.getMonth()}, true))
                {
                    if (!getDayOfYMN(dtfi, result, raw))
                    {
                        return false;
                    }
                    break;
                }
                return false;

            case DateTimeString.DS.DX_YM:
                if (dtfi.yearMonthAdjustment(new int[]{raw.getYear(), raw.getMonth()}, true))
                {
                    if (!getDayOfYM(dtfi, result, raw))
                    {
                        return false;
                    }
                    break;
                }
                return false;

            case DateTimeString.DS.TX_N:
                if (getTimeOfN(dtfi, result, raw))
                {
                    break;
                }
                return false;

            case DateTimeString.DS.TX_NN:
                if (getTimeOfNN(dtfi, result, raw))
                {
                    break;
                }
                return false;

            case DateTimeString.DS.TX_NNN:
                if (getTimeOfNNN(dtfi, result, raw))
                {
                    break;
                }
                return false;

            default:
                return false;
        }
        if (dps > DateTimeString.DS.ERROR)
        {
            raw.setNumCount(0);
        }
        return true;
    }

    private static boolean getHebrewDayOfNM(DateTimeString.DateTimeResult result, DateTimeString.DateTimeRawInfo raw, msDateTimeFormatInfo dtfi)
    {
        int num;
        if ((num = getMonthDayOrder(dtfi.getMonthDayPattern(), dtfi)) == -1)
        {
            return false;
        }
        result.setMonth(raw.getMonth());
        if ((num == 7) && isValidDay(result.getYear(), result.getMonth(), raw.getNumber(0), result.getEra(), result.getCalendar()))
        {
            result.setDay(raw.getNumber(0));
            return true;
        }
        return false;
    }

    private static final int twoDigitYearMax = 2099;
    static final String GMTName = "GMT";
    static final String ZuluName = "Z";
}

