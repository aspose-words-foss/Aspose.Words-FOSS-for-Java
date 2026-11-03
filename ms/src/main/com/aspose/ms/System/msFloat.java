//Konstantin Sidorenko 25/09/12

package com.aspose.ms.System;

/**
 * Contains extension methods to emulate System.Single on java.
 */
public class msFloat
{
	/**
	 * Used instead of float.CompareTo(float)
	 */
	public static int compareTo(float a, float b)
	{
		if (a < b)
			return -1;
		if (a > b)
			return 1;
		if (a == b)
			return 0;

		// At least one of the values is NaN.
		if (Float.isNaN(a))
			return (Float.isNaN(b) ? 0 : -1);
		else
			return 1;
	}

	public static boolean isPositiveInfinity(float value)
	{
		return value == Float.POSITIVE_INFINITY;
	}

	public static boolean isNegativeInfinity(float value)
	{
		return value == Float.NEGATIVE_INFINITY;
	}
}
