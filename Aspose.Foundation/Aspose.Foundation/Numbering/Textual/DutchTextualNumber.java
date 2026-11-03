// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2016 by Edward Voronov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


/// <summary>
/// Encapsulates ordinal/cardinal nuances of Dutch.
/// </summary>
class DutchTextualNumber extends EuropeanTextualNumberProvider
{
    protected /*override*/ void appendHundredFirst19(StringBuilder builder, int value, int hundreds, boolean isCardinal,
        boolean hasHigherDigit, boolean hasLowerDigit, boolean isCardinalOriginal,
        ArrayList<Integer> thousands, int thousandIndex)
    {
        String word = getNumeralFirst19(value, isCardinal);
        appendWord(builder, word, false, "");
    }

    protected /*override*/ void appendHundredTen(StringBuilder builder, int tens, int hundreds, boolean isCardinal,
        boolean hasHigherDigit, boolean hasLowerDigit, boolean isCardinalOriginal, ArrayList<Integer> thousands,
        int thousandIndex)
    {
        appendWord(builder, getNumeralTen(tens, isCardinal), false);
    }

    protected /*override*/ void appendHundredOther(StringBuilder builder, int tens, int value, int hundreds, boolean isCardinal,
        boolean hasHigherDigit, boolean hasLowerDigit, boolean isCardinalOriginal,
        ArrayList<Integer> thousands,
        int thousandIndex)
    {
        String numeralFirst19 = getNumeralFirst19(value, isCardinal);
        //The dots on the ë (called trema) are used in Dutch to separate clashing vowels.
        String word = numeralFirst19 + (numeralFirst19.charAt(numeralFirst19.length() - 1) == 'e' ? "ën" : "en") + getNumeralTen(tens, isCardinal) + (isCardinal ? "" : "ste");
        appendWord(builder, word, false, "");
    }

    /*override*/ void appendHundreds(StringBuilder builder, int hundreds, boolean isCardinal, boolean isNonZeroHundreds,
        boolean hasHigherDigits, boolean hasLowerDigits, boolean isCardinalOriginal,
        ArrayList<Integer> thousands, int thousandIndex)
    {
        appendWord(builder, getNumeralFirst19(hundreds, true), false);
        appendWord(builder, hundred(), false);
    }

    protected /*override*/ String getSeparatorTensOnes() { return ""; }

    /*override*/ boolean useThousandSeparator(boolean isCardinal, int thousand, ArrayList<Integer> thousands, int thousandIndex)
    {
        if ((thousand >= 1 && thousand <= 19) || (thousand >= 100 && thousand <= 119))
        {
            return false;
        }
        if (thousand >= 200)
        {
            char secondDigit = FormatterPal.intToStr(thousand).charAt(1);
            if (secondDigit == '0' || secondDigit == '1')
            {
                return false;
            }
        }
        return isCardinal;
    }

    protected /*override*/ String getThousandsOrdinalSuffix() { return "ste"; }

    /*override*/ boolean getIncludeOneInBigNumbers() { return true; }

    /*override*/ String getAnd() { return "en"; }

    /*override*/ String zero(boolean isCardinal)
    {
        return "nul";
    }

    protected /*override*/ String getSeparator() { return " "; }

    /// <summary>
    /// For Dutch only for 1000 no need to include "one" in big numbers.
    /// </summary>
    /*override*/ boolean includeThousandsWord(long value)
    {
        return value != 1000;
    }

    /*override*/ String getBigNumeral(int thousandIndex, boolean isCardinal, int thousand, ArrayList<Integer> thousands)
    {
        return G_BIG_NUMERALS[thousandIndex - 1];
    }

    protected /*override*/ String[] getOrdinalFirst19() { return G_ORDINAL_FIRST_19; }

    protected /*override*/ String[] getCardinalFirst19() { return G_CARDINAL_FIRST_19; }

    protected /*override*/ String[] getOrdinalTens() { return G_CARDINAL_TENS; }

    protected /*override*/ String[] getCardinalTens() { return G_CARDINAL_TENS; }

    private static String hundred()
    {
        return "honderd";
    }

    private static /*final*/ String[] G_CARDINAL_FIRST_19 =
    {
        "één", "twee", "drie", "vier", "vijf", "zes", "zeven", "acht", "negen", "tien",
        "elf", "twaalf", "dertien", "veertien", "vijftien", "zestien", "zeventien", "achttien", "negentien"
    };

    private static /*final*/ String[] G_ORDINAL_FIRST_19 =
    {
        "eerste", "tweede", "derde", "vierde", "vijfde", "zesde", "zevende", "achtse", "negende", "tiende", "elfde",
        "twaalfde", "dertiende", "veertiende", "vijftiende", "zestiende", "zeventiende", "achttiende", "negentiende"
    };

    private static /*final*/ String[] G_CARDINAL_TENS =
    {
        "twintig", "dertig", "veertig", "vijftig", "zestig", "zeventig", "tachtig", "negentig"
    };

    private static /*final*/ String[] G_BIG_NUMERALS = { "duizend", "miljoen", "miljard" };
}

