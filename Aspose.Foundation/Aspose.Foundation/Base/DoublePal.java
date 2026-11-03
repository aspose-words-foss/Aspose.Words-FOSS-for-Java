// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 25/09/2009 by Roman Korchagin

package com.aspose;

import com.aspose.ms.System.msMath;

/// <summary>
/// This class is to be manually ported to Java.
/// </summary>
public /*static*/ class DoublePal
{
    /* Simulation of static class by using private constructor */
    private DoublePal()
    {}

    /// <summary>
    /// Casting double to int differs when double value is Double.NaN:
    /// .Net returns Integer.MinValue,
    /// Java returns 0.
    ///
    /// The method allows Java behave like .Net.
    /// </summary>
    public static int toInt(double value)
    {
        return Double.isNaN(value) ? Integer.MIN_VALUE : (int) value;
    }

    //JAVA-added to resolve .Net string autoboxing. Note .Net-style name of the method.
    public static int ToInt(Double value)
    {
        double valueType = value.doubleValue();
        return Double.isNaN(valueType) ? Integer.MIN_VALUE : (int) valueType;
    }

    /// <summary>
    /// Rounds up a double value and casts it to the nearest int in a way that will produce the same result on .NET and Java.
    /// </summary>
    public static int roundToIntUp(double value)
    {
        // Java Math.round() rounds as .Net MidpointRounding.AwayFromZero, i.e. 0.5 rounded to 1.
        return (int)Math.round(value);
    }

    /// <summary>
    /// Trims double value to the specified number of digits after decimal point.
    /// </summary>
    public static double trim(double value, int digits)
    {
        double p = Math.pow(10.0, digits);
        return (msMath.truncate(value * p))/p;
    }

    public static int hashCode(double value)
    {
        return new Double(value).hashCode();
    }
}

