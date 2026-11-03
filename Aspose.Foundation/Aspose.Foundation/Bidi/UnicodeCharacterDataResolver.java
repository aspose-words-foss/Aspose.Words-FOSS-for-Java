// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Provides the Unicode bidi classes of characters.
/// </summary>
/// <remarks>
/// The properties come from the Unicode Character Database through the generated <see cref="UnicodeCharacterTables"/>.
/// </remarks>
public /*static*/ class UnicodeCharacterDataResolver
{
	/* Simulation of static class by using private constructor */
	private UnicodeCharacterDataResolver()
	{}

    /// <summary>
    /// Returns the bidi class of the specified character.
    /// </summary>
    public static /*BidiCharacterType*/int getBidiCharacterType(char c)
    {
        return (/*BidiCharacterType*/int)(G_BIDI_CLASSES[c] & 0xFF);
    }

    /// <summary>
    /// Returns <c>true</c>, if the specified character has a strong bidi class: L, R or AL.
    /// </summary>
    public static boolean isStrongBidiCharacterType(char c)
    {
        /*BidiCharacterType*/int bidiClass = getBidiCharacterType(c);
        return (bidiClass == BidiCharacterType.L) ||
            (bidiClass == BidiCharacterType.R) ||
            (bidiClass == BidiCharacterType.AL);
    }

    /// <summary>
    /// Parses 4 hex digits of the specified string that start at the specified index.
    /// </summary>
    static int parseHex4(String s, int start)
    {
        int result = 0;
        for (int i = start; i < start + 4; i++)
        {
            char digit = s.charAt(i);
            int digitValue = (digit <= '9')
                ? digit - '0'
                : digit - 'A' + 10;
            result = (result << 4) | digitValue;
        }
        return result;
    }

    /// <summary>
    /// Expands a table of ranges (see <see cref="UnicodeCharacterTables"/>) into an array with a value for every character.
    /// </summary>
    private static byte[] expandRanges(String ranges)
    {
        byte[] values = new byte[CHAR_COUNT];
        for (int i = 0; i < ranges.length(); i += RANGE_LENGTH)
        {
            int first = parseHex4(ranges, i);
            int next = (i + RANGE_LENGTH < ranges.length())
                ? parseHex4(ranges, i + RANGE_LENGTH)
                : CHAR_COUNT;
            char code = ranges.charAt(i + 4);
            byte value = (byte)((code <= 'Z')
                ? code - 'A'
                : code - 'a' + 26);
            for (int c = first; c < next; c++)
            {
                values[c] = value;
            }
        }
        return values;
    }

    private static final int CHAR_COUNT = 0x10000;
    private static final int RANGE_LENGTH = 5;

    private static /*final*/ byte[] G_BIDI_CLASSES = expandRanges(UnicodeCharacterTables.BIDI_CLASS_RANGES);
}

