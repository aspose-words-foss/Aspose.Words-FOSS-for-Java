// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/04/2014 by Edward Voronov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


/// <summary>
/// Encapsulates ordinal/cardinal nuances of French.
/// </summary>
class FrenchTextualNumber extends EuropeanTextualNumberProvider
{
    /*override*/ void appendHundred(StringBuilder builder, int value, int hundreds, boolean isCardinal,
        boolean hasHigherDigit, boolean hasLowerDigit, boolean isCardinalOriginal, ArrayList<Integer> thousands,
        int thousandIndex)
    {
        if (value == 0)
            return;

        int tens = value / 10;
        int ones = value - 10 * tens;

        if (value < 70) // 1-69
        {
            if (value == 1 && !isCardinal && !hasHigherDigit && !hasLowerDigit)
                appendWord(builder, FIRST, true); // 1
            else
                appendWord(builder, convertCore(value, isCardinal), true); // 2-69
        }
        else if (value < 80) // 70-79
        {
            appendWord(builder,
                getFrenchNumeralTen(6, true) + getSeparatorTensOnes(7, ones) + convertCore(value - 60, isCardinal),
                true);
        }
        else if (value == 80) // 80
        {
            appendWord(builder, isCardinalOriginal ? CARDINAL_EIGHTY : (hasLowerDigit ? EIGHTY : ORDINAL_EIGHTY), true);
        }
        else // 81-99
        {
            appendWord(builder, EIGHTY + getSeparatorTensOnes(tens, ones) + convertCore(value - 80, isCardinal), true);
        }
    }

    /*override*/ void appendHundreds(StringBuilder builder, int hundreds, boolean isCardinal, boolean isNonZeroHundreds,
        boolean hasHigherDigits, boolean hasLowerDigits, boolean isCardinalOriginal,
        ArrayList<Integer> thousands, int thousandIndex)
    {
        boolean cardinal = isCardinal || isNonZeroHundreds;
        if (hundreds != 1)
            appendWord(builder, getNumeralFirst16(hundreds, true), true);
        appendWord(builder, hundred(hundreds, cardinal, isNonZeroHundreds), true);
    }

    /*override*/ void appendThousandsOrdinalSuffix(StringBuilder builder)
    {
        // mille -> millième
        if (builder.charAt(builder.length() - 1) == 'e')
            builder.setLength(builder.length() - 1);

        super.appendThousandsOrdinalSuffix(builder);
    }

    protected /*override*/ String getThousandsOrdinalSuffix() { return ORDINAL_SUFFIX; }

    /*override*/ boolean getIncludeOneInBigNumbers() { return false; }

    /*override*/ String getAnd() { return "et"; }

    /*override*/ String zero(boolean isCardinal)
    {
        return "zéro";
    }

    /*override*/ String getBigNumeral(int thousandIndex, boolean isCardinal, int thousand, ArrayList<Integer> thousands)
    {
        return G_BIG_NUMERALS[thousandIndex - 1];
    }

    protected /*override*/ String[] getOrdinalFirst19() { throw new UnsupportedOperationException("The OrdinalFirst19 property not actual for FrenchTextualNumber"); }

    protected /*override*/ String[] getCardinalFirst19() { throw new UnsupportedOperationException("The CardinalFirst19 property not actual for FrenchTextualNumber"); }

    protected /*override*/ String[] getOrdinalTens() { throw new UnsupportedOperationException("The OrdinalTens property not actual for FrenchTextualNumber"); }

    protected /*override*/ String[] getCardinalTens() { throw new UnsupportedOperationException("The CardinalTens property not actual for FrenchTextualNumber"); }

    private static String getNumeralFirst16(int n, boolean isCardinal)
    {
        int index = n - 1;
        return isCardinal ? G_CARDINAL_FIRST_16[index] : G_ORDINAL_FIRST_16[index];
    }

    private static String getFrenchNumeralTen(int tens, boolean isCardinal)
    {
        int index = tens - 1;
        return isCardinal ? G_CARDINAL_TENS_FIRST_6[index] : G_ORDINAL_TENS_FIRST_6[index];
    }

    private static String hundred(int hundreds, boolean isCardinal, boolean isNonZeroHundreds)
    {
        return isCardinal
                   ? (hundreds != 1 && !isNonZeroHundreds ? CARDINAL_PLURAL_HUNDRED : CARDINAL_SINGULAR_HUNDRED)
                   : ORDINAL_HUNDRED;
    }

    private static String getSeparatorTensOnes(int tens, int ones)
    {
        if (tens > 7 || ones != 1)
        {
            return "-";
        }

        return " et ";
    }

    private static String convertCore(int value, boolean isCardinal)
    {
        if (value <= 0)
        {
            return "";
        }

        if (value < 17) // 1-16
        {
            return getNumeralFirst16(value, isCardinal);
        }

        if (value < 70) // 17-69
        {
            int tens = value / 10;
            value -= 10 * tens;

            if (value == 0)
            {
                // 20, 30, 40, 50, 60
                return getFrenchNumeralTen(tens, isCardinal);
            }

            return getFrenchNumeralTen(tens, true) + getSeparatorTensOnes(tens, value) + getNumeralFirst16(value, isCardinal);
        }

        return "";
    }

    private static /*final*/ String[] G_CARDINAL_FIRST_16 =
    {
        "un", "deux", "trois", "quatre", "cinq", "six", "sept", "huit", "neuf", "dix",
        "onze", "douze", "treize", "quatorze", "quinze", "seize"
    };

    private static /*final*/ String[] G_ORDINAL_FIRST_16 =
    {
        "unième", "deuxième", "troisième", "quatrième", "cinquième",  "sixième", "septième", "huitième", "neuvième", "dixième",
        "onzième", "douzième", "treizième", "quatorzième", "quinzième", "seizième"
    };

    private static /*final*/ String[] G_CARDINAL_TENS_FIRST_6 =
    {
        "dix", "vingt", "trente", "quarante", "cinquante", "soixante"
    };

    private static /*final*/ String[] G_ORDINAL_TENS_FIRST_6 =
    {
        "dixième", "vingtième", "trentième", "quarantième", "cinquantième", "soixantième"
    };

    private static /*final*/ String[] G_BIG_NUMERALS = { "mille", "million", "milliard" };

    private static final String ORDINAL_SUFFIX = "ième";

    private static final String CARDINAL_SINGULAR_HUNDRED = "cent";

    private static final String CARDINAL_PLURAL_HUNDRED = CARDINAL_SINGULAR_HUNDRED + "s";

    private static final String ORDINAL_HUNDRED = CARDINAL_SINGULAR_HUNDRED + ORDINAL_SUFFIX;

    private static final String CARDINAL_EIGHTY = "quatre-vingts";

    private static final String EIGHTY = "quatre-vingt";

    private static final String ORDINAL_EIGHTY = "quatre-vingtième";

    private static final String FIRST = "premier";
}

