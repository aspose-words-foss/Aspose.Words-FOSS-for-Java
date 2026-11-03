// Parts of this file are a Java port, made by Aspose, of .NET Framework code. Microsoft publishes that source
// under the MIT License at https://github.com/microsoft/referencesource : System/regex/system/text/regularexpressions/Regex.cs (method Escape)
//
// Those parts: Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

/***
 * Copyright Aspose.com, 2006
 * Helper Java code to port Aspose.Word C# source, Jan 10, 2007
 *
 * @author Konstantin Sidorenko
 */

package com.aspose.ms.System.Text.RegularExpressions;

import com.aspose.ms.System.Text.RegularExpressions.java.PatternUtils;
import com.aspose.ms.System.msString;
import com.aspose.ms.java.debug.JavaDebug;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;

/**
 * Simplified copycat of .Net System.Text.RegularExpressions.Regex.
 * Backed by java.util.regex.Pattern.
 *
 * Only methods used by Aspose.Words are implemented.
 *
 * Note:
 * 1. Java does not support .Net named groups: (?<groupName>regex).
 */
public class Regex
{
	public Regex(String regex)
	{
		this(regex, Pattern.DOTALL);
	}

    /**
     * com.aspose.ms.System.Text.RegularExpressions.RegexOptions emulates System.Text.RegularExpressions.RegexOptions
     */
    public Regex(String regex, int regexOptions)
	{
		regex = PatternUtils.unicode_charclass(regex);
		mRegexOptions = Pattern.DOTALL | regexOptions;
		mPattern = compile(regex, mRegexOptions);
		mMatcher = new ThreadLocal<Match>() {
			@Override
			protected Match initialValue()
			{
				return new Match(mPattern);
			}
		};
	}

	private Regex(Pattern javaPattern)
	{
		mRegexOptions = javaPattern.flags();
		mPattern = javaPattern;
		mMatcher = new ThreadLocal<Match>() {
			@Override
			protected Match initialValue()
			{
				return new Match(mPattern);
			}
		};
	}

	public int getOptions()
	{
		return mRegexOptions;
	}

    public String toString()
    {
        return mPattern.toString();
    }

    public String[] split(String input)
    {
        return mPattern.split(input);
    }

    public String[] split(String input, int count)
    {
        return mPattern.split(input, count);
    }

	public String[] split(String input, int count, int startat)
	{
		String start = input.substring(0, startat);
		String[] result = split(input.substring(startat), count);
		if (result.length > 0)
			result[0] = start + result[0];
		return result;
	}

	public Match match(String input)
	{
		return mMatcher.get().find(input);
	}

	public Match match(String input, int startat)
	{
		return mMatcher.get().find(input, startat);
	}

    public Match matchStartOnly(String input, int startat)
    {
		return mMatcher.get().lookAt(input, startat);
    }

	public Match match(String input, int start, int length)
	{
        // FIX WORDSJAVA-755
		return mMatcher.get().find(input, start, length);
	}

	public static Match match(String input, String pattern)
	{
		return new Regex(pattern).match(input);
	}

	public static Match match(String input, String pattern, int regexOptions)
	{
		return new Regex(pattern, regexOptions).match(input);
	}

	public MatchCollection matches(String text)
	{
		return matches(this, text, 0);
	}

	public MatchCollection matches(String text, int startAt)
	{
		return matches(this, text, startAt);
	}

    public static MatchCollection matches(String input, String pattern)
    {
        return matches(new Regex(pattern), input, 0);
    }

	/**
	 * Instead of C# Regex.Matches(string, startAt)
	 * <p>
	 * The same can be done by java.util.regex.MatchResult, but it java 1.5 only.
	 * <p>
	 * MSDN: The Matches methods are similar to the Match methods, except they return the list of
	 * successful matches that would result from iteratively calling match= Match (...),
	 * then Match.NextMatch (), and so on. The collection includes only successful matches
	 * and terminates at the first unsuccessful match.
	 */
	private static MatchCollection matches(Regex pattern, String text, int startAt)
	{
		MatchCollection matches = new MatchCollection();
		Matcher matcher = pattern.toJava().matcher(text);

		// WORDSJAVA-2910 The previous algorithm did not work correctly and returned all matches even when only the first one was needed.
		while (matcher.find())
		{
			Match match = new Match(pattern.toJava(), text, startAt);

			if (!match.getSuccess())
				break;

			matches.add(match);
			startAt = match.toJava().end();

			// Greedy patterns like 'a*' or '[^a]*' can infinitely return an empty group where
			// matcher's start == end. Lets help them.
			if (startAt == match.getIndex())
				break;
		}

		return matches;
	}

    /**
     * Instead of C# Regex.Replace(String, String)
     *
     * Code adapted from java.lang.String.replaceAll.
     */
    public String replace(String input, String replacement)
    {
    	return mMatcher.get().replaceAll(input, replacement);
    }

	/**
	 * Instead of C# Regex.Replace(String, String, int)
	 *
	 * Code adapted from java.lang.String.replace.
	 */
	public String replace(String input, String replacement, int count)
	{
		return mMatcher.get().replace(input, replacement, count);
	}

    /**
     * Instead of C# static Regex.Replace(String, String, String)
     */
    public static String replace(java.lang.String input, java.lang.String pattern, java.lang.String replacement)
    {
        return replace(input, pattern, replacement, RegexOptions.NONE);
    }

    /**
	 * Instead of C# static Regex.Replace(String, String, String, RegexOptions)
	 *
	 * Code adapted from java.lang.String.replaceAll.
	 */
	public static String replace(java.lang.String input, java.lang.String pattern, java.lang.String replacement, int regexOptions)
	{
		Regex regex = new Regex(pattern, regexOptions);
        return regex.replace(input, replacement);
	}

    /**
     * Instead .Net Regex.Replace(String input, String pattern1, MatchEvaluator) where MatchEvaluator
     * delegate contains Regex.Replace(match.Value, String pattern2, String replace).
     * The delegate gets every match of pattern1 and replaces in it pattern2 by replace str.
     */
    public static String replaceChain(String input, String pattern1, String pattern2, String replace)
    {
        Pattern regex1 = compile(pattern1, Pattern.DOTALL);
        Pattern regex2 = compile(pattern2, Pattern.DOTALL);

        Matcher matcher1 = regex1.matcher(input);
        StringBuffer buffer = new StringBuffer();
        while (matcher1.find())
        {
            String rawStr = matcher1.group();
            Matcher matcher2 = regex2.matcher(rawStr);
            String finalReplace = matcher2.replaceAll(replace);
            matcher1.appendReplacement(buffer, finalReplace);
        }
        matcher1.appendTail(buffer);

        return buffer.toString();
    }

	/**
	 * Instead of C# static Regex.IsMatch(String, String, RegexOptions)
	 */
	public static boolean isMatch(java.lang.String input, java.lang.String pattern, int regexOptions)
	{
        Regex regex = new Regex(pattern, regexOptions);
        return regex.isMatch(input);
	}

	public static boolean isMatch(java.lang.String input, java.lang.String pattern)
	{
		return isMatch(input, pattern, RegexOptions.NONE);
	}

	public boolean isMatch(String input)
    {
        return mMatcher.get().find(input).getSuccess();
    }

	/**
	 * C# Regex.Escape() method port taken from Rotor.
	 *
	 * Escapes a minimal set of metacharacters (\, *, +, ?, |, {, [, (,), ^, $, ., #, and white space)
	 * by replacing them with their escape codes.
	 * Escapes from input String all metacharacters (including |,(,),[,{,|,^,$,*,+,?,\, spaces and #).
	 */
	public static java.lang.String escape(java.lang.String input)
	{
		for (int i = 0; i < input.length(); i++)
		{
			if (isMetachar(input.charAt(i)))
			{
				java.lang.StringBuilder sb = new java.lang.StringBuilder();
				char ch = input.charAt(i);
				int lastpos;

				sb.append(input, 0, i);
				do
				{
					sb.append('\\');
					switch (ch)
					{
						case '\n':
							ch = 'n';
							break;
						case '\r':
							ch = 'r';
							break;
						case '\t':
							ch = 't';
							break;
						case '\f':
							ch = 'f';
							break;
					}
					sb.append(ch);
					i++;
					lastpos = i;

					while (i < input.length())
					{
						ch = input.charAt(i);
						if (isMetachar(ch))
						{
							break;
						}

						i++;
					}

					sb.append(input, lastpos, i);//java: was "i - lastpos"

				}
				while (i < input.length());

				return sb.toString();
			}
		}

		return input;
	}

	//JAVA-added
	public Pattern toJava()
	{
		return mPattern;
	}

	//JAVA-added
	/**
	 * This static method should be used instead of regex.toJava() to avoid NullPointerException
	 * if regex == null. For instance, the method used by ApiChangeMap.
	 */
	public static Pattern toJava(Regex regex)
	{
		return regex == null ? null : regex.toJava();
	}

	//JAVA-added
	public static Regex fromJava(Pattern javaPattern)
	{
		return new Regex(javaPattern);
	}

	//>>>>>>>>>> method and constants used solely by escape(String)

	// Returns true for chars that should be escaped.
	private static boolean isMetachar(char ch)
	{
		return (ch <= '|' && _CATEGORY[ch] >= E);
	}

	private static Pattern compile(String regex, int regexOptions)
	{
		try
		{
			return Pattern.compile(regex, regexOptions);
		}
		catch (PatternSyntaxException ex)
		{
			if (JavaDebug.ON)
				System.err.println("Regex '" + regex + "' cannot be compiled by Java Pattern. We will try to normalize it. Exception: " + ex.getMessage());

			// WORDSJAVA-1422 For performance's sake I placed this method here.
			// I think we should try this case only if Pattern.compile() failed.
			regex = escapeOpenCurlyBraces(regex);
		}
		return Pattern.compile(regex, regexOptions);
	}

	/**
	 * Pattern expects curly braces to be repetition expression {n}, {n,}, or {n,m}
	 * So there are any other characters inside the braces Pattern will fail.
	 *
	 * @param regex
	 * @return the regexp where an open curve braces has been quoted if needed.
	 */
	private static String escapeOpenCurlyBraces(String regex)
	{
		if (msString.isNullOrEmpty(regex))
			return regex;

		StringBuilder result = new StringBuilder();
		StringBuilder repetitionGroup = new StringBuilder();

		for (int currentPosition = 0; currentPosition < regex.length(); currentPosition++)
		{
			char currentChar = regex.charAt(currentPosition);
			if (currentChar == '{')
			{
				// 1. skip if \\p{, \\x{,
				if ((currentPosition - 2) >= 0)
				{
					char c1 = regex.charAt(currentPosition - 2);
					char c2 = regex.charAt(currentPosition - 1);
					if (c1 == '\\' && (c2 == 'p' || c2 == 'x'))
					{
						result.append(currentChar); // it is ok move on
						continue;
					}
				}

				// 2. check whether it is commented \\{ or correct repetition expression char{n,m}
				if ((currentPosition - 1) >= 0)
				{
					char c1 = regex.charAt(currentPosition - 1);
					if (c1 == '\\')
					{
						result.append(currentChar);
						continue; // it is ok move on
					}

					// 3. check repetition char{n}, char{n,} or char{n,m}
					if (currentPosition + 1 < regex.length())
					{
						int repetitionCharPosition;
						repetitionGroup.setLength(0);
						for (repetitionCharPosition = currentPosition + 1; repetitionCharPosition < regex.length(); repetitionCharPosition++)
						{
							char repetitionChar = regex.charAt(repetitionCharPosition);
							repetitionGroup.append(repetitionChar);

							if (repetitionChar == '{')// not a repetition
							{
								result.append('\\');
								repetitionGroup.setLength(0);
								repetitionCharPosition = currentPosition;
								break;
							}

							if (repetitionChar == '}' && repetitionGroup.length() == 1) // empty repetition {}
							{
								result.append('\\');
								break;
							}

							if (repetitionChar == '}')
								break;

							if (!Character.isDigit(repetitionChar) && repetitionChar != ',')
							{
								result.append('\\');
								break;
							}
						}

						result.append(currentChar);
						result.append(repetitionGroup);
						currentPosition = repetitionCharPosition;
						continue;
					}
				}

				result.append('\\');
				result.append(currentChar);
			}
			else
			{
				result.append(currentChar);
			}
		}

		return result.toString();
	}

	//java: was byte, but internally it int anywhere
	private static final int Q = 5;	// quantifier
	private static final int S = 4;	// ordinary stopper
	private static final int Z = 3;	// ScanBlank stopper
	private static final int X = 2;	// whitespace
	private static final int E = 1;	// should be escaped


	// For categorizing ascii characters.
	private static final int[] _CATEGORY = new int[]{ //java: was byte
			// 0 1 2 3 4 5 6 7 8 9 A B C D E F 0 1 2 3 4 5 6 7 8 9 A B C D E F
			0, 0, 0, 0, 0, 0, 0, 0, 0, X, X, 0, X, X, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			//   ! " # $ % & ' ( ) * + , - . / 0 1 2 3 4 5 6 7 8 9 : ; < = > ?
			X, 0, 0, Z, S, 0, 0, 0, S, S, Q, Q, 0, 0, S, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Q,
			// @ A B C D E F G H I J K L M N O P Q R S T U V W X Y Z [ \ ] ^ _
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, S, S, 0, S, 0,
			// ' a b c d e f g h i j k l m n o p q r s t u v w x y z { | } ~
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Q, S, 0, 0, 0};

	//<<<<<<<<<<<<< method and constans used solely by escape(String)

	private final Pattern mPattern;
	private final ThreadLocal<Match> mMatcher;
	private final int mRegexOptions;
}
