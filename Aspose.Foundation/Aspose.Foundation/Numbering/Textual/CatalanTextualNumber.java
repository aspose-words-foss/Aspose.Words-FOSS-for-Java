// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/02/2024 by Ilya Navrotskiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.msString;


/// <summary>
/// Ordinal/cardinal textual representation for Catalan language.
/// </summary>
class CatalanTextualNumber extends EuropeanTextualNumberProvider
{
    /// <summary>
    /// Appends hundreds.
    /// </summary>
    /*override*/ void appendHundreds(
        StringBuilder builder,
        int hundreds,
        boolean isCardinal,
        boolean isNonZeroHundreds,
        boolean hasHigherDigits,
        boolean hasLowerDigits,
        boolean isCardinalOriginal,
        ArrayList<Integer> thousands,
        int thousandIndex)
    {
        Debug.assertTrue((hundreds >= 1) && (hundreds <= 9));

        // Hundreds equal to '1' are not written.
        if (hundreds > 1)
        {
            String hundred = hasLowerDigits ? G_BIG_HUNDREDS[hundreds - 1] : getNumeralFirst19(hundreds, true);
            appendWord(builder, msString.format("{0}{1}", hundred, SEPARATOR_OTHER), hasHigherDigits);
        }

        // When not a round hundred, like 100, 200,...900, the cardinal hundred has to be written.
        if (isNonZeroHundreds)
            appendWord(builder, CARDINAL_HUNDRED, (hundreds == 1));
        else
            appendWord(builder, isCardinal ? CARDINAL_HUNDRED : ORDINAL_HUNDRED, hasHigherDigits && (hundreds == 1));

        // Add 's' for plural cardinal.
        if (isCardinal && hundreds > 1)
            appendWord(builder, "s", false);
    }

    /// <summary>
    /// Appends value within hundreds (1-99).
    /// </summary>
    /*override*/ void appendHundred(
        StringBuilder builder,
        int value,
        int hundreds,
        boolean isCardinal,
        boolean hasHigherDigit,
        boolean hasLowerDigit,
        boolean isCardinalOriginal,
        ArrayList<Integer> thousands,
        int thousandIndex)
    {
        if (value < 1)
            return;

        if (value < 20)
        {
            appendWord(builder, getFirst19(value, isCardinal, hasHigherDigit, hasLowerDigit), true);
            return;
        }

        int tens = value / 10;
        int ones = value - 10 * tens;

        // 20, 30, 40, ..., 90
        // The special value 'vine' should be written for round cardinal '20' in big numbers.
        boolean isBigRound20 = hasLowerDigit && (ones == 0) && (tens == 2);
        appendWord(builder, (isBigRound20 && isCardinalOriginal) ? BIG_20 : getNumeralTen(tens, isCardinal), true);

        if (ones == 0)
            return;

        // Resolve separator between tens and ones.
        if (!hasLowerDigit)
        {
            // Very first thousand.
            appendWord(builder, getSeparatorTensOnes(), false);
        }
        else if (isCardinalOriginal)
        {
            // Cardinals.
            if (tens != 2)
                appendWord(builder, getSeparatorHundredsTens(), false);
        }
        else
        {
            // Ordinals.
            appendWord(builder, (tens == 2) ? getSeparatorTensOnes() : SEPARATOR_OTHER, false);
        }

        // Before 'mil' (1000) digit '1' should not be written.
        if (isCardinalOriginal && hasLowerDigit && (ones == 1))
            return;

        String one = getFirst19(ones, isCardinal, true, hasLowerDigit);
        appendWord(builder, one, false);
    }

    /// <summary>
    /// Catalan language not adds 1 'un' only for the first big number 'mil' and
    /// only for the very first thousand (>=2000).
    /// </summary>
    /*override*/ boolean includeThousandsWord(long value)
    {
        return (value >= 2000);
    }

    /*override*/ boolean useThousandSeparator(boolean isCardinal, int thousand, ArrayList<Integer> thousands, int thousandIndex)
    {
        boolean hasLowerDigit = thousandIndex > 0 && !isLastNonZeroThousand(thousands, thousandIndex);

        int ones = thousand % 10;
        int tens = (thousand - ones) / 10 % 10;
        // Do not add separator before thousand in the following cases,o be added already
        // (see details in resolving separators in AppendHundred())
        if (isCardinal && (ones == 1) && hasLowerDigit && (tens > 2))
            return false;

        return super.useThousandSeparator(isCardinal, thousand, thousands, thousandIndex);
    }

    /*override*/ void appendThousandsOrdinalSuffix(StringBuilder builder)
    {
        // Add suffix only for mils.
        if (!StringUtil.isEndsWith(builder, G_BIG_NUMERALS[0]))
            return;

        super.appendThousandsOrdinalSuffix(builder);
    }

    /*override*/ String zero(boolean isCardinal)
    {
        return isCardinal ? "zero" : "";
    }

    /*override*/ String getBigNumeral(int thousandIndex, boolean isCardinal, int thousand, ArrayList<Integer> thousands)
    {
        return G_BIG_NUMERALS[thousandIndex - 1];
    }

    /// <summary>
    /// Returns numbers 1-19.
    /// </summary>
    private String getFirst19(int value, boolean isCardinal, boolean hasHigherDigit, boolean hasLowerDigit)
    {
        if (value < 1)
            return "";

        // Catalan ordinal digits 1-3 are ordinal only when they are on their own,
        // i.e., equal exactly to 1, 2 or 3.
        boolean useCardinal = (value <= 3) && (hasLowerDigit || hasHigherDigit);
        String s = getNumeralFirst19(value, useCardinal || isCardinal);

        // Special processing for '1', '2' and '3'.
        // Cardinal one(and its derivatives) takes the reduced
        // form 'u' as a noun to refer to the Digit and the number 1.
        // In other cases it uses long form 'un'. And in ordinal numbers also adds suffix 'è'.
        // The last is true for '2' and '3' as well.
        final String LONG_FORM_SUFFIX = "n";
        if ((value == 1) && ((!isCardinal && hasHigherDigit) || hasLowerDigit))
        {
            // Add 'n' to number 1 ('u[n]').
            s = msString.format("{0}{1}", s, LONG_FORM_SUFFIX);
        }

        final String LONG_ORDINAL_SUFFIX = "è";
        if (useCardinal && !isCardinal)
        {
            // Add 'é' to ordinal numbers 1-3 ('un[é]', 'dos[é]', 'tres[é]').
            s = msString.format("{0}{1}", s, hasLowerDigit ? "" : LONG_ORDINAL_SUFFIX);
        }

        return s;
    }

    /*override*/ boolean getIncludeOneInBigNumbers() { return true; }

    /*override*/ String getAnd() { return "coma"; }

    protected /*override*/ String[] getCardinalFirst19() { return G_CARDINAL_FIRST_19; }

    protected /*override*/ String[] getCardinalTens() { return G_CARDINAL_TENS; }

    protected /*override*/ String getThousandsOrdinalSuffix() { return ORDINAL_SUFFIX; }

    protected /*override*/ String[] getOrdinalFirst19() { return G_ORDINAL_FIRST_19; }

    protected /*override*/ String[] getOrdinalTens() { return G_ORDINAL_TENS; }

    protected /*override*/ String getSeparatorTensOnes() { return "-i-"; }

    protected /*override*/ String getSeparatorHundredsTens() { return " y "; }

    private static final String SEPARATOR_OTHER = "-";

    /// <summary>
    /// Catalan language uses the following hundred digits for numbers >= 100000.
    /// Note the digits 2, 3 and 5 are the same, as usual <see cref="gCardinalFirst19"/>,
    /// but lets use here full set of 1-9 for convenience.
    /// </summary>
    private static /*final*/ String[] G_BIG_HUNDREDS =
    {
        "", "dos", "tres", "cuatro", "cinc", "seis", "sete", "ocho", "nove"
    };

    /// <summary>
    /// Cardinal 1-19.
    /// </summary>
    private static /*final*/ String[] G_CARDINAL_FIRST_19 =
    {
        "u", "dos", "tres", "quatre", "cinc", "sis", "set", "vuit", "nou", "deu",
        "onze", "dotze", "tretze", "catorze", "quinze", "setze", "disset", "divuit", "dinou"
    };

    /// <summary>
    /// Ordinal 1-19.
    /// </summary>
    private static /*final*/ String[] G_ORDINAL_FIRST_19 =
    {
        "primer", "segon", "tercer", "quart", "cinquè", "sisè", "setè", "vuitè", "novè", "desè", "onzè",
        "dozè", "tretzè", "catorzè", "quinzè", "setzè", "dissetè", "divuitè", "dinovè"
    };

    /// <summary>
    /// Cardinal tens 20-90.
    /// </summary>
    private static /*final*/ String[] G_CARDINAL_TENS =
    {
        "vint", // 20
        "trenta", // 30
        "quaranta", // 40
        "cinquanta", // 50
        "seixanta", // 60
        "setanta", // 70
        "vuitanta", //80
        "noranta" //90
    };

    /// <summary>
    /// Ordinal tens 20-90.
    /// </summary>
    /// <remarks>
    /// There are some sources that describes another numerals for Ordinals, for example:
    /// https://omniglot.com/language/numbers/catalan.htm.
    /// But Word uses Cardinal tens the same as Ordinal ones, described here:
    /// https://ca.wikipedia.org/wiki/Numerals_en_català
    /// </remarks>
    private static /*final*/ String[] G_ORDINAL_TENS = G_CARDINAL_TENS;

    /// <summary>
    /// Big numbers '1000', '1.000.000', '1.000.000.000'.
    /// https://ca.wikipedia.org/wiki/Numerals_en_català
    /// </summary>
    private static /*final*/ String[] G_BIG_NUMERALS = { "mil", "milió", "miliard" };


    /// <summary>
    /// The special value 'vine' should be written for round cardinal '20' in big numbers.
    /// </summary>
    private static final String BIG_20 = "vine";

    private static final String ORDINAL_SUFFIX = "é";
    private static final String CARDINAL_HUNDRED = "cent";
    private static final String ORDINAL_HUNDRED = "cené";
}

