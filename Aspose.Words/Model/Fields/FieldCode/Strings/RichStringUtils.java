// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/04/2020 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.CharCase;
import com.aspose.StringUtil;
import com.aspose.IChar;


/// <summary>
/// Rich string aware duplicate of the <see cref="StringUtil"/> class.
/// Don't use <see cref="IString"/> abstraction in <see cref="StringUtil"/> for performance purpose.
/// </summary>
/*static*/ class RichStringUtils
{
	/* Simulation of static class by using private constructor */
	private RichStringUtils()
	{}

    /// <summary>
    /// Formats the text string according to the specified char case.
    /// </summary>
    static RichString formatCharCase(RichString s, /*CharCase*/int charCase)
    {
        switch (charCase)
        {
            case CharCase.UPPER:
                return s.toUpperInternal();
            case CharCase.LOWER:
                return s.toLowerInternal();
            case CharCase.CAPS:
                return capitalizeWords(s);
            case CharCase.FIRST_CAP:
                return capitalizeString(s);
            case CharCase.DB_CHAR:
                return toFullWidth(s);
            case CharCase.DEFAULT:
                return s;
            default:
                throw new IllegalStateException("Unknown char case specified.");
        }
    }

    /// <summary>
    /// Capitalizes first non whitespace character that follows every whitespace.
    /// </summary>
    private static RichString capitalizeWords(RichString s)
    {
        if (s == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "s");

        boolean isInsideWord = false;
        // Use of StringBuilder seems to be simpler here. We can't use string.Split and string.Join
        // because different types and amount of whitespace is possible between words.
        RichStringBuilder builder = new RichStringBuilder(s);

        for (int i = 0; i < builder.getLength(); i++)
        {
            RichChar c = builder.getInternal(i);
            if (StringUtil.isWordBreakChar(c.toSystemChar()))
            {
                isInsideWord = false;
            }
            else if (!isInsideWord)
            {
                builder.setInternal(i, c.toUpperInternal());
                isInsideWord = true;
            }
            else
            {
                builder.setInternal(i, c.toLowerInternal());
            }
        }

        return builder.toRichString();
    }

    /// <summary>
    /// Capitalizes first non whitespace character of the string.
    /// </summary>
    private static RichString capitalizeString(RichString s)
    {
        if (s == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "s");

        RichStringBuilder builder = new RichStringBuilder(s);
        for (int i = 0; i < builder.getLength(); i++)
        {
            RichChar c = builder.getInternal(i);
            if (!StringUtil.isWordBreakChar(c.toSystemChar()))
            {
                builder.setInternal(i, c.toUpperInternal());
                break;
            }
        }

        return builder.toRichString();
    }

    /// <summary>
    /// Converts ASCII numbers and letters to full-witdh numbers and letters.
    /// </summary>
    private static RichString toFullWidth(RichString value)
    {
        if (RichStringBehaviour.isNullOrEmptyInternal(value))
            return RichString.EMPTY;

        RichStringBuilder converted = new RichStringBuilder();
        for (IChar c : (Iterable<IChar>) value)
        {
            char systemChar = c.toSystemChar();

            if (systemChar > 0x20 && systemChar < 0x7F)
            {
                converted.appendInternal((char)(0xFF00 | (systemChar - 0x20)), (RichChar)c);
            }
            else if (systemChar == 0x20)
            {
                converted.appendInternal((char)0x3000, (RichChar)c);
            }
            else
            {
                converted.appendInternal((RichChar)c);
            }
        }

        return converted.toRichString();
    }
}

