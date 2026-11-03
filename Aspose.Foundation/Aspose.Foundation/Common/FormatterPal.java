// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 10/06/2009 by Roman Korchagin

package com.aspose;

import com.aspose.words.ref.Ref;
import com.aspose.ms.System.*;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.ms.System.Globalization.msDateTimeFormatInfo;
import com.aspose.ms.System.Globalization.DateTimeStyles;
import com.aspose.ms.System.Globalization.IFormatProvider;
import com.aspose.ms.System.Threading.CurrentThread;
import com.aspose.ms.java.text.NumberFormatProvider;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/// <summary>
/// FOR MANUAL PORTING
///
/// This class provides method to format numbers ands date time values into strings
/// and also to parse them back into values.
/// </summary>
public /*static*/ class FormatterPal
{
    /* Simulation of static class by using private constructor */
    private FormatterPal()
    {}


    private static msDateTimeFormatInfo applyMsWordTwoDigitYearMax(msDateTimeFormatInfo dateTimeFormatInfo)
    {
        msDateTimeFormatInfo result =(msDateTimeFormatInfo)dateTimeFormatInfo.deepClone();
        result.getCalendar().setTwoDigitYearMax(MS_WORD_TWO_DIGIT_YEAR_MAX);
        return result;
    }


    /// <summary>
    /// Formats a DateTime value to XML string and adds the timezone designator
    /// as UTC offset (+hh:mm or -hh:mm).
    /// </summary>
    public static String dateTimeToXmlExplicitTimezone(DateTime value)
    {
        //JAVA-added: DateTime is struct in .Net and can't be null
        if (value == null)
            value = DateTime.MinValue;

        return value.toString("yyyy-MM-ddTHH:mm:sszzz", msCultureInfo.getInvariantCulture());
    }

    /// <summary>
    /// Formats a DateTime value to XML string and adds the UTC "Z" suffix.
    /// </summary>
    /// <remarks>
    /// Assumes that the specified value has <see cref="DateTimeKind.Utc"/>.
    /// </remarks>
    public static String dateTimeToXmlUtc(DateTime value)
    {
        //JAVA-added: DateTime is struct in .Net and can't be null
        if (value == null)
            value = DateTime.MinValue;

        return value.toString("yyyy-MM-ddTHH:mm:ssZ",  msCultureInfo.getInvariantCulture());
    }

    /// <summary>
    /// This is currently used by ODT export. Does not write any timezone suffix.
    /// </summary>
    public static String dateTimeToXmlNoTimezone(DateTime value)
    {
	    //JAVA-added: DateTime is struct in .Net and can't be null
	    if (value == null)
            value = DateTime.MinValue;

        return value.toString("yyyy-MM-ddTHH:mm:ss", msCultureInfo.getInvariantCulture());
    }

    public static String dateTimeToStrPdfExplicitTimezone( DateTime value)
    {
        //JAVA-added: DateTime is struct in .Net and can't be null
        if (value == null)
            value = DateTime.MinValue;

        String result = value.toString("yyyyMMddHHmmss", msCultureInfo.getInvariantCulture());

        // PDF uses special format for timezone designator.
        // For example +03'30'
        String tzd = msString.replace(value.toString("zzz", msCultureInfo.getInvariantCulture()), ':', '\'') + '\'';

        return result + tzd;
    }

    public static String dateTimeToStr_yyyyMMddHHmmssZ(DateTime value)
    {
	    //JAVA-added: DateTime is struct in .Net and can't be null
	    if (value == null)
            value = DateTime.MinValue;

        return value.toString("yyyyMMddHHmmssZ", msCultureInfo.getInvariantCulture());
    }

    /// <summary>
    /// Parses an XML string into a date time value and returns it in UTC.
    /// If the string represents and invalid date, returns <see cref="DateTime.MinValue"/>.
    /// </summary>
    public static DateTime xmlToDateTime(String value)
    {
        return tryParseDateTime(value, msCultureInfo.getInvariantCulture(), false);
    }

    /// <summary>
    /// Parses an XML string into a date time value ignoring time zone and returns it in UTC.
    /// If the string represents an invalid date, returns <see cref="DateTime.MinValue"/>.
    /// </summary>
    public static DateTime xmlToDateTimeIgnoreTimeZone(String value)
    {
        return tryParseDateTimeOffset(value, msCultureInfo.getInvariantCulture());
    }

    /// <summary>
    /// Parses an XML xs:dateTime string into a date time value and returns it in UTC. 
    /// If the string represents and invalid date, returns <see cref="DateTime.MinValue"/>.
    /// </summary>
    /// <remarks>
    /// AM. This method is needed to parse dateTime only if it's in strict Xml formats. 
    /// Recommended approach is to use XmlConvert class but I think it can produce porting problems.
    /// It seems that format list is uncomplete but it will not make big problems in output document i.e throw or something.
    /// andrnosk: To fix WORDSNET-8479 I have added one more XmlDateTimeFormat, and also I have changed 
    /// DateTimeStyles to AssumeLocal because MS Word uses local time zone to calculate datetime value.
    /// </remarks>
    public static DateTime xmlToDateTimeExact(String value)
    {
        Ref<DateTime> referenceToResult = new Ref<DateTime>(null);
        //Returns DateTime.MinValue if can't parse
        DateTime.tryParseExact(value, gXmlDateTimeFormats, msCultureInfo.getInvariantCulture(), DateTimeStyles.ADJUST_TO_UNIVERSAL | DateTimeStyles.ASSUME_LOCAL, /*out*/ referenceToResult);
        return referenceToResult.get();
    }

    /// <summary>
    /// Formats date and time according to MIME header format.
    /// RK This is according to http://www.faqs.org/rfcs/rfc2822.html
    /// </summary>
    public static String dateTimeToStrRfc2822(DateTime value)
    {
        StringBuilder dateTime = new StringBuilder(value.toString(DateTimeRfc2822Format, msDateTimeFormatInfo.getCurrentInfo()));
        // "Sun, 13 Feb 2011 14:03:04 +13:00" removes ":" in the timezone offset.
	    int lastColon = dateTime.lastIndexOf(":");
	    if (lastColon > 0)
	        dateTime = dateTime.deleteCharAt(lastColon);
        return dateTime.toString();
    }

    public static DateTime parseDateTimeRfc2822(String value) throws Exception
    {
        // RK I have to specify exact format string because otherwise .NET 1.1 cannot recognize date time.
        return DateTime.parseExact(value, DateTimeRfc2822Format, msDateTimeFormatInfo.getInvariantInfo());
    }

    private static final String DateTimeRfc2822Format = "ddd, d MMM yyyy HH':'mm':'ss zzzz";

    /// <summary>
    /// Formats an integer value to XML string.
    /// </summary>
    public static String intToXml(int value)
    {
        return intToStr(value);
    }

    /// <summary>
    /// Formats an unsigned integer value to XML string.
    /// </summary>
    public static String uIntToXml(/*uint*/int val)
    {
        return msUInt.toString(val);
    }

    /// <summary>
    /// Formats a long value to XML string.
    /// </summary>
    public static String longToXml(long val)
    {
        return msNumber.formatInt64(val);
    }

    /// <summary>
    /// Parses an XML string into an integer value.
    /// 
    /// FIX 6000 A document has a value "320.0" in an integer property. This is not valid according to 
    /// the schema but MS Word reads it okay. We used to parse integer strictly and it threw.
    /// Fixed by parsing doubles and casting to int. This is not best, but okay for now.
    /// As a result of the fix returns 0 if cannot parse the string. This is not the best solution of course, but okay for now.
    /// </summary>
    public static int xmlToInt(String val)
    {
        return MathUtil.castDoubleToInt(parseDouble(val));
    }

    /// <summary>
    /// Parses an XML string into an short integer value.
    /// </summary>
    public static int xmlToShortInt( String val)
    {
        return MathUtil.doubleToShort(parseDouble(val));
    }

    /// <summary>
    /// Parses an XML string into an unsigned integer value.
    /// </summary>
    public static /*uint*/int xmlToUInt(String val)
    {
        return tryParseUInt(val);
    }

    /// <summary>
    /// Parses an XML string into a long value.
    /// </summary>
    public static long xmlToLong(String val)
    {
        return tryParseLong(val);
    }

    /// <summary>
    /// Parses a string into a double value.
    /// 
    /// Returns 0 if cannot parse the string. This is not the best solution of course, but okay for now.
    /// 
    /// Allows leading sign.
    /// Allows leading and trailing spaces.
    /// Allows "." as a decimal point.
    /// Allows exponent.
    /// 
    /// Yes, we do get values with exponents, for example in custom document properties.
    /// </summary>
    public static double parseDouble(String val)
    {
        double result = tryParseDoubleInvariant(val);
        return Double.isNaN(result) ? 0.0 : result;
    }

    /// <summary>
    /// In .Net the method has the same body with above ParseDouble(string). 
    /// But in java it differs. 
    /// </summary>
	public static double parseDoubleFloatPrecision(String val)
	{
		double result = tryParseDoubleFloatPrecision(val);
		return Double.isNaN(result) ? 0.0 : result;
	}

	public static double tryParseDoubleFloatPrecision(String s)
    {
        return gInstance.get().tryParseDoubleFloatPrecision(s);
    }

    /// <summary>
    /// Tries to parse a double with the invariant culture. Returns NaN if cannot parse.
    /// 
    /// Allows leading sign
    /// Allows leading and trailing spaces.
    /// Allows "." as a decimal point.
    /// Allows exponent.
    /// </summary>
    public static double tryParseDoubleInvariant(String s)
    {
        return tryParseDoubleInvariant(s, false);
    }

    /// <summary>
    /// Tries to parse a double using the invariant culture with the parameter specifying whether to distinguish
    /// thousands separators. Returns NaN if cannot parse.
    /// </summary>
    /// <remarks>
    /// Allows leading sign
    /// Allows leading and trailing spaces.
    /// Allows "." as a decimal point.
    /// Allows exponent.
    /// </remarks>
    public static double tryParseDoubleInvariant(
            String s,
            boolean allowThousands)
    {
        return gInstance.get().tryParseDoubleInvariant(s, allowThousands);
    }

    /// <summary>
    /// Tries to parse a float with the invariant culture. Returns NaN if cannot parse.
    /// 
    /// Allows leading sign
    /// Allows leading and trailing spaces.
    /// Allows "." as a decimal point.
    /// Allows exponent.
    /// </summary>
    public static float tryParseFloatInvariant(String s)
    {
        return (float) gInstance.get().tryParseDoubleFloatPrecision(s);
    }

    /// <summary>
    /// Tries to parse a double with the current culture. Returns NaN if cannot parse.
    /// 
    /// Allows leading sign.
    /// Allows leading and trailing spaces.
    /// Allows decimal point specified by the current culture.
    /// Allows exponent.
    /// </summary>
    public static double tryParseDoubleCurrent(String s)
    {
        return tryParseDoubleCurrent(s, false);
    }

    /// <summary>
    /// Tries to parse a double using the current culture with the parameter specifying whether to distinguish
    /// thousands separators. Returns NaN if cannot parse.
    /// </summary>
    /// <remarks>
    /// Allows leading sign.
    /// Allows leading and trailing spaces.
    /// Allows decimal point specified by the current culture.
    /// Allows exponent.
    /// </remarks>
    public static double tryParseDoubleCurrent(
            String s,
            boolean allowThousands)
    {
        return gInstance.get().tryParseDoubleCurrent(s, allowThousands);
    }

    /// <summary>
    /// Returns true if the string represents a positive integer number or zero.
    /// </summary>
    public static boolean isPositiveIntegerOrZero(String value)
    {
        // We need to provide this just because Double.TryParse needs this.
        //JAVA-changed from:
//		double result;
//		return Double.TryParse(value, NumberStyles.None, msCultureInfo.INVARIANT_CULTURE, out result);
        //JAVA: "PositiveIntegerOrZero" can contain only digits
        //this method accepts any unicode digits.
        for (int i = 0; i < value.length(); i++)
        {
            if (!Character.isDigit(value.charAt(i)))
                return false;
        }

        return true;
    }

    /// <summary>
    /// Parses an integer using the invariant culture. Returns Int.MinValue if cannot parse.
    /// 
    /// Allows leading sign.
    /// Allows leading and trailing spaces.
    /// </summary>
    public static int tryParseInt(String s)
    {
        NullableInt32 value = parseNullableInt(s);
        return value.getValueOrDefault(Integer.MIN_VALUE);
    }

    /// <summary>
    /// Parses an integer using the invariant culture.
    /// 
    /// Allows leading sign.
    /// Allows leading and trailing spaces.
    /// </summary>
    public static NullableInt32 parseNullableInt(String s)
    {
        try
        {
            // Emulate .Net NumberStyles.Integer behaviour: AllowLeadingWhite, AllowTrailingWhite.
            s = s.trim();
            // and AllowLeadingSign. Java (1.5?) allows only '-' prefix, throws on '+'.
            // But Java 1.8 allows both '-' and '+' prefixes.
            if (s.charAt(0) == '+')
                s = s.substring(1);
            return new NullableInt32(Integer.parseInt(s));
        }
        catch (Exception e)
        {
            return NullableInt32.NULL;
        }
    }

    /// <summary>
    /// Parses an unsigned integer using the invariant culture. Returns Int.MinValue if cannot parse.
    ///
    /// Allows leading sign, leading and trailing spaces. Trims ending ".0".
    /// </summary>
    public static /*uint*/int tryParseUInt(String s)
    {
        try
        {
            // Emulate .Net NumberStyles.Integer behaviour: AllowLeadingWhite, AllowTrailingWhite.
            s = s.trim();
            // Sometimes documents have value like "12.0" as int/uint property.
            if (s.endsWith(".0"))
                s = s.substring(0, s.length() - 2);

            long l = Long.parseLong(s);
            return (int) (l & 0xFFFFFFFF);
        }
        catch (Exception e)
        {
            return Integer.MIN_VALUE;
        }
    }

    /// <summary>
    /// Parses a long using the invariant culture. Returns Long.MinValue if cannot parse.
    ///
    /// Allows leading sign, leading and trailing spaces. Trims ending ".0".
    /// </summary>
    public static long tryParseLong(String s)
    {
        try
        {
            // Emulate .Net NumberStyles.Integer behaviour: AllowLeadingWhite, AllowTrailingWhite.
            s = s.trim();
            // Sometimes documents have value like "12.0" as int/uint property.
            if (s.endsWith(".0"))
                s = s.substring(0, s.length() - 2);

            return Long.parseLong(s);
        }
        catch (Exception e)
        {
            return Long.MIN_VALUE;
        }
    }

    /// <summary>
    /// Try get int from the longest portion of string. Returns Int.MinValue if cannot parse.
    /// </summary>
    public static int tryParseIntPortion(String s)
    {
        int value = Integer.MIN_VALUE; 

        // Try get int from the longest portion of string.
        while(s.length() > 0)
        {
            value = tryParseInt(s);

            if (value != Integer.MIN_VALUE)
                break;

            s = s.substring((0), (0) + (s.length() - 1));
        }

        return value;
    }

    /// <summary>
    /// Uses the current culture for parsing. Returns NaN if cannot parse.
    /// 
    /// Allows currency symbol.
    /// Allows decimal point.
    /// Allows leading and trailing sign.
    /// Allows leading and trailing whitespace.
    /// Allows thousands separator.
    /// Allows parenthesis.
    /// </summary>
    public static double tryParseCurrencyCurrent(String s)
    {
        return gInstance.get().tryParseCurrencyCurrent(s);
    }

    /// <summary>
    /// Returns <see cref="DateTime.MinValue"/> if cannot parse.
    /// </summary>
    public static DateTime tryParseDateTimeInvariant(String s)
    {
        return tryParseDateTimeInvariant(s, false, true);
    }

    public static DateTime tryParseDateTimeInvariant(String s, 
        boolean isRoundtripKind)
    {
        return tryParseDateTimeInvariant(s, isRoundtripKind, false);
    }

    /// <summary>
    /// Returns <see cref="DateTime.MinValue"/> if cannot parse.
    /// </summary>
    /// <remarks>
    /// If the date time specifies a time zone, depending on <paramref name="isRoundtripKind"/> it either converts 
    /// the value to UTC (<b>false</b>), or keeps original datetime kind (<b>true</b>).
    /// </remarks>
    public static DateTime tryParseDateTimeInvariant(String s, 
        boolean isRoundtripKind,
        boolean useMsWordTwoDigitYearMax)
    {
        msDateTimeFormatInfo currentDateTimeFormat = getCurrentDateTimeFormatInfo();
        msDateTimeFormatInfo[] formats = new msDateTimeFormatInfo[]
                {
            useMsWordTwoDigitYearMax ? applyMsWordTwoDigitYearMax(currentDateTimeFormat) : currentDateTimeFormat,
            
                        // This cultures have opposite date formats: M/d/yyyy vs d/MM/yyyy. It's enough for majority cases.
            useMsWordTwoDigitYearMax ? G_EN_US_DATE_TIME_FORMAT_MS_WORD_TWO_DIGIT_YEAR_MAX : G_EN_US_DATE_TIME_FORMAT,
            useMsWordTwoDigitYearMax ? G_EN_NZ_DATE_TIME_FORMAT_MS_WORD_TWO_DIGIT_YEAR_MAX : G_EN_NZ_DATE_TIME_FORMAT
                };

        // FIX WORDSNET-4632 MS Word attempts to parse date with various date format, not only current culture relative.
        for (msDateTimeFormatInfo format : formats)
        {
            DateTime result = tryParseDateTime(s, format, isRoundtripKind);
            if (!DateTime.equals(result, DateTime.MinValue))
                return result;
        }

        return DateTime.MinValue;
    }

    /// <summary>
    /// Returns <see cref="DateTime.MinValue"/> if cannot parse.
    /// </summary>
    public static DateTime tryParseDateTimeCurrent(String s)
    {
        return tryParseDateTimeCurrent(s, false);
    }

    /// <summary>
    /// Returns <see cref="DateTime.MinValue"/> if cannot parse.
    /// </summary>
    /// <remarks>
    /// If the date time specifies a time zone, depending on <paramref name="isRoundtripKind"/> it either converts
    /// the value to UTC (<c>false</c>), or keeps original datetime kind (<c>true</c>).
    /// </remarks>
    public static DateTime tryParseDateTimeCurrent(
            String s,
            boolean isRoundtripKind)
    {
        return tryParseDateTime(s, getCurrentDateTimeFormatInfo(), isRoundtripKind);
    }

    /// <summary>
    /// Tries to parse a date time using the specified culture.
    /// If the date time specifies a time zone, depending on <paramref name="isRoundtripKind"/> it either converts 
    /// the value to UTC (<b>false</b>), or keeps original datetime kind (<b>true</b>).
    /// If the date time value is invalid returns <see cref="DateTime.MinValue"/>.
    /// </summary>
    private static DateTime tryParseDateTime(String value, IFormatProvider provider, boolean isRoundtripKind)
    {
        int styles = isRoundtripKind ? DateTimeStyles.ROUNDTRIP_KIND : DateTimeStyles.ADJUST_TO_UNIVERSAL;
        return DateTime.parse(value, provider, styles);
    }

    /// <summary>
    /// Tries to parse a date time offset using the specified culture.
    /// If the date time value is invalid returns <see cref="DateTimeOffset.MinValue"/>.
    /// </summary>
    private static DateTime tryParseDateTimeOffset(String s, IFormatProvider provider)
    {
        DateTime result = DateTime.parse(s, provider, DateTimeStyles.NONE) ;
        return result != null ? result : DateTime.MinValue ;
    }

    /// <summary>
    /// Parses a string into an integer value.
    /// 
    /// RK This throws when cannot parse. This is inconsistent with <see cref="ParseDouble"/>.
    /// 
    /// Leading sign is allowed.
    /// Leading space is allowed.
    /// Trailing space is allowed.
    /// </summary>
    public static int parseInt(String val)
    {
        return Integer.parseInt(val.trim());
    }

    /// <summary>
    /// Parses a string storing int64 value.
    /// See <see cref="ParseInt"/> for additional concerns.
    /// </summary>
    public static long parseInt64(String val)
    {
        return Long.parseLong(val.trim());
    }

    /// <summary>
    /// Parses a hexadecimal string into an integer value.
    /// 
    /// RK This throws when cannot parse. This is inconsistent with <see cref="ParseDouble"/>.
    /// 
    /// Leading space is allowed.
    /// Trailing space is allowed.
    /// 0x prefix is not allowed.
    /// </summary>
    public static int parseHex(String val)
    {
        //JAVA-changed
        int result = 0;
        if(val == null || "".equals(val))
            throw new NumberFormatException("Empty hex string.");
        for (int i = 0; i < val.length(); i++)
        {
            char c = val.charAt(i);
            if (c == ' ')
                continue;
            if (!StringUtil.isHexDigit(c))
                throw new NumberFormatException("Invalid hex string.");

            result = (result << 4) | StringUtil.hexCharToDigit(c);
        }
        return result;
    }

    /// <summary>
    /// Parses a hexadecimal string into a long value.
    /// 
    /// RK This throws when cannot parse. This is inconsistent with <see cref="ParseDouble"/>.
    /// 
    /// Leading space is allowed.
    /// Trailing space is allowed.
    /// 0x prefix is not allowed.
    /// </summary>
    public static long parseHex64(String val)
    {
        //JAVA-changed, duplicate of method above.
        long result = 0;
        for (int i = 0; i < val.length(); i++)
        {
            char c = val.charAt(i);
            if (c == ' ')
                continue;
            if (!StringUtil.isHexDigit(c))
                throw new IllegalStateException("Invalid hex string.");

            result = (result << 4) | StringUtil.hexCharToDigit(c);
        }
        return result;
    }

	/// <summary>
	/// Try parses a hex string into an integer value.
	/// on error return int.MinValue
	/// </summary>
	public static int tryParseHex(String s)
	{
        if (!StringUtil.hasChars(s))
            return Integer.MIN_VALUE;

		try
		{
			return parseHex(s);
		}
		catch (Exception e)
		{
			return Integer.MIN_VALUE;
		}
	}

    /// <summary>
    /// Copycat of .Net's Boolean.TryParse(String value, out Boolean result) but
    /// without 'out' parameter: returns parsed value or throws for unparsable string.
    /// </summary>
    public static boolean parseBoolTrueFalse(String value)
    {
        if (value==null)
            throw new IllegalArgumentException("Cannot parse a string into boolean.");

        // For perf reasons, let's first see if they're equal, then do the
        // trim to get rid of white space, and check again.
        if (StringUtil.equalsIgnoreCase("true", value))
            return true;

        if (StringUtil.equalsIgnoreCase("false", value))
            return false;

        // Remove leading & trailing white space.
        value = msString.trim(value);
        // Special case: Trim null characters.
        value = msString.trim(value, (char) 0x0000);

        if (StringUtil.equalsIgnoreCase("true", value))
            return true;

        if (StringUtil.equalsIgnoreCase("false", value))
            return false;

        throw new IllegalArgumentException("Cannot parse a string into boolean.");
    }

    /// <summary>
    /// More info see http://demos.aspose.com:8089/display/org/Do+NOT+use+bool.ToString%28%29
    /// </summary>
    public static String boolToTrueFalseLower(boolean val)
    {
        return (val) ? "true" : "false";
    }

    /// <summary>
    /// Copycat of .Net's Boolean.TryParse(String value, out Boolean result) but without
    /// 'out' parameter: just returns true if the string value can be parsed to bool.
    /// </summary>
    public static boolean isBoolTrueFalse(String value)
    {
        if (value==null)
            return false;

        // For perf reasons, let's first see if they're equal, then do the
        // trim to get rid of white space, and check again.
        if (StringUtil.equalsIgnoreCase("true", value))
            return true;

        if (StringUtil.equalsIgnoreCase("false", value))
            return true;

        // Remove leading & trailing white space.
        value = msString.trim(value);
        // Special case: Trim null characters.
        value = msString.trim(value, (char) 0x0000);

        if (StringUtil.equalsIgnoreCase("true", value))
            return true;

        if (StringUtil.equalsIgnoreCase("false", value))
            return true;

        return false;
    }

    public static String intToStr(int val)
    {
        return Integer.toString(val);
    }

    /// <summary>
    /// Formats a integer value into a string using a current culture.
    /// </summary>
    public static String intToStrCurrentCulture(int val)
    {
        // Java: really in .Net the bodies of intToStrCurrentCulture(int val) and intToStr(int val) are equal:
        // 'return val.ToString(msCultureInfo.InvariantCulture);'
        return Integer.toString(val);
    }

    public static String intToStr(long val)
    {
        return Long.toString(val);
    }

    /// <summary>
    /// Returns a string of decimal digits with the specified
    /// minimum number of digits desired in the resulting string.
    /// </summary>
    public static String intToStr(int val, int precision)
    {
        String formatSpecifier = "D" + intToStr(precision);
        return msNumber.formatInt32(val, formatSpecifier, msCultureInfo.getInvariantCulture());
    }

    /// <summary>
    /// Returns a "01" string.
    /// </summary>
    public static String intToStrD2(long val)
    {
        return gInstance.get().intToStrD2(val);
    }

    /// <summary>
    /// Returns a "001" string.
    /// </summary>
    public static String intToStrD3(long val)
    {
        return gInstance.get().intToStrD3(val);
    }

    /// <summary>
    /// Returns a "0001" string.
    /// </summary>
    public static String intToStrD4(long val)
    {
        return gInstance.get().intToStrD4(val);
    }

    /// <summary>
    /// Returns a "00001" string.
    /// </summary>
    public static String intToStrD5(long val)
    {
        return gInstance.get().intToStrD5(val);
    }

    /// <summary>
    /// Returns a "0123456789" string.
    /// </summary>
    public static String intToStrD10(int val)
    {
        return gInstance.get().intToStrD10(val);
    }

    public static String intToStrNoZero(int val)
    {
        return (val == 0) ? "" : intToStr(val);
    }

    /// <summary>
    /// Returns a hex string string.
    /// </summary>
    public static String intToStrX(int val)
    {
        return msNumber.formatInt32(val, "X", msCultureInfo.getInvariantCulture());
    }

    /// <summary>
    /// Returns a string of hex digits with the specified
    /// minimum number of digits desired in the resulting string.
    /// </summary>
    public static String intToStrX(int val, int precision, boolean useLowerCaseLetters)
    {
        String formatSpecifier = useLowerCaseLetters ? "x" : "X";
        if (precision > 0)
            formatSpecifier = msString.plusEqOperator(formatSpecifier, intToStr(precision));
        return msNumber.formatInt32(val, formatSpecifier, msCultureInfo.getInvariantCulture());
    }

    /// <summary>
    /// Returns a hex string string.
    /// </summary>
    public static String longToStrX(long val)
    {
        return gInstance.get().longToStrX(val, 0, true);
    }

    /// <summary>
    /// Returns a hex string string.
    /// </summary>
    public static String intToStrXLower(int val)
    {
        return msNumber.formatInt32(val, "x", msCultureInfo.getInvariantCulture());
    }

    /// <summary>
    /// Returns a "1A" string.
    /// </summary>
    public static String intToStrX2(int val)
    {
        return msNumber.formatInt32(val, "X2", msCultureInfo.getInvariantCulture());
    }

    /// <summary>
    /// Returns a "1a" string.
    /// </summary>
    public static String intToStrX2Lower(int val)
    {
        return msNumber.formatInt32(val, "x2", msCultureInfo.getInvariantCulture());
    }

    /// <summary>
    /// Returns a "123A" string.
    /// </summary>
    public static String intToStrX4(int val)
    {
        return msNumber.formatInt32(val, "X4", msCultureInfo.getInvariantCulture());
    }

    /// <summary>
    /// Returns a "12345A" string.
    /// </summary>
    public static String intToStrX6(int val)
    {
        return msNumber.formatInt32(val, "X6", msCultureInfo.getInvariantCulture());
    }

    /// <summary>
    /// Returns a "1234567A" string.
    /// </summary>
    public static String intToStrX8(int val)
    {
        return msNumber.formatInt32(val, "X8", msCultureInfo.getInvariantCulture());
    }

    /// <summary>
    /// Returns a hex string.
    /// </summary>
    public static String int64ToStrX(long val)
    {
        return msNumber.formatInt64(val, "X", msCultureInfo.getInvariantCulture());
    }

    /// <summary>
    /// Returns a "1234567A" string.
    /// </summary>
    public static String int64ToStrX8(long val)
    {
        return msNumber.formatInt64(val, "X8", msCultureInfo.getInvariantCulture());
    }

    /// <summary>
    /// Formats a double value into a string using an invariant culture.
    /// 
    /// There is a leading zero if needed.
    /// Fractions are only displayed if needed.
    /// 
    /// On .NET precision is limit to 15 digits after which rounding occurs.
    /// On .NET returns the number in scientific notation if large number or less than E-5.
    /// 
    /// On Java it seems the precision is greater and numbers are formatted fully.
    /// </summary>
    public static String doubleToStr(double value)
    {
        // Mimic .Net.
        if (Double.isNaN(value))
            return "NaN";

        return gInstance.get().doubleToStr(value);
    }

    /// <summary>
    /// Formats a double value into a string using a current culture.
    /// </summary>
    public static String doubleToStrCurrentCulture(double value)
    {
        // Mimic .Net.
        if (Double.isNaN(value))
            return "NaN";

        // JAVA-changed from:
        //         'return value.ToString(msCultureInfo.getCurrentCulture());'
        // another java option:
        // 'msNumber.toString(value, null, msCultureInfo.getCurrentCulture())'
        return msNumber.formatDouble(value, CurrentThread.getCurrentCulture());
    }

    /// <summary>
    /// This is used by VML Util.
    /// Add more info about this or refactor later.
    /// </summary>
    public static String doubleToStr11HashesEZero(double val)
    {
        // Mimic .Net.
        if (Double.isNaN(val))
            return "NaN";

        return gInstance.get().doubleToStr11HashesEZero(val);
    }

    /// <summary>
    /// Formats a value into a string using invariant culture and 'R' format.
    /// 'R' format specifier guarantees that a numeric value that is converted to a string will be parsed back into the same numeric value.
    /// Used for DML Charts. Slight difference in values might lead into incorrect chart rendering in MS Word.
    /// So need to use this format.
    /// </summary>
    public static String doubleToStrRoundtrip(double val)
    {
        // Mimic .Net.
        if (Double.isNaN(val))
            return "NaN";

        // FIX WORDSJAVA-959: NumberFormatException is thrown while roundtrip Docx document with Dml Chart
        // on PC with Italy default locale.
        try
        {
            return msNumber.formatDouble(val, "R", CurrentThread.getCurrentCulture());
        }
        catch (NumberFormatException e)
        {
            return msNumber.formatDouble(val, "R", msCultureInfo.getInvariantCulture());
        }
    }

    /// <summary>
    /// Formats a value into a string using invariant culture and and 0.#E+0 format.
    /// </summary>
    public static String doubleToExponential(double val)
    {
        // Mimic .Net.
        if (Double.isNaN(val))
            return "NaN";

        return gInstance.get().doubleToExponential(val);
    }

    /// <summary>
    /// Formats a value into a string using invariant culture and and '0.' - '0.#########' format.
    /// n denotes the number of '#' in the format. Note: n can be only from 0 to 9.
    /// </summary>
    public static String doubleToStrNDecimals(double val, int n)
    {
        // Mimic .Net.
        if (Double.isNaN(val))
            return "NaN";

        assert n >= 0 && n <= 9;
        val = msMath.round(val, n);
        return gInstance.get().doubleToStrNDecimals(val, n);
    }

    /// <summary>
    /// Formats a value into a string using invariant culture and and 0.## format.
    /// </summary>
    public static String doubleToStr2Decimals(double val)
    {
        // Mimic .Net.
        if (Double.isNaN(val))
            return "NaN";

        // RK This used '#.##' format in the before, but for very small numbers it output
        // an empty string which made invalid XML. It is better to output at least zero instead.
        val = msMath.round(val, 2);
        return gInstance.get().doubleToStr2Decimals(val);
    }

    /// <summary>
    /// Formats a value into a string using invariant culture and and 0.######### format.
    /// </summary>
    public static String doubleToStr9Decimals(double val)
    {
        // Mimic .Net.
        if (Double.isNaN(val))
            return "NaN";

        val = msMath.round(val, 9);
        return gInstance.get().doubleToStr9Decimals(val);
    }

    /// <summary>
    /// Outputs all digits of a value. Never outputs in the scientific notation.
    /// 
    /// Do not use method in PDF renderer directly, use PdfWriter.FloatToStr instead.
    /// </summary>
    public static String floatAsDoubleToStr8Decimals(float val)
    {
        return gInstance.get().floatToStr8Decimals(val);
    }

    /// <summary>
    /// Outputs all digits of a value. Never outputs in the scientific notation.
    ///
    /// Do not use method in PDF renderer directly, use PdfWriter.FloatToStr instead.
    /// </summary>
    public static String floatAsDoubleToStrNDecimals(float val, int n)
    {
        Debug.assertTrue(n >= 0 && n <= 9);

        // RK In Java there seems to be no way to format a float with such pattern and currently
        // in Java we convert it to double and then format, but this causes difference in golds
        // because float 419.6 when converted to double equals 419.6000061xxxx.
        //
        // Therefore here in .NET we have to temporarily cast to double before formatting.
        // I think in the future we will write our own float formatting in Java
        // and change this back to float formatting.
        if ( n < 8)
            return gInstance.get().floatToStrNDecimals(val,n);
        else
            return gInstance.get().floatToStr8Decimals(val);
    }

    /// <summary>
    /// Outputs all digits of a value. Never outputs in the scientific notation.
    ///
    /// Do not use method in PDF renderer directly, use PdfWriter.FloatToStr instead.
    /// </summary>
    public static String floatToStr8Decimals(float val)
    {
        return floatToStrNDecimals(val,8);
    }
    
    /// <summary>
    /// Formats a value into a string using invariant culture and and 0.######### format.
    /// </summary>
    public static String floatAsDoubleToStr9Decimals(float val)
    {
        return gInstance.get().floatToStr9Decimals(val);
    }

    /// <summary>
    /// Formats a value into a string using invariant culture and and 0.######### format.
    /// </summary>
    public static String floatToStr9Decimals(float val)
    {
        return floatToStrNDecimals(val,9);
    }

    /// <summary>
    /// Formats a value into a string using invariant culture and and 0.######### format.
    /// </summary>
    public static String floatToStrNDecimals(float val, int n)
    {
        return gInstance.get().floatToStrNDecimals(val, n);
    }

    /// <summary>
    /// Formats a float value into a string using an invariant culture.
    /// </summary>
    public static String floatToStr(float value)
    {
        return floatToStrNDecimals(value,5);
    }

    /// <summary>
    /// Used when we need to format an MS Word field value and the explicit format string is not given.
    /// We format using a set of flags that we've gathered from the original field value.
    /// </summary>
    /// <param name="value"></param>
    /// <param name="isCurrency">Specifies whether to format as a currency or as a number.</param>
    /// <param name="isUsesGroupSeparator">This parameter only has effect when isCurrence is false.</param>
    /// <param name="numberOfDigitsAfterDecimalpoint">Nuber of digits after decimal point.</param>
    /// <param name="isUsesNegativeParentheses">Specifies whether to format negative number with parentheses or with minus symbol.</param>
    public static String numberToStrMSWordWithNoFormat(double value, boolean isCurrency, boolean isUsesGroupSeparator, int numberOfDigitsAfterDecimalpoint, boolean isUsesNegativeParentheses)
    {
        // FIX WORDSNET-10143 A double value contains 15 decimal digits of precision. The Math.Round method throws an exception, if numberOfDigitsAfterDecimalpoint larger then 15.
        /*final*/ int MAX_ROUNDING_DIGITS = 15;
        numberOfDigitsAfterDecimalpoint = numberOfDigitsAfterDecimalpoint > MAX_ROUNDING_DIGITS ? MAX_ROUNDING_DIGITS : numberOfDigitsAfterDecimalpoint;
        //value = msMath.round(value, digits); //JAVA-deleted: rounded below in PalFormatterCore.decimalFormat()
        DecimalFormat format;
        String negativePrefix = "(";
        if (isCurrency)
        {
            format = PalFormatterCore.getCurrencyFormat();

            // WORDSJAVA-1421. numberOfDigitsAfterDecimalpoint can be 0 or some digit.
            // In case numberOfDigitsAfterDecimalpoint=0 we use default MinimumFractionDigits=2;
            // In case numberOfDigitsAfterDecimalpoint>0 we set the exact number of digits
            if (numberOfDigitsAfterDecimalpoint > 0)
            {
                format.setMinimumFractionDigits(numberOfDigitsAfterDecimalpoint);
                format.setMaximumFractionDigits(numberOfDigitsAfterDecimalpoint);
            }
            else
            {
                format.setMinimumFractionDigits(2);
            }

            if (!isUsesGroupSeparator)
                format.setGroupingUsed(false);

            // WORDSJAVA-1421 Currency symbol is getting replaces with negative prefix.
            // So we should add the symbol if we change negative prefix.
            negativePrefix += format.getCurrency().getSymbol(CurrentThread.getCurrentCulture().getLocale());
        }
        else
        {
            // We originally had here a pattern with 15 digits after the fixed point, but .NET seems to sometimes reduce
            // the number of digits displayed, whereas Java does not reduce them. I don't think it is very important
            // to have the full 15 digit precision in MS Word document field evaluation so I cut down the precision
            // here to a level which lets all gold tests on .NET and Java pass.

            StringBuilder formatBuilder = new StringBuilder(isUsesGroupSeparator ? "#,##0" : "0");
            if (numberOfDigitsAfterDecimalpoint > 0)
                formatBuilder.append(".");

            for (int i = 0; i < numberOfDigitsAfterDecimalpoint; i++)
            {
                // MS Word does not truncate first ending "0", if result is 0. So result should be "0.0".
                if (i == 0 && (int) value == 0)
                {
                    // Java-changed: both operands are not null, we can use usual + here.
                    formatBuilder.append("0");
                    continue;
                }
                formatBuilder.append("#");
			}
            String pattern = formatBuilder.toString();

            // Emulates .Net rounding:
            // 1) rounds half away from zero (using number of decimal digits)
            // 2) rounds to 15 significant digits.
            return PalFormatterCore.decimalFormat(value, pattern, false, isUsesNegativeParentheses);
        }

        boolean useNegativeParentheses = isUsesNegativeParentheses && value < 0;
        if (useNegativeParentheses)
        {
            format.setNegativePrefix(negativePrefix);
            format.setNegativeSuffix(")");
        }

        String result =  format.format(value);

        //JAVA-deleted. see above format.setNegativePrefix/setNegativeSuffix()
        //if (useNegativeParentheses)
        //    result = msString.format("({0})", result);

        // //JAVA-deleted: format.setGroupingUsed(false) used instead, see above.
        //if (isCurrency && !isUsesGroupSeparator)
        //    result = result.replace(Character.toString(format.getDecimalFormatSymbols().getGroupingSeparator()), "");

        // add Currency symbol normalization before we update java currency symbols
        return normalizeCurrencySymbols(result);
    }

    public static String numberToStrMSWord(double value, String format, /*NumberFormattingOptions*/int options)
    {
        IString result = numberToStrMSWord(
                value,
                SystemStringAdapter.create(format),
                options,
                SystemStringBehaviour.INSTANCE);
        return result.toSystemString();
    }

    public static IString numberToStrMSWord(
            double value,
            IString format,
            /*NumberFormattingOptions*/int options,
            IStringBehaviour stringBehaviour)
    {

        IString invariantFormat = NumberFormattingOptionsUtil.hasFormatIsInInvariantCulture(options)
                ? format.replace("\\", "\\\\")
                : localizedFormatToInvariantFormat(format, stringBehaviour);

        return numberToStrMSWordCore(value, invariantFormat, options, stringBehaviour);
    }

    private static IString numberToStrMSWordCore(
            double value,
            IString format,
            /*NumberFormattingOptions*/int options,
            IStringBehaviour stringBehaviour)
    {
        boolean isMultiplyPercent = !NumberFormattingOptionsUtil.hasIsMultiplyPercent(options);
        boolean legacyNumberFormat = NumberFormattingOptionsUtil.hasLegacyNumberFormat(options);

        // In .NET and Java the presence of a % character in a format string causes a number to be multiplied by 100
        // before it is formatted. Word seems to do the same when formatting form fields, but this doesn't happen when
        // formatting expressions in formulas. So we replace % with '%' when we don't want the multiplication to happen.
        // This code used a regex to replace, but the regex was hard to port to Java and I wrote this simplified code
        // on Java and then ported it back to .NET.
        // JAVA-changed to preserve isPercentFormat for further usage
        boolean isPercentFormat = (format.indexOf("%") >= 0) && (format.indexOf("'%'") < 0);
        if (isMultiplyPercent && isPercentFormat)
            format = format.replace("%", "'%'");


        if (legacyNumberFormat)
        {
            String legacyFormat = ensureDigitPlaceholderBeforeGroupSeparator(value, format.toSystemString());
            String legacyResult = msNumber.formatDouble(value, legacyFormat, msCultureInfo.getCurrentCulture());
            return stringBehaviour
                    .createBuilder()
                    .append(legacyResult, null)
                    .toIString();
        }

        return NumberFormatter.numberToString(
                format,
                value,
                msCultureInfo.getCurrentCulture(),
                options,
                stringBehaviour);
    }

    private static String ensureDigitPlaceholderBeforeGroupSeparator(double value, String format)
    {
        // WORDSNET-6921 format string begin with comma - it is accepted by MS Word but invalid in .Net.
        // WORDSNET-7517 ".0,00" format string in Dutch culture - MS Word interprets '.' as thousands separator.
        // WORDSNET-7593 a format string can have multiple sections each of which can be started with any character
        // and can have no digit placeholder before a number group separator at the same time.
        //
        // 1. Start iterate through the source format characters.
        // 2. Continue with format builder characters if any change of the source format is to be applied.
        //
        // Bugs can not be reproduced if value less than 1000
        if (Math.abs(value) < 1000)
            return format;

        StringBuilder formatBuilder = null;
        boolean isDigitPlaceholderMet = false;
        boolean isInLiteral = false;
        int i = 0;
        while (i < ((formatBuilder != null) ? formatBuilder.length() : format.length()))
        {
            char c = (formatBuilder != null) ? formatBuilder.charAt(i) : format.charAt(i);
            switch (c)
            {
                case '0':
                case '#':
                case 'x':
                    // A digit placeholder is met.
                    if (!isInLiteral)
                        isDigitPlaceholderMet = true;
                    break;
                case ',':
                    // An invariant group separator is met.
                    if (!isDigitPlaceholderMet)
                    {
                        // Create on the first demand.
                        if (formatBuilder == null)
                            formatBuilder = new StringBuilder(format);

                        // Add a zero-width digit placeholder.
                        formatBuilder.insert(i, '#');
                        isDigitPlaceholderMet = true;

                        // Jump over inserted character.
                        i++;
                    }
                    break;
                case ';':
                    // New section is started.
                    isDigitPlaceholderMet = false;
                    break;
                case '\'':
                    // String literal.
                    isInLiteral = !isInLiteral;
                    break;
                default:
                    break;
            }
            i++;
        }

        // Build new format if any change of the source format is to be applied.
        return formatBuilder != null
                ? formatBuilder.toString()
                : format;
    }

    /// <summary>
    /// WORDSNET-711 Replaces the current number format items with invariant culture items because we use the .NET formatting facility.
    /// </summary>
    private static IString localizedFormatToInvariantFormat(IString format, IStringBehaviour stringBehaviour)
    {
        //JAVA-added
        if (stringBehaviour.isNullOrEmpty(format))
            return  stringBehaviour.getEmptyString();

        DecimalFormatSymbols currentInfo = NumberFormatProvider.getDecimalFormatSymbols();
        IStringBuilder builder = stringBehaviour.createBuilder();
        int i = 0;

        //JAVA-changed: java's DecimalFormatSymbols returns mainly chars, not strings.
        while (i < format.getLength())
        {
            IChar c = format.get(i);
            if (regionMatches(format, i, "" + currentInfo.getDecimalSeparator()))
            {
                builder.append('.', c);
                i += 1;
            }
            else if (regionMatches(format, i, "" + getNumberGroupSeparatorCurrent())) /* FIX WORDSJAVA-783 */
            {
                builder.append(',', c);
                i += 1;
            }
            else if (regionMatches(format, i, "" + currentInfo.getPercent()))
            {
                builder.append('%', c);
                i += 1;
            }
            else if (regionMatches(format, i, "" + currentInfo.getPerMill()))
            {
                builder.append('‰', c);
                i += 1;
            }
            else
            {
                switch (c.toSystemChar())
                {
                    case '.':
                    case ',':
                    case '\\':
                        builder.append('\\', c);
                        break;
                    default:
                        break;
                }
                builder.append(c);
                i++;
            }
        }

        // WORDSJAVA-488 German group separator ' in Pattern is interpreted as quote start
        // under current Culture like English (and most other).
        // Let's try ' as group separator only if the pattern contains unmatched quotes.
        int numOfQuotes = 0;
        for (int j = 0; j < builder.getLength(); j++)
        {
            if (builder.get(j).toSystemChar() == '\'' && (j == 0 || builder.get(j - 1).toSystemChar() != '\\'))
                numOfQuotes++;
        }
        if (numOfQuotes % 2 == 1)
            builder.replace("#'##", "#,##");

        return builder.toIString();
    }

    /// <summary>
    /// This method is a shortcut to make the calling code shorter. 
    /// </summary>
    private static boolean regionMatches(IString a, int indexA, String b)
    {
        return msString.compareOrdinal(
                   a.toSystemString(),
                   indexA,
                   b,
                   0,
                   b.length()) == 0;
    }

	public static String getShortDatePatternCurrent()
    {
        return getCurrentDateTimeFormatInfo().getShortDatePattern();
    }

    public static String getShortTimePatternCurrent()
    {
        String donNetShortTimePattern = getCurrentDateTimeFormatInfo().getShortTimePattern();

        // On implementing 4848 - Japanese date picture format elements, date formatting no longer
        // passes through non-Word patterns. Only Word patterns should be used.
        /*const*/ String DotNetAmpmPattern = "tt";
        /*const*/ String WordAmpmPattern = "am/pm";
        return donNetShortTimePattern.replace(DotNetAmpmPattern, WordAmpmPattern);
    }

    /// <summary>
    /// Returns native that is platform-specific (i.e. .Net or Java) short date pattern 
    /// of the current thread culture.
    /// </summary>
    /// <returns>short date pattern</returns>
    /// <remarks>
    /// This method is the opposite to GetXxxPatternCurrent method family, 
    /// which tends to return MS Word-specific patterns.
    /// </remarks>
    public static String getCurrentNativeShortDatePattern()
    {
        return getShortDatePatternForCulture(null);
    }

    /// <summary>
    /// Returns native that is platform-specific (i.e. .Net or Java) long time pattern
    /// of the certain culture.
    /// </summary>
    /// <remark>
    /// If the <paramref name="culture"/> is null, the current thread culture is used.
    /// </remark>
    public static String getShortDatePatternForCulture(msCultureInfo culture)
    {
        msDateTimeFormatInfo dateTimeFormatInfo = getCertainDateTimeFormatInfo(culture);
        return dateTimeFormatInfo.getShortDatePattern();
    }

    /// <summary>
    /// Returns native that is platform-specific (i.e. .Net or Java) long time pattern 
    /// of the current thread culture.
    /// </summary>
    /// <returns>long time pattern</returns>
    /// <remarks>
    /// This method is the opposite to GetXxxPatternCurrent method family, 
    /// which tends to return MS Word-specific patterns.
    /// </remarks>
    public static String getCurrentNativeLongTimePattern()
    {
        return getLongTimePatternForCulture(null);
    }

    /// <summary>
    /// Returns native that is platform-specific (i.e. .Net or Java) long time pattern
    /// of the certain culture.
    /// </summary>
    /// <remark>
    /// If the <paramref name="culture"/> is null, the current thread culture is used.
    /// </remark>
    public static String getLongTimePatternForCulture(msCultureInfo culture)
    {
        msDateTimeFormatInfo dateTimeFormatInfo = getCertainDateTimeFormatInfo(culture);
        return dateTimeFormatInfo.getLongTimePattern();
    }

    /// <summary>
    /// Returns time separator of the current thread culture.
    /// </summary>
    /// <returns>time separator</returns>
    public static String getCurrentTimeSeparator()
    {
        return getTimeSeparatorForCulture(null);
    }

    /// <summary>
    /// Returns time separator of the certain culture.
    /// </summary>
    /// <remark>
    /// If the <paramref name="culture"/> is null, the current thread culture is used.
    /// </remark>
    /// <returns>time separator</returns>
    public static String getTimeSeparatorForCulture(msCultureInfo culture)
    {
        // Note that .Net implementation of the <see cref="DateTimeFormatInfo.TimeSeparator"/>
        // returns first occurance of time separator in long time pattern for underlying culture.
        // So Java code must implement the same behavior to avoid any related issues.
        msDateTimeFormatInfo dateTimeFormatInfo = getCertainDateTimeFormatInfo(culture);
        return dateTimeFormatInfo.getTimeSeparator();
    }

    private static msDateTimeFormatInfo getCertainDateTimeFormatInfo(msCultureInfo culture)
    {
        return culture != null
                ? culture.getDateTimeFormat()
                : getCurrentDateTimeFormatInfo();
    }

    /// <summary>
    /// If <see cref="DateTimeFormatInfo.CurrentInfo"/> clone is present then returns
    /// its value, otherwise returns <see cref="DateTimeFormatInfo.CurrentInfo"/> itself.
    /// </summary>
    /// <returns><see cref="DateTimeFormatInfo"/> object for the current thread</returns>
    private static msDateTimeFormatInfo getCurrentDateTimeFormatInfo()
    {
        if (gCurrentDateTimeFormatInfoClone != null && gCurrentDateTimeFormatInfoClone.get() != null)
            return gCurrentDateTimeFormatInfoClone.get();

	    return msDateTimeFormatInfo.getCurrentInfo();
    }

//>>>>>>>> #region  Tests

    /// <summary>
    /// Sets native short date pattern for the current thread culture in testing purpose.
    /// </summary>
    /// <param name="shortDatePattern">short date pattern</param>
    public static void setCurrentNativeShortDatePattern(String shortDatePattern)
    {
        ensureCurrentDateTimeFormatInfoClone();
        gCurrentDateTimeFormatInfoClone.get().setShortDatePattern(shortDatePattern);
    }

    /// <summary>
    /// Sets native long time pattern for the current thread culture in testing purpose.
    /// </summary>
    /// <param name="longTimePattern">long time pattern</param>
    public static void setCurrentNativeLongTimePattern(String longTimePattern)
    {
        ensureCurrentDateTimeFormatInfoClone();
        gCurrentDateTimeFormatInfoClone.get().setLongTimePattern(longTimePattern);
    }

    /// <summary>
    /// Sets time separator of the current thread culture in testing purpose.
    /// </summary>
    /// <param name="timeSeparator">time separator</param>
    public static void setCurrentTimeSeparator(String timeSeparator)
    {
        ensureCurrentDateTimeFormatInfoClone();
        gCurrentDateTimeFormatInfoClone.get().setTimeSeparator(timeSeparator);
    }

    /// <summary>
    /// Stores <see cref="DateTimeFormatInfo.CurrentInfo"/> clone if it's not stored yet.
    /// </summary>
    private static void ensureCurrentDateTimeFormatInfoClone()
    {
        if (gCurrentDateTimeFormatInfoClone.get() == null)
            gCurrentDateTimeFormatInfoClone.set(msDateTimeFormatInfo.getCurrentInfo().deepClone());
    }

    /// <summary>
    /// Sets current thread date/time format properties to initial values, which were 
    /// before any of <see cref="FormatterPal"/> class method call changes to be applied.
    /// </summary>
    public static void setInitialCurrentDateTimeFormatInfo()
    {
        gCurrentDateTimeFormatInfoClone.remove();;
    }

//<<<<<<<< #endregion  Tests

	public static char getDecimalSeparatorCurrent()
    {
        return NumberFormatProvider.getDecimalSeparator();
    }

    public static char getNumberGroupSeparatorCurrent()
    {
        // .NET uses a non-breaking space as the numeric group separator for some locales. Let's do the same here.
        return checkSpecificLocale(CurrentThread.getCurrentCulture().getLocale()) ?
                NBSP_NUMBER_GROUPING_SEPARATOR : NumberFormatProvider.getGroupingSeparator();
    }

    public static int getNumberGroupSizeCurrent()
    {
        return NumberFormatProvider.getGroupingSize();
    }

    public static char getListSeparatorCurrent()
    {
        String separator = msCultureInfo.getCurrentCulture().getTextInfo().getListSeparator();
        return (StringUtil.hasChars(separator)) ? separator.charAt(0) : ',';
    }

    public static String getCurrencySymbolCurrent()
    {
        if (FRENCH_SWISS_CULTURE.equals(SystemPal.getCurrentCultureName()))
            return FR_CH_CURRENCY;
        else
            return NumberFormatProvider.getCurrencySymbol();
    }

    public static void setCurrencySymbolCurrent(String currencySymbol)
    {
        NumberFormatProvider.setCurrencySymbol(currencySymbol);
    }

    /// <summary>
    /// WORDSNET-3679 For "fr-CH" culture might be set either of the two currency symbols - "SFr." or "fr.".
    /// WORDSNET-17864 For "fr-CH" culture the currency symbol is "CHF" in .NET 4.0 and above.
    /// </summary>
	public static String normalizeCurrencySymbols(String money)
    {
        String currentCulture = SystemPal.getCurrentCultureName();

        if (FRENCH_SWISS_CULTURE.equals(currentCulture))
        {
            money = normalizeCurrencySymbols(money, OLD_MAIN_FR_CH_CURRENCY);
            money = normalizeCurrencySymbols(money, OLD_OPTIONAL_FR_CH_CURRENCY);

            // Looks like this group separator is also not supported by .NET 4.0.
            money = money.replace("\'", "");
        } else if (RUSSIAN_CULTURE.equals(currentCulture) && money.contains(OPTIONAL_RUSSIAN_CURRENCY))
            money = money.replace(OPTIONAL_RUSSIAN_CURRENCY, MAIN_RUSSIAN_CURRENCY);

        return money;
    }

    private static String normalizeCurrencySymbols(String money, String oldCurrencySymbol)
    {
        if (money.indexOf(oldCurrencySymbol) >= 0)
        {
            money = money.replace(oldCurrencySymbol, "");
            money = msString.format("{0} {1}", money, FR_CH_CURRENCY);
        }

        return money;
    }

    /// <summary>
    /// Parses an XML string into an unsigned integer value.
    /// </summary>
    public static int xmlToIntAsUnsigned(String val)
    {
        return xmlToInt(val) & 0xFFFF;
    }

    /// <summary>
    /// Returns true if the string represents an integer number.
    /// </summary>
    public static boolean isInteger(String value)
    {
        if (value == null)
            return false;

        // skip leading whitespaces
        int startPos = 0;
        while (startPos < value.length())
        {
            if (!msChar.isWhiteSpace(value.charAt(startPos)))
                break;

            ++startPos;
        }

        // skip trailing whitespaces
        int endPos = value.length() - 1;
        while (endPos > startPos)
        {
            if (!msChar.isWhiteSpace(value.charAt(endPos)))
                break;

            --endPos;
        }

        if (endPos < startPos)
            return false;

        int i = startPos;
        char startChar = value.charAt(startPos);
        if (startChar == '-' || startChar == '+')
        {
            if (startPos == endPos)
                return false;

            i = startPos + 1;
        }

        long res = 0;
        for (; i <= endPos; ++i)
        {
            char c = value.charAt(i);
            if (c < '0' || c > '9')
                return false;

            res = res * 10 + (c - '0');

            // Concern with potential overflow problem
            if (startChar == '-')
            {
                if (-res < Integer.MIN_VALUE)
                    return false;
            }
            else if (res > Integer.MAX_VALUE)
            {
                return false;
            }
        }

        return true;
    }

    public static String doubleToStr(double val, String format, msCultureInfo ci)
    {
        return msNumber.formatDouble(val, format, ci);
    }

    public static String doubleToStr(double value, String format)
    {
        return doubleToStr(value, format, msCultureInfo.getCurrentCulture());
    }

    private static boolean checkSpecificLocale(Locale locale)
    {
        return AUSTRIAN_LOCALE_NAME.equals(locale.toString()) || SETSWANA_LOCALE_NAME.equals(locale.toString());
    }

    // This is taken from http://download.oracle.com/javase/6/docs/api/java/lang/ThreadLocal.html
    // It ensures each thread gets its own formatter instance.
    private static final ThreadLocal<PalFormatterCore> gInstance = new ThreadLocal<PalFormatterCore>()
    {
        @Override
        protected PalFormatterCore initialValue()
        {
            return new PalFormatterCore();
        }
    };

    /// <summary>
    /// Stores <see cref="DateTimeFormatInfo.CurrentInfo"/> writable clone.
    /// </summary>
    //@ThreadStatic
    private static final ThreadLocal<msDateTimeFormatInfo> gCurrentDateTimeFormatInfoClone = new ThreadLocal<>();

	private static final String FRENCH_SWISS_CULTURE = "fr-CH";
    private static final String FR_CH_CURRENCY = "CHF";
    private static final String OLD_MAIN_FR_CH_CURRENCY = "SFr.";
    private static final String OLD_OPTIONAL_FR_CH_CURRENCY = "fr.";

	private static final String RUSSIAN_CULTURE = "ru-RU";
	private static final String MAIN_RUSSIAN_CURRENCY = "руб.";
	private static final String OPTIONAL_RUSSIAN_CURRENCY = "р.";

    private static final String[] gXmlDateTimeFormats = new String[]
    {
        "yyyy-MM-ddTHH:mm:ss.FFFFFFFzzz", 
        "yyyy-MM-ddTHH:mm:ss.FFFFFFFK", 
        "yyyy-MM-ddTHH:mm:ss.FFFFFFF", 
        "yyyy-MM-ddTHH:mm:ss", 
        "yyyy-MM-ddTHH:mm:ss%K",
        // FIX 24344. Added another one format.
        "yyyy-MM-dd"
    };
    
    private static /*final*/ msDateTimeFormatInfo G_EN_US_DATE_TIME_FORMAT;
    private static /*final*/ msDateTimeFormatInfo G_EN_NZ_DATE_TIME_FORMAT;

    private static /*final*/ msDateTimeFormatInfo G_EN_US_DATE_TIME_FORMAT_MS_WORD_TWO_DIGIT_YEAR_MAX;
    private static /*final*/ msDateTimeFormatInfo G_EN_NZ_DATE_TIME_FORMAT_MS_WORD_TWO_DIGIT_YEAR_MAX;

    private static final int MS_WORD_TWO_DIGIT_YEAR_MAX = 2029;

    private static final String AUSTRIAN_LOCALE_NAME = "de_AT";
    private static final String SETSWANA_LOCALE_NAME = "tn_ZA";
    private static final char NBSP_NUMBER_GROUPING_SEPARATOR = '\u00A0';
    static/* FormatterPal()*/
    {
        G_EN_US_DATE_TIME_FORMAT = SystemPal.getCulture("en-US").getDateTimeFormat();
        G_EN_US_DATE_TIME_FORMAT_MS_WORD_TWO_DIGIT_YEAR_MAX = applyMsWordTwoDigitYearMax(G_EN_US_DATE_TIME_FORMAT);

        G_EN_NZ_DATE_TIME_FORMAT = SystemPal.getCulture("en-NZ").getDateTimeFormat();
        G_EN_NZ_DATE_TIME_FORMAT_MS_WORD_TWO_DIGIT_YEAR_MAX = applyMsWordTwoDigitYearMax(G_EN_NZ_DATE_TIME_FORMAT);
    }
}
