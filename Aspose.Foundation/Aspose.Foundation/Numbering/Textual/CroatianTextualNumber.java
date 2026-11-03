// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2016 by Edward Voronov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


/// <summary>
/// Ordinal/cardinal textual representation for Croatian language.
/// </summary>
class CroatianTextualNumber extends EuropeanTextualNumberProvider
{
    protected /*override*/ void appendHundredFirst19(StringBuilder builder, int value, int hundreds, boolean isCardinal,
        boolean hasHigherDigit, boolean hasLowerDigit, boolean isCardinalOriginal,
        ArrayList<Integer> thousands, int thousandIndex)
    {
        String word = getNumeralFirst19(value, isCardinal, thousandIndex);
        appendWord(builder, word, true, getSeparator());
    }

    protected /*override*/ void appendHundredTen(StringBuilder builder, int tens, int hundreds, boolean isCardinal,
        boolean hasHigherDigit, boolean hasLowerDigit, boolean isCardinalOriginal, ArrayList<Integer> thousands,
        int thousandIndex)
    {
        appendWord(builder, getNumeralTen(tens, isCardinal, isCardinalOriginal), true);
    }

    protected /*override*/ void appendHundredOther(StringBuilder builder, int tens, int value, int hundreds, boolean isCardinal,
        boolean hasHigherDigit, boolean hasLowerDigit, boolean isCardinalOriginal,
        ArrayList<Integer> thousands,
        int thousandIndex)
    {
        String word = getNumeralTen(tens, isCardinal, isCardinalOriginal) + getSeparator() + getNumeralFirst19(value, isCardinal);
        appendWord(builder, word, true, getSeparator());
    }

    private String getNumeralFirst19(int value, boolean isCardinal, int thousandIndex)
    {
        if (isCardinal && (thousandIndex != 0))
        {
            switch (value)
            {
                case 1:
                    return "jedna";
                case 2:
                    return "dvije";
                default:
                    // Ignore.
                    break;
            }
        }

        return getNumeralFirst19(value, isCardinal);
    }

    private String getNumeralTen(int value, boolean isCardinal, boolean isCardinalOriginal)
    {
        if (isCardinal && !isCardinalOriginal && (value == 3))
            return "tridest";

        return getNumeralTen(value, isCardinal);
    }

    /*override*/ void appendHundreds(StringBuilder builder, int hundreds, boolean isCardinal, boolean isNonZeroHundreds,
        boolean hasHigherDigits, boolean hasLowerDigits, boolean isCardinalOriginal,
        ArrayList<Integer> thousands, int thousandIndex)
    {
        if (hundreds != 1)
            appendWord(builder, getHundredsNumeral(hundreds, isCardinal, isNonZeroHundreds), true);

        appendWord(builder, hundred(isCardinal, isNonZeroHundreds), hundreds == 1);
    }

    private String getHundredsNumeral(int hundreds, boolean isCardinal, boolean isNonZeroHundreds)
    {
        switch (hundreds)
        {
            case 2:
                return "dvje";
            case 6:
                if (isCardinal || isNonZeroHundreds)
                    return "še";
                break;
            default:
                // Ignore.
                break;
        }

        return getNumeralFirst19(hundreds, true);
    }

    private static String hundred(boolean isCardinal, boolean isNonZeroHundreds)
    {
        if (!isCardinal && !isNonZeroHundreds)
            return "stoti";

        return "sto";
    }

    protected /*override*/ String getThousandsOrdinalSuffix() { return ""; }

    /*override*/ boolean getIncludeOneInBigNumbers() { return false; }

    protected /*override*/ String getSeparatorTensOnes() { return " "; }

    /*override*/ String getAnd() { return "i"; }

    /*override*/ String zero(boolean isCardinal)
    {
        return isCardinal ? "nula" : "nulti";
    }

    /*override*/ String getBigNumeral(int thousandIndex, boolean isCardinal, int thousand, ArrayList<Integer> thousands)
    {
        switch (thousandIndex)
        {
            case 1:
                if (!isCardinal && isLastNonZeroThousand(thousands, thousandIndex))
                    return "tisućiti";

                int value = -1;

                int hundred = thousand % 100;
                if (hundred == 0)
                    value = thousand / 100;
                else if ((hundred < 10) || (hundred >= 20))
                    value = thousand % 10;

                switch (value)
                {
                    case 0:
                    case 1:
                        return "tisuću";
                    case 2:
                    case 3:
                    case 4:
                        return "tisuće";
                    default:
                        return "tisuća";
                }
            case 2:
                return "milijun";
            case 3:
                return "milijarda";
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "thousandIndex");
        }
    }

    protected /*override*/ String[] getOrdinalFirst19() { return G_ORDINAL_FIRST_19; }

    protected /*override*/ String[] getCardinalFirst19() { return G_CARDINAL_FIRST_19; }

    protected /*override*/ String[] getOrdinalTens() { return G_ORDINAL_TENS; }

    protected /*override*/ String[] getCardinalTens() { return G_CARDINAL_TENS; }

    private static /*final*/ String[] G_CARDINAL_FIRST_19 =
    {
        "jedan", "dva", "tri", "četiri", "pet", "šest", "sedam", "osam", "devet", "deset",
        "jedanaest", "dvanaest", "trinaest", "četrnaest", "petnaest", "šesnaest", "sedamnaest", "osamnaest", "devetnaest"
    };

    private static /*final*/ String[] G_ORDINAL_FIRST_19 =
    {
        "prvi", "drugi", "treći", "četvrti", "peti", "šesti", "sedmi", "osmi", "deveti", "deseti",
        "jedanaesti", "dvanaesti", "trinaesti", "četrnaesti", "petnaesti", "šesnaesti", "sedamnaesti", "osamnaesti", "devetnaesti"
    };

    private static /*final*/ String[] G_CARDINAL_TENS =
    {
        "dvadeset", "trideset", "četrdeset", "pedeset", "šezdeset", "sedamdeset", "osamdeset", "devedeset"
    };

    private static /*final*/ String[] G_ORDINAL_TENS =
    {
        "dvadeseti", "trideseti", "četrdeseti", "pedeseti", "šezdeseti", "sedamdeseti", "osamdeseti", "devedeseti"
    };
}

