// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/05/2012 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;
import com.aspose.FormatterPal;
import java.util.ArrayList;
import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.StringUtil;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.Convert;


/// <summary>
/// Arabic style numbering (1., 1.1.2.)
/// </summary>
class ArabicDotTxtNumberingStyle extends TxtNumberingStyle
{
    ArabicDotTxtNumberingStyle()
    {
    	super(false, true, NumberStyle.ARABIC);
	
        // WORDSNET-10612 We should take in account list items with entirely empty list label.
        // (?!.) was added to the regex pattern to check it.
        // WORDSNET-10993 To correctly recognize trailing '.' we need to find all digits, including leading '0'. So, in regex pattern ^[1-9]\d* was replaced with ^\d+
        // But as '0' can not start a list, we should check it additionally.
        mRegex = new Regex("^\\d+(\\.|\\s|(?!.))", RegexOptions.COMPILED);
    }

    /*override*/ boolean isStartNumber(String value)
    {
        return "1".equals(value);
    }

    /*override*/ String getNextNumber(String prevNumber)
    {
        int number = FormatterPal.tryParseInt(prevNumber);
        return number == Integer.MIN_VALUE
                   ? ""
                   : Integer.toString((number + 1));
    }

    /*override*/ TxtNumberingInfo detectNumbering(String text)
    {
        // Resilience for text, which is consists from a single integer number. For example see test file for WORDSNET-9958
        if (FormatterPal.isInteger(text))
            return null;

        if ((text.length() > 0) && text.charAt(0) == '0')
            return null;

        ArrayList<String> numberList = new ArrayList<String>();
        int length = 0;
        String textBuffer = text;
        char trailingListNumChar = '\u0000';
        while (true)
        {
            Match match = mRegex.match(textBuffer);
            if (!match.getSuccess())
                break;

            // WORDSNET-10612 If there is no '.' or whitespace after list number,
            // then whole 'match' is a value of list number part, otherwise we should strip last '.' or whitespace.
            trailingListNumChar = match.getValue().charAt(match.getValue().length() - 1);
            String value = StringUtil.isDigit(trailingListNumChar) ? match.getValue() : match.getValue().substring((0), (0) + (match.getValue().length()-1));
            msArrayList.add(numberList, value);

            length += match.getValue().length();
            textBuffer = msString.remove(textBuffer, 0, match.getLength());

            if (StringUtil.contains(match.getValue(), " ", true))
                break;
        }

        if (numberList.size() == 0)
            return null;

        // WORDSNET-10993 Don't make list item if we are not detecting numbering with whitespaces, but list number ends with whitespace.
        // Not allowed trailing whitespace is the same as required dot in our design.
        if (!getDetectNumberingWithWhitespaces() && (trailingListNumChar != '.'))
            return null;

        String[] numbers = new String[numberList.size()];
        for (int i = 0; i < numberList.size(); i++)
            numbers[i] = numberList.get(i);
        return new TxtNumberingInfo(numbers, msString.trim(text.substring((0), (0) + (length))));
    }

    /*override*/ String getNumberFormat(int level)
    {
        String format = "";
        for (int i = 0; i <= level; i++)
            format = msString.plusEqOperator(format, Convert.toChar(i) + ".");
        return format;
    }

    private /*final*/ Regex mRegex;
}

