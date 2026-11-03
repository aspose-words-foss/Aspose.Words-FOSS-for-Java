// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2016 by Edward Voronov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


/// <summary>
/// Ordinal/cardinal textual representation for Czech language.
/// </summary>
class CzechTextualNumber extends EuropeanTextualNumberProvider
{
    protected /*override*/ void appendHundredFirst19(StringBuilder builder, int value, int hundreds, boolean isCardinal,
        boolean hasHigherDigit, boolean hasLowerDigit, boolean isCardinalOriginal,
        ArrayList<Integer> thousands, int thousandIndex)
    {
        boolean useSeparator = useHundredSeparator(isCardinalOriginal, hundreds, hasLowerDigit);
        String word = getNumeralFirst19(value, isCardinalOriginal);
        appendWord(builder, word, true, useSeparator ? getSeparator() : "");
    }

    protected /*override*/ void appendHundredTen(StringBuilder builder, int tens, int hundreds, boolean isCardinal,
        boolean hasHigherDigit, boolean hasLowerDigit, boolean isCardinalOriginal, ArrayList<Integer> thousands,
        int thousandIndex)
    {
        boolean useSeparator = useHundredSeparator(isCardinalOriginal, hundreds, hasLowerDigit);
        appendWord(builder, getNumeralTen(tens, isCardinalOriginal), useSeparator);
    }

    protected /*override*/ void appendHundredOther(StringBuilder builder, int tens, int value, int hundreds, boolean isCardinal,
        boolean hasHigherDigit, boolean hasLowerDigit, boolean isCardinalOriginal,
        ArrayList<Integer> thousands,
        int thousandIndex)
    {
        boolean useSeparator = useHundredSeparator(isCardinalOriginal, hundreds, hasLowerDigit);
        String tensSeparator = isCardinalOriginal || !hasLowerDigit || ((tens != 7) && (tens != 8))
                    ? getSeparator()
                    : "";
        String word = getNumeralTen(tens, isCardinalOriginal) + tensSeparator + getNumeralFirst19(value, isCardinalOriginal);
        appendWord(builder, word, true, useSeparator ? getSeparator() : "");
    }

    private static boolean useHundredSeparator(boolean isCardinalOriginal, int hundreds, boolean hasLowerDigit)
    {
        return !isCardinalOriginal || (hundreds == 0) || !hasLowerDigit;
    }

    /*override*/ void appendHundreds(StringBuilder builder, int hundreds, boolean isCardinal, boolean isNonZeroHundreds,
        boolean hasHigherDigits, boolean hasLowerDigits, boolean isCardinalOriginal,
        ArrayList<Integer> thousands, int thousandIndex)
    {
        if ((hundreds != 1) || (isCardinalOriginal && hasLowerDigits))
            appendWord(builder, getHundredsNumeral(hundreds, isCardinalOriginal, hasLowerDigits), true);

        boolean useSeparator = isCardinalOriginal
            ? !hasLowerDigits
            : hundreds == 1;

        appendWord(builder, hundred(hundreds, isCardinalOriginal, hasLowerDigits), useSeparator);
    }

    private static String hundred(int hundreds, boolean isCardinal, boolean hasLowerDigits)
    {
        if (isCardinal)
        {
            if (hasLowerDigits)
                return "sto";

            switch (hundreds)
            {
                case 1:
                    return "sto";
                case 2:
                    return "stě";
                case 3:
                case 4:
                    return "sta";
                default:
                    return "set";
            }
        }

        return "stý";
    }

    private String getHundredsNumeral(int hundreds, boolean isCardinal, boolean hasLowerDigits)
    {
        if (!isCardinal)
            return G_ORDINAL_HUNDREDS[hundreds - 1];

        if (!hasLowerDigits && (hundreds == 2))
            return "dvě";

        return getNumeralFirst19(hundreds, true);
    }

    /*override*/ boolean useThousandSeparator(boolean isCardinal, int thousand, ArrayList<Integer> thousands, int thousandIndex)
    {
        if (!super.useThousandSeparator(isCardinal, thousand, thousands, thousandIndex))
            return false;

        if (isCardinal)
            return (thousand % 100 != 0);

        switch (thousand % 100)
        {
            case 10:
            case 70:
            case 80:
                return false;
            default:
                return true;
        }
    }

    protected /*override*/ String getThousandsOrdinalSuffix() { return ""; }

    /*override*/ boolean getIncludeOneInBigNumbers() { return false; }

    /*override*/ String getAnd() { return "a"; }

    /*override*/ String zero(boolean isCardinal)
    {
        return isCardinal ? "nula" : "nultý";
    }

    /*override*/ String getBigNumeral(int thousandIndex, boolean isCardinal, int thousand, ArrayList<Integer> thousands)
    {
        switch (thousandIndex)
        {
            case 1:
                if (!isCardinal)
                    return "tisící";

                switch (thousand)
                {
                    case 200:
                    case 300:
                    case 400:
                        return "tisíce";
                    default:
                        // Ignore.
                        break;
                }

                int hundred = thousand % 100;
                switch (thousand % 10)
                {
                    case 2:
                    case 3:
                    case 4:
                        if ((hundred < 10) || (hundred > 20))
                            return "tisíce";
                        break;
                    default:
                        // Ignore.
                        break;
                }

                return "tisíc";
            case 2:
                return "miliónů";
            case 3:
                return "miliarda";
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
        "jedna", "dva", "tři", "čtyři", "pět", "šest", "sedm", "osm", "devět", "deset",
        "jedenáct", "dvanáct", "třináct", "čtrnáct", "patnáct", "šestnáct", "sedmnáct", "osmnáct", "devatenáct"
    };

    private static /*final*/ String[] G_ORDINAL_FIRST_19 =
    {
        "první", "druhý", "třetí", "čtvrtý", "pátý", "šestý", "sedmý", "osmý", "devátý", "desátý",
        "jedenáctý", "dvanáctý", "třináctý", "čtrnáctý", "patnáctý", "šestnáctý", "sedmnáctý", "osmnáctý", "devatenáctý"
    };

    private static /*final*/ String[] G_ORDINAL_HUNDREDS =
    {
        "", "dvou", "tří", "čtyř", "pěti", "šesti", "sedmi", "osmi", "devíti"
    };

    private static /*final*/ String[] G_CARDINAL_TENS =
    {
        "dvacet", "třicet", "čtyřicet", "padesát", "šedesát", "sedmdesát", "osmdesát", "devadesát"
    };

    private static /*final*/ String[] G_ORDINAL_TENS =
    {
        "dvacátý", "třicátý", "čtyřicátý", "padesátý", "šedesátý", "sedmdesátý", "osmdesátý", "devadesátý"
    };
}

