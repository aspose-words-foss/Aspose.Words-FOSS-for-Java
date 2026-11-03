// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/05/2013 by Denis Darkin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


/// <summary>
/// Ordinal/cardinal textual representation for Spanish language.
/// </summary>
class SpanishTextualNumber extends EuropeanTextualNumberProvider
{
    /*override*/ void appendHundreds(StringBuilder builder, int hundreds, boolean isCardinal, boolean isNonZeroHundreds,
        boolean hasHigherDigits, boolean hasLowerDigits, boolean isCardinalOriginal,
        ArrayList<Integer> thousands, int thousandIndex)
    {
        Debug.assertTrue((hundreds >= 1) && (hundreds <= 9));
        appendWord(builder, isCardinal ? G_CARDINAL_HUNDREDS[hundreds - 1] : G_ORDINAL_HUNDREDS[hundreds - 1], true);
    }

    protected /*override*/ boolean mergeOrdinalThousands(int thousand)
    {
        return true;
    }

    protected /*override*/ String getThousandsOrdinalSuffix() { return "ésimo"; }

    /*override*/ boolean getIncludeOneInBigNumbers() { return false; }

    /*override*/ void appendHundred(StringBuilder builder, int value, int hundreds, boolean isCardinal,
        boolean hasHigherDigit, boolean hasLowerDigit, boolean isCardinalOriginal, ArrayList<Integer> thousands,
        int thousandIndex)
    {
        if (value < 20)
        {
            // WORDSNET-4984 Index out of range when converting 100.
            if (value > 0)
                appendWord(builder, getNumeralFirst19(value, isCardinal), true);
        }
        else if ((value > 20) && (value < 30))
        {
            int lastDigit = value - 20;
            if (isCardinal)
            {
                appendWord(builder, G_CARDINAL_TWENTIES[lastDigit - 1], true);
            }
            else
            {
                appendWord(builder, getNumeralTen(2, false) + getSeparator() + getNumeralFirst19(lastDigit, false), true);
            }

        }
        else // 20, 30-99
        {
            int tens = value / 10;
            value -= 10 * tens;

            if (value == 0)
            {
                // 20, 30, 40, ..., 90
                appendWord(builder, getNumeralTen(tens, isCardinal), true);
            }
            else
            {
                appendWord(builder,
                    getNumeralTen(tens, isCardinal) +
                    ((isCardinal) ? getSeparatorTensOnes() : getSeparator()) +
                    getNumeralFirst19(value, isCardinal), true);
            }
        }
    }

    protected /*override*/ String getSeparatorTensOnes() { return " y "; }

    /*override*/ String getAnd() { return "con"; }

    /*override*/ String zero(boolean isCardinal)
    {
        return "cero";
    }

    protected /*override*/ String[] getCardinalFirst19() { return G_CARDINAL_FIRST_19; }

    protected /*override*/ String[] getCardinalTens() { return G_CARDINAL_TENS; }

    /*override*/ String getBigNumeral(int thousandIndex, boolean isCardinal, int thousand, ArrayList<Integer> thousands)
    {
        return G_BIG_NUMERALS[thousandIndex - 1];
    }

    protected /*override*/ String[] getOrdinalFirst19() { return G_ORDINAL_FIRST_19; }

    protected /*override*/ String[] getOrdinalTens() { return G_ORDINAL_TENS; }

    private static /*final*/ String[] G_CARDINAL_FIRST_19 =
    {
        "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve", "diez",
        "once", "doce", "trece", "catorce", "quince", "dieciséis", "diecisiete", "dieciocho", "diecinueve"
    };

    private static /*final*/ String[] G_ORDINAL_FIRST_19 =
    {
        "primero", "segundo", "tercero", "cuarto", "quinto", "sexto", "séptimo", "octavo", "noveno", "décimo", "undécimo",
        "duodécimo", "decimotercero", "decimocuarto", "decimoquinto", "decimosexto", "decimoséptimo", "decimoctavo", "decimonoveno"
    };

    private static /*final*/ String[] G_CARDINAL_TENS =
    {
        "veinte", "treinta", "cuarenta", "cincuenta", "sesenta", "setenta", "ochenta", "noventa"
    };

    private static /*final*/ String[] G_CARDINAL_TWENTIES =
    {
        "veintiuno", "veintidós", "veintitrés", "veinticuatro", "veinticinco", "veintiséis", "veintisiete", "veintiocho", "veintinueve"
    };

    private static /*final*/ String[] G_ORDINAL_TENS =
    {
        "vigésimo", "trigésimo", "cuadragésimo", "quincuagésimo", "sexagésimo", "septuagésimo", "octogésimo", "nonagésimo"
    };

    private static /*final*/ String[] G_CARDINAL_HUNDREDS =
    {
        "ciento", "doscientos", "trescientos", "cuatrocientos", "quinientos", "seiscientos", "setecientos", "ochocientos", "novecientos"
    };

    private static /*final*/ String[] G_ORDINAL_HUNDREDS =
    {
        "centésimo", "ducentésimo", "tricentésimo", "cuadringentésimo", "quingentésimo", "sexcentésimo", "septingentésimo", "octingésimo", "noningentésimo"
    };

    private static /*final*/ String[] G_BIG_NUMERALS = { "mil", "millón", "mil millones" };
}

