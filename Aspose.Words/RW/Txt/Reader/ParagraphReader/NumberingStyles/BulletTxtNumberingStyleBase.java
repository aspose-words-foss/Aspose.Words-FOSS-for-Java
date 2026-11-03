// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/06/2012 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;
import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.ms.System.Convert;
import com.aspose.Debug;


/// <summary>
/// Base class for bullet styles
/// </summary>
abstract class BulletTxtNumberingStyleBase extends TxtNumberingStyle
{
    protected BulletTxtNumberingStyleBase(char bulletChar, boolean onlySpaceSeparateBullet)
    {
    	super(true, false, NumberStyle.BULLET);
	
        mBulletChar = bulletChar;

        String delimiterPattern = (onlySpaceSeparateBullet)
            ? "\\s"
            : "(\\w|\\s)";
        mRegex = new Regex(msString.format("^\\u{0:X4}{1}", (int)mBulletChar, delimiterPattern), RegexOptions.COMPILED);
    }

    protected BulletTxtNumberingStyleBase(char bulletChar)
    {
    	this(bulletChar, false);
	
    }

    /*override*/ TxtNumberingInfo detectNumbering(String text)
    {
        Match match = mRegex.match(text);
        if (match.getSuccess())
        {
            String[] numbers = new String[1];
            numbers[0] = Convert.toString(mBulletChar);
            return new TxtNumberingInfo(numbers, numbers[0]);
        }
        return null;
    }

    /*override*/ boolean isStartNumber(String value)
    {
        Debug.assertTrue(false, "Bullet style doesn't support this operation!");
        return (value.length() == 1) && (value.charAt(0) == mBulletChar);
    }

    /*override*/ String getNextNumber(String prevNumber)
    {
        Debug.assertTrue(false, "Bullet style doesn't support this operation!");
        return Convert.toString(mBulletChar);
    }

    /*override*/ String getNumberFormat(int level)
    {
        Debug.assertTrue(level == 0, "Only level 0 is allowed!");
        return Convert.toString(mBulletChar);
    }

    private /*final*/ char mBulletChar;
    private /*final*/ Regex mRegex;
}

