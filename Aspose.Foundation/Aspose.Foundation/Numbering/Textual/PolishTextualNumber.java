// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2016 by Edward Voronov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


/// <summary>
/// Ordinal/cardinal textual representation for Polish language.
/// </summary>
class PolishTextualNumber extends EuropeanTextualNumberProvider
{
    protected /*override*/ void appendHundredFirst19(StringBuilder builder, int value, int hundreds, boolean isCardinal,
        boolean hasHigherDigit, boolean hasLowerDigit, boolean isCardinalOriginal,
        ArrayList<Integer> thousands, int thousandIndex)
    {
        boolean useSeparator = useHundredSeparator(hundreds, isCardinal, hasHigherDigit, isCardinalOriginal, thousands, thousandIndex);
        String word = getNumeralFirst19(value, isCardinalOriginal, thousands, thousandIndex);
        appendWord(builder, word, true, useSeparator ? getSeparator() : "");
    }

    protected /*override*/ void appendHundredTen(StringBuilder builder, int tens, int hundreds, boolean isCardinal,
        boolean hasHigherDigit, boolean hasLowerDigit, boolean isCardinalOriginal, ArrayList<Integer> thousands,
        int thousandIndex)
    {
        boolean useSeparator = useHundredSeparator(hundreds, isCardinal, hasHigherDigit, isCardinalOriginal, thousands, thousandIndex);
        appendWord(builder, getNumeralTen(tens, isCardinalOriginal, thousands, thousandIndex), useSeparator);
    }

    protected /*override*/ void appendHundredOther(StringBuilder builder, int tens, int value, int hundreds, boolean isCardinal,
        boolean hasHigherDigit, boolean hasLowerDigit, boolean isCardinalOriginal,
        ArrayList<Integer> thousands,
        int thousandIndex)
    {
        boolean useSeparator = useHundredSeparator(hundreds, isCardinal, hasHigherDigit, isCardinalOriginal, thousands, thousandIndex);
        String tensSeparator = (isCardinal || !hasLowerDigit) && (isCardinalOriginal || (thousandIndex == 0) || !isLastNonZeroThousand(thousands, thousandIndex))
            ? getSeparator()
            : "";
        String word = getNumeralTen(tens, isCardinalOriginal, thousands, thousandIndex) + tensSeparator + getNumeralFirst19(value, isCardinalOriginal, thousands, thousandIndex);
        appendWord(builder, word, true, useSeparator ? getSeparator() : "");
    }

    private static boolean useHundredSeparator(int hundreds, boolean isCardinal, boolean hasHigherDigit, boolean isCardinalOriginal, ArrayList<Integer> thousands, int thousandIndex)
    {
        return (hundreds != 0 || useSeparator(isCardinal, hasHigherDigit, thousands, thousandIndex)) &&
               (isCardinalOriginal || (thousandIndex == 0) || !isLastNonZeroThousand(thousands, thousandIndex));
    }

    private String getNumeralFirst19(int value, boolean isCardinal, ArrayList<Integer> thousands, int thousandIndex)
    {
        if (isCardinal)
            return getNumeralFirst19(value, true);

        if (thousandIndex == 0)
            return getNumeralFirst19(value, false);

        if (!isLastNonZeroThousand(thousands, thousandIndex))
            return getNumeralFirst19(value, true);

        return G_ORDINAL_BIGS[value - 1];
    }

    private String getNumeralTen(int value, boolean isCardinal, ArrayList<Integer> thousands, int thousandIndex)
    {
        if (isCardinal)
            return getNumeralTen(value, true);

        if (thousandIndex == 0)
            return getNumeralTen(value, false);

        if (!isLastNonZeroThousand(thousands, thousandIndex))
            return getNumeralTen(value, true);

        return G_ORDINAL_BIG_TENS[value - 1];
    }

    /*override*/ void appendHundreds(StringBuilder builder, int hundreds, boolean isCardinal, boolean isNonZeroHundreds,
        boolean hasHigherDigits, boolean hasLowerDigits, boolean isCardinalOriginal,
        ArrayList<Integer> thousands, int thousandIndex)
    {
        boolean useSeparator = useSeparator(isCardinal, hasHigherDigits, thousands, thousandIndex);
        if (hundreds != 1)
            appendWord(builder, getHundredsNumeral(hundreds, isCardinalOriginal, isNonZeroHundreds, thousands, thousandIndex), useSeparator);

        appendWord(builder, hundred(hundreds, isCardinalOriginal, isNonZeroHundreds, thousands, thousandIndex), hundreds == 1 && useSeparator);
    }

    private static boolean useSeparator(boolean isCardinal, boolean hasHigherDigit, ArrayList<Integer> thousands, int thousandIndex)
    {
        return isCardinal || !hasHigherDigit || (thousands.get(thousandIndex + 1) % 100 < 20) || (thousands.get(thousandIndex + 1) % 10 != 0);
    }

    private static String hundred(int hundreds, boolean isCardinal, boolean isNonZeroHundreds, ArrayList<Integer> thousands, int thousandIndex)
    {
        if (isCardinal || ((thousandIndex == 0) && isNonZeroHundreds) || ((thousandIndex != 0) && !isLastNonZeroThousand(thousands, thousandIndex)))
        {
            switch (hundreds)
            {
                case 1:
                    return "sto";
                case 2:
                    return "ście";
                case 3:
                case 4:
                    return "sta";
                default:
                    return "set";
            }
        }

        if (thousandIndex == 0)
            return "setny";

        return hundreds < 5 ? "stu" : "set";
    }

    private String getHundredsNumeral(int hundreds, boolean isCardinal, boolean isNonZeroHundreds, ArrayList<Integer> thousands, int thousandIndex)
    {
        if (isCardinal)
        {
            if (hundreds == 2)
                return "dwie";
        }
        else
        {
            if (thousandIndex == 0)
            {
                if (hundreds == 2)
                    return isNonZeroHundreds ? "dwie" : "dwu";
            }
            else
            {
                if (isLastNonZeroThousand(thousands, thousandIndex))
                {
                    switch (hundreds)
                    {
                        case 2:
                            return "dwu";
                        case 6:
                            return "szeuść";
                        default:
                            break;
                    }
                }
                else
                {
                    if (hundreds == 2)
                        return "dwie";
                }
            }
        }

        return getNumeralFirst19(hundreds, true);
    }

    /*override*/ boolean useThousandSeparator(boolean isCardinal, int thousand, ArrayList<Integer> thousands, int thousandIndex)
    {
        if (!super.useThousandSeparator(isCardinal, thousand, thousands, thousandIndex))
            return false;

        if (isCardinal)
            return true;

        return !isLastNonZeroThousand(thousands, thousandIndex);
    }

    protected /*override*/ String getThousandsOrdinalSuffix() { return ""; }

    /*override*/ boolean getIncludeOneInBigNumbers() { return false; }

    /*override*/ String getAnd() { return "i"; }

    /*override*/ String zero(boolean isCardinal)
    {
        return isCardinal ? "zero" : "zerowy";
    }

    /*override*/ String getBigNumeral(int thousandIndex, boolean isCardinal, int thousand, ArrayList<Integer> thousands)
    {
        switch (thousandIndex)
        {
            case 1:
                if (thousand == 1)
                {
                    return !isCardinal && (thousands.get(0) == 0)
                        ? "tysiączny"
                        : "tysiąc";
                }

                if (!isCardinal && (thousands.get(0) == 0))
                    return "tysięczny";

                switch (thousand % 10)
                {
                    case 2:
                    case 3:
                    case 4:
                        int hundred = thousand % 100;
                        if ((hundred < 10) || (hundred > 20))
                            return "tysiące";
                        break;
                    default:
                        break;
                }

                return "tysięcy";
            case 2:
                return isCardinal ? "milion" : "milionowy";
            case 3:
                return isCardinal ? "miliard" : "miliardowy";
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "thousandIndex");
        }
    }

    protected /*override*/ String[] getOrdinalFirst19() { return G_ORDINAL_FIRST_19; }

    protected /*override*/ String[] getCardinalFirst19() { return G_CARDINAL_FIRST_19; }

    protected /*override*/ String[] getOrdinalTens() { return G_ORDINAL_TENS; }

    protected /*override*/ String[] getCardinalTens() { return G_CARDINAL_TENS; }

    protected /*override*/ String getSeparatorTensOnes() { return " "; }

    private static /*final*/ String[] G_CARDINAL_FIRST_19 =
    {
        "jeden", "dwa", "trzy", "cztery", "pięć", "sześć", "siedem", "osiem", "dziewięć", "dziesięć",
        "jedenaście", "dwanaście", "trzynaście", "czternaście", "piętnaście", "szesnaście", "siedemnaście", "osiemnaście", "dziewiętnaście"
    };

    private static /*final*/ String[] G_ORDINAL_FIRST_19 =
    {
        "pierwszy", "drugi", "trzeci", "czwarty", "piąty", "szósty", "siódmy", "ósmy", "dziewiąty", "dziesiąty",
        "jedenasty", "dwunasty", "trzynasty", "czternasty", "piętnasty", "szesnasty", "siedemnasty", "osiemnasty", "dziewiętnasty"
    };

    private static /*final*/ String[] G_CARDINAL_TENS =
    {
        "dwadzieścia", "trzydzieści", "czterdzieści", "pięćdziesiąt", "sześćdziesiąt", "siedemdziesiąt", "osiemdziesiąt", "dziewięćdziesiąt"
    };

    private static /*final*/ String[] G_ORDINAL_TENS =
    {
        "dwudziesty", "trzydziesty", "czterdziesty", "pięćdziesiąty", "sześćdziesiąty", "siedemdziesiąty", "osiemdziesiąty", "dziewięćdziesiąty"
    };

    private static /*final*/ String[] G_ORDINAL_BIG_TENS =
    {
        "dziesiącio", "dwudziesto", "trzydziesto", "czterdziesto", "pięćdziesiącio", "sześćdziesiącio", "siedemdziesiącio", "osiemdziesiącio", "dziewięćdziesiącio"
    };

    private static /*final*/ String[] G_ORDINAL_BIGS =
    {
        "jedno", "dwu", "trzy", "cztero", "piącio", "szeącio", "siedmio", "óąmio", "dziewiącio", "dziesiącio",
        "jedenasto", "dwunasto", "trzynasto", "czternasto", "piętnasto", "szesnasto", "siedemnasto", "osiemnasto", "dziewiętnasto"
    };
}

