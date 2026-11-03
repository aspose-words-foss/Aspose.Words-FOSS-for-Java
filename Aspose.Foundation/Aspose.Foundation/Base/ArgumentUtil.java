// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/02/2016 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;


/// <summary>
/// Contains utility methods for argument checking.
/// </summary>
public /*static*/ class ArgumentUtil
{
	/* Simulation of static class by using private constructor */
	private ArgumentUtil()
	{}

    public static void checkHasChars(String value, String paramName)
    {
        if (msString.isNullOrEmpty(value))
            throw new IllegalArgumentException("The argument cannot be null or empty string." + "\r\nParameter name: " + paramName);
    }

    public static void checkNotNull(Object value, String paramName)
    {
        if (value == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + paramName);
    }

    public static void checkRangeInclusive(int value, int min, int max, String paramName)
    {
        if ((value < min) || (value > max))
            throw new IllegalArgumentException(msString.format("Expected a value between {0} and {1}.", min, max) + "\r\nParameter name: " + paramName);
    }

    public static void checkRangeInclusive(double value, double min, double max, String paramName)
    {
        if ((value < min) || (value > max) || Double.isNaN(value))
            throw new IllegalArgumentException(msString.format("Expected a value between {0} and {1}.", min, max) + "\r\nParameter name: " + paramName);
    }

    public static void checkPositive(int value, String paramName)
    {
        if (value <= 0)
            throw new IllegalArgumentException("Expected a positive value." + "\r\nParameter name: " + paramName);
    }

    public static void checkPositive(double value, String paramName)
    {
        if (value <= 0 || Double.isNaN(value))
            throw new IllegalArgumentException("Expected a positive value." + "\r\nParameter name: " + paramName);
    }

    public static void checkNonNegative(int value, String paramName)
    {
        if (value < 0)
            throw new IllegalArgumentException("Expected a non-negative value." + "\r\nParameter name: " + paramName);
    }

    public static void checkNonNegative(double value, String paramName)
    {
        if (value < 0 || Double.isNaN(value))
            throw new IllegalArgumentException("Expected a non-negative value." + "\r\nParameter name: " + paramName);
    }

    /// <summary>
    /// Validates the value in the given range.
    /// Either throws an exception or assigns given saturation values if it exceeds.
    /// </summary>
    public static double validateRange(double value, double minMargin, double minSaturation, double maxMargin, double maxSaturation, boolean isThrow, String paramName)
    {
        double result;

        if (value < minMargin)
        {
            if (isThrow)
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + paramName);

            result = minSaturation;
        }
        else if (value > maxMargin)
        {
            if (isThrow)
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + paramName);

            result = maxSaturation;
        }
        else
        {
            result = value;
        }

        return result;
    }

    // Returns True if either obj1 or obj2 is null but not both.
    public static boolean oneIsNull(Object obj1, Object obj2)
    {
        return ((obj1 == null) ^ (obj2 == null));
    }

    public static boolean bothAreNotNull(Object obj1, Object obj2)
    {
        return ((obj1 != null) && (obj2 != null));
    }

    public static boolean bothAreNull(Object obj1, Object obj2)
    {
        return ((obj1 == null) && (obj2 == null));
    }

    /// <summary>
    /// Returns true if both objects are not null, has the same type and their hashcodes matches.
    /// </summary>
    public static boolean typeAndHashCodeMatches(Object obj1, Object obj2)
    {
        return bothAreNotNull(obj1, obj2) &&
               obj1.getClass() == obj2.getClass() &&
               obj1.hashCode() == obj2.hashCode();
    }
}

