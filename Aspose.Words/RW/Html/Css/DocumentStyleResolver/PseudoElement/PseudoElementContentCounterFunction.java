// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/12/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;

/// <summary>
/// Represents either 'counter' or 'counters' CSS function, which is a souce of generated content for pseudo-elements.
/// Contains code that is common to both functions.
/// </summary>
abstract class PseudoElementContentCounterFunction extends PseudoElementContentPart
{
    protected PseudoElementContentCounterFunction(
        String counterName,
        /*NumberStyle*/int counterStyle)
    {
        Debug.assertTrue(StringUtil.hasChars(counterName));
        Debug.assertTrue(counterStyle != NumberStyle.BULLET);

        mCounterName = counterName;
        mCounterStyle = counterStyle;
    }

    String getCounterName() { return mCounterName; };

    private String mCounterName;

    /*NumberStyle*/int getCounterStyle() { return mCounterStyle; };

    private /*NumberStyle*/int mCounterStyle;

    /// <summary>
    /// Checks whether the value collection is a comma-separated list. Auxillary method for parsing of function parameters.
    /// </summary>
    protected static boolean argumentsAreCommaSeparated(CssValueList arguments)
    {
        // Both 'counter' and 'counters' functions have common syntax: they accept a comma-separated list of arguments.

        // All even arguments must be commas so the total number of arguments must be odd.
        if ((arguments.getCount() % 2) != 1)
        {
            // Error. Arguments are not separated by commas.
            return false;
        }
        for (int i = 1; i < arguments.getCount(); i += 2)
        {
            if (arguments.get(i).getValueType() != CssValueType.COMMA)
            {
                // Error. Arguments are not separated by commas.
                return false;
            }
        }

        return true;
    }

    /// <summary>
    /// Auxillary method for parsing of function parameters.
    /// </summary>
    protected static String parseCounterParameter(CssValue parameter)
    {
        return (parameter.getValueType() == CssValueType.IDENTIFIER)
            ? ((CssIdentifierValue)parameter).getValue()
            : null;
    }

    protected static /*NumberStyle*/int counterStyleToNumberStyle(String counterStyle)
    {
        // Here we check only values, which are also used in HtmlListItemMarker class (except bullet values).
        // counter style values are case-insensitive.
        switch (gStringSwitchMap.of(msString.toLowerInvariant(counterStyle)))
        {
            case /*"decimal"*/0:
            case /*"arabic-indic"*/1: // WORDSNET-26977 Add support for "arabic-indic" list style.
                return NumberStyle.ARABIC;
            case /*"decimal-leading-zero"*/2:
                return NumberStyle.LEADING_ZERO;
            case /*"lower-latin"*/3:
            case /*"lower-alpha"*/4:
            case /*"lower-greek"*/5:
                return NumberStyle.LOWERCASE_LETTER;
            case /*"upper-latin"*/6:
            case /*"upper-alpha"*/7:
            case /*"upper-greek"*/8:
                return NumberStyle.UPPERCASE_LETTER;
            case /*"lower-roman"*/9:
                return NumberStyle.LOWERCASE_ROMAN;
            case /*"upper-roman"*/10:
                return NumberStyle.UPPERCASE_ROMAN;
            default:
                return DEFAULT_NUMBER_STYLE;
        }
    }

    protected static final /*NumberStyle*/int DEFAULT_NUMBER_STYLE = NumberStyle.ARABIC;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"decimal",
		"arabic-indic",
		"decimal-leading-zero",
		"lower-latin",
		"lower-alpha",
		"lower-greek",
		"upper-latin",
		"upper-alpha",
		"upper-greek",
		"lower-roman",
		"upper-roman"
	);

}

