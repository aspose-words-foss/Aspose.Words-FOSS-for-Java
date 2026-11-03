//Konstantin Sidorenko 29/07/2010

package com.aspose.ms.System;

import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.ms.System.Threading.CurrentThread;
import com.aspose.ms.java.debug.JavaDebug;
import com.aspose.ms.java.logging.Log;
import com.aspose.ms.java.text.NumberFormatProvider;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * In .Net System.Number class contains implementation of formatting and parsing for all numeric types.
 * But most formatting functionality implemented in native Number C++ class. So we can't port the class as is:(.
 * Main formatting idea adapted from java.lang.Integer.
 * <p/>
 * Note: At the moment realized only simplified formatting of byte, short, int and long objects.
 * Float, Double, BigInteger and BigDecimal doesn't supported yet.
 */
public class msNumber
{
    /**
     * Decides whether the given object is one of numeric class or not.
     *
     * @param value can be either Integer, Byte, Short, Long, Float or Double
     */
    public static boolean isNumber(Object value)
    {
        return value instanceof Number;
    }

    /**
     * Formats the given number to a string according to the given format.
     * This method is trying to do the same as IFormattable.ToString does.
     * At the moment it handles only standard format
     * see http://msdn.microsoft.com/en-us/library/dwhawy9k(v=vs.110).aspx#EFormatString
     *
     * @return formatted string. The result can be different from .NET's one in some cases.
     */
    public static String toString(Object arg, String fmt)
    {
        return toString(arg, fmt, CurrentThread.getCurrentCulture());
    }

    /**
     * See above. The same but you can specify the current Locale via CultureInfo.
     */
    public static String toString(Object arg, String fmt, msCultureInfo info)
    {
        if (arg == null)
            return null;

        if (fmt == null || fmt.length() == 0)
            return arg.toString();

        if (isStandard(fmt))
        {
            char specifier = Character.toUpperCase(fmt.charAt(0));
            if (gStandardFormatHandlers.containsKey(specifier))
                return gStandardFormatHandlers.get(specifier).toString(arg, fmt, info);
        }

        return format(arg, fmt);
    }

    /**
     * Determines whether the given format is a one of standard specifier.
     * see http://msdn.microsoft.com/en-us/library/dwhawy9k(v=vs.110).aspx#EFormatString
     */
    private static boolean isStandard(String format)
    {
        if (format.length() < 1 || format.length() > 3)
            return false;

        return gStandardFormatHandlers.containsKey(Character.toUpperCase(format.charAt(0)));
    }

    /**
     * Retrieves the number from the standard specifier.
     * e.g. if format is C4 then the fraction digits will be 4 etc
     */
    private static int getFractionDigits(String fmt)
    {
        int fractionDigits = -1;
        if (fmt.length() > 1)
        {
            fractionDigits = fmt.length() > 2 ?
                             Integer.valueOf(fmt.substring(1, 3)) :
                             Integer.valueOf(fmt.substring(1, 2));
        }

        return fractionDigits;
    }

    private static Map<Character, IFormattable> gStandardFormatHandlers = new HashMap<Character, IFormattable>()
    {{
        put('C', new IFormattable()
        {
            public String toString(Object val, String format, msCultureInfo culture)
            {
                NumberFormat formatter = NumberFormatProvider.newCurrencyInstance(culture.getLocale());
                int fractionDigits = getFractionDigits(format);
                formatter.setMinimumFractionDigits(fractionDigits == -1 ? 0 : fractionDigits);
                return formatter.format(val);
            }
        });
        put('E', new IFormattable()
        {
            public String toString(Object val, String format, msCultureInfo culture)
            {
                int fractionDigits = getFractionDigits(format);
                fractionDigits = (fractionDigits == -1 ? 6 : fractionDigits);
                StringBuilder formatted =
                        new StringBuilder(String.format(culture.getLocale(), "%." + fractionDigits + "e", val));
                // Since .NET prints out e+000 <- three places in degree but Java prints out as e+00
                // We will have to add one extra zero at the beginning of degree number.
                // I didn't manage to find a way to do it with String.format.
                int extraZeroBeginningIdx = formatted.lastIndexOf("e") + 2;
                formatted.insert(extraZeroBeginningIdx, '0');
                return formatted.toString();
            }
        });
        put('F', new IFormattable()
        {
            public String toString(Object val, String format, msCultureInfo culture)
            {
                int fractionDigits = getFractionDigits(format);
                fractionDigits = (fractionDigits == -1 ? 2 : fractionDigits);
                return String.format(culture.getLocale(), "%." + fractionDigits + "f", val);
            }
        });
        put('G', new IFormattable()
        {
            public String toString(Object val, String format, msCultureInfo culture)
            {
                int fractionDigits = getFractionDigits(format);
                fractionDigits = (fractionDigits == -1 ? 2 : fractionDigits);
                return String.format(culture.getLocale(), "%" + fractionDigits + "g", val);
            }
        });
        put('N', new IFormattable()
        {
            public String toString(Object val, String format, msCultureInfo culture)
            {
                int fractionDigits = getFractionDigits(format);
                fractionDigits = (fractionDigits == -1 ? 2 : fractionDigits);
                return String.format(culture.getLocale(), "%#,." + fractionDigits + "f", val);
            }
        });
        put('P', new IFormattable()
        {
            public String toString(Object val, String format, msCultureInfo culture)
            {
                NumberFormat formatter = NumberFormatProvider.newPercentInstance(culture.getLocale());
                int fractionDigits = getFractionDigits(format);
                formatter.setMinimumFractionDigits(fractionDigits == -1 ? 2 : fractionDigits);
                return formatter.format(val);
            }
        });
        put('R', new IFormattable()
        {
            public String toString(Object val, String format, msCultureInfo culture)
            {
                NumberFormat formatter = NumberFormatProvider.newNumberInstance(culture.getLocale());
                int fractionDigits = val instanceof Integer ? 7 : 16;
                formatter.setMinimumFractionDigits(fractionDigits);
                return formatter.format(val);
            }
        });

        //put('D', null); fallback to format() in toString(arg, fmt) method
        //put('X', null); fallback to format() in toString(arg, fmt) method
    }};

    interface IFormattable
    {
        String toString(Object val, String format, msCultureInfo culture);
    }

    //JAVA-added

    /**
     * In .Net each number value type has a method ToString(format) that just calls
     * Number.FormatInt32(value, format), FormatDouble(), etc.
	 *
     * Let's use in java this single method to call appropriate formatXXX() implementation.
     */
    public static String format(Object arg, String fmt)
    {
        // Format signed Java byte as sbyte .NET one.
        if (arg instanceof Byte)
            return formatByte((Byte) arg, fmt);

        if (arg instanceof Short)
            return formatInt16((Short) arg, fmt);

        if (arg instanceof Integer)
            return formatInt32((Integer) arg, fmt);

        if (arg instanceof Long)
            return formatInt64((Long) arg, fmt);

        if (arg instanceof Float)
            return formatFloat((Float) arg, fmt);

        if (arg instanceof Double)
            return formatDouble((Double) arg, fmt);

        if (arg instanceof BigDecimal || arg instanceof BigInteger)
            return new DecimalFormat(fmt).format(arg);

        // silently return default string representation for unsupported types:
        // BigInteger and BigDecimal.
        return arg.toString();
    }

    /**
     * Simplified formatting: only d, D, x and X formatting supported at the moment.
     * All other silently formatted with default decimal string format.
     */
    public static String formatByte(byte value, String format)
    {
        int digits = 0;
        int[] referenceToDigits = {digits};
        char fmt = parseFormatSpecifier(format, /*out*/ referenceToDigits);
        digits = referenceToDigits[0];

        switch (fmt)
        {
            case 'x':
                return int32ToHexString(value & 0xFF, digits, maxByteHexDigits, false);
            case 'X':
                return int32ToHexString(value & 0xFF, digits, maxByteHexDigits, true);
            case 'd':
            case 'D':
            default:
                return int32ToDecString(value, digits);
        }
    }

    /**
     * Simplified formatting: only d, D, x and X formatting supported at the moment.
     * All other silently formatted with default decimal string format.
     */
    public static String formatInt16(short value, String format)
    {
        int digits = 0;
        int[] referenceToDigits = {digits};
        char fmt = parseFormatSpecifier(format, /*out*/ referenceToDigits);
        digits = referenceToDigits[0];

        switch (fmt)
        {
            case 'x':
                return int32ToHexString(value & 0xFFFF, digits, maxShortHexDigits, false);
            case 'X':
                return int32ToHexString(value & 0xFFFF, digits, maxShortHexDigits, true);
            case 'd':
            case 'D':
            default:
                return int32ToDecString(value, digits);
        }
    }

    public static String formatInt32(int value, String format, msCultureInfo ci)
    {
        return formatInt32(value, format);
    }

    /**
     * Simplified formatting: only d, D, x and X formatting supported at the moment.
     * All other silently formatted with default decimal string format.
     */
    public static String formatInt32(int value, String format)
    {
        int digits = 0;
        int[] referenceToDigits = {digits};
        char fmt = parseFormatSpecifier(format, /*out*/ referenceToDigits);
        digits = referenceToDigits[0];

        switch (fmt)
        {
            case 'x':
                return int32ToHexString(value, digits, maxIntHexDigits, false);
            case 'X':
                return int32ToHexString(value, digits, maxIntHexDigits, true);
            case 'd':
            case 'D':
            default:
                return int32ToDecString(value, digits);
        }
    }

    /**
     * Simplified version of String formatInt32(int value, String format) for cases when format string
     * is null (not specified). Don't create additional int[1] instance in heap memory and run faster.
     */
    public static String formatInt32(int value)
    {
        return Integer.toString(value);
    }

    public static String formatInt64(long value, String format, msCultureInfo ci)
    {
        return formatInt64(value, format);
    }

    /**
     * Simplified formatting: only d, D, x and X formatting supported at the moment.
     * All other silently formatted with default decimal string format.
     */
    public static String formatInt64(long value, String format)
    {
        int digits = 0;
        int[] referenceToDigits = {digits};
        char fmt = parseFormatSpecifier(format, /*out*/ referenceToDigits);
        digits = referenceToDigits[0];

        switch (fmt)
        {
            case 'x':
                return int64ToHexString(value, digits, false);
            case 'X':
                return int64ToHexString(value, digits, true);
            case 'd':
            case 'D':
            default:
                return int64ToDecString(value, digits);
        }
    }

    /**
     * Simplified version of String formatInt64(int value, String format) for cases when format string
     * is null(not specified). Don't create additional int[1] instance in heap memory and run faster.
     */
    public static String formatInt64(long value)
    {
        return Long.toString(value);
    }

    /**
     * Java doesn't support .Net formatting with ZeroPattern: PositivePattern;NegativePattern;ZeroPattern.
     * Let's extract ZeroPattern for zero values and remove it for all others.
     */
    public static String normalizeZeroPattern(String format, double value)
    {
        if (!containsZeroPattern(format))
            return format;

        if (isZero(value))
            return extractZeroPattern(format);
        else
            return removeZeroPattern(format);
    }

    /**
     * Backslash is used as an escape character according to https://msdn.microsoft.com/en-us/library/0c899ak8(v=vs.110).aspx.
     * In Java (http://docs.oracle.com/javase/7/docs/api/java/text/DecimalFormat.html) single quotes are used as escape characters.
     *
     * So this method replaces backslash character from the given pattern with two single quotes (before and after
     * escaped character). In case if backslash defines an unicode character then this backslash will not be replaced.
     * e.g. #,##0;\-#,##0\[space] will be changed to #,##0;'-'#,##0'space'. See TestFormatDouble.
     *
     * If the pattern contains more than one successive escaped characters then we use only one pair single quotes
     * to enclose all escaped characters.
     * e.g. 0.00\[space]\-\[space] will be changed to 0.00'[space]-[space]', rather than 0.00'[space]''-''[space]'.
     * All characters between two double quotes will not be replaced. Double quotes will be replaced with single
     * quotes if needed.
     * e.g. 0.00\" percent\" will be changed to 0.00' percent'. See TestFormatDouble.
     */
    public static String escapeBackSlashes(String pattern)
    {
        if (msString.isNullOrEmpty(pattern))
            return pattern;

        pattern = mergeDoubleQuotedSuccessiveSubstrings(pattern);
        StringBuilder sb = new StringBuilder();
        boolean singleQuoteStarted = false;
        boolean doubleQuoteStarted = false;
        int length = pattern.length();
        int i = 0;
        while (i < length)
        {
            char curChar = pattern.charAt(i);
            if (curChar == gDoubleQuote)
            {
                doubleQuoteStarted = !doubleQuoteStarted;
                // Replace double quotes with single quotes if needed.
                if (!singleQuoteStarted)
                    sb.append(gSingleQuote);

                i++;
                continue;
            }
            if (doubleQuoteStarted)
            {
                // The enclosed characters should be copied to the result string unchanged.
                // Just copy character as is and move to the next character in pattern.
                sb.append(curChar);
                i++;
                continue;
            }
            if (curChar == gBackslash)
            {
                // if backslash is the last element in pattern then skip it
                if (i + 1 == length)
                    break;

                // if backslash defines unicode char then skip it
                char nextChar = pattern.charAt(i + 1);
                if (nextChar == gUnicodeDefChar)
                {
                    i += 2;
                    continue;
                }

                if (!singleQuoteStarted)
                {
                    sb.append(gSingleQuote);
                    singleQuoteStarted = true;
                }

                sb.append(nextChar);
                i += 2;
            }
            else
            {
                if (singleQuoteStarted)
                {
                    sb.append(gSingleQuote);
                    singleQuoteStarted = false;
                }

                sb.append(curChar);
                i++;
            }
        }
        if (singleQuoteStarted)
            sb.append(gSingleQuote);

        return sb.toString();
    }

    /**
     * Used solely for preliminary processing of the input string in escapeBackSlashes().
     * If input string contains two(or more) adjacent substrings enclosed in double quotes, then "merge" them, skipping intermediate quotes.
     * "ab""cd""ef" -> "abcdef"
     * "ab""""cd" -> "abcd"
     * If double quote has no pair, it is just skipped.
     * "ab"cd"ef -> "ab"cdef
     * "ab"""cd" -> "ab"cd
     */
    private static String mergeDoubleQuotedSuccessiveSubstrings(String in)
    {
        StringBuilder out = new StringBuilder();

        int len = in.length();
        int i = 0, j, k;
        char c;
        while (i < len)
        {
            c = in.charAt(i);
            if (c == gDoubleQuote)
            {
                boolean done = false;
                // found "open" quote
                // now try to find "close" quote
                j = in.indexOf(gDoubleQuote, i + 1);
                if (j == -1)
                {
                    // "close" quote not found
                    out.append(in, i + 1, len);
                    break;
                }

                while (j < len)
                {
                    if (j + 1 == len)
                    {
                        if (getLength(i + 1, j) > 0)
                            addQuotedSubstring(out, in, i + 1, j);

                        done = true;
                        break;
                    }
                    else if (in.charAt(j + 1) == gDoubleQuote)
                    {
                        k = in.indexOf(gDoubleQuote, j + 2);
                        if (k == -1)
                        {
                            if (getLength(i + 1, j) > 0)
                                addQuotedSubstring(out, in, i + 1, j);

                            out.append(in, j + 2, len);
                            done = true;
                            break;
                        }
                        else
                        {
                            j = k;
                            continue;
                        }
                    }
                    else
                    {
                        if (getLength(i + 1, j) > 0)
                            addQuotedSubstring(out, in, i + 1, j);

                        i = j + 1;
                        done = false;
                        break;
                    }
                }
                if (done)
                    break;
            }
            else
            {
                out.append(c);
                i++;
            }
        }

        return out.toString();
    }

    private static void addQuotedSubstring(StringBuilder sb, String s, int start, int end)
    {
        sb.append('\"');
        for (int i = start; i < end; i++)
        {
            char c = s.charAt(i);
            if (c != gDoubleQuote)
                sb.append(c);
        }
        sb.append('\"');
    }

    private static int getLength(int start, int end)
    {
        return end - start;
    }

    /// <summary>
    /// Return true if Double value is zero.
    /// </summary>
    /// <param name="value">Double value</param>
    private static boolean isZero(double value)
    {
        return (Math.abs(value) < Double.MIN_VALUE);
    }

    // In .NET a number format can be PositivePattern;NegativePattern;ZeroPattern but in Java it can only
    // have PositivePattern;NegativePattern and no ZeroPattern. This code checks if string contains .NET-style
    // ZeroPattern
    private static boolean containsZeroPattern(String format)
    {
        if (format == null)
            return false;

        int semicolonCount = 0;
        for (int i = 0; i < format.length(); i++)
        {
            // RK This is a very simplistic search. Escaping of ; is not supported and I am not sure if it is needed.
            if (format.charAt(i) == ';')
            {
                semicolonCount++;
                if (semicolonCount >= 2)
                    return true;
            }
        }
        return false;
    }

    // In .NET a number format can be PositivePattern;NegativePattern;ZeroPattern but in Java it can only
    // have PositivePattern;NegativePattern and no ZeroPattern.
    // DecimalFormat will not be able to parse the pattern and throw an exception.
    // So we extract ZeroPattern explicitly for zero double value
    // Important: call this method only for zero double values formatting
    private static String extractZeroPattern(String format)
    {
        int startIndex = -1;
        int endIndex = -1;
        int semicolonCount = 0;
        for (int i = 0; i < format.length(); i++)
        {
            // RK This is a very simplistic search. Escaping of ; is not supported and I am not sure if it is needed.
            if (format.charAt(i) == ';')
            {
                semicolonCount++;
                if (semicolonCount == 2)
                    startIndex = i + 1;
                if (semicolonCount == 3)
                    endIndex = i;
            }
        }

        if (startIndex < 0)
            return format;

        if (endIndex < 0)
        {
            format = format.substring(startIndex);
        }
        else
        {
            // Having more than 3 semicolons seems strange and should be ignored on .NET side
            // http://msdn.microsoft.com/en-us/library/0c899ak8(v=vs.110).aspx#SectionSeparator
            // but from test for WORDSJAVA-926 I see that .NET doesn't throw any exceptions
            // and treats third section as ZeroPattern
            format = format.substring(startIndex, endIndex);
        }

        return format;
    }

    // In .NET a number format can be PositivePattern;NegativePattern;ZeroPattern but in Java it can only
    // have PositivePattern;NegativePattern and no ZeroPattern. This code removes the ZeroPattern, otherwise
    // DecimalFormat will not be able to parse the pattern and throw an exception.
    private static String removeZeroPattern(String format)
    {
        int semicolonCount = 0;
        for (int i = 0; i < format.length(); i++)
        {
            // RK This is a very simplistic search. Escaping of ; is not supported and I am not sure if it is needed.
            if (format.charAt(i) == ';')
            {
                semicolonCount++;
                if (semicolonCount >= 2)
                    return format.substring(0, i);
            }
        }
        return format;
    }

    public static String formatDouble(double value, String format)
    {
        // .Net formats '1.0' as '1'
        if (format == null && ((long) value == value))
            return formatInt64((long) value);

        // FIX WORDSJAVA-1100: Wrong number formatting in chart label.
        // Use NumberFormatter to format double values according to .Net formatting.
        return NumberFormatter.numberToString(format, value, CurrentThread.getCurrentCulture());
    }

    public static String formatDouble(double value, String format, msCultureInfo ci)
    {
        // .Net formats '1.0' as '1'
        if (format == null && ((long) value == value))
            return formatInt64((long) value);

        // FIX WORDSJAVA-1100: Wrong number formatting in chart label.
        // Use NumberFormatter to format double values according to .Net formatting.
        return NumberFormatter.numberToString(format, value, ci);
    }

    public static String formatDouble(double value, msCultureInfo ci)
    {
        // FIX WORDSJAVA-1100: Wrong number formatting in chart label.
        // Use NumberFormatter to format double values according to .Net formatting.
        return NumberFormatter.numberToString("", value, ci);
    }

    public static String formatDouble(double value, DecimalFormatSymbols nfi)
    {
        // Use NumberFormatter to format double values according to .Net formatting.
        return NumberFormatter.numberToString("", value, nfi);
    }

    public static String formatFloat(float value, String format, msCultureInfo ci)
    {
        // .Net formats '1.0' as '1'
        if (format == null && ((long) value == value))
            return formatInt64((long) value);

        // FIX WORDSJAVA-1100: Wrong number formatting in chart label.
        // Use NumberFormatter to format double values according to .Net formatting.
        return NumberFormatter.numberToString(format, value, ci);
    }

    public static String formatFloat(float value, String format)
    {
        // .Net formats '1.0' as '1'
        if (format == null && ((long) value == value))
            return formatInt64((long) value);

        format = escapeBackSlashes(format);
        format = normalizeZeroPattern(format, value);

        // WORDSJAVA-1535. Check if there is Number scaling specifier exists.
        NumberParserResult parserResult = normalizeScalingSpecifier(format, value);
        if (parserResult != null)
        {
            format = parserResult.getFormat();
            value = parserResult.getValue();
        }

        try
        {
            DecimalFormat decimalFormat = getDecimalFormat(format);
            return decimalFormat.format(value);
        }
        catch (IllegalArgumentException e)
        {
            // WORDSJAVA-1980 at the moment we make an attempt to fix this kind of errors only
            // If this method throws any exception then the previous workflow is restored
            return tryFormatFloatWithUnexpectedZeros(value, format);
        }
    }

    private static String tryFormatFloatWithUnexpectedZeros(float value, String format)
    {
        if (format == null)
            return null;

        Map<Integer, Character> positionsOfIgnoredChars = new LinkedHashMap<Integer, Character>();

        // Java rules:
        // integer part    := '#'* '0'* '0'
        // fraction part  := '0'* '#'*
        // .NET literally does the following:
        // In integral part, if '#' comes after '0' we replace them all with '0'.
        // In fraction part, if '#' comes before '0' we replace them all with '0'
        StringBuilder newFormat = new StringBuilder();
        fixIntegralPart(newFormat, format, positionsOfIgnoredChars);
        fixFractionPart(newFormat, format, positionsOfIgnoredChars);

        // updated format e.g. Before [$-409]#,##0.0 after [$-400,000.0
        String newFormatValue = newFormat.toString();
        newFormat.setLength(0); // reuse

        int decimalDotPos = getDecimalDotPos(newFormatValue);

        DecimalFormat decimalFormat = getDecimalFormat(newFormatValue); // here an exception could be thrown.
        String fixedFormattedValue = decimalFormat.format(value);

        int newDecimalDotPos = getDecimalDotPos(fixedFormattedValue);
        newFormat.append(fixedFormattedValue);

        return restoreIgnoredChars(positionsOfIgnoredChars, decimalDotPos, newDecimalDotPos, newFormat);
    }

    private static String restoreIgnoredChars(Map<Integer, Character> positionsOfIgnoredChars,
                                              int decimalDotPos, int newDecimalDotPos,
                                              StringBuilder formattedValueWithoutIgnoredChars)
    {
        // save this in case if the restoring of ignored chars fails
        String formattedValue = formattedValueWithoutIgnoredChars.toString();

        if (!positionsOfIgnoredChars.isEmpty())
        {
            // Other chars and signs could be added at the beginning or the ending of the output e.g. '-', '$' etc
            // If any new characters have been added then find the new position of the ignored characters according to the new 'decimal point' position.
            int offset = newDecimalDotPos - decimalDotPos;

            // restore the ignored characters based on the new 'decimal point' position
            try
            {
                for (Map.Entry<Integer, Character> ignoredChars : positionsOfIgnoredChars.entrySet())
                    formattedValueWithoutIgnoredChars.insert(offset + ignoredChars.getKey(), ignoredChars.getValue().charValue());

                // restoring went well
                formattedValue = formattedValueWithoutIgnoredChars.toString();
            }
            catch (Throwable e)
            {
                // In this case is better to return the formatted value without ignored chars
                Log.warn(e);
            }
        }

        return formattedValue;
    }

    private static int getDecimalDotPos(String chars)
    {
        for (int i = chars.length() - 1; i > 0; i--)
        {
            if (chars.charAt(i) == '.')
                return i;
        }

        return -1;
    }

    private static void fixFractionPart(StringBuilder newFormat, String formatChars, Map<Integer, Character> positionAndChars)
    {
        boolean rangeStarted = false;
        int lastCharIdx = formatChars.length() - 1;
        for (int i = lastCharIdx; i > 0; i--)
        {
            char ch = formatChars.charAt(i);
            if (ch == '.')
            {
                return;
            }
            if (ch == '0')
            {
                rangeStarted = true;
                newFormat.append(ch);
            }
            else if (ch == '#')
            {
                if (rangeStarted)
                    newFormat.append('0');
                else
                    newFormat.append(ch);
            }
            else
            {
                if (rangeStarted || Character.isDigit(ch))
                    positionAndChars.put(lastCharIdx - i, ch);
                else
                    newFormat.append(ch);
            }
        }
    }

    private static void fixIntegralPart(StringBuilder newFormat, String formatChars, Map<Integer, Character> positionAndChars)
    {
        boolean rangeStarted = false;
        for (int i = 0; i < formatChars.length(); i++)
        {
            char ch = formatChars.charAt(i);
            if (ch == '.')
            {
                newFormat.append(ch);
                return;
            }
            else if (ch == '0')
            {
                newFormat.append(ch);
                rangeStarted = true;
            }
            else if (ch == '#')
            {
                if (rangeStarted)
                    newFormat.append('0');
                else
                    newFormat.append(ch);
            }
            else if (ch == ',')
            {
                newFormat.append(ch);
            }
            else
            {
                if (rangeStarted || Character.isDigit(ch))
                    positionAndChars.put(i, ch);
                else
                    newFormat.append(ch);
            }
        }
    }

    private static DecimalFormat getDecimalFormat(String format)
    {
        // will not work on all patterns - wait manual porting of .Net number formatting.
        DecimalFormat decimalFormat = NumberFormatProvider.newInstance();
        if (format != null && !"D".equals(format))
        {
            format = format.replace("\"", "");
            decimalFormat.applyPattern(format);
        }

        return decimalFormat;
    }

    private static char parseFormatSpecifier(String format, /*out*/ int[] digits)
    {
        if (format != null)
        {
            int index = 0;
            char c = get(format, index);
            if (c != 0)
            {
                if (c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z')
                {
                    index++;
                    int n = -1;
                    c = get(format, index);
                    if (c >= '0' && c <= '9')
                    {
                        n = (c - '0');
                        index++;
                        c = get(format, index);
                        while (c >= '0' && c <= '9')
                        {
                            n = n * 10 + (c - '0');
                            index++;
                            c = get(format, index);
                            if (n >= 10) break;
                        }
                    }
                    if (c == 0)
                    {
                        digits[0] = n;
                        return get(format, 0);
                    }
                }
                digits[0] = -1;
                return '\u0000';
            }
        }
        digits[0] = -1;
        return 'G';
    }

    private static char get(String str, int i)
    {
        return i < str.length() ? str.charAt(i) : '\u0000';
    }

    //>>>>>>>>>>>>>>>>>>>> main idea adapted from java.lang Integer

    // I use the "invariant division by multiplication" trick to
    // accelerate Integer.toString.  In particular we want to
    // avoid division by 10.
    //
    // The "trick" has roughly the same performance characteristics
    // as the "classic" Integer.toString code on a non-JIT VM.
    // The trick avoids .rem and .div calls but has a longer code
    // path and is thus dominated by dispatch overhead.  In the
    // JIT case the dispatch overhead doesn't exist and the
    // "trick" is considerably faster than the classic code.
    //
    // TODO-FIXME: convert (x * 52429) into the equiv shift-add
    // sequence.
    //
    // RE:  Division by Invariant Integers using Multiplication
    //      T Gralund, P Montgomery
    //      ACM PLDI 1994
    //

    private static String int32ToDecString(int value, int digits)
    {
        char sign = 0;

        if (value == Integer.MIN_VALUE)
            return intDecMinValueString(digits);

        if (value < 0)
        {
            sign = '-';
            value = -value;
        }

        int size = stringSize(value);
        if (digits < 1)//== -1 -> format letter without digits, i.e. 'D', 'G'.
            digits = size;
        // .NET: Do not strip significant digits if 'digits' is less than digits in the value.
        if (size < digits)
            size = digits;
        else
            digits = size;
        if (sign != 0)
            size++;

        char[] buf = new char[size];
        getChars(value, size, buf, digits);
        if (sign != 0)
            buf[0] = sign;

        return new String(buf, 0, size);
    }

    // Requires positive x
    static int stringSize(int x)
    {
        for (int i = 0; ; i++)
        {
            if (x <= sizeTable[i])
                return i + 1;
        }
    }

    /**
     * Returns string for Integer.MIN value.
     */
    private static String intDecMinValueString(int digits)
    {
        if (digits > 0)
        {
            int pad = digits - maxIntDigits;
            if (pad > 0)
                return "-" + msString.newString('0', pad) + minIntValue;
        }

        return minInt;
    }

    /**
     * Places characters representing the integer i into the
     * character array buf. The characters are placed into
     * the buffer backwards starting with the least significant
     * digit at the specified index (exclusive), and working
     * backwards from there.
     *
     * Will fail if i == Integer.MIN_VALUE
     *
     * Places as many digits as specified in 'digits' parameter:
     * Strips most significant digits if 'digits' are less than real digits
     * of pads with zeroes if digits are more than real value's digits.
     *
     * Places exactly all value's digits if 'digits' == -1;
     */
    static void getChars(int value, int index, char[] buf, int digits)
    {
        int q, r;

        // Generate two digits per iteration
        while (value >= 65536)
        {
            q = value / 100;
            // really: r = value - (q * 100);
            r = value - ((q << 6) + (q << 5) + (q << 2));
            value = q;
            buf[--index] = DigitOnes[r];
            if (--digits <= 0)
                return;
            buf[--index] = DigitTens[r];
            if (--digits <= 0)
                return;
        }

        // Fall thru to fast mode for smaller numbers
        // assert(value <= 65536, value);
        for (; ; )
        {
            q = (value * 52429) >>> (16 + 3);
            r = value - ((q << 3) + (q << 1));  // r = value-(q*10) ...
            buf[--index] = DigitsLowerCase[r];

            if (--digits <= 0)
                return;

            value = q;
            if (value == 0)
                break;
        }

        while (digits-- > 0)
            buf[--index] = '0';
    }

    private static String int32ToHexString(int value, int digits, int maxHexDigits, boolean isUpper)
    {
        int size = (digits != -1 && digits > maxHexDigits) ? digits : maxHexDigits;
        char[] buf = new char[size];

        int start = getHexChars(value, size, buf, digits, isUpper);

        return new String(buf, start, size - start);
    }

    private static int getHexChars(int value, int index, char[] buf, int digits, boolean isUpper)
    {
        char[] digitChars = isUpper ? DigitsUpperCase : DigitsLowerCase;
        for (; ; )
        {
            buf[--index] = digitChars[value & 0xf];
            --digits;

            value >>>= 4;
            if (value == 0)
                break;
        }

        while (digits-- > 0)
            buf[--index] = '0';

        return index;
    }

    public static String int64ToDecString(long value, int digits)
    {
        char sign = 0;

        if (value == Long.MIN_VALUE)
            return longDecMinValueString(digits);

        if (value < 0)
        {
            sign = '-';
            value = -value;
        }

        int size = stringSize(value);
        if (digits < 1)//== -1 -> format letter without digits, i.e. 'D', 'G'.
            digits = size;
        // .NET: Do not strip significant digits if 'digits' is less than digits in the value.
        if (size < digits)
            size = digits;
        else
            digits = size;
        if (sign != 0)
            size++;

        char[] buf = new char[size];
        getChars(value, size, buf, digits);
        if (sign != 0)
            buf[0] = sign;

        return new String(buf, 0, size);
    }

    // Requires positive x
    static int stringSize(long x)
    {
        for (int i = 0; ; i++)
        {
            if (x <= longSizeTable[i])
                return i + 1;
        }
    }

    /**
     * Returns string for Long.MIN value.
     */
    private static String longDecMinValueString(int digits)
    {
        if (digits > 0)
        {
            int pad = digits - maxLongDigits;
            if (pad > 0)
                return "-" + msString.newString('0', pad) + minLongValue;
        }

        return minLong;
    }

    /**
     * Places characters representing the long value into the
     * character array buf. The characters are placed into
     * the buffer backwards starting with the least significant
     * digit at the specified index (exclusive), and working
     * backwards from there.
     *
     * Will fail if value == Long.MIN_VALUE
     *
     * Places as many digits as specified in 'digits' parameter:
     * Strips most significant digits if 'digits' are less than real digits
     * of pads with zeroes if digits are more than real value's digits.
     *
     * Places exactly all value's digits if 'digits' == -1;
     */
    static void getChars(long value, int index, char[] buf, int digits)
    {
        // calling method int64ToDecString(long value, int digits) has hardcoded
        // case for value == Long.MIN_VALUE.
        assert value != Long.MIN_VALUE : "calling method should filter out value == Long.MIN_VALUE";

        long q;
        int r;

        // Get 2 digits/iteration using longs until quotient fits into an int
        while (value > Integer.MAX_VALUE)
        {
            q = value / 100;
            // really: r = i - (q * 100);
            r = (int) (value - ((q << 6) + (q << 5) + (q << 2)));
            value = q;
            buf[--index] = DigitOnes[r];
            if (--digits <= 0)
                return;
            buf[--index] = DigitTens[r];
            if (--digits <= 0)
                return;
        }

        // Get 2 digits/iteration using ints
        int q2;
        int i2 = (int) value;
        while (i2 >= 65536)
        {
            q2 = i2 / 100;
            // really: r = i2 - (q * 100);
            r = i2 - ((q2 << 6) + (q2 << 5) + (q2 << 2));
            i2 = q2;
            buf[--index] = DigitOnes[r];
            if (--digits <= 0)
                return;
            buf[--index] = DigitTens[r];
            if (--digits <= 0)
                return;
        }

        // Fall thru to fast mode for smaller numbers
        // assert(i2 <= 65536, i2);
        for (; ; )
        {
            q2 = (i2 * 52429) >>> (16 + 3);
            r = i2 - ((q2 << 3) + (q2 << 1));  // r = i2-(q2*10) ...
            buf[--index] = DigitsLowerCase[r];

            if (--digits <= 0)
                return;

            i2 = q2;
            if (i2 == 0)
                break;
        }

        while (digits-- > 0)
            buf[--index] = '0';
    }

    private static String int64ToHexString(long value, int digits, boolean isUpper)
    {
        int size = ((digits != -1) && (digits > maxLongHexDigits)) ? digits : maxLongHexDigits;
        char[] buf = new char[size];

        int start = getHexChars(value, size, buf, digits, isUpper);

        return new String(buf, start, size - start);
    }

    private static int getHexChars(long value, int index, char[] buf, int digits, boolean isUpper)
    {
        char[] digitChars = isUpper ? DigitsUpperCase : DigitsLowerCase;
        for (; ; )
        {
            buf[--index] = digitChars[((int) (value & 0xf))];
            --digits;

            value >>>= 4;
            if (value == 0)
                break;
        }

        while (digits-- > 0)
            buf[--index] = '0';

        return index;
    }

    /**
     * Extension methods to emulate .Net Double.Equals(object obj) that can be called both from
     * double value-type and Double reference-type.
     */
    public static boolean equals(double thisDouble, Object obj)
    {
        if (!(obj instanceof Double))
            return false;

        double doubleValue = ((Double) obj).doubleValue();
        if (Double.isNaN(doubleValue))
            return Double.isNaN(thisDouble);

        return doubleValue == thisDouble;
    }

    public static boolean equals(Double thisDouble, Object obj)
    {
        if (!(obj instanceof Double))
            return false;

        Double d = (Double) obj;
        if (d.equals(thisDouble))
            return true;
        if (d.isNaN())
            return thisDouble.isNaN();
        else
            return false;
    }

    public static long getLongValue(Object value)
    {
        long timestamp = 0L;
        if (value instanceof Integer)
            timestamp = ((Integer) value).longValue();

        else if (value instanceof Byte)
            timestamp = ((Byte) value).longValue();

        else if (value instanceof Short)
            timestamp = ((Short) value).longValue();

        else if (value instanceof Long)
            timestamp = ((Long) value).longValue();

        else if (value instanceof Float)
            timestamp = ((Float) value).longValue();

        else if (value instanceof Double)
            timestamp = ((Double) value).longValue();

        return timestamp;
    }

    /**
     * Static Long.hashCode() is added to 1.8 only. Let's add it to 1.6 and 1.7 too.
     */
    public static int longHashCode(long value)
    {
        return (int) (value ^ (value >>> 32));
    }

    /**
     * Static Double.hashCode() is added to 1.8 only. Let's add it to 1.6 and 1.7 too.
     */
    public static int doubleHashCode(double value)
    {
        long bits = Double.doubleToLongBits(value);
        return (int) (bits ^ (bits >>> 32));
    }

    /**
     * .Net-style implementation of Boolean.hashCode(): returns 1 for true or 0 for false.
     * Java's Boolean.hashCode() returns 1231 or 1237 respectively (and is added in 1.8 only).
     */
    public static int booleanHashCode(boolean value)
    {
        return value ? 1 : 0;
    }

    /**
     * The "," character serves as both a group separator and a number scaling specifier.
     * Group separator: If one or more commas are specified between two digit placeholders (0 or #) that format the integral digits of a number
     * <p>
     * Number scaling specifier: If one or more commas are specified immediately to the left of the explicit or implicit decimal point,
     * the number to be formatted is divided by 1000 for each comma.
     * <p>
     * https://msdn.microsoft.com/en-us/library/0c899ak8(v=vs.110).aspx#The
     * <p>
     * This method removes trailing commas in case of 'Number scaling specifier'.
     *
     * @param format
     * @param value
     * @return normalized pattern
     */
    private static NumberParserResult normalizeScalingSpecifier(String format, float value)
    {
        if (format == null)
            return null;

        int scalingFactorCounter = 0;
        StringBuilder out = new StringBuilder();
        int i = 0;
        while (i < format.length())
        {
            char curChar = format.charAt(i);
            if (curChar == ',')
            {
                scalingFactorCounter = 1; // first comma
                int nextIdx = i + 1;

                // 1. check remaining chars
                int j;
                for (j = nextIdx; j < format.length(); j++)
                {
                    char nextChar = format.charAt(j);
                    if (nextChar == ',') // calculate how many commas there are
                        scalingFactorCounter++;

                    else if (nextChar == '}') // scaling specifier ends
                    {
                        out.append(nextChar);
                        break; // breaks inner loop
                    }
                    else
                    {
                        // 3. there is no scaling specifier set, so write all the commas and the char next to the last one.
                        for (int c = scalingFactorCounter; c > 0; c--)
                            out.append(",");

                        out.append(nextChar);
                        scalingFactorCounter = 0; // skip the counter. It is a group separator
                        break;
                    }
                }
                i = j; // store last checked char
            }
            else
                out.append(curChar);

            i++; // go to next char
        }

        if (scalingFactorCounter > 0)
        {
            for (i = scalingFactorCounter; i > 0; i--)
                value /= 1000;

            value = Math.round(value);

            return new NumberParserResult(out.toString(), value);
        }

        return null;
    }

    private static class NumberParserResult
    {
        private String mFormat;
        private float mValue;

        public NumberParserResult(String format, float value)
        {
            this.mFormat = format;
            this.mValue = value;
        }

        public float getValue() { return mValue;}

        public String getFormat() { return mFormat; }
    }

    final static int[] sizeTable = {9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE};
    final static long[] longSizeTable = {9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, 9999999999L,
            99999999999L, 999999999999L, 9999999999999L, 99999999999999L, 999999999999999L,
            9999999999999999L, 99999999999999999L, 999999999999999999L, Long.MAX_VALUE};

    static final String minInt = "-2147483648";
    static final String minIntValue = "2147483648";
    static final int maxIntDigits = 10;
    static final int maxIntHexDigits = 8;
    static final int maxShortHexDigits = 4;
    static final int maxByteHexDigits = 2;

    static final String minLong = "-9223372036854775808";
    static final String minLongValue = "9223372036854775808";
    static final int maxLongDigits = 19;
    static final int maxLongHexDigits = 16;

    private static final String gSingleQuote = "'";
    private static final char gBackslash = '\\';
    private static final char gUnicodeDefChar = 'u';
    private static final char gDoubleQuote = '\"';

    /**
     * All possible chars for representing a number as a String
     */
    final static char[] DigitsLowerCase = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'
    };

    final static char[] DigitsUpperCase = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B',
            'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    final static char[] DigitTens = {
            '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
            '1', '1', '1', '1', '1', '1', '1', '1', '1', '1',
            '2', '2', '2', '2', '2', '2', '2', '2', '2', '2',
            '3', '3', '3', '3', '3', '3', '3', '3', '3', '3',
            '4', '4', '4', '4', '4', '4', '4', '4', '4', '4',
            '5', '5', '5', '5', '5', '5', '5', '5', '5', '5',
            '6', '6', '6', '6', '6', '6', '6', '6', '6', '6',
            '7', '7', '7', '7', '7', '7', '7', '7', '7', '7',
            '8', '8', '8', '8', '8', '8', '8', '8', '8', '8',
            '9', '9', '9', '9', '9', '9', '9', '9', '9', '9',
    };

    final static char[] DigitOnes = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
    };
}

