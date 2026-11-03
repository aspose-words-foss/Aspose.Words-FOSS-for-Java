// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/03/2021 by Vasiliy Stepchenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


class BasqueTextualNumber extends EuropeanTextualNumberProvider
{
    // The textual values have been taken from Word (1 - 10000), 0 and bigger than 10K from internet.
    // https://www.languagesandnumbers.com/how-to-count-in-basque/en/eus/.

    /*override*/ void appendHundred(StringBuilder builder, int value, int hundreds, boolean isCardinal, boolean hasHigherDigit, boolean hasLowerDigit, boolean isCardinalOriginal, ArrayList<Integer> thousands, int thousandIndex)
    {
        if (value == 0)
            return;

        // There is a very special word for 1, not the same as in 21, 41, 101 and so on.
        if (!isCardinal && value == 1 && hundreds == 0 && thousands.size() == 1)
        {
            appendWord(builder, ORDINAL_FIRST, false);
            return;
        }

        // In values from the 1000 to the 1999 the word 'mila' (thousand) should be without word 'bat' (one).
        // But from the 2000 there should be the appropriate word for the number of thousands, 2000 -> 'bi' (two) 'mila'.
        if (thousands.size() == 2 && thousands.get(1) == 1 && thousandIndex == 1)
            return;

        boolean useSeparator = ((hundreds > 0) || (thousands.size() > 0));

        if (value < 20)
        {
            appendWord(builder, getNumeralFirst19(value, true), useSeparator, getSeparatorHundredsTens());
        }
        else
        {
            int twenty = value / 20;
            value -= 20 * twenty;

            appendWord(builder, G_CARDINAL_TWENTIES[twenty - 1], useSeparator, getSeparatorHundredsTens());

            if (value != 0)
                appendWord(builder, getNumeralFirst19(value, true), true, getSeparatorTensOnes());
        }

        if (!isCardinal)
            appendWord(builder, ORDINAL_SUFFIX, false);
    }

    /*override*/ void appendHundreds(StringBuilder builder, int hundreds, boolean isCardinal, boolean isNonZeroHundreds, boolean hasHigherDigits, boolean hasLowerDigits, boolean isCardinalOriginal, ArrayList<Integer> thousands, int thousandIndex)
    {
        appendWord(builder, G_CARDINAL_HUNDREDS[hundreds - 1], hasHigherDigits);

        if (!isCardinal && !isNonZeroHundreds)
            appendWord(builder, ORDINAL_SUFFIX, false);
    }

    /*override*/ String getBigNumeral(int thousandIndex, boolean isCardinal, int thousand, ArrayList<Integer> thousands)
    {
        return G_BIG_CARDINALS[thousandIndex - 1];
    }

    /*override*/ String zero(boolean isCardinal)
    {
        return isCardinal ? "zero" : "nulua";
    }

    protected /*override*/ String getThousandsOrdinalSuffix() { return "garren"; }

    protected /*override*/ String getSeparatorTensOnes() { return "ta "; }

    protected /*override*/ String getSeparatorHundredsTens() { return " eta "; }

    protected /*override*/ String getSeparator() { return " "; }

    /*override*/ boolean getIncludeOneInBigNumbers() { return true; }

    /// <summary>
    /// For Basque only for 1000 no need to include "one" in big numbers.
    /// </summary>
    /*override*/ boolean includeThousandsWord(long value)
    {
        return value != 1000;
    }

    /*override*/ boolean useThousandSeparator(boolean isCardinal, int thousand, ArrayList<Integer> thousands, int thousandIndex)
    {
        return true;
    }

    protected /*override*/ String[] getCardinalFirst19() { return G_CARDINAL_FIRST_19; }

    protected /*override*/ String[] getOrdinalFirst19() { throw new IllegalStateException(); }

    protected /*override*/ String[] getOrdinalTens() { throw new IllegalStateException(); }

    protected /*override*/ String[] getCardinalTens() { throw new IllegalStateException(); }

    /*override*/ String getAnd() { return "koma"; }

    private static /*final*/ String[] G_CARDINAL_FIRST_19 =
    {
        "bat", "bi", "hiru", "lau", "bost", "sei", "zazpi", "zortzi", "bederatzi", "hamar", "hamaika", "hamabi", "hamairu", "hamalau", "hamabost", "hamasei", "hamazazpi", "hemezortzi", "hemeretzi"
    };

    private static /*final*/ String[] G_CARDINAL_TWENTIES =
    {
        "hogei", "berrogei", "hirurogei", "laurogei"
    };

    private static /*final*/ String[] G_CARDINAL_HUNDREDS =
    {
        "ehun", "berrehun", "hirurehun", "laurehun", "bostehun", "seiehun", "zazpiehun", "zortziehun", "bederatziehun"
    };

    private static /*final*/ String[] G_BIG_CARDINALS =
    {
        "mila", "milioi", "miliar"
    };

    private static final String ORDINAL_SUFFIX = "garren";
    private static final String ORDINAL_FIRST = "lehenengo";
}

