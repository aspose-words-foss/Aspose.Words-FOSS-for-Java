// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2016 by Edward Voronov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


/// <summary>
/// Ordinal/cardinal textual representation for Romanian language.
/// </summary>
class RomanianTextualNumber extends EuropeanTextualNumberProvider
{
    protected /*override*/ void appendHundredFirst19(StringBuilder builder, int value, int hundreds, boolean isCardinal,
        boolean hasHigherDigit, boolean hasLowerDigit, boolean isCardinalOriginal,
        ArrayList<Integer> thousands, int thousandIndex)
    {
        String word = getNumeralFirst19(value, isCardinal, isCardinalOriginal, thousandIndex, hasHigherDigit);

        appendWord(builder, word, true, getSeparator());
    }

    protected /*override*/ void appendHundredTen(StringBuilder builder, int tens, int hundreds, boolean isCardinal,
        boolean hasHigherDigit, boolean hasLowerDigit, boolean isCardinalOriginal, ArrayList<Integer> thousands,
        int thousandIndex)
    {
        appendWord(builder, getNumeralTen(tens, isCardinal), true);
    }

    protected /*override*/ void appendHundredOther(StringBuilder builder, int tens, int value, int hundreds, boolean isCardinal,
        boolean hasHigherDigit, boolean hasLowerDigit, boolean isCardinalOriginal,
        ArrayList<Integer> thousands,
        int thousandIndex)
    {
        String word = getNumeralTen(tens, isCardinal) + " și " + getNumeralFirst19(value, isCardinal, isCardinalOriginal, 0, true);
        appendWord(builder, word, true, getSeparator());
    }

    private String getNumeralFirst19(int value, boolean isCardinal, boolean isCardinalOriginal, int thousandIndex, boolean hasHigherDigit)
    {
        if (thousandIndex > 0)
            return getBigNumeralFirst19(value, isCardinal, thousandIndex);

        if (!isCardinalOriginal)
        {
            if ((value == 1) && !hasHigherDigit)
                return "întâi";

            if (value == 8)
                return "optu";
        }

        return getNumeralFirst19(value, isCardinal);
    }

    private String getBigNumeralFirst19(int value, boolean isCardinal, int thousandIndex)
    {
        switch (value)
        {
            case 1:
                return thousandIndex < 2
                    ? "o"
                    : "un";
            case 2:
                return "două";
            default:
                return getNumeralFirst19(value, isCardinal);
        }
    }

    /*override*/ void appendHundreds(StringBuilder builder, int hundreds, boolean isCardinal, boolean isNonZeroHundreds,
        boolean hasHigherDigits, boolean hasLowerDigits, boolean isCardinalOriginal,
        ArrayList<Integer> thousands, int thousandIndex)
    {
        appendWord(builder, getBigNumeralFirst19(hundreds, true, 0), true);
        appendWord(builder, hundred(hundreds), true);
    }

    private static String hundred(int hundreds)
    {
        return hundreds == 1 ? "sută" : "sute";
    }

    protected /*override*/ String getThousandsOrdinalSuffix() { return ""; }

    protected /*override*/ String getSeparatorTensOnes() { return " "; }

    /*override*/ boolean getIncludeOneInBigNumbers() { return true; }

    /*override*/ String getAnd() { return "şi"; }

    /*override*/ String zero(boolean isCardinal)
    {
        return "zero";
    }

    /*override*/ String getBigNumeral(int thousandIndex, boolean isCardinal, int thousand, ArrayList<Integer> thousands)
    {
        switch (thousandIndex)
        {
            case 1:
                int hundred = thousand % 100;

                if ((hundred == 1) || (hundred == 2))
                    return "mie";

                if ((hundred == 0) || (hundred >= 20))
                    return "de mii";

                return "mii";
            case 2:
                return "milion";
            case 3:
                return "miliard";
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "thousandIndex");
        }
    }

    /*override*/ String getNumberPrefix(long value, boolean isCardinal)
    {
        if (usePrefixAndSuffix(value, isCardinal))
            return "al ";

        return super.getNumberPrefix(value, isCardinal);
    }

    /*override*/ String getNumberSuffix(long value, boolean isCardinal)
    {
        if (usePrefixAndSuffix(value, isCardinal))
            return value < 1000000 ? "lea" : "ulea";

        return super.getNumberSuffix(value, isCardinal);
    }

    private static boolean usePrefixAndSuffix(long value, boolean isCardinal)
    {
        return !isCardinal && value > 1;
    }

    protected /*override*/ String[] getOrdinalFirst19() { return G_FIRST_19; }

    protected /*override*/ String[] getCardinalFirst19() { return G_FIRST_19; }

    protected /*override*/ String[] getOrdinalTens() { return G_TENS; }

    protected /*override*/ String[] getCardinalTens() { return G_TENS; }

    private static /*final*/ String[] G_FIRST_19 =
    {
        "unu", "doi", "trei", "patru", "cinci", "șase", "șapte", "opt", "nouă", "zece",
        "unsprezece", "doisprezece", "treisprezece", "paisprezece", "cincisprezece", "șaisprezece", "șaptesprezece", "optsprezece", "nouăsprezece"
    };

    private static /*final*/ String[] G_TENS =
    {
        "douăzeci", "treizeci", "patruzeci", "cincizeci", "șaizeci", "șaptezeci", "optzeci", "nouăzeci"
    };
}

