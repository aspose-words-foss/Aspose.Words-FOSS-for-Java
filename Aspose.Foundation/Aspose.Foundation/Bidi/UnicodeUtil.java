// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/07/2012 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msArray;
import com.aspose.ms.System.msString;


/// <summary>
/// Contains helper methods to work with Unicode strings and characters.
/// </summary>
public /*static*/ class UnicodeUtil
{
	/* Simulation of static class by using private constructor */
	private UnicodeUtil()
	{}

    /// <summary>
    /// Reverses a string taking UTF-16 surrogate pairs into account.
    /// </summary>
    /// <remarks>
    /// Since each UTF-16 surrogate pair represents a single Unicode code point, they must be preserved upon string
    /// reversal. We cannot let the lead and tail surrogate characters switch places.
    /// </remarks>
    public static String reverseString(String s)
    {
        // Special cases. No need to reverse anything.
        if ((s == null) || (s.length() <= 1))
        {
            return s;
        }

        // First, simply reverse all UTF-16 characters.
        char[] result = s.toCharArray();
        msArray.reverse(result);

        // Check if we've reversed any surrogate pairs during the previous step, and restore them.
        // No need to check the last character, because it doesn't have a pair.
        for (int i = 0; i < result.length - 1; i++)
        {
            // Check if two characters (the current one and the one after it) are surrogates in the wrong order:
            // tail before lead.
            if (isTailSurrogate(result[i]) && isLeadSurrogate(result[i + 1]))
            {
                // Reverse the pair of surrogate characters.
                char tmp = result[i];
                result[i] = result[i + 1];
                result[i + 1] = tmp;

                // We've just also processed the character after the current one (the lead surrogate), so we need to move
                // one character further.
                ++i;
            }
        }

        return new String(result);
    }

    /// <summary>
    /// Converts the specified Unicode code point into a UTF-16 encoded string.
    /// </summary>
    public static String convertFromUtf32(int utf32Char)
    {
        if (!isValidUtf32(utf32Char))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "utf32Char");

        if (isSupplementaryPlanesCharacter(utf32Char))
        {
            char leadSurrogate = (char)((utf32Char - SUPPLEMENTARY_MIN_VALUE) / (1 << 10) + LEAD_SURROGATE_MIN_VALUE);
            char tailSurrogate = (char)((utf32Char - SUPPLEMENTARY_MIN_VALUE) % (1 << 10) + TAIL_SURROGATE_MIN_VALUE);
            return new String(new char[] { leadSurrogate, tailSurrogate });
        }
        else
        {
            return msString.newString((char)utf32Char, 1);
        }
    }

    /// <summary>
    /// Converts the specified Unicode code point into a UTF-16 one or two chars.
    /// </summary>
    /// <remarks>
    /// This method is more effective than <see cref="ConvertFromUtf32(int)"/>, because it doesn't create temporary char[]
    /// and string instances and thus creates less memory pressure.
    /// </remarks>
    public static void appendUtf32(StringBuilder sb, int utf32Char)
    {
        if (!isValidUtf32(utf32Char))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "utf32Char");

        if (isSupplementaryPlanesCharacter(utf32Char))
        {
            char leadSurrogate = (char)((utf32Char - SUPPLEMENTARY_MIN_VALUE) / (1 << 10) + LEAD_SURROGATE_MIN_VALUE);
            char tailSurrogate = (char)((utf32Char - SUPPLEMENTARY_MIN_VALUE) % (1 << 10) + TAIL_SURROGATE_MIN_VALUE);
            sb.append(leadSurrogate);
            sb.append(tailSurrogate);
        }
        else
        {
            sb.append((char)utf32Char);
        }
    }

    /// <summary>
    /// Returns a number indicating how many UTF-16 characters are needed to encode the specified Unicode code point.
    /// </summary>
    /// <returns>
    /// Either one or two.
    /// One UTF-16 character is enough to encode code points from the basic multilingual plane (BMP),
    /// while other code points require two UTF-16 characters (a surrogate pair).
    /// </returns>
    public static int getLenFromUtf32(int utf32Char)
    {
        if (!isValidUtf32(utf32Char))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "utf32Char");

        return isSupplementaryPlanesCharacter(utf32Char) ? 2 : 1;
    }

    /// <summary>
    /// Converts the value of a UTF-16 encoded surrogate pair into a Unicode code point.
    /// </summary>
    public static int convertToUtf32(char leadSurrogate, char tailSurrogate)
    {
        if (!isLeadSurrogate(leadSurrogate))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "leadSurrogate");
        if (!isTailSurrogate(tailSurrogate))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "tailSurrogate");

        return (leadSurrogate - LEAD_SURROGATE_MIN_VALUE) * (1 << 10) + (tailSurrogate - TAIL_SURROGATE_MIN_VALUE) +
               SUPPLEMENTARY_MIN_VALUE;
    }

    /// <summary>
    /// Converts the value of a UTF-16 encoded character or surrogate pair at a specified position in a string into a
    /// Unicode code point.
    /// </summary>
    public static int convertToUtf32(String s, int position)
    {
        if (s == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "s");
        if (position < 0 || position >= s.length())
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "position");

        return isSurrogatePair(s, position)
            // A valid surrogate pair found. It is a character from one of the Supplementary Planes.
            ? convertToUtf32(s.charAt(position), s.charAt(position + 1))
            // No valid surrogate pair found. It is a character from the Basic Multilingual Plane or a broken surrogate pair.
            : s.charAt(position);
    }

    /// <summary>
    /// Indicates whether two adjacent chars at a specified position in a string form a surrogate pair.
    /// </summary>
    public static boolean isSurrogatePair(String s, int position)
    {
        if (s == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "s");
        if (position < 0 || position >= s.length())
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "position");

        return isLeadSurrogate(s.charAt(position)) &&
            ((position + 1) < s.length()) &&
            isTailSurrogate(s.charAt(position + 1));
    }

    /// <summary>
    /// Indicates whether the specified UTF-32 char is a valid Unicode code point.
    /// </summary>
    public static boolean isValidUtf32(int utf32Char)
    {
        // WORDSNET-11210 Currently StringUtf32Enumerator passes broken surrogates as is. I.e. it may return a surrogate
        // as an UTF-32 character. Thus we should consider them as valid UTF-32 chars.
        return (utf32Char >= 0) && (utf32Char <= SUPPLEMENTARY_MAX_VALUE);
    }

    /// <summary>
    /// Indicates whether the specified UTF-32 char is in Unicode Supplementary Planes.
    /// </summary>
    public static boolean isSupplementaryPlanesCharacter(int utf32Char)
    {
        return (utf32Char >= SUPPLEMENTARY_MIN_VALUE) && (utf32Char <= SUPPLEMENTARY_MAX_VALUE);
    }

    /// <summary>
    /// Indicates whether the specified UTF-16 char is a surrogate.
    /// </summary>
    public static boolean isSurrogate(char utf16Char)
    {
        return isSurrogate((int)utf16Char);
    }

    /// <summary>
    /// Indicates whether the specified UTF-32 char is a surrogate.
    /// </summary>
    public static boolean isSurrogate(int utf32Char)
    {
        return (utf32Char >= LEAD_SURROGATE_MIN_VALUE) && (utf32Char <= TAIL_SURROGATE_MAX_VALUE);
    }

    /// <summary>
    /// Indicates whether the specified UTF-16 char is a lead surrogate.
    /// </summary>
    public static boolean isLeadSurrogate(char utf16Char)
    {
        return (utf16Char >= LEAD_SURROGATE_MIN_VALUE) && (utf16Char <= LEAD_SURROGATE_MAX_VALUE);
    }

    /// <summary>
    /// Indicates whether the specified UTF-16 char is a tail surrogate.
    /// </summary>
    public static boolean isTailSurrogate(char utf16Char)
    {
        return (utf16Char >= TAIL_SURROGATE_MIN_VALUE) && (utf16Char <= TAIL_SURROGATE_MAX_VALUE);
    }

    /// <summary>
    /// Mirrors certain characters in RTL text.
    /// </summary>
    /// <param name="text">RTL text</param>
    /// <returns>String with certain characters mirrored</returns>
    /// <remarks>
    /// Certain characters (for example, brackets) should be mirrored when they are in right-to-left context in order to
    /// reflect their semantic. For details, see http://www.unicode.org/reports/tr9/#Mirroring
    /// </remarks>
    public static String mirrorCharacters(String text)
    {
        // The fast path. In most cases, text does not contain any characters that need mirroring and the text itself
        // is the mirroring result.
        boolean mirroringNeeded = false;
        for (int i = 0; i < text.length(); i++)
        {
            char mirroredChar = BidiCharacterMirrorResolver.getBidiCharacterMirror(text.charAt(i));
            // A character needs mirroring if its mirrored equivalent is different from the character itself.
            if (mirroredChar != text.charAt(i))
            {
                mirroringNeeded = true;
                break;
            }
        }
        if (!mirroringNeeded)
        {
            return text;
        }

        // The slow path. One or more characters need mirroring and we have to create a new string for mirrored text.
        char[] result = new char[text.length()];
        for (int i = 0; i < text.length(); i++)
        {
            result[i] = BidiCharacterMirrorResolver.getBidiCharacterMirror(text.charAt(i));
        }
        return new String(result);
    }

    /// <summary>
    /// Indicates whether specified character is a Arabic character.
    /// </summary>
    public static boolean isArabicCharacter(int utf32Char)
    {
        return (0x0600 <= utf32Char && utf32Char <= 0x06FF) ||
               (0x0750 <= utf32Char && utf32Char <= 0x077F) ||
               (0x0780 <= utf32Char && utf32Char <= 0x07BF) || // Thaana Unicode block
               (0x08A0 <= utf32Char && utf32Char <= 0x08FF) ||
               (0xFB50 <= utf32Char && utf32Char <= 0xFDFF) ||
               (0xFE70 <= utf32Char && utf32Char <= 0xFEFF);
    }

    /// <summary>
    /// Indicates whether specified character is a Thai character.
    /// </summary>
    public static boolean isThaiCharacter(int utf32Char)
    {
        return (0x0E00 <= utf32Char) && (utf32Char <= 0x0E7F);
    }

    public static boolean isMongolianCharacter(int utf32Char)
    {
        return (0x1800 <= utf32Char) && (utf32Char <= 0x18FF);
    }

    /// <summary>Indicates whether specified character is a Ethiopic character.</summary>
    /// <remarks>List of Ethiopic characters: https://unicode-table.com/en/blocks/ethiopic/ </remarks>
    public static boolean isEthiopicCharacter(int utf32Char)
    {
        return (0x1200 <= utf32Char) && (utf32Char <= 0x137F);
    }

    /// <summary>Indicates whether specified character is a Runic character.</summary>
    /// <remarks>List of Runic characters: https://unicode-table.com/en/blocks/runic/ </remarks>
    public static boolean isRunicCharacter(int utf32Char)
    {
        return (0x16A0 <= utf32Char) && (utf32Char <= 0x16FF);
    }

    /// <summary>
    /// Returns true if the character is in one of the Unicode Private Use Area (PUA) ranges.
    /// </summary>
    public static boolean isPuaCharacter(int utf32Char)
    {
        // Unicode provides three blocks of private use characters: one primary and two supplimentary.
        return ((0xE000 <= utf32Char) && (utf32Char <= 0xF8FF)) ||
            ((0xF0000 <= utf32Char) && (utf32Char <= 0xFFFFD)) ||
            ((0x100000 <= utf32Char) && (utf32Char <= 0x10FFFD));
    }

    /// <summary>
    /// Returns the Unicode block corresponding to the given charCode.
    /// </summary>
    public static /*UnicodeBlocks*/int getUnicodeBlock(int utf32Char)
    {
        //This algorithm is adopted from the .NET BinarySearch function.
        int lo = 0;
        int hi = G_UNICODE_BLOCK_STARTS.length;

        while (lo <= hi)
        {
            int i = (lo + hi) >> 1;

            int start = G_UNICODE_BLOCK_STARTS[i];
            int end = ((i + 1) < G_UNICODE_BLOCK_STARTS.length) ? G_UNICODE_BLOCK_STARTS[i + 1] : Integer.MAX_VALUE;

            if (utf32Char < start)
            {
                //Position is before, make next iteration to look closer to the beginning.
                hi = i - 1;
            }
            else if (utf32Char >= end)
            {
                //Position is after, make next iteration to look close to the end.
                lo = i + 1;
            }
            else
            {
                //Return position.
                return (/*UnicodeBlocks*/int)i;
            }
        }

        return UnicodeBlocks.NO_BLOCK;
    }

    /// <summary>
    /// Trims the string starting from terminating null (if any).
    /// </summary>
    public static String trimFromTerminatingNull(String str)
    {
        int nullIndex = str.indexOf('\u0000');
        return (nullIndex < 0)
            ? str
            : str.substring((0), (0) + (nullIndex));
    }

    /// <summary>
    /// Unicode 'REPLACEMENT CHARACTER' (U+FFFD)
    /// </summary>
    public static final char REPLACEMENT_CHAR = '\uFFFD';

    private static final int LEAD_SURROGATE_MIN_VALUE = 0xD800;
    private static final int LEAD_SURROGATE_MAX_VALUE = 0xDBFF;
    private static final int TAIL_SURROGATE_MIN_VALUE = 0xDC00;
    private static final int TAIL_SURROGATE_MAX_VALUE = 0xDFFF;
    private static final int SUPPLEMENTARY_MIN_VALUE = 0x10000;
    private static final int SUPPLEMENTARY_MAX_VALUE = 0x10FFFF;

    private static /*final*/ int[] G_UNICODE_BLOCK_STARTS = new int[]
    {
            0x0000, 0x0080, 0x0100, 0x0180, 0x0250, 0x02B0, 0x0300, 0x0370, 0x0400, 0x0500, 0x0530, 0x0590, 0x0600, 0x0700, 0x0750, 0x0780,
            0x07C0, 0x0800, 0x0840, 0x08A0, 0x0900, 0x0980, 0x0A00, 0x0A80, 0x0B00, 0x0B80, 0x0C00, 0x0C80, 0x0D00, 0x0D80, 0x0E00, 0x0E80,
            0x0F00, 0x1000, 0x10A0, 0x1100, 0x1200, 0x1380, 0x13A0, 0x1400, 0x1680, 0x16A0, 0x1700, 0x1720, 0x1740, 0x1760, 0x1780, 0x1800,
            0x18B0, 0x1900, 0x1950, 0x1980, 0x19E0, 0x1A00, 0x1A20, 0x1AB0, 0x1B00, 0x1B80, 0x1BC0, 0x1C00, 0x1C50, 0x1CC0, 0x1CD0, 0x1D00,
            0x1D80, 0x1DC0, 0x1E00, 0x1F00, 0x2000, 0x2070, 0x20A0, 0x20D0, 0x2100, 0x2150, 0x2190, 0x2200, 0x2300, 0x2400, 0x2440, 0x2460,
            0x2500, 0x2580, 0x25A0, 0x2600, 0x2700, 0x27C0, 0x27F0, 0x2800, 0x2900, 0x2980, 0x2A00, 0x2B00, 0x2C00, 0x2C60, 0x2C80, 0x2D00,
            0x2D30, 0x2D80, 0x2DE0, 0x2E00, 0x2E80, 0x2F00, 0x2FF0, 0x3000, 0x3040, 0x30A0, 0x3100, 0x3130, 0x3190, 0x31A0, 0x31C0, 0x31F0,
            0x3200, 0x3300, 0x3400, 0x4DC0, 0x4E00, 0xA000, 0xA490, 0xA4D0, 0xA500, 0xA640, 0xA6A0, 0xA700, 0xA720, 0xA800, 0xA830, 0xA840,
            0xA880, 0xA8E0, 0xA900, 0xA930, 0xA960, 0xA980, 0xA9E0, 0xAA00, 0xAA60, 0xAA80, 0xAAE0, 0xAB00, 0xAB30, 0xAB70, 0xABC0, 0xAC00,
            0xD7B0, 0xD800, 0xDB80, 0xDC00, 0xE000, 0xF900, 0xFB00, 0xFB50, 0xFE00, 0xFE10, 0xFE20, 0xFE30, 0xFE50, 0xFE70, 0xFF00, 0xFFF0,
            0x10000, 0x10080, 0x10100, 0x10140, 0x10190, 0x101D0, 0x10280, 0x102A0, 0x102E0, 0x10300, 0x10330, 0x10350, 0x10380, 0x103A0, 0x10400, 0x10450,
            0x10480, 0x10500, 0x10530, 0x10600, 0x10800, 0x10840, 0x10860, 0x10880, 0x108E0, 0x10900, 0x10920, 0x10980, 0x109A0, 0x10A00, 0x10A60, 0x10A80,
            0x10AC0, 0x10B00, 0x10B40, 0x10B60, 0x10B80, 0x10C00, 0x10C80, 0x10E60, 0x11000, 0x11080, 0x110D0, 0x11100, 0x11150, 0x11180, 0x111E0, 0x11200,
            0x11280, 0x112B0, 0x11300, 0x11480, 0x11580, 0x11600, 0x11680, 0x11700, 0x118A0, 0x11AC0, 0x12000, 0x12400, 0x12480, 0x13000, 0x14400, 0x16800,
            0x16A40, 0x16AD0, 0x16B00, 0x16F00, 0x1B000, 0x1BC00, 0x1BCA0, 0x1D000, 0x1D100, 0x1D200, 0x1D300, 0x1D360, 0x1D400, 0x1D800, 0x1E800, 0x1EE00,
            0x1F000, 0x1F030, 0x1F0A0, 0x1F100, 0x1F200, 0x1F300, 0x1F600, 0x1F650, 0x1F680, 0x1F700, 0x1F780, 0x1F800, 0x1F900, 0x20000, 0x2A700, 0x2B740,
            0x2B820, 0x2F800, 0xE0000, 0xE0100, 0xF0000, 0x100000
    };
}

