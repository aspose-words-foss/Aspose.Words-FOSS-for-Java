// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/08/2022 by Edward Voronov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.msArray;
import com.aspose.ms.System.msString;


/// <summary>
/// Ordinal/cardinal textual representation for Italian language.
/// </summary>
class ItalianTextualNumber extends EuropeanTextualNumberProvider
{
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
        if (hundreds != 1)
            appendWord(builder, getNumeralFirst19(hundreds, true), false);

        appendWord(builder, hundred(thousands, thousandIndex, isCardinal), false);
    }

    private static String hundred(ArrayList<Integer> thousands, int thousandIndex, boolean isCardinal)
    {
        int thousand = thousands.get(thousandIndex);
        int hundred = thousand % 100;
        int tens = hundred / 10;

        if (isCardinal)
            return "cento";

        if ((tens == 8) || (hundred <= 1) || (hundred == 8))
            return "cent";

        return "cento";
    }

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
        if (!isCardinal && tryAppendOrdinalHundred(builder, value, hundreds, thousands))
            return;

        super.appendHundred(builder, value, hundreds, true, hasHigherDigit, hasLowerDigit, isCardinalOriginal, thousands, thousandIndex);

        if (isCardinal)
            return;

        trimTrailingVowel(builder);
        removeTrailingAcuteAccent(builder);
    }

    private boolean tryAppendOrdinalHundred(StringBuilder builder, int value, int hundreds, ArrayList<Integer> thousands)
    {
        if ((thousands.size() == 1) && (hundreds == 0) && (value < 10))
        {
            appendWord(builder, G_ORDINAL_FIRST_9[value - 1], false);
            return true;
        }

        if (value == 10)
        {
            appendWord(builder, "decimo", false);
            return true;
        }

        return false;
    }

    private static void trimTrailingVowel(StringBuilder builder)
    {
        if (!isVowel(builder.charAt(builder.length() - 1)))
            return;

        if (isVowel(builder.charAt(builder.length() - 2)))
            return;

        builder.setLength(builder.length() - 1);
    }

    private static boolean isVowel(char c)
    {
        return msArray.indexOf((G_VOWELS), (c), 0, (G_VOWELS).length) != -1;
    }

    private static void removeTrailingAcuteAccent(StringBuilder builder)
    {
        if (builder.charAt(builder.length() - 1) == 'é')
            builder.setCharAt(builder.length() - 1, 'e');
    }

    /*override*/ String zero(boolean isCardinal)
    {
        return "zero";
    }

    /*override*/ String getBigNumeral(int thousandIndex, boolean isCardinal, int thousand, ArrayList<Integer> thousands)
    {
        switch (thousandIndex)
        {
            case 1:
                if (!isCardinal && (thousands.get(0) == 0))
                    return "mill";

                return thousand == 1
                    ? "mille"
                    : "mila";
            case 2:
                return isCardinal ? "milione" : "milion";
            case 3:
                return isCardinal ? "miliardo" : "miliard";
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "thousandIndex");
        }
    }

    protected /*override*/ void appendHundredFirst19(
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
        Debug.assertTrue(isCardinal);

        if (!hasLowerDigit && (value == 3) && ((hundreds != 0) || hasHigherDigit))
        {
            appendWord(builder, THREE_WITH_ACUTE_ACCENT, false);
            return;
        }

        super.appendHundredFirst19(builder, value, hundreds, true, hasHigherDigit, hasLowerDigit, isCardinalOriginal, thousands, thousandIndex);
    }

    protected /*override*/ void appendHundredOther(
        StringBuilder builder,
        int tens,
        int value,
        int hundreds,
        boolean isCardinal,
        boolean hasHigherDigit,
        boolean hasLowerDigit,
        boolean isCardinalOriginal,
        ArrayList<Integer> thousands,
        int thousandIndex)
    {
        Debug.assertTrue(isCardinal);

        if ((value == 1) || (value == 8))
        {
            String word = msString.trimEnd(getNumeralTen(tens, true), G_VOWELS) + getNumeralFirst19(value, true);
            appendWord(builder, word, false);
            return;
        }

        if (!hasLowerDigit && (value == 3))
        {
            String word = getNumeralTen(tens, true) + THREE_WITH_ACUTE_ACCENT;
            appendWord(builder, word, false);
            return;
        }

        super.appendHundredOther(builder, tens, value, hundreds, true, hasHigherDigit, hasLowerDigit, isCardinalOriginal, thousands, thousandIndex);
    }

    /*override*/ String getNumberSuffix(long value, boolean isCardinal)
    {
        if (isCardinal)
            return "";

        if ((value <= 10) || (value % 100 == 10))
            return "";

        return "esimo";
    }

    protected /*override*/ String getThousandsOrdinalSuffix() { return ""; }

    /*override*/ boolean getIncludeOneInBigNumbers() { return false; }

    /*override*/ String getAnd() { return "e"; }

    protected /*override*/ String[] getOrdinalFirst19() { throw new IllegalStateException(); }

    protected /*override*/ String[] getCardinalFirst19() { return G_CARDINAL_FIRST_19; }

    protected /*override*/ String[] getOrdinalTens() { throw new IllegalStateException(); }

    protected /*override*/ String[] getCardinalTens() { return G_CARDINAL_TENS; }

    protected /*override*/ String getSeparatorTensOnes() { return ""; }

    protected /*override*/ String getSeparatorHundredsTens() { return ""; }

    protected /*override*/ String getSeparator() { return ""; }

    private static /*final*/ String[] G_CARDINAL_FIRST_19 =
    {
        "uno", "due", "tre", "quattro", "cinque", "sei", "sette", "otto", "nove", "dieci",
        "undici", "dodici", "tredici", "quattordici", "quindici", "sedici", "diciassette", "diciotto", "diciannove"
    };

    private static /*final*/ String[] G_CARDINAL_TENS =
    {
        "venti", "trenta", "quaranta", "cinquanta", "sessanta", "settanta", "ottanta", "novanta"
    };

    private static /*final*/ String[] G_ORDINAL_FIRST_9 =
    {
        "primo", "secondo", "terzo", "quarto", "quinto", "sesto", "settimo", "ottavo", "nono"
    };

    private static /*final*/ char[] G_VOWELS = { 'a', 'o', 'i', 'e' };

    private static final String THREE_WITH_ACUTE_ACCENT = "tré";
}

