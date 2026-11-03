// Parts of this file are a Java port, made by Aspose, of .NET Framework code. Microsoft publishes that source
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/string.cs
//
// Those parts: Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

//Konstantin Sidorenko Jan 18, 2007

package com.aspose.ms.System;

import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.java.text.CollatorProvider;

import java.text.Collator;
import java.text.MessageFormat;

public class msString
{
	private msString() {}

	public static boolean isNullOrEmpty(java.lang.String value)
	{
        return value == null || value.isEmpty();
	}

	public static boolean isNullOrWhiteSpace(java.lang.String value)
	{
		return value == null || value.trim().isEmpty();
	}

    /**
	 * Emulates String.Equals() method - does not throw if first string is null.
	 * Returns true if both strings are null.
	 */
	public static boolean equals(java.lang.String a, java.lang.String b)
	{
        if ((Object)a == (Object)b)
            return true;

        return ((a != null) && (b != null) && a.equals(b));
	}

	/**
	 * Emulates String.Equals() method - does not throw if first string is null.
	 * Returns true if both strings are null.
	 */
	public static boolean equals(java.lang.String a, java.lang.String b, StringComparison comparison)
	{
		if ((Object)a == (Object)b)
			return true;

		if (a == null || b == null)
			return false;

		switch (comparison)
		{
			case CURRENT_CULTURE:
			case INVARIANT_CULTURE:
			case ORDINAL:
				return a.equals(b);
			case CURRENT_CULTURE_IGNORE_CASE:
			case INVARIANT_CULTURE_IGNORE_CASE:
			case ORDINAL_IGNORE_CASE:
				return a.toLowerCase().equals(b.toLowerCase());
		}

		// Can't reach here.
		return a.equals(b);
	}

	public static boolean endsWith(java.lang.String a, java.lang.String b, StringComparison comparison)
	{
		switch (comparison)
		{
			case CURRENT_CULTURE:
			case INVARIANT_CULTURE:
			case ORDINAL:
				return a.endsWith(b);
			case CURRENT_CULTURE_IGNORE_CASE:
			case INVARIANT_CULTURE_IGNORE_CASE:
			case ORDINAL_IGNORE_CASE:
				return a.toLowerCase().endsWith(b.toLowerCase());
		}

		// Can't reach here.
		return a.endsWith(b);
	}

	public static int lastIndexOf(java.lang.String a, java.lang.String b, StringComparison comparison)
	{
		switch (comparison)
		{
			case CURRENT_CULTURE:
			case INVARIANT_CULTURE:
			case ORDINAL:
				return a.lastIndexOf(b);
			case CURRENT_CULTURE_IGNORE_CASE:
			case INVARIANT_CULTURE_IGNORE_CASE:
			case ORDINAL_IGNORE_CASE:
				return a.toLowerCase().lastIndexOf(b.toLowerCase());
		}

		// Can't reach here.
		return a.lastIndexOf(b);
	}

	public static int lastIndexOf(java.lang.String a, java.lang.String b, int index, StringComparison comparison)
	{
		switch (comparison)
		{
			case CURRENT_CULTURE:
			case INVARIANT_CULTURE:
			case ORDINAL:
				return a.lastIndexOf(b,index);
			case CURRENT_CULTURE_IGNORE_CASE:
			case INVARIANT_CULTURE_IGNORE_CASE:
			case ORDINAL_IGNORE_CASE:
				return a.toLowerCase().lastIndexOf(b.toLowerCase(),index);
		}

		// Can't reach here.
		return a.lastIndexOf(b,index);
	}

	/**
	 * Emulates string.Compare(a, b). Uses the culture-sensitive comparison.
     *
     * In .NET I think this is "word sort":
     * Word sort performs a culture-sensitive comparison of strings. Certain nonalphanumeric characters might have special
     * weights assigned to them. For example, the hyphen ("-") might have a very small weight assigned to it so that "coop"
     * and "co-op" appear next to each other in a sorted list.
     *
	 */
	public static int compare(java.lang.String a, java.lang.String b)
	{
		// Object equality and null string comparison used only in .Net. Taken from Rotor's String.Compare():
		// "Our (.Net) paradigm is that null sorts less than any other string and
		// that two nulls sort as equal." (sk plus: two equal constant strings always stored in a single object).
		if ((Object)a == (Object)b)
		    return 0;
		if (a == null)
		    return (-1);    // null < non-null
		if (b == null)
		    return (1);     // non-null > null

        // This is the culture-sensitive comparison.
        return CollatorProvider.getWordSortCollator().compare(a, b);
	}

    /**
     * Emulates string.Compare(a, b, ignoreCase).
     * It does culture-sensitive compare.
     */
    public static int compare(java.lang.String a, java.lang.String b, boolean ignoreCase)
    {
        if (!ignoreCase)
            return compare(a, b);

        if ((Object)a == (Object)b)
            return 0;
        if (a == null)
            return (-1);    // null < non-null
        if (b == null)
            return (1);     // non-null > null

		// This is the culture-sensitive ignore-case comparison.
		return CollatorProvider.getWordSortIgnoreCaseCollator().compare(a, b);
    }

	/**
	 * Provides ordinal string compare. There is no c# analogue
	 */
	private static int compareOrdinal(java.lang.String a, java.lang.String b, boolean ignoreCase)
	{
		if ((Object)a == (Object)b)
			return 0;
		if (a == null)
			return (-1);    // null < non-null
		if (b == null)
			return (1);     // non-null > null

		return ignoreCase ? a.compareToIgnoreCase(b) : a.compareTo(b);
	}

	/**
	 * Provides invarian culture string compare. There is no c# analogue
	 */
	private static int compareInvariant(java.lang.String a, java.lang.String b, boolean ignoreCase)
	{
		if ((Object)a == (Object)b)
			return 0;
		if (a == null)
			return (-1);    // null < non-null
		if (b == null)
			return (1);     // non-null > null

		Collator collator = ignoreCase ?
				CollatorProvider.getInvariantIgnoreCaseCollator() :
				CollatorProvider.getInvariantCollator();

		return collator.compare(a,b);
	}

	public static int compare(String strA, int indexA, String strB, int indexB, int length)
	{
		return compare(strA, indexA, strB, indexB, length, false);
	}

	public static int compare(String strA, int indexA, String strB, int indexB, int length, StringComparison comparison)
	{
		switch (comparison)
		{
			case CURRENT_CULTURE:
				return compare(strA, indexA, strB, indexB, length, false);
			case CURRENT_CULTURE_IGNORE_CASE:
				return compare(strA, indexA, strB, indexB, length, true);
			case ORDINAL:
				return compareOrdinal(strA, indexA, strB, indexB, length, false);
			case ORDINAL_IGNORE_CASE:
				return compareOrdinal(strA, indexA, strB, indexB, length, true);
			case INVARIANT_CULTURE:
				return compareInvariant(strA, indexA, strB, indexB, length, false);
			case INVARIANT_CULTURE_IGNORE_CASE:
				return compareInvariant(strA, indexA, strB, indexB, length, true);
		}

		// Can't reach here.
		return compare(strA, indexA, strB, indexB, length, false);
	}

	public static int compare(String strA, String strB, StringComparison comparison)
	{
		switch (comparison)
        {
			case CURRENT_CULTURE:
				return compare(strA, strB, false);
			case CURRENT_CULTURE_IGNORE_CASE:
				return compare(strA, strB, true);
			case INVARIANT_CULTURE:
				return compareInvariant(strA, strB, false);
			case INVARIANT_CULTURE_IGNORE_CASE:
				return compareInvariant(strA, strB, true);
			case ORDINAL:
				return compareOrdinal(strA, strB, false);
			case ORDINAL_IGNORE_CASE:
				return compareOrdinal(strA, strB, true);
		}

        // Can't reach here.
		return compare(strA, strB, false);
	}

    public static int compare(String strA, int indexA, String strB, int indexB, int length, boolean ignoreCase)
    {
		if ((Object)strA == (Object)strB)
			return 0;
		if (strA == null)
			return (-1);    // null < non-null
		if (strB == null)
			return (1);     // non-null > null

	    int num = length;
        int num2 = length;

        if (strA.length() - indexA < num)
            num = strA.length() - indexA;

        if (strB.length() - indexB < num2)
            num2 = strB.length() - indexB;

        String a = strA.substring(indexA, indexA + num);
        String b = strB.substring(indexB, indexB + num2);

        return compare(a, b, ignoreCase);
    }

	private static int compareOrdinal(String strA, int indexA, String strB, int indexB, int length, boolean ignoreCase)
	{
		if ((Object)strA == (Object)strB)
			return 0;
		if (strA == null)
			return (-1);    // null < non-null
		if (strB == null)
			return (1);     // non-null > null

		int num = length;
		int num2 = length;

		if (strA.length() - indexA < num)
			num = strA.length() - indexA;

		if (strB.length() - indexB < num2)
			num2 = strB.length() - indexB;

		String a = strA.substring(indexA, indexA + num);
		String b = strB.substring(indexB, indexB + num2);

		return compareOrdinal(a, b, ignoreCase);
	}

	private static int compareInvariant(String strA, int indexA, String strB, int indexB, int length, boolean ignoreCase)
	{
		if ((Object)strA == (Object)strB)
			return 0;
		if (strA == null)
			return (-1);    // null < non-null
		if (strB == null)
			return (1);     // non-null > null

		int num = length;
		int num2 = length;

		if (strA.length() - indexA < num)
			num = strA.length() - indexA;

		if (strB.length() - indexB < num2)
			num2 = strB.length() - indexB;

		String a = strA.substring(indexA, indexA + num);
		String b = strB.substring(indexB, indexB + num2);

		return compareInvariant(a, b, ignoreCase);
	}

    /**
     * Emulates string.CompareOrdinal(a, b). Uses the ordinal string comparison.
     *
     * Ordinal sort compares strings based on the Unicode values of each element of the string.
     */
    public static int compareOrdinal(java.lang.String a, java.lang.String b)
    {
        return compareOrdinal(a, b, false);
    }

	/**
	 * Compares strA and strB using an ordinal (code-point) comparison.
	 */
	public static int compareOrdinal(String a, int indexA, String b, int indexB, int length)
	{
		// See comments above.
		if ((Object)a == (Object)b)
			return 0;
		if (a == null)
			return (-1);    // null < non-null
		if (b == null)
			return (1);     // non-null > null

        // the code adapted from Java's String.compareTo()
        int lengthA = a.length() - indexA;
        if (lengthA > length)
            lengthA = length;

        int lengthB = b.length() - indexB;
        if (lengthB > length)
            lengthB = length;

		int lim = Math.min(lengthA, lengthB);
		for (int i = 0; i < lim; i++)
        {
			char c1 = a.charAt(i + indexA);
			char c2 = b.charAt(i + indexB);
			if (c1 != c2)
				return c1 - c2;
		}

		return lengthA - lengthB;
	}

	/// <summary>
	/// Converts to lower case (WORDSJAVA-1678)
	/// </summary>
	public static String toLowerInvariant(java.lang.String str)
	{
		if (str == null)
			return str;

		char [] chars = str.toCharArray();
		for (int I=0; I<chars.length; I++)
			chars[I] = msChar.toLowerInvariant(chars[I]);

		return new String(chars);
	}

	/// <summary>
	/// Converts to upper case (WORDSJAVA-1678)
	/// </summary>
	public static String toUpperInvariant(java.lang.String str)
	{
		if (str == null)
			return str;

		char [] chars = str.toCharArray();
		for (int I=0; I<chars.length; I++)
			chars[I] = msChar.toUpperInvariant(chars[I]);

		return new String(chars);
	}

	public static String toUpper(java.lang.String str, msCultureInfo culture)
	{
		if(str.contains("ß"))
			return toUpperCaseWithEszett(str);
		else
			return str.toUpperCase(culture.getLocale());
	}

	public static String toLower(java.lang.String str, msCultureInfo culture)
	{
			return str.toLowerCase(culture.getLocale());
	}

	// The "de_DE" culture must use its own upper case method (see WORDSJAVA-2744). In .NET it works like a native,
	// but in Java we need to come up with a workaround. This method can be extended in the future if similar problems arise.
    // UPD: we must use this workaround not only if culture is de_DE (see WORDSJAVA-3192).
	private static String toUpperCaseWithEszett(java.lang.String str)
	{
		java.lang.StringBuilder stringBuilder = new java.lang.StringBuilder();

		for(char character : str.toCharArray())
			stringBuilder.append(java.lang.Character.toUpperCase(character));

		return stringBuilder.toString();
	}

	/**
	 * Instead of C# String Constructor (Char, Int32)
	 * <p/>
	 * Initializes a new instance of the String class to the value indicated by a specified
	 * Unicode character repeated a specified number of times.
	 */
	public static java.lang.String newString(char c, int count)
	{
		assert count >= 0 : "count must be greater than zero, but count = " + count;

		if (count == 0)
			return "";

		char[] charArray = new char[count];
		//Arrays.fill(charArray, c) does the same but with range check and others not needed here.
		for (int i = 0; i < count; i++)
			charArray[i] = c;

		return new java.lang.String(charArray);
	}

    /**
     * C# String.Remove(int).
     */
    public static String remove(java.lang.String str, int startIndex)
    {
        if (startIndex < 0 || startIndex >= str.length())
            throw new IndexOutOfBoundsException(MessageFormat.format(
                    "String length = {0}, but startIndex = {1}", str.length(), startIndex));

        return str.substring(0, startIndex);
    }

	/**
	 * Instead of C# String.Remove(int,int).
	 */
	public static java.lang.String remove(java.lang.String base, int startIndex, int count)
	{
		int length = base.length();
		if ((startIndex + count) > length)
			throw new IndexOutOfBoundsException(MessageFormat.format(
					"String length = {0}, but startIndex = {1} and count = {2}", base.length(), startIndex, count));

		// Manual arraycopy instead of Stringbuilder and other helpers for performance.
		int newLength = length - count;
		char[] copy = new char[newLength];
		char[] original = base.toCharArray();
		System.arraycopy(original, 0, copy, 0, startIndex);
		System.arraycopy(original, startIndex + count, copy, startIndex, newLength - startIndex);
		java.lang.String s = new String(copy);
		return s;
	}

	/**
	 * Instead .Net string.Format().
	 *
	 * The single method replaces all string.Format() overloads excluding one
	 * using IFormatProvider.
	 *
	 * Only simplified composite format strings allowed:
	 * //@todo 1 sk note supported and unsupported formats here
	 */
    public static String format(String format, /*params*/ Object... args)
    {
        if (format == null)
            throw new IllegalArgumentException("format");

	    //java-added to resolve single null arg.
	    if (args == null)
	        args = new Object[] {null};

        StringBuilder sb = new StringBuilder(format.length() + args.length * 8);
        msStringBuilder.appendFormat(sb, format, args);
        return sb.toString();
    }

    public static void copyTo(java.lang.String base, int sourceIndex, char[] destination, int destinationIndex,	int count)
    {
        base.getChars(sourceIndex, sourceIndex + count, destination, destinationIndex);
    }

    /**
	 * Instead of C# String.Insert(int,string)
	 * <p/>
	 * not very effective because of multiple arraycopy.
	 */
	public static java.lang.String insert(java.lang.String base, int startIndex, java.lang.String insert)
	{
		if (startIndex > base.length())
			throw new IndexOutOfBoundsException(MessageFormat.format(
					"String length = {0}, but startIndex = {1}", base.length(), startIndex));

		return new java.lang.StringBuilder(base).insert(startIndex, insert).toString();
	}

	public static int indexOf(String source, char value, int startIndex, int count)
	{
		return indexOf(source,"" + value, startIndex, count);
	}

	/**
	 * C# String.IndexOf(string value, int startIndex, int count).
	 * Boundaries check taken from Rotor, algorithm adopted from package-private java.lang.String.indexOf().
	 */
	public static int indexOf(String source, String value, int startIndex, int count)
	{
        // JAVA-deleted for performance: all these already checked by .Net tests and will throw anyway
        // if something will be wrong runtime.
        /*
		if (value == null)
			throw new NullPointerException("value");

		if (startIndex < 0 || startIndex > source.length())
			throw new IllegalArgumentException("startIndex");

		if (count < 0 || count > source.length() - startIndex)
			throw new IllegalArgumentException("count");
		*/

		int targetCount = value.length();
		//.Net does this:
		//return -1 for empty string even if startIndex is negative number
		//return 0 if both source and value are empty strings.
		if (source.length() == 0)
		{
			if (targetCount == 0)
				return 0;

			return -1;
		}

		char first  = value.charAt(0);
		//FIX WORDSJAVA-2437 - we must add startIndex here
		int max =  (count - targetCount) + startIndex;

		for (int i =  startIndex; i <= max; i++)
		{
			/* Look for first character. */
			if (source.charAt(i) != first)
				while (++i <= max && source.charAt(i) != first);

			/* Found first character, now look at the rest of v2 */
			if (i <= max)
			{
				int j = i + 1;
				int end = j + targetCount - 1;
				for (int k = 1; j < end && source.charAt(j) == value.charAt(k); j++, k++);

				if (j == end)
					return i ;// Found whole string.
			}
		}
		return -1;
	}

	public static int indexOf(String source, String value, StringComparison comparison)
	{
		switch (comparison)
        {
			case CURRENT_CULTURE:
			case INVARIANT_CULTURE:
			case ORDINAL:
				return source.indexOf(value);
			case CURRENT_CULTURE_IGNORE_CASE:
			case INVARIANT_CULTURE_IGNORE_CASE:
			case ORDINAL_IGNORE_CASE:
				return source.toLowerCase().indexOf(value.toLowerCase());
		}

        // Can't reach here.
		return source.indexOf(value);
	}

	public static int indexOf(String source, String value, int fromIndex, StringComparison comparison)
	{
		switch (comparison)
        {
			case CURRENT_CULTURE:
			case INVARIANT_CULTURE:
			case ORDINAL:
				return source.indexOf(value,fromIndex);
			case CURRENT_CULTURE_IGNORE_CASE:
			case INVARIANT_CULTURE_IGNORE_CASE:
			case ORDINAL_IGNORE_CASE:
				return source.toLowerCase().indexOf(value.toLowerCase(),fromIndex);
		}

        // Can't reach here.
		return source.indexOf(value,fromIndex);
	}

	public static int indexOf(String source, String value, int startIndex, int count, StringComparison comparison)
	{
		switch (comparison)
        {
			case CURRENT_CULTURE:
			case INVARIANT_CULTURE:
			case ORDINAL:
				return indexOf(source, value, startIndex, count);
			case CURRENT_CULTURE_IGNORE_CASE:
			case INVARIANT_CULTURE_IGNORE_CASE:
			case ORDINAL_IGNORE_CASE:
				return indexOf(source.toLowerCase(), value.toLowerCase(), startIndex, count);
		}

        // Can't reach here.
		return indexOf(source, value, startIndex, count);
	}

	/**
	 * Instead of C# String.IndexOfAny(char[]).
	 */
	public static int indexOfAny(java.lang.String targetStr, char[] toFind)
	{
        return indexOfAny(targetStr, toFind, 0);
	}

	/**
	 * Instead of C# String.IndexOfAny(char[] anyOf, int startIndex).
	 */
	public static int indexOfAny(java.lang.String targetStr, char[] toFind, int startIndex)
	{
        return indexOfAny(targetStr, toFind, startIndex, targetStr.length() - startIndex);
    }

	/**
	 * Instead of C# String.IndexOfAny(char[] anyOf, int startIndex, int count).
	 */
	public static int indexOfAny(java.lang.String targetStr, char[] toFind, int startIndex, int count)
	{
        // JAVA-deleted for performance: all these already checked by .Net tests and will throw anyway
        // if something will be wrong runtime.
        /*
        if (targetStr == null)
            throw new NullPointerException("targetStr");

        if (startIndex < 0 || startIndex > targetStr.length())
            throw new IllegalArgumentException("startIndex");

        if (count < 0 || count > targetStr.length() - startIndex)
            throw new IllegalArgumentException("count");
        */

        if (targetStr.length() < 1 || toFind.length < 1 || startIndex < 0)
            return -1;

        for (int i = startIndex; i < startIndex + count; i++)
        {
            char c = targetStr.charAt(i);
            for (int j = 0; j < toFind.length; j++)
                if (toFind[j] == c)
                    return i;
        }
        return -1;
    }

	/**
	 * Instead of C# String.LastIndexOfAny(char[]).
	 */
	public static int lastIndexOfAny(java.lang.String targetStr, char[] toFind)
	{
        if (targetStr == null || targetStr.length() < 1 || toFind == null || toFind.length < 1)
            return -1;

        // We must loop by character position because we need to find the last index of any of the given chars.
        for (int charIdx = targetStr.length() - 1; charIdx >= 0; charIdx--)
        {
            for (int i = 0; i < toFind.length; i++)
            {
                if (targetStr.charAt(charIdx) == toFind[i])
                    return charIdx;
            }
        }

		return -1;
	}

    /**
     * Instead of C# String.LastIndexOfAny(char[],int).
     */
    public static int lastIndexOfAny(java.lang.String targetStr, char[] toFind, int startIndex)
    {
        if (targetStr == null || targetStr.length() < 1 || startIndex < 0 || toFind == null || toFind.length < 1)
            return -1;

        // We must loop by character position because we need to find the last index of any of the given chars.
        for (int charIdx = startIndex; charIdx >= 0; charIdx--)
        {
            for (int i = 0; i < toFind.length; i++)
            {
                if (targetStr.charAt(charIdx) == toFind[i])
                    return charIdx;
            }
        }

        return -1;
    }

	/**
	 * Instead of C# String.trimStart(char).
	 * Made from java.lang.String.trim()
	 */
	public static java.lang.String trimStart(java.lang.String str, char trimChar)
	{
		int len = str.length();
		int st = 0;

		while ((st < len) && (str.charAt(st) == trimChar))
			st++;

		return (st > 0) ? str.substring(st) : str;
	}

    public static String trimStart(String str)
    {
        return trimStart(str, gWhitespaceChars);
    }

    public static String trimStart(String str, char[] trimChars)
    {
        int len = str.length();
        int st = 0;

        while ((st < len) && arrayContains(trimChars, str.charAt(st)))
            st++;

        return (st > 0) ? str.substring(st) : str;
    }

	/**
	 * Instead of C# String.TrimEnd(char).
	 * Maked from java.lang.String.trim()
	 */
	public static java.lang.String trimEnd(java.lang.String str, char trimChar)
	{
		int len = str.length();

		while ((len > 0) && (str.charAt(len - 1) == trimChar))
			len--;

		return len < str.length() ? str.substring(0, len) : str;
	}

	public static java.lang.String trimEnd(java.lang.String str)
    {
        return trimEnd(str, gWhitespaceChars);
    }

	public static java.lang.String trimEnd(java.lang.String str, char... trimChars)
	{
		if(str == null || str.isEmpty())
			return "";

		int len = str.length();

		while ((len > 0) && arrayContains(trimChars, str.charAt(len - 1)))
			len--;

		return len < str.length() ? str.substring(0, len) : str;
	}

	/**
	 * Instead of C# String.Trim(char).
	 * Maked from java.lang.String.trim()
	 */
	public static java.lang.String trim(java.lang.String str, char trimChar)
	{
		int len = str.length();
		int st = 0;

		while ((st < len) && (str.charAt(st) == trimChar))
			st++;

		while ((st < len) && (str.charAt(len - 1) == trimChar))
			len--;

		return ((st > 0) || (len < str.length())) ? str.substring(st, len) : str;
	}

	/**
	 * Analog of System.String.Trim().
	 *
	 * Java trims control symbols together with whitespaces - .Net doesn't.
	 */
	public static java.lang.String trim(java.lang.String str)
	{
        //JAVA: Rotor's WhitespaceChars array used
		return trim(str, gWhitespaceChars);
	}

	/**
	 * Instead of C# String.Trim(char[]).
	 * Maked from java.lang.String.trim().
	 * <p/>
	 * Note: doesn't tream whitespaces if the trimChars array is null or empty (as C# Trim() do).
	 */
	public static java.lang.String trim(java.lang.String str, char... trimChars)
	{
		int len = str.length();
		int st = 0;

		while ((st < len) && arrayContains(trimChars, str.charAt(st)))
			st++;

		while ((st < len) && arrayContains(trimChars, str.charAt(len - 1)))
			len--;

		return ((st > 0) || (len < str.length())) ? str.substring(st, len) : str;
	}

	//used solely by trimChar(String, char[])
	private static boolean arrayContains(char[] array, char theChar)
	{
		for (int i = 0; i < array.length; i++)
		{
			if (array[i] == theChar)
				return true;
		}

		return false;
	}

	/**
	 * Instead of C# String.Join(string, string[]).
	 * <p/>
	 * Concatenates a specified separator String between each element of a specified String array.
	 */
	public static java.lang.String join(java.lang.String divider, java.lang.String... strings)
	{
		if (strings.length == 0)
			return "";

		if (divider == null)
			divider = "";

		java.lang.StringBuilder builder = new java.lang.StringBuilder();
		boolean first = true;
		for (java.lang.String str : strings)
		{
			if (first)
				first = false;
			else
				builder.append(divider);

			//java writes "null" for null string, .Net writes empty string.
			if (str == null)
				str = "";

			builder.append(str);
		}

		return builder.toString();
	}

	/**
	 * Instead of C# String.Concat(params string[]).
	 * <p/>
	 * Concatenates the elements of a specified System.String array.
	 */
	public static java.lang.String concat(java.lang.String... strings)
	{
		//also checks string for null.
		if (strings.length == 0)
			return "";

		java.lang.StringBuilder builder = new java.lang.StringBuilder();
		boolean first = true;
		for (java.lang.String str : strings)
		{
			//java writes "null" for null string, .Net writes empty string.
			if (str == null)
				str = "";

			builder.append(str);
		}

		return builder.toString();
	}

	/**
	 * Instead of C# String.Concat(params object[]).
	 * <p/>
	 * Concatenates the System.String representations of the elements in a specified System.Object array.
	 */
	public static java.lang.String concat(Object... objects)
	{
		//also checks string for null.
		if (objects.length == 0)
			return "";

		java.lang.StringBuilder builder = new java.lang.StringBuilder();
		boolean first = true;
		for (Object obj : objects)
		{
			//java writes "null" for null string, .Net writes empty string.
			if (obj == null)
				obj = "";

			builder.append(obj.toString());
		}

		return builder.toString();
	}

    //>>>>>>>>>>>>>>>>>>>>> split by chars

	/**
	 * Simplified C# String.Split(params char[]) with only one separator char.
     * Added for performance.
	 */
	public static java.lang.String[] split(java.lang.String str, char separator)
	{
		int[] separatorPositions = new int[str.length()];
		int foundSeparators = 0;

		//find all separator's occurrences
		for (int i = 0; i < str.length(); i++)
		{
			if (str.charAt(i) == separator)
				separatorPositions[foundSeparators++] = i;
		}

		java.lang.String[] splittedStrings = new java.lang.String[foundSeparators + 1];

		//separators not found in the str - return just the str.
		if (foundSeparators == 0)
		{
			splittedStrings[0] = str;
			return splittedStrings;
		}

		//split
		int index = 0, start = 0;
		for (int i = 0; i < foundSeparators; i++)
		{
			int finish = separatorPositions[i];
			splittedStrings[index++] = str.substring(start, finish);
			start = finish + 1;
		}

		//the last substring, will be the empty string if separator in the last position of the str.
		splittedStrings[index] = str.substring(start, str.length());

		return splittedStrings;
	}

    public static java.lang.String[] split(java.lang.String str)
    {
        return split(str, (char[]) null);
    }

    public static java.lang.String[] split(java.lang.String str, char[] separators)
    {
	    return split(str, separators, Integer.MAX_VALUE, StringSplitOptions.NONE);
    }

    public static java.lang.String[] split(java.lang.String str, char[] separators, int count)
    {
	    return split(str, separators, count, StringSplitOptions.NONE);
    }

    public static java.lang.String[] split(java.lang.String str, char[] separators, StringSplitOptions options)
    {
	    return split(str, separators, Integer.MAX_VALUE, options);
    }

    public static java.lang.String[] split(java.lang.String str, char[] separators, int count, StringSplitOptions options)
    {
	    assert count >= 0;
        boolean removeEmptyEntries = options == StringSplitOptions.REMOVE_EMPTY_ENTRIES;

        if (count == 0 || (removeEmptyEntries && str.length() == 0))
	        return new java.lang.String[0];

	    int[] separatorPositions = new int[str.length()];
	    int foundSeparators = makeSeparatorList(str, separators, separatorPositions);

        //Handle the special case of no replaces and special count.
	    if (foundSeparators == 0 || count == 1)
            return new java.lang.String[] {str};

        if (removeEmptyEntries)
            return splitRemoveEmptyEntries(str, separatorPositions, null, foundSeparators, count);

        return splitKeepEmptyEntries(str, separatorPositions, null, foundSeparators, count);
    }

    private static int makeSeparatorList(String str, char[] separators, int[] separatorPositions)
    {
        int foundSeparators = 0;
        //find all separator's occurrences

        //If they passed null or an empty string, look for whitespace.
        if (separators == null || separators.length == 0)
        {
            for (int i = 0; i < str.length() && foundSeparators < separatorPositions.length; i++)
            {
                if (msChar.isWhiteSpace(str.charAt(i)))
                    separatorPositions[foundSeparators++] = i;
            }
        }
        else
        {
            for (int i = 0; i < str.length() && foundSeparators < separatorPositions.length; i++)
            {
                for (int j = 0; j < separators.length; j++)
                {
                    if (str.charAt(i) == separators[j])
                    {
                        separatorPositions[foundSeparators++] = i;
                        break;
                    }
                }
            }
        }

        return foundSeparators;
    }

    //>>>>>>>>>>>>>>>>>>>>> split by chars

    //>>>>>>>>>>>>>>>>>>>>> split by strings

    public static java.lang.String[] split(java.lang.String str, String[] separators, StringSplitOptions options)
    {
	    return split(str, separators, Integer.MAX_VALUE, options);
    }

    public static java.lang.String[] split(java.lang.String str, String[] separators, int count, StringSplitOptions options)
    {
	    assert count >= 0;
        boolean removeEmptyEntries = options == StringSplitOptions.REMOVE_EMPTY_ENTRIES;

	    if (count == 0 || (removeEmptyEntries && str.length() == 0))
	        return new java.lang.String[0];
        if (separators == null || separators.length == 0)
            return split(str, (char[]) null);

	    int[] separatorPositions = new int[str.length()];
	    int[] separatorLengths = new int[str.length()];
	    int foundSeparators = makeSeparatorList(str, separators, separatorPositions, separatorLengths);

        //Handle the special case of no replaces and special count.
        if (foundSeparators == 0 || count == 1)
            return new java.lang.String[] {str};

        if (removeEmptyEntries)
            return splitRemoveEmptyEntries(str, separatorPositions, separatorLengths, foundSeparators, count);

        return splitKeepEmptyEntries(str, separatorPositions, separatorLengths, foundSeparators, count);
    }

    private static int makeSeparatorList(String str, String[] separators, int[] separatorPositions, int[] separatorLengths)
    {
        assert separators != null && separators.length > 0;

        int foundCount = 0;

        for (int i = 0; i < str.length() && foundCount < separatorPositions.length; i++)
        {
            for (int j = 0; j < separators.length; j++)
            {
                String separator = separators[j];
                if (msString.isNullOrEmpty(separator))
                    continue;

                int separatorLength = separator.length();
                if ( str.charAt(i) == separator.charAt(0) && separatorLength <= str.length() - i)
                {
                    if (separatorLength == 1 || compare(str, i, separator, 0, separatorLength, false) == 0)
                    {
                        separatorPositions[foundCount] = i;
                        separatorLengths[foundCount] = separatorLength;
                        foundCount++;
                        i += separatorLength - 1;
                        break;
                    }
                }
            }
        }

        return foundCount;
    }

    private static String[] splitRemoveEmptyEntries(String str, int[] separators, int[] lengths, int foundSeparators, int count)
    {
        assert count >= 2;

        int maxItems = (foundSeparators < count) ? (foundSeparators + 1) : count ;
        String[] splitStrings = new String[maxItems];

        int arrIndex = 0;
        int start = 0;
        for (int i = 0; i < foundSeparators; i++)
        {
            int finish = separators[i];
            if (finish - start > 0)
                splitStrings[arrIndex++] = str.substring(start, finish);
            start = finish + ((lengths == null) ? 1 : lengths[i]);

            if (arrIndex == count - 1)
            {
                // If all the remaining entries at the end are empty, skip them
                while (i < foundSeparators - 1 && start == separators[++i])
                    start += ((lengths == null) ? 1 : lengths[i]);

                break;
            }
        }

        // we must have at least one slot left to fill in the last string.
        assert arrIndex < maxItems;

        //Handle the last string at the end of the array if there is one.
        if (start < str.length())
            splitStrings[arrIndex++] = str.substring(start);

        if (arrIndex == maxItems)
            return splitStrings;

        String[] emptyRemoved = new String[arrIndex];
        System.arraycopy(splitStrings, 0, emptyRemoved, 0, emptyRemoved.length);

        return emptyRemoved;
    }

    private static String[] splitKeepEmptyEntries(String str, int[] sepList, int[] lengthList, int foundSeparators, int count)
    {
        assert count >= 2;
        count--;
        int numActualReplaces = (foundSeparators < count) ? foundSeparators : count;

        //Allocate space for the new array.
        //+1 for the string from the end of the last replace to the end of the String.
        String[] splitStrings = new String[numActualReplaces+1];

        int currIndex = 0;
        int arrIndex = 0;
        for (int i = 0; i < numActualReplaces && currIndex < str.length(); i++)
        {
            splitStrings[arrIndex++] = str.substring(currIndex, sepList[i]);
            currIndex=sepList[i] + ((lengthList == null) ? 1 : lengthList[i]);
        }

        //Handle the last string at the end of the array if there is one.
        if (currIndex < str.length() && numActualReplaces >= 0)
        {
            splitStrings[arrIndex] = str.substring(currIndex);
        }
        else if (arrIndex==numActualReplaces)
        {
            //We had a separator character at the end of a string.  Rather than just allowing
            //a null character, we'll replace the last element in the array with an empty string.
            splitStrings[arrIndex] = "";

        }

        return splitStrings;
    }

    //<<<<<<<<<<<<<<<<<<<<< split by strings

    /**
	 * Used instead of 'str1 += str2' operators.
	 * .Net uses "" for null strings. But java's StringBuilder changes
	 * null strings into "null" value. (Compiler uses StringBuilder to
	 * implement these expressions)
	 */
	public static java.lang.String plusEqOperator(java.lang.String lhs, java.lang.String rhs)
	{
		if (lhs == null)
			lhs = "";

		if (rhs == null)
			rhs = "";

		return lhs + rhs;
	}

	public static java.lang.String plusEqOperator(java.lang.String lhs, char rhs)
	{
		if (lhs == null)
			lhs = "";

		return lhs + rhs;
	}

	public static java.lang.String plusEqOperator(java.lang.String lhs, int rhs)
	{
		if (lhs == null)
			lhs = "";

		return lhs + rhs;
	}

	public static java.lang.String plusEqOperator(java.lang.String lhs, StringBuilder rhs)
	{
		if (lhs == null)
			lhs = "";

		return lhs + rhs;
	}

	public static java.lang.String plusEqOperator(java.lang.String lhs, Object rhs)
	{
		if (lhs == null)
			lhs = "";

		return lhs + rhs;
	}

    public static final java.lang.String Empty = "";

	//JAVA: stolen from Rotor. used by trim().
	static final char[] gWhitespaceChars =
	    { (char) 0x9, (char) 0xA, (char) 0xB, (char) 0xC, (char) 0xD, (char) 0x20,   (char) 0x85,
	      (char) 0xA0, (char)0x1680,
	      (char) 0x2000, (char) 0x2001, (char) 0x2002, (char) 0x2003, (char) 0x2004, (char) 0x2005,
	      (char) 0x2006, (char) 0x2007, (char) 0x2008, (char) 0x2009, (char) 0x200A, (char) 0x200B,
	      (char) 0x2028, (char) 0x2029,
	      (char) 0x3000, (char) 0xFEFF };

   /**
    * Returns a new string that left-aligns the characters in this string by padding them
    * on the right with a specified Unicode character, for a specified total length.
    */
    public static String padRight(String src, int length)
    {
        return padRight(src, length, ' ');
    }

	public static String padRight(String src, int length, char padChar)
	{
		if (src.length() >= length)
			return src;

		StringBuilder sb = new StringBuilder(length);
		sb.append(src);
		for (int i = src.length(); i < length; i++)
			sb.append(padChar);

		return sb.toString();
	}

	public static String padLeft(String src, int length)
	{
		return padLeft(src, length, ' ');
	}

	public static String padLeft(String src, int length, char padChar)
	{
		if (src.length() >= length)
			return src;

		StringBuilder sb = new StringBuilder(length);
		for (int i = src.length(); i < length; i++)
			sb.append(padChar);

		sb.append(src);
		return sb.toString();
	}

	public static String replace(String src, char target, char replacement)
	{
		return src.replace(target, replacement);
	}

	public static String replace(String src, String target, String replacement)
	{
		if (src == null)
			return null;

		if (target == null)
			return src;

		int targetLength = target.length();
		if (targetLength == 0)
			return target;

		int replaceLength = (replacement == null ? 0 : replacement.length());

		// Optimization
		if (targetLength == 1 && replaceLength == 1)
			return msString.replace(src, target.charAt(0), replacement.charAt(0));

		StringBuilder result = new StringBuilder(src.length());

		int find = 0;
		int previous = 0;

		while ((find = src.indexOf(target, previous)) >= 0)
		{
			result.append(src.substring(previous, find));

			if (replaceLength > 0)
				result.append(replacement);

			previous = find + targetLength;
		}

		result.append(src.substring(previous));
		return result.toString();
	}

	public static boolean startsWith(String source, String value, StringComparison comparison)
	{
		return indexOf(source, value, comparison) == 0;
	}
}

