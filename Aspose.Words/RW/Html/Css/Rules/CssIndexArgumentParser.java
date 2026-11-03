// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.FormatterPal;
import com.aspose.StringUtil;
import com.aspose.Debug;


/// <summary>
/// Parses arguments of CSS pseudo-classes :nth-child(), :nth-last-child(), :nth-of-type(), and :nth-last-of-type().
/// </summary>
/// <remarks>
/// The argument has the form 'an+b'; its syntax is described here: http://www.w3.org/TR/css3-selectors/#nth-child-pseudo
/// </remarks>
/*static*/ class CssIndexArgumentParser
{
	/* Simulation of static class by using private constructor */
	private CssIndexArgumentParser()
	{}

    /// <summary>
    /// Parses the tokens into a pseudo-class argument.
    /// </summary>
    /// <param name="tokens">Tokens of the argument, which will be parsed.</param>
    /// <returns>The parsed argument or <c>null</c> in case of an error.</returns>
    static CssIndexArgument parse(ArrayList<CssToken> tokens)
    {
        if ((tokens == null) || (tokens.size() == 0))
        {
            // Parsing error. The list of argument tokens is empty.
            return null;
        }

        StringBuilder argumentText = new StringBuilder();

        // Rebuild the text of the argument.
        // Check that the argument consists of valid tokens.
        // Replace whitespace characters with the space (U+0020) character.
        for (int i = 0; i < tokens.size(); i++)
        {
            CssToken token = tokens.get(i);
            if (token.isDelim('+') || token.isDelim('-'))
            {
                char delimChar = ((CssDelimToken)token).getValue();
                argumentText.append(delimChar);
                continue;
            }
            switch (token.getType())
            {
                case CssTokenType.IDENT:
                {
                    msStringBuilder.append(argumentText, ((CssTextToken)token).getText());
                    break;
                }
                case CssTokenType.DIMENSION:
                case CssTokenType.NUMBER:
                {
                    CssNumberToken numberToken = (CssNumberToken)token;
                    if (!numberToken.isInteger())
                    {
                        // Parsing error. All numbers must be integers. No floating-point or scientific numbers allowed.
                        return null;
                    }

                    if (numberToken.isSigned() && numberToken.getValue() > 0)
                    {
                        argumentText.append('+');
                    }
                    msStringBuilder.append(argumentText, FormatterPal.doubleToStr(numberToken.getValue()));

                    if (token.getType() == CssTokenType.DIMENSION)
                    {
                        msStringBuilder.append(argumentText, ((CssDimensionToken)numberToken).getUnit());
                    }
                    break;
                }
                case CssTokenType.WHITESPACE:
                {
                    argumentText.append(' ');
                    break;
                }
                default:
                {
                    // Parsing error. Unexpected token.
                    return null;
                }
            }
        }

        // Trim leading whitespace.
        int leadingSpaceCount = 0;
        for (int i = 0; i < argumentText.length(); i++)
        {
            if (argumentText.charAt(i) != ' ')
            {
                break;
            }
            ++leadingSpaceCount;
        }
        if (leadingSpaceCount > 0)
        {
            argumentText.delete((0), (0) + (leadingSpaceCount));
        }

        // Trim trailing whitespace.
        int trailingSpaceCount = 0;
        for (int i = argumentText.length() - 1; i >= 0; i--)
        {
            if (argumentText.charAt(i) != ' ')
            {
                break;
            }
            ++trailingSpaceCount;
        }
        if (trailingSpaceCount > 0)
        {
            argumentText.delete((argumentText.length() - trailingSpaceCount), (argumentText.length() - trailingSpaceCount) + (trailingSpaceCount));
        }

        // Convert the argument text to lowercase. Arguments are case-insensitive.
        String trimmedArgumentText = StringUtil.asciiLowerCase(argumentText.toString());

        if (trimmedArgumentText.length() == 0)
        {
            // Parsing error. Argument is empty (or contains only whitespace characters).
            return null;
        }

        // Check if the argument is any of the aliases. 
        if ("even".equals(trimmedArgumentText))
        {
            // 'even' is the same as '2n'.
            return new CssIndexArgument(2, 0);
        }
        if ("odd".equals(trimmedArgumentText))
        {
            // 'odd' is the same as '2n+1'.
            return new CssIndexArgument(2, 1);
        }

        int indexOfN = trimmedArgumentText.indexOf('n');
        if (indexOfN >= 0)
        {
            // The argument is a number in the form 'an+b'. Parse the 'a' and 'b' values.
            String stepString = trimmedArgumentText.substring((0), (0) + (indexOfN));
            String offsetString = trimmedArgumentText.substring(indexOfN + 1);

            int step = (stepString.length() > 0)
                ? parseInt(stepString, false, true)
                : 1;
            if (step == Integer.MIN_VALUE)
            {
                // Error. Cannot parse step value.
                return null;
            }

            int offset = (offsetString.length() > 0)
                ? parseInt(offsetString, true, false)
                : 0;
            if (offset == Integer.MIN_VALUE)
            {
                // Error. Cannot parse offset value.
                return null;
            }

            return new CssIndexArgument(step, offset);
        }
        else
        {
            // The argument is a number with optional sign.
            int offset = parseInt(trimmedArgumentText, false, false);
            if (offset == Integer.MIN_VALUE)
            {
                // Error. Cannot parse offset value.
                return null;
            }
            return new CssIndexArgument(0, offset);
        }
    }

    /// <summary>
    /// Parses a string into an integer value.
    /// </summary>
    /// <returns>Parsed value or <see cref="int.MinValue"/> in case of an error.</returns>
    private static int parseInt(String s, boolean allowWhitespaceAroundSign, boolean allowOnlySign)
    {
        Debug.assertTrue(s != null);

        // Character index at which the number value starts. Will be calculated as leading whitespace and sign characters
        // are parsed.
        int numberOffset = 0;

        // Skip whitespace characters before the sign character, if whitespace characters are allowed around the sign
        // character.
        if (allowWhitespaceAroundSign)
        {
            // When the argument's text was rebuilt, all whitespace characters were replaced with spaces, so only spaces
            // are skipped here.
            while ((numberOffset < s.length()) && (s.charAt(numberOffset) == ' '))
            {
                ++numberOffset;
            }
        }

        if (numberOffset >= s.length())
        {
            // Parsing error. Integer value is empty (or contains only whitespace characters).
            return Integer.MIN_VALUE;
        }

        // Parse the sign character if any exists.
        int sign = 1;
        if (s.charAt(numberOffset) == '+')
        {
            ++numberOffset;
        }
        else if (s.charAt(numberOffset) == '-')
        {
            sign = -1;
            ++numberOffset;
        }

        // Skip whitespace characters after the sign character, if whitespace characters are allowed around the sign
        // character.
        if (allowWhitespaceAroundSign)
        {
            // When the argument's text was rebuilt, all whitespace characters were replaced with spaces, so only spaces
            // are skipped here.
            while ((numberOffset < s.length()) && (s.charAt(numberOffset) == ' '))
            {
                ++numberOffset;
            }
        }

        if (numberOffset >= s.length())
        {
            if (allowOnlySign)
            {
                // The number consists only of a sign character. For example, if the argument text is '-n',
                // the step value consists only of a minus sign, and the step is equal to -1 (minus one).
                return sign;
            }
            // Parsing error. Integer value consists only of a sign character.
            return Integer.MIN_VALUE;
        }

        // Extract the integer part from the string.
        String number = s.substring(numberOffset);

        // The integer parsing routine accepts not only digits but also whitespace and sign characters,
        // so we perform an additional check to ensure that the string contains nothing but digits
        // before trying to parse it.
        if (StringUtil.isDecimal(number))
        {
            int value = FormatterPal.tryParseInt(number);
            if (value >= 0)
            {
                return sign * value;
            }
        }
        // Parsing error. Integer value contains characters other than digits.
        return Integer.MIN_VALUE;
    }
}

