// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/05/2013 by Denis Darkin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


/// <summary>
/// Encapsulates ordinal/cardinal nuances of English.
/// </summary>
class EnglishTextualNumber extends EuropeanTextualNumberProvider
{
    protected /*override*/ void appendHundredFirst19(StringBuilder builder, int value, int hundreds, boolean isCardinal,
        boolean hasHigherDigit, boolean hasLowerDigit, boolean isCardinalOriginal,
        ArrayList<Integer> thousands, int thousandIndex)
    {
        appendWord(builder, getNumeralFirst19(value, isCardinal), true);
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
        boolean requireSpecialSeparator = hasHigherDigit && isCardinal;
        appendWord(builder, getNumeralTen(tens, true) + getSeparatorTensOnes() + getNumeralFirst19(value, isCardinal), true, requireSpecialSeparator ? getSeparatorHundredsTens() : getSeparator());
    }

    /*override*/ void appendHundreds(StringBuilder builder, int hundreds, boolean isCardinal, boolean isNonZeroHundreds,
        boolean hasHigherDigits, boolean hasLowerDigits, boolean isCardinalOriginal,
        ArrayList<Integer> thousands, int thousandIndex)
    {
        boolean cardinal = isCardinal || isNonZeroHundreds;
        appendWord(builder, getNumeralFirst19(hundreds, true), true);
        appendWord(builder, hundred(cardinal), true);
    }

    /*override*/ String getAnd() { return "and"; }

    /*override*/ String zero(boolean isCardinal)
    {
        return "zero";
    }

    protected /*override*/ String getThousandsOrdinalSuffix() { return "th"; }

    /*override*/ boolean getIncludeOneInBigNumbers() { return true; }

    protected /*override*/ String[] getCardinalFirst19() { return G_CARDINAL_FIRST_19; }

    protected /*override*/ String[] getCardinalTens() { return G_CARDINAL_TENS; }

    /*override*/ String getBigNumeral(int thousandIndex, boolean isCardinal, int thousand, ArrayList<Integer> thousands)
    {
        return G_BIG_NUMERALS[thousandIndex - 1];
    }

    protected /*override*/ String[] getOrdinalFirst19() { return G_ORDINAL_FIRST_19; }

    protected /*override*/ String[] getOrdinalTens() { return G_ORDINAL_TENS; }

    private static String hundred(boolean isCardinal)
    {
        return (isCardinal) ? "hundred" : "hundredth";
    }

    private static /*final*/ String[] G_CARDINAL_FIRST_19 =
    {
        "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
        "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
    };

    private static /*final*/ String[] G_ORDINAL_FIRST_19 =
    {
        "first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth", "tenth", "eleventh",
        "twelfth", "thirteenth", "fourteenth", "fifteenth", "sixteenth", "seventeenth", "eighteenth", "nineteenth"
    };

    private static /*final*/ String[] G_CARDINAL_TENS =
    {
        "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"
    };

    private static /*final*/ String[] G_ORDINAL_TENS =
    {
        "twentieth", "thirtieth", "fortieth", "fiftieth", "sixtieth", "seventieth", "eightieth", "ninetieth"
    };

    private static /*final*/ String[] G_BIG_NUMERALS = { "thousand", "million", "billion" };
}

