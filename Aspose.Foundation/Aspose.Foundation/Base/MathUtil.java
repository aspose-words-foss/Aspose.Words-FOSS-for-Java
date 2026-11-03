// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/03/2006 by Dmitry Vorobyev

// Parts of this file are derived, by Aspose, from .NET Framework code. Microsoft publishes that source
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/collections/hashtable.cs
//
// Those parts: Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see THIRD-PARTY-NOTICES.md in the root of this repository.

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msMath;
import com.aspose.ms.System.msUShort;
import com.aspose.ms.System.Drawing.msPointF;
import com.aspose.ms.System.Drawing.RectangleF;
import com.aspose.ms.System.Drawing.msPoint;
import com.aspose.ms.System.Drawing.Rectangle;


/// <summary>
/// Provides various mathematical functions missed from the System.Math class.
/// </summary>
public /*static*/ class MathUtil
{
	/* Simulation of static class by using private constructor */
	private MathUtil()
	{}

    /// <summary>
    /// Simple square function.
    /// </summary>
    public static float sqr(float f)
    {
        return f * f;
    }

    /// <summary>
    /// Simple square function.
    /// </summary>
    public static double sqr(double d)
    {
        return d * d;
    }

    public static double degreesToRadians(double degrees)
    {
        return (Math.PI / 180.0) * degrees;
    }

    public static double radiansToDegrees(double radians)
    {
        return (180.0 / Math.PI) * radians;
    }

    /// <summary>
    /// Normalizes specified angle so it becomes greater or equal to zero and less then 360 degrees.
    /// </summary>
    /// <param name="angle">Source angle.</param>
    public static double normalizeAngle(double angle)
    {
        if (Math.abs(angle) >= 360)
            angle %= 360.0;

        if (angle < 0)
            angle += 360.0;

        return angle;
    }

    /// <summary>
    /// Normalizes specified angle so it becomes greater or equal to zero and less then 360 degrees.
    /// </summary>
    /// <param name="angle">Source angle.</param>
    public static double normalizeAngleRadians(double angle)
    {
        return degreesToRadians(normalizeAngle(radiansToDegrees(angle)));
    }

    /// <summary>
    /// Checks if the specified angle is zero.
    /// </summary>
    public static boolean isZeroAngle(double angle)
    {
        return MathUtil.isZero(MathUtil.normalizeAngle(angle));
    }


    /// <summary>
    /// Fits a value to a specified range.
    /// </summary>
    public static double fitToRange(double value, double minValue, double maxValue)
    {
        return Math.min(Math.max(value, minValue), maxValue);
    }

    /// <summary>
    /// Fits a value to a specified range.
    /// </summary>
    public static float fitToRange(float value, float minValue, float maxValue)
    {
        return Math.min(Math.max(value, minValue), maxValue);
    }

    /// <summary>
    /// Fits a value to a specified range.
    /// </summary>
    public static int fitToRange(int value, int minValue, int maxValue)
    {
        return Math.min(Math.max(value, minValue), maxValue);
    }

    /// <summary>
    /// Returns true, if a specified value fits in a specified range.
    /// </summary>
    public static boolean isInRange(int value, int minValue, int maxValue)
    {
        if (minValue > maxValue)
            return ((value <= minValue) && (value >= maxValue));

        return ((value >= minValue) && (value <= maxValue));
    }

    /// <summary>
    /// Multiplies a square matrix and a vector.
    /// </summary>
    public static double[] matrixMultiply(double[] squareMatrix, double[] vector)
    {
        int length = vector.length;
        Debug.assertTrue(length * length == squareMatrix.length);

        double[] result = new double[length];
        for (int i = 0; i < squareMatrix.length; i++)
            result[i / length] += squareMatrix[i] * vector[i % length];
        return result;
    }

    /// <summary>
    /// Rounds a double value and casts it to int in a way that will produce the same result on .NET and Java.
    /// </summary>
    public static int doubleToInt(double value)
    {
        // WORDSJAVA-2352, WORDSJAVA-2641- '(int)double.PositiveInfinity' returns int.MinValue on .Net.
        // '(int)double.MaxValue', '(int)double.NaN' - the same.
        if (Double.isInfinite(value) || Double.isNaN(value) || value == Double.MAX_VALUE)
            return Integer.MIN_VALUE;

        return (int)msMath.round(value);
    }

    /// <summary>
    /// Rounds a double value and casts it to short in a way that will produce the same result on .NET and Java.
    /// </summary>
    public static short doubleToShort(double value)
    {
        return (short)((/*ushort*/short)msMath.round(value));
    }

    /// <summary>
    /// Casts "Int32" numeric value to "Int16", possible overflows are ignored.
    /// </summary>
    /// <returns>
    /// "Int32" value, which was fit to the "Int16" values range.
    /// </returns>
    public static int castIntToShort(int value)
    {
        final int SHORT_CAPACITY = (msUShort.MAX_VALUE & 0xFFFF) + 1;

        return (value % SHORT_CAPACITY > Short.MAX_VALUE) ?
           -(SHORT_CAPACITY - value % SHORT_CAPACITY) : value % SHORT_CAPACITY;
    }

    /// <summary>
    /// Truncates a double value and casts it to int.
    /// </summary>
    public static int truncate(double value)
    {
        long longValue = (long)value;
        return (int)longValue;
    }

    /// <summary>
    /// Return true if Double value is zero.
    /// </summary>
    /// <param name="value">Double value</param>
    public static boolean isZero(double value)
    {
        return (Math.abs(value) < Double.MIN_VALUE);
    }

    /// <summary>
    /// Return true if float value is zero.
    /// </summary>
    /// <param name="value">Float value</param>
    public static boolean isZero(float value)
    {
        return (Math.abs(value) < Float.MIN_VALUE);
    }

    /// <summary>
    /// Return true if integer value is zero.
    /// </summary>
    /// <dev>fix C++ ambiguity</dev>
    /// <param name="value">Float value</param>
    public static boolean isZero(int value)
    {
        return value == 0;
    }

    /// <summary>
    /// Return true if all values in the Double array are zeros.
    /// </summary>
    /// <param name="values">Double values array</param>
    public static boolean isZero(/*params*/ double... values)
    {
        for (int i = 0; i < values.length; i++)
            if (!isZero(values[i]))
                return false;

        return true;
    }

    /// <summary>
    /// Normalizes a zero value by removing the minus sign from negative zero (i.e. -0.0).
    /// If the input value is zero, it returns positive zero (0.0). Otherwise, it returns the original value.
    /// </summary>
    /// <param name="value">The input double value to be normalized.</param>
    /// <returns>A double value with the minus sign removed if it is zero, otherwise the original value.</returns>
    public static double normalizeZero(double value)
    {
        // According to IEEE 754, −0 and +0 compare as equal.
        return (value == 0.0) ? 0.0 : value;
    }

    /// <summary>
    /// Normalizes a zero value by removing the minus sign from negative zero (i.e. -0.0f).
    /// If the input value is zero, it returns positive zero (0.0f). Otherwise, it returns the original value.
    /// </summary>
    /// <param name="value">The input float value to be normalized.</param>
    /// <returns>A float value with the minus sign removed if it is zero, otherwise the original value.</returns>
    public static float normalizeZero(float value)
    {
        // According to IEEE 754, −0 and +0 compare as equal.
        return (value == 0.0f) ? 0.0f : value;
    }

    /// <summary>
    /// Return true if Double value is <c>double.MinValue</c>.
    /// </summary>
    /// <param name="value">Double value</param>
    public static boolean isMinValue(double value)
    {
        return areEqual(value, -Double.MAX_VALUE);
    }

    /// <summary>
    /// Checks whether two double values are 'almost' equal.
    /// Values are considered equal if absolute difference between values is less than 1e-10.
    /// </summary>
    public static boolean areEqual(double value1, double value2)
    {
        final double TOLERANCE = 1e-10;
        return areEqual(value1, value2, TOLERANCE);
    }

    /// <summary>
    /// Checks whether two double values are 'almost' equal.
    /// </summary>
    /// <param name="value1">The first value to compare.</param>
    /// <param name="value2">The second value to compare.</param>
    /// <param name="tolerance">The maximum acceptable difference, when these values are considered as equal.</param>
    /// <returns>"True" if the difference between values is less than tolerance, otherwise - "false"</returns>
    public static boolean areEqual(double value1, double value2, double tolerance)
    {
        return (Math.abs(value1 - value2) < Math.abs(tolerance));
    }

    /// <summary>
    /// Checks whether two float values are 'almost' equal.
    /// Values are considered equal if absolute difference between values is less than 1e-10.
    /// </summary>
    public static boolean areEqual(float value1, float value2)
    {
        final float TOLERANCE = 1e-10f;
        return areEqual(value1, value2, TOLERANCE);
    }

    /// <summary>
    /// Checks whether two float values are 'almost' equal.
    /// </summary>
    /// <param name="value1">The first value to compare.</param>
    /// <param name="value2">The second value to compare.</param>
    /// <param name="tolerance">The maximum acceptable difference, when these values are considered as equal.</param>
    /// <returns>"True" if the difference between values is less than tolerance, otherwise - "false"</returns>
    public static boolean areEqual(float value1, float value2, float tolerance)
    {
        return (Math.abs(value1 - value2) < Math.abs(tolerance));
    }

    /// <summary>
    /// Checks whether two points are 'almost' equal.
    /// </summary>
    public static boolean areEqual(/*PointF*/long point1, /*PointF*/long point2)
    {
        return areEqual(msPointF.getX(point1), msPointF.getX(point2)) && areEqual(msPointF.getY(point1), msPointF.getY(point2));
    }

    /// <summary>
    /// Checks whether two rectangles are 'almost' equal.
    /// </summary>
    public static boolean areEqual(RectangleF r1, RectangleF r2)
    {
        return areEqual(r1.getX(), r2.getX()) && areEqual(r1.getY(), r2.getY()) && areEqual(r1.getWidth(), r2.getWidth()) && areEqual(r1.getHeight(), r2.getHeight());
    }

    /// <summary>
    /// Checks whether the left-hand operand is less than or equal to the right-hand operand.
    /// </summary>
    /// <remarks>
    /// A tolerance must be used when comparing two values, because the comparison of two numbers might change between
    /// versions of the .NET Framework (the precision of the numbers' internal representation might change) (see Test22038).
    /// </remarks>
    /// <param name="leftHandOperand">The float value representing the left-hand operand</param>
    /// <param name="rightHandOperand">The float value representing the right-hand operand</param>
    /// <returns>"True" if the left value is less than or equal to the right value, otherwise - "false"</returns>
    public static boolean isLessOrEqual(float leftHandOperand, float rightHandOperand)
    {
        return (leftHandOperand < rightHandOperand) || areEqual(leftHandOperand, rightHandOperand);
    }

    /// <summary>
    /// Checks whether the left-hand operand is less than or equal within the given tolerance to the right-hand operand.
    /// </summary>
    public static boolean isLessOrEqual(float leftHandOperand, float rightHandOperand, float tolerance)
    {
        return (leftHandOperand < rightHandOperand) || areEqual(leftHandOperand, rightHandOperand, tolerance);
    }

    /// <summary>
    /// Checks whether the left-hand operand is less than or equal to the right-hand operand.
    /// </summary>
    /// <param name="leftHandOperand">The double value representing the left-hand operand</param>
    /// <param name="rightHandOperand">The double value representing the right-hand operand</param>
    /// <returns>"True" if the left value is less than or equal to the right value, otherwise - "false"</returns>
    public static boolean isLessOrEqual(double leftHandOperand, double rightHandOperand)
    {
        return (leftHandOperand < rightHandOperand) || areEqual(leftHandOperand, rightHandOperand);
    }

    /// <summary>
    /// Checks whether the left-hand operand is less than or equal within the given tolerance to the right-hand operand.
    /// </summary>
    public static boolean isLessOrEqual(double leftHandOperand, double rightHandOperand, double tolerance)
    {
        return (leftHandOperand < rightHandOperand) || areEqual(leftHandOperand, rightHandOperand, tolerance);
    }

    /// <summary>
    /// Checks whether the left-hand operand is greater than or equal to the right-hand operand.
    /// </summary>
    /// <param name="leftHandOperand">The float value representing the left-hand operand</param>
    /// <param name="rightHandOperand">The float value representing the right-hand operand</param>
    /// <returns>"True" if the left value is greater than or equal to the right value, otherwise - "false"</returns>
    public static boolean isGreaterOrEqual(float leftHandOperand, float rightHandOperand)
    {
        return (leftHandOperand > rightHandOperand) || areEqual(leftHandOperand, rightHandOperand);
    }

    /// <summary>
    /// Checks whether the left-hand operand is greater than or equal to the right-hand operand.
    /// </summary>
    /// <param name="leftHandOperand">The double value representing the left-hand operand</param>
    /// <param name="rightHandOperand">The double value representing the right-hand operand</param>
    /// <returns>"True" if the left value is greater than or equal to the right value, otherwise - "false"</returns>
    public static boolean isGreaterOrEqual(double leftHandOperand, double rightHandOperand)
    {
        return (leftHandOperand > rightHandOperand) || areEqual(leftHandOperand, rightHandOperand);
    }

    /// <summary>
    /// Checks whether the left-hand operand is greater than or equal within the given tolerance to the right-hand operand.
    /// </summary>
    public static boolean isGreaterOrEqual(double leftHandOperand, double rightHandOperand, double tolerance)
    {
        return (leftHandOperand > rightHandOperand) || areEqual(leftHandOperand, rightHandOperand, tolerance);
    }

    /// <summary>
    /// Returns a value indicating whether the specified value is a prime number.
    /// </summary>
    /// <dev>
    /// Based on ROTOR sources.
    /// </dev>
    public static boolean isPrime(int candidate)
    {
        if ((candidate & 1) != 0)
        {
            int limit = (int)Math.sqrt(candidate);
            for (int divisor = 3; divisor <= limit; divisor += 2)
            {
                if ((candidate % divisor) == 0)
                    return false;
            }

            return true;
        }

        return (candidate == 2);
    }

    /// <summary>
    /// Returns the next prime number after the specified min value to be used as a hashtable capacity.
    /// If the calculated prime number is out of Int32 range, returns the specified min value.
    /// </summary>
    /// <dev>
    /// Based on ROTOR sources.
    /// </dev>
    public static int getPrimeForHashtable(int min)
    {
        ArgumentUtil.checkNonNegative(min, "min");

        for (int i = 0; i < G_PRIMES_FOR_HASHTABLE.length; i++)
        {
            int prime = G_PRIMES_FOR_HASHTABLE[i];
            if (prime >= min)
                return prime;
        }

        // Outside of our predefined table. Compute the hard way.
        for (int i = (min | 1); i < Integer.MAX_VALUE; i += 2)
        {
            if (isPrime(i))
                return i;
        }

        return min;
    }

    /// <summary>
    /// Returns the maximum int value from a specified list.
    /// </summary>
    public static int max(/*params*/ int... list)
    {
        Debug.assertTrue(list.length > 0);

        int max = list[0];
        for (int i = 1; i < list.length; i++)
            max = Math.max(max, list[i]);

        return max;
    }

    /// <summary>
    /// Returns the minimum float value from the specified list.
    /// </summary>
    public static float min(/*params*/ float... list)
    {
        float min = Float.MAX_VALUE;
        for (int i = 0; i < list.length; i++)
            min = Math.min(min, list[i]);

        return min;
    }

    /// <summary>
    /// Returns the maximum float value from the specified list.
    /// </summary>
    public static float max(/*params*/ float... list)
    {
        float max = (-Float.MAX_VALUE);
        for (int i = 0; i < list.length; i++)
            max = Math.max(max, list[i]);

        return max;
    }

    /// <summary>
    /// Calculates Y value for the polyline defined by specified array of <see cref="Point"/>.
    /// </summary>
    /// <remarks>
    /// An array must be sorted in ascending <see cref="Point.X"/> value and
    /// all <see cref="Point.X"/> values must be unique.
    /// </remarks>>
    public static int getPolylineY(int x, /*Point*/long[] function)
    {
        if (x < msPoint.getX(function[0]))
            return 0;
        int length = function.length;

        int rangeId = 1;
        // Find an appropriate range. May be changed to binary search.
        for (; rangeId < length; rangeId++)
        {
            if (msPoint.getX(function[rangeId - 1]) >= msPoint.getX(function[rangeId]))
                throw new IllegalArgumentException("The array of points is given incorrectly.");

            if (x < msPoint.getX(function[rangeId]))
                break;
        }

        // Last polyline segment (last defined point, infinity) has the same line equation like previous segment.
        if (rangeId == length)
            rangeId--;

        int x0 = msPoint.getX(function[rangeId - 1]);
        int x1 = msPoint.getX(function[rangeId]);
        int y0 = msPoint.getY(function[rangeId - 1]);
        int y1 = msPoint.getY(function[rangeId]);

        return y0 + (x - x0) * (y1 - y0) / (x1 - x0);
    }

    /// <summary>
    /// Divides the given integers, rounding the quotient to the nearest integer.
    /// </summary>
    /// <remarks>
    /// Use instead of division to get a rounded integer result, eg 1 instead of 0 from 9/10.
    /// Division by zero is not handled.
    /// Rounding is standard, not banker's (5/2 rounds to 3, not 2).
    /// </remarks>
    public static int divide(int dividend, int divisor)
    {
        return mulDiv(1, dividend, divisor);
    }

    /// <summary>
    /// Returns a rounded value of p1*p2/divisor, handling int32 arithmetic overflow.
    /// </summary>
    /// <param name="p1"></param>
    /// <param name="p2"></param>
    /// <param name="divisor"></param>
    /// <remarks>
    /// The result of p1*p2 is handled via int64, so no arithmetic overflow happens.
    /// This is needed to reproduce some MS Word calculations for table grid,
    /// so that rounding happens in the same way.
    /// </remarks>
    public static int mulDiv(int p1, int p2, int divisor)
    {
        long product = (long)p1 * p2;
        int result = (int)(product / divisor);

        int remainder = (int)(product % divisor);
        int minRoundUpRemainder = divisor / 2 + divisor % 2;
        if (remainder >= minRoundUpRemainder && remainder != 0)
            ++result;

        return result;
    }

    /// <summary>
    /// Returns true if the value is odd (not even).
    /// </summary>
    public static boolean isOdd(long value)
    {
        return ((value & 0x01) != 0);
    }

    /// <summary>
    /// Integer divide and round up if necessary so the remainder will be zero.
    /// </summary>
    public static int divUp(long value, int divider)
    {
        long result = value / divider;
        if ((value % divider) != 0)
            result++;
        return (int)result;
    }

    /// <summary>
    /// Rounds up the value to be divisible by divider with zero remainder.
    /// </summary>
    public static int roundUp(long value, int divider)
    {
        return divUp(value, divider) * divider;
    }

    /// <summary>
    /// Casts a double to int32 in a way that uint32 are "correctly" casted too (they become negative numbers).
    /// </summary>
    public static int castDoubleToInt(double value)
    {
        long temp = (long)value;
        return (int)temp;
    }

    /// <summary>
    /// Calculate the factorial of a number.
    /// </summary>
    public static int factorial(int n)
    {
        int r = 1;
        while (n > 1)
            r *= n--;
        return r;
    }

    private static /*final*/ int[] G_PRIMES_FOR_HASHTABLE = {
        3, 7, 11, 17, 23, 29, 37, 47, 59, 71, 89, 107, 131, 163, 197, 239, 293, 353, 431, 521, 631, 761, 919,
        1103, 1327, 1597, 1931, 2333, 2801, 3371, 4049, 4861, 5839, 7013, 8419, 10103, 12143, 14591,
        17519, 21023, 25229, 30293, 36353, 43627, 52361, 62851, 75431, 90523, 108631, 130363, 156437,
        187751, 225307, 270371, 324449, 389357, 467237, 560689, 672827, 807403, 968897, 1162687, 1395263,
        1674319, 2009191, 2411033, 2893249, 3471899, 4166287, 4999559, 5999471, 7199369};

    /// <summary>
    /// Returns the floating-point value that corresponds to a specific bit pattern.
    /// Sources from https://msdn.microsoft.com/en-us/library/aa987800(v=vs.80).aspx
    /// </summary>
    public static float intBitsToFloat(int bits)
    {
        int sign = ((bits & 0x80000000L) == 0) ? 1 : -1;
        int exponent = ((bits & 0x7f800000) >> 23);
        int mantissa = (bits & 0x007fffff);

        mantissa |= 0x00800000;
        // Calculate the result:
        return (float)(sign * mantissa * Math.pow(2.0, exponent - 150));
    }

    /// <summary>
    /// Normalizes rectangle by inverting negative width and/or height.
    /// </summary>
    public static RectangleF normalizeRectangle(RectangleF rectangle)
    {
        RectangleF result = rectangle;
        if (result.getWidth() < 0)
            result = new RectangleF(result.getX() + result.getWidth(), result.getY(), -result.getWidth(), result.getHeight());
        if (result.getHeight() < 0)
            result = new RectangleF(result.getX(), result.getY() + result.getHeight(), result.getWidth(), -result.getHeight());
        return result;
    }

    /// <summary>
    /// Normalizes rectangle by inverting negative width and/or height.
    /// </summary>
    public static Rectangle normalizeRectangle(Rectangle rectangle)
    {
        Rectangle result = rectangle;
        if (result.getWidth() < 0)
            result = new Rectangle(result.getX() + result.getWidth(), result.getY(), -result.getWidth(), result.getHeight());
        if (result.getHeight() < 0)
            result = new Rectangle(result.getX(), result.getY() + result.getHeight(), result.getWidth(), -result.getHeight());
        return result;
    }

    /// <summary>
    /// Gets the estimated precision that should be used while calculating axis values.
    /// </summary>
    /// <param name="value">The value used as the basis</param>
    /// <returns>The calculated precision</returns>
    public static int getPrecision(double value)
    {
        // Remove integer part of value.
        value -= (int)value;

        if (isZero(value))
            return 0;

        // Implicit casting for Java: it is difference for infinity double values.
        int power = doubleToInt(msMath.round(Math.log10(value)));
        value *= Math.pow(10.0, (-power));
        value -= (int)value;

        // If values is so small, than use the reduced tolerance to correct comparison.
        power -= java.math.BigDecimal.valueOf(value).scale();
        return power;
    }

    /// <summary>
    /// Gets the tolerance used when comparing values (the maximum value of precision is -10).
    /// </summary>
    public static double getTolerance(double value)
    {
        int precision = Math.min(-10, getPrecision(value));
        return Math.pow(10.0, precision);
    }

    /// <summary>
    /// Gets the mean (arithmetic average) of specified values.
    /// </summary>
    public static double getMean(double[] values)
    {
        double result = 0.0;
        for (int i = 0; i < values.length; i++)
            result += values[i];

        return result / values.length;
    }

    /// <summary>
    /// Gets the standard deviation of specified values.
    /// </summary>
    public static double getStandardDeviation(double[] values)
    {
        double mean = getMean(values);
        double sumOfSquares = 0.0;

        for (int i = 0; i < values.length; i++)
            sumOfSquares += Math.pow(values[i] - mean, 2.0);

        double variance = sumOfSquares / (values.length - 1);

        return Math.sqrt(variance);
    }
}

