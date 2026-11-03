// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Provides mirrored counterparts of characters, such as ')' for '('.
/// </summary>
/// <remarks>
/// The pairs come from the Bidi_Mirroring_Glyph property of the Unicode Character Database through the generated
/// <see cref="UnicodeCharacterTables"/>.
/// </remarks>
public /*static*/ class BidiCharacterMirrorResolver
{
	/* Simulation of static class by using private constructor */
	private BidiCharacterMirrorResolver()
	{}

    /// <summary>
    /// Returns the mirrored counterpart of the specified character, or the character itself if it has none.
    /// </summary>
    public static char getBidiCharacterMirror(char c)
    {
        int low = 0;
        int high = G_CHARACTERS.length - 1;
        while (low <= high)
        {
            int middle = (low + high) / 2;
            char middleChar = G_CHARACTERS[middle];
            if (middleChar == c)
            {
                return G_MIRRORS[middle];
            }

            if (middleChar < c)
            {
                low = middle + 1;
            }
            else
            {
                high = middle - 1;
            }
        }
        return c;
    }

    /// <summary>
    /// Takes either the characters or their mirrors from the pairs of <see cref="UnicodeCharacterTables.Mirrors"/>.
    /// </summary>
    private static char[] readPairs(boolean takeMirrors)
    {
        String pairs = UnicodeCharacterTables.MIRRORS;
        char[] result = new char[pairs.length() / PAIR_LENGTH];
        int offset = takeMirrors ? 4 : 0;
        for (int i = 0; i < result.length; i++)
        {
            result[i] = (char)UnicodeCharacterDataResolver.parseHex4(pairs, i * PAIR_LENGTH + offset);
        }
        return result;
    }

    private static final int PAIR_LENGTH = 8;

    /// <summary>
    /// Characters that have mirrored counterparts, sorted.
    /// </summary>
    private static /*final*/ char[] G_CHARACTERS = readPairs(false);

    /// <summary>
    /// The mirrored counterparts of <see cref="gCharacters"/>.
    /// </summary>
    private static /*final*/ char[] G_MIRRORS = readPairs(true);
}

