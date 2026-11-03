// 24/03/11 by Roman Korchagin
package com.aspose;


import com.aspose.ms.System.msChar;
import com.aspose.ms.System.msMath;
import com.aspose.ms.System.msNumber;
import com.aspose.ms.System.msString;
import com.aspose.ms.java.text.NumberFormatProvider;

import javax.swing.text.MaskFormatter;
import java.text.*;
import java.util.Locale;
import java.math.BigDecimal;

// Most of the FormatterPal methods cannot be implemented as static because they use Java's DecimalFormat
// and SimpleDateTimeFormat that are not thread safe. It is also not a good idea to make FormatterPal
// instances as it will be ugly to create a new instance whenever formatting is required.
//
// As a solution, an instance of this class is stored in thread local storage and allows
// FormatterPal methods to remain static and at the same time ensures DecimalFormat etc objects are
// accessed by one thread only.
//
public class PalFormatterCore
{
    PalFormatterCore()
    {
        // Doubles
        mDoubleToStrFixed = NumberFormatProvider.newInstance(Locale.US);
        // In .NET the precision is 15 digits and after E-5 it turns into scientific notation so
        // this mimics .NET's formatting for numbers that are displayed in the fixed-point format.
        // There are 18 hashes because all numbers higher than E-5 should be possible to displayed with full precision.
        // Numbers smaller than 0.0001 are formatted using another (scientific) format string.
        // But numbers such as 0.00012345678901234 need to be displayed fully and can take up to 18 positions.
        mDoubleToStrFixed.applyPattern("0.##################");

        // This mimics .NET's formatting for numbers that are displayed in the fixed-point format with thousands separator.
        mDoubleToStrFixedThousands = NumberFormatProvider.newInstance(Locale.US);
        mDoubleToStrFixedThousands.applyPattern("#,#00.##################");

        // This seems to mimic .NET's formatting for small numbers.
        mDoubleToStrScientific = NumberFormatProvider.newInstance(Locale.US);
        mDoubleToStrScientific.applyPattern("0.##############E00");

        mDoubleToStrExponential = NumberFormatProvider.newInstance(Locale.US);
        mDoubleToStrExponential.applyPattern("0.#E0");

        mDoubleToStrNDecimals = new DecimalFormat[10];
        String pattern = "0";
        for (int i = 0; i < 10; i++)
        {
            mDoubleToStrNDecimals[i] = NumberFormatProvider.newInstance(Locale.US);
            mDoubleToStrNDecimals[i].applyPattern(pattern);
            if (i == 0)
                pattern = pattern + ".#";
            else
                pattern = pattern + "#";
        }

        mDoubleToStr2Decimals = NumberFormatProvider.newInstance(Locale.US);
        mDoubleToStr2Decimals.applyPattern("0.##");

        mDoubleToStr9Decimals = NumberFormatProvider.newInstance(Locale.US);
        mDoubleToStr9Decimals.applyPattern("0.#########");

	    mDoubleToStr2HashesEZero = NumberFormatProvider.newInstance(Locale.US);
	    mDoubleToStr2HashesEZero.applyPattern("0E0");
		mDoubleToStr2HashesEZero.setMinimumIntegerDigits(2);

	    mDoubleToStr11HashesEZero = NumberFormatProvider.newInstance(Locale.US);
	    mDoubleToStr11HashesEZero.applyPattern("0E0");
		mDoubleToStr11HashesEZero.setMinimumIntegerDigits(11);


        // Floats
        mFloatToStr8Decimals = NumberFormatProvider.newInstance(Locale.US);
        mFloatToStr8Decimals.applyPattern("0.########");

        mFloatToStr9Decimals = NumberFormatProvider.newInstance(Locale.US);
        mFloatToStr9Decimals.applyPattern("0.#########");


        // Integers
        mIntToStrD2Format = NumberFormatProvider.newInstance(Locale.US);
        mIntToStrD2Format.applyPattern("00");
        mIntToStrD3Format = NumberFormatProvider.newInstance(Locale.US);
        mIntToStrD3Format.applyPattern("000");
        mIntToStrD4Format = NumberFormatProvider.newInstance(Locale.US);
        mIntToStrD4Format.applyPattern("0000");
        mIntToStrD5Format = NumberFormatProvider.newInstance(Locale.US);
        mIntToStrD5Format.applyPattern("00000");
        mIntToStrD10Format = NumberFormatProvider.newInstance(Locale.US);
        mIntToStrD10Format.applyPattern("0000000000");
    }

    double tryParseDoubleInvariant(String s, boolean allowThousands)
    {
        return allowThousands ?
                tryParseDouble(s, mDoubleToStrFixedThousands) :
                tryParseDouble(s, mDoubleToStrFixed);
    }

    double tryParseDoubleFloatPrecision(String s)
    {
        return tryParseDouble(s, mFloatToStr8Decimals);
    }

    double tryParseDoubleCurrent(String s)
    {
        return tryParseDoubleCurrent(s, false);
    }

    double tryParseDoubleCurrent(String s, boolean allowThousands)
    {
        DecimalFormat format = NumberFormatProvider.newFixedInstance();
        if (allowThousands)
            format.setGroupingSize(3);
        // todo 0 rk should we apply same patterns as for the invariant?
        return tryParseDouble(s, format);
    }

    /// <summary>
    /// Tries to parse a double with the specified number format.
    /// Applies several Java-specific workarounds:
    /// 1. Trims white spaces.
    /// 2. Removes leading '+'
    /// 3. Replaces the exponent char to the one that Java can understand.
    /// 4. Ensures the string is parsed completely. Because JAVA in contrast to .Net allows symbol suffixes.
    ///    E.g. .Net parses "20xx" as 0, but java as 20.
    /// </summary>
    static double tryParseDouble(String s, DecimalFormat numberFormat)
    {
        if (!StringUtil.hasChars(s))
            return Double.NaN;

        // Performance: use char array instead of string.trim(), substring() and regexes.
        char[] buf = s.toCharArray();

        // Mark whitespaces.
        int start = 0;
        int end = buf.length - 1;
        while ((start < end) && msChar.isWhiteSpace(buf[start]))
            start++;
        while ((start < end) && msChar.isWhiteSpace(buf[end]))
            end--;

        // Mark leading ePlus sign.
        if (buf[start] == '+')
            start++;

        if (start > end)
            return Double.NaN;

        // Replace .Net exponent "e" to Java "E", mark "e+" if any.
        int ePlus = 0;
        boolean canBeParsedByBigDecimal = true;
        for (int i = start; i < end; i++)
        {
            // Performance: detect characters which are definitely disallowed in a floating-point number and therefore
            // will fail BigDecimal constructor. Such heuristic is very effective because we do it in the same loop
            // that iterates over the entire array anyway. And our list of allowed characters is rather small.
            //
            // The heuristic prevents excessive parsing and exception processing in case of malformed input.
            if (canBeParsedByBigDecimal && !isAllowedInBigDecimalNotation(buf[i]))
                canBeParsedByBigDecimal = false;

            if (buf[i] == 'e')
            {
                buf[i] = 'E';
                if (++i < end && buf[i] == '+')
                    ePlus = i;

                // We need to appeal to white list one more time since iteration variable has been incremented.
                if (canBeParsedByBigDecimal && !isAllowedInBigDecimalNotation(buf[i]))
                    canBeParsedByBigDecimal = false;
            }
        }

        try
        {
            int len = end - start + 1;
            if (ePlus > 0)
            {
                // Trim whitespaces and remove plus sign from exponent.
                len--;
                char[] copy = new char[len];
                System.arraycopy(buf, start, copy, 0, ePlus - start);
                System.arraycopy(buf, ePlus + 1, copy, ePlus - start, end - ePlus);
                s = new String(copy);
            }
            else
            {
                // Just trim whitespaces.
                s = new String(buf, start, len);
            }

            ParsePosition pos = new ParsePosition(0);
            Number number = numberFormat.parse(s, pos);

            // JAVA: Java NumberFormat can silently parse "1 2/3" or even "1/02/2005" to "1".
            if (pos.getErrorIndex() != -1 || pos.getIndex() != len)
            {
                // WORDSJAVA-2020. Performance improvements
                if (!canBeParsedByBigDecimal)
                    return Double.NaN;

                // WORDSJAVA-1478: DecimalFormat can't parse numbers with exponents. For that purpose it's better to use BigDecimal
                try
                {
                    BigDecimal bd = new BigDecimal(s);
                    return bd.doubleValue();
                }
                catch ( Exception e )
                {
                    return Double.NaN;
                }
            }

            //.Net returns 0.0, instead of -0.0
            double doubleValue = number.doubleValue();
            return (doubleValue == -0.0) ? 0.0 : doubleValue;
        }
        catch (Exception e)
        {
            return Double.NaN;
        }
    }

    // This a list of characters which are allowed in textual
    // notation of double number that can be consumed by
    // java.math.BigDecimal.BigDecimal(java.lang.String)
    // constructor.
    private static boolean isAllowedInBigDecimalNotation(char c)
    {
        return c == '+' ||
                c == '-' ||
                c == '.' ||
                c == 'e' ||
                c == 'E' ||
                Character.isDigit(c);
    }

    double tryParseCurrencyCurrent(String s)
    {
        // JAVA is very strict about the pattern and it will not recognize a normal double value using currency pattern,
        // but .NET will recognize that as a number. So in Java we have to try parsing as a normal double too.
        // At first try to parse this as a normal double value, if that fails, try parsing as a currency value (not vice versa, see WORDSJAVA-2665).

	    double result = tryParseDoubleCurrent(s);

	    if (Double.isNaN(result))
	        result = tryParseDouble(s, getCurrencyFormat());

	    //last try
	    if (Double.isNaN(result))
	    {
            String currencySymbol = FormatterPal.getCurrencySymbolCurrent();
            // Performance change to do not use regexp.
            int indexOfCurrency = s.indexOf(currencySymbol);
            if (indexOfCurrency == 0)
                s = s.substring(currencySymbol.length());
            else if (indexOfCurrency > 0)
                s = msString.remove(s, indexOfCurrency, currencySymbol.length());

            result = tryParseDoubleCurrent(s);
        }

	    return result;
    }

    String intToStrD2(long val)
    {
        return mIntToStrD2Format.format(val);
    }

    String intToStrD3(long val)
    {
        return mIntToStrD3Format.format(val);
    }

    String intToStrD4(long val)
    {
        return mIntToStrD4Format.format(val);
    }

    String intToStrD5(long val)
    {
        return mIntToStrD5Format.format(val);
    }

    String intToStrD10(long val)
    {
        return mIntToStrD10Format.format(val);
    }

    String longToStrX(long val, int digitCount, boolean isUpper)
    {
        return toStrX(val, digitCount, isUpper, mLongToStrXBuffer, gMaxLongHexDigits);
    }

    String intToStrX(long val, int digitCount, boolean isUpper)
    {
        return toStrX(val, digitCount, isUpper,mIntToStrXBuffer, gMaxHexDigits);
    }

    // If you pass 0 or negative value into digitCount, then it will return the formatted value without leading zeroes.
    private String toStrX(long val, int digitCount, boolean isUpper, char[] buffer, int maxHexDigits )
    {
        final char[] hexChars = (isUpper) ? gHexCharsUpper : gHexCharsLower;

        int mostSignificantIndex = maxHexDigits - 1;   // This leaves at least the last "0".
        for (int i = maxHexDigits - 1; i >= 0; i--)
        {
            int nibble = (int) (val & 0x0F);
            if (nibble != 0)
                mostSignificantIndex = i;

            buffer[i] = hexChars[nibble];
            val = val >> 4;
        }

        if (digitCount <= 0)
        {
            // When removing leading zeroes, just grab all characters starting from the first non zero.
            return new String(buffer, mostSignificantIndex, buffer.length - mostSignificantIndex);
        }
        else
        {
            // When formatting to a specified length make sure we don't cut significant digits.
            int start = Math.min(maxHexDigits - digitCount, mostSignificantIndex);
            return new String(buffer, start, buffer.length - start);
        }
    }

    String doubleToStr(double value)
    {
        if (Double.isNaN(value))
            return gNAN;

        // This is to mimic .NET behavior that changes into scientific notation for small numbers.
        // .NET also changes into scientific notation for large numbers, but it is harder to detect so I don't do it in Java.
        double absValue = Math.abs(value);
        boolean isUseScientific = (absValue > 0.0) && (absValue < 0.0001);
        if (isUseScientific)
            return mDoubleToStrScientific.format(value);
        else
            return mDoubleToStrFixed.format(roundTo15SignificantDigits(value));
    }

    String doubleToStr11HashesEZero(double val)
    {
        if (Double.isNaN(val))
            return gNAN;

	    String result = mDoubleToStr11HashesEZero.format(val);
	    //JAVA HACK: the better way is using formatSymbols.setExponentSeparator("e"),
	    //but this works only since 1.6 (non-public in 1.5) so we have to change E to e manually.
	    return msString.replace(result, 'E', 'e');
    }

    String doubleToExponential(double val)
    {
        if (Double.isNaN(val))
            return gNAN;

        return mDoubleToStrExponential.format(val);
    }

    String doubleToStrNDecimals(double val, int n)
    {
        assert n >= 0 && n <= 9;

        if (Double.isNaN(val))
            return gNAN;

        return mDoubleToStrNDecimals[n].format(val);
    }

    String doubleToStr2Decimals(double val)
    {
        if (Double.isNaN(val))
            return gNAN;

        // Emulate .NET rounding before formatting.
        double doubleV = roundTo15SignificantDigits(roundHalfAwayFromZero(val, 2));
        return mDoubleToStr2Decimals.format(doubleV);
    }

    String doubleToStr9Decimals(double val)
    {
        if (Double.isNaN(val))
            return gNAN;

        // Emulate .NET rounding before formatting.
        double doubleV = roundTo15SignificantDigits(roundHalfAwayFromZero(val, 9));
        return mDoubleToStr9Decimals.format(doubleV);
    }

    String floatToStr8Decimals(float val)
    {
        if (Float.isNaN(val))
            return gNAN;

        // Emulate .NET rounding before formatting.
        double doubleV = roundTo15SignificantDigits(roundHalfAwayFromZero(val, 8));
        return mFloatToStr8Decimals.format(doubleV);
    }

    String floatToStr9Decimals(float val)
    {
        if (Float.isNaN(val))
            return gNAN;

        // Emulate .NET rounding before formatting.
        double doubleV = roundTo15SignificantDigits(roundHalfAwayFromZero(val, 9));
        return mFloatToStr9Decimals.format(doubleV);
    }

    String floatToStrNDecimals(float val, int n)
    {
        assert n >= 0 && n <= 9;

        if (Float.isNaN(val))
            return gNAN;

        double doubleV = roundTo15SignificantDigits(roundHalfAwayFromZero(msMath.round(val, n), n));
        String format1 = mDoubleToStrNDecimals[n].format(doubleV);
        String format2 = Float.toString(val);
        return (format1.length() <= format2.length()) ? format1: format2;
    }

	static String maskFormat(double value, String format)
	{
		try
		{
			//.Net also uses '0' as digit (or zero) placeholder - let's print nothing instead of .net zero.
			format = msString.replace(format, '0', '#');
			MaskFormatter formatter = new MaskFormatter(format);
			formatter.setValueContainsLiteralCharacters(false);
			return formatter.valueToString((long)value);
		}
		catch (ParseException e)
		{
			throw new RuntimeException("Can't format value '" + value + "' with format '" + format + "'.", e);
		}
	}

	static String decimalFormat(double value, String format, boolean percentFormat, boolean negativeParentheses)
	{
        DecimalFormat decimalFormat = NumberFormatProvider.newFixedInstance();

        // Fix zero pattern if needed.
        format = msNumber.normalizeZeroPattern(format, value);

        try
		{
			decimalFormat.applyPattern(format);
			//J599, N7517, N6921 - pattern like "0,0.00":
			//in .Net defines just a group separator (',') for standard group size (3)
			//but in java defines also group size 1. Let's do as .Net does.
			if (decimalFormat.getGroupingSize() == 1)
				decimalFormat.setGroupingSize(3);
		}
		catch (Exception e)
		{
			return e.getMessage();
		}

		int fractionDigits = (percentFormat) ? 2 : 0;
		fractionDigits += decimalFormat.getMaximumFractionDigits();

		// It looks like we need to round away from zero and leave only 15 significant digits.
        value = roundHalfAwayFromZero(value, fractionDigits);
        value = roundTo15SignificantDigits(value);

        if (negativeParentheses && value < 0)
        {
            decimalFormat.setNegativePrefix("(");
            decimalFormat.setNegativeSuffix(")");
        }

        return decimalFormat.format(value);
	}

    // This is rounding away from zero like in .NET.
    private static double roundHalfAwayFromZero(double value, int digits)
    {
        if(value == 0.0 || value == -0.0)
            return 0.0;

        // Else "rounds" NaN to 0.0.
        if (Double.isNaN(value))
            return value;

        double factor = Math.pow(10, digits);   // RK can convert this into a table.
        return roundHalfAwayFromZero(value * factor) / factor;
    }

    private static double roundHalfAwayFromZero(double value)
    {
        return Math.signum(value) * Math.floor(Math.abs(value) + 0.5d);
    }

    /**
     * FIX WORDSJAVA-809: 4.9999999999999976E8 .Net formats as 500000000 but java as 499999999.99999976.
     * Round double value to 15 significant digits provides behavior equal to .NET
     */
    private static double roundTo15SignificantDigits(double value)
    {
        return roundToSignificantDigits(value, 15);
    }

    /**
     * Round double value to given number of significant digits.
     */
    private static double roundToSignificantDigits(double value, int n)
    {
        // We have to round up from zero because it looks like what .NET does when formatting the numbers.
        if(value == 0.0 || value == -0.0)
            return 0.0;

        // Else "rounds" NaN to 0.0.
        if (Double.isNaN(value))
            return value;

        final double d = Math.ceil(Math.log10(value < 0 ? -value : value));
        final int power = n - (int) d;

        final double magnitude = Math.pow(10, power);
        final long shifted = Math.round(value * magnitude);
        return shifted/magnitude;
    }

    /**
     * Returns DecimalFormat with currency style and necessary bugfixes
     */
    static DecimalFormat getCurrencyFormat()
    {
        return NumberFormatProvider.newFixedCurrencyInstance();
    }

    private DecimalFormat mDoubleToStrFixed;
    private DecimalFormat mDoubleToStrFixedThousands;
    private DecimalFormat mDoubleToStrScientific;
    private DecimalFormat mDoubleToStrExponential;
    private DecimalFormat[] mDoubleToStrNDecimals;
    private DecimalFormat mDoubleToStr2Decimals;
    private DecimalFormat mDoubleToStr9Decimals;
	private DecimalFormat mDoubleToStr2HashesEZero;
	private DecimalFormat mDoubleToStr11HashesEZero;

    private DecimalFormat mFloatToStr8Decimals;
    private DecimalFormat mFloatToStr9Decimals;

    private DecimalFormat mIntToStrD2Format;
    private DecimalFormat mIntToStrD3Format;
    private DecimalFormat mIntToStrD4Format;
    private DecimalFormat mIntToStrD5Format;
    private DecimalFormat mIntToStrD10Format;

    private static final int gMaxHexDigits = 8;
    private static final int gMaxLongHexDigits = 16;
    private char[] mIntToStrXBuffer = new char[gMaxHexDigits];
    private char[] mLongToStrXBuffer = new char[gMaxLongHexDigits];

    // todo 0 rk this is a duplication of MiscUtil.HexChars. Can we remove duplication.
    private static final char[] gHexCharsUpper = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    private static final char[] gHexCharsLower = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    private static final String gNAN = "NaN";
}
