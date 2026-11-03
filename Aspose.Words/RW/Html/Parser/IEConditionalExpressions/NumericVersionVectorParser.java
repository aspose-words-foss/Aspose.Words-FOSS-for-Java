// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/12/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.FormatterPal;
import java.lang.NumberFormatException;
import com.aspose.words.net.System.OverflowException;


/// <summary>
/// A parser for supported version vectors.
/// </summary>
/// <remarks>
/// Supported version vectors are either one or two non-negative 32-bit integer values separated with a single dot.
/// For example, '9', '9.1', '0.1'.
/// </remarks>
/*static*/ class NumericVersionVectorParser
{
	/* Simulation of static class by using private constructor */
	private NumericVersionVectorParser()
	{}

    /// <summary>
    /// Parses text as a numeric version vector.
    /// </summary>
    /// <returns>
    /// Returns either a parsed version vector or <c>null</c> to indicate an error.
    /// </returns>
    static NumericVersionVector parse(String text)
    {
        Match match = Regex.match(text, "^[0]*([0-9]+)(?:\\.[0]*([0-9]+))?$");
        if (match.getSuccess())
        {
            // Regex captures 3 groups: the text itself, the major part of the version, and its minor part.
            Debug.assertTrue(match.getGroups().getCount() == 3);

            int majorPart = parseNonNegativeNumber(match.getGroups().get(1).getValue());
            if (majorPart >= 0)
            {
                // The minor part is optional. Let's check if it is present.
                String minorPartText = match.getGroups().get(2).getValue();
                if (StringUtil.hasChars(minorPartText))
                {
                    int minorPart = parseNonNegativeNumber(minorPartText);
                    if (minorPart >= 0)
                    {
                        return new NumericVersionVector(majorPart, minorPart);
                    }
                }
                else
                {
                    return new NumericVersionVector(majorPart);
                }
            }
        }

        // Text doesn't match the regex or vector parts cannot be parsed as integers.
        return null;
    }

    /// <summary>
    /// Parses text at the current position as a non-negative 32-bit integer.
    /// </summary>
    /// <returns>
    /// Returns either a parsed integer or -1 to indicate an error.
    /// </returns>
    private static int parseNonNegativeNumber(String text)
    {
        // Actually, the text comes from a regular expression that doesn't allow for invalid characters and the text
        // is expected to be a sequence of digits. However, we check the sign of the result and catch format exceptions
        // anyway just to be sure there are no logical errors.
        final int ERROR = -1;
        try
        {
            int parsedNumber = FormatterPal.parseInt(text);
            return (parsedNumber >= 0)
                ? parsedNumber
                : ERROR;
        }
        catch (NumberFormatException e)
        {
            // If the regex we use to parse vectors is correct, we will never get here.
            return ERROR;
        }
        catch (OverflowException e)
        {
            // Using Regex alone, it is impossible to filter out huge numbers that don't fit into 32-bit integers
            // so we have to check for integer overflow here.
            return ERROR;
        }
    }
}

