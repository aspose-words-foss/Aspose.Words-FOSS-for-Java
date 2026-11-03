// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/05/2013 by Denis Darkin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
///  Abstracts ordinal and cardinal textual number representation in European languages.
/// </summary>
abstract class EuropeanTextualNumberProvider
{
    /*virtual*/ void appendHundred(StringBuilder builder, int value, int hundreds, boolean isCardinal, boolean hasHigherDigit, boolean hasLowerDigit, boolean isCardinalOriginal, ArrayList<Integer> thousands,
        int thousandIndex)
    {
        if (value < 20)
        {
            // WORDSNET-4984 Index out of range when converting 100.
            if (value > 0)
            {
                appendHundredFirst19(builder, value, hundreds, isCardinal, hasHigherDigit, hasLowerDigit, isCardinalOriginal, thousands, thousandIndex);
            }
        }
        else // 20-99
        {
            int tens = value / 10;
            value -= 10 * tens;

            if (value == 0) // 20, 30, 40, ..., 90
            {
                appendHundredTen(builder, tens, hundreds, isCardinal, hasHigherDigit, hasLowerDigit, isCardinalOriginal, thousands, thousandIndex);
            }
            else
            {
                appendHundredOther(builder, tens, value, hundreds, isCardinal, hasHigherDigit, hasLowerDigit, isCardinalOriginal, thousands, thousandIndex);
            }
        }
    }

    protected /*virtual*/ void appendHundredFirst19(StringBuilder builder, int value, int hundreds, boolean isCardinal, boolean hasHigherDigit, boolean hasLowerDigit, boolean isCardinalOriginal,
        ArrayList<Integer> thousands,
        int thousandIndex)
    {
        boolean requireSpecialSeparator = requireHundredSpecialSeparator(isCardinal, hasHigherDigit);

        // German cardinal numerals have "eins" for one, but for any 1-containing number it is "ein", not "eins"
        String word = ((value == 1) && isGerman() && isCardinal && !hasLowerDigit) ? "eins" : getNumeralFirst19(value, isCardinal);

        appendWord(builder, word, true, requireSpecialSeparator ? getSeparatorTensOnes() : getSeparator());
    }

    protected /*virtual*/ void appendHundredTen(StringBuilder builder, int tens, int hundreds, boolean isCardinal, boolean hasHigherDigit, boolean hasLowerDigit, boolean isCardinalOriginal, ArrayList<Integer> thousands,
        int thousandIndex)
    {
        appendWord(builder, getNumeralTen(tens, isCardinal), true);
    }

    protected /*virtual*/ void appendHundredOther(StringBuilder builder, int tens, int value, int hundreds, boolean isCardinal, boolean hasHigherDigit, boolean hasLowerDigit, boolean isCardinalOriginal,
        ArrayList<Integer> thousands, int thousandIndex)
    {
        boolean requireSpecialSeparator = requireHundredSpecialSeparator(isCardinal, hasHigherDigit);

        String separator = (isCardinal || isGerman()) ? getSeparatorTensOnes() : getSeparator(); // German ordinals have Separator between Tens and Ones too.
        String word = !isGerman()
            ? getNumeralTen(tens, isCardinal) + separator + getNumeralFirst19(value, isCardinal)
            : getNumeralFirst19(value, true) + separator + getNumeralTen(tens, isCardinal);

        appendWord(builder, word, true, requireSpecialSeparator ? getSeparatorHundredsTens() : getSeparator());
    }

    private boolean requireHundredSpecialSeparator(boolean isCardinal, boolean hasHigherDigit)
    {
        // in German there are no separators between digits (exept in below 99)
        return hasHigherDigit && isCardinal && !isGerman();
    }

    abstract void appendHundreds(StringBuilder builder, int hundreds, boolean isCardinal, boolean isNonZeroHundreds, boolean hasHigherDigits, boolean hasLowerDigits, boolean isCardinalOriginal,
        ArrayList<Integer> thousands, int thousandIndex);

    /*virtual*/ void appendThousandsOrdinalSuffix(StringBuilder builder)
    {
        msStringBuilder.append(builder, getThousandsOrdinalSuffix());
    }

    /// <summary>
    /// In certain languages numbers are prepended (e.g. German) so we will have to
    /// delegate Appending procedure to concrete classes.
    /// </summary>
    /// <remarks>
    /// Default implementation appends a space (' ') and then a word to a given StringBuilder.
    /// </remarks>
    void appendWord(StringBuilder builder, String word, boolean useSeparator)
    {
        appendWord(builder, word, useSeparator, getSeparator());
    }

    protected static void appendWord(StringBuilder builder, String word, boolean useSeparator, String separator)
    {
        if (useSeparator && builder.length() != 0)
            msStringBuilder.append(builder, separator);
        msStringBuilder.append(builder, word);
    }

    protected String getNumeralFirst19(int n, boolean isCardinal)
    {
        int index = n - 1;
        return isCardinal ? getCardinalFirst19()[index] : getOrdinalFirst19()[index];
    }

    protected String getNumeralTen(int tens, boolean isCardinal)
    {
        int index = tens - 2;
        return isCardinal ? getCardinalTens()[index] : getOrdinalTens()[index];
    }

    /// <summary>
    /// Specifies if leading digit of ordinal big numbers should be merged.
    /// For English it is false: 1000 is written as one thousandth.
    /// For Spanish it is true: 1000 is written as onethousandth
    /// </summary>
    /// <param name="thousand">Number of thousands. Example 1000 - 1, 23000 - 23</param>
    protected /*virtual*/ boolean mergeOrdinalThousands(int thousand)
    {
        return false;
    }

    /// <summary>
    /// Specifies using separators for ordinal or cardinal big numbers.
    /// </summary>
    /// <param name="isCardinal"></param>
    /// <param name="thousand"></param>
    /// <param name="thousands"></param>
    /// <param name="thousandIndex"></param>
    /// <returns></returns>
    /*virtual*/ boolean useThousandSeparator(boolean isCardinal, int thousand, ArrayList<Integer> thousands, int thousandIndex)
    {
        return isCardinal || !mergeOrdinalThousands(thousand);
    }

    /// <summary>
    /// Specifies suffix to add to thousands in ordinal enumeration.
    /// E.g. for English it is "th" to get "thousandth" from 10000.
    /// </summary>
    protected abstract String getThousandsOrdinalSuffix();

    /// <summary>
    /// For some languages e.g. English 1000 is written like one thousand.
    /// But in others, e.g. Spanish, writing "one" is excessive.
    /// This property specifies this behavior for each concrete language.
    /// </summary>
    abstract boolean getIncludeOneInBigNumbers();

    /// <summary>
    /// German numbers between 21 and 99 that are not multiples of ten (30, 40, 50 ...) are expressed in reverse:
    /// one-and-twenty, two-and-twenty, etc.
    /// Note that these are the same numbers that are combined with a hyphen when written out in English.
    /// </summary>
    protected /*virtual*/ boolean isGerman() { return false; }

    /// <summary>
    /// Default separator between textualized numbers.
    /// </summary>
    protected /*virtual*/ String getSeparator() { return " "; }

    /// <summary>
    /// Separator between first and second digit: e.g. between 8 and 5 in number 1985.
    /// in certain languages it is "-" e.g. English, but in some - analog of " and "
    /// </summary>
    protected /*virtual*/ String getSeparatorTensOnes() { return "-"; }

    /// <summary>
    /// Separator between second and third digit: e.g. between 9 and 8 in number 1985.
    /// in certain languages it is " ", but in some an analog of " and "
    /// </summary>
    protected /*virtual*/ String getSeparatorHundredsTens() { return " "; }

    /// <summary>
    /// Returns a boolean value indicating either to include numeral for thousands word.
    /// E.g. For 1000: "one thousand" instead of "thousand".
    /// </summary>
    /*virtual*/ boolean includeThousandsWord(long value)
    {
        return true;
    }

    /// <summary>
    /// Indicates whether the first letter is always uppercase.
    /// </summary>
    /*virtual*/ boolean isFirstLetterAlwaysUppercase() { return false; }

    abstract String getAnd();

    abstract String zero(boolean isCardinal);

    abstract String getBigNumeral(int thousandIndex, boolean isCardinal, int thousand, ArrayList<Integer> thousands);

    /*virtual*/ String getNumberPrefix(long value, boolean isCardinal)
    {
        return "";
    }

    /*virtual*/ String getNumberSuffix(long value, boolean isCardinal)
    {
        return "";
    }

    protected abstract String[] getOrdinalFirst19();
    protected abstract String[] getCardinalFirst19();
    protected abstract String[] getOrdinalTens();
    protected abstract String[] getCardinalTens();

    protected static boolean isLastNonZeroThousand(ArrayList<Integer> thousands, int thousandIndex)
    {
        for (int i = 0; i < thousandIndex; i++)
            if (thousands.get(i) != 0)
                return false;

        return true;
    }
}

