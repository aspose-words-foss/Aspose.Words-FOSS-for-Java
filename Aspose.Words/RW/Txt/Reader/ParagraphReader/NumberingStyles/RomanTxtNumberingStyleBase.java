// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/05/2021 by Alexey Maslov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;
import com.aspose.ms.System.msArray;
import com.aspose.ms.System.msString;
import com.aspose.Debug;
import java.util.HashMap;
import com.aspose.ms.System.Text.msStringBuilder;


class RomanTxtNumberingStyleBase extends TxtNumberingStyle
{
    RomanTxtNumberingStyleBase(char[] letterSet, char separator, /*NumberStyle*/int numberStyle)
    {
    	super(false, false, numberStyle);
	
        if (letterSet == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "letterSet");

        mLetterSet = letterSet;
        mSeparator = separator;
        mNumberStyle = numberStyle;

        switch (numberStyle)
        {
            case NumberStyle.LOWERCASE_ROMAN:
                mRegex = new Regex("(?=[mdclxvi])m*(c[md]|d?c{0,3})(x[cl]|l?x{0,3})(i[xv]|v?i{0,3})", RegexOptions.COMPILED);
                break;
            case NumberStyle.UPPERCASE_ROMAN:
                mRegex = new Regex("(?=[MDCLXVI])M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})", RegexOptions.COMPILED);
                break;
            default:
                throw new IllegalArgumentException("numberStyle");
        }
    }

    /*override*/ TxtNumberingInfo detectNumbering(String text)
    {
        if (text.length() == 0)
            return null;

        int index;

        StringBuilder sb = new StringBuilder();
        for (index = 0; index < text.length(); index++)
        {
            char c = text.charAt(index);
            if (c == mSeparator)
                return getNumberingInfo(text, index, sb.toString());

            if (msArray.indexOf((mLetterSet), (c), 0, (mLetterSet).length) == -1)
                return null;

            sb.append(c);
        }

        return null;
    }

    private TxtNumberingInfo getNumberingInfo(String text, int separatorIndex, String detectedNumber)
    {
        if (!isValidRomanNumber(detectedNumber))
            return null;

        String textNumbering = text.substring((0), (0) + (separatorIndex + 1));

        return new TxtNumberingInfo(detectedNumber, msString.trim(textNumbering));
    }

    /*override*/ String getNextNumber(String prevNumber)
    {
        return isValidRomanNumber(prevNumber) ? getNextRomanNumber(prevNumber) : "";
    }

    /*override*/ String getNumberFormat(int level)
    {
        Debug.assertTrue(level == 0, "Only level 0 is allowed!");
        return "\u0000" + mSeparator;
    }

    /*override*/ boolean isStartNumber(String value)
    {
        return (value.length() != 0) && (value.charAt(0) == mLetterSet[0]);
    }

    private boolean isValidRomanNumber(String textBuffer)
    {
        return mRegex.isMatch(textBuffer);
    }

    private String getNextRomanNumber(String prevNumber)
    {
        return convertArabicToRoman(convertRomanToArabic(prevNumber) + 1);
    }

    private static int convertRomanToArabic(String roman)
    {
        HashMap<Character, Integer> RomanDictionary
            = new HashMap<Character, Integer>();{
                                             RomanDictionary.put('I', 1);
                                             RomanDictionary.put('V', 5);
                                             RomanDictionary.put('X', 10);
                                             RomanDictionary.put('L', 50);
                                             RomanDictionary.put('C', 100);
                                             RomanDictionary.put('D', 500);
                                             RomanDictionary.put('M', 1000);
                                        }

        roman = roman.toUpperCase();

        int total = 0;
        int minus = 0;

        for (int i = 0; i < roman.length(); i++)
        {
            int thisNumeral = RomanDictionary.get(roman.charAt(i)) - minus;
            
            if (i >= roman.length() - 1 || thisNumeral + minus >= RomanDictionary.get(roman.charAt(i + 1)))
            {
                total += thisNumeral;
                minus = 0;
            }
            else
            {
                minus = thisNumeral;
            }
        }

        return total;
    }

    private String convertArabicToRoman(int arabic)
    {
        int[] arabicNums = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
        String[] romanNums = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < arabicNums.length && arabic != 0; i++)
        {
            while (arabic >= arabicNums[i])
            {
                arabic -= arabicNums[i];
                msStringBuilder.append(stringBuilder, romanNums[i]);
            }
        }

        return mNumberStyle == NumberStyle.UPPERCASE_ROMAN ? stringBuilder.toString() : stringBuilder.toString().toLowerCase();
    }

    private /*final*/ char[] mLetterSet;
    private /*final*/ char mSeparator;
    private /*final*/ Regex mRegex;
    private /*final*/ /*NumberStyle*/int mNumberStyle;
}

