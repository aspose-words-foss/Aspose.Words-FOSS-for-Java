// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/05/2013 by Denis Darkin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


/// <summary>
/// Ordinal/cardinal textual representation for Portuguese language.
/// </summary>
class PortugueseTextualNumber extends EuropeanTextualNumberProvider
{
    /*override*/ void appendHundreds(StringBuilder builder, int hundreds, boolean isCardinal, boolean isNonZeroHundreds,
        boolean hasHigherDigits, boolean hasLowerDigits, boolean isCardinalOriginal,
        ArrayList<Integer> thousands, int thousandIndex)
    {
        Debug.assertTrue((hundreds >= 1) && (hundreds <= 9));

        if ((hundreds == 1) && !isNonZeroHundreds && isCardinal)
        {
            appendWord(builder, "cem", true);
        }
        else
        {
            String cardinalNumber = G_CARDINAL_HUNDREDS[hundreds - 1];
            appendWord(builder, isCardinal ? cardinalNumber : G_ORDINAL_HUNDREDS[hundreds - 1], isCardinal);
        }
    }

    protected /*override*/ String getThousandsOrdinalSuffix() { return "ésimo"; }

    protected /*override*/ String getSeparatorTensOnes() { return " e "; }

    protected /*override*/ String getSeparatorHundredsTens() { return " e "; }

    /*override*/ boolean getIncludeOneInBigNumbers() { return false; }

    /*override*/ String getAnd() { return "e"; }

    /*override*/ String zero(boolean isCardinal)
    {
        return "zero";
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
        "um", "dois", "três", "quatro", "cinco", "seis", "sete", "oito", "nove", "dez",
        "onze", "doze", "treze", "catorze", "quinze", "dezasseis", "dezassete", "dezoito", "dezanove"
    };

    private static /*final*/ String[] G_ORDINAL_FIRST_19 =
    {
        "primeiro", "segundo", "terceiro", "quarto", "quinto", "sexto", "sétimo", "oitavo", "nono", "décimo", "décimo primeiro",
        "décimo segundo", "décimo terceiro", "décimo quarto", "décimo quinto", "décimo sexto", "décimo sétimo", "décimo oitavo", "décimo nono"
    };

    private static /*final*/ String[] G_CARDINAL_TENS =
    {
        "vinte", "trinta", "quarenta", "cinquenta", "sessenta", "setenta", "oitenta", "noventa"
    };

    private static /*final*/ String[] G_ORDINAL_TENS =
    {
        "vigésimo", "trigésimo", "quadragésimo", "quinquagésimo", "sexagésimo", "septuagésimo", "octagésimo", "nonagésimo"
    };

    private static /*final*/ String[] G_CARDINAL_HUNDREDS =
    {
        "cento", "duzentos", "trezentos", "quatrocentos", "quinhentos", "seiscentos", "setecentos", "oitocentos", "novecentos"
    };

    private static /*final*/ String[] G_ORDINAL_HUNDREDS =
    {
        "centésimo", "ducentésimo", "tricentésimo", "quadrigentésimo", "quingentésimo", "seiscentésimo", "septigentésimo", "octigentésimo", "nongentésimo"
    };

    private static /*final*/ String[] G_BIG_NUMERALS = { "mil", "milhão", "bilião" };
}

