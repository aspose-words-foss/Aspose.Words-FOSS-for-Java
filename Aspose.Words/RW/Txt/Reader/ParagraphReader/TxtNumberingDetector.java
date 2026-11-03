// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/06/2012 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.msArray;


/// <summary>
/// Detects numbering or bullet of a text line.
/// </summary>
/*static*/ class TxtNumberingDetector
{
	/* Simulation of static class by using private constructor */
	private TxtNumberingDetector()
	{}

    /// <summary>
    /// Parses string for numbering or bullet.
    /// </summary>
    /// <param name="text">Source string</param>
    /// <param name="detectNumberingWithWhitespaces"></param>
    /// <returns>Numbering info if found; otherwise null.</returns>
    /// <remarks>
    /// The method is replaced with <see cref="Detect(string, TxtLoadOptions)"/>
    /// and remained just for backward compatibility in tests.
    /// </remarks>
    static TxtNumbering detect(String text, boolean detectNumberingWithWhitespaces)
    {
        TxtLoadOptions loadOptions = new TxtLoadOptions();{loadOptions.setDetectNumberingWithWhitespaces(detectNumberingWithWhitespaces);}
        return detect(text, loadOptions);
    }

    /// <summary>
    /// Parses string for numbering or bullet.
    /// </summary>
    /// <param name="text">Source string</param>
    /// <param name="loadOptions"> The load options.</param>
    /// <returns>Numbering info if found; otherwise null.</returns>
    static TxtNumbering detect(String text, TxtLoadOptions loadOptions)
    {
        if (!loadOptions.getAutoNumberingDetection())
            return null;

        Debug.assertTrue(!msString.startsWith(text, " ", StringComparison.ORDINAL), "Leading spaces should be removed.");

        // WORDSNET-10993 For ArabicDotTxtNumberingStyle it is allowed to control the way of lists recognition.
        G_NUMBERING_STYLES[0].setDetectNumberingWithWhitespaces(loadOptions.getDetectNumberingWithWhitespaces());

        for (TxtNumberingStyle numberingStyle : G_NUMBERING_STYLES)
        {
            TxtNumberingInfo numberingInfo = numberingStyle.detectNumbering(text);
            if ((numberingInfo != null) &&
                (numberingInfo.getNumbers().length <= NUMBERING_MAX_LEVEL_COUNT) &&
                letterOrRomanNumberingConform(numberingStyle, numberingInfo)) //So as not to confuse Roman and Letter
            {
                return new TxtNumbering(numberingStyle, numberingInfo.getNumbers(), numberingInfo.getText());
            }
        }

        return null;
    }

    private static boolean letterOrRomanNumberingConform(TxtNumberingStyle curStyle, TxtNumberingInfo curNumbering)
    {
        boolean retValue = true;

        char curNumberingTextChar = Character.toLowerCase(curNumbering.getText().charAt(0));

        if ((curStyle.getNumberStyle() == NumberStyle.LOWERCASE_ROMAN || curStyle.getNumberStyle() == NumberStyle.UPPERCASE_ROMAN) &&
            msArray.indexOf((TxtLettersHelper.G_LOWERCASE_ROMAN_NUMBERS), (curNumberingTextChar), 0, (TxtLettersHelper.G_LOWERCASE_ROMAN_NUMBERS).length) != -1)
        {
                retValue = (char)(curNumberingTextChar - 1) != mPrevNumberingTextChar;
        }

        if(retValue)
            mPrevNumberingTextChar = curNumberingTextChar;

        return retValue;
    }

    private static char mPrevNumberingTextChar = ' ';

    private static final int NUMBERING_MAX_LEVEL_COUNT = 8;

    private static /*final*/ TxtNumberingStyle[] G_NUMBERING_STYLES;

    static/* TxtNumberingDetector()*/
    {
        G_NUMBERING_STYLES = new TxtNumberingStyle[]
                               {
                                   new ArabicDotTxtNumberingStyle(),
                                   new ArabicRightBracketTxtNumberingStyle(),
                                   new LowercaseRomanDotTxtNumberingStyle(),
                                   new UppercaseRomanDotTxtNumberingStyle(),
                                   new LowercaseRomanRightBracketTxtNumberingStyle(),
                                   new UppercaseRomanRightBracketTxtNumberingStyle(),
                                   new LowercaseLetterDotTxtNumberingStyle(),
                                   new UppercaseLetterDotTxtNumberingStyle(),
                                   new LowercaseLetterRightBracketTxtNumberingStyle(),
                                   new UppercaseLetterRightBracketTxtNumberingStyle(),
                                   new AsteriskTxtNumberingStyle(),
                                   new DashTxtNumberingStyle(),
                                   new CourierCircleTxtNumberingStyle(),
                                   new Bullet0X95TxtNumberingStyle()
                               };
    }
}

