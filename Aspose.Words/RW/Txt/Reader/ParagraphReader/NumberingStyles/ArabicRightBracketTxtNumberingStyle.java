// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/05/2012 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;
import com.aspose.FormatterPal;
import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.Debug;


/// <summary>
/// Arabic style like 1), 2)
/// </summary>
class ArabicRightBracketTxtNumberingStyle extends TxtNumberingStyle
{
    ArabicRightBracketTxtNumberingStyle()
    {
    	super(false, false, NumberStyle.ARABIC);
	
        mRegex = new Regex("^[1-9]\\d*\\)", RegexOptions.COMPILED);
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
        Match match = mRegex.match(text);
        if (!match.getSuccess())
            return null;

        String value = match.getValue().substring((0), (0) + (match.getValue().length() - 1));
        return new TxtNumberingInfo(new String[] {value}, match.getValue());
    }

    /*override*/ String getNumberFormat(int level)
    {
        Debug.assertTrue(level == 0, "Only level 0 is allowed!");
        return "\u0000)";
    }

    private /*final*/ Regex mRegex;
}
