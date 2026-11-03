// ported 3/10/2008 by Konstantin Sidorenko
package com.aspose.ms.System;

import java.lang.String;

/**
 * Copycat of System.ParseNumbers.
 * Backed by java.lang.Integer methods since .Net ParseNumbers backed by native calls.
 * Note: only NoSpace flag supportd at the moment. All other flag params not implemented
 * since them not implemented in java.lang.Integer.
 */
public class ParseNumbers
{
	public static final int NoSpace=0x2000;//JAVA: only this flag used at the moment.

	public static long stringToLong(java.lang.String s, int radix, int flags)
	{
		if (s == null)
			throw new IllegalArgumentException("s");

		int[] currPos = {0};
		return stringToLong(s, radix, flags, currPos);
	}

	public static long stringToLong(java.lang.String s, int radix, int flags, int[] currPos)
	{
		if (s == null)
			throw new IllegalArgumentException("s");

		int begin = currPos[0];
		int end;

		if (flags == NoSpace)
			end = firstSpace(s, radix, begin);
		else
			end = s.length();

		s = s.substring(begin, end);
		currPos[0] = end;

		return Long.parseLong(s, radix);
	}

	public static int stringToInt(java.lang.String s, int radix, int flags)
	{
		if (s == null)
			throw new IllegalArgumentException("s");

		int[] currPos = {0};
		return stringToInt(s, radix, flags, currPos);
	}

	public static int stringToInt(java.lang.String s, int radix, int flags, int[] currPos)//JAVA: currPos was ref param
	{
		if (s == null)
			throw new IllegalArgumentException("s");

		int begin = currPos[0];
		int end;

		if (flags == NoSpace)
			end = firstSpace(s, radix, begin);
		else
			end = s.length();

		s = s.substring(begin, end);
		currPos[0] = end;

		return Integer.parseInt(s, radix);
	}

	//JAVA-added helper method
	private static int firstSpace(String s, int radix, int begin)
	{
		int i = begin;
		if (s.charAt(0) == '-')
			i++;

		while (i < s.length() && Character.digit(s.charAt(i), radix) != -1)
			i++;

		return i;
	}

	public static java.lang.String intToString(int l, int radix, int width, char paddingChar)
	{
		assert width > 0;

		java.lang.String str = Integer.toString(l, radix);
		return zeroPadString(str, width, paddingChar);
	}

	public static java.lang.String longToString(long l, int radix, int width, char paddingChar)
	{
		assert width > 0;

		java.lang.String str = Long.toString(l, radix);
		return zeroPadString(str, width, paddingChar);
	}

	//JAVA-added helper mehtod
	private static java.lang.String zeroPadString(java.lang.String str, int width, char paddingChar)
	{
		int length = str.length();

		if (length == width)
			return str;

		//zero padding missing digits
		if (length < width)
			return msString.newString(paddingChar, width - length) + str;

		//cut off excess digits
		return str.substring(length - width, length);
	}

}
