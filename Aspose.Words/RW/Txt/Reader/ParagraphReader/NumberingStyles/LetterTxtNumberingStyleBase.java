// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/05/2012 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Convert;
import com.aspose.Debug;


/// <summary>
/// Base class for letter styles
/// </summary>
class LetterTxtNumberingStyleBase extends TxtNumberingStyle
{
    LetterTxtNumberingStyleBase(char[] letterSet, char separator, /*NumberStyle*/int numberStyle)
    {
    	super(false, false, numberStyle);
	
        if (letterSet == null) 
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "letterSet");

        mLetterSet = letterSet;
        mSeparator = separator;
    }

    /*override*/ boolean isStartNumber(String value)
    {
        return (value.length() != 0) && (value.charAt(0) == mLetterSet[0]);
    }

    /*override*/ String getNextNumber(String prevNumber)
    {
        int letterIndex = findLetterIndex(prevNumber);
        return ((letterIndex == -1) || (letterIndex == mLetterSet.length - 1))
            ? ""
            : Convert.toString(mLetterSet[letterIndex + 1]);
    }

    /*override*/ TxtNumberingInfo detectNumbering(String text)
    {
        if ((text.length() < 2) || ((text.charAt(1) != mSeparator)))
            return null;

        for (int i = 0; i < mLetterSet.length; i++)
        {
            if (text.charAt(0) == mLetterSet[i])
                return new TxtNumberingInfo(Convert.toString(mLetterSet[i]), text.substring((0), (0) + (2)));
        }

        return null;
    }

    /*override*/ String getNumberFormat(int level)
    {
        Debug.assertTrue(level == 0, "Only level 0 is allowed!");
        return "\u0000" + mSeparator;
    }

    private int findLetterIndex(String number)
    {
        if (number.length() != 1)
            return -1;

        for (int i = 0; i < mLetterSet.length; i++)
        {
            if (mLetterSet[i] == number.charAt(0))
                return i;
        }

        return -1;
    }

    private /*final*/ char[] mLetterSet;
    private /*final*/ char mSeparator;
}

