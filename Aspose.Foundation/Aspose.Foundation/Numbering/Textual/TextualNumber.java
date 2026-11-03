// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/03/2012 by Andrey Soldatov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.Text.msStringBuilder;
import java.util.ArrayList;
import com.aspose.ms.System.Globalization.msCultureInfo;


/// <summary>
/// This class is a helper for <see cref="NumberConverterCore"/>,
/// its <see cref="ToFullString(long,bool,bool,Language)"/> method converts a number into a textual (cardinal or ordinal) representation.
/// </summary>
public class TextualNumber
{
    /// <summary>
    /// No Ctor.
    /// </summary>
    private TextualNumber(EuropeanTextualNumberProvider numberProvider)
    {
        mNumberProvider = numberProvider;
    }

    /// <summary>
    /// <para>Returns a cardinal (one, two, three, ...) or ordinal (first, second, third, ...) number.</para>
    /// <para>Makes the first letter lower or uppercase as specified.</para>
    /// </summary>
    /// <remarks>
    /// <para>Called as utility method from <see cref="NumberConverterCore"/>. Try to not use it from inside the project.</para>
    /// <para>When <paramref name="language"/> is <see cref="Language.LanguageNotSet"/>, it uses culture of current thread.</para>
    /// </remarks>
    static String toFullString(long value, boolean isCardinal, boolean isFirstLetterUppercase, /*Language*/int language)
    {
        Debug.assertCallingClass(NumberConverterCore.class);

        StringBuilder builder = getFullStringBuilder(value, isCardinal, isFirstLetterUppercase, language);

        return builder.toString();
    }

    /// <summary>
    /// Returns textual representation of a number as in the example:
    /// "one hundred twenty-two and 16/100" if given value is 123.1616,
    /// using <see cref="CultureInfo"/> of the current thread.
    /// </summary>
    public static String toDollarText(double value)
    {
        return toDollarText(value, 0);
    }

    /// <summary>
    /// Returns textual representation of a number as in the example:
    /// "one hundred twenty-two and 16/100" if given value is 123.1616.
    /// </summary>
    /// <remarks>
    /// <para>When <paramref name="language"/> is <see cref="Language.LanguageNotSet"/>, it uses culture of current thread.</para>
    /// </remarks>
    public static String toDollarText(double value, /*Language*/int language)
    {
        long dollars = (long)value;
        StringBuilder builder = getFullStringBuilder(dollars, true, false, language);

        msStringBuilder.append(builder
            .append(' '), getTextualNumberProvider(language).getAnd())
            .append(' ');

        int cents = MathUtil.doubleToInt((value - dollars) * 100.0);
        msStringBuilder.append(builder, FormatterPal.intToStrD2(cents));
        msStringBuilder.append(builder, "/100");

        return builder.toString();
    }

    private static StringBuilder getFullStringBuilder(long value, boolean isCardinal, boolean isFirstLetterUppercase, /*Language*/int language)
    {
        TextualNumber textualNumber = new TextualNumber(getTextualNumberProvider(language));

        return getFullStringBuilder(value, isCardinal, isFirstLetterUppercase, textualNumber);
    }

    private static StringBuilder getFullStringBuilder(long value, boolean isCardinal, boolean isFirstLetterUppercase, TextualNumber textualNumber)
    {
        StringBuilder builder = (value == 0)
            ? new StringBuilder(textualNumber.mNumberProvider.zero(isCardinal))
            : textualNumber.toFullStringCore(value, isCardinal);

        builder.insert(0, textualNumber.mNumberProvider.getNumberPrefix(value, isCardinal));
        msStringBuilder.append(builder, textualNumber.mNumberProvider.getNumberSuffix(value, isCardinal));

        if ((builder.length() > 0) && (textualNumber.mNumberProvider.isFirstLetterAlwaysUppercase() || isFirstLetterUppercase))
            builder.setCharAt(0, Character.toUpperCase(builder.charAt(0)));

        return builder;
    }

    private StringBuilder toFullStringCore(long value, boolean isCardinal)
    {
        Debug.assertTrue(value > 0);

        StringBuilder builder = new StringBuilder();

        ArrayList<Integer> thousands = GroupSplitter.splitToGroups(value, 3);

        for (int i = thousands.size() - 1; i > 0; i--)
        {
            int thousand = thousands.get(i);

            if (thousand == 0)
                continue;

            if ((thousand != 1) || mNumberProvider.getIncludeOneInBigNumbers())
            {
                if (mNumberProvider.includeThousandsWord(value))
                {
                    boolean hasHigherDigit = (i < thousands.size() - 1);
                    appendThousand(builder, thousand, true, hasHigherDigit, true, isCardinal, thousands, i);
                }
            }

            mNumberProvider.appendWord(
                builder,
                mNumberProvider.getBigNumeral(i, isCardinal, thousand, thousands),
                mNumberProvider.useThousandSeparator(isCardinal, thousand, thousands, i));
        }

        if (thousands.get(0) == 0)
        {
            if (!isCardinal)
                mNumberProvider.appendThousandsOrdinalSuffix(builder);
        }
        else
        {
            appendThousand(builder, thousands.get(0), isCardinal, thousands.size() > 1, false, isCardinal, thousands, 0);
        }

        return builder;
    }

    /// <summary>
    /// Appends textual representation of <paramref name="value"/> (from 0 to 999 inclusively)
    /// to <paramref name="builder"/>.
    /// </summary>
    /// <param name="builder">StringBuilder to place a string.</param>
    /// <param name="value">Source value from 0 to 999 inclusively.</param>
    /// <param name="isCardinal">Whether the appended string is cardinal.</param>
    /// <param name="hasHigherDigits">True signals that there are significant digits above these 3</param>
    /// <param name="hasLowerDigits">True signals that there are significant digits after these.</param>
    /// <param name="isCardinalOriginal">Whether the cardinal conversion.</param>
    /// <param name="thousands">Original value split into groups.</param>
    /// <param name="thousandIndex">Current group index.</param>
    private void appendThousand(StringBuilder builder, int value, boolean isCardinal, boolean hasHigherDigits, boolean hasLowerDigits, boolean isCardinalOriginal, ArrayList<Integer> thousands, int thousandIndex)
    {
        Debug.assertTrue(value >= 0 && value <= 999);

        int hundreds = value / 100;
        value -= 100 * hundreds;
        if (hundreds != 0)
            mNumberProvider.appendHundreds(builder, hundreds, isCardinal, value != 0, hasHigherDigits, hasLowerDigits, isCardinalOriginal, thousands, thousandIndex);

        mNumberProvider.appendHundred(builder, value, hundreds, isCardinal, (hundreds != 0) || hasHigherDigits, hasLowerDigits, isCardinalOriginal, thousands, thousandIndex);
    }

    /// <summary>
    /// Returns number provider by the specified <paramref name="language"/>.
    /// </summary>
    private static EuropeanTextualNumberProvider getTextualNumberProvider(/*Language*/int language)
    {
        msCultureInfo cultureInfo;
        try
        {
            cultureInfo = msCultureInfo.getCultureInfo((int)language);
        }
        catch(Exception e)
        {
            // Use current culture if locale Id has some illegal value.
            // For example, 0 - 'undefined', 0x400 - 'default'.
            cultureInfo = SystemPal.getCurrentCulture();
        }

        return getTextualNumberProvider(cultureInfo.getName());
    }

    /// <summary>
    /// Returns number provider by the specified <paramref name="cultureName"/>.
    /// </summary>
    private static EuropeanTextualNumberProvider getTextualNumberProvider(String cultureName)
    {
        switch (gStringSwitchMap.of(cultureName))
        {
            case /*"fr"*/0: // French
            case /*"fr-BE"*/1:// French (Belgium)
            case /*"fr-CA"*/2: // French (Canada)
            case /*"fr-FR"*/3: // French (France)
            case /*"fr-LU"*/4: // French (Luxembourg)
            case /*"fr-MC"*/5: // French (Principality of Monaco)
            case /*"fr-CH"*/6: // French (Switzerland)
            case /*"fy-NL"*/7: // Frisian (Netherlands)
                return new FrenchTextualNumber();
            case /*"de"*/8: // German
            case /*"de-AT"*/9: // German (Austria)
            case /*"de-DE"*/10: // German (Germany)
            case /*"de-LI"*/11: // German (Liechtenstein)
            case /*"de-LU"*/12: // German (Luxembourg)
            case /*"de-CH"*/13: // German (Switzerland)
                return new GermanTextualNumber();
            case /*"pt"*/14: // Portuguese
            case /*"pt-BR"*/15: // Portuguese (Brazil)
            case /*"pt-PT"*/16: // Portuguese (Portugal)
                return new PortugueseTextualNumber();
            case /*"es"*/17: // Spanish
            case /*"es-AR"*/18: // Spanish (Argentina)
            case /*"es-BO"*/19: // Spanish (Bolivia)
            case /*"es-CL"*/20: // Spanish (Chile)
            case /*"es-CO"*/21: // Spanish (Colombia)
            case /*"es-CR"*/22: // Spanish (Costa Rica)
            case /*"es-DO"*/23: // Spanish (Dominican Republic)
            case /*"es-EC"*/24: // Spanish (Ecuador)
            case /*"es-SV"*/25: // Spanish (El Salvador)
            case /*"es-GT"*/26: // Spanish (Guatemala)
            case /*"es-HN"*/27: // Spanish (Honduras)
            case /*"es-MX"*/28: // Spanish (Mexico)
            case /*"es-NI"*/29: // Spanish (Nicaragua)
            case /*"es-PA"*/30: // Spanish (Panama)
            case /*"es-PY"*/31: // Spanish (Paraguay)
            case /*"es-PE"*/32: // Spanish (Peru)
            case /*"es-PR"*/33: // Spanish (Puerto Rico)
            case /*"es-ES"*/34: // Spanish (Spain)
            case /*"es-ES_tradnl"*/35: // Spanish (Spain, Traditional Sort)
            case /*"es-US"*/36: // Spanish (United States)
            case /*"es-UY"*/37: // Spanish (Uruguay)
            case /*"es-VE"*/38: // Spanish (Venezuela)
                return new SpanishTextualNumber();
            case /*"ca"*/39: // Catalan
            case /*"ca-ES"*/40: // Catalan (Spain)
                return new CatalanTextualNumber();
            case /*"nl"*/41: // Dutch (Netherlands)
            case /*"nl-NL"*/42: // Dutch (Netherlands)
            case /*"nl-BE"*/43: // Dutch (Belgium)
                return new DutchTextualNumber();
            case /*"cs-CZ"*/44:
                return new CzechTextualNumber();
            case /*"hr-HR"*/45:
                return new CroatianTextualNumber();
            case /*"pl-PL"*/46:
                return new PolishTextualNumber();
            case /*"ro-RO"*/47:
                return new RomanianTextualNumber();
            case /*"eu-ES"*/48:
                return new BasqueTextualNumber();
            case /*"it-IT"*/49:
                return new ItalianTextualNumber();
            default: // default is English
                return new EnglishTextualNumber();
        }
    }

    private /*final*/ EuropeanTextualNumberProvider mNumberProvider;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"fr",
		"fr-BE",
		"fr-CA",
		"fr-FR",
		"fr-LU",
		"fr-MC",
		"fr-CH",
		"fy-NL",
		"de",
		"de-AT",
		"de-DE",
		"de-LI",
		"de-LU",
		"de-CH",
		"pt",
		"pt-BR",
		"pt-PT",
		"es",
		"es-AR",
		"es-BO",
		"es-CL",
		"es-CO",
		"es-CR",
		"es-DO",
		"es-EC",
		"es-SV",
		"es-GT",
		"es-HN",
		"es-MX",
		"es-NI",
		"es-PA",
		"es-PY",
		"es-PE",
		"es-PR",
		"es-ES",
		"es-ES_tradnl",
		"es-US",
		"es-UY",
		"es-VE",
		"ca",
		"ca-ES",
		"nl",
		"nl-NL",
		"nl-BE",
		"cs-CZ",
		"hr-HR",
		"pl-PL",
		"ro-RO",
		"eu-ES",
		"it-IT"
	);

}

