// Parts of this file are a Java port, made by Aspose, of .NET Framework code. Microsoft publishes that source
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/math.cs
//
// Those parts: Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

//sk 10.05.2010

package com.aspose.ms.System;

/**
 * Contains members that present in System.Math but absent in java.lang.Math.
 */
public class msMath
{
    /**
     * .Net has Math.Min() and Max() for all types - byte, short, etc.
     * Java has only for int, long, float and double.
     */
    public static short min(short a, short b)
    {
        return (a <= b) ? a : b;
    }

    public static short max(short a, short b)
    {
        return (a >= b) ? a : b;
    }

    public static int minUint(int a, int b)
    {
        return ((a ^ Integer.MIN_VALUE) <= (b ^ Integer.MIN_VALUE)) ? a : b;
    }

    /**
     * Returns the logarithm of a specified number in a specified base.
     * Ported from Rotor.
     */
    public static double log(double a, double newBase)
    {
        if (newBase == 1)
            return Double.NaN;
        if (a != 1 && (newBase == 0 || newBase == Double.POSITIVE_INFINITY))
            return Double.NaN;

        return (Math.log(a)/Math.log(newBase));
    }


    public static double truncate(double value)
    {
        return value < 0 ? Math.ceil(value) : Math.floor(value);
    }

    /**
     * Uses banker's rounding like in .NET Math.Round.
     */
    public static double round(double value)
    {
        //We must use Math.rint(), not Math.round because .NET and Java use different algorithms.
        //rint() can return negative zero - we don't want that in our code.
        double rintResult = Math.rint(value);
        return (rintResult == -0.0d) ? 0.0d : rintResult;
    }

    /**
     * Uses banker's rounding like in .NET Math.Round.
     */
    public static double round(double value, int digits)
    {
        if (Double.isNaN(value))
            return value;

        // Code taken from Rotor. Simplified.
        double power10 = roundPower10Double[digits];
        value *= power10;
        value = round(value);   // Use banker's rounding.
        value /= power10;
        return value;
    }

    /**
     * Uses banker's rounding like in .NET Math.Round.
     */
    public static double round(double value, int digits, /*MidpointRounding*/int mode)
    {
        if ((digits < 0) || (digits > maxRoundingDigits))
            throw new IllegalArgumentException("digits");

        if (mode < MidpointRounding.TO_EVEN || mode > MidpointRounding.AWAY_FROM_ZERO)
            throw new IllegalArgumentException("mode");

        if (Double.isNaN(value))
            return value;

        if (Math.abs(value) < doubleRoundLimit)
        {
            Double power10 = roundPower10Double[digits];
            value *= power10;
            if (mode == MidpointRounding.AWAY_FROM_ZERO)
            {
                double fraction = value % 1;
                if (Math.abs(fraction) >= 0.5d)
                    value += Math.signum(fraction);

                // FIX WORDSJAVA-1065: Enlarged crop of image.
                // Remove the fractional part taking into account of value's sign.
                if (value > 0)
                    value = Math.floor(value);
                else if (value < 0)
                    value = Math.ceil(value);
            }
            else
            {
                value = round(value);   // Use banker's rounding.
            }

            value /= power10;
        }
        return value;
    }

    /**
     * Java hasn't Math.signum(int).
     * Returns -1, 0, or 1 if the sign of the number is negative, 0, or positive.
     */
    public static int sign(int value)
    {
        if (value < 0)
            return -1;
        else if (value > 0)
            return 1;
        else
            return 0;
    }

    /**
     * Java's Math.signum(float) returns float, but .Net Math.sign(float) returns int.
     * Returns -1, 0, or 1 if the sign of the number is negative, 0, or positive.
     */
    public static int sign(float value)
    {
        if (value < 0)
            return -1;
        else if (value > 0)
            return 1;
        else
            return 0;
        // .Net throw for NaN, Java returns NaN itself - let's return 0 for resilience (?)
    }

    private static final int maxRoundingDigits = 15;
    private static double doubleRoundLimit = 1e16d;

    // This table is required for the Round function which can specify the number of digits to round to
    private static final double[] roundPower10Double = new double[] {
            1E0, 1E1, 1E2, 1E3, 1E4, 1E5, 1E6, 1E7, 1E8,
            1E9, 1E10, 1E11, 1E12, 1E13, 1E14, 1E15
    };
}
