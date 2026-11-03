// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/05/2012 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;


/// <summary>
/// Information about the numbering: list of numbers and text.
/// </summary>
class TxtNumberingInfo
{
    TxtNumberingInfo(String[] numbers, String text)
    {
        if (numbers == null) 
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "numbers");
        Debug.assertTrue(StringUtil.hasChars(text));

        mNumbers = numbers;
        mText = text;
    }

    TxtNumberingInfo(String number, String text)
    {
        Debug.assertTrue(StringUtil.hasChars(number));
        Debug.assertTrue(StringUtil.hasChars(text));

        mNumbers = new String[1];
        mNumbers[0] = number;
        mText = text;
    }

    /// <summary>
    /// Array of numbers
    /// </summary>
    String[] getNumbers() { return mNumbers; }

    /// <summary>
    /// Text of numbering part
    /// </summary>
    String getText() { return mText; }

    private /*final*/ String[] mNumbers;
    private /*final*/ String mText;
}

