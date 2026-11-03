// Parts of this file are a Java port, made by Aspose, of .NET Framework code. Microsoft publishes that source
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/char.cs
//
// Those parts: Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

package com.aspose.ms.System;

/**
 * User: sk
 * Date: Jan 23, 2007
 */
public final class msChar
{
	public static boolean isNumber(char currentChar)
	{
		return Character.isDigit(currentChar);
	}

    private msChar() {}

	//
	//
	//  Determines if the given character is a white space character.

	/**
	 * C# System.Char.IsWhiteSpace(char) method ported from Rotor's CharacterInfo.IsWhiteSpace()
	 * because java Character.isWitespace() behave differently.
	 *
	 * Determines if the given character is a white space character.
	 */
	public static boolean isWhiteSpace(char c)
	{
		//IsLatin1(c): characters below or equal U+00ff, which is ASCII + Latin-1 Supplement.
		if (c <= '\u00ff')
		{
			// There are characters which belong to UnicodeCategory.Control but are considered as white spaces.
			// We use code point comparisons for these characters here as a temporary fix.

			// U+0009 = <control> HORIZONTAL TAB
			// U+000a = <control> LINE FEED         //java: '\n'
			// U+000b = <control> VERTICAL TAB
			// U+000c = <contorl> FORM FEED         //java: '\f'
			// U+000d = <control> CARRIAGE RETURN   //java: '\r'
			// U+0085 = <control> NEXT LINE 
			// U+00a0 = NO-BREAK SPACE
			if ((c == ' ') || (c >= '\u0009' && c <= '\r' /*'\u000d'*/) || c == '\u00a0' || c == '\u0085')
			    return true;

		}

		// In Unicode 3.0, U+2028 is the only character which is under the category "LineSeparator".
		// And U+2029 is the only character which is under the category "ParagraphSeparator".
		switch (Character.getType(c))			//((int)GetUnicodeCategory(c))
		{
			case Character.SPACE_SEPARATOR:		//((int)UnicodeCategory.SpaceSeparator):
			case Character.LINE_SEPARATOR:		//((int)UnicodeCategory.LineSeparator):
			case Character.PARAGRAPH_SEPARATOR: //((int)UnicodeCategory.ParagraphSeparator):
				return true;
		}

		return false;
	}

	/**
	 * C# System.Char.IsWhiteSpace(string,int).
	 *
	 * Indicates whether the character at the specified position in a specified string is categorized as white space.
	 */
	public static boolean isWhiteSpace(String s, int index)
	{
	    if (s == null)
	        throw new NullPointerException("s");
	    if (index >=  s.length())
	        throw new IllegalArgumentException("index");

	    return isWhiteSpace(s.charAt(index));
	}

	/**
	 * C# System.Char.IsLower(String s, int index).
	 */
	public static boolean isLower(String s, int index)
	{
	    if (s == null)
	        throw new NullPointerException("s");
	    if (index >=  s.length())
	        throw new IllegalArgumentException("index");

	    return Character.isLowerCase(s.charAt(index));
	}

	/**
     * C# System.Char.IsPunctuation(char c).
     *
     * Determines whether a character is a punctuation mark.
     */
    public static boolean isPunctuation(char c)
    {
        // FIX WORDSJAVA-1213: Spare new line symbol in WordArt in DrawingML.
        // .NET returns false for invisible mark character. Let's do the same.
        if (c == HIDDEN_FORMATTING_MARK)
            return false;

        switch (Character.getType(c))
        {
            case Character.CONNECTOR_PUNCTUATION:
            case Character.DASH_PUNCTUATION:
            case Character.START_PUNCTUATION:
            case Character.END_PUNCTUATION:
            case Character.INITIAL_QUOTE_PUNCTUATION:
            case Character.FINAL_QUOTE_PUNCTUATION:
            case Character.OTHER_PUNCTUATION:
                return true;
        }

        return false;
    }

	/// <summary>
	/// Helper which is equal to char.ToUpper(c, CultureInfo.InvariantCulture)
	/// </summary>
	public static char toUpperInvariant(char c)
	{
		// WORDSJAVA-1678: "µ" (\u00b5) is an exception
		if (c == '\u00b5')
			return c;

		// RK I hope it is okay not to worry about "invariant culture" here.
		return Character.toUpperCase(c);
	}

	/// <summary>
	/// Helper which is equal to char.ToLower(c, CultureInfo.InvariantCulture)
	/// </summary>
	public static char toLowerInvariant(char c)
	{
		// WORDSJAVA-1678: "µ" (\u00b5) is an exception
		if (c == '\u00b5')
			return c;

		// RK I hope it is okay not to worry about "invariant culture" here.
		return Character.toLowerCase(c);
	}

	/// <summary>
	/// Analog for char.ConvertFromUtf32(int). Converts the specified Unicode code point into a UTF-16 encoded string.
	/// </summary>
	public static String convertFromUtf32(int utf32Char)
	{
		int LEAD_SURROGATE_MIN_VALUE = 0xD800;
		int TAIL_SURROGATE_MIN_VALUE = 0xDC00;
		int SUPPLEMENTARY_MIN_VALUE = 0x10000;
		int SUPPLEMENTARY_MAX_VALUE = 0x10FFFF;

		if (!(utf32Char >= 0 && utf32Char <= SUPPLEMENTARY_MAX_VALUE))
			throw new IllegalArgumentException("Parameter name: " + "utf32Char");

		if ((utf32Char >= SUPPLEMENTARY_MIN_VALUE && utf32Char <= SUPPLEMENTARY_MAX_VALUE))
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
	/// Converts the value of a UTF-16 encoded character or surrogate pair at a specified position in a string into a
	/// Unicode code point.
	/// </summary>
	public static int convertToUtf32(String s, int position) {
		if (s == null)
			throw new NullPointerException("s");
		if (position < 0 || position >= s.length())
			throw new IllegalArgumentException("Parameter name: " + "position");

		return isSurrogatePair(s, position)
				// A valid surrogate pair found. It is a character from one of the Supplementary Planes.
				? convertToUtf32(s.charAt(position), s.charAt(position + 1))
				// No valid surrogate pair found. It is a character from the Basic Multilingual Plane or a broken surrogate pair.
				: s.charAt(position);
	}

	/// <summary>
	/// Converts the value of a UTF-16 encoded surrogate pair into a Unicode code point.
	/// </summary>
	private static int convertToUtf32(char leadSurrogate, char tailSurrogate) {
		if (!isLeadSurrogate(leadSurrogate))
			throw new IllegalArgumentException("Parameter name: " + "leadSurrogate");
		if (!isTailSurrogate(tailSurrogate))
			throw new IllegalArgumentException("Parameter name: " + "tailSurrogate");

		return (leadSurrogate - LEAD_SURROGATE_MIN_VALUE) * (1 << 10) + (tailSurrogate - TAIL_SURROGATE_MIN_VALUE) +
				SUPPLEMENTARY_MIN_VALUE;
	}

	/// <summary>
	/// Indicates whether the specified UTF-16 char is a lead surrogate.
	/// </summary>
	private static boolean isLeadSurrogate(char utf16Char) {
		return (utf16Char >= LEAD_SURROGATE_MIN_VALUE && utf16Char <= LEAD_SURROGATE_MAX_VALUE);
	}


	/// <summary>
	/// Indicates whether the specified UTF-16 char is a tail surrogate.
	/// </summary>
	private static boolean isTailSurrogate(char utf16Char) {
		return (utf16Char >= TAIL_SURROGATE_MIN_VALUE && utf16Char <= TAIL_SURROGATE_MAX_VALUE);
	}

	/// <summary>
	/// Indicates whether two adjacent chars at a specified position in a string form a surrogate pair.
	/// </summary>
	public static boolean isSurrogatePair(String s, int position) {
		if (s == null)
			throw new NullPointerException("s");
		if (position < 0 || position >= s.length())
			throw new IllegalArgumentException("Parameter name: " + "position");

		return isLeadSurrogate(s.charAt(position)) &&
				((position + 1) < s.length()) &&
				isTailSurrogate(s.charAt(position + 1));
	}

	private static final int LEAD_SURROGATE_MIN_VALUE = 0xD800;
	private static final int LEAD_SURROGATE_MAX_VALUE = 0xDBFF;
	private static final int TAIL_SURROGATE_MIN_VALUE = 0xDC00;
	private static final int TAIL_SURROGATE_MAX_VALUE = 0xDFFF;
	private static final int SUPPLEMENTARY_MIN_VALUE = 0x10000;
	private static final int SUPPLEMENTARY_MAX_VALUE = 0x10FFFF;

	private static final char HIDDEN_FORMATTING_MARK = 182;
}
