// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/05/2013 by Denis Darkin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


/// <summary>
///  Implements german-specific numbering nuances.
/// </summary>
/// <remarks>Unfortunately some of very "different" numbering rules could not be fully abtracted and leaked away into the
/// EuropeanTextualNumberProvider base class where they are isolated by using virtual <see cref="IsGerman"/> member.
/// Example of this is reverse writing of numbers in <see cref="EuropeanTextualNumberProvider.AppendHundred"/></remarks>
class GermanTextualNumber extends EuropeanTextualNumberProvider
{
    /*override*/ void appendHundreds(StringBuilder builder, int hundreds, boolean isCardinal, boolean isNonZeroHundreds,
        boolean hasHigherDigits, boolean hasLowerDigits, boolean isCardinalOriginal,
        ArrayList<Integer> thousands, int thousandIndex)
    {
        Debug.assertTrue((hundreds >= 1) && (hundreds <= 9));

        if (hundreds != 1 || hasHigherDigits)
            appendWord(builder, G_CARDINAL_FIRST_19[hundreds - 1], false);
        appendWord(builder, "hundert", false);

        if (!(isCardinal || isNonZeroHundreds))
            appendWord(builder, "ste", false);
    }

    protected /*override*/ boolean mergeOrdinalThousands(int thousand)
    {
        return true;
    }

    protected /*override*/ String getThousandsOrdinalSuffix() { return "ste"; }

    /*override*/ boolean getIncludeOneInBigNumbers() { return true; }

    /*override*/ String getAnd() { return "und"; }

    /*override*/ String zero(boolean isCardinal)
    {
        return isCardinal ? "null" : "nullte";
    }

    /*override*/ boolean isFirstLetterAlwaysUppercase()
    {
        return true;
    }

    protected /*override*/ boolean isGerman() { return true; }

    protected /*override*/ String getSeparator() { return ""; }

    protected /*override*/ String getSeparatorHundredsTens() { return ""; }

    protected /*override*/ String getSeparatorTensOnes() { return "und"; }

    /*override*/ String getBigNumeral(int thousandIndex, boolean isCardinal, int thousand, ArrayList<Integer> thousands)
    {
        return G_BIG_NUMERALS[thousandIndex - 1];
    }

    protected /*override*/ String[] getOrdinalFirst19() { return G_ORDINAL_FIRST_19; }

    protected /*override*/ String[] getCardinalFirst19() { return G_CARDINAL_FIRST_19; }

    protected /*override*/ String[] getOrdinalTens() { return G_ORDINAL_TENS; }

    protected /*override*/ String[] getCardinalTens() { return G_CARDINAL_TENS; }

    private static /*final*/ String[] G_CARDINAL_FIRST_19 =
    {
        "ein", "zwei", "drei", "vier", "fünf", "sechs", "sieben", "acht", "neun", "zehn",
        "elf", "zwölf", "dreizehn", "vierzehn", "fünfzehn", "sechzehn", "siebzehn", "achtzehn", "neunzehn"
    };

    private static /*final*/ String[] G_CARDINAL_TENS =
    {
        "zwanzig", "dreißig", "vierzig", "fünfzig", "sechzig", "siebzig", "achtzig", "neunzig"
    };

    private static /*final*/ String[] G_ORDINAL_FIRST_19 =
    {
        "erste", "zweite", "dritte", "vierte", "fünfte", "sechste", "siebente", "achte", "neunte", "zehnte", "elfte",
        "zwölfte", "dreizehnte", "vierzehnte", "fünfzehnte", "sechzehnte", "siebzehnte", "achtzehnte", "neunzehnte"
    };

    private static /*final*/ String[] G_ORDINAL_TENS =
    {
        "zwanzigste", "dreißigste", "vierzigste", "fünfzigste", "sechzigste", "siebzigste", "achtzigste", "neunzigste"
    };

    private static /*final*/ String[] G_BIG_NUMERALS = { "tausend", "million", "millarde" };
}

