// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/08/2014 by nislamov

// Parts of this file are derived, by Aspose, from .NET Framework code. Microsoft publishes that source
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/string.cs
//
// Those parts: Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see THIRD-PARTY-NOTICES.md in the root of this repository.

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.msChar;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.msArray;
import com.aspose.ms.System.Text.msStringBuilder;
import java.lang.Class;
import com.aspose.ms.System.Convert;
import java.lang.NumberFormatException;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.IO.Stream;


/// <summary>
/// <para>Contains simple string utility methods (autoportable).</para>
/// <para>Also see <see cref="Aspose.Bidi.UnicodeUtil"/> for some more utility functions.</para>
/// </summary>
public /*static*/ class StringUtil
{
	/* Simulation of static class by using private constructor */
	private StringUtil()
	{}

    /// <summary>
    /// Converts the character to lower case if it is an upper-case ASCII letter ('A'..'Z').
    /// Otherwise, leaves the character unchanged.
    /// </summary>
    public static char asciiLowerCase(char c)
    {
        return isAsciiUpperCase(c)
            ? (char)(c - 'A' + 'a')
            : c;
    }

    /// <summary>
    /// Determines whether all ASCII letters in the specified text are lower case.
    /// </summary>
    public static boolean isAsciiLowerCase(String text)
    {
        if (text == null)
            return true;

        for (int i = 0; i < text.length(); i++)
        {
        	char c = text.charAt(i);
            if (isAsciiUpperCase(c))
            {
                return false;
            }
        }
        return true;
    }

    /// <summary>
    /// Converts all upper-case ASCII letters in a string to lower case.
    /// </summary>
    public static String asciiLowerCase(String text)
    {
        // SPEED. We optimistically assume that the source text is already converted and contains no upper-case ASCII
        // letters. This is usually true in scenarios where this method is used (parsing of CSS and HTML, for example).
        int i = 0;
        while (i < text.length())
        {
            if (isAsciiUpperCase(text.charAt(i)))
            {
                // Found an upper-case ASCII letter. Conversion is needed.
                break;
            }
            ++i;
        }

        // The fast path. No conversion is needed, because the source string contains no upper-case ASCII letters.
        if (i == text.length())
        {
            return text;
        }

        // The slow path.
        // SPEED. It's faster to work with a char array than with a StringBuilder.
        char[] result = new char[text.length()];

        // Copy the beginning of the source string that contains no upper-case ASCII letters.
        msString.copyTo(text, 0, result, 0, i);

        // Convert the rest of the string.
        while (i < text.length())
        {
            result[i] = asciiLowerCase(text.charAt(i));
            ++i;
        }

        return new String(result);
    }

    /// <summary>
    /// Returns true if the character belongs to the Private Use Unicode Category.
    /// </summary>
    public static boolean isPrivateUseCategory(char c)
    {
        return StringUtilPal.isPrivateUseCategory(c);
    }

    /// <summary>
    /// Returns true if the string is not null and not empty.
    /// </summary>
    public static boolean hasChars(String value)
    {
        return !msString.isNullOrEmpty(value);
    }

    /// <summary>
    /// Truncates a string from the right, then appends '...' for the total specified length.
    /// </summary>
    /// <param name="s">A string.</param>
    /// <param name="length">Length desired.</param>
    public static String ellipsisize(String s, int length)
    {
        final String ELLIPSIS = "...";
        return (length <= 0 || !hasChars(s)) ? ""
            : ((length >= s.length()) ? s
                : ((length <= ELLIPSIS.length()) ? s.substring((0), (0) + (length))
                    : s.substring((0), (0) + (length - ELLIPSIS.length())) + ELLIPSIS));
    }

    /// <summary>
    /// Truncates a string from the right to the specified length.
    /// </summary>
    public static String truncate(String value, int length)
    {
        Debug.assertTrue(length >= 0);

        if (value == null)
            return null;

        if (value.length() <= length)
            return value;

        return value.substring((0), (0) + (length));
    }

    /// <summary>
    /// This is a useful method to call before you, for example combine two strings and one of them might be null.
    /// In .NET such string addition will work okay, but in Java it will add the string "null". Using this method
    /// converts a null string into an empty string and allows to avoid the problem on Java.
    /// </summary>
    public static String nullToEmptyString(String value)
    {
        return (value != null ? value : "");
    }

    /// <summary>
    /// Platform abstract version of 'source.IndexOf(value, StringComparison.InvariantCultureIgnoreCase)'.
    /// </summary>
    public static int indexOfIgnoreCase(String source, String value)
    {
        return msString.indexOf(source, value, StringComparison.ORDINAL_IGNORE_CASE);
    }

    /// <summary>
    /// Check if the text contains the specified string.
    /// Works both for .NET 2.0 and .NET 1.1
    /// </summary>
    public static boolean contains(String text, String searchStr, boolean ignoreCase)
    {
        return msString.indexOf(text, searchStr,
            (ignoreCase) ? StringComparison.ORDINAL_IGNORE_CASE : StringComparison.ORDINAL) >= 0;
    }

    /// <summary>
    /// Detects strings consisting solely of spaces.
    /// </summary>
    public static boolean containsOnlySpaces(String text)
    {
        for (int i = 0; i < text.length(); i++)
        {
        	char c = text.charAt(i);
            if (c != ' ')
                return false;
        }

        return true;
    }

    /// <summary>
    /// Detects strings consisting solely of whitespaces.
    /// </summary>
    public static boolean containsOnlyWhitespaces(String text)
    {
        return (indexOfNonWhitespace(text) == -1);
    }

    /// <summary>
    /// Returns true, if a specified string contains only ASCII characters.
    /// </summary>
    public static boolean containsOnlyAscii(String value)
    {
        for (int i = 0; i < value.length(); i++)
        {
        	char c = value.charAt(i);
            if (c > 127)
                return false;
        }

        return true;
    }

    /// <summary>
    /// Returns the index of the first occurence of a non-whitespace character within the specified string
    /// or <b>-1</b> if not found.
    /// </summary>
    public static int indexOfNonWhitespace(String text)
    {
        return indexOfNonWhitespace(text, 0);
    }

    /// <summary>
    /// Returns the index of the first occurence of a non-whitespace character within the specified string
    /// or <b>-1</b> if not found.
    /// </summary>
    public static int indexOfNonWhitespace(String text, int start)
    {
        if ((text != null) && (start >= 0))
        {
            for (int i = start; i < text.length(); i++)
            {
                if (!msChar.isWhiteSpace(text.charAt(i)))
                    return i;
            }
        }

        return -1;
    }

    /// <summary>
    /// Returns true, if a specified string builder consists solely of whitespaces.
    /// </summary>
    public static boolean containsOnlyWhitespaces(StringBuilder text)
    {
        return (indexOfNonWhitespace(text) == -1);
    }

    /// <summary>
    /// Returns the index of the first occurence of a non-whitespace character within the specified string
    /// builder or <b>-1</b> if not found.
    /// </summary>
    public static int indexOfNonWhitespace(StringBuilder text)
    {
        if (text != null)
        {
            for (int i = 0; i < text.length(); i++)
            {
                if (!msChar.isWhiteSpace(text.charAt(i)))
                    return i;
            }
        }

        return -1;
    }

    /// <summary>
    /// Returns the index of the last occurence of a non-whitespace character within the specified string
    /// builder or <b>-1</b> if not found.
    /// </summary>
    public static int lastIndexOfNonWhitespace(StringBuilder text)
    {
        if (text != null)
        {
            for (int i = text.length() - 1; i >= 0; i--)
            {
                if (!msChar.isWhiteSpace(text.charAt(i)))
                    return i;
            }
        }

        return -1;
    }

    /// <summary>
    /// Returns true, if a specified string builder ends with a specified substring.
    /// </summary>
    public static boolean isEndsWith(StringBuilder text, String endSubstr)
    {
        if ((text == null) || (endSubstr == null))
            return false;

        int i;
        int j;
        for (i = (text.length() - 1), j = (endSubstr.length() - 1); ((i >= 0) && (j >= 0)); i--, j--)
        {
            if (text.charAt(i) != endSubstr.charAt(j))
                return false;
        }

        return (j == -1);
    }

    /// <summary>
    /// Returns the index of the first occurence of a whitespace character within the specified string
    /// or <b>-1</b> if not found.
    /// </summary>
    public static int indexOfWhitespace(String text)
    {
        if (text != null)
        {
            for (int i = 0; i < text.length(); i++)
            {
                if (msChar.isWhiteSpace(text.charAt(i)))
                    return i;
            }
        }

        return -1;
    }

    /// <summary>
    /// Returns the index of the first occurrence of a character that is
    /// categorized as a Unicode letter within the specified string,
    /// or <b>-1</b> if not found.
    /// </summary>
    public static int indexOfLetter(String text)
    {
        if (text != null)
        {
            for (int i = 0; i < text.length(); i++)
            {
                if (Character.isLetter(text.charAt(i)))
                    return i;
            }
        }

        return -1;
    }

    /// <summary>
    /// Returns the index of the first occurrence of a character that is
    /// considered to be a word break within the specified string,
    /// or <b>-1</b> if not found.
    /// </summary>
    public static int indexOfWordBreak(String text)
    {
        if (text != null)
        {
            for (int i = 0; i < text.length(); i++)
            {
                char c = text.charAt(i);
                if (isWordBreakChar(c))
                    return i;
            }
        }

        return -1;
    }

    /// <summary>
    /// Removes all whitespace characters from a specified string.
    /// </summary>
    public static StringBuilder removeAllWhitespaces(String text)
    {
        if (text == null)
            return null;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++)
        {
        	char c = text.charAt(i);
            if (!msChar.isWhiteSpace(c))
                sb.append(c);
        }

        return sb;
    }

    /// <summary>
    /// Returns the index of the first character not equal to a specified one in a string starting from a specified index
    /// or <b>-1</b> if not found.
    /// </summary>
    public static int indexOfNotEqualTo(String text, char value, int start)
    {
        if (text != null)
        {
            for (int i = start; i < text.length(); i++)
            {
                if (text.charAt(i) != value)
                    return i;
            }
        }

        return -1;
    }

    /// <summary>
    /// Detects strings containing any whitespaces.
    /// </summary>
    public static boolean containsAnyWhitespaces(String text)
    {
        for (int i = 0; i < text.length(); i++)
        {
        	char c = text.charAt(i);
            if (msChar.isWhiteSpace(c))
                return true;
        }

        return false;
    }

    public static boolean isWhiteSpace(int ch)
    {
        switch (ch)
        {
            case ' ':
            case '\t':
            case '\r':
            case '\n':
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Converts hex digit chars 0-9, A-F, a-f to a correspondent digit 0-15.
    /// </summary>
    public static int hexCharToDigit(char digit)
    {
        int result = (digit > '9') ? (letterToInt(digit) + 10) : (digit - '0');

        if ((result < 0) || (result > 15))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "digit");

        return result;
    }

    /// <summary>
    /// Converts characters 'A'-'Z' (case insensitively) to integers 0-25.
    /// </summary>
    public static int letterToInt(char letter)
    {
        int result = letter - ((letter <= 'Z') ? 'A' : 'a');

        if ((result < 0) || (result > 25))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "letter");

        return result;
    }

    /// <summary>
    /// Checks if a character is a valid Latin letter: 'A'-'Z' or 'a'-'z'.
    /// SPEED Works faster than Char.IsLetter.
    /// </summary>
    /// <remarks>
    /// <paramref name="ch"/> is int to support both characters and bytes read by <see cref="Stream.ReadByte"/>.
    /// </remarks>
    public static boolean isLetter(int ch)
    {
        return
            ((ch >= 'a') && (ch <= 'z')) ||
            ((ch >= 'A') && (ch <= 'Z'));
    }

    /// <summary>
    /// Checks if a character is a valid Latin letter: 'A'-'Z' or 'a'-'z', or
    /// if a character is a valid decimal digit character: 0-9
    /// </summary>
    /// <remarks>
    /// <paramref name="ch"/> is int to support both characters and bytes read by <see cref="Stream.ReadByte"/>.
    /// </remarks>
    public static boolean isLetterOrDigit(int ch)
    {
        return isLetter(ch) || isDigit(ch);
    }

    /// <summary>
    /// Checks if a character is a valid hex digit character: 0-9, A-F, or a-f.
    /// </summary>
    public static boolean isHexDigit(char ch)
    {
        return
            ((ch >= '0') && (ch <= '9')) ||
            ((ch >= 'A') && (ch <= 'F')) ||
            ((ch >= 'a') && (ch <= 'f'));
    }

    /// <summary>
    /// Returns a value indicating whether the specified character is treated as double quote by MS Word.
    /// </summary>
    public static boolean isDoubleQuote(char ch)
    {
        switch (ch)
        {
            case '"':
            case '«':
            case '“':
            case '„':
            case '»':
            case '”':
            case '‟':
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns a value indicating whether the specified character represents a punctuation mark.
    /// </summary>
    public static boolean isPunctuationMark(char c)
    {
        switch (c)
        {
            case '!':
            case '?':
            case '.':
            case ',':
            case ':':
            case ';':
            case '¡':
            case '¿':
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns a value indicating whether the specified character represents a dash (see https://en.wikipedia.org/wiki/Dash).
    /// </summary>
    public static boolean isDash(char ch)
    {
        switch (ch)
        {
            case '‒':
            case '–':
            case '—':
            case '―':
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Checks if string is valid positive decimal number.
    /// </summary>
    public static boolean isDecimal(String value)
    {
        for (int i = 0; i < value.length(); i++)
        {
        	char ch = value.charAt(i);
            if (!isDigit(ch))
                return false;
        }

        return true;
    }

    /// <summary>
    /// Checks if string is valid hexadecimal number.
    /// </summary>
    public static boolean isHexadecimal(String value)
    {
        for (int i = 0; i < value.length(); i++)
        {
        	char ch = value.charAt(i);
            if (!isHexDigit(ch))
                return false;
        }

        return true;
    }

    /// <summary>
    /// Converts a byte array into the string representation, e.g. "B601FE00".
    /// </summary>
    /// <param name="bytes">Array of bytes to convert.</param>
    /// <param name="startIndex">The index of the byte to start the conversion from.</param>
    /// <param name="length">Number of bytes to convert.</param>
    /// <param name="reversed">Put bytes to string in reversed order.</param>
    /// <returns>String representation of the sequence of bytes, e.g. "B601FE00".</returns>
    public static String bytesToHex(byte[] bytes, int startIndex, int length, boolean reversed)
    {
        StringBuilder stringBuilder = new StringBuilder(length * 2);

        int step = (reversed) ? -1 : +1;

        if (reversed)
            startIndex += length - 1;

        for (int i = 0; i < length; i++)
        {
            stringBuilder.append((char[])byteToHex(bytes[startIndex]));
            startIndex += step;
        }

        return stringBuilder.toString();
    }

    /// <summary>
    /// Converts a byte into char array (with length of 2) of its string representation, e.g. 0x2A to {'2', 'A'}.
    /// </summary>
    public static char[] byteToHex(byte b)
    {
        char[] hex = new char[2];

        hex[0] = G_HEX_CHARS[((b & 0xFF) >>> 4) & 0x0F]; // Need to mask because byte is signed on Java.
        hex[1] = G_HEX_CHARS[(b & 0xFF) & 0x0F];

        return hex;
    }

    /// <summary>
    /// Converts byte array into the string representation, e.g. "B601FE00".
    /// </summary>
    /// <param name="bytes">Array of bytes to convert.</param>
    /// <returns>String representation of the sequence of bytes, e.g. "B601FE00".</returns>
    public static String bytesToHex(byte[] bytes)
    {
        return bytesToHex(bytes, 0, bytes.length, false);
    }

    /// <summary>
    /// Converts hex string into the byte array.
    /// Skip characters that is not valid hex characters.
    /// </summary>
    public static byte[] hexToBytes(String hexString)
    {
        int arrayLength = hexString.length() / 2;
        byte[] array = new byte[arrayLength];

        char high = '\u0000';
        int j = 0;
        for (int i = 0; i < hexString.length(); i++)
        {
        	char ch = hexString.charAt(i);
            if (!isHexDigit(ch))
                continue;

            if (high == '\u0000')
            {
                high = ch;
            }
            else
            {
                char low = ch;
                int v1 = hexCharToDigit(high);
                int v2 = hexCharToDigit(low);
                array[j++] = (byte)((v1 << 4) | v2);
                high = '\u0000';
            }
        }

        // Some characters were stripped from string. Return new array.
        if (j < arrayLength)
        {
            byte[] newArray = new byte[j];
            System.arraycopy((array), 0, (newArray), 0, (j));
            return newArray;
        }

        return array;
    }

    /// <summary>
    /// Compares strings using case insensitive compare and returns true if they are same.
    /// Use this method because it works for .NET 2.0 and .NET 1.1 and Java.
    /// </summary>
    public static boolean equalsIgnoreCase(String a, String b)
    {
        return msString.equals(a, b, StringComparison.ORDINAL_IGNORE_CASE);
    }

    /// <summary>
    /// Compares strings using ordinal case insensitive compare and returns true if they are same.
    /// </summary>
    public static boolean equalsOrdinalIgnoreCase(String s1, String s2)
    {
        return equalsOrdinalIgnoreCase(s1, s2, false);
    }
    private static boolean equalsOrdinalIgnoreCase(String s1, String s2, boolean isStartsWith)
    {
        if (msObject.referenceEquals(s1, s2))
            return true;

        if ((s1 == null) || (s2 == null))
            return false;

        int n1 = s1.length();
        int n2 = s2.length();
        if ((isStartsWith && (n1 < n2)) || (!isStartsWith && (n1 != n2)))
            return false;

        return compareOrdinalIgnoreCaseCore(s1, s2, n2) == 0;
    }
    private static int compareOrdinalIgnoreCaseCore(String s1, String s2, int n)
    {
        for (int i = 0; i < n; i++)
        {
            int c1 = s1.charAt(i);
            int c2 = s2.charAt(i);

            // If chars are equal there is no need to compare them case-insensitively.
            if (c1 == c2)
                continue;

            // Case-insensitive comparison of ascii chars taken from Rotor COMString::CaseInsensitiveCompHelper.
            if ((c1 | c2) <= 0x7F)
            {
                // uppercase both chars.
                if (c1 >= 'a' && c1 <= 'z')
                    c1 ^= 0x20;
                if (c2 >= 'a' && c2 <= 'z')
                    c2 ^= 0x20;

                // Return the (case-insensitive) difference between them.
                if (c1 != c2)
                    return (c1 - c2);
            }
            else
            {
                // Non-ascii chars comparison. Added for compatibility.
                // Code taken from java.lang.String.CaseInsensitiveComparator.
                c1 = msChar.toUpperInvariant((char)c1);
                c2 = msChar.toUpperInvariant((char)c2);
                if (c1 != c2)
                {
                    c1 = msChar.toLowerInvariant((char)c1);
                    c2 = msChar.toLowerInvariant((char)c2);
                    if (c1 != c2)
                        return (c1 - c2);
                }
            }
        }

        return 0;
    }

    /// <summary>
    /// Compares strings using ordinal case insensitive compare and returns true if one string starts with the other.
    /// </summary>
    public static boolean startsWithOrdinalIgnoreCase(String source, String prefix)
    {
        return equalsOrdinalIgnoreCase(source, prefix, true);
    }

    /// <summary>
    /// Checks if a character is a valid decimal digit character: 0-9.
    /// SPEED Works faster than Char.IsDigit.
    /// </summary>
    /// <remarks>
    /// <paramref name="ch"/> is int to support both characters and bytes read by Stream.ReadByte.
    /// </remarks>
    public static boolean isDigit(int ch)
    {
        return (ch >= '0') && (ch <= '9');
    }

    /// <summary>
    /// Removes all non-digit characters from the end of the string until the first digit character.
    /// </summary>
    public static String trimEndNonDigits(String input)
    {
        for (int i = input.length() - 1; i >= 0; i--)
        {
            if (Character.isDigit(input.charAt(i)))
                return input.substring((0), (0) + (i + 1));
        }
        return input;
    }

    /// <summary>
    /// Removes leading occurrences of whitespace characters of a specified length.
    /// </summary>
    public static String trimStart(String value, int length)
    {
        int index = 0;
        while ((index < length) && (index < value.length()) && msChar.isWhiteSpace(value.charAt(index)))
            index++;

        return value.substring(index);
    }

    /// <summary>
    /// Removes leading occurrences of whitespace characters from a specified string builder.
    /// </summary>
    public static void trimStart(StringBuilder sb)
    {
        int index = 0;
        while ((index < sb.length()) && msChar.isWhiteSpace(sb.charAt(index)))
            index++;

        sb.delete((0), (0) + (index));
    }

    /// <summary>
    /// Removes all the trailing occurrences of whitespace characters from a specified string builder.
    /// </summary>
    public static void trimEnd(StringBuilder sb)
    {
        int index = sb.length() - 1;
        while ((index >= 0) && msChar.isWhiteSpace(sb.charAt(index)))
            index--;

        sb.delete((index + 1), (index + 1) + (sb.length() - (index + 1)));
    }

    /// <summary>
    /// Returns a new string containing characters
    /// of the specified string in the reversed order.
    /// </summary>
    public static String reverseString(String str)
    {
        char[] chars = str.toCharArray();
        msArray.reverse(chars);
        return new String(chars);
    }

    /// <summary>
    /// Remove all occurences of substring in the string.
    /// For instance, remove("abcdef defde", "def") returns "abc de".
    /// Don't use regexp, so this code run faster than str.replace(substr, string.Empty),
    /// at least in Java.
    /// </summary>
    public static String removeSubstring(String str, String substr)
    {
        int len1 = str.length();
        int len2 = substr.length();

        if (len1 == 0 || len2 == 0)
            return str;

        int n = 0;
        int i = 0;
        // at first, count occurrences
        while (i < len1 - len2 + 1)
        {
            boolean match = true;
            for (int p = 0; p < len2; p++)
            {
                if (str.charAt(i + p) != substr.charAt(p))
                {
                    match = false;
                    break;
                }
            }
            if (match)
            {
                i += len2;
                n++;
            }
            else
                i++;
        }

        if (n == 0)
            return str;

        // allocate and fill new buffer
        char[] result = new char[len1 - n*len2];
        i = 0;
        int j = 0;
        while (i < len1 - len2 + 1)
        {
            boolean match = true;
            for (int p = 0; p < len2; p++)
            {
                if (str.charAt(i + p) != substr.charAt(p))
                {
                    match = false;
                    break;
                }
            }
            if (match)
            {
                i += len2;
            }
            else
            {
                result[j] = str.charAt(i);
                i++;
                j++;
            }
        }

        if (i < len1)
        {
            for (int p = i; p < len1; p++)
                result[j++] = str.charAt(p);
        }

        return new String(result);
    }

    /// <summary>
    /// Helps to format comma separated list.
    /// </summary>
    public static String appendCommaSeparated(String list, String item)
    {
        if (!hasChars(list))
            return item;

        if (!hasChars(item))
            return list;

        return msString.format("{0},{1}", list, item);
    }

    /// <summary>
    /// Helps to porting Environment.NewLine in Java.
    /// </summary>
    public static String getNewLine() throws Exception
    {
        if (PlatformUtilPal.isUnixLike())
            return "\n";
        else
            return "\r\n";
    }

    /// <summary>
    /// Compares string objects using ordinal case insensitive compare.
    /// </summary>
    public static int compareOrdinalIgnoreCase(Object a, Object b)
    {
        // Object equality and null string comparison used only in .Net. Taken from Rotor's String.Compare():
        // Our (.Net) paradigm is that null sorts less than any other string and
        // that two nulls sort as equal. (sk plus: two equal constant strings always stored in a single object).
        if (msObject.referenceEquals(a, b))
            return 0;
        if (a == null)
            return -1;    // null < non-null
        if (b == null)
            return 1;     // non-null > null

        String s1 = (String)a;
        String s2 = (String)b;
        int n1 = s1.length();
        int n2 = s2.length();

        int result = StringUtil.compareOrdinalIgnoreCaseCore(s1, s2, Math.min(n1, n2));

        return (result != 0) ? result : (n1 - n2);
    }

    /// <summary>
    /// Compares strings using CompareOptions.StringSort.
    /// </summary>
    /// <remarks>
    /// MS Word seems to use this method.
    /// The default method causes different results, WORDSNET-4640 is an example.
    /// </remarks>
    public static int compareStringSort(String a, String b)
    {
        return StringUtilPal.compareStringSort(a, b);
    }

    /// <summary>
    /// Outputs fields of the given object to a string. Used in logging.
    /// </summary>
    public static String objectToLogString(Object obj, String name)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	StringBuilder builder = new StringBuilder();
        	msStringBuilder.appendFormat(builder, "{0} Start\n", name);

        	Class type = obj.getClass();
        	java.lang.reflect.Field[] fields = type.getFields();
        	for (java.lang.reflect.Field field : fields)
            	msStringBuilder.appendFormat(builder, "{0}:{1}, 0x{1:X}\n", field.getName(), field.get(obj));

        	msStringBuilder.appendFormat(builder, "{0} End", name);
        	return builder.toString();
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    /// <summary>
    /// Formats the text string according to the specified char case.
    /// </summary>
    public static String formatCharCase(String s, /*CharCase*/int charCase)
    {
        switch (charCase)
        {
            case CharCase.UPPER:
                return s.toUpperCase();
            case CharCase.LOWER:
                return s.toLowerCase();
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
    /// Capitalizes first non whitespace character of the string.
    /// </summary>
    public static String capitalizeString(String s)
    {
        if (s == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "s");

        StringBuilder builder = new StringBuilder(s);
        for (int i = 0; i < builder.length(); i++)
        {
            char c = builder.charAt(i);
            if (!isWordBreakChar(c))
            {
                builder.setCharAt(i, Character.toUpperCase(c));
                break;
            }
        }

        return builder.toString();
    }

    /// <summary>
    /// Capitalizes first non whitespace character that follows every whitespace.
    /// </summary>
    private static String capitalizeWords(String s)
    {
        if (s == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "s");

        boolean isInsideWord = false;
        // Use of StringBuilder seems to be simpler here. We can't use string.Split and string.Join
        // because different types and amount of whitespace is possible between words.
        StringBuilder builder = new StringBuilder(s);

        for (int i = 0; i < builder.length(); i++)
        {
            char c = builder.charAt(i);
            if (isWordBreakChar(c))
            {
                isInsideWord = false;
            }
            else if (!isInsideWord)
            {
                builder.setCharAt(i, Character.toUpperCase(c));
                isInsideWord = true;
            }
            else
            {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }

    /// <summary>
    /// Tries to convert the base64 string to a byte array.
    /// </summary>
    /// <remarks>
    /// This is a wrapper over <see cref="Convert.FromBase64String"/>.
    /// The method doesn't make an attempt to restore corrupted base64 string.
    /// </remarks>
    /// <returns>
    /// Returns a byte array that is equivalent to base64 string or an empty byte array if the conversion failed.
    /// </returns>
    public static byte[] tryConvertFromBase64(String base64String)
    {
        return tryConvertFromBase64String(base64String, false);
    }

    /// <summary>
    /// Safely converts the base64 string to a byte array.
    /// </summary>
    /// <remarks>This is a safe wrapper over <see cref="Convert.FromBase64String"/>.</remarks>
    /// <returns>
    /// Returns a byte array that is equivalent to base64 string or an empty byte array if the conversion failed.
    /// </returns>
    public static byte[] convertFromBase64Safe(String base64String)
    {
        return tryConvertFromBase64String(base64String, true);
    }

    /// <summary>
    /// Tries to convert the base64 string to a byte array either with an attempt to restore
    /// currupted base64 string or not.
    /// </summary>
    /// <remarks>
    /// The method can make an attempt to restore corrupted base64 string.
    /// This behavior is turned on/off by restoreBase64String flag.
    /// </remarks>
    private static byte[] tryConvertFromBase64String(String base64String, boolean restoreBase64String)
    {
        if (msString.isNullOrEmpty(base64String))
        {
            return new byte[0];
        }

        String valueToConvert = restoreBase64String ? restoreBase64String(base64String) : base64String;
        try
        {
            return Convert.fromBase64String(valueToConvert);
        }
        catch (NumberFormatException e)
        {
            return new byte[0];
        }
    }

    /// <summary>
    /// Tries to restore corrupted base64 string.
    /// </summary>
    private static String restoreBase64String(String base64String)
    {
        // The Base64 string must contain only the following characters: A-Z a-z 0-9 + / =
        final String REGEX_PATTERN = "[^A-Za-z0-9+/=]";
        Regex regex = new Regex(REGEX_PATTERN);

        // Remove all symbols that are not in the allowed set.
        String restoredString = regex.replace(base64String, "");
        // WORDSNET-27395 Some corrupted or incorrectly created base64 strings may contain 3 paddig characters '='
        // at the end to revert to the original encoding, mostly created with javascript.
        // Trim all '=' characters when we try to repair the base64 string.
        // For details, see https://www.w3.org/TR/xmlschema-2/#base64Binary.
        restoredString = msString.trimEnd(restoredString, '=');

        // The Base64 string length must be a multiple of 4. If it is not, add '='.
        // It helps determine how many padding characters ('=') are needed to make the length a multiple of 4.
        int paddings = (4 - restoredString.length() % 4) % 4;
        restoredString = StringUtilPal.padRight(restoredString, restoredString.length() + paddings, '=');

        return restoredString;
    }

    /// <summary>
    /// Converts ASCII numbers and letters to full-width numbers and letters.
    /// </summary>
    /// <param name="value">A value to convert.</param>
    /// <returns>A converted <paramref name="value"/>.</returns>
    private static String toFullWidth(String value)
    {
        if (!hasChars(value))
        {
            return "";
        }

        StringBuilder converted = new StringBuilder();
        for (int i = 0; i < value.length(); i++)
        {
        	char c = value.charAt(i);
            if (c > 0x20 && c < 0x7F)
            {
                converted.append((char)(0xFF00 | (c - 0x20)));
            }
            else if (c == 0x20)
            {
                converted.append((char)0x3000);
            }
            else
            {
                converted.append(c);
            }
        }

        return converted.toString();
    }

    /// <summary>
    /// Trim ending CRLF characters from memory stream
    /// Used for C++ port where libxml2 generated documents have such ending
    /// Do nothing on C# anc Java
    /// </summary>
    public static Stream trimRightCrLfStream(Stream stream)
    {
        return stream;
    }

    /// <summary>
    /// Returns a value indicating whether the specified character is considered to be a word break
    /// in one of the capitalization algorithms.
    /// </summary>
    /// <param name="c"></param>
    public static boolean isWordBreakChar(char c)
    {
        // WORDSNET-7593 MS Word treats punctuation characters as word breaks.
        // It seems like the right way here is to return Char.IsWhiteSpace(c) || Char.IsPunctuation(c) || Char.IsSymbol(c)
        // but (!Char.IsLetterOrDigit(c)) is more perfomant although it might be not right in some rare cases.
        return !Character.isLetterOrDigit(c);
    }

    /// <summary>
    /// Returns a value indicating whether the specified character is an upper-case ASCII letter ('A'..'Z').
    /// </summary>
    private static boolean isAsciiUpperCase(char c)
    {
        return (c >= 'A') && (c <= 'Z');
    }

    // WORDSJAVA-3205 This need for Java testing
    public static boolean isNullOrEmpty(String str)
    {
        return msString.isNullOrEmpty(str);
    }

    private static /*final*/ char[] G_HEX_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
}

