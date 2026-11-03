// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/11/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents the 'counter' CSS function, which is a souce of generated content for pseudo-elements.
/// </summary>
class PseudoElementContentCounter extends PseudoElementContentCounterFunction
{
    PseudoElementContentCounter(
        String counterName,
        /*NumberStyle*/int counterStyle)
    {
    	super(
              counterName,
              counterStyle);
	
        // Empty constructor.
    }

    /// <summary>
    /// Parses parameters of the 'counter' CSS function.
    /// </summary>
    /// <returns>
    /// Parsed content or <c>null</c> if parameters are malformed.
    /// </returns>
    static PseudoElementContentCounter parse(CssFunctionValue value)
    {
        boolean syntaxIsValid = ("counter".equals(value.getName())) &&
            argumentsAreCommaSeparated(value.getArguments()) &&
            ((value.getArguments().getCount() == 1) || (value.getArguments().getCount() == 3));
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

        /*NumberStyle*/int numberStyle = DEFAULT_NUMBER_STYLE;
        if (value.getArguments().getCount() == 3)
        {
            // Parameter indexes are shifted because of comma separators.
            String counterStyle = parseCounterParameter(value.getArguments().get(2));
            if (counterStyle == null)
            {
                // Error. The counter style must be a non-reserved identifier.
                return null;
            }
            numberStyle = counterStyleToNumberStyle(counterStyle);
        }

        return new PseudoElementContentCounter(counterName, numberStyle);
    }

    /*override*/ void accept(IPseudoElementContentPartVisitor visitor)
    {
        visitor.visitCounter(getCounterName(), getCounterStyle());
    }

}

