// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/11/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Represents the 'counters' CSS function, which is a souce of generated content for pseudo-elements.
/// </summary>
class PseudoElementContentCounters extends PseudoElementContentCounterFunction
{
    PseudoElementContentCounters(
        String counterName,
        String separator,
        /*NumberStyle*/int counterStyle)
    {
    	super(
              counterName,
              counterStyle);
	
        Debug.assertTrue(separator != null);
        mSeparator = separator;
    }

    /// <summary>
    /// Parses parameters of the 'counters' CSS function.
    /// </summary>
    /// <returns>
    /// Parsed content or <c>null</c> if parameters are malformed.
    /// </returns>
    static PseudoElementContentCounters parse(CssFunctionValue value)
    {
        boolean syntaxIsValid = ("counters".equals(value.getName())) &&
            argumentsAreCommaSeparated(value.getArguments()) &&
            ((value.getArguments().getCount() == 3) || (value.getArguments().getCount() == 5));
        if (!syntaxIsValid)
        {
            // Error. Either function name or function syntax is invalid.
            return null;
        }

        String counterName = parseCounterParameter(value.getArguments().get(0));
        if (counterName == null)
        {
            // Error. The counter name must be a non-reserved identifier.
            return null;
        }

        // Parameter indexes are shifted because of comma separators.
        CssValue secondParameter = value.getArguments().get(2);
        if (secondParameter.getValueType() != CssValueType.STRING)
        {
            // Error. The separator parameter must be a string.
            return null;
        }
        String separator = ((CssStringValue)secondParameter).getValue();

        /*NumberStyle*/int numberStyle = DEFAULT_NUMBER_STYLE;
        if (value.getArguments().getCount() == 5)
        {
            // Parameter indexes are shifted because of comma separators.
            String counterStyle = parseCounterParameter(value.getArguments().get(4));
            if (counterStyle == null)
            {
                // Error. The counter style must be a non-reserved identifier.
                return null;
            }
            numberStyle = counterStyleToNumberStyle(counterStyle);
        }

        return new PseudoElementContentCounters(counterName, separator, numberStyle);
    }

    /*override*/ void accept(IPseudoElementContentPartVisitor visitor)
    {
        visitor.visitCounters(getCounterName(), mSeparator, getCounterStyle());
    }


    private /*final*/ String mSeparator;
}

